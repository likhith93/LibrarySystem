//package DBQueries;
//
//import DataBaseSetup.MySQL;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//
//public class MySQLQuery {
//		MySQL MySQLObj;
//		public MySQLQuery()
//		{
//				MySQLObj=MySQL.MySQLObject();
//		}
//
//		public ResultSet QueryResult() throws SQLException
//		{
//				 String Query="Select * from books";
//				return MySQLObj.ExecuteQuery(Query);
//		}
//
//		public static void main(String[] args) throws SQLException {
//				MySQLQuery Obj=new MySQLQuery();
//				ResultSet ResultSet=Obj.QueryResult();
//				System.out.println(ResultSet);
//				while(ResultSet.next())
//				{
//						int  BookId=ResultSet.getInt("Bookid");
//						String  Title=ResultSet.getString("Title");
//						String  Author=ResultSet.getString("Author");
//						String  Genre=ResultSet.getString("Genre");
//
//						if (BookId==1)
//							System.out.println("BookId : "+BookId+ "Title : "+Title+" Author : "+Author+" Genre : "+Genre);
//				}
//		}
//
//}
