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

import bicat.biclustering.Bicluster;
import bicat.gui.BicatGui;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
/**
 * GUI element that prints and shows the information about the run process.
 *
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class RunInfo implements ActionListener {

    private String runLabel = ""; //null;
    private String runLabelInfo = "";

    /**
     * Hook to governings <code>BiacGUI</code>.
     */
    private BicatGui owner;

    /**
     * Bicluster that this window shows information on.
     */
    private Bicluster bc;

    /**
     * Dialog window.
     */
    private JDialog dialog;

    // ===========================================================================
    public RunInfo() {
    }

    public RunInfo(BicatGui o, String label, String info) {
        owner = o;
        runLabel = label;
        runLabelInfo = info;
    }

    // ===========================================================================

    /**
     * For <code>ActionListener</code> interface, reacts to user selections and
     * button clicks in this search window.
     */
    public void actionPerformed(ActionEvent e) {

        if ("close_info".equals(e.getActionCommand())) {
            // close window without doing anything
            dialog.setVisible(false);
            dialog.dispose();
        }
    /*else if("save_info".equals(e.getActionCommand())) {

      JFileChooser jfc = new JFileChooser(); // open a file chooser dialog window
      jfc.setDialogTitle("Save bicluster information:");
      File file;
      int returnVal = jfc.showOpenDialog(owner);

      if(returnVal == JFileChooser.APPROVE_OPTION) {
        try{
          file = jfc.getSelectedFile();
          FileWriter fw = new FileWriter(file);
          String writeBuffer = new String(bc.toString() + "\n\nGenes:\n");
          int[] genes = bc.getGenes();
          int[] chips = bc.getChips();
          for(int i=0; i<genes.length; i++)
            writeBuffer += owner.pre.getGeneName(genes[i]) + "\n";
          writeBuffer += "\n\nChips:\n";
          for(int i=0; i<chips.length; i++)
            writeBuffer += owner.pre.getChipName(chips[i]) + "\n";
          writeBuffer += "\n";

          fw.write(writeBuffer);
          fw.close();

          // close window
          dialog.setVisible(false);
          dialog.dispose();
        }
        catch(IOException ioe) { System.err.println(ioe); }
      }*/

        else System.out.println("unknown event: " + e.paramString());

    }


    // ===========================================================================

    /**
     * Creates and shows the window.
     */
    public void makeWindow() {

        //int[] genes = bc.getGenes();
        //int[] chips = bc.getChips();

        // new dialog window with general information on bicluster
        dialog = new JDialog(owner, "Run " + runLabel + " information");

        // create top panel with general information on bicluster (SHOULD CORRECT THIS... nije idealno!.. 300404)
        JPanel topPanel = new JPanel(new FlowLayout());

        JTextArea ta = new JTextArea();
        // ta.setText(runLabel+"\n"); // PROBA
        ta.setText(runLabelInfo);
        ta.setEditable(false);
        topPanel.add(ta, BorderLayout.CENTER);

/* topPanel.add(new JLabel(bc.toString()+" ["+bc.getGenes().length + " genes and "+ bc.getChips().length + " chips]"));

    // create gene subpanel
    JPanel gPanel = new JPanel (new GridLayout(0,1) );
    for(int i=0; i<genes.length; i++)
      gPanel.add(new JLabel(owner.pre.getGeneName(genes[i])));
    gPanel.setBorder(BorderFactory.createTitledBorder("Genes:"));

    // create chip subpanel
    JPanel cPanel = new JPanel (new GridLayout(0,1) );
    for(int i=0; i<chips.length; i++)
      cPanel.add(new JLabel(owner.pre.getChipName(chips[i])));
    cPanel.setBorder(BorderFactory.createTitledBorder("Chips:"));
*/

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton closeButton = new JButton("Close");
        // closeButton.setMnemonic(KeyEvent.VK_C);
        closeButton.setActionCommand("close_info");
        closeButton.addActionListener(this);
        buttonPanel.add(closeButton);

/*
    JButton saveButton = new JButton("Save");
    // closeButton.setMnemonic(KeyEvent.VK_S);
    saveButton.setActionCommand("save_info");
    saveButton.addActionListener(this);
    buttonPanel.add(saveButton);
*/
        // main panel of dialog window
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        //contentPane.add(gPanel,   BorderLayout.WEST);
        //contentPane.add(cPanel,   BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.setOpaque(true);
        dialog.setContentPane(contentPane);

        // set size, location and make visible
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

}

