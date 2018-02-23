/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author osita
 */
@Entity
@Table(name = "work_order", catalog = "wfm_new", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ticket_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkOrder.findAll", query = "SELECT w FROM WorkOrder w")
    ,
    @NamedQuery(name = "WorkOrder.findById", query = "SELECT w FROM WorkOrder w WHERE w.id = :id")
    ,
    @NamedQuery(name = "WorkOrder.findByTicketId", query = "SELECT w FROM WorkOrder w WHERE w.ticketId = :ticketId")
    ,
    @NamedQuery(name = "WorkOrder.findByToken", query = "SELECT w FROM WorkOrder w WHERE w.token = :token")
    ,
    @NamedQuery(name = "WorkOrder.findByOwnerId", query = "SELECT w FROM WorkOrder w WHERE w.ownerId = :ownerId")
    ,
    @NamedQuery(name = "WorkOrder.findBySummary", query = "SELECT w FROM WorkOrder w WHERE w.summary = :summary")
    ,
    @NamedQuery(name = "WorkOrder.findByContactNumber", query = "SELECT w FROM WorkOrder w WHERE w.contactNumber = :contactNumber")
    ,
    @NamedQuery(name = "WorkOrder.findByReferenceType", query = "SELECT w FROM WorkOrder w WHERE w.referenceType = :referenceType")
    ,
    @NamedQuery(name = "WorkOrder.findByReferenceTypeData", query = "SELECT w FROM WorkOrder w WHERE w.referenceTypeData = :referenceTypeData")
    ,
    @NamedQuery(name = "WorkOrder.findByAddressLine1", query = "SELECT w FROM WorkOrder w WHERE w.addressLine1 = :addressLine1")
    ,
    @NamedQuery(name = "WorkOrder.findByAddressLine2", query = "SELECT w FROM WorkOrder w WHERE w.addressLine2 = :addressLine2")
    ,
    @NamedQuery(name = "WorkOrder.findByCity", query = "SELECT w FROM WorkOrder w WHERE w.city = :city")
    ,
    @NamedQuery(name = "WorkOrder.findByState", query = "SELECT w FROM WorkOrder w WHERE w.state = :state")
    ,
    @NamedQuery(name = "WorkOrder.findByBusinessUnit", query = "SELECT w FROM WorkOrder w WHERE w.businessUnit = :businessUnit")
    ,
    @NamedQuery(name = "WorkOrder.findByCustomerTariff", query = "SELECT w FROM WorkOrder w WHERE w.customerTariff = :customerTariff")
    ,
    @NamedQuery(name = "WorkOrder.findByPriority", query = "SELECT w FROM WorkOrder w WHERE w.priority = :priority")
    ,
    @NamedQuery(name = "WorkOrder.findByCreateTime", query = "SELECT w FROM WorkOrder w WHERE w.createTime = :createTime")
    ,
    @NamedQuery(name = "WorkOrder.findByIsClosed", query = "SELECT w FROM WorkOrder w WHERE w.isClosed = :isClosed")
    ,
    @NamedQuery(name = "WorkOrder.findByClosedTime", query = "SELECT w FROM WorkOrder w WHERE w.closedTime = :closedTime")
    ,
    @NamedQuery(name = "WorkOrder.findByIsAssigned", query = "SELECT w FROM WorkOrder w WHERE w.isAssigned = :isAssigned")
    ,
    @NamedQuery(name = "WorkOrder.findByDateAssigned", query = "SELECT w FROM WorkOrder w WHERE w.dateAssigned = :dateAssigned")
    ,
    @NamedQuery(name = "WorkOrder.findByChannel", query = "SELECT w FROM WorkOrder w WHERE w.channel = :channel")
    ,
    @NamedQuery(name = "WorkOrder.findByIsActive", query = "SELECT w FROM WorkOrder w WHERE w.isActive = :isActive")
    ,
    @NamedQuery(name = "WorkOrder.findByUpdateTime", query = "SELECT w FROM WorkOrder w WHERE w.updateTime = :updateTime")
    ,
    @NamedQuery(name = "WorkOrder.findByCurrentStatus", query = "SELECT w FROM WorkOrder w WHERE w.currentStatus = :currentStatus")
    ,
    @NamedQuery(name = "WorkOrder.findByInventoryDescription", query = "SELECT w FROM WorkOrder w WHERE w.inventoryDescription = :inventoryDescription")
    ,
    @NamedQuery(name = "WorkOrder.findByReportedBy", query = "SELECT w FROM WorkOrder w WHERE w.reportedBy = :reportedBy")
    ,
    @NamedQuery(name = "WorkOrder.findByInventoryRef", query = "SELECT w FROM WorkOrder w WHERE w.inventoryRef = :inventoryRef")
    ,
    @NamedQuery(name = "WorkOrder.findByRequestedInventory", query = "SELECT w FROM WorkOrder w WHERE w.requestedInventory = :requestedInventory")
    ,
    @NamedQuery(name = "WorkOrder.findByDateRequestedInventory", query = "SELECT w FROM WorkOrder w WHERE w.dateRequestedInventory = :dateRequestedInventory")
    ,
    @NamedQuery(name = "WorkOrder.findByApprovedInventory", query = "SELECT w FROM WorkOrder w WHERE w.approvedInventory = :approvedInventory")
    ,
    @NamedQuery(name = "WorkOrder.findByInventoryApprovedBy", query = "SELECT w FROM WorkOrder w WHERE w.inventoryApprovedBy = :inventoryApprovedBy")
    ,
    @NamedQuery(name = "WorkOrder.findByDateApprovedInventory", query = "SELECT w FROM WorkOrder w WHERE w.dateApprovedInventory = :dateApprovedInventory")
    ,
    @NamedQuery(name = "WorkOrder.findByInventoryApproved", query = "SELECT w FROM WorkOrder w WHERE w.inventoryApproved = :inventoryApproved")
    ,
    @NamedQuery(name = "WorkOrder.findByWorkDate", query = "SELECT w FROM WorkOrder w WHERE w.workDate = :workDate")
    ,
    @NamedQuery(name = "WorkOrder.findBySlot", query = "SELECT w FROM WorkOrder w WHERE w.slot = :slot")
    ,
    @NamedQuery(name = "WorkOrder.findByAgentName", query = "SELECT w FROM WorkOrder w WHERE w.agentName = :agentName")})
