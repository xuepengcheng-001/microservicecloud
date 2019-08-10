package com.sk.springcloud.service;

import java.util.List;

import com.sk.springcloud.entitys.Dept;

public interface DeptService {

	public boolean add(Dept dept);

	public Dept get(Long id);

	public List<Dept> list();
}
