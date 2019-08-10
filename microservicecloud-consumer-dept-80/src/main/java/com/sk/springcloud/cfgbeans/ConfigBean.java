package com.sk.springcloud.cfgbeans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class ConfigBean {// 该类相当于Spring中applicationContext.xml
	@Bean
	@LoadBalanced // 开启负载均衡
	public RestTemplate geRestTemplate() {
		return new RestTemplate();
	}

	/**
	 * 指定负载均衡算法，注意要使用@Bean注解将该组件添加到容器中。
	 * 
	 * @return
	 */
	//@Bean
	public IRule myIRule() {
		return new RandomRule();
	}
}
