/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import net.crowninteractive.wfmworker.entity.InventoryRejection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author USER
 */
@Component
public class InventoryRejectionDao extends AbstractDao<Integer, InventoryRejectionDao>{
    @Autowired
    private EntityManager entityManager;
     public List<InventoryRejection> findByEmailSendTimeNULL(){
       String query="SELECT * from inventory_REJECTION where email_send_time is null";
       return getEntityManager().createNativeQuery(query, InventoryRejection.class).getResultList();
   }
     
    public  void updateInventoryRejection(InventoryRejection inventoryRejection)  {
       
          entityManager.getTransaction().begin();
           entityManager.merge(inventoryRejection);
           entityManager.flush();
         entityManager.getTransaction().commit();
          
      
    }
}
