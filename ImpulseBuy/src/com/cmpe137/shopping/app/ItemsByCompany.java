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
public class ItemsByCompany extends ListActivity
{
   String companySelected;
   private SQLiteDatabase db;
   private Cursor cursor;
   private ListAdapter adapter;
   private Toast toaster;
   
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.itemsbycompany);
      
      Intent itemIntent = getIntent();
      companySelected = itemIntent.getStringExtra("companySelected");
            
      db = (new DatabaseHelper(this)).getWritableDatabase();
      searchItems();
   }
   
   public void searchItems()
   {
      cursor = db.rawQuery("SELECT _id, itemTitle, itemCompany, itemPrice from items where itemCompany LIKE ?", new String[]{"%" + companySelected + "%"});
      cursor.moveToFirst();
      
      adapter = new SimpleCursorAdapter(
            this,
            R.layout.itemsbycompanyresults,
            cursor,
            new String[]{"itemTitle", "itemPrice"},
            new int[] {R.id.itemTitle, R.id.itemPrice}
            );
      
      setListAdapter(adapter);
   }
   
   public void onListItemClick(ListView parent, View view, int position, long id) 
   {
       Intent shippingInfo = new Intent(this, ShippingInfo.class);
       Cursor cursor = (Cursor)adapter.getItem(position);
       shippingInfo.putExtra("ITEM_ID", cursor.getInt(cursor.getColumnIndex("_id")));
       shippingInfo.putExtra("ITEM_TITLE", cursor.getString(cursor.getColumnIndex("itemTitle")));
       shippingInfo.putExtra("ITEM_COMPANY", cursor.getString(cursor.getColumnIndex("itemCompany")));
       shippingInfo.putExtra("ITEM_PRICE", cursor.getString(cursor.getColumnIndex("itemPrice")));
       startActivity(shippingInfo);
   }
   
   

}
