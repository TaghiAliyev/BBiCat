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

package bicat.util;

//import java.io.*;
//import java.util.*;
//
//import com.sun.org.apache.bcel.internal.generic.DMUL;

/**
 * <p>
 * Represents a dataset. Datasets can be obtained from files or be created by
 * random.
 * </p>
 * 
 * <p>
 * This class provides a main method to create random datasets by a command line
 * call.
 * </p>
 * 
 * <p>
 * Notation:
 * </p>
 * <table border cellpadding=5>
 * <tr>
 * <th align=left>object</th>
 * <th align=left>field name</th>
 * </tr>
 * <tr>
 * <td># tissues in dataset</td>
 * <td>m</td>
 * </tr>
 * <tr>
 * <td># genes in dataset</td>
 * <td>n</td>
 * </tr>
 * <tr>
 * <td># tissues in submatrix</td>
 * <td>s</td>
 * </tr>
 * <tr>
 * <td>set of indices of tissues in submatrix</td>
 * <td>T</td>
 * </tr>
 * <tr>
 * <td># planted genes</td>
 * <td>k</td>
 * </tr>
 * <tr>
 * <td>set of indices of genes in submatrix</td>
 * <td>G</td>
 * </tr>
 * </table>
 * 
 * @author Thomas Frech
 * @version 2004-07-22
 */
public final class OPSMDataset {
	// Remark: The following convention for counter variables is used:
	// col for tissue indices
	// row for gene indices
	// i for indices in set T
	// j for indices in set G

	/** Ranked data matrix. The ranks are in range <tt>1...m</tt>. */
	public int[][] rankedDataMatrix;

	/** # tissues (columns). */
	public int nr_col;

	/** # genes (rows). */
	public int nr_rows;

	/** gene names */
	public String[] geneName;

	/** tissue names */
	public String[] experimentName;

	/** Planted model. Not available for real datasets. */
	public Model plantedModel;

//	private boolean isRandom;

	/**
	 * Constructor for OPSMDataset from existing matrix (created 26.7.05 Simon
	 * Barkow)
	 * 
	 * @param matrix
	 */
	public OPSMDataset(float[][] matrix) {
		float[][] DRaw = matrix.clone();
		nr_rows = DRaw.length;
		nr_col = DRaw[0].length;
		rank(DRaw);
	}

	// Create ranked data matrix from raw data matrix.
	private void rank(float[][] DRaw) {
		// initialize ranked data matrix
		rankedDataMatrix = new int[nr_rows][nr_col];
		for (int row = 0; row < nr_rows; row++)
			for (int col = 0; col < nr_col; col++)
				rankedDataMatrix[row][col] = -1;

		// do ranking in all rows
		// WARNING: Inefficient algorithm! Improvment required for very big
		// matrices!
		for (int row = 0; row < nr_rows; row++) {
			for (int rank = nr_col; rank > 0; rank--) {
				float maxValue = -Float.MAX_VALUE;
				int maxCol = -1;

				for (int col = 0; col < nr_col; col++) {
					if (rankedDataMatrix[row][col] == -1
							&& DRaw[row][col] >= maxValue) {
						maxValue = DRaw[row][col];
						maxCol = col;
					}
				}
				rankedDataMatrix[row][maxCol] = rank;
			}
		}
//		for (int row = 0; row < nr_rows; row++) {
//			for (int col = 0; col < nr_col; col++) {
//				System.out.print(col == 0 ? "" : ", ");
//				System.out.print(rankedDataMatrix[row][col]);
//			}
//		}
	}

