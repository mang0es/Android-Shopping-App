package com.cmpe137.shopping.app;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SavedItems extends ListActivity
{
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListAdapter adapter;
    private Toast toaster;

	public void onCreate(Bundle savedInstanceState) 
	{
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saveditems);
        
        db = (new DatabaseHelper(this)).getWritableDatabase();
        search();
	}
	
	public void search()
	{
	   cursor = db.rawQuery("SELECT * FROM saveditems", null);

	   cursor.moveToFirst();
	   
	   adapter = new SimpleCursorAdapter(
	         this,
	         R.layout.savedresults,
	         cursor,
	         new String[] {"item", "manufacturer", "price"},
	         new int[] {R.id.itemTitle, R.id.itemCompany, R.id.price}
	         );
	  
	   setListAdapter(adapter);
	   
	}
	
	public int getSource()
	{
	   return R.drawable.defaulticon;
	}
}
