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

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;

import javax.swing.*;

/**
 * Run Machine implementation for the BiMax algorithm.
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunMachine_BiMax extends RunMachine {

    // ===========================================================================
    public RunMachine_BiMax(BicatGui o) {
        super.setOwner(o);
        super.setEngine(o.getUtilEngine());
    }

    public RunMachine_BiMax() {
        super.setOwner(null);
        super.setEngine(new UtilFunctionalities());
    }

    public RunMachine_BiMax(UtilFunctionalities engine) {
        super.setEngine(engine);
        super.setOwner(null);
    }

    public native void run_bimax(int[] data, int total_gs, int total_cs,
                                 int minGenes, int minChips);

    // get the results from a .temp file in the current directory (this file
    // will be removed)

    static {
        System.loadLibrary("bimax");
        System.out.println("bimax library loaded");
    }

    public void runBiclustering(ArgumentsBiMax bima) {

        // Before calling the Native Method (bimax), prepare the calling
        // arguments
        // the parameters needed for bimax can be retrieved by the owner:

        // =======================================================================
        if (owner != null) {
            callGUIVersion(bima);
        } else {
            if (MethodConstants.debug)
                System.out.println("Number of genes: "
                        + bima.getGeneNumber() + " number of Chips : "
                        + bima.getChipNumber() + " MinGenes: " + bima.getLower_genes() + " MinChips: " + bima.getLower_chips());

            run_bimax(bima.getBinaryDataAsVector(), bima.getGeneNumber(),
                    bima.getChipNumber(), bima.getLower_genes(), bima
                            .getLower_chips());
            if (MethodConstants.debug) {
                System.out.println("Started bimax in a new thread!\n");
            }

            System.out.println("Getting results from bimax... "
                    + engine.getCurrentDirectoryPath() + "/bimax.out");

            computedBiclusters = getOutputBiclusters(engine.getCurrentDirectoryPath()
                    + "/bimax.out");

            outputBiclusters = computedBiclusters;
            // is a LinkedList of
            // BitSet
            // representated-BCs
            // (bicat.runMachine.Bicluster_bitset)

            transferList();

            engine.finishUpRun(bima.getDatasetIdx(), outputBiclusters, bima
                    .getPreprocessOptions(), "BiMax");
            if (owner != null)
                JOptionPane
                        .showMessageDialog(
                                null,
                                "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");
        }
    }

    public void callGUIVersion(final ArgumentsBiMax bima) {
        // Sa SwingWorker of BiMax ??
        // start bimax in a separate thread

        final SwingWorker worker = new SwingWorker() {

            public Object construct() {
                if (MethodConstants.debug)
                    System.out.println("Number of genes: "
                            + bima.getGeneNumber() + " number of Chips : "
                            + bima.getChipNumber() + " MinGenes: " + bima.getLower_genes() + " MinChips: " + bima.getLower_chips());

                run_bimax(bima.getBinaryDataAsVector(), bima.getGeneNumber(),
                        bima.getChipNumber(), bima.getLower_genes(), bima
                                .getLower_chips());
                if (MethodConstants.debug) {
                    System.out.println("Started bimax in a new thread!\n");
                }
                return null;
            }

            public void finished() {
                System.out.println("Getting results from bimax... "
                        + engine.getCurrentDirectoryPath() + "/bimax.out");

                computedBiclusters = getOutputBiclusters(engine.getCurrentDirectoryPath()
                        + "/bimax.out");

                outputBiclusters = computedBiclusters;
                // is a LinkedList of
                // BitSet
                // representated-BCs
                // (bicat.runMachine.Bicluster_bitset)

                transferList();

                engine.finishUpRun(bima.getDatasetIdx(), outputBiclusters, bima
                        .getPreprocessOptions(), "BiMax");
                if (owner != null)
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");

            }
        };
        worker.start();
    }
}
