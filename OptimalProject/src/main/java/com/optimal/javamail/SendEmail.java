/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optimal.javamail;

import javax.ejb.Stateless;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.Stateless;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 *
 *
 * @author AHMED 50070
 */
@Stateless
public class SendEmail {

    public static void sendingGmail(String from, String username, String passord, String to, String message) {

        try {
            // Get system properties
            Properties prop = System.getProperties();

            // Setup mail server
            //"smtp.gmail.com"
            prop.put("mail.smtp.host", "smtp.gmail.com"); // send to gmail accounts

            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.fallback", "false");

            // Get the default Session object.
            Session mailSession = Session.getDefaultInstance(prop, null);

            mailSession.setDebug(true);

            // Create a default MimeMessage object.
            Message mailMsg = new MimeMessage(mailSession);

            // Set From: header field of the header.
            mailMsg.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            mailMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            mailMsg.setContent(message,"text/html");
            // Set Subject: header field
            mailMsg.setSubject("This is the Subject Line!");

          
            // Now set the actual message
            mailMsg.setText(message);

//            
            Transport trans = mailSession.getTransport("smtp");
            trans.connect("smtp.gmail.com", username, passord);
            trans.sendMessage(mailMsg, mailMsg.getAllRecipients());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

   

}
