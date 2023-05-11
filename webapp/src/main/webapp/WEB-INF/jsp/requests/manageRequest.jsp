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
  <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png"></head>
<body class="bodyContent">


<svg  xmlns="http://www.w3.org/2000/svg" style="display: none;">
  <symbol id="check" viewBox="0 0 16 16">
    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
  </symbol>
  <symbol id="star-fill" viewBox="0 0 16 16">
    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
  </symbol>
  <symbol id="star" viewBox="0 0 16 16">
    <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
  </symbol>
</svg>

<components:navBar/>
<div class="formCard justify-content-center align-items-center pt-5 mb-n5">
  <div class="inlineFormInputContainer">
    <div class="card inlineFormInputContainer" style="width: 40rem;">
      <div class="card-header">
        <h4 class="card-title"><b><spring:message code="Details"/></b></h4>
      </div>
      <div class="card-body">
        <img src="https://us.123rf.com/450wm/yehorlisnyi/yehorlisnyi2104/yehorlisnyi210400016/167492439-no-photo-or-blank-image-icon-loading-images-or-missing-image-mark-image-not-available-or-image.jpg?ver=6" class="card-img rounded-start p-3"  alt="TruckImg">
        <table class="table table-striped">
          <tr>
            <td><b><spring:message code="CargoType"/></b></td>
            <td><spring:message code="${request.type}" htmlEscape="true" /></td>
          </tr>
          <tr>
            <td><b><spring:message code="Origin"/>-<spring:message code="Destination"/></b></td>
            <td><c:out value="${request.origin}-${request.destination}"/></td>
          </tr>
          <tr>
            <td><b><spring:message code="DepartureDate"/> - <spring:message code="FiltersArrival"/></b></td>
            <td><c:out value="${request.departureDate.dayOfMonth}/${request.departureDate.monthValue}/${request.departureDate.year} - ${request.arrivalDate.dayOfMonth}/${request.arrivalDate.monthValue}/${request.arrivalDate.year}"/></td>
          </tr>
          <tr>
            <td><b><spring:message code="CreateRequestRequestedVolume"/></b></td>
            <td><c:out value="${request.volume}"/> m3</td>
          </tr>
          <tr>
            <td><b><spring:message code="CreateRequestRequestedWeight"/></b></td>
            <td><c:out value="${request.weight}"/> kg</td>
          </tr>
          <tr>
            <td><b><spring:message code="Price"/></b></td>
            <td>$<c:out value="${request.price}"/></td>
          </tr>
        </table>
      </div>
    </div>
    <c:if test="${request.truckerId > 0}">
      <div class="justify-content-top align-items-top px-5" >
        <div class="card" style="width: 18rem;">
          <div class="card-header">
            <h4><spring:message code="Driver"/>:</h4>
          </div>
          <div class="card-body p-3">
            <h5 class="card-title"><c:out value="${acceptUser.name.toUpperCase()}"/></h5>
            <p class="card-text"><c:out value="${acceptUser.email.toLowerCase()}"/></p>
          </div>
        </div>
        <div class="card mt-4" style="width: 18rem;">
          <div class="card-header">
            <h4><spring:message code="Status"/>:</h4>
          </div>
          <div class="card-body p-3">
            <c:if test="${request.provider_confirmation && !request.trucker_confirmation}">
              <p class="card-text py-1"><svg width="1em" height="1em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="ReceivedCargo"/></p>
            </c:if>
            <c:if test="${!request.provider_confirmation}">
              <p class="card-text py-1"><svg width="1em" height="1em" fill="gray"><use xlink:href="#check"></use></svg> <spring:message code="DidntReceiveCargo"/></p>
            </c:if>
            <c:if test="${request.trucker_confirmation && !request.provider_confirmation}">
              <p class="card-text py-1"><svg width="1em" height="1em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="DriverCompletedTrip"/></p>
            </c:if>
            <c:if test="${!request.trucker_confirmation}">
              <p class="card-text py-1"><svg width="1em" height="1em" fill="gray"><use xlink:href="#check"></use></svg> <spring:message code="DriverDidntCompleteTrip"/></p>
            </c:if>
            <c:if test="${request.provider_confirmation && request.trucker_confirmation}">
              <h4 class="card-text py-1"><svg class="mx-2" width="2em" height="2em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="TripFinished"/></h4>
            </c:if>
          </div>
        </div>
        <c:if test="${!request.provider_confirmation }">
          <c:url value="/requests/confirmRequest" var="confirmPath"/>
          <form:form method="post" action="${confirmPath}?requestId=${request.tripId}">
            <spring:message var="received" code="IReceivedCargo"/>
            <input type="submit" class="btn btn-color mt-3 w-100" value="${received}"/>
          </form:form>
        </c:if>
      </div>
    </c:if>
    <c:if test="${request.trucker_confirmation && request.provider_confirmation }">
      <c:if test="${!reviewed}">
        <c:url value="/requests/sendReview" var="reviewPath"/>
        <form:form method="post" modelAttribute="acceptForm" action="${reviewPath}?requestid=${request.tripId}&reviewsenid=${userId}&reviewrecid=${acceptUser}">
          <div class="card mt-4" style="width: 18rem;">
            <div class="card-header">
              <h4>
                <c:if test="${currentRole == 'TRUCKER'}">
                  <spring:message code="ReviewProvider"/>
                </c:if>
                <c:if test="${currentRole == 'PROVIDER'}">
                  <spring:message code="ReviewTrucker"/>
                </c:if>
              </h4>
            </div>
            <div class="card-body p-3">
              <div>
                <button type="button" onclick="changeStars(0)" class="btn-color btn mr-2">-</button>
                <c:forEach items="${selectedStars}">
                  <svg width="1em" height="1em" class="rating-stars"><use class="star" xlink:href="#star-fill"></use></svg>
                </c:forEach>
                <c:forEach begin="0" step="1" end="${4-selectedStars}">
                  <svg width="1em" height="1em" class="rating-stars"><use class="star" xlink:href="#star"></use></svg>
                </c:forEach>
                <button type="button" onclick="changeStars(1)" class="btn-color btn ml-2">+</button>
              </div>
              <div class="mt-2">
                <spring:message var="writeReview" code="WriteReview"/>
                <form:textarea type="text" class="form-control" path="description" placeholder="${writeReview}"/>
              </div>
            </div>
          </div>
          <spring:message var="sendReview" code="SendReview"/>
          <input type="submit" class="btn btn-color mt-3 w-100" value="${sendReview}"/>
        </form:form>
      </c:if>
      <c:if test="${reviewed}">
        <h4 class="card-text py-1"><svg class="mx-2" width="2em" height="2em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="ReviewSent"/></h4>
      </c:if>
    </c:if>
  <c:if test="${request.truckerId <= 0}">
    <div class="justify-content-top align-items-top px-5" >
      <c:forEach var="offer" items="${offers}">
        <c:url value="/requests/acceptProposal" var="acceptPath"/>
        <form:form action="${acceptPath}?proposalid=${offer.proposalId}&requestid=${offer.tripId}" method="post">
          <div class="card p-3" style="width: 18rem;">
            <div class="ca rd-body">
              <h5 class="card-title"><c:out value="${offer.userName.toUpperCase()}"/></h5>
              <p class="card-text"><c:out value="${offer.description}"/></p>
              <spring:message code="Trips.AcceptProposal" var="reserve"/>
              <input type="submit" class="btn btn-color" value="${reserve}"/>
            </div>
          </div>
        </form:form>
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
  var selectedStars=[1,2,3]

  function changeStars(action){
    if (action === 1)
      selectedStars++;
    if (action === 0)
      selectedStars--;
  }
  $(document).ready(function() {
    // Event handler for when a star is clicked
    $('.star').click(function() {
      var rating = $(this).data('rating');
      //TODO: enviar al server el nuevo rating seleccionado
      // Update the star colors based on the clicked star
      $(this).addClass('bi-star-fill');
      $(this).prevAll().addClass('bi-star-fill');
      $(this).nextAll().removeClass('bi-star-fill');
    });
  });
</script>
