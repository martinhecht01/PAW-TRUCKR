<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet">


<head>
    <title><spring:message code="Explore"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/JmB4xhT/Truckr-Logo.png">
</head>
<body class="bodyContent" style="height: 100%">
<components:navBar/>
<div class="d-flex pt-5" style="width: 100%; padding: 0 10% ">
  <div class="filterCard">
    <components:filters/>
  </div>
  <div class="tripCards">
    <c:forEach var="trip" items="${offers}">
      <a class="card mb-3 browseCards" href="<c:url value="/tripDetail?id=${trip.tripId}"/>" style="display: flex">
        <div class="row g-0">
          <div class="col-md-6">
            <img src="http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQNxLs9ztCGoYOAq9Lg-J6eEHaNgm1trwlfXEhXnKlvzgcztA7wunvdwbsd2vHmnORyvAYbsrpONdQxM2o96Ho" class="img-fluid rounded-start" style="width: 100%; object-fit: cover; object-position: left" alt="...">
          </div>
          <div class="col-md-6">
            <div class="card-body align-content-center">
              <table class="table table-striped">
                <tr>
                  <td><b><spring:message code="OriginDestination"/></b></td>
                  <td><c:out value="${trip.origin}-${trip.destination}"/></td>
                </tr>
                <tr>
                  <td><b><spring:message code="LicensePlate"/></b></td>
                  <td><c:out value="${trip.licensePlate}"/></td>
                </tr>
                <tr>
                  <td><b><spring:message code="Dates"/></b></td>
                  <td><c:out value="${trip.departureDate.dayOfMonth}/${trip.departureDate.monthValue}/${trip.departureDate.year} - ${trip.arrivalDate.dayOfMonth}/${trip.arrivalDate.monthValue}/${trip.arrivalDate.year}"/></td>
                </tr>
                <tr>
                  <td><b><spring:message code="AvailableVolume"/>:</b></td>
                  <td><c:out value="${trip.availableVolume}"/> m3</td>
                </tr>
                <tr>
                  <td><b><spring:message code="AvailableWeight"/>:</b></td>
                  <td><c:out value="${trip.availableWeight}"/> kg</td>
                </tr>
                <tr>
                  <td><b><spring:message code="Price"/>:</b></td>
                  <td>$<c:out value="${trip.price}"/></td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </a>
    </c:forEach>
  </div>
</div>
<div style="margin-top: auto">
  <components:waveDivider/>
  <components:footer/>
</div>
</body>
</html>