package com.cmpe137.shopping.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.ActivityNotFoundException;


public class Browse extends Activity
{
   Button allButton;
   Button company;
   Toast toaster;
   
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        
        allButton = (Button) findViewById(R.id.all);
        allButton.setOnClickListener(new View.OnClickListener()
      {
         
         @Override
         public void onClick(View v)
         {
            startAll();
            
         }
      });
        
        company = (Button) findViewById(R.id.company);
        company.setOnClickListener(new View.OnClickListener()
        {
           public void onClick(View v)
           {
              startCompany();
           }
        });

	}
	protected void startAll()
    {
       try
       {
          Intent all = new Intent(this, SearchAll.class);
          startActivity(all);
       }
       catch (ActivityNotFoundException anfe)
       {
          toaster.makeText(this, "Nearby activity not found!", Toast.LENGTH_SHORT).show();
       }
    }
    
    protected void startCompany()
    {
       try
       {
          Intent company = new Intent(this, SearchCompany.class);
          startActivity(company);
       }
       catch (ActivityNotFoundException anfe)
       {
          toaster.makeText(this, "Types activity not found!", Toast.LENGTH_SHORT).show();
       }
    }
	
	
}
