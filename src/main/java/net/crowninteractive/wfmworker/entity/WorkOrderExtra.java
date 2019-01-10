/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author johnson3yo
 */
@Entity
@Table(name = "work_order_extra")
public class WorkOrderExtra implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private WorkOrder id;
    @Column(name = "disco")
    private String disco;
    @Column(name = "sub_disco")
    private String subDisco;
    @Column(name = "injection_substation")
    private String injectionSubstation;
    @Column(name = "injection_substation_name")
    private String injectionSubstationName;
    @Column(name = "power_transformer")
    private String powerTransformer;
    @Column(name = "power_transformer_name")
    private String powerTransformerName;
    @Column(name = "feeder")
    private String feeder;
    @Column(name = "feeder_name")
    private String feederName;
    @Column(name = "ht_pole")
    private String htPole;
    @Column(name = "high_tension_physical_id")
    private String highTensionPhysicalId;
    @Column(name = "distribution_substation")
    private String distributionSubstation;
    @Column(name = "distribution_substation_name")
    private String distributionSubstationName;
    @Column(name = "upriser")
    private String upriser;
    @Column(name = "service_pole")
    private String servicePole;
    @Column(name = "service_wire")
    private String serviceWire;
    @Column(name = "nerc_id")
    private String nercId;
    @Column(name = "connection_type")
    private String connectionType;
    @Column(name = "transformer")
    private String transformer;

    public WorkOrderExtra() {
    }

    public WorkOrder getId() {
        return id;
    }

    public void setId(WorkOrder id) {
        this.id = id;
    }

    public String getDisco() {
        return disco;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public String getSubDisco() {
        return subDisco;
    }

    public void setSubDisco(String subDisco) {
        this.subDisco = subDisco;
    }

    public String getInjectionSubstation() {
        return injectionSubstation;
    }

    public void setInjectionSubstation(String injectionSubstation) {
        this.injectionSubstation = injectionSubstation;
    }

    public String getInjectionSubstationName() {
        return injectionSubstationName;
    }

    public void setInjectionSubstationName(String injectionSubstationName) {
        this.injectionSubstationName = injectionSubstationName;
    }

    public String getPowerTransformer() {
        return powerTransformer;
    }

    public void setPowerTransformer(String powerTransformer) {
        this.powerTransformer = powerTransformer;
    }

    public String getPowerTransformerName() {
        return powerTransformerName;
    }

    public void setPowerTransformerName(String powerTransformerName) {
        this.powerTransformerName = powerTransformerName;
    }

    public String getFeeder() {
        return feeder;
    }

    public void setFeeder(String feeder) {
        this.feeder = feeder;
    }

    public String getFeederName() {
        return feederName;
    }

    public void setFeederName(String feederName) {
        this.feederName = feederName;
    }

    public String getHtPole() {
        return htPole;
    }

    public void setHtPole(String htPole) {
        this.htPole = htPole;
    }

    public String getHighTensionPhysicalId() {
        return highTensionPhysicalId;
    }

    public void setHighTensionPhysicalId(String highTensionPhysicalId) {
        this.highTensionPhysicalId = highTensionPhysicalId;
    }

    public String getDistributionSubstation() {
        return distributionSubstation;
    }

    public void setDistributionSubstation(String distributionSubstation) {
        this.distributionSubstation = distributionSubstation;
    }

    public String getDistributionSubstationName() {
        return distributionSubstationName;
    }

    public void setDistributionSubstationName(String distributionSubstationName) {
        this.distributionSubstationName = distributionSubstationName;
    }

    public String getUpriser() {
        return upriser;
    }

    public void setUpriser(String upriser) {
        this.upriser = upriser;
    }

    public String getServicePole() {
        return servicePole;
    }

    public void setServicePole(String servicePole) {
        this.servicePole = servicePole;
    }

    public String getServiceWire() {
        return serviceWire;
    }

    public void setServiceWire(String serviceWire) {
        this.serviceWire = serviceWire;
    }

    public String getNercId() {
        return nercId;
    }

    public void setNercId(String nercId) {
        this.nercId = nercId;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getTransformer() {
        return transformer;
    }

    public void setTransformer(String transformer) {
        this.transformer = transformer;
    }

    @Override
    public String toString() {
        return "WorkOrderExtra{" + "id=" + id + ","
                + " disco=" + disco + ", subDisco=" +
                subDisco + ", injectionSubstation=" + 
                injectionSubstation + ", injectionSubstationName=" + 
                injectionSubstationName + ", powerTransformer=" + 
                powerTransformer + ", powerTransformerName=" + 
                powerTransformerName + ", feeder=" + feeder + ","
                + "eederName=" + feederName + ", htPole=" + htPole + ","
                + " highTensionPhysicalId=" + highTensionPhysicalId + 
                ", distributionSubstation=" + distributionSubstation + 
                ", distributionSubstationName=" + distributionSubstationName 
                + ", upriser=" + upriser + ", servicePole=" + servicePole +
                ", serviceWire=" + serviceWire + ", nercId=" + nercId + 
                ", connectionType=" + connectionType + ", "
                + "transformer=" + transformer + '}';
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorkOrderExtra other = (WorkOrderExtra) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
