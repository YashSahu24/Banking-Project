import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AccountStatementServlet extends HttpServlet
{
  public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String accountnum="";
    String name="";
    String type=req.getParameter("type");
    String fromdate=req.getParameter("from");
    String todate=req.getParameter("to");
    int count=1;
    try
     {
       
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
       Statement s = con.createStatement();
       ResultSet rs1=s.executeQuery("select accountnumber,name from activeuser");
       if(rs1.next())
       {
         accountnum=rs1.getString(1);
         name=rs1.getString(2);
       }  
       if(type.equals("all"))
       {
         ResultSet rs=s.executeQuery("select * from transaction where accountnumber='"+accountnum+"'");
         out.println("<p style='color:red;'> Welcome "+name+"<br>");
         out.println("Account No : "+accountnum+"</p>");
         RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
         rd.include(req,res);
         out.println("<html><head><style>table,th,td{border:1px solid black; background-color: #eee; color:black; fontsize:medium;} h3{color:red; font-size: 1.7em}</style></head><body>");
         out.println("<Center><br><h3>Account Statement<br>");
         out.println("Account Number : "+accountnum+"</h3><br>");
         out.println("<table><tr><th>Sr.No</th><th>Transaction Id</th><th>Credited Amount</th><th>Debited Amount</th><th>Credited From</th><th>Debited From</th><th>Transfer To</th><th>Date</th></tr>");
      
         while(rs.next())
         { 
                 
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
       }
       if(type.equals("date"))
       {
         ResultSet rs=s.executeQuery("select * from transaction where accountnumber='"+accountnum+"' AND transactiondate between '"+fromdate+"' and '"+todate+"'");
         out.println("<p style='color:red;'> Welcome "+name+"<br>");
         out.println("Account No : "+accountnum+"</p>");
         RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
         rd.include(req,res);
         out.println("<html><head><style>table,th,td{border:1px solid black; background-color: #eee; color:black; fontsize:medium;} h3{color:red; font-size: 1.7em}</style></head><body>");
         out.println("<Center><br><h3>Account Statement<br>");
         out.println("Account Number : "+accountnum+"</h3><br>");
         out.println("<table><tr><th>Sr.No.</th><th>Transaction Id</th><th>Credited Amount</th><th>Debited Amount</th><th>Credited From</th><th>Debited From</th><th>Transfer To</th><th>Date</th></tr>");
      
         while(rs.next())
         { 
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
       }  
     }
     catch(Exception e)
     {
          out.println(e);
     }
   }
}
            