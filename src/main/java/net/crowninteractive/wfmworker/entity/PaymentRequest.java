/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author osita
 */
@Entity
@Table(name = "payment_request", catalog = "wfm_new", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentRequest.findAll", query = "SELECT p FROM PaymentRequest p"),
    @NamedQuery(name = "PaymentRequest.findById", query = "SELECT p FROM PaymentRequest p WHERE p.id = :id"),
    @NamedQuery(name = "PaymentRequest.findByOrderId", query = "SELECT p FROM PaymentRequest p WHERE p.orderId = :orderId"),
    @NamedQuery(name = "PaymentRequest.findByTicketId", query = "SELECT p FROM PaymentRequest p WHERE p.ticketId = :ticketId"),
    @NamedQuery(name = "PaymentRequest.findByAmount", query = "SELECT p FROM PaymentRequest p WHERE p.amount = :amount"),
    @NamedQuery(name = "PaymentRequest.findBySummary", query = "SELECT p FROM PaymentRequest p WHERE p.summary = :summary"),
    @NamedQuery(name = "PaymentRequest.findByMessage", query = "SELECT p FROM PaymentRequest p WHERE p.message = :message"),
    @NamedQuery(name = "PaymentRequest.findByTimeStarted", query = "SELECT p FROM PaymentRequest p WHERE p.timeStarted = :timeStarted"),
    @NamedQuery(name = "PaymentRequest.findByTimeEnded", query = "SELECT p FROM PaymentRequest p WHERE p.timeEnded = :timeEnded")})
public class PaymentRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "order_id", nullable = false)
    private long orderId;
    @Basic(optional = false)
    @Column(name = "ticket_id", nullable = false)
    private int ticketId;
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
    private double amount;
    @Basic(optional = false)
    @Column(name = "summary", nullable = false, length = 50)
    private String summary;
    @Basic(optional = false)
    @Column(name = "message", nullable = false, length = 400)
    private String message;
    @Column(name = "time_started")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStarted;
    @Column(name = "time_ended")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeEnded;

    public PaymentRequest() {
    }

    public PaymentRequest(Integer id) {
        this.id = id;
    }

    public PaymentRequest(Integer id, long orderId, int ticketId, double amount, String summary, String message) {
        this.id = id;
        this.orderId = orderId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.summary = summary;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Date getTimeEnded() {
        return timeEnded;
    }

    public void setTimeEnded(Date timeEnded) {
        this.timeEnded = timeEnded;
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
        if (!(object instanceof PaymentRequest)) {
            return false;
        }
        PaymentRequest other = (PaymentRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.PaymentRequest[ id=" + id + " ]";
    }
    
}
