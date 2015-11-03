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

package bicat.Main;

import OwnPersonal.computeMeanResidueScore;
import bicat.Constants.MethodConstants;
import bicat.biclustering.Dataset;
import bicat.gui.BicatGui;
import bicat.postprocessor.Postprocessor;
import bicat.preprocessor.FileOffsetException;
import bicat.preprocessor.PreprocessOption;
import bicat.preprocessor.Preprocessor;
import bicat.run_machine.*;
import bicat.util.BicatError;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

/**
 * Contains the common functionalities that are used for processing files, doing analysis and
 * calling other classes for running algorithms
 * <p>
 * For example: There shouldn't be a need for creating a GUI element if simple biclustering run is needed and
 * this class tries to contain all the method-related logic in it and making BicatGui class just about GUI
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */

@Data
@EqualsAndHashCode(of = {"currentDirectoryPath"})
public class UtilFunctionalities {

    /* Current working directory */
    private String currentDirectoryPath;
    private boolean debug = false;

    private Dataset currentDataset = null;

    private int currentDatasetIdx = 0;

    private LinkedList datasetList = new LinkedList();

    private boolean dataAndChipInformationLoaded;

    private int gpDistanceThreshold;

    private float hammingDistance; // for visualization purposes! (for

    private boolean preprocessExtended;

    private boolean processExtended;

    private boolean discretizeExtended;
    private Vector[] currentBiclusterSelection; // for selecting a BC at once

    /**
     * 100504: will generalize this!!! .... 2 if files have an offset that must
     * be ignored (e.g. pathway IDs)
     */
    private int fileOffset;

    private Preprocessor pre;

    private Postprocessor post;

    /**
     * Keep the last Preprocessing Option !
     */
    private PreprocessOption preprocessOptions = new PreprocessOption();

    private BicatGui owner;

    /**
     * Used to perform biclustering: RunMachine for BiMax
     */
    private RunMachine_BiMax runMachineBiMax;

    /**
     * Used to perform biclustering: RunMachine for Cheng and Church (2000)
     */
    private RunMachine_CC runMachine_CC;

    /**
     * Used to perform biclustering: RunMachine for Iterative Signature
     * Algorithm (ISA, 2002)
     */
    private RunMachine_ISA runMachine_ISA;

    /**
     * Used to perform biclustering: RunMachine for xMotifs (2003)
     */
    private RunMachine_XMotifs runMachine_XMotifs;

    /**
     * Used to perform biclustering: RunMachine for OPSM
     */
    private RunMachine_OPSM runMachineOPSM;

    /**
     * Used to perform clustering: RunMachine for Hierarchical Clustering
     */
    private RunMachine_HCL runMachineHCL;

    /**
     * Used to perform clustering: RunMachine for K-Means Clustering
     */
    private RunMachine_KMeans runMachineKMeans;


    /**
     * Contains whatever dataset that is currently being displayed (can be
     * non-normalized data).
     */
    private float[][] rawData;


    /**
     * 1 if no log operation is desired in the preprocessing step, 2 or 10 for
     * respective base logarithm.
     */
    private int logBaseSetting;

    /**
     * Applied to the preprocessed data to get the discretized matrix for
     * biclustering
     */
    private float discretizationThreshold;

    /**
     * True id preprocessing has been performed
     */
    private boolean preprocessComplete;

    private boolean discretizeComplete;


