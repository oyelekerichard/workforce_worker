
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------

import net.crowninteractive.wfmworker.entity.EscalationTemp;
import net.crowninteractive.wfmworker.entity.WorkOrder;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 *
 * @author osita
 */
@Component
public class EscalationTempDao extends AbstractDao<Integer, EscalationTemp> {
    public Integer removeTemp(int workOrderId) {
        String               query   = String.format("select * from escalation_temp where work_order_id=?1");
        List<EscalationTemp> options = getEntityManager().createNativeQuery(query,
                                           EscalationTemp.class).setParameter(1, workOrderId).getResultList();

        if ((options != null) &&!options.isEmpty()) {
            EscalationTemp et = options.get(0);

            delete(et);

            return 1;
        } else {
            return 0;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
