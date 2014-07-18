<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clock-It</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/searchForm.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/verifySearch.js"></script>
</head>

<body>
<%@ include file="/jsp/header.jsp" %>
<div id="container">
<h2>Search for an employee</h2>

	<html:form action="/search" onsubmit="return validate(this);">
		<div id="searchFormBox">
		<table>
			<tr>
				<td>First Name: </td>
				<td><html:text property="firstname"/></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><html:text property="lastname"/></td>
			</tr>
			<tr>
				<td>Employee Id: </td>
				<td><html:text property="id"/></td>
			</tr>
			<tr>
				<td>Gender:</td>
			<td class="fieldLeftAlign">
				<html:select property="gender" value="" styleId="genderSelect">
					<html:option value=""></html:option>
					<html:option value="male">Male</html:option>
					<html:option value="female">Female</html:option>
				</html:select>
			</td>
			</tr>
			<tr>
				<td>Currently working</td>
			<td class="fieldLeftAlign">
				<html:select property="working" value="" styleId="workingSelect">
					<html:option value=""></html:option>
					<html:option value="1">Yes</html:option>
					<html:option value="0">No</html:option>
				</html:select>
			</td>
			</tr>
			<tr>
				
			</tr>
		</table>
		</div>
		<c:choose>
		<c:when test="${isAdmin == true}">
			<a href="${pageContext.request.contextPath}/page?page=first" class="Button">Back</a>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/jsp/index.jsp" class="Button">Back</a>
		</c:otherwise>
		</c:choose>
		<!-- <td><html:reset styleClass="Button" value="Clear"/></td>-->
		<html:submit styleClass="submit" value="Submit"/>
	</html:form>
	
</div>
</body>
</html>