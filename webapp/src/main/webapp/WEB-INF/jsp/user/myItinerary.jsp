<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet">
<link href="<c:url value="/css/tripTypes.css"/>" rel="stylesheet">

<head>
    <title><spring:message code="Explore"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>

<body class="bodyContent" style="height: 100%">
<components:navBar/>
<div class="w-100 px-5 mt-5 mb-2 d-flex justify-content-end">
    <a class="btn btn-outline-secondary mx-3 mb-2" href="<c:url value="/pastTrips"/>"><spring:message code="ViewPastTrips"/></a>
</div>
<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="volume" viewBox="0 0 16 16">
        <path d="M7.752.066a.5.5 0 0 1 .496 0l3.75 2.143a.5.5 0 0 1 .252.434v3.995l3.498 2A.5.5 0 0 1 16 9.07v4.286a.5.5 0 0 1-.252.434l-3.75 2.143a.5.5 0 0 1-.496 0l-3.502-2-3.502 2.001a.5.5 0 0 1-.496 0l-3.75-2.143A.5.5 0 0 1 0 13.357V9.071a.5.5 0 0 1 .252-.434L3.75 6.638V2.643a.5.5 0 0 1 .252-.434L7.752.066ZM4.25 7.504 1.508 9.071l2.742 1.567 2.742-1.567L4.25 7.504ZM7.5 9.933l-2.75 1.571v3.134l2.75-1.571V9.933Zm1 3.134 2.75 1.571v-3.134L8.5 9.933v3.134Zm.508-3.996 2.742 1.567 2.742-1.567-2.742-1.567-2.742 1.567Zm2.242-2.433V3.504L8.5 5.076V8.21l2.75-1.572ZM7.5 8.21V5.076L4.75 3.504v3.134L7.5 8.21ZM5.258 2.643 8 4.21l2.742-1.567L8 1.076 5.258 2.643ZM15 9.933l-2.75 1.571v3.134L15 13.067V9.933ZM3.75 14.638v-3.134L1 9.933v3.134l2.75 1.571Z"></path>
    </symbol>
    <symbol id="arrow" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"></path>
    </symbol>
    <symbol id="heavy" viewBox="0 0 16 16">
        <path d="M8 1a2 2 0 0 0-2 2v2H5V3a3 3 0 1 1 6 0v2h-1V3a2 2 0 0 0-2-2zM5 5H3.36a1.5 1.5 0 0 0-1.483 1.277L.85 13.13A2.5 2.5 0 0 0 3.322 16h9.355a2.5 2.5 0 0 0 2.473-2.87l-1.028-6.853A1.5 1.5 0 0 0 12.64 5H11v1.5a.5.5 0 0 1-1 0V5H6v1.5a.5.5 0 0 1-1 0V5z"></path>
    </symbol>
    <symbol id="Hazardous" viewBox="0 0 16 16">
        <path d="M7.938 2.016A.13.13 0 0 1 8.002 2a.13.13 0 0 1 .063.016.146.146 0 0 1 .054.057l6.857 11.667c.036.06.035.124.002.183a.163.163 0 0 1-.054.06.116.116 0 0 1-.066.017H1.146a.115.115 0 0 1-.066-.017.163.163 0 0 1-.054-.06.176.176 0 0 1 .002-.183L7.884 2.073a.147.147 0 0 1 .054-.057zm1.044-.45a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566z"></path>
        <path d="M7.002 12a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 5.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995z"></path>
    </symbol>
    <symbol id="Refrigerated" viewBox="0 0 16 16">
        <path d="M8 16a.5.5 0 0 1-.5-.5v-1.293l-.646.647a.5.5 0 0 1-.707-.708L7.5 12.793V8.866l-3.4 1.963-.496 1.85a.5.5 0 1 1-.966-.26l.237-.882-1.12.646a.5.5 0 0 1-.5-.866l1.12-.646-.884-.237a.5.5 0 1 1 .26-.966l1.848.495L7 8 3.6 6.037l-1.85.495a.5.5 0 0 1-.258-.966l.883-.237-1.12-.646a.5.5 0 1 1 .5-.866l1.12.646-.237-.883a.5.5 0 1 1 .966-.258l.495 1.849L7.5 7.134V3.207L6.147 1.854a.5.5 0 1 1 .707-.708l.646.647V.5a.5.5 0 1 1 1 0v1.293l.647-.647a.5.5 0 1 1 .707.708L8.5 3.207v3.927l3.4-1.963.496-1.85a.5.5 0 1 1 .966.26l-.236.882 1.12-.646a.5.5 0 0 1 .5.866l-1.12.646.883.237a.5.5 0 1 1-.26.966l-1.848-.495L9 8l3.4 1.963 1.849-.495a.5.5 0 0 1 .259.966l-.883.237 1.12.646a.5.5 0 0 1-.5.866l-1.12-.646.236.883a.5.5 0 1 1-.966.258l-.495-1.849-3.4-1.963v3.927l1.353 1.353a.5.5 0 0 1-.707.708l-.647-.647V15.5a.5.5 0 0 1-.5.5z"></path>
    </symbol>
    <symbol id="Normal" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M15.528 2.973a.75.75 0 0 1 .472.696v8.662a.75.75 0 0 1-.472.696l-7.25 2.9a.75.75 0 0 1-.557 0l-7.25-2.9A.75.75 0 0 1 0 12.331V3.669a.75.75 0 0 1 .471-.696L7.443.184l.01-.003.268-.108a.75.75 0 0 1 .558 0l.269.108.01.003 6.97 2.789ZM10.404 2 4.25 4.461 1.846 3.5 1 3.839v.4l6.5 2.6v7.922l.5.2.5-.2V6.84l6.5-2.6v-.4l-.846-.339L8 5.961 5.596 5l6.154-2.461L10.404 2Z"></path>
    </symbol>
    <symbol id="notification" viewBox="0 0 16 16">
        <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.026A2 2 0 0 0 2 14h6.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.606-3.446l-.367-.225L8 9.586l-1.239-.757ZM16 4.697v4.974A4.491 4.491 0 0 0 12.5 8a4.49 4.49 0 0 0-1.965.45l-.338-.207L16 4.697Z"></path>
        <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm.5-5v1.5a.5.5 0 0 1-1 0V11a.5.5 0 0 1 1 0Zm0 3a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0Z"></path>
    </symbol>
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