	/**
	 * // *
	 * <p> // * Constructor. Loads dataset from file. // *
	 * </p> // * // *
	 * <p> // * The planted model will be searched in a file named // *
	 * "&lt;filename&gt;.model". // *
	 * </p> // * // *
	 * 
	 * @param filename // *
	 *            filename of dataset //
	 */
	// public OPSMDataset(String filename) {
	// if (Option.printMessages)
	// System.out.print("Reading dataset from '" + filename + "' ... ");
	//
	// isRandom = false;
	//
	// // raw data matrix
	// float[][] DRaw = null;
	//
	// try {
	// // read file
	// File file = new File(filename);
	// FileInputStream inStream = new FileInputStream(file);
	// byte[] data = new byte[(int) file.length()];
	// inStream.read(data);
	// inStream.close();
	//
	// // create token vector
	// Vector vector = new Vector(100000, 100000);
	// StringBuffer buffer = null;
	// for (int i = 0; i < data.length; i++) {
	// if (data[i] >= 33 && data[i] <= 127) {
	// if (buffer == null)
	// buffer = new StringBuffer("" + (char) data[i]);
	// else
	// buffer.append((char) data[i]);
	// } else if (buffer != null) {
	// String string = buffer.toString();
	// try {
	// double d = Double.parseDouble(string);
	// vector.addElement(new Double(d));
	// } catch (Exception e) {
	// vector.addElement(string);
	// }
	// buffer = null;
	// }
	// }
	//
	// // check header
	// int index = 0;
	// int maxIndex = vector.size() - 1;
	// if (index > maxIndex)
	// throw new Exception(
	// "Unexpected end of file while reading header.");
	// String header = null;
	// Object token = vector.elementAt(index++);
	// if (token instanceof String)
	// header = (String) token;
	// else
	// header = "" + ((Double) token).doubleValue();
	// if (header.compareTo("probeset") != 0
	// && header.compareTo("randset") != 0)
	// throw new Exception("Wrong header: '" + header
	// + "' instead of 'probeset' or 'randset'!");
	//
	// // read column names
	// nr_col = 0;
	// Vector experimentNameVector = new Vector(1000, 1000);
	// while (index <= maxIndex
	// && (token = vector.elementAt(index++)) instanceof String) {
	// experimentNameVector.add(token);
	// nr_col++;
	// }
	// index -= 2;
	// nr_col--;
	// experimentNameVector.removeElementAt(nr_col);
	//
	// if (index > maxIndex)
	// throw new Exception(
	// "Unexpected end of file while reading header.");
	//
	// // read gene names and data
	// nr_rows = 0;
	// Vector geneNameVector = new Vector(10000, 10000);
	// Vector geneDataVector = new Vector(10000, 10000);
	// while (index <= maxIndex) {
	// if ((token = vector.elementAt(index++)) instanceof Double)
	// throw new Exception("Illegal gene name in row " + nr_rows
	// + ": " + ((Double) token).doubleValue());
	// geneNameVector.add(token);
	//
	// if (index + nr_col - 1 > maxIndex)
	// throw new Exception(
	// "Unexpected end of file while reading row "
	// + nr_rows);
	//
	// double[] geneData = new double[nr_col];
	// for (int i = 0; i < nr_col; i++) {
	// if ((token = vector.elementAt(index++)) instanceof String)
	// throw new Exception("Illegal gene data in row "
	// + nr_rows + ": " + (String) token);
	// geneData[i] = ((Double) token).doubleValue();
	// }
	//
	// geneDataVector.add(geneData);
	// nr_rows++;
	// }
	//
	// // create arrays
	// experimentName = new String[nr_col];
	// geneName = new String[nr_rows];
	// DRaw = new float[nr_rows][nr_col];
	//
	// for (int i = 0; i < nr_col; i++)
	// experimentName[i] = (String) experimentNameVector.elementAt(i);
	//
	// for (int j = 0; j < nr_rows; j++) {
	// geneName[j] = (String) geneNameVector.elementAt(j);
	// double[] geneData = (double[]) geneDataVector.elementAt(j);
	//
	// for (int i = 0; i < nr_col; i++)
	// DRaw[j][i] = (float) geneData[i];
	// }
	//
	// if (Option.printMessages)
	// System.out.println("done");
	// } catch (Exception e) {
	// if (Option.printErrors)
	// System.out.println("ERROR: " + e.getMessage());
	// throw new RuntimeException();
	// }
	//
	// // rank raw data matrix
	// if (Option.printMessages)
	// System.out.print("Ranking dataset ... ");
	// rank(DRaw);
	// if (Option.printMessages)
	// System.out.println("done");
	//
	// // load model
	// if (new File(filename + ".model").exists())
	// plantedModel = new Model(this, filename + ".model");
	// }
	// /**
	// * // *
	// * <p>
	// * Print content of D to console.
	// * </p> // * // *
	// * <p>
	// * For debugging purposes only. Not recommended for large datasets.
	// * </p> //
	// */
	// public void print() {
	// System.out.println();
	//
	// // print col indices
	// System.out.print(" ");
	// for (int col = 0; col < nr_col; col++)
	// System.out.print((col < 10 ? " " : col < 100 ? " " : "") + col
	// + " ");
	// System.out.println();
	//
	// // print planted col markers
	// if (plantedModel != null) {
	// System.out.print(" ");
	// for (int col = 0; col < nr_col; col++)
	// if (plantedModel.isInT[col])
	// System.out.print(" V ");
	// else
	// System.out.print(" ");
	// System.out.println();
	// }
	//
	// // print rows
	// for (int row = 0; row < nr_rows; row++) {
	// // print row indices
	// System.out.print((row < 10 ? " " : row < 100 ? " " : "") + row
	// + " ");
	//
	// // print planted row markers
	// if (plantedModel != null && plantedModel.isInG[row])
	// System.out.print("> ");
	// else
	// System.out.print(" ");
	//
	// // print values
	// for (int col = 0; col < nr_col; col++)
	// System.out.print((rankedDataMatrix[row][col] < 10 ? " "
	// : rankedDataMatrix[row][col] < 100 ? " " : "")
	// + "" + rankedDataMatrix[row][col] + " ");
	//
	// System.out.println();
	// }
	//
	// System.out.println();
	// System.out.println();
	// }
	//
	// /**
	// * <p>
	// * Save random dataset to file. Meaningful for randomly created datasets
	// * only. Non-random datasets will cause an exception.
	// * </p>
	// *
	// * <p>
	// * The names of the tissues and genes are set to their index, with planted
	// * tissues and genes marked with 'X'.
	// * </p>
	// *
	// * <p>
	// * The planted model will be saved in a file named
	// "&lt;filename&gt;.model".
	// * </p>
	// *
	// * @param filename
	// * filename of dataset
	// */
	// public void save(String filename) {
	// if (!isRandom)
	// throw new RuntimeException("Meaningless operation.");
	//
	// if (Option.printMessages)
	// System.out
	// .print("Saving random dataset to '" + filename + "' ... ");
	//
	// // save dataset
	// try {
	// File file = new File(filename);
	// FileOutputStream fileOutputStream = new FileOutputStream(file);
	// PrintStream out = new PrintStream(fileOutputStream);
	//
	// out.print("randset\t");
	// for (int col = 0; col < nr_col; col++)
	// out.print((plantedModel.isInT[col] ? "X_" : "__") + col
	// + (col < nr_col - 1 ? "\t" : ""));
	// out.println();
	// for (int row = 0; row < nr_rows; row++) {
	// out.print((plantedModel.isInG[row] ? "X_" : "__") + row + "\t");
	// for (int col = 0; col < nr_col; col++)
	// out.print(rankedDataMatrix[row][col]
	// + (col < nr_col - 1 ? "\t" : ""));
	// out.println();
	// }
	//
	// out.close();
	// fileOutputStream.close();
	// } catch (Exception e) {
	// if (Option.printErrors)
	// System.out.println("ERROR: Could not save dataset file.");
	// }
	//
	// if (Option.printMessages)
	// System.out.println("done");
	//
	// // save planted model
	// plantedModel.save(filename + ".model");
	// }
	//	