public class WorkOrder implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workOrderId")
    private List<InventoryApproval> inventoryApprovalList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "ticket_id")
    private int ticketId;
    @Basic(optional = false)
    @Column(name = "token", nullable = false, length = 30)
    private String token;
    @Basic(optional = false)
    @Column(name = "owner_id", nullable = false)
    private int ownerId;
    @Basic(optional = false)
    @Column(name = "summary", nullable = false, length = 500)
    private String summary;
    @Lob
    @Column(name = "description", length = 65535)
    private String description;
    @Basic(optional = false)
    @Column(name = "contact_number", nullable = false, length = 30)
    private String contactNumber;
    @Basic(optional = false)
    @Column(name = "reference_type", nullable = false, length = 20)
    private String referenceType;
    @Column(name = "reference_type_data", length = 40)
    private String referenceTypeData;
    @Basic(optional = false)
    @Column(name = "address_line_1", nullable = false, length = 200)
    private String addressLine1;
    @Column(name = "address_line_2", length = 200)
    private String addressLine2;
    @Basic(optional = false)
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    @Basic(optional = false)
    @Column(name = "state", nullable = false, length = 50)
    private String state;
    @Basic(optional = false)
    @Column(name = "business_unit", nullable = false, length = 50)
    private String businessUnit;
    @Column(name = "customer_tariff", length = 50)
    private String customerTariff;
    @Basic(optional = false)
    @Column(name = "priority", nullable = false, length = 10)
    private String priority;
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "is_closed")
    private Short isClosed;
    @Column(name = "closed_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedTime;
    @Column(name = "is_assigned")
    private Short isAssigned;
    @Column(name = "date_assigned")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAssigned;
    @Column(name = "channel", length = 15)
    private String channel;
    @Basic(optional = false)
    @Column(name = "is_active", nullable = false)
    private int isActive;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "current_status", length = 50)
    private String currentStatus;
    @Column(name = "inventory_description", length = 150)
    private String inventoryDescription;
    @Column(name = "reported_by", length = 150)
    private String reportedBy;
    @Column(name = "inventory_ref", length = 150)
    private String inventoryRef;
    @Column(name = "requested_inventory", length = 500)
    private String requestedInventory;
    @Column(name = "date_requested_inventory")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRequestedInventory;
    @Column(name = "approved_inventory", length = 500)
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
    @Column(name = "slot", length = 15)
    private String slot;
    @Column(name = "agent_name", length = 100)
    private String agentName;
    @Column(name = "customer_name", length = 60)
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    @OneToMany(mappedBy = "workOrderId")
    private List<EscalationWorkOrder> escalationWorkOrderList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workOrderId")
    private List<WorkOrderUpdate> workOrderUpdateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workOrderId")
    private List<EngineerToWorkOrder> engineerToWorkOrderList;
    @JoinColumn(name = "engineer_id", referencedColumnName = "id")
    @ManyToOne
    private Engineer engineerId;
    @JoinColumn(name = "queue_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Queue queueId;
    @JoinColumn(name = "queue_type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private QueueType queueTypeId;
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @ManyToOne
    private Team teamId;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private Users createdBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @ManyToOne
    private Users updatedBy;
    @JoinColumn(name = "assigned_by", referencedColumnName = "id")
    @ManyToOne
    private Users assignedBy;
    @JoinColumn(name = "closed_by", referencedColumnName = "id")
    @ManyToOne
    private Users closedBy;
    @JoinColumn(name = "work_order_status_id", referencedColumnName = "id")
    @ManyToOne
    private WorkOrderStatus workOrderStatusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workOrderId")
    private List<WorkOrderAttachments> workOrderAttachmentsList = new ArrayList();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workOrderId")
    private List<WorkOrderRemark> workOrderRemarkList = new ArrayList();

    public WorkOrder() {
    }

    public WorkOrder(Integer id) {
        this.id = id;
    }

    public WorkOrder(Integer id, int ticketId, String token, int ownerId, String summary, String contactNumber, String referenceType, String addressLine1, String city, String state, String businessUnit, String priority, Date createTime, int isActive) {
        this.id = id;
        this.ticketId = ticketId;
        this.token = token;
        this.ownerId = ownerId;
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
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
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

    public Short getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Short isAssigned) {
        this.isAssigned = isAssigned;
    }

    public Date getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
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

    @XmlTransient
    public List<EscalationWorkOrder> getEscalationWorkOrderList() {
        return escalationWorkOrderList;
    }

    public void setEscalationWorkOrderList(List<EscalationWorkOrder> escalationWorkOrderList) {
        this.escalationWorkOrderList = escalationWorkOrderList;
    }

    @XmlTransient
    public List<WorkOrderUpdate> getWorkOrderUpdateList() {
        return workOrderUpdateList;
    }

    public void setWorkOrderUpdateList(List<WorkOrderUpdate> workOrderUpdateList) {
        this.workOrderUpdateList = workOrderUpdateList;
    }

    @XmlTransient
    public List<EngineerToWorkOrder> getEngineerToWorkOrderList() {
        return engineerToWorkOrderList;
    }

    public void setEngineerToWorkOrderList(List<EngineerToWorkOrder> engineerToWorkOrderList) {
        this.engineerToWorkOrderList = engineerToWorkOrderList;
    }

    public Engineer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Engineer engineerId) {
        this.engineerId = engineerId;
    }

    public Queue getQueueId() {
        return queueId;
    }

    public void setQueueId(Queue queueId) {
        this.queueId = queueId;
    }

    public QueueType getQueueTypeId() {
        return queueTypeId;
    }

    public void setQueueTypeId(QueueType queueTypeId) {
        this.queueTypeId = queueTypeId;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Users getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Users updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Users getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Users assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Users getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(Users closedBy) {
        this.closedBy = closedBy;
    }

    public WorkOrderStatus getWorkOrderStatusId() {
        return workOrderStatusId;
    }

    public void setWorkOrderStatusId(WorkOrderStatus workOrderStatusId) {
        this.workOrderStatusId = workOrderStatusId;
    }

    @XmlTransient
    public List<WorkOrderAttachments> getWorkOrderAttachmentsList() {
        return workOrderAttachmentsList;
    }

    public void setWorkOrderAttachmentsList(List<WorkOrderAttachments> workOrderAttachmentsList) {
        this.workOrderAttachmentsList = workOrderAttachmentsList;
    }

    @XmlTransient
    public List<WorkOrderRemark> getWorkOrderRemarkList() {
        return workOrderRemarkList;
    }

    public void setWorkOrderRemarkList(List<WorkOrderRemark> workOrderRemarkList) {
        this.workOrderRemarkList = workOrderRemarkList;
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
        if (!(object instanceof WorkOrder)) {
            return false;
        }
        WorkOrder other = (WorkOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.WorkOrder[ id=" + id + " ]";
    }

    public List<InventoryApproval> getInventoryApprovalList() {
        return inventoryApprovalList;
    }

    public void setInventoryApprovalList(List<InventoryApproval> inventoryApprovalList) {
        this.inventoryApprovalList = inventoryApprovalList;
    }

}
