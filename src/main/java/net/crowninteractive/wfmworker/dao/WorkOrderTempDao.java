/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.util.Date;
import net.crowninteractive.wfmworker.entity.EnumerationWorkOrder;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.misc.WorkOrderJson;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author johnson3yo
 */
@Repository
public class WorkOrderTempDao extends AbstractDao<Integer, WorkOrderTemp> {

   
    private WorkOrderTempDao temp;
    @Autowired
    private EnumerationWorkOrderDao ewod;

    public Integer createEnumerationWorkOrder(WorkOrderJson workOrderJson) {
        int retn = 0;
        String token = (String) getUniqueEnumWorkOrderToken();
        QueueType qt = getQueueTypeByToken(workOrderJson.getQueueTypeToken());

        EnumerationWorkOrder ed = workOrderJson.getEnumerationData();

        WorkOrderTemp wot = new WorkOrderTemp();

        wot.setQueueId(qt.getQueueId().getId());
        wot.setQueueTypeId(qt.getId());
        wot.setSummary(workOrderJson.getSummary());
        wot.setDescription(workOrderJson.getDescription());
        wot.setContactNumber(workOrderJson.getContactNumber());
        wot.setAddressLine1(workOrderJson.getAddress());
        wot.setAddressLine2("");
        wot.setCity(workOrderJson.getCity());
        wot.setState("Lagos");
        wot.setBusinessUnit(workOrderJson.getBusinessUnit());
        wot.setPriority(workOrderJson.getPriority());
        wot.setCreateTime(new Date());
        wot.setChannel("ENUMERATION");
        wot.setReportedBy(workOrderJson.getReportedBy());
        wot.setCreatedBy(0);
        wot.setCustomerName(workOrderJson.getCustomerName());
        wot.setReferenceType(workOrderJson.getReferenceType());
        wot.setReferenceTypeData(workOrderJson.getReferenceTypeData());
        wot.setToken(token);

        if (ed != null) {
            WorkOrderTemp wotSave = save(wot);
            if (wotSave != null) {
                retn = wotSave.getId();
                EnumerationWorkOrder edSave = ewod.save(ed);
                if (edSave != null) {
                    retn = wotSave.getId();
                    //saving was successful
                }
            }

        }
        return retn;
    }

    public QueueType getQueueTypeByToken(String queueTypeToken) {
        String sql = String.format("select * from queue_type where token='%s' ", queueTypeToken);
        System.out.println(sql);
        return (QueueType) getEntityManager().createNativeQuery(sql, QueueType.class).getSingleResult();
    }

    private Object getUniqueEnumWorkOrderToken() {
        String token = RandomStringUtils.randomAlphanumeric(30);

        String sql = String.format("select * from work_order_temp where token='%s' ", token);
        System.out.println(sql);
        WorkOrder w = (WorkOrder) getEntityManager().createNativeQuery(sql, WorkOrder.class).getSingleResult();

        if (w != null) {
            return getUniqueEnumWorkOrderToken();
        } else {
            return token;
        }

    }

}
