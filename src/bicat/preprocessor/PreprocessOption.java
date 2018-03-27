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

package bicat.preprocessor;

import bicat.Constants.MethodConstants;
import lombok.Data;

import java.util.Arrays;

/**
 * Class that holds  the variable values for the preprocessing options.
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="do_compute_ratio"
 */
/**
 * @return
 * @uml.property  name="do_compute_logarithm"
 */
/**
 * @return
 * @uml.property  name="do_normalize"
 */
/**
 * @return
 * @uml.property  name="do_normalize_genes"
 */
/**
 * @return
 * @uml.property  name="do_normalize_chips"
 */
/**
 * @return
 * @uml.property  name="do_discretize"
 */
/**
 * @return
 * @uml.property  name="logarithmBase"
 */
/**
 * @return
 * @uml.property  name="normalizationScheme"
 */
/**
 * @return
 * @uml.property  name="discretizationScheme"
 */
/**
 * @return
 * @uml.property  name="discretizationMode"
 */
/**
 * @return
 * @uml.property  name="onesPercentage"
 */
/**
 * @return
 * @uml.property  name="defaultOnesPercentage"
 */
/**
 * @return
 * @uml.property  name="discretizationThreshold"
 */
/**
 * @return
 * @uml.property  name="defaultLogBase"
 */
/**
 * @return
 * @uml.property  name="defaultDiscrThr"
 */
/**
 * @param do_compute_ratio
 * @uml.property  name="do_compute_ratio"
 */
/**
 * @param do_compute_logarithm
 * @uml.property  name="do_compute_logarithm"
 */
/**
 * @param do_normalize
 * @uml.property  name="do_normalize"
 */
/**
 * @param do_normalize_genes
 * @uml.property  name="do_normalize_genes"
 */
/**
 * @param do_normalize_chips
 * @uml.property  name="do_normalize_chips"
 */
/**
 * @param do_discretize
 * @uml.property  name="do_discretize"
 */
/**
 * @param defaultOnesPercentage
 * @uml.property  name="defaultOnesPercentage"
 */
/**
 * @param defaultLogBase
 * @uml.property  name="defaultLogBase"
 */
/**
 * Class that holds  the variable values for the preprocessing options. <p> Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author  Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="do_compute_ratio"
 */
/**
 * @return
 * @uml.property  name="do_compute_logarithm"
 */
/**
 * @return
 * @uml.property  name="do_normalize"
 */
/**
 * @return
 * @uml.property  name="do_normalize_genes"
 */
/**
 * @return
 * @uml.property  name="do_normalize_chips"
 */
/**
 * @return
 * @uml.property  name="do_discretize"
 */
/**
 * @return
 * @uml.property  name="logarithmBase"
 */
/**
 * @return
 * @uml.property  name="normalizationScheme"
 */
/**
 * @return
 * @uml.property  name="discretizationScheme"
 */
/**
 * @return
 * @uml.property  name="discretizationMode"
 */
/**
 * @return
 * @uml.property  name="onesPercentage"
 */
/**
 * @return
 * @uml.property  name="defaultOnesPercentage"
 */
/**
 * @return
 * @uml.property  name="discretizationThreshold"
 */
/**
 * @return
 * @uml.property  name="defaultLogBase"
 */
/**
 * @return
 * @uml.property  name="defaultDiscrThr"
 */
/**
 * @param do_compute_ratio
 * @uml.property  name="do_compute_ratio"
 */
/**
 * @param do_compute_logarithm
 * @uml.property  name="do_compute_logarithm"
 */
/**
 * @param do_normalize
 * @uml.property  name="do_normalize"
 */
/**
 * @param do_normalize_genes
 * @uml.property  name="do_normalize_genes"
 */
/**
 * @param do_normalize_chips
 * @uml.property  name="do_normalize_chips"
 */
/**
 * @param do_discretize
 * @uml.property  name="do_discretize"
 */
/**
 * @param defaultOnesPercentage
 * @uml.property  name="defaultOnesPercentage"
 */
/**
 * @param defaultLogBase
 * @uml.property  name="defaultLogBase"
 */
/**
 * @param defaultDiscrThr
 * @uml.property  name="defaultDiscrThr"
 */
@Data
public class PreprocessOption {

