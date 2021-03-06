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

package bicat.run_machine;

import bicat.Constants.MethodConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @return
 * @uml.property  name="binaryData"
 */
/**
 * @return
 * @uml.property  name="lower_genes"
 */
/**
 * @return
 * @uml.property  name="lower_chips"
 */
/**
 * @return
 * @uml.property  name="extended"
 */
/**
 * @param binaryData
 * @uml.property  name="binaryData"
 */
/**
 * @param lower_genes
 * @uml.property  name="lower_genes"
 */
/**
 * @param lower_chips
 * @uml.property  name="lower_chips"
 */
/**
 * @return
 */
/**
 * @return
 * @uml.property  name="binaryData"
 */
/**
 * @return
 * @uml.property  name="lower_genes"
 */
/**
 * @return
 * @uml.property  name="lower_chips"
 */
/**
 * @return
 * @uml.property  name="extended"
 */
/**
 * @param binaryData
 * @uml.property  name="binaryData"
 */
/**
 * @param lower_genes
 * @uml.property  name="lower_genes"
 */
/**
 * @param lower_chips
 * @uml.property  name="lower_chips"
 */
/**
 * @param extended
 * @uml.property  name="extended"
 */
@Data
@EqualsAndHashCode(callSuper = false)
/**
 * Class that holds the specific arguments needed for BiMax algorithm
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class ArgumentsBiMax extends Arguments {

	/**
	 * @uml.property  name="binaryData"
	 */
	private int[][] binaryData;

	/**
	 * @uml.property  name="lower_genes"
	 */
	private int lower_genes;

	/**
	 * @uml.property  name="lower_chips"
	 */
	private int lower_chips;

	/**
	 * @uml.property  name="extended"
	 */
	private boolean extended;

	// ===========================================================================
	public ArgumentsBiMax() {
	}


	public int[] getBinaryDataAsVector() {
		int[][] bD = getBinaryData();
		int l = bD.length;
		int w = bD[0].length;
		int[] binaryDataAsVector = new int[l*w];
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < w; j++) {
				binaryDataAsVector[i*w+j]=bD[i][j];
				if(MethodConstants.debug)System.out.println(bD[i][j] + " ");
			}
		}
		if(MethodConstants.debug)System.out.println("Length of Vector: "+ binaryDataAsVector.length + " length of original Matrix: "+l+" width: "+w);
		return binaryDataAsVector;
	}

}