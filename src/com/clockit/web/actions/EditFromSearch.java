package com.clockit.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class EditFromSearch extends Action {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		ActionForward forward = null;
		
		DynaValidatorForm rform = (DynaValidatorForm)form;
		
		String id = (String)rform.get("id");
		request.setAttribute("editing", id);
		
		forward = mapping.findForward("success");
		return (forward);
	}

}
