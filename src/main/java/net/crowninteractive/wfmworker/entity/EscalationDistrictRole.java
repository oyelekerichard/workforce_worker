
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
    name      = "escalation_district_role",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "EscalationDistrictRole.findAll",
        query = "SELECT e FROM EscalationDistrictRole e"
    ) , @NamedQuery(
        name  = "EscalationDistrictRole.findById",
        query = "SELECT e FROM EscalationDistrictRole e WHERE e.id = :id"
    ) , @NamedQuery(
        name  = "EscalationDistrictRole.findByToken",
        query = "SELECT e FROM EscalationDistrictRole e WHERE e.token = :token"
    ) , @NamedQuery(
        name  = "EscalationDistrictRole.findByOwnerId",
        query = "SELECT e FROM EscalationDistrictRole e WHERE e.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "EscalationDistrictRole.findByEmails",
        query = "SELECT e FROM EscalationDistrictRole e WHERE e.emails = :emails"
    ) , @NamedQuery(
        name  = "EscalationDistrictRole.findByCreateTime",
        query = "SELECT e FROM EscalationDistrictRole e WHERE e.createTime = :createTime"
    ) , @NamedQuery(
        name  = "EscalationDistrictRole.findByIsActive",
        query = "SELECT e FROM EscalationDistrictRole e WHERE e.isActive = :isActive"
    ) , @NamedQuery(
        name  = "EscalationDistrictRole.findByUpdateTime",
        query = "SELECT e FROM EscalationDistrictRole e WHERE e.updateTime = :updateTime"
    )
})
public class EscalationDistrictRole implements Serializable {
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
        name     = "emails",
        nullable = false,
        length   = 2000
    )
    private String            emails;
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
        name                 = "role_id",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private EscalationRole    roleId;
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

    public EscalationDistrictRole() {}

    public EscalationDistrictRole(Integer id) {
        this.id = id;
    }

    public EscalationDistrictRole(Integer id, String token, int ownerId, String emails, Date createTime, int isActive) {
        this.id         = id;
        this.token      = token;
        this.ownerId    = ownerId;
        this.emails     = emails;
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

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
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

    public EscalationRole getRoleId() {
        return roleId;
    }

    public void setRoleId(EscalationRole roleId) {
        this.roleId = roleId;
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
        if (!(object instanceof EscalationDistrictRole)) {
            return false;
        }

        EscalationDistrictRole other = (EscalationDistrictRole) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EscalationDistrictRole[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
