<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="../../resources/TruckrLogo.svg" var="logo"/>

<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="logout" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
        <path fill-rule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
    </symbol>
    <symbol id="user" viewBox="0 0 16 16">
        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
    </symbol>
</svg>

<nav class="navbar py-3 navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <%--        <img src="https://i.ibb.co/YBtwbSS/Logo.png" alt="Logo" height="40px" style="margin-right: 40px">--%>
        <a href="<c:url value="/"/>">
            <img src="https://i.ibb.co/JmB4xhT/Truckr-Logo.png" alt="logo" height="40px" style="margin-right: 40px; margin-left: 20px">
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-flex" id="navbarNav">
            <ul class="navbar-nav justify-content-center">
                <li class="nav-item ml-5" style="margin-right: 10px">

                    <c:if test="${currentRole == 'TRUCKER'}">
                        <a class="nav-link" aria-current="page" href="<c:url value="/browseRequests"/>"><spring:message code="Explore"/></a>
                    </c:if>
                    <c:if test="${currentRole == 'PROVIDER'}">
                        <a class="nav-link" aria-current="page" href="<c:url value="/browseTrips"/>"><spring:message code="Explore"/></a>
                    </c:if>
                    <c:if test="${currentRole == ''}">
                        <a class="nav-link" aria-current="page" href="<c:url value="/login"/>"><spring:message code="Explore"/></a>
                    </c:if>
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
            <c:if test="${currentRole == 'TRUCKER' || currentRole=='PROVIDER'}">
                <div class="" style="margin: auto 0 auto auto; display: flex; flex-direction: row">
<%--                    <ul class="navbar-nav">--%>
<%--                        <a class="nav-link m-auto" href="<c:url value="/profile"/>"><svg width="2em" height="2em"><use xlink:href="#user"></use></svg></a>--%>
<%--                    </ul>--%>
                    <ul class="navbar-nav">
                        <a class="nav-link m-auto" href="<c:url value="/logout"/>"><svg width="2em" height="2em"><use xlink:href="#logout"></use></svg></a>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
</nav>

