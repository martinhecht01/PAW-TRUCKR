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
                <img src="http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQNxLs9ztCGoYOAq9Lg-J6eEHaNgm1trwlfXEhXnKlvzgcztA7wunvdwbsd2vHmnORyvAYbsrpONdQxM2o96Ho" class="card-img rounded-start p-3"  alt="TruckImg">
                <table class="table table-striped">
                    <tr>
                        <td><b><spring:message code="Origin"/>-<spring:message code="Destination"/></b></td>
                        <td><c:out value="${trip.origin}-${trip.destination}"/></td>
                    </tr>
<%--                    <tr>--%>
<%--                        <td><b><spring:message code="LicensePlate"/></b></td>--%>
<%--                        <td><c:out value="${trip.licensePlate}"/></td>--%>
<%--                    </tr>--%>
                    <tr>
                        <td><b><spring:message code="DepartureDate"/> - <spring:message code="FiltersArrival"/></b></td>
                        <td><c:out value="${trip.departureDate.dayOfMonth}/${trip.departureDate.monthValue}/${trip.departureDate.year} - ${trip.arrivalDate.dayOfMonth}/${trip.arrivalDate.monthValue}/${trip.arrivalDate.year}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="AvailableVolume"/></b></td>
                        <td><c:out value="${trip.availableVolume}"/> m3</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="AvailableWeight"/></b></td>
                        <td><c:out value="${trip.availableWeight}"/> kg</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="Price"/></b></td>
                        <td>$<c:out value="${trip.price}"/></td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="justify-content-top align-items-top px-5" >
            <c:forEach var="offer" items="${offers}">
                <c:url value="/trips/acceptProposal" var="postPath"/>
                <form:form action="${postPath}?id=${offer.proposalid}" method="post">
                <div class="card p-3" style="width: 18rem;">
                    <div class="ca rd-body">
                        <h5 class="card-title"><c:out value="${offer.userName.toUpperCase()}"/></h5>
                        <p class="card-text"><c:out value="${offer.description}"/></p>
                        <spring:message code="Trips.AcceptProposal" var="reserve"/>
                        <input type="submit" class="btn btn-color" value="${reserve}"/>
                    </div>
                </div>
                </form:form>
            </c:forEach>
        </div>
    </div>
</div>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>