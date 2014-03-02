package ca.jai.LiveHere;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

public class Stat extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		setupProvinceText();
		setupCityText();
		//TableLayout rentTable = (TableLayout)findViewById(R.id.rentTableLayout);
		setXAxis();
		
		SharedPreferences prov = getSharedPreferences("province", MODE_PRIVATE);
		String province = prov.getString("province", "n/a");
		
		SharedPreferences city = getSharedPreferences("city", MODE_PRIVATE);
		String citay = city.getString("city", "n/a");
		Log.i("ca.jai.LiveHere", province+citay);
		dataCalculator dataGetter = new dataCalculator();
		dataGetter.execute(province, citay);
//		DataSetMatrix bigData = null;
//		try {
//			bigData = new DataSetMatrix(readJ.getData(citay, province));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ArrayList <Integer> daVault = bigData.getRent("2010", "One bedroom units");
//		String range = "";
//		if(compare(daVault.get(0), daVault.get(1)) > 0) {
//			range = range + "$" + daVault.get(1) + " - $" + daVault.get(0);
//		} else {
//			range = range + "$" + daVault.get(0) + " - $" + daVault.get(1);
//		}
//		
//		TextView year1OneBed = (TextView)findViewById(R.id.year1OneBed);
//		year1OneBed.setText(range);
	}

	private void setXAxis() {
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		//int lowerYear = upperYear - 4;
		TextView year1 = (TextView)findViewById(R.id.year1);
		year1.setText(String.valueOf(year - 4));
		TextView year2 = (TextView)findViewById(R.id.year2);
		year2.setText(String.valueOf(year - 3));
		TextView year3 = (TextView)findViewById(R.id.year3);
		year3.setText(String.valueOf(year - 2));
		TextView year4 = (TextView)findViewById(R.id.year4);
		year4.setText(String.valueOf(year - 1));
	}

	private void setupProvinceText() {
		TextView province = (TextView) findViewById(R.id.provinceText);
		String currentCity = (String)getResources().getString(R.string.province_text);
		SharedPreferences provinceSharedPref = getSharedPreferences("province", MODE_PRIVATE);
		currentCity += provinceSharedPref.getString("province", "none");
		province.setText(currentCity);
	}
	
	private void setupCityText() {
		TextView city = (TextView) findViewById(R.id.cityText);
		String currentCity = (String)getResources().getString(R.string.city_text);
		SharedPreferences citySharedPref = getSharedPreferences("city", MODE_PRIVATE);
		currentCity += citySharedPref.getString("city", "none");
		city.setText(currentCity);
	}
	
	//return positive if value1 > value2
	//return negative if value1 < value2
	//return zero if value1 = value2
	private int compare(int value1, int value2) {
		return value1 - value2;
	}
	
	private class dataCalculator extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			DataSetMatrix bigData = null;
			try {
				bigData = new DataSetMatrix(readJ.getData(params[1], params[0]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList <Integer> daVault = bigData.getRent("2010", "One bedroom units");
			String range = "";
			if(compare(daVault.get(0), daVault.get(1)) > 0) {
				range = range + "$" + daVault.get(1) + " - $" + daVault.get(0);
			} else {
				range = range + "$" + daVault.get(0) + " - $" + daVault.get(1);
			}
			
			DataSetMatrix bigData2 = null;
			try {
				bigData2 = new DataSetMatrix(readJ.getData(params[1], params[0]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList <Integer> daVault2 = bigData2.getRent("2010", "Two bedroom units");
			String range2 = "";
			if(compare(daVault2.get(0), daVault2.get(1)) > 0) {
				range2 = range2 + "$" + daVault2.get(1) + " - $" + daVault2.get(0);
			} else {
				range2 = range2 + "$" + daVault2.get(0) + " - $" + daVault2.get(1);
			}
			
			publishProgress(range, range2);
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			TextView year1OneBed = (TextView)findViewById(R.id.year1OneBed);
			year1OneBed.setText(values[0]);
			TextView year1TwoBed = (TextView)findViewById(R.id.year1TwoBed);
			year1TwoBed.setText(values[1]);
		}
	}
}
