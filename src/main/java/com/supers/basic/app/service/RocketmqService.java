package com.supers.basic.app.service;

import org.apache.rocketmq.client.exception.MQClientException;

public interface RocketmqService {

    void consumerStart() throws InterruptedException, MQClientException;

    void send() throws MQClientException, InterruptedException;

    void sendOneway() throws MQClientException, InterruptedException;

    void sendAsync() throws MQClientException, InterruptedException;
}
