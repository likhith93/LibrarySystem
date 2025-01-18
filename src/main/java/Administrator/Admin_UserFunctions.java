package Administrator;

import DataBaseSetup.MySQL;
import DataBaseSetup.UsersListClass;
import LibraryUsers.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin_UserFunctions {

		private static ArrayList<Users> UsersList;
		private static UsersListClass UsersListClassObj;
		private static MySQL MySQLObj;
		private static final StringBuilder Result = new StringBuilder();
		private static final StringBuilder Query=new StringBuilder(5);
		private static ResultSet ResultSet;

		//Start System Gets Users List and UsersListClass Object
		public static void StartSystem() {
				UsersListClassObj=UsersListClass.UsersListObj();
				UsersList= UsersListClassObj.GetUsersList();
				MySQLObj=MySQL.MySQLObject();
		}

		//Stops System ,Stores Data to File
		public void StopSystem() {
				UsersListClassObj.PushToFile();
		}

		//Checks User Credentials
		public Users GetUserCredentials(String Name, String Password) {
				Users User=null;
				for(Users i:UsersList){
						if (i.Name.equals(Name) && i.Password.equals(Password)){
								return i;
						}
				}
				return  User;
		}

		//Adds Users to UsersList
		public  void AddUser(Users User) {
				int flag=1;
				for(Users i: UsersList)
				{
						if(i.Name.equals(User.Name))
						{
								flag=0;
								break;
						}
				}
				if (flag==1)
				{
						UsersListClassObj.Add(User);
						System.out.println("Account Created !!");
				}
				else
				{System.out.println("Try Another Name , An Account Exists With This Name ");}
		}
//     SQL Queries Methods

		//Give All Books In DB
		public StringBuilder GiveAllBooks() {
				try
				{
						Query.setLength(0);
						Query.append("Select  BookID,Title from library_system.books");
						ResultSet = MySQLObj.ExecuteQuery(Query);
						System.out.println(ResultSet);
						while (ResultSet.next()) {
								System.out.println();
								Result
									.append("BookID ")
									.append(ResultSet.getInt("BookID"))
									.append(", Name : ")
									.append(ResultSet.getString("Title"))
									.append("\n");
						}
						return Result;
				}
				catch (Exception e){
						System.out.println("Problem At GiveAllBooks:Admin_UserFunctions");
						System.out.println(e.getMessage());
				}
				return Result;
		}

		//Find Book
		public StringBuilder FindBook(String BookName) {
				Result.setLength(0);
				try
				{
						Query.setLength(0);
						Query.append("select BookID,Title,CopiesNumber from library_system.books where Title= '").append(BookName).append("'");
						ResultSet=MySQLObj.ExecuteQuery(Query);
						while(ResultSet.next())
						{
								Result.append("BookID : ")
									.append(ResultSet.getInt("BookID"))
									.append(", Title : ")
									.append(ResultSet.getString("Title"))
									.append(", No Of Copies Present : ")
									.append(ResultSet.getInt("CopiesNumber"));
						}
				}
				catch (Exception e){
						System.out.println("Problem At FindBook : Admin_UserFunctions");
						System.out.println(e.getMessage());
				}
				return Result;
		}

		//Borrow a Book
		public Integer FindBookAndAddBook(String BookName) {
				try
				{
						Query.setLength(0);
						Query.append("select BookID,CopiesNumber  from library_system.books where Title='").append(BookName).append("'");
						ResultSet=MySQLObj.ExecuteQuery(Query);
						Integer BookID=null;
						if(ResultSet.next()){
								int NumberOfCopies=ResultSet.getInt("CopiesNumber");
								BookID=ResultSet.getInt("BookID");
								if(NumberOfCopies==0){
										return null;
								}
								Query.setLength(0);
								Query.append("update books set CopiesNumber= ").append(NumberOfCopies-1).append(" where BookID= ").append(BookID);
								MySQLObj.ExecuteUpdate(Query);
						}
						return BookID;
				}
				catch (Exception e){
						System.out.println("Problem At FindBookAndAddBook : Admin_UserFunctions");
						System.out.println(e.getMessage());
				}
				return null;
		}

		//Return a Book
		public void ReturnBook(String Book,Users User)  {
				try {
						Query.setLength(0);
						Query.append("select BookID,CopiesNumber from books where title ='").append(Book).append("'");
						ResultSet = MySQLObj.ExecuteQuery(Query);
						Integer BookID;
						int NumberOfCopies;
						if (ResultSet.next()) {
								BookID = ResultSet.getInt("BookID");
								NumberOfCopies=ResultSet.getInt("CopiesNumber");
								if(User.BooksBorrowedList.remove(BookID)==Boolean.TRUE) {
										Query.setLength(0);
										Query.append("update books set CopiesNumber=").append(NumberOfCopies+1).append(" where BookID=").append(BookID);
										MySQLObj.ExecuteUpdate(Query);
								}
								else
								{System.out.println("Book Not Present With You");}
						}
				}
				catch (SQLException e){
						System.out.println("Problem At Return Book : Admin_UserFunctions");
						System.out.println(e.getMessage());
				}
		}

		//Give All Users
		public List<String> GetUsers(){
				List<String> GetUsers = new ArrayList<>();
				for (Users i:UsersList){
						GetUsers.add(i.Name);
				}
				return GetUsers;
		}

		//Add a Book
		public Boolean AddBook(){
				Scanner input=new Scanner(System.in);
				try{
						Query.setLength(0);
						System.out.println("Enter BookID :");
						int BookID=input.nextInt();
						input.nextLine();
						System.out.println("Enter Book Name :");
						String Title =input.nextLine();
						System.out.println("Enter Author Name :");
						String Author=input.nextLine();
						System.out.println("Enter Genre Name :");
						String Genre=input.nextLine();
						System.out.println("Enter  Number Of Copies :");
						int  NumCopies=input.nextInt();
						Query.append("Insert into books(BookID,Title,Author,Genre,CopiesNumber) values (").append(BookID).append(",'").append(Title).append("', '").append(Author).append("' ,'").append(Genre).append("',").append(NumCopies).append(")");
						MySQLObj.ExecuteUpdate(Query);
						return Boolean.TRUE;
				}
				catch (Exception e){
						System.out.println("Problem At AddBook : Admin");
						System.out.println(e.getMessage());
						return Boolean.FALSE;
				}
		}

		//Delete a Book
		public Boolean DeleteBook(String BookName){
				try {
						Query.setLength(0);
						Query.append("select BookID from Books where Title='").append(BookName).append("'");
						ResultSet=MySQLObj.ExecuteQuery(Query);
						Query.setLength(0);
						Query.append("delete from Books where Title='").append(BookName).append("'");
						MySQLObj.ExecuteUpdate(Query);
						Integer BookID = null;
						if(ResultSet.next())
						{BookID=ResultSet.getInt("BookID");}
						for(Users i: UsersList){
								if (i.BooksBorrowedList.contains(BookID)==Boolean.TRUE)
								{i.BooksBorrowedList.remove(BookID);}
						}
						return Boolean.TRUE;
				}
				catch (Exception e){
						System.out.println("Problem At AddBook : Admin");
						System.out.println(e.getMessage());
						return Boolean.FALSE;
				}
		}
}
