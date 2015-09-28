/*
 *                                BiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                Copyright (c) 2015 Taghi Aliyev
 *
 *                                This file is part of BiCat.
 *
 *                                BiCat is a free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                BiCat is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with BiCat.  If not, see <http://www.gnu.org/licenses/>.
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
import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Dataset;
import bicat.gui.BicatGui;
import bicat.util.BicatError;
import bicat.util.PreUtil;
import lombok.Data;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * Preprocessor is used to read data from files and perform initial steps such
 * as normalization, log operations and discretization of the original data
 * matrix.
 * <p>
 * The methods in this class are generally called from the governing
 * <code>bicat.gui.BicatGui</code> object. The <code>Preprocessor</code> will
 * keep a copy of the original data at all times, allowing the user to
 * experiment with different preprocessing steps before running any clustering
 * algorithms.
 * <p>
 * Once the files have been read, the files need to be preprocessed, so that the
 * biclustering can be run on it.
 * <p>
 *
 * @author A. Prelic, D.Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 * @version 0.2
 */
@Data
public class Preprocessor {

    private BicatGui owner;

    private PreprocessOption preOption; // maintains the current

    private boolean dataContainsNegValues;

    // preprocessing setting.

    /**
     * Data from the main data file.
     */
    private float[][] rawData;

    private float[][] preprocessedData;

    private int[][] discretizedData;

    /**
     * Contains gene names, as read from main data file.<br>
     * <p>
     * When the data matrix has more than a single column header (for genes,
     * normally) the last header column is assumed to contain the gene labels.
     *
     * @todo WISH FEATURE (AP): Relax Column / Row Offset requirement (allow the
     * user to choose what is the gene column in the matrix).
     */
    private String[] geneNames;

    /**
     * Contains the labels of the <code>fileOffset</code> column headers.
     */
    private Vector labels;

    /**
     * Contains the column header information.
     */
    private Vector labelArrays;

    /**
     * Contains chip names, as read from main data file (chip names are read
     * from the header row of the input file; the first chip name follows at
     * offset <code>fileOffset</code>).<br>
     */
    private String[] chipNames;

    /**
     * Chip names with dependencies (no control chip).
     */
    private String[] workChipNames;

    /**
     * Chip to Control mappings.<br>
     * <p>
     * Element <code>i</code> is the index of the control value for chip
     * <code>i</code>, and -1 if chip <code>i</code> is a control chip.
     */
    private int[] dependencies;

    private float MISSING_VALUE = 0;

    private int DISCRETE_MISSING_VALUE = 0; // 9;

    private boolean chipToControlsLoaded = false;

    private UtilFunctionalities utilEngine;
    // ===========================================================================

    /**
     * Default constructor, initializes some values.
     */
    public Preprocessor(UtilFunctionalities engine) {

        rawData = null;
        preprocessedData = null;
        discretizedData = null;

        labels = null;
        labelArrays = null;

        preOption = new PreprocessOption();
        this.utilEngine = engine;
        this.utilEngine.setPre(this);
    }

    public Preprocessor(BicatGui o) {
        this(o.getUtilEngine());
        owner = o;
    }

