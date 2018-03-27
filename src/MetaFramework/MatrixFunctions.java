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

package MetaFramework;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class containing the common Matrix Functions needed for this library
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class MatrixFunctions {


    /**
     * Computes the intersection between two sets/lists
     *
     * @param geneNames
     * @param termGenes
     * @return
     */
    public static Set<String> intersection(ArrayList<String> geneNames, ArrayList<String> termGenes) {
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

    public static Set<String> intersection(Set<String> geneNames, Set<String> termGenes) {
        Set<String> intersect = new TreeSet(geneNames);
        intersect.retainAll(termGenes);
        return intersect;
    }

    public static Set<String> intersection(Set<String> geneNames, ArrayList<String> termGenes) {
        Set<String> tmp = new HashSet<String>(termGenes);

        Set<String> intersect = new TreeSet(geneNames);
        intersect.retainAll(tmp);

        return intersect;
    }

    public static Set<String> intersection(ArrayList<String> list1, Set<String> list2) {
        return intersection(list2, list1);
    }


    /**
     * Computes the set difference between two lists/sets
     *
     * @param genesInPathway
     * @param genesInOther
     * @return
     */
    // TODO : Test the set difference methods
    public static Set<String> setDifference(ArrayList<String> genesInPathway, Set<String> genesInOther) {
        Set<String> res = new HashSet<String>(genesInPathway);
        res.removeAll(genesInOther);
//        System.out.println(res.size());
        return res;
    }

    /**
     * Computes the element-wise set difference
     *
     * @param genesInOther
     * @param genesInPathway
     * @return
     */
    public static Set<String> setDifference(Set<String> genesInOther, ArrayList<String> genesInPathway) {
        Set<String> res = new HashSet<String>(genesInOther);
        res.removeAll(genesInPathway);
//        genesInOther.removeAll(genesInPathway);
        return res;
    }

    public static Set<String> setDifference(Set<String> gene1, Set<String> gene2)
    {
        Set<String> local = new HashSet<String>(gene1);
        local.removeAll(gene2);

        return local;
    }


    /**
     * Element-wise summation of two vectors
     *
     * @param a
     * @param b
     * @return
     */
    public static int[] vectSum(int[] a, int[] b) {
        int[] tmp = new int[a.length];
        for (int i = 0; i < a.length; i++)
            tmp[i] = a[i] + b[i];

        return tmp;
    }

    public static double[] constantMinusVector(int n, ArrayList<Double> vec) {
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
    public static int[] matrixConstantSum(int[] a, int tmp) {
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
    public static double[] matrixConstantSum(double[] a, double tmp) {
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
    public static int[] vectorMult(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * b[i];
        }

        return result;
    }

    public static double[] vectorConstantMult(double[] a, double b) {
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++)
            result[i] = a[i] * b;

        return result;
    }

    public static int[] vectorConstantMult(int[] a, int b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++)
            result[i] = a[i] * b;

        return result;
    }

    public static double[] matrixSum(double[] a, double[] b) {
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
    public static int[] vectorDiv(int[] a, int[] b) {
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
    public static Double[] expVect(double[] vec) {
        Double[] result = new Double[vec.length];
        for (int i = 0; i < vec.length; i++)
            result[i] = Math.exp(vec[i]);

        return result;
    }
}
