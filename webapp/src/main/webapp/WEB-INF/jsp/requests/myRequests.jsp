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
  <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png"></head>
<body class="bodyContent" style="height: 100%">
<components:navBar/>
<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
  <symbol id="volume" viewBox="0 0 16 16">
    <path d="M7.752.066a.5.5 0 0 1 .496 0l3.75 2.143a.5.5 0 0 1 .252.434v3.995l3.498 2A.5.5 0 0 1 16 9.07v4.286a.5.5 0 0 1-.252.434l-3.75 2.143a.5.5 0 0 1-.496 0l-3.502-2-3.502 2.001a.5.5 0 0 1-.496 0l-3.75-2.143A.5.5 0 0 1 0 13.357V9.071a.5.5 0 0 1 .252-.434L3.75 6.638V2.643a.5.5 0 0 1 .252-.434L7.752.066ZM4.25 7.504 1.508 9.071l2.742 1.567 2.742-1.567L4.25 7.504ZM7.5 9.933l-2.75 1.571v3.134l2.75-1.571V9.933Zm1 3.134 2.75 1.571v-3.134L8.5 9.933v3.134Zm.508-3.996 2.742 1.567 2.742-1.567-2.742-1.567-2.742 1.567Zm2.242-2.433V3.504L8.5 5.076V8.21l2.75-1.572ZM7.5 8.21V5.076L4.75 3.504v3.134L7.5 8.21ZM5.258 2.643 8 4.21l2.742-1.567L8 1.076 5.258 2.643ZM15 9.933l-2.75 1.571v3.134L15 13.067V9.933ZM3.75 14.638v-3.134L1 9.933v3.134l2.75 1.571Z"></path>
  </symbol>
  <symbol id="arrow" viewBox="0 0 16 16">
    <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"/>
  </symbol>
  <symbol id="heavy" viewBox="0 0 16 16">
    <path d="M8 1a2 2 0 0 0-2 2v2H5V3a3 3 0 1 1 6 0v2h-1V3a2 2 0 0 0-2-2zM5 5H3.36a1.5 1.5 0 0 0-1.483 1.277L.85 13.13A2.5 2.5 0 0 0 3.322 16h9.355a2.5 2.5 0 0 0 2.473-2.87l-1.028-6.853A1.5 1.5 0 0 0 12.64 5H11v1.5a.5.5 0 0 1-1 0V5H6v1.5a.5.5 0 0 1-1 0V5z"/>
  </symbol>
  <symbol id="notification" viewBox="0 0 16 16">
    <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.026A2 2 0 0 0 2 14h6.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.606-3.446l-.367-.225L8 9.586l-1.239-.757ZM16 4.697v4.974A4.491 4.491 0 0 0 12.5 8a4.49 4.49 0 0 0-1.965.45l-.338-.207L16 4.697Z"/>
    <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm.5-5v1.5a.5.5 0 0 1-1 0V11a.5.5 0 0 1 1 0Zm0 3a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0Z"/>
  </symbol>
