<%-- 
    Document   : 404
    Created on : 22-Nov-2017, 17:29:52
    Author     : dkude
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="404"/>
<%@include file="/WEB-INF/header.jspf"%>

<div class="container">
    <center><h2>The page you tried to access doesn't exist</h2></center><br>
    <center><h1>404</h1></center>
    <br><br><br>
    <form class="form-signin" action="login">
        <button type="submit" name="login" class="btn btn-primary">Login</button>
    </form>
    <form class="form-signin" action="registration.jsp">
        <button type="submit" class="btn btn-warning">Not a member? Register</button>
    </form>
</div> <!-- /container -->
</body>
</html>
