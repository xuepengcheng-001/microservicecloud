package com.sk.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sk.springcloud.entitys.Dept;
import com.sk.springcloud.service.DeptService;

@RestController
public class DeptController {

	@Autowired
	private DeptService deptService;

	@Autowired
	private DiscoveryClient client;

	// 两种写法都可以
	// @PostMapping("/dept/add")
	@RequestMapping(value = "/dept/add", method = RequestMethod.POST)
	public boolean add(@RequestBody Dept dept) {
		return deptService.add(dept);
	}

	/**
	 * @HystrixCommand ：在需要做熔断处理的方法上添加该注解 
	 * 		fallbackMethod = "processHystrix_Get"：指定出现错误或异常后的回调函数
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "processHystrix_Get")
	public Dept get(@PathVariable("id") Long id) {
		Dept dept = this.deptService.get(id);
		// 如果要查询的ID不存在则抛出异常，此时Hystrix的熔断机制会进行处理，调用fallbackMethod指定的方法。
		if (null == dept) {
			throw new RuntimeException("该ID：" + id + "没有没有对应的信息");
		}
		return dept;

	}

	@RequestMapping(value = "/dept/list", method = RequestMethod.GET)
	public List<Dept> get() {
		return deptService.list();
	}

	@RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
	public Object discovery() {
		List<String> list = client.getServices();
		System.out.println("**********" + list);

		List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
		for (ServiceInstance element : srvList) {
			System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
					+ element.getUri());
		}
		return this.client;
	}
	
	// 定义的熔断后的回调函数
	 public Dept processHystrix_Get(@PathVariable("id") Long id)
	  {
	   return new Dept().setDeptno(id)
	           .setDname("该ID："+id+"没有没有对应的信息,null--@HystrixCommand")
	           .setDb_source("no this database in MySQL");
	  }


}
