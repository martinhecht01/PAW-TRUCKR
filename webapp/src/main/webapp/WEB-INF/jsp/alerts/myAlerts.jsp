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
      <h3><spring:message code="MyAlert"/></h3>
    </div>
    <div class="card-body">
        <div class="d-flex justify-content-center w-100 align-items-center flex-column">
          <c:if test="${currentUser.alert == null}">
            <h5><spring:message code="NoAlert"/></h5>
            <a class="w-25 mt-3 btn btn-lg btn-color btn-outline-primary" href="<c:url value="/alerts/createAlert"/>"><spring:message code="CreateAlert"/></a>
          </c:if>
          <c:if test="${currentUser.alert != null}">
             <div class="d-flex flex-row w-100 p-3 justify-content-between">
                <div class="d-flex flex-column w-25 px-5">
                  Ciudad
                  <label for="text"></label>
                  <input type="text" id="text" value="${currentUser.alert.city}" disabled="true">
                </div>
               <div class="d-flex w-75 px-5">
                 <div class="d-flex flex-column px-3" style="width: 40%">
                   <label for="from">Desde</label>
                   <input type="datetime-local" id="from" value="${currentUser.alert.fromDate}" disabled>
                 </div>
                 <div class="d-flex flex-column px-3" style="width: 40%">
                   <label for="to">Hasta</label>
                   <input type="datetime-local" id="to" value="${currentUser.alert.toDate}" disabled>
                   <div class="form-check my-2">
                     <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" disabled <c:if test="${currentUser.alert.toDate == null}"> checked </c:if>>
                     <label class="form-check-label" for="flexRadioDefault1">
                       Indefinido
                     </label>
                   </div>
                 </div>
                 <div class="d-flex h-100 align-content-center justify-content-center pt-3" style="width: 20%">
                   <c:url value="/alerts/delete" var="postPath"/>
                   <form:form method="post" action="${postPath}">
                     <spring:message code="Delete" var="Delete"/>
                     <input type="submit" class="btn btn-outline-danger mx-2" value="${Delete}">
                   </form:form>
                 </div>
               </div>
             </div>
          </c:if>
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