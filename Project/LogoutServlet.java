import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*; 
import java.sql.*; 
public class LogoutServlet extends HttpServlet 
{  
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {  
            response.setContentType("text/html");  
            PrintWriter out=response.getWriter();  
             try
             {
             Class.forName("oracle.jdbc.driver.OracleDriver");
             Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sbidatabase","sbidatabase");
             Statement s = con.createStatement();
             s.executeUpdate("delete from activeuser");
             }
             catch(Exception e)
             {
               out.println(e);
             }
            request.getRequestDispatcher("home.html").forward(request, response);  
              
            HttpSession session=request.getSession();  
            session.invalidate();  
              
            out.print("You are successfully logged out!");  
              
            out.close();  
    }  
}  