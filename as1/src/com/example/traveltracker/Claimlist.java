//Liangrui Lu
//1366461
//=====================================================================================================================

//Array structure, to store different claims
package com.example.traveltracker;

import java.util.ArrayList;

public class Claimlist {
	private ArrayList<Claim>claimlist;
	
	public Claimlist(){
		this.claimlist = new ArrayList<Claim>();
	}

	public ArrayList<Claim> getClaimlist() {
		return claimlist;
	}

	public void setClaimlist(ArrayList<Claim> claimlist) {
		this.claimlist = claimlist;
	}
	
	public void addclaim(Claim newclaim){
		this.claimlist.add(newclaim);
	}
	
	
	

} 

