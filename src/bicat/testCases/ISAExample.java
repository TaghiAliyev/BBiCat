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

import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Dataset;
import bicat.postprocessor.Postprocessor;
import bicat.preprocessor.Preprocessor;
import bicat.run_machine.ArgumentsISA;
import bicat.run_machine.RunMachine_ISA;

import java.io.File;

/**
 * An example of how ISA Method can be used (without GUI)
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class ISAExample {

    public static void main(String[] args) throws Exception {
        UtilFunctionalities engine = new UtilFunctionalities();

        String benchmark1 = "src/sampleData/ProcessedFirst.txt";
        Preprocessor readingEngine = new Preprocessor(engine);
        File file = new File(benchmark1);

        // This will read the file onto the reading engine (pre-processor) and return a dataset
        Dataset dataset = readingEngine.readMainDataFile(file, 1, 1);

        engine.getDatasetList().add(dataset);
        engine.setCurrentDataset(dataset);

        Postprocessor postprocesor = new Postprocessor(engine);

        float[][] dd = engine.getCurrentDataset().getPreData(); // pre.getOriginalData();
        double[] data = new double[dd.length * dd[0].length];
        for (int i = 0; i < dd.length; i++)
            for (int j = 0; j < dd[0].length; j++)
                data[i * dd[0].length + j] = (double) dd[i][j];

        ArgumentsISA argumentsISA = new ArgumentsISA();
        argumentsISA.setData(data);
        argumentsISA.setGeneNumber(dd.length); // 100
        argumentsISA.setChipNumber(dd[0].length); // 50

        // internal:
        // isaa.setMaxSize(100); // max_size == n_fix !
        argumentsISA.setLogarithm(0);

        argumentsISA.setOutputFile("output.isa");

        argumentsISA.setDatasetIdx(engine.getCurrentDatasetIdx());
        argumentsISA.setPreprocessOptions(engine.getPreprocessOptions());
        argumentsISA.setSeed(1); // Random seed
        argumentsISA.setT_g(8);//threshold genes
        argumentsISA.setT_c(15);//threshold chips
        argumentsISA.setN_fix(27); // number of starting points (max number of biclusters)
        argumentsISA.setMax_size(27);

        RunMachine_ISA runMachine_isa = new RunMachine_ISA(engine);
//        PipedOutputStream pipeOut = new PipedOutputStream();
//        PipedInputStream pipeIn = new PipedInputStream(pipeOut);
//        System.setOut(new PrintStream(pipeOut));

        runMachine_isa.runBiclustering(argumentsISA);

        System.out.println(runMachine_isa.getOutputBiclusters());

    }
}
