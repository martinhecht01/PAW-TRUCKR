<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="/css/main.css" rel="stylesheet"/>

<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


</head>
<body class="bodyContent">
<c:url value="/create" var="postPath"/>

<components:navBar/>
<form:form modelAttribute="tripForm" method="post" action="postPath">
    <div class="card w-75 mb-3 mt-5 formCard">
        <div class="card-body">
            <h5 class="card-title mb-3"><b>Crear Viaje</b></h5>

            <div class="mb-3">
                <form:label for="name" class="form-label" path="name">Nombre</form:label>
                <form:input type="text" class="form-control" placeholder="Pedro Gonzales" path="name"/>
            </div>

            <div class="mb-3">
                <form:label for="id" class="form-label" path="id">Cuit/Cuil</form:label>
                <form:input type="text" class="form-control" path="id" placeholder="00-00000000-0"/>
            </div>

            <div class="mb-3">
                <form:label for="email" class="form-label" path="email">Email</form:label>
                <form:input type="text" class="form-control" path="email" placeholder="name@gmail.com"/>
            </div>

            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label for="departureDate" path="departureDate" class="form-label">Fecha de salida</form:label>
                    <form:input type="date" class="form-control" path="departureDate" placeholder="DD/MM/AAAA"/>
                </div>

                <div class="mb-3 inlineFormInput">
                    <form:label for="arrivalDate" path="arrivalDate" class="form-label">Fecha de llegada</form:label>
                    <form:input type="date" class="form-control" path="arrivalDate" placeholder="DD/MM/AAAA"/>
                </div>
            </div>

            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label path="origin" for="origin" class="form-label">Origen</form:label>
                    <form:input type="text" class="form-control" path="origin" placeholder="Buenos Aires (CABA)"/>
                </div>


                <div class="mb-3 inlineFormInput">
                    <form:label path="destination" for="destination" class="form-label">Destino</form:label>
                    <form:input type="text" class="form-control" path="destination" placeholder="Cordoba (CBA)"/>
                </div>
            </div>

            <div class="mb-3">
                <form:label path="cargoType" class="form-label">Tipo de carga</form:label>
                <form:select class="form-select" path="cargoType">
                    <option selected>Elegi una opcion...</option>
                    <option value="1">Refrigerada</option>
                    <option value="2">Peligrosa</option>
                    <option value="3">Normal</option>
                </form:select>
            </div>

            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label path="availableVolume"  class="form-label">Volumen Disponible</form:label>
                    <div class="input-group">
                        <form:input type="text" class="form-control" path="availableVolume" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">m3</span>
                        </div>
                    </div>
                </div>

                <div class="mb-3 inlineFormInput">
                    <form:label path="availableWeight" for="origin" class="form-label">Peso disponible</form:label>
                    <div class="input-group">
                        <form:input type="text" class="form-control" path="availableWeight" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">kg</span>
                        </div>
                    </div>
                </div>
            </div>

            <a type="submit" class="btn btn-primary formButton">Crear</a>
        </div>
    </div>
</form:form>

</body>
</html>

<script>
</script>