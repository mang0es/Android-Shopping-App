package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Confirmation extends Activity{

	String itemTitleStr;
	String itemCompanyStr;
	String itemPriceStr;
	String personNameStr;
	String streetNameStr;
	String cityNameStr;
	String stateStr;
	String zipCodeStr;
	TextView itemTitleView;
	TextView itemCompanyView;
	TextView itemPriceView;
	TextView personNameView;
	TextView streetNameView;
	TextView cityNameView;
	TextView stateView;
	TextView zipCodeView;
	Button confirmButton;
	Toast toaster;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation);
		
		Intent prevIntent = getIntent();
		itemTitleStr = prevIntent.getStringExtra("ITEM_TITLE");
		itemCompanyStr = prevIntent.getStringExtra("ITEM_COMPANY");
		itemPriceStr = prevIntent.getStringExtra("ITEM_PRICE");
		personNameStr = prevIntent.getStringExtra("NAME");
		streetNameStr = prevIntent.getStringExtra("STREET_NAME");
		cityNameStr = prevIntent.getStringExtra("CITY_NAME");
		stateStr = prevIntent.getStringExtra("STATE");
		zipCodeStr = prevIntent.getStringExtra("ZIP_CODE");
		
		itemTitleView = (TextView) findViewById(R.id.itemTitle);
		itemTitleView.setText(itemTitleStr);
		
		itemCompanyView = (TextView) findViewById(R.id.itemCompany);
		itemCompanyView.setText(itemCompanyStr);
		
		itemPriceView = (TextView) findViewById(R.id.itemPrice);
		itemPriceView.setText(itemPriceStr);
		
		personNameView = (TextView) findViewById(R.id.personName);
		personNameView.setText(personNameStr);
		
		streetNameView = (TextView) findViewById(R.id.streetName);
		streetNameView.setText(streetNameStr);
		
		cityNameView = (TextView) findViewById(R.id.cityName);
		cityNameView.setText(cityNameStr);
		
		stateView = (TextView) findViewById(R.id.state);
		stateView.setText(stateStr);
		
		zipCodeView = (TextView) findViewById(R.id.zipCode);
		zipCodeView.setText(zipCodeStr);
		
		confirmButton = (Button) findViewById(R.id.confirm);
		confirmButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				startConfirm();
			}
		});		
	}
	
	protected void startConfirm() {
		try {
			toaster.makeText(this, "Back to homescreen", Toast.LENGTH_SHORT).show();
		}
		catch(ActivityNotFoundException e) {
			toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
		}
	}	
	
}
