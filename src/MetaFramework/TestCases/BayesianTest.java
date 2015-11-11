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

package MetaFramework.TestCases;

import MetaFramework.Bayesian;
import MetaFramework.BicatMethods;
import MetaFramework.NCI.PathwayAnalysisMixing;
import bicat.biclustering.Bicluster;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * This class contains a test case use of a Bayesian statistics
 * Test cases are generated by hand more or less
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class BayesianTest {

    /**
     * First test to see if the functionality works.
     *
     * @throws Exception
     */
    @Test
    public void simpleTest() throws Exception {
        // In this test case, used dataset is a hand made text file
        // Gene names were randomly selected from the NCI database
        // This test case is mainly for seeing the use of the Bayesian method for enrichment assessment
        String dataFile = "src/sampleData/BayesTestHandMade.txt";

        // Reading the pathway information from NCI Curated Database
        String file = "NCI.xml";
        PathwayAnalysisMixing engine = new PathwayAnalysisMixing(file);
//        engine.parse();
        Set<PathwayAnalysisMixing.Molecule> allGenes = engine.getGeneToPath().keySet();
        Assert.assertEquals(allGenes == null, false);
        Assert.assertEquals(allGenes.size() > 0, true);
        // Performing biclustering
        BicatMethods bicatEngine = new BicatMethods(dataFile);

        // Let's run the algorithm now
        LinkedList<Bicluster> biclusters = bicatEngine.callBiMax(false, 40, 2, 2);

        System.out.println("Biclustering results: ");
        for (Bicluster tmp : biclusters) {
            int[] genes = tmp.getGenes();
            int[] chips = tmp.getChips();
            System.out.println("Involved genes");
            for (int i = 0; i < genes.length; i++) {
                System.out.println(bicatEngine.getData().getGeneName(genes[i]));
            }

            System.out.println();
            System.out.println("Involved chips");
            for (int i = 0; i < chips.length; i++) {
                System.out.println(bicatEngine.getData().getChipName(chips[i]));
            }
            System.out.println("---------------------");
        }

        Bayesian bayesian = new Bayesian();

        System.out.println("\n\n\n\n");
        System.out.println("Bayesian analysis results!!!");

        for (Bicluster tmp : biclusters) {
//            System.out.println("New bicluster!!!\n");
            bayesian.compute(tmp, engine, bicatEngine.getData(), 3);
//            System.out.println("--------------\n\n");
        }
        bayesian.closeFile();
    }

    /**
     * This test case will check the differentially expressed gene computation
     *
     * @throws Exception
     */
    @Test
    public void testDiff() throws Exception {
        String dataFile = "src/sampleData/dataSample_2.txt";

        // Reading the pathway information from NCI Curated Database
        String file = "NCI.xml";
        PathwayAnalysisMixing engine = new PathwayAnalysisMixing(file);
//        engine.parse();
        Set<PathwayAnalysisMixing.Molecule> allGenes = engine.getGeneToPath().keySet();
        Set<String> names = new HashSet<String>();
        for (PathwayAnalysisMixing.Molecule mol : allGenes)
            names.add(mol.getName());
        String[] geneNames = new String[40];
        geneNames = names.toArray(geneNames);

        // Performing biclustering
        BicatMethods bicatEngine = new BicatMethods(dataFile);

        // Let's run the algorithm now
        LinkedList<Bicluster> biclusters = bicatEngine.callBiMax(true, 10, 4, 6);

        System.out.println("Biclustering results: " + biclusters.size());
        Vector<String> newGeneNames = new Vector<String>();
        Random rand = new Random();
        for (int i = 0; i < bicatEngine.getData().getGeneCount(); i++) {
            int randN = rand.nextInt(geneNames.length);
            newGeneNames.add(geneNames[randN]);
        }
        bicatEngine.getData().setGeneNames(newGeneNames);
        for (Bicluster tmp : biclusters) {
            int[] genes = tmp.getGenes();
            int[] chips = tmp.getChips();
            System.out.println("Involved genes");
            for (int i = 0; i < genes.length; i++) {
                System.out.println(bicatEngine.getData().getGeneName(genes[i]));
            }

            System.out.println();
            System.out.println("Involved chips");
            for (int i = 0; i < chips.length; i++) {
                System.out.println(bicatEngine.getData().getChipName(chips[i]));
            }
            System.out.println("---------------------");
        }

        Bayesian bayesian = new Bayesian();

        System.out.println("\n\n\n\n");
        System.out.println("Bayesian analysis results!!!");

        for (Bicluster tmp : biclusters) {
            System.out.println("New bicluster!!!\n");
            // -1 means differential expression
            bayesian.compute(tmp, engine, bicatEngine.getData(), -1);
            System.out.println("--------------\n\n");
        }

        bayesian.closeFile();
    }

    @Test
    public void testPaper() throws Exception
    {
        String dataFile = "src/sampleData/dataSample_2.txt";

        // Reading the pathway information from NCI Curated Database
        String file = "NCI.xml";
        PathwayAnalysisMixing engine = new PathwayAnalysisMixing(file);
//        engine.parse();
        Set<PathwayAnalysisMixing.Molecule> allGenes = engine.getGeneToPath().keySet();
        Set<String> names = new HashSet<String>();
        for (PathwayAnalysisMixing.Molecule mol : allGenes)
            names.add(mol.getName());
        String[] geneNames = new String[40];
        geneNames = names.toArray(geneNames);

        // Performing biclustering
        BicatMethods bicatEngine = new BicatMethods(dataFile);

        // Let's run the algorithm now
        LinkedList<Bicluster> biclusters = bicatEngine.callBiMax(true, 10, 4, 6);

        System.out.println("Biclustering results: " + biclusters.size());
        Vector<String> newGeneNames = new Vector<String>();
        Random rand = new Random();
        for (int i = 0; i < bicatEngine.getData().getGeneCount(); i++) {
            int randN = rand.nextInt(geneNames.length);
            newGeneNames.add(geneNames[randN]);
        }
        bicatEngine.getData().setGeneNames(newGeneNames);
        for (Bicluster tmp : biclusters) {
            int[] genes = tmp.getGenes();
            int[] chips = tmp.getChips();
            System.out.println("Involved genes");
            for (int i = 0; i < genes.length; i++) {
                System.out.println(bicatEngine.getData().getGeneName(genes[i]));
            }

            System.out.println();
            System.out.println("Involved chips");
            for (int i = 0; i < chips.length; i++) {
                System.out.println(bicatEngine.getData().getChipName(chips[i]));
            }
            System.out.println("---------------------");
        }

        Bayesian bayesian = new Bayesian();

        System.out.println("\n\n\n\n");
        System.out.println("Bayesian analysis results!!!");

        for (Bicluster tmp : biclusters) {
            System.out.println("New bicluster!!!\n");
            // -1 means differential expression
            bayesian.compute(tmp, engine, bicatEngine.getData(), -1);
            System.out.println("--------------\n\n");
        }

        bayesian.closeFile();
    }
}
