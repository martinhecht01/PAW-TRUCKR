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
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png"></head>
<body class="bodyContent">

<svg  xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="check" viewBox="0 0 16 16">
        <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
    </symbol>
</svg>


<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
    <symbol id="star-fill" viewBox="0 0 16 16">
        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
    </symbol>
</svg>

<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16">
    <symbol id="star" viewBox="0 0 16 16">
        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
    </symbol>
</svg>

<c:url value="/requests/sendProposal" var="postPath"/>
<components:navBar/>
<div class="formCard justify-content-center align-items-center pt-5 mb-n5">
    <div class="inlineFormInputContainer">
        <div class="card inlineFormInputContainer" style="width: 40rem;">
            <div class="card-header">
                <h4 class="card-title"><b><spring:message code="Details"/></b></h4>
            </div>
            <div class="card-body">
                <img src="https://s3-eu-central-1.amazonaws.com/eurosender-blog/wp-content/uploads/2019/09/11094537/pallets-min.jpg" class="card-img rounded-start p-3"  alt="TruckImg">
                <table class="table table-srequested">
                    <tr>
                        <td><b><spring:message code="Driver"/></b></td>
                        <td><c:out value="${user.name.toUpperCase()}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="CargoType"/></b></td>
                        <td><spring:message code="${request.type}" htmlEscape="true" /></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="Origin"/> - <spring:message code="Destination"/></b></td>
                        <td><c:out value="${request.origin}-${request.destination}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="DepartureDate"/> - <spring:message code="FiltersArrival"/></b></td>
                        <td><c:out value="${request.minDepartureDate.dayOfMonth}/${request.minDepartureDate.monthValue}/${request.minDepartureDate.year} - ${request.maxArrivalDate.dayOfMonth}/${request.maxArrivalDate.monthValue}/${request.maxArrivalDate.year}"/></td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="AvailableVolume"/></b></td>
                        <td><c:out value="${request.requestedVolume}"/> m3</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="AvailableWeight"/></b></td>
                        <td><c:out value="${request.requestedWeight}"/> kg</td>
                    </tr>
                    <tr>
                        <td><b><spring:message code="Price"/></b></td>
                        <td>$<c:out value="${request.maxPrice}"/></td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="inlineFormInputContainer justify-content-top align-items-top" >

        </div>
        <c:if test="${request.acceptUserId <= 0}">
            <div class="inlineFormInputContainer justify-content-top align-items-top" >
                <form:form modelAttribute="acceptForm" action="${postPath}?id=${request.requestId}" method="post">
                    <div class="card browseCards" style="width: 20rem;">
                        <div class="card-header">
                            <h4 class="card-title" style="color: #142D4C"><b><spring:message code="ReserveTrip"/></b></h4>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <form:label for="description" class="form-label" path="description"><spring:message code="Description"/></form:label>
                                <form:textarea type="text" class="form-control" id="description" path="description" placeholder="Write a description"/>
                            </div>
                            <div>
                                <spring:message code="Reserve" var="reserve"/>

                                <c:if test="${currentRole == ''}">
                                    <a href="/login" class="btn btn-color">${reserve}</a>
                                </c:if>
                                <c:if test="${currentRole == 'TRUCKER' || currentRole == 'PROVIDER'}">
                                    <input type="submit" class="btn btn-color" value="${reserve}"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </c:if>
        <c:if test="${request.acceptUserId == userId}">
            <div class="justify-content-top align-items-top px-5" >
                <a href="<c:url value="/profile?id=${request.userId}"/>">
                <div class="card" style="width: 18rem;">
                    <div class="card-header">
                        <h4><spring:message code="Driver"/>:</h4>
                    </div>
                    <div class="card-body p-3">
                        <h5 class="card-title"><c:out value="${user.name.toUpperCase()}"/></h5>
                        <p class="card-text"><c:out value="${user.email.toLowerCase()}"/></p>
                    </div>
                </div>
                </a>
                <div class="card mt-4" style="width: 18rem;">
                    <div class="card-header">
                        <h4><spring:message code="Status"/>: </h4>
                    </div>
                    <div class="card-body p-3">
                        <c:if test="${request.receiverConfirmation && !request.senderConfirmation}">
                            <p class="card-text py-1"><svg width="1em" height="1em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="ReceivedCargo"/></p>
                        </c:if>
                        <c:if test="${!request.receiverConfirmation}">
                            <p class="card-text py-1"><svg width="1em" height="1em" fill="gray"><use xlink:href="#check"></use></svg> <spring:message code="DidntReceiveCargo"/></p>
                        </c:if>
                        <c:if test="${request.senderConfirmation && !request.receiverConfirmation}">
                            <p class="card-text py-1"><svg width="1em" height="1em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="DriverCompletedTrip"/></p>
                        </c:if>
                        <c:if test="${!request.senderConfirmation}">
                            <p class="card-text py-1"><svg width="1em" height="1em" fill="gray"><use xlink:href="#check"></use></svg> <spring:message code="DriverDidntCompleteTrip"/></p>
                        </c:if>
                        <c:if test="${request.receiverConfirmation && request.senderConfirmation}">
                            <h4 class="card-text py-1"><svg class="mx-2" width="2em" height="2em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="TripFinished"/></h4>
                        </c:if>
                    </div>
                </div>
                <c:if test="${request.acceptUserId > 0 && !request.receiverConfirmation}">
                    <c:url value="/requests/confirmRequest" var="confirmPath"/>
                    <form:form method="post" action="${confirmPath}?id=${request.requestId}">
                        <spring:message var="received" code="IReceivedCargo"/>
                        <input type="submit" class="btn btn-color mt-3 w-100" value="${received}"/>
                    </form:form>
                </c:if>
                <c:if test="${request.senderConfirmation && request.receiverConfirmation }">
                    <c:if test="${reviewed == null}">
                        <c:url value="/requests/sendReview" var="reviewPath"/>
                        <form:form id="reviewForm" method="post" modelAttribute="acceptForm" action="${reviewPath}?requestid=${request.requestId}&userid=${request.acceptUserId}&rating=4">
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
                                        <form:textarea type="text" id="review" class="form-control" path="description" placeholder="${writeReview}"/>
                                    </div>
                                </div>
                            </div>
                            <spring:message var="sendReview" code="SendReview"/>
                            <input type="submit" class="btn btn-color mt-3 w-100" value="${sendReview}"/>
                        </form:form>
                    </c:if>
                    <c:if test="${reviewed != null}">
                        <div class="card mt-4" style="width: 18rem;">
                            <div class="card-body p-3">
                                <h4 class="card-text py-1"><svg class="mx-2" width="2em" height="2em" fill="green"><use xlink:href="#check"></use></svg> <spring:message code="ReviewSent"/></h4>
                            </div>
                        </div>
                    </c:if>
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