/*
 *                                BBiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                DOI for citing the release : 10.5281/zenodo.33117
 *
 *                                Copyright (c) 2016 Taghi Aliyev, Marco Manca, Alberto Di Meglio
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

package MetaFramework.Experiments;

import MetaFramework.IlluminaParsers;
import MetaFramework.ReadingBigData;
import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Bicluster;
import bicat.biclustering.Dataset;
import bicat.biclustering.MyBiMax;
import bicat.preprocessor.PreprocessOption;
import bicat.preprocessor.Preprocessor;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Sensitivity analysis -> This should point to the fact that biclustering with this kind of filtering is very
 * sensitive to input numbers. Idea: Look at some statistics with different threshold inputs
 * Things we can show from this:
 * 1. With small changes in percentage, we get different numbers of clusters (Big deviations)
 * 2. Look at Bayesian results of the clusters obtained with different thresholds
 * 3. Maybe look into purity based on the original matrix? (Of the obtained clusters)
 *
 * Expected : It should show that this type of biclustering is very sensitive to the input percentage threshold, meaning
 * it can not be used/it should not be used for creating chain graphs. Thus leading to negative results about the clustering
 * techniques. Which is also why, our goal switched to mainly publishing the software we have so far, and focusing on further
 * developments through different languages, software tools and clustering techniques. That is why, chain graphs are not implemented
 * for now. As, the computing power and the methods first thought of are not compatible and strong enough to perform on
 * meaningful level of accuracy.
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class Experiment2 {

    public static void main(String[] args) throws Exception
    {
        // Reading a dataset
        System.out.println("Reading the dataset");
        ReadingBigData bigEngine = new ReadingBigData("src/sampleData/dataSample_1.txt");
        bigEngine.read2();
        System.out.println("Reading done");

        System.out.println("Size of the matrix are : " + "Row count : " + bigEngine.getRowNames().size() + ", Column count : " + bigEngine.getColNames().length);

        String[] colNames = bigEngine.getColNames();
        ArrayList<String> rowNames = bigEngine.getRowNames();


        System.out.println("Dataset being created");
        float[][] data = bigEngine.getActual();

        float minData = data[0][0], maxData = data[0][0];

        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0;j < data[0].length; j++)
            {
                if (data[i][j] < minData)
                    minData = data[i][j];
                if (data[i][j] > maxData)
                    maxData = data[i][j];
            }
        }

        Vector cols = new Vector();
        for (int i = 0; i < colNames.length; i++)
            cols.add(colNames[i]);

        Vector genes = new Vector();
        for (int i = 0; i < rowNames.size(); i++)
            genes.add(rowNames.get(i));

        String[] rowAsArray = new String[rowNames.size()];
        for (int i = 0; i < rowNames.size(); i++)
        {
            rowAsArray[i] = rowNames.get(i);
        }

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
        pre.setGeneNames(rowAsArray);
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
        options.setOnesPercentage(22); // 25% Thresholding
        options.setDiscretizationMode("onesPercentage");

        // Pre-processing the data
        pre.preprocessData(options);

        System.out.println("Preprocessing done");
        System.out.println("Initializing the solver");
        MyBiMax algo = new MyBiMax(colNames, rowAsArray, dataset);
        algo.setNoColumns(data[0].length);
        algo.setNoRows(data.length);
        algo.setMinNoColumns(8);
        algo.setMinNoRows(8);
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

        System.out.println("Number of biclusters found : " + algo.getBiclusterCounter());
        System.out.println("Minimum sizes allowed : " + algo.getMinNoRows() + ", " + algo.getMinNoColumns());
        ArrayList<Bicluster> result = algo.getBiclusters();

        double maxMS = result.get(0).getMSRS(), minMS = result.get(0).getMSRS();
        double averageMS = 0.0;
        for (Bicluster tmp : result)
        {
            averageMS += tmp.getMSRS();
            if (tmp.getMSRS() > maxMS)
                maxMS = tmp.getMSRS();
            else if (tmp.getMSRS() < minMS)
                minMS = tmp.getMSRS();
        }
        averageMS /= result.size();

        System.out.println("Max MSRS : " + maxMS + ", Min MSRS : " + minMS + ", average MSRS : " + averageMS);
        float expectedMSRS = (maxData - minData ) * (maxData - minData) / 12.0f;
        System.out.println("Expected MSRS for the dataset: " + expectedMSRS);
    }
}
