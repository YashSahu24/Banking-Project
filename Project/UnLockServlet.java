import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UnLockServlet extends HttpServlet
{
  public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String type=req.getParameter("type");
    String acnumber=req.getParameter("acnumber");
    String name=req.getParameter("name");
    String number1=req.getParameter("con");
    String number="";
    
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","sbidatabase","sbidatabase");
      Statement s = con.createStatement();
      if(type.equals("UnLock Account"))
      {
        ResultSet rs=s.executeQuery("select contact from accountdetails where accountnumber='"+acnumber+"' AND name='"+name+"' AND contact='"+number1+"'");
        if(rs.next())
        {
          number=rs.getString(1);
          int i=s.executeUpdate("update employee set log=0 where contact='"+number+"'");
          if(i>0)
          {
            RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
            rd.include(req,res);
            out.println("<br><center><p style='color:red;'>Account Number = "+acnumber+"</p></center>");
            out.println("<br><center><p style='color:red;'>A/c Holder Name ="+name+"</p></center>");
            out.println("<br><center><p style='color:red;'>Account UnLocked</p></center>");
          }
        }
        else
        {
            RequestDispatcher rd=req.getRequestDispatcher("unlock.html");
            rd.include(req,res);
            out.println("<br><br><center><p style='color:red;'>Incorrect Details</p></center>");
        }
      }
      if(type.equals("Verify Account"))
      {
         ResultSet rs=s.executeQuery("select contact from accountdetails where accountnumber='"+acnumber+"' AND name='"+name+"' AND contact='"+number1+"'");
        if(rs.next())
        {
          number=rs.getString(1);
          int i=s.executeUpdate("update accountdetails set accountaccess='true' where accountnumber='"+acnumber+"'");
          if(i>0)
          {
            RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
            rd.include(req,res);
            out.println("<br><center><p style='color:red;'>Account Number = "+acnumber+"</p></center>");
            out.println("<br><center><p style='color:red;'>A/c Holder Name ="+name+"</p></center>");
            out.println("<br><center><p style='color:red;'>Account verified.<br> Access Granted for All Transaction Modules.</p></center>");
          }
        }
        else
        {
            RequestDispatcher rd=req.getRequestDispatcher("unlock.html");
            rd.include(req,res);
            out.println("<br><br><center><p style='color:red;'>Incorrect Details</p></center>");
        }
      }  
    }
    catch(Exception e)
    {
      out.println(e);
    }
  }
}
