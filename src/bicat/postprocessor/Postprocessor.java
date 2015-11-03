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

package bicat.postprocessor;

import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Dataset;
import bicat.gui.BicatGui;
import bicat.util.AnalysisUtil;
import bicat.util.PostUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedList;

/*
 * Postprocessor is used to run clustering algorithms on the data sets and
 * search/sort the results.
 * 
 * It is important to note that <code>Postprocessor</code> uses its own class -
 * <code>Bicluster</code> - instead of the <code>bicat.Bicluster_bitset</code>
 * objects. <p> The methods in this class are generally called from the
 * governing <code>BicaGUI</code> object. <p> The data must be read from a file
 * and prepared by the <code>Preprocessor</code> before being handed to the
 * <code>PostProcessor</code> for biclustering. Although only the binarized data
 * is required for (bi)clustering, <code>Postprocessor</code> will also keep a
 * copy of the raw data for use in creation of <code>Bicluster</code> objects
 * and searching. <p> Once biclustering has been performed, the
 * <code>Postprocessor</code> will keep one copy of the original results in
 * <code>bcs_add</code>, and put all search/sort results into
 * <code>bcs_add_results</code>. This allows an arbitrary number of searches to
 * be performed on the original set of results.
 * 
 * @author D.Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 * @version 0.2
 */

// SHOULD CONTAIN THE MANAGEMENT OF THE BICLUSTERING LISTS, SEARCH, FILTER, AND
// ANALYSIS RESULTS! (ONLY)
@Data
@EqualsAndHashCode(of = {"total_genes", "total_chips"})
public class Postprocessor {

	/**
	 * Hook to the governing <code>BicaGUI</code>, needed to inform it of
	 * process in biclustering.
	 */
	private BicatGui owner;

	private int total_genes = 0; // this information should be associated with
								// the biclustersList (pre-processed, nrG, nrC,
								// extended, cullDegenerate...)

	private int total_chips = 0;

	private UtilFunctionalities engine;

	// ===========================================================================
	public Postprocessor(UtilFunctionalities engine) {
		this.owner = null;
		this.engine = engine;
		this.engine.setPost(this);
	}

	/** Default constructor, initializes some values. */
	public Postprocessor(BicatGui o) {

		owner = o;
		this.engine = owner.getUtilEngine();
		this.engine.setPost(this);
		// cullDegenerate = false; // DF: true;
		// gotBiclusters = false;
		// gotSearchResults = false;
		// extended = false;

		// rawData = null;

		// bcs = null;
		// bcs_add = null;
		// bcs_search_results = null;

		// biclusterList = new LinkedList();
		// searchList = new LinkedList();
		// filterList = new LinkedList();
		// analysisList = new LinkedList();
	}

	// ************************************************************************
	// //
	// * * //
	// * Post-processing procedures: filter, search, gpa... * //
	// * * //
	// ************************************************************************
	// //

	/**
	 * Filter out biclusters where: (a) area of BC smaller than <code>bcD</code>
	 * , or (b) gene set cardinality smaller than <code>gD</code>, or (c) chip
	 * set cardinality smaller that <code>cD</code>.
	 * <p>
	 * 
	 * Additionally, updates the dataset list with obtained results.
	 * 
	 */
	public LinkedList filterBySize(LinkedList bcList, int minG, int maxG,
			int minC, int maxC, int limitG, int limitC) {
		return PostUtil.filterBySize(minG, maxG, minC, maxC, limitG, limitC,
				bcList);
	}

	/**
	 * Filter out biclusters to obtain a <i>representative selection</i> of
	 * biclusters. A representative selection of biclusters are largest
	 * <code>bcNr</code> biclusters with an area overlap limited by parameter
	 * <code>thr</code>.
	 * <p>
	 * 
	 * Additionally, updates the dataset list with obtained results.
	 * 
	 */
	public LinkedList filterByOverlap(LinkedList bcList, int nrBCs, int overlap) {
		return PostUtil.filterByOverlap(nrBCs, overlap, bcList);
	}


	// ===========================================================================
	public static bicat.biclustering.Bicluster getExtension(
			bicat.biclustering.Bicluster bc, int error, boolean ext,
			int[][] dm, boolean gene_d_first) { // error is in percentage!

		int gd, cd;
		if (ext) {
			gd = dm.length / 2;
			cd = dm[0].length / 2;
		} else {
			gd = dm.length;
			cd = dm[0].length;
		}

		return bicat.util.PostUtil.getHammingDistanceExtension(bc, gd, cd,
				error, dm,
				// ext,
				gene_d_first);
	}

	public LinkedList search(LinkedList bcList, String geneStr, String chipStr,
			boolean andSearch) {
		PostUtil postUtil;
		if (owner != null) {
			postUtil = new PostUtil(owner);
		}
		else
		{
			postUtil = new PostUtil(engine);
		}
		return postUtil.search(0, 0, 0, geneStr, chipStr, andSearch, bcList);
	}


