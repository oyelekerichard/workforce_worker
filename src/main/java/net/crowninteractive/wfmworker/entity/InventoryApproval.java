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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "inventory_approval")
@NamedQueries({
    @NamedQuery(name = "InventoryApproval.findAll", query = "SELECT i FROM InventoryApproval i")
    , @NamedQuery(name = "InventoryApproval.findById", query = "SELECT i FROM InventoryApproval i WHERE i.id = :id")
    , @NamedQuery(name = "InventoryApproval.findByPriority", query = "SELECT i FROM InventoryApproval i WHERE i.priority = :priority")
    , @NamedQuery(name = "InventoryApproval.findByEmailSendTime", query = "SELECT i FROM InventoryApproval i WHERE i.emailSendTime = :emailSendTime")
    , @NamedQuery(name = "InventoryApproval.findByStatus", query = "SELECT i FROM InventoryApproval i WHERE i.status = :status")
    , @NamedQuery(name = "InventoryApproval.findByCreateTime", query = "SELECT i FROM InventoryApproval i WHERE i.createTime = :createTime")
    , @NamedQuery(name = "InventoryApproval.findByActionedTime", query = "SELECT i FROM InventoryApproval i WHERE i.actionedTime = :actionedTime")
    , @NamedQuery(name = "InventoryApproval.findByOwnerId", query = "SELECT i FROM InventoryApproval i WHERE i.ownerId = :ownerId")
    , @NamedQuery(name = "InventoryApproval.findByToken", query = "SELECT i FROM InventoryApproval i WHERE i.token = :token")
    , @NamedQuery(name = "InventoryApproval.findByActionedComment", query = "SELECT i FROM InventoryApproval i WHERE i.actionedComment = :actionedComment")
    , @NamedQuery(name = "InventoryApproval.findByUpdateTime", query = "SELECT i FROM InventoryApproval i WHERE i.updateTime = :updateTime")
    , @NamedQuery(name = "InventoryApproval.findByUpdatedBy", query = "SELECT i FROM InventoryApproval i WHERE i.updatedBy = :updatedBy")})
public class InventoryApproval implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "priority")
    private int priority;
    @Basic(optional = false)
    @Lob
    @Column(name = "recipients")
    private String recipients;
    @Column(name = "email_send_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emailSendTime;
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "actioned_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionedTime;
    @Basic(optional = false)
    @Column(name = "owner_id")
    private int ownerId;
    @Basic(optional = false)
    @Column(name = "token")
    private String token;
    @Column(name = "actioned_comment")
    private String actionedComment;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ApprovalLevel levelId;
    @JoinColumn(name = "inventory_req_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private InventoryRequest inventoryReqId;
    @JoinColumn(name = "actioned_by", referencedColumnName = "id")
    @ManyToOne
    private Users actionedBy;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users createdBy;
    @JoinColumn(name = "work_order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private WorkOrder workOrderId;

    public InventoryApproval() {
    }

    public InventoryApproval(Integer id) {
        this.id = id;
    }

    public InventoryApproval(Integer id, int priority, String recipients, Date createTime, int ownerId, String token) {
        this.id = id;
        this.priority = priority;
        this.recipients = recipients;
        this.createTime = createTime;
        this.ownerId = ownerId;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public Date getEmailSendTime() {
        return emailSendTime;
    }

    public void setEmailSendTime(Date emailSendTime) {
        this.emailSendTime = emailSendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getActionedTime() {
        return actionedTime;
    }

    public void setActionedTime(Date actionedTime) {
        this.actionedTime = actionedTime;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getActionedComment() {
        return actionedComment;
    }

    public void setActionedComment(String actionedComment) {
        this.actionedComment = actionedComment;
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

    public ApprovalLevel getLevelId() {
        return levelId;
    }

    public void setLevelId(ApprovalLevel levelId) {
        this.levelId = levelId;
    }

    public InventoryRequest getInventoryReqId() {
        return inventoryReqId;
    }

    public void setInventoryReqId(InventoryRequest inventoryReqId) {
        this.inventoryReqId = inventoryReqId;
    }

    public Users getActionedBy() {
        return actionedBy;
    }

    public void setActionedBy(Users actionedBy) {
        this.actionedBy = actionedBy;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventoryApproval)) {
            return false;
        }
        InventoryApproval other = (InventoryApproval) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.InventoryApproval[ id=" + id + " ]";
    }
    
}