<form:form method="get">
    <div class="d-flex justify-content-end pt-5 pb-2 px-5"></div>
    <div class="px-5 w-100 text-center">
        <ul class="nav nav-underline justify-content-center" id="myTabs" role="tablist">
            <li class="nav-item mx-2" role="presentation">
                <button class="nav-link active" id="tab1-tab" data-bs-toggle="tab" data-bs-target="#tab1" type="button" role="tab" aria-controls="tab1" aria-selected="true"><spring:message code="OngoingTrips"/></button>
            </li>
            <li class="nav-item mx-2" role="presentation">
                <button class="nav-link" id="tab2-tab" data-bs-toggle="tab" data-bs-target="#tab2" type="button" role="tab" aria-controls="tab2" aria-selected="false"><spring:message code="FutureTrips"/></button>
            </li>
        </ul>

        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="tab1" role="tabpanel" aria-labelledby="tab1-tab">
                <!-- Content for Tab 1 goes here -->
                <div class="w-80 px-5 pt-5 justify-content-center m-auto">
                    <div class="row">
                        <div class="col-lg">
                            <div class="my-3 mx-3 card">
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
                                        <a
                                                <c:if test="${currentUser.role == 'TRUCKER'}">
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
                                                            ${trip.departureDateString}
                                                    </div>

                                                    <div class="w-25 text-center">
                                                        <svg width="5em" height="3em"><use xlink:href="#arrow"></use></svg>
                                                    </div>

                                                    <div class="w-25 text-truncate text-center">
                                                        <h5>${trip.destination}</h5>
                                                            ${trip.arrivalDateString}
                                                    </div>
                                                </div>
                                                <div class="vr"></div>
                                                <div class="w-50 d-flex flex-row align-items-center justify-content-evenly">
                                                    <c:if test="${currentUser.role == 'TRUCKER'}">
                                                        <div class="text-center mx-2 align-items-center">
                                                            <img src="<c:url value="/user/${trip.provider.userId}/profilePicture"/>" class="profileImageNavbar"/>
                                                        </div>
                                                        <div class="text-center align-items-center">
                                                            <h5><c:out value="${trip.provider.name}"/></h5>
                                                            <p><spring:message code="${trip.provider.role}"/></p>
                                                        </div>
                                                        <div class="text-center align-items-center">
                                                            <h5><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg>
                                                                <c:if test="${trip.provider.reviews.size() != 0}"><c:out value="${trip.provider.rating}"/></c:if>
                                                                <c:if test="${trip.provider.reviews.size() == 0}">-</c:if>
                                                            </h5>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${currentUser.role == 'PROVIDER'}">
                                                        <div class="text-center mx-2 align-items-center">
                                                            <img src="<c:url value="/user/${trip.trucker.userId}/profilePicture"/>" class="profileImageNavbar"/>
                                                        </div>
                                                        <div class="text-center align-items-center">
                                                            <h5><c:out value="${trip.trucker.name}"/></h5>
                                                            <p><spring:message code="${trip.trucker.role}"/></p>
                                                        </div>
                                                        <div class="text-center align-items-center">
                                                            <h5><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg>
                                                                <c:if test="${trip.trucker.reviews.size() != 0}"><c:out value="${trip.trucker.rating}"/></c:if>
                                                                <c:if test="${trip.trucker.reviews.size() == 0}">-</c:if>
                                                            </h5>
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                            <c:if test="${ongoingTrips.size() > 0}">
                                <ul class="pagination justify-content-center pt-3">
                                    <c:if test="${currentOngoingPage > 2}">
                                        <li class="page-item">
                                            <button type="submit" class="page-link" name="ongoingPage" value="${1}"><spring:message code="First"/></button>
                                        </li>
                                    </c:if>
                                    <c:if test="${currentOngoingPage != 1}">
                                        <li class="page-item">
                                            <button type="submit" class="page-link" name="ongoingPage" value="${currentOngoingPage-1}"><spring:message code="Previous"/></button>
                                        </li>
                                        <li class="page-item"><button type="submit" class="page-link" name="ongoingPage" value="${currentOngoingPage-1}">${currentOngoingPage-1}</button></li>
                                    </c:if>
                                    <li class="page-item disabled"><button type="submit" class="page-link" name="ongoingPage" value="${currentOngoingPage}">${currentOngoingPage}</button></li>
                                    <c:if test="${currentOngoingPage < maxOngoingPage}">
                                        <li class="page-item"><button type="submit" class="page-link" name="ongoingPage" value="${currentOngoingPage+1}">${currentOngoingPage + 1}</button></li>
                                        <li class="page-item">
                                            <button type="submit" class="page-link" name="ongoingPage" value="${currentOngoingPage+1}"><spring:message code="Next"/></button>
                                        </li>
                                    </c:if>
                                    <c:if test="${currentOngoingPage < maxOngoingPage - 1}">
                                        <li class="page-item">
                                            <button type="submit" class="page-link" name="ongoingPage" value="${maxOngoingPage}"><spring:message code="Last"/></button>
                                        </li>
                                    </c:if>
                                </ul>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <div class="tab-pane fade" id="tab2" role="tabpanel" aria-labelledby="tab2-tab">
                <!-- Content for Tab 1 goes here -->
                <div class="w-80 px-5 pt-5 justify-content-center m-auto">
                    <div class="row">
                        <div class="col-lg">
                            <div class="my-3 mx-3 card">
                                <div class="card-body">
                                    <c:if test="${futureTrips.size() == 0}">
                                        <div class="text-center my-3">
                                            <h5><spring:message code="NoFutureTrips"/></h5>
                                        </div>
                                    </c:if>
                                    <c:forEach var="trip" items="${futureTrips}">
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
                                                            ${trip.departureDateString}
                                                    </div>

                                                    <div class="w-25 text-center">
                                                        <svg width="5em" height="3em"><use xlink:href="#arrow"></use></svg>
                                                    </div>

                                                    <div class="w-25 text-truncate text-center">
                                                        <h5>${trip.destination}</h5>
                                                            ${trip.arrivalDateString}
                                                    </div>
                                                </div>
                                                <div class="vr"></div>
                                                <div class="w-50 d-flex flex-row align-items-center justify-content-evenly">
                                                    <c:if test="${currentUser.role == 'TRUCKER'}">
                                                        <div class="text-center mx-2 align-items-center">
                                                            <img src="<c:url value="/user/${trip.provider.userId}/profilePicture"/>" class="profileImageNavbar"/>
                                                        </div>
                                                        <div class="text-center align-items-center">
                                                            <h5><c:out value="${trip.provider.name}"/></h5>
                                                            <p><spring:message code="${trip.provider.role}"/></p>
                                                        </div>
                                                        <div class="text-center align-items-center">
                                                            <h5><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg>
                                                                <c:if test="${trip.provider.reviews.size() != 0}"><c:out value="${trip.provider.rating}"/></c:if>
                                                                <c:if test="${trip.provider.reviews.size() == 0}">-</c:if>
                                                            </h5>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${currentUser.role == 'PROVIDER'}">
                                                        <div class="text-center mx-2 align-items-center">
                                                            <img src="<c:url value="/user/${trip.trucker.userId}/profilePicture"/>" class="profileImageNavbar"/>
                                                        </div>
                                                        <div class="text-center align-items-center">
                                                            <h5><c:out value="${trip.trucker.name}"/></h5>
                                                            <p><spring:message code="${trip.trucker.role}"/></p>
                                                        </div>
                                                        <div class="text-center align-items-center">
                                                            <h5><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg>
                                                                <c:if test="${trip.trucker.reviews.size() != 0}"><c:out value="${trip.trucker.rating}"/></c:if>
                                                                <c:if test="${trip.trucker.reviews.size() == 0}">-</c:if>
                                                            </h5>
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                            <c:if test="${futureTrips.size() > 0}">
                                <ul class="pagination justify-content-center pt-3">
                                    <c:if test="${currentFuturePage > 2}">
                                        <li class="page-item">
                                            <a href="<c:url value="/myItinerary"/>?futurePage=1&&activeSecondTab=true" class="page-link">
                                                <spring:message code="First"/>
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${currentFuturePage != 1}">
                                        <li class="page-item">
                                            <a href="<c:url value="/myItinerary"/>?futurePage=${currentFuturePage-1}&&activeSecondTab=true" class="page-link">
                                                <spring:message code="Previous"/>
                                            </a>
                                        </li>
                                        <li class="page-item">
                                            <a href="<c:url value="/myItinerary"/>?futurePage=${currentFuturePage-1}&&activeSecondTab=true" class="page-link">
                                                ${currentFuturePage-1}
                                            </a>
                                        </li>
                                    </c:if>
                                    <li class="page-item disabled"><button type="submit" class="page-link" name="futurePage" value="${currentFuturePage}">${currentFuturePage}</button></li>
                                    <c:if test="${currentFuturePage < maxFuturePage}">
                                        <li class="page-item">
                                            <a href="<c:url value="/myItinerary"/>?futurePage=${currentFuturePage+1}&&activeSecondTab=true" class="page-link">
                                                ${currentFuturePage + 1}
                                            </a>
                                        </li>
                                        <li class="page-item">
                                            <a href="<c:url value="/myItinerary"/>?futurePage=${currentFuturePage+1}&&activeSecondTab=true" class="page-link">
                                                <spring:message code="Next"/>
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${currentFuturePage < maxFuturePage - 1}">
                                        <li class="page-item">
                                            <a href="<c:url value="/myItinerary"/>?futurePage=${maxFuturePage}&&activeSecondTab=true" class="page-link">
                                                <spring:message code="Last"/>
                                            </a>
                                        </li>
                                    </c:if>
                                </ul>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>

<script>
    // Get the tab buttons
    const tabButtons = document.querySelectorAll('.nav-link');

    // Get the tab content elements
    const tabContents = document.querySelectorAll('.tab-pane');

    // Function to activate the second tab
    const activateSecondTab = () => {
        // Remove "active" class from all tab buttons
        //.tab('show')
        const tab1Content = document.getElementById("tab1");
        tab1Content.classList.remove("active");
        tab1Content.classList.remove("show");

        const tab1Button = document.getElementById("tab1-tab");
        tab1Button.classList.remove("active");

        const tab2Content = document.getElementById("tab2");
        tab2Content.classList.add("active");
        tab2Content.classList.add("show");

        const tab2Button = document.getElementById("tab2-tab");
        tab2Button.classList.add("active");
    };

    // Check if the secondTab parameter is true
    if (${activeSecondTab}) {
        activateSecondTab();
    }

</script>
