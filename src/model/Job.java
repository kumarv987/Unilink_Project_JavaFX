package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Job extends Post{
	private static int jobNumId = 0;
	private String jobId;
	private SimpleDoubleProperty propPrice;
	private SimpleDoubleProperty lowOffer;
	
	//Constructor that calls constructor of Post class first
	public Job(String title, String desc, double propPrice, String creatorID) throws FileNotFoundException {
		super(title,desc,creatorID);
		this.propPrice = new SimpleDoubleProperty(propPrice);
		this.lowOffer = new SimpleDoubleProperty(0);
		setJobNumId(++jobNumId);
		generateJobId();
		super.setPostId(getJobId());
	}

	public Job(String title, String desc, double propPrice, Image image, String creatorID) {
		super(title,desc,image,creatorID);
		this.propPrice = new SimpleDoubleProperty(propPrice);
		this.lowOffer = new SimpleDoubleProperty(0);
		setJobNumId(++jobNumId);
		generateJobId();
		super.setPostId(getJobId());
	}
	
	//2 getter methods
	public int getJobNumId() {
		return jobNumId;
	}
	
	public String getJobId() {
		return jobId;
	}

	public double getPropPrice(){
		return propPrice.get();
	}

	public double getLowOffer(){
		return lowOffer.get();
	}
	
	//2 setter methods
	public void setJobNumId(int n) {
		jobNumId = n;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public void setPropPrice(double propPrice){
		this.propPrice = new SimpleDoubleProperty(propPrice);
	}

	public void setLowOffer(double lowOffer){
		this.lowOffer = new SimpleDoubleProperty(lowOffer);
	}

	//This method generates Auto Job ID
	public void generateJobId() {
		String s1 = "JOB";
		String str = String.format("%03d", getJobNumId());
		String s = s1+str;
		setJobId(s);
	}
	
	//This method overrides the Post class method
	public String getPostDetails() {
		String s1 = super.getPostDetails();
		StringBuilder str1 = new StringBuilder("Proposed price: " +getPropPrice()+ "\n\n");
		StringBuilder str2 = new StringBuilder("Lowest Offer:   " +getLowOffer()+ "\n\n");
		String s = s1+str1.append(str2).toString();
		return s;
	}
	
	//This method overrides the Post class method
	public String getPostDetails(String newVal) {
		StringBuilder str1 = new StringBuilder("Name: " + super.getTitle() + "\n\n");
		StringBuilder str2 = new StringBuilder("Proposed price: $" + getPropPrice()+ "\n\n");
		StringBuilder str3 = new StringBuilder("Lowest offer: $"+ getLowOffer() + "\n\n");
		String s = str1.append(str2).append(str3).toString();
		return s;
	}
	
	public int handleReply(Reply reply) {
		if(reply.getValue() > getPropPrice()) {
			//System.out.println("Offer not accepted!\n");
			return 0;
		}
		//System.out.println("Offer accepted! \n");
		super.getReplies().add(reply);
		setLowOffer(reply.getValue());
		return 1;
	}
	
	public String getReplyDetails() {
		String s1 = getPostDetails();
		String s2 = "\n-- Offer History--\n";
		
		ArrayList<String> offerList = new ArrayList<String>();
		for(int i=0; i<super.getReplies().size();i++) {
			offerList.add(super.getReplies().get(i).getRespId());
		}
		
		String str = s1+s2;
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
		return true;
	}
}
