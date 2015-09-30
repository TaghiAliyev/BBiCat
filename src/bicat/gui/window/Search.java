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

package bicat.gui.window;

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;
import bicat.util.BicatUtil;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * Pop-up window that prompts user for search parameters.
 * <p>
 * Gets user input for a search of the bicluster list.
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class Search implements ActionListener {

    private JDialog dialog;

    private BicatGui owner;

    private UtilFunctionalities engine;

    private int list_idx = 0;

    private int which_list = -1; // 0 def BCs, 1 def Search Results, ...

    private int which_data = 0;

    private boolean andSearch;

    private JTextField geneNameField;

    private JTextField chipNameField;

    // ===========================================================================

    /**
     * Basic constructor, requires a handle to governing frame.
     */
    public Search(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    // ===========================================================================
    private void updateLabel_ListSelection(String item) {

        int[] info = BicatUtil.getListAndIdx(item);
        which_data = info[2];
        which_list = info[0];
        list_idx = info[1];

        if (info[0] == -1) {
            //System.out.println("Select something! (Canceling)");
            dialog.setVisible(false);
            dialog.dispose();
        } else {
            engine.updateCurrentDataset(which_data);
        }
    }

    // ===========================================================================

    /**
     * For <code>ActionListener</code> interface, reacts to user selections
     * and button clicks in this search window.
     */
    public void actionPerformed(ActionEvent e) {

        if (MethodConstants.SEARCH_WINDOW_SEARCH_AND.equals(e.getActionCommand())) {
            andSearch = true;
        } else if (MethodConstants.SEARCH_WINDOW_SEARCH_OR.equals(e.getActionCommand())) {
            andSearch = false;
        } else if (MethodConstants.SEARCH_WINDOW_SELECT_BC_LIST.equals(e.getActionCommand())) {
            JComboBox box = (JComboBox) e.getSource();
            String item = (String) box.getSelectedItem();
            updateLabel_ListSelection(item);
        } else if (MethodConstants.SEARCH_WINDOW_CANCEL.equals(e.getActionCommand())) {
            dialog.setVisible(false);
            dialog.dispose();
        } else if (MethodConstants.SEARCH_WINDOW_APPLY.equals(e.getActionCommand())) {

            String geneStr = geneNameField.getText();
            String chipStr = chipNameField.getText();

            if (which_list == -1) {
                JOptionPane
                        .showMessageDialog(null,
                                "Please choose a bicluster list\nin which you would like to search");
            } else {
                engine.getPost().search(which_data, which_list, list_idx, geneStr,
                        chipStr, andSearch);

                dialog.setVisible(false);
                dialog.dispose();
            }
        }
    }

    // ===========================================================================

    /**
     * Creates the visible pop up window with all buttons, labels and fields.
     */
    public void makeWindow() {

        dialog = new JDialog(owner, "Search Setup Dialog ");

        // //////////////////////////////////////////////////////////////////////////

        JPanel listChoice = new JPanel(new FlowLayout());
        listChoice.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), "Choose list of biclusters"));

        Vector names = engine.getListNamesAll();
        JComboBox cb = new JComboBox(names);
        cb.setActionCommand(MethodConstants.SEARCH_WINDOW_SELECT_BC_LIST);
        cb.setSelectedIndex(0);
        cb.addActionListener(this);
        listChoice.add(cb);

        // //////////////////////////////////////////////////////////////////////////

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        JButton okayButton = new JButton("OK");
        okayButton.setMnemonic(KeyEvent.VK_K);
        okayButton.setActionCommand(MethodConstants.SEARCH_WINDOW_APPLY);
        okayButton.addActionListener(this);
        buttonPanel.add(okayButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic(KeyEvent.VK_C);
        cancelButton.setActionCommand(MethodConstants.SEARCH_WINDOW_CANCEL);
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        // //////////////////////////////////////////////////////////////////////////

        JPanel top = new JPanel(new FlowLayout());

        JPanel labelPanel;
        JPanel fieldPane;

        // create gene, chip name panel
        JPanel gcPanel = new JPanel(new BorderLayout());
        gcPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), "Gene & Chip constraints"));

        // 1st subpane in gene/chip subpanel containing field descriptions
        labelPanel = new JPanel(new GridLayout(0, 1));
        labelPanel.add(new JLabel("Gene name: "));
        labelPanel.add(new JLabel("Chip name: "));
        labelPanel.add(new JLabel("Choose Search mode: "));
        labelPanel.add(new JLabel(" "));
        gcPanel.add(labelPanel, BorderLayout.CENTER);

        // 2nd subpane in gene/chip subpanel with name entry fields and AND/OR
        // radio buttons
        fieldPane = new JPanel(new GridLayout(0, 1));

        geneNameField = new JTextField(32);
        fieldPane.add(geneNameField);
        chipNameField = new JTextField(32);
        fieldPane.add(chipNameField);

        // radio buttons that let user specify whether to search for BCs
        // containing all names, or all BCs that contain at least one name
        ButtonGroup radioButtonGroup = new ButtonGroup();

        JRadioButton radioButton = new JRadioButton("AND");
        radioButton.setActionCommand(MethodConstants.SEARCH_WINDOW_SEARCH_AND);
        radioButton.addActionListener(this);
        radioButton.setSelected(true);
        andSearch = true;
        fieldPane.add(radioButton);
        radioButtonGroup.add(radioButton);

        radioButton = new JRadioButton("OR");
        radioButton.setActionCommand(MethodConstants.SEARCH_WINDOW_SEARCH_OR);
        radioButton.addActionListener(this);
        fieldPane.add(radioButton);
        radioButtonGroup.add(radioButton);
        gcPanel.add(fieldPane, BorderLayout.LINE_END);

        top.add(gcPanel);

        // //////////////////////////////////////////////////////////////////////////
        JPanel contentPane = new JPanel(new GridLayout(0, 1));

        contentPane.add(listChoice);
        contentPane.add(top);
        contentPane.add(buttonPanel);

        contentPane.setOpaque(true);
        dialog.setContentPane(contentPane);

        // set size, location and make visible
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

}
