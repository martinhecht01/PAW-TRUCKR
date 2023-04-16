<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="/css/main.css" rel="stylesheet"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <components:navBar/>
    <div class="container" style="display: flex; justify-content: center; align-items: center; height: 80vh">
        <div class="px-4 py-5 my-5 text-center">
            <h1 class="display-5 fw-bold text-body-emphasis">404 - ${title} no encontrado</h1>
            <div class="col-lg-6 mx-auto">
                <p class="lead mb-4">Por favor intente nuevamente. En caso de persistir el error contactar soporte.</p>
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <button onclick="goBack()" type="button" class="btn btn-primary btn-lg px-4 gap-3">Volver atras</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

<script>
    function goBack() {
        window.history.back();
    }
</script>
