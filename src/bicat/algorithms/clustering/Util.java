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

package bicat.algorithms.clustering;

/**
 * Class with distance metrics functions
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class Util {

    // ===========================================================================
    public Util() {
    }

    // ===========================================================================
    public static double computeEuclideanDistance(float[] X, float[] Y) {
        double euclid = 0.0;
        for (int i = 0; i < X.length; i++)
            euclid += Math.pow((X[i] - Y[i]), 2.0);
        return Math.sqrt(euclid);
    }

    // ===========================================================================
    public static double computePearsonCorrelationDistance(float[] X, float[] Y) {

        double sum_X = 0.0;
        double sum_Y = 0.0;
        double sum_X_sqr = 0.0;
        double sum_Y_sqr = 0.0;
        double sum_product_XY = 0.0;

        for (int i = 0; i < X.length; i++) {
            sum_X += (double) X[i];
            sum_X_sqr += Math.pow((double) X[i], 2.0);
        }
        for (int i = 0; i < Y.length; i++) {
            sum_Y += (double) Y[i];
            sum_Y_sqr += Math.pow((double) Y[i], 2.0);
        }
        for (int i = 0; i < X.length; i++) sum_product_XY += (double) X[i] * (double) Y[i];

        return 1.0 - (sum_product_XY - sum_X * sum_Y / X.length) /
                Math.sqrt((sum_X_sqr - sum_X_sqr / X.length) * (sum_Y_sqr - sum_Y_sqr / Y.length));
    }

    // ===========================================================================
    public static double computeManhattanDistance(float[] X, float[] Y) {
        double manhattan = 0.0;
        for (int i = 0; i < X.length; i++)
            manhattan += Math.abs((X[i] - Y[i]));
        return manhattan;
    }

    // ===========================================================================
    public static double computeCosineDistance(float[] X, float[] Y) {
        double sum_X_sqr = 0.0;
        double sum_Y_sqr = 0.0;
        double sum_product_XY = 0.0;

        for (int i = 0; i < X.length; i++) sum_X_sqr += Math.pow((double) X[i], 2.0);
        for (int i = 0; i < Y.length; i++) sum_Y_sqr += Math.pow((double) Y[i], 2.0);
        for (int i = 0; i < X.length; i++) sum_product_XY += X[i] * Y[i];

        return 1 - sum_product_XY / Math.sqrt(sum_X_sqr * sum_Y_sqr);
    }

    // ===========================================================================
    public static double computeMinkowskiDistance(float[] X, float[] Y) {
        double minkowski = 0.0;
        for (int i = 0; i < X.length; i++)
            minkowski += Math.pow(Math.abs(X[i] - Y[i]), 2.0);
        return Math.sqrt(minkowski);
    }

  /* double computeHammingDistance(float[] X, float[] Y) {
     double hamming = 0.0;
     for(int i = 0; i< X.length; i++)
      hamming += Math.pow((X[i] - Y[i]),2.0);
     return Math.sqrt(hamming);
   } */

}