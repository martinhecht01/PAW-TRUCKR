<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>
<html>
<head>
    <title><spring:message code="500Error"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<body class="bodyContent">
<components:navBar/>
<div class="container" style="display: flex; justify-content: center; align-items: center; height: 80vh">
    <div class="px-4 py-5 my-5 text-center">
        <spring:message code="${errorMsgCode}" var="msg"></spring:message>
        <h1 class="display-5 fw-bold text-body-emphasis"><c:out value="${errorCode} - ${msg}"></c:out> </h1>
        <div class="col-lg-6 mx-auto">
            <p class="lead mb-4"><spring:message code="TryAgain"/></p>
            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <button onclick="goBack()" type="button" class="btn btn-primary btn-lg px-4 gap-3"><spring:message code="GoBack"/></button>
            </div>
        </div>
    </div>
</div>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>

<script>
    function goBack() {
        window.history.back();
    }
</script>