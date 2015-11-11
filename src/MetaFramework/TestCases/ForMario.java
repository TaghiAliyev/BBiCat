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

package MetaFramework.TestCases;

import MetaFramework.Bayesian;
import MetaFramework.IlluminaParsers;
import MetaFramework.NCI.PathwayAnalysisMixing;
import MetaFramework.ReadingBigData;
import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Bicluster;
import bicat.biclustering.Dataset;
import bicat.biclustering.MyBiMax;
import bicat.preprocessor.PreprocessOption;
import bicat.preprocessor.Preprocessor;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

/**
 * Test cases that I will run in the meeting with Mario
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class ForMario {

    public static void main(String[] args) throws Exception
    {
        System.out.println("Starting to read the dataset");
        String dataLoc = "C:/Users/tagi1_000/Desktop/CERN/BBiCat/src/sampleData/MuTHER_Fat_normalized_31032010_uncondensed_Ids.txt";
        ReadingBigData dataReadEngine = new ReadingBigData(dataLoc);
        dataReadEngine.read2(800, 250); // 4000 -> Row limit, 400 -> column
        System.out.println(dataReadEngine.getActual().length + " " + dataReadEngine.getActual()[0].length);
        System.out.println("Done reading the file");
        float[][] matrix = dataReadEngine.getActual();

        String[] columnNames = dataReadEngine.getColNames();
        ArrayList<String> rowNames = dataReadEngine.getRowNames();

        /*
        Translation illumina
         */
        System.out.println("Starting to create the illumina parser");
        IlluminaParsers illuminaEngine = new IlluminaParsers("HumanHT-12_V3_0_R3_11283641_A.txt");
        String[] illuRow = new String[rowNames.size()];

        System.out.println("Illumina parser is ready!");
        int notFound = 0;

        for (int i = 0 ;i < rowNames.size(); i++)
        {
            String newName = illuminaEngine.getIlluminaToHGNC().get(rowNames.get(i));
            if (newName != null)
                illuRow[i] = newName;
            else {
                notFound++;
                illuRow[i] = rowNames.get(i);
            }
        }

        System.out.println("Number of not mapped genes : " + notFound);
//        Assert.assertEquals(0, notFound);

        // Now, all the genes are mapped to their HGNC Symbols.
        // As, we will be using the local version of the Bimax method, we need to pre-process the dataset manually

        // First, let's create vectors containing the column and row names
        Vector colVector = new Vector(), rowVector = new Vector();
        for (int i = 0; i < columnNames.length; i++)
            colVector.add(columnNames[i]);

        for (int i = 0; i < illuRow.length; i++)
            rowVector.add(illuRow[i]);

        Dataset dataset = new Dataset(matrix, matrix, null, rowVector, colVector, colVector, new Vector(), new Vector());
        System.out.println("Dataset created!");

        System.out.println("Pre-processing now!");

        UtilFunctionalities engine = new UtilFunctionalities();
        Preprocessor pre = new Preprocessor(engine);
        pre.setRawData(matrix);
        pre.setChipNames(columnNames);
        pre.setDiscretizedData(null);
        pre.setGeneNames(illuRow);
        pre.setLabels(new Vector());
        pre.setLabelArrays(new Vector());
        pre.setWorkChipNames(columnNames);
        pre.setPreprocessedData(matrix);
        pre.setDataContainsNegValues(false);

        /*
        Pre-processing the dataset
         */
        System.out.println("Pre-processing the dataset now!");
        PreprocessOption options = new PreprocessOption();
        options.setDo_discretize(true);
        options.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP);
        System.out.println("Please enter the discretization percentage : ");
        Scanner systemScan = new Scanner(System.in);
        int percentage = systemScan.nextInt();
        options.setOnesPercentage(percentage);
        options.setDiscretizationMode("onesPercentage");

        // Pre-processing the data
        pre.preprocessData(options);

        System.out.println("Dataset is pre-processed now! Let's apply BiMax");
        dataReadEngine = null;
        MyBiMax algo = new MyBiMax(columnNames, illuRow, dataset);
        algo.setNoColumns(matrix[0].length);
        algo.setNoRows(matrix.length);
        System.out.println("Please enter the least number of columns bicluster should have : ");
        int minCol = systemScan.nextInt();
        algo.setMinNoColumns(minCol);
        System.out.println("Please enter the least number of rows/genes bicluster should have : ");
        int minRow = systemScan.nextInt();
        algo.setMinNoRows(minRow);
        int[][] disData = pre.getDiscretizedData();
        dataset.setDiscrData(disData);

//        for (int i = 0; i < disData[0].length;i++)
//            System.out.println(disData[0][i]);
        System.out.println("Starting to solve!!!");
        long bimaxStart = System.currentTimeMillis();
        if (algo.getNoColumns() > 0L && algo.getNoRows() > 0L && algo.initialize() != 0) {
            algo.readInDataMatrix(disData);
            algo.conquer(0L, algo.getNoRows() - 1L, 0L, 0L);
        }
        algo.closePrint();
        long bimaxEnd = System.currentTimeMillis();

        System.out.println("Solved!");
        System.out.println("There were : " + algo.getBiclusterCounter() + " clusters found!");
        System.out.println("It took bimax " + (bimaxEnd - bimaxStart) + " ms to finish the job");
        System.out.println("Starting post-analysis");
        ArrayList<Bicluster> biclusters = algo.getBiclusters();
        Bayesian bayesian = new Bayesian();
        String file = "NCI.xml"; // NCI Database file
        PathwayAnalysisMixing pathEngine = new PathwayAnalysisMixing(file);

        System.out.println("Now analysing the biclusters");
        for (Bicluster tmp : biclusters)
        {
            bayesian.compute(tmp, pathEngine, dataset, -1);
        }

        System.out.println("Bayesian post-analysis is done! Please check the PostAnalysis.out file for the details!");
    }

}
