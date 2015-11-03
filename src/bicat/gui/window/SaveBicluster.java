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

package bicat.gui.window;

import bicat.gui.BicatGui;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
/**
 * This class is never used as there are other functions which does the work
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class SaveBicluster implements ActionListener {

    /**
     * Dialog window.
     */
    private JDialog dialog;
    /**
     * Hook to governing <code>BicaGUI</code>.
     */
    private BicatGui owner;

    private boolean ok;
    private int gene_offset;
    private int chip_offset;

    // ===========================================================================
    public SaveBicluster() {
    }

    // ===========================================================================
    public SaveBicluster(BicatGui o, int go, int co) {
        owner = o; // ok = false;
        gene_offset = go;
        chip_offset = co;
    }

    // ===========================================================================
    public void makeWindow() {

        dialog = new JDialog(owner, "Do You want to save the current list of BCs? ");

        // create bottom subpanel with cancel and OK buttons
        JPanel closePanel = new JPanel(new FlowLayout());

        JButton okayButton = new JButton("Ok");
        // okayButton.setMnemonic(KeyEvent.VK_K);
        okayButton.setActionCommand("ok_save");
        okayButton.addActionListener(this);
        closePanel.add(okayButton);

        JButton cancelButton = new JButton("Cancel");
        // cancelButton.setMnemonic(KeyEvent.VK_C);
        cancelButton.setActionCommand("nok_save");
        cancelButton.addActionListener(this);
        closePanel.add(cancelButton);

        // create bicluster subpanel
        JPanel bcDimPanel = new JPanel(new BorderLayout());
        // bcDimPanel.setBorder( BorderFactory.createTitledBorder("... dimensions"));

        // 1st subpane in BC subpanel with labels containing field descriptions
        // JPanel labelPanel = new JPanel( new GridLayout(0,1) );
        // labelPanel.add( new JLabel("... will result in "+nrBCs+" BCs in the list of biclusters. Are You sure You want to continue? "));

        //labelPanel.add( new JLabel("Minimum gene count: "));
        //labelPanel.add( new JLabel("Minimum chip count: "));
        // bcDimPanel.add(labelPanel, BorderLayout.CENTER);

        // 2nd subpane in BC subpanel with number entry fields
    /*JPanel fieldPane = new JPanel( new GridLayout(0,1));

    minGeneField = new JFormattedTextField(NumberFormat.getIntegerInstance());
    minGeneField.setValue(new Integer(0));
    minGeneField.setColumns(6);
    fieldPane.add(minGeneField);

    minChipField = new JFormattedTextField(NumberFormat.getIntegerInstance());
    minChipField.setValue(new Integer(0));
    minChipField.setColumns(6);
    fieldPane.add(minChipField);

    bcDimPanel.add(fieldPane, BorderLayout.LINE_END);
*/

        // main panel of dialog window
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(bcDimPanel, BorderLayout.CENTER);
        // contentPane.add(gcPanel, BorderLayout.CENTER);
        contentPane.add(closePanel, BorderLayout.PAGE_END);
        contentPane.setOpaque(true);
        dialog.setContentPane(contentPane);

        // set size, location and make visible
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);

    }

    // ===========================================================================

    /**
     * For <code>ActionListener</code> interface, reacts to user selections and
     * button clicks in this search window.
     */
    public void actionPerformed(ActionEvent e) {

        // NE FERCERA!!!

        if ("ok_save".equals(e.getActionCommand())) {

            System.out.println("am here!");
            // System.out.println("Starting filter...");

            // gene_offset = ((Number)minGeneField.getValue()).intValue();
            // chip_offset = ((Number)minChipField.getValue()).intValue();

            //owner.post.getFilteredBiclusters(gene_offset,chip_offset);

            // owner.post.testFilterBiclusters(gene_offset,chip_offset);
            // ....
            // owner.post.filterBiclusters(gene_offset, chip_offset);
            // owner.buildTree();

            // close window
            // ok = true;

            // boolean proceed = fcw.getProceed();
            //if(proceed) {

            // ask if wants to save the BC list?
            //SaveBCsWindow sbw = new SaveBCsWindow(owner);
            //if(sbw.getOK())

            // BILO BEFORE: owner.writeBiclusters(owner.post.bcs_add);

            // and do the thing.
            // owner.post.filterBiclusters(gene_offset, chip_offset);
            // }
            // */

            dialog.setVisible(false);
            dialog.dispose();
        } else if ("nok_save".equals(e.getActionCommand())) {
            // close window without doing anything
            dialog.setVisible(false);
            dialog.dispose();
        } else {
            // unknown!
            System.out.println("unknown event: " + e.paramString());
        }
    }

    // ===========================================================================
    public boolean getOK() {
        return ok;
    }

}

