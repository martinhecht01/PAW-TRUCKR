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
        <a class="navbar-brand" href="#">Truckr</a>
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

<div class="card w-75 mb-3 mt-5 formCard">
    <div class="card-body">
        <h5 class="card-title mb-3">Crear Viaje</h5>
<%--        <p class="card-text">Ingrese los detalles del viaje:</p>--%>

        <div class="mb-3">
            <label for="departureDate" class="form-label">Fecha de salida</label>
            <input type="date" class="form-control" id="departureDate" placeholder="DD/MM/AAAA">
        </div>

        <div class="mb-3">
            <label for="arrivalDate" class="form-label">Fecha de llegada</label>
            <input type="date" class="form-control" id="arrivalDate" placeholder="DD/MM/AAAA">
        </div>

        <div class="mb-3">
            <label for="origin" class="form-label">Origen</label>
            <input type="text" class="form-control" id="origin" placeholder="Buenos Aires (CABA)">
        </div>

        <div class="mb-3">
            <label for="destination" class="form-label">Destino</label>
            <input type="text" class="form-control" id="destination" placeholder="Cordoba">
        </div>

        <a href="#" class="btn btn-primary">Crear</a>
    </div>
</div>

</body>
</html>

<script>
</script>