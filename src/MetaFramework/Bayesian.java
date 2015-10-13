/*
 *                                BBiCat is a toolbox that combines different Biological Post-Analytic tools, Bi-Clustering and
 *                                clustering techniques in it, which can be applied on a given dataset. This software is the
 *                                modified version of the original BiCat Toolbox implemented at ETH Zurich by Simon
 *                                Barkow, Eckart Zitzler, Stefan Bleuler, Amela Prelic and Don Frick.
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
import bicat.biclustering.Dataset;
import com.sun.security.cert.internal.x509.X509V1CertImpl;
import lombok.Data;
import org.omg.PortableServer.RequestProcessingPolicyOperations;

import javax.swing.*;
import java.util.*;

/**
 * Class that will contain the bayesian statistics computations
 * Method at : http://www.biomedcentral.com/content/pdf/1471-2105-7-86.pdf
 * <p>
 * Will be implemented next week (21.09 - 28.09, 2015)
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class Bayesian {


    private int nSim;

    public Bayesian() {
        nSim = 500; // Default is 500
    }

    public Bayesian(int nSim) {
        this.nSim = nSim;
    }

    public void compute(Bicluster bicluster, PathwayAnalysis engine, Dataset dataset, int colChoice) {
        // Idea is to compute Bayesian statistics for the given gene list/cluster and term
        ArrayList<String> geneNames = new ArrayList<String>();
        System.out.println(bicluster.getGenes().length);
        for (int i = 0; i < bicluster.getGenes().length; i++) {
            geneNames.add(dataset.getGeneName(bicluster.getGenes()[i]));
        }
//        System.out.println(geneNames.size());
        Set<String> allPathways = getAllPath(geneNames, engine);
        ArrayList<String> diffGenes = new ArrayList<String>();
        if (colChoice == -1) {
            // Diff expression
//            System.out.println("Differentially expressed computations are starting noooow....");
            // TODO: Given a set of genes, find all related terms/pathways, and then for each
            diffGenes = calculateDiff(bicluster, colChoice, dataset, -1.0); // Threshold does not matter here
//            System.out.println("Number of differentially expressed genes : " + diffGenes.size());
        } else {
            // Column based differentiation
            // Make sure it is binary (0/1 or true/false)
            // And 1 basically means differentiated
            String threshold = Double.toString(Double.MAX_VALUE);
            if (!checkForBinary(dataset, colChoice)) {
                // If not binary, then ask for a threshold (smaller -> notDiff, higher -> Diff)
                threshold = JOptionPane.showInputDialog(null, "Column values are not binary! Please input a threshold for differentiation:");
            }
            // Now that we know the threshold, we should be fine
            diffGenes = calculateDiff(bicluster, colChoice, dataset, Double.parseDouble(threshold));
        }
        Set<String> notDiffGenes = MatrixFunctions.setDifference(geneNames, new HashSet<String>(diffGenes));
        for (String tmp : allPathways) {
            ArrayList<String> genesInPathway = engine.getPathwayToGenes().get(tmp);
            int nMin = 1;//(int) (geneNames.size() * 0.2); // At least 20 percent of input genes should be part of the pathway
            int xMin = 1; // Let's say at least 1 should be diff expressed
            Set<String> genesInOther = new HashSet<String>();
            for (String tmp2 : allPathways) {
                if (!tmp.equalsIgnoreCase(tmp2))
                    genesInOther.addAll(engine.getPathwayToGenes().get(tmp2));
            }

            Set<String> genesOnlyPathway = MatrixFunctions.setDifference(genesInPathway, genesInOther);
            Set<String> genesNotPathway = MatrixFunctions.setDifference(genesInOther, genesInPathway);

            int x1OnlyPathway = MatrixFunctions.intersection(genesOnlyPathway, diffGenes).size();
            int x0OnlyPathway = MatrixFunctions.intersection(genesOnlyPathway, notDiffGenes).size();
            int nOnlyPath = genesOnlyPathway.size();
            int x1PathAnd = MatrixFunctions.intersection(genesInPathway, diffGenes).size() - x1OnlyPathway;
            int x0PathAnd = MatrixFunctions.intersection(genesInPathway, notDiffGenes).size() - x0OnlyPathway;
            int nAnd = MatrixFunctions.setDifference(genesInPathway, genesOnlyPathway).size();
            int x1NoPathway = MatrixFunctions.intersection(genesNotPathway, diffGenes).size();
            int x0NoPathway = MatrixFunctions.intersection(genesNotPathway, notDiffGenes).size();
            int nNoPath = genesNotPathway.size();
            double gHat = GScore(x1OnlyPathway, x1PathAnd, x1NoPathway, x0OnlyPathway, x0PathAnd, x0NoPathway);
            int x1Path = x1OnlyPathway + x1PathAnd;

            if (!(genesInPathway.size() < nMin) && !(x1Path < xMin) && !(gHat <= 0)) {
//                System.out.println("Not skipping");
                double[] gObs = new double[nSim];
                // Do not skip the loop, still continue
                if (genesInPathway.size() > (x1OnlyPathway + x0OnlyPathway + x1PathAnd + x0PathAnd)) {
                    ArrayList<Double> X1onlyPath = RPosterior(nSim, x1OnlyPathway, x1OnlyPathway + x0OnlyPathway, nOnlyPath);
                    double[] X0onlyPath = MatrixFunctions.constantMinusVector(nOnlyPath, X1onlyPath);
                    ArrayList<Double> X1andPath = RPosterior(nSim, x1PathAnd, x1PathAnd + x0PathAnd, nAnd);
                    double[] X0andPath = MatrixFunctions.constantMinusVector(nAnd, X1andPath);
                    ArrayList<Double> X1noPath = RPosterior(nSim, x1NoPathway, x1NoPathway + x0NoPathway, nNoPath);
                    double[] X0noPath = MatrixFunctions.constantMinusVector(nNoPath, X1noPath);
                    // gObs will be used for the comparison vs simulation results
                    gObs = GScoreMatrix(X1onlyPath, X1andPath, X1noPath, X0onlyPath, X0andPath, X0noPath);

                } else {
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
                for (int j = 0; j < nSim; j++) {
                    // Simulation loop
                    int n = Math.min(poisson(diffGenes.size()), geneNames.size()); // 2nd argument is actually number of observed genes
                    // TODO : Look into observability (not from control theory) of genes. Some genes won't be observable or observed

                    Set<String> diffRandom = new HashSet<String>();
                    Random random = new Random();
                    for (int i = 0; i < n; i++) {
                        diffRandom.add(geneNames.get(random.nextInt(geneNames.size())));
                    }
                    Set<String> notDiffRandom = MatrixFunctions.setDifference(geneNames, diffRandom);
                    int x1OnlyPathR = MatrixFunctions.intersection(genesOnlyPathway, diffRandom).size();
                    int x0OnlyPathR = MatrixFunctions.intersection(genesOnlyPathway, notDiffRandom).size();
                    int nOnlyPathR = genesOnlyPathway.size();

                    int x1AndPathR = MatrixFunctions.intersection(genesInPathway, diffRandom).size() - x1OnlyPathR;
                    int x0AndPathR = MatrixFunctions.intersection(genesInPathway, notDiffRandom).size() - x0OnlyPathR;
                    int nPathAndR = MatrixFunctions.setDifference(genesInPathway, genesOnlyPathway).size();

                    int x1NoPathR = MatrixFunctions.intersection(genesNotPathway, diffRandom).size();
                    int x0NoPathR = MatrixFunctions.intersection(genesNotPathway, notDiffRandom).size();
                    int nNoPathR = genesNotPathway.size();

                    ArrayList<Double> X1onlyPathR = RPosterior(1, x1OnlyPathR, x1OnlyPathR + x0OnlyPathR, nOnlyPathR);
                    double[] X0onlyPathR = new double[X1onlyPathR.size()];
                    for (int k = 0; k < X0onlyPathR.length; k++) {
                        X0onlyPathR[k] = nOnlyPathR - X1onlyPathR.get(k);
                    }
                    ArrayList<Double> X1andPathR = RPosterior(1, x1AndPathR, x1AndPathR + x0AndPathR, nPathAndR);
                    double[] X0andPathR = new double[X1andPathR.size()];
                    for (int k = 0; k < X0andPathR.length; k++) {
                        X0andPathR[k] = nPathAndR - X1andPathR.get(k);
                    }
                    ArrayList<Double> X1noPathR = RPosterior(1, x1NoPathR, x1NoPathR + x0NoPathR, nNoPathR);
                    double[] X0noPathR = new double[X1noPathR.size()];
                    for (int k = 0; k < X1noPathR.size(); k++) {
                        X0noPathR[k] = nNoPathR - X1noPathR.get(k);
                    }
                    gRand[j] = GScoreMatrix(X1onlyPathR, X1andPathR, X1noPathR, X0onlyPathR, X0andPathR, X0noPathR)[0];
                }
                // Simulations are done, so let's start comparing the results
                int cnt = 0;
                for (int j = 0; j < nSim; j++)
                    if (gRand[j] >= gObs[j])
                        cnt++;
                double result = (cnt + 0.0) / (nSim + 0.0); // 0.0 is needed to push for the double division
                if (result <= 0.05)
                    System.out.println("Pathway with name : " + tmp + " has p-value of : " + result);

                // computing error bars
                double errorLeft = Math.min(quantile(gObs, 0.05), gHat);
                double errorRight = Math.max(quantile(gObs, 0.95), gHat);
                System.out.println("G hat : " + gHat);
                System.out.println("Errorbar : [" + errorLeft + ", " + errorRight + "]");
            } else
                System.out.println("GHat : " + gHat);
            System.out.println("-------------------------------");
        }


    }

    /**
     * Implements the quantile function (type 7 from R implementation) where m = 1 - p
     *
     * @param data
     * @param p
     * @return
     */
    public static double quantile(double[] data, double p) {
        double m = 1 - p;
        int j = (int) Math.floor(data.length * p + m);
        double gamma = data.length * p + m - j;

        Arrays.sort(data);
        double res = (1 - gamma) * data[j - 1] + gamma * data[j];
        return res;
    }

    public int poisson(double l) {
        Random random = new Random();
        double limit = Math.exp(-l);
        double prod = random.nextDouble();
        int n = 0;
        for (n = 0; prod >= limit; n++)
            prod *= random.nextDouble();

        return n;
    }

    /**
     * GScore function which takes vectors as input. Why these specifically? Needed it for Bayesian computations
     * Could have also used loop and call gscore repeatedly
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param f
     * @return
     */
    public double[] GScoreMatrix(ArrayList<Double> a, ArrayList<Double> b, ArrayList<Double> c, double[] d, double[] e, double[] f) {
        double[] res = new double[a.size()];

        for (int i = 0; i < a.size(); i++) {
            double p = a.get(i) * (e[i] + f[i]) + b.get(i) * f[i];
            double q = c.get(i) * (d[i] + e[i]) + b.get(i) * d[i];
            res[i] = (p - q) / (p + q);
            if (Double.isNaN(res[i])) {
//                System.out.println("NaN found!");
                res[i] = 0;
            }
        }

        return res;
    }

    /**
     * Gets all the pathways that are represented in the given list of gene names
     *
     * @param geneNames
     * @param engine
     * @return
     */
    public Set<String> getAllPath(ArrayList<String> geneNames, PathwayAnalysis engine) {
        Set<String> allPath = new HashSet<String>();

        for (String tmp : geneNames) {
            if (engine.getGeneToPathways().get(tmp) != null)
                allPath.addAll(engine.getGeneToPathways().get(tmp));
        }

        return allPath;
    }

    /**
     * Checks if a chosen column can be used as differentiation point (has to be binary)
     * Idea: For now, it might as well give user a choice to set a threshold for differentiation
     *
     * @param dataset
     * @param choice
     * @return
     */
    public boolean checkForBinary(Dataset dataset, int choice) {
        Set<String> allValues = new HashSet<String>();

        float[][] data = dataset.getData();
        for (int i = 0; i < data.length; i++) {
            allValues.add(Float.toString(data[i][choice]));
        }

        System.out.println("Amount of different values : " + allValues.size());
        return allValues.size() <= 2; // Means, we can easily distinguish if needed.
        // And we make sure to use original data, not the processed one
    }

    /**
     * Given a list of gene Names, gives back a list which is differentially expressed
     *
     * @param bicluster    A bicluster for which the differential expressed genes should be computed
     * @param chosenColumn Number representing upon which column should the differention be performed
     * @param dataset      Dataset containing the expression level of the genes
     * @param threshold    Threshold value based on which, differentiation will be performed on a @param chosenColumn
     * @return
     */
    private boolean done = false;
    private int[] firstColumns, secondColumns;

    public ArrayList<String> calculateDiff(Bicluster bicluster, int chosenColumn, Dataset dataset, double threshold) {
        ArrayList<String> res = new ArrayList<String>();
        int[] genes = bicluster.getGenes();

        if (chosenColumn == -1) {
            // Differential gene expression
            // Only need this one as the other option is still to be implemented
            // Let's do grouping/based on the conditions. However, we need that as being an input or something
            // There might be many groups
            // First ask for the groups/conditions
            if (!done) {
                String first = JOptionPane.showInputDialog("Enter the columns of first group/condition (comma separated)");
                String second = JOptionPane.showInputDialog("Enter the columns of second group/condition (comma separated)");
                String[] words = first.split(",");
                String[] words2 = second.split(",");
                firstColumns = new int[words.length];
                secondColumns = new int[words2.length];
                done = true;
                for (int i = 0; i < firstColumns.length; i++)
                    firstColumns[i] = Integer.parseInt(words[i]);
                for (int i = 0; i < secondColumns.length; i++)
                    secondColumns[i] = Integer.parseInt(words2[i]);
            }
            float[][] data = dataset.getData();
            // Let's compute the means first
            for (int i = 0; i < genes.length; i++) {
                double meanA = 0.0, meanB = 0.0, mean = 0.0;
                for (int j = 0; j < firstColumns.length; j++)
                    meanA += data[genes[i]][firstColumns[j]];
                for (int j = 0; j < secondColumns.length; j++)
                    meanB += data[genes[i]][secondColumns[j]];

                mean = (meanA + meanB) / (firstColumns.length + secondColumns.length);
                meanA = meanA / firstColumns.length;
                meanB = meanB / secondColumns.length;
                double sum1 = 0.0, sum2 = 0.0, sum3 = 0.0, sum4 = 0.0;
                for (int j = 0; j < firstColumns.length; j++) {
                    sum1 += Math.pow(data[genes[i]][firstColumns[j]] - meanA, 2);
                    sum3 += Math.pow(data[genes[i]][firstColumns[j]] - mean, 2);
                }

                for (int j = 0; j < secondColumns.length; j++) {
                    sum2 += Math.pow(data[genes[i]][secondColumns[j]] - meanB, 2);
                    sum4 += Math.pow(data[genes[i]][secondColumns[j]] - mean, 2);
                }

                double t = 2.0 * (sum1 + sum2) / (sum3 + sum4);
                // Bonferroni correction (divide by sample size)
                double pValue = computePValue(t, 1) / genes.length; // Degrees of freedom for us 1!
                if (pValue <= 0.05) {
//                    System.out.println("Diff expressed!");
                    res.add(dataset.getGeneName(genes[i]));
                }
            }

        } else {
            // Based on the column
            // This part is easy as it will be just about grouping genes into two values
            if (threshold == Double.MAX_VALUE) {
                // Means column was actually binary (so, 2 possible values)
                // This is the case for our test case
                int[][] discrData = dataset.getDiscrData();
                System.out.println("For debugging!");
                for (int i = 0; i < genes.length; i++) {
                    if (discrData[genes[i]][chosenColumn] > 0.5) // 0.5 is used as in binary columns I expect 0/1 values
                        res.add(dataset.getGeneName(genes[i])); // TODO : Change this to min/max
                }
            } else {
                // Based on the threshold. Higher than threshold is diff
                float[][] discrData = dataset.getData();
                for (int i = 0; i < genes.length; i++) {
                    if (discrData[genes[i]][chosenColumn] > threshold)
                        res.add(dataset.getGeneName(genes[i]));
                }
            }
        }

        return res;
    }


    /**
     * Given a critical value t and degrees of freedom to account for, computes a p-value for a chi-square distribution
     *
     * @param t  Critical value/test statistics
     * @param df Degrees of Freedom
     * @return Returns a p-value
     */
    public double computePValue(double t, int df) {
        double result = 0.0;
        if (t < 0 || df < 1) {
            return 0.0;
        }
        double K = ((double) df) * 0.5;
        double X = t * 0.5;
        if (df == 2) {
            return Math.exp(-1.0 * X);
        }

        double PValue = igf(K, X);
        if (Double.isNaN(PValue) || Double.isInfinite(PValue) || PValue <= 1e-8) {
            return 1e-14;
        }

        PValue /= gamma(K);
        //PValue /= tgamma(K);

        return (1.0 - PValue);
    }

    /**
     * Implementation of a incomplete Gamma function
     *
     * @param S
     * @param Z
     * @return
     */
    public double igf(double S, double Z) {
        if (Z < 0.0) {
            return 0.0;
        }
        double Sc = (1.0 / S);
        Sc *= Math.pow(Z, S);
        Sc *= Math.exp(-Z);

        double Sum = 1.0;
        double Nom = 1.0;
        double Denom = 1.0;

        for (int I = 0; I < 200; I++) {
            Nom *= Z;
            S++;
            Denom *= S;
            Sum += (Nom / Denom);
        }

        return Sum * Sc;
    }

    /**
     * Implementation of a Gamma function (not the approximated, faster version)
     *
     * @param N
     * @return
     */
    public double gamma(double N) {
        final double SQRT2PI = 2.5066282746310005024157652848110452530069867406099383;

        double A = 11.0;

        double Z = (double) N;
        double Sc = Math.pow((Z + A), (Z + 0.5));
        Sc *= Math.exp(-1.0 * (Z + A));
        Sc /= Z;

        double F = 1.0;
        double Ck;
        double Sum = SQRT2PI;


        for (int K = 1; K < A; K++) {
            Z++;
            Ck = Math.pow(A - K, K - 0.5);
            Ck *= Math.exp(A - K);
            Ck /= F;

            Sum += (Ck / Z);

            F *= (-1.0 * K);
        }

        return (double) (Sum * Sc);
    }

    /**
     * Implementation of the G score function
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param f
     * @return
     */
    public double GScore(int a, int b, int c, int d, int e, int f) {
        double result = 0.0;
        double P = a * (e + f) + b * f;
        double Q = c * (d + e) + b * d;
        result = (P - Q + 0.0) / (P + Q + 0.0); // 0.0 are there in order to make sure division is not an integer division
        if (Double.isNaN(result)) {
            System.out.println("Here");
            result = 0.0;
        }
        return result;
    }

    public ArrayList<Double> RPosterior(double k, int x, int n, int N) {
        ArrayList<Double> xVect = new ArrayList<Double>();
        int[] xAr = new int[N + 1];
        for (int i = 0; i <= N; i++)
            xAr[i] = i;

        for (int i = 0; i < k; i++) {
            double[] firstPart = MatrixFunctions.matrixConstantSum(lchooseVect(xAr, x), -lchoose(N, n));
            double[] secondPart = lchooseVect(MatrixFunctions.matrixConstantSum(MatrixFunctions.vectorConstantMult(xAr, -1), N), n - x);
            Double[] P = null;
            if (firstPart.length != secondPart.length) {
                // well, this will be weird. One of them should be constant
                if (secondPart.length == 1)
                    P = MatrixFunctions.expVect(MatrixFunctions.matrixConstantSum(firstPart, secondPart[0]));
                else if (firstPart.length == 1)
                    P = MatrixFunctions.expVect(MatrixFunctions.matrixConstantSum(secondPart, firstPart[0]));
                else
                    System.out.println("Strange");
            } else
                P = MatrixFunctions.expVect(MatrixFunctions.matrixSum(firstPart, secondPart));

            for (int j = 0; j < P.length; j++)
                if (Double.isNaN(P[j]))
                    P[j] = 0.0;
            // Sorting both datasets and indices
            SpecialMatrix comparator = new SpecialMatrix(P);
            Integer[] indices = comparator.createIndexArray();
//            Arrays.sort(P, Comparator.reverseOrder());
            Arrays.sort(indices, comparator); // Indices are in correct order now
            xVect.add(randomSelect(xAr, P, indices));
        }

        return xVect;
    }

    public double randomSelect(int[] xAr, Double[] P, Integer[] indices) {
        // Have to follow the indices correctly
        double totalSum = 0.0;
        for (int i = 0; i < P.length; i++) {
            totalSum += P[i];
        }
        double rand = Math.random();
        double sumSoFar = 0.0;
        for (int i = 0; i < indices.length; i++) {
            int actualIndex = indices[i];
            sumSoFar += P[actualIndex] / totalSum;
            if (rand <= sumSoFar)
                return xAr[actualIndex];
        }
        return 0; // Will not happen
    }

    public ArrayList<Double> RPosterior(int x, int n, int N) {
        return RPosterior(1, x, n, N);
    }

    /**
     * Method that returns log of binomial coefficients for given n and k values
     *
     * @param n
     * @param k
     * @return
     */
    public double lchoose(int n, int k) {
        // Computes logarithm of combinations of choosing y from x
        if (k < 0) // If we try to choose more than we have, then there 0 ways of doing it
            return 0;
        else if (k == 0)
            return 1;
        else {
            if (n < k)
                return 0;
            // Formula is: n*(n-1)*...*(n-k+1)/k!
            // We only need the log versions, so, let's not compute any factorials ;)
            double num = 0.0, kFact = 0.0;
            for (int i = n - k + 1; i <= n; i++) {
                num += Math.log(i);
            }

            for (int i = 1; i <= k; i++) {
                kFact += Math.log(i);
            }

            return num - kFact;
        }
    }

    /**
     * Method that returns binomial coefficients for a vector of n values
     *
     * @param n
     * @param k
     * @return
     */
    public double[] lchooseVect(int[] n, int k) {
        if (k < 0) // If we try to choose more than we have, then there 0 ways of doing it
            return new double[]{0};
        else if (k == 0)
            return new double[]{1};
        else {
            // Formula is: n*(n-1)*...*(n-k+1)/k!
            // We only need the log versions, so, let's not compute any factorials ;)
            double[] res = new double[n.length];
            for (int j = 0; j < n.length; j++) {
                if (n[j] < k)
                    res[j] = 0;
                else {
                    double num = 0.0, kFact = 0.0;
                    for (int i = n[j] - k + 1; i <= n[j]; i++) {
                        num += Math.log(i);
                    }

                    for (int i = 1; i <= k; i++) {
                        kFact += Math.log(i);
                    }

                    res[j] = num - kFact;
                }
            }
            return res;
        }
    }
}
