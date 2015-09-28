/*
 * Copyright (c) 2015. This file is part of BiCat.
 *
 *                                BiCat is a free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                BiCat is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with BiCat.  If not, see <http://www.gnu.org/licenses/>.
 *
 *                                Copyright notice : Copyright (c) 2005 Swiss Federal Institute of Technology, Computer Engineering and Networks Laboratory. All rights reserved.
 *
 *                                IN NO EVENT SHALL THE SWISS FEDERAL INSTITUTE OF TECHNOLOGY, COMPUTER
 *                                 ENGINEERING AND NETWORKS LABORATORY BE LIABLE TO ANY PARTY FOR
 *                                 DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING
 *                                 OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE
 *                                 SWISS FEDERAL INSTITUTE OF TECHNOLOGY, COMPUTER ENGINEERING AND
 *                                 NETWORKS LABORATORY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 *                                 DAMAGE.
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

package OwnPersonal;

import java.util.LinkedList;

import bicat.biclustering.Bicluster;
import bicat.biclustering.Dataset;

/**
 * This class will compute the mean residue score of the biclusters
 *
 * @author Taghi Aliyev, email : taghialiyev@gmail.com
 */
public class computeMeanResidueScore {

	public static void computeResidue(Dataset dataset) throws Exception {

		/*
		 * Note to self: A matrix with elements randomly and uniformly generated
		 * in the range of [a; b] has an expected score of (b - a)^2/12.
		 */

		// Steps:
		// 1. Compute max/min of the gene expression value
		// 2. Compute expected score
		// 3. Compute score for every cluster
		// 4. Aggregate
		// 5. Compare to evaluation script

		// Stesp 1-2
		int geneAmount = dataset.getGeneCount();
		int chipAmount = dataset.getChipCount();
		float[][] expressionMatrix = dataset.getData();
		float min = Float.MAX_VALUE;
		float max = -Float.MAX_VALUE;
		for (int i = 0; i < geneAmount; i++) {
			for (int j = 0; j < chipAmount; j++) {
				if (expressionMatrix[i][j] > max)
					max = expressionMatrix[i][j];
				if (expressionMatrix[i][j] < min)
					min = expressionMatrix[i][j];
			}
		}
		System.out.println("Max in whole set : " + max
				+ " , min in whole set : " + min);

		float expected = (max - min) * (max - min) / 12.0f;
		System.out.println("Expected score for bi-clusters : " + expected);

		// Step 3
		int lowerThanExpected = 0;
		if (dataset.getBiclusters().size() > 0) {
			int numberOfBiClusters = ((LinkedList) (dataset.getBiclusters()
					.get(dataset.getBiclusters().size() - 1))).size();
			if (numberOfBiClusters > 0) {
				LinkedList biClusters = (LinkedList) dataset.getBiclusters()
						.get(dataset.getBiclusters().size() - 1);
				float[] scores = new float[numberOfBiClusters];
				for (int i = 0; i < numberOfBiClusters; i++) {
					Bicluster cluster = (Bicluster) (biClusters.get(i));
					float score = 0.0f;
					// Computing the score
					int[] chipIndices = cluster.getChips();
					int[] geneIndices = cluster.getGenes();
					float overallAverage = computeOverallAverage(chipIndices,
							geneIndices, expressionMatrix);
					for (int j = 0; j < geneIndices.length; j++) {
						for (int k = 0; k < chipIndices.length; k++) {
							float tmp = expressionMatrix[geneIndices[j]][chipIndices[k]]
									- computeChipAverage(chipIndices,
											expressionMatrix, geneIndices[j])
									- computeGeneAverage(geneIndices,
											expressionMatrix, chipIndices[k])
									+ overallAverage;
							score += Math.pow(tmp, 2);
						}
					}

					score /= (chipIndices.length * geneIndices.length);
					if (score < expected)
						lowerThanExpected++;
//					System.out.println("Big sum with ID : " + cluster.id + " , : " + overallAverage);
//					System.out.println("Score of a cluster with ID " + cluster.id + " : " + score + " " + "and, overall big sum : " + overallAverage);
					scores[i] = score;
				}
			}
		}
//		System.out.println("Lower than expected : " + lowerThanExpected);
	}

	public static float computeOverallAverage(int[] chipIndices, int[] genes,
			float[][] expressionMatrix) {
		float result = 0.0f;

		for (int i = 0; i < genes.length; i++) {
			for (int j = 0; j < chipIndices.length; j++) {
				result += expressionMatrix[genes[i]][chipIndices[j]];
			}
		}

		result = result / (chipIndices.length * genes.length);

		return result;
	}

	public static float computeChipAverage(int[] chipIndices,
			float[][] expressionMatrix, int gene) {
		float result = 0.0f;

		for (int i = 0; i < chipIndices.length; i++) {
			result += expressionMatrix[gene][chipIndices[i]];
		}

		result /= chipIndices.length;

		return result;
	}

	public static float computeGeneAverage(int[] geneIndices,
			float[][] expressionMatrix, int chip) {
		float result = 0.0f;

		for (int i = 0; i < geneIndices.length; i++) {
			result += expressionMatrix[geneIndices[i]][chip];
		}

		result /= geneIndices.length;

		return result;
	}

}
