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

<body class="bodyContent h-100">

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
</svg>

<%--<c:url value="/trips/browse" var="getPath"/>--%>
<%--<form:form method="get" modelAttribute="filterForm" action="${getPath}">--%>

<components:navBar/>
<form:form method="get">
  <div class="d-flex pt-5 w-100 justify-content-center p-5">
    <div class="w-75">
      <div class="d-flex">
        <div class="tripCards m-auto ml-5 justify-content-center">
          <c:if test="${offers.size() == 0}">
            <div class="pt-4">
              <h2 class="display-5 fw-bold text-body-emphasis text-center"><spring:message code="NoResults"/></h2>
              <p><spring:message code="CreateTripPublication"/></p>
              <a href="<c:url value="/requests/create?origin=${origin}&destination=${destination}&minAvailableVolume=${minAvailableVolume}&minAvailableWeight=${minAvailableWeight}&departureDate=${departureDate}&arrivalDate=${arrivalDate}"/>" type="submit" class="btn btn-color mt-3 formButton"><spring:message code="CreatePublication"/></a>
            </div>
          </c:if>
          <c:forEach var="trip" items="${offers}">
            <a class="text-decoration-none" href="<c:url value="/trips/details?id=${trip.tripId}"/>">
              <div class="card m-3" style="width: 25rem; overflow: hidden">
                <img src="<c:url value="/trips/${trip.tripId}/tripPicture"/>" class="browseImg" alt="truck img">
                <h4 class="mx-4 my-3 w-25 position-absolute top-0 start-0"><span class="badge rounded-pill ${trip.type}"><svg class="mx-2" fill="white" width="1em" height="1em"><use xlink:href="#${trip.type}"></use></svg><spring:message code="${trip.type}" htmlEscape="true"/></span></h4>
                <div class="card-body">
                  <div class="w-100 d-flex space-apart">
                    <div class="text-truncate text-center" style="width: 35%">
                      <h5><c:out value="${trip.origin}"/></h5>
                        ${trip.departureDate.toLocalDateTime().year}-${trip.departureDate.toLocalDateTime().month}-${trip.departureDate.toLocalDateTime().dayOfMonth}
                    </div>

                    <div style="width: 30%">
                      <svg width="9em" height="3em"><use xlink:href="#arrow"></use></svg>
                    </div>

                    <div class="text-truncate text-center" style="width: 35%">
                      <h5><c:out value="${trip.destination}"/></h5>
                        ${trip.arrivalDate.toLocalDateTime().year}-${trip.arrivalDate.toLocalDateTime().month}-${trip.arrivalDate.toLocalDateTime().dayOfMonth}
                    </div>
                  </div>
                </div>
                <ul class="list-group list-group-flush">
                  <li class="list-group-item px-5 pt-4 d-flex justify-content-between align-items-center">
                    <div class="text-center">
                      <h5><svg width="1em" height="1em"><use xlink:href="#heavy"></use></svg> <c:out value="${trip.weight}"/> KG </h5>
                      <p><spring:message code="AvailableWeight"/></p>
                    </div>
                    <div class="text-center">
                      <h5><svg width="1em" height="1em"><use xlink:href="#volume"></use></svg> <c:out value="${trip.volume}"/> M3 </h5>
                      <p><spring:message code="AvailableVolume"/></p>
                    </div>
                  </li>
                  <li class="list-group-item text-truncate text-center"><h4>$<c:out value="${trip.price}"/></h4></li>
                </ul>
              </div>
            </a>
          </c:forEach>
        </div>
      </div>
      <c:if test="${offers.size() != 0}">
        <ul class="pagination justify-content-center pt-3">
          <c:if test="${currentPage > 2}">
            <li class="page-item">
              <button type="submit" class="page-link" name="page" value="${1}">First</button>
            </li>
          </c:if>
          <c:if test="${currentPage != 1}">
            <li class="page-item">
              <button type="submit" class="page-link" name="page" value="${currentPage-1}">Previous</button>
            </li>
            <li class="page-item"><button type="submit" class="page-link" name="page" value="${currentPage-1}">${currentPage-1}</button></li>
          </c:if>
          <li class="page-item disabled"><button type="submit" class="page-link" name="page" value="${currentPage}">${currentPage}</button></li>
          <c:if test="${currentPage < maxPage}">
            <li class="page-item"><button type="submit" class="page-link" name="page" value="${currentPage+1}">${currentPage + 1}</button></li>
            <li class="page-item">
              <button type="submit" class="page-link" name="page" value="${currentPage+1}">Next</button>
            </li>
          </c:if>
          <c:if test="${currentPage < maxPage - 1}">
            <li class="page-item">
              <button type="submit" class="page-link" name="page" value="${maxPage}">Last</button>
            </li>
          </c:if>
        </ul>
      </c:if>
    </div>
  </div>


</form:form>
<div style="margin-top: auto">
  <components:waveDivider/>
  <components:footer/>
</div>
</body>
</html>

