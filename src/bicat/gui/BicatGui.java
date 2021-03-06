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

package bicat.gui;

import MetaFramework.PathwayAnalysis;
import bicat.Constants.MethodConstants;
import bicat.Constants.UtilConstants;
import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Dataset;
import bicat.gui.window.*;
import bicat.postprocessor.Postprocessor;
import bicat.preprocessor.Preprocessor;
import bicat.run_machine.*;
import bicat.util.BicatUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * <code>BicatGui</code> is the central manager for the Graphical Interface,
 * datasets management, coordination of I/O operations.
 */

/**
 * <code>BicatGui</code> is the central manager for the Graphical Interface, datasets management, coordination of I/O operations.
 */
@EqualsAndHashCode(of = {"serialVersionUID", "DEFAULT_TOLERABLE"})
/**
 * @return
 * @uml.property  name="dEFAULT_TOLERABLE"
 */
/**
 * @return
 * @uml.property  name="parsedPathways"
 */
/**
 * @return
 * @uml.property  name="engine"
 */
/**
 * @return
 * @uml.property  name="enlargeContrast"
 */
/**
 * @return
 * @uml.property  name="cONTRAST_VALUE"
 */
/**
 * @return
 * @uml.property  name="stop_icon"
 */
/**
 * @return
 * @uml.property  name="bc_icon"
 */
/**
 * @return
 * @uml.property  name="listScrollPane"
 */
/**
 * @return
 * @uml.property  name="matrixScrollPane"
 */
/**
 * @return
 * @uml.property  name="graphScrollPane"
 */
/**
 * @return
 * @uml.property  name="otherScrollPane"
 */
/**
 * @return
 * @uml.property  name="pp"
 */
/**
 * @return
 * @uml.property  name="gp"
 */
/**
 * @return
 * @uml.property  name="op"
 */
/**
 * @return
 * @uml.property  name="pm"
 */
/**
 * @return
 * @uml.property  name="tree"
 */
/**
 * @return
 * @uml.property  name="top"
 */
/**
 * @return
 * @uml.property  name="treeModel"
 */
/**
 * @return
 * @uml.property  name="originalPath"
 */
/**
 * @return
 * @uml.property  name="preprocessedPath"
 */
/**
 * @return
 * @uml.property  name="matrixInfoLabel"
 */
/**
 * @return
 * @uml.property  name="graphInfoLabel"
 */
/**
 * @return
 * @uml.property  name="otherInfoLabel"
 */
/**
 * @return
 * @uml.property  name="preprocessOptionsMenu"
 */
/**
 * @return
 * @uml.property  name="labelMenu"
 */
/**
 * @return
 * @uml.property  name="saveAnalysisMenu"
 */
/**
 * @return
 * @uml.property  name="saveBiclusterMenu"
 */
/**
 * @return
 * @uml.property  name="saveClusterMenu"
 */
/**
 * @return
 * @uml.property  name="saveSearchMenu"
 */
/**
 * @return
 * @uml.property  name="saveFilterMenu"
 */
/**
 * @return
 * @uml.property  name="loadMenuItem"
 */
/**
 * @return
 * @uml.property  name="labels"
 */
/**
 * @return
 * @uml.property  name="currentLabel"
 */
/**
 * @return
 * @uml.property  name="utilEngine"
 */
/**
 * @return
 * @uml.property  name="popupListener"
 */
/**
 * @return
 * @uml.property  name="matrixViewPane"
 */
/**
 * @return
 * @uml.property  name="graphViewPane"
 */
/**
 * @return
 * @uml.property  name="otherViewPane"
 */
/**
 * @param DEFAULT_TOLERABLE
 * @uml.property  name="dEFAULT_TOLERABLE"
 */
/**
 * @param parsedPathways
 * @uml.property  name="parsedPathways"
 */
/**
 * @param engine
 * @uml.property  name="engine"
 */
/**
 * @param enlargeContrast
 * @uml.property  name="enlargeContrast"
 */
/**
 * @param CONTRAST_VALUE
 * @uml.property  name="cONTRAST_VALUE"
 */
/**
 * @param stop_icon
 * @uml.property  name="stop_icon"
 */
/**
 * @param bc_icon
 * @uml.property  name="bc_icon"
 */
/**
 * @param listScrollPane
 * @uml.property  name="listScrollPane"
 */
/**
 * @param matrixScrollPane
 * @uml.property  name="matrixScrollPane"
 */
/**
 * @param graphScrollPane
 * @uml.property  name="graphScrollPane"
 */
/**
 * @param otherScrollPane
 * @uml.property  name="otherScrollPane"
 */
/**
 * @param pp
 * @uml.property  name="pp"
 */
/**
 * @param gp
 * @uml.property  name="gp"
 */
/**
 * @param op
 * @uml.property  name="op"
 */
/**
 * @param pm
 * @uml.property  name="pm"
 */
/**
 * @param tree
 * @uml.property  name="tree"
 */
/**
 * @param top
 * @uml.property  name="top"
 */
/**
 * @param treeModel
 * @uml.property  name="treeModel"
 */
/**
 * @param originalPath
 * @uml.property  name="originalPath"
 */
/**
 * @param preprocessedPath
 * @uml.property  name="preprocessedPath"
 */
/**
 * @param matrixInfoLabel
 * @uml.property  name="matrixInfoLabel"
 */
/**
 * @param graphInfoLabel
 * @uml.property  name="graphInfoLabel"
 */
/**
 * @param otherInfoLabel
 * @uml.property  name="otherInfoLabel"
 */
/**
 * @param preprocessOptionsMenu
 * @uml.property  name="preprocessOptionsMenu"
 */
/**
 * @param labelMenu
 * @uml.property  name="labelMenu"
 */
/**
 * @param saveAnalysisMenu
 * @uml.property  name="saveAnalysisMenu"
 */
/**
 * @param saveBiclusterMenu
 * @uml.property  name="saveBiclusterMenu"
 */
/**
 * @param saveClusterMenu
 * @uml.property  name="saveClusterMenu"
 */
/**
 * @param saveSearchMenu
 * @uml.property  name="saveSearchMenu"
 */
/**
 * @param saveFilterMenu
 * @uml.property  name="saveFilterMenu"
 */
/**
 * @param loadMenuItem
 * @uml.property  name="loadMenuItem"
 */
/**
 * @param labels
 * @uml.property  name="labels"
 */
/**
 * @param currentLabel
 * @uml.property  name="currentLabel"
 */
/**
 * @param utilEngine
 * @uml.property  name="utilEngine"
 */
/**
 * @param popupListener
 * @uml.property  name="popupListener"
 */
/**
 * @param matrixViewPane
 * @uml.property  name="matrixViewPane"
 */
/**
 * @param graphViewPane
 * @uml.property  name="graphViewPane"
 */
/**
 * @param otherViewPane
 * @uml.property  name="otherViewPane"
 */
/**
 * @return
 * @uml.property  name="dEFAULT_TOLERABLE"
 */
/**
 * @return
 * @uml.property  name="parsedPathways"
 */
/**
 * @return
 * @uml.property  name="engine"
 */
/**
 * @return
 * @uml.property  name="enlargeContrast"
 */
/**
 * @return
 * @uml.property  name="cONTRAST_VALUE"
 */
/**
 * @return
 * @uml.property  name="stop_icon"
 */
/**
 * @return
 * @uml.property  name="bc_icon"
 */
/**
 * @return
 * @uml.property  name="listScrollPane"
 */
/**
 * @return
 * @uml.property  name="matrixScrollPane"
 */
/**
 * @return
 * @uml.property  name="graphScrollPane"
 */
/**
 * @return
 * @uml.property  name="otherScrollPane"
 */
/**
 * @return
 * @uml.property  name="pp"
 */
/**
 * @return
 * @uml.property  name="gp"
 */
/**
 * @return
 * @uml.property  name="op"
 */
/**
 * @return
 * @uml.property  name="pm"
 */
/**
 * @return
 * @uml.property  name="tree"
 */
/**
 * @return
 * @uml.property  name="top"
 */
/**
 * @return
 * @uml.property  name="treeModel"
 */
/**
 * @return
 * @uml.property  name="originalPath"
 */
/**
 * @return
 * @uml.property  name="preprocessedPath"
 */
/**
 * @return
 * @uml.property  name="matrixInfoLabel"
 */
/**
 * @return
 * @uml.property  name="graphInfoLabel"
 */
/**
 * @return
 * @uml.property  name="otherInfoLabel"
 */
/**
 * @return
 * @uml.property  name="preprocessOptionsMenu"
 */
/**
 * @return
 * @uml.property  name="labelMenu"
 */
/**
 * @return
 * @uml.property  name="saveAnalysisMenu"
 */
/**
 * @return
 * @uml.property  name="saveBiclusterMenu"
 */
/**
 * @return
 * @uml.property  name="saveClusterMenu"
 */
/**
 * @return
 * @uml.property  name="saveSearchMenu"
 */
/**
 * @return
 * @uml.property  name="saveFilterMenu"
 */
/**
 * @return
 * @uml.property  name="loadMenuItem"
 */
/**
 * @return
 * @uml.property  name="labels"
 */
/**
 * @return
 * @uml.property  name="currentLabel"
 */
/**
 * @return
 * @uml.property  name="utilEngine"
 */
/**
 * @return
 * @uml.property  name="popupListener"
 */
/**
 * @return
 * @uml.property  name="matrixViewPane"
 */
/**
 * @return
 * @uml.property  name="graphViewPane"
 */
/**
 * @return
 * @uml.property  name="otherViewPane"
 */
/**
 * @param DEFAULT_TOLERABLE
 * @uml.property  name="dEFAULT_TOLERABLE"
 */
/**
 * @param parsedPathways
 * @uml.property  name="parsedPathways"
 */
/**
 * @param engine
 * @uml.property  name="engine"
 */
/**
 * @param enlargeContrast
 * @uml.property  name="enlargeContrast"
 */
/**
 * @param CONTRAST_VALUE
 * @uml.property  name="cONTRAST_VALUE"
 */
/**
 * @param stop_icon
 * @uml.property  name="stop_icon"
 */
/**
 * @param bc_icon
 * @uml.property  name="bc_icon"
 */
/**
 * @param listScrollPane
 * @uml.property  name="listScrollPane"
 */
/**
 * @param matrixScrollPane
 * @uml.property  name="matrixScrollPane"
 */
/**
 * @param graphScrollPane
 * @uml.property  name="graphScrollPane"
 */
/**
 * @param otherScrollPane
 * @uml.property  name="otherScrollPane"
 */
