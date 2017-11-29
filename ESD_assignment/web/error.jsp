<%-- 
    Document   : error
    Created on : 15-Nov-2017
    Author     : DAWID
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Error"/>
<%@include file="/WEB-INF/header.jspf"%>

<div class="container">
    <center><h1>Error!</h1></center>
    <br><br><br>
    <center><h2><c:out value="${ERR_MSG}"/></h2></center>
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
