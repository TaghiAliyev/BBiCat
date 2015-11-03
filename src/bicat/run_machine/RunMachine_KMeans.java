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

import bicat.Main.UtilFunctionalities;
import bicat.algorithms.clustering.EmptyClusterException;
import bicat.algorithms.clustering.KMeans;
import bicat.gui.BicatGui;

/**
 * Run Machine implementation for the K-Means clustering algorithm
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunMachine_KMeans extends RunMachine {

    /**
     * This constructor should be used for no-GUI version of the software.
     *
     * @param engine Util Functionality Engine that should be initialized beforehand.
     */
    // ===========================================================================
    public RunMachine_KMeans(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
    }

    /**
     * Constructor used by the GUI element.
     *
     * @param o GUI element
     */
    public RunMachine_KMeans(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    // ===========================================================================
    public void runClustering(ArgumentsKMeans args) {

        // Updated: This might be fine if GUI is used. However, for console applications this is not fine.
        // Reason behind this is that, this creates another thread, and if a user has more code that has to be executed
        // , results of this code might not be available. That is why, for now, this future is switched off.
        // Parts are just commented out. By commenting out the lines, and making sure brackets are well-matched, you
        // can revert-back the changes.

        final KMeans km = new KMeans(args.getNr_clusters(), args
                .getWhat_distance(), args.getNr_max_iterations(), args
                .getNr_replicates(), args.getStart_list(), null, args
                .getEmpty_action());

        if (owner != null) {
            callGUIVersion(args, km);
        } else {
            try {
                System.out.println("Started K-means\n");
                km.runKMeans(args.getMyData());
            } catch (EmptyClusterException e) {
                System.out
                        .println("EmptyClusterException! / No K-means results expected. ");
                System.err.println(e.toString());
            }
            System.out.println("Getting results from K-means...");

            outputBiclusters = null;
            try {
                outputBiclusters = km.getClusters();
            } catch (EmptyClusterException e) {
                for (int i = 0; i < 800000000; i++) {
                    // This is just a lot of iterations that makes sure that, user does not miss the message
                } //wait

                if (owner != null)
                    JOptionPane.showMessageDialog(null, "Empty cluster found. No K-means results expected.\nReduce the number of clusters to be found.");
            }

            if (outputBiclusters != null) {
                try {
                    engine.finishUpClusterRun(args.getDatasetIdx(),
                            outputBiclusters, args.getPreprocessOptions(),
                            "KMeans");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (owner != null)
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "Calculations finished.\nThe results can be found in the cluster results section of the current dataset.");

            }
        }
    }

    public void callGUIVersion(ArgumentsKMeans args, KMeans km) {
        final SwingWorker worker = new SwingWorker() {
            //
            public Object construct() {
                try {
                    System.out.println("Started K-means\n");
                    km.runKMeans(args.getMyData());
                } catch (EmptyClusterException e) {
                    System.out
                            .println("EmptyClusterException! / No K-means results expected. ");
                    System.err.println(e.toString());
                }
                return null;
            }

            public void finished() {
                System.out.println("Getting results from K-means...");

                outputBiclusters = null;
                try {
                    outputBiclusters = km.getClusters();
                } catch (EmptyClusterException e) {
                    for (int i = 0; i < 800000000; i++) {
                        // This is just a lot of iterations that makes sure that, user does not miss the message
                    } //wait

                    if (owner != null)
                        JOptionPane.showMessageDialog(null, "Empty cluster found. No K-means results expected.\nReduce the number of clusters to be found.");
                }

                if (outputBiclusters != null) {
                    try {
                        engine.finishUpClusterRun(args.getDatasetIdx(),
                                outputBiclusters, args.getPreprocessOptions(),
                                "KMeans");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (owner != null)
                        JOptionPane
                                .showMessageDialog(
                                        null,
                                        "Calculations finished.\nThe results can be found in the cluster results section of the current dataset.");

                }

            }

        };
        worker.start();
    }

}