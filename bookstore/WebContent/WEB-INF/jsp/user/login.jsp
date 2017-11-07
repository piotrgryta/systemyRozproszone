<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title>Login Page</title>

</head>
<body>
	<div id="login-box">

		<h2>Login with Username/Email and Password</h2>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		
		<form:form method="POST" action="login" modelAttribute="userForm">		
		<%--   <form:form modelAttribute="userForm" class="form-signin" method = "POST"> --%>
			<h2 class="form-heading">Log in</h2>

			<div class="form-group ${error != null ? 'has-error' : ''}">
				<span>${message}</span> 
				<input name="username" type="text" class="form-control" placeholder="Email" autofocus="true" />
				<form:errors path="email"></form:errors>
				<input name="password" type="password" class="form-control" placeholder="Password" /> 
				<span>${error}</span> 
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<form:errors path="password"></form:errors>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
				<h4 class="text-center">
					<a href="registration">Create an account</a>
				</h4>
			</div>

		</form:form>
	</div>

</body>
</html>