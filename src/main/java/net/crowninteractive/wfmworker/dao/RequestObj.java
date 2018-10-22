/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author johnson3yo
 */
public class RequestObj {

    private String acctRecId;
    private String ticketId;
    private String amount;
    private String reference;
    private String start;
    private String count;
    private String startdate;
    private String enddate;
    private String oldPassword;
    private String newPassword;
    private String roles;
    private String loginurl;
    private String stateId;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private String state;
    private String image;
    private String thumbnail;
    private String token;
    private String cityToken;
    private String billingId;
    private String city;
    private String reportedBy;
    private String businessUnit;
    private String tariff;
    private String summary;
    private String description;
    private String customerName;
    private String accountNumber;
    private String chargeTypeToken;
    private String frequency;
    private String[] tokens;
    private String[] setup;
    private String name;
    private String district;
    private String currentBill;
    private String lastPaidAmount;
    private Date lastPaymentDate;
    private Integer orderId;
    private String purpose;
    private String orderIdStatus;
    private Double previousOutstanding;
    private Date dueDate;
    private Integer staffId;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getChargeTypeToken() {
        return chargeTypeToken;
    }

    public void setChargeTypeToken(String chargeTypeToken) {
        this.chargeTypeToken = chargeTypeToken;
    }

    public String[] getTokens() {
        return tokens;
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCityToken() {
        return cityToken;
    }

    public void setCityToken(String cityToken) {
        this.cityToken = cityToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getLoginurl() {
        return loginurl;
    }

    public void setLoginurl(String loginurl) {
        this.loginurl = loginurl;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String[] getSetup() {
        return setup;
    }

    public void setSetup(String[] setup) {
        this.setup = setup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAcctRecId() {
        return acctRecId;
    }

    public void setAcctRecId(String acctRecId) {
        this.acctRecId = acctRecId;
    }

    public String getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(String currentBill) {
        this.currentBill = currentBill;
    }

    public String getLastPaidAmount() {
        return lastPaidAmount;
    }

    public void setLastPaidAmount(String lastPaidAmount) {
        this.lastPaidAmount = lastPaidAmount;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOrderIdStatus() {
        return orderIdStatus;
    }

    public void setOrderIdStatus(String orderIdStatus) {
        this.orderIdStatus = orderIdStatus;
    }

    public Double getPreviousOutstanding() {
        return previousOutstanding;
    }

    public void setPreviousOutstanding(Double previousOutstanding) {
        this.previousOutstanding = previousOutstanding;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    @Override
    public String toString() {
        return "RequestObj{" + "acctRecId=" + acctRecId + ", ticketId=" + ticketId + ", amount=" + amount + ", reference=" + reference + ", start=" + start + ", count=" + count + ", startdate=" + startdate + ", enddate=" + enddate + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", roles=" + roles + ", loginurl=" + loginurl + ", stateId=" + stateId + ", email=" + email + ", password=" + password + ", firstname=" + firstname + ", lastname=" + lastname + ", phone=" + phone + ", address=" + address + ", state=" + state + ", image=" + image + ", thumbnail=" + thumbnail + ", token=" + token + ", cityToken=" + cityToken + ", billingId=" + billingId + ", city=" + city + ", reportedBy=" + reportedBy + ", businessUnit=" + businessUnit + ", tariff=" + tariff + ", summary=" + summary + ", description=" + description + ", customerName=" + customerName + ", accountNumber=" + accountNumber + ", chargeTypeToken=" + chargeTypeToken + ", frequency=" + frequency + ", tokens=" + tokens + ", setup=" + setup + ", name=" + name + ", district=" + district + ", currentBill=" + currentBill + ", lastPaidAmount=" + lastPaidAmount + ", lastPaymentDate=" + lastPaymentDate + ", orderId=" + orderId + ", purpose=" + purpose + ", orderIdStatus=" + orderIdStatus + ", previousOutstanding=" + previousOutstanding + ", dueDate=" + dueDate + ", staffId=" + staffId + '}';
    }

   
}
