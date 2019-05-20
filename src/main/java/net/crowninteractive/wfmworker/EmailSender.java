
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

//~--- non-JDK imports --------------------------------------------------------
import net.crowninteractive.wfmworker.dao.EmailSendDao;
import net.crowninteractive.wfmworker.entity.EmailSend;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import net.crowninteractive.wfmworker.misc.Config;

/**
 *
 * @author osita
 */
@Component
public class EmailSender extends TimerTask {

    @Autowired
    private EmailSendDao emailSendDao;

    private final Config CONFIG = Config.getInstance();

    @Override
    public void run() {
        System.out.println("email processor... ");

        try {
            List<EmailSend> les = emailSendDao.findUnsentEmails();

            for (EmailSend es : les) {
                String[] recipients = es.getRecipients().split(",");
                for (String recipient : recipients) {
                    int res = sendNoReplyEmail(es.getSubject(), recipient, es.getMessage());

                    if (res == 1) {
                        System.out.println("sent email to: " + recipient + " - Id: " + es.getId());

                    }

                }
                es.setSentTime(new Date());
                emailSendDao.update(es);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int sendNoReplyEmail(String subject, String to, String msg) {
        HtmlEmail emailAgent = new HtmlEmail();

        emailAgent.setHostName(CONFIG.getEmailHostName());
        emailAgent.setSmtpPort(Integer.parseInt(CONFIG.getEmailPort()));
        emailAgent.setAuthentication(CONFIG.getEmailAddress(), CONFIG.getEmailPassword());
        emailAgent.setTLS(true);

        try {
            emailAgent.setFrom(CONFIG.getEmailAddress());
            Map<String, String> headers = new HashMap<>();
            headers.put("Sender", CONFIG.getEmailSender());
            emailAgent.setSubject(subject);
            emailAgent.setCharset("utf8");
            emailAgent.setHtmlMsg(msg);
            emailAgent.addTo(to);
            emailAgent.send();

            return 1;
        } catch (EmailException e) {
            System.out.println("error while wending email: " + e.getMessage());

            return 0;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
