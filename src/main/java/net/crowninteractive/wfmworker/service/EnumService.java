/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import net.crowninteractive.wfmworker.dao.BarChartWidget_1;
import net.crowninteractive.wfmworker.dao.QueueTypeData;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.Dashboard;
import net.crowninteractive.wfmworker.entity.LowerWidget;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.Config;
import net.crowninteractive.wfmworker.misc.EnumerationRequestModel;
import net.crowninteractive.wfmworker.misc.ExcludeForExcel;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.misc.WorkOrderDownloadModel;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author johnson3yo
 */
@Service
public class EnumService {

    private Logger L = Logger.getLogger("enumservice");

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
            Map<String, String> field = new HashMap();
            field.put("district", d);
            dstrts.add(field);
        }

        for (String e : consultants) {
            LowerWidget dss = new LowerWidget();
            dss.setReportedBy(e);
            statusByConsultant.add(dss);
            BarChartWidget_1 bbd = new BarChartWidget_1();
            Map<String, String> field = new HashMap();
            field.put("consultant", e);
            cslts.add(field);
        }

    }

    public String approveWorkOrders(Token tokens) {

        try {
            if (tokens.getTokens() == null || tokens.getTokens().length == 0) {
                return String.format("Tokens must not be empty");
            } else {
                System.out.println("token found");
                Integer success = 0, failure = 0, approved = 0;
                for (String token : tokens.getTokens()) {
                    WorkOrderTemp workOrderTemp = wdao.getEnumWorkOrderByToken(token);

                    if (workOrderTemp != null) {
                            System.out.println("workOrderTemp is not empty");
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
                            System.out.println("workOrderTemp.getTicketId() != null");
                            approved++;
                        }

                    } else {
                        System.out.println("workOrderTemp is empty");
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

        if (builder != null) {
            return new Awesome(5, "no config");
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
            wdao.getData(get, qtees, con, dis);
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

    @Async
    public Awesome sendWorkOrderFile(String district, String from, String to, String queue, String queueType, String priority, String status, String billingId, String ticketId, String reportedBy, String emailAddress) {
        List<String> err = validateEnumWorkOrder(to, from);

        if (err.isEmpty()) {
            final List<WorkOrderDownloadModel> workOrders = wdao.getWorkOrders(district, from, to, queue, queueType, priority,
                    status, billingId, ticketId, reportedBy);
            if (!workOrders.isEmpty()) {
                sendWorkOrderEmailAttachment(emailAddress, workOrders,fileType.WORKORDER);
            } else {
                L.info("No work order found for given params");
            }
        } else {
            L.info("Errors in validating to {} and from {}");
        }

        return new Awesome(0, "Successful");
    }

    private void sendWorkOrderEmailAttachment(String emailAddress, List<WorkOrderDownloadModel> data,fileType type) {
        L.info("Preparing to send WorkOrder email to " + emailAddress);
        final File excelFileFor = createExcelFileFor(WorkOrderDownloadModel.class, data, true,type);
        if (excelFileFor != null) {
            try {
                sendEmailTo(emailAddress, null, "Your work order download file", "Please find "
                        + "attached your WorkOrder download file", excelFileFor,type);
                L.info("Successfully created and sent file to " + emailAddress);
            } catch (Exception ex) {
                ex.printStackTrace();
                L.warning("An error occurred while trying to send workorder file to " + emailAddress);
            }
        } else {
            L.warning("Couldn't create excel file to send to " + emailAddress);
        }
    }

    private List<String> validateEnumWorkOrder(String to, String from) {
        List<String> errors = new ArrayList<>();
        try {

            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
            dtf.setLenient(false);
            if (!to.equals("create_time")) {
                Date dt = dtf.parse(to);
            }
            if (!from.equals("create_time")) {
                Date dm = dtf.parse(from);
            }

        } catch (ParseException ex) {
            errors.add("invalid date format");
        }

        return errors;
    }

    private <T> File createExcelFileFor(Class<T> clazz, List<T> data, boolean forEmail,fileType type) {
        L.info("------------Logging Here ----------------------------------");
        try {
            L.info("Creating file with data rows -----------" + data.size());

            String fileName = type.equals(fileType.WORKORDER) ? "work_order_report"+System.currentTimeMillis() + ".xls":
                    "request_report"+System.currentTimeMillis();
            
            final String filePath;

            if (forEmail) {
                filePath = "/var/wfm/" + fileName;
            } else {
                filePath = "/var/wfm/downloads/" + fileName;
            }

            final FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            final Workbook workbook = new SXSSFWorkbook(data.size());
            final Sheet sheet = workbook.createSheet();

            final Field[] declaredFields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> !field.isAnnotationPresent(ExcludeForExcel.class)).toArray(Field[]::new);

            final Row headRow = sheet.createRow(0);

            for (int j = 0; j < declaredFields.length; j++) {
                final Cell cellInHeadRow = headRow.createCell(j);
                cellInHeadRow.setCellValue(declaredFields[j].getName().toUpperCase());
            }

            int rowCount = 1;

            for (T datum : data) {
                final Row row = sheet.createRow(rowCount);
                for (int k = 0; k < declaredFields.length; k++) {
                    final Cell cell = row.createCell(k);
                    final Field declaredField = declaredFields[k];
                    declaredField.setAccessible(true);
                    final Object value = declaredField.get(datum);
                    cell.setCellValue(value != null ? value.toString() : " ");
                }

                rowCount++;
            }
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            return new File(filePath);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("-------------------------__Eror occured -----------------");
            ex.printStackTrace();
            L.warning("An error occurred while trying to createWorkOrderFile" + ex);
            return null;
        }
    }

    private void sendEmailTo(String address, String bcc, String subject, String message, File file,fileType type) throws EmailException {
        HtmlEmail emailAgent = new HtmlEmail();
        EmailAttachment e = new EmailAttachment();
        e.setPath(file.getAbsolutePath());
        //TODO stop using hard-coded values!
        emailAgent.setHostName("smtp.office365.com");
        emailAgent.setSmtpPort(587);
        emailAgent.setAuthentication("no-reply@ekedp.com", "CI@ekedp15");
        emailAgent.setStartTLSEnabled(true);
        emailAgent.setFrom("no-reply@ekedp.com");
        emailAgent.setCharset("utf8");
        emailAgent.setMsg(message);
        emailAgent.setSubject(type.equals(fileType.WORKORDER)? "New Work Order Report Generated For You ":
                "New Enumeration Report Generated For You" );
        emailAgent.addTo(address);
        if (bcc != null) {
            emailAgent.addBcc(bcc);
        }
        if (file != null) {
            if (file.exists()) {
                emailAgent.attach(e);
            }
        }
        emailAgent.send();
        L.info("Email sent to " + address);
    }

    @Async
    public Awesome sendRequestFile(String district, String from, String to, String queue, String queueType, String priority, String status, String billingId, String ticketId, String reportedBy, String emailAddress) {
        List<String> err = validateEnumWorkOrder(to, from);

        if (err.isEmpty()) {
            final List<WorkOrderDownloadModel> workOrders = wdao.getRequests(district, from, to, queue, queueType, priority,
                    status, billingId, ticketId, reportedBy);
            if (!workOrders.isEmpty()) {
                sendWorkOrderEmailAttachment(emailAddress, workOrders,fileType.REQUEST);
            } else {
                L.info("No work order found for given params");
            }
        } else {
            L.info("Errors in validating to {} and from {}");
        }
        return new Awesome(0, "Successful");
    }
    
    public Awesome getRequestsList(String district, String from, String to, Integer page,
            String queue, String queueType, String priority, String status, String billingid,
            String reportedBy) {
        try {

            Entry<BigInteger, List<EnumerationRequestModel>> workOrders;
            List<String> err = validateEnumWorkOrder(to, from);
            if (err.isEmpty()) {
                workOrders = wdao.getRequestsList(district, from, to, page, queue, queueType, priority, status, billingid, reportedBy);
            } else {
                return StandardResponse.validationErrors("Invalid Date Format");
            }

            if (workOrders.getValue() != null && !workOrders.getValue().isEmpty()) {
                return StandardResponse.ok(workOrders);
            } else {
                return StandardResponse.noRecords();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Object[] enumerationReport(String district, String from, String to) {
        return wdao.getEnumerationReport(district, from, to);            
    }
    
    public Awesome getEnumRequestByToken(String token) {
        try {
            
            final EnumerationRequestModel requests = wdao.getEnumRequestByToken(token);
            
            if (requests != null) {
                return StandardResponse.ok(requests);
            } else {
                return StandardResponse.noRecords();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}

enum fileType{
    REQUEST,WORKORDER;
}