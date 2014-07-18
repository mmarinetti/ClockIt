<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page errorPage="exception.jsp" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Add Employee</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addemployee.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/verifyAddEmployee.js"></script>
</head>
<body>
	<%@ include file="/jsp/header.jsp" %>
	<div id="container">
	<c:choose>
	<c:when test="${isAdmin == true}">
	<h3>Add New Employee</h3>
	<html:form action="/add" method="post" onsubmit="return validate(this);" focus="first">
	<div id="addEmployeeBox">
		<table>
			<tr>
				<td>First Name:</td>
				<td><html:text property="first"></html:text></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><html:text property="last"></html:text></td>
			</tr>
			<tr>
				<td>Age:</td>
				<td><html:text property="age"></html:text></td>
			</tr>
			<tr>
				<td>Gender:</td>
				<td>
					<html:select property="gender" value="" styleId="genderSelect">
						<html:option value="">Choose</html:option>
						<html:option value="male">Male</html:option>
						<html:option value="female">Female</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>User Name:</td>
				<td><html:text property="username"></html:text></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><html:text property="password"></html:text></td>
			</tr>
			<tr>
				<td>Password Confirm:</td>
				<td><html:text property="passwordConfirm"></html:text></td>
			</tr>
		</table>
		</div>
		<a class="Button" href="${pageContext.request.contextPath}/page?page=first">Back</a>
		<html:submit styleClass="submit" value="Add"/>
	</html:form>
	
	</c:when>
	<c:otherwise>
		<h3>Nope.</h3>
	</c:otherwise>
	</c:choose>
	</div>
</body>
</html>