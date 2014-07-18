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

public class AddEmployeeAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ActionForward forward = null;
		
		DynaValidatorForm aform = (DynaValidatorForm)form;
		
		// Get info from form
		String first = (String)aform.get("first");
		String last = (String)aform.get("last");
		String age = (String)aform.get("age");
		String gender = (String)aform.get("gender");
		String username = (String)aform.get("username");
		String password = (String)aform.get("password");
		//String passConfirm = (String)aform.get("passwordConfirm");
		
		try {
			// Get session
			HttpSession session = request.getSession();
			// Set data on employee from form
			Employee e = new Employee();
			e.setFirst_name(first);
			e.setLast_name(last);
			e.setAge(Integer.parseInt(age));
			if(gender.equals("male")) {
				e.setGender(true);
			} else {
				e.setGender(false);
			}
			// Insert employee
			if(DatabaseAccess.insertEmployee(e, username, password)) {
				request.setAttribute("insertSuccess", true);
			} else {
				request.setAttribute("insertSuccess", false);
			}
			int pageNumber = (int)session.getAttribute("currentPage");
			// Get list of employees to display
			Collection<Employee> es = DatabaseAccess.getEmployees(pageNumber, 10);
			session.setAttribute("employees", es);
			session.setAttribute("employeeList", es);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward = mapping.findForward("success");
		
		return (forward);
	}

}
