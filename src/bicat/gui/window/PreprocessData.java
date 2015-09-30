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
import bicat.gui.*;
import bicat.preprocessor.*;
import lombok.Data;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;

import java.text.NumberFormat;

@Data
public class PreprocessData implements ActionListener {

	private BicatGui owner;

	private UtilFunctionalities engine;

	private JDialog dialog;

	// options to pass to my owner
	private  PreprocessOption preprocessOption;

	// i need these labels, since i will accordingly enable and disable them!
	private JFormattedTextField log_base;

	private JLabel log_label;

	private JLabel n_label;

	private JComboBox n_combo;

	private JFormattedTextField d_field_thr;

	private JFormattedTextField d_field_perc;

	private JComboBox d_combo_discr_scheme;

	private JComboBox d_combo_perc;

	private JLabel d_label_0_perc;

	private JLabel d_label_1_thr;

	private JLabel d_label_2_discr_scheme;

	private boolean norm_gs;

	private boolean norm_cs;

	private JCheckBox cb_thr;

	private JCheckBox cb_perc;

	// ===========================================================================
	public PreprocessData(UtilFunctionalities engine) {
		this.engine = engine;
		this.owner = null;
	}

	public PreprocessData(BicatGui o) {
		owner = o;
		preprocessOption = new PreprocessOption("default");
		this.engine = owner.getUtilEngine();
	}

	// ===========================================================================
	protected void updateLabel_normalization(String item) {
		/*
		 * normalize_items.add("Mean centring (0,1)");
		 * normalize_items.add("Huber norm"); normalize_items.add("Philip Z.
		 * norm"); normalize_items.add("Binarization 1 - Mixture model");
		 * normalize_items.add("Binarization 2 - Norm, InfoTheory");
		 */

		if (item.equals("Mean centring (0,1)"))
			preprocessOption
					.setNormalizationScheme(MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MEAN_CENTRING);

		// else if(item.equals("Huber norm"))
		// preprocessOption.setNormalizationScheme(MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_HUBER);

		// else if(item.equals("Philip Z. norm"))
		// preprocessOption.setNormalizationScheme(MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_PHILIP);

		else if (item.equals("Binarization 1 - Mixture Model"))
			preprocessOption
					.setNormalizationScheme(MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_MIXTURE);

		else if (item.equals("Binarization 2 - Norm, InfoTheory"))
			preprocessOption
					.setNormalizationScheme(MethodConstants.PREPROCESS_OPTIONS_NORMALIZATION_IT);
	}

