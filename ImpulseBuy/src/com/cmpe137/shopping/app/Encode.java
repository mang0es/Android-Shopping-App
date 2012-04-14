package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
public class Encode extends Activity {

	private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
	private boolean image = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.encode);
		
		//Get Spinner instance
		final Spinner spinner = (Spinner) findViewById(R.id.spin_url);
		
		//"Encode" button
		final Button button = (Button) findViewById(R.id.button_encode);
		//Set action to button
		button.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Is there something to encode?
				String code = ( (EditText) findViewById(R.id.txt_code) ).getText().toString();
				if( 0==code.trim().length() ) {
					Toast.makeText( Encode.this , R.string.enter_code, Toast.LENGTH_SHORT ).show();
					return;
				}
				
				//Create a new Intent to send to QR Droid
				Intent qrDroid = new Intent( Services.ENCODE); //Set action "la.droid.qr.encode"
				
				//Set text to encode
				qrDroid.putExtra( Services.CODE , code);
				
				//Check whether an URL or an imge is required
				if( spinner.getSelectedItemId()==0 ) { //First item selected ("Get Bitmap")
					//Notify we want complete results (default is FALSE)
					image = true;
					qrDroid.putExtra( Services.IMAGE , true);
					//Optionally, set requested image size. 0 means "Fit Screen"
					qrDroid.putExtra( Services.SIZE , 0 );
				} else {
					image = false;
				}
				
				//Send intent and wait result
				try {
					startActivityForResult(qrDroid, ACTIVITY_RESULT_QR_DRDROID);
				} catch (ActivityNotFoundException activity) {
					Services.qrDroidRequired(Encode.this);
				}
			}
		});
	}
	
	@Override
	/**
	 * Reads generated QR code
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if( ACTIVITY_RESULT_QR_DRDROID==requestCode && null!=data && data.getExtras()!=null ) {
			//Read result from QR Droid (it's stored in la.droid.qr.result)
			//Result is a string or a bitmap, according what was requested
			ImageView imgResult = ( ImageView ) findViewById(R.id.img_result);
			EditText txtResult = ( EditText ) findViewById(R.id.txt_result);
			
			if( image ) {
				String qrCode = data.getExtras().getString( Services.RESULT );
				
				//If image path was not returned, it could not be saved. Check SD card is mounted and is writable
				if( null==qrCode || 0==qrCode.trim().length() ) {
					Toast.makeText( Encode.this , R.string.not_saved, Toast.LENGTH_LONG).show();
					return;
				}
				
				//Show success message
				Toast.makeText( Encode.this , getString(R.string.saved) + " " + qrCode, Toast.LENGTH_LONG).show();
				
				//Load QR code image from given path
				imgResult.setImageURI( Uri.parse(qrCode) );
				
				imgResult.setVisibility( View.VISIBLE );
				txtResult.setVisibility( View.GONE );
				
				//TODO: After using this QR code, you should move it to a permanent location, or delete it
			} else {
				String result = data.getExtras().getString(Services.RESULT);
				//Just set result to EditText to be able to view it
				txtResult.setText( result );
				txtResult.setVisibility( View.VISIBLE );
				imgResult.setVisibility( View.GONE );
			}
		}
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	//Nothing
    }
}
