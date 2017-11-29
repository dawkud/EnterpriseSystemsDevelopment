<%-- 
    Document   : login
    Created on : 15-Nov-2017
    Author     : DAWID
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Login"/>
<%@include file="/WEB-INF/header.jspf"%>

<div class="container">
    <center><h1>Welcome to XYZ Drivers Association</h1></center>
    <br><br><br>
    <center><h3><c:out value="${LOGIN_MSG}"/></h3></center>
    <br><br><br>
    <form class="form-signin" action="login" method="POST">

        <h4 class="form-signin-heading">Please log in</h4>
        <label for="username" class="sr-only">Username</label>
        <input type="text" name="username" class="form-control" placeholder="Username" required autofocus>
        <label for="password" class="sr-only">Password</label>
        <input type="password" name="password" class="form-control" placeholder="Password" required>
        <button class="btn btn-primary" type="submit" name="submit">Sign in</button>

    </form>

    <br><br><br>
    <form class="form-signin" action="registration">
        <button type="submit" class="btn btn-warning">Not a member? Register</button>
    </form>

</div> <!-- /container -->
</body>
</html>