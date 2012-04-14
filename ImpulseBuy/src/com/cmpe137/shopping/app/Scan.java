package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Shows three Tabs with options to Scan, Decode and Encode QR codes using
 * services provided by "QR Droid"
 * 
 * _________________________________________________________________________________
 * This is part of "QRDroidServices", by DroidLa. If you're creating an Android app
 * which uses one or more services provided by "QR Droid", you can use this code for
 * free, and modify it as you need, for personal and commercial use.
 * 
 * Any other use of this code is forbidden.
 * 
 * @author DroidLa
 * @version 1.0
 */
public class Scan extends Activity {

	private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.scan);
		
		//Get Spinner instance
		final Spinner spinner = (Spinner) findViewById(R.id.spin_complete);
		
		//"Scan" button
		final Button button = (Button) findViewById(R.id.button_scan);
		//Set action to button
		button.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Create a new Intent to send to QR Droid
				Intent qrDroid = new Intent( Services.SCAN ); //Set action "la.droid.qr.scan"
				
				//Check whether a complete or displayable result is needed
				if( spinner.getSelectedItemId()==0 ) { //First item selected ("Complete content")
					//Notify we want complete results (default is FALSE)
					qrDroid.putExtra( Services.COMPLETE , true);
				}
				
				//Send intent and wait result
				try {
					startActivityForResult(qrDroid, ACTIVITY_RESULT_QR_DRDROID);
				} catch (ActivityNotFoundException activity) {
					Services.qrDroidRequired(Scan.this);
				}
			}
		});
	}

	@Override
	/**
	 * Reads data scanned by user and returned by QR Droid
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if( ACTIVITY_RESULT_QR_DRDROID==requestCode && null!=data && data.getExtras()!=null ) {
			//Read result from QR Droid (it's stored in la.droid.qr.result)
			String result = data.getExtras().getString(Services.RESULT);
			//Just set result to EditText to be able to view it
			EditText resultTxt = ( EditText ) findViewById(R.id.result);
			resultTxt.setText( result );
			resultTxt.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	//Nothing
    }
}
