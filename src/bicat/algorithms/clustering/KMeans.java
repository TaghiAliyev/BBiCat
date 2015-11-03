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
import lombok.Data;

import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

/**
 * Class that implements the KMeans clustering. Similar structure to HCL. For possible values of the variables,
 * please refer to the MethodConstants.java
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class KMeans {

    private int K = 0; // nr of clusters

    private int origK;

    private int distance_metric;

    private int nrMaxIterations;

    private int nrReplicates;

    private int nrItems;

    private int nrAttributes;

    private KMeansClusters solution;

    private int emptyAction;

    private int startMode;

    private float[][] start_matrix;

    // ===========================================================================
    public KMeans() {
    }

    public KMeans(int k, int dist, int maxiter, int replicas, int start,
                  float[][] start_mtx, int emptyActn) {

        K = k;
        origK = K;
        distance_metric = dist;
        nrMaxIterations = maxiter;
        nrReplicates = replicas;
        startMode = start;
        if (start == MethodConstants.START_MODE_MATRIX) {
            start_matrix = new float[k][start_mtx[0].length];
            for (int i = 0; i < k; i++)
                for (int j = 0; j < start_mtx[0].length; j++)
                    start_matrix[i][j] = start_mtx[i][j];
        }
        emptyAction = emptyActn;

        solution = null; // new KMeansClusters();
    }

    // ===========================================================================
    public LinkedList getClusters() throws EmptyClusterException {

        LinkedList KMclusters = new LinkedList();

        if (solution != null) {
            K = solution.getNumberClusters();
            System.out.println("K = " + K);

            Vector[] cs = new Vector[K]; // <= K of these ...
            for (int c = 0; c < K; c++)
                cs[c] = new Vector();

            // solution contains the solution ...
            int[] gCs = solution.getClusters();
            if (gCs == null)
                return null;

            for (int i = 0; i < nrItems; i++)
                cs[gCs[i]].add(new Integer(i));

            int[] chips = new int[nrAttributes];
            for (int i = 0; i < nrAttributes; i++)
                chips[i] = i;

            for (int c = 0; c < K; c++) {
                int[] genes = transformToIntArray(cs[c]);
                bicat.biclustering.Bicluster bc = new bicat.biclustering.Bicluster(
                        c, genes, chips);
                KMclusters.add(bc);
            }
            return KMclusters;
        } else {
            throw new EmptyClusterException("Empty cluster! Check your parameters, method returned null!");
        }
    }

    int[] transformToIntArray(Vector v) {
        int[] r = new int[v.size()];
        for (int i = 0; i < v.size(); i++)
            r[i] = ((Integer) v.get(i)).intValue();
        return r;
    }

    // ===========================================================================
    public void runKMeans(float[][] data) throws EmptyClusterException {

        nrItems = data.length;
        nrAttributes = data[0].length;

        solution = computeKClusters(data);

        if (startMode == MethodConstants.START_MODE_RANDOM
                || startMode == MethodConstants.START_MODE_UNIFORM_RANDOM
                || startMode == MethodConstants.START_MODE_CLUSTER) {

            KMeansClusters newSolution;

            for (int replica = 1; replica < nrReplicates; replica++) {
                K = origK;
                newSolution = computeKClusters(data);
                if (newSolution.betterThan(solution))
                    solution = newSolution;
            }
        }

        System.out.println("Finally: Solution Has Value = "
                + solution.getSumValue());

    }

    // ===========================================================================
    KMeansClusters computeKClusters(float[][] data)
            throws EmptyClusterException {

        if (K > nrItems
                && (emptyAction == MethodConstants.EMPTY_ACTION_ERROR || emptyAction == MethodConstants.EMPTY_ACTION_SINGLETON))
            throw new EmptyClusterException("Parameter Exception! Either too many clusters or empty action");

        KMeansClusters newSolution, currSolution; // , bestSolution;

        float[][] centroids = new float[K][nrAttributes];
        int[] geneAss = new int[nrItems];
        double[] distances = new double[K];
        double[] g2c_distances = new double[nrItems];
        double sumValue;

        // .... Initialize (allererste centroids):
        Random rndm = new Random();


//        System.out
//                .println("Initialize Centroids (gene 2 cluster assignments, ... ) ...");

        // initial centroids:
        switch (startMode) {

            case 0: // START_MODE_RANDOM

                int[] initials = new int[K]; // initial gene-centroids ..
                for (int c = 0; c < K; c++)
                    initials[c] = -1;

                if (MethodConstants.debug)
                    System.out.print("DEBUG: ");
                for (int c = 0; c < K; c++) {
                    int next = rndm.nextInt(nrItems);
                    if (notIn(next, initials, c)) {
                        initials[c] = next;
                        centroids[c] = copyArrayTo(data[next], centroids[c]);
//                        System.out.print(" " + next);
                    } else {
                        c--;
                    }
                }
//                System.out.println();
                break;

            case 1: // START_MODE_UNIFORM_RANDOM (range of data[x][y]...) : DEBUG
                // (Pearson's Correlation? )!!!

                float min = Float.MAX_VALUE;
                float max = Float.MIN_VALUE;

                for (int i = 0; i < nrItems; i++)
                    for (int j = 0; j < nrAttributes; j++) {
                        if (data[i][j] > max)
                            max = data[i][j];
                        if (data[i][j] < min)
                            min = data[i][j];
                    }

                for (int c = 0; c < K; c++) {
                    // System.out.println("\n\nNEW (K = "+K+", c = "+c+"): ");
                    for (int j = 0; j < nrAttributes; j++) {
                        double next = rndm.nextDouble(); // between 0 & 1 ?
                        centroids[c][j] = (float) (next * (double) (max - min) + (double) min);
                        // System.out.print(", cs[c][j] = "+centroids[c][j]);
                    }
                }
                break;

            case 2: // START_MODE_CLUSTER

                float[][] newData = new float[Math.round(data.length / 10)][data[0].length];
                for (int i = 0; i < Math.round(data.length / 10); i++) {
                    int next = rndm.nextInt(data.length);
                    newData[i] = copyArrayTo(data[next], newData[i]); // OHNE
                    // duplicate
                    // checks
                    // (eventually,
                    // correct
                    // this)
                }

                // nrReplicates = 1;
                // nrMaxIterations = 100;
                KMeans km = new KMeans(K, distance_metric, 100, 1,
                        MethodConstants.START_MODE_RANDOM, null, MethodConstants.EMPTY_ACTION_DROP);

                km.nrItems = newData.length;
                km.nrAttributes = newData[0].length;

                KMeansClusters preSolution = km.computeKClusters(newData);

                float[][] preCentroids = preSolution.getCentroids();
                for (int c = 0; c < K; c++)
                    for (int j = 0; j < data[0].length; j++)
                        centroids[c][j] = preCentroids[c][j];

                break;

            case 3: // START_MODE_MATRIX (debug later, as of now: not used /
                // end-implemented)

                for (int c = 0; c < K; c++)
                    for (int j = 0; j < nrAttributes; j++)
                        centroids[c][j] = start_matrix[c][j];

                break;

            default:
                break; // (not foreseen)
        }

        for (int c = 0; c < distances.length; c++)
            distances[c] = 0.0;
        // for(int g = 0; g < g2c_distances.length; g++) g2c_distances[g] = 0.0;
        for (int g = 0; g < nrItems; g++) {
            double minDist = Double.MAX_VALUE; // for this gene
            for (int c = 0; c < K; c++) {
                double distance = computeDistance(data[g], centroids[c]);
                if (distance < minDist) {
                    minDist = distance;
                    geneAss[g] = c;
                } // assign the g to the cluster, where the minDist erreicht
                // ist
            }
            distances[geneAss[g]] += minDist;
            g2c_distances[g] = minDist;
        }
        sumValue = 0.0;
        for (int c = 0; c < K; c++)
            sumValue += distances[c];

        // HERE ALSO, check that no cluster is empty!
        for (int c = 0; c < K; c++) {

            int cnt = 0;
            for (int g = 0; g < nrItems; g++)
                if (geneAss[g] == c)
                    cnt++;

//            System.out.print(", cnt = " + cnt);
            if (cnt == 0) {
                switch (emptyAction) { // if RANDOM GENES (START_MODE...) ->
                    // this should not happen!!!

                    case 0: // EMPTY_ACTION_ERROR
                        throw new EmptyClusterException("Empty Action");

                    case 1: // EMPTY_ACTION_DROP

//                        System.out.print("[DROP FALL!]");

                        // remove centroids[c]:
                        for (int p = c; p < K - 1; p++)
                            for (int j = 0; j < nrAttributes; j++)
                                centroids[p][j] = centroids[p + 1][j];
                        for (int j = 0; j < nrAttributes; j++)
                            centroids[K - 1][j] = -1; // invalid last cluster

                        // remove distances[c]:
                        for (int p = c; p < K - 1; p++)
                            distances[p] = distances[p + 1];

                        distances[K - 1] = 0.0;

                        for (int g = 0; g < nrItems; g++)
                            for (int p = c + 1; p < K; p++)
                                if (geneAss[g] == p)
                                    geneAss[g] = p - 1;

                        K--;
                        break;

                    case 2: // EMPTY_ACTION_SINGLETON

                        // find the gene that is furthest from any of the existing
                        // centroids,
                        // -> create a singleton cluster! (forces orig_K!!!)

                        int G = findMaxDistance(g2c_distances);
                        centroids[c] = copyArrayTo(data[G], centroids[c]);
                        sumValue -= g2c_distances[G];
                        g2c_distances[G] = 0.0;
                        geneAss[G] = c;

//                        System.out.println(" =====>>> FORCE K !!!");

                        break;

                    default:
                        break;
                }
            }
        }
        currSolution = new KMeansClusters(geneAss, centroids, sumValue, K);

        if (solution != null && currSolution.betterThan(solution))
            solution = currSolution; // (START_MODE_(pre)CLUSTER) ???
//        System.out.println();

//        System.out.println("First Solution has SumValue (init) = " + sumValue);

        // ....
        // .... Iterate
        for (int i = 0; i < nrMaxIterations; i++) {

            if (MethodConstants.debug)
                System.out.print("\n Iter. Nr. = " + i + ", ... ");

            // 1. re-compute the centroids
            for (int c = 0; c < K; c++) {

                float[] centroid = new float[nrAttributes];
                for (int cc = 0; cc < nrAttributes; cc++)
                    centroid[cc] = (float) 0.0;

                int cnt = 0;
                for (int g = 0; g < nrItems; g++)
                    if (geneAss[g] == c) {
                        centroid = sumUp(centroid, data[g]);
                        cnt++;
                    }

                for (int cc = 0; cc < nrAttributes; cc++)
                    centroids[c][cc] = centroid[cc] / cnt;
            }

            // 2. re-assign the genes/items to (new) centroids (re-init
            // distances):
            for (int c = 0; c < K; c++)
                distances[c] = 0.0;

            for (int g = 0; g < nrItems; g++) {
                double minDist = Double.MAX_VALUE;
                for (int c = 0; c < K; c++) {
                    double distance = computeDistance(data[g], centroids[c]);
                    if (distance < minDist) {
                        minDist = distance;
                        geneAss[g] = c;
                    }
                }
                distances[geneAss[g]] += minDist;
                // System.out.println("Gene "+g+", old = "+g2c_distances[g]+",
                // new = "+minDist);
                g2c_distances[g] = minDist;
            }

            // 3. what is the new sumValue (to be min'ed)?
            sumValue = 0.0;
            for (int c = 0; c < K; c++)
                sumValue += distances[c];

            // HERE ALSO, check that no (NEW) cluster is empty!
//            System.out.print(" .... [");
            for (int c = 0; c < K; c++) {

                int cnt = 0;
                for (int g = 0; g < nrItems; g++)
                    if (geneAss[g] == c)
                        cnt++;
//                System.out.print("" + cnt + ", ");

                if (cnt == 0) {
                    switch (emptyAction) {

                        case 0: // EMPTY_ACTION_ERROR
                            throw new EmptyClusterException("Empty action error! Check your implementation and choices please!");

                        case 1: // EMPTY_ACTION_DROP

                            // remove centroids[c]:
                            for (int p = c; p < K - 1; p++)
                                for (int j = 0; j < nrAttributes; j++)
                                    centroids[p][j] = centroids[p + 1][j];
                            for (int j = 0; j < nrAttributes; j++)
                                centroids[K - 1][j] = -1; // invalid last cluster

                            // remove distances[c]:
                            for (int p = c; p < K - 1; p++)
                                distances[p] = distances[p + 1];
                            distances[K - 1] = 0.0;

                            for (int g = 0; g < nrItems; g++)
                                for (int p = c + 1; p < K; p++)
                                    if (geneAss[g] == p)
                                        geneAss[g] = p - 1;

                            K--;
                            break;

                        case 2: // EMPTY_ACTION_SINGLETON

                            // find the gene that is furthest from any of the
                            // existing centroids,
                            // -> create a singleton cluster! (forces orig_K!!!)

                            int G = findMaxDistance(g2c_distances);
                            centroids[c] = copyArrayTo(data[G], centroids[c]);
                            sumValue -= g2c_distances[G];
                            g2c_distances[G] = 0.0;
                            geneAss[G] = c;

                            System.out.println(" =====>>> FORCE K (" + sumValue
                                    + ")!!!");

                            break;

                        default:
                            break;
                    }
                }
            }
//            System.out.println("], ... ");

//            System.out.print("New sumValue is " + sumValue);

            newSolution = new KMeansClusters(geneAss, centroids, sumValue, K);

            // repeat 1. and 2. bis converge (fix-pt), oder nrMaxIterations
            // reached

            // if(currSolution.equalsTo(newSolution)) break;
            // else

            if (distance_metric == MethodConstants.PEARSON_CORRELATION
                    && startMode == MethodConstants.START_MODE_UNIFORM_RANDOM && i == 0)
                currSolution = newSolution;
            else {
                if (newSolution.betterThan(currSolution))
                    currSolution = newSolution;
                else {
                    if (i > 0) {
//                        System.out.println("Curr " + currSolution.getSumValue()
//                                + ", New " + newSolution.getSumValue()
//                                + " (K = " + K + ")\n\n");
                        break;
                    }
                }
                // else go on (the current solution is still the best one)
            }
            // System.out.println("SumValue is "+currSolution.getSumValue());
        }

        // .... ???
        if (startMode == MethodConstants.START_MODE_CLUSTER && solution == null)
            solution = currSolution;

        return currSolution;
    }

    // ===========================================================================
    float[] sumUp(float[] base, float[] d) {
        for (int i = 0; i < base.length; i++)
            base[i] += d[i];
        return base;
    }

    int findMaxDistance(double[] dists) {
        double maxDist = Double.MIN_VALUE;
        int I = -1;
        for (int i = 0; i < dists.length; i++)
            if (dists[i] > maxDist) {
                maxDist = dists[i];
                I = i;
            }
        return I;
    }

    boolean notIn(int n, int[] arr, int k) {
        for (int i = 0; i < k; i++)
            if (arr[i] == n)
                return false; // true;
        return true;
    }

    float[] copyArrayTo(float[] d_src, float[] d_dest) {
        for (int i = 0; i < d_src.length; i++)
            d_dest[i] = d_src[i];
        return d_dest;
    }

    double computeDistance(float[] d1, float[] d2) {

        switch (distance_metric) {

            case MethodConstants.EUCLIDEAN_DISTANCE:
                return Util.computeEuclideanDistance(d1, d2);
            case MethodConstants.PEARSON_CORRELATION:
                return Util.computePearsonCorrelationDistance(d1, d2);
            case MethodConstants.MANHATTAN_DISTANCE:
                return Util.computeManhattanDistance(d1, d2);
            case MethodConstants.COSINE_DISTANCE_KMeans:
                return Util.computeCosineDistance(d1, d2);

            default:
                break; /* not foreseen */
        }
        return Float.NaN;
    }

}