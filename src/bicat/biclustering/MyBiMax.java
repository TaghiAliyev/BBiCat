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

package bicat.biclustering;

import MetaFramework.ReadingBigData;
import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.preprocessor.PreprocessOption;
import bicat.preprocessor.Preprocessor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

/**
 * Bimax library is compiled under 32-bit architecture, thus limiting the power of the application (Especially from heap space)
 * Following is the re-implementation of the C-code in Java. This code should be tested with some sample datasets
 * Also, its results should be compared to of an original
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */

// Notice from the original code:
/*-------------------------------------------------------------------------------------
 * bimax.c: C implementation for the Bimax algorithm as described in the paper
 *          "A Systematic Comparison and Evaluation of Biclustering Methods for Gene
 *          Expression Data" by A. Prelic et al., Bioinformatics, 2006.
 *
 * USAGE:   Compile the program using any C compiler, e.g., gcc -O4 -o bimax bimax.c
 *          under Linux. The program assumes the data matrix to be contained in the
 *          file 'matrix.txt' in the same directory; this file is structured as follows:
 *
 *          - the first number specifies the number n of rows
 *          - the second number number specifies the number m of columns
 *          - the third number defines the minimal number of rows a bicluster must
 *            contain
 *          - the fourth number defines the minimal number of columns a bicluster must
 *            contain
 *          - the succeeding numbers, which are either 0s or 1s, represent the contents
 *            of the data matrix where the order is first columnwise and then rowwise,
 *            i.e., <row_1_column_1> <row_1_column_2> ... <row_1_column_m>
 *            <row_2_column_1> ... <row_2_column_m> ... <row_n_column_m>
 *
 *          The biclusters found are outputted on the terminal, where for each
 *          bicluster three lines are printed:
 *
 *          - the first line contains the number of the bicluster
 *          - the second line contains the selected rows (tab delimited)
 *          - the third line contains the selected columns (tab delimited)
 *
 *          Subsequent biclusters are separated by blank lines.
 *
 * (c)2005/6 Eckart Zitzler, ETH Zurich, Switzerland
 *-------------------------------------------------------------------------------------*/
@Data
public class MyBiMax {

    private BitVector bitMaskLastBv;
    private int bitsPerBV;
    private long noBVs;

    private long noRows, noColumns, minNoRows, minNoColumns, maxLevels;
    private Row[] rows;
    private Cs_T[] consideredColumns, mandatoryColumns;
    private BitVector[] columnIntersection;
    private PrintWriter outFile;

    private String[] colNames;
    private String[] rowNames;
    private Dataset dataset;

    private ArrayList<Bicluster> biclusters; // Results will be stored inside of this array list

    public enum cMode_t {
        IDENTITY,
        COMPLEMENT
    }


    public MyBiMax(String[] colNames, String[] rowNames, Dataset dataset) throws FileNotFoundException {
        outFile = new PrintWriter("BimaxResults.out");
        this.colNames = colNames;
        this.rowNames = rowNames;
        biclusters = new ArrayList<Bicluster>();
        this.dataset = dataset;
    }

    // BiMax methods

    /**
     * Initializes memory needed for the application/algorithm
     *
     * @return Returns an int representing the fail or not
     */
    public int initialize() {
        BitVector dummy;
        int failed = 0;
        long i;

        dummy = new BitVector(1);
        bitsPerBV = 0;
        while (dummy.getValue() != 0) {
            dummy.setValue(dummy.getValue() << 1);
            bitsPerBV++;
        }

        System.out.println("Bits per BV : " + bitsPerBV);

        bitMaskLastBv = new BitVector();
        bitMaskLastBv.setValue((~0L >>> (bitsPerBV - (noColumns % bitsPerBV))));
        System.out.println("bit Mask Last BV : " + bitMaskLastBv);
        noBVs = (noColumns / bitsPerBV) + ((noColumns % bitsPerBV) == 0 ? 0 : 1);

        System.out.println("Number of BVs : " + noBVs);

        rows = new Row[(int) noRows];

        if (rows == null)
            failed = 1;
//        System.out.println("Failed : " + failed);
        for (i = 0L; i < noRows; i++) {
            rows[(int) i] = new Row();
            rows[(int) i].setOriginalRowNumber(i);
            BitVector[] array = new BitVector[(int) noBVs];
            for (int k = 0; k < array.length; k++)
                array[k] = new BitVector();
            rows[(int) i].setColumnSet(array);
            if (rows[(int) i] == null)
                failed = 1;
        }

        maxLevels = (noRows + 2L);
        consideredColumns = new Cs_T[(int) maxLevels];
        if (consideredColumns == null)
            failed = 1;
        else {
            for (i = 0L; i < maxLevels; i++) {
                BitVector[] array = new BitVector[(int) noBVs];
                for (int k = 0; k < (int) noBVs; k++)
                    array[k] = new BitVector();
                consideredColumns[(int) i] = new Cs_T(array);
            }
            if (failed == 0) {
                for (i = 0L; i < noColumns; i++)
                    setColumn(consideredColumns[0].bitVectors, i);
            }
        }

        mandatoryColumns = new Cs_T[(int) maxLevels];
        if (mandatoryColumns == null)
            failed = 1;
        else {
            for (i = 0L; i < maxLevels; i++) {
                mandatoryColumns[(int) i] = new Cs_T(new BitVector[(int) noBVs]);
                if (mandatoryColumns[(int) i] == null)
                    failed = 1;
            }
        }
        columnIntersection = new BitVector[(int) noBVs];
        if (columnIntersection == null)
            failed = 1;

        return failed == 0 ? 1 : 0;
    }

