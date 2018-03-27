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

import bicat.Constants.UtilConstants;
import lombok.Data;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
/**
 * TableSorter is a decorator for TableModels; adding sorting
 * functionality to a supplied TableModel. TableSorter does
 * not store or copy the data in its TableModel; instead it maintains
 * a map from the row indexes of the view to the row indexes of the
 * model. As requests are made of the sorter (like getValueAt(row, col))
 * they are passed to the underlying model after the row numbers
 * have been translated via the internal mapping array. This way,
 * the TableSorter appears to hold another copy of the table
 * with the rows in a different order.
 * <p/>
 * TableSorter registers itself as a listener to the underlying model,
 * just as the JTable itself would. Events recieved from the model
 * are examined, sometimes manipulated (typically widened), and then
 * passed on to the TableSorter's listeners (typically the JTable).
 * If a change to the model has invalidated the order of TableSorter's
 * rows, a note of this is made and the sorter will resort the
 * rows the next time a value is requested.
 * <p/>
 * When the tableHeader property is set, either by using the
 * setTableHeader() method or the two argument constructor, the
 * table header may be used as a complete UI for TableSorter.
 * The default renderer of the tableHeader is decorated with a renderer
 * that indicates the sorting status of each column. In addition,
 * a mouse listener is installed with the following behavior:
 * <ul>
 * <li>
 * Mouse-click: Clears the sorting status of all other columns
 * and advances the sorting status of that column through three
 * values: {NOT_SORTED, ASCENDING, DESCENDING} (then back to
 * NOT_SORTED again).
 * <li>
 * SHIFT-mouse-click: Clears the sorting status of all other columns
 * and cycles the sorting status of the column through the same
 * three values, in the opposite order: {NOT_SORTED, DESCENDING, ASCENDING}.
 * <li>
 * CONTROL-mouse-click and CONTROL-SHIFT-mouse-click: as above except
 * that the changes to the column do not cancel the statuses of columns
 * that are already sorting - giving a way to initiate a compound
 * sort.
 * </ul>
 * <p/>
 * This is a long overdue rewrite of a class of the same name that
 * first appeared in the swing table demos in 1997.
 *
 * @author Philip Milne
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
*/
/**
 * @return
 * @uml.property  name="mouseListener"
 */
/**
 * @return
 * @uml.property  name="tableModelListener"
 */
/**
 * @return
 * @uml.property  name="columnComparators"
 */
/**
 * @return
 * @uml.property  name="sortingColumns"
 */
/**
 * @param viewToModel
 * @uml.property  name="viewToModel"
 */
/**
 * @param modelToView
 * @uml.property  name="modelToView"
 */
/**
 * @param mouseListener
 * @uml.property  name="mouseListener"
 */
/**
 * @param tableModelListener
 * @uml.property  name="tableModelListener"
 */
/**
 * @param columnComparators
 * @uml.property  name="columnComparators"
 */
/**
 * TableSorter is a decorator for TableModels; adding sorting functionality to a supplied TableModel. TableSorter does not store or copy the data in its TableModel; instead it maintains a map from the row indexes of the view to the row indexes of the model. As requests are made of the sorter (like getValueAt(row, col)) they are passed to the underlying model after the row numbers have been translated via the internal mapping array. This way, the TableSorter appears to hold another copy of the table with the rows in a different order. <p/> TableSorter registers itself as a listener to the underlying model, just as the JTable itself would. Events recieved from the model are examined, sometimes manipulated (typically widened), and then passed on to the TableSorter's listeners (typically the JTable). If a change to the model has invalidated the order of TableSorter's rows, a note of this is made and the sorter will resort the rows the next time a value is requested. <p/> When the tableHeader property is set, either by using the setTableHeader() method or the two argument constructor, the table header may be used as a complete UI for TableSorter. The default renderer of the tableHeader is decorated with a renderer that indicates the sorting status of each column. In addition, a mouse listener is installed with the following behavior: <ul> <li> Mouse-click: Clears the sorting status of all other columns and advances the sorting status of that column through three values: {NOT_SORTED, ASCENDING, DESCENDING} (then back to NOT_SORTED again). <li> SHIFT-mouse-click: Clears the sorting status of all other columns and cycles the sorting status of the column through the same three values, in the opposite order: {NOT_SORTED, DESCENDING, ASCENDING}. <li> CONTROL-mouse-click and CONTROL-SHIFT-mouse-click: as above except that the changes to the column do not cancel the statuses of columns that are already sorting - giving a way to initiate a compound sort. </ul> <p/> This is a long overdue rewrite of a class of the same name that first appeared in the swing table demos in 1997.
 * @author  Philip Milne
 * @author  Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="mouseListener"
 */
