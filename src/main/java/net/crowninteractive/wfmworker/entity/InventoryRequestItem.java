
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "inventory_request_item")
@NamedQueries( {
    @NamedQuery(
        name  = "InventoryRequestItem.findAll",
        query = "SELECT i FROM InventoryRequestItem i"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findById",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.id = :id"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByWorkOrderId",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.workOrderId = :workOrderId"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByResourceId",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.resourceId = :resourceId"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByDistrict",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.district = :district"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByInventoryId",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.inventoryId = :inventoryId"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByInventoryDesc",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.inventoryDesc = :inventoryDesc"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByCategory",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.category = :category"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByQuantity",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.quantity = :quantity"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByCurrentStatus",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.currentStatus = :currentStatus"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByCurrentStatusTime",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.currentStatusTime = :currentStatusTime"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByIsClosed",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.isClosed = :isClosed"
    ) , @NamedQuery(
        name  = "InventoryRequestItem.findByCreateTime",
        query = "SELECT i FROM InventoryRequestItem i WHERE i.createTime = :createTime"
    )
})
public class InventoryRequestItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer           id;
    @Basic(optional = false)
    @Column(name = "work_order_id")
    private int               workOrderId;
    @Basic(optional = false)
    @Column(name = "resource_id")
    private int               resourceId;
    @Basic(optional = false)
    @Column(name = "district")
    private String            district;
    @Basic(optional = false)
    @Column(name = "inventory_id")
    private int               inventoryId;
    @Basic(optional = false)
    @Column(name = "inventory_desc")
    private String            inventoryDesc;
    @Basic(optional = false)
    @Column(name = "category")
    private String            category;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int               quantity;
    @Column(name = "current_status")
    private Integer           currentStatus;
    @Column(name = "current_status_time")
    private Integer           currentStatusTime;
    @Column(name = "is_closed")
    private Integer           isClosed;
    @Basic(optional = false)
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              createTime;

    public InventoryRequestItem() {}

    public InventoryRequestItem(Integer id) {
        this.id = id;
    }

    public InventoryRequestItem(Integer id, int workOrderId, int resourceId, String district, int inventoryId,
                                String inventoryDesc, String category, int quantity, Date createTime) {
        this.id            = id;
        this.workOrderId   = workOrderId;
        this.resourceId    = resourceId;
        this.district      = district;
        this.inventoryId   = inventoryId;
        this.inventoryDesc = inventoryDesc;
        this.category      = category;
        this.quantity      = quantity;
        this.createTime    = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryDesc() {
        return inventoryDesc;
    }

    public void setInventoryDesc(String inventoryDesc) {
        this.inventoryDesc = inventoryDesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getCurrentStatusTime() {
        return currentStatusTime;
    }

    public void setCurrentStatusTime(Integer currentStatusTime) {
        this.currentStatusTime = currentStatusTime;
    }

    public Integer getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Integer isClosed) {
        this.isClosed = isClosed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        if (!(object instanceof InventoryRequestItem)) {
            return false;
        }

        InventoryRequestItem other = (InventoryRequestItem) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.InventoryRequestItem[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
