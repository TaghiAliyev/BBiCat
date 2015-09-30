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

package bicat.gui;

import bicat.Main.UtilFunctionalities;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;

/**
 * Class that has the functionality for showing the gene pair analysis results on the GUI.
 * <p>
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class AnalysisPane extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private BicatGui owner;
    private UtilFunctionalities engine;

    private JSplitPane splitPane;
    private JScrollPane tableScrollPane;
    private JScrollPane graphScrollPane;

    // ===========================================================================
    public AnalysisPane() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AnalysisPane(BicatGui o) {
        this.engine = o.getUtilEngine();
        owner = o;
    }

    // ===========================================================================

    /**
     * For <code>ActionListener</code> interface, could be used to react to user
     * input.
     */
    public void actionPerformed(ActionEvent e) {
    }

    // ===========================================================================

    /**
     * Hands the governing <code>BicaGUI</code> to this <code>GraphPane</code.>
     */
    public void setOwner(BicatGui frame) {
        this.engine = frame.getUtilEngine();
        owner = frame;
    }

    // ===========================================================================
    public void setTable(HashMap gps) {

        setLayout(new BorderLayout());
        removeAll();

        Object[][] data = new Object[gps.size()][4];

        Set keys = gps.keySet();
        Object[] ka = keys.toArray();
        for (int i = 0; i < ka.length; i++) {
            String key = (String) ka[i];
            String[] fragments = key.split("\t");
            if (engine == null)
                System.out.println("ENGINE IS NULL!!!! ABORT HERE!!!");
            if (owner == null)
                System.out.println("OWNER IS NULL AS WELL! HOLY SHIT!");
            data[i][0] = engine.getCurrentDataset().getGeneName(new Integer(fragments[0]).intValue());//pre.getGeneName((new Integer(fragments[0]).intValue()));
            data[i][1] = engine.getCurrentDataset().getGeneName(new Integer(fragments[1]).intValue());//pre.getGeneName((new Integer(fragments[1]).intValue()));
            data[i][2] = ((Object[]) gps.get(key))[0];
            data[i][3] = ((Object[]) gps.get(key))[1];
        }

        TableSorter ts = new TableSorter(new GenePairTableModel(data));
        JTable table = new JTable(ts);
        ts.setTableHeader(table.getTableHeader());

        //Set up tool tips for column headers.
        table.getTableHeader().setToolTipText("Click to specify sorting; Control-Click to specify secondary sorting");

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);

        table.setCellSelectionEnabled(true);

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(Color.WHITE);
        tableScrollPane.setOpaque(true);

        tableScrollPane.setMinimumSize(new Dimension(200, 150));

        // ....
//    graphScrollPane = new JScrollPane();
        //   graphScrollPane.setMaximumSize(new Dimension(10,10));
//    graphScrollPane.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "Display area for neighbor graph"));
//    graphScrollPane.add(new JLabel("Display area for the neighbor graph"));
//    graphScrollPane.setBackground(Color.WHITE);
//    graphScrollPane.setForeground(Color.WHITE);

//    graphScrollPane.setOpaque(true);
        //   graphScrollPane.setVisible(true);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScrollPane, graphScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(600);

        add(splitPane);
    }

    private void jbInit() throws Exception {

        throw new Exception("Please initalize Analysis Pane with parent frame!");
    }

}