	/**
	 * <p>
	 * Constructor. Creates random dataset.
	 * </p>
	 * 
	 * @param n #
	 *            genes
	 * @param m #
	 *            tissues
	 * @param g #
	 *            planted genes
	 * @param t #
	 *            planted tissues
	 */
	// public OPSMDataset(int n, int m, int g, int t)
	// {
	// if (Option.printMessages)
	// System.out.print("Creating random dataset ... ");
	//	
	// // initialize dataset
	// this.nr_rows = n;
	// this.nr_col = m;
	// isRandom = true;
	//	
	// // create planted model
	// plantedModel = new Model(n,m,g,t);
	// plantedModel.dataset = this;
	//	
	// // generate raw data matrix
	// double[][] DRaw = new double[n][m];
	// for (int row=0; row<n; row++)
	// for (int col=0; col<m; col++)
	// DRaw[row][col] = (int)(100*XMath.random());
	//	
	// // rank raw data matrix
	// rank(DRaw);
	//	
	// // plant submatrix (bubble sort on planted columns and rows in DRaw)
	// // WARNING: Inefficient sorting algorithm! Improvment required for very
	// // big matrices!
	// // Remark: The planting has to be done *after* the ranking, since equal
	// // raw values will cause trouble otherwise:
	// // Rows may get unplanted during ranking because of random ranking of
	// // equal raw values.
	// for (int j=0; j<g; j++)
	// {
	// boolean finished = false;
	// int row = plantedModel.G[j];
	//	
	// while (!finished)
	// {
	// finished = true;
	//	
	// for (int i=0; i<t-1; i++)
	// {
	// int col1 = plantedModel.T[i];
	// int col2 = plantedModel.T[i+1];
	//	
	// if (D[row][col1]>D[row][col2])
	// {
	// int buffer = D[row][col1];
	// D[row][col1] = D[row][col2];
	// D[row][col2] = buffer;
	// finished = false;
	// }
	// }
	// }
	// }
	//	
	// if (Option.printMessages)
	// System.out.println("done");
	// }
}
