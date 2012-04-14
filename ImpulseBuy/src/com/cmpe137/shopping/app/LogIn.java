package com.cmpe137.shopping.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogIn extends Activity{
	Button logIn;
	TextView forgotPw;
	Toast toaster;
	LogIn loginscreen = this;
	String[] usernames;
	String[] passwords;
	EditText email;
	EditText password;
	Cursor cursor;
	SQLiteDatabase db;
	String currentuser = "";
	public void onCreate(Bundle savedInstanceState) {
		usernames = getResources().getStringArray(R.array.loginNames);
		passwords = getResources().getStringArray(R.array.loginPasswords);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        db = dbhelper.getWritableDatabase();
        
        logIn = (Button) findViewById(R.id.ButtonLogIn);
        logIn.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) {
				startLogin();
			}
		});   
        forgotPw = (TextView) findViewById(R.id.ForgotPassword);
        forgotPw.setOnClickListener(new View.OnClickListener() 
        {	
			@Override
			public void onClick(View v) {
				startForgotPw();
				//toaster.makeText(loginscreen, "...jump to pw recovery", Toast.LENGTH_LONG).show();
			}
		});
        email = (EditText)findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
       
	}
	
	public void startForgotPw()
	{
		try {
			Intent forgotpw = new Intent(this, ForgotPassword.class);
			forgotpw.putExtra("currentemail", email.getText().toString());
			startActivity(forgotpw);
		}
		catch (Exception e)
		{
			toaster.makeText(this, "Err: Activity not found", Toast.LENGTH_LONG).show();
		}

	}
	
	public void startLogin()
    {
		String querybuilder = email.getText().toString();
		cursor = db.query("customers", new String[]{"email", "password"}, 
					"email=?", new String[] {querybuilder}, null, null, null);
		boolean check = false;
		
		if (cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			//int passwordIndex = cursor.getColumnIndex("password");
			//toaster.makeText(this, "index: " + passwordIndex, Toast.LENGTH_LONG).show();
			String tempPw = cursor.getString(1);
			//toaster.makeText(this, "pw found: " + tempPw, Toast.LENGTH_LONG).show();
			
			if (password.getText().toString().equals(tempPw))
				check = true;
		}
		
		if (check)
			{
			try {
		    	Intent logIn = new Intent(this, LoggedIn.class);
		    	logIn.putExtra("useremail", querybuilder);
		    	startActivity(logIn);
			}
			catch (ActivityNotFoundException afne)
			{
				toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			toaster.makeText(this, "Invalid pw", Toast.LENGTH_LONG).show();
		}	
    }
}
