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

import java.util.Date;

/**
 *
 * @author CROWN INTERACTIVE
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueueTypeEnum {
    
    private Integer id; 
    private String token; 
    private int ownerId;
    private String description; 
    private Date createTime;
    private int isActive;
    private String name;
   
}