/**
 * @return
 * @uml.property  name="tableModelListener"
 */
/**
 * @return
 * @uml.property  name="columnComparators"
 */
/**
 * @return
 * @uml.property  name="sortingColumns"
 */
/**
 * @param viewToModel
 * @uml.property  name="viewToModel"
 */
/**
 * @param modelToView
 * @uml.property  name="modelToView"
 */
/**
 * @param mouseListener
 * @uml.property  name="mouseListener"
 */
/**
 * @param tableModelListener
 * @uml.property  name="tableModelListener"
 */
/**
 * @param columnComparators
 * @uml.property  name="columnComparators"
 */
/**
 * @param sortingColumns
 * @uml.property  name="sortingColumns"
 */
@Data
public class TableSorter extends AbstractTableModel {

  /**
 * @uml.property  name="tableModel"
 * @uml.associationEnd  
 */
protected TableModel tableModel;

  private static Directive EMPTY_DIRECTIVE = new Directive(-1, UtilConstants.NOT_SORTED);

  // ===========================================================================
  public static final Comparator COMPARABLE_COMAPRATOR = new Comparator() {
    public int compare(Object o1, Object o2) {
      return ((Comparable) o1).compareTo(o2);
    }
  };

  // ===========================================================================
  public static final Comparator LEXICAL_COMPARATOR = new Comparator() {
    public int compare(Object o1, Object o2) {
      return o1.toString().compareTo(o2.toString());
    }
  };

  /**
 * @uml.property  name="viewToModel"
 * @uml.associationEnd  multiplicity="(0 -1)" inverse="this$0:bicat.gui.TableSorter$Row"
 */
private Row[] viewToModel;
  /**
 * @uml.property  name="modelToView"
 */
private int[] modelToView;

  /**
 * @uml.property  name="tableHeader"
 * @uml.associationEnd  
 */
private JTableHeader tableHeader;
  /**
 * @uml.property  name="mouseListener"
 */
private MouseListener mouseListener;
  /**
 * @uml.property  name="tableModelListener"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
private TableModelListener tableModelListener;
  /**
 * @uml.property  name="columnComparators"
 * @uml.associationEnd  qualifier="columnType:java.lang.Class java.util.Comparator"
 */
private Map columnComparators = new HashMap();
  /**
 * @uml.property  name="sortingColumns"
 * @uml.associationEnd  multiplicity="(0 -1)" elementType="bicat.gui.TableSorter$Directive"
 */
private List sortingColumns = new ArrayList();


  // ===========================================================================
  public TableSorter() {
    this.mouseListener = new MouseHandler();
    this.tableModelListener = new TableModelHandler();
  }

  public TableSorter(TableModel tableModel) {
    this();
    setTableModel(tableModel);
  }

  public TableSorter(TableModel tableModel, JTableHeader tableHeader) {
    this();
    setTableHeader(tableHeader);
    setTableModel(tableModel);
  }

  // ===========================================================================
  private void clearSortingState() {
    viewToModel = null;
    modelToView = null;
  }

  // ===========================================================================
  /**
 * @return
 * @uml.property  name="tableModel"
 */
public TableModel getTableModel() {
    return tableModel;
  }

