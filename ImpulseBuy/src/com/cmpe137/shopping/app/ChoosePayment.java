package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChoosePayment extends Activity
{
	String userEmail;
	String itemTitleStr;
	String itemCompanyStr;
	String itemPriceStr;
	String personNameStr;
	String streetNameStr;
	String cityNameStr;
	String stateStr;
	String zipCodeStr;
	EditText emailText;
	CheckBox emailChkBox;
	ImageView googleChkOutView;
	ImageView paypalView;
	Toast toaster;
	SQLiteDatabase db;
	
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.choosepayment);
      
      Intent prevIntent = getIntent();
      userEmail = prevIntent.getStringExtra(userEmail);
      itemTitleStr = prevIntent.getStringExtra("ITEM_TITLE");
      itemCompanyStr = prevIntent.getStringExtra("ITEM_COMPANY");
      itemPriceStr = prevIntent.getStringExtra("ITEM_PRICE");
      personNameStr = prevIntent.getStringExtra("NAME");
      streetNameStr = prevIntent.getStringExtra("STREET_NAME");
      cityNameStr = prevIntent.getStringExtra("CITY_NAME");
      stateStr = prevIntent.getStringExtra("STATE");
      zipCodeStr = prevIntent.getStringExtra("ZIP_CODE");     
      emailText = (EditText) findViewById(R.id.email);
      
      emailChkBox = (CheckBox) findViewById(R.id.emailChkBox);
      emailChkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    	  @Override 
    	  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
    	  // TODO Auto-generated method stub 
    		  if (buttonView.isChecked()) {
    			  emailText.setText(userEmail);
    		  }
    		  else { 
    			  emailText.setText("");
    		  }
    	  }
      });
      
      googleChkOutView = (ImageView) findViewById(R.id.googlecheckout);
      googleChkOutView.setOnClickListener(new View.OnClickListener() {	
		@Override
		public void onClick(View v) {
			googleContinue();
		}
	});
      
      paypalView = (ImageView) findViewById(R.id.paypal);
      paypalView.setOnClickListener(new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			paypalContinue();
		}
	});
   }
   
   public void googleContinue()
   {
	   try {
		   Intent confirmationIntent = new Intent(this, Confirmation.class);
		   boolean check = true;
		   if(emailText.getText() == null || emailText.getText().toString() == "")
			   check = false;
		   
		   if(check) {
			   confirmationIntent.putExtra("ITEM_TITLE", itemTitleStr);
			   confirmationIntent.putExtra("ITEM_COMPANY", itemCompanyStr);
			   confirmationIntent.putExtra("ITEM_PRICE", itemPriceStr);
			   confirmationIntent.putExtra("NAME", personNameStr);
			   confirmationIntent.putExtra("STREET_NAME", streetNameStr);
			   confirmationIntent.putExtra("CITY_NAME", cityNameStr);
			   confirmationIntent.putExtra("STATE", stateStr);
			   confirmationIntent.putExtra("ZIP_CODE", zipCodeStr);
	   
			   startActivity(confirmationIntent);
		   }
		   else
			   toaster.makeText(this, "Invalid entries", Toast.LENGTH_SHORT).show();
	   }
	   catch(ActivityNotFoundException e) {
		   toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
	   }
   }
   
   public void paypalContinue()
   {
	   try {
		   Intent confirmationIntent = new Intent(this, Confirmation.class);
		   boolean check = true;
		   if(emailText.getText() == null || emailText.getText().toString() == "")
			   check = false;
		   
		   if(check) {
			   confirmationIntent.putExtra("ITEM_TITLE", itemTitleStr);
			   confirmationIntent.putExtra("ITEM_COMPANY", itemCompanyStr);
			   confirmationIntent.putExtra("ITEM_PRICE", itemPriceStr);
			   confirmationIntent.putExtra("NAME", personNameStr);
			   confirmationIntent.putExtra("STREET_NAME", streetNameStr);
			   confirmationIntent.putExtra("CITY_NAME", cityNameStr);
			   confirmationIntent.putExtra("STATE", stateStr);
			   confirmationIntent.putExtra("ZIP_CODE", zipCodeStr);
	   
			   startActivity(confirmationIntent);
		   }
		   else
			   toaster.makeText(this, "Invalid entries", Toast.LENGTH_SHORT).show();
	   }
	   catch(ActivityNotFoundException e) {
		   toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
	   } 
   }

}
