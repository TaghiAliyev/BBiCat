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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.gui.BicatGui;
import bicat.util.BicatError;
import lombok.Data;

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
 * @uml.property  name="cfo_field"
 */
/**
 * @return
 * @uml.property  name="rfo_field"
 */
/**
 * @return
 * @uml.property  name="f_field"
 */
/**
 * @return
 * @uml.property  name="file"
 */
/**
 * @return
 * @uml.property  name="propFile"
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
 * @param cfo_field
 * @uml.property  name="cfo_field"
 */
/**
 * @param rfo_field
 * @uml.property  name="rfo_field"
 */
/**
 * @param f_field
 * @uml.property  name="f_field"
 */
/**
 * @return
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
 * @uml.property  name="cfo_field"
 */
/**
 * @return
 * @uml.property  name="rfo_field"
 */
/**
 * @return
 * @uml.property  name="f_field"
 */
/**
 * @return
 * @uml.property  name="file"
 */
/**
 * @return
 * @uml.property  name="propFile"
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
 * @param cfo_field
 * @uml.property  name="cfo_field"
 */
/**
 * @param rfo_field
 * @uml.property  name="rfo_field"
 */
/**
 * @param f_field
 * @uml.property  name="f_field"
 */
/**
 * @param file
 * @uml.property  name="file"
 */
@Data
public class LoadData implements ActionListener {

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
	 * @uml.property  name="cfo_field"
	 * @uml.associationEnd  
	 */
	private JFormattedTextField cfo_field;

	/**
	 * @uml.property  name="rfo_field"
	 * @uml.associationEnd  
	 */
	private JFormattedTextField rfo_field;

	/**
	 * @uml.property  name="f_field"
	 * @uml.associationEnd  
	 */
	private JTextField f_field;

	/**
	 * @uml.property  name="file"
	 */
	private File file;

	private static int DEFAULT_FILE_OFFSET = 1;

	// ===========================================================================
	public LoadData() {
	}

	public LoadData(UtilFunctionalities engine)
	{
		this.engine = engine;
		this.owner = null;
	}

	public LoadData(BicatGui o) {
		this.engine = o.getUtilEngine();
		owner = o;
	}

	/**
	 * @uml.property  name="propFile"
	 */
	final File propFile = new File(System.getProperty("user.home"),
			".bicat.lastfile");

	/**
	 * @uml.property  name="lastFilePropertyName"
	 */
	final String lastFilePropertyName = "last-file";

