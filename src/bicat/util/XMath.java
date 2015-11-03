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

package bicat.util;

import java.util.Random;

/**
 * <p>Provides elementary mathematical functions which are not contained in
 * <tt>java.lang.Math</tt>.</p>
 *
 * <p>This is an all-static class.</p>
 *
 * @author Thomas Frech
 * @version 2004-07-22
 */
public final class XMath
{
	// initialization flag
	private static boolean initialized = false;

	// look-up table for factorials
	private static double[] factorial;

	// look-up table for binomial coefficients
	private static double[][] binomial;

	// flags, used to prevent warning floods
	private static boolean factorialWarningDisplayed = false;
	private static boolean binomialWarningDisplayed = false;

	// random generator
	private static Random random = new Random();


	/**
	 * Initialize look-up tables.
	 *
	 * @param nMax maximum value of parameter <tt>n</tt>
	 */
	public static void initialize(int nMax)
	{
		if (initialized)
			return;

		nMax = Math.min(nMax,2001);

		if (Option.printMessages)
			System.out.print("Creating look-up table for factorial(n) and binomial(n,r) ... ");

		// factorials
		factorial = new double[nMax+1];
		factorial[0] = 1;
		for (int n=1; n<factorial.length; n++)
			factorial[n] = factorial[n-1]*n;

		// binomial coefficients
		binomial = new double[nMax+1][];
		binomial[0] = new double[1];
		binomial[0][0] = 1;
		for (int n=1; n<binomial.length; n++)
		{
			binomial[n] = new double[n+1];
			binomial[n][0] = 1;
			for (int r=1; r<n; r++)
				binomial[n][r] = binomial[n-1][r-1] + binomial[n-1][r];
			binomial[n][n] = 1;
		}

		initialized = true;

		if (Option.printMessages)
			System.out.println("done");
	}

	/**
	 * Re-initialize look-up tables.
	 *
	 * @param nMax maximum value of parameter <tt>n</tt>
	 */
	public static void reinitialize(int nMax)
	{
		initialized = false;
		initialize(nMax);
	}

	/**
	 * <p>Implementation of factorial function.</p>
	 *
	 * @param n parameter <tt>n</tt>
	 * @return <tt>1*2*3*...*n</tt>
	 */
	public static double factorial(int n)
	{
		if (!initialized)
			initialize(n);

		// return 1 for negative n
		if (n<0)
			return 1;

		// return value from look-up table if available
		if (n<factorial.length)
			return factorial[n];

		// display warning if requested value is not in look-up table
		if (!factorialWarningDisplayed)
		{
			factorialWarningDisplayed = true;
			if (Option.printWarnings)
				System.out.println("WARNING: Look-up table for factorial is too small: factorial("+n+") requested.");
		}

		if (Option.ignoreXMathOverflow)
			return 0;

		// calculate and return value which is not in look-up table (expensive!)
		double f = 1;
		for (int i=2; i<=n; i++)
			f *= i;
		return f;
	}

	/**
	 * <p>Implementation of binomial coefficient function.</p>
	 *
	 * @param n parameter <tt>n</tt>
	 * @param r parameter <tt>r</tt>
	 * @return <tt>factorial(n)/(factorial(r)*factorial(n-r))</tt>
	 */
	public static double binomial(int n, int r)
	{
		if (!initialized)
			initialize(n);

		// return 0 if input parameters are out of pascal triangle
		if (n<0 || r<0 || r>n)
			return 0;

		// return value from look-up table if available
		if (n<binomial.length)
			return binomial[n][r];

		// display warning if requested value is not in look-up table
		if (!binomialWarningDisplayed)
		{
			binomialWarningDisplayed = true;
			if (Option.printWarnings)
				System.out.println("WARNING: Look-up table for binomial coefficients is too small: binomial("+n+","+r+") requested.");
		}

		if (Option.ignoreXMathOverflow)
			return 0;

		// calculate and return value which is not in look-up table (expensive!)
		double a = 1;
		for (int i=n-r+1; i<=n; i++)
			a *= i;
		double b = 1;
		for (int i=2; i<=r; i++)
			b *= i;
		return a/b;
	}

	/**
	 * Initialize random generator. If this function is not used, the random
	 * values of the random function are unpredictable.
	 *
	 * @param seed random generator seed
	 */
	public static void initRandom(long seed)
	{
		random = new Random(seed);
	}

	/**
	 * Get random value in range [0,1[.
	 *
	 * @return random value
	 */
	public static double random()
	{
		return random.nextDouble();
	}

	// This is an all-static class.
	private XMath()
	{
	}
}
