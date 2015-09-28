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
