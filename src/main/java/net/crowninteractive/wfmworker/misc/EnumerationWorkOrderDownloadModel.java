/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 *
 * @author uchep
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnumerationWorkOrderDownloadModel {

    private String supply_type;
    private String connection_type;
    private String feederid;
    private String htpoleid;
    private String transformername;
    private String transformerid;
    private String cableupriserid;
    private String ltpoleid;
    private String servicewireno;
    private String customer_number;
    private String premises_type;
    private String nature_of_use_electricity;
    private String landlord_name;
    private String customer_name_on_bill;
    private String customer_phone_no;
    private String e_mail_address;
    private String customer_account_no;
    private String plot;
    private String house_no;
    private String street;
    private String landmark;
    private String city;
    private String tariff;
    private String old_tariff;
    private String meter_no;
    private String meter_by_pass;
    private String meter_design_type;
    private String meter_status;
    private String meter_seal_no;
    private String meter_reading;
    private String dials;
    private String multiplier_factor_on_meter;
    private String ct_ratio;
    private String pt_ratio;
    private String x_coordinate;
    private String y_coordinate;
    private String billing_type;
    private String phase_designation;
    private String cutout_size;
    private String number_of_air_conditioner;
    private String approximate_total_rating_of_ac;
    private String comment;
    private String update_stat;
    private String is_edservicepoint_active;
    private String transformer_rating;
    private String transformer_type;
    private String priority;
    private String provider;
    private String current_status;
    private Integer ticket_id;
    private String queue_name;
    private String queue_type_name;
    
    
    public EnumerationWorkOrderDownloadModel(Object[] e) {
        
        this.supply_type = (String) e[0];
        this.connection_type = (String) e[1];
        this.feederid = (String) e[2];
        this.htpoleid = (String) e[3];
        this.transformername = (String) e[4];
        this.transformerid = (String) e[5];
        this.cableupriserid = (String) e[6];
        this.ltpoleid = (String) e[7];
        this.servicewireno = (String) e[8];
        this.customer_number = (String) e[9];
        this.premises_type = (String) e[10];
        this.nature_of_use_electricity = (String) e[11];
        this.landlord_name = (String) e[12];
        this.customer_name_on_bill = (String) e[13];
        this.customer_phone_no = (String) e[14];
        this.e_mail_address = (String) e[15];
        this.customer_account_no = (String) e[16];
        this.plot = (String) e[17];
        this.house_no = (String) e[18];
        this.street = (String) e[19];
        this.landmark = (String) e[20];
        this.city = (String) e[21];
        this.tariff = (String) e[22];
        this.old_tariff = (String) e[23];
        this.meter_no = (String) e[24];
        this.meter_by_pass = (String) e[25];
        this.meter_design_type = (String) e[26];
        this.meter_status = (String) e[27];
        this.meter_seal_no = (String) e[28];
        this.meter_reading = (String) e[29];
        this.dials = (String) e[30];
        this.multiplier_factor_on_meter = (String) e[31];
        this.ct_ratio = (String) e[32];
        this.pt_ratio = (String) e[33];
        this.x_coordinate = (String) e[34];
        this.y_coordinate = (String) e[35];
        this.billing_type = (String) e[36];
        this.phase_designation = (String) e[37];
        this.cutout_size = (String) e[38];
        this.number_of_air_conditioner = (String) e[39];
        this.approximate_total_rating_of_ac = (String) e[40];
        this.comment = (String) e[41];
        this.update_stat = (String) e[42];
        this.is_edservicepoint_active = (String) e[43];
        this.transformer_rating = (String) e[44];
        this.transformer_type = (String) e[45];
        this.priority = (String) e[46];
        this.provider = (String) e[47];
        this.ticket_id = (Integer) e[48];
        this.queue_name = (String) e[49];
        this.queue_type_name = (String) e[50];
        this.current_status = (String) e[51];
    }

    public static String enumerationWorkOrderDataCols() {
        return "e.supply_type,e.connection_type,e.feederid,e.htpoleid,e.transformername,e.transformerid,e.cableupriserid,e.ltpoleid,e.servicewireno,e.customer_number,e.premises_type,e.nature_of_use_electricity,e.landlord_name,e.customer_name_on_bill,e.customer_phone_no,e.e_mail_address,e.customer_account_no,e.plot,e.house_no,e.street,e.landmark,e.city,e.tariff,e.old_tariff,e.meter_no,e.meter_by_pass,e.meter_design_type,e.meter_status,e.meter_seal_no"
                + ",e.meter_reading,e.dials,e.multiplier_factor_on_meter,e.ct_ratio,e.pt_ratio,e.x_coordinate,e.y_coordinate,e.billing_type,e.phase_designation"
                + ",e.cutout_size,e.number_of_air_conditioner,e.approximate_total_rating_of_ac,e.comment,e.update_stat,e.is_edservicepoint_active,e.transformer_rating,e.transformer_type,e.priority,e.provider";
    }
    
    public static String requestQuery() {
        return "SELECT " + enumerationWorkOrderDataCols() + ",wt.ticket_id, "
             + "(select name from queue where id=wt.queue_id) as queue_name,"
             + "(select name from queue_type where id=wt.queue_type_id) as queue_type_name "
             + ", wt.current_status "
             + "FROM `work_order_temp` wt, enumeration_work_order e where wt.token = e.work_order_temp_token "
             + "and wt.business_unit like {unit} and cast(wt.create_time as date) >= cast({from} as date) and cast(wt.create_time as date) <= cast({to} as date ) ORDER BY wt.create_time";
    }
    
    public static String workOrderQuery() {
         return "SELECT " + enumerationWorkOrderDataCols() + ",wt.ticket_id, "
                + "(select name from queue where id=wt.queue_id) as queue_name,"
                + "(select name from queue_type where id=wt.queue_type_id) as queue_type_name "
                + " ,wt.current_status "
                + "FROM `work_order` wt, enumeration_work_order e where wt.ticket_id = e.work_order_id and business_unit like {unit} and cast(create_time as date) >= cast({from} as date) and cast(create_time as date) <= cast({to} as date )";
    }

    public static String enumerationWorkOrderDataColsParam(String cols) {
        String rtn = "";
        for (String s : cols.split(",")) {
            rtn += ",?";
}
        return rtn.replaceFirst(",", "");
    }

}
