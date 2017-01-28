/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.util.List;
import net.crowninteractive.wfmworker.entity.EscalationWorkOrder;
import org.springframework.stereotype.Component;

/**
 *
 * @author osita
 */
@Component
public class EscalationWorkOrderDao extends AbstractDao<Integer, EscalationWorkOrder>{
    
    public List<EscalationWorkOrder> getUnprocessedEscalations(){
        String query = String.format("select * from escalation_work_order where now() > time_to_escalate and isnull(time_escalated) and isnull(status_updated_time) limit 1");
        return getEntityManager().createNativeQuery(query, EscalationWorkOrder.class).getResultList();
    }
    
    public void create(EscalationWorkOrder ew) {
        persist(ew);
    }
}