	// ===========================================================================
	protected void updateLabel_discretization(String item) {

		/*
		 * discretize_items.add("Changed patterns"); //Expressed");
		 * discretize_items.add("Down-regulated patterns"); //Under-expressed
		 * only"); discretize_items.add("Up-regulated patterns");
		 * //Over-expressed only"); discretize_items.add("Complementary
		 * patterns"); //Co-expressed");
		 */
		if (item.equals("Changed patterns")) {
			if (MethodConstants.debug)
				System.out.println("4, changed");
			preprocessOption
					.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_CHANGE);
		} else if (item.equals("Up-regulated patterns")) {
			if (MethodConstants.debug)
				System.out.println("1, up");
			preprocessOption
					.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP);
		} else if (item.equals("Down-regulated patterns")) {
			if (MethodConstants.debug)
				System.out.println("2, down");
			preprocessOption
					.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_DOWN);
		} else if (item.equals("Complementary patterns")) {
			if (MethodConstants.debug)
				System.out.println("3, coex");
			preprocessOption
					.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED);
		}

	}

	// ===========================================================================
	/**
	 * For <code>ActionListener</code> interface, reacts to user selections
	 * and button clicks in this search window.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {

		// if (PREPROCESS_DATA_WINDOW_USE_DEFAULT.equals(e.getActionCommand()))
		// {
		//
		// // preprocessOption = new PreprocessOptions("default");
		// log_label.setEnabled(true);
		// log_base.setEnabled(true);
		//
		// n_label.setEnabled(true);
		// n_combo.setEnabled(true);
		//
		// d_label_1.setEnabled(true);
		// d_field.setEnabled(true);
		// d_label_0.setEnabled(true); // Percentage
		// d_fieldP.setEnabled(true);
		// d_label_2.setEnabled(true);
		// d_combo.setEnabled(true);
		// }

		if (MethodConstants.PREPROCESS_DATA_WINDOW_APPLY.equals(e.getActionCommand())) {

			if (!d_label_1_thr.isEnabled()
					&& !d_label_0_perc.isEnabled()) {
				preprocessOption.resetDiscretization();
			}

			if (d_label_1_thr.isEnabled()) {
				String discretizationThresholdString = d_field_thr.getText();
				discretizationThresholdString = discretizationThresholdString
						.replaceAll("'", "");
				double[] discretizationThreshold = new double[]{Double
						.parseDouble(discretizationThresholdString)};
				preprocessOption
						.setDiscretizationThreshold(discretizationThreshold);
				preprocessOption.setDiscretizationMode("threshold");
			}

			if (d_label_0_perc.isEnabled()) {
				String onesPercentageString = d_field_perc.getText();
				onesPercentageString = onesPercentageString.replaceAll("'", "");
				int onesPercentage = (int) Double
						.parseDouble(onesPercentageString);
				preprocessOption.setOnesPercentage(onesPercentage);
				preprocessOption.setDiscretizationMode("onesPercentage");
			}

			System.out.println("APPLY preprocessing");

			preprocessOption.setLogarithmBase(new Integer(log_base.getText())
					.intValue());

			engine.getPre().setPreprocessRun(preprocessOption);

			dialog.setVisible(false);
			dialog.dispose();
		}
		// }

		else if (MethodConstants.PREPROCESS_DATA_WINDOW_CANCEL.equals(e.getActionCommand())) {
			dialog.setVisible(false);
			dialog.dispose();
		}

		else if (MethodConstants.PREPROCESS_DATA_WINDOW_RATIO.equals(e.getActionCommand())) {
			if (preprocessOption.isDo_compute_ratio())
				preprocessOption.resetComputeRatio();
			else
				preprocessOption.setComputeRatio();
		}

		else if (MethodConstants.PREPROCESS_DATA_WINDOW_LOGARITHM.equals(e.getActionCommand())) {
			if (preprocessOption.isDo_compute_logarithm()) {
				preprocessOption.resetComputeLogarithm();

				// log_label.setEnabled(false);
				log_base.setEnabled(false);
			} else {
				// @todo check if the data is already logarithmized

				preprocessOption.setComputeLogarithm();

			}

			// log_label.setEnabled(true);
			log_base.setEnabled(true);

		}

		else if (MethodConstants.PREPROCESS_DATA_WINDOW_SET_LOGARITHM_BASE.equals(e
				.getActionCommand())) {
			// preprocessOption.setLogarithmBase(new
			// Integer(log_base.getText()).intValue());
			preprocessOption.setLogarithmBase(((Integer) log_base.getValue())
					.intValue());
		}

		else if (MethodConstants.PREPROCESS_DATA_WINDOW_NORMALIZE_GENES.equals(e
				.getActionCommand())) {

			if (preprocessOption.isDo_normalize()) {

				if (norm_cs) {
					norm_gs = !norm_gs;
					if (norm_gs)
						preprocessOption.setGeneNormalization();
					else
						preprocessOption.resetGeneNormalization();
				} else {
					n_label.setEnabled(false);
					n_combo.setEnabled(false);

					norm_gs = false;
					preprocessOption.resetNormalization();
					preprocessOption.resetGeneNormalization();
				}
			} else {
				n_label.setEnabled(true);
				n_combo.setEnabled(true);

				norm_gs = true;
				preprocessOption.setNormalization();
				preprocessOption.setGeneNormalization();
			}
		}

		else if (MethodConstants.PREPROCESS_DATA_WINDOW_NORMALIZE_CHIPS.equals(e
				.getActionCommand())) {

			if (preprocessOption.isDo_normalize()) {
				if (norm_gs) {
					norm_cs = !norm_cs;
					if (norm_cs)
						preprocessOption.setChipNormalization();
					else
						preprocessOption.resetChipNormalization();
				} else {
					n_label.setEnabled(false);
					n_combo.setEnabled(false);

					norm_cs = false;
					preprocessOption.resetNormalization();
					preprocessOption.resetChipNormalization();
				}
			} else {
				n_label.setEnabled(true);
				n_combo.setEnabled(true);

				norm_cs = true;
				preprocessOption.setNormalization();
				preprocessOption.setChipNormalization();
			}
		}

		else if (MethodConstants.PREPROCESS_DATA_WINDOW_CHOOSE_NORMALIZATION_SCHEME.equals(e
				.getActionCommand())) {
			JComboBox box = (JComboBox) e.getSource();
			String item = (String) box.getSelectedItem();
			updateLabel_normalization(item);
		}

		else if (MethodConstants.PREPROCESS_DATA_WINDOW_DISCRETIZE.equals(e.getActionCommand())) {

			if (cb_perc.isSelected()) {
				cb_perc.setSelected(false);
				d_label_0_perc.setEnabled(false);
				d_field_perc.setEnabled(false);
				d_label_1_thr.setEnabled(true); // Percentage
				d_field_thr.setEnabled(true);
			} else {
				if (preprocessOption.isDo_discretize()) {
					d_label_1_thr.setEnabled(false);
					d_field_thr.setEnabled(false);
					d_label_0_perc.setEnabled(false);
					d_field_perc.setEnabled(false);
					d_label_2_discr_scheme.setEnabled(false);
					d_combo_discr_scheme.setEnabled(false);
					preprocessOption.resetDiscretization();
				} else {
					preprocessOption.setDiscretizationTrue();
					d_label_1_thr.setEnabled(true);
					d_field_thr.setEnabled(true);
					d_label_2_discr_scheme.setEnabled(true);
					d_combo_discr_scheme.setEnabled(true);
				}

			}
		} else if (MethodConstants.PREPROCESS_DATA_WINDOW_ONES_PERCENTAGE.equals(e
				.getActionCommand())) {

			if (cb_thr.isSelected()) {
				cb_thr.setSelected(false);
				d_label_1_thr.setEnabled(false);
				d_field_thr.setEnabled(false);
				d_label_0_perc.setEnabled(true); // Percentage
				d_field_perc.setEnabled(true);
			} else {
				if (preprocessOption.isDo_discretize()) {
					d_label_1_thr.setEnabled(false);
					d_field_thr.setEnabled(false);
					d_label_0_perc.setEnabled(false);
					d_field_perc.setEnabled(false);
					d_label_2_discr_scheme.setEnabled(false);
					d_combo_discr_scheme.setEnabled(false);
					preprocessOption.resetDiscretization();
				} else {
					preprocessOption.setDiscretizationTrue();
					d_label_0_perc.setEnabled(true);
					d_field_perc.setEnabled(true);
					d_label_2_discr_scheme.setEnabled(true);
					d_combo_discr_scheme.setEnabled(true);
				}
			}
		}	
		
		else if (MethodConstants.PREPROCESS_DATA_WINDOW_CHOOSE_DISCRETIZATION_SCHEME.equals(e
				.getActionCommand())) {
			JComboBox box = (JComboBox) e.getSource();
			String item = (String) box.getSelectedItem();
			updateLabel_discretization(item);
		}

	}

	// ===========================================================================
	public void makeWindow() {

		preprocessOption = new PreprocessOption();

		dialog = new JDialog(owner, "Preprocess Setup Dialog");
		
		// *-*-*-* //

		JPanel normalize = new JPanel(new GridLayout(0, 1));
		normalize.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.black), "Normalization"));

		JPanel log_choice = new JPanel(new GridLayout(1, 2));

		JCheckBox cb = new JCheckBox("Compute log data with base ");
		cb.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
		cb.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_LOGARITHM);
		cb.addActionListener(this);
		log_choice.add(cb);

		log_base = new JFormattedTextField(NumberFormat.getIntegerInstance());
		log_base.setValue(new Integer(2));
		log_base.setAlignmentX(JFormattedTextField.CENTER_ALIGNMENT);
		log_base.setEnabled(false);
		log_base.addActionListener(this);
		log_base.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_SET_LOGARITHM_BASE);
		log_choice.add(log_base);

		normalize.add(log_choice);

		// *-*-*-* //

		cb = new JCheckBox("Normalize genes");
		cb.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_NORMALIZE_GENES);
		cb.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
		cb.addActionListener(this);
		normalize.add(cb);

		cb = new JCheckBox("Normalize chips");
		cb.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_NORMALIZE_CHIPS);
		cb.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
		cb.addActionListener(this);
		normalize.add(cb);

		norm_gs = false;
		norm_cs = false;

		JPanel norm_choice = new JPanel(new GridLayout(0, 2));

		n_label = new JLabel("      Set normalization scheme: ");
		n_label.setEnabled(false);
		n_label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		norm_choice.add(n_label);

		Vector normalize_items = new Vector();
		normalize_items.add("Mean centring (0,1)");
		/**
		 * EVENTUELL: normalize_items.add("Binarization 1 - Mixture model");
		 * normalize_items.add("Binarization 2 - Norm, InfoTheory");
		 */

		n_combo = new JComboBox(normalize_items);
		n_combo.setSelectedIndex(0);
		n_combo
				.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_CHOOSE_NORMALIZATION_SCHEME);
		n_combo.addActionListener(this);
		n_combo.setEnabled(false);
		norm_choice.add(n_combo);

		normalize.add(norm_choice);

		// *-*-*-* //

		JPanel discretize = new JPanel(new GridLayout(0, 1));
		discretize.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.black), "Discretization"));

		cb_thr = new JCheckBox("Discretize (to binary values) by threshold");
		cb_thr.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_DISCRETIZE);
		cb_thr.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
		cb_thr.addActionListener(this);
		discretize.add(cb_thr);

		cb_perc = new JCheckBox("Discretize (to binary values) by percentage");
		cb_perc.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_ONES_PERCENTAGE);
		cb_perc.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
		cb_perc.addActionListener(this);
		discretize.add(cb_perc);

		JPanel d_choice_1 = new JPanel(new GridLayout(2, 2, 8, 2));
		d_label_0_perc = new JLabel("      Set percentage of ones: ");
		d_label_0_perc.setEnabled(false);
		d_label_0_perc.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		d_choice_1.add(d_label_0_perc);

		d_field_perc = new JFormattedTextField(NumberFormat.getNumberInstance());
		d_field_perc.setValue(new Double(10.0));
		d_field_perc.addActionListener(this);
		d_field_perc.setEnabled(false);
		d_choice_1.add(d_field_perc);

		d_label_1_thr = new JLabel("      Set discretization threshold: ");
		d_label_1_thr.setEnabled(false);
		d_label_1_thr.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		d_choice_1.add(d_label_1_thr);

		d_field_thr = new JFormattedTextField(NumberFormat.getNumberInstance());
		d_field_thr.setValue(new Double(2.0));
		d_field_thr.addActionListener(this);
		d_field_thr.setEnabled(false);
		d_choice_1.add(d_field_thr);

		discretize.add(d_choice_1);

		JPanel d_choice_2 = new JPanel(new GridLayout(1, 2, 6, 2));

		d_label_2_discr_scheme = new JLabel(
				"      Select discretization scheme: ");
		d_label_2_discr_scheme.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		d_label_2_discr_scheme.setEnabled(false);
		d_choice_2.add(d_label_2_discr_scheme); // , BorderLayout.NORTH);

		Vector discretize_items = new Vector();
		discretize_items.add("Changed patterns"); // Expressed");
		discretize_items.add("Down-regulated patterns"); // Under-expressed
		// only");
		discretize_items.add("Up-regulated patterns"); // Over-expressed
		// discretize_items.add("Complementary patterns"); // Co-expressed");

		// only");

		d_combo_discr_scheme = new JComboBox(discretize_items);
		d_combo_discr_scheme
				.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_CHOOSE_DISCRETIZATION_SCHEME);
		d_combo_discr_scheme.setSelectedIndex(2);
		d_combo_discr_scheme.addActionListener(this);
		d_combo_discr_scheme.setEnabled(false);
		d_choice_2.add(d_combo_discr_scheme); // , BorderLayout.SOUTH);

		discretize.add(d_choice_2);

		// *-*-*-* //

		JPanel p1 = new JPanel(new FlowLayout());

		JButton okButton = new JButton("Apply");
		okButton.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_APPLY);
		okButton.addActionListener(this);
		p1.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(MethodConstants.PREPROCESS_DATA_WINDOW_CANCEL);
		cancelButton.addActionListener(this);
		p1.add(cancelButton);

		// *-*-*-* //

		JPanel contentPane = new JPanel(new FlowLayout()); // new
		// GridLayout(0,1));

		contentPane.add(normalize); // normalization panel,
		contentPane.add(discretize); // discretization panel
		contentPane.add(p1); // two end buttons

		contentPane.setPreferredSize(new Dimension(428, 300)); // 500,300));

		dialog.setContentPane(contentPane);

		dialog.setLocationRelativeTo(owner);
		dialog.pack();
		dialog.setSize(430,420);
		dialog.setVisible(true);

		// *-*-*-* //
	}

}
