/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import java.util.concurrent.locks.ReentrantLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author johnson3yo
 */
@Configuration
public class LockConfig {

    @Bean(name = "workOrderCreationLock")
    public ReentrantLock workOrderCreationLock() {
        return new ReentrantLock();
    }

}
