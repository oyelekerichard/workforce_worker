/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author osita
 */
@Entity
@Table(name = "customer", catalog = "wfm_new", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"account_number"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "Customer.findByAccountNumber", query = "SELECT c FROM Customer c WHERE c.accountNumber = :accountNumber"),
    @NamedQuery(name = "Customer.findByAccountType", query = "SELECT c FROM Customer c WHERE c.accountType = :accountType"),
    @NamedQuery(name = "Customer.findByAdc", query = "SELECT c FROM Customer c WHERE c.adc = :adc"),
    @NamedQuery(name = "Customer.findByAddress1", query = "SELECT c FROM Customer c WHERE c.address1 = :address1"),
    @NamedQuery(name = "Customer.findByAddress2", query = "SELECT c FROM Customer c WHERE c.address2 = :address2"),
    @NamedQuery(name = "Customer.findByAddress3", query = "SELECT c FROM Customer c WHERE c.address3 = :address3"),
    @NamedQuery(name = "Customer.findByBusinessDistrict", query = "SELECT c FROM Customer c WHERE c.businessDistrict = :businessDistrict"),
    @NamedQuery(name = "Customer.findByCreatedDate", query = "SELECT c FROM Customer c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "Customer.findByCustomerStatus", query = "SELECT c FROM Customer c WHERE c.customerStatus = :customerStatus"),
    @NamedQuery(name = "Customer.findByCustomerType", query = "SELECT c FROM Customer c WHERE c.customerType = :customerType"),
    @NamedQuery(name = "Customer.findByCustomerCycle", query = "SELECT c FROM Customer c WHERE c.customerCycle = :customerCycle"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.findByGovt", query = "SELECT c FROM Customer c WHERE c.govt = :govt"),
    @NamedQuery(name = "Customer.findByInstitutionCode", query = "SELECT c FROM Customer c WHERE c.institutionCode = :institutionCode"),
    @NamedQuery(name = "Customer.findByKcg", query = "SELECT c FROM Customer c WHERE c.kcg = :kcg"),
    @NamedQuery(name = "Customer.findByMeterNumber", query = "SELECT c FROM Customer c WHERE c.meterNumber = :meterNumber"),
    @NamedQuery(name = "Customer.findByMeterStatus", query = "SELECT c FROM Customer c WHERE c.meterStatus = :meterStatus"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByOrganizationCode", query = "SELECT c FROM Customer c WHERE c.organizationCode = :organizationCode"),
    @NamedQuery(name = "Customer.findByPhoneNumber", query = "SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Customer.findByTariff", query = "SELECT c FROM Customer c WHERE c.tariff = :tariff"),
    @NamedQuery(name = "Customer.findByVsoId", query = "SELECT c FROM Customer c WHERE c.vsoId = :vsoId"),
    @NamedQuery(name = "Customer.findByOldAccountDetailid", query = "SELECT c FROM Customer c WHERE c.oldAccountDetailid = :oldAccountDetailid"),
    @NamedQuery(name = "Customer.findByFts", query = "SELECT c FROM Customer c WHERE c.fts = :fts")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "account_number", length = 60)
    private String accountNumber;
    @Column(name = "account_type", length = 60)
    private String accountType;
    @Column(name = "adc", length = 2)
    private String adc;
    @Column(name = "address_1", length = 255)
    private String address1;
    @Column(name = "address_2", length = 255)
    private String address2;
    @Column(name = "address_3", length = 255)
    private String address3;
    @Column(name = "business_district", length = 60)
    private String businessDistrict;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "customer_status", length = 20)
    private String customerStatus;
    @Column(name = "customer_type", length = 60)
    private String customerType;
    @Column(name = "customer_cycle", length = 60)
    private String customerCycle;
    @Column(name = "email", length = 120)
    private String email;
    @Column(name = "govt", length = 60)
    private String govt;
    @Column(name = "institution_code", length = 255)
    private String institutionCode;
    @Column(name = "kcg", length = 255)
    private String kcg;
    @Column(name = "meter_number", length = 60)
    private String meterNumber;
    @Column(name = "meter_status", length = 5)
    private String meterStatus;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "organization_code", length = 60)
    private String organizationCode;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "tariff", length = 60)
    private String tariff;
    @Column(name = "vso_id", length = 10)
    private String vsoId;
    @Column(name = "oldAccountDetail_id")
    private BigInteger oldAccountDetailid;
    @Column(name = "fts", length = 255)
    private String fts;

    public Customer() {
    }

    public Customer(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getBusinessDistrict() {
        return businessDistrict;
    }

    public void setBusinessDistrict(String businessDistrict) {
        this.businessDistrict = businessDistrict;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerCycle() {
        return customerCycle;
    }

    public void setCustomerCycle(String customerCycle) {
        this.customerCycle = customerCycle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGovt() {
        return govt;
    }

    public void setGovt(String govt) {
        this.govt = govt;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getKcg() {
        return kcg;
    }

    public void setKcg(String kcg) {
        this.kcg = kcg;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getMeterStatus() {
        return meterStatus;
    }

    public void setMeterStatus(String meterStatus) {
        this.meterStatus = meterStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getVsoId() {
        return vsoId;
    }

    public void setVsoId(String vsoId) {
        this.vsoId = vsoId;
    }

    public BigInteger getOldAccountDetailid() {
        return oldAccountDetailid;
    }

    public void setOldAccountDetailid(BigInteger oldAccountDetailid) {
        this.oldAccountDetailid = oldAccountDetailid;
    }

    public String getFts() {
        return fts;
    }

    public void setFts(String fts) {
        this.fts = fts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.Customer[ customerId=" + customerId + " ]";
    }
    
}
