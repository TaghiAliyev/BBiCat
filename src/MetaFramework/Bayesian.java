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

    public void compute(ArrayList<String> geneNames, PathwayAnalysis engine, Dataset dataset, int colChoice) {
        // Idea is to compute Bayesian statistics for the given gene list/cluster and term
        int intersect, geneExc, termExc;
        if (colChoice == -1) {
            // Diff expression
            System.out.println("Differentially expressed computations are starting noooow....");
            // TODO: Given a set of genes, find all related terms/pathways, and then for each
            // TODO: assess the enrichment through Bayesian statistics (Check WinGo.R)
            Set<String> allPathways = getAllPath(geneNames, engine);
            ArrayList<String> diffGenes = calculateDiff(geneNames, colChoice, dataset);
            Set<String> notDiffGenes = setDifference(geneNames, new HashSet<String>(diffGenes));
            for (String tmp : allPathways) {
                ArrayList<String> genesInPathway = engine.getPathwayToGenes().get(tmp);
                int nMin = (int) (geneNames.size() * 0.2); // At least 20 percent of input genes should be part of the pathway
                int xMin = 1; // Let's say at least 1 should be diff expressed
                Set<String> genesInOther = new HashSet<String>();
                for (String tmp2 : allPathways) {
                    if (!tmp.equalsIgnoreCase(tmp2))
                        genesInOther.addAll(engine.getPathwayToGenes().get(tmp2));
                }

                Set<String> genesOnlyPathway = setDifference(genesInPathway, genesInOther);
                Set<String> genesNotPathway = setDifference(genesInOther, genesInPathway);

                int x1OnlyPathway = intersection(genesOnlyPathway, diffGenes).size();
                int x0OnlyPathway = intersection(genesOnlyPathway, notDiffGenes).size();
                int nOnlyPath = genesOnlyPathway.size();
                int x1PathAnd = intersection(genesInPathway, diffGenes).size() - x1OnlyPathway;
                int x0PathAnd = intersection(genesInPathway, notDiffGenes).size() - x0OnlyPathway;
                int nAnd = setDifference(genesInPathway, genesOnlyPathway).size();
                int x1NoPathway = intersection(genesNotPathway, diffGenes).size();
                int x0NoPathway = intersection(genesNotPathway, notDiffGenes).size();
                int nNoPath = genesNotPathway.size();

                double gHat = GScore(x1OnlyPathway, x1PathAnd, x1NoPathway, x0OnlyPathway, x0PathAnd, x0NoPathway);
                int x1Path = x1OnlyPathway + x1PathAnd;

                if (!(genesInPathway.size() < nMin || x1Path < xMin || gHat <= 0)) {
                    double[] gObs = new double[nSim];
                    // Do not skip the loop, still continue
                    if (genesInPathway.size() > (x1OnlyPathway + x0OnlyPathway + x1PathAnd + x0PathAnd)) {
                        ArrayList<Double> X1onlyPath = RPosterior(nSim, x1OnlyPathway, x1OnlyPathway + x0OnlyPathway, nOnlyPath);
                        double[] X0onlyPath = constantMinusVector(nOnlyPath, X1onlyPath);
                        ArrayList<Double> X1andPath = RPosterior(nSim, x1PathAnd, x1PathAnd + x0PathAnd, nAnd);
                        double[] X0andPath = constantMinusVector(nAnd, X1andPath);
                        ArrayList<Double> X1noPath = RPosterior(nSim, x1NoPathway, x1NoPathway + x0NoPathway, nNoPath);
                        double[] X0noPath = constantMinusVector(nNoPath, X1noPath);
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
                        int n = Math.min(poisson(diffGenes.size()), diffGenes.size() + notDiffGenes.size());
                        Set<String> diffRandom = new HashSet<String>();
                        Random random = new Random();
                        for (int i = 0; i < n; i++) {
                            diffRandom.add(geneNames.get(random.nextInt(geneNames.size())));
                        }
                        Set<String> notDiffRandom = setDifference(geneNames, diffRandom);
                        int x1OnlyPathR = intersection(genesOnlyPathway, diffRandom).size();
                        int x0OnlyPathR = intersection(genesOnlyPathway, notDiffRandom).size();
                        int nOnlyPathR = genesOnlyPathway.size();

                        int x1AndPathR = intersection(genesInPathway, diffRandom).size() - x1OnlyPathR;
                        int x0AndPathR = intersection(genesInPathway, notDiffRandom).size() - x0OnlyPathR;
                        int nPathAndR = setDifference(genesInPathway, genesOnlyPathway).size();

                        int x1NoPathR = intersection(genesNotPathway, diffRandom).size();
                        int x0NoPathR = intersection(genesNotPathway, notDiffRandom).size();
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
                        if (gRand[j] > gObs[j])
                            cnt++;
                    double result = (cnt + 0.0) / (nSim + 0.0); // 0.0 is needed to push for the double division
                    System.out.println("Pathway with name : " + tmp + " has p-value of : " + result);

                    // computing error bars
                    double errorLeft = Math.min(quantile(gObs, 0.05), gHat);
                    double errorRight = Math.max(quantile(gObs, 0.95), gHat);
                    System.out.println("G hat : " + gHat);
                    System.out.println("Errorbar : [" + errorLeft + ", " + errorRight + "]");
                    // TODO : Write a test case for this method. Quantile works, but overall bayesian needs checking
                }
                System.out.println("-------------------------------");
            }
        } else {
            // Column based differentiation
            // Make sure it is binary (0/1 or true/false)
            // And 1 basically means differentiated
            if (checkForBinary(dataset, colChoice)) {
                calculateDiff(geneNames, colChoice, dataset);
                System.out.println("All is fine, let's do this");
            } else {
                JOptionPane.showMessageDialog(null, "Column values are not binary! More than 2 possible values");
            }
        }

    }

    /**
     * Computes the intersection between two sets/lists
     *
     * @param geneNames
     * @param termGenes
     * @return
     */
    public Set<String> intersection(ArrayList<String> geneNames, ArrayList<String> termGenes) {
        Set<String> gene = new HashSet<String>();
        Set<String> term = new HashSet<String>();
        for (String tmp : geneNames)
            gene.add(tmp);
        for (String tmp : termGenes)
            term.add(tmp);

        Set<String> intersect = new TreeSet(gene);
        intersect.retainAll(term);

        return intersect;
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

        return (1 - gamma) * data[j - 1] + gamma * data[j];
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
        }

        return res;
    }

    public Set<String> intersection(Set<String> geneNames, Set<String> termGenes) {
        Set<String> intersect = new TreeSet(geneNames);
        intersect.retainAll(termGenes);
        return intersect;
    }

    public Set<String> intersection(Set<String> geneNames, ArrayList<String> termGenes) {
        Set<String> tmp = new HashSet<String>(termGenes);

        Set<String> intersect = new TreeSet(geneNames);
        intersect.retainAll(tmp);

        return intersect;
    }

    public Set<String> intersection(ArrayList<String> list1, Set<String> list2) {
        return intersection(list2, list1);
    }


    /**
     * Computes the set difference between two lists/sets
     *
     * @param genesInPathway
     * @param genesInOther
     * @return
     */
    public Set<String> setDifference(ArrayList<String> genesInPathway, Set<String> genesInOther) {
        genesInPathway.removeAll(genesInOther);

        return new HashSet<String>(genesInPathway);
    }

    /**
     * Computes the element-wise set difference
     *
     * @param genesInOther
     * @param genesInPathway
     * @return
     */
    public Set<String> setDifference(Set<String> genesInOther, ArrayList<String> genesInPathway) {
        genesInOther.removeAll(genesInPathway);
        return genesInOther;
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
            for (int j = 0; j < data[0].length; j++) {
                allValues.add(Float.toString(data[i][j]));
            }
        }

        return allValues.size() < 2; // Means, we can easily distinguish if needed.
        // And we make sure to use original data, not the processed one
    }

    /**
     * Given a list of gene Names, gives back a list which is differentially expressed
     *
     * @param geneNames     List of gene names that should be assessed
     * @param choisenColumn Number representing upon which column should the differention be performed
     * @param dataset       Dataset containing the expression level of the genes
     */
    public ArrayList<String> calculateDiff(ArrayList<String> geneNames, int choisenColumn, Dataset dataset) {
        if (choisenColumn == -1) {
            // Differential gene expression
        } else {
            // Based on the column
        }

        return null;
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
        return result;
    }

    /**
     * Element-wise summation of two vectors
     *
     * @param a
     * @param b
     * @return
     */
    public int[] vectSum(int[] a, int[] b) {
        int[] tmp = new int[a.length];
        for (int i = 0; i < a.length; i++)
            tmp[i] = a[i] + b[i];

        return tmp;
    }

    public double[] constantMinusVector(int n, ArrayList<Double> vec) {
        double[] res = new double[vec.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = n - vec.get(i);
        return res;
    }

    /**
     * Adding a constant to all the elements of a vector
     *
     * @param a
     * @param tmp
     * @return
     */
    public int[] matrixConstantSum(int[] a, int tmp) {
        int[] tmpVec = new int[a.length];
        for (int i = 0; i < tmpVec.length; i++) {
            tmpVec[i] = a[i] + tmp;
        }

        return tmpVec;
    }

    /**
     * Adds a constant to a given double vector (element-wise)
     *
     * @param a
     * @param tmp
     * @return
     */
    public double[] matrixConstantSum(double[] a, double tmp) {
        double[] res = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] + tmp;
        }

        return res;
    }

    /**
     * Element-wise multiplication of two vectors
     *
     * @param a
     * @param b
     * @return
     */
    public int[] vectorMult(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * b[i];
        }

        return result;
    }

    public double[] vectorConstantMult(double[] a, double b) {
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++)
            result[i] = a[i] * b;

        return result;
    }

    public int[] vectorConstantMult(int[] a, int b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++)
            result[i] = a[i] * b;

        return result;
    }

    public double[] matrixSum(double[] a, double[] b) {
        double[] res = new double[a.length];

        for (int i = 0; i < a.length; i++)
            res[i] = a[i] + b[i];

        return res;
    }

    /**
     * Element-wise division of two vectors
     *
     * @param a
     * @param b
     * @return
     */
    public int[] vectorDiv(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] / b[i];
        }

        return result;
    }

    /**
     * Computes the exponent of each element of a given vector
     *
     * @param vec
     * @return
     */
    public Double[] expVect(double[] vec) {
        Double[] result = new Double[vec.length];
        for (int i = 0; i < vec.length; i++)
            result[i] = Math.exp(vec[i]);

        return result;
    }

    public ArrayList<Double> RPosterior(double k, int x, int n, int N) {
        ArrayList<Double> xVect = new ArrayList<Double>();
        int[] xAr = new int[N + 1];
        for (int i = 0; i <= N; i++)
            xAr[i] = i;

        for (int i = 0; i < k; i++) {
            double[] firstPart = matrixConstantSum(lchooseVect(xAr, x), -lchoose(N, n));
            double[] secondPart = lchooseVect(matrixConstantSum(vectorConstantMult(xAr, -1), N), n - x);
            Double[] P = expVect(matrixSum(firstPart, secondPart));

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
