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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class that contains the arguments/variables needed/used for the K-Means clustering algorithm
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="what_distance"
 */
/**
 * @return
 * @uml.property  name="start_list"
 */
/**
 * @return
 * @uml.property  name="empty_action"
 */
/**
 * @return
 * @uml.property  name="nr_max_iterations"
 */
/**
 * @return
 * @uml.property  name="nr_replicates"
 */
/**
 * @return
 * @uml.property  name="nr_clusters"
 */
/**
 * @return
 * @uml.property  name="myData"
 */
/**
 * @param what_distance
 * @uml.property  name="what_distance"
 */
/**
 * @param start_list
 * @uml.property  name="start_list"
 */
/**
 * @param empty_action
 * @uml.property  name="empty_action"
 */
/**
 * @param nr_max_iterations
 * @uml.property  name="nr_max_iterations"
 */
/**
 * @param nr_replicates
 * @uml.property  name="nr_replicates"
 */
/**
 * @param nr_clusters
 * @uml.property  name="nr_clusters"
 */
/**
 * Class that contains the arguments/variables needed/used for the K-Means clustering algorithm Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author  Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="what_distance"
 */
/**
 * @return
 * @uml.property  name="start_list"
 */
/**
 * @return
 * @uml.property  name="empty_action"
 */
/**
 * @return
 * @uml.property  name="nr_max_iterations"
 */
/**
 * @return
 * @uml.property  name="nr_replicates"
 */
/**
 * @return
 * @uml.property  name="nr_clusters"
 */
/**
 * @return
 * @uml.property  name="myData"
 */
/**
 * @param what_distance
 * @uml.property  name="what_distance"
 */
/**
 * @param start_list
 * @uml.property  name="start_list"
 */
/**
 * @param empty_action
 * @uml.property  name="empty_action"
 */
/**
 * @param nr_max_iterations
 * @uml.property  name="nr_max_iterations"
 */
/**
 * @param nr_replicates
 * @uml.property  name="nr_replicates"
 */
/**
 * @param nr_clusters
 * @uml.property  name="nr_clusters"
 */
/**
 * @param myData
 * @uml.property  name="myData"
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArgumentsKMeans extends Arguments {

  /**
 * @uml.property  name="what_distance"
 */
private int what_distance;
  /**
 * @uml.property  name="start_list"
 */
private int start_list;
  /**
 * @uml.property  name="empty_action"
 */
private int empty_action;

  /**
 * @uml.property  name="nr_max_iterations"
 */
private int nr_max_iterations;
  /**
 * @uml.property  name="nr_replicates"
 */
private int nr_replicates;

  /**
 * @uml.property  name="nr_clusters"
 */
private int nr_clusters;

  /**
 * @uml.property  name="myData"
 */
private float[][] myData;

  // ===========================================================================
  public ArgumentsKMeans() { }

}