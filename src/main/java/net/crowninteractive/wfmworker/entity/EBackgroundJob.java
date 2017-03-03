
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
    name      = "e_background_job",
    catalog   = "wfm_new",
    schema    = ""
)
@XmlRootElement
@NamedQueries( {
    @NamedQuery(
        name  = "EBackgroundJob.findAll",
        query = "SELECT e FROM EBackgroundJob e"
    ) , @NamedQuery(
        name  = "EBackgroundJob.findById",
        query = "SELECT e FROM EBackgroundJob e WHERE e.id = :id"
    ) , @NamedQuery(
        name  = "EBackgroundJob.findByProgress",
        query = "SELECT e FROM EBackgroundJob e WHERE e.progress = :progress"
    ) , @NamedQuery(
        name  = "EBackgroundJob.findByStatus",
        query = "SELECT e FROM EBackgroundJob e WHERE e.status = :status"
    ) , @NamedQuery(
        name  = "EBackgroundJob.findByStartTime",
        query = "SELECT e FROM EBackgroundJob e WHERE e.startTime = :startTime"
    ) , @NamedQuery(
        name  = "EBackgroundJob.findByUpdatedTime",
        query = "SELECT e FROM EBackgroundJob e WHERE e.updatedTime = :updatedTime"
    ) , @NamedQuery(
        name  = "EBackgroundJob.findByEndTime",
        query = "SELECT e FROM EBackgroundJob e WHERE e.endTime = :endTime"
    )
})
public class EBackgroundJob implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(
        name                                   = "id",
        nullable                               = false
    )
    private Integer           id;
    @Column(name = "progress")
    private Integer           progress;
    @Column(name = "status")
    private Integer           status;
    @Basic(optional = false)
    @Column(
        name     = "start_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date              startTime;
    @Basic(optional = false)
    @Column(
        name     = "updated_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date              updatedTime;
    @Basic(optional = false)
    @Column(
        name     = "end_time",
        nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date              endTime;
    @Lob
    @Column(
        name   = "request",
        length = 65535
    )
    private String            request;
    @Lob
    @Column(
        name   = "status_text",
        length = 65535
    )
    private String            statusText;

    public EBackgroundJob() {}

    public EBackgroundJob(Integer id) {
        this.id = id;
    }

    public EBackgroundJob(Integer id, Date startTime, Date updatedTime, Date endTime) {
        this.id          = id;
        this.startTime   = startTime;
        this.updatedTime = updatedTime;
        this.endTime     = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
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
        if (!(object instanceof EBackgroundJob)) {
            return false;
        }

        EBackgroundJob other = (EBackgroundJob) object;

        if (((this.id == null) && (other.id != null)) || ((this.id != null) &&!this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.EBackgroundJob[ id=" + id + " ]";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
