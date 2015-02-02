//Liangrui Lu
//1366461
//======================================================================================
//This class is used to add a single claim

package com.example.traveltracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;

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
import android.widget.Toast;

public class AddCliam extends Activity {
	private static final String FILENAME = "save.sav";
	private Claimlist datafile;
	private Claimlist claimlist;
	private EditText place;
	private EditText datefrom;
	private EditText dateto;
	private EditText claimdescription;
	public String text;
	private String placevalue;
	private String fromdate;
	private String todate;
	private String description;
	

	
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addclaim);
		place = (EditText) findViewById(R.id.placename);
		datefrom = (EditText) findViewById(R.id.fromdate);
		dateto = (EditText) findViewById(R.id.todate);
		claimdescription = (EditText) findViewById(R.id.description);
		datafile = this.loadFromFile();
		
	
//=====================================================================================================================
//Cancel get back to main
		
		Button cancel = (Button) findViewById(R.id.claimcancel);
		//final EditText description = (EditText)findViewById(R.id.description) ;
		
		
		cancel.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{

			finish();
			}


		}); 
	
		
	
		
		//=====================================================================================================================
		//confirm the claim
		
		Button add = (Button) findViewById(R.id.claimadd);
		add.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{
				//=====================================================================================================================
				//save information of the claim,set text to string and store them into file
			
			placevalue = place.getText().toString();
			fromdate = datefrom.getText().toString();
			todate = dateto.getText().toString();
			description = claimdescription.getText().toString();
			
			Claim newclaim = new Claim();
			newclaim.setPlace(placevalue);
			newclaim.setDatefrom(fromdate);
			newclaim.setDateto(todate);
			newclaim.setClaimdescription(description);
			datafile.addclaim(newclaim);
			Collections.sort(datafile.getClaimlist(),new Comparator<Claim>(){
				public int compare(Claim claim_1,Claim claim_2){
					return claim_1.getDatefrom().compareTo(claim_2.getDatefrom());
				}
			});
			saveInFile();
			
			
			
			

			
			
			finish();
			}
			
		});  
		
		
		
	
		
	}
	private void addInClaimlist(String text) {
				// TODO Auto-generated method stub
				
	}
	@Override
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
	//Load and save from Gson(google)
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
