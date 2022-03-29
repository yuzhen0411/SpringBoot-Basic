package com.yilia.springboot.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.yilia.springboot.dao.DepartmentDao;
import com.yilia.springboot.dao.EmployeeDao;
import com.yilia.springboot.entities.Department;
import com.yilia.springboot.entities.Employee;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	DepartmentDao departmentDao;
	
	@GetMapping("/emps")
	public String list(Model model) {
		Collection<Employee> employees = employeeDao.getAll();
		model.addAttribute("emps", employees);
		return "emp/list";
	}
	
	//turn to add page
	@GetMapping("/emp")
	public String toAddPage(Model model) {
		//list all departments option
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("depts", departments);
		return "emp/add";
	}
	
	//add employee
	@PostMapping("/emp")
	public String addEmp(Employee employee) {
		System.out.println("已新增員工: " + employee);
		employeeDao.save(employee);
		return"redirect:/emps";
	}
	
	//turn to edit page
	@GetMapping("/emp/{id}")
	public String toEditPage(@PathVariable("id") Integer id, Model model) {
		Employee employee = employeeDao.get(id);
		model.addAttribute("emp", employee);
		
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("depts", departments);
		return "emp/add";		
	}
	
	@PutMapping("/emp")
	public String updateEmployee(Employee employee) {
		System.out.println("更新後員工資訊: " + employee);
		employeeDao.save(employee);
		return "redirect:/emps";
	}
}
