
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
    name      = "zone",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "Zone.findAll",
        query = "SELECT z FROM Zone z"
    ) , @NamedQuery(
        name  = "Zone.findById",
        query = "SELECT z FROM Zone z WHERE z.id = :id"
    ) , @NamedQuery(
        name  = "Zone.findByToken",
        query = "SELECT z FROM Zone z WHERE z.token = :token"
    ) , @NamedQuery(
        name  = "Zone.findByOwnerId",
        query = "SELECT z FROM Zone z WHERE z.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "Zone.findByName",
        query = "SELECT z FROM Zone z WHERE z.name = :name"
    ) , @NamedQuery(
        name  = "Zone.findByDescription",
        query = "SELECT z FROM Zone z WHERE z.description = :description"
    ) , @NamedQuery(
        name  = "Zone.findByCreateTime",
        query = "SELECT z FROM Zone z WHERE z.createTime = :createTime"
    ) , @NamedQuery(
        name  = "Zone.findByIsActive",
        query = "SELECT z FROM Zone z WHERE z.isActive = :isActive"
    ) , @NamedQuery(
        name  = "Zone.findByUpdateTime",
        query = "SELECT z FROM Zone z WHERE z.updateTime = :updateTime"
    )
})
public class Zone implements Serializable {
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
        name   = "name",
        length = 40
    )
    private String            name;
    @Basic(optional = false)
    @Column(
        name     = "description",
        nullable = false,
        length   = 400
    )
    private String            description;
    @Basic(optional = false)
    @Column(
        name     = "create_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date              createTime;
    @Basic(optional = false)
    @Column(
        name     = "is_active",
        nullable = false
    )
    private int               isActive;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              updateTime;
    @JoinColumn(
        name                 = "district_id",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private District          districtId;
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

    public Zone() {}

    public Zone(Integer id) {
        this.id = id;
    }

    public Zone(Integer id, String token, int ownerId, String description, Date createTime, int isActive) {
        this.id          = id;
        this.token       = token;
        this.ownerId     = ownerId;
        this.description = description;
        this.createTime  = createTime;
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

    public District getDistrictId() {
        return districtId;
    }

    public void setDistrictId(District districtId) {
        this.districtId = districtId;
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

        hash += ((id != null)
                 ? id.hashCode()
                 : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zone)) {
            return false;
        }

        Zone other = (Zone) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.Zone[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
