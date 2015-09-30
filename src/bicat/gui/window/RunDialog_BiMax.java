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
import bicat.gui.*;
import bicat.run_machine.ArgumentsBiMax;
import lombok.Data;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import java.text.NumberFormat;

//import javax.swing.ProgressMonitor;
@Data
/**
 * GUI element that reads in the values needed for the BiMax algorithm and sends them through
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunDialog_BiMax implements ActionListener {

    private String RUN_BIMAX = "run_bimax";
    private String RUN_BIMAX_DIALOG_CANCEL = "cancel";

    private int DEFAULT_GENES_VALUE = 2;
    private int DEFAULT_CHIPS_VALUE = 2;

    private BicatGui owner;
    private UtilFunctionalities engine;
    private JDialog dialog;

    JFormattedTextField genes_f;
    JFormattedTextField chips_f;

    ArgumentsBiMax bmxa;

    // ===========================================================================
    public RunDialog_BiMax() {
        this.engine = null;
        this.owner = null;
    }

    public RunDialog_BiMax(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
    }

    public RunDialog_BiMax(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    public RunDialog_BiMax(BicatGui o, ArgumentsBiMax args) {
        this.engine = o.getUtilEngine();
        owner = o;
        bmxa = args;
    }

    // ===========================================================================
    public void makeWindow() {

        // user-defined parameters: seed, delta, alpha, n
        dialog = new JDialog(owner, "Run BiMax");

        JPanel parameters = new JPanel(new GridLayout(0, 1));

        // ........................................................................

        JPanel p0 = new JPanel(new FlowLayout());
        JPanel parameter_values = new JPanel(new GridLayout(0, 2));

        JLabel genes_l = new JLabel("Specify minimum number of genes: ");
        genes_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(genes_l);

        genes_f = new JFormattedTextField(NumberFormat.getNumberInstance());
        genes_f.setValue(new Integer(DEFAULT_GENES_VALUE));
        genes_f.addActionListener(this);
        parameter_values.add(genes_f);

        // ....
        JLabel chips_l = new JLabel("Specify minimum number of chips: ");
        chips_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(chips_l);

        chips_f = new JFormattedTextField(NumberFormat.getNumberInstance());
        chips_f.setValue(new Integer(DEFAULT_CHIPS_VALUE));
        chips_f.addActionListener(this);
        parameter_values.add(chips_f);
        p0.add(parameter_values);

        // ........................................................................

        JPanel p1 = new JPanel(new FlowLayout());

        JButton okButton = new JButton("Run BiMax");
        okButton.setActionCommand(RUN_BIMAX);
        okButton.addActionListener(this);
        p1.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(RUN_BIMAX_DIALOG_CANCEL);
        cancelButton.addActionListener(this);
        p1.add(cancelButton);

        // ........................................................................

        parameters.add(p0); //parameter_values;
        parameters.add(p1);

        dialog.setContentPane(parameters);

        // ........................................................................
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

        if (RUN_BIMAX.equals(e.getActionCommand())) {

            try {
                int genes = DEFAULT_GENES_VALUE;
                int chips = DEFAULT_CHIPS_VALUE;

                genes = (new Integer(genes_f.getText()).intValue());
                chips = (new Integer(chips_f.getText()).intValue());

                bmxa.setLower_genes(genes);
                bmxa.setLower_chips(chips);

                //ProgressMonitor pm = new ProgressMonitor(owner, "Biclustering data...", null, 0,0); // creates a progress monitor
                dialog.setVisible(false);
                dialog.dispose();
                if (owner != null)
                    JOptionPane.showMessageDialog(null, "Bimax algorithm is running... \nThe calculations may take some time");
                for (int i = 0; i < 300000000; i++) {
                } // wait a bit
                engine.getRunMachineBiMax().runBiclustering(bmxa);

            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else if (RUN_BIMAX_DIALOG_CANCEL.equals(e.getActionCommand())) {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }
}