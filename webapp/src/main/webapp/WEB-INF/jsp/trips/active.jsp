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
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<body class="bodyContent" style="height: 100%">

<components:navBar/>

<c:if test="${trips.size()>0}">
    <h3 class="mt-5 mb-2 text-center"><spring:message code="TripsInProgress"/></h3>
    <div class="w-100 d-flex justify-content-center">
        <hr class="w-50">
    </div>
</c:if>
<div class="tripCards w-75 justify-content-center m-auto">

    <c:forEach var="trip" items="${trips}" varStatus="loop">
        <a class="text-decoration-none">
            <div class="card m-3" style="width: 25rem;">
                <img src="http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQNxLs9ztCGoYOAq9Lg-J6eEHaNgm1trwlfXEhXnKlvzgcztA7wunvdwbsd2vHmnORyvAYbsrpONdQxM2o96Ho" class="card-img-top" alt="...">
                <h4 class="mx-4 my-3 w-25 position-absolute top-0 start-0"><span class="badge rounded-pill text-bg-primary"><spring:message code="${trip.type}" htmlEscape="true"/></span></h4>
                <div class="card-body">
                    <div class="w-100 d-flex space-apart">
                        <div class="text-truncate text-center" style="width: 35%">
                            <h5><c:out value="${trip.origin}"/></h5>
                            <c:out value="${trip.departureDate.dayOfMonth}/${trip.departureDate.monthValue}/${trip.departureDate.year}"/>
                        </div>

                        <div style="width: 30%">
                            <svg width="9em" height="3em"><use xlink:href="#arrow"></use></svg>
                        </div>

                        <div class="text-truncate text-center" style="width: 35%">
                            <h5><c:out value="${trip.destination}"/></h5>
                            <c:out value="${trip.arrivalDate.dayOfMonth}/${trip.arrivalDate.monthValue}/${trip.arrivalDate.year}"/>
                        </div>
                    </div>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item px-5 pt-4 d-flex justify-content-between align-items-center">
                        <div class="text-center">
                            <h5><svg width="1em" height="1em"><use xlink:href="#heavy"></use></svg> <c:out value="${trip.availableWeight}"/> KG </h5>
                            <p><spring:message code="AvailableWeight"/></p>
                        </div>
                        <div class="text-center">
                            <h5><svg width="1em" height="1em"><use xlink:href="#volume"></use></svg> <c:out value="${trip.availableVolume}"/> M3 </h5>
                            <p><spring:message code="AvailableVolume"/></p>
                        </div>
                    </li>
                    <li class="list-group-item text-truncate text-center"><h4>$<c:out value="${trip.price}"/></h4></li>
                </ul>
            </div>
            <div class="card m-3" style="width: 25rem;">
            <ul class="list-group list-group-flush">
                <li class="list-group-item text-truncate"><b><spring:message code="Name"/>:</b> <c:out value="${truckers[loop.index].name}"/></li>
                <li class="list-group-item text-truncate"><b><spring:message code="Email"/>:</b> <c:out value="${truckers[loop.index].email}"/></li>
                <li class="list-group-item text-truncate"><b><spring:message code="Cuit"/>:</b> <c:out value="${truckers[loop.index].cuit}"/></li>
            </ul>
            </div>
        </a>

    </c:forEach>
</div>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>

