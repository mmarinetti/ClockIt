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

public class SaveEmployeeAction extends Action {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ActionForward forward = null;
		DynaValidatorForm sform = (DynaValidatorForm)form;
		
		String id = (String)sform.get("id");
		String first = (String)sform.get("first");
		String last = (String)sform.get("last");
		String age = (String)sform.get("age");
		String gender = (String)sform.get("gender");
		String totalHours = (String)sform.get("totalHours");
		
		HttpSession session = request.getSession();
		
		try {			
			Employee e = new Employee();
			e.setEmployee_id(Integer.parseInt(id));
			e.setFirst_name(first);
			e.setLast_name(last);
			e.setAge(Integer.parseInt(age));
			if(gender.equals("male")) {
				e.setGender(true);
			} else {
				e.setGender(false);
			}
			e.setTotalHours(Integer.parseInt(totalHours));
			
			if(DatabaseAccess.updateEmployee(e)) {
				request.setAttribute("updateSuccess", true);
			} else {
				request.setAttribute("updateSuccess", false);
			}
			
			int pageNumber = (int)session.getAttribute("currentPage");
			// Get list of employees to display
			Collection<Employee> es = DatabaseAccess.getEmployees(pageNumber, 10);
			session.setAttribute("employeeList", es);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward = mapping.findForward("success");
		return (forward);
	}

}
