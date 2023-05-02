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
<c:url var="resetPassword" value="/resetPassword"/>
<components:navBar/>
<div class="w-75 m-auto">
    <main class="form-signin m-auto">
        <div class="card py-1 mt-5">
            <div class="card-body">
                <form:form modelAttribute="userForm" action="${resetPassword}" method="post">
                    <%--    <img class="mb-4" src="../assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">--%>
                    <h1 class="h3 mb-3 text-center"><spring:message code="ResetPassword" /></h1>
                    <input hidden="hidden" value="${hash}" name="hash">
                    <div class="mb-3">
                        <form:label path="password" class="form-label">Password</form:label>
                        <form:errors cssClass="formError" path="password" element="p"/>
                        <form:input type="password" class="form-control" path="password" placeholder="Password"/>
                    </div>
                    <div class="mb-3">
                        <form:label path="repeatPassword" class="form-label">Confirm Password</form:label>
                        <form:errors cssClass="formError" path="repeatPassword" element="p"/>
                        <form:input type="password" class="form-control" path="repeatPassword" placeholder="Confirm Password"/>
                        <form:errors cssClass="formError"/>
                    </div>
                    <spring:message code="ResetPassword" var="value"/>
                    <input class="w-100 btn btn-lg btn-color" type="submit" value="${value}"/>
                </form:form>
            </div>
        </div>
    </main>
</div>
</body>
<components:waveDivider/>
<components:footer/>
</html>