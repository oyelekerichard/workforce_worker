/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import com.dyfferential.vyral.web.util.DateFormatter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import net.crowninteractive.wfmworker.dao.WorkOrderDao;
import net.crowninteractive.wfmworker.entity.WorkOrder;
import net.crowninteractive.wfmworker.service.SendEmail;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    
    

    @JmsListener(destination = "${nerc.report.queue}")
    public void handleMessage(Message message) {

        if (message instanceof TextMessage) {
            try {
                TextMessage textMessage = (TextMessage) message;
                String[] txt = textMessage.getText().split(",");
                processWrite(txt[0], txt[1], txt[2]);
            } 
             catch (Exception ex) {
                 System.out.println("-----------------------Exception occured --------------------------");
                 System.out.println("-----------------------Processing file ---------------------------- ");
                ex.printStackTrace();
            }
        }

    }

    private void processWrite(String from, String to, String email) throws FileNotFoundException, IOException, EmailException {
        FileInputStream file = new FileInputStream(new File("/var/files/wfm/nerctemplate.xlsx"));

        //String[] mths = {"-", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        String[] districts = {"APAPA", "MUSHIN", "AGBARA", "ISLANDS", "IJORA", "LEKKI", "OJO", "FESTAC", "UNKNOWN","IBEJU","ORILE"};

        XSSFWorkbook workbook = new XSSFWorkbook(file);

        for (int i = 0; i < districts.length; i++) {

            List<WorkOrder> lwListt = wdao.getWorkOrderByParams(districts[i], from, to);

            XSSFSheet sheet = workbook.getSheetAt(i);

            sheet.getRow(3).getCell(13).setCellValue(DateFormatter.getSimpleDate(from) + " - " + DateFormatter.getSimpleDate(to));
            int rownum = 6;
            double count = 1;
            for (WorkOrder w : lwListt) {
                Row row = sheet.createRow(rownum++);
                Cell cell = row.createCell(0);
                if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(count++);
                } else {
                    cell.setCellValue(count++);
                }

                row.createCell(1).setCellValue(w.getTicketId());
                row.createCell(2).setCellValue(wdao.getCustomerName(w));
                row.createCell(3).setCellValue(w.getAddressLine1() + ", " + w.getCity());
                row.createCell(4).setCellValue(getBillingId(w));
                row.createCell(5).setCellValue(String.valueOf(w.getContactNumber()));
                row.createCell(6).setCellValue(String.valueOf(w.getCustomerTariff()));
                row.createCell(7).setCellValue(String.valueOf(w.getBusinessUnit()));
                row.createCell(8).setCellValue(String.valueOf(w.getSummary()));
                row.createCell(9).setCellValue(String.valueOf(w.getCreateTime()));
                String status = w.getIsClosed()== 0 ? w.getCurrentStatus(): "CLOSED";
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
        System.out.println("file is " + report);

        emm.sendAnEmail("a12wq_minions", "NERC customer care report", email, "Your report is now ready. Please find attached the requested report", localfile, null);
        System.out.println("report processor - Message Sent");
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

}
