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
import net.crowninteractive.wfmworker.dao.WorkOrderTempDao;
import net.crowninteractive.wfmworker.entity.Dashboard;
import net.crowninteractive.wfmworker.entity.EnumReportObj;
import net.crowninteractive.wfmworker.entity.LowerWidget;
import net.crowninteractive.wfmworker.entity.Queue;
import net.crowninteractive.wfmworker.entity.RequestEnumerationBody;
import net.crowninteractive.wfmworker.entity.WorkOrderTemp;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.misc.Config;
import net.crowninteractive.wfmworker.misc.EnumerationRequestModel;
import net.crowninteractive.wfmworker.misc.RequestListModel;
import net.crowninteractive.wfmworker.misc.EnumerationWorkOrderDownloadModel;
import net.crowninteractive.wfmworker.misc.ExcludeForExcel;
import net.crowninteractive.wfmworker.misc.QueueTypeEnum;
import net.crowninteractive.wfmworker.misc.StandardResponse;
import net.crowninteractive.wfmworker.misc.WorkOrderJson;
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

    @Autowired
    private WorkOrderTempDao wotDao;
    
    private static final Config c = Config.getInstance();

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

    public Awesome createEnumerationWorkOrder(WorkOrderJson workOrderJson){

        return wotDao.createEnumerationWorkOrder(workOrderJson);
     
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
                    System.out.println("Done and dusted!!!");
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
    
    public File createEnumerationWorkOrderTempRequestFile(final String[] tokens) {
        final List<EnumerationWorkOrderDownloadModel> workOrderTemps = wdao.getWorkOrderEnumerationTempByTokens(tokens);

        final File excelFileForWorkOrder = createExcelFileFor(EnumerationWorkOrderDownloadModel.class,
                    workOrderTemps, false, fileType.REQUEST);
        return excelFileForWorkOrder; 
    }
    
    public File createEnumerationWorkOrderFile(final String[] tokens) {
        final List<EnumerationWorkOrderDownloadModel> workOrders = wdao.getWorkOrderEnumerationByTokens(tokens);

        final File excelFileForWorkOrder = createExcelFileFor(EnumerationWorkOrderDownloadModel.class,
                    workOrders, false, fileType.WORKORDER);
        return excelFileForWorkOrder; 
    }

    @Async
    public Awesome sendEnumerationWorkOrderFile(String emailAddress, String district, String from, String to, String queue, String queueType, String priority, String status, String billingId, String ticketId, String reportedBy) {
        List<String> err = validateEnumWorkOrder(to, from);

        if (err.isEmpty()) {
              final List<EnumerationWorkOrderDownloadModel> workOrders = wdao.getEnumerationDownloadList(EnumerationWorkOrderDownloadModel.workOrderQuery(), district, from, to, queue, queueType, priority,status, billingId, ticketId, reportedBy);
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

    private void sendWorkOrderEmailAttachment(String emailAddress, List<EnumerationWorkOrderDownloadModel> data,fileType type) {
        L.info("Preparing to send WorkOrder email to " + emailAddress);
        final File excelFileFor = createExcelFileFor(EnumerationWorkOrderDownloadModel.class, data, true,type);
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
                    "request_report"+System.currentTimeMillis() + ".xls";
            
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
    
    /**
     * Convenience method to send email in application
     *
     * @param address email address for email to be sent to
     * @param bcc bcc if any can be {@code null}
     * @param subject subject of message
     * @param message message to send
     * @param file file to be attached. Can be {@code null}
     * @throws EmailException Exception thrown when a checked error occurs
     * @see EmailException
     * @see HtmlEmail
     */
    @Async
    public void sendEmailTo(final String address, final String bcc, final String subject,
            final String message, final File file, fileType type) throws EmailException {

        HtmlEmail emailAgent = new HtmlEmail();

        //TODO stop using hard-coded values!
        emailAgent.setHostName("smtp.office365.com");
        emailAgent.setSmtpPort(587);
        emailAgent.setAuthentication("no-reply@ekedp.com", "CI@ekedp15");
        emailAgent.setStartTLSEnabled(true);
        emailAgent.setFrom("no-reply@ekedp.com");
        emailAgent.setCharset("utf8");
        emailAgent.setMsg(message);
        emailAgent.setSubject(type.equals(fileType.WORKORDER)? "New Enumeration Work Order Report Generated For You ":
                "New Enumeration Request Report Generated For You" );
        emailAgent.addTo(address);
        if (bcc != null) {
            emailAgent.addBcc(bcc);
        }
        if (file != null) {
            if (file.exists()) {
                EmailAttachment e = new EmailAttachment();
                e.setPath(file.getAbsolutePath());
                emailAgent.attach(e);
            }
        }

        emailAgent.send();
        L.info("Email sent to " + address);

    }

     @Async
    public Awesome sendEnumerationRequestListFile(String emailAddress, String district, String from, String to, String queue, String queueType, String priority, String status, String billingId, String ticketId, String reportedBy) {
        List<String> err = validateEnumWorkOrder(to, from);

        if (err.isEmpty()) {
            final List<EnumerationWorkOrderDownloadModel> requests = wdao.getEnumerationDownloadList(EnumerationWorkOrderDownloadModel.requestQuery(), district, from, to, queue, queueType, priority,status, billingId, ticketId, reportedBy);
            
            if (!requests.isEmpty()) {
                sendWorkOrderEmailAttachment(emailAddress, requests,fileType.REQUEST);
            } else {
                L.info("No work order found for given params");
            }
        } else {
            L.info("Errors in validating to {} and from {}");
        }
        return new Awesome(0, "Successful");
    }
    
    public Awesome getEnumRequestsList(String district, String from, String to, Integer page,
            String queue, String queueType, String priority, String status, String billingId,
            String reportedBy) {
        try {

            Entry<BigInteger, List<RequestListModel>> workOrders;
            List<String> err = validateEnumWorkOrder(to, from);
            String ticketId = null;
            if (err.isEmpty()) {
                
                String sql = "SELECT wt.`id` as id, "
                    + "(select name from queue where id=wt.queue_id) as queue_id,"
                    + "(select name from queue_type where id=wt.queue_type_id) "
                    + "as queue_type_id,wt.ticket_id as ticket_id, wt.`reference_type` as reference_type, wt.`reference_type_data` as reference_type_data, "
                    + "wt.`business_unit` as business_unit, wt.`priority` as priority, wt.`create_time` as create_time,  wt.`current_status` as current_status, wt.`reported_by` as reported_by,  wt.`token` as token, wt.address_line_1 as address_line_1, wt.city as city, e.is_migrated as is_migrated "
                    + "FROM `work_order_temp` wt, enumeration_work_order e where wt.token= e.work_order_temp_token and cast(create_time as date) >= cast({from} as date) and cast(create_time as date) <= cast({to} as date )";
                
                workOrders = wdao.getEnumerationList(sql, district, from, to, page, queue, queueType, priority, status, billingId, ticketId, reportedBy);
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
    
    public Awesome getEnumWorkOrderList(String district, String from, String to, Integer page, String queue, 
                                String queueType, String priority, String status, String billingId, String ticketId, String reportedBy) { 
        try {

            Entry<BigInteger, List<RequestListModel>> workOrders;
            List<String> err = validateEnumWorkOrder(to, from);
            if (err.isEmpty()) {
                
                String sql = "SELECT wt.id as id, "
                    + "(select name from queue where id=wt.queue_id) as queue_id,"
                    + "(select name from queue_type where id=wt.queue_type_id) "
                    + "as queue_type_id, wt.ticket_id as ticketId, wt.reference_type as reference_type, wt.reference_type_data as reference_type_data, "
                    + "wt.business_unit as business_unit, wt.priority as priority, wt.create_time as create_time,  wt.current_status as current_status, wt.reported_by as reported_by, wt.token as token, wt.address_line_1 as address_line_1, wt.city as city, e.is_migrated as is_migrated "
                    + "FROM `work_order` wt join enumeration_work_order e on wt.ticket_id = e.work_order_id where "
                    + " wt.current_status != 'Obsolete' and wt.queue_id = (select id from queue where name like '%enumeration%') and cast(create_time as date) >= cast({from} as date) and cast(create_time as date) <= cast({to} as date ) ";
    
                workOrders = wdao.getEnumerationList(sql, district, from, to, page, queue, queueType, priority, status, billingId, ticketId, reportedBy);
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
            
            RequestEnumerationBody requests = wdao.getEnumRequestByToken(token);           
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
    
    public Awesome getqueueTypeByQueueid(String token) {
        try {
            List<QueueTypeEnum> qts = wdao.getEnumerationQueueTypeByQueueIdList(token);


            if (qts != null && !qts.isEmpty()) {
                return StandardResponse.ok(qts);
            } else {
                return StandardResponse.noRecords();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Awesome getEnumerationQueue() {
        try {
            Queue qt = wdao.getQueue(c.getEnumerationQueueName());
            if (qt != null) {
                return StandardResponse.ok(qt);
            } else {
                return StandardResponse.unableToComplete();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Awesome createEnumerationReportFile(EnumReportObj obj) {
        System.out.println(obj.getType());
        if ("WORK_ORDERS_all".equals(obj.getType()) || "REQUESTS_all".equals(obj.getType())) {
            if ("WORK_ORDERS_all".equals(obj.getType())) {
                final List<EnumerationWorkOrderDownloadModel> workOrders = wdao.getEnumerationDownloadList(EnumerationWorkOrderDownloadModel.workOrderQuery(), "business_unit","create_time", "create_time", null, null, null,null, null, null, null);
                if (workOrders.size() > 0) {
                    File excelFileForWorkOrder = createReportExcel(EnumerationWorkOrderDownloadModel.class,workOrders, obj.getFileName());
                    if (excelFileForWorkOrder.exists()) {
                        return new Awesome(0, "Successful");
                    } else {
                        return new Awesome(400, "File generation failed");
                    }
                } else {
                    return new Awesome(100, "No data retrieved for report");
                }
            } else {
                final List<EnumerationWorkOrderDownloadModel> requests = wdao.getEnumerationDownloadList(EnumerationWorkOrderDownloadModel.requestQuery(), "business_unit", "create_time", "create_time", null, null, null,null, null, null, null);
                if (requests.size() > 0) {
                    File excelFileForWorkOrder = createReportExcel(EnumerationWorkOrderDownloadModel.class,requests, obj.getFileName());
                    if (excelFileForWorkOrder.exists()) {
                        return new Awesome(0, "Successful");
                    } else {
                        return new Awesome(400, "File generation failed");
                    }
                } else {
                    return new Awesome(100, "No data retrieved for report");
                }
            }
        } else if ("WORK_ORDERS_with_select_customers".equals(obj.getType()) || "REQUESTS_with_select_customers".equals(obj.getType())) {
            if ("WORK_ORDERS_with_select_customers".equals(obj.getType())) {
                final List<EnumerationWorkOrderDownloadModel> workOrders = wdao.getWorkOrderEnumerationByTokens(obj.getTokens());
                if (workOrders.size() > 0) {
                    File excelFileForWorkOrder = createReportExcel(EnumerationWorkOrderDownloadModel.class,workOrders, obj.getFileName());
                    if (excelFileForWorkOrder.exists()) {
                        return new Awesome(0, "Successful");
                    } else {
                        return new Awesome(400, "File generation failed");
                    }
                } else {
                    return new Awesome(100, "No data retrieved for report");
                }
            } else {
                final List<EnumerationWorkOrderDownloadModel> requests = wdao.getWorkOrderEnumerationTempByTokens(obj.getTokens());
                if (requests.size() > 0) {
                    File excelFileForWorkOrder = createReportExcel(EnumerationWorkOrderDownloadModel.class,requests, obj.getFileName());
                    if (excelFileForWorkOrder.exists()) {
                        return new Awesome(0, "Successful");
                    } else {
                        return new Awesome(400, "File generation failed");
                    }
                } else {
                    return new Awesome(100, "No data retrieved for report");
                }
            }
        } else {
            return new Awesome(100, "Invalid Report Type");
        }
    }
    
     private <T> File createReportExcel(Class<T> clazz, List<T> data, String fileName) {
        try {
            L.info("Creating file with data rows -----------" + data.size());
            
            final String filePath;

            filePath = "/var/wfm/downloads/" + fileName;

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
    
}


enum fileType{
    REQUEST,WORKORDER;
}
