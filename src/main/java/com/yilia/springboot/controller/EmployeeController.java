package com.yilia.springboot.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	//至員工新增頁面
	@GetMapping("/emp")
	public String toAddPage(Model model) {
		//部門選項中列出所有部門供選
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("depts", departments);
		
		return "emp/add";
	}
	
	//新增員工
	@PostMapping("/emp")
	public String addEmp(Employee employee) {
		
		employeeDao.save(employee);
		
		return"redirect:/emps";
	}
	
	//至員工編輯頁面
	@GetMapping("/emp/{id}")
	public String toEditPage(@PathVariable("id") Integer id, Model model) {
		Employee employee = employeeDao.get(id);
		model.addAttribute("emp", employee);
		
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("depts", departments);
		return "emp/add";		
	}
	
	//編輯員工，並儲存
	@PutMapping("/emp")
	public String updateEmployee(Employee employee) {
		employeeDao.save(employee);
		
		return "redirect:/emps";
	}
	
	//刪除員工
	@DeleteMapping("/emp/{id}")
	public String deleteEmployee(@PathVariable("id") Integer id) {
		employeeDao.delete(id);
		return "redirect:/emps";
	}
}
