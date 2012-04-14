package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserContinue extends Activity{
	private Button register;
	private String name, streetname, cityname, statename, zipcode;
	EditText email, password;
	SQLiteDatabase db;
	Toast toaster;
	public void onCreate(Bundle savedInstanceState) {
	    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newusercont);
        
        Intent prevIntent = getIntent();
        
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        db = dbhelper.getWritableDatabase();
        
        name = prevIntent.getStringExtra("name");
        streetname = prevIntent.getStringExtra("streetname");
        cityname = prevIntent.getStringExtra("cityname");
        statename = prevIntent.getStringExtra("statename");
        zipcode = prevIntent.getStringExtra("zipcode");
        email = (EditText) findViewById(R.id.PersonEmail);
        password = (EditText) findViewById(R.id.PersonPassword);
        
        register = (Button)findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener() 
        {	
			@Override
			public void onClick(View v) {
				startRegister();
			}
		});
	}
	private void startRegister()
	{
		// enter user data into database
		boolean check = true;
		if (email.getText().toString().equals("")) check = false;
		if (password.getText().toString().equals("")) check = false;
		
		if (check)
		{	boolean dbcheck = true;
			// db name = item_directory
			String dbstring = "INSERT INTO customers VALUES('";
			dbstring += email.getText().toString() + "', '";
			dbstring += password.getText().toString() + "', '";
			dbstring += name + "', '";
			dbstring += streetname + "', '";
			dbstring += cityname + "', '";
			dbstring += statename + "', '";
			dbstring += zipcode + "')";
			
			try {
				db.execSQL(dbstring);
			}
			catch (SQLException e)
			{
				dbcheck = false;
				toaster.makeText(this, "Invalid: " + dbstring, Toast.LENGTH_SHORT);
			}
			try {
				
				if (dbcheck)
				{
					toaster.makeText(this, password.getText().toString(), Toast.LENGTH_SHORT).show();
					Intent register = new Intent(this, LoggedIn.class);
					register.putExtra("useremail", email.getText().toString());
					startActivity(register);
				}
			}
			catch (ActivityNotFoundException afne)
			{
				toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
