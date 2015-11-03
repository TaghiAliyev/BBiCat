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

import bicat.util.OPSMDataset;
import bicat.util.XMath;

/**
 * Provides functionality to store a model initialization with its score and to
 * create the model, if it belongs to the best ones.
 *
 * @author Thomas Frech
 * @version 2004-07-22
 */
public final class BendorReloadedModelInitialization extends BendorReloadedModelEvaluation
{
	// dataset
	public OPSMDataset dataset;

	// model size
	public int s;

	// column with lowest rank
	public int ta;

	// column with highest rank
	public int tb;


	/**
	 * Constructor.
	 *
	 * @param dataset dataset
	 * @param s model size
	 * @param ta column index of lowest rank
	 * @param tb column index of highest rank
	 */
	public BendorReloadedModelInitialization(OPSMDataset dataset, int s, int ta, int tb)
	{
		this.dataset = dataset;
		this.s = s;
		this.ta = ta;
		this.tb = tb;
		p = getScore();
	}

	// get score of initial partial model
	private double getScore()
	{
		int n = dataset.nr_rows;
		int m = dataset.nr_col;
		int[][] D = dataset.rankedDataMatrix;

		// calculate exact value for p if initialized model is complete
		if (s==2)
		{
			int k = 0;

			for (int row=0; row<dataset.nr_rows; row++)
				if (D[row][ta]<D[row][tb])
					k++;

			return (double)k/(double)n;
		}

		// else do newton iteration to estimate p
		int gapSize = s - 2;
		double commonDenominator = XMath.binomial(m,s);
		double B = 1.0/(XMath.binomial(m,2)*2);
		double[] A = new double[n];
		double[] diff = new double[n];
		double[] denominator = new double[n];
		double epsilon = BendorReloadedModel.epsilon;

		// calculate A[i] and diff[i]
		for (int i=0; i<n; i++)
		{
			int gi = D[i][tb] - D[i][ta] - 1;
			A[i] = XMath.binomial(gi,gapSize)/commonDenominator;
			diff[i] = A[i] - B;
		}

		// initial value for p
		double p = BendorReloadedModel.p0;

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

	// realize evaluated model
	public BendorReloadedModel realize()
	{
		return new BendorReloadedModel(dataset,s,ta,tb);
	}
}
