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

package bicat.run_machine;

import javax.swing.JOptionPane;

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;

/**
 * Run Machine implementation for the CC algorithm.
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunMachine_CC extends RunMachine {

    // ===========================================================================

    /**
     * This constructor should be used if console-based approach is taken and no GUI is needed
     *
     * @param engine Util functionalities engine which should be initialized beforehand.
     */
    public RunMachine_CC(UtilFunctionalities engine) {
        this.engine = engine;
        owner = null;
    }

    /**
     * Constructor used by the GUI element.
     *
     * @param o, owner frame. Basically, the GUI element which is running right now. Needed for the correct update of the GUI elements
     */
    public RunMachine_CC(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    public native void run_CC(double[] data, int total_gs, int total_cs,
                              // String[] g_names, String[] c_names,
                              int seed, double delta, double alpha, double p_rand_1,
                              double p_rand_2, double p_rand_3, int logarithm, int randomize,
                              int n, String out_file);

    // get the results from a .temp file in the current directory (this file
    // will be removed)

    static {
        // Loading of the library
        System.loadLibrary("cc");
        System.out.println("cc library loaded");
    }

    public void runBiclustering(ArgumentsCC cca) {

        // Original comments:
        // Before calling the Native Method (CC), prepare the calling arguments
        // the parameters needed for CC can be retrieved by the owner:
        // data, tg, tc, seed, delta, alpha, pr1-3, logarithm, randomize, n,
        // out_file

        // Updated: This might be fine if GUI is used. However, for console applications this is not fine.
        // Reason behind this is that, this creates another thread, and if a user has more code that has to be executed
        // , results of this code might not be available. That is why, for now, this future is switched off.
        // Parts are just commented out. By commenting out the lines, and making sure brackets are well-matched, you
        // can revert-back the changes.

        if (owner != null) {
            callGUIVersion(cca);
        } else {
            if (MethodConstants.debug)
                System.out.println("gene number: " + cca.getGeneNumber() + " Chip number: " + cca.getChipNumber());

            run_CC(cca.getData(), cca.getGeneNumber(), cca.getChipNumber(),
                    cca.getSeed(), cca.getDelta(), cca.getAlpha(), cca
                            .getP_rand_1(), cca.getP_rand_2(), cca.getP_rand_3(), cca
                            .getLogarithm(), cca.getRandomize(),
                    cca.getN(), cca.getOutputFile());

            if (MethodConstants.debug) {
                System.out.println("Started CC in a new thread!\n");
            }

            System.out.println("Getting results from CC... "
                    + owner.getUtilEngine().getCurrentDirectoryPath() + "/output.cc");

            computedBiclusters = getOutputBiclusters(owner.getUtilEngine().getCurrentDirectoryPath()
                    + "/output.cc");

            outputBiclusters = computedBiclusters; // is a LinkedList of

            transferList();

            owner.getUtilEngine().finishUpRun(cca.getDatasetIdx(), outputBiclusters, cca
                    .getPreprocessOptions(), "CC");
            JOptionPane
                    .showMessageDialog(
                            null,
                            "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");

        }
    }

    public void callGUIVersion(ArgumentsCC cca) {
        final SwingWorker worker = new SwingWorker() {
            //
            public Object construct() {
                // new RunMachineCC(null).runCC();
                if (MethodConstants.debug)
                    System.out.println("gene number: " + cca.getGeneNumber() + " Chip number: " + cca.getChipNumber());

                run_CC(cca.getData(), cca.getGeneNumber(), cca.getChipNumber(),
                        cca.getSeed(), cca.getDelta(), cca.getAlpha(), cca
                                .getP_rand_1(), cca.getP_rand_2(), cca.getP_rand_3(), cca
                                .getLogarithm(), cca.getRandomize(),
                        cca.getN(), cca.getOutputFile());

                if (MethodConstants.debug) {
                    System.out.println("Started CC in a new thread!\n");
                }

                return null;
            }

            public void finished() {
//
                System.out.println("Getting results from CC... "
                        + owner.getUtilEngine().getCurrentDirectoryPath() + "/output.cc");

                computedBiclusters = getOutputBiclusters(owner.getUtilEngine().getCurrentDirectoryPath()
                        + "/output.cc");

                outputBiclusters = computedBiclusters; // is a LinkedList of
                // BitSet
                // representated-BCs
                // (bicat.runMachine.Bicluster_bitset)

                transferList();

                owner.getUtilEngine().finishUpRun(cca.getDatasetIdx(), outputBiclusters, cca
                        .getPreprocessOptions(), "CC");
                JOptionPane
                        .showMessageDialog(
                                null,
                                "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");

            }
        };
        worker.start();
    }

}
