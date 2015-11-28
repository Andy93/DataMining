package scraper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
public class scraper {
	public static void main(String[] args) {

		scraper obj = new scraper();
		obj.run();

	  }

	  public void run() {
		//C:\Users\Andrew\Desktop
		String csvFile = "/Users/Andrew/Desktop/airports.csv";
		String csvFile2 = "/Users/Andrew/Desktop/test.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String GLOBAL_CONSTANT = "N/A";
		
		try {

			br = new BufferedReader(new FileReader(csvFile));
			PrintWriter writer = new PrintWriter(csvFile2);
			while ((line = br.readLine()) != null) {

			// use comma as separator
				String[] country = line.split(cvsSplitBy);

				
				if(country[0].equals("")){
					country[0] = GLOBAL_CONSTANT;
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append(country[0]);
				sb.append(",");
				sb.append(country[1]);
				sb.append(",");
				sb.append(country[2]);
				sb.append(",");
				sb.append(country[3]);
				sb.append(",");
				sb.append(country[4]);
				sb.append(",");
				sb.append(country[5]);
				sb.append(",");
				sb.append(country[6]);
				sb.append(",");
				sb.append(country[7]);
				sb.append(",");
				sb.append(country[8]);
				sb.append(",");
				sb.append(country[9]);
				sb.append(",");
				sb.append(country[10]);
				sb.append(",");
				sb.append(country[11]);
				sb.append(" \n");
				writer.write(sb.toString());
			    writer.flush();
			    
				System.out.println("Airport [Airport ID= " + country[0] 
	                                 + " , Name=" + country[1] + "" + " , City=" + country[2] + "" + " , Country=" + country[3] + "" + " , IATA/FAA=" + country[4] + "" + " , ICAO=" + country[5] + ""+ " , Latitude=" + country[6] + ""+ " , Longtitude=" + country[7] + ""+ " , Altitude=" + country[8] + ""+ " , Timezone=" + country[9] + ""+ " , DST=" + country[10] + ""+ " , Database Timezone=" + country[11] + "]");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	  }

}
