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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author osita
 */
@Entity
@Table(name = "billing", catalog = "wfm_new", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Billing.findAll", query = "SELECT b FROM Billing b"),
    @NamedQuery(name = "Billing.findByBillingId", query = "SELECT b FROM Billing b WHERE b.billingId = :billingId"),
    @NamedQuery(name = "Billing.findByAccountNumber", query = "SELECT b FROM Billing b WHERE b.accountNumber = :accountNumber"),
    @NamedQuery(name = "Billing.findByAdjustment", query = "SELECT b FROM Billing b WHERE b.adjustment = :adjustment"),
    @NamedQuery(name = "Billing.findByBillingDate", query = "SELECT b FROM Billing b WHERE b.billingDate = :billingDate"),
    @NamedQuery(name = "Billing.findByBillingMode", query = "SELECT b FROM Billing b WHERE b.billingMode = :billingMode"),
    @NamedQuery(name = "Billing.findByClosingBalance", query = "SELECT b FROM Billing b WHERE b.closingBalance = :closingBalance"),
    @NamedQuery(name = "Billing.findByConsumption", query = "SELECT b FROM Billing b WHERE b.consumption = :consumption"),
    @NamedQuery(name = "Billing.findByCreatedDate", query = "SELECT b FROM Billing b WHERE b.createdDate = :createdDate"),
    @NamedQuery(name = "Billing.findByCustomerStatus", query = "SELECT b FROM Billing b WHERE b.customerStatus = :customerStatus"),
    @NamedQuery(name = "Billing.findByEnergyCharge", query = "SELECT b FROM Billing b WHERE b.energyCharge = :energyCharge"),
    @NamedQuery(name = "Billing.findByFc", query = "SELECT b FROM Billing b WHERE b.fc = :fc"),
    @NamedQuery(name = "Billing.findByFixed", query = "SELECT b FROM Billing b WHERE b.fixed = :fixed"),
    @NamedQuery(name = "Billing.findByKvaConsumptions", query = "SELECT b FROM Billing b WHERE b.kvaConsumptions = :kvaConsumptions"),
    @NamedQuery(name = "Billing.findByKvaDemandChage", query = "SELECT b FROM Billing b WHERE b.kvaDemandChage = :kvaDemandChage"),
    @NamedQuery(name = "Billing.findByKvaMultiplier", query = "SELECT b FROM Billing b WHERE b.kvaMultiplier = :kvaMultiplier"),
    @NamedQuery(name = "Billing.findByKvaRate", query = "SELECT b FROM Billing b WHERE b.kvaRate = :kvaRate"),
    @NamedQuery(name = "Billing.findByKvaReadCode", query = "SELECT b FROM Billing b WHERE b.kvaReadCode = :kvaReadCode"),
    @NamedQuery(name = "Billing.findByKvaReadDate", query = "SELECT b FROM Billing b WHERE b.kvaReadDate = :kvaReadDate"),
    @NamedQuery(name = "Billing.findByLar", query = "SELECT b FROM Billing b WHERE b.lar = :lar"),
    @NamedQuery(name = "Billing.findByLarDate", query = "SELECT b FROM Billing b WHERE b.larDate = :larDate"),
    @NamedQuery(name = "Billing.findByLastDate", query = "SELECT b FROM Billing b WHERE b.lastDate = :lastDate"),
    @NamedQuery(name = "Billing.findByLastPayment", query = "SELECT b FROM Billing b WHERE b.lastPayment = :lastPayment"),
    @NamedQuery(name = "Billing.findByMeteredStatus", query = "SELECT b FROM Billing b WHERE b.meteredStatus = :meteredStatus"),
    @NamedQuery(name = "Billing.findByMmc", query = "SELECT b FROM Billing b WHERE b.mmc = :mmc"),
    @NamedQuery(name = "Billing.findByMultiplier", query = "SELECT b FROM Billing b WHERE b.multiplier = :multiplier"),
    @NamedQuery(name = "Billing.findByNoEstimates", query = "SELECT b FROM Billing b WHERE b.noEstimates = :noEstimates"),
    @NamedQuery(name = "Billing.findByNodials", query = "SELECT b FROM Billing b WHERE b.nodials = :nodials"),
    @NamedQuery(name = "Billing.findByPayment", query = "SELECT b FROM Billing b WHERE b.payment = :payment"),
    @NamedQuery(name = "Billing.findByPresentCode", query = "SELECT b FROM Billing b WHERE b.presentCode = :presentCode"),
    @NamedQuery(name = "Billing.findByPresentReading", query = "SELECT b FROM Billing b WHERE b.presentReading = :presentReading"),
    @NamedQuery(name = "Billing.findByPresentReadingDate", query = "SELECT b FROM Billing b WHERE b.presentReadingDate = :presentReadingDate"),
    @NamedQuery(name = "Billing.findByPreviousBalance", query = "SELECT b FROM Billing b WHERE b.previousBalance = :previousBalance"),
    @NamedQuery(name = "Billing.findByStatusCode", query = "SELECT b FROM Billing b WHERE b.statusCode = :statusCode"),
    @NamedQuery(name = "Billing.findByTariff", query = "SELECT b FROM Billing b WHERE b.tariff = :tariff"),
    @NamedQuery(name = "Billing.findByTotalCharge", query = "SELECT b FROM Billing b WHERE b.totalCharge = :totalCharge"),
    @NamedQuery(name = "Billing.findByVat", query = "SELECT b FROM Billing b WHERE b.vat = :vat"),
    @NamedQuery(name = "Billing.findByCustomerId", query = "SELECT b FROM Billing b WHERE b.customerId = :customerId"),
    @NamedQuery(name = "Billing.findByBillAmountWithinPeriod", query = "SELECT b FROM Billing b WHERE b.billAmountWithinPeriod = :billAmountWithinPeriod"),
    @NamedQuery(name = "Billing.findByBillCycle", query = "SELECT b FROM Billing b WHERE b.billCycle = :billCycle"),
    @NamedQuery(name = "Billing.findByBillDueDate", query = "SELECT b FROM Billing b WHERE b.billDueDate = :billDueDate"),
    @NamedQuery(name = "Billing.findByBillEmbededPowerCharges", query = "SELECT b FROM Billing b WHERE b.billEmbededPowerCharges = :billEmbededPowerCharges"),
    @NamedQuery(name = "Billing.findByBillProductionDate", query = "SELECT b FROM Billing b WHERE b.billProductionDate = :billProductionDate"),
    @NamedQuery(name = "Billing.findByBillingPeriods", query = "SELECT b FROM Billing b WHERE b.billingPeriods = :billingPeriods"),
    @NamedQuery(name = "Billing.findByCostPerUnitPod", query = "SELECT b FROM Billing b WHERE b.costPerUnitPod = :costPerUnitPod"),
    @NamedQuery(name = "Billing.findByCostPerUnitPog", query = "SELECT b FROM Billing b WHERE b.costPerUnitPog = :costPerUnitPog"),
    @NamedQuery(name = "Billing.findByEmbeddedPowerRate", query = "SELECT b FROM Billing b WHERE b.embeddedPowerRate = :embeddedPowerRate"),
    @NamedQuery(name = "Billing.findByInvoiceNumber", query = "SELECT b FROM Billing b WHERE b.invoiceNumber = :invoiceNumber"),
    @NamedQuery(name = "Billing.findByNetArrears", query = "SELECT b FROM Billing b WHERE b.netArrears = :netArrears"),
    @NamedQuery(name = "Billing.findByNewCharges", query = "SELECT b FROM Billing b WHERE b.newCharges = :newCharges"),
    @NamedQuery(name = "Billing.findByNextInvoicePeriod", query = "SELECT b FROM Billing b WHERE b.nextInvoicePeriod = :nextInvoicePeriod"),
    @NamedQuery(name = "Billing.findByPaid", query = "SELECT b FROM Billing b WHERE b.paid = :paid"),
    @NamedQuery(name = "Billing.findByPremiumServiceSubscription", query = "SELECT b FROM Billing b WHERE b.premiumServiceSubscription = :premiumServiceSubscription"),
    @NamedQuery(name = "Billing.findByPreviousReadingAmount", query = "SELECT b FROM Billing b WHERE b.previousReadingAmount = :previousReadingAmount"),
    @NamedQuery(name = "Billing.findByPreviousReadingDate", query = "SELECT b FROM Billing b WHERE b.previousReadingDate = :previousReadingDate"),
    @NamedQuery(name = "Billing.findByTotalAmountDue", query = "SELECT b FROM Billing b WHERE b.totalAmountDue = :totalAmountDue"),
    @NamedQuery(name = "Billing.findByTotalUsages", query = "SELECT b FROM Billing b WHERE b.totalUsages = :totalUsages"),
    @NamedQuery(name = "Billing.findByUnitsConsumedPod", query = "SELECT b FROM Billing b WHERE b.unitsConsumedPod = :unitsConsumedPod"),
    @NamedQuery(name = "Billing.findByUnitsConsumedPog", query = "SELECT b FROM Billing b WHERE b.unitsConsumedPog = :unitsConsumedPog"),
    @NamedQuery(name = "Billing.findByAdc", query = "SELECT b FROM Billing b WHERE b.adc = :adc"),
    @NamedQuery(name = "Billing.findByDistrict", query = "SELECT b FROM Billing b WHERE b.district = :district")})
