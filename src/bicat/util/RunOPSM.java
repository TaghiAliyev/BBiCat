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

package bicat.util;

import java.io.*;
import java.util.*;

import bicat.biclustering.Dataset;

/**
 * Superclass of algorithm main classes.
 *
 * @author Thomas Frech
 * @version 2004-07-22
 */
public abstract class RunOPSM
{
	// dataset filename
	protected String datasetFilename;

	// dataset
	protected OPSMDataset dataset;

	// id string, used by run engine
	public static String idString = "";


//	/**
//	 * Constructor.
//	 *
//	 * @param datasetFilename dataset filename
//	 */
//	public RunOPSM(String datasetFilename)
//	{
//		// initialize random generator
//		if (Option.initializeRandomGenerator)
//			XMath.initRandom(11031979);
//
//		// load dataset
//		this.datasetFilename = datasetFilename;
//		dataset = new OPSMDataset(datasetFilename);
//	}

	/**
	 * Constructor.
	 *
	 * @param datasetFilename dataset filename
	 */
	public RunOPSM(Dataset dataset)
	{
	}
	
	/**
	 * Run algorithm, display and save result.
	 *
	 * @return result
	 */
	public ParetoModelRecord run()
	{
		if (dataset==null)
			return null;

		// initialize XMath
		XMath.initialize(Math.max(dataset.nr_rows,dataset.nr_col));

		// run algorithm
		if (Option.printMainMessages)
			System.out.println("\nAlgorithm started.");
		long start = System.currentTimeMillis();
		ParetoModelRecord result = runAlgorithm();
		long runtime = (System.currentTimeMillis()-start)/1000;
		if (Option.printMainMessages)
			System.out.println("Algorithm finished.\n");

		// display and save result
		if (Option.printMainMessages)
		{
			System.out.println("Pareto optimal models:\n");
			System.out.println(result.toString());
			System.out.println("Runtime: "+runtime+"s");
		}
		String algorithmName = algorithmName().toLowerCase().replace(' ','_').replace('-','_');
		String resultDirectoryname = datasetFilename+"."+algorithmName+"_result"+idString;
		if (Option.saveResults)
			result.save(resultDirectoryname);

		// write report
		String reportFilename = datasetFilename+"."+algorithmName+"_report"+idString+".txt";
		writeReport(dataset,result,resultDirectoryname,runtime,reportFilename);

		return result;
	}

	/**
	 * Subclasses have to call the algorithm in this method.
	 *
	 * @return result
	 */
	protected abstract ParetoModelRecord runAlgorithm();

	// write report
	private void writeReport(OPSMDataset dataset, ParetoModelRecord result, String resultDirectoryname, long runtime, String reportFilename)
	{
		if (Option.printMainMessages)
			System.out.print("Writing report to '"+reportFilename+"' ... ");

		try
		{
			// open stream
			File file = new File(reportFilename);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			PrintStream out = new PrintStream(fileOutputStream);

			// write header
			writeReportHeader(dataset,datasetFilename,runtime,out);

			// write parameter section
			out.println("parameters:");
			writeReportParameterSection(out);

			// write result section
			writeReportResultSection(dataset,result,resultDirectoryname,out);

			// close stream
			out.close();
			fileOutputStream.close();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			if (Option.printErrors)
				System.out.println("ERROR: "+e.toString());
			e.printStackTrace();
		}

		if (Option.printMainMessages)
			System.out.println("done");
	}

	// write report header
	private void writeReportHeader(OPSMDataset dataset, String datasetFilename, long runtime, PrintStream out)
	{
		String title = algorithmName()+" Algorithm Report";
		out.println(title);
		for (int i=0; i<title.length(); i++)
			out.print("-");
		out.println();
		out.println();

		Date date = new Date();
		out.println("date and time of run: "+date.toString());
		out.println();

		out.println("run time: "+runtime+"s");
		out.println();

		out.println("dataset loaded from file '"+datasetFilename+"':");
		out.println("   number of tissues        : m      = "+dataset.nr_col);
		out.println("   number of genes          : n      = "+dataset.nr_rows);
		if (dataset.plantedModel!=null)
		{
			out.println("   number of planted tissues: t      = "+dataset.plantedModel.lengthOfArrayOfTissueIndices);
			out.println("   number of planted genes  : g      = "+dataset.plantedModel.lengthArrayOfGeneIndices);
			dataset.plantedModel.U();
			out.println("   score                    : U(t,g) = "+dataset.plantedModel.U());
		}
		out.println();
	}

	/**
	 * Subclasses have to return the algorithm name in this method.
	 *
	 * @return algorithm name
	 */
	public abstract String algorithmName();

	/**
	 * Subclasses have to print the parameter section of the report in this method.
	 * Format: 1 line per parameter, 3 spaces at the beginning of each line.
	 *
	 * @param out print stream
	 */
	protected abstract void writeReportParameterSection(PrintStream out);

	// write report result section
	private void writeReportResultSection(OPSMDataset dataset, ParetoModelRecord result, String resultDirectoryname, PrintStream out)
	{
		if (result==null)
		{
			out.println();
			out.println("No models found.");
			return;
		}

		Model[] model = result.getArray();
		for (int index=0; index<model.length; index++)
		{
			// write model properties
			out.println();
			out.println("Model saved to '"+resultDirectoryname+"/model_"+(index+1)+".txt':");
			out.println("   number of tissues: s      = "+model[index].lengthOfArrayOfTissueIndices);
			out.println("   number of genes  : k      = "+model[index].lengthArrayOfGeneIndices);
			out.println("   score            : U(s,k) = "+model[index].U());

			// compare model to planted model
			if (dataset.plantedModel!=null)
			{
				int plantedTissuesFound = 0;
				int unplantedTissuesFound = 0;
				int plantedGenesFound = 0;
				int unplantedGenesFound = 0;

				for (int i=0; i<model[index].lengthOfArrayOfTissueIndices; i++)
					if (dataset.plantedModel.isInT[model[index].arrayOfTissueIndices[i]])
						plantedTissuesFound++;
					else
						unplantedTissuesFound++;
				for (int j=0; j<model[index].lengthArrayOfGeneIndices; j++)
					if (dataset.plantedModel.isInG[model[index].arrayOfGeneIndices[j]])
						plantedGenesFound++;
					else
						unplantedGenesFound++;

				out.println("   model contains:");
				out.println("      "+plantedTissuesFound+" of "+dataset.plantedModel.lengthOfArrayOfTissueIndices+" planted tissues");
				out.println("      "+unplantedTissuesFound+" unplanted tissues");
				out.println("      "+plantedGenesFound+" of "+dataset.plantedModel.lengthArrayOfGeneIndices+" planted genes");
				out.println("      "+unplantedGenesFound+" unplanted genes");
			}

			out.println("   model info:");
			out.println(model[index].infoString);
		}

		if (dataset.plantedModel==null)
		{
			out.println();
			out.println("No information about planted model available.");
		}
	}
}
