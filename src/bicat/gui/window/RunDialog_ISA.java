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

import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;
import bicat.run_machine.ArgumentsISA;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
/**
 * GUI element that reads in the values needed for the ISA algorithm and sends them through
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunDialog_ISA implements ActionListener {

    private BicatGui owner;

    private UtilFunctionalities engine;

    private JDialog dialog;

    private String DEFAULT_PARAMETERS = "set_defaults";

    private String RUN_ISA = "run_isa";

    private String RUN_ISA_DIALOG_CANCEL = "cancel";

    private String DEFAULT_SEED_VALUE = "13";

    private String DEFAULT_NUMBER_BICLUSTERS_VALUE = "100";

    private String DEFAULT_T_G_VALUE = "2.0";

    private String DEFAULT_T_C_VALUE = "2.0";

    private JTextField seed_f;

    private JTextField t_g_f;

    private JTextField t_c_f;

    private JTextField number_BCs_f;

    private ArgumentsISA isaa;

    // ===========================================================================
    public RunDialog_ISA() {
    }

    public RunDialog_ISA(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
    }

    public RunDialog_ISA(BicatGui o, ArgumentsISA args) {
        this.engine = o.getUtilEngine();
        owner = o;
        isaa = args;
    }

    // ===========================================================================
    public void makeWindow() {

        // user-defined parameters: seed, delta, alpha, n
        dialog = new JDialog(owner, "Run ISA");

        JPanel parameters = new JPanel(new GridLayout(0, 1));

        // ....
        JPanel p0 = new JPanel(new FlowLayout());

        JPanel parameter_values = new JPanel(new GridLayout(0, 2));

        JLabel seed_l = new JLabel("Set seed for random number generator: ");
        seed_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(seed_l);

        seed_f = new JTextField();
        seed_f.setText(DEFAULT_SEED_VALUE);
        seed_f.addActionListener(this);
        parameter_values.add(seed_f);

        // ....

        JLabel t_g_l = new JLabel("Set t_g (threshold genes): ");
        t_g_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(t_g_l);

        t_g_f = new JTextField();
        t_g_f.setText(DEFAULT_T_G_VALUE);
        t_g_f.addActionListener(this);
        parameter_values.add(t_g_f);

        JLabel t_c_l = new JLabel("Set t_c (threshold chips): ");
        t_c_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(t_c_l);

        t_c_f = new JTextField();
        t_c_f.setText(DEFAULT_T_C_VALUE);
        t_c_f.addActionListener(this);
        parameter_values.add(t_c_f);

        // ....

        JLabel number_BCs_l = new JLabel(
                "Set the number of starting points: ");
        number_BCs_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(number_BCs_l);

        number_BCs_f = new JTextField();
        number_BCs_f.setText(DEFAULT_NUMBER_BICLUSTERS_VALUE);
        number_BCs_f.addActionListener(this);
        parameter_values.add(number_BCs_f);

        p0.add(parameter_values);

        // ........................................................................

        JPanel p1 = new JPanel(new GridBagLayout());

        JButton okButton = new JButton("Run ISA");
        okButton.setActionCommand(RUN_ISA);
        okButton.addActionListener(this);
        p1.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(RUN_ISA_DIALOG_CANCEL);
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

        if (RUN_ISA.equals(e.getActionCommand())) {
            try {
                int seed = (new Integer(seed_f.getText()).intValue());
                double t_g = (new Double(t_g_f.getText()).doubleValue());
                double t_c = (new Double(t_c_f.getText()).doubleValue());
                int number_BCs = (new Integer(number_BCs_f.getText())
                        .intValue());

                isaa.setSeed(seed);
                isaa.setT_g(t_g);
                isaa.setT_c(t_c);
                isaa.setN_fix(number_BCs); // number of starting points (max number of biclusters)
                isaa.setMax_size(number_BCs);

                dialog.setVisible(false);
                dialog.dispose();

                JOptionPane
                        .showMessageDialog(null,
                                "ISA algorithm is running... \nThe calculations may take some time");

                for (int i = 0; i < 500000000; i++) {
                }
                engine.getRunMachine_ISA().runBiclustering(isaa);


            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else if (RUN_ISA_DIALOG_CANCEL.equals(e.getActionCommand())) {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }

}