package com.clockit.web.actions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.clockit.web.DatabaseAccess;


public class ClockOutAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ActionForward forward = null;
		
		// Get session
		HttpSession session = request.getSession();
		
		// Get username of user
		String username = (String) session.getAttribute("username");
		
		// If clocked out, display red
		// Else leave as is
		if(DatabaseAccess.clockOut(username)) {
			session.setAttribute("checkedIn", false);
			request.setAttribute("checkOutSuccess", true);
		} else {
			request.setAttribute("checkOutSuccess", false);
		}
		
		forward = mapping.findForward("success");
		return (forward);
	}

}
