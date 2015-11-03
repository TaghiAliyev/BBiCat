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

package bicat.gui;

import MetaFramework.RConnection;
import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Dataset;
import bicat.util.BicatUtil;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

/**
 * GUI element that will do translation and alter the loaded dataset
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class TranslationGUI implements ActionListener{

    private BicatGui owner;
    private UtilFunctionalities engine;

    private JDialog dialog;

    public TranslationGUI(BicatGui owner)
    {
        this.owner = owner;
        this.engine = owner.getUtilEngine();
    }

    public TranslationGUI(UtilFunctionalities engine)
    {
        this.owner = null;
        this.engine = engine;
    }

    public TranslationGUI()
    {
        this.owner = null;
        this.engine = new UtilFunctionalities();
    }

    public void showYourself()
    {

        dialog = new JDialog(owner, "Transform/Translate your genes to different domain!");
        // TODO: Implement the logic related to translation engine
        // Need to be able to read database names as input
        // Choose the dataset
        // Do translation to HGNC for now and alter the dataset
        JPanel top = new JPanel(new FlowLayout());
        top.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), ""));

        Vector names = engine.getListDatasets();

        JComboBox cb = new JComboBox(names);
        cb.setActionCommand(MethodConstants.FILTER_WINDOW_SELECT_BC_LIST);
        cb.setSelectedIndex(0);
        cb.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        cb.addActionListener(this);
        top.add(cb, BorderLayout.CENTER);

        JPanel dataName = new JPanel(new GridLayout(1, 2));
        dataName.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), "Enter database name"));

        JLabel tmp = new JLabel("Enter the database name");
        dataName.add(tmp);
        JTextField dataField = new JTextField(20);
        dataField.setText("ath1121501.db");
        dataName.add(dataField);

        JPanel buttons = new JPanel(new FlowLayout());

        JButton start = new JButton("Start the translation");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Here the actual translation should occur
                // If there are more than 1 possibilities: Take user input and let user choose the option
                translate(dataField.getText());
                System.out.println("Database chosen : " + dataField.getText());
            }
        });
        buttons.add(start);

        JPanel contentPane = new JPanel(new GridLayout(0, 1));
        contentPane.add(top);
        contentPane.add(dataName);
        contentPane.add(buttons);

        dialog.setContentPane(contentPane);

        // set size, location and make visible
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    private void translate(String dbName)
    {
        // TODO : A Problem! If a given database is wrong, R will throw an exception and results will be empty
        // So, we need to catch it and let the user know about it
        Dataset dataset = (Dataset) engine.getDatasetList().get(which_data);
        Vector<String> genes = dataset.getGeneNames();
        String rScript = "C:/Users/tagi1_000/Desktop/CERN/BBiCat/TranslationScript.R";
        RConnection connection = new RConnection(false);
        connection.setUp(rScript);
        // Entrez : org.Hs.eg.db
        // Some random: hgu133plus2.db . Examples for it : "91617_at","78495_at","65585_at", "241834_at","209079_x_at"
        //
        connection.getCode().addRCode("db <- \"" + dbName + "\"");
        // Note: this ones do not have any ambiguity. They are 1 to 1 matches. Still looking for ambiguity
        // "91617_at","78495_at","65585_at", "241834_at",
        connection.getCode().addStringArray("geneNames", genes.toArray(new String[1]));
        connection.callRScript("result");
        String entrOrProbe = connection.getRcaller().getParser().getNames().get(0);

        // Getting the symbol
        NodeList symList = connection.getRcaller().getParser().getValueNodes("SYMBOL");
        NodeList entrezList = connection.getRcaller().getParser().getValueNodes(entrOrProbe);
        HashMap<String, ArrayList<String>> entrezToSymbols = new HashMap<String, ArrayList<String>>();

        for (int i = 0; i < symList.getLength(); i++) {
            if (symList.item(i).getChildNodes() != null && symList.item(i).getChildNodes().getLength() > 0) {
                String entrezID = entrezList.item(i).getChildNodes().item(0).getNodeValue();
                String geneName = symList.item(i).getChildNodes().item(0).getNodeValue();
                if (entrezToSymbols.get(entrezID) == null) {
                    ArrayList<String> syms = new ArrayList<String>();
                    syms.add(geneName);
                    entrezToSymbols.put(entrezID, syms);
                } else {
                    ArrayList<String> syms = entrezToSymbols.get(entrezID);
                    syms.add(geneName);
                    entrezToSymbols.put(entrezID, syms);
                }
            }
        }

        // Here is the idea: Filter through the genes and only keep the ones we have
        // Afterwards check if there is still more than two left
        // Let's go through them
        Set<String> allEntrez = entrezToSymbols.keySet();
        for (String tmp : allEntrez) {
            ArrayList<String> allGenes = entrezToSymbols.get(tmp);
            System.out.println("Amount of Genes that will be filtered: " + allGenes.size());
            ArrayList<String> actualThere = new ArrayList<String>();
            for (String tmp2 : allGenes) {
//                System.out.println(tmp2);
                // Filter it here
                if (owner.getEngine().getGeneToPathways().get(tmp2) != null) {
                    actualThere.add(tmp2);
                    for (String pathway : owner.getEngine().getGeneToPathways().get(tmp2))
                        System.out.println(pathway);
                }
            }
            System.out.println(actualThere.size());
            if (actualThere.size() > 1)
                System.out.println("DAAAAAAANGEEEEEER -> Here we will have the choosing option");
            System.out.println();
        }
    }


    private int which_data;

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox box = (JComboBox) e.getSource();
        String item = (String) box.getSelectedItem();
        which_data = Integer.parseInt(item.split(" ")[1]);
    }
}
