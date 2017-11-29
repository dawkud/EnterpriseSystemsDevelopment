<%-- 
    Document   : admin-dashboard
    Created on : 15-Nov-2017
    Author     : DAWID
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" scope="request" value="Admin panel"/>
<%@include file="/WEB-INF/header.jspf"%>

<div class="container">
    <center><h1>Welcome to XYZ Drivers Association</h1></center>
    <center><h2><c:out value="${username}"/></h2></center>
    <br><br><br>

    <center><h2><c:out value="${DASH_MSG}"/></h2></center>
    <br><br>

    <c:choose>
        <c:when test="${!empty listMembers}">
            <%@include file="/WEB-INF/admin/listMembers.jspf"%>
        </c:when>
        <c:when test="${!empty listOutstandingBalances}">
            <%@include file="/WEB-INF/admin/listOutstandingBalances.jspf"%>
        </c:when>
        <c:when test="${!empty listClaims}">
            <%@include file="/WEB-INF/admin/listClaims.jspf"%>
        </c:when>
        <c:when test="${!empty listOutstandingClaims}">
            <%@include file="/WEB-INF/admin/listOutstandingClaims.jspf"%>
        </c:when>
        <c:when test="${!empty listProvisionalMembers}">
            <%@include file="/WEB-INF/admin/listProvisionalMembers.jspf"%>
        </c:when>
        <c:when test="${!empty annualTurnover}">
            <%@include file="/WEB-INF/admin/annualTurnover.jspf"%>
        </c:when>
    </c:choose>

    <form class="form-signin" action="admin-dashboard" method="POST">
        <h4 class="form-signin-heading">Admin functions</h4><br><p>
            <button name="function" value="listMembers" class="btn btn-primary" type="submit">List members</button><br><p>
            <button name="function" value="listOutstandingBalances" class="btn btn-primary" type="submit">List outstanding balances</button><br><p>
            <button name="function" value="listClaims" class="btn btn-primary" type="submit">List all claims</button><br><p>
            <button name="function" value="listOutstandingClaims" class="btn btn-primary" type="submit">List outstanding claims</button><br><p>
            <button name="function" value="listProvisionalMembers" class="btn btn-primary" type="submit">List provisional members</button><br><p>
            <button name="function" value="annualTurnover" class="btn btn-primary" type="submit">Turnover report</button><br><p> <!-- annual turnover report -->
            <button name="function" value="annualFee" class="btn btn-primary" type="submit">Charge annual fee (£10)</button><br><p> 
            <button name="function" value="membershipFee" class="btn btn-primary" type="submit">Charge membership fee (claims/users)</button><br><p> <!-- fee based on claims and users -->
    </form>
</div> <!-- /container -->
</body>
</html>
