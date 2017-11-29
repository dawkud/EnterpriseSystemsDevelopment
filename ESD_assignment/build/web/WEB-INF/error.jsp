<%-- 
    Document   : error
    Created on : 15-Nov-2017
    Author     : DAWID
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/header.jspf"%>

<c:set var="title" scope="request" value="Error"/>

<div class="container">
    <center><h1>Error!</h1></center>
    <br><br><br>
    <center><h2>${ERR_MSG} error msg</h2></center>
    <br><br><br>
    <form class="form-signin" action="registration">
        <button type="submit" class="btn btn-primary btn-success btn-info">Not a Member? Register</button>
    </form>

</div> <!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
