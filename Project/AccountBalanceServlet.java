import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AccountBalanceServlet extends HttpServlet
{
  public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String accountnum=req.getParameter("acnumber");
    String name1=req.getParameter("name");
    
    try
     {
       int flag=0;
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
       Statement s = con.createStatement();
       ResultSet rs=s.executeQuery("select accountnumber,name,amount,accounttype from accountdetails where accountnumber='"+accountnum+"' AND name='"+name1+"'");
       while(rs.next())
       {    flag=1;
                 String ac=rs.getString(1);
                 String name=rs.getString(2);
                 String amount=rs.getString(3);
                 String atype=rs.getString(4);
                 RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
                 rd.include(req,res);
                 out.println("<html><head><style>h3{color:red; font-size: 1.7em}</style></head><body><div class='box2'");
                 out.println("<center><h3><br>Account Number : "+ac); 
                 out.println("<br><br>Account Holder Name : "+name);
                 out.println("<br><br>Account Type : "+atype);
                 out.println("<br><br>Account Balance : "+amount+"</h3></center></div></body></html>");
                 break;
          }
        if(flag==0)
        {
            RequestDispatcher rd=req.getRequestDispatcher("accountbalance.html");
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
            