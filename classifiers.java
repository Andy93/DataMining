package classifier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Classifiers {
	public static void main(String[] args) {
		Classifiers obj = new Classifiers();
		obj.run();
	}

	public void run() {
		String csvFile = "/Users/Andrew/Desktop/DataMining/src/Competator_Use_Of_Airports_In_Range.csv";
		String csvFile2 = "/Users/Andrew/Desktop/DataMining/src/classifier.csv";
		BufferedReader reader = null;
		String line = "";
		String cvsSplitBy = ",";
		double median = (780 - 189)/2;
		try {

			reader = new BufferedReader(new FileReader(csvFile));
			PrintWriter writer = new PrintWriter(csvFile2);
			while ((line = reader.readLine()) != null) {
			    // use comma as separator
				String[] classifier = line.split(cvsSplitBy);
				StringBuilder x = new StringBuilder();
				x.append(classifier[0]);
				x.append(",");
				int foo = Integer.parseInt(classifier[1]);
				if(foo <= 20){
					x.append("<=20");
				}else if(foo > 20){
					x.append(">20");
				}else{
					x.append("No ones go!");
				}
				x.append(",");
				Double doubleVal = Double.parseDouble(classifier[2]);
				if(doubleVal <= median){
					x.append("Short Flight");
				}else{
					x.append("Long Flight");
				}
				x.append(",");
				x.append(classifier[3]);
				x.append("\n");
				writer.write(x.toString());
			    writer.flush();
				System.out.println("done");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Done");
	  }
}
