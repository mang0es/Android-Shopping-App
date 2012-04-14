package com.cmpe137.shopping.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SearchCompany extends ListActivity
{
   private SQLiteDatabase db;
   private Cursor cursor;
   private ListAdapter adapter;
   private Toast toaster;
   
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.searchcompany);
      
      db = (new DatabaseHelper(this)).getWritableDatabase();
      searchCompany();
      
   }
   
   public void searchCompany()
   {
      cursor = db.rawQuery("SELECT _id, itemCompany from items GROUP BY itemCompany", null);
      cursor.moveToFirst();
      
      adapter = new SimpleCursorAdapter(
            this,
            R.layout.companyresults,
            cursor,
            new String[] {"itemCompany"},
            new int[] {R.id.itemCompany}
            );
            
      setListAdapter(adapter);
            
   }
   
   public void onListItemClick(ListView parent, View view, int position, long id)
   {
      Intent itemsByCompany = new Intent(this, ItemsByCompany.class);
      Cursor cursor = (Cursor)adapter.getItem(position);
      itemsByCompany.putExtra("companySelected", cursor.getString(cursor.getColumnIndex("itemCompany")));
      startActivity(itemsByCompany);
   }

}
