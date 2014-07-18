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

public class RemoveEmployeeAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ActionForward forward = null;
		
		DynaValidatorForm rform = (DynaValidatorForm)form;
		
		String id = (String)rform.get("id");
		
		try {
			HttpSession session = request.getSession();
			Employee e = new Employee();
			e.setEmployee_id(Integer.parseInt(id));
			if(DatabaseAccess.deleteEmployee(e)) {
				request.setAttribute("deleteSuccess", true);
			} else {
				request.setAttribute("deleteSuccess", false);
			}
			int pageNumber = (int)session.getAttribute("currentPage");
			Collection<Employee> es = DatabaseAccess.getEmployees(pageNumber, 10);
			session.setAttribute("employeeList", es);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward = mapping.findForward("success");
		
		return (forward);
	}

}
