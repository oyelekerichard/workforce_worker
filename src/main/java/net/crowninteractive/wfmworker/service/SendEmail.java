/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.crowninteractive.wfmworker.misc.Config;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

/**
 *
 * @author johnson3yo
 */
@Service
public class SendEmail {

    private final Config CONFIG = Config.getInstance();

    public void sendAnEmail(String emailkey, String subject, String to, String msg, String filename, String bcc) throws EmailException {

        if (emailkey.equals("a12wq_minions")) {
            HtmlEmail emailAgent = new HtmlEmail();

            emailAgent.setHostName(CONFIG.getEmailHostName());
            emailAgent.setSmtpPort(Integer.parseInt(CONFIG.getEmailPort()));
            emailAgent.setAuthentication(CONFIG.getEmailAddress(), CONFIG.getEmailPassword());
            emailAgent.setTLS(true);
            //emailAgent.setDebug(true);
            emailAgent.setFrom(CONFIG.getEmailAddress());
            Map<String, String> headers = new HashMap<>();
            headers.put("Sender", CONFIG.getEmailSender());
            emailAgent.setHeaders(headers);
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
