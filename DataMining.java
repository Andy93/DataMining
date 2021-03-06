package DataMining;

import java.io.*;
import java.util.*;

public class DataMining 
{
	public static void compareCSV() //Creates CSV to store the data in preperation to be trained
	{
		String csvFile = "C:/Users/Andrew/Desktop/DataMining/src/";
		
		BufferedReader distanceAirports = null;
		BufferedReader countAirports = null;
		BufferedReader cityJet = null;
		PrintWriter in = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try
		{
			countAirports = new BufferedReader(new FileReader(csvFile + "Destination_Count.csv"));
			distanceAirports = new BufferedReader(new FileReader(csvFile + "Reachable_Airports.csv"));
			cityJet = new BufferedReader(new FileReader(csvFile + "CityJet.csv"));
			in = new PrintWriter(csvFile + "Competator_Use_Of_Airports_In_Range.csv");
			//in.println("Airport,Number of Flights To,Distance,Do City Jet go here");
			
			
			HashMap<String, String> file1Map = new HashMap<String, String>(); //Creates 3 hashSets to store the 3 CSVs being compared
			HashMap<String, String> file2Map = new HashMap<String, String>();
			HashMap<String, String> file3Map = new HashMap<String, String>();
			
			while ((line = cityJet.readLine()) != null) 
			{
				String[] numOf = line.split(cvsSplitBy);
		
				file3Map.put(numOf[2], numOf[5]);
				
			}
			
			while ((line = distanceAirports.readLine()) != null) 
			{
				String[] canFly = line.split(cvsSplitBy);
				String airport = canFly[0];
				String distance = canFly[1];
				file1Map.put(airport, distance);
			}
			distanceAirports = new BufferedReader(new FileReader(csvFile + "Reachable_Airports.csv"));
			while ((line = countAirports.readLine()) != null) 
			{
				String[] numOf = line.split(cvsSplitBy);
				String key = numOf[0];
				
				
				if (file1Map.containsKey(key) && !numOf[1].equals("Number of Flights To")) 
				{
					if(file3Map.containsKey(numOf[0]))
					{
						
						in.println(numOf[0] + "," +numOf[1] + "," + file1Map.get(key) + ",Y");
					}
						
					else in.println(numOf[0] + "," +numOf[1] + "," + file1Map.get(key) + ",N");
					file2Map.put(numOf[0], numOf[1]);
				}
			}
			
			while ((line = distanceAirports.readLine()) != null) 
			{
				String[] numOf = line.split(cvsSplitBy);
				String key = numOf[0];
				
				if (!file2Map.containsKey(key) && !numOf[0].equals("Airport")) 
				{
					if(file3Map.containsKey(numOf[0]))
					{
						in.println(numOf[0] + ",0" + "," + numOf[1] + ",Y");
					}
					else in.println(numOf[0] + ",0" + "," + numOf[1] + ",N");
				}
			}
			
			
			in.flush();
			in.close();
			
		}
		catch(Exception e)
		{
			System.out.println("Could not find file1");
		}
	}
	public static void countAirports() //A simple program to count how many competitors go to each airport.
	{
		String csvFile = "C:/Users/Andrew/Desktop/DataMining/src/";
		
		
		BufferedReader out = null;
		PrintWriter in = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try
		{
			in = new PrintWriter(csvFile + "Destination_Count.csv"); //Values stored in new CSV
			in.println("Airport,Number of Flights To");
			out = new BufferedReader(new FileReader(csvFile + "CityJet_Competitors.csv"));
			List<String> list = new ArrayList<String>();
			
			while ((line = out.readLine()) != null) 
			{
				String[] flight = line.split(cvsSplitBy);
				
				
				if(!flight[4].equals("To")) //Assures the top line is ignored
				{
					list.add(flight[4]);
				}
			}
			Set<String> unique = new HashSet<String>(list); //Hashset stores values.

			for (String key : unique) 
			{
				in.println(key + "," + Collections.frequency(list, key)); //count the values
				
			}
			in.flush();
			in.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not find file");
		}
		
	}
	public static void getDistance() //This method gets the distance to each Airport
	{
		//The Coordinates from LCY, CityJets main Airport.
		double lcylat = 51.505278;
		double lcylon = 0.055278;
		
		String csvFile = "C:/Users/Andrew/Desktop/DataMining/src/";
		
		BufferedReader out = null;
	
		PrintWriter in = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try
		{
			out = new BufferedReader(new FileReader(csvFile + "airports.csv"));
			
			
			in = new PrintWriter(csvFile + "Reachable_Airports.csv");//creates a csv to store the airports cityJet can reach.
			in.println("Airport,Distance");
			
			while ((line = out.readLine()) != null) 
			{
				try
				{
					String[] flight = line.split(cvsSplitBy);
					if(!flight[6].equals("Latitude"))
					{
					
						String lat = flight[6]; //extracts long and lat to compare to LCY
						String lon = flight[7];
						//Convert longs and lats from file to doubles
						Double dlat = Double.parseDouble(lat);
						Double dlon = Double.parseDouble(lon);
						double distance = distFrom(lcylat, lcylon, dlat, dlon ); //Applies formula.
						//if airport has no name or is outside CityJets max Distance and above their min. 
						if(!flight[4].isEmpty() && distance <= 740 && distance >= 189)//740 is longest flight CityJet can do
						{
							in.println(flight[4]+ ","  + distance); //Stores the flights within CityJets limits
						}
					}
				}
				catch(Exception e)
				{
					//In case of strange data that stops the code running
					System.out.println("Problem Reading, Unknown origin"); //Certain data may slow the process so continue if code stumbles
					continue;
				}
			}
			in.flush();
			in.close();
		}
		catch(Exception e)
		{
			System.out.println("Could not find file");
		}
	}

