package com.ebs.beans;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebs.model.Account;

@Component
public class Project {
	private int id;
	private String name;
	private String start_date;
	private String estimated_end_date;
	private String end_date;
	private String client_name;
	private String budget;
	private String status;
	private String manager_id;

	private Account account;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEstimated_end_date() {
		return estimated_end_date;
	}

	public void setEstimated_end_date(String estimated_end_date) {
		this.estimated_end_date = estimated_end_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Account getAccount() {
		return account;
	}
	
	@Autowired
	public void setAccount(Account account) {
		this.account = account;
	}
	
	

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public ArrayList<Project> getAllProjects() {
		// go to DB and extract all projects 
		ArrayList<Project> list =(ArrayList<Project>)account.getAllProjects();
		return list;
	}

	public void addProject(Project p) {
		// TODO Auto-generated method stub
		account.addProject(p);
		
	}
	

	public void assignManager(String pid, String eid) {
		//Go to DB and insert record
		account.assignManager(pid,eid);
		
	}

	public void changeManager(String pid, String eid) {
		//Go to DB and insert record
				account.changeManager(pid,eid);
		
	}

	public Project getProjectid(String search_project) {
		// TODO Auto-generated method stub
		Project p=account.getProjectid(search_project);
		return p;
	}

	public Project searchProjectByName(String p_name) {
		// TODO Auto-generated method stub
		Project p=account.searchProjectByName(p_name);
		return p;
	}
	
	public Project searchProjectById(String p_id) {
		// TODO Auto-generated method stub
		Project p=account.searchProjectById(p_id);
		return p;
	}

	public Project searchClientByName(String c_name) {
		// TODO Auto-generated method stub
		Project p=account.searchClientByName(c_name);
		return p;
	}

	public void broadcast(ArrayList<String> elist, String message,String username) {
		// TODO Auto-generated method stub
		Employee e=account.getEmployeeByEmail(username);
		System.out.println(e.getId());
		account.broadcast(elist, message,e.getId());
	}

	public void closure(String pid) {
		// TODO Auto-generated method stub
		account.closure(pid);
	}

}
