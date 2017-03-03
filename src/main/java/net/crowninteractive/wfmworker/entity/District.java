
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

//~--- JDK imports ------------------------------------------------------------

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
@Table(
    name      = "district",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "District.findAll",
        query = "SELECT d FROM District d"
    ) , @NamedQuery(
        name  = "District.findById",
        query = "SELECT d FROM District d WHERE d.id = :id"
    ) , @NamedQuery(
        name  = "District.findByToken",
        query = "SELECT d FROM District d WHERE d.token = :token"
    ) , @NamedQuery(
        name  = "District.findByOwnerId",
        query = "SELECT d FROM District d WHERE d.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "District.findByName",
        query = "SELECT d FROM District d WHERE d.name = :name"
    ) , @NamedQuery(
        name  = "District.findByDescription",
        query = "SELECT d FROM District d WHERE d.description = :description"
    ) , @NamedQuery(
        name  = "District.findByCreateTime",
        query = "SELECT d FROM District d WHERE d.createTime = :createTime"
    ) , @NamedQuery(
        name  = "District.findByCreatedBy",
        query = "SELECT d FROM District d WHERE d.createdBy = :createdBy"
    ) , @NamedQuery(
        name  = "District.findByIsActive",
        query = "SELECT d FROM District d WHERE d.isActive = :isActive"
    ) , @NamedQuery(
        name  = "District.findByUpdateTime",
        query = "SELECT d FROM District d WHERE d.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "District.findByUpdatedBy",
        query = "SELECT d FROM District d WHERE d.updatedBy = :updatedBy"
    )
})
public class District implements Serializable {
    private static final long            serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(
        name                                              = "id",
        nullable                                          = false
    )
    private Integer                      id;
    @Basic(optional = false)
    @Column(
        name     = "token",
        nullable = false,
        length   = 30
    )
    private String                       token;
    @Basic(optional = false)
    @Column(
        name     = "owner_id",
        nullable = false
    )
    private int                          ownerId;
    @Column(
        name   = "name",
        length = 40
    )
    private String                       name;
    @Basic(optional = false)
    @Column(
        name     = "description",
        nullable = false,
        length   = 400
    )
    private String                       description;
    @Basic(optional = false)
    @Column(
        name     = "create_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date                         createTime;
    @Basic(optional = false)
    @Column(
        name     = "created_by",
        nullable = false
    )
    private int                          createdBy;
    @Basic(optional = false)
    @Column(
        name     = "is_active",
        nullable = false
    )
    private int                          isActive;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date                         updateTime;
    @Column(name = "updated_by")
    private Integer                      updatedBy;
    @OneToMany(
        cascade  = CascadeType.ALL,
        mappedBy = "districtId"
    )
    private List<Feeder>                 feederList;
    @OneToMany(
        cascade  = CascadeType.ALL,
        mappedBy = "districtId"
    )
    private List<EscalationDistrictRole> escalationDistrictRoleList;
    @OneToMany(
        cascade  = CascadeType.ALL,
        mappedBy = "districtId"
    )
    private List<Zone>                   zoneList;

    public District() {}

    public District(Integer id) {
        this.id = id;
    }

    public District(Integer id, String token, int ownerId, String description, Date createTime, int createdBy,
                    int isActive) {
        this.id          = id;
        this.token       = token;
        this.ownerId     = ownerId;
        this.description = description;
        this.createTime  = createTime;
        this.createdBy   = createdBy;
        this.isActive    = isActive;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @XmlTransient
    public List<Feeder> getFeederList() {
        return feederList;
    }

    public void setFeederList(List<Feeder> feederList) {
        this.feederList = feederList;
    }

    @XmlTransient
    public List<EscalationDistrictRole> getEscalationDistrictRoleList() {
        return escalationDistrictRoleList;
    }

    public void setEscalationDistrictRoleList(List<EscalationDistrictRole> escalationDistrictRoleList) {
        this.escalationDistrictRoleList = escalationDistrictRoleList;
    }

    @XmlTransient
    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
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
        if (!(object instanceof District)) {
            return false;
        }

        District other = (District) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.District[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