	//formula for distance between Long and Lat.
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) 
	{
	    double earthRadius = 3958.75; // miles (or 6371.0 kilometers)
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist;
	} 

	public void run() { //fills in the missing Values for Data Cleaning
		String csvFile = "C:/Users/Andrew/Desktop/DataMining/src/routes.csv";
		String csvFile2 = "C:/Users/Andrew/Desktop/DataMining/src/fixedRoutes.csv";
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
	
	public void run2() { // takes in the competitors usage of airports and returns airports to be used in the classifier
		String csvFile = "/Users/Andrew/Desktop/DataMining/src/Competator_Use_Of_Airports_In_Range.csv";
		String csvFile2 = "/Users/Andrew/Desktop/DataMining/src/classifier.csv";
		BufferedReader reader = null;
		String line = "";
		String cvsSplitBy = ",";
		double midRange = (780 + 189)/2;
		
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
				if(foo <= 20 && foo >=1){
					x.append("<=20");
				}else if(foo > 20){
					x.append(">20");
				}else{
					x.append("No one goes!");
				}
				x.append(",");
				Double doubleVal = Double.parseDouble(classifier[2]);
				if(doubleVal <= midRange){
					x.append("Short Flight");
				}else{
					x.append("Long Flight");
				}
				x.append(",");
				if(classifier[3].equals("Y")){
					x.append(classifier[3]);
				}else if(classifier[0].equals("CDG")||classifier[0].equals("FRA")||classifier[0].equals("MUC")||classifier[0].equals("BCN")||classifier[0].equals("CPH")||classifier[0].equals("ZRH")||classifier[0].equals("OSL")||classifier[0].equals("BRU")||classifier[0].equals("DUS")||classifier[0].equals("TXL")||classifier[0].equals("MXP")){
					x.append("N");
				}else{
					x.append("?");
				}
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
	
	public void run3() { // uses the classifier airports to return a list of prediction airports
		String csvFile1 = "/Users/Andrew/Desktop/DataMining/src/classifier.csv";
		String csvFile2 = "/Users/Andrew/Desktop/DataMining/src/Prediction.csv";
		BufferedReader reader = null;
		String line = "";
		String cvsSplitBy = ",";
		try {

			reader = new BufferedReader(new FileReader(csvFile1));
			PrintWriter writer = new PrintWriter(csvFile2);
			while ((line = reader.readLine()) != null) {
			    // use comma as separator
				String[] prediction = line.split(cvsSplitBy);
				StringBuilder x = new StringBuilder();
				if(prediction[1].equals("<=20")&&prediction[2].equals("Short Flight")&&prediction[3].equals("?")){
					x.append(prediction[0]);
					x.append(",");
					x.append(prediction[1]);
					x.append(",");
					x.append(prediction[2]);
					x.append(",");
					x.append(prediction[3]);
					x.append("\n");
				}
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
	public void runCompetitors() { // takes from the file of routes and supplies back a csv of selected competitor airlines of CityJet
		String csvFile = "/Users/Andrew/Desktop/DataMining/src/fixedRoutes.csv";
		String csvFile2 = "/Users/Andrew/Desktop/DataMining/src/CityJet_Competitors.csv";
		BufferedReader reader = null;
		String line = "";
		String cvsSplitBy = ",";
		try {

			reader = new BufferedReader(new FileReader(csvFile));
			PrintWriter writer = new PrintWriter(csvFile2);
			int BAcount =0;
			int EIcount =0;
			int FRcount =0;
			int U2count =0;
			while ((line = reader.readLine()) != null) {

			    // use comma as separator
				String[] Merging = line.split(cvsSplitBy);
				StringBuilder x = new StringBuilder();
				if(Merging[0].equals("EI")){
					x.append(Merging[0]);
					x.append(",");
					x.append(Merging[1]);
					x.append(",");
					x.append(Merging[2]);
					x.append(",");
					x.append(Merging[3]);
					x.append(",");
					x.append(Merging[4]);
					x.append(",");
					x.append(Merging[6]);
					x.append(",");
					x.append(Merging[7]);
					x.append(",");
					x.append(Merging[8]);
					x.append("\n");
					EIcount++;
				}else if(Merging[0].equals("FR")){
					x.append(Merging[0]);
					x.append(",");
					x.append(Merging[1]);
					x.append(",");
					x.append(Merging[2]);
					x.append(",");
					x.append(Merging[3]);
					x.append(",");
					x.append(Merging[4]);
					x.append(",");
					x.append(Merging[6]);
					x.append(",");
					x.append(Merging[7]);
					x.append(",");
					x.append(Merging[8]);
					x.append("\n");
					FRcount++;
				}else if(Merging[0].equals("BA")){
					x.append(Merging[0]);
					x.append(",");
					x.append(Merging[1]);
					x.append(",");
					x.append(Merging[2]);
					x.append(",");
					x.append(Merging[3]);
					x.append(",");
					x.append(Merging[4]);
					x.append(",");
					x.append(Merging[6]);
					x.append(",");
					x.append(Merging[7]);
					x.append(",");
					x.append(Merging[8]);
					x.append("\n");
					BAcount++;
				}else if(Merging[0].equals("U2")){
					x.append(Merging[0]);
					x.append(",");
					x.append(Merging[1]);
					x.append(",");
					x.append(Merging[2]);
					x.append(",");
					x.append(Merging[3]);
					x.append(",");
					x.append(Merging[4]);
					x.append(",");
					x.append(Merging[6]);
					x.append(",");
					x.append(Merging[7]);
					x.append(",");
					x.append(Merging[8]);
					x.append("\n");
					U2count++;
				}
				writer.write(x.toString());
			    writer.flush();
			    
			
				
				
			}
			System.out.println("BA count:" + BAcount);
			System.out.println("FR count:" + FRcount);
			System.out.println("EI count:" + EIcount);
			System.out.println("U2 count:" + U2count);
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
	public static void main(String [] args)
	{
		DataMining w = new DataMining();
		w.run();
		DataMining wz = new DataMining();
		wz.runCompetitors();
		countAirports();
		getDistance();
		compareCSV();
		DataMining x = new DataMining();
		x.run2();
		//BayesNavie.java was Run here to make prediction.
		DataMining y = new DataMining();
		y.run3();

	}
}
