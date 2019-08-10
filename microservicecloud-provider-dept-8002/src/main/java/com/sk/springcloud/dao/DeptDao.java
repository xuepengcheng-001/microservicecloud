package com.sk.springcloud.dao;

import java.util.List;


import com.sk.springcloud.entitys.Dept;

import org.apache.ibatis.annotations.Mapper;
// 一定不能忘记加注解
@Mapper
public interface DeptDao {
	public boolean addDept(Dept dept);

	public Dept findById(Long id);

	public List<Dept> findAll();
}
