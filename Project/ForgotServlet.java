import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.swing.JOptionPane;
import javax.servlet.http.*;
public class ForgotServlet extends HttpServlet
{
  public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    String type=req.getParameter("type");
    String name=req.getParameter("username");
    String newpassword=req.getParameter("password");
    String number=req.getParameter("number");
    
    try
     {
       int flag=1;
       Class.forName("oracle.jdbc.driver.OracleDriver");
       Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
       Statement s = con.createStatement();
       if(type.equals("User"))
       { 
         flag=0;
         ResultSet rs=s.executeQuery("select username,contact,log from employee");
         while(rs.next())
         {
           if((name.equals(rs.getString(1)))&&(number.equals(rs.getString(2))))
           {
             if(rs.getInt(3)<5)
             {
               int i=s.executeUpdate("update employee set password='"+newpassword+"'");
               if(i>0)
               { 
                 flag=1;
                 out.println("<center><p style='color:darkblue;'><br>Password Changed Successfully....</p></center>");
                 RequestDispatcher rd=req.getRequestDispatcher("login.html");
                 rd.include(req,res);
                 break;
               }
             }
             else
             {
                flag=1;
                out.println("<center><p style='color:red;'><br>Your Account Temporarily Blocked.<br> Please Conatct to Admin.</p></center>");
                RequestDispatcher rd=req.getRequestDispatcher("home.html");
                rd.include(req,res);
             }
           }
          }
          if(flag==0)
          {
            JOptionPane.showMessageDialog(null,"<html><h1 style='font-family: Calibri; font-size: 36pt;' color='darkblue'>Incorrect Details</h1></html>");
            out.println("<center><p style='color:darkblue;'><br>Incorrect Details....</p></center>");
            RequestDispatcher rd=req.getRequestDispatcher("forgot.html");
            rd.include(req,res);
          }
        }
 
       if(type.equals("Admin"))
       { 
         flag=0;
         ResultSet rs=s.executeQuery("select username,contact from admin");
         while(rs.next())
         {
           if((name.equals(rs.getString(1)))&&(number.equals(rs.getString(2))))
           {
             int i=s.executeUpdate("update admin set password='"+newpassword+"'");
             if(i>0)
              { 
                flag=1;
                out.println("<center><p style='color:darkblue;'><br>Password Changed Successfully....</p></center>");
                RequestDispatcher rd=req.getRequestDispatcher("login.html");
                rd.include(req,res);
                break;
              }
            }
          }
          if(flag==0)
          {
            JOptionPane.showMessageDialog(null,"<html><h1 style='font-family: Calibri; font-size: 36pt;' color='darkblue'>Incorrect Details</h1></html>");
            out.println("<center><p style='color:darkblue;'><br>Incorrect Details....</p></center>");
            RequestDispatcher rd=req.getRequestDispatcher("forgot.html");
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
            