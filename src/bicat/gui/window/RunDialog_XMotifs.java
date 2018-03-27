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
import bicat.run_machine.ArgumentsXMotifs;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * GUI element that reads in the values needed for the XMotifs algorithm and sends them through
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="owner"
 */
/**
 * @return
 * @uml.property  name="engine"
 */
/**
 * @return
 * @uml.property  name="dialog"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_PARAMETERS"
 */
/**
 * @return
 * @uml.property  name="rUN_XMOTIFS"
 */
/**
 * @return
 * @uml.property  name="rUN_XMOTIFS_DIALOG_CANCEL"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_SEED_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_MAX_P_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_ALPHA_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_N_S_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_N_D_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_S_D_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_MAX_LENGTH_VALUE"
 */
/**
 * @return
 * @uml.property  name="seed_f"
 */
/**
 * @return
 * @uml.property  name="max_p_value_f"
 */
/**
 * @return
 * @uml.property  name="alpha_f"
 */
/**
 * @return
 * @uml.property  name="n_s_f"
 */
/**
 * @return
 * @uml.property  name="n_d_f"
 */
/**
 * @return
 * @uml.property  name="s_d_f"
 */
/**
 * @return
 * @uml.property  name="max_length_f"
 */
/**
 * @return
 * @uml.property  name="nf"
 */
/**
 * @return
 * @uml.property  name="xma"
 */
/**
 * @param owner
 * @uml.property  name="owner"
 */
/**
 * @param engine
 * @uml.property  name="engine"
 */
/**
 * @param dialog
 * @uml.property  name="dialog"
 */
/**
 * @param DEFAULT_PARAMETERS
 * @uml.property  name="dEFAULT_PARAMETERS"
 */
/**
 * @param RUN_XMOTIFS
 * @uml.property  name="rUN_XMOTIFS"
 */
/**
 * @param RUN_XMOTIFS_DIALOG_CANCEL
 * @uml.property  name="rUN_XMOTIFS_DIALOG_CANCEL"
 */
/**
 * @param DEFAULT_SEED_VALUE
 * @uml.property  name="dEFAULT_SEED_VALUE"
 */
/**
 * @param DEFAULT_MAX_P_VALUE
 * @uml.property  name="dEFAULT_MAX_P_VALUE"
 */
/**
 * @param DEFAULT_ALPHA_VALUE
 * @uml.property  name="dEFAULT_ALPHA_VALUE"
 */
/**
 * @param DEFAULT_N_S_VALUE
 * @uml.property  name="dEFAULT_N_S_VALUE"
 */
/**
 * @param DEFAULT_N_D_VALUE
 * @uml.property  name="dEFAULT_N_D_VALUE"
 */
/**
 * @param DEFAULT_S_D_VALUE
 * @uml.property  name="dEFAULT_S_D_VALUE"
 */
/**
 * @param DEFAULT_MAX_LENGTH_VALUE
 * @uml.property  name="dEFAULT_MAX_LENGTH_VALUE"
 */
/**
 * @param seed_f
 * @uml.property  name="seed_f"
 */
/**
 * @param max_p_value_f
 * @uml.property  name="max_p_value_f"
 */
/**
 * @param alpha_f
 * @uml.property  name="alpha_f"
 */
/**
 * @param n_s_f
 * @uml.property  name="n_s_f"
 */
/**
 * @param n_d_f
 * @uml.property  name="n_d_f"
 */
/**
 * @param s_d_f
 * @uml.property  name="s_d_f"
 */
/**
 * @param max_length_f
 * @uml.property  name="max_length_f"
 */
/**
 * @param nf
 * @uml.property  name="nf"
 */
/**
 * GUI element that reads in the values needed for the XMotifs algorithm and sends them through <p> Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author  Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="owner"
 */
/**
 * @return
 * @uml.property  name="engine"
 */
/**
 * @return
 * @uml.property  name="dialog"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_PARAMETERS"
 */
