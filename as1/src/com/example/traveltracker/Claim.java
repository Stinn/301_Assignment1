//Liangrui Lu
//1366461
//=====================================================================================================================
//Create a claim class, contains the information of the Claim
package com.example.traveltracker;

import java.util.ArrayList;

public class Claim {
	private String place;
	private String datefrom;
	private String dateto;
	private String claimdescription;
	private ArrayList<Item> itemlist;
	private ArrayList<Integer> sum;
	private ArrayList<String> unit;
	
	public Claim(){
		this.itemlist=new ArrayList<Item>();
	}

	public String getPlace() {
		return place;
	}

	public String getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return dateto;
	}

	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

	public String getClaimdescription() {
		return claimdescription;
	}

	public void setClaimdescription(String claimdescription) {
		this.claimdescription = claimdescription;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public ArrayList<Item> getItemlist(){
		return itemlist;
		
	}
	public void setItemlist(ArrayList<Item> itemlist){
		this.itemlist = itemlist;
	}
	
	public void addItem(Item newItem){
		this.itemlist.add(newItem);
		
	}
	//get expense
	public void getExpense(){
		sum = new ArrayList<Integer>();
		unit = new ArrayList<String>();
		int num = this.itemlist.size();
		for (int i = 0;i<num;i++){
			Integer itemamount = Integer.parseInt(this.itemlist.get(i).getAmount());
			String itemunit = this.itemlist.get(i).getUnit();
			int sign = 0;
			if(this.unit.isEmpty()!= true){
				int unitsize = this.unit.size();
				for (int a = 0;a < unitsize;a++){
					String newunit = this.unit.get(a);
					if (newunit.equals(itemunit)){
						int newamount = this.sum.get(a);
						int newtotal = newamount+itemamount;
						this.sum.set(a,newtotal);
						sign = 1;
					}
				}
				if (sign == 0){
					unit.add(itemunit);
					sum.add(itemamount);
					
				}
			}
			else{
				unit.add(itemunit);
				sum.add(itemamount);
			}
		}
	}
	public String toString(){
		return this.getPlace();
		
	}
	
	public String getTotalAmount(){
		StringBuffer buffer = new StringBuffer();
		this.getExpense();
		int n = sum.size();
		for (int b =0;b<n;b++){
			buffer.append(unit.get(b).toString()+" : "+sum.get(b).toString()+"\n");
			
		}
		return buffer.toString();
		
		
	}
	
	public ArrayList<Integer> getSum(){
		return this.sum;
	}
	public ArrayList<String> getUnit(){
		return this.unit;
	}

	
	//get expense

		
	
	
	
	
}
