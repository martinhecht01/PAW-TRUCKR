<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet">

<head>
    <title><spring:message code="TripDetails"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/JmB4xhT/Truckr-Logo.png">
</head>
<body class="bodyContent">

<c:url value="/accept" var="postPath"/>
<components:navBar/>
<div class="formCard justify-content-center align-items-center pt-5 mb-n5">
    <div class="inlineFormInputContainer">
        <div class="card inlineFormInputContainer" style="width: 40rem;">
            <div class="card-header">
                <h4 class="card-title"><b><spring:message code="Details"/></b></h4>
            </div>
            <div class="card-body">
                <table class="table table-striped">
                    <tr>
                        <td><b><spring:message code="Driver"/></b></td>
                        <td><c:out value="${currentUser.name.toUpperCase()}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="Origin"/>-<spring:message code="Destination"/></b></td>
                        <td><c:out value="${request.origin}-${request.destination}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="DepartureDate"/> - <spring:message code="FiltersArrival"/></b></td>
                        <td><c:out value="${request.minDepartureDate.dayOfMonth}/${request.minDepartureDate.monthValue}/${request.minDepartureDate.year} - ${request.maxArrivalDate.dayOfMonth}/${request.maxArrivalDate.monthValue}/${request.maxArrivalDate.year}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="AvailableVolume"/></b></td>
                        <td><c:out value="${request.requestedVolume}"/> m3</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="AvailableWeight"/></b></td>
                        <td><c:out value="${request.requestedWeight}"/> kg</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="Price"/></b></td>
                        <td>$<c:out value="${request.maxPrice}"/></td>
                    </tr>
                </table>
            </div>
        </div>
<%--        <div class="inlineFormInputContainer justify-content-top align-items-top" >--%>
<%--            <form:form modelAttribute="acceptForm" action="${postPath}?id=${request.requestId}" method="post">--%>
<%--                <div class="card browseCards" style="width: 20rem;">--%>
<%--                    <div class="card-header">--%>
<%--                        <h4 class="card-title" style="color: #142D4C"><b><spring:message code="ReserveTrip"/></b></h4>--%>
<%--                    </div>--%>
<%--                    <div class="card-body">--%>
<%--                        <div class="mb-3">--%>
<%--                            <form:label for="name" class="form-label" path="name"><spring:message code="Name"/></form:label>--%>
<%--                            <form:errors path="name" cssClass="formError" element="p"/>--%>
<%--                            <spring:message code="NamePlaceholder" var="namePlaceholder"/>--%>
<%--                            <form:input type="text" class="form-control" placeholder="${namePlaceholder}" path="name"/>--%>
<%--                        </div>--%>

<%--                        <div class="mb-3">--%>
<%--                            <form:label for="cuit" class="form-label" path="cuit"><spring:message code="Cuit"/></form:label>--%>
<%--                            <form:errors path="cuit" cssClass="formError" element="p"/>--%>
<%--                            <form:input type="text" class="form-control" path="cuit" placeholder="00-00000000-0"/>--%>
<%--                        </div>--%>

<%--                        <div class="mb-3">--%>
<%--                            <form:label for="email" class="form-label" path="email"><spring:message code="Email"/></form:label>--%>
<%--                            <form:errors path="email" cssClass="formError" element="p"/>--%>
<%--                            <spring:message code="EmailPlaceHolder" var="emailPlaceholder"/>--%>
<%--                            <form:input type="text" class="form-control" path="email" placeholder="${emailPlaceholder}"/>--%>
<%--                        </div>--%>
<%--                        <div>--%>
<%--                            <spring:message code="Reserve" var="reserve"/>--%>
<%--                            <input type="submit" class="btn btn-color" value="${reserve}"/>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </form:form>--%>
<%--        </div>--%>
    </div>
</div>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>