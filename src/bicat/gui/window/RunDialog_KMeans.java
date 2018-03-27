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

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;
import bicat.run_machine.ArgumentsKMeans;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * GUI element that reads in the values needed for the K-Means algorithm and sends them through
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
 * @uml.property  name="number_Cs_f"
 */
/**
 * @return
 * @uml.property  name="number_max_iter_f"
 */
/**
 * @return
 * @uml.property  name="number_replicates_f"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_NUMBER_CLUSTERS_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_NUMBER_MAX_ITERATIONS"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_NUMBER_REPLICATES"
 */
/**
 * @return
 * @uml.property  name="rUN_KMEANS"
 */
/**
 * @return
 * @uml.property  name="rUN_KMEANS_DIALOG_CANCEL"
 */
/**
 * @return
 * @uml.property  name="sELECT_DISTANCE_LIST"
 */
/**
 * @return
 * @uml.property  name="sELECT_START_LIST"
 */
/**
 * @return
 * @uml.property  name="sELECT_EMPTY_ACTION_LIST"
 */
/**
 * @return
 * @uml.property  name="kma"
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
 * @param number_Cs_f
 * @uml.property  name="number_Cs_f"
 */
/**
 * @param number_max_iter_f
 * @uml.property  name="number_max_iter_f"
 */
/**
 * @param number_replicates_f
 * @uml.property  name="number_replicates_f"
 */
/**
 * @param DEFAULT_NUMBER_CLUSTERS_VALUE
 * @uml.property  name="dEFAULT_NUMBER_CLUSTERS_VALUE"
 */
/**
 * @param DEFAULT_NUMBER_MAX_ITERATIONS
 * @uml.property  name="dEFAULT_NUMBER_MAX_ITERATIONS"
 */
/**
 * @param DEFAULT_NUMBER_REPLICATES
 * @uml.property  name="dEFAULT_NUMBER_REPLICATES"
 */
/**
 * @param RUN_KMEANS
 * @uml.property  name="rUN_KMEANS"
 */
/**
 * @param RUN_KMEANS_DIALOG_CANCEL
 * @uml.property  name="rUN_KMEANS_DIALOG_CANCEL"
 */
/**
 * @param SELECT_DISTANCE_LIST
 * @uml.property  name="sELECT_DISTANCE_LIST"
 */
/**
 * @param SELECT_START_LIST
 * @uml.property  name="sELECT_START_LIST"
 */
/**
 * @param SELECT_EMPTY_ACTION_LIST
 * @uml.property  name="sELECT_EMPTY_ACTION_LIST"
 */
/**
 * GUI element that reads in the values needed for the K-Means algorithm and sends them through <p> Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
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
 * @uml.property  name="number_Cs_f"
 */
/**
 * @return
 * @uml.property  name="number_max_iter_f"
 */
/**
 * @return
 * @uml.property  name="number_replicates_f"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_NUMBER_CLUSTERS_VALUE"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_NUMBER_MAX_ITERATIONS"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_NUMBER_REPLICATES"
 */
/**
 * @return
 * @uml.property  name="rUN_KMEANS"
 */
/**
 * @return
 * @uml.property  name="rUN_KMEANS_DIALOG_CANCEL"
 */
/**
 * @return
 * @uml.property  name="sELECT_DISTANCE_LIST"
 */
/**
 * @return
 * @uml.property  name="sELECT_START_LIST"
 */
/**
 * @return
 * @uml.property  name="sELECT_EMPTY_ACTION_LIST"
 */
/**
 * @return
 * @uml.property  name="kma"
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
 * @param number_Cs_f
 * @uml.property  name="number_Cs_f"
 */
/**
 * @param number_max_iter_f
 * @uml.property  name="number_max_iter_f"
 */
/**
 * @param number_replicates_f
 * @uml.property  name="number_replicates_f"
 */
/**
 * @param DEFAULT_NUMBER_CLUSTERS_VALUE
 * @uml.property  name="dEFAULT_NUMBER_CLUSTERS_VALUE"
 */
/**
 * @param DEFAULT_NUMBER_MAX_ITERATIONS
 * @uml.property  name="dEFAULT_NUMBER_MAX_ITERATIONS"
 */
