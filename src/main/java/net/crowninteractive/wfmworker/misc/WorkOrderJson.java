/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.crowninteractive.wfmworker.entity.EnumerationWorkOrder;

/**
 *
 * @author uchep
 */
public class WorkOrderJson {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("queueTypeToken")
    @Expose
    private String queueTypeToken;
    @SerializedName("businessUnit")
    @Expose
    private String businessUnit;
    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("referenceType")
    @Expose
    private String referenceType;
    @SerializedName("referenceTypeData")
    @Expose
    private String referenceTypeData;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("serviceWireCode")
    @Expose
    private String serviceWireCode;
    @SerializedName("reportedBy")
    @Expose
    private String reportedBy;
    @SerializedName("disco")
    @Expose
    private String disco;
    @SerializedName("subDisco")
    @Expose
    private String subDisco;
    @SerializedName("injectionSubstation")
    @Expose
    private String injectionSubstation;
    @SerializedName("injectionSubstationName")
    @Expose
    private String injectionSubstationName;
    @SerializedName("powerTransformer")
    @Expose
    private String powerTransformer;
    @SerializedName("powerTransformerName")
    @Expose
    private String powerTransformerName;
    @SerializedName("feeder")
    @Expose
    private String feeder;
    @SerializedName("feederName")
    @Expose
    private String feederName;
    @SerializedName("htPole")
    @Expose
    private String htPole;
    @SerializedName("highTensionPhysicalId")
    @Expose
    private String highTensionPhysicalId;
    @SerializedName("distributionSubstation")
    @Expose
    private String distributionSubstation;
    @SerializedName("distributionSubstationName")
    @Expose
    private String distributionSubstationName;
    @SerializedName("upriser")
    @Expose
    private String upriser;
    @SerializedName("servicePole")
    @Expose
    private String servicePole;
    @SerializedName("serviceWire")
    @Expose
    private String serviceWire;
    @SerializedName("nercId")
    @Expose
    private String nercId;
    @SerializedName("connectionType")
    @Expose
    private String connectionType;
    @SerializedName("transformer")
    @Expose
    private String transformer;

    private String customerName;
    
    private EnumerationWorkOrder enumerationData;

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

    public String getQueueTypeToken() {
        return queueTypeToken;
    }

    public void setQueueTypeToken(String queueTypeToken) {
        this.queueTypeToken = queueTypeToken;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceWireCode() {
        return serviceWireCode;
    }

    public void setServiceWireCode(String serviceWireCode) {
        this.serviceWireCode = serviceWireCode;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
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

    public String getTransformer() {
        return transformer;
    }

    public void setTransformer(String transformer) {
        this.transformer = transformer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public EnumerationWorkOrder getEnumerationData() {
        return enumerationData;
    }

    public void setEnumerationData(EnumerationWorkOrder enumerationData) {
        this.enumerationData = enumerationData;
    }

}
