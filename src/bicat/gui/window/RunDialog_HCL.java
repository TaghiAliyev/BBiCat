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
import bicat.run_machine.ArgumentsHCL;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Vector;

@Data
/**
 * GUI element that reads in the values needed for the HCL algorithm and sends them through
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunDialog_HCL implements ActionListener {

	private BicatGui owner;
	private UtilFunctionalities engine;

	private JDialog dialog;

	private JFormattedTextField number_Cs_f;

	private int DEFAULT_NUMBER_CLUSTERS_VALUE = 5;

	private String RUN_HCL = "run_hcl";

	private String RUN_HCL_DIALOG_CANCEL = "cancel";

	private String SELECT_LINKAGE_LIST = "select_linkage";

	private String SELECT_DISTANCE_LIST = "select_distance";

	private int wL = -1; // what linkage mode?

	private int wD = -1; // what distance metric?

	private int nC = DEFAULT_NUMBER_CLUSTERS_VALUE;

	private ArgumentsHCL hcla;

	// ===========================================================================
	public RunDialog_HCL() {
	}

	public RunDialog_HCL(UtilFunctionalities engine)
	{
		this.engine = engine;
		this.owner = null;
	}

	public RunDialog_HCL(BicatGui o) {
		this.engine = o.getUtilEngine();
		owner = o;
	}

	public RunDialog_HCL(BicatGui o, ArgumentsHCL args) {
		this.engine = o.getUtilEngine();
		owner = o;
		hcla = args;
	}

	// ===========================================================================
	public void makeWindow() {

		dialog = new JDialog(owner, "Run HCL");

		// ........................................................................
		JPanel p0 = new JPanel(new FlowLayout());
		JPanel parameter_values = new JPanel(new GridLayout(0, 2));

		JLabel number_Cs_l = new JLabel("Number of clusters: ");
		number_Cs_l.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		parameter_values.add(number_Cs_l);

		number_Cs_f = new JFormattedTextField(NumberFormat.getNumberInstance());
		number_Cs_f.setValue(new Integer(DEFAULT_NUMBER_CLUSTERS_VALUE));
		number_Cs_f.addActionListener(this);
		parameter_values.add(number_Cs_f);

		p0.add(parameter_values);

		// ....
		JPanel p1 = new JPanel(new GridLayout(0, 1)); // FlowLayout());
		Vector linkageOp = new Vector();
		linkageOp.add("Choose Linkage Mode ...");
		linkageOp.add(" Single linkage");
		linkageOp.add(" Complete linkage");
		linkageOp.add(" Average linkage");

		JComboBox cb = new JComboBox(linkageOp);
		cb.setActionCommand(SELECT_LINKAGE_LIST);
		cb.setSelectedIndex(0);
		cb.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		cb.addActionListener(this);

		p1.add(cb);

		// ....
		JPanel p2 = new JPanel(new GridLayout(0, 1)); // FlowLayout() );
		Vector distanceOp = new Vector();
		distanceOp.add("Choose Distance Metric ...");
		distanceOp.add(" Euclidean distance");
		distanceOp.add(" Pearson's correlation distance");
		distanceOp.add(" Manhattan (City-block) distance");
		distanceOp.add(" Minkowski distance (pow 2)");
		distanceOp.add(" Cosine distance");

		JComboBox cb2 = new JComboBox(distanceOp);
		cb2.setActionCommand(SELECT_DISTANCE_LIST);
		cb2.setSelectedIndex(0);
		cb2.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		cb2.addActionListener(this);

		p2.add(cb2);

		// ........................................................................

		JPanel p3 = new JPanel(new FlowLayout()); // GridBagLayout() );

		JButton okButton = new JButton("Run HCL");
		okButton.setActionCommand(RUN_HCL);
		okButton.addActionListener(this);
		p3.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(RUN_HCL_DIALOG_CANCEL);
		cancelButton.addActionListener(this);
		p3.add(cancelButton);

		// parameters.add(p1);

		// ........................................................................

		JPanel parameters = new JPanel(new GridLayout(0, 1));
		parameters.add(p0); // arameter_values);
		parameters.add(p1);
		parameters.add(p2);
		parameters.add(p3);

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
	 * 
	 */
	public void actionPerformed(ActionEvent e) {

		if (SELECT_LINKAGE_LIST.equals(e.getActionCommand())) {
			JComboBox box = (JComboBox) e.getSource();
			String item = (String) box.getSelectedItem();
			updateLabel_LinkageListSelection(item);
		}

		else if (SELECT_DISTANCE_LIST.equals(e.getActionCommand())) {
			JComboBox box = (JComboBox) e.getSource();
			String item = (String) box.getSelectedItem();
			updateLabel_DistanceListSelection(item);
		}

		else if (RUN_HCL.equals(e.getActionCommand())) {

			try {

				if (wL == -1 || wD == -1) {
					JOptionPane
							.showMessageDialog(null,
									"Please make a choice for Linkage mode\nand distance metric.");
				} else if (new Integer(number_Cs_f.getText()).intValue() >= hcla
						.getMyData().length) {
					JOptionPane
							.showMessageDialog(null,
									"The number of clusters must be smaller than the number of genes.");
				} else {

					nC = (new Integer(number_Cs_f.getText()).intValue());

					hcla.setWhat_distance(wD);
					hcla.setWhat_linkage(wL);
					hcla.setNr_clusters(nC);

					for (int i = 0; i < 500000000; i++) {
					} // wait
					engine.getRunMachineHCL().runClustering(hcla);

					dialog.setVisible(false);
					dialog.dispose();
					JOptionPane
							.showMessageDialog(null,
									"HCL algorithm is running... \nThe calculations may take some time");

				}
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}

		else if (RUN_HCL_DIALOG_CANCEL.equals(e.getActionCommand())) {
			dialog.setVisible(false);
			dialog.dispose();
		}

	}

	// ===========================================================================
	void updateLabel_LinkageListSelection(String item) {

		/*
		 * linkageOp.add("Choose Linkage Mode ..."); linkageOp.add(" Single
		 * linkage"); linkageOp.add(" Complete linkage"); linkageOp.add("
		 * Average linkage");
		 */

		if (item.equals(" Single linkage"))
			wL = MethodConstants.SINGLE_LINKAGE;
		else if (item.equals(" Complete linkage"))
			wL = MethodConstants.COMPLETE_LINKAGE;
		else if (item.equals(" Average linkage"))
			wL = MethodConstants.AVERAGE_LINKAGE;

		// System.out.println("Select linkage method! (Canceling) / ... " +
		// item); }
	}

	// ===========================================================================
	void updateLabel_DistanceListSelection(String item) {

		/*
		 * distanceOp.add("Choose Distance Metric ..."); distanceOp.add("
		 * Euclidean distance"); distanceOp.add(" Pearson's correlation
		 * distance"); distanceOp.add(" Manhattan (City-block) distance");
		 * distanceOp.add(" Minkowski distance (pow 2)"); distanceOp.add("
		 * Cosine distance");
		 */

		if (item.equals(" Euclidean distance"))
			wD = MethodConstants.EUCLIDEAN_DISTANCE;
		else if (item.equals(" Pearson's correlation distance"))
			wD = MethodConstants.PEARSON_CORRELATION;
		else if (item.equals(" Manhattan (City-block) distance"))
			wD = MethodConstants.MANHATTAN_DISTANCE;
		else if (item.equals(" Minkowski distance (pow 2)"))
			wD = MethodConstants.MINKOWSKI_DISTANCE;
		else if (item.equals(" Cosine distance"))
			wD = MethodConstants.COSINE_DISTANCE;

		else {
			System.out.println("Select distance metric! (Canceling) / ... "
					+ item);
		}
		if (MethodConstants.debug)
			System.out.println("The selected distance metric is: " + wD);
	}

}