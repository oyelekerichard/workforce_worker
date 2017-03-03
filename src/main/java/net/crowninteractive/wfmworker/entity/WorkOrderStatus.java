
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
    name      = "work_order_status",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "WorkOrderStatus.findAll",
        query = "SELECT w FROM WorkOrderStatus w"
    ) , @NamedQuery(
        name  = "WorkOrderStatus.findById",
        query = "SELECT w FROM WorkOrderStatus w WHERE w.id = :id"
    ) , @NamedQuery(
        name  = "WorkOrderStatus.findByToken",
        query = "SELECT w FROM WorkOrderStatus w WHERE w.token = :token"
    ) , @NamedQuery(
        name  = "WorkOrderStatus.findByOwnerId",
        query = "SELECT w FROM WorkOrderStatus w WHERE w.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "WorkOrderStatus.findByName",
        query = "SELECT w FROM WorkOrderStatus w WHERE w.name = :name"
    ) , @NamedQuery(
        name  = "WorkOrderStatus.findByDescription",
        query = "SELECT w FROM WorkOrderStatus w WHERE w.description = :description"
    ) , @NamedQuery(
        name  = "WorkOrderStatus.findByCreateTime",
        query = "SELECT w FROM WorkOrderStatus w WHERE w.createTime = :createTime"
    ) , @NamedQuery(
        name  = "WorkOrderStatus.findByUpdateTime",
        query = "SELECT w FROM WorkOrderStatus w WHERE w.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "WorkOrderStatus.findByIsActive",
        query = "SELECT w FROM WorkOrderStatus w WHERE w.isActive = :isActive"
    )
})
public class WorkOrderStatus implements Serializable {
    private static final long              serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(
        name                                                = "id",
        nullable                                            = false
    )
    private Integer                        id;
    @Basic(optional = false)
    @Column(
        name     = "token",
        nullable = false,
        length   = 30
    )
    private String                         token;
    @Basic(optional = false)
    @Column(
        name     = "owner_id",
        nullable = false
    )
    private int                            ownerId;
    @Column(
        name   = "name",
        length = 40
    )
    private String                         name;
    @Basic(optional = false)
    @Column(
        name     = "description",
        nullable = false,
        length   = 400
    )
    private String                         description;
    @Basic(optional = false)
    @Column(
        name     = "create_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date                           createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date                           updateTime;
    @Basic(optional = false)
    @Column(
        name     = "is_active",
        nullable = false
    )
    private int                            isActive;
    @OneToMany(
        cascade  = CascadeType.ALL,
        mappedBy = "workOrderStatusId"
    )
    private List<WorkOrderUpdate>          workOrderUpdateList;
    @OneToMany(
        cascade  = CascadeType.ALL,
        mappedBy = "workOrderStatusId"
    )
    private List<WorkOrderStatusQueueType> workOrderStatusQueueTypeList;
    @OneToMany(mappedBy = "workOrderStatusId")
    private List<WorkOrder>                workOrderList;
    @OneToMany(mappedBy = "statusId")
    private List<EscalationSettings>       escalationSettingsList;
    @JoinColumn(
        name                 = "created_by",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private Users                          createdBy;
    @JoinColumn(
        name                 = "updated_by",
        referencedColumnName = "id"
    )
    @ManyToOne
    private Users                          updatedBy;

    public WorkOrderStatus() {}

    public WorkOrderStatus(Integer id) {
        this.id = id;
    }

    public WorkOrderStatus(Integer id, String token, int ownerId, String description, Date createTime, int isActive) {
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
    public List<WorkOrderUpdate> getWorkOrderUpdateList() {
        return workOrderUpdateList;
    }

    public void setWorkOrderUpdateList(List<WorkOrderUpdate> workOrderUpdateList) {
        this.workOrderUpdateList = workOrderUpdateList;
    }

    @XmlTransient
    public List<WorkOrderStatusQueueType> getWorkOrderStatusQueueTypeList() {
        return workOrderStatusQueueTypeList;
    }

    public void setWorkOrderStatusQueueTypeList(List<WorkOrderStatusQueueType> workOrderStatusQueueTypeList) {
        this.workOrderStatusQueueTypeList = workOrderStatusQueueTypeList;
    }

    @XmlTransient
    public List<WorkOrder> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<WorkOrder> workOrderList) {
        this.workOrderList = workOrderList;
    }

    @XmlTransient
    public List<EscalationSettings> getEscalationSettingsList() {
        return escalationSettingsList;
    }

    public void setEscalationSettingsList(List<EscalationSettings> escalationSettingsList) {
        this.escalationSettingsList = escalationSettingsList;
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
        if (!(object instanceof WorkOrderStatus)) {
            return false;
        }

        WorkOrderStatus other = (WorkOrderStatus) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.WorkOrderStatus[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
