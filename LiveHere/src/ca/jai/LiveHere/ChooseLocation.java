package ca.jai.LiveHere;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ChooseLocation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_choose_location);
		
		setupProvinceSpinner();
		setupCHooseCityButton();
	}

	private void setupProvinceSpinner() {
		final Spinner spinnerProvince = (Spinner)findViewById(R.id.provinceSpinner);
		spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int positon, long arg3) {
				spinnerProvince.setSelection(spinnerProvince.getSelectedItemPosition());
				SharedPreferences provinceSharedPref = getSharedPreferences("province", MODE_PRIVATE);
				SharedPreferences.Editor provinceEditor = provinceSharedPref.edit();
				provinceEditor.putString("province", (String)spinnerProvince.getItemAtPosition(positon));
				provinceEditor.commit();
				ChooseLocation.this.setProgressBarIndeterminateVisibility(true);
				setupCitySpinner();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private void setupCitySpinner() {
		final Spinner spinnerCity = (Spinner)findViewById(R.id.citySpinner);
	
		getData daData = new getData();
		daData.execute();

		spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int positon, long arg3) {
				spinnerCity.setSelection(spinnerCity.getSelectedItemPosition());
				SharedPreferences citySharedPref = getSharedPreferences("city", MODE_PRIVATE);
				SharedPreferences.Editor cityEditor = citySharedPref.edit();
				cityEditor.putString("city", (String)spinnerCity.getItemAtPosition(positon));
				cityEditor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private void setupCHooseCityButton() {
		Button getStatButton = (Button)findViewById(R.id.getStatButton);
		getStatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ChooseLocation.this, Stat.class));
			}
		});
	}
	
	private class getData extends AsyncTask<String[], String, String> {

		@Override
		protected String doInBackground(String[] ...params) {
			String output;
			try 
			{
				output= readJ.readURL("http://namara.io/api/v0/resources/1f1db6c6-15c9-"
						+ "430a-bfe8-c3c2eddadb23/data/aggregate?api_key=e53683a95b14f1" +
						"34f71a5009a33d6b03c7ed5accb994c9434155c0a95c273bb2&aggregate=%" +
						"7B%22column%22:1,%22operation%22:%22distinct%22");
			}
			catch(Exception E)
			{
				output = "N/A";
			}
			publishProgress(output);
			return output;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ChooseLocation.this.setProgressBarIndeterminateVisibility(false);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			SharedPreferences prov = getSharedPreferences("province", MODE_PRIVATE);
			String province = prov.getString("province", "n/a");
			String[] cities = readJ.extractCities(values[0], province);
			
			SharedPreferences citySharedPref = getSharedPreferences("city", MODE_PRIVATE);
			SharedPreferences.Editor cityEditor = citySharedPref.edit();
			cityEditor.putString("city", cities[0]);
			cityEditor.commit();
			
			final Spinner spinnerCity = (Spinner)findViewById(R.id.citySpinner);
			ArrayAdapter<String> citySpinnerArray = new ArrayAdapter<String>
					(ChooseLocation.this, android.R.layout.simple_spinner_dropdown_item, cities);
			spinnerCity.setAdapter(citySpinnerArray);
		}
	}
}
