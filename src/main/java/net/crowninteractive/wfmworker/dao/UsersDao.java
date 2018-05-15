
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
import java.util.stream.Collectors;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;

/**
 *
 * @author osita
 */
@Component
public class UsersDao extends AbstractDao<Integer, Users> {

    public Users findByOwnerAndId(int owner, Integer id) {
        String query = String.format("select * from users where owner_id=?1 and id=?2");
        List<Users> options = getEntityManager().createNativeQuery(query, Users.class).setParameter(1,
                owner).setParameter(2, id).getResultList();

        if ((options != null) && !options.isEmpty()) {
            return options.get(0);
        } else {
            return null;
        }
    }

    public Users findByEmail(String email) {
        String query = String.format("select * from users where email=?1 ");
        List<Users> users = getEntityManager().createNativeQuery(query, Users.class).setParameter(1,
                email).getResultList();
        if ((users != null) && !users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public String getQueueTypeIds(String email) throws WfmWorkerException {
        String query = String.format("select queue_type_id from right_template_queue_type where"
                + " right_template_id = (select right_template_id from users where email = ?1) ");
        List<Integer> queueTypeIds = getEntityManager().createNativeQuery(query).setParameter(1,
                email).getResultList();
        String values = queueTypeIds.stream().map(found -> String.valueOf(found)).collect(Collectors.joining(","));
        if (values == null) {
            throw new WfmWorkerException("No queueTypes found ");
        }
        return values;
    }

    public String getAssignedTariffs(String email) throws WfmWorkerException {
        String query = String.format("select tariff from right_template_tariff where"
                + " right_template_id = (select right_template_id from users where email = ?1) ");
        List<String> queueTypeIds = getEntityManager().createNativeQuery(query).setParameter(1,
                email).getResultList();
        String values = queueTypeIds.stream().map(found -> "'".concat(found).concat("'")).collect(Collectors.joining(","));
        if (values == null) {
            throw new WfmWorkerException("No tariffs  found ");
        }
        return values;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
