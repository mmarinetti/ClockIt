<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page errorPage="exception.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/displayemployees.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/verifyRemoveEmployee.js"></script>
<title>Employees</title>
</head>
<body>
<%@ include file="/jsp/header.jsp" %>
<c:set var="isAlt" scope="page" value="${false}"/>
<div id="container">
<h1>Employees</h1>
<div class="datagrid">
<table>
	<c:choose>
	<c:when test="${isAdmin == false}">
		<thead>
		<tr>
			<th>First</th>
			<th>Last</th>
			<th>Working</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${employees}" var="item">
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
				<td>${item.firstName}</td>
				<td>${item.lastName}</td>
				<td>${item.working}</td>
			</tr>
		</c:forEach>
		</tbody>
	</c:when>
	<c:otherwise>
		<thead>
			<tr>
				<th>First</th>
				<th>Last</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Hours Worked</th>
				<th>Total Hours</th>
				<th>Edit</th>
				<th></th>
			</tr>
		</thead>
			<tbody>
			<c:forEach items="${employees}" var="item">
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
					<td>${item.firstName}</td>
					<td>${item.lastName}</td>
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
					<td>
		         	<html:form action="/searchedit">
		         		<html:text property="id" value="${item.employee_id}" style="display: none;"></html:text>
		         		<html:submit value="Edit"></html:submit>
					</html:form>
		         	</td>
		         	<td>
						<html:form action="/remove" onsubmit="return confirmDelete(this);">
							<html:text property="id" value="${item.employee_id}" style="display: none;"></html:text>
							<html:submit value="X" styleClass="removeButton"></html:submit>
						</html:form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</c:otherwise>
	</c:choose>
</table>
</div>
<a class="utilityLinks" href="${pageContext.request.contextPath}/page?page=first">Home</a>
</div>
</body>
</html>