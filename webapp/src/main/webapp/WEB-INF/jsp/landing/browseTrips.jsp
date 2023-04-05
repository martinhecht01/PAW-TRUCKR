<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="/css/main.css" rel="stylesheet"/>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">Truckr</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/browseTrips">Browse</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/createTrip">Create Trip</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
<div class="mt-5">
  <c:forEach var="offer" items="${offers}">
    <div class="card mb-3 browseCards" >
      <div class="row g-0">
        <div class="col-md-6">
          <img src="https://transportemundial.com.ar/wp-content/uploads/2018/09/scania-r450-6x2-highline-2.jpg" class="img-fluid rounded-start" alt="...">
        </div>
        <div class="col-md-6">
          <div class="card-body">
            <h5 class="card-title"></h5>
            <p class="card-text"><b>Salida: </b> ${offer.departureDate.toLocaleString()}</p>
            <p class="card-text"><b>Origen-Destino: </b>${offer.origin}-${offer.destination}</p>
            <p class="card-text"><b>Peso disponible: </b>${offer.availableWeight}kg</p>
            <p class="card-text"><b>Volumen disponible: </b>${offer.availableVolume}m3</p>
              <%--                            <p class="card-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>--%>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>
</div>


</body>
</html>

<script>
</script>