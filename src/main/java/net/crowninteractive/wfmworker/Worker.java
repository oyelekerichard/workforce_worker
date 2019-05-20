
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

//~--- non-JDK imports --------------------------------------------------------
import net.crowninteractive.wfmworker.dao.EscalationDistrictRoleDao;
import net.crowninteractive.wfmworker.dao.EscalationSettingsDao;
import net.crowninteractive.wfmworker.dao.EscalationTempDao;
import net.crowninteractive.wfmworker.dao.EscalationWorkOrderDao;
import net.crowninteractive.wfmworker.dao.UsersDao;
import net.crowninteractive.wfmworker.entity.EscalationDistrictRole;
import net.crowninteractive.wfmworker.entity.EscalationSettings;
import net.crowninteractive.wfmworker.entity.EscalationWorkOrder;
import net.crowninteractive.wfmworker.entity.Users;
import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------
import java.io.File;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.jws.WebParam;

/**
 *
 * @author osita
 */
@Component
public class Worker extends TimerTask {

    @Autowired
    private EscalationSettingsDao escalationSettingsDao;
    @Autowired
    private EscalationWorkOrderDao escalationWorkOrderDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private EscalationDistrictRoleDao escalationDistrictRoleDao;
    @Autowired
    private EscalationTempDao escalationTempDao;

    @Override
    public void run() {
        try {
            System.out.println("escalation processor ...");

            List<EscalationWorkOrder> lewo = escalationWorkOrderDao.getUnprocessedEscalations();

            for (EscalationWorkOrder ewo : lewo) {
                String emailtitle = String.format("WORK ORDER ESCALATION!!! (#00%s) - %s",
                        ewo.getWorkOrderId().getTicketId(),
                        ewo.getEscalationSettingId().getLabel());
                String recipients = ewo.getEscalatedEmails();
                String body
                        = format(FileUtils.readFileToString(new File("/var/files/wfm/request.htm")),
                                String.valueOf(ewo.getWorkOrderId().getTicketId()), ewo.getWorkOrderId().getSummary(),
                                ewo.getWorkOrderId().getQueueId().getName(),
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ewo.getWorkOrderId().getCreateTime()),
                                ewo.getWorkOrderId().getCurrentStatus(), ewo.getWorkOrderId().getDescription());

                sendNoReplyEmail("a12wq_minions", emailtitle, recipients, body);
                System.out.println("Escalation email sent for work order #" + ewo.getWorkOrderId().getTicketId()
                        + "  - PRIORITY: " + ewo.getEscalationSettingId().getPriority());
                ewo.setTimeEscalated(new Date());
                escalationWorkOrderDao.edit(ewo);

                // raise  escalation level
                EscalationSettings es2
                        = escalationSettingsDao.getNextEscalationLevel(ewo.getEscalationSettingId().getStatusId(),
                                ewo.getEscalationSettingId().getQueueTypeId().getId(),
                                ewo.getEscalationSettingId().getPriority() + 1);

                if (es2 != null) {
                    WorkOrder i = ewo.getWorkOrderId();
                    Calendar cal = Calendar.getInstance();

                    cal.setTime(i.getCreateTime());

                    if (es2.getTimeValueType().equals("hrs")) {
                        cal.add(Calendar.HOUR_OF_DAY, es2.getTimeValue());
                    } else if (es2.getTimeValueType().equals("min")) {
                        cal.add(Calendar.MINUTE, es2.getTimeValue());
                    }

                    EscalationWorkOrder ew = new EscalationWorkOrder(i.getOwnerId(), es2.getStatusId(), cal.getTime(),
                            emailRecipients(ewo.getWorkOrderId(), es2), es2, i);

                    escalationWorkOrderDao.create(ew);

                    if (ew.getId() > 0) {
                        if (es2.getStatusId() == null) {
                            int res2 = escalationTempDao.removeTemp(i.getId());

                            if (res2 > 0) {
                                System.out.println("cleared... " + i.getId());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String format(String filedata, String ticketId, String summary, String queueName, String simpleDateTime,
            String currentStatus, String description) {
        return filedata.replace("$ticketid", ticketId).replace("$summary", summary).replace("$queue",
                queueName).replace("$time", simpleDateTime).replace("$status",
                currentStatus).replace("$desc", description);
    }

    public void sendNoReplyEmail(@WebParam(name = "qw") String emailkey, @WebParam(name = "subject") String subject,
            @WebParam(name = "to") String to, @WebParam(name = "msg") String msg,
            @WebParam(name = "filename") String filename) {
        if (emailkey.equals("a12wq_minions")) {
            HtmlEmail emailAgent = new HtmlEmail();

            emailAgent.setHostName("smtp.office365.com");
            emailAgent.setSmtpPort(587);
            emailAgent.setAuthentication("no-reply@ekedp.com", "CI@ekedp15");
            emailAgent.setTLS(true);

            try {
                emailAgent.setFrom("no-reply@ekedp.com");
                emailAgent.setSubject(subject);
                emailAgent.setCharset("utf8");
                emailAgent.setHtmlMsg(msg);
                emailAgent.addTo(to);
                emailAgent.send();
            } catch (EmailException e) {
                System.out.println("error while wending email: " + e.getMessage());
            }
        } else {
            System.out.println("invalid key while sending email");
        }
    }

    public void sendNoReplyEmail(@WebParam(name = "qw") String emailkey, @WebParam(name = "subject") String subject,
            @WebParam(name = "to") String to, @WebParam(name = "msg") String msg) {
        sendNoReplyEmail(emailkey, subject, to, msg, null);
    }

    private String emailRecipients(WorkOrder i, EscalationSettings lescs) {
        List<String> emailStore = new ArrayList<>();
        List<EscalationDistrictRole> edrs
                = escalationDistrictRoleDao.fetchDistrictRoleEmails(i.getBusinessUnit(), lescs.getRoles());

        for (EscalationDistrictRole ed : edrs) {
            emailStore.add(ed.getEmails());
        }

        if (lescs.getInformAssigner() && (i.getAssignedBy() != null)) {
            Users assignedBy = usersDao.findByOwnerAndId(i.getOwnerId(), i.getAssignedBy().getId());

            if (assignedBy != null) {
                emailStore.add(assignedBy.getEmail());
            }
        }

        return StringUtils.join(emailStore, ",");
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
