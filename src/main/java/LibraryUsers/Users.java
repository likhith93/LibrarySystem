package LibraryUsers;

import java.util.ArrayList;

public class Users {
		public  String Name;
		public String Password;
		public ArrayList<Integer> BooksBorrowedList;

		//Constructor
		public Users(String name, String password, ArrayList<Integer> BooksBorrowedList) {
				Name = name;
				Password = password;
				this.BooksBorrowedList = BooksBorrowedList;
		}

		//For JSON To Object Purposes(Files)
		public Users(){}
}
