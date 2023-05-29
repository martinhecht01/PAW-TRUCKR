<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="../../resources/TruckrLogo.svg" var="logo"/>

<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="logout" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"></path>
        <path fill-rule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"></path>
    </symbol>
    <symbol id="user" viewBox="0 0 16 16">
        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"></path>
    </symbol>
</svg>

<nav class="navbar py-2 navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <c:if test="${currentUser == null}">
            <a href="<c:url value="/"/>">
                <img src="https://i.ibb.co/JmB4xhT/Truckr-Logo.png" alt="logo" height="40px" style="margin-right: 40px; margin-left: 20px">
            </a>
        </c:if>
        <c:if test="${currentUser != null}">
            <a href="<c:url value="/myItinerary"/>">
                <img src="https://i.ibb.co/JmB4xhT/Truckr-Logo.png" alt="logo" height="40px" style="margin-right: 40px; margin-left: 20px">
            </a>
        </c:if>


        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-flex" id="navbarNav">
            <c:if test="${currentUser == null}">
                <ul class="navbar-nav nav-underline justify-content-center">
                    <li class="nav-item mx-5" style="margin-right: 10px">
                        <a class="nav-link"  id="browseTrips2" href="<c:url value="/trips/browse"/>"><spring:message code="BrowseTrips"/></a>
                    </li>
                    <li class="nav-item mx-5" style="margin-right: 10px">
                        <a class="nav-link" id="browseRequests2" href="<c:url value="/requests/browse"/>"><spring:message code="BrowseCargo"/></a>
                    </li>
                </ul>
            </c:if>

            <c:if test="${currentUser != null}">

                <ul class="navbar-nav nav-underline justify-content-center">
                    <li class="nav-item mr-5" style="margin-right: 10px">
                        <a class="nav-link" id="explore" href="<c:url value="/explore"/>">
                            <c:if test="${currentRole == 'TRUCKER'}">
                                <spring:message code="BrowseCargo"/>
                            </c:if>
                            <c:if test="${currentRole == 'PROVIDER'}">
                                <spring:message code="BrowseTrips"/>
                            </c:if>
                        </a>
                    </li>
                    <div class="vr mx-5"></div>
                    <li class="nav-item ml-5" style="margin-right: 10px">
                        <a class="nav-link" id="dashboard" href="<c:url value="/myItinerary"/>"><spring:message code="MyItinerary"/></a>
                    </li>
<%--                    <li class="nav-item ml-5">--%>
<%--                        <a class="nav-link" id="create" href="<c:url value="/create"/>"><spring:message code="Create"/></a>--%>
<%--                    </li>--%>
                    <li class="nav-item mx-5">
                        <c:if test="${currentRole == 'TRUCKER'}">
                            <a class="nav-link" id="myTrips" href="<c:url value="/trips/myTrips"/>">
                                <spring:message code="MyPublications"/>
<%--                                <c:if test="${currentUser.truckerTrips.stream().filter(trip -> trip.proposals.size() > 0).count() > 0}">--%>
<%--                                    <span class="position-absolute translate-middle p-2 bg-danger border border-light rounded-circle">--%>
<%--                                        <span class="visually-hidden">New alerts</span>--%>
<%--                                    </span>--%>
<%--                                </c:if>--%>
                            </a>
                        </c:if>
                        <c:if test="${currentRole == 'PROVIDER'}">
                            <a class="nav-link" id="myRequests" href="<c:url value="/requests/myRequests"/>">
                                <spring:message code="MyPublications"/>
<%--                                <c:if test="${currentUser.truckerTrips.stream().filter(trip -> trip.proposals.size() > 0).count() > 0}">--%>
<%--                                    <span class="position-absolute translate-middle p-2 bg-danger border border-light rounded-circle">--%>
<%--                                        <span class="visually-hidden">New alerts</span>--%>
<%--                                    </span>--%>
<%--                                </c:if>--%>
                            </a>
                        </c:if>
                    </li>
                    <li class="nav-item ml-5" style="margin-right: 10px">
                        <a class="nav-link" id="offers" href="<c:url value="/myOffers"/>"><spring:message code="MyOffers"/></a>
                    </li>
                </ul>
            </c:if>
            <div class="" style="margin: auto 0 auto auto; display: flex; flex-direction: row">
                <c:if test="${currentUser == null}">
                    <ul class="navbar-nav">
                        <a class="nav-link m-auto" href="<c:url value="/login"/>"><spring:message code="Login"/></a>
                    </ul>
                </c:if>
                <c:if test="${currentUser != null}">
                    <div class="flex-row" style="margin: auto 0 auto auto; display: flex;">
                        <ul class="navbar-nav nav-underline">
                            <a class="nav-link m-auto d-flex justify-content-evenly" id="profile" href="<c:url value="/profile"/>">
                                <img class="profileImageNavbar mx-2" src="<c:url value="/user/${currentUser.getUserId()}/profilePicture"/>" />
                                <div class="userDetailsNavBar mx-2">
                                        <h6 style="margin-bottom: 0.25rem; text-align: center;margin-top: 0.25rem;margin-right: 0.3rem"><b>${currentUser.getName()}</b></h6>
                                        <p style="margin:0 0.25rem 0 0;font-size: x-small; text-align: center;"><c:if test="${currentRole == 'TRUCKER'}"><spring:message code="Trucker"/></c:if><c:if test="${currentRole == 'PROVIDER'}"><spring:message code="Provider"/></c:if></p>
                                </div>
                            </a>
                        </ul>

                        <ul class="navbar-nav nav-underline">
                            <a class="nav-link m-auto" href="<c:url value="/logout"/>"><svg width="2em" height="2em"><use xlink:href="#logout"></use></svg></a>
                        </ul>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</nav>

<script>
    var path = window.location.pathname;

    // Select the navbar links and add the "active" class to the appropriate link
    var exploreTripsLink = document.getElementById('explore');
    if (exploreTripsLink && exploreTripsLink.getAttribute('href') === path) {
        exploreTripsLink.classList.add('active');
    }

    var createTripsLink = document.getElementById('create');
    if (createTripsLink && createTripsLink.getAttribute('href') === path) {
        createTripsLink.classList.add('active');
    }

    var myTripsLink = document.getElementById('myTrips');
    if (myTripsLink && myTripsLink.getAttribute('href') === path) {
        myTripsLink.classList.add('active');
    }

    var myRequestsLink = document.getElementById('myRequests');
    if (myRequestsLink && myRequestsLink.getAttribute('href') === path) {
        myRequestsLink.classList.add('active');
    }

    var dashboardLink = document.getElementById('dashboard');
    if (dashboardLink && dashboardLink.getAttribute('href') === path) {
        dashboardLink.classList.add('active');
    }

    var myOffersLink = document.getElementById('offers');
    if (myOffersLink && myOffersLink.getAttribute('href') === path) {
        myOffersLink.classList.add('active');
    }

    var profileLink = document.getElementById('profile');
    if (profileLink && profileLink.getAttribute('href') === path) {
        profileLink.classList.add('active');
    }

    var browseTripsLink = document.getElementById('browseTrips2');
    if (browseTripsLink && browseTripsLink.getAttribute('href') === path) {
        browseTripsLink.classList.add('active');
    }

    var browseRequestsLink = document.getElementById('browseRequests2');
    if (browseRequestsLink && browseRequestsLink.getAttribute('href') === path) {
        browseRequestsLink.classList.add('active');
    }
</script>

