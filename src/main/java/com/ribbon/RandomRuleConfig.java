package com.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import com.ribbon.randomrule.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomRuleConfig 需要放在 Spring ComponentScan 扫描不到的包下
 */
@Configuration
public class RandomRuleConfig {
    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}
