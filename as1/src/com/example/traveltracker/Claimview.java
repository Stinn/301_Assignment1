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

public class Claimview extends Activity{

	private static final String FILENAME = "save.sav";
	private Claimlist datafile;
	//private Claim claim;
	private TextView place;
	private TextView datefrom;
	private TextView dateto;
	private TextView claimdescription;
	public String text;
	private String placevalue;
	private String fromdate;
	private String todate;
	private String description;
	private String expense;
	private TextView totalexpense;
	
	




	//=====================================================================================================================
	//load the information of claim and put the information on the page


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimview);
		
		Intent intent = getIntent();
		final int claimID = intent.getIntExtra("claimID", 0);
		
		datafile = loadFromFile();

		placevalue = datafile.getClaimlist().get(claimID).getPlace().toString();
		place = (TextView) findViewById(R.id.claimname);
		place.setText(placevalue);
		
		fromdate = datafile.getClaimlist().get(claimID).getDatefrom().toString();
		datefrom = (TextView) findViewById(R.id.fromtime);
		datefrom.setText(fromdate);
		
		todate= datafile.getClaimlist().get(claimID).getDateto().toString();
		dateto = (TextView) findViewById(R.id.totime);
		dateto.setText(todate);
		
		
		description = datafile.getClaimlist().get(claimID).getClaimdescription().toString();
		claimdescription = (TextView) findViewById(R.id.claimdescription);
		claimdescription.setText(description);
		
		expense = datafile.getClaimlist().get(claimID).getTotalAmount().toString();
		totalexpense = (TextView)findViewById(R.id.expense);
		totalexpense.setText(expense);

		
		
		//=====================================================================================================================
		//connect to the expenselist of this claim
		
		Button itemlistiview = (Button) findViewById(R.id.viewitemlist);
		itemlistiview.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{

				Intent intent = new Intent(Claimview.this,Itemlistactivity.class);
				intent.putExtra("claimID", claimID);
				startActivity(intent);
			}
		}); 
		
		//=====================================================================================================================
		//connect to the email page of the claim
		
		Button email = (Button) findViewById(R.id.emailclaim);
		email.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{

				Intent intent = new Intent(Claimview.this,Emailactivity.class);
				intent.putExtra("claimID", claimID);
				startActivity(intent);
			}
		}); 
		//=====================================================================================================================
		//connect to the edit page of the claim
		Button editclaim = (Button) findViewById(R.id.editclaimagain);
		editclaim.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{

				Intent intent = new Intent(Claimview.this,Editclaimactivity.class);
				intent.putExtra("claimID", claimID);
				startActivity(intent);
			}
		}); 
		
		//=====================================================================================================================
		//back intent
		Button back = (Button) findViewById(R.id.backclaimlist);
		back.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{
				
				Intent backIntent=new Intent();
				backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				backIntent.setClass(Claimview.this, MainActivity.class);
				//intent.putExtra("claimID", claimID);
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
