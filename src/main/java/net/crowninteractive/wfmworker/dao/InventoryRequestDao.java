package net.crowninteractive.wfmworker.dao;

import java.util.List;
import net.crowninteractive.wfmworker.entity.InventoryApproval;
import net.crowninteractive.wfmworker.entity.InventoryRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author osita
 */
@Component
public class InventoryRequestDao extends AbstractDao<Integer, InventoryRequest>{
    
    public InventoryRequest findById(int id){
        String query = "SELECT * from inventory_request where id=?1";
        List<InventoryRequest> result = getEntityManager().createNativeQuery(query, InventoryApproval.class).setParameter(1, id).getResultList();
        if(result != null && !result.isEmpty()){
            return result.get(0);
        }else{
            return null;
        }
    }
    
}
