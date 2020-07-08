import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;
public class CreditServlet extends HttpServlet
{
 public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
 {
  res.setContentType("text/html");
  PrintWriter out=res.getWriter();
  String acnumber=req.getParameter("acnumber");
  String name="";
  String email="";
  String amount2=req.getParameter("amount");
  long amount=Long.parseLong(amount2);
  long amount1;
  SimpleDateFormat dft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  Date now=new Date();
  String date=dft.format(now);
  

  try
   {
     Class.forName("oracle.jdbc.driver.OracleDriver");
     Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
     Statement s = con.createStatement();
     ResultSet rs=s.executeQuery("select name,amount,email from accountdetails where accountnumber='"+acnumber+"'");
     if(rs.next())
     {
       name=rs.getString(1);
       amount1=amount+rs.getInt(2);
       email=rs.getString(3);
       s.executeUpdate("update accountdetails set amount='"+amount1+"' where accountnumber='"+acnumber+"'");
       Random r=new Random();
       int trn=1000000000+r.nextInt(1345656787);
       String tn=String.valueOf(trn);
       String from="BANK";
       s.executeUpdate("insert into transaction(accountnumber,transactionid,CreditAmount,debitamount,CreditFrom,debitfrom,transferto,transactiondate) values('"+acnumber+"','"+tn+"','"+amount2+"','--','"+from+"','--','--','"+date+"')");
       String message="Dear Customer, Your A/c No. "+acnumber+" is Credited for Rs. "+amount2+" on "+date+"(Your transaction ID:"+tn+" ) Account New Balance = "+amount1;
       Mail.sendmail(email,message);
      
       RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
       rd.include(req,res);
       out.println("<html><head><style>h3{color:red; font-size: 1.7em}</style></head><body><div class='box2'");
       out.println("<center><h3><br>Account Number : "+acnumber);
       out.println("<br><br>Transaction ID : "+tn); 
       out.println("<br><br>Account Holder Name : "+name);
       out.println("<br><br>Credit Ammount : "+amount);
       out.println("<br><br>Account Balance : "+amount1+"</h3></center></div></body></html>");  
     }
     else
     { 
        out.println("<center><p style='color:red;'><br>Incorrect AccountNumber.....</p></center>");
        RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
        rd.include(req,res);      
     }      
   }
   catch(Exception ie)
   {
     out.println(ie);
   }
   out.close();
  }
}