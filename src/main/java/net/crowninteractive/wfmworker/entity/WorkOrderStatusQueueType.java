
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author osita
 */
@Entity
@Table(
    name      = "work_order_status_queue_type",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "WorkOrderStatusQueueType.findAll",
        query = "SELECT w FROM WorkOrderStatusQueueType w"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findById",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.id = :id"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByToken",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.token = :token"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByOwnerId",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByPriority",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.priority = :priority"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByShowToResource",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.showToResource = :showToResource"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByTriggerName",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.triggerName = :triggerName"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findBySummary",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.summary = :summary"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByMessage",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.message = :message"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByNextQueueType",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.nextQueueType = :nextQueueType"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByCreateTime",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.createTime = :createTime"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByUpdateTime",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "WorkOrderStatusQueueType.findByIsActive",
        query = "SELECT w FROM WorkOrderStatusQueueType w WHERE w.isActive = :isActive"
    )
})
public class WorkOrderStatusQueueType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(
        name                                   = "id",
        nullable                               = false
    )
    private Integer           id;
    @Basic(optional = false)
    @Column(
        name     = "token",
        nullable = false,
        length   = 30
    )
    private String            token;
    @Basic(optional = false)
    @Column(
        name     = "owner_id",
        nullable = false
    )
    private int               ownerId;
    @Basic(optional = false)
    @Column(
        name     = "priority",
        nullable = false
    )
    private short             priority;
    @Column(name = "show_to_resource")
    private Short             showToResource;
    @Column(
        name   = "trigger_name",
        length = 50
    )
    private String            triggerName;
    @Column(
        name   = "summary",
        length = 50
    )
    private String            summary;
    @Column(
        name   = "message",
        length = 400
    )
    private String            message;
    @Column(name = "next_queue_type")
    private Integer           nextQueueType;
    @Basic(optional = false)
    @Column(
        name     = "create_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date              createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              updateTime;
    @Basic(optional = false)
    @Column(
        name     = "is_active",
        nullable = false
    )
    private int               isActive;
    @JoinColumn(
        name                 = "queue_type_id",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private QueueType         queueTypeId;
    @JoinColumn(
        name                 = "created_by",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private Users             createdBy;
    @JoinColumn(
        name                 = "updated_by",
        referencedColumnName = "id"
    )
    @ManyToOne
    private Users             updatedBy;
    @JoinColumn(
        name                 = "work_order_status_id",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private WorkOrderStatus   workOrderStatusId;

    public WorkOrderStatusQueueType() {}

    public WorkOrderStatusQueueType(Integer id) {
        this.id = id;
    }

    public WorkOrderStatusQueueType(Integer id, String token, int ownerId, short priority, Date createTime,
                                    int isActive) {
        this.id         = id;
        this.token      = token;
        this.ownerId    = ownerId;
        this.priority   = priority;
        this.createTime = createTime;
        this.isActive   = isActive;
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

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public Short getShowToResource() {
        return showToResource;
    }

    public void setShowToResource(Short showToResource) {
        this.showToResource = showToResource;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNextQueueType() {
        return nextQueueType;
    }

    public void setNextQueueType(Integer nextQueueType) {
        this.nextQueueType = nextQueueType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
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

    public WorkOrderStatus getWorkOrderStatusId() {
        return workOrderStatusId;
    }

    public void setWorkOrderStatusId(WorkOrderStatus workOrderStatusId) {
        this.workOrderStatusId = workOrderStatusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash += ((id != null)
                 ? id.hashCode()
                 : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkOrderStatusQueueType)) {
            return false;
        }

        WorkOrderStatusQueueType other = (WorkOrderStatusQueueType) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.WorkOrderStatusQueueType[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
