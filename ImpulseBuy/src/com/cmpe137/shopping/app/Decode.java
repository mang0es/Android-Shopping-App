package com.cmpe137.shopping.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
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
public class Decode extends Activity {

	private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
	private ProgressDialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.decode);
		
		//Get Spinner instance
		final Spinner spinner = (Spinner) findViewById(R.id.spin_complete);
		
		//"Decode" button
		final Button button = (Button) findViewById(R.id.button_decode);
		//Set action to button
		button.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Has the user entered image path?
				String path = ( (EditText) findViewById(R.id.txt_path) ).getText().toString();
				//TODO: This path should not be entered manually by the user!
				
				if( 0==path.trim().length() ) {
					Toast.makeText(Decode.this, getString(R.string.enter_url), Toast.LENGTH_LONG).show();
					return;
				}
				
				//Create a new Intent to send to QR Droid
				Intent qrDroid = new Intent( Services.DECODE ); //Set action "la.droid.qr.decode"
				
				qrDroid.putExtra(Services.IMAGE, path);
				
				//Check whether a complete or displayable result is needed
				if( spinner.getSelectedItemId()==0 ) { //First item selected ("Complete content")
					//Notify we want complete results (default is FALSE)
					qrDroid.putExtra( Services.COMPLETE , true);
				}
				
				//Send intent and wait result
				try {
					startActivityForResult(qrDroid, ACTIVITY_RESULT_QR_DRDROID);
					
					//Wait for result
					if( null==dialog || ! dialog.isShowing() ) {
						dialog = ProgressDialog.show(Decode.this, "", getString(R.string.procesing), true);
						dialog.setCancelable(true);
						dialog.show();
					}
				} catch (ActivityNotFoundException activity) {
					Services.qrDroidRequired(Decode.this);
				}
			}
		});
	}
	
	@Override
	/**
	 * Reads data decoded from image and returned by QR Droid
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		//Close dialog
		if( null!=dialog && dialog.isShowing() ) {
			dialog.cancel();
		}
		
		if( ACTIVITY_RESULT_QR_DRDROID==requestCode && null!=data && data.getExtras()!=null ) {
			//Read result from QR Droid (it's stored in la.droid.qr.result)
			String result = data.getExtras().getString(Services.RESULT);
			
			if( resultCode!=RESULT_OK || null==result || 0==result.length() ) {
				//Image could not been loaded or decoded
				Toast.makeText(Decode.this, R.string.not_decoded, Toast.LENGTH_LONG).show();
				return;
			}
			
			//Just set result to EditText to be able to view it
			( ( EditText ) findViewById(R.id.result) ).setText( result );
		}
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	//Nothing
    }
}