	// ===========================================================================
	public void makeWindow() {

		dialog = new JDialog(owner, "Load Data");

		// //////////////////////////////////////////////////////////////////////////

		JPanel panel_1 = new JPanel(new GridLayout(0, 2));
		panel_1.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.black), "Input File Format "));

		JLabel fo_label = new JLabel(
				"        Set column offset (column(s) with gene names): ");
		fo_label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel_1.add(fo_label);

		cfo_field = new JFormattedTextField(NumberFormat.getIntegerInstance());
		cfo_field.setValue(new Integer(DEFAULT_FILE_OFFSET));
		cfo_field.setColumns(6);
		cfo_field.addActionListener(this);
		panel_1.add(cfo_field);

		fo_label = new JLabel(
				"        Set row offset (row(s) with chip names): ");
		fo_label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel_1.add(fo_label);

		rfo_field = new JFormattedTextField(NumberFormat.getIntegerInstance());
		rfo_field.setValue(new Integer(DEFAULT_FILE_OFFSET));
		rfo_field.setColumns(6);
		rfo_field.addActionListener(this);
		panel_1.add(rfo_field);

		// //////////////////////////////////////////////////////////////////////////

		JPanel panel_2 = new JPanel(new FlowLayout());
		panel_2.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.black), "File Selection"));

		JLabel fileOne = new JLabel("Select data file: ");
		fileOne.setOpaque(true);
		panel_2.add(fileOne);

		// Show default file ""
		f_field = new JTextField(
				"C:/TEMP/100_20.txt",
				28);
		panel_2.add(f_field);

		JButton browse = new JButton("Browse...");
		browse.setActionCommand(MethodConstants.LOAD_DATA_WINDOW_BROWSE_DATA_MATRIX_FILE);
		browse.addActionListener(this);
		panel_2.add(browse);

		// //////////////////////////////////////////////////////////////////////////

		JPanel lowest = new JPanel(new FlowLayout());

		JButton okButton = new JButton("Load data");
		okButton.setActionCommand(MethodConstants.LOAD_DATA_WINDOW_APPLY);
		okButton.addActionListener(this);
		lowest.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(MethodConstants.LOAD_DATA_WINDOW_CANCEL);
		cancelButton.addActionListener(this);
		lowest.add(cancelButton);

		// //////////////////////////////////////////////////////////////////////////

		JPanel contentPane = new JPanel(new GridLayout(0, 1));

		contentPane.add(panel_1);
		contentPane.add(panel_2);
		contentPane.add(lowest);

		contentPane.setOpaque(true);
		dialog.setContentPane(contentPane);

		// ----
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

		if (MethodConstants.LOAD_DATA_WINDOW_APPLY.equals(e.getActionCommand())) {

			try {
				System.out.println("Here");
				int columnFileOffset = (new Integer(cfo_field.getText()))
						.intValue();
				int rowFileOffset = (new Integer(rfo_field.getText()))
						.intValue();

				if (null == file) {
					String fileName = f_field.getText();
					file = new File(fileName); // BicatGui.currentDirectoryPath
					// + "/" +
				}

				if (columnFileOffset > 0 & rowFileOffset > 0) {
					engine.getPre().loadData(file, columnFileOffset, rowFileOffset);
					System.out.println("Amount of datasets : " + engine.getDatasetList().size());
				} else {
					BicatError.offsetError();
				}
				dialog.setVisible(false);
				dialog.dispose();

			} catch (NumberFormatException ee) {
				BicatError.wrongOffsetError();
			} catch (Exception ee) {
				BicatError.errorMessage(ee, owner, true, "Gene and chip names must not contain spaces.\n\n");
//				dialog.setVisible(false);
//				dialog.dispose();
			}
		}

		else if (MethodConstants.LOAD_DATA_WINDOW_CANCEL.equals(e.getActionCommand())) {
			dialog.setVisible(false);
			dialog.dispose();
		}

		else if (MethodConstants.LOAD_DATA_WINDOW_BROWSE_DATA_MATRIX_FILE.equals(e
				.getActionCommand())) {

			if (MethodConstants.debug)
				System.out.println("Current working directory is: "
						+engine.getCurrentDirectoryPath());

			JFileChooser jfc = new JFileChooser(engine.getCurrentDirectoryPath());
			jfc.setMultiSelectionEnabled(true);
			jfc.setDragEnabled(true);
			jfc.setDialogTitle("Select main data file:");
			try {
				Properties prop = new Properties();
				prop.load(new FileInputStream(propFile));
				if (prop.containsKey(lastFilePropertyName)) {
					File selFile = new File(prop
							.getProperty(lastFilePropertyName));
					jfc.setSelectedFile(selFile);
				}
			} catch (IOException ex) {
				// could not fetch last file from properties file
				// ignore exception
				ex.printStackTrace();
			}

			if (jfc.showOpenDialog(owner) == JFileChooser.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				Properties prop = new Properties();
				prop.setProperty(lastFilePropertyName, file.getAbsolutePath());
				try {
					prop.store(new FileOutputStream(propFile),
							lastFilePropertyName);
				} catch (IOException ex) {
					// ignore
					ex.printStackTrace();
				}
				f_field.setText(file.getName());
			} else{

			}
		}

		else {
			System.out
					.println("What action is this? (LoadData.actionPerformed())");
		}
	}

}
