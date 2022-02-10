package com.supers.basic.api.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.supers.basic.feign.BasicFeignClient;
import com.supers.common.util.jwt.annotation.Access;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Api(tags="测试")
@RestController
@RequestMapping("/sentinel")
public class SentinelController {

    @Autowired
    @SuppressWarnings("all")
    private BasicFeignClient basicFeignClient;

    @Access(accessNoToken = true)
    @ApiOperation(value = "测试")
    @GetMapping("/temple")
    @SentinelResource(value = "dashboard-sentinel")
    public ResponseEntity<String> temple() {
        System.out.println("调用成功");
        return new ResponseEntity("temple调用成功", HttpStatus.OK);
    }

    @Access(accessNoToken = true)
    @ApiOperation(value = "Feign测试")
    @GetMapping("/feign-temple")
    @SentinelResource(value = "dashboard-sentinel")
    public ResponseEntity<String> feignTemple() {
        basicFeignClient.temple();
        System.out.println("Feign调用成功");
        return new ResponseEntity("feign-temple调用成功", HttpStatus.OK);
    }

    @Access(accessNoToken = true)
    @ApiOperation(value = "自定义限制规则测试")
    @GetMapping("/customize-rule/temple")
    @SentinelResource(value = "local-sentinel", blockHandler = "sentinelRule")
    public ResponseEntity<String> sentinelTemple() {
        System.out.println("调用成功");
        return new ResponseEntity("sentinel-temple调用成功", HttpStatus.OK);
    }

    /**
     * 定义限流规则
     * 1、必须是public
     * 2、返回式必须与原方法一致
     * 3、默认与原方法在同一个类中
     */
    public ResponseEntity<String> sentinelRule(BlockException e){
        // 创建存放限流规则集合
        List<FlowRule> flowRules = new ArrayList<>();
        // 创建限流规则
        FlowRule rule = new FlowRule();
        rule.setResource("local-sentinel");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);

        flowRules.add(rule);

        // 加载限流规则
        FlowRuleManager.loadRules(flowRules);
        return new ResponseEntity("触发流控", HttpStatus.OK);
    }
}
