package com.cmpe137.shopping.app;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class Search extends ListActivity
{
   private String searchInput;
   private EditText searchText;
   private SQLiteDatabase db;
   private ListAdapter adapter;
   private Cursor cursor;
   private Cursor cursor2;
   private Toast toaster;
   private Bitmap imgIcon;
   ImageView imageView;
   //private String img;
   ImageView i11=null;
   View convertView;

   private String image;
  
   int index = 0;
   final int SAVE_TODO = Menu.FIRST;
   final int CANCEL_TODO = Menu.FIRST + 1;
   private boolean saveItem = false;
   
   private LayoutInflater mInflater;
      
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.search);

	        Intent getData = getIntent();
	        searchInput = getData.getStringExtra("searchInput");
	        
	        db = (new DatabaseHelper(this)).getWritableDatabase();

	        search();
	      
	 }
	 
	 public void search() 
	 {
        // || is the concatenation operation in SQLite
		 
        cursor = db.rawQuery("SELECT _id, itemTitle, itemCompany, " +
        		"itemPrice, itemIcon from items WHERE itemTitle || " +
        		"' ' || itemCompany LIKE ?", 
                        new String[]{"%" + searchInput + "%"});
        cursor.moveToFirst();
       
        
        adapter = new SimpleCursorAdapter(
              this, 
              R.layout.searchresults, 
              cursor, 
              new String[] {"itemTitle", "itemCompany", "itemPrice", "itemIcon"}, 
              new int[] {R.id.itemTitle, R.id.itemCompany, R.id.itemPrice, 
            		  R.drawable.defaulticon}
              );
         
        setListAdapter(adapter);
		registerForContextMenu(getListView());
	 }
	 
	 public void onListItemClick(ListView parent, View view, int position, long id) {
		 Intent shippingInfo = new Intent(this, ShippingInfo.class);
		 Cursor cursor = (Cursor)adapter.getItem(position);
		 shippingInfo.putExtra("ITEM_ID", cursor.getInt(cursor.getColumnIndex("_id")));
		 shippingInfo.putExtra("ITEM_TITLE", cursor.getString(cursor.getColumnIndex("itemTitle")));
		 shippingInfo.putExtra("ITEM_COMPANY", cursor.getString(cursor.getColumnIndex("itemCompany")));
		 shippingInfo.putExtra("ITEM_PRICE", cursor.getString(cursor.getColumnIndex("itemPrice")));
		 startActivity(shippingInfo);
	 }
	  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	 {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.setHeaderTitle("Selected To Do Item");
	    menu.add(0, SAVE_TODO, Menu.NONE, "Save Item");
	    menu.add(1, CANCEL_TODO, Menu.NONE, "Cancel");

	 }
	 
	 public boolean onContextItemSelected(MenuItem item)
	 {
	    super.onOptionsItemSelected(item);
	    
	    switch (item.getItemId())
	    {
	    case (SAVE_TODO):
	    {
	       AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
	             .getMenuInfo();
	       int index = menuInfo.position;
	       saveItem(index);
	       return true;
	    }
	    
	    }
	    return false;
	    
	 }
	 
	 private void saveItem(int position)
	 {
	    DatabaseHelper dbhelper = new DatabaseHelper(this);
        db = dbhelper.getWritableDatabase();
        
	    Cursor cursor = (Cursor)adapter.getItem(position);
        String item = cursor.getString(cursor.getColumnIndex("itemTitle"));
        String man  = cursor.getString(cursor.getColumnIndex("itemCompany"));
        String price = cursor.getString(cursor.getColumnIndex("itemPrice"));
	    String insertString = "INSERT INTO saveditems VALUES(null ,'" + item + "', " +
	          "'" + man + "', " + "'" + price + "')";
	    
	    try
	    {
	       db.execSQL(insertString);
	    }
	    catch (SQLException e)
	    {
	       toaster.makeText(this, "NOPE!", Toast.LENGTH_SHORT);
	    }
	 }

}
