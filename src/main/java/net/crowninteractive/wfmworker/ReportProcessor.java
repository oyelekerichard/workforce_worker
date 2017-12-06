/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import java.util.TimerTask;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class ReportProcessor  extends TimerTask {

    @Override
    public void run() {
        System.out.println(">>>>REport Processor Running >>>>>");
    
    }
    
}
