package com.clockit.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.clockit.web.DatabaseAccess;

public class ClockInAction extends Action {
	
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
		
		// If the user is clocked in, display green
		// Else keep as is
		if(DatabaseAccess.clockIn(username)) {
			session.setAttribute("checkedIn", true);
			request.setAttribute("checkInSuccess", true);
		} else {
			request.setAttribute("checkInSuccess", false);
		}
		
		forward = mapping.findForward("success");
		return (forward);
	}

}
