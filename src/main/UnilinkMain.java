package main;

import model.*;

import java.util.*;

public class UnilinkMain {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		Scanner scs = new Scanner(System.in);
		
		int choice = 1;
		ArrayList<Post> user = new ArrayList<Post>();
		int userIndex = 0;
		
		while(choice != 2) { //This loop checks if choice is 2 then the program will terminate 
			System.out.println("*****UniLink System*****");
			System.out.println("1. Log in");
			System.out.println("2. Quit");
			System.out.print("Enter your choice: ");
			choice = scn.nextInt();
			
			if(choice < 1 || choice > 2) {
				System.out.println("Invalid Input! Press 1 to Log in OR 2 to Quit the Application");
				System.out.println();
			}
			else if(choice == 1) {
				System.out.print("Enter username: ");
				String userId = scs.nextLine(); 
				System.out.println();
				int menuChoice = 0;
				
				while(menuChoice != 9) {
					//Student Menu
					System.out.println("Welcome "+userId);
					System.out.println("***Student Menu***");
					System.out.println("1. New Event Post");
					System.out.println("2. New Sale Post");
					System.out.println("3. New Job Post");
					System.out.println("4. Reply To Post");
					System.out.println("5. Display My Posts");
					System.out.println("6. Display All Posts");
					System.out.println("7. Close Post");
					System.out.println("8. Delete Post");
					System.out.println("9. Log Out");
					System.out.print("Enter your choice: ");
					menuChoice = scn.nextInt();
					
					//These if statements checks for the option selected from Student Menu
					if(menuChoice == 1) {//New event post is created here
						System.out.println();
						System.out.println("Enter details of the event below:");
						System.out.print("Name: ");
						String name = scs.nextLine();
						
						System.out.print("Description: ");
						String desc = scs.nextLine();
						
						System.out.print("Venue: ");
						String venue = scs.nextLine();
						
						System.out.print("Date: ");
						String date = scs.nextLine();
						
						System.out.print("Capacity: ");
						int capacity = scn.nextInt();
						
						user.add(new Event(name, desc, userId, venue, date, capacity));
						System.out.println("Success! Your event post has been created with id " + user.get(userIndex).getPostId());
						System.out.println();
						++userIndex;
					}
					
					//New sale post is created here
					else if(menuChoice == 2) {
						System.out.println();
						System.out.println("Enter details of the item to sale below:");
						System.out.print("Name: ");
						String name = scs.nextLine();
						
						System.out.print("Description: ");
						String desc = scs.nextLine();
						
						System.out.print("Asking price: ");
						double askPrice = scn.nextDouble();
						
						System.out.print("Minimum raise: ");
						double minRaise = scn.nextDouble();
						
						user.add(new Sale(name, desc, userId, askPrice, minRaise));
						System.out.println("Success! Your sale post has been created with id " + user.get(userIndex).getPostId());
						System.out.println();
						++userIndex;
					}
					
					//New Job post is created here
					else if(menuChoice == 3) {
						System.out.println();
						System.out.println("Enter details of the job below:");
						System.out.print("Name: ");
						String name = scs.nextLine();
						
						System.out.print("Description: ");
						String desc = scs.nextLine();
						
						System.out.print("Propose price: ");
						double propPrice = scn.nextDouble();
						
						user.add(new Job(name, desc, userId, propPrice));
						System.out.println("Success! Your job post has been created with id " + user.get(userIndex).getPostId());
						System.out.println();
						++userIndex;
					}
					
					// Replying to a post
					else if(menuChoice == 4) {
						int flag = 0;
						while(flag != 1) {
							System.out.print("Enter post id or 'Q' to quit: ");
							String s = scs.nextLine();
							if(s.equals("Q")) {
								break;
							}
							for(int i=0; i<user.size(); i++) {// This loop iterates through the array list of posts and checks for valid ID
								if(user.get(i).getStudId().equals(userId)) {
									System.out.println("Replying to your own post is invalid");
									break;
								}
								else if(user.get(i).getPostId().equals(s)) {
									if(user.get(i).getStatus().equals("CLOSED")) {
										System.out.println("You cannot reply! This post is already closed");
										break;
									}
									flag = 1;
									System.out.println(user.get(i).getPostDetails("GET"));
									int flag2 = 0;
									while(flag2 != 1) {
										user.get(i).printType();//This method asks the user for a response depending on the type of post
										String resp = scs.nextLine();
										if(resp.equals("Q")) {
											break;
										}
										else if (user.get(i).responseChecker(resp)) {
											double val = Double.parseDouble(resp);
											Reply reply = new Reply(user.get(i).getPostId(),val,userId);
											boolean check = user.get(i).handleReply(reply); // This tests if reply is added or not
											if(check == true) {
												flag2 = 1;
											}
										}
										else {
											System.out.println("Invalid input!");
										}
									}
								}
							}
							if(flag == 0) {
								System.out.println("Invalid post id! Post not found");
							}
						}
					}
					
					//View My posts
					else if(menuChoice == 5) {
						for(int i=0; i<user.size(); i++) {
							if(user.get(i).getStudId().equals(userId)) {//This checks if user id is same as Creator id
								System.out.println("\n***MY POSTS***\n");
								System.out.print(user.get(i).getReplyDetails());
								System.out.println("\n______________________________");
								System.out.println();
							}
						}
					}
					
					//Display all posts
					else if(menuChoice == 6) {
						for(int i=0; i<user.size(); i++) {
							System.out.println("\n***ALL POSTS***\n");
							System.out.print(user.get(i).getPostDetails());
							System.out.println("______________________________");
							System.out.println();
						}
					}
					
					//Close a post
					else if(menuChoice == 7) {
						int flag = 0;
						while(flag != 1) {
							System.out.print("Enter post id or 'Q' to quit: ");
							String s = scs.nextLine();
							if(s.equals("Q")) {
								break;
							}
							for(int i=0; i<user.size(); i++) {// This loop iterates through the array list of posts and checks for valid ID
								if(user.get(i).getStudId().equals(userId)) {
									flag = 1;
									user.get(i).setStatus("CLOSED");
									System.out.println("Succeeded! Post closed.");
								}
								else {
									System.out.println("Request denied! You're not the owner of this post.");
								}
							}
							if(flag == 0) {
								System.out.println("Invalid post id! Post not found");
							}
						}
					}
					
					//Delete a post
					else if(menuChoice == 8) {
						int flag = 0;
						while(flag != 1) {
							System.out.print("Enter post id or 'Q' to quit: ");
							String s = scs.nextLine();
							if(s.equals("Q")) {
								break;
							}
							for(int i=0; i<user.size(); i++) {// This loop iterates through the array list of posts and checks for valid ID
								if(user.get(i).getStudId().equals(userId)) {
									flag = 1;
									String id = user.get(i).getPostId();
									user.remove(i);
									System.out.println("Succeeded! Post " + id + " closed.");
								}
								else {
									System.out.println("Request denied! You're not the owner of this post.");
								}
							}
							if(flag == 0) {
								System.out.println("Invalid post id! Post not found");
							}
						}
					}
					
					/*FIND an EXCEPTION WAY*/
					//Logout
					else if(menuChoice == 9) {}
					
					//This checks for invalid input for student menu
					else {
						System.out.println("You must enter a value between [1 9]");
						System.out.print("Enter your choice: ");
						menuChoice = scn.nextInt();
					}
				}
			}
			else {//This Quits the application
				System.out.println("Thank You!! BYE BYE");
			}
		}
		scn.close();
		scs.close();
	}
}