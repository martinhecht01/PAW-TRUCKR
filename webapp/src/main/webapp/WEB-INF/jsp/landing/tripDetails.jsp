<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<c:set var="locator" value="new org.webjars.WebJarAssetLocator()" />
<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="/css/main.css" rel="stylesheet"/>
</head>
<body class="bodyContent">

<components:navBar/>

<div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card border-0 shadow-sm rounded-3">
        <div class="row no-gutters">
            <div class="col-md-4">
                <img src="http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQNxLs9ztCGoYOAq9Lg-J6eEHaNgm1trwlfXEhXnKlvzgcztA7wunvdwbsd2vHmnORyvAYbsrpONdQxM2o96Ho" class="card-img rounded-start" alt="Your Image">
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">Card Title</h5>
                    <p class="card-text"><strong>Conductor: </strong>${user.name.toUpperCase()}</p>
                    <p class="card-text"><strong>Origen: </strong>${trip.origin}</p>
                    <p class="card-text"><strong>Destino: </strong>${trip.destination}</p>
                    <p class="card-text"><strong>Fecha partida: </strong>${trip.departureDate.toString()}</p>
                    <p class="card-text"><strong>Fecha llegada: </strong>${trip.arrivalDate.toString()}</p>
                    <p class="card-text"><strong>Volumen disponible: </strong>${trip.availableVolume}</p>
                    <p class="card-text"><strong>Peso disponible: </strong>${trip.availableWeight}</p>
                    <div class="form-group mb-3">
                        <label for="emailInput"><strong>Email:</strong></label>
                        <input type="email" class="form-control" id="emailInput" placeholder="Ingrese su email">
                    </div>
                    <div class="text-end">
                        <button type="button" class="btn btn-primary mt-3">Hacer Reserva</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>