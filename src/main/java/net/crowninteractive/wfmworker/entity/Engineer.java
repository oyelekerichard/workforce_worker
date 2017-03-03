
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
    name      = "engineer",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "Engineer.findAll",
        query = "SELECT e FROM Engineer e"
    ) , @NamedQuery(
        name  = "Engineer.findById",
        query = "SELECT e FROM Engineer e WHERE e.id = :id"
    ) , @NamedQuery(
        name  = "Engineer.findByToken",
        query = "SELECT e FROM Engineer e WHERE e.token = :token"
    ) , @NamedQuery(
        name  = "Engineer.findByOwnerId",
        query = "SELECT e FROM Engineer e WHERE e.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "Engineer.findByCity",
        query = "SELECT e FROM Engineer e WHERE e.city = :city"
    ) , @NamedQuery(
        name  = "Engineer.findByState",
        query = "SELECT e FROM Engineer e WHERE e.state = :state"
    ) , @NamedQuery(
        name  = "Engineer.findByAddress1",
        query = "SELECT e FROM Engineer e WHERE e.address1 = :address1"
    ) , @NamedQuery(
        name  = "Engineer.findByAddress2",
        query = "SELECT e FROM Engineer e WHERE e.address2 = :address2"
    ) , @NamedQuery(
        name  = "Engineer.findByBusinessDistrict",
        query = "SELECT e FROM Engineer e WHERE e.businessDistrict = :businessDistrict"
    ) , @NamedQuery(
        name  = "Engineer.findByNextOfKin",
        query = "SELECT e FROM Engineer e WHERE e.nextOfKin = :nextOfKin"
    ) , @NamedQuery(
        name  = "Engineer.findByDateJoined",
        query = "SELECT e FROM Engineer e WHERE e.dateJoined = :dateJoined"
    ) , @NamedQuery(
        name  = "Engineer.findByNextOfKinPhone",
        query = "SELECT e FROM Engineer e WHERE e.nextOfKinPhone = :nextOfKinPhone"
    ) , @NamedQuery(
        name  = "Engineer.findBySuspendReason",
        query = "SELECT e FROM Engineer e WHERE e.suspendReason = :suspendReason"
    ) , @NamedQuery(
        name  = "Engineer.findByCreateTime",
        query = "SELECT e FROM Engineer e WHERE e.createTime = :createTime"
    ) , @NamedQuery(
        name  = "Engineer.findByUpdateTime",
        query = "SELECT e FROM Engineer e WHERE e.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "Engineer.findByIsActive",
        query = "SELECT e FROM Engineer e WHERE e.isActive = :isActive"
    )
})
public class Engineer implements Serializable {
    private static final long         serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(
        name                                           = "id",
        nullable                                       = false
    )
    private Integer                   id;
    @Basic(optional = false)
    @Column(
        name     = "token",
        nullable = false,
        length   = 30
    )
    private String                    token;
    @Basic(optional = false)
    @Column(
        name     = "owner_id",
        nullable = false
    )
    private int                       ownerId;
    @Basic(optional = false)
    @Column(
        name     = "city",
        nullable = false,
        length   = 30
    )
    private String                    city;
    @Basic(optional = false)
    @Column(
        name     = "state",
        nullable = false,
        length   = 30
    )
    private String                    state;
    @Basic(optional = false)
    @Column(
        name     = "address1",
        nullable = false,
        length   = 100
    )
    private String                    address1;
    @Basic(optional = false)
    @Column(
        name     = "address2",
        nullable = false,
        length   = 100
    )
    private String                    address2;
    @Column(
        name   = "business_district",
        length = 100
    )
    private String                    businessDistrict;
    @Basic(optional = false)
    @Column(
        name     = "next_of_kin",
        nullable = false,
        length   = 100
    )
    private String                    nextOfKin;
    @Basic(optional = false)
    @Column(
        name     = "date_joined",
        nullable = false
    )
    @Temporal(TemporalType.DATE)
    private Date                      dateJoined;
    @Basic(optional = false)
    @Column(
        name     = "next_of_kin_phone",
        nullable = false,
        length   = 15
    )
    private String                    nextOfKinPhone;
    @Basic(optional = false)
    @Column(
        name     = "suspend_reason",
        nullable = false,
        length   = 200
    )
    private String                    suspendReason;
    @Basic(optional = false)
    @Column(
        name     = "create_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date                      createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date                      updateTime;
    @Basic(optional = false)
    @Column(
        name     = "is_active",
        nullable = false
    )
    private int                       isActive;
    @OneToMany(
        cascade  = CascadeType.ALL,
        mappedBy = "engineerId"
    )
    private List<EngineerAttachments> engineerAttachmentsList;
    @OneToMany(
        cascade  = CascadeType.ALL,
        mappedBy = "engineerId"
    )
    private List<EngineerToWorkOrder> engineerToWorkOrderList;
    @OneToMany(mappedBy = "engineerId")
    private List<WorkOrder>           workOrderList;
    @JoinColumn(
        name                 = "eng_level",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private EngineerLevel             engLevel;
    @JoinColumn(
        name                 = "eng_schedule",
        referencedColumnName = "id"
    )
    @ManyToOne
    private EngineerSchedule          engSchedule;
    @JoinColumn(
        name                 = "eng_type",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private EngineerType              engType;
    @JoinColumn(
        name                 = "team_id",
        referencedColumnName = "id"
    )
    @ManyToOne
    private Team                      teamId;
    @JoinColumn(
        name                 = "created_by",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private Users                     createdBy;
    @JoinColumn(
        name                 = "updated_by",
        referencedColumnName = "id"
    )
    @ManyToOne
    private Users                     updatedBy;
    @JoinColumn(
        name                 = "user_id",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private Users                     userId;

    public Engineer() {}

    public Engineer(Integer id) {
        this.id = id;
    }

    public Engineer(Integer id, String token, int ownerId, String city, String state, String address1, String address2,
                    String nextOfKin, Date dateJoined, String nextOfKinPhone, String suspendReason, Date createTime,
                    int isActive) {
        this.id             = id;
        this.token          = token;
        this.ownerId        = ownerId;
        this.city           = city;
        this.state          = state;
        this.address1       = address1;
        this.address2       = address2;
        this.nextOfKin      = nextOfKin;
        this.dateJoined     = dateJoined;
        this.nextOfKinPhone = nextOfKinPhone;
        this.suspendReason  = suspendReason;
        this.createTime     = createTime;
        this.isActive       = isActive;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getBusinessDistrict() {
        return businessDistrict;
    }

    public void setBusinessDistrict(String businessDistrict) {
        this.businessDistrict = businessDistrict;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getNextOfKinPhone() {
        return nextOfKinPhone;
    }

    public void setNextOfKinPhone(String nextOfKinPhone) {
        this.nextOfKinPhone = nextOfKinPhone;
    }

    public String getSuspendReason() {
        return suspendReason;
    }

    public void setSuspendReason(String suspendReason) {
        this.suspendReason = suspendReason;
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

    @XmlTransient
    public List<EngineerAttachments> getEngineerAttachmentsList() {
        return engineerAttachmentsList;
    }

    public void setEngineerAttachmentsList(List<EngineerAttachments> engineerAttachmentsList) {
        this.engineerAttachmentsList = engineerAttachmentsList;
    }

    @XmlTransient
    public List<EngineerToWorkOrder> getEngineerToWorkOrderList() {
        return engineerToWorkOrderList;
    }

    public void setEngineerToWorkOrderList(List<EngineerToWorkOrder> engineerToWorkOrderList) {
        this.engineerToWorkOrderList = engineerToWorkOrderList;
    }

    @XmlTransient
    public List<WorkOrder> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<WorkOrder> workOrderList) {
        this.workOrderList = workOrderList;
    }

    public EngineerLevel getEngLevel() {
        return engLevel;
    }

    public void setEngLevel(EngineerLevel engLevel) {
        this.engLevel = engLevel;
    }

    public EngineerSchedule getEngSchedule() {
        return engSchedule;
    }

    public void setEngSchedule(EngineerSchedule engSchedule) {
        this.engSchedule = engSchedule;
    }

    public EngineerType getEngType() {
        return engType;
    }

    public void setEngType(EngineerType engType) {
        this.engType = engType;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
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

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof Engineer)) {
            return false;
        }

        Engineer other = (Engineer) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.Engineer[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
