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

package bicat.testCases;

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Dataset;
import bicat.postprocessor.Postprocessor;
import bicat.preprocessor.PreprocessOption;
import bicat.preprocessor.Preprocessor;
import bicat.run_machine.ArgumentsBiMax;
import bicat.run_machine.ArgumentsKMeans;
import bicat.run_machine.RunMachine_BiMax;
import bicat.run_machine.RunMachine_KMeans;

import java.io.File;

/**
 * This class contains test cases which can be used as reference to using BiCat/Bimax without its GUI.
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class BiCatExamples {

    public static void main(String[] args) throws Exception {

        UtilFunctionalities engine = new UtilFunctionalities();

        RunMachine_BiMax runningMachine = new RunMachine_BiMax(engine);

        String benchmark1 = "src/sampleData/ProcessedFirst.txt";
        Preprocessor readingEngine = new Preprocessor(engine);
        File file = new File(benchmark1);

        // This will read the file onto the reading engine (pre-processor) and return a dataset
        Dataset dataset = readingEngine.readMainDataFile(file, 1, 1);

        // Now, once dataset is created, let's run the bi-clustering on it

        // Let's first create some Preprocess option that should be run/executed on our dataset
        PreprocessOption options = new PreprocessOption();
        options.setDo_discretize(true);
        options.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP);
        options.setOnesPercentage(25); // 25% Thresholding
        options.setDiscretizationMode("onesPercentage");

        // Pre-processing the data
        readingEngine.preprocessData(options);

        // After this point, we can already use Bimax and run bi-clustering on the processed data
        // First, let's set some parameters/arguments for the BiMax
        ArgumentsBiMax argumentsBiMax = new ArgumentsBiMax();
        argumentsBiMax.setBinaryData(readingEngine.getDiscreteData());
        argumentsBiMax.setLower_genes(8);
        argumentsBiMax.setLower_chips(15);
        argumentsBiMax.setExtended(false);
        argumentsBiMax.setGeneNumber(readingEngine.getGeneCount());
        argumentsBiMax.setChipNumber(readingEngine.getOriginalChipCount());

        engine.getDatasetList().add(dataset);
        engine.setCurrentDataset(dataset);

        Postprocessor postprocessor = new Postprocessor(engine);

        runningMachine.runBiclustering(argumentsBiMax);

        // How to read the biclusters? Just, use methods from RunMachine class
        System.out.println("Outputting the biclusters: " + runningMachine.getComputedBiclusters().size());

        postprocessor.search(0, 0, 0, "10", "IND4", false);
        System.out.println("Outputting the biclusters: " + runningMachine.getComputedBiclusters().size());

    }
}