    public UtilFunctionalities() {

        try {
            File f = new File(".");
            currentDirectoryPath = f.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        datasetList = new LinkedList();
        preprocessOptions = new PreprocessOption();

        dataAndChipInformationLoaded = false; // mada konnte schon auch true -
        // default sein! boh
        // (02.06.2004)

        discretizeComplete = false;
        preprocessComplete = false;

        discretizeExtended = false;
        preprocessExtended = false;
        processExtended = false;

        fileOffset = 1; // assuming the data file has BOTH the column and row
        // headers
        logBaseSetting = 2;
        discretizationThreshold = (float) 2.0; // 1.0 <=> 2-fold change (when
        // Log Base is 2)

        // post-processing stuff:
        gpDistanceThreshold = 0; // correct this! (so to have only the
        // induced subgraph (degree should be
        // thresholded, as well!))
        hammingDistance = (float) 0.8; // should be user-input, though! (20 %)
        owner = null;
    }

    public UtilFunctionalities(BicatGui owner) {

        try {
            File f = new File(".");
            currentDirectoryPath = f.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        datasetList = new LinkedList();
        preprocessOptions = new PreprocessOption();

        dataAndChipInformationLoaded = false; // mada konnte schon auch true -
        // default sein! boh
        // (02.06.2004)

        discretizeComplete = false;
        preprocessComplete = false;

        discretizeExtended = false;
        preprocessExtended = false;
        processExtended = false;

        fileOffset = 1; // assuming the data file has BOTH the column and row
        // headers
        logBaseSetting = 2;
        discretizationThreshold = (float) 2.0; // 1.0 <=> 2-fold change (when
        // Log Base is 2)

        // post-processing stuff:
        gpDistanceThreshold = 0; // correct this! (so to have only the
        // induced subgraph (degree should be
        // thresholded, as well!))
        hammingDistance = (float) 0.8; // should be user-input, though! (20 %)
        this.owner = owner;
    }


    // ===========================================================================
    public void finishUpRun(int datasetIdx, LinkedList result,
                            PreprocessOption preOpts, String algo) {

        int ALGO = 0;
        if (algo.equals("BiMax"))
            ALGO = MethodConstants.BIMAX_ID;
        else if (algo.equals("CC"))
            ALGO = MethodConstants.CC_ID;
        else if (algo.equals("ISA"))
            ALGO = MethodConstants.ISA_ID;
        else if (algo.equals("xMotif"))
            ALGO = MethodConstants.XMOTIF_ID;
        else if (algo.equals("OPSM"))
            ALGO = MethodConstants.OPSM_ID;

        updateCurrentDataset(datasetIdx);
        addBiclusters(currentDataset, result, ALGO);
        addPreprocessOptions(currentDataset, preOpts);

//		System.setOut(System.out);
        try {
            computeMeanResidueScore.computeResidue(currentDataset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (owner != null) {
            owner.updatePreprocessOptionsMenu();
            owner.updateBiclusterMenu();
            owner.buildTree();
        }
    }

    /**
     * Set the current dataset to <code>datasetList.get(x)</code>.
     */
    public void updateCurrentDataset(int x) {
        if (debug)
            System.out.println("Update the \"current\" dataset! (" + x + ")");
        currentDataset = (Dataset) datasetList.get(x);
        currentDatasetIdx = x;
    }

    /**
     * Set the current dataset to <code>br</code>.
     */
    public void updateCurrentDataset(Dataset br) {
        currentDataset = br;
    }

    /**
     * Add bicluster results to the dataset <code>BcR</code> (done after
     * running biclustering).
     */
    public void addBiclusters(Dataset BcR, LinkedList newBcs, int wBCing) {
        BcR.addBiclusters(newBcs);
        BcR.addBiclustersName(wBCing);
    }


    public void addPreprocessOptions(Dataset BcR, PreprocessOption po) {
        BcR.addPreprocessOption(po);
        updateCurrentDataset(BcR);
    }

    // ===========================================================================
    public void finishUpClusterRun(int datasetIdx, LinkedList result,
                                   PreprocessOption preOpts, String algo) throws Exception {

        int ALGO = 0;
        if (algo.equals("HCL"))
            ALGO = MethodConstants.HCL_ID;
        else if (algo.equals("KMeans"))
            ALGO = MethodConstants.KMEANS_ID;
        else
            ;

        updateCurrentDataset(datasetIdx);
        addClusters(currentDataset, result, ALGO);
        addClustersPreprocessOptions(currentDataset, preOpts);

        if (owner != null) {
            owner.updatePreprocessOptionsMenu();
            owner.updateClusterMenu();
            owner.buildTree();
        }
    }


    /**
     * Add cluster results to the dataset <code>BcR</code> (done after running
     * clustering).
     */
    public void addClusters(Dataset BcR, LinkedList newBcs, int wBCing) {
//		System.out.println("LinkedList newBcs: "+newBcs.toString());
        BcR.addClusters(newBcs);
        BcR.addClustersName(wBCing);
    }

    public void addClustersPreprocessOptions(Dataset BcR, PreprocessOption po) {
        BcR.addClustersPreprocessOption(po);
        updateCurrentDataset(BcR);
    }


    // ************************************************************************
    // //
    // * * //
    // * Central: datasets management * //
    // * * //
    // ************************************************************************
    // //

    /**
     * Check if any Biclustering results are available.
     */
    public boolean anyBiclustersAvailable() {
        for (int i = 0; i < datasetList.size(); i++) {
            Dataset br = (Dataset) datasetList.get(i);
            if (br.getBiclusters().size() + br.getClusters().size() > 0)
                return true;
        }
        return false;
    }

    /**
     * Check if any Valid Bicluster / Cluster / Filter / Search list is
     * available.
     */
    public boolean anyValidListAvailable() {
        for (int i = 0; i < datasetList.size(); i++) {
            Dataset BcR = (Dataset) datasetList.get(i);
            if ((BcR.getValid_biclusters() + BcR.getValid_clusters() + BcR.getValid_filters() + BcR.getValid_searches()) > 0)
                return true;
        }
        return false;
    }

    /**
     * Obtain the names of all lists of biclusters.
     */
    public Vector getListNamesAll() {

        Vector vec = new Vector();
        vec.add("Choose bicluster list...");

        for (int i = 0; i < datasetList.size(); i++) {
            Dataset br = (Dataset) datasetList.get(i);

            Vector bcN = br.getBiclustersNames();
            Vector bV = br.getBiclustersValid();
            for (int j = 0; j < bcN.size(); j++)
                if (((Boolean) bV.get(j)).booleanValue())
                    vec.add("D" + i + " " + bcN.get(j));

            Vector cN = br.getClustersNames();
            Vector cV = br.getClustersValid();
            for (int j = 0; j < cN.size(); j++)
                if (((Boolean) cV.get(j)).booleanValue())
                    vec.add("D" + i + " " + cN.get(j));

            Vector sN = br.getSearchNames();
            Vector sV = br.getSearchValid();
            for (int j = 0; j < sN.size(); j++)
                if (((Boolean) sV.get(j)).booleanValue())
                    vec.add("D" + i + " " + sN.get(j));

            Vector fN = br.getFiltersNames();
            Vector fV = br.getFiltersValid();
            for (int j = 0; j < fN.size(); j++)
                if (((Boolean) fV.get(j)).booleanValue())
                    vec.add("D" + i + " " + fN.get(j));
        }

        if (debug)
            System.out.println("D: Size of all datasets list names = "
                    + vec.size());

        return vec;
    }

    /**
     * Obtain the names of all lists of biclusters of the dataset <code>d</code>.
     */
    public Vector getListNames(int d) {
        Dataset data = (Dataset) datasetList.get(d);

        Vector vec = new Vector();
        vec.addAll(data.getBiclustersNames());
        vec.addAll(data.getClustersNames());
        vec.addAll(data.getSearchNames());
        vec.addAll(data.getFiltersNames());

        if (debug)
            System.out
                    .println("D: Size of all biclusters lists (dataset d) names = "
                            + vec.size());

        return vec;
    }

    /**
     * Obtain the names of all datasets.
     */
    public Vector getListDatasets() {
        Vector vec = new Vector();
        for (int i = 0; i < datasetList.size(); i++)
            vec.add("Dataset " + i);
        if (debug)
            System.out.println("D: Size of all datasets list names = "
                    + vec.size());
        return vec;
    }

    /**
     * Add dataset to the central management list (is done after loading data).
     *
     * @throws Exception
     */
    public void addDataset(float[][] data, boolean withCtrl, int[] dep,
                           Vector gNames, Vector chipNames, Vector workCNames,
                           Vector colLabels, Vector colInfo) throws Exception {
        System.out.println("Here, reading file!");
        datasetList.add(new Dataset(data, withCtrl, dep, gNames, chipNames,
                workCNames, colLabels, colInfo));
        updateCurrentDataset(datasetList.size() - 1);
        computeMeanResidueScore.computeResidue((Dataset) datasetList.get(datasetList.size() - 1));
        if (debug)
            System.out.println("New dataset added [" + datasetList.size()
                    + "]. ");
    }

    public void addDataset(Dataset dataset) throws Exception {
        datasetList.add(dataset);
        updateCurrentDataset(datasetList.size() - 1);

        computeMeanResidueScore.computeResidue((Dataset) datasetList.get(datasetList.size() - 1));

        if (debug)
            System.out.println("New dataset added [" + datasetList.size()
                    + "]. ");
    }


    // ************************************************************************
    // //
    // * * //
    // * I/O-specific: load, save, export facilities... * //
    // * * //
    // ************************************************************************
    // //

    public String writeAnalysisResults_BioLayout(HashMap analysis) {
        System.out.println("Be here please");
        StringBuffer sb = new StringBuffer();

        Set ks = analysis.keySet();
        Object[] ka = ks.toArray();
        System.out.println(ka.length);
        for (int i = 0; i < ka.length; i++) {
//			System.out.println("Here!");
            String key = (String) ka[i];
            String[] gs = key.split("\t");
            sb.append(currentDataset.getGeneName((new Integer(gs[0]))
                    .intValue())
                    + "\t"
                    + currentDataset.getGeneName((new Integer(gs[1]))
                    .intValue())
                    + "\t"
                    + (((Object[]) analysis.get(key))[0])
                    + "\t"
                    + (((Object[]) analysis.get(key))[1]) + "\n");
        }
        System.out.println("Done!");
        return sb.toString();
    }

    public void writeGPA(String content) {
        System.out.println("Here, should export now!");
        JFileChooser jfc = new JFileChooser(currentDirectoryPath);
        jfc.setDialogTitle("Save Analysis Results:");
        File file;
        int returnVal = jfc.showOpenDialog(owner);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                file = jfc.getSelectedFile();
                FileWriter fw = new FileWriter(file);
                fw.write(content);
                fw.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }

    /**
     * Writes a list of biclusters to a file that the user can choose using a
     * pop up window.
     * <p>
     * Overwrites existing files without asking. (CORRECT THIS!) First line
     * contains number of biclusters, blocks after that are as follows: (number
     * of genes) (number of chips) (gene indices) (chip indices)
     *
     * @param BiclusterList linked list of biclusters that are written to file
     */
    public void writeBiclusters(LinkedList BiclusterList) {

        JFileChooser jfc = new JFileChooser(currentDirectoryPath);
        jfc.setDialogTitle("Save bicluster data:");
        File file;
        int returnVal = jfc.showOpenDialog(owner);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                file = jfc.getSelectedFile();
                FileWriter fw = new FileWriter(file);
                String writeBuffer = ("Number of BCs = "
                        + BiclusterList.size() + "\n\n\n");

                bicat.biclustering.Bicluster bc;
                for (int i = 0; i < BiclusterList.size(); i++) {
                    bc = (bicat.biclustering.Bicluster) BiclusterList.get(i);
                    writeBuffer = (bc.getGd() + " " + bc.getCd() + "\n");

                    // write genes to file
                    for (int j = 0; j < bc.getGd(); j++)
                        writeBuffer += bc.getGenes()[j] + " ";
                    writeBuffer = writeBuffer.trim(); // remove trailing
                    writeBuffer += "\n";

                    // write chips to file
                    for (int j = 0; j < bc.getCd(); j++)
                        writeBuffer += bc.getChips()[j] + " ";
                    writeBuffer = writeBuffer.trim(); // remove trailing
                    writeBuffer += "\n";

                    fw.write(writeBuffer);
                }
                fw.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * Writes a list of biclusters to a file that the user can choose using a
     * pop up window.
     * <p>
     * Overwrites existing files without asking. (CORRECT THIS!) First line
     * contains number of biclusters, blocks after that are as follows: (number
     * of genes) (number of chips) (gene indices) (chip indices)
     *
     * @param BiclusterList linked list of biclusters that are written to file
     */
    public void writeBiclustersHuman(LinkedList BiclusterList) {

        JFileChooser jfc = new JFileChooser(currentDirectoryPath); // open a
        // file
        // chooser
        // dialog
        // window
        jfc.setDialogTitle("Save bicluster data:");
        File file;
        int returnVal = jfc.showOpenDialog(owner);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                file = jfc.getSelectedFile();
                FileWriter fw = new FileWriter(file);
                String writeBuffer = ("Number of BCs = "
                        + BiclusterList.size() + "\n\n\n");

                bicat.biclustering.Bicluster bc;
                for (int i = 0; i < BiclusterList.size(); i++) {
                    bc = (bicat.biclustering.Bicluster) BiclusterList.get(i);
                    writeBuffer = (bc.getGd() + " " + bc.getCd() + "\n");

                    // write genes to file
                    for (int j = 0; j < bc.getGd(); j++)
                        writeBuffer += currentDataset.getGeneName(bc.getGenes()[j])
                                + " ";// pre.getGeneName(bc.genes[j]) + " ";
                    writeBuffer = writeBuffer.trim(); // remove trailing
                    // whitespace! (OVO
                    // NISAM ZNALA)
                    writeBuffer += "\n";

                    // write chips to file
                    for (int j = 0; j < bc.getCd(); j++)
                        writeBuffer += currentDataset
                                .getWorkingChipName(bc.getChips()[j])
                                + " ";// pre.getChipName(bc.chips[j]) + " ";
                    writeBuffer = writeBuffer.trim(); // remove trailing
                    // whitespace! (OVO
                    // NISAM ZNALA)
                    writeBuffer += "\n";

                    fw.write(writeBuffer);
                }
                fw.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    // ===========================================================================
    // DISPLAY PURPOSES ...

    /**
     * Hands a discretized data matrix to the for display.<br>
     * <p>
     * Reads the values from the provided <code>int[][]</code> array and
     * places them in <code>rawData</code>.
     * <p>
     * <p>
     * //...Missing values are set in the middle of the range, for want of a
     * better solution.
     */
    public void setData(int[][] dataMatrix) {

        float[][] intDataMatrix = new float[dataMatrix.length][dataMatrix[0].length];
        for (int i = 0; i < dataMatrix.length; i++)
            for (int j = 0; j < dataMatrix[0].length; j++)
                intDataMatrix[i][j] = (float) dataMatrix[i][j];

        setData(intDataMatrix);
    }

    /**
     * Hands a <code>float</code> data matrix for display.<br>
     * <p>
     * Normalization of the values for display is needed, because of the color
     * mapping.
     */

    private float[][] rawData_for_GP;

    public void setData(float[][] dataMatrix) {
        System.out.println("Setting the data!");
        rawData = new float[dataMatrix.length][dataMatrix[0].length];

        rawData_for_GP = new float[dataMatrix.length][dataMatrix[0].length];
        for (int i = 0; i < dataMatrix.length; i++)
            for (int j = 0; j < dataMatrix[0].length; j++)
                rawData_for_GP[i][j] = dataMatrix[i][j];

        float minValue = Float.MAX_VALUE;
        float maxValue = Float.MIN_VALUE;

        for (int i = 0; i < dataMatrix.length; i++)
            for (int j = 0; j < dataMatrix[0].length; j++) {
                if (maxValue < dataMatrix[i][j])
                    maxValue = dataMatrix[i][j];
                if (minValue > dataMatrix[i][j])
                    minValue = dataMatrix[i][j];
            }

        // squeeze the values of the matrix, for visualization purposes:
        if (maxValue == minValue)
            for (int i = 0; i < dataMatrix.length; i++)
                for (int j = 0; j < dataMatrix[0].length; j++)
                    rawData[i][j] = (float) 0.5;
        else
            for (int i = 0; i < dataMatrix.length; i++)
                for (int j = 0; j < dataMatrix[0].length; j++)
                    rawData[i][j] = (dataMatrix[i][j] - minValue)
                            / (maxValue - minValue);

		/*
         * if(debug) { System.out.println("Dimensions, 1: "+dataMatrix.length+",
		 * "+dataMatrix[0].length); System.out.println("Dimensions, 2:
		 * "+rawData.length+", "+rawData[0].length);
		 * System.out.println("Dimensions, 3: "+rawData_for_GP.length+",
		 * "+rawData_for_GP[0].length); System.out.flush(); }
		 */
    }


    // ===========================================================================

    @Override
    public String toString() {
        return "Util Functionalities!";
    }
}
