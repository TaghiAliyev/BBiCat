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

package MetaFramework;

import bicat.biclustering.Bicluster;
import lombok.Data;
import org.w3c.dom.NodeList;
import rcaller.RCaller;
import rcaller.RCode;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Method that will create R connection, get clusters and call R scripts
 * This class calls an R script on a given data. Please change addClusters and some other parts of the code
 * for specific needs when needed. One note: Connection and calling R is not very fast, so it is better to use Java
 * versions of everything whenever possible.
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class RConnection {

    private RCaller rcaller;
    private RCode code;

    public RConnection(boolean streamOutput) {
        // Initializing the needed instances/classes
        this.rcaller = new RCaller();
        this.code = new RCode();
        // Setting the R executable that is used. Change this to your local one!
        rcaller.setRscriptExecutable("C:\\Program Files\\R\\R-3.1.2\\bin\\x64\\Rscript.exe");
        if (streamOutput)
            rcaller.redirectROutputToStream(System.out);
//        rcaller.StopRCallerOnline();
    }

    /**
     * Adding the computed (bi)-clusters to the R environment
     *
     * @param biclusters Computed (bi-)clusters
     */
    public void addClusters(LinkedList<Bicluster> biclusters, Vector<String> geneNames) {
        // Adding clusters to the R environment.
        code.addRCode("whole_list <- list()");
        // So, let's do this: First cluster gets first, second second and third all 3
        int cnt = 0;
        for (Bicluster tmp : biclusters)
        {
                // Now, gotta be careful
            String[] geneCluster = new String[tmp.getGenes().length];
            for (int i = 0; i < geneCluster.length; i++)
            {
                geneCluster[i] = geneNames.get(tmp.getGenes()[i]);
            }

            // geneCluster now contains all the gene names needed
            // We should add it as a list
            code.addStringArray("geneList" + cnt, geneCluster);

            // Now geneList in R contains the names
            code.addRCode("whole_list[[length(whole_list) + 1]] <- geneList" + cnt);
            cnt++;
        }
    }

    public void addCluster(Bicluster bicluster, Vector<String> geneNames) {
        code.addRCode("rm(geneNames)");
        int[] genes = bicluster.getGenes(); // Array with the gene indices
        String[] toAdd = new String[genes.length];
        for (int i = 0; i < genes.length; i++) {
            toAdd[i] = geneNames.get(genes[i]);
        }
        code.addStringArray("geneNames", toAdd);
        // Alright, here is the problem:
        // We have list of lists kind of. So, maybe let's do it that way?

    }

    public void addStringArray(String[] names) {
        code.addStringArray("geneNames", names);
    }


    public void setUp(String script) {
        // Sets up the environment, so that we do not call these codes all the time over and over again
        code.addRCode("chooseCRANmirror(graphics = FALSE, ind = 81)");
        code.addRCode("library(\"methods\")");
        code.addStringArray("link1", new String[]{"biomaRt", "C:/Users/tagi1_000/Desktop/CERN"});

//            String[] geneNames = new String[]{"ADCY6", "ADCY3"};
//            code.addStringArray("geneNames", geneNames);
        code.addStringArray("link", new String[]{script});
    }

    /**
     *
     *
     */
    public void callRScript(String returnVar) {
        // Calling the R script after adding the clusters to the environment
        try {
            // Here we should call the script
            code.addRCode("source(link[1])");

            rcaller.setRCode(code);
            if (returnVar == "")
                rcaller.runOnly();
            else
                rcaller.runAndReturnResult(returnVar);
//            rcaller.runAndReturnResult("counter");

//            rcaller.runOnly();
//            rcaller.runAndReturnResult(returnVar);
            System.out.println(rcaller.getParser().getNames());
//            System.out.println(rcaller.getRCode().getCode());
//            System.out.println(rcaller.getParser().getValueNodes("ENTREZID").item(1).getChildNodes().item(0).getNodeValue());

            if (returnVar != "")
                System.out.println(rcaller.getParser().getNames());
        } catch (Exception e) {
            // Catch the exception here!
            JOptionPane.showMessageDialog(null, "Seems like wrong database name was inputted. Please, consider changing the database name!");
//            e.printStackTrace();
        } finally {
            rcaller.StopRCallerOnline();
            rcaller.stopStreamConsumers();
        }
    }
}
