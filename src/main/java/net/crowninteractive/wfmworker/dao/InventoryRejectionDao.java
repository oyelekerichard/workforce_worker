
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------
import java.util.ArrayList;
import net.crowninteractive.wfmworker.entity.InventoryRejection;

import org.springframework.stereotype.Component;

import java.util.List;
import javax.persistence.EntityNotFoundException;

//~--- JDK imports ------------------------------------------------------------
/**
 *
 * @author USER
 */
@Component
public class InventoryRejectionDao extends AbstractDao<Integer, InventoryRejection> {

    public List<InventoryRejection> findByEmailSendTimeNULL() {
        String query = "SELECT * from inventory_rejection where email_send_time is null";

        try {
            return getEntityManager().createNativeQuery(query, InventoryRejection.class).getResultList();
        } catch (Exception e) {
            if(e instanceof EntityNotFoundException){
                
            }
            return new ArrayList();
            
        }

    }

    public void updateInventoryRejection(InventoryRejection inventoryRejection) {
        this.edit(inventoryRejection);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
