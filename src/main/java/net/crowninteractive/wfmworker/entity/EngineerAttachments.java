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
@Table(name = "engineer_attachments", catalog = "wfm_new", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EngineerAttachments.findAll", query = "SELECT e FROM EngineerAttachments e"),
    @NamedQuery(name = "EngineerAttachments.findById", query = "SELECT e FROM EngineerAttachments e WHERE e.id = :id"),
    @NamedQuery(name = "EngineerAttachments.findByToken", query = "SELECT e FROM EngineerAttachments e WHERE e.token = :token"),
    @NamedQuery(name = "EngineerAttachments.findByOwnerId", query = "SELECT e FROM EngineerAttachments e WHERE e.ownerId = :ownerId"),
    @NamedQuery(name = "EngineerAttachments.findByFilename", query = "SELECT e FROM EngineerAttachments e WHERE e.filename = :filename"),
    @NamedQuery(name = "EngineerAttachments.findByDescription", query = "SELECT e FROM EngineerAttachments e WHERE e.description = :description"),
    @NamedQuery(name = "EngineerAttachments.findByCreateTime", query = "SELECT e FROM EngineerAttachments e WHERE e.createTime = :createTime"),
    @NamedQuery(name = "EngineerAttachments.findByUpdateTime", query = "SELECT e FROM EngineerAttachments e WHERE e.updateTime = :updateTime"),
    @NamedQuery(name = "EngineerAttachments.findByIsActive", query = "SELECT e FROM EngineerAttachments e WHERE e.isActive = :isActive")})
public class EngineerAttachments implements Serializable {

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
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Basic(optional = false)
    @Column(name = "is_active", nullable = false)
    private int isActive;
    @JoinColumn(name = "engineer_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Engineer engineerId;
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Users createdBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @ManyToOne
    private Users updatedBy;

    public EngineerAttachments() {
    }

    public EngineerAttachments(Integer id) {
        this.id = id;
    }

    public EngineerAttachments(Integer id, String token, int ownerId, String filename, String description, Date createTime, int isActive) {
        this.id = id;
        this.token = token;
        this.ownerId = ownerId;
        this.filename = filename;
        this.description = description;
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

    public Engineer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Engineer engineerId) {
        this.engineerId = engineerId;
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
        if (!(object instanceof EngineerAttachments)) {
            return false;
        }
        EngineerAttachments other = (EngineerAttachments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EngineerAttachments[ id=" + id + " ]";
    }
    
}
