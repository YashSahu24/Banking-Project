import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AdminAccountStatementServlet extends HttpServlet
{
  public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String acnumber=req.getParameter("acnumber");
    int count=1;
    try
     {
       int flag=0;
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
       Statement s = con.createStatement();
       ResultSet rs=s.executeQuery("select * from transaction where accountnumber='"+acnumber+"'");
       RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
       rd.include(req,res);
       out.println("<html><head><style>table,th,td{border:1px solid black; background-color: #eee; color:black; fontsize:medium;} h3{color:red; font-size: 1.7em}</style></head><body>");
       out.println("<Center><br><h3>Account Statement<br>");
       out.println("Account Number : "+acnumber+"</h3><br>");
       out.println("<table><tr><th>Sr.No</th><th>Transaction Id</th><th>Credited Amount</th><th>Debited Amount</th><th>Credited From</th><th>Debited From</th><th>Transfer To</th><th>Date</th></tr>");
      
       while(rs.next())
       {    flag=1;
                 String tid=rs.getString(2);
                 String camu=rs.getString(3);
                 String damu=rs.getString(4);
                 String cfrom=rs.getString(5);
                 String dfrom=rs.getString(6);
                 String tsto=rs.getString(7);
                 String date=rs.getString(8);
                 
           out.println("<tr><td>"+count+"</td><td>"+tid+"</td><td>"+camu+"</td><td>"+damu+"</td><td>"+cfrom+"</td><td>"+dfrom+"</td><td>"+tsto+"</td><td>"+date+"</td></tr>"); 
           count++;
        }
        out.println("</table></center></body></html>");
        if(flag==0)
        {
            
            RequestDispatcher rd1=req.getRequestDispatcher("adminhome.html");
            rd1.forward(req,res);
            out.println("<center><h3>Incorrect AccountNumber...</h3></center>");
        }
     }
     catch(Exception e)
     {
          out.println(e);
     }
   }
}
            