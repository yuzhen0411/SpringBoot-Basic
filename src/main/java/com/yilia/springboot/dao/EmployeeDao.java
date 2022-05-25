package com.yilia.springboot.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yilia.springboot.entities.Department;
import com.yilia.springboot.entities.Employee;



@Repository
public class EmployeeDao {

	private static Map<Integer, Employee> employees = null;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	static{
		employees = new HashMap<Integer, Employee>();

		employees.put(201, new Employee(201, "Huang", "benson@gmail.com", 1, new Department(101, "A101"),"1990-03-11"));
		employees.put(202, new Employee(202, "Lee", "iron@gmail.com", 1, new Department(102, "B102"),"1985-06-12"));
		employees.put(203, new Employee(203, "Su", "gina@gmail.com", 0, new Department(103, "A103"),"1970-07-30"));
		employees.put(204, new Employee(204, "Chen", "helen@gmail.com", 0, new Department(104, "A102"),"1973-10-05"));
		employees.put(205, new Employee(205, "Chian", "kai@gmail.com", 1, new Department(105, "B101"),"1982-08-23"));
	}
	
	private static Integer initId = 206;
	
	public void save(Employee employee){
		if(employee.getId() == null){
			employee.setId(initId++);
		}
		
		employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
		//若emp id不為空，則將該員工資料重新put到map裡
		employees.put(employee.getId(), employee);
	}
	
	public Collection<Employee> getAll(){
		return employees.values();
	}
	
	public Employee get(Integer id){
		return employees.get(id);
	}
	
	public void delete(Integer id){
		employees.remove(id);
	}
}
