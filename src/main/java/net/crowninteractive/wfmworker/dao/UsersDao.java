
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

//~--- non-JDK imports --------------------------------------------------------

import net.crowninteractive.wfmworker.entity.Users;

import org.springframework.stereotype.Component;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 *
 * @author osita
 */
@Component
public class UsersDao extends AbstractDao<Integer, Users> {
    public Users findByOwnerAndId(int owner, Integer id) {
        String      query   = String.format("select * from users where owner_id=?1 and id=?2");
        List<Users> options = getEntityManager().createNativeQuery(query, Users.class).setParameter(1,
                                  owner).setParameter(2, id).getResultList();

        if ((options != null) &&!options.isEmpty()) {
            return options.get(0);
        } else {
            return null;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
