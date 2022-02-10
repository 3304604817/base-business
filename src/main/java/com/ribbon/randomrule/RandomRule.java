package com.ribbon.randomrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机策略
 */
public class RandomRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        ILoadBalancer loadBalancer = this.getLoadBalancer();

        // 获取当前请求服务实例
        List<Server> reachableServers = loadBalancer.getReachableServers();

        // 获取一个随机实例
        Server server = reachableServers.get(
                ThreadLocalRandom.current().nextInt(reachableServers.size())
        );
        if(server.isAlive()){
            return server;
        }
        return null;
    }
}
