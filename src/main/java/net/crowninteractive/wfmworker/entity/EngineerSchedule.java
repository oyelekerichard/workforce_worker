
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    name      = "engineer_schedule",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "EngineerSchedule.findAll",
        query = "SELECT e FROM EngineerSchedule e"
    ) , @NamedQuery(
        name  = "EngineerSchedule.findById",
        query = "SELECT e FROM EngineerSchedule e WHERE e.id = :id"
    ) , @NamedQuery(
        name  = "EngineerSchedule.findByToken",
        query = "SELECT e FROM EngineerSchedule e WHERE e.token = :token"
    ) , @NamedQuery(
        name  = "EngineerSchedule.findByOwnerId",
        query = "SELECT e FROM EngineerSchedule e WHERE e.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "EngineerSchedule.findByName",
        query = "SELECT e FROM EngineerSchedule e WHERE e.name = :name"
    ) , @NamedQuery(
        name  = "EngineerSchedule.findByCreateTime",
        query = "SELECT e FROM EngineerSchedule e WHERE e.createTime = :createTime"
    ) , @NamedQuery(
        name  = "EngineerSchedule.findByUpdateTime",
        query = "SELECT e FROM EngineerSchedule e WHERE e.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "EngineerSchedule.findByIsActive",
        query = "SELECT e FROM EngineerSchedule e WHERE e.isActive = :isActive"
    )
})
public class EngineerSchedule implements Serializable {
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
        name                 = "monday",
        referencedColumnName = "id"
    )
    @ManyToOne
    private EngineerShift     monday;
    @JoinColumn(
        name                 = "tuesday",
        referencedColumnName = "id"
    )
    @ManyToOne
    private EngineerShift     tuesday;
    @JoinColumn(
        name                 = "wednesday",
        referencedColumnName = "id"
    )
    @ManyToOne
    private EngineerShift     wednesday;
    @JoinColumn(
        name                 = "thursday",
        referencedColumnName = "id"
    )
    @ManyToOne
    private EngineerShift     thursday;
    @JoinColumn(
        name                 = "friday",
        referencedColumnName = "id"
    )
    @ManyToOne
    private EngineerShift     friday;
    @JoinColumn(
        name                 = "saturday",
        referencedColumnName = "id"
    )
    @ManyToOne
    private EngineerShift     saturday;
    @JoinColumn(
        name                 = "sunday",
        referencedColumnName = "id"
    )
    @ManyToOne
    private EngineerShift     sunday;
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
    @OneToMany(mappedBy = "engSchedule")
    private List<Engineer>    engineerList;

    public EngineerSchedule() {}

    public EngineerSchedule(Integer id) {
        this.id = id;
    }

    public EngineerSchedule(Integer id, String token, int ownerId, Date createTime, int isActive) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public EngineerShift getMonday() {
        return monday;
    }

    public void setMonday(EngineerShift monday) {
        this.monday = monday;
    }

    public EngineerShift getTuesday() {
        return tuesday;
    }

    public void setTuesday(EngineerShift tuesday) {
        this.tuesday = tuesday;
    }

    public EngineerShift getWednesday() {
        return wednesday;
    }

    public void setWednesday(EngineerShift wednesday) {
        this.wednesday = wednesday;
    }

    public EngineerShift getThursday() {
        return thursday;
    }

    public void setThursday(EngineerShift thursday) {
        this.thursday = thursday;
    }

    public EngineerShift getFriday() {
        return friday;
    }

    public void setFriday(EngineerShift friday) {
        this.friday = friday;
    }

    public EngineerShift getSaturday() {
        return saturday;
    }

    public void setSaturday(EngineerShift saturday) {
        this.saturday = saturday;
    }

    public EngineerShift getSunday() {
        return sunday;
    }

    public void setSunday(EngineerShift sunday) {
        this.sunday = sunday;
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

    @XmlTransient
    public List<Engineer> getEngineerList() {
        return engineerList;
    }

    public void setEngineerList(List<Engineer> engineerList) {
        this.engineerList = engineerList;
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
        if (!(object instanceof EngineerSchedule)) {
            return false;
        }

        EngineerSchedule other = (EngineerSchedule) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EngineerSchedule[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
