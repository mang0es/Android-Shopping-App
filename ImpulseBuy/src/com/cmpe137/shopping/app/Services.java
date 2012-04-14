package com.cmpe137.shopping.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TabHost;

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
public class Services extends TabActivity {

	//Actions
	public static final String SCAN = "la.droid.qr.scan";
	public static final String ENCODE = "la.droid.qr.encode";
	public static final String DECODE = "la.droid.qr.decode";

	//Parameters
	//SCAN / DECODE
	public static final String COMPLETE = "la.droid.qr.complete"; //Default: false
	//ENCODE
	public static final String CODE =  "la.droid.qr.code"; //Required
	public static final String SIZE = "la.droid.qr.size"; //Default: Fit screen
	//ENCODE / DECODE
	public static final String IMAGE =  "la.droid.qr.image"; //Default for encode: false / Required for decode

	//Result
	public static final String RESULT = "la.droid.qr.result";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        
        //Recycled objects
	    Resources res = getResources();
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;
 
	    //Scan Activity
	    intent = new Intent().setClass(this, Scan.class);
	    spec = tabHost.newTabSpec("Scan").setIndicator("", res.getDrawable(R.drawable.camera)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    //Encode Activity
	    intent = new Intent().setClass(this, Encode.class);
	    spec = tabHost.newTabSpec("Encode").setIndicator("", res.getDrawable(R.drawable.text)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    //Decode Activity
	    intent = new Intent().setClass(this, Decode.class);
	    spec = tabHost.newTabSpec("Decode").setIndicator("", res.getDrawable(R.drawable.image)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    //Show DEMO alert dialog
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage( getString(R.string.demo) )
		       .setCancelable(true)
		       .setNegativeButton( R.string.close, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       })
		       .setNeutralButton(  R.string.source, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( getString(R.string.url_source) ) ) );
					}
				})
		       .setPositiveButton( R.string.qrDroid, new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int id) {
		    		   try {
		    			   startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( getString(R.string.url_market) ) ) );
		    			   finish();
		    		   } catch (ActivityNotFoundException e) {
		    			   startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( getString(R.string.url_direct) ) ) );
		    			   finish();
		    		   }
		           }
		       });
		builder.create().show();
    }

    /**
     * Display a message stating that QR Droid is requiered, and lets the user download it for free
     * @param activity
     */
    public static void qrDroidRequired( final Activity activity ) {
    	//Apparently, QR Droid is not installed, or it's previous to version 3.5
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage( activity.getString(R.string.qrdroid_missing) )
		       .setCancelable(true)
		       .setNegativeButton( activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       })
		       .setPositiveButton( activity.getString(R.string.from_market), new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int id) {
		    		   activity.startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( activity.getString(R.string.url_market) ) ) );
		           }
		       })
	           .setNeutralButton(activity.getString(R.string.direct), new DialogInterface.OnClickListener() {
	        	   public void onClick(DialogInterface dialog, int id) {
	        		   activity.startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( activity.getString(R.string.url_direct) ) ) );
	        	   }
	           });
		builder.create().show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	//Nothing
    }
}