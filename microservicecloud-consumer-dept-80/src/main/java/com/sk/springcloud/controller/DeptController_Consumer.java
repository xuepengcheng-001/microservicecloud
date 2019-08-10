package com.sk.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sk.springcloud.entitys.Dept;

@RestController
public class DeptController_Consumer {
	//在没有使用Eureka时我们使用具体的地址信息来访问接口
	//private static final String REST_URL_PREFIX = "http://localhost:8001";
	//使用Eureka之后我们就可以通过服务名来访问接口了
	private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/consumer/dept/add")
	public boolean add(Dept dept) {
		// (url,requestMap,ResponseBean.class)
		//这三个参数分别代表REST请求地址，请求参数，HTTP响应转换被转换成的对象类型
		return restTemplate.postForObject(REST_URL_PREFIX+"/dept/add", dept, Boolean.class);
	}
	@RequestMapping("/consumer/dept/get/{id}")
	public Dept get(@PathVariable("id") Long id) {
		return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/"+id, Dept.class);
	}
	@RequestMapping("/consumer/dept/list")
	public List<Dept> list() {
		return restTemplate.getForObject(REST_URL_PREFIX+"/dept/list", List.class);
	}
	// 测试@EnableDiscoveryClient,消费端可以调用服务发现
	@RequestMapping(value = "/consumer/dept/discovery")
	public Object discovery()
	{
		return restTemplate.getForObject(REST_URL_PREFIX + "/dept/discovery", Object.class);
	}

}
