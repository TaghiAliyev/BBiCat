/*
 *                                BBiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                DOI for citing the release : 10.5281/zenodo.33117
 *
 *                                Copyright (c) 2016 Taghi Aliyev, Marco Manca, Alberto Di Meglio
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
import MetaFramework.MatrixFunctions;
import MetaFramework.NCI.NCIInteraction;
import MetaFramework.NCI.NCIMolecule;
import MetaFramework.NCI.NCIPathway;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Class that implements a local, easy version of Bayesian analysis used for verification purposes. Code is same as the
 * one in Bayesian.Java file. This code only uses part of that class, specifically the P and Ghat computations.
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class BayesianVerification {

    // Scenario files
//    private String diffGenes = "C:/Users/tagi1_000/Desktop/Test_organism/example_1/diff_full.txt",
//            notDiffGenes = "C:/Users/tagi1_000/Desktop/Test_organism/example_1/notdiff_full.txt";


    private String diffGenes = "C:/Users/tagi1_000/Desktop/Test_organism/example_2/diff.txt",
            notDiffGenes = "C:/Users/tagi1_000/Desktop/Test_organism/example_2/notdiff_notfull.txt";

    // Information files
    private String geneList = "C:/Users/tagi1_000/Desktop/Test_organism/example_2/genome_notfull.txt";
    private String geneToGO = "C:/Users/tagi1_000/Desktop/Test_organism/gene_to_GO_genemerge.txt";
    private String geneOnto = "C:/Users/tagi1_000/Desktop/Test_organism/gene_ontology.txt";


    public BayesianVerification() throws Exception {
        FileInputStream stream = new FileInputStream(geneList);
        Scanner scanner = new Scanner(stream);
        Set<String> allGenes = new HashSet<String>();
        while (scanner.hasNextLine()) {
            allGenes.add(scanner.nextLine());
        }

        stream = new FileInputStream(geneOnto);
        scanner = new Scanner(stream);

        Set<String> geneOnto = new HashSet<String>();
        while (scanner.hasNextLine()) {
            geneOnto.add(scanner.nextLine().split("\t")[0]);
        }

        stream = new FileInputStream(geneToGO);
        scanner = new Scanner(stream);

        Set<String> iGenes = new HashSet<String>();
        Set<String> jGenes = new HashSet<String>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] words = line.split("\t");
            String geneName = words[0];
            String[] onto = words[1].split(";");
            for (int i = 0; i < onto.length; i++) {
                if (onto[i].substring(onto[i].length() - 1).equalsIgnoreCase("i"))
                    iGenes.add(geneName);
                else
                    jGenes.add(geneName);
            }
        }

        System.out.println("iGenes size : " + iGenes.size() + ", jGenes size : " + jGenes.size());

        stream = new FileInputStream(this.diffGenes);
        scanner = new Scanner(stream);

        Set<String> diff = new HashSet<String>();
        while (scanner.hasNextLine()) {
            diff.add(scanner.nextLine());
        }

        stream = new FileInputStream(this.notDiffGenes);
        scanner = new Scanner(stream);

        Set<String> nonDiff = new HashSet<String>();
        while (scanner.hasNextLine()) {
            nonDiff.add(scanner.nextLine());
        }

        compute(iGenes, jGenes, diff, nonDiff, allGenes);

    }

    public void compute(Set<String> iGenes, Set<String> jGenes, Set<String> diff, Set<String> nonDiff, Set<String> allGenes) throws Exception
    {
        Bayesian bayesian = new Bayesian(NCIMolecule.class, NCIInteraction.class, NCIPathway.class);
        PrintWriter outFile = new PrintWriter("verificationResults1.out");
        int nSim = 1000;

        int nMin = 0;
        int xMin = 0;

        int nObs = diff.size() + nonDiff.size();

        Set<String> genesOnlyPathway = MatrixFunctions.setDifference(iGenes, jGenes);
        System.out.println();
        Set<String> genesNotPathway = MatrixFunctions.setDifference(jGenes, iGenes);

        // Computation of relevant terms
        int x1OnlyPathway = MatrixFunctions.intersection(genesOnlyPathway, diff).size();
        int x0OnlyPathway = MatrixFunctions.intersection(genesOnlyPathway, nonDiff).size();
        int nOnlyPath = genesOnlyPathway.size();
        int x1PathAnd = MatrixFunctions.intersection(iGenes, diff).size() - x1OnlyPathway;
        int x0PathAnd = MatrixFunctions.intersection(iGenes, nonDiff).size() - x0OnlyPathway;
        int nAnd = MatrixFunctions.setDifference(iGenes, genesOnlyPathway).size();
        int x1NoPathway = MatrixFunctions.intersection(genesNotPathway, diff).size();
        int x0NoPathway = MatrixFunctions.intersection(genesNotPathway, nonDiff).size();


        int nNoPath = genesNotPathway.size();
        double gHat = bayesian.GScore(x1OnlyPathway, x1PathAnd, x1NoPathway, x0OnlyPathway, x0PathAnd, x0NoPathway);
//            if (gHat != 0)
//                System.out.println("Ghat is : " + gHat);
        int x1Path = x1OnlyPathway + x1PathAnd;
        outFile.println("Contingency table: ");
        outFile.println("x1OnlyPathway: " + x1OnlyPathway + ", x0OnlyPathway : " + x0OnlyPathway + ", nOnlyPath : " + nOnlyPath + ", x1PathAnd : " + x1PathAnd + ", x0PathAnd : " + x0PathAnd + ", nAnd : " + nAnd + ", x1NoPathway : " + x1NoPathway + ", x0NoPathway : " + x0NoPathway + " , nNoPath : " + nNoPath);
        outFile.println("Ghat is : " + gHat);
//            System.out.println("Genes in Pathways : " + genesInPathway.size() + " , x1Path : " + x1Path);
        if (!(iGenes.size() < nMin || x1Path < xMin || gHat <= 0)) {

//                System.out.println("Not skipping");
            double[] gObs = new double[nSim];
            // Do not skip the loop, still continue
            if (iGenes.size() > (x1OnlyPathway + x0OnlyPathway + x1PathAnd + x0PathAnd)) {
                    System.out.println("In this weird case");
                ArrayList<Double> X1onlyPath = bayesian.RPosterior(nSim, x1OnlyPathway, x1OnlyPathway + x0OnlyPathway, nOnlyPath);
                System.out.println("First rposterior");
                double[] X0onlyPath = MatrixFunctions.constantMinusVector(nOnlyPath, X1onlyPath);
                System.out.println("Matrix done");
                ArrayList<Double> X1andPath = bayesian.RPosterior(nSim, x1PathAnd, x1PathAnd + x0PathAnd, nAnd);
                System.out.println("First rposterior");
                double[] X0andPath = MatrixFunctions.constantMinusVector(nAnd, X1andPath);
                System.out.println("Matrix done");
                ArrayList<Double> X1noPath = bayesian.RPosterior(nSim, x1NoPathway, x1NoPathway + x0NoPathway, nNoPath);
                System.out.println("First rposterior");
                double[] X0noPath = MatrixFunctions.constantMinusVector(nNoPath, X1noPath);
                System.out.println("Matrix done");
                System.out.println("into the gscore matrix computations");
                // gObs will be used for the comparison vs simulation results
                gObs = bayesian.GScoreMatrix(X1onlyPath, X1andPath, X1noPath, X0onlyPath, X0andPath, X0noPath);
//                    System.out.println("For debug");
            } else {
                System.out.println("Debug purposes");
                double X1onlyPath = x1OnlyPathway;
                double X0onlyPath = nOnlyPath - X1onlyPath;
                double X1andPath = x1OnlyPathway;
                double X0andPath = nAnd - X1andPath;
                double X1noPath = x1NoPathway;
                double X0noPath = nNoPath - X1noPath;
                for (int k = 0; k < nSim; k++)
                    gObs[k] = gHat;
            }
            double[] gRand = new double[nSim];
            // Random simulations loop.
            // NOTE: As you will see in the comments in the loop, some of the variables actually should be replaced
            // in case there is an existence of knowledge about observability of certain genes
            for (int j = 0; j < nSim; j++) {
                System.out.println("At simulation : " + j);
                // Simulation loop
                int n = Math.min(bayesian.poisson(diff.size()), nObs); // 2nd argument is actually number of observed genes

                Set<String> diffRandom = new HashSet<String>();
                Random random = new Random();
                String[] allGeneNames = new String[allGenes.size()];
                allGeneNames = allGenes.toArray(allGeneNames);

                while (diffRandom.size() != n) {

                    diffRandom.add(allGeneNames[random.nextInt(allGenes.size())]);
                }
                Set<String> notDiffRandom = MatrixFunctions.setDifference(allGenes, diffRandom);
                int x1OnlyPathR = MatrixFunctions.intersection(genesOnlyPathway, diffRandom).size();
                int x0OnlyPathR = MatrixFunctions.intersection(genesOnlyPathway, notDiffRandom).size();
                int nOnlyPathR = genesOnlyPathway.size();

                int x1AndPathR = MatrixFunctions.intersection(iGenes, diffRandom).size() - x1OnlyPathR;
                int x0AndPathR = MatrixFunctions.intersection(iGenes, notDiffRandom).size() - x0OnlyPathR;
                int nPathAndR = MatrixFunctions.setDifference(iGenes, genesOnlyPathway).size();

                int x1NoPathR = MatrixFunctions.intersection(genesNotPathway, diffRandom).size();
                int x0NoPathR = MatrixFunctions.intersection(genesNotPathway, notDiffRandom).size();
                int nNoPathR = genesNotPathway.size();

                ArrayList<Double> X1onlyPathR = bayesian.RPosterior(1, x1OnlyPathR, x1OnlyPathR + x0OnlyPathR, nOnlyPathR);
                double[] X0onlyPathR = new double[X1onlyPathR.size()];
                for (int k = 0; k < X0onlyPathR.length; k++) {
                    X0onlyPathR[k] = nOnlyPathR - X1onlyPathR.get(k);
                }
                ArrayList<Double> X1andPathR = bayesian.RPosterior(1, x1AndPathR, x1AndPathR + x0AndPathR, nPathAndR);
                double[] X0andPathR = new double[X1andPathR.size()];
                for (int k = 0; k < X0andPathR.length; k++) {
                    X0andPathR[k] = nPathAndR - X1andPathR.get(k);
                }
                ArrayList<Double> X1noPathR = bayesian.RPosterior(1, x1NoPathR, x1NoPathR + x0NoPathR, nNoPathR);
                double[] X0noPathR = new double[X1noPathR.size()];
                for (int k = 0; k < X1noPathR.size(); k++) {
                    X0noPathR[k] = nNoPathR - X1noPathR.get(k);
                }
                gRand[j] = bayesian.GScoreMatrix(X1onlyPathR, X1andPathR, X1noPathR, X0onlyPathR, X0andPathR, X0noPathR)[0];
            }
            // Simulations are done, so let's start comparing the results
            int cnt = 0;
            for (int j = 0; j < nSim; j++)
                if (gRand[j] >= gObs[j])
                    cnt++;
            System.out.println("Cnt is : " + cnt);
            outFile.println("Results for : " + "i");
            double result = (cnt + 0.0) / (nSim + 0.0); // 0.0 is needed to push for the double division
            if (result <= 0.05)
                outFile.println("Pathway with name : " + "i" + " has P of : " + result);

            // computing error bars
            double errorLeft = Math.min(quantile(gObs, 0.05), gHat);
            double errorRight = Math.max(quantile(gObs, 0.95), gHat);
            outFile.println("P value : " + result);
            outFile.println("G hat : " + gHat);
            outFile.println("Errorbar : [" + errorLeft + ", " + errorRight + "]");
            outFile.println("From the diff expressed genes, " + (x1PathAnd + x1OnlyPathway) + " were in the pathway");
        } else {
            outFile.println("Results for : " + "i");
            outFile.println("GHat : " + gHat);
            outFile.println("From the diff expressed genes, " + (x1PathAnd + x1OnlyPathway) + " were in the pathway");

        }
        outFile.close();
    }

    public static double quantile(double[] data, double p) {
        double m = 1 - p;
        int j = (int) Math.floor(data.length * p + m);
        double gamma = data.length * p + m - j;

        Arrays.sort(data);
        double res = (1 - gamma) * data[j - 1] + gamma * data[j];
        return res;
    }

    public static void main(String[] args) throws Exception {
        BayesianVerification engine = new BayesianVerification();
    }

}
