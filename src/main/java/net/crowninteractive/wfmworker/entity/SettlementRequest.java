
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.math.BigInteger;

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
@Table(
    name      = "settlement_request",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "SettlementRequest.findAll",
        query = "SELECT s FROM SettlementRequest s"
    ) , @NamedQuery(
        name  = "SettlementRequest.findById",
        query = "SELECT s FROM SettlementRequest s WHERE s.id = :id"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByOrderId",
        query = "SELECT s FROM SettlementRequest s WHERE s.orderId = :orderId"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByTicketId",
        query = "SELECT s FROM SettlementRequest s WHERE s.ticketId = :ticketId"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByAccountNumber",
        query = "SELECT s FROM SettlementRequest s WHERE s.accountNumber = :accountNumber"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByAmount",
        query = "SELECT s FROM SettlementRequest s WHERE s.amount = :amount"
    ) , @NamedQuery(
        name  = "SettlementRequest.findBySummary",
        query = "SELECT s FROM SettlementRequest s WHERE s.summary = :summary"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByMessage",
        query = "SELECT s FROM SettlementRequest s WHERE s.message = :message"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByTimeStarted",
        query = "SELECT s FROM SettlementRequest s WHERE s.timeStarted = :timeStarted"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByTimeEnded",
        query = "SELECT s FROM SettlementRequest s WHERE s.timeEnded = :timeEnded"
    ) , @NamedQuery(
        name  = "SettlementRequest.findBySettlementType",
        query = "SELECT s FROM SettlementRequest s WHERE s.settlementType = :settlementType"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByBalance",
        query = "SELECT s FROM SettlementRequest s WHERE s.balance = :balance"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByRemark",
        query = "SELECT s FROM SettlementRequest s WHERE s.remark = :remark"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByPaymentCycle",
        query = "SELECT s FROM SettlementRequest s WHERE s.paymentCycle = :paymentCycle"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByNextPaymentDate",
        query = "SELECT s FROM SettlementRequest s WHERE s.nextPaymentDate = :nextPaymentDate"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByTimeSubmitted",
        query = "SELECT s FROM SettlementRequest s WHERE s.timeSubmitted = :timeSubmitted"
    ) , @NamedQuery(
        name  = "SettlementRequest.findByUserId",
        query = "SELECT s FROM SettlementRequest s WHERE s.userId = :userId"
    )
})
public class SettlementRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(
        name                                   = "id",
        nullable                               = false
    )
    private Integer           id;
    @Column(name = "order_id")
    private BigInteger        orderId;
    @Basic(optional = false)
    @Column(
        name     = "ticket_id",
        nullable = false
    )
    private int               ticketId;
    @Column(
        name   = "account_number",
        length = 40
    )
    private String            accountNumber;
    @Basic(optional = false)
    @Column(
        name     = "amount",
        nullable = false
    )
    private double            amount;
    @Column(
        name   = "summary",
        length = 50
    )
    private String            summary;
    @Column(
        name   = "message",
        length = 400
    )
    private String            message;
    @Column(name = "time_started")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              timeStarted;
    @Column(name = "time_ended")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              timeEnded;
    @Basic(optional = false)
    @Column(
        name     = "settlement_type",
        nullable = false,
        length   = 20
    )
    private String            settlementType;
    @Basic(optional = false)
    @Column(
        name     = "balance",
        nullable = false
    )
    private double            balance;
    @Basic(optional = false)
    @Column(
        name     = "remark",
        nullable = false,
        length   = 1000
    )
    private String            remark;
    @Basic(optional = false)
    @Column(
        name     = "payment_cycle",
        nullable = false
    )
    private int               paymentCycle;
    @Column(name = "next_payment_date")
    @Temporal(TemporalType.DATE)
    private Date              nextPaymentDate;
    @Column(name = "time_submitted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date              timeSubmitted;
    @Column(name = "user_id")
    private Integer           userId;

    public SettlementRequest() {}

    public SettlementRequest(Integer id) {
        this.id = id;
    }

    public SettlementRequest(Integer id, int ticketId, double amount, String settlementType, double balance,
                             String remark, int paymentCycle) {
        this.id             = id;
        this.ticketId       = ticketId;
        this.amount         = amount;
        this.settlementType = settlementType;
        this.balance        = balance;
        this.remark         = remark;
        this.paymentCycle   = paymentCycle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(int paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public Date getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(Date nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public Date getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Date timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
        if (!(object instanceof SettlementRequest)) {
            return false;
        }

        SettlementRequest other = (SettlementRequest) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.SettlementRequest[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
