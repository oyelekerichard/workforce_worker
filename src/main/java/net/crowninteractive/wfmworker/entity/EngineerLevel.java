/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

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
@Table(name = "engineer_level", catalog = "wfm_new", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EngineerLevel.findAll", query = "SELECT e FROM EngineerLevel e"),
    @NamedQuery(name = "EngineerLevel.findById", query = "SELECT e FROM EngineerLevel e WHERE e.id = :id"),
    @NamedQuery(name = "EngineerLevel.findByToken", query = "SELECT e FROM EngineerLevel e WHERE e.token = :token"),
    @NamedQuery(name = "EngineerLevel.findByOwnerId", query = "SELECT e FROM EngineerLevel e WHERE e.ownerId = :ownerId"),
    @NamedQuery(name = "EngineerLevel.findByName", query = "SELECT e FROM EngineerLevel e WHERE e.name = :name"),
    @NamedQuery(name = "EngineerLevel.findByDescription", query = "SELECT e FROM EngineerLevel e WHERE e.description = :description"),
    @NamedQuery(name = "EngineerLevel.findByCreateTime", query = "SELECT e FROM EngineerLevel e WHERE e.createTime = :createTime"),
    @NamedQuery(name = "EngineerLevel.findByUpdateTime", query = "SELECT e FROM EngineerLevel e WHERE e.updateTime = :updateTime"),
    @NamedQuery(name = "EngineerLevel.findByIsActive", query = "SELECT e FROM EngineerLevel e WHERE e.isActive = :isActive")})
public class EngineerLevel implements Serializable {

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
    @Column(name = "name", length = 40)
    private String name;
    @Basic(optional = false)
    @Column(name = "description", nullable = false, length = 400)
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
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Users createdBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @ManyToOne
    private Users updatedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "engLevel")
    private List<Engineer> engineerList;

    public EngineerLevel() {
    }

    public EngineerLevel(Integer id) {
        this.id = id;
    }

    public EngineerLevel(Integer id, String token, int ownerId, String description, Date createTime, int isActive) {
        this.id = id;
        this.token = token;
        this.ownerId = ownerId;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EngineerLevel)) {
            return false;
        }
        EngineerLevel other = (EngineerLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EngineerLevel[ id=" + id + " ]";
    }
    
}
