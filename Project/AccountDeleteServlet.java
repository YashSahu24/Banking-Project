import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.swing.JOptionPane;
import javax.servlet.http.*;
public class AccountDeleteServlet extends HttpServlet
{
  public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String acnumber=req.getParameter("acnumber");
    String name=req.getParameter("name");
    String cnumber=req.getParameter("cnumber");
    try
     {
       int flag=0;
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
       Statement s = con.createStatement();  
       int i=s.executeUpdate("delete from accountdetails where (accountnumber='"+acnumber+"' AND contact='"+cnumber+"') AND name='"+name+"'");
       if(i>0)
         {
           s.executeUpdate("delete from transaction where accountnumber='"+acnumber+"'");
             s.executeUpdate("delete from employee where contact='"+cnumber+"'");   
             RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
             rd.include(req,res);
                 out.println("<html><head><style>h3{color:red; font-size: 1.7em}</style></head><body>");
                 out.println("<center><div><h3><br>Your Account Deleted Successfully...."); 
                 out.println("<br><br>Account Number : "+acnumber);
                 out.println("<br><br>A/c Name : "+name);
                 out.println("<br><br>Contact Number : "+cnumber+"</h3></center></body></html>");
        }
        else
        {
            RequestDispatcher rd=req.getRequestDispatcher("accountdelete.html");
            rd.include(req,res);
            out.println("<center><style>h3{color:red; font-size: 1.0em}</style><h3>Incorrect Details....</h3></center>");
        }
      }
     catch(Exception e)
     {
          out.println(e);
     }
   }
}
            