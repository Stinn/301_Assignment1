//Liangrui Lu
//1366461
//======================================================================================
//This function display the list of items(expenses)
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Itemlistactivity extends Activity {
	private static final String FILENAME = "save.sav";
	private Claimlist datafile;
	private ItemDatalistAdapter itemdatalistadapter;
	private ListView itemmainlistview;
	private int claimID;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		datafile = this.loadFromFile();

		Intent intent = getIntent();
		claimID = intent.getIntExtra("claimID", 0);
		

		
		
		itemdatalistadapter = new ItemDatalistAdapter(this, datafile.getClaimlist().get(claimID).getItemlist());
		itemmainlistview.setAdapter(itemdatalistadapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item);
		datafile = this.loadFromFile();

		Intent intent = getIntent();
		final int claimID = intent.getIntExtra("claimID", 0);


		itemmainlistview = (ListView) findViewById(R.id.itemlist);
		//=====================================================================================================================
		//connect to the item-add activity
		Button additem = (Button) findViewById(R.id.itemadd);
		additem.setOnClickListener(new View.OnClickListener() {

			public void onClick(View fa1) {

				Intent intent = new Intent();
				intent.setClass(Itemlistactivity.this, Additemactivity.class);
				intent.putExtra("claimID", claimID);
				// intent.putExtra("itemID", itemID);

				startActivity(intent);
			}
		});
		//=====================================================================================================================
		//intent back to claim
		Button back = (Button) findViewById(R.id.backclaim);
		back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View fa1) {

				Intent intent = new Intent();
				intent.setClass(Itemlistactivity.this, Claimview.class);


				startActivity(intent);
			}
		});
		//=====================================================================================================================
		//long click to delete the item(expense)
		itemmainlistview
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int itemposition, long id) {
						// TODO Auto-generated method stub

						final int itemID = itemposition;
						AlertDialog.Builder adb = new AlertDialog.Builder(
								Itemlistactivity.this);
						adb.setMessage("Delete "
								+ datafile.getClaimlist().get(claimID)
										.getItemlist().get(itemID).getItem()
										.toString() + " ?");
						adb.setCancelable(true);
						adb.setPositiveButton("Delete", new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Item item = datafile.getClaimlist()
										.get(claimID).getItemlist().get(itemID);
								datafile.getClaimlist().get(claimID)
										.getItemlist().remove(item);
								saveInFile();
								itemdatalistadapter.clear();

								datafile = loadFromFile();
								itemdatalistadapter.addAll(datafile
										.getClaimlist().get(claimID)
										.getItemlist());
								itemdatalistadapter.notifyDataSetChanged();

							}

						});
						adb.setNegativeButton("Cancel", new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
						adb.show();
						return false;
					}

				});
		//=====================================================================================================================
		//click the item to see the details
		itemmainlistview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int itemposition, long id) {
						final int itemID = itemposition;
						// TODO Auto-generated method stub
						Intent intent = new Intent(Itemlistactivity.this,
								Itemview.class);

						intent.putExtra("claimID", claimID);
						intent.putExtra("itemID", itemID);
						startActivity(intent);

					}
				});

	}

	//=====================================================================================================================
	//Load and save
	private Claimlist loadFromFile() {
		Gson gson = new Gson();
		datafile = new Claimlist();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<Claimlist>() {
			}.getType();
			datafile = gson.fromJson(in, typeOfT);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return datafile;
	}

	private void saveInFile() {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(datafile, osw);
			osw.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
