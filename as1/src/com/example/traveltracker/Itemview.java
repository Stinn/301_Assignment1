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
import android.widget.TextView;

public class Itemview extends Activity{

	private static final String FILENAME = "save.sav";
	private Claimlist datafile;
	//private Item item;
	private TextView itemname;
	private String itemnamestr;
	private TextView itemdate;
	private String itemdatestr;
	private TextView itemcategory;
	private String itemcategorystr;
	private TextView itemamount;
	private String itemamountstr;
	private TextView itemunit;
	private String itemunitstr;
	private TextView itemdescription;
	private String itemdescriptionstr;
	//=====================================================================================================================
	//get information from datafile and put them on the text view
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itemview);
		datafile = loadFromFile();
		Intent intent = getIntent();
		final int claimID = intent.getIntExtra("claimID", 0);
		final int itemID = intent.getIntExtra("itemID",0);
		
		itemnamestr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getItem().toString();
		itemname= (TextView) findViewById(R.id.itemnameview);
		itemname.setText(itemnamestr);
		
		itemdatestr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getDate().toString();
		itemdate= (TextView) findViewById(R.id.itemdateview);
		itemdate.setText(itemdatestr);
		
		itemcategorystr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getCategory().toString();
		itemcategory= (TextView) findViewById(R.id.itemcategoryview);
		itemcategory.setText(itemcategorystr);
		
		itemunitstr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getUnit().toString();
		itemunit= (TextView) findViewById(R.id.itemunitview);
		itemunit.setText(itemunitstr);
		
		itemamountstr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getAmount().toString();
		itemamount= (TextView) findViewById(R.id.itemamountview);
		itemamount.setText(itemamountstr);
		
		itemdescriptionstr = datafile.getClaimlist().get(claimID).getItemlist().get(itemID).getDescription().toString();
		itemdescription= (TextView) findViewById(R.id.itemdescriptionview);
		itemdescription.setText(itemdescriptionstr);
		
		//=====================================================================================================================
		//connect to edit item(expense)
		Button editclaim = (Button) findViewById(R.id.itemedit);
		editclaim.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{

				Intent intent = new Intent(Itemview.this,Edititemactivity.class);
				intent.putExtra("claimID", claimID);
				intent.putExtra("itemID", itemID);
				startActivity(intent);
			}
		}); 

		
	}
	
	
	
	//=====================================================================================================================
	//Load and save
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
