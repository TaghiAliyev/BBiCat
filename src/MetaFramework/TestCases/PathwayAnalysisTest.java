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

import MetaFramework.BicatMethods;
import MetaFramework.PathwayAnalysisMixing;
import bicat.biclustering.Bicluster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Showcase of how pathway parser could be used in order to see what pathways are involved with the biclusters
 * NOTE : Some gene names are changed locally in order to be able to show the functionality and use of the parser
 * In real cases, most of the genes won't be even related to NCI Pathways, so expected list should be a bit smaller
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class PathwayAnalysisTest {

    public static void main(String[] args) throws Exception {
        // Let's read the pathway information first.
        String file = "NCI.xml";
        long start = System.currentTimeMillis();
        PathwayAnalysisMixing engine = new PathwayAnalysisMixing(file);
        long end = System.currentTimeMillis();
        System.out.println("Took : " + (end - start) + " ms");
        Set<PathwayAnalysisMixing.Molecule> mols = engine.getGeneToPath().keySet();
        Set<String> genes = new HashSet<String>();
        for (PathwayAnalysisMixing.Molecule mol : mols)
            genes.add(mol.getName());
        String[] geneNames = new String[40];
        geneNames = genes.toArray(geneNames);
//
        for (String tmp : genes) {
            System.out.println("Gene name : " + tmp);
        }
//
        ArrayList<PathwayAnalysisMixing.Pathway> pathways = engine.getGeneToPath().get(new PathwayAnalysisMixing.Molecule(0, "ADCY3", false, null));

        for (PathwayAnalysisMixing.Pathway tmp : pathways) {
            System.out.println("Pathway named:" + tmp.getName() + ", contains ADCY3 gene");
        }

        String fileLocation = "src/sampleData/ProcessedFirst.txt";

        // Creating BiCat engine that can run the algorithms on the dataset.
        // If you want to change the parameters, adopt the methods themselves
        BicatMethods bicatEngine = new BicatMethods(fileLocation);

        // Let's run the algorithm now
        LinkedList<Bicluster> biclusters = bicatEngine.callBiMax(true, 25, 8, 15);
        Bicluster oneSample;
        int[] toFetch;
        for (int j = 0; j < biclusters.size(); j++) {
            oneSample = biclusters.get(j);
            toFetch = oneSample.getGenes();
            for (int i = 0; i < toFetch.length; i++) {
                // This call will be updated. As in the sample data, gene names are just numbers, we do this
                ArrayList<PathwayAnalysisMixing.Pathway> pathways2 = engine.getGeneToPath().get(new PathwayAnalysisMixing.Molecule(0, geneNames[toFetch[i]], false, null));
                for (PathwayAnalysisMixing.Pathway tmp : pathways2) {
                    System.out.println("Pathway named : " + tmp.getName() + " contains gene named " + geneNames[toFetch[i]]);
                }
            }
            System.out.println();
            System.out.println("------------------------------------------------");
        }
    }
}