  // ===========================================================================
  /**
 * @param  tableModel
 * @uml.property  name="tableModel"
 */
public void setTableModel(TableModel tableModel) {
    if (this.tableModel != null) {
      this.tableModel.removeTableModelListener(tableModelListener);
    }

    this.tableModel = tableModel;
    if (this.tableModel != null) {
      this.tableModel.addTableModelListener(tableModelListener);
    }

    clearSortingState();
    fireTableStructureChanged();
  }

  // ===========================================================================
  /**
 * @return
 * @uml.property  name="tableHeader"
 */
public JTableHeader getTableHeader() {
    return tableHeader;
  }

  // ===========================================================================
  /**
 * @param  tableHeader
 * @uml.property  name="tableHeader"
 */
public void setTableHeader(JTableHeader tableHeader) {
    if (this.tableHeader != null) {
      this.tableHeader.removeMouseListener(mouseListener);
      TableCellRenderer defaultRenderer = this.tableHeader.getDefaultRenderer();
      if (defaultRenderer instanceof SortableHeaderRenderer) {
        this.tableHeader.setDefaultRenderer(((SortableHeaderRenderer) defaultRenderer).tableCellRenderer);
      }
    }
    this.tableHeader = tableHeader;
    if (this.tableHeader != null) {
      this.tableHeader.addMouseListener(mouseListener);
      this.tableHeader.setDefaultRenderer(
          new SortableHeaderRenderer(this.tableHeader.getDefaultRenderer()));
    }
  }

  // ===========================================================================
  public boolean isSorting() {
    return sortingColumns.size() != 0;
  }

  // ===========================================================================
  private Directive getDirective(int column) {
    for (int i = 0; i < sortingColumns.size(); i++) {
      Directive directive = (Directive)sortingColumns.get(i);
      if (directive.column == column) {
        return directive;
      }
    }
    return EMPTY_DIRECTIVE;
  }

  // ===========================================================================
  public int getSortingStatus(int column) {
    return getDirective(column).direction;
  }

  // ===========================================================================
  private void sortingStatusChanged() {
    clearSortingState();
    fireTableDataChanged();
    if (tableHeader != null) {
      tableHeader.repaint();
    }
  }

  // ===========================================================================
  public void setSortingStatus(int column, int status) {
    Directive directive = getDirective(column);
    if (directive != EMPTY_DIRECTIVE) {
      sortingColumns.remove(directive);
    }
    if (status != UtilConstants.NOT_SORTED) {
      sortingColumns.add(new Directive(column, status));
    }
    sortingStatusChanged();
  }

  // ===========================================================================
  protected Icon getHeaderRendererIcon(int column, int size) {
    Directive directive = getDirective(column);
    if (directive == EMPTY_DIRECTIVE) {
      return null;
    }
    return new Arrow(directive.direction == UtilConstants.DESCENDING, size, sortingColumns.indexOf(directive));
  }

  // ===========================================================================
  private void cancelSorting() {
    sortingColumns.clear();
    sortingStatusChanged();
  }

  // ===========================================================================
  public void setColumnComparator(Class type, Comparator comparator) {
    if (comparator == null) {
      columnComparators.remove(type);
    } else {
      columnComparators.put(type, comparator);
    }
  }

  // ===========================================================================
  protected Comparator getComparator(int column) {
    Class columnType = tableModel.getColumnClass(column);
    Comparator comparator = (Comparator) columnComparators.get(columnType);
    if (comparator != null) {
      return comparator;
    }
    if (Comparable.class.isAssignableFrom(columnType)) {
      return COMPARABLE_COMAPRATOR;
    }
    return LEXICAL_COMPARATOR;
  }

  // ===========================================================================
  /**
 * @return
 * @uml.property  name="viewToModel"
 */
private Row[] getViewToModel() {
    if (viewToModel == null) {
      int tableModelRowCount = tableModel.getRowCount();
      viewToModel = new Row[tableModelRowCount];
      for (int row = 0; row < tableModelRowCount; row++) {
        viewToModel[row] = new Row(row);
      }

      if (isSorting()) {
        Arrays.sort(viewToModel);
      }
    }
    return viewToModel;
  }

