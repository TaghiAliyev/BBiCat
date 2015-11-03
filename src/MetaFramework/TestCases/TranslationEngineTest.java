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

package MetaFramework.TestCases;

import MetaFramework.PathwayAnalysis;
import MetaFramework.RConnection;
import bicat.biclustering.Bicluster;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.*;

/**
 * This script tries to run Translation code written in R and test it to see how it works and how the results could be interpreted
 *
 * NOTE : Not used right now, as translation is only needed from Illumina to HGNC, which is implemented in Java
 * However, this code could be used as a reference if someone wants to use R libraries and methods for translation
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class TranslationEngineTest {

    public static void main(String[] args) throws Exception {
//        // Getting all the gene names
        String file = "NCI.xml";
        PathwayAnalysis engine = new PathwayAnalysis(file);

        Set<String> genes = engine.getGeneToPathways().keySet();
        System.out.println("Number of genes : " + genes.size());
        String[] geneNames = new String[40];
        geneNames = genes.toArray(geneNames);
//
//        // Reading a sample dataset
//        String fileLocation = "C:/Users/tagi1_000/eclipseWorkspace/LocalCopyBiCat/src/sampleData/ProcessedFirst.txt";
//
//        // Creating BiCat engine that can run the algorithms on the dataset.
//        // If you want to change the parameters, adopt the methods themselves
//        BicatMethods bicatEngine = new BicatMethods(fileLocation);
//
//        // Let's run the algorithm now
//        LinkedList<Bicluster> biclusters = bicatEngine.callBiMax();

        String rScript = "TranslationScript.R";
        RConnection connection = new RConnection(false);
        connection.setUp(rScript);
        // Entrez : org.Hs.eg.db
        // Some random: hgu133plus2.db . Examples for it : "91617_at","78495_at","65585_at", "241834_at","209079_x_at"
        //
        connection.getCode().addRCode("db <- \"hgu133plus2.db\"");
        // Note: this ones do not have any ambiguity. They are 1 to 1 matches. Still looking for ambiguity
        // "91617_at","78495_at","65585_at", "241834_at",
        connection.getCode().addRCode("geneNames <- c( \"209079_x_at\")");
        connection.callRScript("result");
        String entrOrProbe = connection.getRcaller().getParser().getNames().get(0);

        // Getting the symbol
        NodeList symList = connection.getRcaller().getParser().getValueNodes("SYMBOL");
        NodeList entrezList = connection.getRcaller().getParser().getValueNodes(entrOrProbe);
        HashMap<String, ArrayList<String>> entrezToSymbols = new HashMap<String, ArrayList<String>>();

        for (int i = 0; i < symList.getLength(); i++) {
            if (symList.item(i).getChildNodes() != null && symList.item(i).getChildNodes().getLength() > 0) {
                String entrezID = entrezList.item(i).getChildNodes().item(0).getNodeValue();
                String geneName = symList.item(i).getChildNodes().item(0).getNodeValue();
                if (entrezToSymbols.get(entrezID) == null) {
                    ArrayList<String> syms = new ArrayList<String>();
                    syms.add(geneName);
                    entrezToSymbols.put(entrezID, syms);
                } else {
                    ArrayList<String> syms = entrezToSymbols.get(entrezID);
                    syms.add(geneName);
                    entrezToSymbols.put(entrezID, syms);
                }
            }
        }

        // Here is the idea: Filter through the genes and only keep the ones we have
        // Afterwards check if there is still more than two left
        // Let's go through them
        Set<String> allEntrez = entrezToSymbols.keySet();
        for (String tmp : allEntrez) {
            ArrayList<String> allGenes = entrezToSymbols.get(tmp);
            ArrayList<String> actualThere = new ArrayList<String>();
            for (String tmp2 : allGenes) {
//                System.out.println(tmp2);
                // Filter it here
                if (engine.getGeneToPathways().get(tmp2) != null) {
                    actualThere.add(tmp2);
                    for (String pathway : engine.getGeneToPathways().get(tmp2))
                        System.out.println(pathway);
                }
            }
            System.out.println(actualThere.size());
            if (actualThere.size() > 1)
                System.out.println("DAAAAAAANGEEEEEER -> Here we will have the choosing option");
            System.out.println();
        }

    }

}