    /**
	 * @uml.property  name="do_compute_ratio"
	 */
    private boolean do_compute_ratio;
    /**
	 * @uml.property  name="do_compute_logarithm"
	 */
    private boolean do_compute_logarithm;
    /**
	 * @uml.property  name="do_normalize"
	 */
    private boolean do_normalize;
    /**
	 * @uml.property  name="do_normalize_genes"
	 */
    private boolean do_normalize_genes;
    /**
	 * @uml.property  name="do_normalize_chips"
	 */
    private boolean do_normalize_chips;
    /**
	 * @uml.property  name="do_discretize"
	 */
    private boolean do_discretize;
    /**
	 * @uml.property  name="logarithmBase"
	 */
    private int logarithmBase;
    /**
	 * @uml.property  name="normalizationScheme"
	 */
    private int normalizationScheme;
    /**
	 * @uml.property  name="discretizationScheme"
	 */
    private int discretizationScheme;
    /**
	 * @uml.property  name="discretizationMode"
	 */
    private String discretizationMode;
    /**
	 * @uml.property  name="onesPercentage"
	 */
    private int onesPercentage;
    /**
	 * @uml.property  name="defaultOnesPercentage"
	 */
    private int defaultOnesPercentage = 10;
    /**
	 * @uml.property  name="discretizationThreshold"
	 */
    private double[] discretizationThreshold;
    /**
	 * @uml.property  name="defaultLogBase"
	 */
    private int defaultLogBase = 2;
    /**
	 * @uml.property  name="defaultDiscrThr"
	 */
    private double defaultDiscrThr = 2.0;
    /**
	 * @uml.property  name="discreteMatrix"
	 */
    private int[][] discreteMatrix;
    /**
	 * @uml.property  name="preprocessedMatrix"
	 */
    private float[][] preprocessedMatrix;
    // ===========================================================================

    /**
     * Constructor: assumes that the default setting is to be used (common for
     * oligonucleids microarrays: (eventually) normalize, discretize).
     */
    public PreprocessOption(String def) {

        if (def.equals("default")) {
            do_compute_ratio = false; // true;
            do_compute_logarithm = false; // true;
            do_normalize = true;
            do_discretize = true;

            logarithmBase = defaultLogBase;
            normalizationScheme = MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MEAN_CENTRING;
            discretizationScheme = MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED;
            discretizationThreshold = new double[]{defaultDiscrThr};
        }
    }

    /**
     * Constructor: all options are reset (set to false). All user desired
     * preprocessing settings need to be set manually.
     */
    public PreprocessOption() {

        do_compute_ratio = false;
        do_compute_logarithm = false;
        do_normalize = false;
        do_discretize = false;

        logarithmBase = defaultLogBase;
        normalizationScheme = MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MEAN_CENTRING;
        discretizationScheme = MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP;
        discretizationThreshold = new double[]{defaultDiscrThr};
    }

    /**
     * Constructor: create a copy of the parameter <code>po</code> object.
     */
    public PreprocessOption(PreprocessOption po) {

        do_compute_ratio = po.do_compute_ratio;
        do_compute_logarithm = po.do_compute_logarithm;
        do_normalize = po.do_normalize;
        do_discretize = po.do_discretize;

        logarithmBase = po.logarithmBase;
        normalizationScheme = po.normalizationScheme;
        discretizationScheme = po.discretizationScheme;
        discretizationThreshold = po.discretizationThreshold;
    }

    // ===========================================================================
    public void setComputeRatio() {
        do_compute_ratio = true;
    }

    public void resetComputeRatio() {
        do_compute_ratio = false;
    }

    // ===========================================================================
    /**
	 * @return
	 * @uml.property  name="discreteMatrix"
	 */
    public int[][] getDiscreteMatrix() {
        return discreteMatrix;
    }

    /**
	 * @return
	 * @uml.property  name="preprocessedMatrix"
	 */
    public float[][] getPreprocessedMatrix() {
        return preprocessedMatrix;
    }

    /**
	 * @param  data
	 * @uml.property  name="discreteMatrix"
	 */
    public void setDiscreteMatrix(int[][] data) {
        discreteMatrix = new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                discreteMatrix[i][j] = data[i][j];
    }


    /**
	 * @param  data
	 * @uml.property  name="preprocessedMatrix"
	 */
    public void setPreprocessedMatrix(float[][] data) {
        preprocessedMatrix = new float[data.length][data[0].length];
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                preprocessedMatrix[i][j] = data[i][j];
    }

