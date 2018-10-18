/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author uchep
 */
@Entity
@Table(name = "enumeration_work_order")
@XmlRootElement
public class EnumerationWorkOrder {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "connection_type")
    private String connection_type;
    @Column(name = "feederid")
    private String feederid;
    @Column(name = "transformername")
    private String transformername;
    @Column(name = "transformerid")
    private String transformerid;
    @Column(name = "cableupriserid")
    private String cableupriserid;
    @Column(name = "ltpoleid")
    private String ltpoleid;
    @Column(name = "servicewireno")
    private String servicewireno;
    @Column(name = "customer_number")
    private String customer_number;
    @Column(name = "supply_type")
    private String supply_type;
    @Column(name = "premises_type")
    private String premises_type;
    @Column(name = "nature_of_use_electricity")
    private String nature_of_use_electricity;
    @Column(name = "landlord_name")
    private String landlord_name;
    @Column(name = "to_emcc")
    private String to_emcc;
    @Column(name = "customer_name_on_bill")
    private String customer_name_on_bill;
    @Column(name = "customer_phone_no")
    private String customer_phone_no;
    @Column(name = "e_mail_address")
    private String e_mail_address;
    @Column(name = "customer_account_no")
    private String customer_account_no;
    @Column(name = "plot")
    private String plot;
    @Column(name = "house_no")
    private String house_no;
    @Column(name = "street")
    private String street;
    @Column(name = "landmark")
    private String landmark;
    @Column(name = "city")
    private String city;
    @Column(name = "tariff")
    private String tariff;
    @Column(name = "meter_no")
    private String meter_no;
    @Column(name = "meter_by_pass")
    private String meter_by_pass;
    @Column(name = "meter_design_type")
    private String meter_design_type;
    @Column(name = "meter_type")
    private String meter_type;
    @Column(name = "meter_status")
    private String meter_status;
    @Column(name = "meter_seal_no")
    private String meter_seal_no;
    @Column(name = "meter_reading")
    private String meter_reading;
    @Column(name = "dials")
    private String dials;
    @Column(name = "multiplier_factor_on_meter")
    private String multiplier_factor_on_meter;
    @Column(name = "ct_ratio")
    private String ct_ratio;
    @Column(name = "pt_ratio")
    private String pt_ratio;
    @Column(name = "x_coordinate")
    private String x_coordinate;
    @Column(name = "y_coordinate")
    private String y_coordinate;
    @Column(name = "billing_type")
    private String billing_type;
    @Column(name = "phase_designation")
    private String phase_designation;
    @Column(name = "cutout_size")
    private String cutout_size;
    @Column(name = "number_of_air_conditioner")
    private String number_of_air_conditioner;
    @Column(name = "approximate_total_rating_of_ac")
    private String approximate_total_rating_of_ac;
    @Column(name = "comment")
    private String comment;
    @Column(name = "token")
    private String token;
    @Column(name = "created_by")
    private int created_by;
    @Column(name = "created_time")
    private String created_time;
    @Column(name = "ts")
    private String ts;
    @Column(name = "remark")
    private String remark;
    @Column(name = "status")
    private String status;
    @Column(name = "update_stat")
    private String update_stat;
    @Column(name = "is_work_order")
    private String is_work_order;
    @Column(name = "version")
    private String version;
    @Column(name = "is_edservicepoint_active")
    private boolean is_edservicepoint_active = true;
    @Column(name = "file_id")
    private int file_id;
    @Column(name = "htpoleid")
    private String htpoleid;
    @Column(name = "priority")
    private String priority;
    @Column(name = "provider")
    private String provider;
    @Column(name = "old_tariff")
    private String old_tariff;
    @Column(name = "work_order_temp_token")
    private String work_order_temp_token;
    @Column(name = "work_order_id")
    private String work_order_id;
    @Column(name = "is_migrated")
    private int is_migrated;
    @OneToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

   

    public String getConnection_type() {
        return connection_type;
    }

    public void setConnection_type(String connection_type) {
        this.connection_type = connection_type;
    }

    public String getFeederid() {
        return feederid;
    }

    public void setFeederid(String feederid) {
        this.feederid = feederid;
    }

    public String getTransformername() {
        return transformername;
    }

    public void setTransformername(String transformername) {
        this.transformername = transformername;
    }

    public String getTransformerid() {
        return transformerid;
    }

    public void setTransformerid(String transformerid) {
        this.transformerid = transformerid;
    }

    public String getCableupriserid() {
        return cableupriserid;
    }

    public void setCableupriserid(String cableupriserid) {
        this.cableupriserid = cableupriserid;
    }

    public String getLtpoleid() {
        return ltpoleid;
    }

    public void setLtpoleid(String ltpoleid) {
        this.ltpoleid = ltpoleid;
    }

    public String getServicewireno() {
        return servicewireno;
    }

    public void setServicewireno(String servicewireno) {
        this.servicewireno = servicewireno;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getSupply_type() {
        return supply_type;
    }

    public void setSupply_type(String supply_type) {
        this.supply_type = supply_type;
    }

    public String getPremises_type() {
        return premises_type;
    }

    public void setPremises_type(String premises_type) {
        this.premises_type = premises_type;
    }

    public String getNature_of_use_electricity() {
        return nature_of_use_electricity;
    }

    public void setNature_of_use_electricity(String nature_of_use_electricity) {
        this.nature_of_use_electricity = nature_of_use_electricity;
    }

    public String getLandlord_name() {
        return landlord_name;
    }

    public void setLandlord_name(String landlord_name) {
        this.landlord_name = landlord_name;
    }

    public String getTo_emcc() {
        return to_emcc;
    }

    public void setTo_emcc(String to_emcc) {
        this.to_emcc = to_emcc;
    }

    public String getCustomer_name_on_bill() {
        return customer_name_on_bill;
    }

    public void setCustomer_name_on_bill(String customer_name_on_bill) {
        this.customer_name_on_bill = customer_name_on_bill;
    }

    public String getCustomer_phone_no() {
        return customer_phone_no;
    }

    public void setCustomer_phone_no(String customer_phone_no) {
        this.customer_phone_no = customer_phone_no;
    }

    public String getE_mail_address() {
        return e_mail_address;
    }

    public void setE_mail_address(String e_mail_address) {
        this.e_mail_address = e_mail_address;
    }

    public String getCustomer_account_no() {
        return customer_account_no;
    }

    public void setCustomer_account_no(String customer_account_no) {
        this.customer_account_no = customer_account_no;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getMeter_no() {
        return meter_no;
    }

    public void setMeter_no(String meter_no) {
        this.meter_no = meter_no;
    }

    public String getMeter_by_pass() {
        return meter_by_pass;
    }

    public void setMeter_by_pass(String meter_by_pass) {
        this.meter_by_pass = meter_by_pass;
    }

    public String getMeter_design_type() {
        return meter_design_type;
    }

    public void setMeter_design_type(String meter_design_type) {
        this.meter_design_type = meter_design_type;
    }

    public String getMeter_type() {
        return meter_type;
    }

    public void setMeter_type(String meter_type) {
        this.meter_type = meter_type;
    }

    public String getMeter_status() {
        return meter_status;
    }

    public void setMeter_status(String meter_status) {
        this.meter_status = meter_status;
    }

    public String getMeter_seal_no() {
        return meter_seal_no;
    }

    public void setMeter_seal_no(String meter_seal_no) {
        this.meter_seal_no = meter_seal_no;
    }

    public String getMeter_reading() {
        return meter_reading;
    }

    public void setMeter_reading(String meter_reading) {
        this.meter_reading = meter_reading;
    }

    public String getDials() {
        return dials;
    }

    public void setDials(String dials) {
        this.dials = dials;
    }

    public String getMultiplier_factor_on_meter() {
        return multiplier_factor_on_meter;
    }

    public void setMultiplier_factor_on_meter(String multiplier_factor_on_meter) {
        this.multiplier_factor_on_meter = multiplier_factor_on_meter;
    }

    public String getCt_ratio() {
        return ct_ratio;
    }

    public void setCt_ratio(String ct_ratio) {
        this.ct_ratio = ct_ratio;
    }

    public String getPt_ratio() {
        return pt_ratio;
    }

    public void setPt_ratio(String pt_ratio) {
        this.pt_ratio = pt_ratio;
    }

    public String getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(String x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public String getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(String y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public String getBilling_type() {
        return billing_type;
    }

    public void setBilling_type(String billing_type) {
        this.billing_type = billing_type;
    }

    public String getPhase_designation() {
        return phase_designation;
    }

    public void setPhase_designation(String phase_designation) {
        this.phase_designation = phase_designation;
    }

    public String getCutout_size() {
        return cutout_size;
    }

    public void setCutout_size(String cutout_size) {
        this.cutout_size = cutout_size;
    }

    public String getNumber_of_air_conditioner() {
        return number_of_air_conditioner;
    }

    public void setNumber_of_air_conditioner(String number_of_air_conditioner) {
        this.number_of_air_conditioner = number_of_air_conditioner;
    }

    public String getApproximate_total_rating_of_ac() {
        return approximate_total_rating_of_ac;
    }

    public void setApproximate_total_rating_of_ac(String approximate_total_rating_of_ac) {
        this.approximate_total_rating_of_ac = approximate_total_rating_of_ac;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdate_stat() {
        return update_stat;
    }

    public void setUpdate_stat(String update_stat) {
        this.update_stat = update_stat;
    }

    public String getIs_work_order() {
        return is_work_order;
    }

    public void setIs_work_order(String is_work_order) {
        this.is_work_order = is_work_order;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean getIs_edservicepoint_active() {
        return is_edservicepoint_active;
    }

    public void setIs_edservicepoint_active(boolean is_edservicepoint_active) {
        this.is_edservicepoint_active = is_edservicepoint_active;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getHtpoleid() {
        return htpoleid;
    }

    public void setHtpoleid(String htpoleid) {
        this.htpoleid = htpoleid;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getOld_tariff() {
        return old_tariff;
    }

    public void setOld_tariff(String old_tariff) {
        this.old_tariff = old_tariff;
    }

    public String getWork_order_temp_token() {
        return work_order_temp_token;
    }

    public void setWork_order_temp_token(String work_order_temp_token) {
        this.work_order_temp_token = work_order_temp_token;
    }

    public String getWork_order_id() {
        return work_order_id;
    }

    public void setWork_order_id(String work_order_id) {
        this.work_order_id = work_order_id;
    }

    public int getIs_migrated() {
        return is_migrated;
    }

    public void setIs_migrated(int is_migrated) {
        this.is_migrated = is_migrated;
    }

}
