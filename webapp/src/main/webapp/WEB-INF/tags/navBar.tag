<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="../../resources/TruckrLogo.svg" var="logo"/>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <%--        <img src="https://i.ibb.co/YBtwbSS/Logo.png" alt="Logo" height="40px" style="margin-right: 40px">--%>
        <img src="https://i.ibb.co/NCYsByY/Truckr-Logo.png" alt="logo" height="40px" style="margin-right: 40px">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav justify-content-center">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="./browseTrips">Explorar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="./createTrip">Crear Viaje</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

