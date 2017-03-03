
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------

import net.crowninteractive.wfmworker.entity.EscalationSettings;
import net.crowninteractive.wfmworker.entity.WorkOrderStatus;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 *
 * @author osita
 */
@Component
public class EscalationSettingsDao extends AbstractDao<Integer, EscalationSettings> {
    public EscalationSettings getEscalationSettingsById(int id) {
        String                   query   = String.format("select * from escalation_settings where id=?1");
        List<EscalationSettings> options = getEntityManager().createNativeQuery(query,
                                               EscalationSettings.class).setParameter(1, id).getResultList();

        if ((options != null) &&!options.isEmpty()) {
            return options.get(0);
        } else {
            return null;
        }
    }

    public EscalationSettings getEscalationSettingsForNewWorkOrder(int queueTypeId) {
        String query =
            String.format(
                "select * from escalation_settings where is_active=1 and isnull(status_id) and queue_type_id=?1 order by priority asc limit 1");
        List<EscalationSettings> options = getEntityManager().createNativeQuery(query,
                                               EscalationSettings.class).setParameter(1, queueTypeId).getResultList();

        if ((options != null) &&!options.isEmpty()) {
            return options.get(0);
        } else {
            return null;
        }
    }

    public EscalationSettings getNextEscalationLevel(WorkOrderStatus statusId, int queueTypeId, int priority) {
        List<EscalationSettings> options;

        if (statusId == null) {
            String query =
                String.format(
                    "select * from escalation_settings where queue_type_id=?1 and priority=?2 and isnull(status_id)");

            options = getEntityManager().createNativeQuery(query, EscalationSettings.class).setParameter(1,
                    queueTypeId).setParameter(2, priority).getResultList();
        } else {
            String query =
                String.format(
                    "select * from escalation_settings where queue_type_id=?1 and priority=?2 and status_id=?3");

            options = getEntityManager().createNativeQuery(query, EscalationSettings.class).setParameter(1,
                    queueTypeId).setParameter(2, priority).setParameter(3, statusId.getId()).getResultList();
        }

        if ((options != null) &&!options.isEmpty()) {
            return options.get(0);
        } else {
            return null;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
