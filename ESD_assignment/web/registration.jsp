<%-- 
    Document   : index
    Created on : 03-Nov-2017
    Author     : DAWID
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" scope="request" value="Registration"/>
<%@include file="/WEB-INF/header.jspf"%>

<div class="container">
    <center><h1>Welcome to XYZ Drivers Association</h1></center>
    <br><br><br>
    <form class="form-signin" action="registration" method="POST">

        <h4 class="form-signin-heading">Register your details</h4>
        <input type="text" name="firstName" class="form-control" placeholder="First name" value="${firstName}" required>
        <input type="text" name="lastName" class="form-control" placeholder="Last name" value="${lastName}" required>
        <div class="form-group">
            <c:if test="${empty addresses}">
                <c:if test="${manualAddress == true}">
                    <input type="text" name="address" class="form-control" placeholder="Address" value="${address}" required>
                </c:if>
                <c:if test="${manualAddress != true}">
                    <input name="postcode" type="text" class="form-control" placeholder="Enter postcode" required>
                    <button name="submit" type="submit" value="resolvePostcode" class="btn btn-primary">Check postcode</button>
                </c:if>
            </c:if>
            <c:if test="${!empty addresses}">
                <select class="form-control" name="address">
                    <c:forEach items="${addresses}" var="address">
                        <option>
                            <c:out value="${address}" />
                        </option>
                    </c:forEach>
                </select>
            </c:if>
        </div>
        <label for="dob" class="sr-only">Date of birth</label>
        <input type="text" name="dob" class="form-control" placeholder="Date of birth (YYYY-MM-DD)" value="${dob}" required>


        <c:if test="${(!empty addresses) || manualAddress == true}">
            <button name="submit" type="submit" value="generateUser" class="btn btn-primary">Submit</button>
        </c:if>
    </form>
</div> <!-- /container -->
</body>
</html>
