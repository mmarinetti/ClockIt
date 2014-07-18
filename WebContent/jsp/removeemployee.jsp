<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page errorPage="exception.jsp" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/verifyRemoveEmployee.js"></script>
</head>
<body>
<%@ include file="/jsp/header.jsp" %>
<c:choose>
<c:when test="${isAdmin == true}">
	<h3>Remove Employee</h3>
	<html:form action="/remove" method="post" onsubmit="return validate(this);">
		First Name*: <html:text property="first"></html:text> <br/>
		Last Name*: <html:text property="last"></html:text> <br/>
		<html:submit value="Submit"/> <br/>
	</html:form>
</c:when>
<c:otherwise>
	<h3>Nope.</h3>
</c:otherwise>
</c:choose>
</body>
</html>