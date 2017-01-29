package net.crowninteractive.wfmworker.dao;

import java.util.List;
import net.crowninteractive.wfmworker.entity.InventoryApproval;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import org.springframework.stereotype.Component;

/**
 *
 * @author osita
 */
@Component
public class InventoryApprovalDao extends AbstractDao<Integer, InventoryApproval>{
    
   public List<InventoryApproval> findByEmailSendTimeNULL()
   {
  String query="SELECT * from inventory_approval where email_send_time is ";
  return getEntityManager().createNativeQuery(query, InventoryApproval.class).getResultList();
   }
    
    public List<WorkOrder> findTempWorkOrders(){
        String query = String.format("select * from work_order where id in (select work_order_id from escalation_temp where `type`='CREATE'  )");
        return getEntityManager().createNativeQuery(query, WorkOrder.class).getResultList();
    }
}