/**
 * @return
 * @uml.property  name="rUN_XMOTIFS"
 */
/**
 * @return
 * @uml.property  name="rUN_XMOTIFS_DIALOG_CANCEL"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_SEED_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_MAX_P_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_ALPHA_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_N_S_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_N_D_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_S_D_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_MAX_LENGTH_VALUE"
 */
/**
 * @return
 * @uml.property  name="seed_f"
 */
/**
 * @return
 * @uml.property  name="max_p_value_f"
 */
/**
 * @return
 * @uml.property  name="alpha_f"
 */
/**
 * @return
 * @uml.property  name="n_s_f"
 */
/**
 * @return
 * @uml.property  name="n_d_f"
 */
/**
 * @return
 * @uml.property  name="s_d_f"
 */
/**
 * @return
 * @uml.property  name="max_length_f"
 */
/**
 * @return
 * @uml.property  name="nf"
 */
/**
 * @return
 * @uml.property  name="xma"
 */
/**
 * @param owner
 * @uml.property  name="owner"
 */
/**
 * @param engine
 * @uml.property  name="engine"
 */
/**
 * @param dialog
 * @uml.property  name="dialog"
 */
/**
 * @param DEFAULT_PARAMETERS
 * @uml.property  name="dEFAULT_PARAMETERS"
 */
/**
 * @param RUN_XMOTIFS
 * @uml.property  name="rUN_XMOTIFS"
 */
/**
 * @param RUN_XMOTIFS_DIALOG_CANCEL
 * @uml.property  name="rUN_XMOTIFS_DIALOG_CANCEL"
 */
/**
 * @param DEFAULT_SEED_VALUE
 * @uml.property  name="dEFAULT_SEED_VALUE"
 */
/**
 * @param DEFAULT_MAX_P_VALUE
 * @uml.property  name="dEFAULT_MAX_P_VALUE"
 */
/**
 * @param DEFAULT_ALPHA_VALUE
 * @uml.property  name="dEFAULT_ALPHA_VALUE"
 */
/**
 * @param DEFAULT_N_S_VALUE
 * @uml.property  name="dEFAULT_N_S_VALUE"
 */
/**
 * @param DEFAULT_N_D_VALUE
 * @uml.property  name="dEFAULT_N_D_VALUE"
 */
/**
 * @param DEFAULT_S_D_VALUE
 * @uml.property  name="dEFAULT_S_D_VALUE"
 */
/**
 * @param DEFAULT_MAX_LENGTH_VALUE
 * @uml.property  name="dEFAULT_MAX_LENGTH_VALUE"
 */
/**
 * @param seed_f
 * @uml.property  name="seed_f"
 */
/**
 * @param max_p_value_f
 * @uml.property  name="max_p_value_f"
 */
/**
 * @param alpha_f
 * @uml.property  name="alpha_f"
 */
/**
 * @param n_s_f
 * @uml.property  name="n_s_f"
 */
/**
 * @param n_d_f
 * @uml.property  name="n_d_f"
 */
/**
 * @param s_d_f
 * @uml.property  name="s_d_f"
 */
/**
 * @param max_length_f
 * @uml.property  name="max_length_f"
 */
/**
 * @param nf
 * @uml.property  name="nf"
 */
/**
 * @param xma
 * @uml.property  name="xma"
 */
@Data
public class RunDialog_XMotifs implements ActionListener {

    /**
	 * @uml.property  name="owner"
	 * @uml.associationEnd  
	 */
    private BicatGui owner;

    /**
	 * @uml.property  name="engine"
	 * @uml.associationEnd  
	 */
    private UtilFunctionalities engine;

    /**
	 * @uml.property  name="dialog"
	 * @uml.associationEnd  
	 */
    private JDialog dialog;

    /**
	 * @uml.property  name="dEFAULT_PARAMETERS"
	 */
    private String DEFAULT_PARAMETERS = "set_defaults";

    /**
	 * @uml.property  name="rUN_XMOTIFS"
	 */
    private String RUN_XMOTIFS = "run_xmotifs";

