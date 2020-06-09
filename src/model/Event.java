package model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.*;

public class Event extends Post {
	private static int numId = 0;
	private String eventId;
	private SimpleStringProperty venue;
	private SimpleStringProperty date;
	private SimpleIntegerProperty capacity;
	private SimpleIntegerProperty attCount;
	
	//Constructor that calls constructor of Post class first
	public Event(String title, String desc, String venue, String date, int capacity, String creatorID) throws FileNotFoundException {
		super(title,desc, creatorID);
		this.venue =new SimpleStringProperty(venue);
		this.date = new SimpleStringProperty(date);
		this.capacity = new SimpleIntegerProperty(capacity);
		this.attCount = new SimpleIntegerProperty(0);
		setNumId(++numId);
		generateId();
		super.setPostId(getEventId());
	}

	public Event(String title, String desc, String venue, String date, int capacity, Image photo, String creatorID) {
		super(title,desc,photo,creatorID);
		this.venue =new SimpleStringProperty(venue);
		this.date = new SimpleStringProperty(date);
		this.capacity = new SimpleIntegerProperty(capacity);
		this.attCount = new SimpleIntegerProperty(0);
		setNumId(++numId);
		generateId();
		super.setPostId(getEventId());
	}

	
	// Getter methods
	public int getNumId() {
		return numId;
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public int getAttCount() {
		return attCount.get();
	}

	public String getVenue(){
		return venue.get();
	}

	public String getDate(){
		return date.get();
	}

	public int getCapacity(){
		return capacity.get();
	}
	
	//Setter methods
	public void setEventId(String eId) {
		this.eventId = eId;
	}
	
	public void setNumId(int n) {
		numId = n;
	}

	public void setAttCount(int count){
		this.attCount = new SimpleIntegerProperty(count);
	}

	public void setCapacity(int capacity){
		this.capacity = new SimpleIntegerProperty(capacity);
	}

	public void setVenue(String venue){
		this.venue = new SimpleStringProperty(venue);
	}

	public void setDate(String date){
		this.date = new SimpleStringProperty(date);
	}

	//This method generates Auto event ID
	public void generateId() {
		String s1 = "EVE";
		String str = String.format("%03d", getNumId());
		String s = s1+str;
		setEventId(s);
	}
	
	//This method overrides the Post class method
	public String getPostDetails() {
		StringBuilder str = new StringBuilder("ID:            " +this.eventId + "\n");
		String s1 = super.getPostDetails();
		StringBuilder str1 = new StringBuilder("Venue:         " +this.venue + "\n");
		StringBuilder str2 = new StringBuilder("Date:          " +this.date + "\n");
		StringBuilder str3 = new StringBuilder("Capacity:      " +this.capacity + "\n");
		StringBuilder str4 = new StringBuilder("Attendees:     " +this.attCount + "\n");
		String s = str.toString()+s1+str1.append(str2).append(str3).append(str4).toString();
		return s;
	}
	
	//This method overrides the Post class method
	public String getPostDetails(String newVal) {
		StringBuilder str1 = new StringBuilder("Name: " + super.getTitle() + "\n");
		StringBuilder str2 = new StringBuilder("Venue: " + this.venue + "\n");
		StringBuilder str3 = new StringBuilder("Status: "+ super.getStatus() + "\n");
		String s = str1.append(str2).append(str3).toString();
		return s;
	}
	
	public boolean handleReply(Reply reply) {
		if((getCapacity()-getAttCount()) > 0) {
			for(int i=0; i<super.getReplies().size();i++) {	
				if(super.getReplies().get(i).getRespId().equals(reply.getRespId())) {
					System.out.println("You are already registered\n");
					return false;
				}
			}
			setAttCount(getAttCount()+1);
			super.getReplies().add(reply);
			if((getCapacity()-getAttCount()) == 0) {
				super.setStatus("CLOSED");
			}
			System.out.println("Event Registration Accepted\n");
			return true;
		}
		else {
			System.out.println("Event capacity is full.\n");
			return false;
		}
	}
	
	public String getReplyDetails() {
		String s1 = getPostDetails();
		if(getAttCount() == 0) {
			String s3 = "\nAttendee list: Empty";
			String s = s1+s3;
			return s;
		}
		
		ArrayList<String> attList = new ArrayList<String>();
		for(int i=0; i<super.getReplies().size();i++) {
			attList.add(super.getReplies().get(i).getRespId());
		}
		String s2 = "\nAttendee list: ";
		String str = s1+s2;
		for(int i=0; i<attList.size(); i++) {
			if(i < (attList.size()-1)) {
				str += attList.get(i)+", ";
			}
			else if (i == (attList.size()-1)){
				str += attList.get(i);
			}
		}
		return str;
	}
	
	public void printType() {
		System.out.print("Enter '1' to join the event or 'Q' to quit: ");
	}
	
	public boolean responseChecker(String resp) {
		return resp.equals("1");
	}
}