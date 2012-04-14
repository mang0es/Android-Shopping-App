package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShippingInfo extends Activity {
	TextView itemTitleView;
	TextView itemCompanyView;
	TextView itemPriceView;
	String userEmail;
	String itemTitleStr;
	String itemCompanyStr;
	String itemPriceStr;
	EditText personName;
	EditText streetName;
	EditText cityName;
	EditText zipCode;
	AutoCompleteTextView state;
	Button continueButton;
	Toast toaster;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shipping_info);
		
		Intent prevIntent = getIntent();
		userEmail = prevIntent.getStringExtra("USER_EMAIL");
		itemTitleStr = prevIntent.getStringExtra("ITEM_TITLE");
		itemCompanyStr = prevIntent.getStringExtra("ITEM_COMPANY");
		itemPriceStr = prevIntent.getStringExtra("ITEM_PRICE");
		
		itemTitleView = (TextView) findViewById(R.id.itemTitle);
		itemTitleView.setText(itemTitleStr);
		
		itemCompanyView = (TextView) findViewById(R.id.itemCompany);
		itemCompanyView.setText(itemCompanyStr);
		
		itemPriceView = (TextView) findViewById(R.id.itemPrice);
		itemPriceView.setText(itemPriceStr);
		
		personName = (EditText) findViewById(R.id.personName);
		streetName = (EditText) findViewById(R.id.streetName);
		cityName = (EditText) findViewById(R.id.cityName);
		zipCode = (EditText) findViewById(R.id.zipCode);
		state = (AutoCompleteTextView) findViewById(R.id.state);
		initState();
		
		continueButton = (Button) findViewById(R.id.shippingContinue);
		continueButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				startContinue();
			}
		});
	}
	
	protected void initState()
	{
		 ArrayAdapter<String> adapter = 
	        	new ArrayAdapter<String>(this, R.layout.list_item, STATES);
	        state.setAdapter(adapter);
	        state.setThreshold(1);
	}
	
	protected void startContinue()
	{
		try {
			Intent choosePaymentIntent = new Intent(this, ChoosePayment.class);
			boolean check = true;
			if(personName.getText().toString().equals("")) check = false;
			if (streetName.getText().toString().equals("")) check = false;
			if (cityName.getText().toString().equals("")) check = false;
			if (state.getText().toString().equals("")) check = false;
			if (zipCode.getText().toString().equals("")) check = false;
			if(check) {
				choosePaymentIntent.putExtra("USER_EMAIL", userEmail);
				choosePaymentIntent.putExtra("ITEM_TITLE", itemTitleStr);
				choosePaymentIntent.putExtra("ITEM_COMPANY", itemCompanyStr);
				choosePaymentIntent.putExtra("ITEM_PRICE", itemPriceStr);
				choosePaymentIntent.putExtra("NAME", personName.getText().toString());
				choosePaymentIntent.putExtra("STREET_NAME",  streetName.getText().toString());
				choosePaymentIntent.putExtra("CITY_NAME", cityName.getText().toString());
				choosePaymentIntent.putExtra("STATE", state.getText().toString());
				choosePaymentIntent.putExtra("ZIP_CODE", zipCode.getText().toString());
				
				startActivity(choosePaymentIntent);
			}
			else
				toaster.makeText(this, "Invalid entries", Toast.LENGTH_SHORT).show();
		}
		catch(ActivityNotFoundException e) {
			toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
		}
	}
	
	static final String[] STATES = new String[] 
			  {"AL", "AK", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", 
				"ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA",
				"MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
				"NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
				"UT", "VT", "VA", "WA", "WV", "WI", "WY"};	
}
