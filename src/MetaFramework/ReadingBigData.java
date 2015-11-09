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

package MetaFramework;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * BicatGui has trouble reading the data file. So, new version of file reader is needed for Big Data
 * One issue: BiMax and some other algorithms have been implemented in 32-bit architecture, limiting the memory
 * that we can use.
 *
 * NOTE : THIS CLASS CAN READ BIG FILES EVEN IN 32-BIT THAT WERE NOT POSSIBLE TO BE READ BY BicatGUI file readers
 * However, as BiMax is implemented now locally, 64-bit architecture can be used for reading files as well.
 * One thing: BicatGUI still uses the .dll files as most of the other methods are not implemented locally and rely
 * on native libraries. So, big files are better to be processed without the GUI.
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class ReadingBigData {

    private String fileLoc = "";

    public ReadingBigData(String file) {
        this.fileLoc = file;
    }

    public void read(int row, int colLimit) throws Exception
    {
        File file = new File(fileLoc);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String colLine = br.readLine();
        String[] cols = colLine.split("\t");
        int numCol = cols.length;
        colNames = new String[Math.min(numCol - 1, colLimit)];
        for (int i = 1; i < colNames.length; i++)
            colNames[i - 1] = cols[i];
        br.readLine();
//        float[][] matrix;
        System.out.println("Started reading");
        String line;
        int cnt = 0;
        int partsLen = 0;
        while ((line = br.readLine()) != null && cnt < row - 1) {
            partsLen = line.split("\t").length - 1;
            cnt++;
        }
        System.out.println("First part of reading done");
        file = new File(fileLoc);
        br = new BufferedReader(new FileReader(file));
        br.readLine();
        br.readLine();
        actual = new float[cnt + 1][Math.min(partsLen, colLimit)];
        cnt = 0;
//        float max = 0f;
//        float min = 12f;
        rowNames = new ArrayList<String>();
        while ((line = br.readLine()) != null && cnt < row - 1) {
            String[] parts = line.split("\t");
            String geneName = parts[0]; // First column is gene names
            rowNames.add(geneName);
            // The rest are the values
            for (int i = 1; i < Math.min(partsLen, colLimit); i++) {
                actual[cnt][i - 1] = Float.parseFloat(parts[i]);
//                if (matrix[cnt][i - 1] > max)
//                    max = matrix[cnt][i - 1];
//                if (matrix[cnt][i - 1] < min)
//                    min = matrix[cnt][i - 1];
            }
            cnt++;
        }
//        System.out.println("Max value : " + max + ", Min value : " + min);
        System.out.println("Done reading");
    }


    public void read() throws Exception {
        rowNames = new ArrayList<String>();
        File file = new File(fileLoc);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String colLine = br.readLine();
        String[] cols = colLine.split("\t");
        int numCol = cols.length;
        colNames = new String[numCol - 1];
        for (int i = 1; i < numCol; i++)
            colNames[i - 1] = cols[i];
        br.readLine();
//        float[][] matrix;
        System.out.println("Started reading");
        String line;
        int cnt = 0;
        int partsLen = 0;
        while ((line = br.readLine()) != null) {
            partsLen = line.split("\t").length - 1;
            cnt++;
        }
        System.out.println("First part of reading done");
        file = new File(fileLoc);
        br = new BufferedReader(new FileReader(file));
        br.readLine();
        br.readLine();
        actual = new float[cnt + 1][partsLen];
        cnt = 0;
//        float max = 0f;
//        float min = 12f;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            String geneName = parts[0]; // First column is gene names
            rowNames.add(geneName);
            // The rest are the values
            for (int i = 1; i < parts.length; i++) {
                actual[cnt][i - 1] = Float.parseFloat(parts[i]);
//                if (matrix[cnt][i - 1] > max)
//                    max = matrix[cnt][i - 1];
//                if (matrix[cnt][i - 1] < min)
//                    min = matrix[cnt][i - 1];
            }
            cnt++;
        }
//        System.out.println("Max value : " + max + ", Min value : " + min);
        System.out.println("Done reading");

    }

    private float[][] actual;
    private String[] colNames;
    private ArrayList<String> rowNames;

    public void read2() throws Exception {
        rowNames = new ArrayList<String>();
        File file = new File(fileLoc);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String colLine = br.readLine();
        String[] cols = colLine.split("\t");
        int numCol = cols.length;
        colNames = new String[numCol - 1];
        for (int i = 1; i < numCol; i++)
            colNames[i - 1] = cols[i];
        br.readLine();
        ArrayList<Float> tmpMat = new ArrayList<Float>();
        tmpMat = readToList(br);
        System.out.println("Done read 2");
        br.close();

        actual = new float[tmpMat.size() / columnNum][columnNum];

        int len = tmpMat.size();

        for (int i = 0; i < len; i++) {
            actual[i / columnNum][i % columnNum] = tmpMat.get(i);
        }
        System.out.println("Done matricizing the 2nd version");
    }

    public ArrayList<Float> readToList(BufferedReader br) throws Exception {
        ArrayList<Float> dat = new ArrayList<Float>(40000);
        String line;
        int cnt = 0;
        while ((line = br.readLine()) != null) {
            cnt++;
            String[] parts = line.split("\t");
            rowNames.add(parts[0]);// Line starts with gene name
            columnNum = parts.length - 1;
            for (int i = 0; i < parts.length - 1; i++) {
                dat.add(Float.parseFloat(parts[i + 1]));
            }
        }

        return dat;
    }

    public void read2(int rowLimit, int colLimit) throws Exception {
        rowNames = new ArrayList<String>();
        File file = new File(fileLoc);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String colLine = br.readLine();
        String[] cols = colLine.split("\t");
        int numCol = cols.length;
        colNames = new String[Math.min(numCol - 1, colLimit)];
        for (int i = 1; i < Math.min(numCol, colLimit); i++)
            colNames[i - 1] = cols[i];
        br.readLine();
        ArrayList<Float> tmpMat = new ArrayList<Float>();
        tmpMat = readToList(br, colLimit, rowLimit);
        System.out.println("Done read 2");
        br.close();

        actual = new float[tmpMat.size() / columnNum][columnNum];

        int len = tmpMat.size();

        for (int i = 0; i < len; i++) {
            actual[i / columnNum][i % columnNum] = tmpMat.get(i);
        }
        System.out.println("Done matricizing the 2nd version");
    }

    private int columnNum;

    public ArrayList<Float> readToList(BufferedReader br, int colLimit, int rowLimit) throws Exception {
        ArrayList<Float> dat = new ArrayList<Float>(40000);
        String line;
        int cnt = 0;
        while ((line = br.readLine()) != null && cnt < rowLimit) {
            cnt++;
            String[] parts = line.split("\t");
            rowNames.add(parts[0]);// Line starts with gene name
            columnNum = Math.min(parts.length - 1, colLimit);
            for (int i = 0; i < Math.min(parts.length - 1, colLimit); i++) {
                dat.add(Float.parseFloat(parts[i + 1]));
            }
        }

        return dat;
    }

    public static void main(String[] args) throws Exception {
        ReadingBigData engine = new ReadingBigData("MuTHER_Fat_normalized_31032010_uncondensed_Ids.txt");
        long start, end;
//        start = System.currentTimeMillis();
//        engine.read();
//        end = System.currentTimeMillis();
//
//        System.out.println("Time spent : " + (end - start));
        start = System.currentTimeMillis();
        engine.read2();
        end = System.currentTimeMillis();

        System.out.println("Time spent with 2nd way : " + (end - start));

    }

}
