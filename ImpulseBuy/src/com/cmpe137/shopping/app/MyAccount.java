package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyAccount extends Activity{
	Button update;
	Button transactions;
	Button preferences;
	Toast toaster;
	String currentemail;
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount);
        
        Intent tempIntent = getIntent();
        currentemail = tempIntent.getStringExtra("currentemail");
        
        update = (Button) findViewById(R.id.Update);
        update.setOnClickListener(new View.OnClickListener() 
        {	
			@Override
			public void onClick(View v) {
				startUpdate();
			}
		});
        
        transactions = (Button) findViewById(R.id.Transactions);
        transactions.setOnClickListener(new View.OnClickListener() 
        {	
			@Override
			public void onClick(View v) {
				startTransactions();
			}
		});
        
       /* preferences = (Button) findViewById(R.id.Preferences);
        preferences.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) {
				startPreferences();
			}
		});*/
	}
	protected void startUpdate()
	{
		try
		{
			Intent update = new Intent(this, Update.class);
			update.putExtra("currentemail", currentemail);
			startActivity(update);
		}
		catch (ActivityNotFoundException anfe)
		{
			toaster.makeText(this, "Update activity not found!", Toast.LENGTH_SHORT).show();
		}
	}
	protected void startTransactions()
	{
		try
		{
			Intent transactions = new Intent(this, Transactions.class);
			transactions.putExtra("currentemail", currentemail);
			startActivity(transactions);
		}
		catch (ActivityNotFoundException anfe)
		{
			toaster.makeText(this, "Transaction activity not found!", Toast.LENGTH_SHORT).show();
		}
	}
	/*protected void startPreferences()
	{
		try
		{
			Intent preferences = new Intent(this, MyPreferences.class);
			startActivity(preferences);
		}
		catch (ActivityNotFoundException anfe)
		{
			toaster.makeText(this, "Preferences activity not found!", Toast.LENGTH_SHORT).show();
		}
	}*/
	
}
