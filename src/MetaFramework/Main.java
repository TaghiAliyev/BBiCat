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

import bicat.biclustering.Bicluster;
import bicat.preprocessor.FileOffsetException;
import gnu.xml.dom.DomElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Main class that will call correct classes and methods in order to do the analysis
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // Let's try to read the xml file here
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File("C:/Users/tagi1_000/Desktop/NCI-Nature_Curated.xml"));
        System.out.println("Done reading the xml file!");
        System.out.println(doc.getFirstChild().getNodeName());

        Element docElement = doc.getDocumentElement();

        // Idea behind this hashmap is the following:
        // We parse the whole thing only once and remember in hashmap the relations between pathways and proteins
        // Then, in bayesian computations we just make use of this information
        // And, also we will need this hashmap for search through the genes/proteins involved in the cluster
        //
        HashMap<Integer, Node> hashMap = new HashMap<Integer, Node>();

        System.out.println(docElement.getChildNodes().getLength());
        DomElement model = (DomElement) docElement.getElementsByTagName("Model").item(0);
        DomElement interactions = (DomElement) model.getElementsByTagName("InteractionList").item(0);
        DomElement molecules = (DomElement) model.getElementsByTagName("MoleculeList").item(0);
//        ((DomElement)model.getElementsByTagName("Pathway"))
        DomElement pathwayList = (DomElement) model.getElementsByTagName("PathwayList").item(0);

        int length2 = pathwayList.getChildNodes().getLength();
        int counter = 0;
        for (int i = 0; i < length2; i++) {
            if (pathwayList.getChildNodes().item(i).getNodeName().equalsIgnoreCase("Pathway")) {
                // This is a pathway!
                DomElement PathwayComponent = (DomElement) ((DomElement) (pathwayList.getChildNodes().item(i))).getElementsByTagName("PathwayComponentList").item(0);

                // Seems like there is one exception to this rule. I think the pathway is represented as being an interaction
                // Ignored for now.
                if (PathwayComponent == null)
                    System.out.println("Hell no! at :" + i);
                else {
//                    System.out.println(PathwayComponent.getElementsByTagName("PathwayComponent").getLength());
                    NodeList pathName = (DomElement) ((DomElement) (pathwayList.getChildNodes().item(i))).getElementsByTagName("LongName").item(0);
                    System.out.println(pathName.item(0).getNodeValue());
                    NodeList pathInteractions = PathwayComponent.getElementsByTagName("PathwayComponent");

                    for (int k = 0; k < pathInteractions.getLength(); k++) {
                        String id = pathInteractions.item(k).getAttributes().item(0).getFirstChild().getNodeValue();
                        // Given a pathway components, let's find them
                        // Every pathway component is an interaction and has a reference to that interaction
                        // So, let's fetch some information about it. For now let's try only on one pathway
                        for (int j = 0; j < interactions.getChildNodes().getLength(); j++) {
                            if (interactions.getChildNodes().item(j).getNodeName().equalsIgnoreCase("Interaction")) {
                                DomElement interaction = (DomElement) interactions.getChildNodes().item(j);
                                if (interaction.getAttribute("id").equalsIgnoreCase(id)) {
//                                    System.out.println(interaction.getAttribute("interaction_type"));

                                    NodeList parts = ((DomElement) interaction.getElementsByTagName("InteractionComponentList").item(0)).getElementsByTagName("InteractionComponent");
//                                    System.out.println("Length : " + parts.getLength());
                                    for (int l = 0; l < parts.getLength(); l++) {
                                        // We should look for molecules here
                                        String moleculeID = parts.item(l).getAttributes().item(1).getFirstChild().getNodeValue();
                                        for (int m = 0; m < molecules.getChildNodes().getLength(); m++) {
                                            if (molecules.getChildNodes().item(m).getNodeName().equalsIgnoreCase("Molecule")) {
                                                DomElement mol = (DomElement) molecules.getChildNodes().item(m);
                                                if (mol.getAttribute("id").equalsIgnoreCase(moleculeID)) {
                                                    NodeList mol_att = mol.getChildNodes();
                                                    for (int t = 0; t < mol_att.getLength(); t++) {
                                                        // Trying to find the Preferred Symbol name: Same asa HUGO, HNBC_Symbol
                                                        if (mol_att.item(t).hasAttributes() && mol_att.item(t).getAttributes().item(0).getNodeValue().equalsIgnoreCase("PF"))
                                                        {
//                                                            System.out.println("Molecule name : " + mol_att.item(t).getAttributes().item(2).getNodeValue());
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
//                                        System.out.println(parts.item(l).getAttributes().item(1).getFirstChild().getNodeValue());
                                    }

                                }
                            }

                        }
                    }

                    counter++;
                }
            }
        }
        System.out.println("Number of pathways found : " + counter);

    }
}
