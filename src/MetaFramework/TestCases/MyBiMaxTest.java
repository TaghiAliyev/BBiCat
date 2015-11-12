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
import MetaFramework.NCI.NCIInteraction;
import MetaFramework.NCI.NCIMolecule;
import MetaFramework.NCI.NCIPathway;
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
import java.util.Set;
import java.util.Vector;

/**
 * Showcase of a possible use of a re-implemented BiMax method.
 * It includes data processing, pre-processing, Bimax use, Use of illumina dataset parser, and post-analysis
 * with Bayesian.
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class MyBiMaxTest {

    // Small test case
    public static void main(String[] args) throws Exception {
        // First read the data file
        // Then apply the conquer method


        // Reading a dataset
        System.out.println("Reading the dataset");
        ReadingBigData bigEngine = new ReadingBigData("src/sampleData/ProcessedFirst.txt");
        bigEngine.read2();
        System.out.println("Reading done");

        String[] colNames = bigEngine.getColNames();
        ArrayList<String> rowNames = bigEngine.getRowNames();

        // For this special test case, we just have numbers as row/gene names, so let's change that to illumina names
        // basically, create


        System.out.println("Reading the illumina information");

        IlluminaParsers illuminaEngine = new IlluminaParsers("HumanHT-12_V3_0_R3_11283641_A.txt");

        ArrayList<String> rowNamesNew = new ArrayList<String>();
        String[] allEntries = illuminaEngine.getIlluminaToHGNC().keySet().toArray(new String[]{});

        System.out.println("Number of illumina mappings : " + allEntries.length);

        for (int i = 0; i < rowNames.size(); i++) {
            rowNamesNew.add(allEntries[(int) (Math.random() * (allEntries.length - 1))]);
        }

        rowNames = rowNamesNew;

        // This is just for the proof-of-concept. Should not be done in other circumstances!!
        String[] illuminaNames = new String[rowNames.size()];
        for (int i = 0; i < illuminaNames.length; i++) {
            String illName = illuminaEngine.getIlluminaToHGNC().get(rowNames.get(i));
            if (illName != null)
                illuminaNames[i] = illName; // Change accordingly
            else
                illuminaNames[i] = rowNames.get(i); // Do not change if illumina does not corresponding name
        }

        System.out.println("Done reading the illumina information");


        System.out.println("Dataset being created");
        float[][] data = bigEngine.getActual();
        Vector cols = new Vector();
        for (int i = 0; i < colNames.length; i++)
            cols.add(colNames[i]);

        Vector genes = new Vector();
        for (int i = 0; i < illuminaNames.length; i++)
            genes.add(illuminaNames[i]);

        // Dataset that will be pre-processed before being sent to the bimax algorithm
        Dataset dataset = new Dataset(data, data, null, genes, cols, cols, new Vector(), new Vector());

        System.out.println("Dataset is ready");
        // Pre-processing
        System.out.println("Getting ready for pre-processing");
        UtilFunctionalities engine = new UtilFunctionalities();
        Preprocessor pre = new Preprocessor(engine);
        pre.setRawData(data);
        pre.setChipNames(colNames);
        pre.setDiscretizedData(null);
        pre.setGeneNames(illuminaNames);
        pre.setLabels(new Vector());
        pre.setLabelArrays(new Vector());
        pre.setWorkChipNames(colNames);
        pre.setPreprocessedData(data);
        pre.setDataContainsNegValues(false);

        /*
        Pre-process the data
         */

        PreprocessOption options = new PreprocessOption();
        options.setDo_discretize(true);
        options.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP);
        options.setOnesPercentage(30); // 25% Thresholding
        options.setDiscretizationMode("onesPercentage");

        // Pre-processing the data
        pre.preprocessData(options);

        System.out.println("Preprocessing done");
        System.out.println("Initializing the solver");
        MyBiMax algo = new MyBiMax(colNames, illuminaNames, dataset);
        algo.setNoColumns(data[0].length);
        algo.setNoRows(data.length);
        algo.setMinNoColumns(15);
        algo.setMinNoRows(11);
        int[][] disData = pre.getDiscretizedData();
        dataset.setDiscrData(disData);

//        for (int i = 0; i < disData[0].length;i++)
//            System.out.println(disData[0][i]);
        System.out.println("Starting to solve!!!");
        if (algo.getNoColumns() > 0L && algo.getNoRows() > 0L && algo.initialize() != 0) {
            algo.readInDataMatrix(disData);
            algo.conquer(0L, algo.getNoRows() - 1L, 0L, 0L);
        }
        algo.closePrint();

        System.out.println("Solved!");

        System.out.println("Starting post-analysis");
        ArrayList<Bicluster> biclusters = algo.getBiclusters();
        Bayesian bayesian = new Bayesian(NCIMolecule.class, NCIInteraction.class, NCIPathway.class);
        String file = "NCI.xml";
        PathwayAnalysisMixing pathEngine = new PathwayAnalysisMixing(file);

        // Let's transform the genes to some random genes from NCI database in order to be able to test the Bayesian
        // Method. This is just for the proof-of-concept. Should not be done in other circumstances!!

        // Transforming gene names to some random gene name from the bayesian analysis
        Set<NCIMolecule> mols = pathEngine.getGeneToPath().keySet();
        String[] nciGenes = new String[mols.size()];
        int cnt = 0;
        for (NCIMolecule mol : mols) {
            nciGenes[cnt] = mol.getName();
            cnt++;
        }

        Vector newGenes = new Vector();
        for (int i = 0; i < dataset.getGeneCount(); i++) {
            newGenes.add(nciGenes[(int) (Math.random() * nciGenes.length - 1)]);
        }

        dataset.setGeneNames(newGenes);

        System.out.println(dataset.getGeneName(0) + " " + dataset.getGeneName(1));

        for (Bicluster tmp : biclusters) {
//            System.out.println("New bicluster!!!\n");
            bayesian.compute(tmp, pathEngine, dataset, -1);
//            System.out.println("--------------\n\n");
        }

        bayesian.closeFile();

        System.out.println("Post analysis done");

        // Following example is of using the files of format where first line contains number of rows, columns, min rows
        // min columns and then a binary matrix of size rows x columns

        /*
        // Let's first try the simple one
        FileInputStream stream = new FileInputStream("C:/Users/tagi1_000/Desktop/CERN/BBiCat/src/sampleData/BayesTestHandMade.txt");
        Scanner scanner = new Scanner(stream);

        MyBiMax engine = new MyBiMax();
        engine.setNoRows(scanner.nextLong());
        engine.setNoColumns(scanner.nextLong());
        engine.setMinNoRows(scanner.nextLong());
        engine.setMinNoColumns(scanner.nextLong());
        if (engine.getMinNoRows() < 1L)
            engine.setMinNoRows(1L);
        if (engine.getMinNoColumns() < 1L)
            engine.setMinNoColumns(1L);

        int[][] dataset = new int[(int)engine.getNoRows()][(int)engine.getNoColumns()];
        for (int i = 0; i < dataset.length; i++)
        {
            for (int j = 0; j < dataset[0].length; j++)
            {
                dataset[i][j] = scanner.nextInt();
            }
        }

        if (engine.getNoColumns() > 0L && engine.getNoRows() > 0L && engine.initialize() != 0) {
            engine.readInDataMatrix(dataset);
            engine.conquer(0L, engine.getNoRows() - 1L, 0L, 0L);
        }
        */
    }
}
