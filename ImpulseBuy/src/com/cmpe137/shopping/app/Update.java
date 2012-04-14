package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update extends Activity{
	EditText name, streetName, cityName, stateName, 
	zipCode, newpassword, oldpassword;
	Button update;
	boolean updateName=true, updateStreet=true, updateCity=true, 
			updateState=true, updateZip=true, updatePw=true;
	String currentemail;
	Toast toaster;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		
		Intent myIntent = getIntent();
		currentemail = myIntent.getStringExtra("currentemail");
		
		name = (EditText)findViewById(R.id.Name);
		streetName = (EditText) findViewById(R.id.StreetName);
		cityName = (EditText) findViewById(R.id.CityName);
		stateName = (EditText) findViewById(R.id.StateName);
		zipCode = (EditText) findViewById(R.id.ZipCode);
		newpassword = (EditText) findViewById(R.id.NewPassword);
		oldpassword = (EditText) findViewById(R.id.OldPassword);
		
		update = (Button)findViewById(R.id.UpdateConfirm);
		
		update.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				update();
			}
		});
		}
		public void update()
		{
			
			/*
			 * UPDATE table_name
				SET column1=value, column2=value2,...
				WHERE some_column=some_value
			 */
			if (name.getText().toString().equals("")) updateName = false;
			if (streetName.getText().toString().equals("")) updateStreet = false;
			if (cityName.getText().toString().equals("")) updateCity = false;
			if (stateName.getText().toString().equals("")) updateState = false;
			if (zipCode.getText().toString().equals("")) updateZip = false;
			if (newpassword.getText().toString().equals("")) updatePw = false;
			
			String updateBuilder = "UPDATE customers SET ";
			if (updateName) 
				updateBuilder += "name='" + name.getText().toString() + "'";
			if (updateStreet)
			{
				if (updateName) updateBuilder += ", ";
				updateBuilder += "streetname='" + streetName.getText().toString() + "'";
			}
			if (updateCity)
			{
				if (updateName || updateStreet) updateBuilder += ", ";
				updateBuilder += "cityname='" + cityName.getText().toString() + "'";
			}
			if (updateState)
			{
				if (updateName || updateStreet || updateCity)
					updateBuilder += ", ";
				updateBuilder += "statename='" + stateName.getText().toString() + "'";
			}
			if (updateZip)
			{
				if (updateName || updateStreet || updateCity || updateState)
					updateBuilder += ", ";
				updateBuilder += "zipcode='" + zipCode.getText().toString() + "'";
			}
			if (updatePw)
			{
				if (updateName || updateStreet || updateCity || updateState || updateZip)
					updateBuilder += ", ";
				updateBuilder += "password='" + newpassword.getText().toString() + "'";
			}
			updateBuilder += " WHERE email='" + currentemail + "'";
			
			toaster.makeText(this, updateBuilder, Toast.LENGTH_LONG).show();
			/*
			 * UPDATE table_name
				//SET column1=value, column2=value2,...
				WHERE some_column=some_value
			 */
			if (!updateName && !updateStreet && !updateCity &&  
					!updateState && !updateZip && !updatePw) return;
			SQLiteDatabase db = (new DatabaseHelper(this)).getWritableDatabase();
			db.execSQL(updateBuilder);
			
			//finish();
	}
}
