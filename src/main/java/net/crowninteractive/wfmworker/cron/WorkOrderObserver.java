/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.cron;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class WorkOrderObserver {

    @Value(value = "${meter.rollout.queue.local}")
    private String meterRollOutQueueLocal;

    @Autowired
    private JmsTemplate template;

    public void update(String value) {
        template.send(meterRollOutQueueLocal, new MessageCreator() {
            @Override
            public Message createMessage(javax.jms.Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setText(value);
                return message;
            }
        });

    }

}
