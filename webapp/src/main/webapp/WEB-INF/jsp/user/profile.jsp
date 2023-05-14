<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<link>
<link href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"/>" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous"/>
<script src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"/>" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>
<link href="<c:url value="/css/userControl.css"/>" rel="stylesheet"/>


<head>
    <title>Truckr</title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png"></head>
<body class="bodyContent">
<components:navBar/>
<div class="w-75 m-auto pt-5">
    <main class="form-signin m-auto">
        <div class="card">
            <div class="card-header">
                <h4 class="card-title"><b><spring:message code="Profile"/></b></h4>
            </div>
            <div class="card-body">
                <div>
                    <h5><b><spring:message code="Name"/></b></h5>
                    <p><c:out value="${currentUser.getName()}"/></p>
                </div>
                <div>
                    <h5><b><spring:message code="Cuit"/></b></h5>
                    <p><c:out value="${currentUser.getCuit()}"/></p>
                </div>
                <div>
                    <h5><b><spring:message code="Email"/></b></h5>
                    <p><c:out value="${currentUser.getEmail()}"/></p>
                </div>
                <div>
                    <h5><b><spring:message code="Role"/></b></h5>
                    <p><c:out value="${currentRole}"/></p>
                </div>
                <div>
                    <a href="<c:url value="/profile/edit"/>" class="w-100 btn btn-lg btn-color"><spring:message code="editProfile"/></a>
                </div>
            </div>

        </div>
    </main>
</div>
<components:waveDivider/>
<components:footer/>
</body>
</html>