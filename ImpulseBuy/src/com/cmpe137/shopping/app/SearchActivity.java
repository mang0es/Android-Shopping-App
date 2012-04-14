package com.cmpe137.shopping.app;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class SearchActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        dbhelper.onCreate(db);
    }
    
}
