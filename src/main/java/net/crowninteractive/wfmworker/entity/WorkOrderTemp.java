/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnson3yo
 */
@Entity
@Table(name = "work_order_temp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkOrderTemp_1.findAll", query = "SELECT w FROM WorkOrderTemp_1 w")
    , @NamedQuery(name = "WorkOrderTemp_1.findById", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.id = :id")
    , @NamedQuery(name = "WorkOrderTemp_1.findByToken", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.token = :token")
    , @NamedQuery(name = "WorkOrderTemp_1.findByOwnerId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.ownerId = :ownerId")
    , @NamedQuery(name = "WorkOrderTemp_1.findByQueueId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.queueId = :queueId")
    , @NamedQuery(name = "WorkOrderTemp_1.findByQueueTypeId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.queueTypeId = :queueTypeId")
    , @NamedQuery(name = "WorkOrderTemp_1.findBySummary", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.summary = :summary")
    , @NamedQuery(name = "WorkOrderTemp_1.findByContactNumber", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.contactNumber = :contactNumber")
    , @NamedQuery(name = "WorkOrderTemp_1.findByReferenceType", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.referenceType = :referenceType")
    , @NamedQuery(name = "WorkOrderTemp_1.findByReferenceTypeData", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.referenceTypeData = :referenceTypeData")
    , @NamedQuery(name = "WorkOrderTemp_1.findByAddressLine1", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.addressLine1 = :addressLine1")
    , @NamedQuery(name = "WorkOrderTemp_1.findByAddressLine2", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.addressLine2 = :addressLine2")
    , @NamedQuery(name = "WorkOrderTemp_1.findByCity", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.city = :city")
    , @NamedQuery(name = "WorkOrderTemp_1.findByState", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.state = :state")
    , @NamedQuery(name = "WorkOrderTemp_1.findByBusinessUnit", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.businessUnit = :businessUnit")
    , @NamedQuery(name = "WorkOrderTemp_1.findByPriority", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.priority = :priority")
    , @NamedQuery(name = "WorkOrderTemp_1.findByCreateTime", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.createTime = :createTime")
    , @NamedQuery(name = "WorkOrderTemp_1.findByWorkOrderStatusId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.workOrderStatusId = :workOrderStatusId")
    , @NamedQuery(name = "WorkOrderTemp_1.findByIsClosed", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.isClosed = :isClosed")
    , @NamedQuery(name = "WorkOrderTemp_1.findByClosedTime", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.closedTime = :closedTime")
    , @NamedQuery(name = "WorkOrderTemp_1.findByClosedBy", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.closedBy = :closedBy")
    , @NamedQuery(name = "WorkOrderTemp_1.findByIsAssigned", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.isAssigned = :isAssigned")
    , @NamedQuery(name = "WorkOrderTemp_1.findByEngineerId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.engineerId = :engineerId")
    , @NamedQuery(name = "WorkOrderTemp_1.findByTeamId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.teamId = :teamId")
    , @NamedQuery(name = "WorkOrderTemp_1.findByDateAssigned", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.dateAssigned = :dateAssigned")
    , @NamedQuery(name = "WorkOrderTemp_1.findByAssignedBy", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.assignedBy = :assignedBy")
    , @NamedQuery(name = "WorkOrderTemp_1.findByCreatedBy", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.createdBy = :createdBy")
    , @NamedQuery(name = "WorkOrderTemp_1.findByChannel", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.channel = :channel")
    , @NamedQuery(name = "WorkOrderTemp_1.findByIsActive", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.isActive = :isActive")
    , @NamedQuery(name = "WorkOrderTemp_1.findByUpdateTime", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.updateTime = :updateTime")
    , @NamedQuery(name = "WorkOrderTemp_1.findByUpdatedBy", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.updatedBy = :updatedBy")
    , @NamedQuery(name = "WorkOrderTemp_1.findByCurrentStatus", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.currentStatus = :currentStatus")
    , @NamedQuery(name = "WorkOrderTemp_1.findByInventoryDescription", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.inventoryDescription = :inventoryDescription")
    , @NamedQuery(name = "WorkOrderTemp_1.findByReportedBy", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.reportedBy = :reportedBy")
    , @NamedQuery(name = "WorkOrderTemp_1.findByInventoryRef", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.inventoryRef = :inventoryRef")
    , @NamedQuery(name = "WorkOrderTemp_1.findByRequestedInventory", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.requestedInventory = :requestedInventory")
    , @NamedQuery(name = "WorkOrderTemp_1.findByDateRequestedInventory", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.dateRequestedInventory = :dateRequestedInventory")
    , @NamedQuery(name = "WorkOrderTemp_1.findByApprovedInventory", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.approvedInventory = :approvedInventory")
    , @NamedQuery(name = "WorkOrderTemp_1.findByInventoryApprovedBy", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.inventoryApprovedBy = :inventoryApprovedBy")
    , @NamedQuery(name = "WorkOrderTemp_1.findByDateApprovedInventory", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.dateApprovedInventory = :dateApprovedInventory")
    , @NamedQuery(name = "WorkOrderTemp_1.findByInventoryApproved", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.inventoryApproved = :inventoryApproved")
    , @NamedQuery(name = "WorkOrderTemp_1.findByWorkDate", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.workDate = :workDate")
    , @NamedQuery(name = "WorkOrderTemp_1.findBySlot", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.slot = :slot")
    , @NamedQuery(name = "WorkOrderTemp_1.findByAgentName", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.agentName = :agentName")
    , @NamedQuery(name = "WorkOrderTemp_1.findByCustomerName", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.customerName = :customerName")
    , @NamedQuery(name = "WorkOrderTemp_1.findByTicketId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.ticketId = :ticketId")
    , @NamedQuery(name = "WorkOrderTemp_1.findByCustomerTariff", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.customerTariff = :customerTariff")
    , @NamedQuery(name = "WorkOrderTemp_1.findByDisco", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.disco = :disco")
    , @NamedQuery(name = "WorkOrderTemp_1.findBySubDisco", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.subDisco = :subDisco")
    , @NamedQuery(name = "WorkOrderTemp_1.findByInjectionSubstation", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.injectionSubstation = :injectionSubstation")
    , @NamedQuery(name = "WorkOrderTemp_1.findByInjectionSubstationName", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.injectionSubstationName = :injectionSubstationName")
    , @NamedQuery(name = "WorkOrderTemp_1.findByPowerTransformer", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.powerTransformer = :powerTransformer")
    , @NamedQuery(name = "WorkOrderTemp_1.findByPowerTransformerName", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.powerTransformerName = :powerTransformerName")
    , @NamedQuery(name = "WorkOrderTemp_1.findByFeeder", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.feeder = :feeder")
    , @NamedQuery(name = "WorkOrderTemp_1.findByFeederName", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.feederName = :feederName")
    , @NamedQuery(name = "WorkOrderTemp_1.findByHtPole", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.htPole = :htPole")
    , @NamedQuery(name = "WorkOrderTemp_1.findByHighTensionPhysicalId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.highTensionPhysicalId = :highTensionPhysicalId")
    , @NamedQuery(name = "WorkOrderTemp_1.findByDistributionSubstation", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.distributionSubstation = :distributionSubstation")
    , @NamedQuery(name = "WorkOrderTemp_1.findByDistributionSubstationName", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.distributionSubstationName = :distributionSubstationName")
    , @NamedQuery(name = "WorkOrderTemp_1.findByUpriser", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.upriser = :upriser")
    , @NamedQuery(name = "WorkOrderTemp_1.findByServicePole", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.servicePole = :servicePole")
    , @NamedQuery(name = "WorkOrderTemp_1.findByServiceWire", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.serviceWire = :serviceWire")
    , @NamedQuery(name = "WorkOrderTemp_1.findByNercId", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.nercId = :nercId")
    , @NamedQuery(name = "WorkOrderTemp_1.findByConnectionType", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.connectionType = :connectionType")
    , @NamedQuery(name = "WorkOrderTemp_1.findByTransformer", query = "SELECT w FROM WorkOrderTemp_1 w WHERE w.transformer = :transformer")})
public class WorkOrderTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @Column(name = "owner_id")
    private int ownerId;
    @Basic(optional = false)
    @Column(name = "queue_id")
    private int queueId;
    @Basic(optional = false)
    @Column(name = "queue_type_id")
    private int queueTypeId;
    @Basic(optional = false)
    @Column(name = "summary")
    private String summary;
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "contact_number")
    private String contactNumber;
    @Basic(optional = false)
    @Column(name = "reference_type")
    private String referenceType;
    @Column(name = "reference_type_data")
    private String referenceTypeData;
    @Basic(optional = false)
    @Column(name = "address_line_1")
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @Basic(optional = false)
    @Column(name = "business_unit")
    private String businessUnit;
    @Basic(optional = false)
    @Column(name = "priority")
    private String priority;
    @Basic(optional = false)
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "work_order_status_id")
    private Integer workOrderStatusId;
    @Column(name = "is_closed")
    private Short isClosed;
    @Column(name = "closed_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedTime;
    @Column(name = "closed_by")
    private Integer closedBy;
    @Column(name = "is_assigned")
    private Short isAssigned;
    @Column(name = "engineer_id")
    private Integer engineerId;
    @Column(name = "team_id")
    private Integer teamId;
    @Column(name = "date_assigned")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAssigned;
    @Column(name = "assigned_by")
    private Integer assignedBy;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "channel")
    private String channel;
    @Basic(optional = false)
    @Column(name = "is_active")
    private int isActive;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @Column(name = "current_status")
    private String currentStatus;
    @Column(name = "inventory_description")
    private String inventoryDescription;
    @Column(name = "reported_by")
    private String reportedBy;
    @Column(name = "inventory_ref")
    private String inventoryRef;
    @Column(name = "requested_inventory")
    private String requestedInventory;
    @Column(name = "date_requested_inventory")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRequestedInventory;
    @Column(name = "approved_inventory")
    private String approvedInventory;
    @Column(name = "inventory_approved_by")
    private Integer inventoryApprovedBy;
    @Column(name = "date_approved_inventory")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApprovedInventory;
    @Column(name = "inventory_approved")
    private Short inventoryApproved;
    @Column(name = "work_date")
    @Temporal(TemporalType.DATE)
    private Date workDate;
    @Column(name = "slot")
    private String slot;
    @Column(name = "agent_name")
    private String agentName;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "ticket_id")
    private Integer ticketId;
    @Column(name = "customer_tariff")
    private String customerTariff;
    @Column(name = "disco")
    private String disco;
    @Column(name = "sub_disco")
    private String subDisco;
    @Column(name = "injection_substation")
    private String injectionSubstation;
    @Column(name = "injection_substation_name")
    private String injectionSubstationName;
    @Column(name = "power_transformer")
    private String powerTransformer;
    @Column(name = "power_transformer_name")
    private String powerTransformerName;
    @Column(name = "feeder")
    private String feeder;
    @Column(name = "feeder_name")
    private String feederName;
    @Column(name = "ht_pole")
    private String htPole;
    @Column(name = "high_tension_physical_id")
    private String highTensionPhysicalId;
    @Column(name = "distribution_substation")
    private String distributionSubstation;
    @Column(name = "distribution_substation_name")
    private String distributionSubstationName;
    @Column(name = "upriser")
    private String upriser;
    @Column(name = "service_pole")
    private String servicePole;
    @Column(name = "service_wire")
    private String serviceWire;
    @Column(name = "nerc_id")
    private String nercId;
    @Basic(optional = false)
    @Column(name = "connection_type")
    private String connectionType;
    @Basic(optional = false)
    @Column(name = "transformer")
    private String transformer;

    public WorkOrderTemp() {
    }

    public WorkOrderTemp(Integer id) {
        this.id = id;
    }

    public WorkOrderTemp(Integer id, String token, int ownerId, int queueId, int queueTypeId, String summary, String contactNumber, String referenceType, String addressLine1, String city, String state, String businessUnit, String priority, Date createTime, int isActive, String connectionType, String transformer) {
        this.id = id;
        this.token = token;
        this.ownerId = ownerId;
        this.queueId = queueId;
        this.queueTypeId = queueTypeId;
        this.summary = summary;
        this.contactNumber = contactNumber;
        this.referenceType = referenceType;
        this.addressLine1 = addressLine1;
        this.city = city;
        this.state = state;
        this.businessUnit = businessUnit;
        this.priority = priority;
        this.createTime = createTime;
        this.isActive = isActive;
        this.connectionType = connectionType;
        this.transformer = transformer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getWorkOrderStatusId() {
        return workOrderStatusId;
    }

    public void setWorkOrderStatusId(Integer workOrderStatusId) {
        this.workOrderStatusId = workOrderStatusId;
    }

    public Short getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Short isClosed) {
        this.isClosed = isClosed;
    }

    public Date getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }

    public Integer getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(Integer closedBy) {
        this.closedBy = closedBy;
    }

    public Short getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Short isAssigned) {
        this.isAssigned = isAssigned;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Date getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public Integer getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Integer assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getInventoryDescription() {
        return inventoryDescription;
    }

    public void setInventoryDescription(String inventoryDescription) {
        this.inventoryDescription = inventoryDescription;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getInventoryRef() {
        return inventoryRef;
    }

    public void setInventoryRef(String inventoryRef) {
        this.inventoryRef = inventoryRef;
    }

    public String getRequestedInventory() {
        return requestedInventory;
    }

    public void setRequestedInventory(String requestedInventory) {
        this.requestedInventory = requestedInventory;
    }

    public Date getDateRequestedInventory() {
        return dateRequestedInventory;
    }

    public void setDateRequestedInventory(Date dateRequestedInventory) {
        this.dateRequestedInventory = dateRequestedInventory;
    }

    public String getApprovedInventory() {
        return approvedInventory;
    }

    public void setApprovedInventory(String approvedInventory) {
        this.approvedInventory = approvedInventory;
    }

    public Integer getInventoryApprovedBy() {
        return inventoryApprovedBy;
    }

    public void setInventoryApprovedBy(Integer inventoryApprovedBy) {
        this.inventoryApprovedBy = inventoryApprovedBy;
    }

    public Date getDateApprovedInventory() {
        return dateApprovedInventory;
    }

    public void setDateApprovedInventory(Date dateApprovedInventory) {
        this.dateApprovedInventory = dateApprovedInventory;
    }

    public Short getInventoryApproved() {
        return inventoryApproved;
    }

    public void setInventoryApproved(Short inventoryApproved) {
        this.inventoryApproved = inventoryApproved;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerTariff() {
        return customerTariff;
    }

    public void setCustomerTariff(String customerTariff) {
        this.customerTariff = customerTariff;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkOrderTemp)) {
            return false;
        }
        WorkOrderTemp other = (WorkOrderTemp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.WorkOrderTemp_1[ id=" + id + " ]";
    }
    
}
