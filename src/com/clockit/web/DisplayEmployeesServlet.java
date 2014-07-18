package com.clockit.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.clockit.util.Employee;

/**
 * Servlet implementation class DisplayEmployeesServlet
 */
public class DisplayEmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayEmployeesServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String to = "/jsp/displayemployees.jsp";
		try {
			HttpSession session = request.getSession();
			Collection<Employee> employees = DatabaseAccess.getEmployees(0, -1);
			session.setAttribute("employees", employees);
		} catch (Exception e) {
			e.printStackTrace();
			to = "/jsp/exception.jsp";
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(to);
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String to = "/jsp/displayemployees.jsp";
		try {
			HttpSession session = request.getSession();
			Collection<Employee> employees = DatabaseAccess.getEmployees(0, -1);
			session.setAttribute("employees", employees);
		} catch (Exception e) {
			e.printStackTrace();
			to = "/jsp/exception.jsp";
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(to);
		rd.forward(request, response);
	}

}
