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

import java.io.*;
import java.util.*;

/**
 * <p>This is the superclass for all models. It provides fields which are indispensable for the characterization of an order-preserving submatrix.</p> <p>This class shall be extended by algorithm implementations in order to implement algorithm-specific functions on the fields.</p> <p>No field hiding is done for performance reasons. Nevertheless only subclasses of this class shall modify the fields.</p> <p>Notation:</p> <table border cellpadding=5> <tr><th align=left>object</th><th align=left>field name</th></tr> <tr><td># tissues in dataset</td><td>m</td></tr> <tr><td># genes in dataset</td><td>n</td></tr> <tr><td># tissues in submatrix</td><td>s</td></tr> <tr><td>set of indices of tissues in submatrix</td><td>T</td></tr> <tr><td># planted genes</td><td>k</td></tr> <tr><td>set of indices of genes in submatrix</td><td>G</td></tr> </table>
 * @author  Thomas Frech
 * @version  2004-07-22
 */
public class Model
{
	// Remark: The following convention for counter variables is used:
	// col for tissue indices
	// row for gene indices
	// i for indices in set T
	// j for indices in set G


	/**
	 * Dataset this model belongs to.
	 * @uml.property  name="dataset"
	 * @uml.associationEnd  inverse="plantedModel:bicat.util.OPSMDataset"
	 */
	public OPSMDataset dataset;

	/**
	 * Length of <tt>T</tt>.
	 * @uml.property  name="lengthOfArrayOfTissueIndices"
	 */
	public int lengthOfArrayOfTissueIndices;

	/**
	 * List of tissue indices in range <tt>0...m-1</tt>, ordered by increasing rank.
	 * @uml.property  name="arrayOfTissueIndices"
	 */
	public int[] arrayOfTissueIndices;

	/**
	 * List of length <tt>m</tt>, <tt>true</tt> for indices which are in <tt>T</tt>.
	 * @uml.property  name="isInT"
	 */
	public boolean[] isInT;

	/**
	 * Length of <tt>G</tt>.
	 * @uml.property  name="lengthArrayOfGeneIndices"
	 */
	public int lengthArrayOfGeneIndices;

	/**
	 * List of gene indices in range <tt>0...n-1</tt>, ordered by increasing index.
	 * @uml.property  name="arrayOfGeneIndices"
	 */
	public int[] arrayOfGeneIndices;

	/**
	 * List of length <tt>n</tt>, <tt>true</tt> for indices which are in <tt>G</tt>.
	 * @uml.property  name="isInG"
	 */
	public boolean[] isInG;

	/**
	 * Model info string which contains information about the circumstances under which this model was found.
	 * @uml.property  name="infoString"
	 */
	public String infoString;

	/** Line separator. */
	public static final String BR = System.getProperty("line.separator");


	/**
	 * Constructor to create empty model.
	 *
	 * @param dataset dataset this model belongs to
	 */
	public Model(OPSMDataset dataset)
	{
		this.dataset = dataset;
		infoString = "";
	}

	/**
	 * Constructor to load model from file.
	 *
	 * @param dataset dataset this model belongs to
	 * @param filename filename of model
	 */
	public Model(OPSMDataset dataset, String filename)
	{
		this.dataset = dataset;
		infoString = "";

		if (Option.printMessages)
			System.out.print("Reading model from '"+filename+"' ... ");

		try
		{
			// read file
			File file = new File(filename);
			FileInputStream inStream = new FileInputStream(file);
			byte[] data = new byte[(int)file.length()];
			inStream.read(data);
			inStream.close();

			// create token vector
			Vector vector = new Vector(1000,1000);
			StringBuffer buffer = null;
			for (int i=0; i<data.length; i++)
			{
				if (data[i]>=33 && data[i]<=127)
				{
					if (buffer==null)
						buffer = new StringBuffer(""+(char)data[i]);
					else
						buffer.append((char)data[i]);
				}
				else if (buffer!=null)
				{
					String string = buffer.toString();
					try
					{
						int integer = Integer.parseInt(string);
						vector.addElement(new Integer(integer));
					}
					catch (Exception e)
					{
						throw new Exception("Illegal entry: "+string);
					}
					buffer = null;
				}
			}

			// get number of columns and rows
			int index = 0;
			if (vector.size()<2)
				throw new Exception("Too few entries.");
			lengthOfArrayOfTissueIndices = ((Integer)vector.elementAt(index++)).intValue();
			lengthArrayOfGeneIndices = ((Integer)vector.elementAt(index++)).intValue();
			if (vector.size()<2+lengthOfArrayOfTissueIndices+lengthArrayOfGeneIndices)
				throw new Exception("Too few entries.");
			else if (vector.size()>2+lengthOfArrayOfTissueIndices+lengthArrayOfGeneIndices)
				throw new Exception("Too many entries.");

			// get columns
			arrayOfTissueIndices = new int[lengthOfArrayOfTissueIndices];
			isInT = new boolean[dataset.nr_col];
			for (int col=0; col<dataset.nr_col; col++)
				isInT[col] = false;
			for (int i=0; i<lengthOfArrayOfTissueIndices; i++)
			{
				arrayOfTissueIndices[i] = ((Integer)vector.elementAt(index++)).intValue();
				isInT[arrayOfTissueIndices[i]] = true;
			}

			// get rows
			arrayOfGeneIndices = new int[lengthArrayOfGeneIndices];
			isInG = new boolean[dataset.nr_rows];
			for (int row=0; row<dataset.nr_rows; row++)
				isInG[row] = false;
			for (int i=0; i<lengthArrayOfGeneIndices; i++)
			{
				arrayOfGeneIndices[i] = ((Integer)vector.elementAt(index++)).intValue();
				isInG[arrayOfGeneIndices[i]] = true;
			}

			// set info string
			infoString = "      Read from file '"+filename+"'.";
		}
		catch (Exception e)
		{
			if (Option.printErrors)
				System.out.println("ERROR: "+e.toString());
			throw new RuntimeException();
		}

		if (Option.printMessages)
			System.out.println("done");
	}

