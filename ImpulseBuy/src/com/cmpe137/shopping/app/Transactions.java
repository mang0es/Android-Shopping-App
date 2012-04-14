package com.cmpe137.shopping.app;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class Transactions extends ListActivity{
	private ListAdapter adapter;
	String currentemail;
	SQLiteDatabase db;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transactions);
		
		Intent myIntent = getIntent();
		currentemail = myIntent.getStringExtra("currentemail");
		
		db = (new DatabaseHelper(this)).getWritableDatabase();
		listTransactions();
	}
	/**
	 * transactionid	VARCHAR(50) PRIMARY KEY,
		itemname		VARCHAR(40),
		date			VARCHAR(20),
		totalamount		VARCHAR(40),
		email			VARCHAR(40))
	 */
	public void listTransactions()
	{
		Cursor cursor = db.rawQuery("SELECT _id, itemname, date, totalamount, email " +
        		"FROM transactions WHERE email=?", 
                        new String[]{"%" + currentemail + "%"});
		
		/**
		 * boolean ok;
		 * if (cursor.getCount()) > 0) ok = true;
		 */
		
		cursor.moveToFirst();
		
		adapter = new SimpleCursorAdapter(
				this,
				R.layout.transactionresults,
				cursor,
				new String[] {"itemname", "date", "totalamount"},
				new int[] {R.id.tranTitle, R.id.tranDate, R.id.tranPrice}
				);
		setListAdapter(adapter);
		
	}
}
