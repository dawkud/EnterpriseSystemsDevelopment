<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>XYZ Drivers :: Admin</title>
    </head>

        <body>
            <%@include  file="navigation.jsp" %>
<h2>${dashboardMessage}</h2>

    <form action="adminDashboard" method="POST">
        <button name="act" value="listMembers" type="submit" class="btn btn-primary">List all members</button><br>
        <button name="act" value="listBalances" type="submit" class="btn btn-primary">List outstanding balances</button><br>
        <button name="act" value="listClaims" type="submit" class="btn btn-primary">List all claims</button><br>
        <button name="act" value="listProvisionals" type="submit" class="btn btn-primary">List all provisional member applications</button><br>
        <button name="act" value="listApprovedClaims" type="submit" class="btn btn-primary">List all approved claims</button><br>
        <button name="act" value="listFinancialStats" type="submit" class="btn btn-primary">List financial statistics</button><br>
    </form>

<c:choose>
    <c:when test="${!empty listMembers}">
        <form action="adminDashboard" method="POST">
            <button name="act" value="chargeMembershipFee" type="submit" class="btn btn-primary">Charge Yearly Membership Fee(10GBP)</button>
        </form>

        <%@include file="/WEB-INF/admin/listMembers.jspf"%>
    </c:when>
    <c:when test="${!empty listClaims}">
        <%@include file="/WEB-INF/admin/listClaims.jspf"%>
    </c:when>
    <c:when test="${!empty listBalances}">
        <%@include file="/WEB-INF/admin/listBalances.jspf"%>
    </c:when>
    <c:when test="${!empty listProvisionals}">
        <%@include file="/WEB-INF/admin/listProvisionals.jspf"%>
    </c:when>
    <c:when test="${!empty listApprovedClaims}">
        <%@include file="/WEB-INF/admin/listApprovedClaims.jspf"%>
    </c:when>
    <c:when test="${!empty numUsers}">
        <%@include file="/WEB-INF/admin/financialStatistics.jspf"%>
    </c:when>
</c:choose>

<div>${financialStats}</div>

<%@include file="/WEB-INF/footer.jspf"%>
