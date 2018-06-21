/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * @author johnson3yo
 */
@Configuration
@ComponentScan("net.crowninteractive.wfmworker.service")
@EnableAsync
public class AsyncConfig {

}