/**
 * @param pp
 * @uml.property  name="pp"
 */
/**
 * @param gp
 * @uml.property  name="gp"
 */
/**
 * @param op
 * @uml.property  name="op"
 */
/**
 * @param pm
 * @uml.property  name="pm"
 */
/**
 * @param tree
 * @uml.property  name="tree"
 */
/**
 * @param top
 * @uml.property  name="top"
 */
/**
 * @param treeModel
 * @uml.property  name="treeModel"
 */
/**
 * @param originalPath
 * @uml.property  name="originalPath"
 */
/**
 * @param preprocessedPath
 * @uml.property  name="preprocessedPath"
 */
/**
 * @param matrixInfoLabel
 * @uml.property  name="matrixInfoLabel"
 */
/**
 * @param graphInfoLabel
 * @uml.property  name="graphInfoLabel"
 */
/**
 * @param otherInfoLabel
 * @uml.property  name="otherInfoLabel"
 */
/**
 * @param preprocessOptionsMenu
 * @uml.property  name="preprocessOptionsMenu"
 */
/**
 * @param labelMenu
 * @uml.property  name="labelMenu"
 */
/**
 * @param saveAnalysisMenu
 * @uml.property  name="saveAnalysisMenu"
 */
/**
 * @param saveBiclusterMenu
 * @uml.property  name="saveBiclusterMenu"
 */
/**
 * @param saveClusterMenu
 * @uml.property  name="saveClusterMenu"
 */
/**
 * @param saveSearchMenu
 * @uml.property  name="saveSearchMenu"
 */
/**
 * @param saveFilterMenu
 * @uml.property  name="saveFilterMenu"
 */
/**
 * @param loadMenuItem
 * @uml.property  name="loadMenuItem"
 */
/**
 * @param labels
 * @uml.property  name="labels"
 */
/**
 * @param currentLabel
 * @uml.property  name="currentLabel"
 */
/**
 * @param utilEngine
 * @uml.property  name="utilEngine"
 */
/**
 * @param popupListener
 * @uml.property  name="popupListener"
 */
/**
 * @param matrixViewPane
 * @uml.property  name="matrixViewPane"
 */
/**
 * @param graphViewPane
 * @uml.property  name="graphViewPane"
 */
/**
 * @param otherViewPane
 * @uml.property  name="otherViewPane"
 */
