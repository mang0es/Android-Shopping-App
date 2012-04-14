package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;


public class ShoppingAppActivity extends Activity {
	
	Toast toaster;
	SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        db = dbhelper.getWritableDatabase();
        //db.execSQL("DROP TABLE saveditems");
        dbhelper.onCreate(db);
        //db.close();
    }
    public boolean onTouchEvent(MotionEvent event)
	{
    	if (event.getAction() == (MotionEvent.ACTION_UP))
    		startWelcome();
		return true;
	}
    
    protected void startWelcome()
    {
    	try {
	    	Intent search = new Intent(this, WelcomeScreen.class);
			startActivity(search);

    	}
    	catch (ActivityNotFoundException afne)
		{
			toaster.makeText(this, "Activity not found!", Toast.LENGTH_SHORT).show();
		}
    }
    
    @Override
    protected void onStart(){
    	super.onStart();
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    }
    
    @Override
    protected void onRestart(){
    	super.onRestart();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    }
    
    @Override
    protected void onStop(){
    	super.onStop();
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    }
    
}