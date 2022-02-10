package com.supers.basic.app.service.impl;

import com.supers.basic.app.service.RocketmqService;
import com.supers.common.util.rocketmq.Consumer;
import com.supers.common.util.rocketmq.Producer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RocketmqServiceImpl implements RocketmqService {

    @Value("${rocketmq.name-server}")
    private String NAMESRV_ADDR;
    @Value("${rocketmq.consumer.group}")
    private String CONSUMER_GROUP;
    @Value("${rocketmq.producer.group}")
    private String PRODUCER_GROUP;

    @Override
    public void consumerStart() throws InterruptedException, MQClientException{
        Consumer.consumerStart(NAMESRV_ADDR, CONSUMER_GROUP);
    }

    @Override
    public void send() throws MQClientException, InterruptedException {
        Producer.send(NAMESRV_ADDR, PRODUCER_GROUP, Producer.defaultMsgBody());
    }

    @Override
    public void sendOneway() throws MQClientException, InterruptedException {
        Producer.sendOneway(NAMESRV_ADDR, PRODUCER_GROUP, Producer.defaultMsgBody());
    }

    @Override
    public void sendAsync() throws MQClientException, InterruptedException {
        Producer.sendAsync(NAMESRV_ADDR, PRODUCER_GROUP, Producer.defaultMsgBody());
    }
}
