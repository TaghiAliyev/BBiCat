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

//package bicat.runMachine;
//
//import bicat.gui.BicatGui;
//
//public class RunMachine_OPSM extends RunMachine{
//
//public RunMachine_OPSM(BicatGui gui) {
//		// TODO Auto-generated constructor stub
//	}
//
//public void runBiclustering(Arguments_OPSM opsma) {
//	// TODO Auto-generated method stub
//	
//}
//
//}

package bicat.run_machine;

import javax.swing.JOptionPane;

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.algorithms.opsm.BendorReloadedAlgorithm;
import bicat.gui.BicatGui;

/**
 * Run Machine implementation for the OPSM clustering method.
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunMachine_OPSM extends RunMachine {

    // ===========================================================================
    public RunMachine_OPSM(UtilFunctionalities engine) {
        this.engine = engine;
        owner = null;
    }

    public RunMachine_OPSM(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    // ===========================================================================

    /**
     * Takes a dataset, stores it, runs <code>OPSM</code> on it Its completion
     * is marked by a function call in the <code>finished</code> method in the
     * local <code>SwingWorker</code> object.
     */
    public void runBiclustering(ArgumentsOPSM args) {

        nrGenes = args.getNumberGenes();
        nrChips = args.getNumberChips();

        // create new OPSM object
        final int l = args.getNr_l();
        final BendorReloadedAlgorithm opsm = new BendorReloadedAlgorithm();
        final float[][] matrix = args.getMyData();

        // Updated: This might be fine if GUI is used. However, for console applications this is not fine.
        // Reason behind this is that, this creates another thread, and if a user has more code that has to be executed
        // , results of this code might not be available. That is why, for now, this future is switched off.
        // Parts are just commented out. By commenting out the lines, and making sure brackets are well-matched, you
        // can revert-back the changes.
        if (owner != null) {
            callGUIVersion(l, args, opsm, matrix);
        } else {
            // start BiMax in a separate thread
//		final SwingWorker worker = new SwingWorker() {

//			public Object construct() {
            if (MethodConstants.debug)
                System.out.println("Started OPSM in a new thread!\n"
                        + "Matrix dimensions are: " + matrix.length + " / "
                        + matrix[0].length);
            opsm.run(matrix, l);
//				return null;
//			}

//			public void finished() {
            System.out.println("Getting results from OPSM...");
            computedBiclusters = opsm.getBiclusters();
            outputBiclusters = computedBiclusters;
            engine.finishUpRun(args.getDatasetIdx(), outputBiclusters, args
                    .getPreprocessOptions(), "OPSM");
            JOptionPane.showMessageDialog(null, "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");
        }
    }

    public void callGUIVersion(int l, ArgumentsOPSM args, BendorReloadedAlgorithm opsm, float[][] matrix) {
        // start BiMax in a separate thread
        final SwingWorker worker = new SwingWorker() {

            public Object construct() {
                if (MethodConstants.debug)
                    System.out.println("Started OPSM in a new thread!\n"
                            + "Matrix dimensions are: " + matrix.length + " / "
                            + matrix[0].length);
                opsm.run(matrix, l);
                return null;
            }

            public void finished() {
                System.out.println("Getting results from OPSM...");
                computedBiclusters = opsm.getBiclusters();
                outputBiclusters = computedBiclusters;
                engine.finishUpRun(args.getDatasetIdx(), outputBiclusters, args
                        .getPreprocessOptions(), "OPSM");
                JOptionPane.showMessageDialog(null, "Calculations finished.\nThe results can be found in the bicluster results section of the current dataset.");
            }
        };
        worker.start();
    }

}
