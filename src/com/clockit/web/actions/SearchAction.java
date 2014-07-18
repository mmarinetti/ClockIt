package com.clockit.web.actions;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.clockit.util.Employee;
import com.clockit.web.DatabaseAccess;

public class SearchAction extends Action {
	
	public static final String SQL_FIRST_NAME = "first_name";
	public static final String SQL_LAST_NAME = "last_name";
	public static final String SQL_ID = "employee_id";
	public static final String SQL_GENDER = "isMale";
	public static final String SQL_WORKING = "working";
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ActionForward forward = null;
		
		DynaValidatorForm sform = (DynaValidatorForm)form;
		String first = (String)sform.get("firstname");
		String last = (String)sform.get("lastname");
		String strId = (String)sform.get("id");
		String working = (String)sform.get("working");
		String gender = (String)sform.get("gender");
		
		// TODO: FIX THIS MESS
		// TODO: COMPRESS THIS DOWN TO ONE SQL STATEMENT
		
		try {
			HttpSession session = request.getSession();
			
			// Set id to -1 if the user did not enter a value
			int id = -1;
			if(!strId.isEmpty())
				id = Integer.parseInt((String)request.getParameter("id"));
				
			Collection<Employee> employees = new ArrayList<Employee>();
			// If the user did enter a value for ID, fetch all the employees with that id
			// and add them to the collection
			// Should only be one
			if(id != -1) {
				employees.addAll(DatabaseAccess.findByKeyword(SQL_ID, strId));
			}
			// If the user filled in the first name field...
			if(!first.isEmpty()) {
				// If the list is empty and the user did not enter in an ID,
				// just fill the list with the search results
				if(employees.isEmpty() && id == -1)
					employees.addAll(DatabaseAccess.findByKeyword(SQL_FIRST_NAME, first));
				// If there are employees in the list already, do a separate query search for the
				// first name field and check it against the existing list
				// Add employees that are in both lists to a new list
				else {
					Collection<Employee> eFirst = DatabaseAccess.findByKeyword(SQL_FIRST_NAME, first);
					Collection<Employee> newEmployees = new ArrayList<Employee>();
					for(Employee e : employees) {
						if(containsEmployee(eFirst, e)) {
							newEmployees.add(e);
						}
					}
					// Set the new list to the old list
					employees = newEmployees;
				}
			}
			// If the user filled in the last name field...
			if(!last.isEmpty()) {
				// If the list is empty and the user did not enter in an ID,
				// just fill the list with the search results
				// This means that either the id and/or first name searches returned nothing,
				// or the user did not fill these fields in
				if(employees.isEmpty() && id == -1)
					employees.addAll(DatabaseAccess.findByKeyword(SQL_LAST_NAME, last));
				// If there are employees in the list already, do a separate query search for the
				// last name field and check it against the existing list
				// Add employees that are in both lists to a new list
				else {
					Collection<Employee> eLast = DatabaseAccess.findByKeyword(SQL_LAST_NAME, last);
					Collection<Employee> newEmployees = new ArrayList<Employee>();
					for(Employee e : employees) {
						if(containsEmployee(eLast, e)) {
							newEmployees.add(e);
						}
					}
					// Set the new list to the old list
					employees = newEmployees;
				}
			}
			// If the user selected a gender...
			if(gender != null && !gender.isEmpty()) {
				// If the list is empty and the user did not enter in an ID,
				// just fill the list with the search results
				// This means that either the id and/or first name and/or last name
				// searches returned nothing, or the user did not fill these fields in
				if(employees.isEmpty() && id == -1)
					employees.addAll(DatabaseAccess.findByKeyword(SQL_GENDER, gender));
				// If there are employees in the list already, do a separate query search for the
				// gender field and check it against the existing list
				// Add employees that are in both lists to a new list
				else {
					Collection<Employee> eGender = DatabaseAccess.findByKeyword(SQL_GENDER, gender);
					Collection<Employee> newEmployees = new ArrayList<Employee>();
					for(Employee e : employees) {
						if(containsEmployee(eGender, e)) {
							newEmployees.add(e);
						}
					}
					// Set the new list to the old list
					employees = newEmployees;
				}
			}
			// If the user checked one of the working radio buttons
			if(working != null && !working.isEmpty()) {
				// If the list is empty and the user did not enter in an ID,
				// just fill the list with the search results
				// This means that either the id and/or first name and/or last name and/or gender
				// searches returned nothing, or the user did not fill these fields in
				if(employees.isEmpty() && id == -1)
					employees.addAll(DatabaseAccess.findByKeyword(SQL_WORKING, working));
				// If there are employees in the list already, do a separate query search for the
				// working field and check it against the existing list
				// Add employees that are in both lists to a new list
				else {
					Collection<Employee> eWorking = DatabaseAccess.findByKeyword(SQL_WORKING, working);
					Collection<Employee> newEmployees = new ArrayList<Employee>();
					for(Employee e : employees) {
						if(containsEmployee(eWorking, e)) {
							newEmployees.add(e);
						}
					}
					// Set the new list to the old list
					employees = newEmployees;
				}
			}
			session.setAttribute("employees", employees);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//sform.reset(mapping, request);
		
		forward = mapping.findForward("success");
		return (forward);
	}
	
	/**
	 * Does e1 contain an employee e2?
	 * @param e1 Collection to loop through
	 * @param e2 Employee
	 * @return false If e2 is not in the collection e1
	 */
	private boolean containsEmployee(Collection<Employee> e1, Employee e2) {
		for(Employee e : e1) {
			if(e.getEmployee_id() == e2.getEmployee_id())
				return true;
		}
		return false;
	}

}
