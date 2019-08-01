<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>VO Portal Error Page</title>
</head>

<body>
<H2>There was a problem getting a proxy</H2>

<c:if test="${not empty message}"><B>Message:</B>
<pre>${message}</pre>
<br></c:if><c:if test="${not empty cause}">
<B>Cause:</B>
<pre>${cause}</pre>
<br></c:if><c:if test="${not empty error}">
<B>Error:</B>
<pre>${error}</pre>
<br></c:if><c:if test="${not empty error_description}">
<B>Error description:</B>
<pre>${error_description}</pre>
<br></c:if><c:if test="${not empty state}">
<B>State:</B>
<pre>${state}</pre></c:if>

<br><br>

<form name="input" action="${action}" method="get"/>
<input type="submit" value="Return to client"/>
</form>

</body>
</html>
