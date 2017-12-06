/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

/**
 *
 * @author johnson3yo
 */
@Service
public class SendEmail {
    
    
    public void sendAnEmail(String emailkey, String subject, String to,String msg,String filename,String bcc) throws EmailException{
        
         int retn = 0;
        String desc = "ok";
        List obj = new ArrayList<>();

        if (emailkey.equals("a12wq_minions")) {
            HtmlEmail emailAgent = new HtmlEmail();

            emailAgent.setHostName("smtp.office365.com");
            emailAgent.setSmtpPort(587);
            emailAgent.setAuthentication("no-reply@ekedp.com", "CI@ekedp15");
            emailAgent.setTLS(true);
            //emailAgent.setDebug(true);
                emailAgent.setFrom("no-reply@ekedp.com");
                emailAgent.setSubject(subject);
                emailAgent.setCharset("utf8");
                emailAgent.setMsg(msg);
                emailAgent.addTo(to);
                if (bcc != null) {
                    emailAgent.addBcc(bcc);
                }
                if (filename != null) {
                    String filePath = "/var/files/email/" + filename;
                    File attachment = new File(filePath);
                    if (attachment.exists()) {
                        emailAgent.attach(attachment);
                    }
                }

                emailAgent.send();
        
        
    }
    
}
    
}
