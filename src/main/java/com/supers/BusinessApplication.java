package com.supers;

import com.ribbon.RandomRuleConfig;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.oas.annotations.EnableOpenApi;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableOpenApi  // swagger3   http://localhost:8086/swagger-ui/index.html#/
@SpringBootApplication
@MapperScan(value = {"com.supers.basic.infra.mapper", "com.supers.srm.infra.mapper"}) // Mybatis Mapper接口文件位置，不用写@Mapper注解
@RibbonClients(value = {
		@RibbonClient(name = "base-basic", configuration = RandomRuleConfig.class),
		@RibbonClient(name = "base-business", configuration = RandomRuleConfig.class)
})
@EnableFeignClients
public class BusinessApplication {

	public static void main(String[] args) {
		try{
			SpringApplication app = new SpringApplication(BusinessApplication.class);
			app.run(args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Bean
	public Redisson redisson(){
		Config config = new Config();
		config.useSingleServer().setAddress("redis://39.101.202.67:6379").setPassword("^#@^$(@&&@awdsccdawpq028372").setDatabase(0);
		return (Redisson)Redisson.create(config);
	}
}
