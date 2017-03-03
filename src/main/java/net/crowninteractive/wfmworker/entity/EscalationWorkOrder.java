
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.commons.lang3.RandomStringUtils;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author osita
 */
@Entity
@Table(
    name      = "escalation_work_order",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "EscalationWorkOrder.findAll",
        query = "SELECT e FROM EscalationWorkOrder e"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findById",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.id = :id"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findByToken",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.token = :token"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findByOwnerId",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.ownerId = :ownerId"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findByStatusId",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.statusId = :statusId"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findByStatusUpdatedTime",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.statusUpdatedTime = :statusUpdatedTime"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findByTimeToEscalate",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.timeToEscalate = :timeToEscalate"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findByTimeEscalated",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.timeEscalated = :timeEscalated"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findByEscalatedEmails",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.escalatedEmails = :escalatedEmails"
    ) , @NamedQuery(
        name  = "EscalationWorkOrder.findByTs",
        query = "SELECT e FROM EscalationWorkOrder e WHERE e.ts = :ts"
    )
})
public class EscalationWorkOrder implements Serializable {
    private static final long  serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(
        name                                    = "id",
        nullable                                = false
    )
    private Integer            id;
    @Basic(optional = false)
    @Column(
        name     = "token",
        nullable = false,
        length   = 30
    )
    private String             token;
    @Basic(optional = false)
    @Column(
        name     = "owner_id",
        nullable = false
    )
    private int                ownerId;
    @Column(name = "status_id")
    private Integer            statusId;
    @Column(name = "status_updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date               statusUpdatedTime;
    @Basic(optional = false)
    @Column(
        name     = "time_to_escalate",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date               timeToEscalate;
    @Column(name = "time_escalated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date               timeEscalated;
    @Column(
        name   = "escalated_emails",
        length = 2000
    )
    private String             escalatedEmails;
    @Column(name = "ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date               ts;
    @JoinColumn(
        name                 = "escalation_setting_id",
        referencedColumnName = "id",
        nullable             = false
    )
    @ManyToOne(optional = false)
    private EscalationSettings escalationSettingId;
    @JoinColumn(
        name                 = "work_order_id",
        referencedColumnName = "id"
    )
    @ManyToOne
    private WorkOrder          workOrderId;

    public EscalationWorkOrder() {}

    public EscalationWorkOrder(Integer id) {
        this.id = id;
    }

    public EscalationWorkOrder(Integer id, String token, int ownerId, Date timeToEscalate) {
        this.id             = id;
        this.token          = token;
        this.ownerId        = ownerId;
        this.timeToEscalate = timeToEscalate;
    }

//  "insert into escalation_work_order (token,owner_id,work_order_id,escalation_setting_id,escalated_emails, time_to_escalate, status_id) values (?,?,?,?,?,?,?)",RandomStringUtils.randomAlphanumeric(20),i.getOwnerId(),i.getId(), es.getId(), emails,dt, es.getStatusId() == null ? 0 : es.getStatusId() );

    public EscalationWorkOrder(int ownerId, WorkOrderStatus statusId, Date timeToEscalate, String escalatedEmails,
                               EscalationSettings escalationSettingId, WorkOrder workOrderId) {
        this.ownerId = ownerId;

        if (statusId != null) {
            this.statusId = statusId.getId();
        }

        this.timeToEscalate      = timeToEscalate;
        this.escalatedEmails     = escalatedEmails;
        this.escalationSettingId = escalationSettingId;
        this.workOrderId         = workOrderId;
        this.token               = RandomStringUtils.randomAlphanumeric(30);
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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getStatusUpdatedTime() {
        return statusUpdatedTime;
    }

    public void setStatusUpdatedTime(Date statusUpdatedTime) {
        this.statusUpdatedTime = statusUpdatedTime;
    }

    public Date getTimeToEscalate() {
        return timeToEscalate;
    }

    public void setTimeToEscalate(Date timeToEscalate) {
        this.timeToEscalate = timeToEscalate;
    }

    public Date getTimeEscalated() {
        return timeEscalated;
    }

    public void setTimeEscalated(Date timeEscalated) {
        this.timeEscalated = timeEscalated;
    }

    public String getEscalatedEmails() {
        return escalatedEmails;
    }

    public void setEscalatedEmails(String escalatedEmails) {
        this.escalatedEmails = escalatedEmails;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public EscalationSettings getEscalationSettingId() {
        return escalationSettingId;
    }

    public void setEscalationSettingId(EscalationSettings escalationSettingId) {
        this.escalationSettingId = escalationSettingId;
    }

    public WorkOrder getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(WorkOrder workOrderId) {
        this.workOrderId = workOrderId;
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
        if (!(object instanceof EscalationWorkOrder)) {
            return false;
        }

        EscalationWorkOrder other = (EscalationWorkOrder) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EscalationWorkOrder[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
