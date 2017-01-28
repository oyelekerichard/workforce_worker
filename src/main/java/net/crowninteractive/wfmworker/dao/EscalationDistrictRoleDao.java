/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.util.ArrayList;
import java.util.List;
import net.crowninteractive.wfmworker.entity.District;
import net.crowninteractive.wfmworker.entity.EscalationDistrictRole;
import net.crowninteractive.wfmworker.entity.EscalationSettings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author osita
 */
@Component
public class EscalationDistrictRoleDao extends AbstractDao<Integer, EscalationSettings>{
    
    public List<EscalationDistrictRole> fetchDistrictRoleEmails(String businessUnit, String roles) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from escalation_district_role where district_id=? and (");
        List<String> pp = new ArrayList<>();
        for(String r : roles.split(",") ){
            if(StringUtils.isNumeric(r)){
                pp.add(String.format(" role_id=%s ", r));
            }
        }
        
        sql.append(StringUtils.join(pp, "or")).append(" ) and is_active=1");
        
        List<EscalationDistrictRole> options = getEntityManager().createNativeQuery(sql.toString(), EscalationDistrictRole.class).setParameter(1, findByName(businessUnit).getId()).getResultList();
        if(options != null && !options.isEmpty()){
            return options;
        }else{
           return null;
        } 
    }
    
    public District findByName(String name){
        String query = String.format("select * from district where name=?1");
        List<District> options = getEntityManager().createNativeQuery(query, District.class).setParameter(1, name).getResultList();            
       
        if(options != null && !options.isEmpty()){
           return options.get(0);
        }else{
           return null;
        }         
    }
}
