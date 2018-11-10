package com.ebs.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ebs.beans.EHM;
import com.ebs.beans.EHP;
import com.ebs.beans.Employee;
import com.ebs.beans.Project;


@Controller
public class ProjectController {

	private Project project;
	Employee employee;
	EHP ehp;
	public Project getProject() {
		return project;
	}
	@Autowired
	public void setProject(Project project ) {
		this.project = project;
	}
	
	
	public Employee getEmployee() {
		return employee;
	}
	
	@Autowired
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public EHP getEhp() {
		return ehp;
	}
	@Autowired
	public void setEhp(EHP ehp) {
		this.ehp = ehp;
	}
	@RequestMapping(value="/AddProject", method=RequestMethod.POST)
	public ModelAndView addProject(Model model,HttpServletRequest request,@ModelAttribute("proj") Project p){
		
		//go to DB with Project (Service Layer) and insert project
		project.addProject(p);
		//go to DB via Employee bean and get all employees 
		ArrayList<Employee> emp_list = employee.getAllEmployees();
		ArrayList<Project> proj_list = project.getAllProjects();
		request.getSession().setAttribute("emp_list", emp_list);
		request.getSession().setAttribute("proj_list", proj_list);

		ModelAndView mav = new ModelAndView();
		mav.addObject("proj", p);
		mav.setViewName("project");
		model.addAttribute("msg","Project: " + p.getName() + " Added to the system");
		return mav;
	}
	
	@RequestMapping(value="/showProjectReport",method=RequestMethod.POST)
	public ModelAndView showProjectReport(@RequestParam(value="pid") String pid, Model model){
		//go to DB via EHP class and fetch employee_has_project table 
		ArrayList<EHP> list = ehp.fetch(pid);
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("show_project_report");
		return mav;
	}
	
	@RequestMapping(value="/ChangeManager",method=RequestMethod.POST)
	public String changeManager(@RequestParam(value="pid") String pid, @RequestParam(value="eid") String eid, Model model){
		//go to DB via project and insert pid,eid,date in employee_has_project table 
		project.changeManager(pid,eid);
		model.addAttribute("msg", "Project Records Updated ");
		return "redirect:/project";
	}
	
	
	@RequestMapping(value="/Broadcast",method=RequestMethod.POST)
	public String broadCast(@RequestParam(value="project") String pid, @RequestParam(value="message") String message, Model model,HttpServletRequest request){
		String name=request.getUserPrincipal().getName();
	
		ArrayList<EHP> list=ehp.fetch(pid);
		ArrayList<String> elist=new ArrayList<String>();
		for(EHP e:list)
		{
		
			elist.add(e.getEid());
		}
		project.broadcast(elist, message,name);
		return "redirect:/project";
	}
	
	@RequestMapping(value="/Closure",method=RequestMethod.POST)
	public String closure(@RequestParam(value="project") String pid, Model model,HttpServletRequest request){
		//go to DB via project and insert pid,eid,date in employee_has_project table 
		project.closure(pid);
		ArrayList<Project> proj_list = project.getAllProjects();
		request.getSession().setAttribute("proj_list", proj_list);
		return "redirect:/project";
	}
	
	@RequestMapping(value="/SearchProjectByName",method=RequestMethod.POST)
	public String searchProjectByName(@RequestParam(value="search") String p_name, Model model){
		
		Project p=project.searchProjectByName(p_name);
		model.addAttribute("p", p);
		
		return "project_search";
	}
	
	@RequestMapping(value="/SearchProjectById",method=RequestMethod.POST)
	public String searchProjectById(@RequestParam(value="search") String p_id, Model model){
		
		Project p=project.searchProjectById(p_id);
		model.addAttribute("p", p);
		
		return "project_search";
	}
	
	@RequestMapping(value="/SearchClientByName",method=RequestMethod.POST)
	public String searchClientByName(@RequestParam(value="search") String c_name, Model model){
		
		Project p=project.searchClientByName(c_name);
		model.addAttribute("p", p);
		
		return "project_search";
	}
	
	@RequestMapping("/mymessages")
public String showMessage(HttpServletRequest request,Model model){
		String username=request.getUserPrincipal().getName();
		Employee e=employee.getEmployeeByEmail(username);
		ArrayList<EHM> ehmlist=employee.getMessages(e.getId());
		
		model.addAttribute("ehmlist",ehmlist);
		int count=0;
		for(EHM eh:ehmlist)
		{
		count++;	
		}
		System.out.println(count);
		request.getSession().setAttribute("count",count);
		return "user_messages";
	}
	
	@RequestMapping("/deletemessage")
public String deleteMessage(HttpServletRequest request,Model model,@RequestParam("toemp_id") String toemp_id,@RequestParam("message") String message){
		ArrayList<EHM> ehmlist=employee.deleteMessage(message,toemp_id);
		model.addAttribute("ehmlist",ehmlist);
		int count=0;
		for(EHM eh:ehmlist)
		{
		count++;	
		}
		request.getSession().setAttribute("count",count);
		return "user_messages";
	}
	
}

	
	
