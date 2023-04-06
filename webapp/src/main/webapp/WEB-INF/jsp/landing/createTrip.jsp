<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="/css/main.css" rel="stylesheet"/>

<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>

</head>
<body class="bodyContent">

<components:navBar/>

<div class="card w-75 mb-3 mt-5 formCard">
    <div class="card-body">
        <h5 class="card-title mb-3"><b>Crear Viaje</b></h5>
<%--        <p class="card-text">Ingrese los detalles del viaje:</p>--%>

        <div class="mb-3">
            <label for="name" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="name" placeholder="Pedro Gonzales">
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="text" class="form-control" id="email" placeholder="name@gmail.com">
        </div>

        <div class="inlineFormInputContainer">
            <div class="mb-3 inlineFormInput">
                <label for="departureDate" class="form-label">Fecha de salida</label>
                <input type="date" class="form-control" id="departureDate" placeholder="DD/MM/AAAA">
            </div>

            <div class="mb-3 inlineFormInput">
                <label for="arrivalDate" class="form-label">Fecha de llegada</label>
                <input type="date" class="form-control" id="arrivalDate" placeholder="DD/MM/AAAA">
            </div>
        </div>

        <div class="inlineFormInputContainer">
            <div class="mb-3 inlineFormInput">
                <label for="origin" class="form-label">Origen</label>
                <input type="text" class="form-control" id="origin" placeholder="Buenos Aires (CABA)">
            </div>


            <div class="mb-3 inlineFormInput">
                <label for="destination" class="form-label">Destino</label>
                <input type="text" class="form-control" id="destination" placeholder="Cordoba (CBA)">
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label" for="autoSizingSelect">Tipo de carga</label>
            <select class="form-select" id="autoSizingSelect">
                <option selected>Elegi una opcion...</option>
                <option value="1">Refrigerada</option>
                <option value="2">Peligrosa</option>
                <option value="3">Normal</option>
            </select>
        </div>

        <div class="inlineFormInputContainer">
            <div class="mb-3 inlineFormInput">
                <label for="origin" class="form-label">Volumen Disponible</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="availableVolume" placeholder="0">
                    <div class="input-group-append">
                        <span class="input-group-text inputSpan">kg</span>
                    </div>
                </div>
            </div>

            <div class="mb-3 inlineFormInput">
                <label for="origin" class="form-label">Peso disponible</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="availableWeight" placeholder="0">
                    <div class="input-group-append">
                        <span class="input-group-text inputSpan">kg</span>
                    </div>
                </div>
            </div>
        </div>

        <a href="#" class="btn btn-primary formButton">Crear</a>
    </div>
</div>

</body>
</html>

<script>
</script>