package com.greatlearning.employeemgmt.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.greatlearning.employeemgmt.model.Employee;
import com.greatlearning.employeemgmt.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService Service;
	
	/*
	 * Request Mapping
	 * /employees - GET
	 * /employees/new
	 * /employees/edit/{id}
	 * /employees/delete/{id}
	 * /employees - POST
	 * /employees/{id} - POST
	 */
	
	
	@GetMapping("/employees")
	public String getAllEmployee(Model model) {
		List<Employee> result = Service.getAllEmployees();
		model.addAttribute("employees", result);
		return "employees";
	}
	
	@GetMapping("/employees/new")
	public String addNewEmployee(Model model) {
		Employee emp = new Employee();
		model.addAttribute("employee", emp);
		return "create_employee";
	}
	
	@GetMapping("/employees/edit/{id}")
	public String updateEmployee(Model model, @PathVariable("id") Integer id) {
		Employee emp = Service.getEmployeeById(id);
		model.addAttribute("employee", emp);
		return "edit_employee";
	}
	
	@GetMapping("/employees/delete/{id}")
	public String deleteEmployee(@PathVariable("id") Integer id) {
		Service.deleteEmpById(id);
		return "redirect:/employees";
	}
	
	@PostMapping("/employees")
	public String addEmployee(@ModelAttribute(name = "employee")Employee emp) {
		Service.saveOrUpdate(emp);
		return "redirect:/employees";
	}
	
	@PostMapping("/employees/{id}")
	public String updateEmployee(@ModelAttribute(name = "employee")Employee emp, @PathVariable("id") Integer id) {
		Employee existingEmp = Service.getEmployeeById(id);
		if(existingEmp.getId() == emp.getId()) {
			existingEmp.setFirstName(emp.getFirstName());
			existingEmp.setFirstName(emp.getFirstName());
			existingEmp.setEmail(emp.getEmail());
		}
		Service.saveOrUpdate(existingEmp);
		return "redirect:/employees";
	}
	
}
