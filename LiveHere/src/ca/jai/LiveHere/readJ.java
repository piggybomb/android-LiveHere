package ca.jai.LiveHere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Arrays;

import android.util.Log;

public class readJ {
	public static String readURL(String url) throws IOException
	{
		URL link = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream(),"UTF-8"));

		String inputLine = in.readLine();
				
		return inputLine;
	}
	
	//Enter city and province from user input and request data from API
	public static String[][] getData(String city, String province) throws IOException
	{
		Calendar today = Calendar.getInstance();
		int upperYear = today.get(Calendar.YEAR);
		int lowerYear = upperYear - 4;
		String CityData = "";
		int page = 1;
		
		String[][] bigDataSet = new String[0][0];
		
		
		String upperYearStr = String.valueOf(upperYear);
		String lowerYearStr = String.valueOf(lowerYear);
		
		
		String geo_loc = city + ", " + province; //concatenate city and province to be used as location
		String urlLocation = geo_loc.replace(" ", "%20"); //replace whitespace with %20 for encoding
		while (!CityData.equals("[]")) {
			String strPage = Integer.toString(page);
			String urlString = "http://namara.io/api/v0/resources/1f1db6c6-15c9-430a-bfe8-c3c2eddadb23/data?api_key=e53683a95b14f134f71a5009a33d6b03c7ed5accb994c9434155c0a95c273bb2&where="
					+ "[%7B\"column\":7,\"selector\":\"gt\",\"value\":0%7D,%7B\"column\":1,\"selector\":\"eq\",\"value\":\"" + urlLocation + "\"%7D,%7B\"column\":0,\"selector\":\"lt\",\"value\":\"" + upperYearStr
					+ "\"%7D,%7B\"column\":0,\"selector\":\"gte\",\"value\":\"" + lowerYearStr + "\"%7D]&page=" + strPage + "\"";
			CityData = readURL(urlString);

			//Code to parse each page goes here
			if (!CityData.equals("[]"))
			{
				bigDataSet = append(bigDataSet,parseOutput(CityData,8));
			}
			System.out.println(Arrays.deepToString(bigDataSet));
			page++;
		}
		return bigDataSet;
	}
	
	//helper function for appending nested arrays
    public static String[][] append(String[][] a, String[][] b) 
    {
        String[][] result = new String[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
    //use regexes to remove unnecessary clutter from our result
    //returns array of the cities
	public static String[] extractCities(String result, String province)
	{
		//result = result.substring(12);
		//Log.i("ca.jai.LiveHere", "hi");
		try{
			result = result.replaceFirst("\\[\\{\"result\"\\:\\[\"", "");
			result = result.replaceFirst("\"]}]", "");
		}catch(Exception E) {
			Log.i("ca.jai.LiveHere", E.toString());
		}
		
		String[] tempArray = result.split("\",\"");	
		int size = 0;
		
		for (int i=0; i < tempArray.length; i++)
		{
			if (tempArray[i].contains(province))
			{
				//cityArray[counter] = tempArray[i].replace(", " + province, "");
				size++;
			}
		}
		String[] cityArray = new String[size];
		int counter = 0;
		for (int i=0; i < tempArray.length; i++)
		{
			if (tempArray[i].contains(province))
			{
				cityArray[counter] = tempArray[i].replace(", " + province, "");
				counter++;
			}
		}
		
		
		return cityArray;
	}
	
	//converts the bigass string received from the API into a bigass nested array
	public static String[][] parseOutput(String sample, int sampleLen)
	{
		sample = sample.substring(2, sample.length()-2);
		String[] foo = sample.split("\\],\\[");
		String[][] bar = new String[foo.length][sampleLen-1];
		for (int i=0; i < foo.length; i++)
		{
			bar[i] = foo[i].split("\",\"|,\"|\",");

		}

		
		return bar;
	}
	
	
	
	
//	public static void main(String [ ] args) throws IOException
//	{
//		String output = readURL("http://namara.io/api/v0/resources/1f1db6c6-15c9-430a-bfe8-c3c2eddadb23/data/aggregate?api_key=e53683a95b14f134f71a5009a33d6b03c7ed5accb994c9434155c0a95c273bb2&aggregate=%7B%22column%22:1,%22operation%22:%22distinct%22");
//		
//		String[] testList = extractCities(output);
//		
//		for (int i=0; i < testList.length; i++)
//		{
//			System.out.println(testList[i]);
//		
//		}
		//&where=[%7B%22column%22:1,%22selector%22:%22eq%22,%22value%22:%2259913%22%7D]
		
//		Province testProv = new Province("BC");
//		System.out.println(testProv.getProvName());
//		testProv.addCity("Ditchmond");
//		testProv.addCity("Burnaby");
//		testProv.addCity("Surrey");
//		System.out.println(testProv.getCityList());
		
		//parseOutput("[[a,s],[d,f],[q,w],[e,r],[7,y]]",2);
		
//		getData("Corner Brook", "Newfoundland and Labrador");
		
		//System.out.print(Integer.parseInt("435.0"));

//	}
}


