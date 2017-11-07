<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<spring:url value="/" var="urlHome" />
<spring:url value="/login" var="urlAddUser" />
<spring:url value="/logout" var="urlLogout" />

<nav>
	<div>
		<div>
			<a class="navbar-brand" href="${urlHome}">Ksiegarnia internetowa</a>
		</div>
		<div>
			<ul>
				<c:choose>
					<c:when test="${pageContext.request.userPrincipal.name != null}">
						<li class="active"><a href="${urlLogout}">Logout</a></li>
						<li class="active"><a href="${mojeDane}">Moje Dane</a></li>
					</c:when>
					<c:otherwise>
						<li class="active"><a href="${urlAddUser}">Login</a></li>
					</c:otherwise>
				</c:choose>

			</ul>
		</div>
	</div>
</nav>
