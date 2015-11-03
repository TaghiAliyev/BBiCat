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

package bicat.util;

import java.io.*;

/**
 * PrintStream to print text to console and file simultaneously.
 *
 * @author Thomas Frech
 * @version 2004-07-22
 */
public class XPrintStream
{
	private final OutputStream outStream;
	private final PrintStream out;

	public XPrintStream(String filename) throws IOException
	{
		outStream = new FileOutputStream(new File(filename));
		out = new PrintStream(outStream);
	}

	public XPrintStream(OutputStream outStream)
	{
		this.outStream = null;
		out = new PrintStream(outStream);
	}

	public XPrintStream(PrintStream out)
	{
		outStream = null;
		this.out = out;
	}

	public void print(char c)
	{
		out.print(c);
		System.out.print(c);
	}

	public void print(char[] parm1)
	{
		out.print(parm1);
		System.out.print(parm1);
	}

	public void print(String s)
	{
		out.print(s);
		System.out.print(s);
	}

	public void print(boolean b)
	{
		out.print(b);
		System.out.print(b);
	}

	public void print(int i)
	{
		out.print(i);
		System.out.print(i);
	}

	public void print(long l)
	{
		out.print(l);
		System.out.print(l);
	}

	public void print(float f)
	{
		out.print(f);
		System.out.print(f);
	}

	public void print(double d)
	{
		out.print(d);
		System.out.print(d);
	}

	public void print(Object obj)
	{
		out.print(obj);
		System.out.print(obj);
	}

	public void println()
	{
		out.println();
		System.out.println();
	}

	public void println(String s)
	{
		out.println(s);
		System.out.println(s);
	}

	public void println(char c)
	{
		out.println(c);
		System.out.println(c);
	}

	public void println(char[] parm1)
	{
		out.println(parm1);
		System.out.println(parm1);
	}

	public void println(boolean b)
	{
		out.println(b);
		System.out.println(b);
	}

	public void println(int i)
	{
		out.println(i);
		System.out.println(i);
	}

	public void println(long l)
	{
		out.println(l);
		System.out.println(l);
	}

	public void println(float f)
	{
		out.println(f);
		System.out.println(f);
	}

	public void println(double d)
	{
		out.println(d);
		System.out.println(d);
	}

	public void println(Object obj)
	{
		out.println(obj);
		System.out.println(obj);
	}

	public void flush()
	{
		out.flush();
		System.out.flush();
	}

	public void close()
	{
		if (outStream!=null)
			try
			{
				outStream.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		out.close();
	}
}