    // ===========================================================================
    public void setComputeLogarithm() {
        do_compute_logarithm = true;
    }

    public void resetComputeLogarithm() {
        do_compute_logarithm = false;
    }

    /**
	 * @param  i
	 * @uml.property  name="logarithmBase"
	 */
    public void setLogarithmBase(int i) {
        logarithmBase = i;
    }

    public void resetLogarithmBase() {
        logarithmBase = defaultLogBase;
    }

    // ===========================================================================
    public void setNormalization() {
        do_normalize = true;
    }

    public void resetNormalization() {
        do_normalize = false;
    }

    public void setGeneNormalization() {
        do_normalize_genes = true;
    }

    public void resetGeneNormalization() {
        do_normalize_genes = false;
    }

    public void setChipNormalization() {
        do_normalize_chips = true;
    }

    public void resetChipNormalization() {
        do_normalize_chips = false;
    }

    /**
	 * @param  i
	 * @uml.property  name="normalizationScheme"
	 */
    public void setNormalizationScheme(int i) {
        normalizationScheme = i;
    }

    public void resetNormalizationScheme() {
        normalizationScheme = MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MEAN_CENTRING;
    }

    // ===========================================================================
    public void setDiscretizationTrue() {
        do_discretize = true;
    }

    public void resetDiscretization() {
        do_discretize = false;
    }

    /**
	 * @param  i
	 * @uml.property  name="discretizationScheme"
	 */
    public void setDiscretizationScheme(int i) {
        discretizationScheme = i;
    }

    public void resetDiscretizationScheme() {
        discretizationScheme = MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED;
    }

    /**
	 * @param  thr
	 * @uml.property  name="discretizationThreshold"
	 */
    public void setDiscretizationThreshold(double[] thr) {
        discretizationThreshold = thr;
    }
    

    /**
	 * @param  onesPercentage
	 * @uml.property  name="onesPercentage"
	 */
    public void setOnesPercentage(int onesPercentage) {
        this.onesPercentage = onesPercentage;
    }

    public void resetOnesPercentage() {
        onesPercentage = defaultOnesPercentage;
    }


    // ===========================================================================

    /**
     * String representation of the preprocess settings.
     */
    public String toString() {
        StringBuffer sbuff = new StringBuffer();

        sbuff.append("\nPreprocessing Options:\n");
        sbuff.append("  Compute ratio: " + do_compute_ratio + "\n");
        sbuff.append("  Compute log  : " + do_compute_logarithm); // +"
        // ("+logarithmBase+")\n");
        if (do_compute_logarithm)
            sbuff.append(" (base = " + logarithmBase + ")\n");
        else
            sbuff.append("\n");

        String nS = NStoString(normalizationScheme);
        sbuff.append("  Normalize     : " + do_normalize + " (" + nS + ")\n");

        String dM = discretizationMode;
        sbuff.append("  Discretization Mode     : " + dM + "\n");

        String dS = DStoString(discretizationScheme);
        sbuff.append("  Discretize      : " + do_discretize + " (" + dS + ", "
                + Arrays.toString(discretizationThreshold) + ")\n");
        sbuff.append("\n");

        return sbuff.toString();
    }

    String NStoString(int a) {
        switch (a) {
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_IT:
                return "Info Theory"; // Norm";
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MEAN_CENTRING:
                return "Mean Centring 0,1"; // Norm";
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MIXTURE:
                return "Mixture"; // Norm";
            default:
                System.out.println("Undefined Normalization Scheme!\n");
                return null;
        }
    }

    String DStoString(int a) {
        switch (a) {
            case MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED:
                return "Complementary pattern";
            // return "Coexpression Discr";
            case MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_DOWN:
                return "Down-regulated genes";
            // return "Under-expression Discr";
            case MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP:
                return "Up-regulated genes";
            // return "Over-expression Discr";
            case MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_CHANGE:
                return "Changed genes";
            default:
                System.out.println("Undefined Discretization Scheme!\n");
                return null;
        }
    }

    /**
	 * @param  discretizationMode
	 * @uml.property  name="discretizationMode"
	 */
    public void setDiscretizationMode(String discretizationMode) {
        this.discretizationMode = discretizationMode;

    }

}
