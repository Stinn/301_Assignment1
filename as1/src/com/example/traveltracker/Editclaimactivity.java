//Liangrui Lu
//1366461
//======================================================================================
//This class is used to edit the claim
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Editclaimactivity extends Activity{
	
	private static final String FILENAME = "save.sav";
	private Claimlist datafile;
	
	private EditText place;
	private EditText datefrom;
	private EditText dateto;
	private EditText claimdescription;
	public String text;
	private String placevalue;
	private String fromdate;
	private String todate;
	private String description;
	private Claim existclaim;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimedit);
		Intent intent = getIntent();
		final int claimID = intent.getIntExtra("claimID", 0);
		
		//=====================================================================================================================
		//load the data from datefile and put the information on the Edittext
		
		
		datafile = loadFromFile();
		existclaim = datafile.getClaimlist().get(claimID);
		

		placevalue = datafile.getClaimlist().get(claimID).getPlace().toString();
		place = (EditText) findViewById(R.id.editplacename);
		place.setText(placevalue);
		
		fromdate = datafile.getClaimlist().get(claimID).getDatefrom().toString();
		datefrom = (EditText) findViewById(R.id.editfromtime);
		datefrom.setText(fromdate);
		
		todate= datafile.getClaimlist().get(claimID).getDateto().toString();
		dateto = (EditText) findViewById(R.id.edittotime);
		dateto.setText(todate);
		
		
		description = datafile.getClaimlist().get(claimID).getClaimdescription().toString();
		claimdescription = (EditText) findViewById(R.id.editdescription);
		claimdescription.setText(description);
		
		
		Button editclaim = (Button) findViewById(R.id.editconfirm);
		editclaim.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{
				//=====================================================================================================================
				//save information of the claim
			
			placevalue = place.getText().toString();
			fromdate = datefrom.getText().toString();
			todate = dateto.getText().toString();
			description = claimdescription.getText().toString();
			

			existclaim.setPlace(placevalue);
			existclaim.setDatefrom(fromdate);
			existclaim.setDateto(todate);
			existclaim.setClaimdescription(description);
			
			Collections.sort(datafile.getClaimlist(),new Comparator<Claim>(){
				public int compare(Claim claim_1,Claim claim_2){
					return claim_1.getDatefrom().compareTo(claim_2.getDatefrom());
				}
			});

			saveInFile();
			
			
			
			

			
			
			Intent backIntent=new Intent();
			backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			backIntent.setClass(Editclaimactivity.this, MainActivity.class);
			
			startActivity(backIntent);
			}
			
		});  

		
		
		
	}
	
	
	
	
	
	
	
	
	//=====================================================================================================================
	//load and save
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