    // Utility functions
    public int isSet(BitVector[] columnSet, long column) {
        BitVector bv = new BitVector();
        if (column >= 0L && column < noColumns) {
            bv.setValue(1L << (column % bitsPerBV));
            boolean result = ((columnSet[(int) (column / bitsPerBV)].getValue() & bv.getValue()) != 0);
            return result ? 1 : 0;
        }

        return 0;
    }

    public void setColumn(BitVector[] columnSet, long column) {
        BitVector bv = new BitVector();
        if (column >= 0L && column < noColumns) {
//            System.out.println("Setting the column : " + column);
            bv.setValue(1L << (column % bitsPerBV));
            columnSet[(int) (column / bitsPerBV)].setValue(columnSet[(int) (column / bitsPerBV)].getValue() | bv.getValue());
        }
    }

    public void unsetColumn(BitVector[] columnSet, long column) {
        BitVector bv = new BitVector();

        if (column >= 0L && column < noColumns) {
//            System.out.println("Unsetting the column : " + column);
            bv.setValue(~(~(columnSet[(int) (column / bitsPerBV)].getValue()) | (1L << (column % bitsPerBV))));
            columnSet[(int) (column / bitsPerBV)].setValue(columnSet[(int) (column / bitsPerBV)].getValue() & bv.getValue());
        }
    }

    public long columnCount(BitVector[] columnSet) {
        long i, j, counter;
        BitVector bv = new BitVector();

        columnSet[(int) noBVs - 1].setValue(columnSet[(int) noBVs - 1].getValue() & bitMaskLastBv.getValue());
        counter = 0L;
        for (i = noBVs - 1; i >= 0; i--) {
            bv = new BitVector(columnSet[(int) i].getValue());
            if (bv.getValue() != 0L) {
                for (j = 0L; j < bitsPerBV; j++) {
                    if ((bv.getValue() & 1L) != 0L)
                        counter++;
                    bv.setValue(bv.getValue() >>> 1);
                }
            }
        }
        return counter;
    }

    public int compareColumns(BitVector[] columnSetA, BitVector[] columnSetB, BitVector[] mask) {
        int i, contained, disjoint;
        BitVector bitMask, sharedColumns;

        contained = 1;
        disjoint = 1;
//        System.out.println("Bitmask value before : " + bitMaskLastBv.getValue());
        bitMask = new BitVector(bitMaskLastBv.getValue());
        for (i = (int) noBVs - 1; i >= 0; i--) {
            sharedColumns =
                    new BitVector(((columnSetA[i].getValue() & columnSetB[i].getValue()) & mask[i].getValue()) & bitMask.getValue());
            if ((sharedColumns.getValue() | columnSetB[i].getValue()) != sharedColumns.getValue())
                contained = 0;
            if (sharedColumns.getValue() != 0L)
                disjoint = 0;
            bitMask.setValue(~0L);
        }
//        System.out.println("Bitmask value after : " + bitMaskLastBv.getValue());

        if (contained != 0 && disjoint != 0)
            return -2;
        if (contained != 0)
            return -1;
        if (disjoint != 0)
            return 1;

        return 0;
    }


    public void copyColumnSet(BitVector[] columnSet, BitVector[] columnSetDest, cMode_t copyMode) {
        int i;

        for (i = (int) noBVs - 1; i >= 0; i--)
            if (copyMode == cMode_t.COMPLEMENT)
                columnSetDest[i] = new BitVector(~columnSet[i].getValue());
            else
                columnSetDest[i] = new BitVector(columnSet[i].getValue());
    }

