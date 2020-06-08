package model;

import javafx.scene.image.Image;
import java.util.ArrayList;

public class Job extends Post{
	private static int jobNumId = 0;
	private String jobId;
	private double propPrice;
	private double lowOffer;
	
	//Constructor that calls constructor of Post class first
	public Job(String title, String desc, double propPrice) {
		super(title,desc);
		this.propPrice = propPrice;
		this.lowOffer = 0;
		setJobNumId(++jobNumId);
		generateJobId();
		super.setPostId(getJobId());
	}

	public Job(String title, String desc, double propPrice, Image image) {
		super(title,desc,image);
		this.propPrice = propPrice;
		this.lowOffer = 0;
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
	
	//2 setter methods
	public void setJobNumId(int n) {
		jobNumId = n;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
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
		StringBuilder str = new StringBuilder("ID:             " +this.jobId + "\n");
		String s1 = super.getPostDetails();
		StringBuilder str1 = new StringBuilder("Proposed price: " +this.propPrice+ "\n");
		StringBuilder str2 = new StringBuilder("Lowest Offer:   " +this.lowOffer+ "\n");
		String s = str.toString()+s1+str1.append(str2).toString();
		return s;
	}
	
	//This method overrides the Post class method
	public String getPostDetails(String newVal) {
		StringBuilder str1 = new StringBuilder("Name: " + super.getTitle() + "\n");
		StringBuilder str2 = new StringBuilder("Proposed price: $" + this.propPrice+ "\n");
		StringBuilder str3 = new StringBuilder("Lowest offer: $"+ this.lowOffer + "\n");
		String s = str1.append(str2).append(str3).toString();
		return s;
	}
	
	public boolean handleReply(Reply reply) {
		if(reply.getValue() > this.propPrice) {
			System.out.println("Offer not accepted!\n");
			return false;
		}
		System.out.println("Offer accepted! \n");
		super.getReplies().add(reply);
		this.lowOffer = reply.getValue();
		return true;
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
