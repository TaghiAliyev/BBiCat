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
@Data
public class BicatMethods {

    // Varible that keeps the location of the file to be loaded
    private String fileLocation;
    private Dataset data;
    private UtilFunctionalities engine;
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
    public LinkedList<Bicluster> callBiMax() {
        LinkedList<Bicluster> result = null;

        RunMachine_BiMax runningMachine = new RunMachine_BiMax(engine);

        PreprocessOption options = new PreprocessOption();
        options.setDo_discretize(true);
        options.setDiscretizationScheme(MethodConstants.PREPROCESS_OPTIONS_DISCRETIZATION_UP);
        options.setOnesPercentage(25); // 25% Thresholding
        options.setDiscretizationMode("onesPercentage");

        // Pre-processing the data
        readingEngine.preprocessData(options);

        // After this point, we can already use Bimax and run bi-clustering on the processed data
        // First, let's set some parameters/arguments for the BiMax
        ArgumentsBiMax argumentsBiMax = new ArgumentsBiMax();
        argumentsBiMax.setBinaryData(readingEngine.getDiscreteData());
        argumentsBiMax.setLower_genes(8);
        argumentsBiMax.setLower_chips(15);
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

        return result;
    }

    /**
     * Method that runs K-Means method on the given dataset with default variable values
     */
    public LinkedList<Bicluster> callKMeans() {
        LinkedList<Bicluster> result = null;


        return result;
    }
}
