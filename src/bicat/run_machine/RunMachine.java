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

package bicat.run_machine;

import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Generic Run Machine implementation. Every clustering method implements its own, by extending from this class.
 * Specific classes only needs arguments as input (Arguments holds the dataset in it as well) for them to be run
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class RunMachine {

	// this super-class contains the fields and methods common to different
	// Run-Machines (BiMax, CC, ISA, xMotif,OPSM)
	// ===========================================================================
	public RunMachine() {
	}


	protected BicatGui owner;

	protected int nrGenes = -1;

	protected int nrChips = -1;

	protected UtilFunctionalities engine;

	protected LinkedList computedBiclusters; // "raw" biclusters computed by algorithm.

	protected LinkedList outputBiclusters; // transferred from bimax... run.

	protected boolean cullDegenerate; // remove the "line" BCs?

	// ===========================================================================
	/**
	 * 
	 * Retrieve the list of <code>bimax.Bicluster</code> objects computed by
	 * <code>bimax.Bimax</code> to the local representation,
	 * <code>bicat.biclustering.Bicluster_Bitset</code> objects. <br>
	 * 
	 * The transferred list is automatically sorted (by Size).
	 * <p>
	 * 
	 */
	void transferList() {

		bicat.run_machine.Bicluster_bitset bIn; // Bicluster object from bcs
		// list that will be made into a
		// Bicluster
		bicat.biclustering.Bicluster bOut; // new Bicluster object that will be
		// added to the outputBiclusters
		// list

		int[] genes, chips; // aux. arrays for gene sets and chips sets of a bc

		outputBiclusters = new LinkedList();

		for (int i = 0; i < computedBiclusters.size(); i++) {

			bIn = (bicat.run_machine.Bicluster_bitset) computedBiclusters
					.get(i);
			genes = bIn.getGenes();
			chips = bIn.getChips();

			if (cullDegenerate)
				if ((genes.length <= 1) || (chips.length <= 1))
					continue;
			
			bOut = new bicat.biclustering.Bicluster(i, genes, chips, engine.getCurrentDataset().getData());
			outputBiclusters.add(bOut);
		}

		// SORT works?
		Collections.sort(outputBiclusters);
	}

	// ===========================================================================
	LinkedList getOutputBiclusters(String file) {

		try {
			FileReader fr = new FileReader(file);
			LineNumberReader lnr = new LineNumberReader(fr);

			String s = lnr.readLine();

			if (s == null)
				return new LinkedList();
			if (0 == s.length())
				return new LinkedList(); // special case, when no BCs are
			// returned.

			String[] fragments = s.split(" ", -1);
			BitSet genes = convertIntoBitSet(fragments); // new BitSet();

			s = lnr.readLine();
			fragments = s.split(" ", -1);
			BitSet chips = convertIntoBitSet(fragments);

			LinkedList biclusters = new LinkedList();

			Bicluster_bitset bc = new Bicluster_bitset(genes, chips);
			biclusters.add(bc);

			s = lnr.readLine();
			boolean is_gene = true;

			while ((null != s) && (0 != s.length())) {
				fragments = s.split(" ", -1);
				if (is_gene) {
					genes = convertIntoBitSet(fragments);
					is_gene = false;
				} else {
					chips = convertIntoBitSet(fragments);
					is_gene = true;

					bc = new Bicluster_bitset(genes, chips);
					biclusters.add(bc);
				}
				s = lnr.readLine();
			}
			return biclusters;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // System.exit(1);
		catch (IOException e) {
			e.printStackTrace();
		} // System.exit(1); }

		return null;
	}

	// ===========================================================================
	BitSet convertIntoBitSet(String[] bits) {

		BitSet r = new BitSet(bits.length);
		for (int i = 0; i < bits.length; i++) {
			int v = Integer.parseInt(bits[i]);
			if (1 == v)
				r.set(i);
		}

		return r;
	}

}