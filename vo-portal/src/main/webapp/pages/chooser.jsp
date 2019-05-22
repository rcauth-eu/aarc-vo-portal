<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:if test="${not empty vomses}">
<script type="text/javascript">
function redirect(voms) {

    var host = "${redirect_host}"
    //var redirect_url = "${redirect_url}"

    if (voms) {

        var volist = document.getElementById("volist");
        var vo = volist.options[volist.selectedIndex].text

        var roles = document.getElementById("roles").value;
        var fqan;
        if (roles) {
                fqan = vo + ":" + roles;
        } else {
                fqan = vo;
        }

        //window.location = host + "?voms_fqan=" + fqan + "&redirect_url=" + redirect_url;
        window.location = host + "?voname=" + fqan;

    } else {

         window.location = host

    }
}
</script>
</c:if>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VO Portal</title>
</head>

<body>

<h1>Welcome to the VO Portal!</h1>

<br><br><c:if test="${not empty vomses}">
Choose your vo from the list, optionally followed by the desired roles and capabilities:
<p>
FQAN :
<select name="volist" id="volist">
    <c:forEach var="vo" items="${vomses}"><option value="${vo}">${vo}</option></c:forEach>
</select>
:
<input id="roles" type="text" size="25" name="roles" />
<br><br>
<button onclick="redirect(true)">Get VOMS Proxy</button>
or
<button onclick="redirect(false)">Get Plain Proxy</button>
</c:if><c:if test="${empty vomses}">
<form action="${redirect_host}"><input type="submit" value="Get Proxy"></form>
</c:if>
</body>
</html>
