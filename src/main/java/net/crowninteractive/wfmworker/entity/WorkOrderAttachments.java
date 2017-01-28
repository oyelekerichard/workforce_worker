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
@Table(name = "work_order_attachments", catalog = "wfm_new", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkOrderAttachments.findAll", query = "SELECT w FROM WorkOrderAttachments w"),
    @NamedQuery(name = "WorkOrderAttachments.findById", query = "SELECT w FROM WorkOrderAttachments w WHERE w.id = :id"),
    @NamedQuery(name = "WorkOrderAttachments.findByToken", query = "SELECT w FROM WorkOrderAttachments w WHERE w.token = :token"),
    @NamedQuery(name = "WorkOrderAttachments.findByOwnerId", query = "SELECT w FROM WorkOrderAttachments w WHERE w.ownerId = :ownerId"),
    @NamedQuery(name = "WorkOrderAttachments.findByFilename", query = "SELECT w FROM WorkOrderAttachments w WHERE w.filename = :filename"),
    @NamedQuery(name = "WorkOrderAttachments.findByDescription", query = "SELECT w FROM WorkOrderAttachments w WHERE w.description = :description"),
    @NamedQuery(name = "WorkOrderAttachments.findByCreateTime", query = "SELECT w FROM WorkOrderAttachments w WHERE w.createTime = :createTime"),
    @NamedQuery(name = "WorkOrderAttachments.findByCreatedBy", query = "SELECT w FROM WorkOrderAttachments w WHERE w.createdBy = :createdBy"),
    @NamedQuery(name = "WorkOrderAttachments.findByUpdateTime", query = "SELECT w FROM WorkOrderAttachments w WHERE w.updateTime = :updateTime"),
    @NamedQuery(name = "WorkOrderAttachments.findByUpdatedBy", query = "SELECT w FROM WorkOrderAttachments w WHERE w.updatedBy = :updatedBy"),
    @NamedQuery(name = "WorkOrderAttachments.findByIsActive", query = "SELECT w FROM WorkOrderAttachments w WHERE w.isActive = :isActive")})
public class WorkOrderAttachments implements Serializable {

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
    @Column(name = "filename", nullable = false, length = 100)
    private String filename;
    @Basic(optional = false)
    @Column(name = "description", nullable = false, length = 200)
    private String description;
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false)
    private int createdBy;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @Basic(optional = false)
    @Column(name = "is_active", nullable = false)
    private int isActive;
    @JoinColumn(name = "work_order_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private WorkOrder workOrderId;

    public WorkOrderAttachments() {
    }

    public WorkOrderAttachments(Integer id) {
        this.id = id;
    }

    public WorkOrderAttachments(Integer id, String token, int ownerId, String filename, String description, Date createTime, int createdBy, int isActive) {
        this.id = id;
        this.token = token;
        this.ownerId = ownerId;
        this.filename = filename;
        this.description = description;
        this.createTime = createTime;
        this.createdBy = createdBy;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
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
        if (!(object instanceof WorkOrderAttachments)) {
            return false;
        }
        WorkOrderAttachments other = (WorkOrderAttachments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.WorkOrderAttachments[ id=" + id + " ]";
    }
    
}
