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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/verifyRemoveEmployee.js"></script>
</head>
<body>
	<c:set var="curPage" scope="session" value="${currentPage}"/>
	<%@ include file="/jsp/header.jsp" %>
	<div id="container">
		<c:choose>
		<c:when test="${isAdmin == false}">
			<c:if test="${checkInSuccess == false}">
				<script type="text/javascript">
					alert("Check In Failed.");
				</script>
			</c:if>
			<c:if test="${checkOutSuccess == false}">
				<script type="text/javascript">
					alert("Check Out Failed.");
				</script>
			</c:if>
			<html:form styleClass="buttons" action="/clockin">
				<html:submit styleClass="checkInButton" value="Clock In">
			</html:submit>
			</html:form>
			<html:form styleClass="buttons" action="/clockout">
				<html:submit styleClass="checkOutButton" value="Clock Out"></html:submit>
			</html:form>
			<c:choose>
			<c:when test="${checkedIn == false}">
				<div id="statusBox" class="red">
					<p>Out</p>
				</div>
			</c:when>
			<c:otherwise>
				<div id="statusBox" class="green">
					<p>In</p>
				</div>
			</c:otherwise>
			</c:choose>
			<br/>
			<div id="links">
				<a class="utilityLinks" href="${pageContext.request.contextPath}/jsp/searchform.jsp">Search</a>
				<a class="utilityLinks" href="${pageContext.request.contextPath}/displayemployees">Display</a>
			<html:link styleClass="utilityLinks" action="/logout">Log Out</html:link>
		</div>
		</c:when>
		<c:otherwise>
		<c:choose>
		<c:when test="${insertSuccess == false}">
			<script type="text/javascript">
				alert("Insert Employee Failed. Check connection." +
						" If not connected, reconnect and resubmit.");
			</script>
		</c:when>
		<c:when test="${insertSuccess == true}">
			<div id="updateBox">
				<p>Insert Successful</p>
			</div>
		</c:when>
		</c:choose>
		<c:choose>
		<c:when test="${deleteSuccess == false}">
			<script type="text/javascript">
			alert("Delete Employee Failed. Check connection." +
			" If not connected, reconnect and resubmit.");
			</script>
		</c:when>
		<c:when test="${deleteSuccess == true}">
			<div id="updateBox">
				<p>Delete Successful</p>
			</div>
		</c:when>
		</c:choose>
		<c:choose>
			<c:when test="${updateSuccess == true}">
				<div id="updateBox">
					<p>Update Successful</p>
				</div>
			</c:when>
			<c:when test="${updateSuccess == false}">
				<script type="text/javascript">
					alert("Update Failed.");
				</script>
			</c:when>
		</c:choose>
		<div class="datagrid">
		<table>
		<thead>
			<tr>
			  	<th>First</th>
			  	<th>Last</th>
			  	<th>Start Time</th>
			  	<th>End Time</th>
			  	<th class="hoursColumn">Hours Worked</th>
			  	<th class="hoursColumn">Total Hours</th>
			  	<th>Edit</th>
			  	<th></th>
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
		         	<html:form action="/edit">
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