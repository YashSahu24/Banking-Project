import   java.sql.*;
import  java.io.*;
class sbidatabase
{

	public  static void main(String  args[]) throws Exception

	{
	  try
	  {

      	    Class.forName("oracle.jdbc.driver.OracleDriver");

	    Connection c =  DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","java");
	    Statement   s = c.createStatement();

            //First Table customer
            s.executeUpdate("create table employee(username varchar(20) NOT NULL,password varchar(20) NOT NULL,email varchar(50) NOT NULL,contact number NOT NULL)");
	    //Second Table accountdetails
            s.executeUpdate("create table accountdetails(name varchar(20) NOT NULL,account number NOT NULL,adhar number NOT NULL,contact number NOT NULL,fathername varchar(20) NOT NULL, accounttype varchar(20) NOT NULL, balance number NOT NULL,gender varchar(20) NOT NULL, updatedata varchar(20))");   
            //Third Table credit
            s.executeUpdate("create table credit(name varchar(20) NOT NULL, amount number NOT NULL,creditdate date NOT NULL)");
            //Fourth Table debit
            s.executeUpdate("create table debit(name varchar(20) NOT NULL, amount number NOT NULL, debitdate date NOT NULL)");       
            //Fifth Table transaction
            s.executeUpdate("create table transaction(name varchar(20) NOT NULL,Sender number NOT NULL, reciever number NOT NULL,amount number NOT NULL,transactiondate date NOT NULL)");
            //Sixth Table Admin
            s.executeUpdate("create table admin(username varchar(20) NOT NULL,password varchar(20) NOT NULL,contact number NOT NULL)");
            s.close();
          }
         catch(Exception e)
          {
            System.out.println(e);
          }
        }
}