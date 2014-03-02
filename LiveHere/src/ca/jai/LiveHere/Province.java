package ca.jai.LiveHere;

import java.util.*;
public class Province 
{
	private String provName;
	private ArrayList<String> cityList = new ArrayList<String>();  
	public Province(String name)
	{
		provName = name;
	}
	
	public String getProvName()
	{
		return provName;
	}
	public ArrayList<String> getCityList()
	{
		return cityList;
	}
	
	public void addCity(String cityName)
	{
		cityList.add(cityName);

	}
	public void sortList()
	{
		Collections.sort(cityList);		
	}
	
}
