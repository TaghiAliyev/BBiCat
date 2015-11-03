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

package bicat.algorithms.opsm;

import bicat.util.Model;
import bicat.util.OPSMDataset;
import bicat.util.XMath;

/**
 * Model of Bendor Reloaded Algorithm. Represents Partial Models and Complete Models.
 *
 * @author Thomas Frech
 * @version 2004-07-22
 */
public class BendorReloadedModel extends Model
{
	// accuracy in newton iteration
	protected static final double epsilon = 0.0001;

	// start value in newton iteration
	protected static final double p0 = 0.05;

	/** number of columns with low rank */
	public int a;

	/** number of columns with high rank */
	public int b;


	/**
	 * Constructor to create new model.
	 *
	 * @param dataset dataset this model belongs to
	 * @param nr_col size of model when it is complete (Anzahl Spalten)
	 * @param ta column with lowest rank
	 * @param tb column with highest rank
	 */
	public BendorReloadedModel(OPSMDataset dataset, int nr_col, int ta, int tb)
	{
		super(dataset);

		this.lengthOfArrayOfTissueIndices = nr_col;
		a = 1;
		b = 1;

		arrayOfTissueIndices = new int[nr_col];
		for (int i=1; i<nr_col-1; i++)
			arrayOfTissueIndices[i] = -1;
		arrayOfTissueIndices[0] = ta;
		arrayOfTissueIndices[nr_col-1] = tb;

		isInT = new boolean[dataset.nr_col];
		for (int i=0; i<dataset.nr_col; i++)
			isInT[i] = false;
		isInT[ta] = true;
		isInT[tb] = true;

		isInG = new boolean[dataset.nr_rows];
		for (int i=0; i<dataset.nr_rows; i++)
			isInG[i] = dataset.rankedDataMatrix[i][ta]<dataset.rankedDataMatrix[i][tb];

		updateCompatibilityInfo();
		updateG();
	}

	/**
	 * Constructor to create copy of parent model.
	 *
	 * @param parent parent model
	 */
	public BendorReloadedModel(BendorReloadedModel parent)
	{
		super(parent);

		a = parent.a;
		b = parent.b;
	}

	/**
	 * @return <tt>true</tt> if model is complete
	 */
	public boolean isComplete()
	{
		return a+b==lengthOfArrayOfTissueIndices;
	}

	/**
	 * Extend model with a column index (extension side is alternated automatically).
	 *
	 * @param t column index
	 */
	public void extend(int t)
	{
		if (a+b==lengthOfArrayOfTissueIndices)
			throw new RuntimeException("Complete Models cannot be extended!");

		if (a==b)
		{
			arrayOfTissueIndices[a] = t;
			a++;
		}
		else
		{
			arrayOfTissueIndices[lengthOfArrayOfTissueIndices-b-1] = t;
			b++;
		}

		isInT[t] = true;

		updateCompatibilityInfo();

		if (a+b==lengthOfArrayOfTissueIndices)
			updateG();
	}