  // ===========================================================================
  public int modelIndex(int viewIndex) {
    return getViewToModel()[viewIndex].modelIndex;
  }

  // ===========================================================================
  /**
 * @return
 * @uml.property  name="modelToView"
 */
private int[] getModelToView() {
    if (modelToView == null) {
      int n = getViewToModel().length;
      modelToView = new int[n];
      for (int i = 0; i < n; i++) {
        modelToView[modelIndex(i)] = i;
      }
    }
    return modelToView;
  }

  // TableModel interface methods
  // ===========================================================================
  public int getRowCount() {
    return (tableModel == null) ? 0 : tableModel.getRowCount();
  }

  public int getColumnCount() {
    return (tableModel == null) ? 0 : tableModel.getColumnCount();
  }

  public String getColumnName(int column) {
    return tableModel.getColumnName(column);
  }

  public Class getColumnClass(int column) {
    return tableModel.getColumnClass(column);
  }

  public boolean isCellEditable(int row, int column) {
    return tableModel.isCellEditable(modelIndex(row), column);
  }

  public Object getValueAt(int row, int column) {
    return tableModel.getValueAt(modelIndex(row), column);
  }

  public void setValueAt(Object aValue, int row, int column) {
    tableModel.setValueAt(aValue, modelIndex(row), column);
  }


  // Helper classes

  // ===========================================================================
  // ===========================================================================
  // ===========================================================================
  private class Row implements Comparable {
    private int modelIndex;

    public Row(int index) {
      this.modelIndex = index;
    }

    public int compareTo(Object o) {
      int row1 = modelIndex;
      int row2 = ((Row) o).modelIndex;

      for (Iterator it = sortingColumns.iterator(); it.hasNext();) {
        Directive directive = (Directive) it.next();
        int column = directive.column;
        Object o1 = tableModel.getValueAt(row1, column);
        Object o2 = tableModel.getValueAt(row2, column);

        int comparison = 0;
        // Define null less than everything, except null.
        if (o1 == null && o2 == null) {
          comparison = 0;
        } else if (o1 == null) {
          comparison = -1;
        } else if (o2 == null) {
          comparison = 1;
        } else {
          comparison = getComparator(column).compare(o1, o2);
        }
        if (comparison != 0) {
          return directive.direction == UtilConstants.DESCENDING ? -comparison : comparison;
        }
      }
      return 0;
    }
  }

 // ============================================================================
 // ============================================================================
 // ============================================================================
 private class TableModelHandler implements TableModelListener {

   public void tableChanged(TableModelEvent e) {
     // If we're not sorting by anything, just pass the event along.
     if (!isSorting()) {
       clearSortingState();
       fireTableChanged(e);
       return;
     }

     // If the table structure has changed, cancel the sorting; the
     // sorting columns may have been either moved or deleted from
     // the model.
     if (e.getFirstRow() == TableModelEvent.HEADER_ROW) {
       cancelSorting();
       fireTableChanged(e);
       return;
     }

     // We can map a cell event through to the view without widening
     // when the following conditions apply:
     //
     // a) all the changes are on one row (e.getFirstRow() == e.getLastRow()) and,
     // b) all the changes are in one column (column != TableModelEvent.ALL_COLUMNS) and,
     // c) we are not sorting on that column (getSortingStatus(column) == NOT_SORTED) and,
     // d) a reverse lookup will not trigger a sort (modelToView != null)
     //
     // Note: INSERT and DELETE events fail this test as they have column == ALL_COLUMNS.
     //
     // The last check, for (modelToView != null) is to see if modelToView
     // is already allocated. If we don't do this check; sorting can become
     // a performance bottleneck for applications where cells
     // change rapidly in different parts of the table. If cells
     // change alternately in the sorting column and then outside of
     // it this class can end up re-sorting on alternate cell updates -
     // which can be a performance problem for large tables. The last
     // clause avoids this problem.
     int column = e.getColumn();
     if (e.getFirstRow() == e.getLastRow()
         && column != TableModelEvent.ALL_COLUMNS
         && getSortingStatus(column) == UtilConstants.NOT_SORTED
         && modelToView != null) {
       int viewIndex = getModelToView()[e.getFirstRow()];
       fireTableChanged(new TableModelEvent(TableSorter.this,
                                            viewIndex, viewIndex,
                                            column, e.getType()));
       return;
     }

     // Something has happened to the data that may have invalidated the row order.
     clearSortingState();
     fireTableDataChanged();
   }
 }

