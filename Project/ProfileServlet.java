import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.swing.JOptionPane;
import javax.servlet.http.*;
public class ProfileServlet extends HttpServlet
{
  public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String accountnum="";
    String name="";
    
    try
     {
       int flag=0;
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
       Statement s = con.createStatement();
       ResultSet rs1=s.executeQuery("select accountnumber from activeuser");
       while(rs1.next())
       accountnum=rs1.getString(1);
         ResultSet rs=s.executeQuery("select accountnumber,name,fathername,aadhar,birthday,gender,email,contact,amount,accounttype,address,accountdate from accountdetails where accountnumber='"+accountnum+"'");
         while(rs.next())
         {    flag=1;
                 String acnumber=rs.getString(1);
                  name=rs.getString(2);
                 String fname=rs.getString(3);
                 String aadhar=rs.getString(4);
                 String bday=rs.getString(5);
                 String gender=rs.getString(6);
                 String email=rs.getString(7);
                 String contact=rs.getString(8);
                 String amount=rs.getString(9);
                 String atype=rs.getString(10);
                 String address=rs.getString(11);
                 String adate=rs.getString(12);
                 out.println("<p style='color:red;'> Welcome "+name+"<br>");
                 out.println("Account No : "+accountnum+"</p>");
                 RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
                 rd.include(req,res);
                 out.println("<html><body><div class='box'");
                 out.println("<p><br>Account Number : "+acnumber); 
                 out.println("<br><br>Account Holder Name : "+name);
                 out.println("<br><br>Father's Name : "+fname);
                 out.println("<br><br>Aadhar Number : "+aadhar);
                 out.println("<br><br>Date of Birth : "+bday);
                 out.println("<br><br>Gender : "+gender);
                 out.println("<br><br>Email ID : "+email);
                 out.println("<br><br>Contact No : "+contact);
                 out.println("<br><br>Account Balance : "+amount);
                 out.println("<br><br>Account Type : "+atype);
                 out.println("<br><br>A/C Opening Date : "+adate);
                 out.println("<br><br>Address : "+address+"</p></div></html>");
                 break;
         }
        if(flag==0)
        {
            out.println("<p style='color:red;'> Welcome "+name+"<br>");
            out.println("Account No : "+accountnum+"</p>");
            RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
            rd.include(req,res);
        }
     }
     catch(Exception e)
     {
          out.println(e);
     }
   }
}
            