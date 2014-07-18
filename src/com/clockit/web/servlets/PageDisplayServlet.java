package com.clockit.web.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.clockit.util.Employee;
import com.clockit.web.DatabaseAccess;

/**
 * Servlet implementation class PageDisplayServlet
 */
public class PageDisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public PageDisplayServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String strPageNumber = request.getParameter("page");
		
		ArrayList<Employee> employees;
		int pageNumber;
		try {
			pageNumber = Integer.parseInt(strPageNumber);
			if(pageNumber < 0) {
				pageNumber = 0;
			}
			int numOfEmp = DatabaseAccess.numberOfEmployees();
			if(pageNumber > numOfEmp / 10) {
				pageNumber = numOfEmp / 10;
			}
			employees = (ArrayList<Employee>) DatabaseAccess.getEmployees(pageNumber, 10);
			if(employees.size() == 0) {
				pageNumber--;
				employees = (ArrayList<Employee>)DatabaseAccess.getEmployees(pageNumber, 10);
			}
			session.setAttribute("currentPage", pageNumber);
		} catch (NumberFormatException e) {
			// first page
			employees = (ArrayList<Employee>) DatabaseAccess.getEmployees(0, 10);
			session.setAttribute("currentPage", 0);
		}
		
		session.setAttribute("employeeList", employees);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/index.jsp");
		dispatcher.forward(request,response);
	}

}
