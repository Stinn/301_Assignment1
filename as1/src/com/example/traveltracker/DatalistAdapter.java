//=====================================================================================================================
//Liangrui Lu
//1366461
//set this adapter to get the textiew and then put them on the listview
package com.example.traveltracker;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DatalistAdapter extends ArrayAdapter<Claim> {
	private Context context;
	private ArrayList<Claim> claimlist;

	public DatalistAdapter(Context context,ArrayList<Claim> claimlist){
		super (context,0,claimlist);
		this.context = context;
		this.claimlist = claimlist;
		
		
	}
	public View getView(int position,View convertView,ViewGroup parent){
		Claim tempclaim = getItem(position);
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
		}
		TextView place = (TextView)convertView.findViewById(R.id.placeView);
		place.setText(tempclaim.getPlace());
		return convertView;
	}
	
}
