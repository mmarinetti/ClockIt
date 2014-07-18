<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page errorPage="exception.jsp" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/loginvalidation.js"></script>
</head>
<body>
	<div id="container">
	<h2>Log In - Clock It</h2>
	<div id="logInBox">
		<html:form action="/logon" onsubmit="return validate(this);" focus="username">
			<span class="label">User Name: </span><html:text property="username" /><br/>
			<span class="label">Password: </span><html:password property="password"></html:password><br/>
			<html:submit styleClass="submitButton" value="Log In"/>
		</html:form>
	</div>
	</div>
</body>
</html>