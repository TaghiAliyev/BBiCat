/*
 *                                BiCat is a toolbox that combines different Bi-Clustering and clustering techniques in it, which
 *                                can be applied on a given dataset. This software is the modified version of the original BiCat
 *                                Toolbox implemented at ETH Zurich by Simon Barkow, Eckart Zitzler, Stefan Bleuler, Amela
 *                                Prelic and Don Frick.
 *
 *                                Copyright (c) 2015 Taghi Aliyev
 *
 *                                This file is part of BiCat.
 *
 *                                BiCat is a free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                BiCat is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with BiCat.  If not, see <http://www.gnu.org/licenses/>.
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
import bicat.util.*;
import lombok.Data;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.NumberFormat;

@Data
/**
 * GUI element associated with the overall GenePairAnalysis. Calls the methods from UtilFunctionalities in order
 * to perform Gene Pair Analysis
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class GenePairAnalysis implements ActionListener {

	private BicatGui owner;

	private UtilFunctionalities engine;

	private JDialog dialog;

	private int which_list = -1;

	private int list_idx = -1;

	private int which_data = -1;

	private JFormattedTextField scoreCoocurrenceField;

	private JFormattedTextField scoreCommonChipsField;

	private JCheckBox coocLabel;

	private JCheckBox commLabel;
	
	private boolean bcListSelected = false;

	// ===========================================================================
	public GenePairAnalysis() {
	}

	public GenePairAnalysis(UtilFunctionalities engine)
	{
		this.engine = engine;
		owner = null;
	}

	public GenePairAnalysis(BicatGui o) {
		this.engine = o.getUtilEngine();
		owner = o;
	}

	boolean byCooc = true;

	// ===========================================================================
	/**
	 * For <code>ActionListener</code> interface, reacts to user selections
	 * and button clicks in this search window.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_SELECT_BC_LIST.equals(e
				.getActionCommand())) {
			JComboBox box = (JComboBox) e.getSource();
			String item = (String) box.getSelectedItem();
			updateLabel_BiclusterListSelection(item);
			bcListSelected = true;
		}

		else if (MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_BY_COOCURRENCE.equals(e
				.getActionCommand())) {
			byCooc = !byCooc;
			if (byCooc) {
				coocLabel.setEnabled(true);
				coocLabel.setSelected(true);
				commLabel.setEnabled(false);
				commLabel.setSelected(false);
			} else {
				coocLabel.setEnabled(false);
				coocLabel.setSelected(false);
				commLabel.setEnabled(true);
				commLabel.setSelected(true);
			}
		}

		else if (MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_BY_COMMON_CHIPS.equals(e
				.getActionCommand())) {
			byCooc = !byCooc;
			if (byCooc) {
				coocLabel.setEnabled(true);
				coocLabel.setSelected(true);
				commLabel.setEnabled(false);
				commLabel.setSelected(false);
			} else {
				coocLabel.setEnabled(false);
				coocLabel.setSelected(false);
				commLabel.setEnabled(true);
				commLabel.setSelected(true);
			}
		}

		else if (MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_APPLY.equals(e.getActionCommand())) {

			if (!bcListSelected)
				JOptionPane
						.showMessageDialog(null,
								"Please choose a bicluster list and\nthe counting mode.");
			else {
				int scoreCooc = (new Integer(scoreCoocurrenceField.getText()))
						.intValue();
				int scoreComm = (new Integer(scoreCommonChipsField.getText()))
						.intValue();

				try {
					if (engine.getPost() == null)
						System.out.println("PROBLEM!!!");
					engine.getPost().genePairAnalysis(which_data, which_list, list_idx,
							scoreCooc, scoreComm, byCooc);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// JOptionPane.showMessageDialog(owner, "GPA started");
				dialog.setVisible(false);
				dialog.dispose();
				JOptionPane.showMessageDialog(null, "The GPA results can be found in the\nanalysis view of the corresponding dataset\n(please open the analysis result folder and click on the results)");  
				 
			}
		}

		else if (MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_CANCEL.equals(e.getActionCommand())) {
			dialog.setVisible(false);
			dialog.dispose();
		}

		else
			System.out.println("unknown event: " + e.paramString());
	}

	// ===========================================================================
	private void updateLabel_BiclusterListSelection(String item) {

		int[] info = BicatUtil.getListAndIdx(item);
		which_list = info[0];
		list_idx = info[1];
		which_data = info[2];

		if (info[0] == -1) {
			JOptionPane.showMessageDialog(null,
					"Please choose a bicluster list and\nthe counting mode.");
			//System.out.println("Select something! (Canceling)");
			dialog.setVisible(false);
			dialog.dispose();
		} else
			engine.updateCurrentDataset(which_data);
	}

	// ===========================================================================
	/**
	 * Creates the visible pop up window with all buttons, labels and fields.
	 * 
	 */
	public void makeWindow() {

		dialog = new JDialog(owner, "GPA Setup Dialog ");

		// //////////////////////////////////////////////////////////////////////////

		JPanel top = new JPanel(new FlowLayout());

		Vector names = engine.getListNamesAll();
		JComboBox cb = new JComboBox(names);
		cb.setActionCommand(MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_SELECT_BC_LIST);
		cb.setSelectedIndex(0);
		cb.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		cb.addActionListener(this);

		top.add(cb);

		// //////////////////////////////////////////////////////////////////////////

		JPanel byCooc = new JPanel(new GridLayout(0, 2));
		byCooc.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.black), "By coocurrence count"));

		coocLabel = new JCheckBox("Minimum for coocurrence score of genes",
				true);
		coocLabel.setActionCommand(MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_BY_COOCURRENCE);
		coocLabel.setEnabled(true);
		coocLabel.addActionListener(this);
		byCooc.add(coocLabel);

		scoreCoocurrenceField = new JFormattedTextField(NumberFormat
				.getIntegerInstance());
		scoreCoocurrenceField.setValue(new Integer(
				MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_DEFAULT_SCORE_VALUE));
		byCooc.add(scoreCoocurrenceField);

		// //////////////////////////////////////////////////////////////////////////

		JPanel byCommon = new JPanel(new GridLayout(0, 2));
		byCommon.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.black), "By common chips Count"));

		commLabel = new JCheckBox("Minimum for common chips score of genes",
				false); // new JLabel(" Lower Bound on the Common Chips Score
		// ");
		commLabel.setActionCommand(MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_BY_COMMON_CHIPS);
		commLabel.setEnabled(false);
		commLabel.addActionListener(this);
		byCommon.add(commLabel);

		scoreCommonChipsField = new JFormattedTextField(NumberFormat
				.getIntegerInstance());
		scoreCommonChipsField.setValue(new Integer(
				MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_DEFAULT_SCORE_VALUE));
		byCommon.add(scoreCommonChipsField);

		// //////////////////////////////////////////////////////////////////////////

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton okayButton = new JButton("OK");
		okayButton.setMnemonic(KeyEvent.VK_K);
		okayButton.setActionCommand(MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_APPLY);
		okayButton.addActionListener(this);
		buttonPanel.add(okayButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setMnemonic(KeyEvent.VK_C);
		cancelButton.setActionCommand(MethodConstants.GENE_PAIR_ANALYSIS_WINDOW_CANCEL);
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

		// //////////////////////////////////////////////////////////////////////////

		JPanel contentPane = new JPanel(new GridLayout(0, 1)); // BorderLayout());
		contentPane.add(top);
		contentPane.add(byCooc);
		contentPane.add(byCommon);
		contentPane.add(buttonPanel);

		dialog.setContentPane(contentPane);

		// set size, location and make visible
		dialog.pack();
		dialog.setLocationRelativeTo(owner);
		dialog.setVisible(true);
	}

}
