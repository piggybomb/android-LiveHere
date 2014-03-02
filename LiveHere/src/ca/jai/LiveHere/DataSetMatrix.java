package ca.jai.LiveHere;

import java.util.*;
public class DataSetMatrix {
	private String[][] matrix;
	
	public DataSetMatrix(String[][] dataset)
	{
		matrix = dataset;
	}
	
	public ArrayList<Integer> getRent(String year, String type)
	{
		ArrayList<Integer> rentList =  new ArrayList<Integer>();
		int value;
		for (int i = 0; i < matrix.length; i++ )
		{
			if (matrix[i][0].equals(year) && matrix[i][4].equals(type))
			{
				try
				{
					value =  (int) Float.parseFloat(matrix[i][7]);
					if (value > 0)
					{
						rentList.add(value);
					}
				}
				catch(NumberFormatException E)
				{
					//if we get an exception this means we get a useless value that we would not need
					//catch block will remain empty because we do not want to add a value into the array if an exception is thrown
					rentList.add(0);
				}
			}
		}
		
		return rentList;
		
	}
	
	
}
