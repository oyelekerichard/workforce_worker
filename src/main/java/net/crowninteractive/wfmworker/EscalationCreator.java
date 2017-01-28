/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import net.crowninteractive.wfmworker.dao.EscalationDistrictRoleDao;
import net.crowninteractive.wfmworker.dao.EscalationSettingsDao;
import net.crowninteractive.wfmworker.dao.EscalationTempDao;
import net.crowninteractive.wfmworker.dao.EscalationWorkOrderDao;
import net.crowninteractive.wfmworker.dao.UsersDao;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.EscalationDistrictRole;
import net.crowninteractive.wfmworker.entity.EscalationSettings;
import net.crowninteractive.wfmworker.entity.EscalationWorkOrder;
import net.crowninteractive.wfmworker.entity.Users;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author osita
 */
@Component
public class EscalationCreator extends TimerTask{
    
    @Autowired
    private EscalationSettingsDao escalationSettingsDao;
    
    @Autowired
    private EscalationWorkOrderDao escalationWorkOrderDao;
    
    @Autowired
    private UsersDao usersDao;
    
    @Autowired
    private WorkOrderDao workOrderDao;
    
    @Autowired
    private EscalationDistrictRoleDao escalationDistrictRoleDao;
    
    @Autowired
    private EscalationTempDao escalationTempDao;
    
    

    @Override
    public void run() {
        try {
            System.out.println("escalation creator ...");
            List<WorkOrder> lewo = workOrderDao.findTempWorkOrders();
            
            for (WorkOrder i : lewo) {
                EscalationSettings es2 = escalationSettingsDao.getEscalationSettingsForNewWorkOrder(i.getQueueTypeId().getId());
                
                if(es2 != null){
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(i.getCreateTime());
                    if(es2.getTimeValueType().equals("hrs")){
                        cal.add(Calendar.HOUR_OF_DAY, es2.getTimeValue());
                    }else if(es2.getTimeValueType().equals("min")){
                        cal.add(Calendar.MINUTE, es2.getTimeValue());
                    }
                    String recs = emailRecipients(i, es2);
                    if(recs != null){
                        EscalationWorkOrder ew = new EscalationWorkOrder(i.getOwnerId(), es2.getStatusId(), cal.getTime(), recs, es2, i);
                        escalationWorkOrderDao.create(ew);
                        if(ew.getId() > 0){
                            if(es2.getStatusId() == null){
                                int res2 = escalationTempDao.removeTemp(i.getId());
                                if(res2 > 0){
                                    System.out.println("cleared... "+i.getId());
                                }
                            }
                        }          
                    }else{
                        System.out.println("skipping - no recipients to escalate to for work order #"+i.getTicketId());
                    }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    private String emailRecipients(WorkOrder i, EscalationSettings lescs){
        List<String> emailStore = new ArrayList<>();
        List<EscalationDistrictRole> edrs = escalationDistrictRoleDao.fetchDistrictRoleEmails(i.getBusinessUnit(), lescs.getRoles());
        if(edrs != null){
            for(EscalationDistrictRole ed : edrs){
                emailStore.add(ed.getEmails());
            }
            if(lescs.getInformAssigner()  && i.getAssignedBy() != null){
                Users assignedBy = usersDao.findByOwnerAndId(i.getOwnerId(), i.getAssignedBy().getId());
                if(assignedBy != null){
                    emailStore.add(assignedBy.getEmail());
                }
            }

            return StringUtils.join(emailStore,",");
        }else{
            return null;
        }
    }
}
