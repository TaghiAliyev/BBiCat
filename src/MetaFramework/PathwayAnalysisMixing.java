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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Just a test class, playing around with the pathway analysis
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class PathwayAnalysisMixing {

    public static void main(String[] args) throws Exception {
//        System.out.println(System.getProperty("os.arch"));
        Document mainDoc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        long start = System.currentTimeMillis();
        mainDoc = db.parse(new File("C:/Users/tagi1_000/Desktop/NCI.xml"));
        System.out.println("Done parsing");
        mainDoc.getDocumentElement().normalize();
        System.out.println("Done normalizing as well");
        NodeList models = mainDoc.getElementsByTagName("Model");
        System.out.println(models.getLength());
        int lengthModel = models.getLength();
        for (int i = 0; i < lengthModel; i++) {
            Node instance = models.item(i);
            System.out.println("Current element : " + instance.getNodeName());
            if (instance.getNodeType() == Node.ELEMENT_NODE) {
                Element tmp = (Element) instance;
                Node pathwayList = tmp.getElementsByTagName("PathwayList").item(0);
                Node interactionList = tmp.getElementsByTagName("InteractionList").item(0);
                Node moleculesList = tmp.getElementsByTagName("MoleculeList").item(0);
                HashMap<Integer, Molecule> molecules = new HashMap<Integer, Molecule>();
                HashMap<String, Interaction> interactions = new HashMap<String, Interaction>();
                HashMap<Integer, Pathway> pathways = new HashMap<Integer, Pathway>();
                readMolecules(molecules, moleculesList);
                // Done reading the molecules into hashmap
                // Now, the interactions
                readInteractions(interactions, interactionList, molecules);
                readPathways(pathways, pathwayList, interactions, molecules);
                // Hashmaps
                HashMap<Pathway, ArrayList<Molecule>> pathToGene = new HashMap<Pathway, ArrayList<Molecule>>();
                HashMap<Molecule, ArrayList<Pathway>> geneToPath = new HashMap<Molecule, ArrayList<Pathway>>();

                fillHashs(pathToGene, geneToPath, pathways);
                ArrayList<Molecule> result = pathToGene.get(new Pathway(null, 12, null, "Aurora C signaling"));
                for (Molecule name : result)
                    System.out.println(name.getName());
//                System.out.println(pathToGene.get(new Pathway(null, 12, null, "Aurora C signaling")).get(0).getName());
                long end = System.currentTimeMillis();
                System.out.println("Time spent : " + (end - start));
                // TODO : Populate the hashmaps
                System.out.println("Amount of molecules : " + molecules.size());
                System.out.println("Number of interactions : " + interactions.size());
                System.out.println("Number of pathways : " + pathways.size());

            }
        }
    }

    public static void fillHashs(HashMap<Pathway, ArrayList<Molecule>> pathToGene, HashMap<Molecule, ArrayList<Pathway>> geneToPath, HashMap<Integer, Pathway> pathways) {
        Set<Integer> allId = pathways.keySet();
        for (Integer id : allId) {
            Pathway pathway = pathways.get(id);
            if (pathway != null) {
                // Not null, let's do this. Though it can never be null
                ArrayList<Molecule> mols = pathway.getMolList();
                pathToGene.put(pathway, mols);
                for (Molecule mol : mols) {
                    ArrayList<Pathway> genePaths;
                    if (geneToPath.get(mol) == null) {
                        genePaths = new ArrayList<Pathway>();
                        genePaths.add(pathway);
                        geneToPath.put(mol, genePaths);
                    } else {
                        genePaths = geneToPath.get(mol);
                        genePaths.add(pathway);
                        geneToPath.put(mol, genePaths);
                    }
                }
            }
        }
    }

    public static void readPathways(HashMap<Integer, Pathway> pathways, Node pathwayList, HashMap<String, Interaction> interactions, HashMap<Integer, Molecule> molecules) {
        // TODO : Read through the pathways. Should be quite straight-forward
        long start = System.currentTimeMillis();
        if (pathwayList.getNodeType() == Node.ELEMENT_NODE) {
            Element pathE = (Element) (pathwayList);
            NodeList pathwayL = pathE.getElementsByTagName("Pathway");
            int pLen = pathwayL.getLength();
            for (int i = 0; i < pLen; i++) {
                Node path = pathwayL.item(i);
                if (path.getNodeType() == Node.ELEMENT_NODE) {
                    Element ePath = (Element) path;
                    int id = Integer.parseInt(ePath.getAttribute("id"));
                    String name = ePath.getElementsByTagName("LongName").item(0).getFirstChild().getNodeValue();
//                    System.out.println("Pathway name : " + name);
//                    System.out.println(id);
                    Node pathComList = ePath.getElementsByTagName("PathwayComponentList").item(0);
                    if (pathComList != null) {
                        NodeList pathCom = pathComList.getChildNodes();
                        int comLen = pathCom.getLength();
                        ArrayList<Interaction> interac = new ArrayList<Interaction>();
                        Set<Molecule> molecList = new HashSet<Molecule>();
                        for (int j = 0; j < comLen; j++) {
                            Node components = pathCom.item(j);
                            if (components.getNodeType() == Node.ELEMENT_NODE) {
                                Element compE = (Element) components;
                                String interID = compE.getAttribute("interaction_idref");
                                Interaction tmp = interactions.get(interID);
                                if (tmp != null) {
                                    interac.add(tmp);
                                    molecList.addAll(tmp.getMolecules());
                                }
                            }
                        }
                        ArrayList<Molecule> molList = new ArrayList<Molecule>();
                        molList.addAll(molecList);
                        Pathway pathway = new Pathway(interac, id, molList, name);
                        pathways.put(id, pathway);
                    }
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Time spent for reading pathways : " + (end - start));
    }

    public static void readInteractions(HashMap<String, Interaction> interactions, Node interactionList, HashMap<Integer, Molecule> molecules) {
        long start = System.currentTimeMillis();
        if (interactionList.getNodeType() == Node.ELEMENT_NODE) {
            Element interE = (Element) interactionList;
            NodeList child = interE.getElementsByTagName("Interaction");
            int childLen = child.getLength();
            for (int k = 0; k < childLen; k++) {
                Node el = child.item(k);
                if (el.getNodeType() == Node.ELEMENT_NODE) {
                    Element tmp = (Element) el;
                    String id = tmp.getAttribute("id");
                    Node components = tmp.getElementsByTagName("InteractionComponentList").item(0);
                    if (components.getNodeType() == Node.ELEMENT_NODE) {
                        Element compE = (Element) components;
                        NodeList parts = compE.getElementsByTagName("InteractionComponent");
                        int lenP = parts.getLength();
                        Interaction interaction;
                        ArrayList<Molecule> moleculesToAdd = new ArrayList<Molecule>();
                        for (int i = 0; i < lenP; i++) {
                            Element elC = (Element) parts.item(i);
                            int molID = Integer.parseInt(elC.getAttribute("molecule_idref"));
                            Molecule mol = molecules.get(molID); // Getting the molecule
                            if (mol.isType()) {
                                // mol is complex -> Meaning we need to go through the list of ids
                                ArrayList<Integer> toAdd = mol.getIds();
                                for (Integer tmpAdd : toAdd) {
                                    // What if this one is also complex? SCREWED FOR NOW.
                                    // BUT CAN A COMPLEX BE COMBINATION OF SIMPLE AND COMPLEX? HOPEFULLY NOT
                                    moleculesToAdd.add(molecules.get(tmpAdd));
                                }
                            } else {
                                // mol is simple
                                moleculesToAdd.add(mol);
                            }
                        }
                        interaction = new Interaction(moleculesToAdd, id);
                        interactions.put(id, interaction); // Adds the interactions
                    }
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Time spent for interactions : " + (end - start) + " ms");
    }

    public static void readMolecules(HashMap<Integer, Molecule> molecules, Node moleculesList) {
        long start = System.currentTimeMillis();
        if (moleculesList.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println("Hashing the molecules");
            Element molE = (Element) moleculesList;
            NodeList molList = molE.getElementsByTagName("Molecule");
            System.out.println("Amount of molecules : " + molList.getLength());
            int lengthMol = molList.getLength();
            for (int j = 0; j < lengthMol; j++) {
//                        System.out.println("At iteration : " + j);
                Element mol = (Element) molList.item(j);
                int id = Integer.parseInt(mol.getAttribute("id"));
                String type = mol.getAttribute("molecule_type");
                String name = "";
                if (!type.equalsIgnoreCase("complex")) {
                    // Simple, just get PF
                    NodeList PF = mol.getElementsByTagName("Name");
                    for (int k = 0; k < PF.getLength(); k++) {
                        Element PFt = (Element) PF.item(k);
                        if (PFt.getAttribute("name_type").equalsIgnoreCase("PF")) {
                            name = PFt.getAttribute("value");
                            molecules.put(id, new Molecule(id, name, false, null));
//                                    System.out.println(name);
                        }
                    }
                } else {
                    // Complex -> Means there is an element with ComplexComponentList tag
                    Node complexList = mol.getElementsByTagName("ComplexComponentList").item(0);
                    if (complexList.getNodeType() == Node.ELEMENT_NODE) {
                        Element cE = (Element) complexList;
                        NodeList list = cE.getElementsByTagName("ComplexComponent");
                        NodeList PF = mol.getElementsByTagName("Name");
                        for (int k = 0; k < PF.getLength(); k++) {
                            Element PFt = (Element) PF.item(k);
                            if (PFt.getAttribute("name_type").equalsIgnoreCase("PF")) {
                                name = PFt.getAttribute("value");
                                molecules.put(id, new Molecule(id, name, false, null));
//                                    System.out.println(name);
                            }
                        }
                        int lenC = list.getLength();
                        ArrayList<Integer> ids = new ArrayList<Integer>();
                        for (int k = 0; k < lenC; k++) {
                            Element cEl = (Element) list.item(k);
                            int molID = Integer.parseInt(cEl.getAttribute("molecule_idref"));
                            ids.add(molID);
                        }
                        // IDs of reference molecules are known
                        // Name of the complex molecule is known as well
                        molecules.put(id, new Molecule(id, name, true, ids));
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Time spent for molecules : " + (end - start));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Molecule implements Comparable {
        private int id;
        private String name; // maybe just String needed
        private boolean type; // False -> protein, True -> Complex
        private ArrayList<Integer> ids; // if type is True -> list of ids making up the complex protein

        @Override
        public int hashCode() {
            return 0;
        }

        private static int minimum(int a, int b, int c) {
            return Math.min(Math.min(a, b), c);
        }

        public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {
            int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

            for (int i = 0; i <= lhs.length(); i++)
                distance[i][0] = i;
            for (int j = 1; j <= rhs.length(); j++)
                distance[0][j] = j;

            for (int i = 1; i <= lhs.length(); i++)
                for (int j = 1; j <= rhs.length(); j++)
                    distance[i][j] = minimum(
                            distance[i - 1][j] + 1,
                            distance[i][j - 1] + 1,
                            distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

            return distance[lhs.length()][rhs.length()];
        }


        @Override
        public boolean equals(Object o) {
            if (o instanceof Molecule) {
                Molecule o2 = (Molecule) o;
                if (name.equalsIgnoreCase(o2.name))
                    return true;

                // TODO : MIGHT NEED TO USE LEVENSHTEIN DISTANCE TO GET BETTER
            }

            return false;
        }

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Interaction {
        private ArrayList<Molecule> molecules;
        private String id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Pathway implements Comparable {
        private ArrayList<Interaction> interactions;
        private int id;
        private ArrayList<Molecule> molList;
        private String name;

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public int compareTo(Object o) {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Pathway) {
                Pathway o2 = (Pathway) o;
                if (o2.id == id || o2.name.equalsIgnoreCase(name))
                    return true;
            }


            return false;
        }
    }
}