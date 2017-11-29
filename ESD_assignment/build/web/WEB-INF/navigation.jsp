
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" crossorigin="anonymous">

<div class="container">
    <nav class="navbar navbar-default" style="margin-top:10px">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.jsp">XYZ Drivers Association</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="index.jsp">Home</a></li>

                    <c:choose>
                        <c:when test="${empty user}">
                           <li><a href="registration">Register</a></li>
                        </c:when>
                        <c:when test="${user.status == 'ADMIN'}">
                            <li><a href="adminDashboard">Dashboard</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="memberDashboard">Dashboard</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${empty user}">
                           <li><a href="login">Login</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="logout">Logout</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>

            </div>

    </nav>