</svg>
<form:form method="get">
  <div class="pt-5 w-100 justify-content-center">
    <c:if test="${myRequests.size()>0}">
      <h3 class="mt-3 mb-2 text-center"><spring:message code="ActiveRequests"/></h3>
      <div class="w-100 d-flex justify-content-center">
        <hr class="w-50">
      </div>
    </c:if>
    <div class="tripCards w-75 justify-content-center m-auto">
      <c:if test="${myRequests.size() == 0 && acceptedRequests.size() == 0}">
        <h2 class="display-5 fw-bold text-body-emphasis text-center"><spring:message code="NoRequestsAvailable"/></h2>
      </c:if>
      <c:forEach var="request" items="${myRequests}">
        <a class="text-decoration-none" href="<c:url value="/requests/manageRequest?requestId=${request.key.requestId}"/>">
          <div class="card m-3" style="width: 25rem;">
            <c:if test="${request.value > 0}">
              <h5 class="position-absolute top-0 end-0 M-3 ">
                  <span class="badge rounded-pill bg-danger">
                      <c:out value="${request.value}"/> <svg width="1em" height="1em"><use fill="white" xlink:href="#notification"></use></svg>
                      <span class="visually-hidden">unread messages</span>
                  </span>
              </h5>
            </c:if>
            <img src="https://s3-eu-central-1.amazonaws.com/eurosender-blog/wp-content/uploads/2019/09/11094537/pallets-min.jpg" class="card-img-top" alt="...">
            <h4 class="mx-4 my-3 w-25 position-absolute top-0 start-0"><span class="badge rounded-pill text-bg-primary"><spring:message code="${request.key.type}" htmlEscape="true"/></span></h4>
            <div class="card-body">
              <div class="w-100 d-flex space-apart">
                <div class="text-truncate text-center" style="width: 35%">
                  <h5><c:out value="${request.key.origin}"/></h5>
                  <c:out value="${request.key.minDepartureDate.dayOfMonth}/${request.key.minDepartureDate.monthValue}/${request.key.minDepartureDate.year}"/>
                </div>

                <div style="width: 30%">
                  <svg width="9em" height="3em"><use xlink:href="#arrow"></use></svg>
                </div>

                <div class="text-truncate text-center" style="width: 35%">
                  <h5><c:out value="${request.key.destination}"/></h5>
                  <c:out value="${request.key.maxArrivalDate.dayOfMonth}/${request.key.maxArrivalDate.monthValue}/${request.key.maxArrivalDate.year}"/>
                </div>
              </div>
            </div>
            <ul class="list-group list-group-flush">
              <li class="list-group-item px-5 pt-4 d-flex justify-content-between align-items-center">
                <div class="text-center">
                  <h5><svg width="1em" height="1em"><use xlink:href="#heavy"></use></svg> <c:out value="${request.key.requestedWeight}"/> KG </h5>
                  <p><spring:message code="CreateRequestRequestedWeight"/></p>
                </div>
                <div class="text-center">
                  <h5><svg width="1em" height="1em"><use xlink:href="#volume"></use></svg> <c:out value="${request.key.requestedVolume}"/> M3 </h5>
                  <p><spring:message code="CreateRequestRequestedVolume"/></p>
                </div>
              </li>
              <li class="list-group-item text-truncate text-center"><h4>$<c:out value="${request.key.maxPrice}"/></h4></li>
            </ul>
          </div>
        </a>
      </c:forEach>
    </div>
    <c:if test="${acceptedRequests.size()>0}">
      <h3 class="mt-5 mb-2 text-center"><spring:message code="AcceptedRequests"/></h3>
      <div class="w-100 d-flex justify-content-center">
        <hr class="w-50">
      </div>
    </c:if>
    <div class="tripCards w-75 justify-content-center m-auto">
      <c:forEach var="request" items="${acceptedRequests}">
        <a class="text-decoration-none" href="<c:url value="/requests/manageRequest?requestId=${request.requestId}"/>">
          <div class="card m-3" style="width: 25rem;">
            <img src="https://s3-eu-central-1.amazonaws.com/eurosender-blog/wp-content/uploads/2019/09/11094537/pallets-min.jpg" class="card-img-top" alt="...">
            <h4 class="mx-4 my-3 w-25 position-absolute top-0 start-0"><span class="badge rounded-pill text-bg-primary"><spring:message code="${request.type}" htmlEscape="true"/></span></h4>
            <div class="card-body">
              <div class="w-100 d-flex space-apart">
                <div class="text-truncate text-center" style="width: 35%">
                  <h5><c:out value="${request.origin}"/></h5>
                  <c:out value="${request.minDepartureDate.dayOfMonth}/${request.minDepartureDate.monthValue}/${request.minDepartureDate.year}"/>
                </div>

                <div style="width: 30%">
                  <svg width="9em" height="3em"><use xlink:href="#arrow"></use></svg>
                </div>

                <div class="text-truncate text-center" style="width: 35%">
                  <h5><c:out value="${request.destination}"/></h5>
                  <c:out value="${request.maxArrivalDate.dayOfMonth}/${request.maxArrivalDate.monthValue}/${request.maxArrivalDate.year}"/>
                </div>
              </div>
            </div>
            <ul class="list-group list-group-flush">
              <li class="list-group-item px-5 pt-4 d-flex justify-content-between align-items-center">
                <div class="text-center">
                  <h5><svg width="1em" height="1em"><use xlink:href="#heavy"></use></svg> <c:out value="${request.requestedWeight}"/> KG </h5>
                  <p><spring:message code="CreateRequestRequestedWeight"/></p>
                </div>
                <div class="text-center">
                  <h5><svg width="1em" height="1em"><use xlink:href="#volume"></use></svg> <c:out value="${request.requestedVolume}"/> M3 </h5>
                  <p><spring:message code="CreateRequestRequestedVolume"/></p>
                </div>
              </li>
              <li class="list-group-item text-truncate text-center"><h4>$<c:out value="${request.maxPrice}"/></h4></li>
            </ul>
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
