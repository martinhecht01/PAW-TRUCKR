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
</svg>
<form:form method="get">
  <div class="d-flex pt-5" style="width: 100%; padding: 0 10% ">
    <div class="tripCards m-auto">
      <c:if test="${offers.size() == 0}">
        <h2 class="display-5 fw-bold text-body-emphasis text-center"><spring:message code="NoRequestsAvailable"/></h2>
      </c:if>
      <c:forEach var="request" items="${offers}">
        <a class="card mb-3 browseCards" href="<c:url value="/requests/manageRequest?requestId=${request.requestId}"/>" style="display: flex; padding: 0">
          <div class="card-header">
            <div class="row g-0">
              <div style="display: flex; justify-content: space-between; border-right: 3px black">
                <div class="py-1 px-3" style="width: 50%; justify-content: space-between; display: flex;">
                  <div style="display: flex; width: 100%; justify-content: space-between; text-align: center">
                    <div>
                      <div class="mx-2">
                        <h5><c:out value="${request.origin}"/></h5>
                        <c:out value="${request.minDepartureDate.dayOfMonth}/${request.minDepartureDate.monthValue}/${request.minDepartureDate.year}"/>
                      </div>
                    </div>
                    <div>
                      <div>
                        <svg width="9em" height="3em"><use xlink:href="#arrow"></use></svg>
                      </div>
                    </div>
                    <div>
                      <div class="mx-2">
                        <h5><c:out value="${request.destination}"/></h5>
                        <c:out value="${request.maxArrivalDate.dayOfMonth}/${request.maxArrivalDate.monthValue}/${request.maxArrivalDate.year}"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div  class="py-3" style="display: flex; flex-direction: row; width: 50%; justify-content: center; text-align: center; align-items: center">
                  <h5 class="px-3"><c:out value="${request.type}"/></h5>
                    <%--                <svg width="2em" height="3em"><use xlink:href="#cold"></use></svg>--%>
                </div>
              </div>
            </div>
          </div>
          <div class="row g-0">
            <div style="display: flex; justify-content: space-between">
              <div  class="p-2" style="width: 50%; height: 100%; justify-content: center; align-items: center">
                <div class="row g-0" style="height: 75%">
                  <div style="display: flex; margin-top: auto; justify-content: space-between">
                    <div style="display: flex; flex-direction: column; width: 50%; justify-content: center; text-align: center; align-items: center">
                      <p class="pb-2"><spring:message code="CreateRequestRequestedWeight"/></p>
                      <svg width="3em" height="3em"><use xlink:href="#heavy"></use></svg>
                      <h4><c:out value="${request.requestedWeight}"/> KG </h4>
                    </div>
                    <div style="display: flex; flex-direction: column; width: 50%; justify-content: center; text-align: center; align-items: center">
                      <p class="pb-2"><spring:message code="CreateRequestRequestedVolume"/></p>
                      <svg width="3em" height="3em"><use xlink:href="#volume"></use></svg>
                      <h4><c:out value="${request.requestedVolume}"/> M3 </h4>
                    </div>
                  </div>
                </div>
                <div class="row g-0 pt-3" style="text-align: center; height: 25%">
                  <div>
                    <h4>$<c:out value="${request.maxPrice}"/></h4>
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