	/**
	 * Constructor to create random model with uniformely distributed tissues and genes.
	 *
	 * @param n # genes in dataset
	 * @param m # tissues in dataset
	 * @param k # genes in submatrix
	 * @param s # tissues in submatrix
	 */
	public Model(int n, int m, int k, int s)
	{
		if (k>n || s>m)
			throw new RuntimeException("Illegal parameters.");

		// initialize model
		dataset = null;
		this.lengthOfArrayOfTissueIndices = s;
		arrayOfTissueIndices = new int[s];
		isInT = new boolean[m];
		this.lengthArrayOfGeneIndices = k;
		arrayOfGeneIndices = new int[k];
		isInG = new boolean[n];

		// select columns
		for (int col=0; col<m; col++)
			isInT[col] = false;
		for (int i=0; i<s; i++)
		{
			int col;
			while (isInT[col=(int)(m*XMath.random())]);
			arrayOfTissueIndices[i] = col;
			isInT[col] = true;
		}

		// select rows
		for (int row=0; row<n; row++)
			isInG[row] = false;
		for (int j=0; j<k; j++)
		{
			int row;
			while (isInG[row=(int)(n*XMath.random())]);
			isInG[row] = true;
		}
		int j = 0;
		for (int row=0; row<n; row++)
			if (isInG[row])
				arrayOfGeneIndices[j++] = row;

		// set info string
		infoString = "      Created by random.";
	}

	/**
	 * Constructor to create copy of parent model.
	 *
	 * @param parent parent model
	 */
	public Model(Model parent)
	{
		// dataset
		dataset = parent.dataset;

		// column related fields
		lengthOfArrayOfTissueIndices = parent.lengthOfArrayOfTissueIndices;
		arrayOfTissueIndices = new int[lengthOfArrayOfTissueIndices];
		for (int i=0; i<lengthOfArrayOfTissueIndices; i++)
			arrayOfTissueIndices[i] = parent.arrayOfTissueIndices[i];
		isInT = new boolean[dataset.nr_col];
		for (int col=0; col<dataset.nr_col; col++)
			isInT[col] = parent.isInT[col];

		// row related fields
		lengthArrayOfGeneIndices = parent.lengthArrayOfGeneIndices;
		if (parent.arrayOfGeneIndices!=null)
		{
			arrayOfGeneIndices = new int[lengthArrayOfGeneIndices];
			for (int i=0; i<lengthArrayOfGeneIndices; i++)
				arrayOfGeneIndices[i] = parent.arrayOfGeneIndices[i];
		}
		isInG = new boolean[dataset.nr_rows];
		for (int row=0; row<dataset.nr_rows; row++)
			isInG[row] = parent.isInG[row];

		// info string
		infoString = (parent.infoString);
	}

	/**
	 * Score function according to Amir Ben-Dor, evaluated with parameters
	 * of this model.
	 *
	 * @return <tt>U(n,m,k,s)</tt> according to Amir Ben-Dor
	 *
	 * @see <a href="#U(n,m,k,s)"><tt>U(n,m,k,s)</tt></a>
	 */
	public final double U()
	{
		return U(dataset.nr_rows,dataset.nr_col,lengthArrayOfGeneIndices,lengthOfArrayOfTissueIndices);
	}

