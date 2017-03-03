
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
    name      = "team",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "Team.findAll",
        query = "SELECT t FROM Team t"
    ) , @NamedQuery(
        name  = "Team.findById",
        query = "SELECT t FROM Team t WHERE t.id = :id"
    ) , @NamedQuery(
        name  = "Team.findByToken",
        query = "SELECT t FROM Team t WHERE t.token = :token"
    ) , @NamedQuery(
        name  = "Team.findByOwnerId",
        query = "SELECT t FROM Team t WHERE t.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "Team.findByName",
        query = "SELECT t FROM Team t WHERE t.name = :name"
    ) , @NamedQuery(
        name  = "Team.findByDescription",
        query = "SELECT t FROM Team t WHERE t.description = :description"
    ) , @NamedQuery(
        name  = "Team.findByCreateTime",
        query = "SELECT t FROM Team t WHERE t.createTime = :createTime"
    ) , @NamedQuery(
        name  = "Team.findByCreatedBy",
        query = "SELECT t FROM Team t WHERE t.createdBy = :createdBy"
    ) , @NamedQuery(
        name  = "Team.findByIsActive",
        query = "SELECT t FROM Team t WHERE t.isActive = :isActive"
    ) , @NamedQuery(
        name  = "Team.findByUpdateTime",
        query = "SELECT t FROM Team t WHERE t.updateTime = :updateTime"
    ) , @NamedQuery(
        name  = "Team.findByUpdatedBy",
        query = "SELECT t FROM Team t WHERE t.updatedBy = :updatedBy"
    ) , @NamedQuery(
        name  = "Team.findBySupervisor",
        query = "SELECT t FROM Team t WHERE t.supervisor = :supervisor"
    )
})
public class Team implements Serializable {
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
        name     = "created_by",
        nullable = false
    )
    private int               createdBy;
    @Basic(optional = false)
    @Column(
        name     = "is_active",
        nullable = false
    )
    private int               isActive;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              updateTime;
    @Column(name = "updated_by")
    private Integer           updatedBy;
    @Column(name = "supervisor")
    private Integer           supervisor;
    @OneToMany(mappedBy = "teamId")
    private List<WorkOrder>   workOrderList;
    @OneToMany(mappedBy = "teamId")
    private List<Engineer>    engineerList;

    public Team() {}

    public Team(Integer id) {
        this.id = id;
    }

    public Team(Integer id, String token, int ownerId, String description, Date createTime, int createdBy,
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

    public Integer getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Integer supervisor) {
        this.supervisor = supervisor;
    }

    @XmlTransient
    public List<WorkOrder> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<WorkOrder> workOrderList) {
        this.workOrderList = workOrderList;
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
        if (!(object instanceof Team)) {
            return false;
        }

        Team other = (Team) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.Team[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