    /**
     * Reads the input data file and saves it locally, for management and
     * visualization purposes.<br>
     * <p>
     * Input file format must be as follows:
     * <ul>
     * <li>The first line contains <code>offset</code> header columns, followed
     * by the chip names (all separated by tabs; whitespaces are also allowed);
     * <li>Whitespace is defined as exactly one tab or space character;
     * <li>All names and data values may <b>not</b> contain any whitespace;
     * <li>Two consecutive whitespaces mark a missing data value. These
     * omissions are duly noted by the program.
     * </ul>
     */
    public Dataset readMainDataFile(File inputFile, int colOffset,
                                    int rowOffset) throws FileOffsetException {
        // tab-separated input file
        try {

            System.out.println("\nReading datafile...");
            // System.out.println("File Offset: " + colOffset);

            FileReader fr = new FileReader(inputFile);
            LineNumberReader lnr = new LineNumberReader(fr);

            Vector dataVector = new Vector();
            Vector geneNameVector = new Vector();

            String[] fragments;
            float[] data;
            dataContainsNegValues = false;

            // each line: replace whitespaces with blank space character; split
            // the line
            String s = lnr.readLine(); // read top line with chip names
            s = s.replaceAll(" ", ""); // delete all spaces
            s = s.replace((char) 9, (char) 32); // replace tabs with spaces
            fragments = s.split(" ", -1); // ... and split line along its
            // spaces, reading all chip names

            // initialize the header columns information
            labels = new Vector(colOffset);
            for (int i = 0; i < colOffset; i++)
                labels.add(fragments[i]);
            labelArrays = new Vector(colOffset);
            for (int i = 0; i < colOffset; i++)
                labelArrays.add(new Vector());

            if (colOffset > 0) {
                chipNames = new String[fragments.length - colOffset];
                for (int i = colOffset; i < fragments.length; i++) {
                    chipNames[i - colOffset] = fragments[i];
                }
            } else {
                chipNames = new String[fragments.length];
                for (int i = 0; i < fragments.length; i++) {
                    chipNames[i] = "Chip " + i + 1;
                }
            }

            for (int i = 1; i < rowOffset; i++)
                lnr.readLine();

            if (MethodConstants.debug)
                for (int i = 0; i < chipNames.length; i++)
                    System.out.println(chipNames[i]);

            s = lnr.readLine(); // read first line of real data

            while ((null != s) && (0 != s.length())) {

                // each line: replace whitespaces with blank space character;
                // split the line
                s = s.trim();
                // char[] ca = s.toCharArray();
                // char c = ca[0];
                // if (c == (char) 32) {
                // BicatError.spaceError();
                // }
                s = s.replace((char) 9, (char) 32);
                fragments = s.split(" ", -1);

                data = new float[fragments.length - colOffset]; // initialize
                // array for next line of data values..
                dataVector.add(data); // .. and append said array to the
                // dataVector

                // update column headers...
                for (int i = 0; i < colOffset; i++)
                    ((Vector) labelArrays.get(i)).add(fragments[i]);

                // assume the last header column are the gene names
                geneNameVector.add(fragments[colOffset - 1]);
                // value at index offset in line is gene name, insert into
                // geneNameVector

                // all following values are samples
                for (int i = colOffset; i < fragments.length; i++) {
                    if (0 == fragments[i].length())
                        data[i - colOffset] = MISSING_VALUE;// Float.NaN;
                    else if (null == fragments[i])
                        data[i - colOffset] = MISSING_VALUE;// Float.NaN;
                    else
                        data[i - colOffset] = Float.parseFloat(fragments[i]);
                }

                s = lnr.readLine();
            }

            // trim empty elements off all the vectors
            dataVector.trimToSize();
            geneNameVector.trimToSize();

            geneNames = new String[geneNameVector.size()];

            rawData = new float[dataVector.size()][chipNames.length];
            for (int i = 0; i < dataVector.size(); i++) {
                data = (float[]) dataVector.get(i);
                geneNames[i] = (String) geneNameVector.get(i);
                for (int j = 0; j < chipNames.length; j++)
                    rawData[i][j] = data[j];
            }
            // preprocessedData = rawData;

            preprocessedData = new float[rawData.length][rawData[0].length];
            for (int i = 0; i < rawData.length; i++)
                for (int j = 0; j < rawData[0].length; j++)
                    preprocessedData[i][j] = rawData[i][j];

            // discretizedData = new int[dataVector.size()][chipNames.length];
            // originalData = rawData;

            workChipNames = chipNames;

            lnr.close();
            return new Dataset(getOriginalData(), getOriginalData(), getDiscreteData(),
                    getGeneNames(), getChipNames(),
                    getWorkingChipNames(),
                    getHeaderColumnLabels(), getHeaderColumns());
        } catch (FileNotFoundException e) {
            BicatError.errorMessage(e, owner, true, "File not found");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new FileOffsetException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ************************************************************************
    // //
    // * * //
    // * Pre-processor: Preprocess Data... * //
    // * * //
    // ************************************************************************
    // //

    public void resetData() {

        preprocessedData = new float[rawData.length][rawData[0].length];
        for (int i = 0; i < rawData.length; i++)
            for (int j = 0; j < rawData[0].length; j++)
                preprocessedData[i][j] = rawData[i][j];

        // preprocessedData = rawData; //originalData;
        discretizedData = null;
    }

    public void resetData(float[][] d) {
        rawData = new float[d.length][d[0].length];
        preprocessedData = new float[d.length][d[0].length];
        for (int i = 0; i < d.length; i++)
            for (int j = 0; j < d[0].length; j++) {
                rawData[i][j] = d[i][j]; // ???
                preprocessedData[i][j] = d[i][j];
            }
        discretizedData = null;
    }

    public boolean extended = false;

    // ===========================================================================
    public void preprocessData(PreprocessOption po) {

        preOption = new PreprocessOption(po); // all false!
        boolean negValues = false;

        // check if the data to be preprocessed has
        // negative values
        for (int i = 0; i < preprocessedData[0].length; i++)
            for (int j = 0; j < preprocessedData.length; j++)
                if (preprocessedData[j][i] <= 0) {
                    negValues = true;
                }

        // if(po.do_compute_ratio) computeRatio();
        if (po.isDo_compute_logarithm()) {
            if (negValues) {
                JOptionPane
                        .showMessageDialog(
                                null,
                                "The data contains negative values\nThe logarithm cannot be calculated.\nMaybe the data are already logarithmized");
            } else {
                computeLogarithm(po.getLogarithmBase());
            }
        }

        if (po.isDo_normalize()) {
            if (po.isDo_normalize_genes())
                normalize(po.getNormalizationScheme(), 0);
            if (po.isDo_normalize_chips())
                normalize(po.getNormalizationScheme(), 1);
        }

        if (po.isDo_discretize() && po.getDiscretizationMode().equalsIgnoreCase("threshold")) {
            System.out.println("Discretize by threshold");
            if (po.getDiscretizationScheme() == MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED) {
                extended = true;
                discretizeByThresholdExtended(po.getDiscretizationThreshold());
            } else {
                extended = false;
                discretizeByThreshold(po.getDiscretizationThreshold(),
                        po.getDiscretizationScheme());
            }
            preOption.setDiscreteMatrix(discretizedData);
        }

        if (po.isDo_discretize() && po.getDiscretizationMode().equalsIgnoreCase("onesPercentage")) {
            System.out.println("Discretize by onesPercentage");
            System.out.println("po.onesPercentage: " + po.getOnesPercentage());
            if (po.getDiscretizationScheme() == MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP)
                po.setDiscretizationThreshold(computeThreshold(po.getOnesPercentage()));
            if (po.getDiscretizationScheme() == MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_DOWN)
                po.setDiscretizationThreshold(computeThreshold(100 - po.getOnesPercentage()));
            if (po.getDiscretizationScheme() == MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_CHANGE)
                po.setDiscretizationThreshold(computeThreshold(po.getOnesPercentage()));

            discretizeByThreshold(po.getDiscretizationThreshold(),
                    po.getDiscretizationScheme());
            preOption.setDiscreteMatrix(discretizedData);
        }

        // set the matrices...

        preOption.setPreprocessedMatrix(preprocessedData);
    }

    /**
     * Compute the logarithm of the data.
     * <p>
     * PRE-CONDITION: preprocessedData must be initialized.
     */
    public void computeLogarithm(int base) {

        for (int i = 0; i < preprocessedData[0].length; i++)
            for (int j = 0; j < preprocessedData.length; j++)
                if (preprocessedData[j][i] != Float.NaN
                        && preprocessedData[j][i] != 0)
                    preprocessedData[j][i] = (float) (Math
                            .log((double) preprocessedData[j][i]) / base);
    }

    // if(po.do_normalize) normalize(po.normalizationScheme);

    /**
     * Compute the normalization on the data.
     * <p>
     * PRE-CONDITION: preprocessedData must be initialized.
     */
    public void normalize(int scheme) {

        switch (scheme) {
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_IT:
                // normalize_InfoTheory(preprocessedData);
                break;
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MIXTURE:
                // normalize_Mixture(preprocessedData);
                break;
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MEAN_CENTRING:
                normalize_MeanCentring(preprocessedData, false);
                break;
            default:
                break;
        }
    }

    // static boolean dataPreprocessed_Genes = false;

    public void normalize(int scheme, int direction) {

        switch (scheme) {
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_IT:
                // normalize_InfoTheory(preprocessedData);
                break;
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MIXTURE:
                // normalize_Mixture(preprocessedData);
                break;
            case MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MEAN_CENTRING:
                if (direction == 0)
                    normalize_MeanCentring(preprocessedData);
                if (direction == 1)
                    normalize_MeanCentring(preprocessedData, true);
                break;
            default:
                break;
        }
    }

    public void normalize_MeanCentring(float[][] data) {

        if (MethodConstants.debug)
            System.out.println("D: Preprocessor.normalize_MeanCentring,GENES: "
                    + data.length + ", " + data[0].length);

        int gCnt = data.length;
        int cCnt = data[0].length;

        // correct the means (per gene)
        float[] row_means = new float[gCnt];
        for (int i = 0; i < gCnt; i++) {
            int sum = 0;
            for (int j = 0; j < cCnt; j++)
                sum += data[i][j];
            row_means[i] = sum / cCnt;
        }

        for (int i = 0; i < gCnt; i++)
            for (int j = 0; j < cCnt; j++)
                data[i][j] -= row_means[i];

        // compute the variance
        float[] row_vars = new float[gCnt];
        for (int i = 0; i < gCnt; i++)
            row_vars[i] = PreUtil.computeVariance(data[i]);

        for (int i = 0; i < gCnt; i++)
            for (int j = 0; j < cCnt; j++)
                if (row_vars[i] != 0)
                    // / ??? data[i][j] = data[i][j] / row_vars[i];
                    preprocessedData[i][j] = data[i][j] / row_vars[i];
                else
                    /* what to do? */;
    }

    // ... new, as of 22.03.2005:
    public void normalize_MeanCentring(float[][] data, boolean chips) {

        if (MethodConstants.debug)
            System.out.println("D: Preprocessor.normalize_MeanCentring,CHIPS: "
                    + data.length + ", " + data[0].length);

        int gCnt = data.length;
        int cCnt = data[0].length;

        // correct the means (per gene)
        float[] col_means = new float[cCnt];
        for (int i = 0; i < cCnt; i++) {
            int sum = 0;
            for (int j = 0; j < gCnt; j++)
                sum += data[j][i];
            col_means[i] = sum / gCnt;
        }

        for (int i = 0; i < cCnt; i++)
            for (int j = 0; j < gCnt; j++)
                data[j][i] -= col_means[i];

        // compute the variance
        float[] col_vars = new float[cCnt];
        for (int i = 0; i < cCnt; i++) {
            float[] column = new float[gCnt];
            for (int k = 0; k < gCnt; k++)
                column[k] = data[k][i];
            col_vars[i] = PreUtil.computeVariance(column);
        }

        for (int i = 0; i < cCnt; i++)
            for (int j = 0; j < gCnt; j++)
                if (col_vars[i] != 0)
                    preprocessedData[j][i] = data[j][i] / col_vars[i];
                else
                    /* what to do? */
                    ;
    }

    /**
     * Applies a threshold to discretize the processed data into 1s and 0s,
     * marking missing values with a <code>DISCRETE_MISSING_VALUE</code>. The
     * preprocessed data matrix is copied four times, to obtain the extended
     * discretized matrix.
     */
    public void discretizeByThresholdExtended(double[] threshold) {

        if (MethodConstants.debug)
            System.out.println("Threshold ist = " + Arrays.toString(threshold));

        // scheme ==
        // PreprocessOption.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED

        int gCnt = preprocessedData.length;
        int cCnt = preprocessedData[0].length;

        discretizedData = new int[2 * gCnt][2 * cCnt];

        for (int i = 0; i < 2 * gCnt; i++)
            for (int j = 0; j < 2 * cCnt; j++) {

                // determine the quadrant and discretize accordingly!
                // discretized matrix:
                // A | B
                // -----
                // C | D

                // A or D
                if ((i < gCnt && j < cCnt) || (i >= gCnt && j >= cCnt)) {

                    if (preprocessedData[i % gCnt][j % cCnt] == Float.NaN)
                        discretizedData[i][j] = DISCRETE_MISSING_VALUE;
                    else if (threshold[i % gCnt] < preprocessedData[i % gCnt][j % cCnt]) {
                        discretizedData[i][j] = 1;
                        if (owner != null && owner.isEnlargeContrast())
                            preprocessedData[i % gCnt][j % cCnt] += owner.getCONTRAST_VALUE(); // up-regulated
                    } else
                        discretizedData[i][j] = 0;
                }

                // B or C
                else if ((i >= gCnt && j < cCnt) || (i < gCnt && j >= cCnt)) {

                    if (preprocessedData[i % gCnt][j % cCnt] == Float.NaN)
                        discretizedData[i][j] = DISCRETE_MISSING_VALUE;
                    else if (
					/*
					 * preprocessedData[i % gCnt][j % cCnt] < 0 && threshold <
					 * Math.abs(preprocessedData[i % gCnt][j % cCnt])
					 */
                            (0 - threshold[i % gCnt]) > preprocessedData[i % gCnt][j % cCnt]) {
                        discretizedData[i][j] = -1;
                        if (owner != null && owner.isEnlargeContrast())
                            preprocessedData[i % gCnt][j % cCnt] -= owner.getCONTRAST_VALUE(); // down-regulated
                    } else
                        discretizedData[i][j] = 0;
                }
            }
    }

    /**
     * Applies a threshold to discretize the processed data into 1s and 0s,
     * marking missing values with a <code>DISCRETE_MISSING_VALUE</code>.
     */
    private void discretizeByThreshold(double[] threshold, int scheme) {


        // Threshold one works by setting 0's and 1's on each data value. So, no fold changes or anything like that
        // Is this correct? Not sure.
        // NOTE : IT DOES IT ON EACH GENE EXPRESSION VALUE SEPARATELY!!!
        // TODO : Might need to adopt/change this one as well.

        if (MethodConstants.debug)
            System.out.println("Threshold ist = " + Arrays.toString(threshold));

        int gCnt = preprocessedData.length;
        int cCnt = preprocessedData[0].length;

        discretizedData = new int[gCnt][cCnt];
        switch (scheme) {

            case MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP:

                for (int i = 0; i < gCnt; i++)
                    for (int j = 0; j < cCnt; j++)
                        if (preprocessedData[i][j] == Float.NaN)
                            discretizedData[i][j] = DISCRETE_MISSING_VALUE;
                        else {
                            double toUse;
                            if (threshold.length <= 1)
                                toUse = threshold[0];
                            else
                                toUse = threshold[i];
                            if (toUse < preprocessedData[i][j]) {
                                discretizedData[i][j] = 1;
                                if (owner != null && owner.isEnlargeContrast())
                                    preprocessedData[i][j] += owner.getCONTRAST_VALUE();
                            } else {
                                discretizedData[i][j] = 0;
                            }
                        }
                break;

            case MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_CHANGE:

                for (int i = 0; i < gCnt; i++)
                    for (int j = 0; j < cCnt; j++)
                        if (preprocessedData[i][j] == Float.NaN)
                            discretizedData[i][j] = DISCRETE_MISSING_VALUE;
                        else {
                            if (threshold[i] < Math.abs(preprocessedData[i][j])) {
                                discretizedData[i][j] = 1;
                                if (owner != null && owner.isEnlargeContrast())
                                    preprocessedData[i][j] += owner.getCONTRAST_VALUE();
                            } else {
                                discretizedData[i][j] = 0;
                                if (owner != null && owner.isEnlargeContrast())
                                    preprocessedData[i][j] -= owner.getCONTRAST_VALUE();
                            }
                        }
                break;

            case MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_DOWN:
                System.out.println();
                for (int i = 0; i < gCnt; i++)
                    for (int j = 0; j < cCnt; j++)
                        if (preprocessedData[i][j] == Float.NaN)
                            discretizedData[i][j] = DISCRETE_MISSING_VALUE;
                        else {
                            if ((0 - threshold[i]) > preprocessedData[i][j]) {
                                discretizedData[i][j] = -1;
                                if (owner != null && owner.isEnlargeContrast())
                                    preprocessedData[i][j] -= owner.getCONTRAST_VALUE();
                            } else {
                                discretizedData[i][j] = 0;
                            }
                        }
                break;

            default:
                break;
        }
    }

    // ************************************************************************
    // //
    // * * //
    // * Preprocessing Settings... * //
    // * * //
    // ************************************************************************
    // //

    public void setPreprocessRun(PreprocessOption po) {

        if (utilEngine.isDebug())
            System.out.println("What current Dataset is it? "
                    + utilEngine.getCurrentDatasetIdx());


        utilEngine.setPreprocessOptions(new PreprocessOption(po));

        if (po.isDo_compute_logarithm())
            utilEngine.setLogBaseSetting(po.getLogarithmBase());

        if (po.isDo_discretize()) {
            utilEngine.setPreprocessExtended(po.getDiscretizationScheme() == MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED);
        }

        // ....

       resetData(utilEngine.getCurrentDataset().getOrigData());
       preprocessData(po);
        System.out.println("Preprocessing with setup: \n" + po.toString());

        // ....
        if (utilEngine.getPreprocessOptions().getDiscretizationScheme() == MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED) {
            utilEngine.setPreprocessExtended(true);
            utilEngine.setDiscretizeExtended(true);
        }

        utilEngine.setDiscretizeComplete(true);
        utilEngine.setPreprocessComplete(true);

         // show results to user
        utilEngine.setData(getPreprocessedData());
        if (owner != null) {
            owner.getPp().setData(rawData);
            owner.readjustPictureSize();
            owner.getPp().repaint();
        }
        // ... update the just-processed data
        if (utilEngine.isPreprocessExtended())
            utilEngine.getCurrentDataset().setExtended();
        else
            utilEngine.getCurrentDataset().resetExtended();

        utilEngine.getCurrentDataset().setPreData(getPreprocessedData());

        if (po.isDo_discretize()) {
            utilEngine.getCurrentDataset().setDiscrData(getDiscreteData());
        } // binary,
        // or
        // discrete
        // currentDataset.setVisualDiscrData(pre.getDiscreteData()); // discrete
        utilEngine.getCurrentDataset().setPreprocessComplete();
    }

    private double[] computeThreshold(int onesPercentage) {

        int gCnt = preprocessedData.length;
        int cCnt = preprocessedData[0].length;
        int numberOfValues = gCnt * cCnt;
        int numOnes;
        double[] threshold = new double[gCnt];
        for (int i = 0; i < gCnt; i++) {
            float[] preprocessedData_1d = new float[cCnt + 1];

            // Calculate number of ones
            numOnes = Math.round(cCnt * onesPercentage / 100);

            int k = 0;
            for (int j = 0; j < cCnt; j++) {
                k++;
                preprocessedData_1d[k] = preprocessedData[i][j];
                // System.out.println(k);
            }
            // sort Array
            Arrays.sort(preprocessedData_1d);
            // Calculate threshold
            threshold[i] = preprocessedData_1d[cCnt - numOnes];

            if (MethodConstants.debug) {
                System.out.println("DEBUG discrization by percentage of ones:");
                System.out.println("numberOfValues: " + numberOfValues);
                System.out.println("onesPercentage: " + onesPercentage);
                System.out.println("Number of Ones: " + numOnes);
                System.out.println("Length of 1D-Array: "
                        + preprocessedData_1d.length);
                System.out.println("Position 1: " + preprocessedData_1d[1]);
                System.out.println("Position 100: " + preprocessedData_1d[100]);
                System.out.println("Position 1000: "
                        + preprocessedData_1d[1000]);
            }
        }
        return threshold;

    }


    // ===========================================================================

    /**
     * Called from bicat.gui.window.LoadData
     *
     * @throws Exception
     */
    public void loadData(File file, int colOffset, int rowOffset) throws Exception {

        try {

            if (utilEngine.isDebug())
                System.out.println("Opening: " + file.getName() + ".");

            System.out.println("Here!");

            System.out.println("Here, adding the dataset");
            // get the last loaded dataset be the current one ...
            utilEngine.addDataset(readMainDataFile(file, colOffset, rowOffset));

            // make sure BicaGUI has the data and display it in the PicturePane
            utilEngine.setData(utilEngine.getCurrentDataset().getOrigData());
            if (owner != null) {
                owner.getPp().setData(rawData);
                owner.readjustPictureSize();
                owner.getPp().repaint();

                owner.updateColumnHeadersMenu(); // does the same twice?

                owner.buildTree();
                owner.getTree().setSelectionPath(owner.getOriginalPath());
            }
        } catch (FileOffsetException ee) {
            BicatError.wrongOffsetError();
        }
    }

    private double[] computeThresholdChange(int onesPercentage) {
        int gCnt = preprocessedData.length;
        int cCnt = preprocessedData[0].length;
        int numberOfValues = gCnt * cCnt;
        int numOnes;
        double[] threshold = new double[gCnt];
        for (int i = 0; i < gCnt; i++) {
            float[] preprocessedData_1d = new float[cCnt + 1];

            // Calculate number of ones
            numOnes = Math.round(cCnt * onesPercentage / 100);

            int k = 0;
            for (int j = 0; j < cCnt; j++) {
                k++;
                preprocessedData_1d[k] = Math.abs(preprocessedData[i][j]);

            }
            // sort Array
            Arrays.sort(preprocessedData_1d);
            // Calculate threshold
            threshold[i] = preprocessedData_1d[cCnt - numOnes + 1];

            if (MethodConstants.debug) {
                System.out.println("DEBUG discrization by percentage of ones:");
                System.out.println("numberOfValues: " + numberOfValues);
                System.out.println("onesPercentage: " + onesPercentage);
                System.out.println("Number of Ones: " + numOnes);
                System.out.println("Length of 1D-Array: "
                        + preprocessedData_1d.length);
                System.out.println("Position 1: " + preprocessedData_1d[1]);
                System.out.println("Position 100: " + preprocessedData_1d[100]);
                System.out.println("Position 1000: "
                        + preprocessedData_1d[1000]);
            }
        }
        return threshold;
    }

    // ************************************************************************
    // //
    // * * //
    // * Preprocessor: Interface to others ... * //
    // * * //
    // ************************************************************************
    // //

    // related to the current dataset (just loaded) only:

    public boolean dataReady() {
        return null != rawData;
    }

    public boolean discreteDataReady() {
        return null != discretizedData;
    }

    public int getWorkingChipCount() {
        return preprocessedData[0].length;
    }

    public int getOriginalChipCount() {
        return rawData[0].length;
    }

    public int getGeneCount() {
        return rawData.length;
    }

    public float[][] getOriginalData() {
        return rawData;
    }

    public float[][] getPreprocessedData() {
        return preprocessedData;
    }

    public int[][] getDiscreteData() {
        return discretizedData;
    }

    // ===========================================================================
    public Vector getGeneNames() {
        Vector gNs = new Vector();
        for (int i = 0; i < geneNames.length; i++)
            gNs.add(geneNames[i]);
        return gNs;
    }

    public Vector getChipNames() {
        Vector cNs = new Vector();
        for (int i = 0; i < chipNames.length; i++)
            cNs.add(chipNames[i]);
        return cNs;
    }

    public Vector getWorkingChipNames() {
        Vector cNs = new Vector();
        for (int i = 0; i < workChipNames.length; i++)
            cNs.add(workChipNames[i]);
        return cNs;
    }

    public int[] getDependencies() {
        return dependencies;
    }

    public Vector getHeaderColumnLabels() {
        return labels;
    }

    public Vector getHeaderColumns() {
        return labelArrays;
    }

}
