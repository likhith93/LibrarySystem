package org.example;

import Administrator.Admin_UserFunctions;
import LibraryUsers.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
		public static void main(String[] args) {
				Scanner input = new Scanner(System.in);
				System.out.println("Hey There!!\uD83D\uDE0A");
				System.out.println("User!! Press 1   Admin!! Press 2 ");
				int UserOrAdmin = input.nextInt();
				Admin_UserFunctions AdminObj = new Admin_UserFunctions();
				Admin_UserFunctions.StartSystem();
				//User OR Admin_UserFunctions Check (User Here)
				if (UserOrAdmin == 1) {
						System.out.println("Login!! Press 1   SignUp!! Press 2");
						int LoginOrSignUp = input.nextInt();
						input.nextLine();
						System.out.println("Enter Name");
						String Name = input.nextLine();
						System.out.println("Enter Password");
						String Password = input.nextLine();
						//Login OR SignUp Check (Login Here)
						if (LoginOrSignUp == 1) {
								Users User=AdminObj.GetUserCredentials(Name,Password);
								System.out.println(User);
								//Valid User
								if (User!=null) {
										while(Boolean.TRUE) {
												System.out.println("Display Books: 1, Find Book: 2, Borrow Book: 3, Return Book: 4, Exit System: 9 ");
												System.out.println("Enter Option");
												int Option = input.nextInt();
												//Display Available Books
												if(Option==1){
														System.out.println("Available Books Are : ");
														System.out.println(AdminObj.GiveAllBooks());
												}
												//Find Book Name
												if(Option==2) {
														System.out.println("Enter Book Name :");
														input.nextLine();
														String BookName=input.nextLine();
														StringBuilder Temp=AdminObj.FindBook(BookName);
														if(Temp.length()!=0)
														{System.out.println(AdminObj.FindBook(BookName));}
														else
														{System.out.println("Book Not Found");}
												}
												//Borrow Book
												if(Option==3) {
														System.out.println("Enter Book Name You Want to Borrow : ");
														input.nextLine();
														String BookName=input.nextLine();
														Integer BookID=AdminObj.FindBookAndAddBook(BookName);
														//If Book Not Found
														if(BookID==null)
														{System.out.println("Book Not Found Try For Another One");}
														//If Book Found
														else
														{
																//If User Doesn't Contain The Book
																if(User.BooksBorrowedList.contains(BookID)==Boolean.FALSE) {
																		System.out.println("Book Available To Borrow");
																		User.BooksBorrowedList.add(BookID);}
																//If Book Already Exists With User
																else
																{System.out.println("You Have This Book Already");}
														}
												}
												//Return Book
												if(Option==4) {
														System.out.println("Enter Name Of The Book You Want To Return : ");
														input.nextLine();
														String Book=input.nextLine();
														AdminObj.ReturnBook(Book,User);
												}
												//Stop System
												if (Option==9) {
														AdminObj.StopSystem();
														break;
												}
										}
								}
								//Invalid Credentials
								else {System.out.println("Invalid Credentials Please Try Again");}
						}
						//Login OR SignUp Check (SignUp Here)
						else {
								ArrayList<Integer> BooksBorrowedList=new ArrayList<>();
								Users Obj = new Users(Name, Password, BooksBorrowedList);
								AdminObj.AddUser(Obj);
								AdminObj.StopSystem();
						}
				}
				//User OR Admin_UserFunctions Check (Admin_UserFunctions Here)
				else {
						while(Boolean.TRUE)
						{
								System.out.println("1 :Display Users, 2 :Add a Book, 3 :Delete a Book,  4:Exit");
								int Option=input.nextInt();
								//Display Users
								if(Option==1){
										System.out.println("Get Users");
										List<String> GetUsers=AdminObj.GetUsers();
										for(String s:GetUsers){
												System.out.println(s);
										}
								}
								//Add a Book
								if(Option==2) {
										System.out.println("Add a Book");
										if(AdminObj.AddBook()==Boolean.TRUE)
										{System.out.println("Book Added Successfully");}
										else
										{System.out.println("Book Addition Not Successful");}
								}
								//Delete a Book
								if(Option==3){
										System.out.println("Enter Book Name :");
										input.nextLine();
										String BookName=input.nextLine();
										if(AdminObj.DeleteBook(BookName)==Boolean.TRUE)
										{System.out.println("Book Deleted Successfully");}
										else
										{System.out.println("Book Deletion UnSuccessful");}
//										System.out.println("Delete a Book");
								}
								//Stop System
								if(Option==4) {
										AdminObj.StopSystem();
										break;
								}
						}
				}
		}
}