	// get score for extended model
	public double getScoreForExtension(int t)
	{
		int n = dataset.nr_rows;
		int m = dataset.nr_col;
		int[][] D = dataset.rankedDataMatrix;
		int ta = arrayOfTissueIndices[a-1];
		int tb = arrayOfTissueIndices[lengthOfArrayOfTissueIndices-b];

		// calculate exact value for p if extended model is complete
		if (a+b==lengthOfArrayOfTissueIndices-1)
		{
			int k = 0;

			// get number of compatible rows
			for (int row=0; row<n; row++)
				if (isInG[row] && D[row][ta]<D[row][t] && D[row][t]<D[row][tb])
					k++;

			return (double)k/(double)n;
		}

		// else do newton iteration to estimate p
		double commonDenominator = XMath.binomial(m,lengthOfArrayOfTissueIndices);
		double B = 1.0/(XMath.binomial(m,a+b+1)*XMath.factorial(a+b+1));
		double[] A = new double[n];
		double[] diff = new double[n];
		double[] denominator = new double[n];

		// calculate A[i] and diff[i]
		for (int i=0; i<n; i++)
		{
			A[i] = 0;
			if (isInG[i] && D[i][ta]<D[i][t] && D[i][t]<D[i][tb])
			{
				int gi = 0;
				if (a==b)
					gi = D[i][tb] - D[i][t] - 1;
				else
					gi = D[i][t] - D[i][ta] -1;
				A[i] = XMath.binomial(gi,lengthOfArrayOfTissueIndices-a-b-1)/commonDenominator;
			}
			diff[i] = A[i] - B;
		}

		// initial value for p
		double p = p0;

		// do at most 20 newton iteration steps to get p
		for (int iteration=0; iteration<20; iteration++)
		{
			double f = -n;
			double dfdp = 0;

			for (int i=0; i<n; i++)
			{
				denominator[i] = diff[i]*p + B;

				if (A[i]==0)
				{
				}
				else if (denominator[i]==0)
				{
					System.out.println("WARNING: Division by 0 in Newton iteration.");
					f = 0;
					dfdp = 1;
				}
				else
				{
					f += A[i]/denominator[i];
					dfdp -= A[i]*diff[i]/(denominator[i]*denominator[i]);
				}
			}

			if (dfdp==0)
			{
				return 0;
			}
			else
			{
				double q = p;

				p -= f/dfdp;

				if (Math.abs(f/dfdp)<epsilon)
					break;

				// special modification of newton algorithm, required to prevent from being caught in negative range
				if (p<0 && iteration<19)
					p = q/2;
			}
		}

		if (p<0 || p>1)
			p = 0;

		return p;
	}

	// Checks for each row if it is compatible with this model. The check relies
	// on previous checks, since it only checks the indices adjacent to the gap.
	// Only k and isInG[] are updated, G has to be updated separately.
	protected void updateCompatibilityInfo()
	{
		lengthArrayOfGeneIndices = 0;
		int[][] D = dataset.rankedDataMatrix;

		for (int i=0; i<dataset.nr_rows; i++)
		{
			if (isInG[i])
			{
				isInG[i] = (a==1 || D[i][arrayOfTissueIndices[a-2]]<D[i][arrayOfTissueIndices[a-1]])
					&& (D[i][arrayOfTissueIndices[a-1]]<D[i][arrayOfTissueIndices[lengthOfArrayOfTissueIndices-b]])
					&& (b==1 || D[i][arrayOfTissueIndices[lengthOfArrayOfTissueIndices-b]]<D[i][arrayOfTissueIndices[lengthOfArrayOfTissueIndices-b+1]]);
				if (isInG[i])
					lengthArrayOfGeneIndices++;
			}
		}
	}

	/**
	 * Update set G.
	 */
	public void updateG()
	{
		arrayOfGeneIndices = new int[lengthArrayOfGeneIndices];
		int cursor = 0;
		for (int i=0; i<dataset.nr_rows; i++)
			if (isInG[i])
				arrayOfGeneIndices[cursor++] = i;
	}

	/**
	 * Print model info.
	 */
	public void print()
	{
		updateG();

		System.out.println(isComplete()?"Complete Model:":"Partial Model:");
		System.out.println("n = "+dataset.nr_rows+", m = "+dataset.nr_col+", s = "+lengthOfArrayOfTissueIndices+", k = "+lengthArrayOfGeneIndices+(isComplete()?"":", a = "+a+", b = "+b));
		if (isComplete())
			System.out.println("Score U(s,k) = "+U());

		System.out.print("Experiment indices: ");
		for (int i=0; i<lengthOfArrayOfTissueIndices; i++)
			System.out.print(arrayOfTissueIndices[i]+" ");
		System.out.println();
		System.out.print("Gene indices: ");
		for (int i=0; i<lengthArrayOfGeneIndices; i++)
			System.out.print(arrayOfGeneIndices[i]+" ");
		System.out.println();
		System.out.println();
	}
}
