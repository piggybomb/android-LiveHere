package ca.jai.LiveHere;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupGpsButton();
        setupChooseButton();
    }

	private void setupGpsButton() {
		Button gpsButton = (Button)findViewById(R.id.gpsButton);
        gpsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Stat.class));
			}
		});
	}
	
	private void setupChooseButton() {
		Button chooseButton = (Button)findViewById(R.id.chooseButton);
        chooseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ChooseLocation.class));
			}
		});
	}
}
