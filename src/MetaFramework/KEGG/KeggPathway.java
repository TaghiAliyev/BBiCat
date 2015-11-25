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
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that parses and analyzes biclusters based on the KEGG Pathway lists
 *
 * NOTE: TODO : STILL TO BE IMPLEMENTED FOR POST-ANALYSIS
 * If someone wants to implement this, please refer to PathwayAnalysisMixing to see the format of results that are expected
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
public class KeggPathway extends AbstractPathwayAnalysis<KEGGMolecule, KEGGInteraction, KEGGPathwayInstance>{

    private String allKeggPath = "http://rest.kegg.jp/list/pathway/hsa";
    private String allGenes = "http://rest.kegg.jp/list/hsa";
    private String getBaseLink = "http://rest.kegg.jp/get/";
    private String getGeneBase = "http://rest.kegg.jp/get/hsa:";

    private HashMap<String, ArrayList<String>> codeToName = new HashMap<String, ArrayList<String>>();

    private URL allPathURL;

    public KeggPathway(String file) throws Exception
    {
        super(file);
        allPathURL = new URL(allKeggPath);
        readAllGenes();
        System.out.println("All genes are been mapped");
        parse();
        System.out.println("All the pathways are done");
    }

    public void readAllGenes() throws Exception
    {
        URL allGeneURL = new URL(allGenes);
        BufferedReader geneIn = new BufferedReader(new InputStreamReader(allGeneURL.openStream()));

        String gene;
        while ((gene = geneIn.readLine()) != null)
        {
            String[] parts = gene.split("\t");
            String code = parts[0];
            String geneName = parts[1];
            String geneParts = geneName.split(";")[0];
            String[] actualParts = geneParts.split(",");
            ArrayList<String> toAddList = new ArrayList<String>();
            for (int i = 0 ; i < actualParts.length; i++)
            {
                String toAdd;
                if (actualParts.length != 1)
                    toAdd = actualParts[i].replaceAll("\\s+", "");
                else
                    toAdd = actualParts[i];
                toAddList.add(toAdd);
//                System.out.println("Parts: " + toAdd + " , " + code);
            }
//            System.out.println("Gene with code : " + code + " has " + actualParts.length + " parts");
            codeToName.put(code, toAddList);
        }

    }


    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();
        KeggPathway engine = new KeggPathway("");
        long end = System.currentTimeMillis();
        System.out.println("It took " + (end - start) + " ms to parse the KEGG");
        ArrayList<KEGGPathwayInstance> mols = engine.getGeneToPath().get(new KEGGMolecule(0, "XDH", false, null));
        for (KEGGPathwayInstance tmp : mols)
        {
            System.out.println("Molecule name in the pathway : " + tmp.getName());
        }
    }

    @Override
    public void parse() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(allPathURL.openStream()));

        String line;
        int pathCnt = 0, molCnt = 0;
        while((line = in.readLine()) != null)
        {
            if (pathCnt % 50 == 0)
                System.out.println(pathCnt);
            // Line is already read
            String[] parts = line.split("\t");
            String pathCode = parts[0];
            String pathName = parts[1].substring(0, parts[1].length() - 23);
//            System.out.println(pathName);
            KEGGPathwayInstance path = new KEGGPathwayInstance(null, pathCnt, null, pathName);
//            System.out.println("Path name : " + pathName);
            String pathLink = getBaseLink + pathCode;
            URL pathURL = new URL(pathLink);
            BufferedReader pathIn = new BufferedReader(new InputStreamReader(pathURL.openStream()));
            boolean geneStarted = false;
            boolean geneEnd = false;
            String pLine;
            ArrayList<KEGGMolecule> mols = new ArrayList<KEGGMolecule>();
            while ((pLine = pathIn.readLine()) != null && (!geneStarted || !geneEnd))
            {
                if (pLine.length() < 4)
                    System.out.println(pathName + " ;" + pathCode);
                if (pLine.length() >= 4 && !pLine.substring(0, 4).equalsIgnoreCase("    ") && geneStarted)
                    geneEnd = true;
                if (pLine.length() >= 4 && pLine.substring(0,4).equalsIgnoreCase("GENE"))
                    geneStarted = true;
                if (geneStarted && !geneEnd) {
                    String pureGene = pLine.substring(12);
                    // Getting the name and the code of the gene. Name might not be necessary
                    String code = pureGene.split(" ")[0];
                    if (pureGene.split(" ").length >= 3) {
                        String geneName2 = pureGene.split(" ")[2].split(";")[0];
                        ArrayList<String> geneName = codeToName.get("hsa:" + code);
                        if (geneName == null)
                            System.out.println(code + " ; " + geneName2);
                        ArrayList<KEGGMolecule> pathMols = new ArrayList<KEGGMolecule>();
                        // TODO: LOOK INTO HOW COMPARISONS ARE DONE FOR HASHMAP THING.
                        // THIS WAY ID WILL BE PROBLEMATIC
                        for (String tmp : geneName) {
                            KEGGMolecule tmpMol = new KEGGMolecule(molCnt, tmp, false, null);
                            mols.add(tmpMol);
                            ArrayList<KEGGPathwayInstance> paths = this.geneToPath.get(tmpMol);
                            if (paths == null) {
                                paths = new ArrayList<KEGGPathwayInstance>();
                                paths.add(path);
                                this.geneToPath.put(tmpMol, paths);
                            } else {
                                paths.add(path);
                                this.geneToPath.put(tmpMol, paths);
                            }
                        }

                        molCnt++;
//                    System.out.println(geneName.get(0) + " " + code);
                    }
                }
            }
            pathIn.close();
//            System.out.println();
            this.pathToGene.put(path, mols);
//            System.out.println("Amount of parts : " + parts.length);
//            System.out.println("First part : " + parts[0]);
//            System.out.println("Second part : " + parts[1]);
            pathCnt++;
        }

        in.close();
    }
}
