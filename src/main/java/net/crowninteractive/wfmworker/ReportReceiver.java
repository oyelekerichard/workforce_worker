/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import com.dyfferential.vyral.web.util.DateFormatter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.jms.Message;
import javax.jms.TextMessage;
import net.crowninteractive.wfmworker.dao.UsersDao;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.Users;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.exception.WfmWorkerException;
import net.crowninteractive.wfmworker.service.SendEmail;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class ReportReceiver {

    @Autowired
    private WorkOrderDao wdao;
    @Autowired
    private SendEmail emm;
    @Autowired
    private UsersDao usersDao;

    @JmsListener(destination = "${nerc.report.queue}")
    public void handleMessage(Message message) {

        if (message instanceof TextMessage) {
            try {
                TextMessage textMessage = (TextMessage) message;
                String[] txt = textMessage.getText().split(",");
                processWriteV2(txt[0], txt[1], txt[2]);
            } catch (Exception ex) {
                System.out.println("-----------------------Exception occured --------------------------");
                System.out.println("-----------------------Processing file ---------------------------- ");
                ex.printStackTrace();
            }
        }

    }

    private String getBillingId(WorkOrder w) {

        try {
            return (w.getReferenceType() != null && w.getReferenceTypeData() != null && w.getReferenceType().equals("Billing ID") && !w.getReferenceTypeData().equals("Not Applicable")) ? String.valueOf(w.getReferenceTypeData()) : "";
        } catch (Exception e) {
            return "";
        }
    }

    private String getResolution(WorkOrder w) {
        switch (w.getCurrentStatus().toLowerCase()) {
            case "closed":
            case "completed":
            case "resolved":
                return w.getCurrentStatus();
            default:
                return "PENDING";
        }
    }

    public void processWriteV2(String from, String to, String email) throws FileNotFoundException, IOException, EmailException, WfmWorkerException {
        System.out.println("-------------------------Modified report ---------");
        System.out.println("userDao-----------" + this.usersDao);
        System.out.println("-Email-------------" + email);
        Users u = usersDao.findByEmail(email);
        if (u == null) {
            System.out.println(">>>>>>>>No district for user >>>>>>>>.." + email);
            return;
        }
        String commaSeparated = u.getDistricts();
        System.out.println("-----Districts are ------ " + commaSeparated);

        String queueTypeId = usersDao.getQueueTypeIds(email);
        String tariffs = usersDao.getAssignedTariffs(email);

        List<String> districts = new ArrayList<String>(Arrays.asList(commaSeparated.split("\\s*,\\s*")));
        System.out.println("districts >>>>>>" + districts);

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
       
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeight((short) 200);

        CellStyle h = workbook.createCellStyle();
        Font f = workbook.createFont();
        f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        f.setFontHeight((short) 300);
        h.setFont(f);
        h.setAlignment(CellStyle.ALIGN_CENTER);

        headerStyle.setFont(font);

        for (int i = 0; i < districts.size(); i++) {

            List<WorkOrder> lwListt = wdao.getWorkOrderByParams(districts.get(i), from, to, queueTypeId, tariffs);

            SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(districts.get(i));
            sheet.setRandomAccessWindowSize(100);

            Cell info0 = sheet.createRow(2).createCell(0);
            info0.setCellStyle(h);
            info0.setCellValue("EKO ELECTRICITY DISTRIBUTION COMPANY (EKEDP)");
            sheet.addMergedRegion(new CellRangeAddress(
                    2, //first row (0-based)
                    2, //last row  (0-based)
                    0, //first column (0-based)
                    13 //last column  (0-based)
            ));

            Cell info1 = sheet.createRow(3).createCell(13);
            info1.setCellValue(DateFormatter.getSimpleDate(from) + " - " + DateFormatter.getSimpleDate(to));
            info1.setCellStyle(headerStyle);
            Cell info2 = sheet.getRow(3).createCell(12);
            info2.setCellValue("Month : ");
            info2.setCellStyle(headerStyle);

            Row xheader = sheet.createRow(5);
            String[] header = {"S/N", "Ticket ID",
                "Customer Name",
                "Customer's Address",
                "Account Number",
                "Phone No", "Tariff Class", "B/Unit",
                "Nature of Complaint", "Date Received",
                "Action Taken", "Date Resolved", "Resolution", "Category"};
            for (int col = 0; col < 14; col++) {
                Cell cell = xheader.createCell(col);
                cell.setCellValue(header[col]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(col);
            }

            int rownum = 6;
            double count = 1;

            for (WorkOrder w : lwListt) {
                Row row = sheet.createRow(rownum++);
                Cell cell = row.createCell(0);
                cell.setCellValue(count++);

                row.createCell(1).setCellValue(w.getTicketId());
                String cName = "";
                int c = Cell.CELL_TYPE_BLANK;
                if (w.getCustomerName() == null) {
                    row.createCell(2).setCellValue(Cell.CELL_TYPE_BLANK);
                } else {
                    row.createCell(2).setCellValue(w.getCustomerName());
                }

                row.createCell(3).setCellValue(w.getAddressLine1() + ", " + w.getCity());
                row.createCell(4).setCellValue(getBillingId(w));
                row.createCell(5).setCellValue(String.valueOf(w.getContactNumber()));
                row.createCell(6).setCellValue(String.valueOf(w.getCustomerTariff()));
                row.createCell(7).setCellValue(String.valueOf(w.getBusinessUnit()));
                row.createCell(8).setCellValue(String.valueOf(w.getSummary()));
                row.createCell(9).setCellValue(String.valueOf(w.getCreateTime()));
                String status = "";
                if (w.getIsClosed() != null) {
                    if (w.getCurrentStatus() != null) {
                        status = w.getIsClosed() == 0 ? w.getCurrentStatus() : "CLOSED";
                    }
                }
                row.createCell(10).setCellValue(status);
                row.createCell(11).setCellValue(wdao.getDateResolved(w));
                row.createCell(12).setCellValue(getResolution(w));
                row.createCell(13).setCellValue(String.valueOf(wdao.getQueueTypeName(1, w.getQueueTypeId())));

            }
        }

        String localfile = "generated_" + RandomStringUtils.randomAlphanumeric(5) + "_" + System.currentTimeMillis() + ".xlsx";
        String report = "/var/files/email/" + localfile;
        FileOutputStream outputStream = new FileOutputStream(report);
        workbook.write(outputStream);
        outputStream.close();
       
        System.out.println("file is " + report);

        emm.sendAnEmail("a12wq_minions", "NERC customer care report", email, "Your report is now ready. Please find attached the requested report", localfile, null);
        System.out.println("report processor - Message Sent");
    }

}
