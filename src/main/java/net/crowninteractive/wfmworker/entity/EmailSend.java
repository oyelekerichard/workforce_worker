
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
import javax.persistence.Lob;
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
    name      = "email_send",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "EmailSend.findAll",
        query = "SELECT e FROM EmailSend e"
    ) , @NamedQuery(
        name  = "EmailSend.findById",
        query = "SELECT e FROM EmailSend e WHERE e.id = :id"
    ) , @NamedQuery(
        name  = "EmailSend.findBySubject",
        query = "SELECT e FROM EmailSend e WHERE e.subject = :subject"
    ) , @NamedQuery(
        name  = "EmailSend.findByCreateTime",
        query = "SELECT e FROM EmailSend e WHERE e.createTime = :createTime"
    ) , @NamedQuery(
        name  = "EmailSend.findBySentTime",
        query = "SELECT e FROM EmailSend e WHERE e.sentTime = :sentTime"
    )
})
public class EmailSend implements Serializable {
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
        name     = "subject",
        nullable = false,
        length   = 1000
    )
    private String            subject;
    @Basic(optional = false)
    @Lob
    @Column(
        name     = "recipients",
        nullable = false,
        length   = 16777215
    )
    private String            recipients;
    @Basic(optional = false)
    @Lob
    @Column(
        name     = "message",
        nullable = false,
        length   = 2147483647
    )
    private String            message;
    @Basic(optional = false)
    @Column(
        name     = "create_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date              createTime;
    @Basic(optional = false)
    @Column(
        name     = "sent_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date              sentTime;

    public EmailSend() {}

    public EmailSend(Integer id) {
        this.id = id;
    }

    public EmailSend(Integer id, String subject, String recipients, String message, Date createTime, Date sentTime) {
        this.id         = id;
        this.subject    = subject;
        this.recipients = recipients;
        this.message    = message;
        this.createTime = createTime;
        this.sentTime   = sentTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
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
        if (!(object instanceof EmailSend)) {
            return false;
        }

        EmailSend other = (EmailSend) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EmailSend[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
