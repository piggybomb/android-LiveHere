package ca.jai.LiveHere;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Window;
import android.widget.TextView;

public class Stat extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_stat);
		
		setupProvinceText();
		setupCityText();
		setXAxis();
		setupTable();
	}

	private void setXAxis() {
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
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
	
	private void setupTable() {
		SharedPreferences prov = getSharedPreferences("province", MODE_PRIVATE);
		String province = prov.getString("province", "n/a");
		
		SharedPreferences city = getSharedPreferences("city", MODE_PRIVATE);
		String citay = city.getString("city", "n/a");
		
		Stat.this.setProgressBarIndeterminateVisibility(true);
		dataCalculator dataGetter = new dataCalculator();
		dataGetter.execute(province, citay);
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
			DataSetMatrix bigData = null;
			try {
				bigData = new DataSetMatrix(readJ.getData(params[1], params[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}

			String range2010 = getData(bigData, "2010", "One bedroom units");
			String rangeTwo2010 = getData(bigData, "2010", "Two bedroom units");
			String rangeThree2010 = getData(bigData, "2010", "Three bedroom units");
			String rangeFour2010 = getData(bigData, "2010", "Bachelor");
			
			String range2011 = getData(bigData, "2011", "One bedroom units");
			String rangeTwo2011 = getData(bigData, "2011", "Two bedroom units");
			String rangeThree2011 = getData(bigData, "2011", "Three bedroom units");
			String rangeFour2011 = getData(bigData, "2011", "Bachelor");
			
			String range2012 = getData(bigData, "2012", "One bedroom units");
			String rangeTwo2012 = getData(bigData, "2012", "Two bedroom units");
			String rangeThree2012 = getData(bigData, "2012", "Three bedroom units");
			String rangeFour2012 = getData(bigData, "2012", "Bachelor");
			
			String range2013 = getData(bigData, "2013", "One bedroom units");
			String rangeTwo2013 = getData(bigData, "2013", "Two bedroom units");
			String rangeThree2013 = getData(bigData, "2013", "Three bedroom units");
			String rangeFour2013 = getData(bigData, "2013", "Bachelor");

			publishProgress(range2010, rangeTwo2010, rangeThree2010, rangeFour2010,
					range2011, rangeTwo2011, rangeThree2011, rangeFour2011,
					range2012, rangeTwo2012, rangeThree2012, rangeFour2012,
					range2013, rangeTwo2013, rangeThree2013, rangeFour2013);
			
			return null;
		}

		private String getData(DataSetMatrix bigData, String year, String unit) {
			ArrayList <Integer> daVault = bigData.getRent(year, unit);
			daVault.add(0);
			daVault.add(0);
			
			String range = "";
			if(compare(daVault.get(0), daVault.get(1)) > 0) {
				range = range + "$" + daVault.get(1) + " - $" + daVault.get(0);
			} else {
				range = range + "$" + daVault.get(0) + " - $" + daVault.get(1);
			}
			return range;
		}

		@Override
		protected void onPostExecute(String result) {
			Stat.this.setProgressBarIndeterminateVisibility(false);
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			TextView year1OneBed = (TextView)findViewById(R.id.year1OneBed);
			year1OneBed.setText(values[0]);
			TextView year1TwoBed = (TextView)findViewById(R.id.year1TwoBed);
			year1TwoBed.setText(values[1]);
			TextView year1ThreeBed = (TextView)findViewById(R.id.year1ThreeBed);
			year1ThreeBed.setText(values[2]);
			TextView year1Bachelor = (TextView)findViewById(R.id.year1Bachelor);
			year1Bachelor.setText(values[3]);
			
			TextView year2OneBed = (TextView)findViewById(R.id.year2OneBed);
			year2OneBed.setText(values[4]);
			TextView year2TwoBed = (TextView)findViewById(R.id.year2TwoBed);
			year2TwoBed.setText(values[5]);
			TextView year2ThreeBed = (TextView)findViewById(R.id.year2ThreeBed);
			year2ThreeBed.setText(values[6]);
			TextView year2Bachelor = (TextView)findViewById(R.id.year2Bachelor);
			year2Bachelor.setText(values[7]);
			
			TextView year3OneBed = (TextView)findViewById(R.id.year3OneBed);
			year3OneBed.setText(values[8]);
			TextView year3TwoBed = (TextView)findViewById(R.id.year3TwoBed);
			year3TwoBed.setText(values[9]);
			TextView year3ThreeBed = (TextView)findViewById(R.id.year3ThreeBed);
			year3ThreeBed.setText(values[10]);
			TextView year3Bachelor = (TextView)findViewById(R.id.year3Bachelor);
			year3Bachelor.setText(values[11]);
			
			TextView year4OneBed = (TextView)findViewById(R.id.year4OneBed);
			year4OneBed.setText(values[12]);
			TextView year4TwoBed = (TextView)findViewById(R.id.year4TwoBed);
			year4TwoBed.setText(values[13]);
			TextView year4ThreeBed = (TextView)findViewById(R.id.year4ThreeBed);
			year4ThreeBed.setText(values[14]);
			TextView year4Bachelor = (TextView)findViewById(R.id.year4Bachelor);
			year4Bachelor.setText(values[15]);
		}
	}
}
