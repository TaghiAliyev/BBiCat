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


import lombok.Data;

import javax.swing.*;
import java.awt.*;

/**
 * @return
 * @uml.property  name="tabbedPanel"
 */
/**
 * @return
 * @uml.property  name="tabbedPane"
 */
/**
 * @return
 * @uml.property  name="emptyFilePanel"
 */
/**
 * @return
 * @uml.property  name="emptyFileArea"
 */
/**
 * @return
 * @uml.property  name="tablePreviewHandler"
 */
/**
 * @return
 * @uml.property  name="noFiles"
 */
/**
 * @return
 * @uml.property  name="fileSeparator"
 */
/**
 * @param tabbedPanel
 * @uml.property  name="tabbedPanel"
 */
/**
 * @param tabbedPane
 * @uml.property  name="tabbedPane"
 */
/**
 * @param emptyFilePanel
 * @uml.property  name="emptyFilePanel"
 */
/**
 * @param emptyFileArea
 * @uml.property  name="emptyFileArea"
 */
/**
 * @param tablePreviewHandler
 * @uml.property  name="tablePreviewHandler"
 */
/**
 * @param noFiles
 * @uml.property  name="noFiles"
 */
/**
 * @return
 */
/**
 * @return
 * @uml.property  name="tabbedPanel"
 */
/**
 * @return
 * @uml.property  name="tabbedPane"
 */
/**
 * @return
 * @uml.property  name="emptyFilePanel"
 */
/**
 * @return
 * @uml.property  name="emptyFileArea"
 */
/**
 * @return
 * @uml.property  name="tablePreviewHandler"
 */
/**
 * @return
 * @uml.property  name="noFiles"
 */
/**
 * @return
 * @uml.property  name="fileSeparator"
 */
/**
 * @param tabbedPanel
 * @uml.property  name="tabbedPanel"
 */
/**
 * @param tabbedPane
 * @uml.property  name="tabbedPane"
 */
/**
 * @param emptyFilePanel
 * @uml.property  name="emptyFilePanel"
 */
/**
 * @param emptyFileArea
 * @uml.property  name="emptyFileArea"
 */
/**
 * @param tablePreviewHandler
 * @uml.property  name="tablePreviewHandler"
 */
/**
 * @param noFiles
 * @uml.property  name="noFiles"
 */
/**
 * @param fileSeparator
 * @uml.property  name="fileSeparator"
 */
@Data
/**
 * Original Developers : Simon Barkow, Stefan Bleuler, Eckart Zitzler, Contributors: Amela Prelic, Don Frick
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class TabbedPaneController {

    /**
	 * @uml.property  name="tabbedPanel"
	 * @uml.associationEnd  
	 */
    private JPanel tabbedPanel = null;
    /**
	 * @uml.property  name="tabbedPane"
	 * @uml.associationEnd  
	 */
    private JTabbedPane tabbedPane;
    /**
	 * @uml.property  name="emptyFilePanel"
	 * @uml.associationEnd  
	 */
    private JPanel emptyFilePanel = null;
    /**
	 * @uml.property  name="emptyFileArea"
	 * @uml.associationEnd  
	 */
    private JTextArea emptyFileArea = null;
    //FileAndTextTransferHandler transferHandler;
    /**
	 * @uml.property  name="tablePreviewHandler"
	 * @uml.associationEnd  inverse="tpc:bicat.gui.window.TablePreviewHandler"
	 */
    private TablePreviewHandler tablePreviewHandler;
    /**
	 * @uml.property  name="noFiles"
	 */
    private boolean noFiles = true;
    /**
	 * @uml.property  name="fileSeparator"
	 */
    private String fileSeparator;

    // ===========================================================================
    public TabbedPaneController() {
    }

    public TabbedPaneController(JTabbedPane tb, JPanel tp) {
        tabbedPane = tb;
        tabbedPanel = tp;
        tablePreviewHandler = new TablePreviewHandler(this);
        // transferHandler = new FileAndTextTransferHandler(this);
        fileSeparator = System.getProperty("file.separator");

        //The split method in the String class uses
        //regular expressions to define the text used for
        //the split.  The forward slash "\" is a special
        //character and must be escaped.  Some look and feels,
        //such as Microsoft Windows, use the forward slash to
        //delimit the path.
        if ("\\".equals(fileSeparator)) {
            fileSeparator = "\\\\";
        }
        init();
        // makeTextPanel("see","see-2");
    }

    // ===========================================================================
    public JTextArea addTab(String filename) {
        if (noFiles) {
            tabbedPanel.remove(emptyFilePanel);
            tabbedPanel.add(tabbedPane, BorderLayout.CENTER);
            noFiles = false;
        }
        String[] str = filename.split(fileSeparator);
        return makeTextPanel(str[str.length - 1], filename);
    }

    // ===========================================================================
    //Remove all tabs and their components, then put the default file area back.
    public void clearAll() {
        if (!noFiles) {
            tabbedPane.removeAll();
            tabbedPanel.remove(tabbedPane);
        }
        init();
    }

    // ===========================================================================
    private void init() {
        String defaultText = "Click on the upper-leftmost data value in the table";
        noFiles = true;
        if (emptyFilePanel == null) {
            emptyFileArea = new JTextArea(20, 15);
            emptyFileArea.setEditable(false);
            // emptyFileArea.setDragEnabled(true);
            // emptyFileArea.setTransferHandler(previewHandler);
            emptyFileArea.setTransferHandler(tablePreviewHandler);
            emptyFileArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane fileScrollPane = new JScrollPane(emptyFileArea);
            emptyFilePanel = new JPanel(new BorderLayout(), false);
            emptyFilePanel.add(fileScrollPane, BorderLayout.CENTER);
        }
        tabbedPanel.add(emptyFilePanel, BorderLayout.CENTER);
        tabbedPanel.repaint();
        emptyFileArea.setText(defaultText);
    }

    // ===========================================================================
    protected JTextArea makeTextPanel(String name, String toolTip) {
        JTextArea fileArea = new JTextArea(20, 15);
        // fileArea.setDragEnabled(true);
        // fileArea.setTransferHandler(previewHandler);
        fileArea.add(new JTable(2, 3));

        fileArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane fileScrollPane = new JScrollPane(fileArea);
        tabbedPane.addTab(name, null, fileScrollPane, toolTip);
        tabbedPane.setSelectedComponent(fileScrollPane);
        return fileArea;
    }

}
