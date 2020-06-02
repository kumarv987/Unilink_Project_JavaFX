package model;

import java.util.ArrayList;

public class Sale extends Post{
	private double askPrice;
	private double highOffer;
	private double minRaise;
	private String saleId;
	private static int saleNumId = 0;
	
	//Constructor that calls constructor of Post class first
	public Sale(String title, String desc, String studId, double askPrice, double minRaise) {
		super(title,desc,studId);
		this.askPrice = askPrice;
		this.minRaise = minRaise;
		this.highOffer = 0;
		setSaleNumId(++saleNumId);
		generateSaleId();
		super.setPostId(getSaleId());
	}
	
	//2 getter methods
	public int getSaleNumId() {
		return saleNumId;
	}
	
	public String getSaleId() {
		return saleId;
	}
	
	//2 setter methods
	public void setSaleNumId(int num) {
		saleNumId = num;
	}
	
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	
	//This method generates Auto Sale ID
	public void generateSaleId() {
		String s1 = "SAL";
		String str = String.format("%03d", getSaleNumId());
		String s = s1+str;
		setSaleId(s);
	}
	
	//This method overrides the Post class method
	public String getPostDetails() {
		StringBuilder str = new StringBuilder("ID:            " +this.saleId + "\n");
		String s1 = super.getPostDetails();
		StringBuilder str1 = new StringBuilder("Minimum raise: " +this.minRaise+ "\n");
		if(this.highOffer == 0.0) {
			StringBuilder str2 = new StringBuilder("Highest Offer: NO OFFER" + "\n");
		}
		else {
			StringBuilder str2 = new StringBuilder("Highest Offer: " +this.highOffer+ "\n");
		}
		StringBuilder str2 = new StringBuilder("Highest Offer: " +this.highOffer+ "\n");
		String s = str.toString()+s1+str1.append(str2).toString();
		return s;
	}
	
	//This method overrides the Post class method
		public String getPostDetails(String newVal) {
			StringBuilder str1 = new StringBuilder("Name: " + super.getTitle() + "\n");
			StringBuilder str2 = new StringBuilder("Highest offer: $" + this.highOffer + "\n");
			StringBuilder str3 = new StringBuilder("Minimum raise: $"+ this.minRaise + "\n");
			String s = str1.append(str2).append(str3).toString();
			return s;
		}
	
	public boolean handleReply(Reply reply) {
		if(reply.getValue() < (this.highOffer+this.minRaise)) {
			System.out.println("Offer not accepted!\n");
			return false;
		}
		else if(reply.getValue() >= this.askPrice) {
			System.out.println("Congratulations! The "+ super.getTitle() + " has been sold to you.\n" + "Please contact the owner " + super.getStudId()+" for more details.\n");
			super.getReplies().add(reply);
			this.highOffer = reply.getValue();
			super.setStatus("CLOSED");
			return true;
		}
		System.out.println("Your offer has been submitted!\n"+"However, your offer is below the asking price.\n" +"The item is still on sale\n");
		super.getReplies().add(reply);
		this.highOffer = reply.getValue();
		return true;
	}
	
	public String getReplyDetails() {
		String s1 = getPostDetails();
		String s2 = "\nAsking Price: $" + this.askPrice + "\n\n";
		String s3 = "-- Offer History--\n";
		
		ArrayList<String> offerList = new ArrayList<String>();
		for(int i=0; i<super.getReplies().size();i++) {
			offerList.add(super.getReplies().get(i).getRespId());
		}
		
		String str = s1+s2+s3;
		for(int i=offerList.size()-1; i>=0; i--) {
			String temp1 = super.getReplies().get(i).getRespId();
			String temp2 = String.valueOf(super.getReplies().get(i).getValue());
			String temp= temp1+": $"+temp2+"\n";
			str += temp;
		}
		return str;
	}
	
	public void printType() {
		System.out.print("Enter your offer or 'Q' to quit: ");
	}
	
	public boolean responseChecker(String resp) {
		
		/*
		int flag = 0;
		for(int i=0; i<resp.length(); i++) {
			if((int)(resp.charAt(i)) >= 48 || (int)(resp.charAt(i)) <= 57 || resp.charAt(i) == "."))) {
				flag = 0;
			}
			else {
				flag = 1;
			}
		}
		if(flag == 0) {
			return true;
		}
		else {
			return false;
		}*/
		return true;
	}
	
}