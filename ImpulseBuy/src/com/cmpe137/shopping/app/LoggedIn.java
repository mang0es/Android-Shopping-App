package com.cmpe137.shopping.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoggedIn extends Activity{
	private static final int RECOGNIZER_EXAMPLE = 1001;
	private static final int QRSCANNER_EXAMPLE = 1003;
	private EditText searchBox;
	private Toast toaster;
	private LoggedIn loggedin = this;
	private Button myAccount;
	private Button savedItems;
	private Button browse;
	String currentuser;
	private TextView useremail;
	public void onCreate(Bundle savedInstanceState) {
		    
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loggedinscreen);
		
		Intent intent = getIntent();
		currentuser = intent.getStringExtra("useremail");
		
		useremail = (TextView) findViewById(R.id.UserEmail);
		useremail.setText("Logged in as: " + currentuser);
		ImageButton speakButton = (ImageButton) findViewById(R.id.Speak);
	        
		speakButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// RecognizerIntent prompts for speech and returns text
				try
				{
					Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);			
					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to search!");
					startActivityForResult(intent, RECOGNIZER_EXAMPLE);
				}
				catch (ActivityNotFoundException e)
				{
					toaster.makeText(loggedin, "Speech Recognizer not found!\nInstall from Android Market", Toast.LENGTH_LONG).show();
				}
			}
		});
		searchBox = (EditText) findViewById(R.id.SearchBox);
        
        Button searchButton = (Button) findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener()
        {	
			@Override
			public void onClick(View v) {
				startSearch();
			}
		});
        
        Button qrButton = (Button) findViewById(R.id.QRButton);
        qrButton.setOnClickListener(new View.OnClickListener()
        {	
			@Override
			public void onClick(View v) {
				startQR();
			}
		});	       
        
        myAccount = (Button) findViewById(R.id.MyAccountButton);
        myAccount.setOnClickListener(new View.OnClickListener() 
        {	
			@Override
			public void onClick(View v) {
				startMyAccount();
			}
		});
        
        savedItems = (Button) findViewById(R.id.SavedItems);
        savedItems.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSavedItems();
			}
		});
        
        browse = (Button) findViewById(R.id.Browse);
        browse.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startBrowse();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode==RECOGNIZER_EXAMPLE && resultCode==RESULT_OK)
		{
			ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			if (!result.isEmpty())
				searchBox.setText(result.get(0));
		}
		else if (requestCode==QRSCANNER_EXAMPLE && resultCode==RESULT_OK)
		{
			//Read result from QR Droid (it's stored in la.droid.qr.result)
			String result = data.getExtras().getString(Services.RESULT);
			//toaster.makeText(welcome, result, Toast.LENGTH_LONG).show();
			if (result != null)
				searchBox.setText(result);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void startSearch()
	{
		try
		{
			Intent search = new Intent(this, Search.class);
			search.putExtra("searchInput", searchBox.getText().toString());
			startActivity(search);
		}
		catch (ActivityNotFoundException afne)
		{
			toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
		}
	}
	private void startMyAccount()
	{
		try
		{
			Intent myaccount = new Intent(this, MyAccount.class);
			myaccount.putExtra("currentemail", currentuser);
			startActivity(myaccount);
		}
		catch (ActivityNotFoundException afne)
		{
			toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
		}
	}
	private void startSavedItems()
	{
		try
		{
			Intent saveditems = new Intent(this, SavedItems.class);
			startActivity(saveditems);
		}
		catch (ActivityNotFoundException afne)
		{
			toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
		}
	}
	private void startBrowse()
	{
		try
		{
			Intent browse = new Intent(this, Browse.class);
			startActivity(browse);
		}
		catch (ActivityNotFoundException afne)
		{
			toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
		}
	}
	protected void startQR()
    {
		try
		{
			Intent qrscan = new Intent(Services.SCAN);
			startActivityForResult(qrscan, QRSCANNER_EXAMPLE);
		}
		catch (ActivityNotFoundException activity)
		{
			Services.qrDroidRequired(LoggedIn.this);
		}
    }
	
}
