package com.ebs.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ebs.beans.EHM;
import com.ebs.beans.EHP;
import com.ebs.beans.EmpIncr;
import com.ebs.beans.Employee;
import com.ebs.beans.Project;


@Component
public class Account {
	private NamedParameterJdbcTemplate jdbc;
	private PasswordEncoder passenc;
	private Employee employee;
	private Project project ;
	
	@Autowired
	public void setProject(Project project) {
		this.project = project;
	}


	@Autowired
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
	public void setPassenc(PasswordEncoder passenc) {
		this.passenc = passenc;
	}

	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Employee> getAllEmployees() {
		// Extract all employees from DB
		return jdbc.query("select * from employee", new RowMapper<Employee>(){

			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee e=new Employee();
				e.setId(rs.getInt("emp_id"));
				e.setName(rs.getString("emp_name"));
				e.setEmail(rs.getString("emp_email"));
				e.setAddress(rs.getString("emp_address"));
				e.setJoin_date(rs.getString("emp_joindate"));
				e.setLeave_date(rs.getString("emp_leavedate"));
				e.setSalary(rs.getString("emp_salary"));
				e.setUsername(rs.getString("username"));
				e.setJob_title(rs.getString("emp_job_title"));
				return e;
			}
			
		});
		
	}


	public List<Project> getAllProjects() {
		// Extract all projects from DB
				return jdbc.query("select * from project", new RowMapper<Project>(){

					@Override
					public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
						Project p =new Project();
						p.setId(rs.getInt("proj_id"));
						p.setName(rs.getString("proj_name"));
						p.setStart_date(rs.getString("proj_start_date"));
						p.setEstimated_end_date(rs.getString("proj_estimated_end_date"));
						p.setEnd_date(rs.getString("proj_end_date"));
						p.setClient_name(rs.getString("proj_client_name"));
						p.setBudget(rs.getString("budget"));
						p.setStatus(rs.getString("status"));
						return p;
					}
					
				});
	}


	public void addEmployee(Employee e) {
		// Insert e into DB
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("emp_name", e.getName());
		map.addValue("emp_email", e.getEmail());
		map.addValue("emp_address", e.getAddress());
		map.addValue("emp_joindate", e.getJoin_date());
		map.addValue("emp_leavedate", e.getLeave_date());
		map.addValue("emp_salary", e.getSalary());
		map.addValue("emp_job_title", e.getJob_title());
		map.addValue("username", e.getEmail());
		map.addValue("password", "000");
		map.addValue("authority", "employee");
		map.addValue("enabled", "true");

		jdbc.update("insert into employee(emp_name,emp_email,emp_address,emp_joindate,emp_leavedate,emp_salary,emp_job_title,username)"
				+ "values (:emp_name,:emp_email,:emp_address,:emp_joindate,:emp_leavedate,:emp_salary,:emp_job_title,:username)", map);
		jdbc.update("insert into users(username,password,enabled) values (:emp_email,:password,:enabled)", map);
		jdbc.update("insert into authorities(username,authority) values (:emp_email,:authority)", map);
	}


	public void addProject(Project p) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("name", p.getName());
		map.addValue("start_date", p.getStart_date());
		map.addValue("end_date", p.getEnd_date());
		map.addValue("estimated_end_date", p.getEstimated_end_date());
		map.addValue("client_name", p.getClient_name());
		map.addValue("mid",p.getManager_id());
		map.addValue("budget", p.getBudget());
		map.addValue("status", p.getStatus());

		jdbc.update("insert into project(proj_name,proj_start_date,proj_end_date,proj_estimated_end_date,proj_client_name,mid,budget,status) "
				+ "values (:name,:start_date,:end_date,:estimated_end_date,:client_name,:mid,:budget,:status)", map);
		
	}


	public void assignEmployeeToProject(String emp_id, String proj_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("proj_id", proj_id);
		map.addValue("emp_id", emp_id);
		map.addValue("date_from", DateFormat.getDateInstance().format(new Date()));
		map.addValue("date_to", null);
		map.addValue("role", "employee");
		
		
		jdbc.update("insert into employee_has_project(emp_id,proj_id,date_from,date_to,role)"
				+ "values (:emp_id,:proj_id,:date_from,:date_to,:role)", map);
	}
	
	

	public void assignManager(String pid, String eid) {
		// insert pid,eid in DB
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pid", pid);
		map.addValue("eid", eid);
		map.addValue("date_from", DateFormat.getDateInstance().format(new Date()));
		map.addValue("date_to", null);
		map.addValue("role", "manager");
		
		
		jdbc.update("insert into employee_has_project(emp_id,proj_id,date_from,date_to,role)"
				+ "values (:eid,:pid,:date_from,:date_to,:role)", map);
		
	}


	public void changeManager(String pid, String eid) {
		// update pid,eid in DB
				MapSqlParameterSource map = new MapSqlParameterSource();
				map.addValue("pid", pid);
				map.addValue("eid", eid);
				map.addValue("role", "manager");
				
				jdbc.update("update employee_has_project set emp_id=:eid where proj_id=:pid AND role=:role", map);
		
	}


	public List<EHP> fetch(String pid2) {
		// Extract all projects from DB
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pid", pid2);
		return jdbc.query("select * from employee_has_project where proj_id=:pid", map,new RowMapper<EHP>(){

			@Override
			public EHP mapRow(ResultSet rs, int rowNum) throws SQLException {
				EHP p =new EHP();
				p.setEid(Integer.toString(rs.getInt("emp_id")));
				p.setPid(Integer.toString(rs.getInt("proj_id")));
				p.setDate_from(rs.getString("date_from"));
				p.setDate_to(rs.getString("date_to"));
				p.setRole(rs.getString("role"));
				return p;
			}
			
		});
		
	}
	

	public void incrementSalary(String eid, String sal) {
		// update eid,salary in DB
		MapSqlParameterSource map = new MapSqlParameterSource();
		int s = Integer.parseInt(sal);
		float s1 = s / 100; //0.02
		map.addValue("eid", eid);
		map.addValue("salary", s);
		jdbc.update("update employee set emp_salary= ((emp_salary * :salary)/100) + emp_salary  where emp_id=:eid", map);

		
	}
	
	public List<EmpIncr> getSalaryRecord(String eid) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("eid", eid);
		return jdbc.query("select * from salary_record where emp_id=:eid", map,new RowMapper<EmpIncr>(){

			@Override
			public EmpIncr mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmpIncr p =new EmpIncr();
				p.setEid(eid);
				p.setSalary(rs.getString("salary"));
				p.setDate(rs.getString("date"));
				return p;
			}
			
		});
		
	}

	/*
	public List<Product> getAllProducts(){
		return jdbc.query("select * from product", new RowMapper<Product>(){

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product p =new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getString("price"));
				p.setDesc(rs.getString("description"));
				return p;
			}
			
		});
		
	}


	@SuppressWarnings("unchecked")
	public List<Product> getAllProducts(){
		return sessionFactory.getCurrentSession().createQuery("from Product").list();
	}
	
	public Product getSingleProduct(int id){
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		return jdbc.queryForObject("select * from product where id=:id", map ,new RowMapper<Product>(){

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product p =new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getString("price"));
				p.setDesc(rs.getString("description"));
				return p;
			}
			
		});
	}
	
	public void addEmployee(Student s){
	//BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(v);
	MapSqlParameterSource map = new MapSqlParameterSource();
	map.addValue("name", s.getName());
	map.addValue("contact", s.getContact());
	map.addValue("email", s.getEmail());
	map.addValue("password", s.getPassword());
	map.addValue("p1", passenc.encode(s.getPassword()));
	map.addValue("authority", "student");
	map.addValue("enabled", "true");

	jdbc.update("insert into employee(name,contact,email,password) "
			+ "values (:name,:contact,:email,:password)", map);
	jdbc.update("insert into users(username,password,enabled) values (:email,:p1,:enabled)", map);
	jdbc.update("insert into authorities(username,authority) values (:email,:authority)", map);
	
}

public Student getEmployeeRecord(String currentUsername) {
	MapSqlParameterSource map = new MapSqlParameterSource();
	map.addValue("email", currentUsername);
	return jdbc.queryForObject("select * from employee where email=:email", map ,new RowMapper<Student>(){

		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student s =new Student();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setContact(rs.getString("contact"));
			s.setEmail(currentUsername);
			return s;
		}
		
	});
	
	
	
}
	*/


	public void unassignEmployeeToProject(String emp_id, String proj_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("proj_id",proj_id);
		map.addValue("emp_id", emp_id);
		jdbc.update("delete from employee_has_project where emp_id=:emp_id and proj_id=:proj_id ", map);
	
	}


	public Project getProjectid(String proj_name) {
		// TODO Auto-generated method stub
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("proj_name",proj_name);
		Project p= jdbc.queryForObject("select * from project where proj_name=:proj_name",map, new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum)throws SQLException {
			Project p=new Project();
			p.setId(rs.getInt("proj_id"));
			p.setName(rs.getString("proj_name"));
			p.setStart_date(rs.getString("proj_start_date"));
			p.setEstimated_end_date(rs.getString("proj_estimated_end_date"));
			p.setEnd_date(rs.getString("proj_end_date"));
			p.setClient_name(rs.getString("proj_client_name"));
			p.setBudget(rs.getString("budget"));
			p.setStatus(rs.getString("status"));
			return p;
			}
		});
		return p;
	}


	


	public Employee getEmployeeById(String e_id) {
		// TODO Auto-generated method stub
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		int emp_id=Integer.parseInt(e_id);
		map.addValue("emp_id",emp_id);
		return jdbc.queryForObject("select * from employee where emp_id=:emp_id", map ,new RowMapper<Employee>(){

			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee e=new Employee();
				e.setId(rs.getInt("emp_id"));
				e.setName(rs.getString("emp_name"));
				e.setEmail(rs.getString("emp_email"));
				e.setAddress(rs.getString("emp_address"));
				e.setJoin_date(rs.getString("emp_joindate"));
				e.setLeave_date(rs.getString("emp_leavedate"));
				e.setSalary(rs.getString("emp_salary"));
				e.setUsername(rs.getString("username"));
				e.setJob_title(rs.getString("emp_job_title"));
				return e;
			}
			
		});
		
	}


	public Project searchProjectByName(String proj_name) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("proj_name",proj_name);
		Project p= jdbc.queryForObject("select * from project where proj_name=:proj_name",map, new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum)throws SQLException {
			Project p=new Project();
			p.setId(rs.getInt("proj_id"));
			p.setName(rs.getString("proj_name"));
			p.setStart_date(rs.getString("proj_start_date"));
			p.setEstimated_end_date(rs.getString("proj_estimated_end_date"));
			p.setEnd_date(rs.getString("proj_end_date"));
			p.setClient_name(rs.getString("proj_client_name"));
			p.setBudget(rs.getString("budget"));
			p.setStatus(rs.getString("status"));
			return p;
			}
		});
		return p;
		
	}


	public Project searchClientByName(String proj_client_name) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("proj_client_name",proj_client_name);
		Project p= jdbc.queryForObject("select * from project where proj_client_name=:proj_client_name",map, new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum)throws SQLException {
			Project p=new Project();
			p.setId(rs.getInt("proj_id"));
			p.setName(rs.getString("proj_name"));
			p.setStart_date(rs.getString("proj_start_date"));
			p.setEstimated_end_date(rs.getString("proj_estimated_end_date"));
			p.setEnd_date(rs.getString("proj_end_date"));
			p.setClient_name(rs.getString("proj_client_name"));
			p.setBudget(rs.getString("budget"));
			p.setStatus(rs.getString("status"));
			return p;
			}
		});
		return p;
	}


	public Project searchProjectById(String proj_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("proj_id",proj_id);
		Project p= jdbc.queryForObject("select * from project where proj_id=:proj_id",map, new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum)throws SQLException {
			Project p=new Project();
			p.setId(rs.getInt("proj_id"));
			p.setName(rs.getString("proj_name"));
			p.setStart_date(rs.getString("proj_start_date"));
			p.setEstimated_end_date(rs.getString("proj_estimated_end_date"));
			p.setEnd_date(rs.getString("proj_end_date"));
			p.setClient_name(rs.getString("proj_client_name"));
			p.setBudget(rs.getString("budget"));
			p.setStatus(rs.getString("status"));
			return p;
			}
		});
		return p;
	}


	public void broadcast(ArrayList<String> elist, String message,int fromemp_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		MapSqlParameterSource map1=new MapSqlParameterSource();

		String date=DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
		map1.addValue("emp_id",fromemp_id);
		Employee e=jdbc.queryForObject("select * from employee where emp_id=:emp_id", map1 ,new RowMapper<Employee>(){

			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee e=new Employee();
				e.setId(rs.getInt("emp_id"));
				e.setName(rs.getString("emp_name"));
				e.setEmail(rs.getString("emp_email"));
				e.setAddress(rs.getString("emp_address"));
				e.setJoin_date(rs.getString("emp_joindate"));
				e.setLeave_date(rs.getString("emp_leavedate"));
				e.setSalary(rs.getString("emp_salary"));
				e.setUsername(rs.getString("username"));
				e.setJob_title(rs.getString("emp_job_title"));
				return e;
			}
			
		});
		map.addValue("username",e.getUsername());
		for(String toemp_id:elist)
		{
		map.addValue("toemp_id", toemp_id);
		map.addValue("fromemp_id",fromemp_id);
		map.addValue("message", message);
		map.addValue("date",date);
		jdbc.update("insert into employee_has_message(toemp_id,fromemp_id,message,date,username) values(:toemp_id,:fromemp_id,:message,:date,:username)", map);
		}
		
		
	}


	public void closure(String proj_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("proj_id",proj_id);
		Project p= jdbc.queryForObject("select * from project where proj_id=:proj_id",map, new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum)throws SQLException {
			Project p=new Project();
			p.setId(rs.getInt("proj_id"));
			p.setName(rs.getString("proj_name"));
			p.setStart_date(rs.getString("proj_start_date"));
			p.setEstimated_end_date(rs.getString("proj_estimated_end_date"));
			p.setEnd_date(rs.getString("proj_end_date"));
			p.setClient_name(rs.getString("proj_client_name"));
			p.setBudget(rs.getString("budget"));
			p.setStatus(rs.getString("status"));
			return p;
			}
		});
		
		map.addValue("name", p.getName());
		map.addValue("start_date", p.getStart_date());
		map.addValue("end_date", p.getEnd_date());
		map.addValue("estimated_end_date", p.getEstimated_end_date());
		map.addValue("client_name", p.getClient_name());
		map.addValue("budget", p.getBudget());
		map.addValue("status", p.getStatus());
		jdbc.update("delete from project where proj_id=:proj_id", map);
		
		
		
	}


	public void passwordChange(String username, String password) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("username",username);
		map.addValue("password",password );
		map.addValue("enabled",true);
		jdbc.update("update users set password=:password where username=:username",map);
		
		
	}


	public Employee getEmployeeByEmail(String username) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map = new MapSqlParameterSource();
		
		map.addValue("username",username);
		return jdbc.queryForObject("select * from employee where username=:username", map ,new RowMapper<Employee>(){

			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee e=new Employee();
				e.setId(rs.getInt("emp_id"));
				e.setName(rs.getString("emp_name"));
				e.setEmail(rs.getString("emp_email"));
				e.setAddress(rs.getString("emp_address"));
				e.setJoin_date(rs.getString("emp_joindate"));
				e.setLeave_date(rs.getString("emp_leavedate"));
				e.setSalary(rs.getString("emp_salary"));
				e.setUsername(rs.getString("username"));
				e.setJob_title(rs.getString("emp_job_title"));
				return e;
			}
			
		});
	}


	public ArrayList<EHM> check(int toemp_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("toemp_id", toemp_id);
		ArrayList<EHM> ehmlist=(ArrayList<EHM>)jdbc.query("select * from employee_has_message where toemp_id=:toemp_id", map,new RowMapper<EHM>(){

			@Override
			public EHM mapRow(ResultSet rs, int rowNum) throws SQLException {
				EHM e=new EHM();
				e.setToemp_id(Integer.toString(rs.getInt("toemp_id")));
				e.setFromemp_id(rs.getString("fromemp_id"));
				e.setMessage(rs.getString("message"));
				e.setDate(rs.getString("date"));
				e.setUsername(rs.getString("username"));
				return e;
				
	}
			
		});
		return ehmlist;
			}


	public ArrayList<EHM> getMessages(int toemp_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("toemp_id", toemp_id);
		ArrayList<EHM> ehmlist=(ArrayList)jdbc.query("select * from employee_has_message where toemp_id=:toemp_id", map,new RowMapper<EHM>(){

			@Override
			public EHM mapRow(ResultSet rs, int rowNum) throws SQLException {
				EHM e=new EHM();
				e.setToemp_id(Integer.toString(rs.getInt("toemp_id")));
				e.setFromemp_id(rs.getString("fromemp_id"));
				e.setMessage(rs.getString("message"));
				e.setDate(rs.getString("date"));
				e.setUsername(rs.getString("username"));
				return e;
				
	}
			
		});
		return ehmlist;
	}


	public ArrayList<EHM> deleteMessage(String message, String toemp_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("toemp_id", toemp_id);
		map.addValue("message",message);
		jdbc.update("delete from employee_has_message where message=:message and toemp_id=:toemp_id",map);
		ArrayList<EHM> ehmlist=(ArrayList)jdbc.query("select * from employee_has_message where toemp_id=:toemp_id", map,new RowMapper<EHM>(){

			@Override
			public EHM mapRow(ResultSet rs, int rowNum) throws SQLException {
				EHM e=new EHM();
				e.setToemp_id(Integer.toString(rs.getInt("toemp_id")));
				e.setFromemp_id(rs.getString("fromemp_id"));
				e.setMessage(rs.getString("message"));
				e.setDate(rs.getString("date"));
				return e;
				
	}
			
		});
		return ehmlist;
	}


	public void sendMessage(String toemp_id, String message,int fromemp_id,String username) {
		// TODO Auto-generated method stub
		
		MapSqlParameterSource map=new MapSqlParameterSource();
		MapSqlParameterSource map1=new MapSqlParameterSource();

		String date=DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
		map1.addValue("emp_id",fromemp_id);
	
		map.addValue("username",username);
		
		map.addValue("toemp_id", toemp_id);
		map.addValue("fromemp_id",fromemp_id);
		map.addValue("message", message);
		map.addValue("date",date);
		jdbc.update("insert into employee_has_message(toemp_id,fromemp_id,message,date,username) values(:toemp_id,:fromemp_id,:message,:date,:username)", map);
		
		
		
		
	}
	
	
}
