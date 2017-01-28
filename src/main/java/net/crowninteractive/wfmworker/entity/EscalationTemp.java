/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author osita
 */
@Entity
@Table(name = "escalation_temp", catalog = "wfm_new", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EscalationTemp.findAll", query = "SELECT e FROM EscalationTemp e"),
    @NamedQuery(name = "EscalationTemp.findById", query = "SELECT e FROM EscalationTemp e WHERE e.id = :id"),
    @NamedQuery(name = "EscalationTemp.findByWorkOrderId", query = "SELECT e FROM EscalationTemp e WHERE e.workOrderId = :workOrderId"),
    @NamedQuery(name = "EscalationTemp.findByType", query = "SELECT e FROM EscalationTemp e WHERE e.type = :type")})
public class EscalationTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "work_order_id", nullable = false)
    private int workOrderId;
    @Basic(optional = false)
    @Column(name = "type", nullable = false, length = 6)
    private String type;

    public EscalationTemp() {
    }

    public EscalationTemp(Integer id) {
        this.id = id;
    }

    public EscalationTemp(Integer id, int workOrderId, String type) {
        this.id = id;
        this.workOrderId = workOrderId;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(object instanceof EscalationTemp)) {
            return false;
        }
        EscalationTemp other = (EscalationTemp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EscalationTemp[ id=" + id + " ]";
    }
    
}
