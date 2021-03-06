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

import bicat.Constants.MethodConstants;
import bicat.Main.UtilFunctionalities;
import bicat.biclustering.Bicluster;
import bicat.biclustering.Dataset;
import bicat.postprocessor.Postprocessor;
import bicat.preprocessor.FileOffsetException;
import bicat.preprocessor.PreprocessOption;
import bicat.preprocessor.Preprocessor;
import bicat.run_machine.ArgumentsBiMax;
import bicat.run_machine.RunMachine_BiMax;
import lombok.Data;

import java.io.File;
import java.util.LinkedList;

/**
 * Given a dataset, will create needed instances and call the methods accordingly.
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="fileLocation"
 */
/**
 * @return
 * @uml.property  name="data"
 */
/**
 * @return
 * @uml.property  name="engine"
 */
/**
 * @return
 * @uml.property  name="readingEngine"
 */
/**
 * @param fileLocation
 * @uml.property  name="fileLocation"
 */
/**
 * @param data
 * @uml.property  name="data"
 */
/**
 * @param engine
 * @uml.property  name="engine"
 */
/**
 * Given a dataset, will create needed instances and call the methods accordingly.
 * @author  Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
/**
 * @return
 * @uml.property  name="fileLocation"
 */
/**
 * @return
 * @uml.property  name="data"
 */
/**
 * @return
 * @uml.property  name="engine"
 */
/**
 * @return
 * @uml.property  name="readingEngine"
 */
/**
 * @param fileLocation
 * @uml.property  name="fileLocation"
 */
/**
 * @param data
 * @uml.property  name="data"
 */
/**
 * @param engine
 * @uml.property  name="engine"
 */
/**
 * @param readingEngine
 * @uml.property  name="readingEngine"
 */
@Data
public class BicatMethods {

    // Varible that keeps the location of the file to be loaded
    /**
	 * @uml.property  name="fileLocation"
	 */
    private String fileLocation;
    /**
	 * @uml.property  name="data"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private Dataset data;
    /**
	 * @uml.property  name="engine"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private UtilFunctionalities engine;
    /**
	 * @uml.property  name="readingEngine"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private Preprocessor readingEngine;

    public BicatMethods(String fileLocation) throws FileOffsetException {
        this.fileLocation = fileLocation;
        // Reading the dataset
        this.engine = new UtilFunctionalities();

        this.readingEngine = new Preprocessor(engine);
        File file = new File(fileLocation);

        // This will read the file onto the reading engine (pre-processor) and return a dataset
        this.data = readingEngine.readMainDataFile(file, 1, 1);
    }

    /**
     * Method that runs BiMax method on the given dataset with default variable values
     */
    public LinkedList<Bicluster> callBiMax(boolean thresholding, int perc, int minGene, int minChip) {
        LinkedList<Bicluster> result = null;

        RunMachine_BiMax runningMachine = new RunMachine_BiMax(engine);

        if (thresholding) {
            PreprocessOption options = new PreprocessOption();
            options.setDo_discretize(true);
            options.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP);
            options.setOnesPercentage(perc);
            options.setDiscretizationMode("onesPercentage");

            // Pre-processing the data
            readingEngine.preprocessData(options);
        }
        else
        {
            int[][] discreteData = new int[data.getData().length][data.getData()[0].length];
            for (int i = 0; i < discreteData.length; i++)
            {
                for (int j = 0; j < discreteData[0].length; j++)
                {
                    discreteData[i][j] = (int)(data.getData()[i][j]);
                }
            }
            readingEngine.setDiscretizedData(discreteData);
            data.setDiscrData(discreteData);
        }
        // After this point, we can already use Bimax and run bi-clustering on the processed data
        // First, let's set some parameters/arguments for the BiMax
        ArgumentsBiMax argumentsBiMax = new ArgumentsBiMax();
        argumentsBiMax.setBinaryData(readingEngine.getDiscreteData());
        argumentsBiMax.setLower_genes(minGene);
        argumentsBiMax.setLower_chips(minChip);
        argumentsBiMax.setExtended(false);
        argumentsBiMax.setGeneNumber(readingEngine.getGeneCount());
        argumentsBiMax.setChipNumber(readingEngine.getOriginalChipCount());

        engine.getDatasetList().add(data);
        engine.setCurrentDataset(data);

        runningMachine.runBiclustering(argumentsBiMax);
        result = runningMachine.getOutputBiclusters();
        return result;
    }

    /**
     * Method that runs ISA method on the given dataset with default variable values
     */
    public LinkedList<Bicluster> callISA() {
        LinkedList<Bicluster> result = null;

        // TODO: DO THIS!!!

        return result;
    }

    /**
     * Method that runs K-Means method on the given dataset with default variable values
     */
    public LinkedList<Bicluster> callKMeans() {
        LinkedList<Bicluster> result = null;

        // TODO : DO THIS!!!


        return result;
    }
}
