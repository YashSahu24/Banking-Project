import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.swing.JOptionPane;
import javax.servlet.http.*;
public class CheckBalanceServlet extends HttpServlet
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
       if(rs1.next())
         accountnum=rs1.getString(1);  
      
         ResultSet rs=s.executeQuery("select accountnumber,name,amount,accounttype from accountdetails where accountnumber='"+accountnum+"'");
         while(rs.next())
         {    flag=1;
                 String ac=rs.getString(1);
                        name=rs.getString(2);
                 String amount=rs.getString(3);
                 String atype=rs.getString(4);
                 out.println("<p style='color:red;'> Welcome "+name+"<br>");
                 out.println("Account No : "+accountnum+"</p>");
                 RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
                 rd.include(req,res);
                 out.println("<html><head><style>h3{color:red; font-size: 1.3em}</style></head><body><div class='box2'");
                 out.println("<center><h3><br>Account Number : "+ac); 
                 out.println("<br><br>Account Holder Name : "+name);
                 out.println("<br><br>Account Type : "+atype);
                 out.println("<br><br>Account Balance : "+amount+"</h3></center></div></body></html>");
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
            