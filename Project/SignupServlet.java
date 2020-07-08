import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;
public class SignupServlet extends HttpServlet
{
 public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
 {
  res.setContentType("text/html");
  PrintWriter out=res.getWriter();
  String name=req.getParameter("username");
  String pass=req.getParameter("password");
  String email=req.getParameter("email");
  String contact=req.getParameter("contactno");

  try
   {
     Class.forName("oracle.jdbc.driver.OracleDriver");
     Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
     Statement s = con.createStatement();
     ResultSet rs=s.executeQuery("select username,contact from employee");
     int flag=0;
       while(rs.next())
       {
         if((name.equals(rs.getString(1)))||(contact.equals(rs.getString(2))))
         {  
            flag=1;
            JOptionPane.showMessageDialog(null,"<html><h1 style='font-family: Calibri; font-size: 36pt;' color='red'>The username already exists.<br/> Please use a different username.</h1></html>");
            out.println("<center><p style='color:red;' size='6'><br>Username Already Exist. Please Use a Different Username.....<br></p></center>"); 
            RequestDispatcher rd=req.getRequestDispatcher("signup.html");
            rd.include(req,res);
            break;
         }
      }
      if(flag==0)
       {        
           PreparedStatement ps=con.prepareStatement("insert into employee values(?,?,?,?,?)");
           ps.setString(1,name);
           ps.setString(2,pass);
           ps.setString(3,email);
           ps.setString(4,contact);
           ps.setInt(5,0);

           int i=ps.executeUpdate();
           if(i>0)
           {
             JOptionPane.showMessageDialog(null,"<html><h1 style='font-family: Calibri; font-size: 36pt;' color='darkblue'>SignUp Succesfully... </h1></html>");
             out.println("<center><p style='color:darkblue' size='6'><br>SignUp Successfully.....<br></p></center>");
             RequestDispatcher dis=req.getRequestDispatcher("login.html");
             dis.include(req,res);
           }
       }
    }
   catch(Exception ie)
   {
     out.println(ie);
   }
   out.close();
  }
}