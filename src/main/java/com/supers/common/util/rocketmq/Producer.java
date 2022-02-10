/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.supers.common.util.rocketmq;

import com.supers.basic.app.service.impl.MinioServiceImpl;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class Producer {

    public static String defaultMsgBody(){
        return "Message " + System.currentTimeMillis();
    }

    /**
     * 同步发送消息
     */
    public static void send(String namesrvAddr, String producerGroup, String msgBody) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();
        for (int i = 0; i < 5; i++){
            try {
                Message msg = new Message("Topic", "TagDefault", msgBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }

    /**
     * 无返回值发送消息
     */
    public static void sendOneway(String namesrvAddr, String producerGroup, String msgBody) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();
        for (int i = 0; i < 5; i++){
            try {
                Message msg = new Message("TopicOneway", "TagDefault", msgBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.sendOneway(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }

    /**
     * 异步发送消息
     */
    public static void sendAsync(String namesrvAddr, String producerGroup, String msgBody) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();
        for (int i = 0; i < 5; i++){
            try {
                Message msg = new Message("TopicSync", "TagDefault", msgBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%s%n", sendResult);
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("异步消息失败");
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }

    /**
     * 发送局部顺序消息
     */
    public static void sendOrder(String namesrvAddr, String producerGroup, List<String> msgBodys) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();
        for(int i = 0; i < msgBodys.size(); i++){
            try {
                Message msg = new Message("TopicOrder", "TagDefault", msgBodys.get(i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        int mqIndex = (Integer) arg;
                        return mqs.get(mqIndex);
                    }
                }, 1);
                System.out.printf("%s%n", sendResult);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }

    /**
     * 发送事务消息
     */
    public static void sendTransaction(String namesrvAddr, String producerGroup, String msgBody) throws MQClientException, InterruptedException {
        TransactionMQProducer producer = new TransactionMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                /**
                 * 执行本地事务逻辑
                 */
                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                /**
                 * 消息回查 LocalTransactionState.UNKNOW 会继续调用 checkLocalTransaction 回查
                 */
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        // 只能启动不能停止，停止的话就没办法启动回查方法
        producer.start();

        for (int i = 0; i < 5; i++){
            try {
                Message msg = new Message("TopicTransaction", "TagDefault", msgBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
