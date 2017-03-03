
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
    name      = "work_order_update",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "WorkOrderUpdate.findAll",
        query = "SELECT w FROM WorkOrderUpdate w"
    ) , @NamedQuery(
        name  = "WorkOrderUpdate.findById",
        query = "SELECT w FROM WorkOrderUpdate w WHERE w.id = :id"
    ) , @NamedQuery(
        name  = "WorkOrderUpdate.findByToken",
        query = "SELECT w FROM WorkOrderUpdate w WHERE w.token = :token"
    ) , @NamedQuery(
        name  = "WorkOrderUpdate.findByOwnerId",
        query = "SELECT w FROM WorkOrderUpdate w WHERE w.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "WorkOrderUpdate.findByComment",
        query = "SELECT w FROM WorkOrderUpdate w WHERE w.comment = :comment"
    ) , @NamedQuery(
        name  = "WorkOrderUpdate.findByCreateTime",
        query = "SELECT w FROM WorkOrderUpdate w WHERE w.createTime = :createTime"
    ) , @NamedQuery(
        name  = "WorkOrderUpdate.findByUpdateTime",
        query = "SELECT w FROM WorkOrderUpdate w WHERE w.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "WorkOrderUpdate.findByIsActive",
        query = "SELECT w FROM WorkOrderUpdate w WHERE w.isActive = :isActive"
    )
})
public class WorkOrderUpdate implements Serializable {
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
    @Column(
        name   = "comment",
        length = 500
    )
    private String            comment;
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
        name                 = "updated_by",
        referencedColumnName = "id"
    )
    @ManyToOne
    private Users             updatedBy;
    @JoinColumn(
        name                 = "created_by",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private Users             createdBy;
    @JoinColumn(
        name                 = "work_order_id",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private WorkOrder         workOrderId;
    @JoinColumn(
        name                 = "work_order_status_id",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private WorkOrderStatus   workOrderStatusId;

    public WorkOrderUpdate() {}

    public WorkOrderUpdate(Integer id) {
        this.id = id;
    }

    public WorkOrderUpdate(Integer id, String token, int ownerId, Date createTime, int isActive) {
        this.id         = id;
        this.token      = token;
        this.ownerId    = ownerId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Users getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Users updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public WorkOrder getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(WorkOrder workOrderId) {
        this.workOrderId = workOrderId;
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
        if (!(object instanceof WorkOrderUpdate)) {
            return false;
        }

        WorkOrderUpdate other = (WorkOrderUpdate) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.WorkOrderUpdate[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
