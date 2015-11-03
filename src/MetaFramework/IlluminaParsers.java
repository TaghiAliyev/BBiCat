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

package MetaFramework;

import lombok.Data;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Parses a illumina database file
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class IlluminaParsers {

    private String file;
    private HashMap<String, String> illuminaToHGNC;

    public IlluminaParsers(String file) throws Exception
    {
        this.file = file;
        illuminaToHGNC = new HashMap<String, String>();
        parse();
    }

    /**
     * Method that parses the information from illumina database file. Results are stored in a hashmap which maps
     * illumina gene name to a HGNC Symbol name.
     *
     * @throws Exception
     */
    public void parse() throws Exception
    {
        FileInputStream stream = new FileInputStream(file);
        Scanner scanner = new Scanner(stream);

        // First read till [Probes] line
        String line = scanner.nextLine();
        while (!line.equalsIgnoreCase("[Probes]"))
        {
            line = scanner.nextLine();
        }
        System.out.println(line);
        line = scanner.nextLine();
        line = scanner.nextLine();
        int cnt = 0;
        while (!line.equalsIgnoreCase("[Controls]"))
        {
//            System.out.println(line);
            String[] parts = line.split("\t");
            // 12th is the illumina we are interested in (13th column)
            // HGNC name of the gene is at index: 4 (5th column)
            String illumina = parts[13];
            String symbol = parts[4];

            if (cnt == 0)
                System.out.println(illumina + " " + symbol);

            illuminaToHGNC.put(illumina, symbol);
            line = scanner.nextLine();
            cnt++;
        }
    }

    public static void main(String[] args) throws Exception
    {
        String file = "C:/Users/tagi1_000/Desktop/CERN/BBiCat/HumanHT-12_V3_0_R3_11283641_A.txt";
        IlluminaParsers engine = new IlluminaParsers(file);
    }

}
