/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.util.List;
import net.crowninteractive.wfmworker.entity.EmailSend;
import org.springframework.stereotype.Component;

/**
 *
 * @author osita
 */
@Component
public class EmailSendDao extends AbstractDao<Integer, EmailSend>{
    
    public void update(EmailSend es){
        edit(es);
    }
    
    public List<EmailSend> findUnsentEmails(){
        String query = String.format("select * from email_send where isnull(sent_time)");
        return getEntityManager().createNativeQuery(query, EmailSend.class).getResultList();
    }
}
