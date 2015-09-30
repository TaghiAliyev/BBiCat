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

package bicat.util;

import bicat.Constants.MethodConstants;
import bicat.gui.BicatGui;

import javax.swing.JOptionPane;

public class BicatError {

	static BicatGui owner;

	// ===========================================================================
	public BicatError(BicatGui o) {
		owner = o;
	}

	// ===========================================================================
	public static void errorMessage(Exception e) {
		if (MethodConstants.debug)
			e.printStackTrace();
		JOptionPane.showMessageDialog(owner, "Exception Raised!\n\n"
				+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	// ===========================================================================
	public static void errorMessage(Exception e, BicatGui o) {
		if (MethodConstants.debug)
			e.printStackTrace();
		JOptionPane.showMessageDialog(o, "Exception Raised!\n\n"
				+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	// ===========================================================================
	public static void errorMessage(Exception e, BicatGui o, boolean ext,
			String extStr) {
		if (MethodConstants.debug)
			e.printStackTrace();

		if (ext)
			JOptionPane.showMessageDialog(o, extStr, "Error",
					JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(o, "3. Exception Raised!\n\n"
					+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	// =============== Offset Error =======================
	public static void offsetError() {
		JOptionPane.showMessageDialog(null, "The offset must be at least 1",
				"alert", JOptionPane.ERROR_MESSAGE);
	}

	public static void wrongOffsetError() {
		JOptionPane
				.showMessageDialog(
						null,
						"Wrong offset!\nThe problem is probably that your file contains additional\ncolumns with annotations at the beginning of the data set.\nTo keep the gene names, delete all other columns that do not\ncontain expression values (e.g. with MS Excel) and set\nthe column offset to one.\nIf the problem persists, check if there are missing\nvalues in your data set and set those to zero or\ndelete the according lines.",
						"alert", JOptionPane.ERROR_MESSAGE);
	}

	// =============== Space Error =======================
	public static void spaceError() {
		JOptionPane.showMessageDialog(null,
				"Gene and chip names must not contain spaces", "alert",
				JOptionPane.ERROR_MESSAGE);
	}

}
