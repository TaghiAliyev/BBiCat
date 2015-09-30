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

package bicat.algorithms.opsm;

import java.util.LinkedList;

import bicat.util.OPSMDataset;
import bicat.util.Option;
import bicat.util.ParetoModelRecord;

/**
 * Bendor Reloaded Algorithm.
 * 
 * @author Thomas Frech
 * @version 2004-07-22
 */
public final class BendorReloadedAlgorithm {

	public static ParetoModelRecord computedBiclustersPmr;
	public static LinkedList computedBiclusters;
	public void run(float[][] matrix, int l) {
		OPSMDataset o = new OPSMDataset(matrix); 
		run(o, l);
	}
	/**
	 * Run algorithm over full <tt>s</tt> range: <tt>s = 2...m</tt>
	 * 
	 * @param dataset
	 * @param l algorithm parameter
	 * @return result record
	 */
	public static LinkedList run(OPSMDataset dataset, int l) {
		computedBiclustersPmr = run(dataset, l, 2, dataset.nr_col);
		System.out.println("OPSM run started");
		Pmr2listConverter pmrConverter = new Pmr2listConverter();
		computedBiclusters = pmrConverter.convert(computedBiclustersPmr);
		return computedBiclusters;
	}

	/**
	 * Run algorithm for a specified range of <tt>s</tt>:
	 * <tt>s = s1...s2</tt>
	 * 
	 * @param dataset
	 *            dataset
	 * @param l
	 *            algorithm parameter
	 * @param s1
	 *            start value for s
	 * @param s2
	 *            end value for s
	 * @return result record
	 */
	public static ParetoModelRecord run(OPSMDataset dataset, int l, int s1,
			int s2) {
		ParetoModelRecord pmr = new ParetoModelRecord();

		for (int s = s1; s <= s2; s++) {
			if (Option.printProgress) {
				System.out.println("s = " + s);
			}

			// run algorithm for one value of s
			BendorReloadedModel model = run(dataset, s, l);
			if (model == null || model.lengthArrayOfGeneIndices <= 1) {
				break;
			}
			pmr.add(model);
			//System.out.println("ParetoModel Size: " + pmr.getSize());
		}
		System.out.println("ParetoModelRecord: " + pmr.toString()); //pmr ausgeben
		return pmr;
	}

	/**
	 * Run algorithm for one value of <tt>s</tt>
	 * 
	 * @param dataset
	 *            dataset
	 * @param l
	 *            algorithm parameter
	 * @param s
	 *            value of s
	 * @return result record
	 */
	public static BendorReloadedModel run(OPSMDataset dataset, int s, int l) {
		int m = dataset.nr_col;

		// fill first record with best l model initializations
		BendorReloadedModelRecord record = new BendorReloadedModelRecord(l);
		for (int ta = 0; ta < m; ta++) {
			for (int tb = 0; tb < m; tb++) {
				if (ta != tb) {
					BendorReloadedModelInitialization modelInitialization = new BendorReloadedModelInitialization(
							dataset, s, ta, tb);
					record.put(modelInitialization);
				}
			}
		}

		// if s=2, realize and return best complete model of size 2
		if (s == 2) {
			return record.realizeBest();
		}

		// else iterate until models are complete
		for (int iteration = 0; iteration < s - 2; iteration++) {
			// realize best l models
			BendorReloadedModel[] parent = record.realizeAll();

			if (parent.length == 0) {
				return null;
			}

			// create new record
			record = new BendorReloadedModelRecord(l);

			// evaluate all possible extensions of all parent models
			for (int i = 0; i < parent.length; i++) {
				for (int t = 0; t < m; t++) {
					if (!parent[i].isInT[t]) {
						record.put(new BendorReloadedModelExtension(parent[i],
								t));
					}
				}
			}
		}

		// realize and return best complete model of size s
		return record.realizeBest();
	}

	public LinkedList getBiclusters() {
		return computedBiclusters;
	}
}
