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
@Table(name = "inventory_request")
@NamedQueries({
    @NamedQuery(name = "InventoryRequest.findAll", query = "SELECT i FROM InventoryRequest i")
    , @NamedQuery(name = "InventoryRequest.findById", query = "SELECT i FROM InventoryRequest i WHERE i.id = :id")
    , @NamedQuery(name = "InventoryRequest.findByQuantity", query = "SELECT i FROM InventoryRequest i WHERE i.quantity = :quantity")
    , @NamedQuery(name = "InventoryRequest.findByItemId", query = "SELECT i FROM InventoryRequest i WHERE i.itemId = :itemId")
    , @NamedQuery(name = "InventoryRequest.findByItemCategoryId", query = "SELECT i FROM InventoryRequest i WHERE i.itemCategoryId = :itemCategoryId")
    , @NamedQuery(name = "InventoryRequest.findByLocationId", query = "SELECT i FROM InventoryRequest i WHERE i.locationId = :locationId")
    , @NamedQuery(name = "InventoryRequest.findByUserId", query = "SELECT i FROM InventoryRequest i WHERE i.userId = :userId")
    , @NamedQuery(name = "InventoryRequest.findByCurrentStatus", query = "SELECT i FROM InventoryRequest i WHERE i.currentStatus = :currentStatus")
    , @NamedQuery(name = "InventoryRequest.findByWorkOrderId", query = "SELECT i FROM InventoryRequest i WHERE i.workOrderId = :workOrderId")
    , @NamedQuery(name = "InventoryRequest.findByToken", query = "SELECT i FROM InventoryRequest i WHERE i.token = :token")
    , @NamedQuery(name = "InventoryRequest.findByCreateTime", query = "SELECT i FROM InventoryRequest i WHERE i.createTime = :createTime")
    , @NamedQuery(name = "InventoryRequest.findByUpdateTime", query = "SELECT i FROM InventoryRequest i WHERE i.updateTime = :updateTime")
    , @NamedQuery(name = "InventoryRequest.findByCreatedBy", query = "SELECT i FROM InventoryRequest i WHERE i.createdBy = :createdBy")
    , @NamedQuery(name = "InventoryRequest.findByUpdatedBy", query = "SELECT i FROM InventoryRequest i WHERE i.updatedBy = :updatedBy")
    , @NamedQuery(name = "InventoryRequest.findByOwnerId", query = "SELECT i FROM InventoryRequest i WHERE i.ownerId = :ownerId")
    , @NamedQuery(name = "InventoryRequest.findByItemName", query = "SELECT i FROM InventoryRequest i WHERE i.itemName = :itemName")
    , @NamedQuery(name = "InventoryRequest.findByLocationName", query = "SELECT i FROM InventoryRequest i WHERE i.locationName = :locationName")
    , @NamedQuery(name = "InventoryRequest.findByIsActive", query = "SELECT i FROM InventoryRequest i WHERE i.isActive = :isActive")})
public class InventoryRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "item_id")
    private int itemId;
    @Column(name = "item_category_id")
    private Integer itemCategoryId;
    @Basic(optional = false)
    @Column(name = "location_id")
    private int locationId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @Column(name = "current_status")
    private String currentStatus;
    @Basic(optional = false)
    @Column(name = "work_order_id")
    private int workOrderId;
    @Basic(optional = false)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Basic(optional = false)
    @Column(name = "created_by")
    private int createdBy;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @Basic(optional = false)
    @Column(name = "owner_id")
    private int ownerId;
    @Basic(optional = false)
    @Column(name = "item_name")
    private String itemName;
    @Basic(optional = false)
    @Column(name = "location_name")
    private String locationName;
    @Basic(optional = false)
    @Column(name = "is_active")
    private int isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventoryReqId")
    private List<InventoryApproval> inventoryApprovalList;

    public InventoryRequest() {
    }

    public InventoryRequest(Integer id) {
        this.id = id;
    }

    public InventoryRequest(Integer id, int quantity, int itemId, int locationId, int userId, String currentStatus, int workOrderId, String token, Date createTime, int createdBy, int ownerId, String itemName, String locationName, int isActive) {
        this.id = id;
        this.quantity = quantity;
        this.itemId = itemId;
        this.locationId = locationId;
        this.userId = userId;
        this.currentStatus = currentStatus;
        this.workOrderId = workOrderId;
        this.token = token;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.ownerId = ownerId;
        this.itemName = itemName;
        this.locationName = locationName;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Integer getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
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
        if (!(object instanceof InventoryRequest)) {
            return false;
        }
        InventoryRequest other = (InventoryRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.InventoryRequest[ id=" + id + " ]";
    }
    
}
