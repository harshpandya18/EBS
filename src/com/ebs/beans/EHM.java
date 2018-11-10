package com.ebs.beans;

import org.springframework.stereotype.Component;

@Component
public class EHM {

	private String toemp_id;
	private String fromemp_id;
	private String date;
	private String message;
	private String username;
	public String getToemp_id() {
		return toemp_id;
	}
	public void setToemp_id(String toemp_id) {
		this.toemp_id = toemp_id;
	}
	public String getFromemp_id() {
		return fromemp_id;
	}
	public void setFromemp_id(String fromemp_id) {
		this.fromemp_id = fromemp_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
