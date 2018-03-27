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

package MetaFramework.NCI;

import MetaFramework.AbstractPathwayUtils.AbstractPathwayAnalysis;
import MetaFramework.AbstractPathwayUtils.Interaction;
import MetaFramework.AbstractPathwayUtils.Molecule;
import MetaFramework.AbstractPathwayUtils.Pathway;
import lombok.Data;
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
 * New version of a pathway database parser (NCI-Curated)
 * @author  Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class PathwayAnalysisMixing extends AbstractPathwayAnalysis<NCIMolecule, NCIInteraction, NCIPathway> {

    /**
	 * @uml.property  name="molecules"
	 */
    private HashMap<Integer, NCIMolecule> molecules;
    /**
	 * @uml.property  name="interactions"
	 */
    private HashMap<String, NCIInteraction> interactions;
    /**
	 * @uml.property  name="pathways"
	 */
    private HashMap<Integer, NCIPathway> pathways;


    public PathwayAnalysisMixing(String file) throws Exception {
        super(file);
        parse();
    }

    /**
     * Parser that reads through NCI-Curated pathway database
     * Prints out times spent on different parts of the parsing and overall time.
     * As a result creates two hashmaps : genesToPathways and pathwaysToGenes
     * These are lated used by the Bayesian analysis for post-analysis of the biclusters
     *
     * @throws Exception
     */
    public void parse() throws Exception {
//        System.out.println(System.getProperty("os.arch"));
        Document mainDoc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        long start = System.currentTimeMillis();
        mainDoc = db.parse(new File(file));
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
                molecules = new HashMap<Integer, NCIMolecule>();
                interactions = new HashMap<String, NCIInteraction>();
                pathways = new HashMap<Integer, NCIPathway>();
                readMolecules(molecules, moleculesList);
                // Done reading the molecules into hashmap
                // Now, the interactions
                readInteractions(interactions, interactionList, molecules);
                readPathways(pathways, pathwayList, interactions, molecules);
                // Hashmaps
                pathToGene = new HashMap<NCIPathway, ArrayList<NCIMolecule>>();
                geneToPath = new HashMap<NCIMolecule, ArrayList<NCIPathway>>();

                fillHashs(pathToGene, geneToPath, pathways);
//                ArrayList<Molecule> result = pathToGene.get(new Pathway(null, 12, null, "Aurora C signaling"));
//                for (Molecule name : result)
//                    System.out.println(name.getName());
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

    /**
     * Fills the hashmaps with the correct elements
     *
     * @param pathToGene
     * @param geneToPath
     * @param pathways
     */
    private void fillHashs(HashMap<NCIPathway, ArrayList<NCIMolecule>> pathToGene, HashMap<NCIMolecule, ArrayList<NCIPathway>> geneToPath, HashMap<Integer, NCIPathway> pathways) {
        Set<Integer> allId = pathways.keySet();
        for (Integer id : allId) {
            NCIPathway pathway = pathways.get(id);
            if (pathway != null) {
                // Not null, let's do this. Though it can never be null
                ArrayList<NCIMolecule> mols = pathway.getMolList();
                pathToGene.put(pathway, mols);
                for (NCIMolecule mol : mols) {
                    ArrayList<NCIPathway> genePaths;
                    if (geneToPath.get(mol) == null) {
                        genePaths = new ArrayList<NCIPathway>();
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

    /**
     * Reads through pathways. First interactions and molecules should be read and mapped before calling this method
     *
     * @param pathways  Hashmap that will be filled
     * @param pathwayList   Node from which search starts
     * @param interactions  List of already parsed interactions
     * @param molecules List of already parsed molecules
     */
    private void readPathways(HashMap<Integer, NCIPathway> pathways, Node pathwayList, HashMap<String, NCIInteraction> interactions, HashMap<Integer, NCIMolecule> molecules) {
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
                        ArrayList<NCIInteraction> interac = new ArrayList<NCIInteraction>();
                        Set<NCIMolecule> molecList = new HashSet<NCIMolecule>();
                        for (int j = 0; j < comLen; j++) {
                            Node components = pathCom.item(j);
                            if (components.getNodeType() == Node.ELEMENT_NODE) {
                                Element compE = (Element) components;
                                String interID = compE.getAttribute("interaction_idref");
                                NCIInteraction tmp = interactions.get(interID);
                                if (tmp != null) {
                                    interac.add(tmp);
                                    molecList.addAll(tmp.getMolecules());
                                }
                            }
                        }
                        ArrayList<NCIMolecule> molList = new ArrayList<NCIMolecule>();
                        molList.addAll(molecList);
                        NCIPathway pathway = new NCIPathway(interac, id, molList, name);
                        pathways.put(id, pathway);
                    }
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Time spent for reading pathways : " + (end - start));
    }

    /**
     * Method that parses list of interactions from the database and fills the hashmap
     *
     * @param interactions  Hashmap that will be filled in with the interactions
     * @param interactionList   Node from which search starts (top-level tree node)
     * @param molecules List of already parsed Molecules list
     */
    private void readInteractions(HashMap<String, NCIInteraction> interactions, Node interactionList, HashMap<Integer, NCIMolecule> molecules) {
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
                        NCIInteraction interaction;
                        ArrayList<NCIMolecule> moleculesToAdd = new ArrayList<NCIMolecule>();
                        for (int i = 0; i < lenP; i++) {
                            Element elC = (Element) parts.item(i);
                            int molID = Integer.parseInt(elC.getAttribute("molecule_idref"));
                            NCIMolecule mol = molecules.get(molID); // Getting the molecule
                            if (mol.isType() || mol.getIds() != null) {
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
                        interaction = new NCIInteraction(moleculesToAdd, id);
                        interactions.put(id, interaction); // Adds the interactions
                    }
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Time spent for interactions : " + (end - start) + " ms");
    }

    /**
     * Parses the list of molecules into an empty hashmap
     *
     * @param molecules Empty hashmap
     * @param moleculesList Top-level tree node for reading through the molecules list
     */
    private void readMolecules(HashMap<Integer, NCIMolecule> molecules, Node moleculesList) {
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
                    // Might have family component list
                    ArrayList<Integer> ids = null;
                    NodeList family = mol.getElementsByTagName("FamilyMemberList");
                    if (family != null && family.getLength() > 0)
                    {
                        Node famN = family.item(0);
                        if (famN.getNodeType() == Node.ELEMENT_NODE)
                        {
                            ids = new ArrayList<Integer>();
                            Element el = (Element) famN;
                            NodeList members = el.getElementsByTagName("Member");
                            int memLen = members.getLength();
                            for (int k = 0; k < memLen; k++)
                            {
                                Element mem = (Element) members.item(k);
                                String idM = mem.getAttribute("member_molecule_idref");
                                ids.add(Integer.parseInt(idM));
                            }
                        }
                    }
                    // Simple, just get PF
                    NodeList PF = mol.getElementsByTagName("Name");
                    for (int k = 0; k < PF.getLength(); k++) {
                        Element PFt = (Element) PF.item(k);
                        if (PFt.getAttribute("name_type").equalsIgnoreCase("PF")) {
                            name = PFt.getAttribute("value");
                            molecules.put(id, new NCIMolecule(id, name, false, ids));
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
                                molecules.put(id, new NCIMolecule(id, name, false, null));
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
                        molecules.put(id, new NCIMolecule(id, name, true, ids));
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Time spent for molecules : " + (end - start));
    }



}
