package com.yilia.springboot.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.yilia.springboot.entities.Department;

@Repository
public class DepartmentDao {

	private static Map<Integer, Department> departments = null;
	
	static{
		departments = new HashMap<Integer, Department>();
		
		departments.put(101, new Department(101, "A101"));
		departments.put(102, new Department(102, "A102"));
		departments.put(103, new Department(103, "A103"));
		departments.put(104, new Department(104, "B101"));
		departments.put(105, new Department(105, "B102"));
	}
	
	public Collection<Department> getDepartments(){
		return departments.values();
	}
	
	public Department getDepartment(Integer id){
		return departments.get(id);
	}
	
}
