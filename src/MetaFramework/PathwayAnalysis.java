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

package MetaFramework;

import gnu.xml.dom.DomElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains the methods needed for generating and reading the information about pathways and related proteins
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class PathwayAnalysis {

    // Two hashmaps that will contain pathway to the genes relations and genes to pathway relations
    HashMap<String, ArrayList<String>> pathwayToGenes = new HashMap<String, ArrayList<String>>();
    HashMap<String, ArrayList<String>> geneToPathways = new HashMap<String, ArrayList<String>>();

    // Main Document File that will be used around
    Document mainDoc;
    DomElement model, interactions, molecules, pathwayList;

    /**
     * Constructor for the pathway analysis. File is the NCI Curated XML file
     * Constructor creates all the variables and fills in the hashmaps already
     *
     * @param file String representing the location of the XML file
     * @throws Exception
     */
    public PathwayAnalysis(String file) throws Exception {
        // Will read the NCI Curated XML file
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        mainDoc = db.parse(new File(file));
        System.out.println("Done reading the xml file!");
        System.out.println(mainDoc.getFirstChild().getNodeName());

        // Reading the parts of the ontology that we will use. Ontology tag is not used
        // Because molecules/proteins, interactions and pathways are all in the Model tag
        Element docElement = mainDoc.getDocumentElement();
        model = (DomElement) docElement.getElementsByTagName("Model").item(0);
        interactions = (DomElement) model.getElementsByTagName("InteractionList").item(0);
        molecules = (DomElement) model.getElementsByTagName("MoleculeList").item(0);
        pathwayList = (DomElement) model.getElementsByTagName("PathwayList").item(0);
        createHashMaps();
    }

    /**
     * This method goes through all pathways and updates the hashmaps with the information about which proteins
     * are connected with the pathway
     */
    public void createHashMaps() {
        int length2 = pathwayList.getChildNodes().getLength();
        for (int i = 0; i < length2; i++) {
            if (pathwayList.getChildNodes().item(i).getNodeName().equalsIgnoreCase("Pathway")) {
                // This is a pathway!
                DomElement PathwayComponent = (DomElement) ((DomElement) (pathwayList.getChildNodes().item(i))).getElementsByTagName("PathwayComponentList").item(0);

                // Seems like there is one exception to this rule. I think the pathway is represented as being an interaction
                // Ignored for now.
                if (PathwayComponent == null)
                    System.out.println("Strange Pathway at :" + i);
                else {
                    System.out.println("Pathway number : " + i);
//                    System.out.println(PathwayComponent.getElementsByTagName("PathwayComponent").getLength());
                    NodeList pathName = (DomElement) ((DomElement) (pathwayList.getChildNodes().item(i))).getElementsByTagName("LongName").item(0);
                    String pathwayName = pathName.item(0).getNodeValue();
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
                                    findMolecules(parts, pathwayName);
                                    // Adding the correct relations
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void findMolecules(NodeList parts, String pathwayName) {
        // TODO : We have to look if molecules are complex are not
        // If they are, just add them to the hashmaps.

//        System.out.println("Length : " + parts.getLength());
        for (int l = 0; l < parts.getLength(); l++) {
            // We should look for molecules here
//            String moleculeID = "200185";
            String moleculeID = parts.item(l).getAttributes().item(1).getFirstChild().getNodeValue();
            for (int m = 0; m < molecules.getChildNodes().getLength(); m++) {
                if (molecules.getChildNodes().item(m).getNodeName().equalsIgnoreCase("Molecule")) {

                    DomElement mol = (DomElement) molecules.getChildNodes().item(m);
                    if (mol.getAttribute("id").equalsIgnoreCase(moleculeID)) {
                        NodeList mol_att = mol.getChildNodes();
                        for (int t = mol_att.getLength() - 1; t >= 0; t--) {
                            // Trying to find the Preferred Symbol name: Same asa HUGO, HNBC_Symbol
                            boolean done = false;
                            if (mol_att.item(t) instanceof DomElement) {
                                DomElement child = (DomElement) (mol_att.item(t));
                                if (child.getNodeName().equalsIgnoreCase("FamilyMemberList")) {
                                    // This means there are family members in the list
                                    for (int i = 0; i < child.getChildNodes().getLength(); i++) {
                                        if (child.getChildNodes().item(i).hasChildNodes()) {
                                            // Let's find the actual name of the guys.
                                            searchForTheName(child.getChildNodes().item(i).getAttributes().item(0).getNodeValue(), pathwayName);
                                            done = true;
                                        }
                                    }
                                }
                                if (mol_att.item(t).hasAttributes() && mol_att.item(t).getAttributes().item(0).getNodeValue().equalsIgnoreCase("PF")) {
                                    // Means that it is not family member and is just simple
                                    String name = mol_att.item(t).getAttributes().item(2).getNodeValue();
                                    String[] toAdd = name.split("/");
                                    for (String tmp : toAdd)
                                        addToHashmaps(tmp, pathwayName);
                                    done = true;
                                }
                                if (done)
                                    return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void searchForTheName(String name, String pathwayName)
    {
        // Let's find the name of the molecule with id-> name
        for (int m = 0; m < molecules.getChildNodes().getLength(); m++) {
            if (molecules.getChildNodes().item(m).getNodeName().equalsIgnoreCase("Molecule")) {

                DomElement mol = (DomElement) molecules.getChildNodes().item(m);
                if (mol.getAttribute("id").equalsIgnoreCase(name)) {
                    NodeList mol_att = mol.getChildNodes();
                    for (int t = mol_att.getLength() - 1; t >= 0; t--) {
                        // Trying to find the Preferred Symbol name: Same asa HUGO, HNBC_Symbol
                            if (mol_att.item(t).hasAttributes() && mol_att.item(t).getAttributes().item(0).getNodeValue().equalsIgnoreCase("PF")) {
                                // Means that it is not family member and is just simple
                                String gg = mol_att.item(t).getAttributes().item(2).getNodeValue();
                                String[] toAdd = gg.split("/");
                                for (String tmp : toAdd)
                                    addToHashmaps(tmp, pathwayName);
                            }
                    }
                }
            }
        }
    }


    public void addToHashmaps(String molName, String pathwayName) {
        if (pathwayToGenes.get(pathwayName) == null) {
            ArrayList<String> alreadyKnown = new ArrayList<String>();
            alreadyKnown.add(molName);
            pathwayToGenes.put(pathwayName, alreadyKnown);
        } else {
            ArrayList<String> alreadyKnown = pathwayToGenes.get(pathwayName);
            alreadyKnown.add(molName);
            pathwayToGenes.put(pathwayName, alreadyKnown);
        }

        // Second geneToPathway
        if (geneToPathways.get(molName) == null) {
            ArrayList<String> pathways = new ArrayList<String>();
            pathways.add(pathwayName);
            geneToPathways.put(molName, pathways);
        } else {
            ArrayList<String> pathways = geneToPathways.get(molName);
            pathways.add(pathwayName);
            geneToPathways.put(molName, pathways);
        }
    }

}
