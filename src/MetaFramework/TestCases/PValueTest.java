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
import MetaFramework.NCI.NCIInteraction;
import MetaFramework.NCI.NCIMolecule;
import MetaFramework.NCI.NCIPathway;
import bicat.biclustering.Bicluster;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * These test cases try to use the p-value method from Bayesian.java class and make sure they return correct values
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class PValueTest {

    @Test
    public void test1() throws Exception {
        double t1 = 3.46; // From some online presentation
        int df = 1;

        Bayesian engine = new Bayesian(NCIMolecule.class, NCIInteraction.class, NCIPathway.class);

        Assert.assertEquals(0.06, engine.computePValue(t1, df),0.01);
    }

    @Test
    public void test2() throws Exception
    {
        // Let's run a test on the whole dataset
        String dataFile = "src/sampleData/dataSample_2.txt";
        BicatMethods bicatEngine = new BicatMethods(dataFile);

        Bayesian bayesian = new Bayesian(NCIMolecule.class, NCIInteraction.class, NCIPathway.class);
        LinkedList<Bicluster> biclusters = bicatEngine.callBiMax(true, 25, 6, 10);

        int[][] discrData = bicatEngine.getReadingEngine().getDiscretizedData();
        for (int i = 0; i < discrData.length; i++)
        {
            for (int j = 0; j < discrData[0].length; j++)
            {
                System.out.print(discrData[i][j] + ",");
            }
            System.out.println();
        }

        System.out.println("Number of biclusters : " + biclusters.size());
        for (Bicluster tmp : biclusters)
        {
            int[] genes = tmp.getGenes();
            int[] chips = tmp.getChips();
            System.out.println("Involved genes");
            for (int i = 0; i < genes.length; i++)
            {
                System.out.println(bicatEngine.getData().getGeneName(genes[i]));
            }

            System.out.println();
            System.out.println("Involved chips");
            for (int i = 0; i < chips.length; i++)
            {
                System.out.println(bicatEngine.getData().getChipName(chips[i]));
            }
            System.out.println("---------------------");
        }
        ArrayList<String> diffGenes = bayesian.calculateDiff(biclusters.get(0), -1, bicatEngine.getData(), 10.0);

        for (String tmp : diffGenes)
            System.out.println("Diff expressed gene : " + tmp);
    }

}
