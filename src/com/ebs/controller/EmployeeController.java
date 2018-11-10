package com.ebs.controller;

import java.util.ArrayList;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ebs.beans.EHP;
import com.ebs.beans.EmpIncr;
import com.ebs.beans.Employee;
import com.ebs.beans.Project;

@Controller
public class EmployeeController {
	
	Employee employee;
	Project project;
	EHP ehp;
	private TreeSet<Employee> list_search = new TreeSet<>();
	
	@Autowired
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	@Autowired
	public void setProject(Project project) {
		this.project = project;
	}

	
	@Autowired
	public void setEhp(EHP ehp) {
		this.ehp = ehp;
	}


	@RequestMapping(value="/AddEmployee",method=RequestMethod.POST)
	public String addEmployee(@ModelAttribute("emp") Employee e ,Model model, HttpServletRequest request){
		model.addAttribute("msg", "Employee " + e.getName()  +" is added to the System ");
		//Go to Account via Employee and insert record
		employee.addEmployee(e);
		@SuppressWarnings("unchecked")
		ArrayList<Employee> list = (ArrayList<Employee>)request.getSession().getAttribute("emp_list");
		list.add(e);
		request.getSession().setAttribute("emp_list", list);
		return "redirect:/employee";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/SearchEmployeeByName",method=RequestMethod.POST)
	public String searchEmployeeByName(Model model,HttpServletRequest request, @RequestParam(value="search") String search_name){
		//all the employees are already extracted in emp_list. 
				
		list_search.clear();
				ArrayList<Employee> elist = (ArrayList<Employee>)request.getSession().getAttribute("emp_list");
						for( Employee  e : elist){
							if(e.getName().contains(search_name)){
								list_search.add(e);
							}
						}
			model.addAttribute("search",search_name);	
			model.addAttribute("list_search",list_search);
			
			return "employee_search";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/SearchEmployeeByEmail",method=RequestMethod.POST)
	public String searchEmployeeByEmail(Model model,@RequestParam(value="search") String search_email,HttpServletRequest request){
		//all the employees are already extracted in emp_list. 
		model.addAttribute("search_email",search_email);
		
		list_search.clear();
		ArrayList<Employee> elist = (ArrayList<Employee>)request.getSession().getAttribute("emp_list");
				for( Employee  e : elist){
					if(e.getEmail().contains(search_email)){
						list_search.add(e);
					}
				}
	model.addAttribute("search",search_email);	
	model.addAttribute("list_search",list_search);
		
		return "employee_search";
	}
	
	@RequestMapping(value="SearchEmployeeByProject",method=RequestMethod.POST)
	public String searchEmployeeByProject(Model model,@RequestParam(value="search") String search_project,HttpServletRequest request){
		ArrayList<Employee> elist=new ArrayList<Employee>();
		Project p=project.getProjectid(search_project);
		int project_id=p.getId();
		String proj_id=Integer.toString(project_id);
		ArrayList<EHP> list=ehp.fetch(proj_id);
		for(EHP e :list)
		{
			Employee emp=employee.getEmployeeById(e.getEid());
			elist.add(emp);
		}
		model.addAttribute("list_search",elist);
		
		return "employee_search";
	}
	
	@RequestMapping(value="/AssignEmployeeToProject",method=RequestMethod.POST)
	public String assignEmployee(Model model,HttpServletRequest request,@RequestParam("eid") String emp_id,@RequestParam("pid") String proj_id){
	
		employee.assignEmployeeToProject(emp_id,proj_id);
		return "redirect:/employee";
	}
	
	@RequestMapping(value="/UnAssignEmployeeToProject",method=RequestMethod.POST)
	public String unassignEmployee(@RequestParam("employee") String emp_id,@RequestParam("project") String proj_id){
		employee.unassignEmployeeToProject(emp_id,proj_id);
		return "redirect:/employee";
	}
	

	@RequestMapping("/showIncrementReport")
	public String showIncrementReport(@RequestParam(value="eid") String eid,Model model){
		//go to DB via Employee and get record of all increments of eid from salary_record table
		ArrayList<EmpIncr> list  = employee.getSalaryRecord(eid);
		model.addAttribute("list",list);
		return "increment_report";
	}
	
	@RequestMapping("/IncrementSalary")
	public String incrementSalary(@RequestParam(value="eid") String eid,@RequestParam(value="increment_sal") String sal,Model model,HttpServletRequest request){
		//go to db via EMployee and update salary in employee and add record in salary_record using trigger
		employee.incrementSalary(eid,sal);
		model.addAttribute("msg", "Salary Updated");
		ArrayList<Employee> emp_list = employee.getAllEmployees();
		request.getSession().setAttribute("emp_list", emp_list);
		return "salary_record";
	}
	
	@RequestMapping("/SendEmployeeMessage")
	public String sendEmployeeMessage(@RequestParam("employee") String toemp_id,@RequestParam("message") String message,HttpServletRequest request){
		String username=request.getUserPrincipal().getName();
		Employee e=employee.getEmployeeByEmail(username);
		employee.sendMessage(toemp_id,message,e.getId(),username);
		return "redirect:/employee";
	}
}
