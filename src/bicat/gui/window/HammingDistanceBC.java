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

import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Bicluster;
import bicat.gui.BicatGui;
import bicat.postprocessor.Postprocessor;
import bicat.preprocessor.Preprocessor;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Vector;

@Data
public class HammingDistanceBC implements ActionListener {

    private JDialog dialog;
    private BicatGui owner;
    private UtilFunctionalities engine;
    private Preprocessor preprocessor;


    private Bicluster bcluster;
    private boolean extended = false;

    // ===========================================================================
    public HammingDistanceBC() {
    }

    public HammingDistanceBC(UtilFunctionalities engine) {
        this.engine = engine;
        owner = null;
        this.preprocessor = engine.getPre();
    }

    public HammingDistanceBC(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
        this.preprocessor = engine.getPre();
    }

    public HammingDistanceBC(Bicluster bc, BicatGui o) {
        bcluster = bc;
        owner = o;
        this.engine = o.getUtilEngine();
        this.preprocessor = engine.getPre();
    }

    public HammingDistanceBC(Bicluster bc, boolean isExt, BicatGui o) {
        bcluster = bc;
        extended = isExt;
        owner = o;
        this.engine = o.getUtilEngine();
        this.preprocessor = engine.getPre();
    }

    static final String HAMMING_DISTANCE_BC_WINDOW_EXTEND_GENE_D = "hdbc_gene_d";
    static final String HAMMING_DISTANCE_BC_WINDOW_EXTEND_CHIP_D = "hdbc_chip_d";
    static final String HAMMING_DISTANCE_BC_WINDOW_APPLY = "hdbc_apply";
    static final String HAMMING_DISTANCE_BC_WINDOW_CANCEL = "hdbc_cancel";

    private JFormattedTextField field;

    // ===========================================================================
    public void makeWindow() {

        dialog = new JDialog(owner, "BC Extension Setup Dialog ");

        JPanel top = new JPanel(new GridLayout(0, 1));
        top.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), ""));

        JPanel pp = new JPanel(new GridLayout(0, 2));

        JRadioButton right = new JRadioButton("Extend in gene dimension");
        right.setActionCommand(HAMMING_DISTANCE_BC_WINDOW_EXTEND_GENE_D);
        right.addActionListener(this);
        pp.add(right);

        JRadioButton left = new JRadioButton("Extend in chip dimension"); //Absolute values ( 2 files )");
        left.setActionCommand(HAMMING_DISTANCE_BC_WINDOW_EXTEND_CHIP_D);
        left.addActionListener(this);
        pp.add(left);

        top.add(pp);

        JPanel pane = new JPanel(new GridLayout(0, 2));

        JLabel label = new JLabel("Specify allowed error (%)");
        pane.add(label);

        field = new JFormattedTextField(NumberFormat.getIntegerInstance());
        field.setValue(new Integer(5));
        field.addActionListener(this);
        pane.add(field);

        top.add(pane, BorderLayout.CENTER);

        // create bottom subpanel with cancel and OK buttons
        JPanel closePanel = new JPanel(new FlowLayout());

        JButton okayButton = new JButton("OK");
        okayButton.setMnemonic(KeyEvent.VK_K);
        okayButton.setActionCommand(HAMMING_DISTANCE_BC_WINDOW_APPLY);
        okayButton.addActionListener(this);
        closePanel.add(okayButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic(KeyEvent.VK_C);
        cancelButton.setActionCommand(HAMMING_DISTANCE_BC_WINDOW_CANCEL);
        cancelButton.addActionListener(this);
        closePanel.add(cancelButton);

        // ....
        // main panel of dialog window
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(top);
        contentPane.add(closePanel, BorderLayout.PAGE_END);
        // contentPane.setOpaque(true);
        dialog.setContentPane(contentPane);

        // set size, location and make visible
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    boolean gene_d_selected = false;
    boolean chip_d_selected = false;

    // ===========================================================================

    /**
     * For <code>ActionListener</code> interface, reacts to user selections and
     * button clicks in this search window.
     */
    public void actionPerformed(ActionEvent e) {

        if (HAMMING_DISTANCE_BC_WINDOW_APPLY.equals(e.getActionCommand())) {

            int bcErr = (new Integer(field.getText()).intValue());

            Bicluster extension;
            if (extended)
                extension = Postprocessor.getExtension(bcluster, bcErr, extended, preprocessor.getDiscreteData(), gene_d_selected);
            else
                extension = Postprocessor.getExtension(bcluster, bcErr, extended, preprocessor.getDiscreteData(), gene_d_selected);

            owner.getPp().clearSelections();
            Vector[] currentBiclusterSelection = owner.getPp().setTranslationTable(extension);

            owner.getMatrixScrollPane().repaint();
            owner.readjustPictureSize();
            owner.getPp().repaint();

            owner.getPp().biclusterSelected(currentBiclusterSelection[1], currentBiclusterSelection[2]);
            owner.refreshGraphicPanel();
            owner.getPp().repaint();
            owner.getGp().setGraphDataList(currentBiclusterSelection[0], currentBiclusterSelection[1], currentBiclusterSelection[2]);
            owner.getPp().repaint();

            dialog.setVisible(false);
            dialog.dispose();
        } else if (HAMMING_DISTANCE_BC_WINDOW_CANCEL.equals(e.getActionCommand())) {
            // close window without doing anything
            dialog.setVisible(false);
            dialog.dispose();
        } else if (HAMMING_DISTANCE_BC_WINDOW_EXTEND_GENE_D.equals(e.getActionCommand())) {
            if (gene_d_selected) {
                gene_d_selected = false;
                chip_d_selected = true;
            } else {
                gene_d_selected = true;
                chip_d_selected = false;
            }
        } else if (HAMMING_DISTANCE_BC_WINDOW_EXTEND_CHIP_D.equals(e.getActionCommand())) {
            if (chip_d_selected) {
                chip_d_selected = false;
                gene_d_selected = true;
            } else {
                chip_d_selected = true;
                gene_d_selected = false;
            }

        } else System.out.println("unknown event: " + e.paramString());
    }
}
