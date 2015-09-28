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

package bicat.run_machine;

import javax.swing.JOptionPane;

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;

/**
 * Run machine implementation for the ISA algorithm.
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunMachine_ISA extends RunMachine {

    /**
     * This constructor is used if no GUI based approach is needed.
     * It shows that method is not used because test case does not try to run ISA method.
     *
     * @param engine The util functionality engine that is already initialized somewhere else.
     */
    // ===========================================================================
    public RunMachine_ISA(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
    }

    public RunMachine_ISA(BicatGui o) {
        owner = o;
        engine = o.getUtilEngine();
    }

    // ===========================================================================
    public native void run_ISA(double[] data, int total_gs, int total_cs,
                               int seed,
                               double t_g, double t_c,
                               int n_fix,
                               int logarithm,
                               int max_size,
                               String out_file);

    static {
//	  System.loadLibrary("libgslcblas"); //do these libs have to be loaded?
//	  System.loadLibrary("libgsl");
        System.loadLibrary("isa");
        System.out.println("isa library loaded ");
    }

    public void runBiclustering(ArgumentsISA isaa) {

        // Updated: This might be fine if GUI is used. However, for console applications this is not fine.
        // Reason behind this is that, this creates another thread, and if a user has more code that has to be executed
        // , results of this code might not be available. That is why, for now, this future is switched off.
        // Parts are just commented out. By commenting out the lines, and making sure brackets are well-matched, you
        // can revert-back the changes.
        if (owner != null) {
            callGUIVersion(isaa);
        } else {
            run_ISA(isaa.getData(), isaa.getGeneNumber(), isaa.getChipNumber(),
                    isaa.getSeed(),
                    isaa.getT_g(), isaa.getT_c(),
                    isaa.getN_fix(),
                    isaa.getLogarithm(),
                    isaa.getMax_size(), isaa.getOutputFile());

            if (MethodConstants.debug) System.out.println("Started ISA in a new thread!\n");

            System.out.println("Getting results from ISA... " + engine.getCurrentDirectoryPath() + "/output.isa");

            computedBiclusters = getOutputBiclusters(engine.getCurrentDirectoryPath() + "/output.isa"); //cca.getOutputFile());

            outputBiclusters = computedBiclusters; // is a LinkedList of BitSet representated-BCs (bicat.runMachine.Bicluster)
            transferList();

            engine.finishUpRun(isaa.getDatasetIdx(), outputBiclusters, isaa.getPreprocessOptions(), "ISA");
        }
    }

    public void callGUIVersion(ArgumentsISA isaa) {
        final SwingWorker worker = new SwingWorker() {

            public Object construct() {

                run_ISA(isaa.getData(), isaa.getGeneNumber(), isaa.getChipNumber(),
                        isaa.getSeed(),
                        isaa.getT_g(), isaa.getT_c(),
                        isaa.getN_fix(),
                        isaa.getLogarithm(),
                        isaa.getMax_size(), isaa.getOutputFile());

                if (MethodConstants.debug) System.out.println("Started ISA in a new thread!\n");

                return null;
            }

            public void finished() {

                System.out.println("Getting results from ISA... " + engine.getCurrentDirectoryPath() + "/output.isa");

                computedBiclusters = getOutputBiclusters(engine.getCurrentDirectoryPath() + "/output.isa"); //cca.getOutputFile());

                outputBiclusters = computedBiclusters; // is a LinkedList of BitSet representated-BCs (bicat.runMachine.Bicluster)
                transferList();

                engine.finishUpRun(isaa.getDatasetIdx(), outputBiclusters, isaa.getPreprocessOptions(), "ISA");
                JOptionPane.showMessageDialog(null, "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");
            }

        };
        worker.start();
    }

}