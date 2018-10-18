/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import java.util.Date;

/**
 *
 * @author johnson3yo
 */
public class WorkOrderMessage {

    private int id;
    private int ticketId;
    private int ownerId;
    private int queueId;
    private int queueTypeId;
    private String summary;
    private String description;
    private String contactNumber;
    private String referenceType;
    private String referenceTypeData;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String businessUnit;
    private String customerTariff;
    private String status;
    private int createdBy;
    private int updatedBy;
    private int assignedBy;
    private String currentStatus;
    private String reportedBy;
    private boolean sentToEmcc;
    private String billingID;
    private String customerName;
    private String inventoryDescription;
    private String customerEmail;
    private String customerCategory;
    private String accountType;
    private attachment file;
    private String currentBill;
    private String lastPaymentAmount;
    private Date lastPaymentDate;

    public WorkOrderMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public int getQueueTypeId() {
        return queueTypeId;
    }

    public void setQueueTypeId(int queueTypeId) {
        this.queueTypeId = queueTypeId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceTypeData() {
        return referenceTypeData;
    }

    public void setReferenceTypeData(String referenceTypeData) {
        this.referenceTypeData = referenceTypeData;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getCustomerTariff() {
        return customerTariff;
    }

    public void setCustomerTariff(String customerTariff) {
        this.customerTariff = customerTariff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(int assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public boolean isSentToEmcc() {
        return sentToEmcc;
    }

    public void setSentToEmcc(boolean sentToEmcc) {
        this.sentToEmcc = sentToEmcc;
    }

    public String getBillingID() {
        return billingID;
    }

    public void setBillingID(String billingID) {
        this.billingID = billingID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInventoryDescription() {
        return inventoryDescription;
    }

    public void setInventoryDescription(String inventoryDescription) {
        this.inventoryDescription = inventoryDescription;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(String customerCategory) {
        this.customerCategory = customerCategory;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public class attachment {

        private String base64String;
        private String filename;
        private String fileDescription;

        public String getBase64String() {
            return base64String;
        }

        public void setBase64String(String base64String) {
            this.base64String = base64String;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFileDescription() {
            return fileDescription;
        }

        public void setFileDescription(String fileDescription) {
            this.fileDescription = fileDescription;
        }
    }

    public attachment getFile() {
        return file;
    }

    public void setFile(attachment file) {
        this.file = file;
    }

    public String getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(String currentBill) {
        this.currentBill = currentBill;
    }

    public String getLastPaymentAmount() {
        return lastPaymentAmount;
    }

    public void setLastPaymentAmount(String lastPaymentAmount) {
        this.lastPaymentAmount = lastPaymentAmount;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

}
