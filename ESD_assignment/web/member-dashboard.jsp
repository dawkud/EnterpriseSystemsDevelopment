<%-- 
    Document   : admin-dashboard
    Created on : 15-Nov-2017
    Author     : DAWID
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Member panel"/>
<%@include file="/WEB-INF/header.jspf"%>

<div class="container">
    <center><h1>Welcome to XYZ Drivers Association</h1></center>
    <center><h2><c:out value="${username}"/></h2></center>
    <br><br><br>

    <center><h2><c:out value="${DASH_MSG}"/></h2></center>
    <br><br>

    <c:choose>
        <c:when test="${!empty listPayments}">
            <%@include file="/WEB-INF/listMemberPaymentAndClaims.jspf"%>
        </c:when>
        <c:when test="${!empty checkOutstandingBalance}">
            <%@include file="/WEB-INF/checkOutstandingBalance.jspf"%>
        </c:when>
        <c:when test="${!empty submitClaim}">
            <%@include file="/WEB-INF/submitClaim.jspf"%>
        </c:when>
    </c:choose>

    <form class="form-signin" action="member-dashboard" method="POST">
        <h4 class="form-signin-heading">Member functions: </h4><br><p>
            <button name="function" value="checkOutstandingBalance" class="btn btn-primary" type="submit">Check outstanding balance</button><br><p>
            <button name="function" value="submitClaim" class="btn btn-primary" type="submit">Submit a claim</button><br><p>
            <button name="function" value="listPayments" class="btn btn-primary" type="submit">List claims and payments</button><br><p> <!-- all claims and payments -->
            <button name="function" value="changePassword" class="btn btn-primary" type="submit">Change password</button><br><p> 
    </form>

</div> <!-- /container -->
</body>
</html>
