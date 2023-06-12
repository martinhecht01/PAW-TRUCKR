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
    <symbol id="details-arrow" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M1.5 1.5A.5.5 0 0 0 1 2v4.8a2.5 2.5 0 0 0 2.5 2.5h9.793l-3.347 3.346a.5.5 0 0 0 .708.708l4.2-4.2a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708.708L13.293 8.3H3.5A1.5 1.5 0 0 1 2 6.8V2a.5.5 0 0 0-.5-.5z"></path>
    </symbol>
</svg>

<components:navBar/>

<div class="w-100 px-5 d-flex justify-content-center">
    <div class="mt-5 w-75 card">
        <div class="card-header">
            <h3><spring:message code="OffersSent"/></h3>
        </div>
        <div class="card-body">
            <c:if test="${offers.size() == 0}">
                <div class="d-flex justify-content-center align-items-center flex-column">
                    <h5><spring:message code="NoOffersSent"/></h5>
                    <a class="w-25 mt-3 btn btn-lg btn-color btn-outline-primary" href="<c:url value="/explore"/>"><spring:message code="BrowseCargo"/></a>
                </div>
            </c:if>
            <c:if test="${offers.size() > 0}">
                <c:forEach var="offer" items="${offers}">
                    <a
                            class="text-decoration-none text-dark"
                        <c:if test="${currentUser.role == 'TRUCKER'}">
                            href="<c:url value="/requests/details?id=${offer.trip.tripId}"/>"
                        </c:if>
                        <c:if test="${currentUser.role == 'PROVIDER'}">
                            href="<c:url value="/trips/details?id=${offer.trip.tripId}"/>"
                        </c:if>
                    >
                        <c:if test="${!offer.equals(offers[0])}">
                            <hr class="py-2">
                        </c:if>
                        <div class="d-flex align-items-center justify-content-between">
                            <div class="d-flex justify-content-center" style="width: 40%">
                                <div class="w-25 text-truncate text-center">
                                    <h5>${offer.trip.origin}</h5>
                                        ${offer.trip.departureDate.toLocalDateTime().year}-${offer.trip.departureDate.toLocalDateTime().month}-${offer.trip.departureDate.toLocalDateTime().dayOfMonth}
                                </div>

                                <div class="w-25 text-center">
                                    <svg width="5em" height="3em"><use xlink:href="#arrow"></use></svg>
                                </div>

                                <div class="w-25 text-truncate text-center">
                                    <h5>${offer.trip.destination}</h5>
                                        ${offer.trip.arrivalDate.toLocalDateTime().year}-${offer.trip.arrivalDate.toLocalDateTime().month}-${offer.trip.arrivalDate.toLocalDateTime().dayOfMonth}
                                </div>
                            </div>
                            <div class="vr"></div>
                            <div class="d-flex align-items-center justify-content-evenly" style="width: 35%">
                                <div class="text-center align-items-center">
                                    <img src="<c:url value="/user/${currentUser.role == 'TRUCKER' ? offer.trip.provider.userId : offer.trip.trucker.userId}/profilePicture"/> " class="profileImageNavbar"/>
                                </div>
                                <div class="mx-3 text-center align-items-center">
                                    <h5>${offer.trip.provider.name}</h5>
                                    <p>Provider</p>
                                </div>
                                <div class="text-center align-items-center">
                                    <h5><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg>
                                        <c:if test="${currentUser.role == 'TRUCKER'}"><c:if test="${offer.trip.provider.reviews.size() != 0}"><c:out value="${offer.trip.provider.rating}"/></c:if> - (${offer.trip.provider.reviews.size()})</c:if>
                                        <c:if test="${currentUser.role == 'PROVIDER'}"><c:if test="${offer.trip.trucker.reviews.size() != 0}"><c:out value="${offer.trip.trucker.rating}"/></c:if> - (${offer.trip.trucker.reviews.size()})</c:if>
                                    </h5>
                                </div>
                            </div>
                            <div class="vr"></div>
                            <div class="d-flex align-items-center justify-content-evenly" style="width: 15%">
                                <div class="w-50">
                                    <div class="text-center align-items-center">
                                        <h4>$${offer.price}</h4>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${offer.counterProposal == null}">
                                <div class="vr"></div>
                                <div class="d-flex justify-content-center align-items-center" style="width: 10%">
                                    <c:url value="/user/cancelOffer" var="postPath"/>
                                    <form:form method="post" action="${postPath}?offerId=${offer.proposalId}">
                                        <spring:message code="Cancel" var="Cancel"/>
                                        <input type="submit" class="btn btn-outline-danger mx-2" value="${Cancel}"/>
                                    </form:form>
                                </div>
                            </c:if>
                        </div>
                        <c:if test="${offer.counterProposal != null}">
                            <div class="d-flex justify-content-center w-100">
                                <div class="bg-white border border-dark-subtle rounded mt-4 mb-2 px-4 py-4 w-50 text-center">
                                    <h4><spring:message code="CounterOffer"/></h4>
                                    <div class="my-3 d-flex flex-row justify-content-evenly align-items-center">
                                        <p><c:out value="${offer.counterProposal.description}"/></p>
                                        <h4>$<c:out value="${offer.counterProposal.price}"/></h4>
                                    </div>
                                    <div class="d-flex flex-row justify-content-center align-items-center">
                                        <c:url value="/offers/acceptCounterOffer" var="postPath"/>
                                        <form:form action="${postPath}?offerId=${offer.counterProposal.proposalId}&tripId=${offer.trip.tripId}" method="post">
                                            <input type="submit" class="btn btn-outline-success mx-2" value="Aceptar"/>
                                        </form:form>
                                        <c:url value="/offers/rejectCounterOffer" var="postPath2"/>
                                        <form:form action="${postPath2}?offerId=${offer.counterProposal.proposalId}" method="post">
                                            <input type="submit" class="btn btn-outline-danger mx-2" value="Rechazar"/>
                                        </form:form>
                                    </div>
                            </div>
                            </div>
                        </c:if>
                    </a>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>

<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>