    public void intersectColumnSets(BitVector[] columnSetA, BitVector[] columnSetB, BitVector[] columnSetDest) {
        int i;
        for (i = (int) noBVs - 1; i >= 0; i--)
            columnSetDest[i] = new BitVector(columnSetA[i].getValue() & columnSetB[i].getValue());
    }

    public void determineColumnsInCommon(long firstRow, long lastRow, BitVector[] sharedColumnSet) {
        int i;
        long j;

        if (firstRow >= 0L && lastRow >= firstRow && lastRow < noRows) {
            for (i = (int) noBVs - 1; i >= 0; i--) {
                sharedColumnSet[i] = new BitVector(~0L);
                for (j = firstRow; j <= lastRow; j++)
                    sharedColumnSet[i].setValue(sharedColumnSet[i].getValue() & rows[(int) j].getColumnSet()[i].getValue());
            }
        }
    }

    public boolean containsMandatoryColumns(BitVector[] columnSet, int noSets) {
        int contains, j;
        long i;

        contains = 1;

        for (i = 0; i < noSets; i++) {
            if ((mandatoryColumns[(int) i].bitVectors[(int) noBVs - 1].getValue() & columnSet[(int) noBVs - 1].getValue() & bitMaskLastBv.getValue()) == 0L) {
                j = (int) noBVs - 2;
                while (j >= 0 && (mandatoryColumns[(int) i].bitVectors[j].getValue() & columnSet[j].getValue()) == 0L)
                    j--;
                if (j < 0) {
                    contains = 0;
                    i = noSets;
                }
            }
        }

        return contains != 0 ? true : false;
    }

    public void swapRows(long a, long b) {
        long tempOriginalRowNumber;
        BitVector[] tempColumnSet;
        if (a != b && a >= 0L && a < noRows && b >= 0L && b < noRows) {
            tempOriginalRowNumber = rows[(int) a].originalRowNumber;
            tempColumnSet = rows[(int) a].columnSet;
            rows[(int) a].originalRowNumber = rows[(int) b].originalRowNumber;
            rows[(int) a].columnSet = rows[(int) b].columnSet;
            rows[(int) b].originalRowNumber = tempOriginalRowNumber;
            rows[(int) b].columnSet = tempColumnSet;
        }

    }

    public long chooseSplitRow(long firstRow, long lastRow, int level) {
        long i;

        for (i = firstRow; i <= lastRow &&
                compareColumns(rows[(int) i].columnSet, consideredColumns[level].bitVectors, consideredColumns[0].bitVectors) < 0; i++)
            ;

//        System.out.println(i + " " + firstRow + " " + lastRow);

        if (i <= lastRow)
            return i;

        return firstRow;
    }

    public long selectRows(long firstRow, long lastRow, long level, IntHolder overlapping) {
        long selected;

        selected = 0L;
        overlapping.setValue(0);
        while (firstRow <= lastRow) {
            switch (compareColumns(consideredColumns[(int) level].bitVectors, rows[(int) firstRow].columnSet, consideredColumns[(int) (level - 1L)].bitVectors)) {
                case -2:
                case 1:
                    swapRows(lastRow, firstRow);
                    lastRow--;
                    break;
                case 0:
//                    System.out.println("Overlapping should be 1!");
                    overlapping.setValue(1);
                default:
                    selected++;
                    firstRow++;
                    break;
            }
        }

        return selected;
    }

    private long biclusterCounter = 0;

    public void writeBicluster(long firstRow, long lastRow, BitVector[] columnSet) {
//        System.out.println("Bicluster found!!!");

        long i;
        Bicluster bicluster;

        /*
        Bicluster(int identification, int[] g, int[] c, float[][] rawData,
                     int[][] binData)
         */
        System.out.println("Bicluster counter : " + biclusterCounter);
        biclusterCounter++;
        int[] g = new int[(int)(lastRow - firstRow + 1)];
        ArrayList<Integer> cL = new ArrayList<Integer>();
//        outFile.printf("\n%s\n", biclusterCounter);
        for (i = firstRow; i <= lastRow; i++) {
//            outFile.printf("%s\t", rowNames[(int)rows[(int) i].originalRowNumber]);
            g[(int)(i - firstRow)] = (int)(rows[(int)i].originalRowNumber);
        }
//        outFile.printf("\n");
        for (i = 0; i < noColumns; i++)
            if (isSet(columnSet, i) != 0) {
//                outFile.printf("%s\t", colNames[(int) i]);
                cL.add((int) i);
            }
//        outFile.printf("\n");
        int[] c = new int[cL.size()];
        for (int j = 0; j < cL.size(); j++)
            c[j] = cL.get(j);
        bicluster = new Bicluster((int)i, g, c, this.dataset.getData(), this.dataset.getDiscrData());
        biclusters.add(bicluster);
    }

