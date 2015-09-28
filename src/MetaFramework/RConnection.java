package MetaFramework;

import bicat.biclustering.Bicluster;
import lombok.Data;
import rcaller.RCaller;
import rcaller.RCode;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Method that will create R connection, get clusters and call R scripts
 *
 * @author Taghi Aliyev, email : taghi.aliyev@cern.ch
 */
@Data
public class RConnection {

    private RCaller rcaller;
    private RCode code;

    public RConnection() {
        // Initializing the needed instances/classes
        this.rcaller = new RCaller();
        this.code = new RCode();
        // Setting the R executable that is used. Change this to your local one!
        rcaller.setRscriptExecutable("C:\\Program Files\\R\\R-3.1.2\\bin\\x64\\Rscript.exe");
        rcaller.redirectROutputToStream(System.out);
//        rcaller.StopRCallerOnline();
    }

    /**
     * Adding the computed (bi)-clusters to the R environment
     * TODO : Think about the representation of the clusters in R
     *
     * @param biclusters Computed (bi-)clusters
     */
    public void addClusters(LinkedList<Bicluster> biclusters, Vector<String> geneNames) {
        // Adding clusters to the R environment.
        code.addRCode("whole_list <- list()");
        String[] tmpNames = new String[]{"ADCY6", "ADCY3"};
        // So, let's do this: First cluster gets first, second second and third all 3
        int cnt = 0;
        for (Bicluster tmp : biclusters)
        {
                // Now, gotta be careful
            String[] geneCluster = new String[tmp.getGenes().length];
            for (int i = 0; i < geneCluster.length; i++)
            {
                geneCluster[i] = geneNames.get(tmp.getGenes()[i]);
            }

            if (cnt %3 == 0)
            {
                // Only first
                geneCluster[0] = tmpNames[0];
            }
            else if (cnt % 3 == 1)
            {
                geneCluster[0] = tmpNames[1];
            }
            else
            {
                geneCluster[0] = tmpNames[0];
                geneCluster[1] = tmpNames[1];
            }
            // geneCluster now contains all the gene names needed
            // We should add it as a list
            code.addStringArray("geneList" + cnt, geneCluster);

            // Now geneList in R contains the names
            code.addRCode("whole_list[[length(whole_list) + 1]] <- geneList" + cnt);
            cnt++;
        }
    }

    public void addCluster(Bicluster bicluster, Vector<String> geneNames) {
        code.addRCode("rm(geneNames)");
        int[] genes = bicluster.getGenes(); // Array with the gene indices
        String[] toAdd = new String[genes.length];
        for (int i = 0; i < genes.length; i++) {
            toAdd[i] = geneNames.get(genes[i]);
        }
        code.addStringArray("geneNames", toAdd);
        // Alright, here is the problem:
        // We have list of lists kind of. So, maybe let's do it that way?

    }

    public void addStringArray(String[] names) {
        code.addStringArray("geneNames", names);
    }


    public void setUp(String script) {
        // Sets up the environment, so that we do not call these codes all the time over and over again
        code.addRCode("chooseCRANmirror(graphics = FALSE, ind = 81)");
        code.addRCode("library(\"methods\")");
        code.addStringArray("link1", new String[]{"biomaRt", "C:/Users/tagi1_000/Desktop/CERN"});

//            String[] geneNames = new String[]{"ADCY6", "ADCY3"};
//            code.addStringArray("geneNames", geneNames);
        code.addStringArray("link", new String[]{script});
    }

    /**
     *
     *
     */
    public void callRScript() {
        // Calling the R script after adding the clusters to the environment
        try {
            // Here we should call the script
            code.addRCode("source(link[1])");

            rcaller.setRCode(code);
            rcaller.runOnly();
//            rcaller.runAndReturnResult("counter");

//            System.out.println(rcaller.getParser().getNames());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rcaller.StopRCallerOnline();
            rcaller.stopStreamConsumers();
        }
    }
}
