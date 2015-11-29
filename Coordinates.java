// pulling latitude and longtitude for clustering of airports

package Coordinates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Coordinates {
	public static void main(String[] args) {
		Coordinates obj = new Coordinates();
		obj.run();
	}

	public void run() {
		String csvFile = "/Users/Andrew/Desktop/airports.csv";
		String csvFile2 = "/Users/Andrew/Desktop/Clustering.txt";
		BufferedReader reader = null;
		String line = "";
		String cvsSplitBy = ",";
		try {

			reader = new BufferedReader(new FileReader(csvFile));
			PrintWriter writer = new PrintWriter(csvFile2);
			while ((line = reader.readLine()) != null) {

			    // use comma as separator
				String[] Coordinate = line.split(cvsSplitBy);
				StringBuilder x = new StringBuilder();
				x.append(Coordinate[6]);
				x.append(",");
				x.append(Coordinate[7]);
				x.append("\n");
				writer.write(x.toString());
			    writer.flush();
			    
				System.out.println("Airport [Latitude = " + Coordinate[6] + " , Longtitude=" + Coordinate[7] + "]");
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
