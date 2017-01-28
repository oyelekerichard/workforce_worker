/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author osita
 */
@Entity
@Table(name = "escalation_settings", catalog = "wfm_new", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EscalationSettings.findAll", query = "SELECT e FROM EscalationSettings e"),
    @NamedQuery(name = "EscalationSettings.findById", query = "SELECT e FROM EscalationSettings e WHERE e.id = :id"),
    @NamedQuery(name = "EscalationSettings.findByToken", query = "SELECT e FROM EscalationSettings e WHERE e.token = :token"),
    @NamedQuery(name = "EscalationSettings.findByOwnerId", query = "SELECT e FROM EscalationSettings e WHERE e.ownerId = :ownerId"),
    @NamedQuery(name = "EscalationSettings.findByLabel", query = "SELECT e FROM EscalationSettings e WHERE e.label = :label"),
    @NamedQuery(name = "EscalationSettings.findByTimeValue", query = "SELECT e FROM EscalationSettings e WHERE e.timeValue = :timeValue"),
    @NamedQuery(name = "EscalationSettings.findByTimeValueType", query = "SELECT e FROM EscalationSettings e WHERE e.timeValueType = :timeValueType"),
    @NamedQuery(name = "EscalationSettings.findByInformAssigner", query = "SELECT e FROM EscalationSettings e WHERE e.informAssigner = :informAssigner"),
    @NamedQuery(name = "EscalationSettings.findByPriority", query = "SELECT e FROM EscalationSettings e WHERE e.priority = :priority"),
    @NamedQuery(name = "EscalationSettings.findByRoles", query = "SELECT e FROM EscalationSettings e WHERE e.roles = :roles"),
    @NamedQuery(name = "EscalationSettings.findByCreateTime", query = "SELECT e FROM EscalationSettings e WHERE e.createTime = :createTime"),
    @NamedQuery(name = "EscalationSettings.findByIsActive", query = "SELECT e FROM EscalationSettings e WHERE e.isActive = :isActive"),
    @NamedQuery(name = "EscalationSettings.findByUpdateTime", query = "SELECT e FROM EscalationSettings e WHERE e.updateTime = :updateTime")})
public class EscalationSettings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "token", nullable = false, length = 30)
    private String token;
    @Basic(optional = false)
    @Column(name = "owner_id", nullable = false)
    private int ownerId;
    @Basic(optional = false)
    @Column(name = "label", nullable = false, length = 100)
    private String label;
    @Basic(optional = false)
    @Column(name = "time_value", nullable = false)
    private int timeValue;
    @Basic(optional = false)
    @Column(name = "time_value_type", nullable = false, length = 10)
    private String timeValueType;
    @Basic(optional = false)
    @Column(name = "inform_assigner", nullable = false)
    private boolean informAssigner;
    @Basic(optional = false)
    @Column(name = "priority", nullable = false)
    private short priority;
    @Basic(optional = false)
    @Column(name = "roles", nullable = false, length = 100)
    private String roles;
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Basic(optional = false)
    @Column(name = "is_active", nullable = false)
    private int isActive;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escalationSettingId")
    private List<EscalationWorkOrder> escalationWorkOrderList;
    @JoinColumn(name = "queue_type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private QueueType queueTypeId;
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Users createdBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @ManyToOne
    private Users updatedBy;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne
    private WorkOrderStatus statusId;

    public EscalationSettings() {
    }

    public EscalationSettings(Integer id) {
        this.id = id;
    }

    public EscalationSettings(Integer id, String token, int ownerId, String label, int timeValue, String timeValueType, boolean informAssigner, short priority, String roles, Date createTime, int isActive) {
        this.id = id;
        this.token = token;
        this.ownerId = ownerId;
        this.label = label;
        this.timeValue = timeValue;
        this.timeValueType = timeValueType;
        this.informAssigner = informAssigner;
        this.priority = priority;
        this.roles = roles;
        this.createTime = createTime;
        this.isActive = isActive;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(int timeValue) {
        this.timeValue = timeValue;
    }

    public String getTimeValueType() {
        return timeValueType;
    }

    public void setTimeValueType(String timeValueType) {
        this.timeValueType = timeValueType;
    }

    public boolean getInformAssigner() {
        return informAssigner;
    }

    public void setInformAssigner(boolean informAssigner) {
        this.informAssigner = informAssigner;
    }

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @XmlTransient
    public List<EscalationWorkOrder> getEscalationWorkOrderList() {
        return escalationWorkOrderList;
    }

    public void setEscalationWorkOrderList(List<EscalationWorkOrder> escalationWorkOrderList) {
        this.escalationWorkOrderList = escalationWorkOrderList;
    }

    public QueueType getQueueTypeId() {
        return queueTypeId;
    }

    public void setQueueTypeId(QueueType queueTypeId) {
        this.queueTypeId = queueTypeId;
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

    public WorkOrderStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(WorkOrderStatus statusId) {
        this.statusId = statusId;
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
        if (!(object instanceof EscalationSettings)) {
            return false;
        }
        EscalationSettings other = (EscalationSettings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EscalationSettings[ id=" + id + " ]";
    }
    
}
