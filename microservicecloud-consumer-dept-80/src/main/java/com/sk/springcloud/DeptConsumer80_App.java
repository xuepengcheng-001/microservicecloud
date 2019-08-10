package com.sk.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.sk.myrule.MySelfRule;

@SpringBootApplication
@EnableEurekaClient //开启Eureka，这里是客户端。
//在启动该微服务的时候就能去加载我们的自定义Ribbon配置类，从而使配置生效
//name指定目标服务，configuration指向我们自定义的负载均衡规则
//需要注意的是我们自定义的MySelfRule配置类不能放在@ComponentScan所扫描的当前包下以及子包下即不能放在主启动类所在包或其子包下。
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration=MySelfRule.class)
public class DeptConsumer80_App {
	public static void main(String[] args) {
		SpringApplication.run(DeptConsumer80_App.class, args);
	}
}
