import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.swing.JOptionPane;
import javax.servlet.http.*;
public class StatementServlet extends HttpServlet
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
       ResultSet rs1=s.executeQuery("select accountnumber,name from activeuser");
       if(rs1.next())
       {
         accountnum=rs1.getString(1);
         name=rs1.getString(2);
       }  
                 out.println("<p style='color:red;'> Welcome "+name+"<br>");
                 out.println("Account No : "+accountnum+"</p>");
                 RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
                 rd.include(req,res);
                 
                 out.println("<div class='bolc'>");
                 out.println("<form action='statement2' method='Get'>");
                 out.println("<center><h2>Account Statement<h2>");
                 out.println("<h4>A/c No.- "+accountnum); 
                 out.println("<br><br>");
                 out.println("<h4><input type='radio' name='type' value='all' class='con' checked>All Statements</h4>");
                 out.println("<br>");
                 out.println("<h4><input type='radio' name='type' value='date' class='con'>Date</h4>");
                 out.println("<input type='date' name='from' class='con'> <br>");
                 out.println("<input type='date' name='to' class='con'> <br><br>");
                 out.println("<input type='submit' value='Submit' class='button'>");
                 out.println("</center></form></div>");

     }
     catch(Exception e)
     {
          out.println(e);
     }
   }
}
            