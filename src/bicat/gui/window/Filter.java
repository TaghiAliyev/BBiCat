/*
 *                                BBiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                DOI for citing the release : 10.5281/zenodo.33117
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
import bicat.biclustering.Dataset;
import bicat.gui.BicatGui;
import bicat.util.BicatError;
import bicat.util.BicatUtil;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Vector;

@Data
/**
 * GUI element related to the filtering of the clusters. Calls the filter method from UtilFunctionalities class
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class Filter implements ActionListener {

    private JDialog dialog;

    private BicatGui owner;
    private UtilFunctionalities engine;

    private int which_list = -1;

    private int list_idx = -1;

    private int which_data = -1;

    private JTextField minGsField;

    private JTextField maxGsField;

    private JTextField minCsField;

    private JTextField maxCsField;

    private JTextField bcNumber;

    private JFormattedTextField overlap;

    // ===========================================================================
    public Filter(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
    }

    public Filter(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    void reset() {
        which_list = -1;
        list_idx = -1;
        which_data = -1;
    }

    // ===========================================================================
    public void makeWindow() {

        reset();

        dialog = new JDialog(owner, "Filter Setup Dialog ");

        // //////////////////////////////////////////////////////////////////////////
        JPanel top = new JPanel(new FlowLayout());
        top.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), ""));

        /** @todo CHECK IF THERE IS ANY */
        Vector names = engine.getListNamesAll();

        JComboBox cb = new JComboBox(names);
        cb.setActionCommand(MethodConstants.FILTER_WINDOW_SELECT_BC_LIST);
        cb.setSelectedIndex(0);
        cb.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        cb.addActionListener(this);
        top.add(cb, BorderLayout.CENTER);

        // //////////////////////////////////////////////////////////////////////////

        int limitG = 0;
        int limitC = 0;

        // //////////////////////////////////////////////////////////////////////////
        JPanel bySize = new JPanel(new GridLayout(0, 2));
        bySize.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), "By Size"));

        JPanel aux = new JPanel(new GridLayout(1, 2));
        JLabel g = new JLabel(" Min gene cnt");
        aux.add(g);
        minGsField = new JTextField();
        minGsField.setText("0");
        aux.add(minGsField);

        bySize.add(aux);
        aux = new JPanel(new GridLayout(1, 2));

        g = new JLabel(" Max gene cnt");
        aux.add(g);
        maxGsField = new JTextField();
        maxGsField.setText("0");
        aux.add(maxGsField);

        bySize.add(aux);
        aux = new JPanel(new GridLayout(1, 2));

        JLabel c = new JLabel(" Min chip cnt");
        aux.add(c);
        minCsField = new JTextField();
        minCsField.setText("0");
        aux.add(minCsField);

        bySize.add(aux);
        aux = new JPanel(new GridLayout(1, 2));

        c = new JLabel(" Max chip cnt");
        aux.add(c);
        maxCsField = new JTextField();
        maxCsField.setText("0");
        aux.add(maxCsField);

        bySize.add(aux);

        // //////////////////////////////////////////////////////////////////////////
        JPanel byOverlap = new JPanel(new GridLayout(0, 2));
        byOverlap.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createLineBorder(Color.black), "By Overlap"));

        JLabel l = new JLabel(" Limit the number of biclusters ");
        byOverlap.add(l);

        bcNumber = new JTextField();
        bcNumber.setText(MethodConstants.FILTER_WINDOW_DEFAULT_NR_OUTPUT_BICLUSTERS);
        byOverlap.add(bcNumber);

        l = new JLabel(" Limit the allowed overlap (%)  ");
        byOverlap.add(l);

        overlap = new JFormattedTextField(NumberFormat.getIntegerInstance());
        overlap.setValue(new Integer(MethodConstants.FILTER_WINDOW_DEFAULT_ALLOWED_OVERLAP));
        byOverlap.add(overlap);

        // //////////////////////////////////////////////////////////////////////////
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton okayButton = new JButton("OK");
        okayButton.setMnemonic(KeyEvent.VK_K);
        okayButton.setActionCommand(MethodConstants.FILTER_WINDOW_APPLY);
        okayButton.addActionListener(this);
        buttonPanel.add(okayButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic(KeyEvent.VK_C);
        cancelButton.setActionCommand(MethodConstants.FILTER_WINDOW_CANCEL);
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        // //////////////////////////////////////////////////////////////////////////

        // main panel of dialog window
        JPanel contentPane = new JPanel(new GridLayout(0, 1));
        contentPane.add(top);
        contentPane.add(bySize);
        contentPane.add(byOverlap);
        contentPane.add(buttonPanel);

        dialog.setContentPane(contentPane);

        // set size, location and make visible
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    // ===========================================================================
    private void updateLabel_BiclusterListSelection(String item) {

        if (MethodConstants.debug)
            System.out.println("D, label selected: " + item);

        int[] info = BicatUtil.getListAndIdx(item);
        which_data = info[2];
        which_list = info[0];
        list_idx = info[1];

        if (info[0] == -1) {
            // System.out.println("Select something! (Canceling)");
            dialog.setVisible(false);
            dialog.dispose();
        } else {
            engine.updateCurrentDataset(which_data); // to do, or not?

            int limit_G = ((Dataset) engine.getDatasetList().get(which_data))
                    .getGeneCount();
            int limit_C = ((Dataset) engine.getDatasetList().get(which_data))
                    .getWorkingChipCount();

            maxGsField.setText(Integer.toString(limit_G));
            maxCsField.setText(Integer.toString(limit_C));

            if (MethodConstants.debug)
                System.out.println("D: data = " + which_data + ", list = "
                        + which_list + ", idx= " + list_idx);
        }
    }

    private int tries = 0;

    // ===========================================================================

    /**
     * For <code>ActionListener</code> interface, reacts to user selections
     * and button clicks in this search window.
     */
    public void actionPerformed(ActionEvent e) {

        if (MethodConstants.FILTER_WINDOW_SELECT_BC_LIST.equals(e.getActionCommand())) {
            JComboBox box = (JComboBox) e.getSource();
            String item = (String) box.getSelectedItem();
            updateLabel_BiclusterListSelection(item);
        } else if (MethodConstants.FILTER_WINDOW_APPLY.equals(e.getActionCommand())) {

            if (which_list == -1) {
                JOptionPane
                        .showMessageDialog(null,
                                "Please choose the bicluster list\nand values for the gene and chip count.");
                // System.out.println("Select something! (Canceling)");
                tries++;
                if (tries == 2) {
                    dialog.setVisible(false);
                    dialog.dispose();
                }
            } else {
                int minGs = (new Integer(minGsField.getText())).intValue();
                int maxGs = (new Integer(maxGsField.getText())).intValue();
                int minCs = (new Integer(minCsField.getText())).intValue();
                int maxCs = (new Integer(maxCsField.getText())).intValue();
                int nrBCs = (new Integer(bcNumber.getText())).intValue();
                int ovrlp = (new Integer(overlap.getText())).intValue();

                // catch too high numbers of genes and conditions
                int limit_G = ((Dataset) engine.getDatasetList().get(which_data))
                        .getGeneCount();
                int limit_C = ((Dataset) engine.getDatasetList().get(which_data))
                        .getWorkingChipCount();
                if (minGs > limit_G)
                    JOptionPane.showMessageDialog(null,
                            "The minimun gene count is too large");
                else if (minCs > limit_C)
                    JOptionPane.showMessageDialog(null,
                            "The minimun chip count is too large");
                else
                    try {
                        engine.getPost().filter(which_data, which_list, list_idx, minGs,
                                maxGs, minCs, maxCs, nrBCs, ovrlp);
                    } catch (NumberFormatException ee) {
                        BicatError.errorMessage(ee);
                    }

                dialog.setVisible(false);
                dialog.dispose();
            }
        } else if (MethodConstants.FILTER_WINDOW_CANCEL.equals(e.getActionCommand())) {
            // close window without doing anything
            dialog.setVisible(false);
            dialog.dispose();
        } else
            System.out.println("unknown event: " + e.paramString());
    }
}
