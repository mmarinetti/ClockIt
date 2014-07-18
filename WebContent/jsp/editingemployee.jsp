<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clock-It</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/verifySave.js"></script>
</head>
<body>
	<c:set var="curPage" scope="session" value="${currentPage}"/>
	<%@ include file="/jsp/header.jsp" %>
	<div id="container">
		<c:choose>
		<c:when test="${isAdmin == false}">
			<!-- NOT ALLOWED -->
			<h2>FORBIDDEN</h2>
		</c:when>
		<c:otherwise>
		<c:if test="${insertSuccess == false}">
			<script type="text/javascript">
				alert("Insert Employee Failed. Check connection." +
						" If not connected, reconnect and resubmit.");
			</script>
		</c:if>
		<c:if test="${deleteSuccess == false}">
			<script type="text/javascript">
			alert("Delete Employee Failed. Check connection." +
			" If not connected, reconnect and resubmit.");
			</script>
		</c:if>
		<div class="datagrid">
		<table>
		<thead>
			<tr>
				<th>ID</th>
			  	<th>First</th>
			  	<th>Last</th>
			  	<th>Age</th>
			  	<th>Gender</th>
			  	<th>Start Time</th>
			  	<th>End Time</th>
			  	<th>Hours Worked</th>
			  	<th>Total Hours</th>
			  	<th>Edit</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
			<td colspan="11">
			<div id="paging">
				<ul>
					<li><a href="page?page=first" class="active"><span>&lt;&lt; First</span></a></li>
					<li><a href="page?page=${curPage-10}"><span>&lt; 10</span></a></li>
					<li><a href="page?page=${curPage-5}"><span>&lt; 5</span></a></li>
					<li><a href="page?page=${curPage-1}"><span>&lt; 1</span></a></li>
					<li><a href="page?page=${curPage+1}"><span>1 &gt;</span></a></li>
					<li><a href="page?page=${curPage+5}"><span>5 &gt;</span></a></li>
					<li><a href="page?page=${curPage+10}"><span>10 &gt;</span></a></li>
				</ul>
			</div>
			</tr>
		</tfoot>
		<tbody>
			<c:set var="isAlt" scope="page" value="${false}"/>
			<c:forEach items="${employeeList}" var="item">
			  <c:choose>
			  <c:when test="${editing != item.employee_id}">
			   <c:choose>
			     <c:when test="${isAlt == true}">
			     	<tr class="alt">
			     	<c:set var="isAlt" scope="page" value="${false}"></c:set>
			     </c:when>
			     <c:otherwise>
			     	<tr>
			     	<c:set var="isAlt" scope="page" value="${true}"></c:set>
			     </c:otherwise>
			   </c:choose>
		         <td>${item.employee_id}
		         <td>${item.firstName}</td>
		         <td>${item.lastName}</td>
		         <td>${item.age}</td>
		         <c:choose>
		         <c:when test="${item.gender == true}">
		         	<td>Male</td>
		         </c:when>
		         <c:otherwise>
		         	<td>Female</td>
		         </c:otherwise>
		         </c:choose>
		         <c:choose>
		         <c:when test="${item.start_date == null}">
		         	<td>None</td>
		         </c:when>
		         <c:otherwise>
		         	<td>${item.start_date}</td>
		         </c:otherwise>
		         </c:choose>
		         <c:choose>
		         <c:when test="${item.end_date == null}">
		         	<td>None</td>
		         </c:when>
		         <c:otherwise>
		         	<td>${item.end_date}</td>
		         </c:otherwise>
		         </c:choose>
		         <td>${item.hoursWorked}</td>
		         <td>${item.totalHours}</td>
		         <td></td>
		       </tr>
		       </c:when>
		       <c:when test="${editing == item.employee_id}">
			       <c:choose>
				     <c:when test="${isAlt == true}">
				     	<tr class="alt">
				     	<c:set var="isAlt" scope="page" value="${false}"></c:set>
				     </c:when>
				     <c:otherwise>
				     	<tr>
				     	<c:set var="isAlt" scope="page" value="${true}"></c:set>
				     </c:otherwise>
				   </c:choose>
				   <html:form action="/save" onsubmit="return validate(this);">
			         <td>${item.employee_id}</td>
			         <td><html:text property="first" value="${item.firstName}" size="7"></html:text>
			         <td><html:text property="last" value="${item.lastName}" size="7"></html:text></td>
			         <td><html:text property="age" value="${item.age}" size="1"></html:text></td>
			         <td>
			         	<c:choose>
			         		<c:when test="${item.gender}">
			         			<html:select property="gender" value="male">
			         				<html:option value="male">Male</html:option>
									<html:option value="female">Female</html:option>
								</html:select>
			         		</c:when>
			         		<c:otherwise>
			         			<html:select property="gender" value="female">
			         				<html:option value="male">Male</html:option>
									<html:option value="female">Female</html:option>
								</html:select>
			         		</c:otherwise>
			         	</c:choose>
			         </td>
			         <c:choose>
				         <c:when test="${item.start_date == null}">
				         	<td>None</td>
				         </c:when>
				         <c:otherwise>
				         	<td>${item.start_date}</td>
				         </c:otherwise>
			         </c:choose>
			         <c:choose>
				         <c:when test="${item.end_date == null}">
				         	<td>None</td>
				         </c:when>
				         <c:otherwise>
				         	<td>${item.end_date}</td>
				         </c:otherwise>
			         </c:choose>
			         <td>${item.hoursWorked}</td>
			         <td><html:text property="totalHours" value="${item.totalHours}" size="1"></html:text></td>
			         <td>
			         	<html:text property="id" value="${item.employee_id}" style="display: none;"></html:text>
			         	<html:submit value="Save"></html:submit>
			         </td>
			       </tr>
			       </html:form>
		       </c:when>
		       </c:choose>
		     </c:forEach>
		</tbody>
		</table>
		</div>
		<div id="links">
			<a class="utilityLinks" href="${pageContext.request.contextPath}/jsp/addemployee.jsp">Add</a>
			<a class="utilityLinks" href="${pageContext.request.contextPath}/jsp/searchform.jsp">Search</a>
			<html:link styleClass="utilityLinks" action="/logout">Log Out</html:link>
		</div>
		</c:otherwise>
		</c:choose>
	</div>
</body>
</html>