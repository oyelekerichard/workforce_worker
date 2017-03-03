
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

//~--- non-JDK imports --------------------------------------------------------
import net.crowninteractive.wfmworker.entity.InventoryRequest;
import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

//~--- JDK imports ------------------------------------------------------------
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.crowninteractive.wfmworker.dao.InventoryRejectionDao;
import net.crowninteractive.wfmworker.entity.InventoryRejection;
import net.crowninteractive.wfmworker.misc.EmailText;

/**
 *
 * @author USER
 */
public class SendRejectEmails extends TimerTask {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private InventoryRejectionDao inventoryRejectionDao;

    @Override
    public void run() {
        try {
            List<InventoryRejection> inventoryRejections = inventoryRejectionDao.findByEmailSendTimeNULL();

            for (InventoryRejection inventoryRejection : inventoryRejections) {
                InventoryRequest request = inventoryRejection.getRequestId().getInventoryReqId();
                WorkOrder workOrderId = inventoryRejection.getRequestId().getWorkOrderId();
                String[] recipients = inventoryRejection.getRecipients().split(",");
                String name = inventoryRejection.getCreatedBy().getFirstname() + " " + inventoryRejection.getCreatedBy().getLastname();
                String template;

                template = EmailText.getRejectionContent();

                for (String recipient : recipients) {
                    String content = String.format(template, name, workOrderId.getSummary(), workOrderId.getDescription(), request.getItemName(), request.getLocationName(), request.getQuantity()).replaceFirst("(%%)","%");
                    emailSender.sendNoReplyEmail(
                            "INVENTORY REJECTION FOR WORK ORDER" + workOrderId.getId().toString(), recipient,
                            content);
                }
                inventoryRejection.setEmailSendTime(new Date());
                inventoryRejection.setUpdateTime(new Date());
                inventoryRejectionDao.edit(inventoryRejection);
            }
        } catch (IOException ex) {
            Logger.getLogger(SendRejectEmails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
