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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edititemactivity extends Activity{
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
	private Item existitem;
	@Override
	//=====================================================================================================================
	//get the information of the expense and put them on the edit text
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itemedit);
		Intent intent = getIntent();
		final int claimID = intent.getIntExtra("claimID", 0);
		final int itemID = intent.getIntExtra("itemID", 0);
		datafile = loadFromFile();
		existitem = datafile.getClaimlist().get(claimID).getItemlist().get(itemID);
		item = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getItem();
		itemname = (EditText) findViewById(R.id.edititemname);
		itemname.setText(item);
		
		itemdatestr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getDate();
		itemdate = (EditText) findViewById(R.id.edititemdate);
		itemdate.setText(itemdatestr);
		
		itemcategorystr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getCategory();
		itemcategory = (EditText) findViewById(R.id.edititemcategory);
		itemcategory.setText(itemcategorystr);
		
		itemamountstr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getAmount();
		itemamount = (EditText) findViewById(R.id.edititemamount);
		itemamount.setText(itemamountstr);
		
		itemunitstr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getUnit();
		itemunit = (EditText) findViewById(R.id.edititemunit);
		itemunit.setText(itemunitstr);
		
		itemdescriptionstr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getDescription();
		itemdescription = (EditText) findViewById(R.id.edititemdescription);
		itemdescription.setText(itemdescriptionstr);

		Button edititem = (Button) findViewById(R.id.edititemconfirm);
		edititem.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{
				//=====================================================================================================================
				//
			//save information of the claim
			item = itemname.getText().toString();
			itemdescriptionstr = itemdescription.getText().toString();
			itemunitstr = itemunit.getText().toString();
			itemamountstr = itemamount.getText().toString();
			itemcategorystr = itemcategory.getText().toString();
			itemdatestr = itemdate.getText().toString();
			
				
			
			existitem.setItem(item);
			existitem.setUnit(itemunitstr);
			existitem.setDescription(itemdescriptionstr);
			existitem.setAmount(itemamountstr);
			existitem.setCategory(itemcategorystr);
			existitem.setDate(itemdatestr);
			
			


			saveInFile();
			
			
			
			

			
			Intent backIntent=new Intent();
			backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			backIntent.setClass(Edititemactivity.this, MainActivity.class);
			
			startActivity(backIntent);
			}
			
		});  
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	//=====================================================================================================================
	//
	// Load and save
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
