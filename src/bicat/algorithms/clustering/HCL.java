
/*
 *                                BBiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                DOI for citing the release : 10.5281/zenodo.33117
 *
 *                                Copyright (c) 2015 Taghi Aliyev
 *
 *                                This file is part of BBiCat.
 *
 *                                BBiCat is a free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                BBiCat is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with BBiCat.  If not, see <http://www.gnu.org/licenses/>.
 *
 *                                You can contact the author at the following email address:
 *                                taghi.aliyev@cern.ch
 *
 *                                PLEASE NOTE THAT ORIGINAL GROUP AT ETH ZURICH HAS BEEN DISSOLVED AND SO,
 *                                CONTACTING TAGHI ALIYEV MIGHT BE MORE BENEFITIAL FOR ANY QUESTIONS.
 *
 *                                IN NO EVENT SHALL THE AUTHORS AND MAINTAINERS OF THIS SOFTWARE BE LIABLE TO
 *                                ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 *                                ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE
 *                                AUTHORS AND MAINTAINERS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *                                AUTHORS AND THE MAINTAINERS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 *                                BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *                                FOR A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HEREUNDER IS ON AN "AS IS"
 *                                BASIS, AND THE AUTHORS AND THE MAINTAINERS HAS NO OBLIGATION TO PROVIDE
 *                                MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS
 *
 *                                COPYRIGHT NOTICE FROM THE ORIGINAL SOFTWARE:
 *
 *                                Copyright notice : Copyright (c) 2005 Swiss Federal Institute of Technology, Computer
 *                                Engineering and Networks Laboratory. All rights reserved.
 *                                BicAT - A Biclustering Analysis Toolbox
 *                                IN NO EVENT SHALL THE SWISS FEDERAL INSTITUTE OF TECHNOLOGY, COMPUTER
 *                                ENGINEERING AND NETWORKS LABORATORY BE LIABLE TO ANY PARTY FOR
 *                                DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING
 *                                OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE
 *                                SWISS FEDERAL INSTITUTE OF TECHNOLOGY, COMPUTER ENGINEERING AND
 *                                NETWORKS LABORATORY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 *                                DAMAGE.
 *
 *                                THE SWISS FEDERAL INSTITUTE OF TECHNOLOGY, COMPUTER ENGINEERING AND
 *                                NETWORKS LABORATORY, SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 *                                BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *                                FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HEREUNDER IS
 *                                ON AN "AS IS" BASIS, AND THE SWISS FEDERAL INSTITUTE OF TECHNOLOGY,
 *                                COMPUTER ENGINEERING AND NETWORKS LABORATORY HAS NO OBLIGATION
 *                                TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 *                                MODIFICATIONS.
 */

package bicat.algorithms.clustering;

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;
import lombok.Data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Class that implements the HCL clustering method. For the possible set of values for variables,
 * please refer to the MethodConstants.java
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */

@Data
public class HCL {

    private int linkage_method;

    private int distance_metric;

    private int modus;

    private int N;

    private boolean DEBUG = false;

    private BicatGui owner;

    private UtilFunctionalities engine;

    // Some additional constructors that might be needed in future.

    public HCL(int linkage, int distMetr, int mode) {
        linkage_method = linkage;
        distance_metric = distMetr;
        modus = mode;
    }

    public HCL(BicatGui o, int nr_Cs, int linkage, int dist, int mode) {
        owner = o;
        engine = o.getUtilEngine();
        N = nr_Cs;
        linkage_method = linkage;
        distance_metric = dist;
        modus = mode;
    }

    public HCL(UtilFunctionalities engine, int nr_Cs, int linkage, int dist, int mode) {
        this.engine = engine;
        N = nr_Cs;
        linkage_method = linkage;
        distance_metric = dist;
        modus = mode;
    }

    public HCL(BicatGui o, int linkage, int dist, int mode) {
        owner = o;
        this.engine = o.getUtilEngine();
        linkage_method = linkage;
        distance_metric = dist;
        modus = mode;
    }

