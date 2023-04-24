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


<head>
    <title><spring:message code="Explore"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/JmB4xhT/Truckr-Logo.png">
</head>
<body class="bodyContent" style="height: 100%">
<components:navBar/>
<form:form method="get">
    <div class="d-flex pt-5" style="width: 100%; padding: 0 10% ">
        <div class="tripCards m-auto">
            <c:if test="${offers.size() == 0}">
                <h2 class="display-5 fw-bold text-body-emphasis text-center"><spring:message code="NoTripsAvailable"/></h2>
            </c:if>
            <c:forEach var="trip" items="${offers}">
                <a class="card mb-3 browseCards" href="<c:url value="/tripDetail?id=${trip.tripId}"/>" style="display: flex; padding: 0">
                    <div class="card-header">
                        <div class="row g-0">
                            <div style="display: flex; justify-content: space-between; border-right: 3px black">
                                <div class="py-1 px-3" style="width: 50%; justify-content: space-between; display: flex;">
                                    <div style="display: flex; width: 100%; justify-content: space-between; text-align: center">
                                        <div>
                                            <div class="mx-2">
                                                <h5><c:out value="${trip.origin}"/></h5>
                                                <c:out value="${trip.departureDate.dayOfMonth}/${trip.departureDate.monthValue}/${trip.departureDate.year}"/>
                                            </div>
                                        </div>
                                        <div>
                                            <div>
                                                <svg width="9em" height="3em"><use xlink:href="#arrow"></use></svg>
                                            </div>
                                        </div>
                                        <div>
                                            <div class="mx-2">
                                                <h5><c:out value="${trip.destination}"/></h5>
                                                <c:out value="${trip.arrivalDate.dayOfMonth}/${trip.arrivalDate.monthValue}/${trip.arrivalDate.year}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div  class="py-3" style="display: flex; flex-direction: row; width: 50%; justify-content: center; text-align: center; align-items: center">
                                    <h5 class="px-3"><c:out value="${trip.type}"/></h5>
                                        <%--                <svg width="2em" height="3em"><use xlink:href="#cold"></use></svg>--%>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row g-0">
                        <div style="display: flex; justify-content: space-between">
                            <div style="width: 50%; justify-content: center;">
                                <img src="http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQNxLs9ztCGoYOAq9Lg-J6eEHaNgm1trwlfXEhXnKlvzgcztA7wunvdwbsd2vHmnORyvAYbsrpONdQxM2o96Ho" class="img-fluid" style="border-bottom-left-radius: 5px; width: 100%; height: 100%; max-height: 20vh ; object-position: left" alt="...">
                            </div>
                            <div  class="p-2" style="width: 50%; height: 100%; justify-content: center; align-items: center">
                                <div class="row g-0" style="height: 75%">
                                    <div style="display: flex; margin-top: auto; justify-content: space-between">
                                        <div style="display: flex; flex-direction: column; width: 50%; justify-content: center; text-align: center; align-items: center">
                                            <p class="pb-2"><spring:message code="AvailableWeight"/></p>
                                            <svg width="3em" height="3em"><use xlink:href="#heavy"></use></svg>
                                            <h4><c:out value="${trip.availableWeight}"/> KG </h4>
                                        </div>
                                        <div style="display: flex; flex-direction: column; width: 50%; justify-content: center; text-align: center; align-items: center">
                                            <p class="pb-2"><spring:message code="AvailableVolume"/></p>
                                            <svg width="3em" height="3em"><use xlink:href="#volume"></use></svg>
                                            <h4><c:out value="${trip.availableVolume}"/> M3 </h4>
                                        </div>
                                    </div>
                                </div>
                                <div class="row g-0 pt-3" style="text-align: center; height: 25%">
                                    <div>
                                        <h4>$<c:out value="${trip.price}"/></h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
    <%--  <script>--%>
    <%--    function filterApply() {--%>
    <%--      currentPage = 1;--%>
    <%--      form.submit();--%>

    <%--    }--%>
    <%--  </script>--%>
</form:form>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</body>
</html>
