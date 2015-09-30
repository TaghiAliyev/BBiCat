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

import bicat.gui.*;
import lombok.Data;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.text.NumberFormat;

/**
 * This class is never used but can be used later on by the Hamming Distance method.
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class AuxDialog  implements ActionListener {

  private JDialog dialog;
  private bicat.gui.BicatGui owner;

  private int wl = 0;  // default: is ALWAYS the first list of biclusters!
  private int wi = 0;

  // ===========================================================================
  public AuxDialog() { }
  public AuxDialog(BicatGui o) { owner = o; }
  public AuxDialog(BicatGui o, int l, int i) { owner = o; wl = l; wi = i; }

  // ===========================================================================
  /**
   * For <code>ActionListener</code> interface, reacts to user selections and
   * button clicks in this search window.
   *
   * */
  public void actionPerformed(ActionEvent e) {

    // ...... Filtering procedures ...
    if(AUX_DIALOG_FBHD_APPLY.equals(e.getActionCommand())) {

      int maxErr = ( new Integer(maxErrorField.getText()).intValue());

      //owner.post.filterByHammingDistanceExtension(owner.currentDataset, maxErr, wl, wi,
      //                                            (owner.pre.preOption.discretizationScheme == PreprocessOption.PREPROCESS_OPTIONS_DISCRETIZATION_COEXPRESSED));
      owner.updateFilterMenu();
      owner.buildTree();

      dialog.setVisible(false);
      dialog.dispose();
    }

    else if(AUX_DIALOG_GPA_COOCURRENCE_APPLY.equals(e.getActionCommand())) {

      /* try { if(null != input) gpScore = Integer.parseInt(input); }
            catch(NumberFormatException nfe) {
        JOptionPane.showMessageDialog(this, "Invalid input, Gene Pair Distance Threshold is still "+gpScore);
      } */

      int gpScore = ( new Integer(minGPScoreField.getText()).intValue());

      owner.updateAnalysisMenu();
      owner.buildTree();

      owner.refreshAnalysisPanel();

      dialog.setVisible(false);
      dialog.dispose();
    }

    else if(AUX_DIALOG_GPA_COMMONCHIPS_APPLY.equals(e.getActionCommand())) {

      /*      try { if(null != input) gpDistanceThreshold = Integer.parseInt(input); }
             catch(NumberFormatException helloIAmYourNumberFormatExceptionForTodayHowMayIAnnoyYou) {
        JOptionPane.showMessageDialog(this, "Invalid input, GP distance is still "+gpDistanceThreshold);
             }
       */
      int gpScore = ( new Integer(minGPScoreField.getText()).intValue());

      //owner.post.gpaByCommonChips(owner.currentDataset, gpScore, wl, wi);
      // temp:
      owner.updateAnalysisMenu();
      owner.buildTree();

      owner.refreshAnalysisPanel();

      dialog.setVisible(false);
      dialog.dispose();
    }

    else if(AUX_DIALOG_CANCEL.equals(e.getActionCommand())) {
      dialog.setVisible(false);
      dialog.dispose();
    }

    else System.out.println("unknown event: "+e.paramString());

  }

  // ===========================================================================
  // AUX-DIALOG: Filter By Size

  static final String AUX_DIALOG_FBS_APPLY = "ad_fbs_ok";

  //static JFormattedTextField bcSizeField;
  //static JFormattedTextField geneSizeField;
  //static JFormattedTextField chipSizeField;

  // ===========================================================================
  // AUX-DIALOG: Filter By No Overlap

  //static final String AUX_DIALOG_FBNO_APPLY = "ad_fbno_ok";
  //static JFormattedTextField maxNumberOfBCsField;
  //static JFormattedTextField maxOverlapThresholdField;
  static JFormattedTextField maxErrorField;

  // ===========================================================================
  // AUX-DIALOG: Filter By New Area
  static final String AUX_DIALOG_FBNA_APPLY = "ad_fbna_ok";
  static final String AUX_DIALOG_FBHD_APPLY = "ad_fbhd_ok";
  static final String AUX_DIALOG_CANCEL = "ad_fbna_cancel";

  static JFormattedTextField minNewAreaThresholdField;

  // ===========================================================================
  // AUX-DIALOG: Filter By Hamming Distance

  // ===========================================================================
  public void makeWindow_FilterByHammingDistance() {

    dialog = new JDialog(owner, "Filter By Hamming Distance ");

    JPanel top = new JPanel( new GridLayout(0,1) );

    JPanel dimPanel = new JPanel( new GridLayout(0,2) );
    dimPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "Specify Error Tollerance "));

    JPanel labelPanel = new JPanel( new GridLayout(0,1) );
    labelPanel.add(new JLabel("Error Threshold must be smaller than (%)   "));
    dimPanel.add(labelPanel);

    // 2nd subpane in gene/chip subpanel with name entry fields and AND/OR radio buttons
    JPanel fieldPane = new JPanel( new GridLayout(0,1) );

    maxErrorField = new JFormattedTextField(NumberFormat.getIntegerInstance());
    maxErrorField.setValue(new Integer(50));
    maxErrorField.setAlignmentX(JFormattedTextField.RIGHT_ALIGNMENT);
    fieldPane.add(maxErrorField);
    dimPanel.add(fieldPane);

    top.add(dimPanel, BorderLayout.CENTER);

    // create bottom subpanel with cancel and OK buttons
    JPanel closePanel = new JPanel( new FlowLayout() );

    JButton okayButton = new JButton("OK");
    okayButton.setMnemonic(KeyEvent.VK_K);
    okayButton.setActionCommand(AUX_DIALOG_FBHD_APPLY);
    okayButton.addActionListener(this);
    closePanel.add(okayButton);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setMnemonic(KeyEvent.VK_C);
    cancelButton.setActionCommand(AUX_DIALOG_CANCEL);
    cancelButton.addActionListener(this);
    closePanel.add(cancelButton);

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.add(top);
    contentPane.add(closePanel, BorderLayout.PAGE_END);
    contentPane.setOpaque(true);
    dialog.setContentPane(contentPane);

    // set size, location and make visible
    dialog.pack();
    dialog.setLocationRelativeTo(owner);
    dialog.setVisible(true);

  }

  // ===========================================================================
  // AUX-DIALOG: GenePair Analysis By Co-ocurrence

  JFormattedTextField minGPScoreField;
  static final String AUX_DIALOG_GPA_COOCURRENCE_APPLY = "gpa_cooc_apply";

  // ===========================================================================
  public void makeWindow_GenePairAnalysis_ByCoocurrence() {

    dialog = new JDialog(owner, "Gene-Pair Analysis By Co-ocurrence ");

    JPanel top = new JPanel( new GridLayout(0,1) );

    JPanel dimPanel = new JPanel( new GridLayout(0,2) );
    dimPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "Specify Lower Score Threshold"));

    JPanel labelPanel = new JPanel( new GridLayout(0,1) );
    labelPanel.add(new JLabel("Minimum GP score    "));
    dimPanel.add(labelPanel);

    // 2nd subpane in gene/chip subpanel with name entry fields and AND/OR radio buttons
    JPanel fieldPane = new JPanel( new GridLayout(0,1) );

    minGPScoreField = new JFormattedTextField(NumberFormat.getIntegerInstance());
    minGPScoreField.setValue(new Integer(5));
    minGPScoreField.setAlignmentX(JFormattedTextField.RIGHT_ALIGNMENT);
    fieldPane.add(minGPScoreField);
    dimPanel.add(fieldPane);

    top.add(dimPanel);

    // create bottom subpanel with cancel and OK buttons
    JPanel closePanel = new JPanel( new FlowLayout() );

    JButton okayButton = new JButton("OK");
    okayButton.setMnemonic(KeyEvent.VK_K);
    okayButton.setActionCommand(AUX_DIALOG_GPA_COOCURRENCE_APPLY);
    okayButton.addActionListener(this);
    closePanel.add(okayButton);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setMnemonic(KeyEvent.VK_C);
    cancelButton.setActionCommand(AUX_DIALOG_CANCEL);
    cancelButton.addActionListener(this);
    closePanel.add(cancelButton);

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.add(top);
    contentPane.add(closePanel, BorderLayout.PAGE_END);
    contentPane.setOpaque(true);
    dialog.setContentPane(contentPane);

    // set size, location and make visible
    dialog.pack();
    dialog.setLocationRelativeTo(owner);
    dialog.setVisible(true);
  }

  // ===========================================================================
  // AUX-DIALOG: GenePair Analysis By Common Chips

  static final String AUX_DIALOG_GPA_COMMONCHIPS_APPLY = "gpa_chips_apply";

  // ===========================================================================
  public void makeWindow_GenePairAnalysis_ByCommonChips() {

    dialog = new JDialog(owner, "Gene-Pair Analysis By Common Chips ");

    JPanel top = new JPanel( new GridLayout(0,1) );

    JPanel dimPanel = new JPanel( new GridLayout(0,2) );
    dimPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "Specify Lower Score Threshold"));

    JPanel labelPanel = new JPanel( new GridLayout(0,1) );
    labelPanel.add(new JLabel("Minimum GP score    "));
    dimPanel.add(labelPanel);

    // 2nd subpane in gene/chip subpanel with name entry fields and AND/OR radio buttons
    JPanel fieldPane = new JPanel( new GridLayout(0,1) );

    minGPScoreField = new JFormattedTextField(NumberFormat.getIntegerInstance());
    minGPScoreField.setValue(new Integer(5));
    minGPScoreField.setAlignmentX(JFormattedTextField.RIGHT_ALIGNMENT);
    fieldPane.add(minGPScoreField);
    dimPanel.add(fieldPane);

    top.add(dimPanel);

    // create bottom subpanel with cancel and OK buttons
    JPanel closePanel = new JPanel( new FlowLayout() );

    JButton okayButton = new JButton("OK");
    okayButton.setMnemonic(KeyEvent.VK_K);
    okayButton.setActionCommand(AUX_DIALOG_GPA_COMMONCHIPS_APPLY);
    okayButton.addActionListener(this);
    closePanel.add(okayButton);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setMnemonic(KeyEvent.VK_C);
    cancelButton.setActionCommand(AUX_DIALOG_CANCEL);
    cancelButton.addActionListener(this);
    closePanel.add(cancelButton);

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.add(top);
    contentPane.add(closePanel, BorderLayout.PAGE_END);
    contentPane.setOpaque(true);
    dialog.setContentPane(contentPane);

    // set size, location and make visible
    dialog.pack();
    dialog.setLocationRelativeTo(owner);
    dialog.setVisible(true);
  }

}