    public void runHCL(float[][] data, int distMetr) {
        if (modus == MethodConstants.AGGLOMERATIVE)
            runAgglomerative(data, distMetr);
        else if (modus == MethodConstants.DIVISIVE) // not implemented yet
            try {
                throw new Exception("This method is not implemented yet! Please do not use it!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private double curr_distance;

    private String HCL_output;

    // ===========================================================================

    /**
     * First dim (data.length) is the number of items to cluster. Dementspreched
     * is the second dim (data[0].length) number of attributes.
     * <p>
     * Add the labeling management. + Outputting (joins)
     * <p>
     * OUTPUT format: C.nr_joins.dist\n L x1 L x2
     * <p>
     * C.nr_joins.dist\n L x3 C N1 ...
     *
     * @param distMetr
     */
    public void runAgglomerative(float[][] data, int distMetr) {

        StringBuffer sb = new StringBuffer(); // at the end, write it OUT
        distance_metric = distMetr;
        int nr_genes = data.length;
        int nr_joins = 0; // numbering of clusters begins @ 1
        int[] back_translation = new int[nr_genes]; // current L idx -> original
        Vector clusters = new Vector(); // contains extended view of the
        // clusters, as they are created

        if (DEBUG)
            System.out.println("nr_items = " + nr_genes);

        // INIT: compute the distance matrix, and create the singletons:
        double[][] distance_matrix = new double[nr_genes][nr_genes];
        if (MethodConstants.debug)
            System.out.println("The distance metric is: " + distance_metric);

        for (int i = 0; i < nr_genes; i++) {
            distance_matrix[i][i] = 0.0;
            for (int j = i + 1; j < nr_genes; j++) {

                switch (distance_metric) {

                    case MethodConstants.EUCLIDEAN_DISTANCE:
                        distance_matrix[i][j] = Util.computeEuclideanDistance(
                                data[i], data[j]);
                        break;

                    case MethodConstants.PEARSON_CORRELATION:
                        distance_matrix[i][j] = Util
                                .computePearsonCorrelationDistance(data[i], data[j]);
                        break;

                    case MethodConstants.MANHATTAN_DISTANCE:
                        distance_matrix[i][j] = Util.computeManhattanDistance(
                                data[i], data[j]);
                        break;

                    case MethodConstants.MINKOWSKI_DISTANCE:
                        distance_matrix[i][j] = Util.computeMinkowskiDistance(
                                data[i], data[j]);
                        break;

                    case MethodConstants.COSINE_DISTANCE:
                        distance_matrix[i][j] = Util.computeCosineDistance(data[i],
                                data[j]);
                        break;

                    default:
                        break; /* not foreseen */

                }

                distance_matrix[j][i] = distance_matrix[i][j];
                if (MethodConstants.debug) System.out.println("i: " + i + " j: " + j
                        + " distance Matrix[i][j]: " + distance_matrix[i][j]);

            }
        }

        Vector cluster_idxs = new Vector();
        for (int i = 0; i < nr_genes; i++) {
            int[] cl = new int[1];
            cl[0] = i;
            cluster_idxs.add(cl);
        }

        Vector cluster_dists = new Vector();
        for (int i = 0; i < nr_genes; i++) {
            Vector distance_vector = new Vector();
            for (int j = 0; j < nr_genes; j++)
                distance_vector.add(new Double(distance_matrix[i][j]));
            cluster_dists.add(distance_vector);
        }

        for (int i = 0; i < nr_genes; i++) {
            back_translation[i] = i;
        }

        while (cluster_idxs.size() > N) {

            // 2. merge (into) clusters
            int[] idxs = findMinDistancePair(cluster_dists); // 2 (i,j)

            // if(DEBUG) System.out.println("cl_dists size = " +
            // cluster_dists.size());

            int[] cl_1 = (int[]) cluster_idxs.get(idxs[0]);
            int[] cl_2 = (int[]) cluster_idxs.get(idxs[1]);

            if (DEBUG) {
                if (idxs[0] >= idxs[1]) {
                    System.out.println("ATTENTION: idxs[0] >= idxs[1] ");
                }
            }
            // --- Management Ops:
            nr_joins++;
            sb.append("C " + nr_joins + " " + curr_distance + " ");
            if (((int[]) cluster_idxs.get(idxs[0])).length == 1) // sb.append("L
                sb.append("L " + back_translation[idxs[0]] + " ");
            else {
                int[] cl_0 = ((int[]) cluster_idxs.get(idxs[0]));
                int N = findClusterIdx(cl_0, clusters);
                sb.append("C " + N + " "); // \n");
            }
            if (((int[]) cluster_idxs.get(idxs[1])).length == 1)
                sb.append("L " + back_translation[idxs[1]] + " "); // \n\n");
            else {
                int[] cl_0 = ((int[]) cluster_idxs.get(idxs[1]));
                int N = findClusterIdx(cl_0, clusters);
                sb.append("C " + N + " "); // \n\n");
            }

            Vector new_cl = new Vector();
            int[] new_arr = appendArrays(cl_1, cl_2);
            for (int i = 0; i < new_arr.length; i++)
                new_cl.add(new Integer(new_arr[i]));
            clusters.add(new_cl); // its idx is -1 of the ""official"" idx

            cluster_idxs.remove(idxs[1]);
            cluster_idxs.set(idxs[0], appendArrays(cl_1, cl_2));

            // mngmt op:
            for (int j = idxs[1]; j < cluster_idxs.size(); j++)
                back_translation[j] = back_translation[j + 1];
            for (int j = cluster_idxs.size(); j < nr_genes; j++)
                back_translation[j] = -1;

            // 3. recompute the distances
            for (int j = 0; j < cluster_idxs.size(); j++) {
                switch (linkage_method) {

                    case MethodConstants.SINGLE_LINKAGE:
                        ((Vector) cluster_dists.get(idxs[0])).set(j, min(
                                (Double) ((Vector) cluster_dists.get(idxs[0]))
                                        .get(j), (Double) ((Vector) cluster_dists
                                        .get(idxs[1])).get(j)));
                        break;

                    case MethodConstants.COMPLETE_LINKAGE:
                        ((Vector) cluster_dists.get(idxs[0])).set(j, max(
                                (Double) ((Vector) cluster_dists.get(idxs[0]))
                                        .get(j), (Double) ((Vector) cluster_dists
                                        .get(idxs[1])).get(j)));
                        break;

                    case MethodConstants.AVERAGE_LINKAGE:
                        ((Vector) cluster_dists.get(idxs[0])).set(j, avg(
                                (Double) ((Vector) cluster_dists.get(idxs[0]))
                                        .get(j), (Double) ((Vector) cluster_dists
                                        .get(idxs[1])).get(j), cl_1.length,
                                cl_2.length));
                        break;

                    default:
                        break; /* not foreseen! */
                }
            }
            for (int i = 0; i <= cluster_idxs.size(); i++)
                ((Vector) cluster_dists.get(i)).remove(idxs[1]);

            cluster_dists.remove(idxs[1]);

            for (int i = 0; i < cluster_idxs.size(); i++) {
                if (i != idxs[0])
                    ((Vector) cluster_dists.get(i)).set(idxs[0],
                            ((Vector) cluster_dists.get(idxs[0])).get(i));
            }

            // repeat 2 and 3 until all items are in a single cluster
        }

        HCL_output = sb.toString();

        if (MethodConstants.debug)
            System.out.println("nr_items, nr_joins: " + nr_genes + ", "
                    + nr_joins);

        System.out.println("Here and GUI is : " + owner + ", and engine is : " + engine);
        int[] chipsTemplate = new int[engine.getPre().getWorkingChipCount()];
        System.out.println("lenght of array is : " + chipsTemplate.length);
        for (int i = 0; i < chipsTemplate.length; i++)
            chipsTemplate[i] = i;

        // ....
        quasi_bi_clusters = new LinkedList();
        for (int i = 0; i < cluster_idxs.size(); i++) {

            int[] genes = (int[]) cluster_idxs.get(i);
            int[] chips = chipsTemplate.clone();
            bicat.biclustering.Bicluster bc = new bicat.biclustering.Bicluster(
                    i + 1, genes, chips);
            quasi_bi_clusters.add(bc);
        }
        if (engine == null)
            System.out.println("Engine is null!");

        engine.setData(engine.getPre().getPreprocessedData());
        if (owner != null) {
            owner.getPp().setData(engine.getRawData());
            int[] temp = (int[]) cluster_idxs.get(0);
            bicat.biclustering.Bicluster selection = new bicat.biclustering.Bicluster(
                    0, temp.clone(), chipsTemplate);
            engine.setCurrentBiclusterSelection(owner.getPp().setTranslationTable(selection));

            owner.getMatrixScrollPane().repaint();
            owner.readjustPictureSize();
            owner.getPp().repaint();
        }
    }

    private LinkedList quasi_bi_clusters;

    // =-=-
    public LinkedList getClusters() {
        return quasi_bi_clusters;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--------

    public Double min(Double v1, Double v2) {
        if (v1.doubleValue() < v2.doubleValue())
            return v1;
        else
            return v2;
    }

    public Double max(Double v1, Double v2) {
        if (v1.doubleValue() < v2.doubleValue())
            return v2;
        else
            return v1;
    }

    public Double avg(Double v1, Double v2, int s1, int s2) {
        return new Double((v1.doubleValue() * (double) s1 + v2.doubleValue()
                * (double) s2)
                / (double) (s1 + s2));
    }

    public int[] findMinDistancePair(Vector dists) {

        double minimum = Double.MAX_VALUE;
        int[] pair = new int[2];
        pair[0] = 0;
        pair[1] = 1;
        for (int i = 0; i < dists.size(); i++) {
            Vector inner_dists = (Vector) dists.get(i);
            for (int j = i + 1; j < inner_dists.size(); j++) {
                double value = ((Double) inner_dists.get(j)).doubleValue();
                if (value < minimum) {
                    pair[0] = i;
                    pair[1] = j;
                    minimum = value;
                }
            }
        }
        this.setCurr_distance(minimum);
        return pair;
    }

    public int[] appendArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        for (int i = 0; i < arr1.length; i++)
            result[i] = arr1[i];
        for (int i = 0; i < arr2.length; i++)
            result[arr1.length + i] = arr2[i];
        return result;
    }

    public int findClusterIdx(int[] cl, Vector clusters) {
        // int idx = -1;
        Vector cluster = new Vector();
        for (int i = 0; i < cl.length; i++)
            cluster.add(new Integer(cl[i]));
        Collections.sort(cluster);

        for (int i = 0; i < clusters.size(); i++) {
            Vector existing = (Vector) clusters.get(i);
            Collections.sort(existing);
            if (existing.equals(cluster))
                return (i + 1);
        }
        return -1;
    }

    // ===========================================================================
    // ===========================================================================
    // ===========================================================================

    /**
     * First dim (data.length) is the number of items to cluster. Dementspreched
     * is the second dim (data[0].length) number of attributes.
     */
    public void runAgglomerative_ORIG(float[][] data) {

        int nr_items = data.length;
        if (DEBUG)
            System.out.println("nr_items = " + nr_items);

        // INIT: compute the distance matrix, and create the singletons:
        double[][] distance_matrix = new double[nr_items][nr_items];
        for (int i = 0; i < nr_items; i++) {
            distance_matrix[i][i] = 0.0;
            for (int j = i + 1; j < nr_items; j++) {
                if (distance_metric == MethodConstants.EUCLIDEAN_DISTANCE) {
                    distance_matrix[i][j] = Util.computeEuclideanDistance(
                            data[i], data[j]);
                    distance_matrix[j][i] = distance_matrix[i][j];
                }
            }
        }
        if (DEBUG)
            System.out.println("(2) nr_items = " + nr_items);

        Vector cluster_idxs = new Vector();
        for (int i = 0; i < nr_items; i++) {
            int[] cl = new int[1];
            cl[0] = i;
            cluster_idxs.add(cl);
        }

        Vector cluster_dists = new Vector();
        for (int i = 0; i < nr_items; i++) {
            Vector distance_vector = new Vector();
            for (int j = 0; j < nr_items; j++)
                distance_vector.add(new Double(distance_matrix[i][j]));
            cluster_dists.add(distance_vector);
        }

        while (cluster_idxs.size() > 1) {

            // 2. merge (into) clusters
            int[] idxs = findMinDistancePair(cluster_dists); // 2 (i,j)

            int[] cl_1 = (int[]) cluster_idxs.get(idxs[0]);
            int[] cl_2 = (int[]) cluster_idxs.get(idxs[1]);

            if (DEBUG)
                System.out.println("Merging clusters " + idxs[0] + ", "
                        + idxs[1]);
            if (idxs[0] > idxs[1])
                System.out.println("ACHTUNG: 0 veci od 1!!!");

            cluster_idxs.remove(idxs[1]);
            cluster_idxs.set(idxs[0], appendArrays(cl_1, cl_2));

            for (int i = 0; i < cluster_idxs.size(); i++)
                ((Vector) cluster_dists.get(i)).remove(idxs[1]);

            // 3. recompute the distances
            for (int j = 0; j < cluster_idxs.size(); j++) {
                if (linkage_method == MethodConstants.SINGLE_LINKAGE)
                    ((Vector) cluster_dists.get(idxs[0])).set(j, min(
                            (Double) ((Vector) cluster_dists.get(idxs[0]))
                                    .get(j), (Double) ((Vector) cluster_dists
                                    .get(idxs[1])).get(j)));
                else if (linkage_method == MethodConstants.COMPLETE_LINKAGE)
                    ((Vector) cluster_dists.get(idxs[0])).set(j, max(
                            (Double) ((Vector) cluster_dists.get(idxs[0]))
                                    .get(j), (Double) ((Vector) cluster_dists
                                    .get(idxs[1])).get(j)));
                /*
				 * else if(linkage_method == AVERAGE_LINKAGE) {} else { /* not
				 * foreseen! ../ }
				 */
            }

            cluster_dists.remove(idxs[1]);

            for (int i = 0; i < cluster_idxs.size(); i++) {
                // if(i != idxs[0])
                // ((Vector)cluster_dists.get(i)).remove(idxs[1]);
                if (i != idxs[0])
                    ((Vector) cluster_dists.get(i)).set(idxs[0],
                            ((Vector) cluster_dists.get(idxs[0])).get(i));
            }

            // repeat 2 and 3 until all items are in a single cluster
        }
    }

}
