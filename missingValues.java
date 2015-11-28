package missingValues;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class missingValues {
	public static void main(String[] args) {
		missingValues obj = new missingValues();
		obj.run();
	}

	public void run() {
		String csvFile = "/Users/Andrew/Desktop/routes.csv";
		String csvFile2 = "/Users/Andrew/Desktop/fixedRoutes.csv";
		BufferedReader reader = null;
		String line = "";
		String cvsSplitBy = ",";
		String GLOBAL_CONSTANT = "N";
		try {

			reader = new BufferedReader(new FileReader(csvFile));
			PrintWriter writer = new PrintWriter(csvFile2);
			while ((line = reader.readLine()) != null) {

			    // use comma as separator
				String[] routes = line.split(cvsSplitBy);
				if(routes[6].equals("")){
					routes[6] = GLOBAL_CONSTANT;
				}
				
				StringBuilder x = new StringBuilder();
				x.append(routes[0]);
				x.append(",");
				x.append(routes[1]);
				x.append(",");
				x.append(routes[2]);
				x.append(",");
				x.append(routes[3]);
				x.append(",");
				x.append(routes[4]);
				x.append(",");
				x.append(routes[5]);
				x.append(",");
				x.append(routes[6]);
				x.append(",");
				x.append(routes[7]);
				x.append(",");
				x.append(routes[8]);
				x.append("\n");
				writer.write(x.toString());
			    writer.flush();
			    
				System.out.println("Route [Airline = " + routes[0] 
	                                 + " , Airline ID=" + routes[1] + "" + " , Source Airport =" + routes[2] + "" + " , Source Airport ID =" + routes[3] + "" + " , Destination Airport =" + routes[4] + "" + " , Destination Airport ID =" + routes[5] + ""+ " , Codeshare =" + routes[6] + ""+ " , Stops =" + routes[7] + ""+ " , Equipment =" + routes[8] + "]");
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
