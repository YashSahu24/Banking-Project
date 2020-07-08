import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import javax.servlet.*;
import javax.swing.JOptionPane;
import javax.servlet.http.*;
public class Transfer2Servlet extends HttpServlet
{
  public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String otp1=req.getParameter("otp");
    String accountnum="";
    String name="";
    Long amount1,amount2;
    SimpleDateFormat dft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now=new Date();
    String date=dft.format(now);
    HttpSession session = req.getSession();
    String accountnum2=(String)session.getAttribute("toaccount");
    String sum=(String)session.getAttribute("amount");
    long amount=Long.parseLong(sum);
    String otp=(String)session.getAttribute("otp");
    String email="";
    String email2="";
    
    try
     {
       int flag=0;
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
       Statement s = con.createStatement();
       if(otp1.equals(otp))
       {
         ResultSet rs1=s.executeQuery("select accountnumber,name from activeuser");
         if(rs1.next())
         {
           accountnum=rs1.getString(1);
           name=rs1.getString(2);
         } 
         ResultSet rs2=s.executeQuery("select amount,email from accountdetails where accountnumber='"+accountnum+"'");
         if(rs2.next())
         {             
           long amu=rs2.getInt(1);
           email=rs2.getString(2);
              if(amount<amu)
                 {
                   ResultSet rs=s.executeQuery("select amount,email from accountdetails where accountnumber='"+accountnum2+"'");
                   if(rs.next())
                   {
                     amount2=rs.getInt(1)+amount;
                     email2=rs.getString(2);
                     s.executeUpdate("update accountdetails set amount='"+amount2+"' where accountnumber='"+accountnum2+"'");
         
                     amount1=amu-amount;
                     s.executeUpdate("update accountdetails set amount='"+amount1+"' where accountnumber='"+accountnum+"'"); 
                   
                     Random r=new Random();
                     int tnr=1000000000+r.nextInt(1345656787);
                     String tn=String.valueOf(tnr);
                     s.executeUpdate("insert into transaction(accountnumber,transactionid,creditamount,DebitAmount,creditfrom,debitfrom,transferto,transactiondate) values('"+accountnum+"','"+tn+"','--','"+sum+"','--','--','"+accountnum2+"','"+date+"')");
                     s.executeUpdate("insert into transaction(accountnumber,transactionid,CreditAmount,debitamount,creditfrom,debitfrom,transferto,transactiondate) values('"+accountnum2+"','"+tn+"','"+sum+"','--','"+accountnum+"','--','--','"+date+"')");
                     String message= "Dear Customer, Your A/c No. "+accountnum+" is Debited for Rs. "+sum+" on "+date+"(Your transaction ID:"+tn+" ) Account New Balance = "+amount1;                    
                     Mail.sendmail(email,message);
                     String message2="Dear Customer, Your A/c No. "+accountnum2+" is Credited for Rs. "+sum+" on "+date+"(Your transaction ID:"+tn+" ) Account New Balance = "+amount2;
                     Mail.sendmail(email2,message2);
                     out.println("<p style='color:red;'> Welcome "+name+"<br>");
                     out.println("Account No : "+accountnum+"</p>");
                     RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
                     rd.include(req,res);
                 
                     out.println("<div class='bolc'>");
                     out.println("<center><h2>Trasaction Details<h2><br>");
                     out.println("<h4>From : A/c No.- "+accountnum); 
                     out.println("<br>TO : A/c No. "+accountnum2);
                     out.println("<br>Transaction ID : "+tn);
                     out.println("<br>Amount : "+amount);
                     out.println("</center></form></div>");
                   }
                   else
                   {
                     out.println("<p style='color:red;'> Welcome "+name+"<br>");
                     out.println("Account No : "+accountnum+"</p>");
                     RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
                     rd.include(req,res);
                     out.println("<center><h4>Incorrect Account Details...<h4></center>"); 
                   }
                 }
                 else
                 { 
                    out.println("<p style='color:red;'> Welcome "+name+"<br>");
                     out.println("Account No : "+accountnum+"</p>");
                     RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
                     rd.include(req,res);
                     out.println("<center><h4>Your Account Balance is Low...<h4></center>");    
                }
                 
             }
         }
         else
         {
           out.println("<center>Invalid OTP</center>");
            RequestDispatcher dis=req.getRequestDispatcher("verify");
            dis.include(req,res);
         }
     }
     catch(Exception e)
     {
          e.printStackTrace();
     }
   }
}
            