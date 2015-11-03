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

package bicat.Constants;

/**
 * This class contains all the constants that are used by different methods/algorithms
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class MethodConstants {

    /*
    Constants used by the HCL Method
     */

    // Linkage constants
    public final static int SINGLE_LINKAGE = 0;

    public final static int COMPLETE_LINKAGE = 1;

    public final static int AVERAGE_LINKAGE = 2;

    // Modus possibilities
    public final static int AGGLOMERATIVE = 0;

    public final static int DIVISIVE = 1; // NOT implemented!

    // Distance measures - Shared mostly by both K-Means and HCL
    public final static int EUCLIDEAN_DISTANCE = 0;

    public final static int PEARSON_CORRELATION = 1;

    public final static int MANHATTAN_DISTANCE = 2;

    public final static int MINKOWSKI_DISTANCE = 3; // power 2

    public final static int COSINE_DISTANCE = 4;


    /*
    Constants that are used by the KMeans method
     */

    public final static int COSINE_DISTANCE_KMeans = 3;

    public static int EMPTY_ACTION_ERROR = 0;

    public static int EMPTY_ACTION_DROP = 1;

    public static int EMPTY_ACTION_SINGLETON = 2;

    public static int START_MODE_RANDOM = 0;

    public static int START_MODE_UNIFORM_RANDOM = 1;

    public static int START_MODE_CLUSTER = 2; // do a pre-phase ...

    public static int START_MODE_MATRIX = 3; // pre-specify the starting

    /*
    Run machine constants
     */

    public static int HCL_ID = 0;

    public static int KMEANS_ID = 1;

    public static int BIMAX_ID = 0;

    public static int ISA_ID = 1;

    public static int CC_ID = 2;

    public static int XMOTIF_ID = 3;

    public static int OPSM_ID = 4;

    /*
    Debug Constant
     */
        /* Print debug information */
    public static boolean debug = false;

    /*
    Constants from PicturePane
     */

    /**
     * The location of a click was below and/or to the right of the displayed
     * matrix.
     */
    public static int NOWHERE = -1;

    /**
     * The location of the click was inside the matrix, in one of the samples.
     */
    public static int IN_MATRIX = 0;

    /**
     * The location of the clisk was in the region of the chip names, above the
     * matrix.
     */
    public static int IN_CHIPS = 1;

    /**
     * The location of the click was in the region of the gene names, to the
     * left of the matrix.
     */
    public static int IN_GENES = 2;

    /**
     * The location of the click was in the top left corner, between gene and
     * chip names. ("clear area")
     */
    public static int OUTSIDE = 3;


    // Constant from the Filter class. Filter related constants

    public static String FILTER_WINDOW_DEFAULT_NR_OUTPUT_BICLUSTERS = "100";

    public static int FILTER_WINDOW_DEFAULT_ALLOWED_OVERLAP = 25;

    public static String FILTER_WINDOW_APPLY = "start_filter";

    public static String FILTER_WINDOW_CANCEL = "cancel_filter";

    public static String FILTER_WINDOW_SELECT_BC_LIST = "select_BC_list";

    // Constants related with the Preprocessing the data

    // to have a clean interface:
    public static final String PREPROCESS_DATA_WINDOW_USE_DEFAULT = "default_preprocessing";

    public static final String PREPROCESS_DATA_WINDOW_APPLY = "apply_preprocessing";

    public static final String PREPROCESS_DATA_WINDOW_CANCEL = "cancel_preprocessing";

    public static final String PREPROCESS_DATA_WINDOW_RATIO = "compute_ratio";

    public static final String PREPROCESS_DATA_WINDOW_LOGARITHM = "compute_logarithm";

    public static final String PREPROCESS_DATA_WINDOW_SET_LOGARITHM_BASE = "set_log_base";

    public static final String PREPROCESS_DATA_WINDOW_NORMALIZE_GENES = "normalize_genes";

    public static final String PREPROCESS_DATA_WINDOW_NORMALIZE_CHIPS = "normalize_chips";

    public static final String PREPROCESS_DATA_WINDOW_CHOOSE_NORMALIZATION_SCHEME = "choose_norm_scheme";

    public static final String PREPROCESS_DATA_WINDOW_DISCRETIZE = "discretize";

    public static final String PREPROCESS_DATA_WINDOW_ONES_PERCENTAGE = "ones_percentage";

    public static final String PREPROCESS_DATA_WINDOW_CHOOSE_DISCRETIZATION_SCHEME = "choose_discr_scheme";

    // Search related constants

    public static String SEARCH_WINDOW_APPLY = "start_search";

    public static String SEARCH_WINDOW_CANCEL = "cancel_search";

    public static String SEARCH_WINDOW_SEARCH_AND = "search_and";

    public static String SEARCH_WINDOW_SEARCH_OR = "search_or";

    public static String SEARCH_WINDOW_SELECT_BC_LIST = "select_BC_list";

    // Gene Pair analysis constants
    public static final String GENE_PAIR_ANALYSIS_WINDOW_APPLY = "gpa_apply";

    public static final String GENE_PAIR_ANALYSIS_WINDOW_CANCEL = "gpa_cancel";

    public static final String GENE_PAIR_ANALYSIS_WINDOW_BY_COOCURRENCE = "gpa_by_coocurrence";

    public static final String GENE_PAIR_ANALYSIS_WINDOW_BY_COMMON_CHIPS = "gpaby_common_chips";

    public static final String GENE_PAIR_ANALYSIS_WINDOW_SELECT_BC_LIST = "gpa_select_bc_list";

    public static final int GENE_PAIR_ANALYSIS_WINDOW_DEFAULT_SCORE_VALUE = 1;

    // Load Data constants

    public static final String LOAD_DATA_WINDOW_APPLY = "ld_apply";

    public static final String LOAD_DATA_WINDOW_CANCEL = "ld_cancel";

    public static final String LOAD_DATA_WINDOW_BROWSE_DATA_MATRIX_FILE = "ld_browse_file_main";


    // Pre processor constants

    public static final int PREPROCESS_OPTIONS_NORMALIZATION_MEAN_CENTRING = 1;
    public static final int PREPROCESS_OPTIONS_NORMALIZATION_MIXTURE = 4;
    public static final int PREPROCESS_OPTIONS_NORMALIZATION_IT = 5;
    public static final int PREPROCESS_OPTIONS_DISCRETIZATION_CHANGE = 4;
    public static final int PREPROCESS_OPTIONS_DISCRETIZATION_UP = 1;
    public static final int PREPROCESS_OPTIONS_DISCRETIZATION_DOWN = 2;
    public static final int PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED = 3;

}
