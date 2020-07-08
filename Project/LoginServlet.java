import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.swing.JOptionPane;
public class LoginServlet extends HttpServlet
{
 public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
 {
  res.setContentType("text/html");
  PrintWriter out=res.getWriter();
  String type=req.getParameter("type");
  String name=req.getParameter("username");
  String password=req.getParameter("password");
  String acno="";
  int login=0;
   try
   {
     int flag=1;
     Class.forName("oracle.jdbc.driver.OracleDriver");
     Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
     Statement s = con.createStatement();
     s.executeUpdate("delete from activeuser");
     if(type.equals("User"))
     { 
       flag=0;
       ResultSet rs=s.executeQuery("select password,contact,log from employee where username='"+name+"'");
       if(rs.next())
       {
           String password1=rs.getString(1);
           String number=rs.getString(2);
           login=rs.getInt(3);
           if(login<5)
           {
             if(password.equals(password1))
             {  
               flag=2;
               
               s.executeUpdate("update employee set log=0 where username='"+name+"'");
               ResultSet rs1=s.executeQuery("select contact,accountnumber,name from accountdetails");
               while(rs1.next())
               {
                 if(number.equals(rs1.getString(1)))
                 {
                   flag=1;
                   acno=rs1.getString(2);
                   String name1=rs1.getString(3);
                   s.executeUpdate("delete from activeuser");
                   s.executeUpdate("insert into activeuser(accountnumber,name,contact) values('"+acno+"','"+name1+"','"+number+"')");                                    

                   out.println("<p style='color:red;'> Welcome "+name1+"<br>");
                   out.println("Account No : "+acno+"</p>");
                   RequestDispatcher rd=req.getRequestDispatcher("userhome.html");
                   rd.include(req,res);
                   break;
                 }
               }
               if(flag==2)
               {
                 out.println("<center><p style='color:darkblue;'><br>LogIn Successfully....</p></center>");
                 flag=1;
                 RequestDispatcher rd=req.getRequestDispatcher("CreateAccount.html");
                 rd.include(req,res);
                 
               }
             }     
             else
             {
               login+=1;
               s.executeUpdate("update employee set log='"+login+"' where username='"+name+"'");
               JOptionPane.showMessageDialog(null,"<html><h1 style='font-family: Calibri; font-size: 36pt;' color='darkblue'>Incorrect Password</h1></html>");
               out.println("<center><p style='color:darkblue;'><br>Incorrect Password....</p></center>");
               out.println("<center><p style='color:red;'><br>Wront attempt= "+login+" Maxmimum attempt = 5</p></center>");
               flag=1;
               RequestDispatcher rd=req.getRequestDispatcher("login.html");
               rd.include(req,res);
             
             }
           }
           else
           {
               out.println("<center><p style='color:red;'><br>Your Account Temporarily Blocked.<br> Please Conatct to Admin.</p></center>");
               flag=1;
               RequestDispatcher rd=req.getRequestDispatcher("login.html");
               rd.include(req,res);
           }
        }
       else
       {
         JOptionPane.showMessageDialog(null,"<html><h1 style='font-family: Calibri; font-size: 36pt;' color='darkblue'>Incorrect Username. Username Not Registered....</h1></html>");
         out.println("<center><p style='color:red;'><br>Incorrect Username....</p></center>");
         RequestDispatcher rd=req.getRequestDispatcher("login.html");
         rd.include(req,res);
       } 
     }
     if(type.equals("Admin"))
     {
       flag=0;
       ResultSet rs=s.executeQuery("select username,password from admin");
       while(rs.next())
       {
         if((name.equals(rs.getString(1)))&&(password.equals(rs.getString(2))))
         {
             out.println("<center><p style='color:darkblue;'><br>Admin Login Succesfully...</p></center>");
             flag=1;
             RequestDispatcher rd=req.getRequestDispatcher("adminhome.html");
             rd.forward(req,res);
             break;
          }
        }
        if(flag==0)
        {
          JOptionPane.showMessageDialog(null,"<html><h1 style='font-family: Calibri; font-size: 36pt;' color='darkblue'>Incorrect Admin Username and Password.</h1></html>");
          out.println("<center><p style='color:red;'><br>Incorrect Admin Username and Password.</p></center>");
          RequestDispatcher rd=req.getRequestDispatcher("login.html");
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

   
  