    /**
	 * @uml.property  name="rUN_XMOTIFS_DIALOG_CANCEL"
	 */
    private String RUN_XMOTIFS_DIALOG_CANCEL = "cancel";

    /**
	 * @uml.property  name="dEFAULT_SEED_VALUE"
	 */
    private String DEFAULT_SEED_VALUE = "13";

    /**
	 * @uml.property  name="dEFAULT_MAX_P_VALUE"
	 */
    private String DEFAULT_MAX_P_VALUE = "1e-9"; // "0.0000000001" // 10 ^ -1

    /**
	 * @uml.property  name="dEFAULT_ALPHA_VALUE"
	 */
    private String DEFAULT_ALPHA_VALUE = "0.05"; // 2.0

    /**
	 * @uml.property  name="dEFAULT_N_S_VALUE"
	 */
    private String DEFAULT_N_S_VALUE = "10";

    /**
	 * @uml.property  name="dEFAULT_N_D_VALUE"
	 */
    private String DEFAULT_N_D_VALUE = "100";

    /**
	 * @uml.property  name="dEFAULT_S_D_VALUE"
	 */
    private String DEFAULT_S_D_VALUE = "4";

    /**
	 * @uml.property  name="dEFAULT_MAX_LENGTH_VALUE"
	 */
    private int DEFAULT_MAX_LENGTH_VALUE; // = 50;... etwa 75 % nr chips

    /**
	 * @uml.property  name="seed_f"
	 * @uml.associationEnd  
	 */
    private JTextField seed_f;

    /**
	 * @uml.property  name="max_p_value_f"
	 * @uml.associationEnd  
	 */
    private JTextField max_p_value_f;

    /**
	 * @uml.property  name="alpha_f"
	 * @uml.associationEnd  
	 */
    private JTextField alpha_f;

    /**
	 * @uml.property  name="n_s_f"
	 * @uml.associationEnd  
	 */
    private JTextField n_s_f;

    /**
	 * @uml.property  name="n_d_f"
	 * @uml.associationEnd  
	 */
    private JTextField n_d_f;

    /**
	 * @uml.property  name="s_d_f"
	 * @uml.associationEnd  
	 */
    private JTextField s_d_f;

    /**
	 * @uml.property  name="max_length_f"
	 * @uml.associationEnd  
	 */
    private JFormattedTextField max_length_f;

    /**
	 * @uml.property  name="nf"
	 */
    private NumberFormat nf;

    /**
	 * @uml.property  name="xma"
	 * @uml.associationEnd  
	 */
    private ArgumentsXMotifs xma;

    // ===========================================================================
    public RunDialog_XMotifs() {
    }

    public RunDialog_XMotifs(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
        DEFAULT_MAX_LENGTH_VALUE = engine.getPre().getWorkingChipCount();
    }

    public RunDialog_XMotifs(BicatGui o, ArgumentsXMotifs args) {
        this.engine = o.getUtilEngine();
        owner = o;
        xma = args;
        DEFAULT_MAX_LENGTH_VALUE = engine.getPre().getWorkingChipCount();
    }

