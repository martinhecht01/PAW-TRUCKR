<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="../../resources/TruckrLogo.svg" var="logo"/>

<nav class="navbar py-3 navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <%--        <img src="https://i.ibb.co/YBtwbSS/Logo.png" alt="Logo" height="40px" style="margin-right: 40px">--%>
        <a href="<c:url value="/"/>">
            <img src="https://i.ibb.co/JmB4xhT/Truckr-Logo.png" alt="logo" height="40px" style="margin-right: 40px; margin-left: 20px">
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav justify-content-center">
                <li class="nav-item ml-5" style="margin-right: 10px">
                    <a class="nav-link" aria-current="page" href="<c:url value="/browseTrips"/>"><spring:message code="Explore"/></a>
                </li>
                <li class="nav-item ml-5">
                    <c:if test="${currentRole == 'TRUCKER'}">
                        <a class="nav-link" href="<c:url value="/createTrip"/>"><spring:message code="CreateTrip"/></a>
                    </c:if>
                    <c:if test="${currentRole == 'PROVIDER'}">
                        <a class="nav-link" href="<c:url value="/createRequest"/>"><spring:message code="CreateRequest"/></a>
                    </c:if>
                </li>
            </ul>
        </div>
    </div>
</nav>

