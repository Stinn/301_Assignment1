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
import java.util.ArrayList;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Emailactivity extends Activity{
	private Claimlist datafile;
	private Claimlist  claimlist;
	private Claim claim;
	private String claimID;
	private String claimname;
	private String datefrom;
	private String dateto;
	private String claimdescription;
	final Context context = this;
	private String test;
	private ArrayList<Item> itemlist;
	private EditText emailaddress;
	//private Button buttonEmailCancle;
	private Button emailsend;
	private static final String FILENAME = "save.sav";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		//Intent intent = getIntent();
		//final int claimID = intent.getIntExtra("claimID",0);
		emailaddress = (EditText)findViewById(R.id.emailaddress);
		emailsend = (Button)findViewById(R.id.emailsend);
		//=====================================================================================================================
		//connect to a tool which has email function
		emailsend.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {

					Intent emailintent = new Intent(Intent.ACTION_SEND);
					emailintent.setType("plain/text");
					String[] recipients = {emailaddress.getText().toString()};
					
					emailintent.putExtra(Intent.EXTRA_EMAIL , recipients);   
					emailintent.putExtra(Intent.EXTRA_SUBJECT,"Travel Claim: "+claim.getPlace());  
					emailintent.putExtra(Intent.EXTRA_TEXT,content());  
					try {
					    startActivity(Intent.createChooser(emailintent, "Sending...Please wait.."));
					} catch (android.content.ActivityNotFoundException ex) {
					    Toast.makeText(Emailactivity.this, "Sending Email failed", Toast.LENGTH_SHORT).show();
					}
					
					saveInFile();
				
					
				}
			});
		
	}
	
	//=====================================================================================================================
	//collect all the informaiton about this claim, store them into a buffer as a string
	
	@Override
	protected void onStart() {
		super.onStart();
		claimlist = this.loadFromFile();
		datafile = loadFromFile();
		Intent intent = getIntent();
		final int claimID = intent.getIntExtra("claimID",0);
		claim = datafile.getClaimlist().get(claimID);
		TextView emailcontent = (TextView)findViewById(R.id.emailcontent);
		emailcontent.setText(content());
		
		
	}
	
	
	
	
		public String content() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Travel Claim \n");
		claimname = claim.getPlace();
		buffer.append("Claim Name: "+claimname+"\n");
		datefrom= claim.getDatefrom();
		buffer.append("From: "+datefrom+"\n");
		dateto= claim.getDateto();
		buffer.append("To: "+dateto+"\n");
		claimdescription = claim.getClaimdescription();
		buffer.append("Description: "+claimdescription+"\n");
		buffer.append("Item:\n");
		for (int i = 0; i < claim.getItemlist().size();i++){
			String itemId = String.valueOf(i+1);
			buffer.append(itemId);
			buffer.append("\n");
			String itemname = claim.getItemlist().get(i).getItem();
			buffer.append("Name: "+itemname);
			buffer.append("\n");			
			String itemdate = claim.getItemlist().get(i).getDate();
			buffer.append("Date: "+itemdate);
			buffer.append("\n");
			String itemCategory = claim.getItemlist().get(i).getCategory();
			buffer.append("Category: "+itemCategory);
			buffer.append("\n");
			String itemAmount =String.valueOf(claim.getItemlist().get(i).getAmount());
			String itemUnit = claim.getItemlist().get(i).getUnit();
			buffer.append("Amount Spend: "+itemAmount+" "+itemUnit);
			buffer.append("\n");
			String itemDescription = claim.getItemlist().get(i).getDescription();
			buffer.append("Description: "+itemDescription);
			buffer.append("\n");
		
		}
		
		
		
		
		return buffer.toString();
	}
		
		
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.emailclaim, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//if (id == R.id.action_settings) {
		//	return true;
	//	}
		return super.onOptionsItemSelected(item);
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
