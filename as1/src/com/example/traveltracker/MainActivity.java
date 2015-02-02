//Liangrui Lu

//1366461
//============================Main===============================
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







//import android.content.DialogInterface;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traveltracker.MainActivity;
import com.example.traveltracker.AddCliam;
import com.example.traveltracker.Claimlist;
import com.example.traveltracker.DatalistAdapter;

public class MainActivity extends Activity {
	private static final String FILENAME = "save.sav";
	private Claimlist datafile;
	private DatalistAdapter datalistadapter;
	private ListView mainlistview;

	protected void onStart(){
		super.onStart();
		datafile = this.loadFromFile();
		datalistadapter = new DatalistAdapter(this,datafile.getClaimlist());
		mainlistview.setAdapter(datalistadapter);
		
	}
	
	
	
	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainlistview = (ListView)findViewById(R.id.claimlist);
	
		//=====================================================================================================================
		//exit button
		Button exit = (Button)findViewById(R.id.Exit);
		exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//finish
				AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
				adb.setMessage("Exit?");
				adb.setNegativeButton(android.R.string.no, null);
		        adb.setPositiveButton(android.R.string.yes, new OnClickListener() {
		            public void onClick(DialogInterface arg0, int arg1) {
		               	MainActivity.this.finish();
		               	
		            }
		        }).create().show();	

				}
				
			
		});
		
		
		//=====================================================================================================================
		//connect to Add Claim
		Button add = (Button) findViewById(R.id.addclaim);
		add.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View fa1)
			{

				Intent intent = new Intent(MainActivity.this,AddCliam.class);
				startActivity(intent);
			}
		}); 
		//=====================================================================================================================
		//long click to delete the certain claim
		mainlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				
				final int claimID = position;
				AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
				adb.setMessage("Delete "+datafile.getClaimlist().get(claimID).getPlace().toString()+" ?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){
					public void onClick(DialogInterface dialog,int which){
						Claim claim=datafile.getClaimlist().get(claimID);
						datafile.getClaimlist().remove(claim);
						saveInFile();
						datalistadapter.clear();
						
						datafile = loadFromFile();
						datalistadapter.addAll(datafile.getClaimlist());
						datalistadapter.notifyDataSetChanged();
					
					}
										
				});
				adb.setNegativeButton("Cancel", new OnClickListener(){
					public void onClick(DialogInterface dialog,int which){
						
					}
				}); 
				adb.show();
				return false;
					
					
				}
				
				
				
			});				
		
		
	
		//=====================================================================================================================
		//short click the item to see the details of the claim(go to claim view page)
		mainlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

	
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				final int claimID = position;
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MainActivity.this,Claimview.class);
				
				intent.putExtra("claimID", claimID);
				startActivity(intent);
				
				
			}
		});
		
		
	}
	
	/*

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	}
/*
	
	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	
	}
	*/

	//public void emailclaim(MenuItem menu){
		//Toast.makeText(this,"Email Claim",Toast.LENGTH_SHORT).show();
//	}
	//Claim list view
	/*
	public void chooseclaim(View v){
		Claimlist cl  = new Claimlist();
		TextView view = (TextView)findViewById(R.id.claimlist);
		//view.setText(s.getClaim());
	}
	*/
	

	 
	
	
	
	
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
