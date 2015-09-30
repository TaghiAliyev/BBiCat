/*
 *                                BBiCat is a toolbox that combines different Biological Post-Analytic tools, Bi-Clustering and
 *                                clustering techniques in it, which can be applied on a given dataset. This software is the
 *                                modified version of the original BiCat Toolbox implemented at ETH Zurich by Simon
 *                                Barkow, Eckart Zitzler, Stefan Bleuler, Amela Prelic and Don Frick.
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

package bicat.util;

import OwnPersonal.computeMeanResidueScore;
import bicat.biclustering.Bicluster;
import bicat.biclustering.Dataset;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class AnalysisUtil {

	// ===========================================================================
	public AnalysisUtil() {
	}


	// ===========================================================================
	// GENE PAIR ANALYSIS ("gpa")....

	// ===========================================================================
	public static HashMap gpaByCoocurrence(int gThr, LinkedList list,
			Dataset dataset) throws Exception {
		computeMeanResidueScore.computeResidue(dataset);
		HashMap GPs_scores = new HashMap();
		// Need to add all the gene pairs possible. hmm, how? is it saved
		// somewhere?

		int sizeGene = dataset.getGeneCount();
		for (int i = 0; i < sizeGene; i++) {
			for (int j = 0; j < sizeGene; j++) {
				String skey = ((new Integer(Math.min(i, j))).toString()) + "\t"
						+ ((new Integer(Math.max(i, j))).toString());

				String skey2 = ((new Integer(Math.max(i, j))).toString())
						+ "\t" + ((new Integer(Math.min(i, j))).toString());

				Object[] values = new Object[2];
				values[0] = 0.0;
				GPs_scores.put(skey, values);
				GPs_scores.put(skey2, values);
			}
		}
		//
		double maxOfAll = 0;
		for (int i = 0; i < list.size(); i++) {
			int[] genes = ((Bicluster) (list.get(i))).getGenes();
			for (int p = 0; p < genes.length - 1; p++)
				for (int q = p + 1; q < genes.length; q++) {
					int min = genes[p];
					int max = genes[q];
					if (max < min) {
						int tmp = max;
						max = min;
						min = tmp;
					}
					String skey = ((new Integer(min)).toString()) + "\t"
							+ ((new Integer(max)).toString()); // ???
					String skey2 = ((new Integer(max)).toString()) + "\t"
							+ ((new Integer(min)).toString());
					// System.out.println(skey+"?");

					if (GPs_scores.containsKey(skey)) {
						Double score = Double.valueOf(((Object[]) GPs_scores
								.get(skey))[0].toString());
						double is = score.doubleValue();
						if (is > 0 && is < 1)
							System.out.println("Fuck this shit");
						is = is + 1.0;
						if (is > maxOfAll)
							maxOfAll = is;
						Object[] scores = new Object[2];
						scores[0] = is;
						GPs_scores.put(skey, scores);
						GPs_scores.put(skey2, scores);
						// GPs_scores.put(skey,new Integer(is));
					} else {
						Object[] scores = new Object[2];
						scores[0] = new Double(1.0);
						GPs_scores.put(skey, scores);
						GPs_scores.put(skey2, scores);
						// GPs_scores.put(skey,new Integer(1));
					}
				}
		}

		// Post-processing. Divide by max, so that you get [0,1] range

		for (int i = 0; i < sizeGene; i++) {
			for (int j = 0; j < sizeGene; j++) {
				String skey = ((new Integer(i)).toString()) + "\t"
						+ ((new Integer(j)).toString());

				// String skey2 = ((new Integer(Math.max(i, j))).toString())
				// + "\t" + ((new Integer(Math.min(i, j))).toString());

				Object[] values = new Object[2];
				Double score = Double
						.valueOf(((Object[]) GPs_scores.get(skey))[0]
								.toString());
				// System.out.println("score is : " + score);
				if (score > 0 && score < 1)
					System.out.println(i + " " + j + " " + score);
				values[0] = score / maxOfAll;
				GPs_scores.put(skey, values);
				// GPs_scores.put(skey2, values);
			}
		}

		// remove the distances smaller than the given 'distanceThreshold'
		// NOTE : Ignore sorting for now!
		// Set ks = GPs_scores.keySet();
		// Object[] ka = ks.toArray();
		// for (int i = 0; i < ka.length; i++) {
		// // int d =
		// // ((Integer)((Object[])(GPs_scores.get(ka[i]))[0]).intValue());
		// int d = ((Integer) ((Object[]) GPs_scores.get(ka[i]))[0])
		// .intValue();
		// if (d < gThr)
		// GPs_scores.remove(ka[i]);
		// }

		// Compute the graph distances (between all the pairs), in the remaining
		// hash structure!
		int[][] FW = computeAllPairsShortestPaths(GPs_scores);
		GPs_scores = updateGPAResults(GPs_scores, FW);

		// Let's print out the matrix
		// Why is this needed? So, useless to be fair
		System.out.println(maxOfAll);
		// sort the GP distance list!
		// ..

		return GPs_scores;
	}

	static HashMap name_2_id = null;

	static HashMap id_2_name = null;

	// ===========================================================================
	private static int[][] computeAllPairsShortestPaths(HashMap gps) {

		name_2_id = new HashMap();
		id_2_name = new HashMap();
		int idx = 0;

		Set ks = gps.keySet();
		Object[] ka = ks.toArray();
		for (int i = 0; i < ka.length; i++) {
			String[] sp = ((String) ka[i]).split("\t");
			if (!name_2_id.containsKey(sp[0])) {
				name_2_id.put(sp[0], new Integer(idx));
				id_2_name.put(new Integer(idx), sp[0]);
				idx++;
			}
			if (!name_2_id.containsKey(sp[1])) {
				name_2_id.put(sp[1], new Integer(idx));
				id_2_name.put(new Integer(idx), sp[1]);
				idx++;
			}
		}

		// initialize the adjacency matrix:
		int[][] adj_matrix = new int[name_2_id.size()][name_2_id.size()];
		for (int i = 0; i < ka.length; i++) {
			String[] sp = ((String) ka[i]).split("\t");
			int idx1 = ((Integer) name_2_id.get(sp[0])).intValue();
			int idx2 = ((Integer) name_2_id.get(sp[1])).intValue();
			adj_matrix[idx1][idx2] = 1;
			adj_matrix[idx2][idx1] = 1;
		}

		floyd.AllPairsShortestPath apsp = new floyd.AllPairsShortestPath(
				adj_matrix);
		// apsp.FloydTest();

		return apsp.getFWResult();
	}

	// ===========================================================================
	private static HashMap updateGPAResults(HashMap gps, int[][] distances) {

		Set ks = gps.keySet();
		Object[] ka = ks.toArray();
		for (int i = 0; i < ka.length; i++) {
			String[] sp = ((String) ka[i]).split("\t");

			int idx1 = ((Integer) name_2_id.get(sp[0])).intValue();
			int idx2 = ((Integer) name_2_id.get(sp[1])).intValue();

			gps.put(ka[i], new Object[] { ((Object[]) gps.get(ka[i]))[0],
					new Integer(distances[idx1][idx2]) });
		}

		return gps;
	}

	// ===========================================================================
	public static HashMap gpaByCommonChips(int gThr, LinkedList list) {
		// TO DO!

		HashMap GPs_scores = new HashMap();

		for (int i = 0; i < list.size(); i++) {
			int[] genes = ((Bicluster) (list.get(i))).getGenes();
			int cs = ((Bicluster) (list.get(i))).getChipsSize();
			for (int p = 0; p < genes.length - 1; p++)
				for (int q = p + 1; q < genes.length; q++) {
					int min = genes[p];
					int max = genes[q];
					if (max < min) {
						int tmp = max;
						max = min;
						min = tmp;
					}
					String skey = ((new Integer(min)).toString()) + "\t"
							+ ((new Integer(max)).toString()); // ???
					// System.out.println(skey+"?");

					if (GPs_scores.containsKey(skey)) {
						Integer score = (Integer) ((Object[]) GPs_scores
								.get(skey))[0];
						// Integer score = (Integer)GPs_scores.get(skey);
						int is = score.intValue();
						if (is < cs) {
							Object[] scores = new Object[2];
							scores[0] = new Integer(is);
							GPs_scores.put(skey, scores);
						}
						// GPs_scores.put(skey,new Integer(cs));
					} else {
						Object[] scores = new Object[2];
						scores[0] = new Integer(cs);
						GPs_scores.put(skey, scores);
						// GPs_scores.put(skey,new Integer(cs));
					}
				}
		}

		// remove the distances smaller than the given 'distanceThreshold'
		Set ks = GPs_scores.keySet();
		Object[] ka = ks.toArray();
		for (int i = 0; i < ka.length; i++) {
			int d = ((Integer) ((Object[]) GPs_scores.get(ka[i]))[0])
					.intValue();
			// int d = ((Integer)(GPs_scores.get(ka[i]))).intValue();
			if (d < gThr)
				GPs_scores.remove(ka[i]);
		}

		// Compute the graph distances (between all the pairs), in the remaining
		// hash structure!
		int[][] FW = computeAllPairsShortestPaths(GPs_scores);
		GPs_scores = updateGPAResults(GPs_scores, FW);

		// .should sort it somehow!>>.
		return GPs_scores;
	}

}
