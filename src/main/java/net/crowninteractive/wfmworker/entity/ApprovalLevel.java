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

/**
 *
 * @author USER
 */
@Entity
@Table(name = "approval_level")
@NamedQueries({
    @NamedQuery(name = "ApprovalLevel.findAll", query = "SELECT a FROM ApprovalLevel a")
    , @NamedQuery(name = "ApprovalLevel.findById", query = "SELECT a FROM ApprovalLevel a WHERE a.id = :id")
    , @NamedQuery(name = "ApprovalLevel.findByName", query = "SELECT a FROM ApprovalLevel a WHERE a.name = :name")
    , @NamedQuery(name = "ApprovalLevel.findByCreateTime", query = "SELECT a FROM ApprovalLevel a WHERE a.createTime = :createTime")
    , @NamedQuery(name = "ApprovalLevel.findByUpdateTime", query = "SELECT a FROM ApprovalLevel a WHERE a.updateTime = :updateTime")
    , @NamedQuery(name = "ApprovalLevel.findByIsActive", query = "SELECT a FROM ApprovalLevel a WHERE a.isActive = :isActive")
    , @NamedQuery(name = "ApprovalLevel.findByToken", query = "SELECT a FROM ApprovalLevel a WHERE a.token = :token")
    , @NamedQuery(name = "ApprovalLevel.findByOwnerId", query = "SELECT a FROM ApprovalLevel a WHERE a.ownerId = :ownerId")})
public class ApprovalLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "is_active")
    private Integer isActive;
    @Basic(optional = false)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @Column(name = "owner_id")
    private int ownerId;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users createdBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @ManyToOne
    private Users updatedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelId")
    private List<InventoryApproval> inventoryApprovalList;

    public ApprovalLevel() {
    }

    public ApprovalLevel(Integer id) {
        this.id = id;
    }

    public ApprovalLevel(Integer id, String name, Date createTime, String token, int ownerId) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.token = token;
        this.ownerId = ownerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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

    public List<InventoryApproval> getInventoryApprovalList() {
        return inventoryApprovalList;
    }

    public void setInventoryApprovalList(List<InventoryApproval> inventoryApprovalList) {
        this.inventoryApprovalList = inventoryApprovalList;
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
        if (!(object instanceof ApprovalLevel)) {
            return false;
        }
        ApprovalLevel other = (ApprovalLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.ApprovalLevel[ id=" + id + " ]";
    }
    
}
