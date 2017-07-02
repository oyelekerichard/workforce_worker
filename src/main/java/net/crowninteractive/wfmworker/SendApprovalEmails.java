
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
import net.crowninteractive.wfmworker.misc.EmailText;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.crowninteractive.wfmworker.misc.Config;

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
    System.out.println("SendApprovalEmails starting...");
        try {
            List<InventoryApproval> inventoryApprovals = inventoryApprovalDao.findByEmailSendTimeNULL();
            System.out.println("SendApprovalEmails running...");
            
            if(inventoryApprovals != null && inventoryApprovals.size() > 0){
                Map<Integer, List<InventoryApproval>> requests = getRequests(inventoryApprovals);
                System.out.println("size of requests is: "+requests.size());
                for (Integer workorderId  : requests.keySet()) {
                    InventoryApproval inventoryApproval = requests.get(workorderId).get(0);
                    
                    WorkOrder workOrder = inventoryApproval.getWorkOrderId();
                    String[] recipients = inventoryApproval.getRecipients().split(",");
                    String template;
                    template = EmailText.getApprovalContent();

                    System.out.println("SendApprovalEmails processing #"+ workOrder.getTicketId());

                    String url = Config.getInstance().wfmFrontendUrl();
                    String items = getItems(requests.get(workorderId));
                    for (String recipient : recipients) {
                        String content = String.format(template,workOrder.getSummary(), workOrder.getDescription(), items,url).replaceFirst("(%%)","%");

                        System.out.println("SendApprovalEmails to " + recipient);
                        emailSender.sendNoReplyEmail(
                                "INVENTORY APPROVAL FOR WORK ORDER #" + workOrder.getTicketId(), recipient,
                                content);
                        System.out.println("SendApprovalEmails sent to " + recipient);
                    }
                    inventoryApproval.setEmailSendTime(new Date());
                    inventoryApproval.setUpdateTime(new Date());
                    inventoryApprovalDao.edit(inventoryApproval);
                    System.out.println("SendApprovalEmails done processing inventory " + inventoryApproval);
                }
            
            
            }
            System.out.println("SendApprovalEmails run ended successfully!");
        } catch (IOException ex) {
            System.out.println("SendApprovalEmails an exception occurred  " + ex);
            Logger.getLogger(SendApprovalEmails.class.getName()).log(Level.ALL, null, ex);
        }
    }

    private Map<Integer, List<InventoryApproval>> getRequests(List<InventoryApproval> inventoryApprovals) {
        Map<Integer, List<InventoryApproval>> m = new LinkedHashMap<>();
        for(InventoryApproval iv : inventoryApprovals){
            WorkOrder w = iv.getWorkOrderId();
            if(w != null){
                if(m.get(w.getId()) == null){
                    List<InventoryApproval> l = new ArrayList();
                    l.add(iv);
                    m.put(w.getId(), l);
                }else{
                    List<InventoryApproval> l = m.get(w.getId());
                    l.add(iv);
                    m.put(w.getId(), l);
                }
            }
        }
        
        return m;
    }

    private String getItems(List<InventoryApproval> get) {
        StringBuilder sb = new StringBuilder();
        
        for(InventoryApproval inventoryApproval : get){
            sb.append("<tr>");
            InventoryRequest request = inventoryApproval.getInventoryReqId();
            sb.append("<td style='padding: 10px;'>").append(request.getItemName()).append("</td>");
            sb.append("<td style='padding: 10px;'>").append(request.getLocationName()).append("</td>");
            sb.append("<td style='padding: 10px;'>").append(request.getQuantity()).append("</td>");
            sb.append("</tr>");
        }
        
        return sb.toString();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