@Data
public class BicatGui extends JFrame implements ActionListener,
        TreeSelectionListener, MouseListener, ItemListener {

    /**
	 * @uml.property  name="serialVersionUID"
	 */
    private final long serialVersionUID = 1L;

    /**
	 * @uml.property  name="dEFAULT_TOLERABLE"
	 */
    private int DEFAULT_TOLERABLE = 1000;

    /**
	 * @uml.property  name="parsedPathways"
	 */
    private boolean parsedPathways = false;

    /**
	 * @uml.property  name="engine"
	 * @uml.associationEnd  
	 */
    private PathwayAnalysis engine;

    /**
	 * @uml.property  name="genes"
	 */
    private Set<String> genes;

    /* Matrix visualization: whether add contrast? */
    /**
	 * @uml.property  name="enlargeContrast"
	 */
    private boolean enlargeContrast = true;

    /**
	 * @uml.property  name="cONTRAST_VALUE"
	 */
    private float CONTRAST_VALUE = (float) 0; // 1; // 2; // 0.50;

    /**
	 * @uml.property  name="stop_icon"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private ImageIcon stop_icon;

    /**
	 * @uml.property  name="bc_icon"
	 * @uml.associationEnd  
	 */
    private ImageIcon bc_icon;

    /**
	 * A scroll pane for display of biclusters and other things in a tree.
	 * @uml.property  name="listScrollPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JScrollPane listScrollPane;

    /**
	 * A scroll pane for matrix display.
	 * @uml.property  name="matrixScrollPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JScrollPane matrixScrollPane;

    /**
	 * A scroll pane for expression graph graph(ik) display.
	 * @uml.property  name="graphScrollPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JScrollPane graphScrollPane;

    /**
	 * A scroll pane for visualizing GenePair Analysis results.
	 * @uml.property  name="otherScrollPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JScrollPane otherScrollPane;

    /**
	 * Used to draw the data matrix and display biclusters.
	 * @uml.property  name="pp"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="owner:bicat.gui.PicturePane"
	 */
    private PicturePane pp;

    /**
	 * Used to draw expression graph.
	 * @uml.property  name="gp"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="owner:bicat.gui.GraphicPane"
	 */
    private GraphicPane gp;

    /**
	 * Used to display the GPA table (and, eventually the graph)
	 * @uml.property  name="op"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="owner:bicat.gui.AnalysisPane"
	 */
    private AnalysisPane op;

    /**
	 * @todo   would like to "personalize" it.
	 * @uml.property  name="pm"
	 */
    private PopupMenu pm;

    /**
	 * Contains various display options, list of biclusters and list of search results, if available
	 * @uml.property  name="tree"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private JTree tree;

    /**
	 * Root node of <code>tree</code>.
	 * @uml.property  name="top"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private DefaultMutableTreeNode top;

    /**
	 * Model for <code>tree</code>.
	 * @uml.property  name="treeModel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private DefaultTreeModel treeModel;

    /**
	 * Tree path to node that represents original data display.
	 * @uml.property  name="originalPath"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private TreePath originalPath;

    /**
	 * Tree path to node that represents display of preprocessed data.
	 * @uml.property  name="preprocessedPath"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private TreePath preprocessedPath;

    /**
	 * Provides information in the matrix display panel.
	 * @uml.property  name="matrixInfoLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JLabel matrixInfoLabel;

    /**
	 * Provides informations in the expression graph(ik) panel.
	 * @uml.property  name="graphInfoLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JLabel graphInfoLabel;

    /**
	 * Provides information in the analysis panel.
	 * @uml.property  name="otherInfoLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JLabel otherInfoLabel;

    /**
	 * @uml.property  name="preprocessOptionsMenu"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JMenu preprocessOptionsMenu = null;

    /**
	 * @uml.property  name="labelMenu"
	 * @uml.associationEnd  
	 */
    private JMenu labelMenu = null;

    /**
	 * @uml.property  name="saveAnalysisMenu"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JMenu saveAnalysisMenu = null;

    /**
	 * @uml.property  name="saveBiclusterMenu"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JMenu saveBiclusterMenu = null;

    /**
	 * @uml.property  name="saveClusterMenu"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JMenu saveClusterMenu = null;

    /**
	 * @uml.property  name="saveSearchMenu"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JMenu saveSearchMenu = null;

    /**
	 * @uml.property  name="saveFilterMenu"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JMenu saveFilterMenu = null;

    /**
	 * @uml.property  name="loadMenuItem"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private JMenuItem loadMenuItem;

    /**
	 * @uml.property  name="labels"
	 */
    private Vector labels = null;

    /**
	 * @uml.property  name="currentLabel"
	 */
    private int currentLabel = -1;

    /**
	 * @uml.property  name="utilEngine"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="owner:bicat.Main.UtilFunctionalities"
	 */
    private UtilFunctionalities utilEngine;

    // ===========================================================================
    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        String s = "Item event detected."
                + "\n"
                + "\tEvent source: "
                + source.getText()
                + " (an instance of "
                + getClassName(source)
                + ")"
                + "\n"
                + "\tNew state: "
                + ((e.getStateChange() == ItemEvent.SELECTED) ? "selected"
                : "unselected");
        // different from the sample:
        System.out.println(s);
    }

    // ===========================================================================
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }

    // ===========================================================================

    /**
     * For <code>MouseListener</code> interface, called when mouse is clicked
     * inside <code>PicturePane</code>
     */
    public void mouseClicked(MouseEvent e) {

        if (utilEngine.getRawData() == null)
            matrixInfoLabel.setText(" Load data files for analysis");

        else {
            if (MethodConstants.IN_MATRIX == pp.clickedRegion(e.getX(), e.getY())) { // click
                // in
                // matrix,
                // so
                // update
                // info
                // label

                Point clickedRect = pp
                        .getRectFromCoordinate(e.getX(), e.getY());
                String geneName, chipName;

                if (null == clickedRect) {
                    geneName = null;
                    chipName = null;
                } else if (utilEngine.getRawData()[0].length == utilEngine.getPre().getWorkingChipCount()) { // if
                    // working
                    // with
                    // data
                    // stripped
                    // of
                    // control
                    // chips
                    geneName = utilEngine.getCurrentDataset().getGeneName((int) clickedRect
                            .getY());
                    chipName = utilEngine.getCurrentDataset()
                            .getWorkingChipName((int) clickedRect.getX());
                } else { // if working with data that includes control chips
                    geneName = utilEngine.getCurrentDataset().getGeneName((int) clickedRect
                            .getY());
                    chipName = utilEngine.getCurrentDataset().getChipName((int) clickedRect
                            .getX());
                }

                if ((null == geneName) || (null == chipName))
                    matrixInfoLabel
                            .setText(" Click on a sample to get gene and chip names");
                else
                    matrixInfoLabel.setText(" Gene: " + geneName + "\tChip: "
                            + chipName);
            } else if (MethodConstants.IN_GENES == pp.clickedRegion(e.getX(), e
                    .getY())) { // click on gene names, update selections
                pp.geneSelected(pp.getGeneNameIndex(e.getY()));
                refreshGraphicPanel();
                pp.repaint();

            } else if (MethodConstants.IN_CHIPS == pp.clickedRegion(e.getX(), e
                    .getY())) { // click on chip names, update selections
                pp.chipSelected(pp.getChipNameIndex(e.getX()));
                pp.repaint();
            } else if (MethodConstants.OUTSIDE == pp
                    .clickedRegion(e.getX(), e.getY())) { // click in clear
                // area, clear
                // selections
                pp.clearSelections();
                pp.repaint();
                gp.setGraphDataList(null, null, null);
            } else
                // click wasn't in a valid clickable region
                matrixInfoLabel
                        .setText(" Click on a sample to get gene and chip names");
        }
    }

    // ===========================================================================

    /**
     * For <code>MouseListener</code> interface, called when mouse is
     * depressed inside <code>PicturePane</code>
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * For <code>MouseListener</code> interface, called when mouse is released
     * inside <code>PicturePane</code>
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * For <code>MouseListener</code> interface, called when mouse enters
     * <code>PicturePane</code>
     */
    public void mouseEntered(MouseEvent e) {
        if (!utilEngine.isPreprocessComplete())
            matrixInfoLabel.setText(" Load data files for analysis");
        else
            matrixInfoLabel
                    .setText(" Click on a sample to get gene and chip names");
    }

    /**
     * For <code>MouseListener</code> interface, called when mouse exits
     * <code>PicturePane</code>
     */
    public void mouseExited(MouseEvent e) {
        if (utilEngine.getRawData() != null)
            matrixInfoLabel.setText(" Load data files for analysis");
        else if (!utilEngine.anyBiclustersAvailable())
            matrixInfoLabel.setText(" Run bi/clustering algorithm on data");
        else
            matrixInfoLabel
                    .setText(" Select biclusters from left panel to display them");
    }

    // ===========================================================================

    /**
     * For <code>TreeSelectionListener</code> interface, called when a tree is
     * selected
     */
    public void valueChanged(TreeSelectionEvent e) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                .getLastSelectedPathComponent();
        if (node == null)
            return;

        Object maybeBC = node.getUserObject();

        // **********************************************************************
        // //
        if ("Original data".equals(node.toString())) { // check which tree node
            // has been selected

            // get the correct dataset, and display it:
            TreePath tp = tree.getSelectionPath();
            int dataset_idx = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            utilEngine.updateCurrentDataset(dataset_idx);

            if (null == utilEngine.getCurrentDataset().getOrigData())
                JOptionPane.showMessageDialog(this, "No data loaded", "Error",
                        JOptionPane.ERROR_MESSAGE);
            else {
                // display original data
                utilEngine.setData(utilEngine.getCurrentDataset().getOrigData()); // get the data to
                // visualize on the PP
                pp.setData(utilEngine.getRawData());
                readjustPictureSize();
                pp.repaint();
                updateColumnHeadersMenu();
            }
        }

        // **********************************************************************
        // //
        else if ("Preprocessed data".equals(node.toString())) {

            // get the correct dataset, and display it:
            TreePath tp = tree.getSelectionPath();
            int dataset_idx = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            utilEngine.updateCurrentDataset(dataset_idx);

            if (null == utilEngine.getCurrentDataset().getPreData()) {
                JOptionPane.showMessageDialog(this, "Data not preprocessed",
                        "Error", JOptionPane.ERROR_MESSAGE);
                tree.setSelectionPath(preprocessedPath); // originalPath);
            } else {
                // display processed data
                utilEngine.setData(utilEngine.getCurrentDataset().getPreData()); // pre.getPreprocessedData());
                pp.setData(utilEngine.getRawData());
                readjustPictureSize();
                pp.repaint();
                updateColumnHeadersMenu();
            }
        }

        // **********************************************************************
        // //
        else if ("Discretized data".equals(node.toString())) {

            // get the correct dataset, and display it:
            TreePath tp = tree.getSelectionPath();
            int dataset_idx = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            utilEngine.updateCurrentDataset(dataset_idx);

            if (null == utilEngine.getCurrentDataset().getDiscrData()) {
                JOptionPane.showMessageDialog(this, "Data not discretized",
                        "Error", JOptionPane.ERROR_MESSAGE);
                tree.setSelectionPath(originalPath);
            } else {
                // display binary matrix
                utilEngine.setData(utilEngine.getCurrentDataset().getVisualDiscrData(utilEngine.getCurrentDataset()
                        .isExtended()));
                pp.setData(utilEngine.getRawData());
                readjustPictureSize();
                pp.repaint();
                matrixInfoLabel
                        .setText("Threshold: " + utilEngine.getDiscretizationThreshold());
                updateColumnHeadersMenu();
            }
        }

        // **********************************************************************
        // //
        else if (Pattern.matches("DataSet \\d*", node.toString())) {

            // if(debug) System.out.println("D: selected node
            // \""+node.toString()+"\"");

            // get the correct dataset, and display it:
            TreePath tp = tree.getSelectionPath();
            int dataset_idx = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            utilEngine.updateCurrentDataset(dataset_idx);

            // display original data
            utilEngine.setData(utilEngine.getCurrentDataset().getOrigData()); // get the data to visualize
            // on the PP
            pp.setData(utilEngine.getRawData());
            readjustPictureSize();
            pp.repaint();
            updateColumnHeadersMenu();
        }

        // **********************************************************************
        // //
        else if ("Data Display".equals(node.toString())) {

            // get the correct dataset, and display it:
            TreePath tp = tree.getSelectionPath();
            int dataset_idx = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            utilEngine.updateCurrentDataset(dataset_idx);

            // display original data
            utilEngine.setData(utilEngine.getCurrentDataset().getOrigData()); // get the data to visualize
            // on the PP
            pp.setData(utilEngine.getRawData());
            readjustPictureSize();
            pp.repaint();
            updateColumnHeadersMenu();
        }

        // **********************************************************************
        // //
        else if// ( "Display".equals(node.toString())
            // || (
                ("Bicluster results".equals(node.toString())
                        || "Cluster results".equals(node.toString())
                        || "Filter results".equals(node.toString())
                        || "Search results".equals(node.toString())
                        || "Analysis results".equals(node.toString())) {

            // if(debug) System.out.println("D: selected node
            // \""+node.toString()+"\"");

            // Always display the correct dataset...
            // get the correct dataset, and display it:
            TreePath tp = tree.getSelectionPath();
            int dataset_idx = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            utilEngine.updateCurrentDataset(dataset_idx);

            // display original data
            utilEngine.setData(utilEngine.getCurrentDataset().getOrigData()); // get the data to visualize
            // on the PP
            pp.setData(utilEngine.getRawData());
            readjustPictureSize();
            pp.repaint();
            updateColumnHeadersMenu();
        }

        // **********************************************************************
        // //
        else if (Pattern.matches("Analysis result of .*", node.toString())) {

            // get the correct dataset, and display it:
            TreePath tp = tree.getSelectionPath();
            int dataset_idx = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            utilEngine.updateCurrentDataset(dataset_idx);

            updateColumnHeadersMenu();
            // display original data
            utilEngine.setData(utilEngine.getCurrentDataset().getOrigData()); // get the data to visualize
            // on the PP
            pp.setData(utilEngine.getRawData());
            readjustPictureSize();
            pp.repaint();

            // ....
            int idx = BicatUtil.getAnalysisNodeIdx(node.toString());

			/*
             * if(debug) { // System.out.println("D: "+e.getPath().toString()); //
			 * System.out.println("D: depth: " + node.getDepth()); TreeNode[] tn =
			 * node.getPath(); for (int p = 0; p < tn.length; p++) {
			 * System.out.println("D: node in path: " + tn[p].toString()); } }
			 */

            TreeNode[] tn = node.getPath();
            int data = BicatUtil.getDataset(tn[1].toString())[0];

            refreshAnalysisPanel(data, idx);

        } else if ("bicat.biclustering.Bicluster".equals(maybeBC.getClass()
                .getName())) { // a bicluster node has been selected

            // get the correct dataset, and display it:

            TreePath tp = tree.getSelectionPath();
            int dataset_idx = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            utilEngine.updateCurrentDataset(dataset_idx);
            updateColumnHeadersMenu();

            // display original data
            /*
             * setData(currentDataset.getOrigData()); // get the data to
			 * visualize on the PP pp.setData(rawData); readjustPictureSize();
			 * pp.repaint();
			 */

            // *** visualize the selected bicluster in the upper left corner
            /*
			 * if(rawData[0].length != pre.getWorkingChipCount()) { // if the
			 * user is viewing original data with control chips
			 * setData(pre.getPreprocessedData()); pp.setData(rawData); }
			 */
            utilEngine.setData(utilEngine.getCurrentDataset().getPreData());
            pp.setData(utilEngine.getRawData());

            bicat.biclustering.Bicluster selection = (bicat.biclustering.Bicluster) (node
                    .getUserObject());
            utilEngine.setCurrentBiclusterSelection(pp.setTranslationTable(selection));

            matrixScrollPane.repaint();
            readjustPictureSize();
            pp.repaint();

            // *** in bg, visualize the gene expression profiles graphik (with
            // discr. thr. plotted)

            int dataset = BicatUtil.getDataset((tp.getPathComponent(1))
                    .toString())[0];
            int[] listAndIdx = BicatUtil.getListAndIdxTree(tp.getPathComponent(
                    3).toString());

            if (MethodConstants.debug) {
                System.out.println("Debug: "
                        + tp.getPathComponent(3).toString());
                System.out.println("Debug: dataset = " + dataset + ", l_0 = "
                        + listAndIdx[0] + // what list selected (L, F, S) ...
                        // oder C (clusters, as of
                        // 16.03.2005)
                        ", l_1 = " + listAndIdx[1] + // what was the original
                        // list (if any) (L, F,
                        // S)_orig
                        ", l_2 = " + listAndIdx[2] + // idx original list
                        ", l_3 = " + listAndIdx[3]); // idx list selected
            }

            Dataset br = ((Dataset) utilEngine.getDatasetList().get(dataset));

            while (listAndIdx[1] != 0 && listAndIdx[1] != 3) { // what is the
                // original BC
                // list?
                // if(debug) System.out.println("Before: "+listAndIdx[0]+",
                // "+listAndIdx[1]+", "+listAndIdx[2]+", "+listAndIdx[3]);
                listAndIdx = br
                        .getOriginatingList(listAndIdx[1], listAndIdx[2]);
                // if(debug) System.out.println("After: "+listAndIdx[0]+",
                // "+listAndIdx[1]+", "+listAndIdx[2]+", "+listAndIdx[3]);
            }
            if (MethodConstants.debug)
                System.out.println("Original BC list is " + listAndIdx[1]
                        + ", " + listAndIdx[2]);

            double[] discretizationThr;
            if (listAndIdx[1] == 0)
                discretizationThr = br.getPreprocessOptions(listAndIdx[2]).getDiscretizationThreshold();
            else
                discretizationThr = br
                        .getPreprocessOptionsClusters(listAndIdx[2]).getDiscretizationThreshold();

            pp.setTranslationTable(selection);
            // pp.setTranslationTable(selection, rawData_for_GP);

            matrixScrollPane.repaint();
            readjustPictureSize();
            pp.repaint();
            pp.biclusterSelected(utilEngine.getCurrentBiclusterSelection()[1],
                    utilEngine.getCurrentBiclusterSelection()[2]);

            gp.setGraphDataList(utilEngine.getCurrentBiclusterSelection()[0],
                    utilEngine.getCurrentBiclusterSelection()[1], utilEngine.getCurrentBiclusterSelection()[2]);

            refreshGraphicPanel(discretizationThr);

            // *** visualize the list of gene IDs in the Analysis Pane

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < utilEngine.getCurrentBiclusterSelection()[1].size(); i++)
                sb.append(utilEngine.getCurrentDataset()
                        .getGeneName(((Integer) utilEngine.getCurrentBiclusterSelection()[1]
                                .get(i)).intValue())
                        + "\n");

            op.repaint();
            op.removeAll();
            JTextPane tpane = new JTextPane();
            tpane.setText(sb.toString());
            tpane.setAlignmentX((float) 0.0);
            op.add(tpane, BorderLayout.WEST);

        } else if (node.isLeaf()) {
            if (MethodConstants.debug)
                System.out.println("D: Leaf node has been selected: \""
                        + maybeBC + "\"");
        } else {

            if (BicatUtil.isListOfBCs((String) maybeBC)) {
                LinkedList list = BicatUtil.getListOfBiclusters(tree
                        .getSelectionPath(), utilEngine.getDatasetList());
                if (list.size() < DEFAULT_TOLERABLE)
                    gp.visualizeAll(list, utilEngine.getRawData_for_GP(), gp.getDEFAULT_X_TABLE());
            }

            // check if some Bicluster List has been selected
            if (MethodConstants.debug) {
                String node_str = (String) maybeBC;
                System.out
                        .println("D (final else clause, BicatGui.ValueChanged): selected node \""
                                + node_str + "\"");
            }
        }
    }

    // ************************************************************************
    // //
    // * * //
    // * Action Manager * //
    // * * //
    // ************************************************************************
    // //

    /**
     * For <code>ActionListener</code> interface, called when an action
     * command is performed (usually through the pull down menus)
     */

    public void actionPerformed(ActionEvent e) {

        try {

            if ("Bayes_Simple".equalsIgnoreCase(e.getActionCommand()))
            {
                // Bayesian statistics here.
                // We have to be sure that biclustering is already performed before we
                // compute Bayesian statistics
                if (utilEngine.anyValidListAvailable())
                {
                    // Make sure to add the user-choice on how the groups should be divided
                    // Options: based on the column from a dataset
                    // or, differentially expressed genes

                    BayesianWindow bayes = new BayesianWindow(this);
                    bayes.show();
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Please perform bi-clustering before applying bayesian statistics");
                }
            }
			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if ("Pathway_Analysis".equalsIgnoreCase(e.getActionCommand())) {
                // Do pathway analysis here

                // Here, check for the biclusters.
                if (utilEngine.anyValidListAvailable()) {
                    PathwayGUI pathwayGUI = new PathwayGUI();
                    pathwayGUI.showWindow();
                } else
                    JOptionPane.showMessageDialog(this,
                            "Perform biclustering before doing Pathway Analysis!");

            } else if ("Translation".equalsIgnoreCase(e.getActionCommand())) {
                // Do translation
                if (parsedPathways && utilEngine.getDatasetList().size() > 0) {
                    TranslationGUI transGUI = new TranslationGUI(this);
                    transGUI.showYourself();
                } else
                    JOptionPane.showMessageDialog(this, "Load dataset first please!");
            } else if (UtilConstants.MAIN_QUIT.equals(e.getActionCommand()))
                System.exit(0);

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (Pattern.matches("\\d*", (e
                    .getActionCommand()))
                    && (new Integer(e.getActionCommand())).intValue() >= 0
                    && (new Integer(e.getActionCommand())).intValue() < pp.getOwner().utilEngine.getPre().getLabels()
                    .size()) {

                // mozda bih trebala das ding update (dynamic menu, as the
                // datasets are being selected...)
                // auch labeled, muss false (resetted werden)
                pp.setLabeled(true);
                pp.setLabelIdx((new Integer(e.getActionCommand())).intValue());
                pp.getOwner().matrixScrollPane.repaint();
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            // CHECK IF CORRECT:
            else if (Pattern.matches("save_analysis_\\d*.*", (e
                    .getActionCommand()))) {

                String command = (e.getActionCommand());
                String[] sp = command.split("_");
                int dIdx = (new Integer(sp[3])).intValue();
                int lIdx = (new Integer(sp[4])).intValue();

                utilEngine.writeGPA(utilEngine.writeAnalysisResults_BioLayout(((Dataset) utilEngine.getDatasetList()
                        .get(dIdx)).getAnalysis(lIdx))); // TO DO.
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            // CHECK IF CORRECT:
            else if (Pattern.matches("save_bicluster_results_\\d*.*",
                    (e.getActionCommand()))) {

                String command = (e.getActionCommand());
                String[] sp = command.split("_");
                int dIdx = (new Integer(sp[3])).intValue();
                int lIdx = (new Integer(sp[4])).intValue();

                utilEngine.writeBiclustersHuman(((Dataset) utilEngine.getDatasetList().get(dIdx))
                        .getBiclusters(lIdx));
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
			/*
			 * else if("load_bcs".equals(e.getActionCommand())) {
			 * 
			 * if(pre.discreteDataReady()) { LinkedList list = readBiclusters();
			 * if(null != list) { post.loadList(list, rawData); buildTree(); //
			 * build tree with results tree.setSelectionPath(preprocessedPath); }
			 * else JOptionPane.showMessageDialog(this, "An error occurred while
			 * loading the bicluster file"); } else
			 * JOptionPane.showMessageDialog(this, "Must load and preprocess
			 * data before loading bicluster file"); }
			 */

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (Pattern.matches("save_cluster_results_\\d*.*",
                    (e.getActionCommand()))) {

                String command = (e.getActionCommand());
                String[] sp = command.split("_");
                int dIdx = (new Integer(sp[3])).intValue();
                int lIdx = (new Integer(sp[4])).intValue();

                utilEngine.writeBiclustersHuman(((Dataset) utilEngine.getDatasetList().get(dIdx))
                        .getClusters(lIdx));
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (Pattern.matches("save_search_results_\\d*.*",
                    (e.getActionCommand()))) {

                String command = (e.getActionCommand());

                String[] sp = command.split("_");
                int dIdx = (new Integer(sp[3])).intValue();
                int lIdx = (new Integer(sp[4])).intValue();
                utilEngine.writeBiclustersHuman(((Dataset) utilEngine.getDatasetList().get(dIdx))
                        .getSearch(lIdx));
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (Pattern.matches("save_filter_results_\\d*.*",
                    (e.getActionCommand()))) {

                String command = (e.getActionCommand());
                String[] sp = command.split("_");
                int dIdx = (new Integer(sp[3])).intValue();
                int lIdx = (new Integer(sp[4])).intValue();

                utilEngine.writeBiclustersHuman(((Dataset) utilEngine.getDatasetList().get(dIdx))
                        .getFilters(lIdx));
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (Pattern.matches("show_info_\\d*.*", (e
                    .getActionCommand()))) {

                String command = (e.getActionCommand());

                int idx = command.lastIndexOf("_");
                String[] sp = command.split("_");
                int dIdx = (new Integer(sp[2])).intValue();
                int lIdx = (new Integer(sp[3])).intValue();

                String rit = ((Dataset) utilEngine.getDatasetList().get(dIdx))
                        .getPreprocessOptions(lIdx).toString();
                RunInfo info = new RunInfo(this, "L."
                        + command.substring(idx + 1), rit);
                info.makeWindow();
            }

			/* -=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=- */
            else if (UtilConstants.MAIN_LOAD_TWO_INPUT_FILES.equals(e
                    .getActionCommand())) {
                LoadData ldw = new LoadData(this);
                ldw.makeWindow();
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_RUN_BIMAX.equals(e.getActionCommand())) {

                // at least one preprocessed dataset should be ready ...
                if (!utilEngine.getCurrentDataset().preprocessComplete())
                    JOptionPane.showMessageDialog(this,
                            "Load and discretize data before proceeding",
                            "Error", JOptionPane.ERROR_MESSAGE);

                else {

                    utilEngine.setRunMachineBiMax(new RunMachine_BiMax(this));

                    ArgumentsBiMax bmxa = new ArgumentsBiMax();
                    float[][] data = utilEngine.getCurrentDataset().getPreData();
                    int[][] discreteData = utilEngine.getCurrentDataset().getDiscrData();
                    System.out.println("Sizes of the matrices: " + data.length + ", " + data[0].length + ", " + discreteData.length + ", " + discreteData[0].length);
                    // correct the discrete matrix into binary matrix (needed
                    // for BiMax)
                    for (int i = 0; i < discreteData.length; i++)
                        for (int j = 0; j < discreteData[0].length; j++)
                            if (discreteData[i][j] == -1)
                                discreteData[i][j] = 1;

                    bmxa.setBinaryData(discreteData);
                    if (data.length == discreteData.length)
                        bmxa.setExtended(false);
                    else
                        bmxa.setExtended(true);
                    bmxa.setGeneNumber(data.length);
                    bmxa.setChipNumber(data[0].length);

                    bmxa.setDatasetIdx(utilEngine.getCurrentDatasetIdx());
                    bmxa.setPreprocessOptions(utilEngine.getPreprocessOptions());

                    RunDialog_BiMax rbmxd = new RunDialog_BiMax(this, bmxa);
                    rbmxd.makeWindow();
                }
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_RUN_CC.equals(e.getActionCommand())) {

                utilEngine.setRunMachine_CC(new RunMachine_CC(this));

                // prepare the ChengChurchArguments ...
                ArgumentsCC cca = new ArgumentsCC();

                float[][] dd = utilEngine.getCurrentDataset().getPreData(); // pre.getOriginalData();
                double[] data = new double[dd.length * dd[0].length];
                for (int i = 0; i < dd.length; i++)
                    for (int j = 0; j < dd[0].length; j++)
                        data[i * dd[0].length + j] = (double) dd[i][j];
                cca.setData(data);
                cca.setGeneNumber(dd.length);
                cca.setChipNumber(dd[0].length);

                // INTERNAL, not needed, if (randomize == 0)
                cca.setRandomize(0);
                cca.setP_rand_1(0.5);
                cca.setP_rand_2(0.5);
                cca.setP_rand_3(0.5);
                // compute the logarithm, from the BicAT, sonst == 0:
                cca.setLogarithm(0);

                cca.setOutputFile("output.cc");

                cca.setDatasetIdx(utilEngine.getCurrentDatasetIdx());
                cca.setPreprocessOptions(utilEngine.getPreprocessOptions());

                // Get User-Defined parameters (and run things):

                RunDialog_CC rccd = new RunDialog_CC(this, cca);
                rccd.makeWindow();
                // }
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_RUN_ISA.equals(e.getActionCommand())) {

                utilEngine.setRunMachine_ISA(new RunMachine_ISA(this));

                ArgumentsISA isaa = new ArgumentsISA();

                float[][] dd = utilEngine.getCurrentDataset().getPreData(); // pre.getOriginalData();
                double[] data = new double[dd.length * dd[0].length];
                for (int i = 0; i < dd.length; i++)
                    for (int j = 0; j < dd[0].length; j++)
                        data[i * dd[0].length + j] = (double) dd[i][j];
                isaa.setData(data);
                isaa.setGeneNumber(dd.length); // 100
                isaa.setChipNumber(dd[0].length); // 50

                // internal:
                // isaa.setMaxSize(100); // max_size == n_fix !
                isaa.setLogarithm(0);

                isaa.setOutputFile("output.isa");

                isaa.setDatasetIdx(utilEngine.getCurrentDatasetIdx());
                isaa.setPreprocessOptions(utilEngine.getPreprocessOptions());

                RunDialog_ISA risad = new RunDialog_ISA(this, isaa);
                risad.makeWindow();
            }
            // }

			/* -=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_RUN_XMOTIF.equals(e.getActionCommand())) {

                utilEngine.setRunMachine_XMotifs(new RunMachine_XMotifs(this));

                ArgumentsXMotifs xma = new ArgumentsXMotifs();

                float[][] dd = utilEngine.getCurrentDataset().getPreData(); // pre.getOriginalData();
                double[] data = new double[dd.length * dd[0].length];
                for (int i = 0; i < dd.length; i++)
                    for (int j = 0; j < dd[0].length; j++)
                        data[i * dd[0].length + j] = (double) dd[i][j];
                xma.setData(data);
                xma.setGeneNumber(dd.length); // 100
                xma.setChipNumber(dd[0].length); // 50

                xma.setLogarithm(0);
                xma.setOutputFile("output.xMotifs");

                xma.setDatasetIdx(utilEngine.getCurrentDatasetIdx());
                xma.setPreprocessOptions(utilEngine.getPreprocessOptions());

                RunDialog_XMotifs rxmd = new RunDialog_XMotifs(this, xma);
                rxmd.makeWindow();
                // }
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_RUN_OPSM.equals(e.getActionCommand())) {

                utilEngine.setRunMachineOPSM(new RunMachine_OPSM(this));

                ArgumentsOPSM OPSMa = new ArgumentsOPSM();
                float[][] dd = utilEngine.getCurrentDataset().getPreData();
                double[] data = new double[dd.length * dd[0].length];
                for (int i = 0; i < dd.length; i++)
                    for (int j = 0; j < dd[0].length; j++)
                        data[i * dd[0].length + j] = (double) dd[i][j];
                OPSMa.setData(data);
                OPSMa.setMyData(dd);

                OPSMa.setDatasetIdx(utilEngine.getCurrentDatasetIdx());
                OPSMa.setPreprocessOptions(utilEngine.getPreprocessOptions());

                RunDialog_OPSM rOPSMd = new RunDialog_OPSM(this, OPSMa);
                rOPSMd.makeWindow();
                // }
            }
			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_RUN_HCL.equals(e.getActionCommand())) {

                utilEngine.setRunMachineHCL(new RunMachine_HCL(this));

                ArgumentsHCL hcla = new ArgumentsHCL();
                float[][] dd = utilEngine.getCurrentDataset().getPreData();
                double[] data = new double[dd.length * dd[0].length];
                for (int i = 0; i < dd.length; i++)
                    for (int j = 0; j < dd[0].length; j++)
                        data[i * dd[0].length + j] = (double) dd[i][j];
                hcla.setData(data);
                hcla.setMyData(dd);

                hcla.setDatasetIdx(utilEngine.getCurrentDatasetIdx());
                hcla.setPreprocessOptions(utilEngine.getPreprocessOptions());

                RunDialog_HCL rhcld = new RunDialog_HCL(this, hcla);
                rhcld.makeWindow();
                // }
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_RUN_KMEANS.equals(e.getActionCommand())) {

                utilEngine.setRunMachineKMeans(new RunMachine_KMeans(this));

                ArgumentsKMeans kma = new ArgumentsKMeans();
                float[][] dd = utilEngine.getCurrentDataset().getPreData();
                double[] data = new double[dd.length * dd[0].length];
                for (int i = 0; i < dd.length; i++)
                    for (int j = 0; j < dd[0].length; j++)
                        data[i * dd[0].length + j] = (double) dd[i][j];
                kma.setData(data);
                kma.setMyData(dd);

                kma.setDatasetIdx(utilEngine.getCurrentDatasetIdx());
                kma.setPreprocessOptions(utilEngine.getPreprocessOptions());

                RunDialog_KMeans rkmd = new RunDialog_KMeans(this, kma);
                rkmd.makeWindow();
                // }
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
			/*
			 * else if("save_preprocessed".equals(e.getActionCommand())) {
			 * 
			 * if(preprocessComplete) ; //
			 * writePreprocessed(pre.getPreprocessedData()); else
			 * JOptionPane.showMessageDialog(this, "Must preprocess data
			 * first"); }
			 */

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if ("save_selected_bc".equals(e.getActionCommand())) {

                Object maybeBC = ((DefaultMutableTreeNode) tree
                        .getSelectionPath().getLastPathComponent())
                        .getUserObject();
                try {

                    if ("bicat.biclustering.Bicluster".equals(maybeBC
                            .getClass().getName())) {
                        // System.out.println("is a BC");
                        BiclusterInfo info = new BiclusterInfo(this,
                                (bicat.biclustering.Bicluster) maybeBC);
                        info.makeWindow();
                    }
                } catch (Exception wee) {
                    wee.printStackTrace();
                }
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
			/*
			 * else if("export...".equals(e.getActionCommand())) {
			 * System.out.println("To be implemented
			 * ("+e.getActionCommand()+")!"); }
			 */

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if ("preprocess".equals(e.getActionCommand())) {

                if (MethodConstants.debug)
                    System.out.println("D: dataset size is = "
                            + utilEngine.getDatasetList().size());

                if (utilEngine.getDatasetList().isEmpty() || !utilEngine.getPre().dataReady())
                    JOptionPane.showMessageDialog(this,
                            "Load input data first before proceeding");

                else {
                    PreprocessData pdw = new PreprocessData(this);
                    pdw.makeWindow();
                }
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_SEARCH_BICLUSTERS.equals(e
                    .getActionCommand())) {

                if (utilEngine.anyValidListAvailable()) {
                    Search search = new Search(this);
                    search.makeWindow();
                } else
                    JOptionPane.showMessageDialog(this,
                            "Perform biclustering before Searching!");
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_FILTER_BICLUSTERS.equals(e
                    .getActionCommand())) {

                if (utilEngine.anyValidListAvailable()) {
                    Filter filter = new Filter(this);
                    filter.makeWindow();
                } else
                    JOptionPane.showMessageDialog(this,
                            "Perform biclustering before Filtering!");
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_GENE_PAIR_ANALYSIS.equals(e
                    .getActionCommand())) {

                if (utilEngine.anyValidListAvailable()) {
                    GenePairAnalysis gpa = new GenePairAnalysis(this);
                    gpa.makeWindow();
                } else
                    JOptionPane.showMessageDialog(this,
                            "Perform biclustering before Gene-Pair Analysis!");
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if ("info_bc".equals(e.getActionCommand())) {

                Object maybeBC = ((DefaultMutableTreeNode) tree
                        .getSelectionPath().getLastPathComponent())
                        .getUserObject();
                try {
                    if ("bica_gui.Bicluster".equals(maybeBC.getClass()
                            .getName())) {
                        BiclusterInfo info = new BiclusterInfo(this,
                                (bicat.biclustering.Bicluster) maybeBC);
                        info.makeWindow();
                    }
                } catch (Exception wee) {
                    wee.printStackTrace();
                }
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_INFO_HELP.equals(e.getActionCommand())) {
                System.out
                        .println("To be implemented (" + e.getActionCommand());
                System.out
                        .println(")! => check the 'Readme_Help_BINAr.txt' file in the installation directory!.");
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_INFO_LICENSE.equals(e
                    .getActionCommand())) {
                System.out.println("To be implemented (" + e.getActionCommand()
                        + ")!");
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            else if (UtilConstants.MAIN_INFO_ABOUT.equals(e.getActionCommand())) {
                About aw = new About(this);
                aw.makeWindow();
            }

			/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */
            // Popup Menu:
            else if ("deleteNode".equals(e.getActionCommand())) {

                TreePath currentSelection = tree.getSelectionPath();
                if (currentSelection != null) {

                    DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection
                            .getLastPathComponent());

                    if ((currentNode.toString()).equals("Data Display")
                            || (currentNode.toString()).equals("Original data")
                            || (currentNode.toString())
                            .equals("Preprocessed data")
                            || (currentNode.toString())
                            .equals("Discretized data")) {
                    } else {

                        MutableTreeNode parent = (MutableTreeNode) (currentNode
                                .getParent());
                        if (parent != null) {

                            treeModel.removeNodeFromParent(currentNode);

                            // (correct) Management:
                            if (MethodConstants.debug)
                                System.out
                                        .println("BicatGui.actionPerformed().deleteNode.ACTION,");
                            if (MethodConstants.debug)
                                System.out.println("Selected node is: "
                                        + currentNode.toString());

                            // **** Dataset?
                            if (BicatUtil.isDataset(currentNode.toString())) { // delete
                                // all
                                // structures
                                // associated
                                // with
                                // the
                                // Dataset;

                                int idx = BicatUtil.getDatasetID(currentNode
                                        .toString());
                                if (MethodConstants.debug)
                                    System.out
                                            .println("Debug: Removing dataset idxed "
                                                    + idx);
                                utilEngine.getDatasetList().remove(idx);

                                // remove the dataMatrices lists ...
                                utilEngine.setRawData(null);
                                utilEngine.setRawData_for_GP(null);

                                // matrix Labels (hdr columns/rows)
                                labelMenu = new JMenu();

                                // TO DO (as of 21.03.2005): if there are other
                                // datasets -> get the data visualized,
                                if (utilEngine.getDatasetList().size() > 0) {
                                    // UPDATE THE PP & GP tab (visualize the
                                    // last dataset : update also the LABEL
                                    // (MATRIX VIEW - DataSet X))

                                    // TO DO: IMPLEMENT !!!

                                } else {
                                    // OTHERWISE, clean the visualization
                                    // Panes...

                                    if (MethodConstants.debug)
                                        System.out
                                                .println("No more datasets loaded (clean the Panes) ...");

                                    // temporary, only 1 dataset at a time is
                                    // allowed.
                                    // loadMenuItem.setEnabled(true);

                                    cleanPicturePane();
                                    cleanGraphicPane();
                                    cleanAnalysisPane();
                                }
                            }

                            // *** Collection of BC lists?
                            else if (BicatUtil.isCollectionOfLists(currentNode
                                    .toString())) {

                                if (MethodConstants.debug)
                                    System.out
                                            .println("Delete the list of Collection");

                                // invalid all the BC lists of the collection
                                int[] idxs = BicatUtil
                                        .getListOfDatasetIdx(currentSelection);
                                Dataset BcR = (Dataset) utilEngine.getDatasetList()
                                        .get(idxs[1]);
                                for (int i = 0; i < (BcR.getBCsList(idxs[0]))
                                        .size(); i++)
                                    BcR.invalidList(idxs[0], i);
                            }

                            // *** List of (B)Cs?
                            else if (BicatUtil.isListOfBCs(currentNode
                                    .toString())) {

                                if (MethodConstants.debug)
                                    System.out
                                            .println("Delete the list of BCs");

                                int[] idxs = BicatUtil
                                        .getListAndIdxPath(currentSelection); // tree.getSelectionPath());
                                ((Dataset) utilEngine.getDatasetList().get(idxs[4]))
                                        .invalidList(idxs[0], idxs[3]);

                                // update the menu's (for save, filter, search,
                                // gpa, ...)
                                switch (idxs[0]) {
                                    case 0:
                                        updateBiclusterMenu();
                                        break;
                                    case 1:
                                        updateSearchMenu();
                                        break;
                                    case 2:
                                        updateFilterMenu();
                                        break;
                                    case 3:
                                        updateClusterMenu();
                                        break;
                                    default:
                                        updateAnalysisMenu();
                                        break;
                                }

                            }

                            // *** (Bi)cluster?
                            else if (BicatUtil.isBC(currentNode.toString())) {

                                if (MethodConstants.debug)
                                    System.out.println("Delete a BC");
                                // get the list and remove the bc from this list
                                int[] idxs = BicatUtil
                                        .getListAndIdxPath(currentSelection);
                                int id = BicatUtil.getBcId(currentSelection
                                        .getLastPathComponent().toString());
                                Dataset BcR = ((Dataset) utilEngine.getDatasetList()
                                        .get(idxs[4]));
                                BcR.removeBicluster(BcR.getBCList(idxs[0],
                                        idxs[3]), id);
                            } else if (MethodConstants.debug)
                                System.out.println("Should not happen!");

                            // buildTree();
                        }
                    }
                }
            } else
                System.out
                        .println("BicatGui.actionPerformed(), Unknown event: "
                                + e.paramString());
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    // ************************************************************************
    // //
    // * * //
    // * GUI-specific: windows, trees, panes... * //
    // * * //
    // ************************************************************************
    // //

    void cleanPicturePane() {
        // picture panel
        pp.removeAll();

        pp = new PicturePane(utilEngine.getPre());
        pp.addMouseListener(this);
        pp.setBackground(Color.WHITE);
        pp.setOpaque(true);

        matrixScrollPane.removeAll();
        matrixScrollPane = new JScrollPane(pp);

        matrixViewPane.removeAll();
        matrixViewPane.add(matrixInfoLabel, BorderLayout.SOUTH);
        matrixViewPane.add(matrixScrollPane, BorderLayout.CENTER);

        matrixInfoLabel
                .setText(" Click on a sample to get gene and chip names");
    }

    void cleanGraphicPane() {
        // graph panel
        gp.removeAll();

        gp = new GraphicPane();
        gp.addMouseListener(this);
        gp.setBackground(Color.WHITE);
        gp.setOpaque(true);

        graphScrollPane.removeAll();
        graphScrollPane = new JScrollPane(gp);

        graphViewPane.removeAll();
        graphViewPane.add(graphInfoLabel, BorderLayout.SOUTH);
        graphViewPane.add(graphScrollPane, BorderLayout.CENTER);

        graphInfoLabel.setText("");
    }

    void cleanAnalysisPane() {
        // analysis panel : to visualize the (various) analysis results
        op.removeAll();

        op = new AnalysisPane();
        op.addMouseListener(this);
        op.setBackground(Color.WHITE);
        op.setOpaque(true);

        otherScrollPane.removeAll();
        otherScrollPane = new JScrollPane(op);

        otherViewPane.removeAll();
        otherViewPane.add(otherInfoLabel, BorderLayout.SOUTH);
        otherViewPane.add(otherScrollPane, BorderLayout.CENTER);

        otherInfoLabel.setText("");
    }

    // ===========================================================================

    /**
     * Builds a tree containing the hierarchy for datasets: runs, search, filter
     * and analysis results.
     * <p>
     * Creates the tree that is visible in the lefthand part of the splitpane in
     * the GUI.
     */
    public void buildTree() {

        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode matrix = null;
        DefaultMutableTreeNode dataset = null;

        // re-set
        top.removeAllChildren();

        for (int a = 0; a < utilEngine.getDatasetList().size(); a++) {

            dataset = new DefaultMutableTreeNode("DataSet " + a);
            top.add(dataset);

            // For each dataset, get its corresponding data: BC lists, Filter
            // lists, Analysis lists...

            // ========================================================================
            category = new DefaultMutableTreeNode("Data Display"); // Preprocessing
            // steps");
            dataset.add(category);

            matrix = new DefaultMutableTreeNode("Original data");
            category.add(matrix);

            Object[] pathList = new Object[4];
            pathList[0] = top;
            pathList[1] = dataset;
            pathList[2] = category;
            pathList[3] = matrix;
            originalPath = new TreePath(pathList);

            matrix = new DefaultMutableTreeNode("Preprocessed data");
            category.add(matrix);
            pathList[3] = matrix;
            preprocessedPath = new TreePath(pathList);

            matrix = new DefaultMutableTreeNode("Discretized data");
            category.add(matrix);

            // **************************************** //
            // todo_eventualno_1(); // ... add icons to the nodes of the tree.

            // **************************************** //
            Dataset BcR = (Dataset) utilEngine.getDatasetList().get(a);

            if (!BcR.biclustersAvailable()
                    && !BcR.clustersAvailable())

                continue; // go to the next dataset, if any.

            else {

                /** display the hierarchy: */

                if (BcR.validBiclustersAvailable()) {
                    if (MethodConstants.debug)
                        System.out
                                .println("Building subtree with biclustering results...");
                    buildSubTree(dataset, BcR.getBiclusters(), BcR
                                    .getBiclustersNames(), BcR.getBiclustersValid(),
                            "Bicluster results");
                }

                if (BcR.validClustersAvailable()) {
                    if (MethodConstants.debug)
                        System.out
                                .println("Building subtree with clustering results...");
                    buildSubTree(dataset, BcR.getClusters(), BcR
                                    .getClustersNames(), BcR.getClustersValid(),
                            "Cluster results");
                }

                if (BcR.validSearchesAvailable()) {
                    if (MethodConstants.debug)
                        System.out
                                .println("Building subtree with search results...");
                    buildSubTree(dataset, BcR.getSearch(),
                            BcR.getSearchNames(), BcR.getSearchValid(),
                            "Search results");
                }

                if (BcR.validFiltersAvailable()) {
                    if (MethodConstants.debug)
                        System.out
                                .println("Building subtree with filtering results...");
                    buildSubTree(dataset, BcR.getFilters(), BcR
                                    .getFiltersNames(), BcR.getFiltersValid(),
                            "Filter results");
                }

                buildAnalysisSubTree(dataset, BcR);
            }
        }

        // *************************************** //
        listScrollPane.validate();
        listScrollPane.repaint();
        treeModel.reload();
    }

    // ===========================================================================
    public void buildSubTree(DefaultMutableTreeNode top,
                             LinkedList biclusterList, Vector biclusterListNames,
                             Vector biclusterListValid, String label) {

        DefaultMutableTreeNode category = new DefaultMutableTreeNode(label);
        top.add(category);

        for (int i = 0; i < biclusterList.size(); i++) {

            if (((Boolean) biclusterListValid.get(i)).booleanValue()) {
                DefaultMutableTreeNode lb = new DefaultMutableTreeNode(
                        biclusterListNames.get(i));
                LinkedList bcs = (LinkedList) biclusterList.get(i);

                for (int j = 0; j < bcs.size(); j++) {
                    lb.add(new DefaultMutableTreeNode(bcs.get(j)));
                    // tree.addMouseListener(popupListener);
                    // tree.addMouseListener(createPopupMenu_new());
                }
                category.add(lb);
            }
        }
        tree.treeDidChange();
    }

    // ===========================================================================
    //
    private void buildAnalysisSubTree(DefaultMutableTreeNode top,
                                      Dataset BcR) {
        if (BcR.analysisResultsAvailable()) {

            Vector analysisItems = new Vector();
            if (MethodConstants.debug)
                System.out.println("Building subtree with analysis results...");

            DefaultMutableTreeNode category = new DefaultMutableTreeNode(
                    "Analysis results");
            top.add(category);

            for (int j = 0; j < BcR.getAnalysis().size(); j++) {
                DefaultMutableTreeNode lb = new DefaultMutableTreeNode(BcR
                        .getAnalysisNames().get(j));
                // HashMap gp_scores = BcR.getAnalysis(j);
                analysisItems.add(lb);
                category.add(lb);
            }
            tree.treeDidChange();
        } else {
            // System.out.println("No analysis results available.");
        }
    }

    // ===========================================================================
    /**
	 * @uml.property  name="popupListener"
	 */
    private MouseListener popupListener;

    private void createPopupMenu() {

        JMenuItem menuItem;

        JPopupMenu popup = new JPopupMenu();

        menuItem = new JMenuItem("Delete entry");
        menuItem.addActionListener(this);
        menuItem.setActionCommand("deleteNode");
        popup.add(menuItem);

        // add MouseListener for this PopupMenu
        popupListener = new PopupListener(popup);

        tree.addMouseListener(popupListener);
    }

    /**
	 * @uml.property  name="matrixViewPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    JPanel matrixViewPane;

    /**
	 * @uml.property  name="graphViewPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    JPanel graphViewPane;

    /**
	 * @uml.property  name="otherViewPane"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    JPanel otherViewPane;

    // ===========================================================================

    /**
     * Default constructor, initializes many local values and creates most of
     * GUI
     */
    public BicatGui() throws Exception {
        // main window
        super("BBicAT");

        utilEngine = new UtilFunctionalities(this);
        top = new DefaultMutableTreeNode("Display");
        treeModel = new DefaultTreeModel(top);
        tree = new JTree(treeModel);
        tree.addTreeSelectionListener(this);

        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
        createPopupMenu();

        tree.setShowsRootHandles(true);
        listScrollPane = new JScrollPane(tree);
        buildTree();

        // ----
        JTextPane workflowPane = new JTextPane();
        workflowPane.setEditable(false);

        String wfString = "";
        wfString = wfString + "1. Load Data\n\n";
        wfString = wfString + "2. Preprocess Data\n\n";
        wfString = wfString + "3. Run Biclustering\n\n";
        wfString = wfString + "4. Data Analysis\n\n";
        wfString = wfString + "5. Export Results\n\n";

        workflowPane.setText(wfString);

        // ------------------------

        JTabbedPane tabbedPane1 = new JTabbedPane();
        tabbedPane1.setTabPlacement(JTabbedPane.BOTTOM);
        tabbedPane1.addTab("Display view", listScrollPane);
        tabbedPane1.addTab("Workflow", workflowPane);

        // ------------------------

        // picture panel
        pp = new PicturePane(utilEngine.getPre());
        pp.addMouseListener(this);
        pp.setBackground(Color.WHITE);
        pp.setOpaque(true);
        matrixScrollPane = new JScrollPane(pp);

        // graph panel
        gp = new GraphicPane();
        gp.addMouseListener(this);
        gp.setBackground(Color.WHITE);
        gp.setOpaque(true);
        graphScrollPane = new JScrollPane(gp);

        // other panel : to visualize the (various) analysis results
        op = new AnalysisPane(this);
        op.addMouseListener(this);
        op.setBackground(Color.WHITE);
        op.setOpaque(true);
        otherScrollPane = new JScrollPane(op);

        // matrix view tab on the right side of the splitpane, contains picture
        // panel and infolabel
        matrixInfoLabel = new JLabel(" ", JLabel.CENTER);
        matrixViewPane = new JPanel(new BorderLayout());
        matrixViewPane.add(matrixInfoLabel, BorderLayout.SOUTH);
        matrixViewPane.add(matrixScrollPane, BorderLayout.CENTER);

        // graph view tab on the right side of the splitpane, contains graph
        // panel and infolabel
        graphInfoLabel = new JLabel(" ", JLabel.CENTER);
        graphViewPane = new JPanel(new BorderLayout());
        graphViewPane.add(graphInfoLabel, BorderLayout.SOUTH);
        graphViewPane.add(graphScrollPane, BorderLayout.CENTER);

        // Analysis view tab on the right side of the splitpane
        otherInfoLabel = new JLabel(" ", JLabel.CENTER);
        otherViewPane = new JPanel(new BorderLayout());
        otherViewPane.add(otherInfoLabel, BorderLayout.SOUTH);
        otherViewPane.add(otherScrollPane, BorderLayout.CENTER);

        // create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Matrix view", matrixViewPane);
        tabbedPane.addTab("Expression view", graphViewPane);
        tabbedPane.addTab("Analysis view", otherViewPane);

        // create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                tabbedPane1, tabbedPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250); // 200

        // set dimensions and location of the main window
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - 2 * inset, screenSize.height
                - 2 * inset);
        getContentPane().add(splitPane);

        setJMenuBar(createMenuBar());
//        String file = "C:/Users/tagi1_000/Desktop/NCI.xml";
//        engine = new PathwayAnalysis(file);
//
//        genes = engine.getGeneToPathways().keySet();
        parsedPathways = true;

    }

    // ===========================================================================

    /**
     * Creates top bar of pull down menus with all menu items.
     * <p>
     * Requires a <code>BicaGUI</code> and a <code>PicturePane</code> to add
     * as action listeners.
     *
     * @return the complete menu bar
     */
    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        JMenu menu, subMenu;
        JMenuItem menuItem;
        JCheckBoxMenuItem cbMenuItem;
        JRadioButtonMenuItem rbMenuItem;
        ButtonGroup group;

        stop_icon = new ImageIcon("images/realfrown.gif");

		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

        // create main menu...
        menu = new JMenu("File");
        // menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

        subMenu = new JMenu("Load... ");

        loadMenuItem = new JMenuItem("Expression Data");
        loadMenuItem.setActionCommand(UtilConstants.MAIN_LOAD_TWO_INPUT_FILES);
        loadMenuItem.addActionListener(this);
        loadMenuItem.setEnabled(true);
        subMenu.add(loadMenuItem);

        menu.add(subMenu);

        subMenu = new JMenu("Export..."); // for ex. -> XML, SVG (graph), PDF
        menu.add(subMenu);

        menuItem = new JMenuItem("Selected bicluster"); // HERE SHOULD BE A
        // CHOICE AVAILABLE,
        // which list of BCs
        menuItem.setActionCommand("save_selected_bc");
        menuItem.addActionListener(this);
        subMenu.add(menuItem);

        subMenu.addSeparator();

        saveBiclusterMenu = new JMenu("Biclustering results...");
        saveBiclusterMenu.setEnabled(false);
        subMenu.add(saveBiclusterMenu);

        saveClusterMenu = new JMenu("Clustering results...");
        saveClusterMenu.setEnabled(false);
        subMenu.add(saveClusterMenu);

        saveSearchMenu = new JMenu("Search results...");
        saveSearchMenu.setEnabled(false);
        subMenu.add(saveSearchMenu);

        saveFilterMenu = new JMenu("Filter results...");
        saveFilterMenu.setEnabled(false);
        subMenu.add(saveFilterMenu);

        saveAnalysisMenu = new JMenu("Gene Pair Analysis results...");
        saveAnalysisMenu.setEnabled(false);
        subMenu.add(saveAnalysisMenu);

        menu.addSeparator();

		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setActionCommand(UtilConstants.MAIN_QUIT);
        menuItem.addActionListener(this);
        menu.add(menuItem);

		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */
        menu = new JMenu("Preprocess");
        menuBar.add(menu);

        // perform an automatized preprocessing of the matrix:

        menuItem = new JMenuItem("Preprocess data"); // preprocess in one
        // step
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setActionCommand("preprocess");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu.addSeparator();

        preprocessOptionsMenu = new JMenu("View preprocess options...");
        // preprocess in one step
        preprocessOptionsMenu.setEnabled(false);
        menu.add(preprocessOptionsMenu);

		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */
        menu = new JMenu("Run");
        menuBar.add(menu);

        menuItem = new JMenuItem("Biclustering BiMax");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setActionCommand(UtilConstants.MAIN_RUN_BIMAX);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Biclustering CC");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setActionCommand(UtilConstants.MAIN_RUN_CC);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Biclustering ISA");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setActionCommand(UtilConstants.MAIN_RUN_ISA);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Biclustering xMotifs");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setActionCommand(UtilConstants.MAIN_RUN_XMOTIF);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Biclustering OPSM");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setActionCommand(UtilConstants.MAIN_RUN_OPSM);
        menuItem.addActionListener(this);
        menuItem.setEnabled(true);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Clustering HCL");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setActionCommand(UtilConstants.MAIN_RUN_HCL);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Clustering K-means");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setActionCommand(UtilConstants.MAIN_RUN_KMEANS);
        menuItem.addActionListener(this);
        menu.add(menuItem);

		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

        menu = new JMenu("Tools");
        menu.setMnemonic(KeyEvent.VK_P);
        menuBar.add(menu);

		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= (10.07.2004) */

        menuItem = new JMenuItem("Search biclusters ");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setActionCommand(UtilConstants.MAIN_SEARCH_BICLUSTERS);
        menuItem.addActionListener(this);
        menu.add(menuItem);

		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

        menuItem = new JMenuItem("Filter biclusters ");
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.setActionCommand(UtilConstants.MAIN_FILTER_BICLUSTERS);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Gene Pair Analysis");
        menuItem.setActionCommand(UtilConstants.MAIN_GENE_PAIR_ANALYSIS);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // create display options menu...
        menu = new JMenu("View");
        menuBar.add(menu);

        subMenu = new JMenu("Zoom...");
        subMenu.setMnemonic(KeyEvent.VK_Z);
        menu.add(subMenu);

        group = new ButtonGroup();

        rbMenuItem = new JRadioButtonMenuItem("25 %");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_ZOOM_25);
        rbMenuItem.addActionListener(pp);
        rbMenuItem.addActionListener(gp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("50 %");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_ZOOM_50);
        rbMenuItem.addActionListener(pp);
        rbMenuItem.addActionListener(gp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("75 %");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_ZOOM_75);
        rbMenuItem.addActionListener(pp);
        rbMenuItem.addActionListener(gp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("100 %");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_ZOOM_100);
        rbMenuItem.setSelected(true);
        rbMenuItem.addActionListener(pp);
        rbMenuItem.addActionListener(gp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("150 %");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_ZOOM_150);
        rbMenuItem.addActionListener(pp);
        rbMenuItem.addActionListener(gp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("200 %");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_ZOOM_200);
        rbMenuItem.addActionListener(pp);
        rbMenuItem.addActionListener(gp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        menu.addSeparator();

        subMenu = new JMenu("Limit...");
        subMenu.setMnemonic(KeyEvent.VK_L);
        menu.add(subMenu);

        group = new ButtonGroup();

        rbMenuItem = new JRadioButtonMenuItem("100 genes");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_LIMIT_100);
        rbMenuItem.addActionListener(pp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("500 genes");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_LIMIT_500);
        rbMenuItem.setSelected(true);
        rbMenuItem.addActionListener(pp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("1000 genes");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_LIMIT_1000);
        rbMenuItem.addActionListener(pp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("5000 genes");
        rbMenuItem.setActionCommand(UtilConstants.PICTUREPANE_LIMIT_5000);
        rbMenuItem.addActionListener(pp);
        group.add(rbMenuItem);
        subMenu.add(rbMenuItem);

        menu = new JMenu("About");
        menuBar.add(menu);

        menuItem = new JMenuItem("About");
        menuItem.setActionCommand("info_about");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //////////////////////////////////////////////////////////////////////
//        menu = new JMenu("Biological Post-Analysis");
//        menuBar.add(menu);
//
//        menuItem = new JMenuItem("Pathway Analysis");
//        menuItem.setActionCommand("Pathway_Analysis");
//        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        menuItem = new JMenuItem("Translation of genes");
//        menuItem.setActionCommand("Translation");
//        menuItem.addActionListener(this);
//        menu.add(menuItem);

        //////////////////////////////////////////////////////////////////////

        // Adding the Bayesian statistic computing tab
//        menu = new JMenu("Bayesian");
//        menuBar.add(menu);
//
//        menuItem = new JMenuItem("Assess bi-clusters with Bayesian statistics");
//        menuItem.setActionCommand("Bayes_Simple");
//        menuItem.addActionListener(this);
//        menu.add(menuItem);
		/* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

        return menuBar;
    }

    // ===========================================================================

    /**
     * Alerts the scroll pane to a change in the matrix size, causing the scroll
     * bars to adjust.
     */
    public void readjustPictureSize() {
        pp.setMinimumSize(new Dimension(0, 0));
        pp.setPreferredSize(pp.getPictureSize());
        matrixScrollPane.setMinimumSize(new Dimension(0, 0));
        matrixScrollPane.setPreferredSize(pp.getPictureSize());
        matrixScrollPane.revalidate();
    }

    // ===========================================================================

    /**
     * Alerts the graph panel to changes in geneList selection, causing a repaint.
     */
    public void refreshGraphicPanel() {

        Vector markedGeneList = pp.getMarkedGeneList();
        Vector markedChipList = pp.getMarkedChipList();
        Vector graphDataList = new Vector();

        for (int i = 0; i < markedGeneList.size(); i++)
            graphDataList.add(utilEngine.getRawData_for_GP()[((Integer) markedGeneList.get(i))
                    .intValue()]);
        // bis 27.01.2005:
        // graphDataList.add(rawData[((Integer)markedGeneList.get(i)).intValue()]);
        // graphDataList.add(pre.dataMatrix[((Integer)markedGeneList.get(i)).intValue()]);
        gp.setGraphDataList(graphDataList, markedGeneList, markedChipList);

        gp.repaint();

        // bis 27.01.2005: gp.updateGraphic();
        gp.updateGraphic(new double[]{2.0}, true);
    }

    /**
     * Alerts the graph panel to changes in gene selection, causing a repaint.
     */
    public void refreshGraphicPanel(double[] dThr) {


        Vector markedGeneList = pp.getMarkedGeneList();
        Vector markedChipList = pp.getMarkedChipList();
        Vector graphDataList = new Vector();

        for (int i = 0; i < markedGeneList.size(); i++)
            graphDataList.add(utilEngine.getRawData_for_GP()[((Integer) markedGeneList.get(i))
                    .intValue()]);
        // bis 27.01.2005:
        // graphDataList.add(rawData[((Integer)markedGeneList.get(i)).intValue()]);
        // graphDataList.add(pre.dataMatrix[((Integer)markedGeneList.get(i)).intValue()]);
        gp.setGraphDataList(graphDataList, markedGeneList, markedChipList);

        gp.repaint();
        // bis 27.01.2005: gp.updateGraphic();
        gp.updateGraphic(dThr, true);
    }

    // ===========================================================================

    /**
     * Alerts the graph panel to changes in gene selection, causing a repaint.
     */
    public void refreshAnalysisPanel() {
        op.repaint();
        op.revalidate();
        readjustPictureSize();
    }

    public void refreshAnalysisPanel(int d, int x) {
        op.setTable(((Dataset) utilEngine.getDatasetList().get(d)).getAnalysis(x));
        op.repaint();
        op.revalidate();
        readjustPictureSize();
    }

    // ************************************************************************
    // //
    // * * //
    // * Dynamic Menus management * //
    // * * //
    // ************************************************************************
    // //

    /**
     * Update the Export Biclustering Results... menu list.
     */
    public void updateBiclusterMenu() {

        saveBiclusterMenu.removeAll();

        saveBiclusterMenu.setEnabled(true);
        for (int i = 0; i < utilEngine.getDatasetList().size(); i++) {
            JMenuItem mi = new JMenuItem("Dataset " + i);
            saveBiclusterMenu.add(mi);

            Vector bNames = ((Dataset) utilEngine.getDatasetList().get(i)).getBiclustersNames();
            Vector bValid = ((Dataset) utilEngine.getDatasetList().get(i)).getBiclustersValid();
            for (int j = 0; j < bNames.size(); j++) {

                if (((Boolean) bValid.get(j)).booleanValue()) {
                    mi = new JMenuItem("\"" + bNames.get(j) + "\"");
                    mi
                            .setActionCommand("save_bicluster_results_" + i
                                    + "_" + j);
                    mi.addActionListener(this);
                    saveBiclusterMenu.add(mi);
                }
            }
        }
    }

    /**
     * Update the Export Clustering Results... menu list.
     */
    public void updateClusterMenu() {

        saveClusterMenu.removeAll();

        saveClusterMenu.setEnabled(true);
        for (int i = 0; i < utilEngine.getDatasetList().size(); i++) {
            JMenuItem mi = new JMenuItem("Dataset " + i);
            saveClusterMenu.add(mi);

            Vector cNames = ((Dataset) utilEngine.getDatasetList().get(i)).getClustersNames();
            Vector cValid = ((Dataset) utilEngine.getDatasetList().get(i)).getClustersValid();
            for (int j = 0; j < cNames.size(); j++) {

                if (((Boolean) cValid.get(j)).booleanValue()) {
                    mi = new JMenuItem("\"" + cNames.get(j) + "\"");
                    mi.setActionCommand("save_cluster_results_" + i + "_" + j);
                    mi.addActionListener(this);
                    saveClusterMenu.add(mi);
                }
            }
        }
    }

    /**
     * Update the Export Filter Results... menu list.
     */
    public void updateFilterMenu() {

        saveFilterMenu.removeAll();

        saveFilterMenu.setEnabled(true);
        for (int i = 0; i < utilEngine.getDatasetList().size(); i++) {
            JMenuItem mi = new JMenuItem("Dataset " + i);
            saveFilterMenu.add(mi);

            Vector fNames = ((Dataset) utilEngine.getDatasetList().get(i)).getFiltersNames();
            Vector fValid = ((Dataset) utilEngine.getDatasetList().get(i)).getFiltersValid();
            for (int j = 0; j < fNames.size(); j++) {
                if (((Boolean) fValid.get(j)).booleanValue()) {
                    mi = new JMenuItem("\"" + fNames.get(j) + "\"");
                    mi.setActionCommand("save_filter_results_" + i + "_" + j);
                    mi.addActionListener(this);
                    saveFilterMenu.add(mi);
                }
            }
        }
    }

    /**
     * Update the Export Search Results... menu list.
     */
    public void updateSearchMenu() {

        saveSearchMenu.removeAll();

        saveSearchMenu.setEnabled(true);
        for (int i = 0; i < utilEngine.getDatasetList().size(); i++) {
            JMenuItem mi = new JMenuItem("Dataset " + i);
            saveSearchMenu.add(mi);

            Vector sNames = ((Dataset) utilEngine.getDatasetList().get(i)).getSearchNames();
            Vector sValid = ((Dataset) utilEngine.getDatasetList().get(i)).getSearchValid();
            for (int j = 0; j < sNames.size(); j++) {
                if (((Boolean) sValid.get(j)).booleanValue()) {
                    mi = new JMenuItem("\"" + sNames.get(j) + "\"");
                    mi.setActionCommand("save_search_results_" + i + "_" + j);
                    mi.addActionListener(this);
                    saveSearchMenu.add(mi);
                }
            }
        }
    }

    /**
     * Update the Export Analysis Results... menu list.
     */
    public void updateAnalysisMenu() {

        saveAnalysisMenu.removeAll();

        saveAnalysisMenu.setEnabled(true);
        for (int i = 0; i < utilEngine.getDatasetList().size(); i++) {
            JMenuItem mi = new JMenuItem("Dataset " + i);
            saveAnalysisMenu.add(mi);

            Vector aNames = ((Dataset) utilEngine.getDatasetList().get(i)).getAnalysisNames();
            Vector aValid = ((Dataset) utilEngine.getDatasetList().get(i)).getAnalysisValid();
            for (int j = 0; j < aNames.size(); j++) {
                if (((Boolean) aValid.get(j)).booleanValue()) {
                    mi = new JMenuItem("\"" + aNames.get(j) + "\"");
                    mi.setActionCommand("save_analysis_results_" + i + "_" + j);
                    mi.addActionListener(this);
                    saveAnalysisMenu.add(mi);
                }
            }
        }
    }

    // ===========================================================================

    /**
     * @todo get the Column/Row Headers done.
     */
    public void updateColumnHeadersMenu() {
        labels = utilEngine.getCurrentDataset().getHeaderColumnLabels();
    }

    // ===========================================================================
    public void updatePreprocessOptionsMenu() {

        preprocessOptionsMenu.removeAll();

        preprocessOptionsMenu.setEnabled(true);
        for (int i = 0; i < utilEngine.getDatasetList().size(); i++) {

            if (((Dataset) utilEngine.getDatasetList().get(i)).preprocessComplete()) {

                JMenuItem mi = new JMenuItem("Dataset " + i);
                preprocessOptionsMenu.add(mi);

                Vector bNames = ((Dataset) utilEngine.getDatasetList().get(i))
                        .getBiclustersNames();
                Vector bValid = ((Dataset) utilEngine.getDatasetList().get(i))
                        .getBiclustersValid();
                for (int j = 0; j < bNames.size(); j++) {
                    if (((Boolean) bValid.get(j)).booleanValue()) {
                        mi = new JMenuItem("for run \""
                                + bNames.get(j) + "\"");
                        mi.setActionCommand("show_info_" + i + "_" + j);
                        mi.addActionListener(this);
                        preprocessOptionsMenu.add(mi);
                    }
                }

                Vector cNames = ((Dataset) utilEngine.getDatasetList().get(i))
                        .getClustersNames();
                Vector cValid = ((Dataset) utilEngine.getDatasetList().get(i))
                        .getClustersValid();
                if (cNames.size() > 0)
                    preprocessOptionsMenu.addSeparator();
                for (int j = 0; j < cNames.size(); j++) {
                    if (((Boolean) cValid.get(j)).booleanValue()) {
                        mi = new JMenuItem("for run \""
                                + cNames.get(j) + "\"");
                        mi.setActionCommand("show_info_" + i + "_" + j);
                        mi.addActionListener(this);
                        preprocessOptionsMenu.add(mi);
                    }
                }

            }
        }
    }

    // ===========================================================================

    /**
     * Creates all basic objects and starts the GUI in a separate thread.
     * <p>
     * This is where the program is started.
     *
     * @param args not used
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        BicatGui frame = new BicatGui();

        Preprocessor pre = new Preprocessor(frame);
        Postprocessor post = new Postprocessor(frame);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

        frame.pp.setOwner(frame);
        frame.gp.setOwner(frame);

        frame.listScrollPane.setMinimumSize(new Dimension(0, 0));
        frame.listScrollPane.setPreferredSize(new Dimension(50, 200));

        frame.readjustPictureSize();

    }

    @Override
    public String toString() {
        return "BICAT GUI!";
    }

}
