package net.crowninteractive.wfmworker.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Isaac A.
 */
public class EnumReportObj implements java.io.Serializable {
    
    private String type;
    private String fileName;
    private String[]tokens;
    private String email;
    private String district;
    private String fromDate;
    private String toDate;

    public EnumReportObj(String type, String fileName, String[] tokens) {
        this.type = type;
        this.fileName = fileName;
        this.tokens = tokens;
    }

    public EnumReportObj(String type, String fileName) {
        this.type = type;
        this.fileName = fileName;
    }

    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getTokens() {
        return tokens;
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
