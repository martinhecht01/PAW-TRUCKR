<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet">

<head>
    <title><spring:message code="TripDetailsTitle"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<body class="bodyContent">

<svg  xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="check" viewBox="0 0 16 16">
        <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
    </symbol>
</svg>

<c:url value="/trips/sendProposal" var="postPath"/>
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
                        <td><b><spring:message code="Driver"/></b></td>
                        <td><c:out value="${user.name.toUpperCase()}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="CargoType"/></b></td>
                        <td><spring:message code="${trip.type}" htmlEscape="true" /></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="Origin"/> - <spring:message code="Destination"/></b></td>
                        <td><c:out value="${trip.origin}-${trip.destination}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="LicensePlate"/></b></td>
                        <td><c:out value="${trip.licensePlate}"/></td>
                    </tr>
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
        <c:if test="${trip.acceptUserId <= 0}">
            <div class="inlineFormInputContainer justify-content-top align-items-top" >
                <form:form modelAttribute="acceptForm" action="${postPath}?id=${trip.tripId}" method="post">
                    <div class="card browseCards" style="width: 20rem;">
                        <div class="card-header">
                            <h4 class="card-title" style="color: #142D4C"><b><spring:message code="ReserveTrip"/></b></h4>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <form:label for="description" class="form-label" path="description"><spring:message code="Description"/></form:label>
                                <form:textarea type="text" class="form-control" path="description" placeholder="Write a description"/>
                            </div>
                            <div>
                                <spring:message code="Reserve" var="reserve"/>
                                <input type="submit" class="btn btn-color" value="${reserve}"/>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </c:if>
        <c:if test="${trip.acceptUserId == userId}">
            <div class="justify-content-top align-items-top px-5" >
                <div class="card" style="width: 18rem;">
                    <div class="card-header">
                        <h4>Driver:</h4>
                    </div>
                    <div class="card-body p-3">
                        <h5 class="card-title"><c:out value="${user.name.toUpperCase()}"/></h5>
                        <p class="card-text"><c:out value="${user.email.toLowerCase()}"/></p>
                    </div>
                </div>
                <div class="card mt-4" style="width: 18rem;">
                    <div class="card-header">
                        <h4>Status:</h4>
                    </div>
                    <div class="card-body p-3">
                        <c:if test="${trip.receiverConfirmation && !trip.senderConfirmation}">
                            <p class="card-text py-1"><svg width="1em" height="1em" fill="green"><use xlink:href="#check"></use></svg> I received the cargo!</p>
                        </c:if>
                        <c:if test="${!trip.receiverConfirmation}">
                            <p class="card-text py-1"><svg width="1em" height="1em" fill="gray"><use xlink:href="#check"></use></svg> You didn't receive the cargo.</p>
                        </c:if>
                        <c:if test="${trip.senderConfirmation && !trip.receiverConfirmation}">
                            <p class="card-text py-1"><svg width="1em" height="1em" fill="green"><use xlink:href="#check"></use></svg> Driver finished this trip!</p>
                        </c:if>
                        <c:if test="${!trip.senderConfirmation}">
                            <p class="card-text py-1"><svg width="1em" height="1em" fill="gray"><use xlink:href="#check"></use></svg> Awaiting for driver to complete the trip.</p>
                        </c:if>
                        <c:if test="${trip.receiverConfirmation && trip.senderConfirmation}">
                            <h4 class="card-text py-1"><svg class="mx-2" width="2em" height="2em" fill="green"><use xlink:href="#check"></use></svg>Trip finished!</h4>
                        </c:if>
                    </div>
                </div>
                <c:if test="${trip.acceptUserId > 0 && !trip.receiverConfirmation}">
                    <c:url value="/trips/confirmTrip" var="confirmPath"/>
                    <form:form method="post" action="${confirmPath}?id=${trip.tripId}">
                        <input type="submit" class="btn btn-color mt-3 w-100" value="I received the cargo!"/>
                    </form:form>
                </c:if>
            </div>
        </c:if>

    </div>
</div>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>