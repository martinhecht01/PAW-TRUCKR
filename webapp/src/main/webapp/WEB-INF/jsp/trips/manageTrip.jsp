<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet">

<head>
    <title><spring:message code="TripDetails"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<body class="bodyContent">

<svg  xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="check" viewBox="0 0 16 16">
        <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"></path>
    </symbol>
    <symbol id="star-fill" viewBox="0 0 16 16">
        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
    </symbol>
    <symbol id="star" viewBox="0 0 16 16">
        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"></path>
    </symbol>
</svg>

<c:url value="/accept" var="postPath"/>
<components:navBar/>
<div class="formCard justify-content-center align-items-center pt-5 mb-n5">
    <div class="inlineFormInputContainer">
        <div class="card inlineFormInputContainer" style="width: 40rem;">
            <div class="card-body">
                <img src="<c:url value="/trips/${trip.tripId}/tripPicture"/>" class="card-img rounded-start p-3"  alt="TruckImg">
                <table class="table">
                    <tr>
                        <td><b><spring:message code="CargoType"/></b></td>
                        <td><spring:message code="${trip.type}" htmlEscape="true"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="Origin"/>-<spring:message code="Destination"/></b></td>
                        <td><c:out value="${trip.origin}-${trip.destination}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="LicensePlate"/></b></td>
                        <td><c:out value="${trip.licensePlate}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="DepartureDate"/> - <spring:message code="FiltersArrival"/></b></td>
                        <td>${trip.departureDate.toLocalDateTime().year}-${trip.departureDate.toLocalDateTime().monthValue}-${trip.departureDate.toLocalDateTime().dayOfMonth} - ${trip.arrivalDate.toLocalDateTime().year}-${trip.arrivalDate.toLocalDateTime().monthValue}-${trip.arrivalDate.toLocalDateTime().dayOfMonth}</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="AvailableVolume"/></b></td>
                        <td><c:out value="${trip.volume}"/> m3</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="AvailableWeight"/></b></td>
                        <td><c:out value="${trip.weight}"/> kg</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="SuggestedPrice"/></b></td>
                        <td>$<c:out value="${trip.price}"/></td>
                    </tr>
                </table>
            </div>
        </div>
        <c:if test="${trip.provider != null}">
            <div class="justify-content-top align-items-top px-5" >
                <div class="card" style="width: 30rem;">
                    <div class="card-body p-3">
                        <a class="text-decoration-none" href="<c:url value="/profile?id=${trip.provider.userId}"/>">
                            <div class="d-flex justify-content-evenly">
                                <img class="profileImageNavbar" src="<c:url value="/user/${trip.provider.userId}/profilePicture"/>"/>
                                <div>
                                    <h5 class="card-title"><c:out value="${trip.provider.name.toUpperCase()}"/>&nbsp;&nbsp;&nbsp;&nbsp;<svg class="ml-2" width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg> <c:if test="${trip.provider.reviews.size() != 0}"><c:out value="${trip.provider.rating}"/></c:if> - (${trip.provider.reviews.size()})</h5>
                                    <p class="text-dark card-text"><c:out value="${trip.provider.email.toLowerCase()}"/></p>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
                <c:if test="${trip.departureDate.before(now)}">
                    <div class="card mt-4" style="width: 30rem;">
                        <div class="card-header">
                            <h4><spring:message code="Status"/>:</h4>
                        </div>
                        <div class="card-body p-3">
                            <c:if test="${trip.truckerConfirmation && !trip.providerConfirmation}">
                                <p class="card-text pb-1 pt-2"><svg width="1em" height="1em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="FinishedTrip"/></p>
                            </c:if>
                            <c:if test="${!trip.truckerConfirmation}">
                                <p class="card-text py-1"><svg width="1em" height="1em" fill="gray"><use xlink:href="#check"></use></svg> <spring:message code="DidntFinishTrip"/></p>
                            </c:if>
                            <c:if test="${trip.providerConfirmation && !trip.truckerConfirmation}">
                                <p class="card-text py-1"><svg width="1em" height="1em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="ProviderReceivedCargo"/></p>
                            </c:if>
                            <c:if test="${!trip.providerConfirmation}">
                                <p class="card-text py-1"><svg width="1em" height="1em" fill="gray"><use xlink:href="#check"></use></svg> <spring:message code="ProviderDidntReceiveCargo"/></p>
                            </c:if>
                            <c:if test="${trip.providerConfirmation && trip.truckerConfirmation}">
                                <h4 class="card-text py-1"><svg class="mx-2" width="2em" height="2em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="TripFinished"/></h4>
                            </c:if>
                            <c:if test="${trip.confirmationDate != null}">
                                <div class="pt-2 pb-0 w-100 text-center">
                                    <span class="text-center fw-lighter"><spring:message code="LastUpdate"/>: ${trip.confirmationDate}</span>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${!trip.truckerConfirmation}">
                        <c:url value="/trips/confirmTrip" var="confirmPath"/>
                        <form:form method="post" action="${confirmPath}?id=${trip.tripId}">
                            <spring:message var="finished" code="IFinishedTrip"/>
                            <input type="submit" class="btn btn-color mt-3 w-100" value="${finished}"/>
                        </form:form>
                    </c:if>
                </c:if>
                <c:if test="${trip.truckerConfirmation && trip.providerConfirmation }">
                    <c:if test="${trip.review == null}">
                        <c:url value="/reviews/sendReview" var="reviewPath"/>
                        <form:form method="post" modelAttribute="acceptForm" action="${reviewPath}?tripid=${trip.tripId}&userid=${trip.provider.userId}">
                        <div class="card mt-4" style="width: 30rem;">
                            <div class="card-header">
                                <h4>
                                    <spring:message code="Review"/>
                                </h4>
                            </div>
                            <div class="card-body p-3">
                                <div class="rating">
                                    <input type="radio" id="star5" name="rating" value="5">
                                    <label for="star5" title="5 stars"></label>
                                    <input type="radio" id="star4" name="rating" value="4">
                                    <label for="star4" title="4 stars"></label>
                                    <input type="radio" id="star3" name="rating" value="3">
                                    <label for="star3" title="3 stars"></label>
                                    <input type="radio" id="star2" name="rating" value="2">
                                    <label for="star2" title="2 stars"></label>
                                    <input type="radio" id="star1" name="rating" value="1">
                                    <label for="star1" title="1 star"></label>
                                </div>

                                <div class="mt-2 mb-2">
                                    <spring:message var="writeReview" code="WriteReview"/>
                                    <form:textarea type="text" class="form-control" path="description" placeholder="${writeReview}"/>
                                </div>
                                <spring:message var="sendReview" code="SendReview"/>
                                <input type="submit" class="btn btn-color mt-3 w-25" value="${sendReview}"/>
                            </div>
                        </div>
                        </form:form>
                    </c:if>
                    <c:if test="${trip.review != null}">
                        <div class="card mt-4" style="width: 30rem;">
                            <div class="card-body p-3">
                                <h5 class="card-text py-1"><svg class="mx-2" width="2em" height="2em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="ReviewSent"/></h5>
                            </div>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </c:if>
        <c:if test="${trip.provider == null}">
        <div class="justify-content-top align-items-top px-5" >
            <c:if test="${trip.proposals.size() == 0}">
                <div class="card p-3" style="width: 30rem;">
                    <div class="card-body">
                        <h5 class="card-title"><spring:message code="NoProposalsYet"/></h5>
                    </div>
                </div>
            </c:if>
            <c:forEach var="offer" items="${trip.proposals}">
                <div class="card p-2" style="width: 30rem;">
                    <div class="card-body">
                        <a href="<c:url value="/profile?id=${offer.user.userId}"/>">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="card-title"><c:out value="${offer.user.name.toUpperCase()}"/></h5>
                                <img class="profileImageNavbar" src="<c:url value="/user/${offer.user.userId}/profilePicture"/>"/>
                            </div>
                        </a>
                        <div class="mb-3">
                            <label for="description" class="form-label"><spring:message code="Description"/></label>
                            <textarea id="description" disabled class="form-control bg-light">${offer.description}</textarea>
                        </div>
                        <div class="d-flex w-100 flex-row align-items-center justify-content-between">
                            <div class="w-25 mb-3 flex-column">
                                <label for="description" class="form-label"><spring:message code="OfferedPrice"/></label>
                                <h4>$${offer.price}</h4>
                            </div>
                        </div>
                        <spring:message code="Trips.AcceptProposal" var="reserve"/>
                        <c:if test="${offer.counterProposal == null}">
                            <div class="d-flex justify-content-between">
                                <c:url value="/offers/acceptOffer" var="postPath"/>
                                <form:form action="${postPath}?offerId=${offer.proposalId}&tripId=${trip.tripId}" method="post">
                                    <input type="submit" class="btn btn-outline-success mx-2" value="Aceptar"/>
                                </form:form>
                                <a href="<c:url value="/offers/sendCounterOffer?offerId=${offer.proposalId}"/>">
                                    <input type="submit" class="btn btn-outline-warning mx-2" value="Contraoferta"/>
                                </a>
                                <c:url value="/offers/rejectOffer" var="postPath2"/>
                                <form:form action="${postPath2}?offerId=${offer.proposalId}&tripId=${trip.tripId}" method="post">
                                    <input type="submit" class="btn btn-outline-danger mx-2" value="Rechazar"/>
                                </form:form>
                            </div>
                        </c:if>
                        <c:if test="${offer.counterProposal != null}">
                            <hr/>
                            <h4 class="mb-3"><spring:message code="CounterOffer"/></h4>
                            <div class="mb-3">
                                <label for="description" class="form-label"><spring:message code="Description"/></label>
                                <textarea disabled class="form-control bg-light">${offer.counterProposal.description}</textarea>
                            </div>
                            <div class="d-flex w-100 flex-row align-items-center justify-content-between">
                                <div class="w-25 mb-3 flex-column">
                                    <label class="form-label"><spring:message code="OfferedPrice"/></label>
                                    <h4>$${offer.counterProposal.price}</h4>
                                </div>
                                <div class="w-25">
                                    <c:url value="/offers/deleteCounterOffer" var="postPath"/>
                                    <form:form method="post" action="${postPath}?offerId=${offer.counterProposal.proposalId}&tripId=${trip.tripId}">
                                        <spring:message code="Cancel" var="Cancel"/>
                                        <input type="submit" class="btn btn-outline-danger mx-2" value="${Cancel}"/>
                                    </form:form>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
        </c:if>
    </div>
</div>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>

<script>
    $(function() {
        $('.rating input[type="radio"]').on('change', function() {
            var rating = $(this).val();
            $('input[name="rating_value"]').val(rating);
        });
    });
</script>


