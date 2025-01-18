package DataBaseSetup;

import LibraryUsers.Users;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;

public class UsersListClass {
		private  final File FileObj=new File("src/main/resources/UsersDetails.JSON");
		private final ObjectMapper JsonObj=new ObjectMapper();
		public ArrayList<Users> UsersList;

		//Methods

		//Constructor
		private UsersListClass(){}

		//Return Instance of This Class
		public static UsersListClass UsersListObj() {
				return new UsersListClass();
		}

		//Get Users List
		public ArrayList<Users> GetUsersList() {
				try
				{
						UsersList=JsonObj.readValue(FileObj,new TypeReference<>(){});
				}
				catch (Exception e){
						System.out.println("Problem At GetUsersList");
						System.out.println(e.getMessage());
				}
				return UsersList;
		}

		//Push  Users Data To File
		public void PushToFile() {
				try {
						JsonObj.writeValue(FileObj,UsersList);
				}
				catch (Exception e){
						System.out.println("Problem At PushToFile Method");
						System.out.println(e.getMessage());
				}
		}

		//Add Users To List
		public void Add(Users User) {
				UsersList.add(User);
		}
}
