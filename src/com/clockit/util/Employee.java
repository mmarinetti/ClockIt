package com.clockit.util;

import java.sql.Timestamp;
import java.util.Date;

public class Employee {
	private int employee_id;
	private String first_name;
	private String last_name;
	private int age;
	private Timestamp start_date;
	private Timestamp end_date;
	private Boolean gender;
	private Boolean working;
	private int hoursWorked;
	private int totalHours;
	
	public int getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(int totalHours) {
		this.totalHours = totalHours;
	}
	public int getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getFirstName() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLastName() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public Boolean getWorking() {
		return working;
	}
	public void setWorking(Boolean working) {
		this.working = working;
	}
}
