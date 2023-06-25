<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet">

<head>
    <title><spring:message code="RequestDetails"/></title>
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

<c:url value="/requests/sendProposal" var="postPath"/>
<components:navBar/>
<div class="formCard justify-content-center align-items-center pt-5 mb-n5">
    <div class="inlineFormInputContainer">
        <div class="card inlineFormInputContainer" style="width: 40rem;">
            <div class="card-body">
                <img src="<c:url value="/trips/${request.tripId}/tripPicture"/>" class="card-img rounded-start p-3"  alt="TruckImg">
                <table class="table table-srequested">
                        <tr>
                            <td>
                                <b><spring:message code="CargoType"/></b>
                            </td>
                            <td>
                                <a class="text-decoration-none text-dark" href="<c:url value="/explore?type=${request.type}"/>">
                                    <spring:message code="${request.type}" htmlEscape="true" />
                                </a>
                            </td>
                        </tr>
                    </a>
                    <tr>
                        <td><b><spring:message code="Origin"/> - <spring:message code="Destination"/></b></td>
                        <td>
                            <a class="text-decoration-none text-dark" href=" <c:url value="/explore?origin=${request.origin}"/> ">
                                <c:out value="${request.origin}"/>
                            </a>
                            -
                            <a class="text-decoration-none text-dark" href=" <c:url value="/explore?destination=${request.destination}"/> ">
                                <c:out value="${request.destination}"/>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="DepartureDate"/> - <spring:message code="FiltersArrival"/></b></td>
                        <td>
                            <a class="text-decoration-none text-dark" href="<c:url value="/explore?departureDate=${request.departureDate}&arrivalDate=${request.arrivalDate}"/>">
                                ${request.departureDateString} - ${request.arrivalDateString}
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="NecessaryVolume"/></b></td>
                        <td>
                            <a class="text-decoration-none text-dark" href="<c:url value="/explore?minAvailableVolume=${request.volume}"/>">
                                <c:out value="${request.volume}"/> m3
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="NecessaryWeight"/></b></td>
                        <td>
                            <a class="text-decoration-none text-dark" href="<c:url value="/explore?minAvailableWeight=${request.weight}"/>">
                                <c:out value="${request.weight}"/> kg
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="SuggestedPrice"/></b></td>
                        <td>$<c:out value="${request.price}"/></td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="inlineFormInputContainer justify-content-top align-items-top" >

        </div>
        <c:if test="${request.trucker == null}">
            <div class="justify-content-top align-items-top" >
                <div class="card mx-4" style="width: 30rem;">
                    <div class="card-body p-3">
                        <a  class="text-decoration-none" href="<c:url value="/profile?id=${request.provider.userId}"/>">
                            <div class="d-flex justify-content-evenly">
                                <img class="profileImageNavbar" src="<c:url value="/user/${request.provider.userId}/profilePicture"/>" alt="ProfilePicture">
                                <div>
                                    <h5 class="card-title"><c:out value="${request.provider.name.toUpperCase()}"/>&nbsp;&nbsp;&nbsp;&nbsp;<svg class="ml-2" width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg><c:if test="${request.provider.reviews.size() != 0}"><c:out value="${request.provider.rating}"/></c:if> - (${request.provider.reviews.size()} <spring:message code="Reviews"/>)</h5>
                                    <p class="text-dark card-text text-decoration-none"><c:out value="${request.provider.email.toLowerCase()}"/></p>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
                <c:if test="${request.offer == null}">
                    <form:form nestedPath="reserveForm" id="reserveForm" modelAttribute="acceptForm" action="${postPath}?id=${request.tripId}" method="post">
                        <div class="card mx-4 mt-4" style="width: 30rem;">
                            <div class="card-header">
                                <h4 class="card-title" style="color: #142D4C"><b><spring:message code="ReserveRequest"/></b></h4>
                            </div>
                            <div class="card-body">
                                <div class="mb-3">
                                    <form:label for="description" class="form-label" path="description"><spring:message code="Description"/></form:label>
                                    <spring:message var="writeDescription" code="WriteDescription"/>
                                    <form:textarea type="text" id="description" class="form-control" path="description" placeholder="${writeDescription}"/>
                                </div>
                                <p><spring:message code="SuggestedPrice"/> </p>
                                <p>$${request.price}</p>
                                <div class="mb-3 flex-column">
                                    <form:label for="description" class="form-label" path="price"><spring:message code="OfferedPrice"/></form:label>
                                    <form:errors cssClass="formError" path="price"/>
                                    <form:input type="number" id="description" class="form-control" path="price"  placeholder="0"/>
                                </div>
                                <div>
                                    <spring:message code="Reserve" var="reserve"/>

                                    <c:if test="${currentRole == ''}">
                                        <a href=" <c:url value="/login" />" class="btn btn-color">${reserve}</a>
                                    </c:if>
                                    <c:if test="${currentRole == 'TRUCKER' || currentRole == 'PROVIDER'}">
                                        <input type="submit" class="btn btn-color" value="${reserve}"/>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </c:if>
                <c:if test="${request.offer != null}">
                    <div class="card mx-4 mt-4" style="width: 30rem;">
                        <div class="card-header">
                            <h4 class="card-title" style="color: #142D4C"><spring:message code="OfferYouSent"/>:</h4>
                        </div>
                        <div class="card-body w-100">
                            <div class="mb-3">
                                <label for="description" class="form-label"><spring:message code="Description"/></label>
                                <textarea id="description" disabled class="form-control bg-light" placeholder="${writeDescription}">${request.offer.description}</textarea>
                            </div>
                            <div class="d-flex w-100 align-items-center flex-row justify-content-between">
                                <div class="w-25 mb-3 flex-column">
                                    <label for="description" class="form-label"><spring:message code="OfferedPrice"/></label>
                                    <h4>$${request.offer.price}</h4>
                                </div>
                                <c:if test="${request.offer.counterProposal == null}">
                                    <div class="w-25 pt-1">
                                        <div class="text-center align-items-center">
                                            <c:url value="/user/cancelOffer" var="postPath"/>
                                            <form:form method="post" action="${postPath}?offerId=${request.offer.proposalId}">
                                                <spring:message code="Cancel" var="Cancel"/>
                                                <input type="submit" class="btn btn-outline-danger mx-2" value="${Cancel}"/>
                                            </form:form>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <c:if test="${request.offer.counterProposal != null}">
                                <hr/>
                                <h4 class="mb-3"><spring:message code="CounterOffer"/></h4>
                                <div class="mb-3">
                                    <label for="description" class="form-label"><spring:message code="Description"/></label>
                                    <textarea disabled class="form-control bg-light">${request.offer.counterProposal.description}</textarea>
                                </div>
                                <div class="d-flex w-100 flex-row align-items-center justify-content-between">
                                    <div class="w-25 mb-3 flex-column">
                                        <label class="form-label"><spring:message code="OfferedPrice"/></label>
                                        <h4>$${request.offer.counterProposal.price}</h4>
                                    </div>
                                </div>
                                <div class="d-flex justify-content-evenly">
                                    <c:url value="/offers/acceptCounterOffer" var="postPath"/>
                                    <form:form action="${postPath}?offerId=${request.offer.counterProposal.proposalId}&tripId=${request.tripId}" method="post">
                                        <input type="submit" class="btn btn-outline-success mx-2" value="Aceptar"/>
                                    </form:form>
                                    <c:url value="/offers/rejectCounterOffer" var="postPath2"/>
                                    <form:form action="${postPath2}?offerId=${request.offer.counterProposal.proposalId}&tripId=${request.tripId}" method="post">
                                        <input type="submit" class="btn btn-outline-danger mx-2" value="Rechazar"/>
                                    </form:form>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>
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
