package DataBaseSetup;

import java.sql.*;

public class MySQL {
		private static final MySQL MySQLObj=new MySQL();

		private MySQL(){}

		public  static  MySQL MySQLObject() {
				return MySQLObj;
		}

		private static Statement Connection() {
				String url = "jdbc:mysql://localhost:3306/library_system";  // Change to your database URL
				String user = "root";  // Change to your MySQLQuery username
				String password = "Htihkil13@";  // Change to your MySQLQuery password
				Statement Statement;
				try {
						Connection Connection = DriverManager.getConnection(url, user, password);
						Statement=Connection.createStatement();
				} catch (SQLException ex) {
						// Handle any SQL errors
						System.out.println("Connection failed: " + ex.getMessage());
						throw new RuntimeException(ex);  // Optionally re-throw the exception
				}
				return Statement;
		}

		public ResultSet ExecuteQuery(StringBuilder Str)  {
				try{
						Statement Obj=Connection();
						return Obj.executeQuery(String.valueOf(Str));
				}
				catch (Exception e)
				{
						System.out.println("Problem At ExecuteQuery : MySQL");
						System.out.println(e.getMessage());
				}
				return null;
		}

		public void ExecuteUpdate(StringBuilder Str)  {
				try
				{
						Statement Obj=Connection();
						Obj.executeUpdate(Str.toString());
				}
				catch (Exception e){
						System.out.println("Problem At ExecuteUpdate : MySQL");
						System.out.println(e.getMessage());
				}
		}
}
