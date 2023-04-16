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
<div class="formCard justify-content-center align-items-center pt-5 mb-n5">
    <div class="inlineFormInputContainer">
        <div class="card inlineFormInputContainer" style="width: 40rem;">
            <div class="card-header">
                <h4 class="card-title"><b>Detalles</b></h4>
            </div>
            <div class="card-body">
                <img src="http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQNxLs9ztCGoYOAq9Lg-J6eEHaNgm1trwlfXEhXnKlvzgcztA7wunvdwbsd2vHmnORyvAYbsrpONdQxM2o96Ho" class="card-img rounded-start p-3"  alt="TruckImg">
                <table class="table table-striped">
                    <tr>
                        <td><b>Conductor</b></td>
                        <td>${user.name.toUpperCase()}</td>
                    </tr>
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
        <div class="inlineFormInputContainer justify-content-top align-items-top" >
            <form:form modelAttribute="acceptForm" action="${postPath}?id=${trip.tripId}" method="post">
                <div class="card browseCards" style="width: 20rem;">
                    <div class="card-header">
                        <h4 class="card-title" style="color: #142D4C"><b>Reservar viaje</b></h4>
                    </div>
                    <div class="card-body">
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
                            <input type="submit" class="btn btn-color" value="Reservar"/>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>