
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

//~--- non-JDK imports --------------------------------------------------------
import net.crowninteractive.wfmworker.dao.InventoryApprovalDao;
import net.crowninteractive.wfmworker.entity.InventoryApproval;
import net.crowninteractive.wfmworker.entity.InventoryRequest;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.crowninteractive.wfmworker.misc.Config;
import net.crowninteractive.wfmworker.misc.EmailText;

/**
 *
 * @author USER
 */
@Component
public class SendApprovalEmails extends TimerTask {

    @Autowired
    private InventoryApprovalDao inventoryApprovalDao;
    @Autowired
    private EmailSender emailSender;

    @Override
    public void run() {
        try {
            List<InventoryApproval> inventoryApprovals = inventoryApprovalDao.findByEmailSendTimeNULL();

            for (InventoryApproval inventoryApproval : inventoryApprovals) {
                InventoryRequest request = inventoryApproval.getInventoryReqId();
                WorkOrder workOrderId = inventoryApproval.getWorkOrderId();
                String[] recipients = inventoryApproval.getRecipients().split(",");
                String template;
                template = EmailText.getApprovalContent();
               

                for (String recipient : recipients) {
                    String content = String.format(template,workOrderId.getSummary(), workOrderId.getDescription(), request.getItemName(), request.getLocationName(), request.getQuantity().toString()).replaceFirst("(%%)","%");
                   
                    emailSender.sendNoReplyEmail(
                            "INVENTORY APPROVAL FOR WORK ORDER #" + workOrderId.getId().toString(), recipient,
                            content);
                }
                inventoryApproval.setEmailSendTime(new Date());
                inventoryApproval.setUpdateTime(new Date());
                inventoryApprovalDao.edit(inventoryApproval);
            }
        } catch (IOException ex) {
            Logger.getLogger(SendApprovalEmails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
