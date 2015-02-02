//Liangrui Lu
//1366461
package com.example.traveltracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Additemactivity extends Activity {
	
	private static final String FILENAME = "save.sav";
	private Claimlist datafile;
	
	private EditText itemname;
	private String item;
	private EditText itemdate;
	private String itemdatestr;
	private EditText itemcategory;
	private String itemcategorystr;
	private EditText itemamount;
	private String itemamountstr;
	private EditText itemunit;
	private String itemunitstr;
	private EditText itemdescription;
	private String itemdescriptionstr;
	private int claimID;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//=====================================================================================================================
		//get text form input
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		
		Intent intent = getIntent();
		final int claimID = intent.getIntExtra("claimID", 0);
		datafile = this.loadFromFile();
		itemname= (EditText) findViewById(R.id.itemname);
		itemdate= (EditText) findViewById(R.id.itemdate);
		itemcategory= (EditText) findViewById(R.id.itemcategory);
		itemamount= (EditText) findViewById(R.id.itemamount);
		itemunit= (EditText) findViewById(R.id.itemunit);
		itemdescription= (EditText) findViewById(R.id.itemdescription);
		
		
		
		//=====================================================================================================================
		//Cancel get back to itemlist
		
		Button itemcancel = (Button) findViewById(R.id.itemcancel);
		
		
		
		itemcancel.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa3)
			{

			finish();
			}


		}); 
	
		
	
		
		//=====================================================================================================================
		//confirm the item and store the information
		
		Button itemadd = (Button) findViewById(R.id.itemadd);
		itemadd.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa3)
			{
			//save information of the item
			
			item = itemname.getText().toString();
			itemdatestr = itemdate.getText().toString();
			itemamountstr = itemamount.getText().toString();
			itemunitstr = itemunit.getText().toString();
			itemcategorystr = itemcategory.getText().toString();
			itemdescriptionstr = itemdescription.getText().toString();
			
			Item newitem = new Item();
			newitem.setItem(item);
			newitem.setDate(itemdatestr);
			newitem.setAmount(itemamountstr);
			newitem.setUnit(itemunitstr);
			newitem.setCategory(itemcategorystr);
			newitem.setDescription(itemdescriptionstr);

			datafile.getClaimlist().get(claimID).addItem(newitem);
			saveInFile();
			
			
			
			
			Intent backIntent=new Intent();
			backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			backIntent.setClass(Additemactivity.this, MainActivity.class);
			
			startActivity(backIntent);

			
			
			//finish();
			}
			
		});  
		
	}	
		
	

	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_cliam, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.nouse) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	
	//=====================================================================================================================
	//load and sava from Gson
	private Claimlist loadFromFile(){
		Gson gson = new Gson();
		datafile = new Claimlist();
		try{
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<Claimlist>(){}.getType();
			datafile = gson.fromJson(in, typeOfT);
			fis.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch (IOException e){
			e.printStackTrace();
		}
		return datafile;
	}
	private void saveInFile(){
		Gson gson = new Gson();
		try{
			FileOutputStream fos = openFileOutput(FILENAME,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(datafile,osw);
			osw.flush();
			fos.close();
			
			
		} catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
		
		
	}
	
}