    // ===========================================================================
    public void makeWindow() {

        // user-defined parameters: seed, delta, alpha, n
        dialog = new JDialog(owner, "Run xMotifs");

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

        JLabel max_p_value_l = new JLabel("Set maximum P-value: ");
        max_p_value_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(max_p_value_l);

        // max_p_value_f = new JFormattedTextField(nf);
        // max_p_value_f.setValue(new Double(DEFAULT_MAX_P_VALUE));
        // max_p_value_f.addActionListener(this);
        // parameter_values.add(max_p_value_f);
        max_p_value_f = new JTextField();
        max_p_value_f.setText(DEFAULT_MAX_P_VALUE);
        max_p_value_f.addActionListener(this);
        parameter_values.add(max_p_value_f);

        JLabel alpha_l = new JLabel("Set alpha: ");
        alpha_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(alpha_l);

        alpha_f = new JTextField();
        alpha_f.setText(DEFAULT_ALPHA_VALUE);
        alpha_f.addActionListener(this);
        parameter_values.add(alpha_f);

        // ....

        JLabel n_s_l = new JLabel("Set the n_s parameter: ");
        n_s_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(n_s_l);

        n_s_f = new JTextField();
        n_s_f.setText(DEFAULT_N_S_VALUE);
        n_s_f.addActionListener(this);
        parameter_values.add(n_s_f);

        JLabel n_d_l = new JLabel("Set the n_d parameter: ");
        n_d_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(n_d_l);

        n_d_f = new JTextField();
        n_d_f.setText(DEFAULT_N_D_VALUE);
        n_d_f.addActionListener(this);
        parameter_values.add(n_d_f);

        JLabel s_d_l = new JLabel("Set the s_d parameter: ");
        s_d_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(s_d_l);

        s_d_f = new JTextField();
        s_d_f.setText(DEFAULT_S_D_VALUE);
        s_d_f.addActionListener(this);
        parameter_values.add(s_d_f);

        JLabel max_length_l = new JLabel("Set the max_length parameter: ");
        max_length_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(max_length_l);

        max_length_f = new JFormattedTextField(NumberFormat.getNumberInstance());
        DEFAULT_MAX_LENGTH_VALUE = Math.round((float) (0.75 * xma
                .getChipNumber()));
        max_length_f.setValue(new Integer(DEFAULT_MAX_LENGTH_VALUE));
        max_length_f.addActionListener(this);
        parameter_values.add(max_length_f);

        p0.add(parameter_values);

        // ........................................................................

        JPanel p1 = new JPanel(new GridBagLayout());

        JButton okButton = new JButton("Run xMotifs");
        okButton.setActionCommand(RUN_XMOTIFS);
        okButton.addActionListener(this);
        p1.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(RUN_XMOTIFS_DIALOG_CANCEL);
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

        if (RUN_XMOTIFS.equals(e.getActionCommand())) {

            int seed = (new Integer(seed_f.getText()).intValue());

            // Double alpha_Double = ((Double) alpha_f.getValue());
            // double alpha = alpha_Double.doubleValue();
            double alpha = Double.parseDouble(alpha_f.getText());

            // Double max_p_value_Double = ((Double) max_p_value_f.getValue());
            // double max_p_value = max_p_value_Double.doubleValue();
            double max_p_value = Double.parseDouble(max_p_value_f.getText());
            // System.out.println("max_p_value * 2: " + max_p_value * 2);

            int n_s = (new Integer(n_s_f.getText()).intValue());
            int n_d = (new Integer(n_d_f.getText()).intValue());
            int s_d = (new Integer(s_d_f.getText()).intValue());
            int max_length = (new Integer(max_length_f.getText()).intValue());

            xma.setSeed(seed);
            xma.setAlpha(alpha);
            xma.setN_d(n_d);
            xma.setN_s(n_s);
            xma.setS_d(s_d);
            xma.setMax_length(max_length);
            xma.setMax_p_value(max_p_value);

            xma.setMax_size(n_d * n_s);

            dialog.setVisible(false);
            dialog.dispose();
            int numberOfChips = xma.getChipNumber();
            if (numberOfChips > 64) {
                JOptionPane
                        .showMessageDialog(null,
                                "Sorry, xMotifs does not run for data sets with more than 64 chips.");
            } else {
                JOptionPane
                        .showMessageDialog(null,
                                "XMotifs algorithm is running... \nThe calculations may take some time");
                for (int i = 0; i < 300000000; i++) {
                } // wait a bit
                engine.getRunMachine_XMotifs().runBiclustering(xma);
            }
        } else if (RUN_XMOTIFS_DIALOG_CANCEL.equals(e.getActionCommand())) {
            dialog.setVisible(false);
            dialog.dispose();
        }

    }
}