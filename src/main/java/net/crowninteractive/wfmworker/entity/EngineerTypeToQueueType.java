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
@Table(name = "engineer_type_to_queue_type", catalog = "wfm_new", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EngineerTypeToQueueType.findAll", query = "SELECT e FROM EngineerTypeToQueueType e"),
    @NamedQuery(name = "EngineerTypeToQueueType.findById", query = "SELECT e FROM EngineerTypeToQueueType e WHERE e.id = :id"),
    @NamedQuery(name = "EngineerTypeToQueueType.findByToken", query = "SELECT e FROM EngineerTypeToQueueType e WHERE e.token = :token"),
    @NamedQuery(name = "EngineerTypeToQueueType.findByOwnerId", query = "SELECT e FROM EngineerTypeToQueueType e WHERE e.ownerId = :ownerId"),
    @NamedQuery(name = "EngineerTypeToQueueType.findByQueueTypeId", query = "SELECT e FROM EngineerTypeToQueueType e WHERE e.queueTypeId = :queueTypeId"),
    @NamedQuery(name = "EngineerTypeToQueueType.findByEngineerTypeId", query = "SELECT e FROM EngineerTypeToQueueType e WHERE e.engineerTypeId = :engineerTypeId"),
    @NamedQuery(name = "EngineerTypeToQueueType.findByCreateTime", query = "SELECT e FROM EngineerTypeToQueueType e WHERE e.createTime = :createTime"),
    @NamedQuery(name = "EngineerTypeToQueueType.findByUpdateTime", query = "SELECT e FROM EngineerTypeToQueueType e WHERE e.updateTime = :updateTime"),
    @NamedQuery(name = "EngineerTypeToQueueType.findByIsActive", query = "SELECT e FROM EngineerTypeToQueueType e WHERE e.isActive = :isActive")})
public class EngineerTypeToQueueType implements Serializable {

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
    @Column(name = "queue_type_id", nullable = false)
    private int queueTypeId;
    @Basic(optional = false)
    @Column(name = "engineer_type_id", nullable = false)
    private int engineerTypeId;
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Basic(optional = false)
    @Column(name = "is_active", nullable = false)
    private int isActive;
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Users createdBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @ManyToOne
    private Users updatedBy;

    public EngineerTypeToQueueType() {
    }

    public EngineerTypeToQueueType(Integer id) {
        this.id = id;
    }

    public EngineerTypeToQueueType(Integer id, String token, int ownerId, int queueTypeId, int engineerTypeId, Date createTime, int isActive) {
        this.id = id;
        this.token = token;
        this.ownerId = ownerId;
        this.queueTypeId = queueTypeId;
        this.engineerTypeId = engineerTypeId;
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

    public int getQueueTypeId() {
        return queueTypeId;
    }

    public void setQueueTypeId(int queueTypeId) {
        this.queueTypeId = queueTypeId;
    }

    public int getEngineerTypeId() {
        return engineerTypeId;
    }

    public void setEngineerTypeId(int engineerTypeId) {
        this.engineerTypeId = engineerTypeId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EngineerTypeToQueueType)) {
            return false;
        }
        EngineerTypeToQueueType other = (EngineerTypeToQueueType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EngineerTypeToQueueType[ id=" + id + " ]";
    }
    
}