 // ============================================================================
 // ============================================================================
 // ============================================================================
 private class MouseHandler extends MouseAdapter {

   public void mouseClicked(MouseEvent e) {
     JTableHeader h = (JTableHeader) e.getSource();
     TableColumnModel columnModel = h.getColumnModel();
     int viewColumn = columnModel.getColumnIndexAtX(e.getX());
     int column = columnModel.getColumn(viewColumn).getModelIndex();
     if (column != -1) {
       int status = getSortingStatus(column);
       if (!e.isControlDown()) {
         cancelSorting();
       }
       // Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or
       // {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed.
       status = status + (e.isShiftDown() ? -1 : 1);
       status = (status + 4) % 3 - 1; // signed mod, returning {-1, 0, 1}
       setSortingStatus(column, status);
     }
   }
 }

 // ============================================================================
 // ============================================================================
 // ============================================================================
 private static class Arrow implements Icon {
   private boolean descending;
   private int size;
   private int priority;

   public Arrow(boolean descending, int size, int priority) {
     this.descending = descending;
     this.size = size;
     this.priority = priority;
   }

   public void paintIcon(Component c, Graphics g, int x, int y) {
     Color color = c == null ? Color.GRAY : c.getBackground();
     // In a compound sort, make each succesive triangle 20%
     // smaller than the previous one.
     int dx = (int)(size/2*Math.pow(0.8, priority));
     int dy = descending ? dx : -dx;
     // Align icon (roughly) with font baseline.
     y = y + 5*size/6 + (descending ? -dy : 0);
     int shift = descending ? 1 : -1;
     g.translate(x, y);

     // Right diagonal.
     g.setColor(color.darker());
     g.drawLine(dx / 2, dy, 0, 0);
     g.drawLine(dx / 2, dy + shift, 0, shift);

     // Left diagonal.
     g.setColor(color.brighter());
     g.drawLine(dx / 2, dy, dx, 0);
     g.drawLine(dx / 2, dy + shift, dx, shift);

     // Horizontal line.
     if (descending) {
       g.setColor(color.darker().darker());
     } else {
       g.setColor(color.brighter().brighter());
     }
     g.drawLine(dx, 0, 0, 0);

     g.setColor(color);
     g.translate(-x, -y);
   }

   public int getIconWidth() {
     return size;
   }

   public int getIconHeight() {
     return size;
   }
 }

 // ============================================================================
 // ============================================================================
 // ============================================================================
 private class SortableHeaderRenderer implements TableCellRenderer {
   private TableCellRenderer tableCellRenderer;

   public SortableHeaderRenderer(TableCellRenderer tableCellRenderer) {
     this.tableCellRenderer = tableCellRenderer;
   }

   public Component getTableCellRendererComponent(JTable table,
       Object value,
       boolean isSelected,
       boolean hasFocus,
       int row,
       int column) {
     Component c = tableCellRenderer.getTableCellRendererComponent(table,
         value, isSelected, hasFocus, row, column);
     if (c instanceof JLabel) {
       JLabel l = (JLabel) c;
       l.setHorizontalTextPosition(JLabel.LEFT);
       int modelColumn = table.convertColumnIndexToModel(column);
       l.setIcon(getHeaderRendererIcon(modelColumn, l.getFont().getSize()));
     }
     return c;

   }
 }

 // ============================================================================
 // ============================================================================
 // ============================================================================
 private static class Directive {
   private int column;
   private int direction;

   public Directive(int column, int direction) {
     this.column = column;
     this.direction = direction;
   }
 }


}

