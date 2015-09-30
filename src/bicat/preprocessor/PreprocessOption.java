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
@Data
public class PreprocessOption {

    private boolean do_compute_ratio;
    private boolean do_compute_logarithm;
    private boolean do_normalize;
    private boolean do_normalize_genes;
    private boolean do_normalize_chips;
    private boolean do_discretize;
    private int logarithmBase;
    private int normalizationScheme;
    private int discretizationScheme;
    private String discretizationMode;
    private int onesPercentage;
    private int defaultOnesPercentage = 10;
    private double[] discretizationThreshold;
    private int defaultLogBase = 2;
    private double defaultDiscrThr = 2.0;
    private int[][] discreteMatrix;
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
    public int[][] getDiscreteMatrix() {
        return discreteMatrix;
    }

    public float[][] getPreprocessedMatrix() {
        return preprocessedMatrix;
    }

    public void setDiscreteMatrix(int[][] data) {
        discreteMatrix = new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                discreteMatrix[i][j] = data[i][j];
    }


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

    public void setDiscretizationScheme(int i) {
        discretizationScheme = i;
    }

    public void resetDiscretizationScheme() {
        discretizationScheme = MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED;
    }

    public void setDiscretizationThreshold(double[] thr) {
        discretizationThreshold = thr;
    }
    

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

    public void setDiscretizationMode(String discretizationMode) {
        this.discretizationMode = discretizationMode;

    }

}
