<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet">

<head>
    <title><spring:message code="TripDetails"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>

<body class="bodyContent">

<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="success" viewBox="0 0 16 16">
        <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"></path>
    </symbol>
</svg>

<components:navBar/>
<div class="m-auto text-center mt-5">
    <svg style="fill: green" width="200" height="200"><use xlink:href="#success"></use></svg>
    <h2 class="mt-5"><spring:message code="TripAcceptSuccess"/></h2>
</div>
<div class="card inlineFormInputContainer m-auto w-50">
    <div class="card-header">
        <h4 class="card-title"><b><spring:message code="Details"/></b></h4>
    </div>
    <div class="card-body">
        <table class="table table-striped">
            <tr>
                <td><b><spring:message code="CreateTripCargoType"/></b></td>
                <td><spring:message code="${request.type}" htmlEscape="true"/></td>
            </tr>
            <tr>
                <td><b><spring:message code="Origin"/>-<spring:message code="Destination"/></b></td>
                <td><c:out value="${request.origin}-${request.destination}"/></td>
            </tr>
            <tr>
                <td><b><spring:message code="DepartureDate"/> - <spring:message code="FiltersArrival"/></b></td>
                <td><${request.departureDate} - ${request.arrivalDate}</td>
            </tr>
            <tr>
                <td><b><spring:message code="AvailableVolume"/></b></td>
                <td><c:out value="${request.volume}"/> m3</td>
            </tr>
            <tr>
                <td><b><spring:message code="AvailableWeight"/></b></td>
                <td><c:out value="${request.weight}"/> kg</td>
            </tr>
            <tr>
                <td><b><spring:message code="Price"/></b></td>
                <td>$<c:out value="${request.price}"/></td>
            </tr>
        </table>
    </div>
</div>
<a href="<c:url value="/explore"/>"><button value="<spring:message code="GoBack"/>" class="btn btn-color mt-3 formButton"></button></a>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>