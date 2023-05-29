<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<link>
<head>
    <title>Truckr</title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<link href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"/>" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous"/>
<script src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"/>" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>
<link href="<c:url value="/css/userControl.css"/>" rel="stylesheet"/>

<body class="bodyContent">

<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="star-fill" viewBox="0 0 16 16">
        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
    </symbol>
    <symbol id="arrow" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"></path>
    </symbol>
    <symbol id="star-fill" viewBox="0 0 16 16">
        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
    </symbol>
</svg>

<components:navBar/>
<div class="w-100 px-5 mt-5 mb-2 d-flex justify-content-end">
    <a class="btn btn-outline-secondary mx-3 mb-2" href="<c:url value="/pastTrips"/>"><spring:message code="ViewPastTrips"/></a>
</div>
<div class="w-100 px-5">
    <div class="row">
        <div class="col-lg">
            <div class="my-3 mx-3 card">
                <div class="card-header">
                    <h3><spring:message code="OngoingTrips"/> (${ongoingTrips.size()})</h3>
                </div>
                <div class="card-body">
                    <c:if test="${ongoingTrips.size() == 0}">
                        <div class="text-center my-3">
                            <h5><spring:message code="NoOngoingTrips"/></h5>
                        </div>
                    </c:if>
                    <c:forEach var="trip" items="${ongoingTrips}">
                        <c:if test="${trip != ongoingTrips[0]}">
                            <hr class="py-2">
                        </c:if>
                        <a <c:if test="${currentUser.role == 'TRUCKER'}">
                                href="<c:url value="/trips/manageTrip?tripId=${trip.tripId}"/>"
                            </c:if>
                            <c:if test="${currentUser.role == 'PROVIDER'}">
                                href="<c:url value="/requests/manageRequest?requestId=${trip.tripId}"/>"
                            </c:if>
                                class="text-decoration-none text-dark"
                        >
                            <div class="d-flex align-items-center justify-content-between">
                                <div class="w-50 d-flex justify-content-center">
                                    <div class="w-25 text-truncate text-center">
                                        <h5>${trip.origin}</h5>
                                            ${trip.departureDate}
                                    </div>

                                    <div class="w-25 text-center">
                                        <svg width="5em" height="3em"><use xlink:href="#arrow"></use></svg>
                                    </div>

                                    <div class="w-25 text-truncate text-center">
                                        <h5>${trip.destination}</h5>
                                            ${trip.arrivalDate}
                                    </div>
                                </div>
                                <div class="vr"></div>
                                <div class="w-25 d-flex align-items-center justify-content-center">
                                    <div class="row">
                                        <c:if test="${currentUser.role == 'TRUCKER'}">
                                            <div class="col-md">
                                                <div class="text-center mx-2 align-items-center">
                                                    <img src="<c:url value="/user/${trip.provider.userId}/profilePicture"/>" class="profileImageNavbar"/>
                                                </div>
                                            </div>
                                            <div class="col-md">
                                                <div class="text-center align-items-center">
                                                    <h5><c:out value="${trip.provider.name}"/></h5>
                                                    <p><spring:message code="${trip.provider.role}"/></p>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${currentUser.role == 'PROVIDER'}">
                                            <div class="col-md">
                                                <div class="text-center mx-2 align-items-center">
                                                    <img src="<c:url value="/user/${trip.trucker.userId}/profilePicture"/>" class="profileImageNavbar"/>
                                                </div>
                                            </div>
                                            <div class="col-md">
                                                <div class="text-center align-items-center">
                                                    <h5><c:out value="${trip.trucker.name}"/></h5>
                                                    <p><spring:message code="${trip.trucker.role}"/></p>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
<%--                                <div class="vr"></div>--%>
<%--                                <div>--%>
<%--                                    <h4 class="mx-3 my-3"><span class="badge rounded-pill bg-primary">in progress</span></h4>--%>
<%--                                </div>--%>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="col-lg">
            <div class="my-3 mx-3 card">
                <div class="card-header">
                    <h3><spring:message code="FutureTrips"/> (${futureTrips.size()})</h3>
                </div>
                <div class="card-body">
                    <c:if test="${futureTrips.size() == 0}">
                        <div class="text-center my-3">
                            <h5><spring:message code="NoFutureTrips"/></h5>
                        </div>
                    </c:if>
                    <c:forEach items="${futureTrips}" var="trip">
                        <c:if test="${trip != futureTrips[0]}">
                            <hr class="py-2">
                        </c:if>
                        <a <c:if test="${currentUser.role == 'TRUCKER'}">
                                href="<c:url value="/trips/manageTrip?tripId=${trip.tripId}"/>"
                        </c:if>
                        <c:if test="${currentUser.role == 'PROVIDER'}">
                                href="<c:url value="/requests/manageRequest?requestId=${trip.tripId}"/>"
                        </c:if>
                                class="text-decoration-none text-dark"
                        >
                            <div class="d-flex align-items-center justify-content-between">
                                <div class="w-50 d-flex justify-content-center">
                                    <div class="w-25 text-truncate text-center">
                                        <h5>${trip.origin}</h5>
                                        ${trip.departureDate}
                                    </div>

                                    <div class="w-25 text-center">
                                        <svg width="5em" height="3em"><use xlink:href="#arrow"></use></svg>
                                    </div>

                                    <div class="w-25 text-truncate text-center">
                                        <h5>${trip.destination}</h5>
                                        ${trip.arrivalDate}
                                    </div>
                                </div>
                                <div class="vr"></div>
                                <div class="w-50 d-flex align-items-center justify-content-evenly">
                                    <c:if test="${currentUser.role == 'TRUCKER'}">
                                        <div class="text-center mx-3 align-items-center">
                                            <img src="<c:url value="/user/${trip.provider.userId}/profilePicture"/> " class="profileImageNavbar"/>
                                        </div>
                                        <div class="text-center align-items-center">
                                            <h5><c:out value="${trip.provider.name}"/></h5>
                                            <p><spring:message code="${trip.provider.role}"/></p>
                                        </div>
    <%--                                    <div class="mx-3 text-center align-items-center">--%>
    <%--                                        <h5><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg> 4.2</h5>--%>
    <%--                                    </div>--%>
                                    </c:if>
                                    <c:if test="${currentUser.role == 'PROVIDER'}">
                                        <div class="text-center mx-3 align-items-center">
                                            <img src="<c:url value="/user/${trip.trucker.userId}/profilePicture"/> " class="profileImageNavbar"/>
                                        </div>
                                        <div class="text-center align-items-center">
                                            <h5><c:out value="${trip.trucker.name}"/></h5>
                                            <p><spring:message code="${trip.trucker.role}"/></p>
                                        </div>
    <%--                                    <div class="mx-3 text-center align-items-center">--%>
    <%--                                        <h5><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg> 4.2</h5>--%>
    <%--                                    </div>--%>
                                    </c:if>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>