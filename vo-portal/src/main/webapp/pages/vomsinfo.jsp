<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VO Portal</title>
</head>

<style type="text/css">
    .hidden {
        display: none;
    }

    .unhidden {
        display: block;
    }
</style>
<script type="text/javascript">
    function unhide(divID) {
        var item = document.getElementById(divID);
        if (item) {
            item.className = (item.className == 'hidden') ? 'unhidden' : 'hidden';
        }
    }
</script>

<body>

    <h1>You now possess the following proxy</h1>

    <p>

    Username:
    <pre>${username}</pre>

    <p>

    The results of voms-proxy-info -all:

    <p>

    <pre>${vomsinfo}</pre>

    <p>

    Your proxy certificate:

    <p>

    <ul>
        <li><a href="javascript:unhide('showCert');">Show/Hide Proxy</a></li>
    </ul>
    <div id="showCert" class="hidden">
        <p>
        <pre>${proxy}</pre>
    </div>

    <p>

    User information:
    <p>
    <ul>
        <li><a href="javascript:unhide('showUI');">Show/Hide user information</a>
    </ul>
    <div id="showUI" class="hidden">
        <pre>${userinfo}</pre>
    </div>
    <p>
    <hr>
    <p>
    <form name="input" action="${start}" method="get"/>
    <input type="submit" value="Return to client"/>
    </form>

</body>
</html>
