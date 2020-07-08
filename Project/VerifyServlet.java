import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class VerifyServlet extends HttpServlet
{
  public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    HttpSession session1=req.getSession();
    
   String otp=(String)session1.getAttribute("otp");
   String accountno=(String)session1.getAttribute("accountnum");

   
   out.println("<html><body bgcolor='skyblue'>");
   out.println("<center><h1>Verify OTP</h1>");
   out.println("<form action='transfer3' method='Get'>");
   out.println("Enter OTP:<input type='number' name='otp'>");
   out.println("<br><br><input type='submit' value='Verify'></center>");
   out.println("</form></body></html>");
  }
}