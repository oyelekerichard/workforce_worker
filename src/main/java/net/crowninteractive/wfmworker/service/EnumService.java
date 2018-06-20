/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import net.crowninteractive.wfmworker.dao.BarChartWidget;
import net.crowninteractive.wfmworker.dao.BarChartWidget_1;
import net.crowninteractive.wfmworker.dao.QueueTypeData;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.Dashboard;
import net.crowninteractive.wfmworker.entity.LowerWidget;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author johnson3yo
 */
@Service
public class EnumService {

    @Autowired
    private WorkOrderDao wdao;

    private List<QueueTypeData> qtees;
    private List<LowerWidget> statusByDistrict = new ArrayList();
    private List<LowerWidget> statusByConsultant = new ArrayList();
   
    private List<QueueTypeData> qd = new ArrayList();
    private List<Map<String, String>> cslts = new ArrayList();
     private List<Map<String, String>> dstrts = new ArrayList();
    
    
    @PostConstruct
    public void initQueueTypes() {
        qtees = wdao.getQueueTypesByQueue(17);

        String[] districts = new String[]{"AGBARA",
            "APAPA", "FESTAC", "IBEJU", "IJORA", "LEKKI", "MUSHIN", "ISLANDS", "OJO", "ORILE"};
        String[] consultants = new String[]{"HAFMANI",
            "POWERCAP", "POLARIS", "TURBO"};

        for (String d : districts) {
            LowerWidget dss = new LowerWidget();
            dss.getDistrictName(d);
            statusByDistrict.add(dss);
            BarChartWidget_1 cbd = new BarChartWidget_1();
            Map<String,String>field = new HashMap();
            field.put("district", d);
            dstrts.add(field);
        }

        for (String e : consultants) {
            LowerWidget dss = new LowerWidget();
            dss.setReportedBy(e);
            statusByConsultant.add(dss);
            BarChartWidget_1 bbd = new BarChartWidget_1();
            Map<String,String>field = new HashMap();
            field.put("consultant", e);
            cslts.add(field);
        }

    }

    public String approveWorkOrders(Token tokens) {

        try {
            if (tokens.getTokens() == null || tokens.getTokens().length == 0) {
                return String.format("Tokens must not be empty");
            } else {

                Integer success = 0, failure = 0, approved = 0;
                for (String token : tokens.getTokens()) {
                    WorkOrderTemp workOrderTemp = wdao.getEnumWorkOrderByToken(token);

                    if (workOrderTemp != null) {
                        if (workOrderTemp.getTicketId() == null) {
                            String customername = null;
                            String type = "a";
                            Awesome awe = getCustomerDetails(workOrderTemp.getReferenceTypeData(), type);
                            if (awe.getResp() == 0) {
                                Gson gson = new GsonBuilder().create();
                                Map jsonMap = gson.fromJson(gson.toJson(awe.getObject()), Map.class);
                                customername = (String) jsonMap.get("name");
                            } else {
                                customername = workOrderTemp.getCustomerName();
                            }
                            workOrderTemp.setCustomerName(customername);
                            wdao.approveEnumWorkOrder(workOrderTemp);
                            success++;
                        } else {
                            approved++;
                        }

                    } else {
                        failure++;
                    }

                }
                return String.format((success > 0 ? success + " work order(s) were successfully approved," : "") + (failure > 0 ? failure + " approval request failed," : "") + (approved > 0 ? approved + " already approved" : ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    public Awesome getCustomerDetails(String number, String type) throws IOException {
        Config c = Config.getInstance();

        HashMap<String, String> queryparams = new HashMap<String, String>();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(c.getEMCCGetCustomerDetailsURL());
        
        if(builder!= null){
            return new Awesome(5,"no config");
        }
        if (type.toLowerCase().equals("a")) {
            builder.queryParam("accountNumber", number);
        } else {
            builder.queryParam("meterNumber", number);
        }
        RestTemplate restTemplate = new RestTemplate();
        Awesome response = restTemplate.getForObject(builder.toUriString(), Awesome.class);
        return response;

    }

    public Dashboard getDashboard(String startDate, String endDate, String flag1, String flag2) {
        //Statuses Data

        Dashboard bss = new Dashboard();

        if (flag1 != null) {

            lowerWidget(statusByDistrict, 0);
            bss.setDistrictStatuses(statusByDistrict);

        } else {

            lowerWidget(statusByConsultant, 0);
            bss.setDistrictStatuses(statusByConsultant);
        }

        if (flag2 != null) {

            barWidgetData(cslts, 0);
            bss.setConsultants(cslts);
        } else {

            barWidgetData(dstrts, 0);
            bss.setDistricts(dstrts);

        }

        List<BigInteger> res = wdao.getTotalCount();

        bss.setTotalClosed(res.get(0));
        bss.setTotalOpened(res.get(1));

        //Graph Data
        return bss;
    }

    public void barWidgetData(List<Map<String, String>> c, int i) {
        if (i == c.size() - 1) {
            return;
        } else {
            Map<String, String> get = c.get(i);
            String con = get.get("consultant");
            String dis = get.get("district");
            wdao.getData(get,qtees, con, dis);           
            i++;
            barWidgetData(c, i);
        }
    }

    public void lowerWidget(List<LowerWidget> ds, int size) {

        if (size == ds.size() - 1) {
            return;
        } else {

            String name = ds.get(size).getDistrictName() != null ? ds.get(size).getDistrictName() : null;
            String reportedBy = ds.get(size).getReportedBy() != null ? ds.get(size).getReportedBy() : null;
            BigInteger openCount
                    = wdao.getWorkOrderByStatusAndDistrict("OPEN", name, reportedBy);
            BigInteger closedCount
                    = wdao.getWorkOrderByStatusAndDistrict("CLOSED", name, reportedBy);
            LowerWidget dw = ds.get(size);
            dw.setOpen(openCount);
            dw.setClosed(closedCount);
            size++;
            lowerWidget(ds, size);
        }

    }

    public void updateEnumWorkOrder(Map<String, String> update) throws WfmWorkerException {
        String ticketId = update.get("ticket_id");
        String status = update.get("status");
        WorkOrderTemp wot = wdao.getEnumWorkOrderByTicketId(Integer.parseInt(ticketId));
    }

}
