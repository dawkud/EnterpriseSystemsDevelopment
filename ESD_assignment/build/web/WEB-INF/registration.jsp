<%-- 
    Document   : index
    Created on : 03-Nov-2017
    Author     : DAWID
--%>

<%@include file="/WEB-INF/header.jspf"%>
<c:set var="title" scope="request" value="Registration"/>
<${manualAddress = true}>

<div class="container">
    <center><h1>Welcome to XYZ Drivers Association</h1></center>
    <br><br><br>
    <form class="form-signin" action="registration" method="POST">

        <h4 class="form-signin-heading">Register your details</h4>
        <label for="firstName" class="sr-only">First name</label>
        <input type="text" name="firstName" class="form-control" placeholder="First name" value="${firstName}" required>
        <label for="lastName" class="sr-only">Last name</label>
        <input type="text" name="lastName" class="form-control" placeholder="Last name" value="${lastName}" required>
        <div class="form-group">
            <c:if test="${empty addresses}">
                <c:if test="${manualAddress == true}">
                    <label for="address" class="sr-only">Address</label>
                    <input type="text" name="address" class="form-control" placeholder="Address" value="${address}" required>
                </c:if>
                <c:if test="${manualAddress != true}">
                    <label for="address">Postcode</label>
                    <input name="postcode" type="text" class="form-control" placeholder="Enter postcode" required>
                    <button name="submit" type="submit" value="addressLookup" class="btn btn-info">Check postcode</button>
                </c:if>
            </c:if>
            <c:if test="${!empty addresses}">
                <label for="address">Select an address</label>
                <select class="form-control" name="address">
                    <c:forEach items="${addresses}" var="address">
                        <option>
                            <c:out value="${address}" />
                        </option>
                    </c:forEach>
                </select>
            </c:if>
        </div>
        <label for="dob" class="sr-only">Date of birth (yyyy-mm-dd)</label>
        <input type="text" name="dob" class="form-control" placeholder="Date of birth" value="${dob}" required>


        <c:if test="${(!empty addresses) || manualAddress == true}">
            <button name="submit" type="submit" value="generateUser" class="btn btn-primary">Submit</button>
        </c:if>
    </form>
</div> <!-- /container -->
</body>
</html>
