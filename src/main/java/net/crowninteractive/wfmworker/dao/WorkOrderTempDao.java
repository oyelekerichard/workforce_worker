/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.util.Date;
import java.util.List;
import net.crowninteractive.wfmworker.entity.EnumerationWorkOrder;
import net.crowninteractive.wfmworker.entity.QueueType;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.misc.WorkOrderJson;
import net.crowninteractive.wfmworker.service.Awesome;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author johnson3yo
 */
@Repository
public class WorkOrderTempDao extends AbstractDao<Integer, WorkOrderTemp> {

    @Autowired
    private EnumerationWorkOrderDao ewod;

    public Awesome createEnumerationWorkOrder(WorkOrderJson workOrderJson) {
        Awesome retn = StandardResponse.unableToComplete();
        String token = (String) getUniqueEnumWorkOrderToken();
        QueueType qt = getQueueTypeByToken(workOrderJson.getQueueTypeToken());
        if (qt != null) {
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
                    
                    ed.setWork_order_temp_token(token);
                    EnumerationWorkOrder edSave = ewod.save(ed);
                    if (edSave != null) {
                        retn = StandardResponse.ok();
                        //saving was successful
                    }else{
                    retn = StandardResponse.Error("Couldn't save Enumeration Work Order");    
                    }
                }else{
                    retn = StandardResponse.Error("Couldn't save Work Order request");
                }

            }else{
                retn = StandardResponse.Error("Couldn't find Enumeration Work Order in request");
            }
        }else{
            retn = StandardResponse.Error("Unknown queue type");
        }
        return retn;
    }

    public QueueType getQueueTypeByToken(String queueTypeToken) {
        String sql = String.format("select * from queue_type where token='%s' limit 1", queueTypeToken);
        List<QueueType> qt = (List<QueueType>) getEntityManager().createNativeQuery(sql).getResultList();
        if (!qt.isEmpty()) {
            return qt.get(0);
        }
        return null;
    }

    private Object getUniqueEnumWorkOrderToken() {
        String token = RandomStringUtils.randomAlphanumeric(30);

        String sql = String.format("select token from work_order_temp where token='%s' limit 1", token);
        System.out.println(sql);
        List resultList = getEntityManager().createNativeQuery(sql).getResultList();

        if (!resultList.isEmpty()) {
            return getUniqueEnumWorkOrderToken();
        } else {
            return token;
        }

    }

}
