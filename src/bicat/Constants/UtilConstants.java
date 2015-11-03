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
 * Class which contains all the constanst used by graphical tools.
 * There is a difference with MethodConstants
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class UtilConstants {

    // Action Manager Constants
    // BicatGUI:
    public static final String MAIN_SET_FILE_OFFSET = "set_file_offset";

    public static final String MAIN_QUIT = "quit";

    public static final String MAIN_LOAD_TWO_INPUT_FILES = "load_data_ctrl";
    public static final String MAIN_LOAD_ONE_INPUT_FILE = "load_data";
    public static final String MAIN_LOAD_BICLUSTERS = "load_bcs";

    public static final String MAIN_SAVE_PREPROCESSED = "save_preprocessed";
    public static final String MAIN_SAVE_ALL_BICLUSTERS = "save_all_bcs";
    public static final String MAIN_SAVE_SELECTED_BICLUSTER = "save_selected_bc";
    public static final String MAIN_SAVE_SEARCH_RESULT_BICLUSTERS = "save_search_bcs";
    public static final String MAIN_EXPORT = "export...";
    public static final String MAIN_EXPORT_ALL_BICLUSTERS = "save_all_bcs_human";
    public static final String MAIN_EXPORT_ALL_SEARCH_RESULT_BICLUSTERS = "save_search_bc_human";

    public static final String MAIN_SET_DISCRETIZATION_THRESHOLD = "set_discretization_threshold";
    public static final String MAIN_SET_LOGARITHM_BASE = "set_logarithm_base";

    public static final String MAIN_PREPROCESS = "preprocess";
    public static final String MAIN_SET_PREPROCESSED = "set_preprocessing";
    public static final String MAIN_SET_EXTENDED = "set_extended";
    public static final String MAIN_COMPUTE_RATIO = "compute_ratio";
    public static final String MAIN_MAKE_OTHER_LOG = "make_log_other";

    public static final String MAIN_NORMALIZATION_STANDARD = "normalization_std";
    public static final String MAIN_NORMALIZATION_MIXTURE_MODEL = "normalization_anja";
    public static final String MAIN_NORMALIZATION_PHZ = "normalization_phz";
    public static final String MAIN_NORMALIZATION_HUBER = "normalization_huber";

    public static final String MAIN_DISCRETIZE_MATRIX = "discretize_matrix";

    public static final String MAIN_RUN_BENDORFRECHRELOADED_BICLUSTERING = "bendorfrechreloaded";

    public static final String MAIN_RUN_BIMAX = "bicluster_bimax";   // changed 22.02.2005
    public static final String MAIN_RUN_CC = "bicluster_cc";         // added 22.02.2005
    public static final String MAIN_RUN_OPSM = "bicluster_opsm";
    public static final String MAIN_RUN_ISA = "bicluster_isa";
    public static final String MAIN_RUN_XMOTIF = "bicluster_xmotif";
    public static final String MAIN_RUN_HCL = "cluster_hcl";
    public static final String MAIN_RUN_KMEANS = "cluster_kmeans";

    public static final String MAIN_RUN_BICLUSTERING_PATTERN = "extended_bicluster";

    public static final String MAIN_SEARCH_BICLUSTERS = "search";
    public static final String MAIN_FILTER_BICLUSTERS = "filter_biclusters";
    public static final String MAIN_EXTEND_BICLUSTERS = "extend_biclusters";
    public static final String MAIN_GET_BICLUSTER_INFORMATION = "info_bc";
    public static final String MAIN_SORT_BICLUSTERS_BY_SIZE = "sort_by_size";

    public static final String MAIN_GENE_PAIR_ANALYSIS = "do_gpa_analysis";
    public static final String MAIN_GENE_ANNOTATION_ANALYSIS = "do_annotation_analysis";
    public static final String MAIN_GENE_NETWORK_ANALYSIS = "do_network_analysis";

    public static final String MAIN_INFO_HELP = "info_help";
    public static final String MAIN_INFO_LICENSE = "info_license";
    public static final String MAIN_INFO_ABOUT = "info_about";

    // GraphicPane
    public static final String GRAPHPANE_ZOOM_25 = "zoom_25";
    public static final String GRAPHPANE_ZOOM_50 = "zoom_50";
    public static final String GRAPHPANE_ZOOM_75 = "zoom_75";
    public static final String GRAPHPANE_ZOOM_100 = "zoom_100";
    public static final String GRAPHPANE_ZOOM_150 = "zoom_150";
    public static final String GRAPHPANE_ZOOM_200 = "zoom_200";

    // PicturePane
    public static final String PICTUREPANE_ZOOM_25 = "zoom_25";
    public static final String PICTUREPANE_ZOOM_50 = "zoom_50";
    public static final String PICTUREPANE_ZOOM_75 = "zoom_75";
    public static final String PICTUREPANE_ZOOM_100 = "zoom_100";
    public static final String PICTUREPANE_ZOOM_150 = "zoom_150";
    public static final String PICTUREPANE_ZOOM_200 = "zoom_200";

    public static final String PICTUREPANE_LIMIT_100 = "limit_100";
    public static final String PICTUREPANE_LIMIT_500 = "limit_500";
    public static final String PICTUREPANE_LIMIT_1000 = "limit_1000";
    public static final String PICTUREPANE_LIMIT_5000 = "limit_5000";
    public static final String PICTUREPANE_LIMIT_TOGGLE = "limit_toggle";
    public static final String PICTUREPANE_FIT_2_FRAME = "limit_fit2pp";

    // Dataset constants

    public static int BICLUSTER_LIST = 0; // "Bicluster Results";

    public static int SEARCH_LIST = 1; // "Search Results";

    public static int FILTER_LIST = 2; // "Filter Results";

    public static int CLUSTER_LIST = 3; // "Cluster Results";

    // Table Sorter constants

    public static final int DESCENDING = -1;
    public static final int NOT_SORTED = 0;
    public static final int ASCENDING = 1;
}