    public void conquer(long firstRow, long lastRow, long level, long noMandatorySets) {
        if (biclusterCounter > 1000)
            return;
//        System.out.println("Bicluster count from conquer : " + biclusterCounter);
//        System.out.println("Some statistics : " + noRows + " " + noColumns + " " + minNoRows + " " + minNoColumns);
        IntHolder overlapping = new IntHolder(0);
        long splitRow, noSelectedRows;

        determineColumnsInCommon(firstRow, lastRow, columnIntersection);
        if (compareColumns(columnIntersection, consideredColumns[(int) level].bitVectors, consideredColumns[(int) level].bitVectors) == -1)
            writeBicluster(firstRow, lastRow, columnIntersection);
        else {
            splitRow = chooseSplitRow(firstRow, lastRow, (int) level);
//            System.out.println("First row : " + firstRow + ", Last row : " + lastRow + ", level : " + level + ", split row : " + splitRow);
            intersectColumnSets(consideredColumns[(int) level].bitVectors, rows[(int) splitRow].columnSet, consideredColumns[(int) (level + 1L)].bitVectors);
            if (columnCount(consideredColumns[(int) (level + 1L)].bitVectors) >= minNoColumns &&
                    containsMandatoryColumns(consideredColumns[(int) (level + 1L)].bitVectors, (int) noMandatorySets)) {
                noSelectedRows = selectRows(firstRow, lastRow, level + 1L, overlapping);
//                System.out.println("Overlapping after : " + overlapping.getValue());
                if (noSelectedRows >= minNoRows)
                    conquer(firstRow, firstRow + noSelectedRows - 1L, level + 1L, noMandatorySets);
            }
            copyColumnSet(consideredColumns[(int) (level + 1L)].bitVectors, consideredColumns[(int) (level + 1L)].bitVectors,
                    cMode_t.COMPLEMENT);
            intersectColumnSets(consideredColumns[(int) level].bitVectors, consideredColumns[(int) (level + 1L)].bitVectors,
                    consideredColumns[(int) (level + 1L)].bitVectors);
            if (overlapping.getValue() != 0) {
                copyColumnSet(consideredColumns[(int) (level + 1L)].bitVectors, mandatoryColumns[(int) noMandatorySets].bitVectors, cMode_t.IDENTITY);
                noMandatorySets++;
            }

            noSelectedRows = selectRows(firstRow, lastRow, level + 1L, overlapping);
            copyColumnSet(consideredColumns[(int) level].bitVectors, consideredColumns[(int) (level + 1L)].bitVectors, cMode_t.IDENTITY);
//            System.out.println("Number of selected rows : " + noSelectedRows);
            if (noSelectedRows >= minNoRows)
                conquer(firstRow, firstRow + noSelectedRows - 1L, level + 1L, noMandatorySets);
        }
    }

    public void readInDataMatrix(Scanner scanner) {
        long i, j, cell;
        for (i = 0L; i < noRows; i++) {
            for (j = 0L; j < noColumns; j++) {
                cell = scanner.nextLong();
//                System.out.println("Value that is read : " + cell);
                if (cell == 0L) {
                    unsetColumn(rows[(int) i].columnSet, j);
                } else
                    setColumn(rows[(int) i].columnSet, j);
            }
        }
    }

    public void readInDataMatrix(int[][] dataset) {
        int i, j, cell;
        for (i = 0; i < dataset.length; i++) {
            for (j = 0; j < dataset[0].length; j++) {
                cell = dataset[i][j];
                if (cell == 0)
                    unsetColumn(rows[i].columnSet, j);
                else
                    setColumn(rows[i].columnSet, j);
            }
        }
    }

    public void closePrint()
    {
        this.outFile.close();
    }


    // Private classes holding some data together needed for the algorithm
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class BitVector {
        private long value;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Cs_T {
        private BitVector[] bitVectors;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Row {
        private long originalRowNumber;
        private BitVector[] columnSet;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class IntHolder {
        private int value;
    }

}
