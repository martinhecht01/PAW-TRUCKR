<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="/css/main.css" rel="stylesheet"/>

<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>

<c:import url="../../tags/filters.tag">
  <c:param name="origin" value="${origin}" />
  <c:param name="destination" value="${destination}" />
  <c:param name="minAvailableVolume" value="${minAvailableVolume}" />
  <c:param name="minAvilableWeight" value="${origin}" />
  <c:param name="minPrice" value="${minPrice}" />
  <c:param name="maxPrice" value="${maxPrice}"/>
  <c:param name="departureDate" value="${departureDate}"/>
  <c:param name="arrivalDate" value="${arrivalDate}"/>
</c:import>

</head>
<body class="bodyContent" style="height: 100%">
<components:navBar/>
<div class="d-flex pt-5" style="width: 100%; padding: 0 10% ">
  <div class="filterCard">
    <components:filters/>
  </div>
  <div class="tripCards">
    <c:forEach var="trip" items="${offers}">
      <a class="card mb-3 browseCards" href="/tripDetail?id=${trip.tripId}" style="display: flex">
        <div class="row g-0">
          <div class="col-md-6">
            <img src="http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQNxLs9ztCGoYOAq9Lg-J6eEHaNgm1trwlfXEhXnKlvzgcztA7wunvdwbsd2vHmnORyvAYbsrpONdQxM2o96Ho" class="img-fluid rounded-start" style="height:100%; width: 100%; object-fit: cover; object-position: left" alt="...">
          </div>
          <div class="col-md-6">
            <div class="card-body align-content-center">
              <table class="table table-striped">
                <tr>
                  <td><b>Origen-Destino</b></td>
                  <td>${trip.origin}-${trip.destination}</td>
                </tr>
                <tr>
                  <td><b>Patente</b></td>
                  <td>${trip.licensePlate}</td>
                </tr>
                <tr>
                  <td><b>Fecha partida - llegada</b></td>
                  <td>${trip.departureDate.dayOfMonth}/${trip.departureDate.monthValue}/${trip.departureDate.year} - ${trip.arrivalDate.dayOfMonth}/${trip.arrivalDate.monthValue}/${trip.arrivalDate.year}</td>
                </tr>
                <tr>
                  <td><b>Volumen disponible:</b></td>
                  <td>${trip.availableVolume} m3</td>
                </tr>
                <tr>
                  <td><b>Peso disponible:</b></td>
                  <td>${trip.availableWeight} kg</td>
                </tr>
                <tr>
                  <td><b>Precio:</b></td>
                  <td>$${trip.price}</td>
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