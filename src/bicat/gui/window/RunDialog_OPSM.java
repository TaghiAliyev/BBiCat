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
import bicat.gui.BicatGui;
import bicat.run_machine.ArgumentsOPSM;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * GUI element that reads in the values needed for the OPSM algorithm and sends them through
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class RunDialog_OPSM implements ActionListener {

    private BicatGui owner;
    private UtilFunctionalities engine;

    private JDialog dialog;

    private String DEFAULT_PARAMETERS = "set_defaults";

    private String RUN_OPSM = "run_opsm";

    private String RUN_OPSM_DIALOG_CANCEL = "cancel";

    private int DEFAULT_L_VALUE = 10;

    private JFormattedTextField l_textfield;

    private ArgumentsOPSM opsma;

    // ===========================================================================
    public RunDialog_OPSM() {
    }

    public RunDialog_OPSM(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
    }

    public RunDialog_OPSM(BicatGui o, ArgumentsOPSM args) {
        this.engine = o.getUtilEngine();
        owner = o;
        opsma = args;
    }

    // ===========================================================================
    public void makeWindow() {

        dialog = new JDialog(owner, "Run OPSM");

        JPanel parameters = new JPanel(new GridLayout(0, 1));

        // ....

        JPanel p0 = new JPanel(new FlowLayout());
        JPanel parameter_values = new JPanel(new GridLayout(0, 2));

        JLabel l_label = new JLabel(
                "Set number of passed models for each iteration (l) ");
        l_label.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(l_label);

        l_textfield = new JFormattedTextField(NumberFormat.getNumberInstance());
        l_textfield.setValue(new Integer(DEFAULT_L_VALUE));
        l_textfield.addActionListener(this);
        parameter_values.add(l_textfield);
        p0.add(parameter_values);

        // ........................................................................

        JPanel p1 = new JPanel(new FlowLayout());

        JButton okButton = new JButton("Run OPSM");
        okButton.setActionCommand(RUN_OPSM);
        okButton.addActionListener(this);
        p1.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(RUN_OPSM_DIALOG_CANCEL);
        cancelButton.addActionListener(this);
        p1.add(cancelButton);

        // ........................................................................

        parameters.add(p0);
        parameters.add(p1);

        dialog.setContentPane(parameters);

        // ........................................................................
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);

    }

    // ===========================================================================

    /**
     * For <code>ActionListener</code> interface, reacts to user selections
     * and button clicks in this search window.
     */
    public void actionPerformed(ActionEvent e) {

        if (RUN_OPSM.equals(e.getActionCommand())) {

            try {
                int parameter = (new Integer(l_textfield.getText()).intValue());

                opsma.setNr_l(parameter); // number of passed models

                dialog.setVisible(false);
                dialog.dispose();
                JOptionPane
                        .showMessageDialog(null,
                                "OPSM algorithm is running... \nThe calculations may take some time");
                for (int i = 0; i < 50000000; i++) {
                } // wait a bit
                engine.getRunMachineOPSM().runBiclustering(opsma);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else if (RUN_OPSM_DIALOG_CANCEL.equals(e.getActionCommand())) {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }
}
