import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AccountsListServlet extends HttpServlet
{
  public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    
    try
     {
       int flag=0;
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
       Statement s = con.createStatement();
       ResultSet rs=s.executeQuery("select accountnumber,name,contact from accountdetails");
       RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
       rd.include(req,res);
       out.println("<html><head><style>table,th,td{border:1px solid black; background-color: #eee; color:black; fontsize:medium;} h3{color:red; font-size: 1.7em}</style></head><body>");
       out.println("<Center><br><h3>Accounts List</h3><br>");
       out.println("<table><tr><th>Account Number</th><th>Account Holder Name</th><th>Contact Number</th></tr>");
      
       while(rs.next())
       {    flag=1;
                 String ac=rs.getString(1);
                 String name=rs.getString(2);
                 String number=rs.getString(3);
                 
           out.println("<tr><td>"+ac+"</td><td>"+name+"</td><td>"+number+"</td></tr>"); 
        }
        out.println("</table></center></body></html>");
        if(flag==0)
        {
            out.println("<center><h3>Incorrect Details...</h3></center>");
            RequestDispatcher rd1=req.getRequestDispatcher("adminhome.html");
            rd1.include(req,res);
        }
     }
     catch(Exception e)
     {
          out.println(e);
     }
   }
}
            