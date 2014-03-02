//package ca.jai.LiveHere;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class MoneyStuff {
//	public ArrayList<Integer> daVault;
//	public String range;
//	//public ArrayList<Integer> daVaultMaxRange;	
//	
//	public MoneyStuff(String city, String province, String year, String type) throws Exception {
//		
//		DataSetMatrix bigData = new DataSetMatrix(readJ.getData(city, province));
//		daVault = bigData.getRent(year, type);
//	
//		//daVaultMaxRange = daMoneyMax;
//	}
//	
//	daVault.
//	
//	//return positive if value1 > value2
//	//return negative if value1 < value2
//	//return zero if value1 = value2
//	private int compare(int value1, int value2) {
//		return value1 - value2;
//	}
//	
////	public String getRange() {
////		String daRange = "";
////		int min = getAverage(daVault);
////		int max = getAverage(daVaultMaxRange);
////		daRange = "$" + min + " - $" + max;
////		return daRange;
////	}
//	
////	private int getAverage(int[] moneyArray) {
////		int count = 0;
////		int sum = 0;
////		for (int cash : moneyArray) {
////			count++;
////			sum += cash;
////		}
////		return sum/count;
////	}
//}