public class Billing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "billing_id", nullable = false)
    private Long billingId;
    @Column(name = "account_number", length = 60)
    private String accountNumber;
    @Column(name = "adjustment")
    private Integer adjustment;
    @Column(name = "billing_date")
    @Temporal(TemporalType.DATE)
    private Date billingDate;
    @Column(name = "billing_mode", length = 60)
    private String billingMode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "closing_balance", precision = 22)
    private Double closingBalance;
    @Column(name = "consumption", precision = 22)
    private Double consumption;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "customer_status", length = 60)
    private String customerStatus;
    @Column(name = "energy_charge", precision = 22)
    private Double energyCharge;
    @Column(name = "fc", precision = 22)
    private Double fc;
    @Column(name = "fixed", length = 60)
    private String fixed;
    @Column(name = "kva_consumptions")
    private Integer kvaConsumptions;
    @Column(name = "kva_demand_chage", precision = 22)
    private Double kvaDemandChage;
    @Column(name = "kva_multiplier", precision = 22)
    private Double kvaMultiplier;
    @Column(name = "kva_rate", length = 60)
    private String kvaRate;
    @Column(name = "kva_read_code", length = 60)
    private String kvaReadCode;
    @Column(name = "kva_read_date")
    @Temporal(TemporalType.DATE)
    private Date kvaReadDate;
    @Column(name = "lar")
    private Integer lar;
    @Column(name = "lar_date")
    @Temporal(TemporalType.DATE)
    private Date larDate;
    @Column(name = "last_date")
    @Temporal(TemporalType.DATE)
    private Date lastDate;
    @Column(name = "last_payment", precision = 22)
    private Double lastPayment;
    @Column(name = "metered_status", length = 60)
    private String meteredStatus;
    @Column(name = "mmc", length = 60)
    private String mmc;
    @Column(name = "multiplier", precision = 22)
    private Double multiplier;
    @Column(name = "no_estimates", length = 60)
    private String noEstimates;
    @Column(name = "nodials")
    private Integer nodials;
    @Column(name = "payment", precision = 22)
    private Double payment;
    @Column(name = "present_code", length = 20)
    private String presentCode;
    @Column(name = "present_reading", precision = 22)
    private Double presentReading;
    @Column(name = "present_reading_date")
    @Temporal(TemporalType.DATE)
    private Date presentReadingDate;
    @Column(name = "previous_balance", precision = 22)
    private Double previousBalance;
    @Column(name = "status_code", length = 20)
    private String statusCode;
    @Column(name = "tariff", length = 20)
    private String tariff;
    @Column(name = "total_charge", precision = 22)
    private Double totalCharge;
    @Column(name = "vat", precision = 22)
    private Double vat;
    @Column(name = "customer_id")
    private BigInteger customerId;
    @Column(name = "bill_amount_within_period", precision = 22)
    private Double billAmountWithinPeriod;
    @Column(name = "bill_cycle", length = 60)
    private String billCycle;
    @Basic(optional = false)
    @Column(name = "bill_due_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date billDueDate;
    @Column(name = "bill_embeded_power_charges", precision = 22)
    private Double billEmbededPowerCharges;
    @Basic(optional = false)
    @Column(name = "bill_production_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date billProductionDate;
    @Basic(optional = false)
    @Column(name = "billing_periods", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date billingPeriods;
    @Column(name = "cost_per_unit_pod", precision = 22)
    private Double costPerUnitPod;
    @Column(name = "cost_per_unit_pog", precision = 22)
    private Double costPerUnitPog;
    @Column(name = "embedded_power_rate", precision = 22)
    private Double embeddedPowerRate;
    @Column(name = "invoice_number", length = 60)
    private String invoiceNumber;
    @Column(name = "net_arrears", precision = 22)
    private Double netArrears;
    @Column(name = "new_charges", precision = 22)
    private Double newCharges;
    @Column(name = "next_invoice_period")
    @Temporal(TemporalType.DATE)
    private Date nextInvoicePeriod;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "premium_service_subscription", precision = 22)
    private Double premiumServiceSubscription;
    @Column(name = "previous_reading_amount", precision = 22)
    private Double previousReadingAmount;
    @Column(name = "previous_reading_date")
    @Temporal(TemporalType.DATE)
    private Date previousReadingDate;
    @Column(name = "total_amount_due", precision = 22)
    private Double totalAmountDue;
    @Column(name = "total_usages", precision = 22)
    private Double totalUsages;
    @Column(name = "units_consumed_pod", precision = 22)
    private Double unitsConsumedPod;
    @Column(name = "units_consumed_pog", precision = 22)
    private Double unitsConsumedPog;
    @Column(name = "adc", length = 60)
    private String adc;
    @Column(name = "district", length = 60)
    private String district;

    public Billing() {
    }

    public Billing(Long billingId) {
        this.billingId = billingId;
    }

    public Billing(Long billingId, Date billDueDate, Date billProductionDate, Date billingPeriods) {
        this.billingId = billingId;
        this.billDueDate = billDueDate;
        this.billProductionDate = billProductionDate;
        this.billingPeriods = billingPeriods;
    }

    public Long getBillingId() {
        return billingId;
    }

    public void setBillingId(Long billingId) {
        this.billingId = billingId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
        this.adjustment = adjustment;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public String getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(String billingMode) {
        this.billingMode = billingMode;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
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

    public Double getEnergyCharge() {
        return energyCharge;
    }

    public void setEnergyCharge(Double energyCharge) {
        this.energyCharge = energyCharge;
    }

    public Double getFc() {
        return fc;
    }

    public void setFc(Double fc) {
        this.fc = fc;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public Integer getKvaConsumptions() {
        return kvaConsumptions;
    }

    public void setKvaConsumptions(Integer kvaConsumptions) {
        this.kvaConsumptions = kvaConsumptions;
    }

    public Double getKvaDemandChage() {
        return kvaDemandChage;
    }

    public void setKvaDemandChage(Double kvaDemandChage) {
        this.kvaDemandChage = kvaDemandChage;
    }

    public Double getKvaMultiplier() {
        return kvaMultiplier;
    }

    public void setKvaMultiplier(Double kvaMultiplier) {
        this.kvaMultiplier = kvaMultiplier;
    }

    public String getKvaRate() {
        return kvaRate;
    }

    public void setKvaRate(String kvaRate) {
        this.kvaRate = kvaRate;
    }

    public String getKvaReadCode() {
        return kvaReadCode;
    }

    public void setKvaReadCode(String kvaReadCode) {
        this.kvaReadCode = kvaReadCode;
    }

    public Date getKvaReadDate() {
        return kvaReadDate;
    }

    public void setKvaReadDate(Date kvaReadDate) {
        this.kvaReadDate = kvaReadDate;
    }

    public Integer getLar() {
        return lar;
    }

    public void setLar(Integer lar) {
        this.lar = lar;
    }

    public Date getLarDate() {
        return larDate;
    }

    public void setLarDate(Date larDate) {
        this.larDate = larDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Double getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(Double lastPayment) {
        this.lastPayment = lastPayment;
    }

    public String getMeteredStatus() {
        return meteredStatus;
    }

    public void setMeteredStatus(String meteredStatus) {
        this.meteredStatus = meteredStatus;
    }

    public String getMmc() {
        return mmc;
    }

    public void setMmc(String mmc) {
        this.mmc = mmc;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public String getNoEstimates() {
        return noEstimates;
    }

    public void setNoEstimates(String noEstimates) {
        this.noEstimates = noEstimates;
    }

    public Integer getNodials() {
        return nodials;
    }

    public void setNodials(Integer nodials) {
        this.nodials = nodials;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public String getPresentCode() {
        return presentCode;
    }

    public void setPresentCode(String presentCode) {
        this.presentCode = presentCode;
    }

    public Double getPresentReading() {
        return presentReading;
    }

    public void setPresentReading(Double presentReading) {
        this.presentReading = presentReading;
    }

    public Date getPresentReadingDate() {
        return presentReadingDate;
    }

    public void setPresentReadingDate(Date presentReadingDate) {
        this.presentReadingDate = presentReadingDate;
    }

    public Double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(Double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public BigInteger getCustomerId() {
        return customerId;
    }

    public void setCustomerId(BigInteger customerId) {
        this.customerId = customerId;
    }

    public Double getBillAmountWithinPeriod() {
        return billAmountWithinPeriod;
    }

    public void setBillAmountWithinPeriod(Double billAmountWithinPeriod) {
        this.billAmountWithinPeriod = billAmountWithinPeriod;
    }

    public String getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle;
    }

    public Date getBillDueDate() {
        return billDueDate;
    }

    public void setBillDueDate(Date billDueDate) {
        this.billDueDate = billDueDate;
    }

    public Double getBillEmbededPowerCharges() {
        return billEmbededPowerCharges;
    }

    public void setBillEmbededPowerCharges(Double billEmbededPowerCharges) {
        this.billEmbededPowerCharges = billEmbededPowerCharges;
    }

    public Date getBillProductionDate() {
        return billProductionDate;
    }

    public void setBillProductionDate(Date billProductionDate) {
        this.billProductionDate = billProductionDate;
    }

    public Date getBillingPeriods() {
        return billingPeriods;
    }

    public void setBillingPeriods(Date billingPeriods) {
        this.billingPeriods = billingPeriods;
    }

    public Double getCostPerUnitPod() {
        return costPerUnitPod;
    }

    public void setCostPerUnitPod(Double costPerUnitPod) {
        this.costPerUnitPod = costPerUnitPod;
    }

    public Double getCostPerUnitPog() {
        return costPerUnitPog;
    }

    public void setCostPerUnitPog(Double costPerUnitPog) {
        this.costPerUnitPog = costPerUnitPog;
    }

    public Double getEmbeddedPowerRate() {
        return embeddedPowerRate;
    }

    public void setEmbeddedPowerRate(Double embeddedPowerRate) {
        this.embeddedPowerRate = embeddedPowerRate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getNetArrears() {
        return netArrears;
    }

    public void setNetArrears(Double netArrears) {
        this.netArrears = netArrears;
    }

    public Double getNewCharges() {
        return newCharges;
    }

    public void setNewCharges(Double newCharges) {
        this.newCharges = newCharges;
    }

    public Date getNextInvoicePeriod() {
        return nextInvoicePeriod;
    }

    public void setNextInvoicePeriod(Date nextInvoicePeriod) {
        this.nextInvoicePeriod = nextInvoicePeriod;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Double getPremiumServiceSubscription() {
        return premiumServiceSubscription;
    }

    public void setPremiumServiceSubscription(Double premiumServiceSubscription) {
        this.premiumServiceSubscription = premiumServiceSubscription;
    }

    public Double getPreviousReadingAmount() {
        return previousReadingAmount;
    }

    public void setPreviousReadingAmount(Double previousReadingAmount) {
        this.previousReadingAmount = previousReadingAmount;
    }

    public Date getPreviousReadingDate() {
        return previousReadingDate;
    }

    public void setPreviousReadingDate(Date previousReadingDate) {
        this.previousReadingDate = previousReadingDate;
    }

    public Double getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(Double totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }

    public Double getTotalUsages() {
        return totalUsages;
    }

    public void setTotalUsages(Double totalUsages) {
        this.totalUsages = totalUsages;
    }

    public Double getUnitsConsumedPod() {
        return unitsConsumedPod;
    }

    public void setUnitsConsumedPod(Double unitsConsumedPod) {
        this.unitsConsumedPod = unitsConsumedPod;
    }

    public Double getUnitsConsumedPog() {
        return unitsConsumedPog;
    }

    public void setUnitsConsumedPog(Double unitsConsumedPog) {
        this.unitsConsumedPog = unitsConsumedPog;
    }

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billingId != null ? billingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Billing)) {
            return false;
        }
        Billing other = (Billing) object;
        if ((this.billingId == null && other.billingId != null) || (this.billingId != null && !this.billingId.equals(other.billingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.crowninteractive.wfmworker.entity.Billing[ billingId=" + billingId + " ]";
    }
    
}
