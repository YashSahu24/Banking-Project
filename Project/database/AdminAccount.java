import java.sql.*;
import java.util.*;
import java.io.*;
class AdminAccount
{
 	public static void main(String a[]) throws Exception
	{	
	 String username="VIJAY";
         String password="VIJAY123";
         String number="1234567890";
	try
	{
	 Class.forName("oracle.jdbc.driver.OracleDriver");
	 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","sbidatabase","sbidatabase");
	 PreparedStatement ps=con.prepareStatement("insert into admin values(?,?,?)");
           ps.setString(1,username);
           ps.setString(2,password);
           ps.setString(3,number);

           int i=ps.executeUpdate();
           if(i>0)
             System.out.println("Admin username and password save successfully..");
           else
             System.out.println("Admin username and password are not saved...");
          }
	catch(SQLException e)
	{
	 System.out.println(e);
	}
	 catch(Exception ei)
	{
	 System.out.println(ei);
	 }
 	}
}