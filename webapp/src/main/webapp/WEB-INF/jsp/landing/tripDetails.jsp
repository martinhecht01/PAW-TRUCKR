<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>


<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="/css/main.css" rel="stylesheet"/>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


</head>
<body class="bodyContent">

<c:url value="/accept" var="postPath"/>

<components:navBar/>

<form:form modelAttribute="acceptForm" action="${postPath}?id=${trip.tripId}" method="post">
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
                    <p class="card-text"><strong>Patente: </strong>${trip.licensePlate}</p>
                    <p class="card-text"><strong>Fecha partida: </strong>${trip.departureDate.year}-${trip.departureDate.monthValue}-${trip.departureDate.dayOfMonth} ${trip.departureDate.hour}:${trip.departureDate.minute}</p>
                    <p class="card-text"><strong>Fecha llegada: </strong>${trip.arrivalDate.year}-${trip.arrivalDate.monthValue}-${trip.arrivalDate.dayOfMonth} ${trip.arrivalDate.hour}:${trip.arrivalDate.minute}</p>
                    <p class="card-text"><strong>Volumen disponible: </strong>${trip.availableVolume} m3</p>
                    <p class="card-text"><strong>Peso disponible: </strong>${trip.availableWeight} kg</p>
                    <p class="card-text"><strong>Precio: </strong>$${trip.price}</p>
                        <div class="mb-3">
                            <form:label for="name" class="form-label" path="name">Nombre</form:label>
                            <form:errors path="name" cssClass="formError" element="p"/>
                            <form:input type="text" class="form-control" placeholder="Pedro Gonzales" path="name"/>
                        </div>

                        <div class="mb-3">
                            <form:label for="cuit" class="form-label" path="cuit">Cuit/Cuil</form:label>
                            <form:errors path="cuit" cssClass="formError" element="p"/>
                            <form:input type="text" class="form-control" path="cuit" placeholder="00-00000000-0"/>
                        </div>

                        <div class="mb-3">
                            <form:label for="email" class="form-label" path="email">Email</form:label>
                            <form:errors path="email" cssClass="formError" element="p"/>
                            <form:input type="text" class="form-control" path="email" placeholder="name@gmail.com"/>
                        </div>
                        <div>
                            <input type="submit" value="Accept Trip"/>
                        </div>

                </div>
            </div>
        </div>

    </div>
</div>
</form:form>

</body>
</html>