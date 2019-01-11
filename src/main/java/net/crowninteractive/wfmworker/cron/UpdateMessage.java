/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

/**
 *
 * @author johnson3yo
 */
public class UpdateMessage {
    private String accountNumber;
    private String status;

    public UpdateMessage() {
    }
   
    public UpdateMessage(String accountNumber, String status) {
        this.accountNumber = accountNumber;
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateMessage{" + "accountNumber=" + accountNumber + ", status=" + status + '}';
    }
    
    
    
}
