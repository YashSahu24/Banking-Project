import java.io.*;
import java.sql.*;
import javax.servlet.*;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.servlet.http.*;
public class Transfer1Servlet extends HttpServlet
{
  public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String toaccountnum=req.getParameter("acnumber");
    String money=req.getParameter("amount");
    long money1=Long.parseLong(money);
    String email="";
    String name="";
    String accountnum="";
    String access="";
    
    try
     {
           Class.forName("oracle.jdbc.driver.OracleDriver");
           Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
           Statement s = con.createStatement();
           ResultSet rs=s.executeQuery("select accountnumber,name from activeuser");
           if(rs.next())
           {
             accountnum=rs.getString(1);
             name=rs.getString(2);
           } 
           ResultSet rs1=s.executeQuery("select email,accountaccess from accountdetails where accountnumber='"+accountnum+"'");
           if(rs1.next())
           {
             email=rs1.getString(1);
             access=rs1.getString(2);
           } 
           if(access.equals("true"))
           {
             if(!accountnum.equals(toaccountnum))
             {
               Random r=new Random();
               int ren = 100000+r.nextInt(123456);
               String otp=String.valueOf(ren);
               HttpSession session1=req.getSession();
               session1.setAttribute("toaccount",toaccountnum);
               session1.setAttribute("amount",money);
               session1.setAttribute("otp",otp);
               Mail.otpmail(email,otp,money1);
               out.println("<center> otp successfully sent to your registered mail id : "+email+"</center><br><br>");
               RequestDispatcher des=req.getRequestDispatcher("verify");
               des.include(req,res);
             }
             else
             {
                     out.println("<p style='color:red;'> Welcome "+name+"<br>");
                     out.println("Account No : "+accountnum+"</p>");
                     RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
                     rd.include(req,res);
                     out.println("<center><h4>With In Account Transfer Not Possible.<h4></center>");    
             }
           }
           else
           {
              out.println("<p style='color:red;'> Welcome "+name+"<br>");
              out.println("Account No : "+accountnum+"</p>");
              RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
              rd.include(req,res);
              out.println("<center><h4>Account Verification Not Completed.<br>Transfer Access Not Allowed.<br>Please Verify Your Account First.<br> Contact To Admin to Verify account.<h4></center>");
           }
     }
     catch(Exception e)
     {
          out.println(e);
     }
   }
}
            