import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Mail 
{ 
  public static void otpmail(String email,String otp1,long amount)
   {
      String to=email;
      String otp=otp1;
      long money=amount;
      
      final String username="vijay.java.2019@gmail.com";
      final String password="vijay12java";
      Properties properties= new Properties();
      properties.put("mail.smtp.host","smtp.gmail.com");
      properties.put("mail.smtp.socketFactory.port", "465");    
      properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
      properties.put("mail.smtp.auth","true");
      properties.put("mail.smtp.port","587");
      properties.put("mail.smtp.starttls.enable","true");
       
      Session session = Session.getInstance(properties,new javax.mail.Authenticator(){ 
                                                        protected PasswordAuthentication getPasswordAuthentication(){ 
                                                                        return new PasswordAuthentication(username,password);}});
      try
       {
         //create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);
         
         // set Form: header field of the header.
         message.setFrom(new InternetAddress("mail.vijay.java.2019@gmail.com"));
   
         // set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // set Subject: header field 
         message.setSubject("SBI OTP");

         // Now set the actual message 
         message.setText("Dear Customer ,"+otp+" is your One Time Password for Transfer Amount = "+money);
   
         // send message
         Transport.send(message);
       }
       catch(MessagingException mex)
       {
         mex.printStackTrace();
       }
       
    }
 
    public static void sendmail(String email,String mess)
    {
      String to=email;
      String mesg=mess;
      
      final String username="vijay.java.2019@gmail.com";
      final String password="vijay12java";
      Properties properties= new Properties();
      properties.put("mail.smtp.host","smtp.gmail.com");
      properties.put("mail.smtp.socketFactory.port", "465");    
      properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
      properties.put("mail.smtp.auth","true");
      properties.put("mail.smtp.port","587");
      properties.put("mail.smtp.starttls.enable","true");
       
      Session session = Session.getInstance(properties,new javax.mail.Authenticator(){ 
                                                        protected PasswordAuthentication getPasswordAuthentication(){ 
                                                                        return new PasswordAuthentication(username,password);}});
      try
       {
         //create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);
         
         // set Form: header field of the header.
         message.setFrom(new InternetAddress("mail.vijay.java.2019@gmail.com"));
   
         // set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // set Subject: header field 
         message.setSubject("SBI");

         // Now set the actual message 
         message.setText(mesg);
   
         // send message
         Transport.send(message);
       }
       catch(MessagingException mex)
       {
         mex.printStackTrace();
       }
    }
}