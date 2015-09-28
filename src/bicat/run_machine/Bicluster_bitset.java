/*
 *                                BiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                Copyright (c) 2015 Taghi Aliyev
 *
 *                                This file is part of BiCat.
 *
 *                                BiCat is a free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                BiCat is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with BiCat.  If not, see <http://www.gnu.org/licenses/>.
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

import lombok.Data;

import java.util.*;

/**
 * Class that represents the bicluster as being the bitset.
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class Bicluster_bitset implements Comparable {

  private BitSet genes;
  private BitSet chips;

  // new - optimization related
  private BitSet geneStarsOrigins;
  private BitSet chipStarsOrigins;

  // ===========================================================================
  public Bicluster_bitset() {  }

  // ===========================================================================
  public Bicluster_bitset(BitSet g, BitSet c) {
    genes = g;
    chips = c;
  }

  // ===========================================================================
  public Bicluster_bitset(BitSet g, BitSet c, BitSet gs, BitSet cs) {
    genes = g;
    chips = c;
    if (gs.cardinality() > 0) geneStarsOrigins = gs;
    if (cs.cardinality() > 0) chipStarsOrigins = cs;
  }

  // ===========================================================================
  public int compareTo(Object o) {

    if(genes.cardinality()*chips.cardinality() > ((Bicluster_bitset)o).genes.cardinality()*((Bicluster_bitset)o).chips.cardinality())
      return -1;
    else if(genes.cardinality()*chips.cardinality() > ((Bicluster_bitset)o).genes.cardinality()*((Bicluster_bitset)o).chips.cardinality())
      return 1;
    else return 0;
  }

  // ===========================================================================
  public int[] getGenes() {
    int card = genes.cardinality();
    int[] arr = new int[card];
    int idx = 0;
    for(int i=genes.nextSetBit(0); i>=0; i=genes.nextSetBit(i+1))
      if (genes.get(i)) {
        arr[idx] = i;
        idx++;
      }
    return arr;
  }

  // ===========================================================================
  public int[] getChips() {
    int card = chips.cardinality();
    int[] arr = new int[card];
    int idx = 0;
    for(int i=chips.nextSetBit(0); i>=0; i=chips.nextSetBit(i+1))
      if (chips.get(i)) {
        arr[idx] = i;
        idx++;
      }
    return arr;
  }

  // ====
  public int get_gene_dim() { return genes.cardinality(); }
  public int get_chip_dim() { return chips.cardinality(); }

  // ===========================================================================
  BitSet getGeneStarsOrigins() { return geneStarsOrigins; }

  // ===========================================================================
  BitSet getChipStarsOrigins() { return chipStarsOrigins; }

  // ===========================================================================
  public void printOut() {
//    System.out.println("BC "+genes.cardinality()+" "+chips.cardinality());
    for (int i = genes.nextSetBit(0); i>=0; i=genes.nextSetBit(i+1))
      System.out.print((i+1)+" ");
    System.out.println();
    for (int i = chips.nextSetBit(0); i>=0; i=chips.nextSetBit(i+1))
      System.out.print((i+1)+" ");
    System.out.println();
  }

}
