
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
    name      = "audit",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "Audit.findAll",
        query = "SELECT a FROM Audit a"
    ) , @NamedQuery(
        name  = "Audit.findById",
        query = "SELECT a FROM Audit a WHERE a.id = :id"
    ) , @NamedQuery(
        name  = "Audit.findByParams",
        query = "SELECT a FROM Audit a WHERE a.params = :params"
    ) , @NamedQuery(
        name  = "Audit.findByDescription",
        query = "SELECT a FROM Audit a WHERE a.description = :description"
    ) , @NamedQuery(
        name  = "Audit.findByRecordType",
        query = "SELECT a FROM Audit a WHERE a.recordType = :recordType"
    ) , @NamedQuery(
        name  = "Audit.findByCreateTime",
        query = "SELECT a FROM Audit a WHERE a.createTime = :createTime"
    ) , @NamedQuery(
        name  = "Audit.findByCreatedBy",
        query = "SELECT a FROM Audit a WHERE a.createdBy = :createdBy"
    ) , @NamedQuery(
        name  = "Audit.findByUpdateTime",
        query = "SELECT a FROM Audit a WHERE a.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "Audit.findByUpdatedBy",
        query = "SELECT a FROM Audit a WHERE a.updatedBy = :updatedBy"
    ) , @NamedQuery(
        name  = "Audit.findByIsActive",
        query = "SELECT a FROM Audit a WHERE a.isActive = :isActive"
    )
})
public class Audit implements Serializable {
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
        name     = "params",
        nullable = false,
        length   = 1000
    )
    private String            params;
    @Basic(optional = false)
    @Column(
        name     = "description",
        nullable = false,
        length   = 100
    )
    private String            description;
    @Basic(optional = false)
    @Column(
        name     = "record_type",
        nullable = false,
        length   = 30
    )
    private String            recordType;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              createTime;
    @Column(name = "created_by")
    private Integer           createdBy;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              updateTime;
    @Column(name = "updated_by")
    private Integer           updatedBy;
    @Basic(optional = false)
    @Column(
        name     = "is_active",
        nullable = false
    )
    private short             isActive;

    public Audit() {}

    public Audit(Integer id) {
        this.id = id;
    }

    public Audit(Integer id, String params, String description, String recordType, short isActive) {
        this.id          = id;
        this.params      = params;
        this.description = description;
        this.recordType  = recordType;
        this.isActive    = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
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

    public short getIsActive() {
        return isActive;
    }

    public void setIsActive(short isActive) {
        this.isActive = isActive;
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
        if (!(object instanceof Audit)) {
            return false;
        }

        Audit other = (Audit) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.Audit[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
