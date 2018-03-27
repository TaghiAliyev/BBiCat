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

package MetaFramework.KEGG;

import MetaFramework.AbstractPathwayUtils.AbstractPathwayAnalysis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * This is the updated version of KEGG parser. Some pathways seem to be having missing information which can be retrieved from the genes page. So, this version will focus on doing that. Pathway URLs are not opened at all. This will reduce the internet connectivity requests and hopefully will solve some issues as well as optimize the whole mapping procedure
 * @author  Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class KEGGParserUpdated extends AbstractPathwayAnalysis<KEGGMolecule, KEGGInteraction, KEGGPathwayInstance>{

    // USEFUL KEGG LINKS
    /**
	 * @uml.property  name="allKeggPath"
	 */
    private String allKeggPath = "http://rest.kegg.jp/list/pathway/hsa";
    /**
	 * @uml.property  name="allGenes"
	 */
    private String allGenes = "http://rest.kegg.jp/list/hsa";
    /**
	 * @uml.property  name="getBaseLink"
	 */
    private String getBaseLink = "http://rest.kegg.jp/get/";
    /**
	 * @uml.property  name="getGeneBase"
	 */
    private String getGeneBase = "http://rest.kegg.jp/get/hsa:";

    public KEGGParserUpdated(String file) throws Exception
    {
        super(file);
        parse();
    }

    // Main method that has to be implemented by all the classes extending AbstractPathwayAnalysis
    @Override
    public void parse() throws Exception {
        URL allGenesURL = new URL(this.allGenes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(allGenesURL.openStream()));

        String line;

        int molCnt = 0;
        int pathCnt = 0;

        while ((line = reader.readLine()) != null)
        {
            // Line is read. Just process it
            String[] parts = line.split("\t");
            String code = parts[0];
            String name = parts[1];
            String[] genes = name.split(";");
            molCnt++; // Just a counter
            for (String tmp : genes)
            {
                // Go through all the gene names
                KEGGMolecule mol = new KEGGMolecule(-1, tmp, false, null);
                ArrayList<KEGGPathwayInstance> pathway = this.geneToPath.get(mol);
                if (pathway == null)
                {

                }
                else
                {

                }
            }
        }


        reader.close();
    }

}
