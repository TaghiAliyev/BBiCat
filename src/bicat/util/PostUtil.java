/*
 *                                BBiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                DOI for citing the release : 10.5281/zenodo.33117
 *
 *                                Copyright (c) 2015 Taghi Aliyev, Marco Manca, Alberto Di Meglio
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

package bicat.util;

import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Bicluster;
import bicat.gui.BicatGui;

import javax.swing.*;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

public class PostUtil {

    // ===========================================================================
    public PostUtil(UtilFunctionalities utilEngine) {
        this.utilEngine = utilEngine;
        this.owner = null;
    }

    private UtilFunctionalities utilEngine;

    private BicatGui owner;

    public PostUtil(BicatGui owner) {
        System.out.println("Post Util constructor");
        this.owner = owner;
        this.utilEngine = owner.getUtilEngine();
    }

    // ===========================================================================
    // FILTER....

    public static LinkedList filterBySize(int minG, int maxG, int minC,
                                   int maxC, int limitG, int limitC, LinkedList list_in) {

        if (minG == 0 && minC == 0 && maxG == limitG && maxC == limitC)
            return list_in;

        LinkedList list_out = new LinkedList();

        for (int i = 0; i < list_in.size(); i++) {

            Bicluster bc = (Bicluster) list_in.get(i);

            if (bc.getGenesSize() >= minG && bc.getChipsSize() >= minC) {
                if (maxG == limitG && maxC == limitC)
                    list_out.add(bc);
                else {
                    if (bc.getGenesSize() <= maxG && bc.getChipsSize() <= maxC)
                        list_out.add(bc);
                }
            }
        }
        return list_out;
    }

    // ===========================================================================
    public static LinkedList filterByOverlap(int nrBCs, int overlap,
                                      LinkedList list_in) {
        return filterByNoOverlap(nrBCs, overlap, list_in);
    }

    // ===========================================================================
    public LinkedList filterBySize(int b, int g, int c, LinkedList list_in) {

        LinkedList list = new LinkedList(list_in);
        int s_count = list.size();

        for (int i = 0; i < list.size(); i++) {

            if (c == 0 && g == 0) {
                if (((Bicluster) list.get(i)).getSize() < b) {
                    list.remove(i);
                    i--;
                }
            } else {

                if (b > 0) { // ALWAYS consider the BC size, too.

                    if (g == 0 && c == 0) {
                        if (((Bicluster) list.get(i)).getSize() < b) {
                            list.remove(i);
                            i--;
                        }
                        // ;
                    } else if (g > 0 && c > 0) {
                        if (((Bicluster) list.get(i)).getChipsSize() < c
                                || ((Bicluster) list.get(i)).getGenesSize() < g
                                || ((Bicluster) list.get(i)).getSize() < b) {
                            list.remove(i);
                            i--;
                        }
                    } else if (g == 0) {
                        if (((Bicluster) list.get(i)).getChipsSize() < c
                                || ((Bicluster) list.get(i)).getSize() < b) {
                            list.remove(i);
                            i--;
                        }
                    } else if (c == 0) {
                        if (((Bicluster) list.get(i)).getGenesSize() < g
                                || ((Bicluster) list.get(i)).getSize() < b) {
                            list.remove(i);
                            i--;
                        }
                    }
                } else { // ONLY consider the (g,c)

                    if (g == 0 && c == 0) {
                    } else if (g > 0 && c > 0) {
                        if (((Bicluster) list.get(i)).getChipsSize() < c
                                || ((Bicluster) list.get(i)).getGenesSize() < g) {
                            list.remove(i);
                            i--;
                        }
                    } else if (g == 0) {
                        if (((Bicluster) list.get(i)).getChipsSize() < c) {
                            list.remove(i);
                            i--;
                        }
                    } else if (c == 0) {
                        if (((Bicluster) list.get(i)).getGenesSize() < g) {
                            list.remove(i);
                            i--;
                        }
                    }

                }
            }
        }

        System.out.println("Filter By Size DONE. [" + list.size() + "/"
                + s_count + "]");
        return list;
    }

    // ===========================================================================
    private static int computeIntersectionArea(Bicluster b1, Bicluster b2) {

        int[] gs_1 = b1.getGenes();
        int[] cs_1 = b1.getChips();

        int[] gs_2 = b2.getGenes();
        int[] cs_2 = b2.getChips();

        int g_cnt = 0;
        for (int i = 0; i < gs_1.length; i++)
            for (int j = 0; j < gs_2.length; j++)
                if (gs_1[i] == gs_2[j]) {
                    g_cnt++;
                    break;
                }

        int c_cnt = 0;
        for (int i = 0; i < cs_1.length; i++)
            for (int j = 0; j < cs_2.length; j++)
                if (cs_1[i] == cs_2[j]) {
                    c_cnt++;
                    break;
                }

        return c_cnt * g_cnt;
    }

    // ===========================================================================
    private static LinkedList filterByNoOverlap(int K, int thr, LinkedList list) {

        LinkedList result_list = new LinkedList();

        // ASSUME, the list is already sorted by size...
        result_list.add(list.get(0)); // the biggest BC belongs here

        float threshold = (float) ((float) thr / 100.0);

        for (int i = 1; i < list.size(); i++) {

            // System.out.println("\n\n*** New for cycle...");

            Bicluster next = (Bicluster) list.get(i);
            if (next.getGenesSize() == 1 || next.getChipsSize() == 1)
                continue;

            float max_intersection_area = -1;

            for (int j = 0; j < result_list.size(); j++) {
                Bicluster ref = (Bicluster) result_list.get(j);
                int intersect_area = computeIntersectionArea(ref, next);

				/*
                 * System.out.println("Debug.a: intArea =
				 * "+intersect_area+"/"+ref.getSize()+ "; % =
				 * "+100.0*(float)((float)intersect_area/(float)ref.getSize())+ ";
				 * threshold == "+threshold);
				 */

                float proportion = (float) intersect_area
                        / (float) ref.getSize();

                // if(proportion <= threshold)
                if (proportion > max_intersection_area) // && proportion <=
                    // threshold)
                    max_intersection_area = proportion;
            }

            if (max_intersection_area <= threshold)
                result_list.add(next);

            if (result_list.size() == K)
                break;
        }

        System.out.println("Filter By No Overlap DONE. [" + result_list.size()
                + "/" + K + "]");
        return result_list;
    }

    // ===========================================================================
    public LinkedList filterByNewArea(int b, int t, LinkedList list,
                                      int N, int M) {

        LinkedList result_list = new LinkedList();
        int[][] bm = new int[N][M];

        for (int i = 0; i < list.size(); i++) {
            Bicluster next = (Bicluster) list.get(i);
            if (next.getGenesSize() == 1 || next.getChipsSize() == 1)
                continue;

            int new_area = computeMatrixCoverage(bm, next);
            if (new_area / next.getSize() >= t / 100) {
                bm = updateMatrix(bm, next);
                result_list.add(next);
            }

            if (result_list.size() == b)
                break;
        }

        System.out.println("Filter By New Area DONE. [" + result_list.size()
                + "/" + b + "]");
        return result_list;
    }

    // ===========================================================================
    // LinkedList filter = PostprocessorUtil.filterByHammingDistance(maxErr,
    // bcList, total_genes, total_chips);
    public LinkedList filterByHammingDistance(int err, LinkedList list,
                                              int N, int M, int[][] binaryMatrix) {

        LinkedList result_list = new LinkedList();
        for (int i = 0; i < list.size(); i++) {
            Bicluster next_bc = (Bicluster) list.get(i);
            Bicluster next_bc_ext = getHammingDistanceExtension(next_bc, N, M,
                    err, binaryMatrix, false);
            result_list.add(next_bc_ext);
        }

        result_list = clean_list_of_bcs(result_list);
        return result_list;
    }

    // ===========================================================================
    private LinkedList clean_list_of_bcs(LinkedList list) {

        System.out.print("HD-filtered list = " + list.size() + ".... ");

        for (int i = 0; i < list.size(); i++) {
            Bicluster bc = (Bicluster) list.get(i);
            for (int j = 0; j < list.size(); j++) {
                Bicluster other = (Bicluster) list.get(j);
                if (bc.compareTo(other) == 0)
                    list.remove(j);
            }
        }

        System.out.println(list.size());
        Collections.sort(list);
        return list;
    }

    // ===========================================================================
    private int computeMatrixCoverage(int[][] bm, Bicluster next) {
        int na = 0;
        for (int i = 0; i < next.getGenes().length; i++)
            for (int j = 0; j < next.getChips().length; j++)
                if (bm[i][j] == 0)
                    na++;
        return na;
    }

    // ===========================================================================
    private int[][] updateMatrix(int[][] bm, Bicluster next) {
        for (int i = 0; i < next.getGenes().length; i++)
            for (int j = 0; j < next.getChips().length; j++)
                if (bm[i][j] == 0)
                    bm[i][j] = 1;
        return bm;
    }

    // ===========================================================================
    // SEARCH....

    // ===========================================================================
    // ....
    public LinkedList search(int minSize, int minGeneCount,
                             int minChipCount, String geneString, String chipString,
                             boolean andSearch, LinkedList list) {
        // Msg:
        System.out.println("\nSearch with parameters called: ");
        // System.out.println("min BC size: "+minSize+", min Geneset Size:
        // "+minGeneCount+", min Chipset Size: "+minChipCount);
        System.out.println("Genes contained in a BC: " + geneString); // space-separated
        // string
        System.out.println("Chips contained in a BC: " + chipString);
        System.out.print("Search by Genes/Chips in ");
        if (andSearch)
            System.out.print("AND");
        else
            System.out.print("OR");
        System.out.print(" mode. ... ");

        // .... IST DER INPUT FORMAT CORRECT??? (do Check!!!)
        String[] geneNames = geneString.split(" ", -1);
        String[] chipNames = chipString.split(" ", -1);

        Vector geneIndices = new Vector(); // vectors hold list of the gene and
        // chip indices that user wants to
        // look for
        Vector chipIndices = new Vector();

        for (int i = 0; i < geneNames.length; i++) { // get indices of the
            // chips that are being
            // search for
            if (!geneNames[i].equals("")) {
                int index = utilEngine.getCurrentDataset().getGeneIdx(geneNames[i]); // pre.getGeneIndex(geneNames[i]);
                // System.out.println("D(*): gene to search = "+geneNames[i]+",
                // "+index);
                // if(-1 != index)
                geneIndices.add(new Integer(index)); // only add gene to list
                // of indiced if it was
                // a valid name
            }
        }
        for (int i = 0; i < chipNames.length; i++) {
            if (!chipNames[i].equals("")) {
                int index = utilEngine.getCurrentDataset()
                        .getWorkChipIdx(chipNames[i]); // BicatGui.pre.getChipIndex(chipNames[i]);
                // if(-1 != index)
                chipIndices.add(new Integer(index));
            }
        }

        LinkedList bcs_search_results = new LinkedList(); // linked list that
        // will hold search
        // results

        // //////////////////////////////////////////////////////////////////////////

        if (andSearch) { // AND mode

            // PROOF-CHECK The list of genes / chips (all genes NEED TO BE
            // DEFINED!)

            // System.out.println("D: IT IS and SEARCH");

            for (int i = 0; i < geneIndices.size(); i++)
                if (((Integer) geneIndices.get(i)).intValue() == -1) {
                    JOptionPane.showMessageDialog(null, "Unknown gene\nSearch results are empty");
                    System.out
                            .println("Unknown gene detected. Quitting the Search.");
                    return bcs_search_results;
                }
            for (int i = 0; i < chipIndices.size(); i++)
                if (((Integer) chipIndices.get(i)).intValue() == -1) {
                    JOptionPane.showMessageDialog(null, "Unknown chip\nSearch results are empty");

                    System.out
                            .println("Unknown chip detected. Quitting the Search.");
                    return bcs_search_results;
                }

            // // if all the IDs valid are, continue with the search.

            // System.out.println("AND mode searching!.... given (g,c) =
            // "+geneIndices.size()+", "+chipIndices.size()
            // +"; BC List Size = "+list.size());

            // alle gene UND alle chips mussen in der BC drin sein

            for (int i = 0; i < list.size(); i++) {

                Bicluster next = (Bicluster) list.get(i);

                int cnt = 0; // bis jetzt, 0 match gefunden

                // System.out.println("\nI = "+i);

                for (int p = 0; p < geneIndices.size(); p++)
                    for (int q = 0; q < next.getGenesSize(); q++) {
                        // System.out.print(", "+next.genes[q]);
                        if (next.getGenes()[q] == ((Integer) geneIndices.get(p))
                                .intValue()) {
                            cnt++;
                            break;
                        }
                    }
                if (cnt != geneIndices.size())
                    continue;

                cnt = 0;
                for (int p = 0; p < chipIndices.size(); p++)
                    for (int q = 0; q < next.getChipsSize(); q++)
                        if (next.getChips()[q] == ((Integer) chipIndices.get(p))
                                .intValue()) {
                            cnt++;
                            break;
                        }
                if (cnt != chipIndices.size())
                    continue;

                // System.out.println("Add a BC to the search result.");
                bcs_search_results.add(next);
            }

        }

        // //////////////////////////////////////////////////////////////////////////

        else { // OR mode

            // REMOVE FROM geneIndices, and chipIndices the -1's :
            for (int i = 0; i < geneIndices.size(); i++)
                if (((Integer) geneIndices.get(i)).intValue() == -1)
                    geneIndices.remove(i);
            for (int i = 0; i < chipIndices.size(); i++)
                if (((Integer) chipIndices.get(i)).intValue() == -1)
                    chipIndices.remove(i);

            // Now, consider all the Gs/Cs that have valid IDs...

            for (int i = 0; i < list.size(); i++) {

                Bicluster next = (Bicluster) list.get(i);

                // if (next.getSize() < minSize)
                // continue;
                // else if (next.getGenesSize() < minGeneCount ||
                // next.getChipsSize() < minChipCount)
                // continue;
                // else { // SIZE constraints are met

                int cnt = 0;

                for (int p = 0; p < geneIndices.size(); p++) {
                    for (int q = 0; q < next.getGenesSize(); q++) {
                        if (next.getGenes()[q] == ((Integer) geneIndices.get(p))
                                .intValue()) {
                            cnt++; // bcs_search_results.add(next);
                            break;
                        }
                    }
                    if (cnt > 0) {
                        bcs_search_results.add(next);
                        break;
                    }
                }

                if (cnt == 0) { // no genes matched, but maybe chips then?

                    for (int p = 0; p < chipIndices.size(); p++) {
                        for (int q = 0; q < next.getChipsSize(); q++) {
                            if (next.getChips()[q] == ((Integer) chipIndices.get(p))
                                    .intValue()) {
                                cnt++; // bcs_search_results.add(next);
                                break;
                            }
                        }
                        if (cnt > 0) {
                            bcs_search_results.add(next);
                            break;
                        }
                    }
                }

            } // end for (over all BCs in the original list)

        }

        System.out.println("Search resulted in " + bcs_search_results.size()
                + "/" + list.size() + " biclusters.\n");
        // if(bcs_search_results.size()==0){System.out.println("NO SEARCH
        // RESULTS");}
        return bcs_search_results;
    }

    // ===========================================================================
    // SORT....

    // ===========================================================================
    // other....

    // ===========================================================================
    private static int hammingDistance(BitSet bs1, BitSet bs2) {

        BitSet ref = (BitSet) bs1.clone(); // reference mask, ... do AND, to
        // see how many of the remainings
        // are '1'
        ref.and(bs2);

        // System.out.println("HD = "+(bs1.cardinality()- ref.cardinality()));
        return (bs1.cardinality() - ref.cardinality());
        // return ref.cardinality();
    }

    // ===========================================================================
    public static Bicluster getHammingDistanceExtension(Bicluster bc,
                                                 int gene_nummer, int chip_nummer, int err, int[][] data, // ,
                                                 // boolean
                                                 // extended,
                                                 boolean gene_d_first) {

        // LATER: should take care of the direction!!!!

        // NB: koristi heuristicu (mozda postoji bolji BC, to add!)
        BitSet bc_chip_side = new BitSet(chip_nummer); // reference
        // "horizontal" star
        BitSet bc_gene_side = new BitSet(gene_nummer); // reference "vertical"
        // star

        for (int i = 0; i < bc.getGenes().length; i++)
            bc_gene_side.set(bc.getGenes()[i]);
        for (int i = 0; i < bc.getChips().length; i++)
            bc_chip_side.set(bc.getChips()[i]);

		/*
		 * System.out.println("\n\nref-c: "+bc_gene_side.toString());
		 * System.out.println("\n\nref-g: "+bc_chip_side.toString());
		 */
        double constant = 4.0 / 3.0; // 0.75; // 2.0;

        double err_perc = ((double) err / (double) 100);

        // ... get the best chips,...
        LinkedList best_chips = new LinkedList(); // best "vertical"
        // candidates (they agree
        // well with the
        // gene-marginal-set)

        for (int i = 0; i < chip_nummer; i++) {
            if (!bc_chip_side.get(i)) { // that star is not already in a
                // bc
                // copy into a BitSet (to work with) the chip (vert.) star:
                BitSet current_chip = new BitSet(gene_nummer);
                for (int j = 0; j < gene_nummer; j++)
                    if (data[j][i] == 1)
                        current_chip.set(j);
                int hd = hammingDistance(bc_gene_side, current_chip);
                // if ((double)hd <=
                // (double)((double)bc_gene_side.cardinality()/constant))
                best_chips.add(new Chip(i, current_chip, hd));
            }
        }

        // ... get the best genes,...
        LinkedList best_genes = new LinkedList();

        for (int i = 0; i < gene_nummer; i++) {
            if (!bc_gene_side.get(i)) {
                BitSet current_gene = new BitSet(chip_nummer);
                for (int j = 0; j < chip_nummer; j++)
                    if (data[i][j] == 1)
                        current_gene.set(j);
                int hd = hammingDistance(bc_chip_side, current_gene);
                // if((double)hd <=
                // (double)((double)bc_chip_side.cardinality()/constant))
                best_genes.add(new Gene(i, current_gene, hd));
            }
        }

        Collections.sort(best_chips);
        Collections.sort(best_genes);
		/*
		 * System.out.println("CHECK CHIP ORDER - BY HD"); for(int k = 0; k<best_chips.size();
		 * k++) System.out.print(" "+((Chip)best_chips.get(k)).hd_err);
		 * System.out.println("\nCHECK GENE ORDER - BY HD"); for(int k = 0; k<best_genes.size();
		 * k++) System.out.print(" "+((Gene)best_genes.get(k)).hd_err);
		 * System.out.println(); //System.exit(0);
		 */
        // ... now, greedily extend this BC
        Bicluster ext_bc = new Bicluster(); // (bc.id, bc_gene_side,
        // gene_nummer, bc_chip_side,
        // chip_nummer); // set error = 0.0;

        // ALTERNATIVA

        while (best_chips.size() > 0 || best_genes.size() > 0) {

            int min_hd = 999999999;
            if (best_chips.size() > 0)
                min_hd = ((Chip) best_chips.get(0)).hd_err;

            if (best_genes.size() > 0) {

                if (((Gene) best_genes.get(0)).hd_err < min_hd) {

                    if (best_genes.size() > 0) {
                        Gene curr_g = (Gene) best_genes.get(0);
                        int hd = hammingDistance(bc_chip_side, curr_g.gene);
                        double up = ((double) (hd + ext_bc.getError()) / (double) ((bc_gene_side
                                .cardinality() * (1 + bc_chip_side
                                .cardinality()))));
                        if (up < err_perc) {
                            ext_bc.updateGene(curr_g.idx);
                            ext_bc.addToError(hd); // hammingDistance(gene_mask,
                            // curr_g.gene));
                            ext_bc.setSize(ext_bc.getSize() + bc_chip_side.cardinality());
                            best_genes.remove(0);
                        } else
                            break;
                        up = (((double) ext_bc.getError()) / ((double) (bc_gene_side
                                .cardinality() * bc_chip_side.cardinality())));
                        if (up > err_perc) {
                            // System.out.println("returining; up = "+up);
                            return ext_bc;
                        }
                    }
                } else {

                    if (best_chips.size() > 0) {
                        Chip curr_c = (Chip) best_chips.get(0);
                        // System.out.println("ADDING NEW CHIP:" + curr_c.idx
                        // +", hd = "+curr_c.hd_err);
                        int hd = hammingDistance(bc_gene_side, curr_c.chip);
                        double up = ((double) (hd + ext_bc.getError()) / (double) ((bc_gene_side
                                .cardinality() * (1 + bc_chip_side
                                .cardinality()))));
                        if (up < err_perc) {
                            ext_bc.updateChip(curr_c.idx);
                            ext_bc.addToError(hd);
                            ext_bc.setSize(ext_bc.getSize() + bc_gene_side.cardinality());
                            bc_chip_side.set(curr_c.idx);
                            best_chips.remove(0);
                        } else
                            break; // return ext_bc;
                        up = ((double) ext_bc.getError() / (double) (bc_gene_side
                                .cardinality() * bc_chip_side.cardinality()));
                        if (up > err_perc) {
                            // System.out.println("returning, up = "+up+";
                            // err_perc = "+err_perc);
                            return ext_bc;
                        }
                    }
                }
            } else {
                // work with best_chip only (further)
                if (best_chips.size() > 0) {
                    Chip curr_c = (Chip) best_chips.get(0);
                    // System.out.println("ADDING NEW CHIP:" + curr_c.idx +", hd
                    // = "+curr_c.hd_err);
                    int hd = hammingDistance(bc_gene_side, curr_c.chip);
                    double up = ((double) (hd + ext_bc.getError()) / (double) ((bc_gene_side
                            .cardinality() * (1 + bc_chip_side.cardinality()))));
                    if (up < err_perc) {
                        ext_bc.updateChip(curr_c.idx);
                        ext_bc.addToError(hd);
                        ext_bc.setSize(bc_gene_side.cardinality() + ext_bc.getSize());
                        bc_chip_side.set(curr_c.idx);
                        best_chips.remove(0);
                    } else
                        break; // return ext_bc;
                    up = ((double) ext_bc.getError() / (double) (bc_gene_side
                            .cardinality() * bc_chip_side.cardinality()));
                    if (up > err_perc) {
                        // System.out.println("returning, up = "+up+"; err_perc
                        // = "+err_perc);
                        return ext_bc;
                    }
                }
            }

        }

		/*
		 * if(gene_d_first == false) {
		 * 
		 * while(best_chips.size() > 0) { Chip curr_c = (Chip)best_chips.get(0);
		 * //System.out.println("ADDING NEW CHIP:" + curr_c.idx +", hd =
		 * "+curr_c.hd_err); int hd = hammingDistance(bc_gene_side,
		 * curr_c.chip); double up = (double)((double)(hd+ext_bc.error)
		 * /(double)((bc_gene_side.cardinality()*(1+bc_chip_side.cardinality()))));
		 * if(up < err_perc) { ext_bc.updateChip(curr_c.idx);
		 * ext_bc.addToError(hd); ext_bc.size += bc_gene_side.cardinality();
		 * bc_chip_side.set(curr_c.idx); best_chips.remove(0);
		 * 
		 * //System.out.println("ERROR: (err = "+err+")error nakon novog chipa:
		 * "+ext_bc.error+"/"+(bc_gene_side.cardinality()*bc_chip_side.cardinality())); }
		 * else break; //return ext_bc;
		 * 
		 * up =
		 * (double)((double)ext_bc.error/(double)(bc_gene_side.cardinality()*bc_chip_side.cardinality()));
		 * if(up > err_perc) { //System.out.println("returning, up = "+up+";
		 * err_perc = "+err_perc); return ext_bc; } }
		 *  // .... while(best_genes.size() > 0) { Gene curr_g =
		 * (Gene)best_genes.get(0); int hd = hammingDistance(bc_chip_side,
		 * curr_g.gene); double up = (double)((double)(hd+ext_bc.error)
		 * /(double)((bc_gene_side.cardinality()*(1+bc_chip_side.cardinality()))));
		 * if(up < err_perc) { ext_bc.updateGene(curr_g.idx);
		 * ext_bc.addToError(hd); //hammingDistance(gene_mask, curr_g.gene));
		 * ext_bc.size += bc_chip_side.cardinality();
		 * bc_gene_side.set(curr_g.idx); best_genes.remove(0);
		 * 
		 * //System.out.println("ERROR: error nakon novog genea:
		 * "+ext_bc.error+"/"+(bc_gene_side.cardinality()*bc_chip_side.cardinality())); }
		 * else break;
		 * 
		 * up = (double)(((double)ext_bc.error)/
		 * ((double)(bc_gene_side.cardinality()*bc_chip_side.cardinality())));
		 * if(up > err_perc) { // System.out.println("returning; up = "+up);
		 * return ext_bc; } }
		 *  } //
		 * .................................................................. //
		 * else {
		 * 
		 * while(best_genes.size() > 0) { Gene curr_g = (Gene)best_genes.get(0);
		 * int hd = hammingDistance(bc_chip_side, curr_g.gene); double up =
		 * (double)((double)(hd+ext_bc.error)
		 * /(double)((bc_gene_side.cardinality()*(1+bc_chip_side.cardinality()))));
		 * if(up < err_perc) { ext_bc.updateGene(curr_g.idx);
		 * ext_bc.addToError(hd); //hammingDistance(gene_mask, curr_g.gene));
		 * ext_bc.size += bc_chip_side.cardinality(); best_genes.remove(0);
		 * 
		 * //System.out.println("ERROR: error nakon novog genea:
		 * "+ext_bc.error+"/"+(bc_gene_side.cardinality()*bc_chip_side.cardinality())); }
		 * else break;
		 * 
		 * up = (double)(((double)ext_bc.error)/
		 * ((double)(bc_gene_side.cardinality()*bc_chip_side.cardinality())));
		 * if(up > err_perc) { // System.out.println("returining; up = "+up);
		 * return ext_bc; } }
		 *  // .... while(best_chips.size() > 0) { Chip curr_c =
		 * (Chip)best_chips.get(0); int hd = hammingDistance(bc_gene_side,
		 * curr_c.chip); double up = (double)((double)(hd+ext_bc.error)
		 * /(double)((bc_gene_side.cardinality()*(1+bc_chip_side.cardinality()))));
		 * if(up < err_perc) { ext_bc.updateChip(curr_c.idx);
		 * ext_bc.addToError(hd); ext_bc.size += bc_gene_side.cardinality();
		 * bc_chip_side.set(curr_c.idx); best_chips.remove(0);
		 *  // System.out.println("ERROR: (err = "+err+")error nakon novog
		 * chipa:
		 * "+ext_bc.error+"/"+(bc_gene_side.cardinality()*bc_chip_side.cardinality())); }
		 * else break;
		 * 
		 * up =
		 * (double)((double)ext_bc.error/(double)(bc_gene_side.cardinality()*bc_chip_side.cardinality()));
		 * if(up > err_perc) { // System.out.println("returning, up = "+up+";
		 * err_perc = "+err_perc); return ext_bc; } }
		 *  }
		 */
        return ext_bc;
    }

}

// =============================================================================
class Mask implements Comparable {

    int hd_err;

    public Mask() {
    }

    public int compareTo(Object o) {
        Mask m = (Mask) o;
        if (hd_err < m.hd_err)
            return -1;
        else if (hd_err > m.hd_err)
            return 1;
        else
            return 0;
    }
}

// =============================================================================
class Gene extends Mask implements Comparable {

    int idx;

    BitSet gene;

    int hd_err;

    public Gene(int i, BitSet gn, int err) {
        idx = i;
        gene = gn;
        hd_err = err;
    }

    public int compareTo(Object o) {
        Gene m = (Gene) o;
        if (hd_err < m.hd_err)
            return -1;
        else if (hd_err > m.hd_err)
            return 1;
        else
            return 0;
    }

}

// =============================================================================
class Chip extends Mask implements Comparable {

    int idx;

    BitSet chip;

    int hd_err;

    public Chip(int i, BitSet ch, int err) {
        idx = i;
        chip = ch;
        hd_err = err;
    }

    public int compareTo(Object o) {
        Chip m = (Chip) o;
        if (hd_err < m.hd_err)
            return -1;
        else if (hd_err > m.hd_err)
            return 1;
        else
            return 0;
    }

}