	/**
	 * <a name="U(n,m,k,s)"></a>
	 *
	 * <p>Score function according to Amir Ben-Dor.</p>
	 *
	 * <p><b>Warning</b>: This function is meaningful for small parameters
	 * <tt>n</tt> and <tt>m</tt> only! For large parameters, this function
	 * evaluates to zero!</p>
	 *
	 * @param n # genes in dataset
	 * @param m # tissues in dataset
	 * @param k # genes in submatrix
	 * @param s # tissues in submatrix
	 * @return <tt>U(n,m,k,s)</tt> according to Amir Ben-Dor
	 */
	public static double U(int n, int m, int k, int s)
	{
		double f = 1;

		for (int i=m-s+1; i<=m; i++)
			f *= i;

		double sum = 0;

		for (int i=k; i<=n; i++)
		{
			double t = 1.0/XMath.factorial(s);
			sum += XMath.binomial(n,i)*Math.pow(t,i)*Math.pow(1-t,n-i);
		}

		return f*sum;
	}

	/**
	 * Compare to models.
	 *
	 * @param model model to compare this model with
	 * @return <tt>true</tt> if both models are identical
	 */
	public boolean isEqualTo(Model model)
	{
		if (model.lengthOfArrayOfTissueIndices!=lengthOfArrayOfTissueIndices || model.lengthArrayOfGeneIndices!=lengthArrayOfGeneIndices)
			return false;

		for (int i=0; i<lengthOfArrayOfTissueIndices; i++)
			if (model.arrayOfTissueIndices[i]!=arrayOfTissueIndices[i])
				return false;

		for (int j=0; j<lengthArrayOfGeneIndices; j++)
			if (model.arrayOfGeneIndices[j]!=arrayOfGeneIndices[j])
				return false;

		return true;
	}

	/**
	 * Test if this model contains the given submodel.
	 *
	 * @param submodel submodel
	 * @return <tt>true</tt> if submodel is contained in this model.
	 */
	public boolean contains(Model submodel)
	{
		if (submodel.lengthOfArrayOfTissueIndices>lengthOfArrayOfTissueIndices || submodel.lengthArrayOfGeneIndices>lengthArrayOfGeneIndices)
			return false;

		int i1 = 0;
		for (int i2=0; i2<submodel.lengthOfArrayOfTissueIndices; i2++)
		{
			while (submodel.arrayOfTissueIndices[i2]!=arrayOfTissueIndices[i1])
			{
				i1++;
				if (i1>=lengthOfArrayOfTissueIndices)
					return false;
			}
		}

		int j1 = 0;
		for (int j2=0; j2<submodel.lengthArrayOfGeneIndices; j2++)
		{
			while (submodel.arrayOfGeneIndices[j2]!=arrayOfGeneIndices[j1])
			{
				j1++;
				if (j1>=lengthArrayOfGeneIndices)
					return false;
			}
		}

		return true;
	}


	/**
	 * <p>Create model string.</p>
	 *
	 * <p>String format:</p>
	 * <pre>
	 * s = ..., k = ...
	 * T = { ... ... ... }
	 * G = { ... ... ... }
	 * </pre>
	 *
	 * @return model string
	 */
	public final String toString()
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("s = "+lengthOfArrayOfTissueIndices+", k = "+lengthArrayOfGeneIndices+BR);

		buffer.append("T = { ");
		for (int i=0; i<lengthOfArrayOfTissueIndices; i++)
			buffer.append(arrayOfTissueIndices[i]+(i<lengthOfArrayOfTissueIndices-1?" ":" }"+BR));

		buffer.append("G = { ");
		for (int j=0; j<lengthArrayOfGeneIndices; j++)
			buffer.append(arrayOfGeneIndices[j]+(j<lengthArrayOfGeneIndices-1?" ":" }"+BR));

		return buffer.toString();
	}

	/**
	 * <p>Save model to file.</p>
	 *
	 * <p>File format:</p>
	 * <pre>
	 * s
	 * k
	 * T1 T2 T3 ... Ts
	 * G1 G2 G3 ... Gk
	 * </pre>
	 *
	 * @param filename filename of model file
	 */
	public final void save(String filename)
	{
		if (Option.printMessages)
			System.out.print("Saving model to '"+filename+"' ... ");

		try
		{
			File file = new File(filename);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			PrintStream out = new PrintStream(fileOutputStream);

			out.println(lengthOfArrayOfTissueIndices);
			out.println(lengthArrayOfGeneIndices);
			for (int i=0; i<lengthOfArrayOfTissueIndices; i++)
				out.print(arrayOfTissueIndices[i]+(i<lengthOfArrayOfTissueIndices-1?" ":""));
			out.println();
			for (int j=0; j<lengthArrayOfGeneIndices; j++)
				out.print(arrayOfGeneIndices[j]+(j<lengthArrayOfGeneIndices-1?" ":""));
			out.println();

			out.close();
			fileOutputStream.close();
		}
		catch (Exception e)
		{
			if (Option.printErrors)
				System.out.println("ERROR: Could not save model file.");
			e.printStackTrace();
		}

		if (Option.printMessages)
			System.out.println("done");
	}
}
