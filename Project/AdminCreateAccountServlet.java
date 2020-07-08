import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminCreateAccountServlet extends HttpServlet
{
  public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String name=req.getParameter("aname");
    String fname=req.getParameter("fname");
    int aadhar=Integer.parseInt(req.getParameter("aadhar"));
    String bday=req.getParameter("bday");        
    String gender=req.getParameter("gender");
    String email=req.getParameter("email");
    String number=req.getParameter("number");
    int amount=Integer.parseInt(req.getParameter("amount"));
    String atype=req.getParameter("atype");
    String address=req.getParameter("address");         
    Date date=new Date();

    int anumber=1111222210;
  
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
      Statement s=con.createStatement();
      PreparedStatement ps1=con.prepareStatement("select max(accountnumber) from accountdetails");
      ResultSet rs=ps1.executeQuery();
      if(rs.next())
       anumber=rs.getInt(1);   
       anumber++;



      ResultSet rs1=s.executeQuery("select * from accountdetails where contact='"+number+"'");
      if(rs.next())
      {
        out.println("<center><p style='color:darkblue;'><br>Mobile Number Already Used.<br> Please Use Different Mobile Number.</center>");
         RequestDispatcher rd=req.getRequestDispatcher("admincreateaccount.html");
         rd.include(req,res);
      }
      else
      {
      PreparedStatement ps=con.prepareStatement("insert into accountdetails values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
      ps.setInt(1,anumber);
      ps.setString(2,name);
      ps.setString(3,fname);
      ps.setInt(4,aadhar);
      ps.setString(5,bday);
      ps.setString(6,gender);
      ps.setString(7,email);
      ps.setString(8,number);
      ps.setInt(9,amount);
      ps.setString(10,atype);
      ps.setString(11,address);
      java.sql.Date dateJoin1= new java.sql.Date( date.getTime() );
      ps.setDate(12,dateJoin1);
      ps.setString(13,"true");
      int i=ps.executeUpdate();
      if(i>0)
       {
         RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
         rd.include(req,res);
         out.println("<center><p style='color:darkblue;' 'size=4;'><br>Account Created Successfully....<br>Your Account Number Is ="+anumber+"</p> </center>");
       }
       else
       {
         out.println("<center><p style='color:darkblue;'><br>Incorrect Details...</center>");
         RequestDispatcher rd=req.getRequestDispatcher("admincreateaccount.html");
         rd.include(req,res);
       }
      }
    }
    catch(Exception e)
    {
      out.println(e);
    }
   }
}