/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author johnson3yo
 */
@Configuration
@EnableJms
public class ActiveMQConfig {
    
    @Value(value = "${ucg.jms.broker.url}")
    private String activeMqURL;
    @Value(value = "${ucg.jms.broker.username}")
    private String activeMqUsername;
    @Value(value = "${ucg.jms.broker.password}")
    private String activeMqPassword;
    
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory
                = new ActiveMQConnectionFactory(activeMqURL);
        connectionFactory.setUserName(activeMqUsername);
        connectionFactory.setPassword(activeMqPassword);
        return connectionFactory;
    }
    
    @Bean
    public JmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory
                = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("4-8");
        return factory;
    }
    
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        return template;
    }
    
}
