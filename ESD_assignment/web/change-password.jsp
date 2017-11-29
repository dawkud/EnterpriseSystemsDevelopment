<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Change password"/>
<%@include file="/WEB-INF/header.jspf"%>

<center><h2><c:out value="${DASH_MSG}"/></h2></center>
<br><br>

<div class="container">   
    <form class="form-signin" action="changePassword" method="POST">

        <h4 class="form-signin-heading"> </h4>
        <label for="currentPassword" class="sr-only">Current password</label>
        <input type="password" name="currentPassword" class="form-control" placeholder="Current password" required>
        <label for="newPassword" class="sr-only">New password</label>
        <input type="password" name="newPassword" class="form-control" placeholder="New password" required>
        <label for="confirmNewPassword" class="sr-only">Confirm new password</label>
        <input type="password" name="confirmNewPassword" class="form-control" placeholder="Confirm new password" required a>
        <button class="btn btn-primary" type="submit" name="submit">Change password</button>

    </form>
</div> <!-- /container -->
</body>
</html>
