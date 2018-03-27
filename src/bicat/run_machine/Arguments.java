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

import bicat.preprocessor.PreprocessOption;
import lombok.Data;

/**
 * Generic arguments class. Contains the variables and methods every argument class should have.
 * Specific arguments can be found in respective classes.
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="total_genes"
 */
/**
 * @return
 * @uml.property  name="total_chips"
 */
/**
 * @return
 * @uml.property  name="outputFileName"
 */
/**
 * @return
 * @uml.property  name="preOpts"
 */
/**
 * @param total_genes
 * @uml.property  name="total_genes"
 */
/**
 * @param total_chips
 * @uml.property  name="total_chips"
 */
/**
 * @param outputFileName
 * @uml.property  name="outputFileName"
 */
/**
 * Generic arguments class. Contains the variables and methods every argument class should have. Specific arguments can be found in respective classes. Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author  Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="total_genes"
 */
/**
 * @return
 * @uml.property  name="total_chips"
 */
/**
 * @return
 * @uml.property  name="outputFileName"
 */
/**
 * @return
 * @uml.property  name="preOpts"
 */
/**
 * @param total_genes
 * @uml.property  name="total_genes"
 */
/**
 * @param total_chips
 * @uml.property  name="total_chips"
 */
/**
 * @param outputFileName
 * @uml.property  name="outputFileName"
 */
/**
 * @param preOpts
 * @uml.property  name="preOpts"
 */
@Data
public class Arguments {

  // ===========================================================================
  public Arguments() {  }

  // ===========================================================================
  /**
 * @uml.property  name="data"
 */
private double[] data;

  /**
 * @uml.property  name="total_genes"
 */
private int total_genes;
  /**
 * @uml.property  name="total_chips"
 */
private int total_chips;

  /**
 * @uml.property  name="seed"
 */
private int seed;
  /**
 * @uml.property  name="logarithm"
 */
private int logarithm;

  /**
 * @uml.property  name="outputFileName"
 */
private String outputFileName;

  /**
 * @uml.property  name="datasetIdx"
 */
private int datasetIdx;
  /**
 * @uml.property  name="preOpts"
 * @uml.associationEnd  
 */
private PreprocessOption preOpts;

  // ===========================================================================
  /**
 * @param  d
 * @uml.property  name="data"
 */
public void setData(double[] d) {
    data = new double[d.length];
    for(int i = 0; i< d.length; i++) data[i] = d[i];
  }

  public void setGeneNumber(int tg) { total_genes = tg; }
  public void setChipNumber(int tc) { total_chips = tc; }

  /**
 * @param  s
 * @uml.property  name="seed"
 */
public void setSeed(int s) { seed = s; }
  /**
 * @param  l
 * @uml.property  name="logarithm"
 */
public void setLogarithm(int l) { logarithm = l; }

  public void setOutputFile(String of) { outputFileName = of; }

  /**
 * @param  idx
 * @uml.property  name="datasetIdx"
 */
public void setDatasetIdx(int idx) { datasetIdx = idx; }
  public void setPreprocessOptions(PreprocessOption po) { preOpts = po; }

  // ===========================================================================
  /**
 * @return
 * @uml.property  name="data"
 */
public double[] getData() { return data; }

  public int getGeneNumber() { return total_genes; }
  public int getChipNumber() { return total_chips; }

  /**
 * @return
 * @uml.property  name="seed"
 */
public int getSeed() { return seed; }
  /**
 * @return
 * @uml.property  name="logarithm"
 */
public int getLogarithm() { return logarithm; }

  public String getOutputFile() { return outputFileName; }

  /**
 * @return
 * @uml.property  name="datasetIdx"
 */
public int getDatasetIdx() { return datasetIdx; }
  public PreprocessOption getPreprocessOptions() { return preOpts; }

}