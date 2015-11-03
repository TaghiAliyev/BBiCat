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

package bicat.run_machine;

import javax.swing.JOptionPane;

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;

/**
 * Run Machine implementation for the XMotifs Clustering algorithm.
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunMachine_XMotifs extends RunMachine {

    /**
     * Constructor that should be used by the non-GUI version of the software.
     * Please refer to the test case in order to see how to use it.
     *
     * @param engine Util Functionalities Engine that should be initialized somewhere else
     */
    // ===========================================================================
    public RunMachine_XMotifs(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
    }

    /**
     * Constructor used by the GUI element
     *
     * @param o GUI element
     */
    public RunMachine_XMotifs(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    // ===========================================================================
    public native void run_xMotifs(double[] data, int total_gs, int total_cs,
                                   int seed, double max_p_value, double alpha, int n_s, int n_d,
                                   int s_d, int max_length, int logarithm, int max_size,
                                   String out_file);

    static {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println("xmotifs library loaded");
        System.loadLibrary("xmotifs"); // should distinguish between the
        // platforms ...
    }

    public void runBiclustering(ArgumentsXMotifs xma) {

        // Updated: This might be fine if GUI is used. However, for console applications this is not fine.
        // Reason behind this is that, this creates another thread, and if a user has more code that has to be executed
        // , results of this code might not be available. That is why, for now, this future is switched off.
        // Parts are just commented out. By commenting out the lines, and making sure brackets are well-matched, you
        // can revert-back the changes.

        if (owner != null) {
            callGUIVersion(xma);
        } else {
            run_xMotifs(xma.getData(), xma.getGeneNumber(), xma
                            .getChipNumber(), xma.getSeed(), xma.getMax_p_value(),
                    xma.getAlpha(), xma.getN_s(), xma.getN_d(), xma.getS_d(),
                    xma.getMax_length(), xma.getLogarithm(), xma
                            .getMax_size(), xma.getOutputFile());

            if (MethodConstants.debug)
                System.out.println("Started xMotifs in a new thread!\n");

//				return null;
//			}

//			public void finished() {

            System.out.println("Getting results from xMotifs... "
                    + engine.getCurrentDirectoryPath() + "/output.xMotifs");

            computedBiclusters = getOutputBiclusters(engine.getCurrentDirectoryPath()
                    + "/output.xMotifs");

            outputBiclusters = computedBiclusters; // is a LinkedList of
            // BitSet
            // representated-BCs
            // (bicat.runMachine.Bicluster)
            transferList();

            engine.finishUpRun(xma.getDatasetIdx(), outputBiclusters, xma
                    .getPreprocessOptions(), "xMotif");
            JOptionPane
                    .showMessageDialog(
                            null,
                            "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");
        }
    }

    public void callGUIVersion(ArgumentsXMotifs xma) {
        final SwingWorker worker = new SwingWorker() {

            public Object construct() {
                run_xMotifs(xma.getData(), xma.getGeneNumber(), xma
                                .getChipNumber(), xma.getSeed(), xma.getMax_p_value(),
                        xma.getAlpha(), xma.getN_s(), xma.getN_d(), xma.getS_d(),
                        xma.getMax_length(), xma.getLogarithm(), xma
                                .getMax_size(), xma.getOutputFile());

                if (MethodConstants.debug)
                    System.out.println("Started xMotifs in a new thread!\n");

                return null;
            }

            public void finished() {

                System.out.println("Getting results from xMotifs... "
                        + engine.getCurrentDirectoryPath() + "/output.xMotifs");

                computedBiclusters = getOutputBiclusters(engine.getCurrentDirectoryPath()
                        + "/output.xMotifs");

                outputBiclusters = computedBiclusters; // is a LinkedList of
                // BitSet
                // representated-BCs
                // (bicat.runMachine.Bicluster)
                transferList();

                engine.finishUpRun(xma.getDatasetIdx(), outputBiclusters, xma
                        .getPreprocessOptions(), "xMotif");
                JOptionPane
                        .showMessageDialog(
                                null,
                                "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");
            }

        };
        worker.start();
    }

}