	/**
	 * Called from bicat.gui.window.Filter
	 */
	public void filter(int data, int list, int idx, int minG, int maxG,
					   int minC, int maxC, int nrBCs, int overlap) {

		if (engine.isDebug())
			System.out.println("Starting filter ...");

		Dataset BcR = (Dataset) engine.getDatasetList().get(data);

		LinkedList biclusterList = (LinkedList) BcR.getBCsList(list).get(idx);
		int limit_G = BcR.getGeneCount();
		int limit_C = BcR.getWorkingChipCount();

		biclusterList = filterBySize(biclusterList, minG, maxG, minC,
				maxC, limit_G, limit_C);
		biclusterList = filterByOverlap(biclusterList, nrBCs, overlap);

		// do management ...
		BcR.updateFilterBiclustersLists(biclusterList, list, idx);

		if (owner != null) {
			owner.updateFilterMenu();
			owner.buildTree();

			owner.getTree().setSelectionPath(owner.getPreprocessedPath());
			int row = owner.getTree().getRowCount() - 1;
			while (row >= 0) {
				owner.getTree().collapseRow(row);
				row--;
			}
		}
	}

	// ===========================================================================

	/**
	 * Called from bicat.gui.window.Search
	 */
	public void search(int data, int list, int idx, String genes, String chips,
					   boolean andSearch) {

		if (engine.isDebug())
			System.out.println("Starting search ...");

		Dataset BcR = (Dataset) engine.getDatasetList().get(data);
		LinkedList biclusterList = (LinkedList) BcR.getBCsList(list).get(idx);

		biclusterList = search(biclusterList, genes, chips, andSearch);

		// do management ...
		BcR.updateSearchBiclustersLists(biclusterList, list, idx);

		if (owner != null) {
			owner.updateSearchMenu();
			owner.buildTree();

			owner.getTree().setSelectionPath(owner.getPreprocessedPath());
			owner.getTree().setSelectionPath(owner.getPreprocessedPath());
			int row = owner.getTree().getRowCount() - 1;
			while (row >= 0) {
				owner.getTree().collapseRow(row);
				row--;
			}
		}
	}

	/**
	 * Called from bicat.gui.window.GenePairAnalysis
	 *
	 * @throws Exception
	 */
	public void genePairAnalysis(int data, int list, int idx, int minCoocScore,
								 int minCommonScore, boolean byCooc) throws Exception {

		Dataset BcR = (Dataset) engine.getDatasetList().get(data);
		LinkedList biclusterList = (LinkedList) BcR.getBCsList(list).get(idx);

		HashMap gpa = new HashMap();

		if (engine.isDebug()) {
			System.out.println("Starting gene pair analysis ...");
			System.out.println("Hashmap for biclusterList: "
					+ biclusterList.toString());
		}

		if (byCooc)
			gpa = gpaByCoocurrence(biclusterList, minCoocScore, BcR);
		else
			gpa = gpaByCommonChips(biclusterList, minCommonScore);

		if (engine.isDebug())
			System.out.println("Hashmap size for gpa: " + gpa.size());
		// do management ...
		BcR.updateAnalysisBiclustersLists(gpa, list, idx);

		if (owner != null) {
			owner.updateAnalysisMenu();
			owner.buildTree();

			owner.getTree().setSelectionPath(owner.getPreprocessedPath());
			owner.getTree().setSelectionPath(owner.getPreprocessedPath());
			int row = owner.getTree().getRowCount() - 1;
			while (row >= 0) {
				owner.getTree().collapseRow(row);
				row--;
			}
		}

	}

	/**
	 * Gene Pair Analysis (GPA) by Cooccurrence, of biclusters in the
	 * <code>list.idx</code>. Each gene pair obtains a score, which is a
	 * frequence of co-occurrence in considered biclusters.
	 * <p>
	 * 
	 * Additionally, updates the dataset list with obtained results.
	 * @throws Exception 
	 * 
	 */
	public HashMap gpaByCoocurrence(LinkedList bcList, int minCoocScore,
			Dataset dataset) throws Exception {
		return AnalysisUtil.gpaByCoocurrence(minCoocScore, bcList, dataset);
	}

	/**
	 * Gene Pair Analysis (GPA) by Common Chips, of biclusters in the
	 * <code>list.idx</code>. Each gene pair obtains a score, which is the
	 * maximum size of the chip set, where the two genes are co-biclustered.
	 * <p>
	 * 
	 * Additionally, updates the dataset list with obtained results.
	 * 
	 */
	public HashMap gpaByCommonChips(LinkedList bcList, int minCommonScore) {
		return AnalysisUtil.gpaByCommonChips(minCommonScore, bcList);
	}

	@Override
	public String toString()
	{
		return "Postprocessor with total gene count of " + this.getTotal_genes() + " and total chip count of " + this.getTotal_chips();
	}

}
