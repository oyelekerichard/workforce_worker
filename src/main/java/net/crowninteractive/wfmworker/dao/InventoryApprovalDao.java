package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------

import net.crowninteractive.wfmworker.entity.InventoryApproval;
import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 *
 * @author osita
 */
@Component
public class InventoryApprovalDao extends AbstractDao<Integer, InventoryApproval> {
    public List<InventoryApproval> findByEmailSendTimeNULL() {
        String query = "SELECT * from inventory_approval where email_send_time is null ";

        return getEntityManager().createNativeQuery(query, InventoryApproval.class).getResultList();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