/**
 * @param DEFAULT_NUMBER_REPLICATES
 * @uml.property  name="dEFAULT_NUMBER_REPLICATES"
 */
/**
 * @param RUN_KMEANS
 * @uml.property  name="rUN_KMEANS"
 */
/**
 * @param RUN_KMEANS_DIALOG_CANCEL
 * @uml.property  name="rUN_KMEANS_DIALOG_CANCEL"
 */
/**
 * @param SELECT_DISTANCE_LIST
 * @uml.property  name="sELECT_DISTANCE_LIST"
 */
/**
 * @param SELECT_START_LIST
 * @uml.property  name="sELECT_START_LIST"
 */
/**
 * @param SELECT_EMPTY_ACTION_LIST
 * @uml.property  name="sELECT_EMPTY_ACTION_LIST"
 */
/**
 * @param kma
 * @uml.property  name="kma"
 */
@Data
public class RunDialog_KMeans implements ActionListener {

    // ===========================================================================
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
	 * @uml.property  name="number_Cs_f"
	 * @uml.associationEnd  
	 */
    private JTextField number_Cs_f;
    /**
	 * @uml.property  name="number_max_iter_f"
	 * @uml.associationEnd  
	 */
    private JTextField number_max_iter_f;
    /**
	 * @uml.property  name="number_replicates_f"
	 * @uml.associationEnd  
	 */
    private JTextField number_replicates_f;
    /**
	 * @uml.property  name="dEFAULT_NUMBER_CLUSTERS_VALUE"
	 */
    private String DEFAULT_NUMBER_CLUSTERS_VALUE = "10";
    /**
	 * @uml.property  name="dEFAULT_NUMBER_MAX_ITERATIONS"
	 */
    private String DEFAULT_NUMBER_MAX_ITERATIONS = "100";
    /**
	 * @uml.property  name="dEFAULT_NUMBER_REPLICATES"
	 */
    private String DEFAULT_NUMBER_REPLICATES = "1"; // OBAVEZNO vise raditi,... ali
    // onda duze traje

    /**
	 * @uml.property  name="rUN_KMEANS"
	 */
    private String RUN_KMEANS = "run_hcl";
    /**
	 * @uml.property  name="rUN_KMEANS_DIALOG_CANCEL"
	 */
    private String RUN_KMEANS_DIALOG_CANCEL = "cancel";
    /**
	 * @uml.property  name="sELECT_DISTANCE_LIST"
	 */
    private String SELECT_DISTANCE_LIST = "select_distance";
    /**
	 * @uml.property  name="sELECT_START_LIST"
	 */
    private String SELECT_START_LIST = "select_start";
    /**
	 * @uml.property  name="sELECT_EMPTY_ACTION_LIST"
	 */
    private String SELECT_EMPTY_ACTION_LIST = "select_empty_action";
    /**
	 * @uml.property  name="wD"
	 */
    private int wD = -1; // what distance metric?
    /**
	 * @uml.property  name="wS"
	 */
    private int wS = -1; // what start mode?
    /**
	 * @uml.property  name="wE"
	 */
    private int wE = -1; // what empty action?
    /**
	 * @uml.property  name="nC"
	 */
    private int nC = 10;
    /**
	 * @uml.property  name="nMI"
	 */
    private int nMI = 100;
    /**
	 * @uml.property  name="nR"
	 */
    private int nR = 1;

    /**
	 * @uml.property  name="kma"
	 * @uml.associationEnd  
	 */
    private ArgumentsKMeans kma;

    // ===========================================================================
    public RunDialog_KMeans() {
    }

    public RunDialog_KMeans(UtilFunctionalities engine) {
        this.engine = engine;
        this.owner = null;
    }

