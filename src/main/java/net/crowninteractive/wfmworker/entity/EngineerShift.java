
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
    name      = "engineer_shift",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "EngineerShift.findAll",
        query = "SELECT e FROM EngineerShift e"
    ) , @NamedQuery(
        name  = "EngineerShift.findById",
        query = "SELECT e FROM EngineerShift e WHERE e.id = :id"
    ) , @NamedQuery(
        name  = "EngineerShift.findByToken",
        query = "SELECT e FROM EngineerShift e WHERE e.token = :token"
    ) , @NamedQuery(
        name  = "EngineerShift.findByOwnerId",
        query = "SELECT e FROM EngineerShift e WHERE e.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "EngineerShift.findByName",
        query = "SELECT e FROM EngineerShift e WHERE e.name = :name"
    ) , @NamedQuery(
        name  = "EngineerShift.findByStartTime",
        query = "SELECT e FROM EngineerShift e WHERE e.startTime = :startTime"
    ) , @NamedQuery(
        name  = "EngineerShift.findByEndTime",
        query = "SELECT e FROM EngineerShift e WHERE e.endTime = :endTime"
    ) , @NamedQuery(
        name  = "EngineerShift.findByShiftColor",
        query = "SELECT e FROM EngineerShift e WHERE e.shiftColor = :shiftColor"
    ) , @NamedQuery(
        name  = "EngineerShift.findByCreateTime",
        query = "SELECT e FROM EngineerShift e WHERE e.createTime = :createTime"
    ) , @NamedQuery(
        name  = "EngineerShift.findByUpdateTime",
        query = "SELECT e FROM EngineerShift e WHERE e.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "EngineerShift.findByIsActive",
        query = "SELECT e FROM EngineerShift e WHERE e.isActive = :isActive"
    )
})
public class EngineerShift implements Serializable {
    private static final long      serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(
        name                                        = "id",
        nullable                                    = false
    )
    private Integer                id;
    @Basic(optional = false)
    @Column(
        name     = "token",
        nullable = false,
        length   = 30
    )
    private String                 token;
    @Basic(optional = false)
    @Column(
        name     = "owner_id",
        nullable = false
    )
    private int                    ownerId;
    @Column(
        name   = "name",
        length = 40
    )
    private String                 name;
    @Basic(optional = false)
    @Column(
        name     = "start_time",
        nullable = false
    )
    @Temporal(TemporalType.TIME)
    private Date                   startTime;
    @Basic(optional = false)
    @Column(
        name     = "end_time",
        nullable = false
    )
    @Temporal(TemporalType.TIME)
    private Date                   endTime;
    @Basic(optional = false)
    @Column(
        name     = "shift_color",
        nullable = false,
        length   = 30
    )
    private String                 shiftColor;
    @Basic(optional = false)
    @Column(
        name     = "create_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date                   createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date                   updateTime;
    @Basic(optional = false)
    @Column(
        name     = "is_active",
        nullable = false
    )
    private int                    isActive;
    @JoinColumn(
        name                 = "created_by",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private Users                  createdBy;
    @JoinColumn(
        name                 = "updated_by",
        referencedColumnName = "id"
    )
    @ManyToOne
    private Users                  updatedBy;
    @OneToMany(mappedBy = "monday")
    private List<EngineerSchedule> engineerScheduleList;
    @OneToMany(mappedBy = "tuesday")
    private List<EngineerSchedule> engineerScheduleList1;
    @OneToMany(mappedBy = "wednesday")
    private List<EngineerSchedule> engineerScheduleList2;
    @OneToMany(mappedBy = "thursday")
    private List<EngineerSchedule> engineerScheduleList3;
    @OneToMany(mappedBy = "friday")
    private List<EngineerSchedule> engineerScheduleList4;
    @OneToMany(mappedBy = "saturday")
    private List<EngineerSchedule> engineerScheduleList5;
    @OneToMany(mappedBy = "sunday")
    private List<EngineerSchedule> engineerScheduleList6;

    public EngineerShift() {}

    public EngineerShift(Integer id) {
        this.id = id;
    }

    public EngineerShift(Integer id, String token, int ownerId, Date startTime, Date endTime, String shiftColor,
                         Date createTime, int isActive) {
        this.id         = id;
        this.token      = token;
        this.ownerId    = ownerId;
        this.startTime  = startTime;
        this.endTime    = endTime;
        this.shiftColor = shiftColor;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getShiftColor() {
        return shiftColor;
    }

    public void setShiftColor(String shiftColor) {
        this.shiftColor = shiftColor;
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

    @XmlTransient
    public List<EngineerSchedule> getEngineerScheduleList() {
        return engineerScheduleList;
    }

    public void setEngineerScheduleList(List<EngineerSchedule> engineerScheduleList) {
        this.engineerScheduleList = engineerScheduleList;
    }

    @XmlTransient
    public List<EngineerSchedule> getEngineerScheduleList1() {
        return engineerScheduleList1;
    }

    public void setEngineerScheduleList1(List<EngineerSchedule> engineerScheduleList1) {
        this.engineerScheduleList1 = engineerScheduleList1;
    }

    @XmlTransient
    public List<EngineerSchedule> getEngineerScheduleList2() {
        return engineerScheduleList2;
    }

    public void setEngineerScheduleList2(List<EngineerSchedule> engineerScheduleList2) {
        this.engineerScheduleList2 = engineerScheduleList2;
    }

    @XmlTransient
    public List<EngineerSchedule> getEngineerScheduleList3() {
        return engineerScheduleList3;
    }

    public void setEngineerScheduleList3(List<EngineerSchedule> engineerScheduleList3) {
        this.engineerScheduleList3 = engineerScheduleList3;
    }

    @XmlTransient
    public List<EngineerSchedule> getEngineerScheduleList4() {
        return engineerScheduleList4;
    }

    public void setEngineerScheduleList4(List<EngineerSchedule> engineerScheduleList4) {
        this.engineerScheduleList4 = engineerScheduleList4;
    }

    @XmlTransient
    public List<EngineerSchedule> getEngineerScheduleList5() {
        return engineerScheduleList5;
    }

    public void setEngineerScheduleList5(List<EngineerSchedule> engineerScheduleList5) {
        this.engineerScheduleList5 = engineerScheduleList5;
    }

    @XmlTransient
    public List<EngineerSchedule> getEngineerScheduleList6() {
        return engineerScheduleList6;
    }

    public void setEngineerScheduleList6(List<EngineerSchedule> engineerScheduleList6) {
        this.engineerScheduleList6 = engineerScheduleList6;
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
        if (!(object instanceof EngineerShift)) {
            return false;
        }

        EngineerShift other = (EngineerShift) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EngineerShift[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
