package com.clockit.web.actions;

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

public class LogonAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ActionForward forward = null;
		
		// Get username and password from log on form
		DynaValidatorForm lform = (DynaValidatorForm)form;
		String username = (String)lform.get("username");
		String password = (String)lform.get("password");
		
		// Get session
		HttpSession session = request.getSession();
		// If the username and password are in the database...
		if(DatabaseAccess.checkLogIn(username, password)) {
			// If the user is an admin...
			if(DatabaseAccess.isAdmin(username)) {
				// Set the admin attribute
				session.setAttribute("isAdmin", true);
				Collection<Employee> employees = DatabaseAccess.getEmployees(0, 10);
				session.setAttribute("employeeList", employees);
				session.setAttribute("currentPage", 0);
			} else { // The user is not an admin...
				session.setAttribute("isAdmin", false);
				// If the user is currently working...
				if(DatabaseAccess.checkWorking(username)) {
					session.setAttribute("checkedIn", true);
				} else {
					session.setAttribute("checkedIn", false);
				}
			}
			request.setAttribute("loginFail", false);
			forward = mapping.findForward("success");
		} else {
			request.setAttribute("loginFail", true);
			forward = mapping.findForward("failure");
		}
		
		session.setAttribute("username", username);
		return (forward);
	}

}