    public RunDialog_KMeans(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    public RunDialog_KMeans(BicatGui o, ArgumentsKMeans args) {
        this.engine = o.getUtilEngine();
        owner = o;
        kma = args;
    }

    // ===========================================================================
    public void makeWindow() {

        dialog = new JDialog(owner, "Run K-means");

        // ........................................................................

        JPanel parameter_values = new JPanel(new GridLayout(0, 2));

        JLabel number_Cs_l = new JLabel("Number of clusters (K): ");
        number_Cs_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(number_Cs_l);

        number_Cs_f = new JTextField();
        number_Cs_f.setText(DEFAULT_NUMBER_CLUSTERS_VALUE);
        number_Cs_f.addActionListener(this);
        parameter_values.add(number_Cs_f);

        // JPanel p0 = new JPanel( new FlowLayout() );
        // p0.add(parameter_values);

        // .........................................................................

        JLabel number_max_iter_l = new JLabel("Number of iterations: ");
        number_max_iter_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(number_max_iter_l);

        number_max_iter_f = new JTextField();
        number_max_iter_f.setText(DEFAULT_NUMBER_MAX_ITERATIONS);
        number_max_iter_f.addActionListener(this);
        parameter_values.add(number_max_iter_f);

        // JPanel p1 = new JPanel( new FlowLayout() );
        // p1.add(parameter_values);

        // .........................................................................

        JLabel number_replicates_l = new JLabel("Number of replications: ");
        number_replicates_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        parameter_values.add(number_replicates_l);

        number_replicates_f = new JTextField();
        number_replicates_f.setText(DEFAULT_NUMBER_REPLICATES);
        number_replicates_f.addActionListener(this);
        parameter_values.add(number_replicates_f);

        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(parameter_values);

        // .........................................................................

        Vector distanceOp = new Vector();
        distanceOp.add("Choose Distance Metric ...");
        distanceOp.add(" Euclidean distance");
        distanceOp.add(" Pearson's correlation distance");
        distanceOp.add(" Manhattan (City-block) distance");
        distanceOp.add(" Cosine distance");

        JComboBox cb = new JComboBox(distanceOp);
        cb.setActionCommand(SELECT_DISTANCE_LIST);
        cb.setSelectedIndex(0);
        cb.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        cb.addActionListener(this);

        JPanel p3 = new JPanel(new GridLayout(0, 1)); // FlowLayout() );
        p3.add(cb);

        // .........................................................................

        Vector startOp = new Vector();
        startOp.add("Choose Start Mode (Initial Centroids) ...");
        startOp.add(" Random Genes");
        startOp.add(" Random Vectors");
        startOp.add(" Pre-Cluster");
        // startOp.add(" Specify Centroids"); (TO DO)

        JComboBox cb2 = new JComboBox(startOp);
        cb2.setActionCommand(SELECT_START_LIST);
        cb2.setSelectedIndex(0);
        cb2.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        cb2.addActionListener(this);

        // JPanel p4 = new JPanel( new FlowLayout() );
        p3.add(cb2);

        // .........................................................................

        Vector emptyOp = new Vector();
        emptyOp.add("Choose Empty Action ...");
        emptyOp.add(" Report Error");
        emptyOp.add(" Drop Cluster");
        emptyOp.add(" Singleton (force K)");

        JComboBox cb3 = new JComboBox(emptyOp);
        cb3.setActionCommand(SELECT_EMPTY_ACTION_LIST);
        cb3.setSelectedIndex(0);
        cb3.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        cb3.addActionListener(this);

        // JPanel p5 = new JPanel( new FlowLayout() );
        p3.add(cb3);

        // ........................................................................

        JPanel p6 = new JPanel(new FlowLayout());

        JButton okButton = new JButton("Run K-means");
        okButton.setActionCommand(RUN_KMEANS);
        okButton.addActionListener(this);
        p6.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(RUN_KMEANS_DIALOG_CANCEL);
        cancelButton.addActionListener(this);
        p6.add(cancelButton);

        // ........................................................................

        JPanel parameters = new JPanel(new BorderLayout()); // GridLayout(0,1)
        // );

        // parameters.add(p0);
        // parameters.add(p1);
        parameters.add(p2, BorderLayout.NORTH);
        parameters.add(p3, BorderLayout.CENTER);
        // parameters.add(p4);
        // parameters.add(p5);
        parameters.add(p6, BorderLayout.SOUTH);

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

        if (SELECT_DISTANCE_LIST.equals(e.getActionCommand())) {
            JComboBox box = (JComboBox) e.getSource();
            String item = (String) box.getSelectedItem();
            updateLabel_DistanceListSelection(item);
        } else if (SELECT_START_LIST.equals(e.getActionCommand())) {
            JComboBox box = (JComboBox) e.getSource();
            String item = (String) box.getSelectedItem();
            updateLabel_StartListSelection(item);
        } else if (SELECT_EMPTY_ACTION_LIST.equals(e.getActionCommand())) {
            JComboBox box = (JComboBox) e.getSource();
            String item = (String) box.getSelectedItem();
            updateLabel_EmptyActionListSelection(item);
        } else if (RUN_KMEANS.equals(e.getActionCommand())) {
            try {

                if (wD == -1 || wS == -1 || wE == -1) {
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "Please choose the distance metric, the start mode\nand what action should be performed if an\nempty cluster is found.");
                } else {

                    nC = (new Integer(number_Cs_f.getText()).intValue());
                    nMI = (new Integer(number_max_iter_f.getText()).intValue());
                    nR = (new Integer(number_replicates_f.getText()).intValue());

                    kma.setNr_clusters(nC);
                    kma.setNr_max_iterations(nMI);
                    kma.setNr_replicates(nR);

                    kma.setWhat_distance(wD);
                    kma.setStart_list(wS);
                    kma.setEmpty_action(wE);


                    JOptionPane
                            .showMessageDialog(null,
                                    "K-Means algorithm is running... \nThe calculations may take some time");
                    for (int i = 0; i < 500000000; i++) {
                    } //wait
                    engine.getRunMachineKMeans().runClustering(kma);

                    dialog.setVisible(false);
                    dialog.dispose();
                }
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else if (RUN_KMEANS_DIALOG_CANCEL.equals(e.getActionCommand())) {
            dialog.setVisible(false);
            dialog.dispose();
        }

    }

    // ===========================================================================
    void updateLabel_DistanceListSelection(String item) {

		/*
         * distanceOp.add("Choose Distance Metric ..."); distanceOp.add("
		 * Euclidean distance"); distanceOp.add(" Pearson's correlation
		 * distance"); distanceOp.add(" Manhattan (City-block) distance");
		 * distanceOp.add(" Cosine distance");
		 */

        if (item.equals(" Euclidean distance"))
            wD = MethodConstants.EUCLIDEAN_DISTANCE;
        else if (item.equals(" Pearson's correlation distance"))
            wD = MethodConstants.PEARSON_CORRELATION;
        else if (item.equals(" Manhattan (City-block) distance"))
            wD = MethodConstants.MANHATTAN_DISTANCE;
        else if (item.equals(" Cosine distance"))
            wD = MethodConstants.COSINE_DISTANCE_KMeans;

        else {
            System.out.println("Select distance metric! (Canceling)");
        }
    }

    // ===========================================================================
    void updateLabel_StartListSelection(String item) {

		/*
         * startOp.add("Choose Start Mode (Initial Centroids) ...");
		 * startOp.add(" Random Genes"); startOp.add(" Random Vectors");
		 * startOp.add(" Pre-Cluster");
		 */

        if (item.equals(" Random Genes"))
            wS = MethodConstants.START_MODE_RANDOM;
        else if (item.equals(" Random Vectors"))
            wS = MethodConstants.START_MODE_UNIFORM_RANDOM;
        else if (item.equals(" Pre-Cluster"))
            wS = MethodConstants.START_MODE_CLUSTER;
            // else if(item.equals(" Fix/Given Cluster")) wS = START_MODE_MATRIX ;

        else {
            System.out.println("Select start mode! (Canceling)");
        }
    }

    // ===========================================================================
    void updateLabel_EmptyActionListSelection(String item) {

		/*
         * emptyOp.add("Choose Empty Action ..."); emptyOp.add(" Report Error");
		 * emptyOp.add(" Drop Cluster"); emptyOp.add(" Singleton (force K)");
		 */

        if (item.equals(" Report Error"))
            wE = MethodConstants.EMPTY_ACTION_ERROR;
        else if (item.equals(" Drop Cluster"))
            wE = MethodConstants.EMPTY_ACTION_DROP;
        else if (item.equals(" Singleton (force K)"))
            wE = MethodConstants.EMPTY_ACTION_SINGLETON;

        else {
            System.out.println("Select empty action! (Canceling)");
        }
    }

}