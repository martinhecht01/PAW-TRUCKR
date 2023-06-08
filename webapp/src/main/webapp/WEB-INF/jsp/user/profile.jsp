<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<link>
<link href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"/>" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous"/>
<script src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"/>" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>
<link href="<c:url value="/css/userControl.css"/>" rel="stylesheet"/>

<head>
    <title>Truckr</title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>

<body class="bodyContent">

<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="star-fill" viewBox="0 0 16 16">
        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
    </symbol>
</svg>


<components:navBar/>
<div class="m-auto w-50">
    <div class="d-flex justify-content-center mt-5">
        <div class="card w-50 mx-3">
            <div class="card-header">
                <h4 class="card-title"><b><spring:message code="Profile"/></b></h4>
            </div>
            <div class="card-body">
                <div class="text-center my-3">
                    <img id="imagePreview" src="<c:url value="/user/${currUser.userId}/profilePicture"/>" class="profileImage" alt="Profile Picture"/>
                </div>
                <div>
                    <h5><b><spring:message code="Name"/></b></h5>
                    <p><c:out value="${currUser.getName()}"/></p>
                </div>
                <div>
                    <h5><b><spring:message code="Cuit"/></b></h5>
                    <p><c:out value="${currUser.getCuit()}"/></p>
                </div>
                <div>
                    <h5><b><spring:message code="Email"/></b></h5>
                    <p><c:out value="${currUser.getEmail()}"/></p>
                </div>
<%--                <div>--%>
<%--                    <h5><b><spring:message code="Role"/></b></h5>--%>
<%--                    <p><spring:message code="${currentRole}" htmlEscape="true"/></p>--%>
<%--                </div>--%>
                <c:if test="${currUser.cuit == currentUser.cuit}">
                    <div>
                        <a href="<c:url value="/profile/edit"/>" class="w-100 btn btn-lg btn-color"><spring:message code="editProfile"/></a>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mx-3 w-25 h-25">
            <div class="card mb-3">
                <div class="card-header">
                    <h5 class="card-title"><b><spring:message code="CompletedTrips"/></b></h5>
                </div>
                <div class="card-body flex-grow-1">
                    <h1 class="text-center">${completedTrips}</h1>
                </div>
            </div>
        </div>
    </div>
    <div class="card mt-5">
        <div class="card-header" style="display: inline-flex">
            <h4><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg><c:if test="${userReviews.size() != 0}"><c:out value="${currUser.rating}"/></c:if> - (${currUser.reviews.size()} <spring:message code="Reviews"/>)</h4>
        </div>
        <div class="card-body">
            <c:if test="${currUser.reviews.size() == 0}">
                <p><b><spring:message code="NoReviewsYet"/></b></p>
            </c:if>
            <ul class="list-group list-group-flush">
            <c:forEach items="${currUser.reviews}" var="review">
                <li class="list-group-item">
                    <h5><b><c:out value="${review.rating}"/> <svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg>
                    </b></h5>
                    <c:if test="${review.review != ''}">
                        <p><c:out value="${review.review}"/></p>
                    </c:if>
                </li>
            </c:forEach>
            </ul>
        </div>
    </div>
</div>
<components:waveDivider/>
<components:footer/>
</body>
</html>