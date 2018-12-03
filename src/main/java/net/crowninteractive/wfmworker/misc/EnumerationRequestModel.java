/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author CROWN INTERACTIVE
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnumerationRequestModel {

    private Integer id;
    private String queueId;
    private String queueTypeId;
    private String summary;
    private String description;
    private Integer ticketId;
    private String disco;
    private String subDisco;
    private String injectionSubstation;
    private String injectionSubstationName;
    private String powerTransformer;
    private String powerTransformerName;
    private String feeder;
    private String feederName;
    private String htPole;
    private String highTensionPhysicalId;
    private String distributionSubstation;
    private String distributionSubstationName;
    private String transformer;
    private String upriser;
    private String servicePole;
    private String serviceWire;
    private String nercId;
    private String connectionType;
    private String contactNumber;
    private String referenceType;//Meter Number
    private String referenceTypeData;//Account Number
    private String customerName;
    private String tariff;
    private String businessUnit;
    private String addressLine1;
    private String landmark = "";
    private String city;
    private String state;
    private String priority;
    private Timestamp createTime;
    private String currentStatus;
    private String reportedBy;
    private String customerTariff;
    private String channel;
    private Integer isActive;
    private Integer createdBy;
    private String token;
    
    
    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getQueueTypeId() {
        return queueTypeId;
    }

    public void setQueueTypeId(String queueTypeId) {
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

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getDisco() {
        return disco;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public String getSubDisco() {
        return subDisco;
    }

    public void setSubDisco(String subDisco) {
        this.subDisco = subDisco;
    }

    public String getInjectionSubstation() {
        return injectionSubstation;
    }

    public void setInjectionSubstation(String injectionSubstation) {
        this.injectionSubstation = injectionSubstation;
    }

    public String getInjectionSubstationName() {
        return injectionSubstationName;
    }

    public void setInjectionSubstationName(String injectionSubstationName) {
        this.injectionSubstationName = injectionSubstationName;
    }

    public String getPowerTransformer() {
        return powerTransformer;
    }

    public void setPowerTransformer(String powerTransformer) {
        this.powerTransformer = powerTransformer;
    }

    public String getPowerTransformerName() {
        return powerTransformerName;
    }

    public void setPowerTransformerName(String powerTransformerName) {
        this.powerTransformerName = powerTransformerName;
    }

    public String getFeeder() {
        return feeder;
    }

    public void setFeeder(String feeder) {
        this.feeder = feeder;
    }

    public String getFeederName() {
        return feederName;
    }

    public void setFeederName(String feederName) {
        this.feederName = feederName;
    }

    public String getHtPole() {
        return htPole;
    }

    public void setHtPole(String htPole) {
        this.htPole = htPole;
    }

    public String getHighTensionPhysicalId() {
        return highTensionPhysicalId;
    }

    public void setHighTensionPhysicalId(String highTensionPhysicalId) {
        this.highTensionPhysicalId = highTensionPhysicalId;
    }

    public String getDistributionSubstation() {
        return distributionSubstation;
    }

    public void setDistributionSubstation(String distributionSubstation) {
        this.distributionSubstation = distributionSubstation;
    }

    public String getDistributionSubstationName() {
        return distributionSubstationName;
    }

    public void setDistributionSubstationName(String distributionSubstationName) {
        this.distributionSubstationName = distributionSubstationName;
    }

    public String getTransformer() {
        return transformer;
    }

    public void setTransformer(String transformer) {
        this.transformer = transformer;
    }

    public String getUpriser() {
        return upriser;
    }

    public void setUpriser(String upriser) {
        this.upriser = upriser;
    }

    public String getServicePole() {
        return servicePole;
    }

    public void setServicePole(String servicePole) {
        this.servicePole = servicePole;
    }

    public String getServiceWire() {
        return serviceWire;
    }

    public void setServiceWire(String serviceWire) {
        this.serviceWire = serviceWire;
    }

    public String getNercId() {
        return nercId;
    }

    public void setNercId(String nercId) {
        this.nercId = nercId;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerTariff() {
        return customerTariff;
    }

    public void setCustomerTariff(String customerTariff) {
        this.customerTariff = customerTariff;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}