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

<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
    <symbol id="star-fill" viewBox="0 0 16 16">
        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
    </symbol>
</svg>

<head>
    <title>Truckr</title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png"></head>
<body class="bodyContent">
<components:navBar/>
<div class="m-auto pt-5">
    <main class="form-signin m-auto" style="display: flex;gap: 15%;">
        <div class="card">
            <div class="card-header">
                <h4 class="card-title"><b><spring:message code="Profile"/></b></h4>
            </div>
            <div class="card-body">
                <div>
                    <h5><b>Name:</b></h5>
                    <p><c:out value="${currentUser.getName()}"/></p>
                </div>
                <div>
                    <h5><b>Cuit:</b></h5>
                    <p><c:out value="${currentUser.getCuit()}"/></p>
                </div>
                <div>
                    <h5><b>Email:</b></h5>
                    <p><c:out value="${currentUser.getEmail()}"/></p>
                </div>
                <div>
                    <h5><b>Role:</b></h5>
                    <p><c:out value="${currentRole}"/></p>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header" style="display: inline-flex">
                <h4 class="card-title"><b><c:out value="${currentUser.name}"/> </b></h4>
                <h4><svg width="1em" height="1em"><use class="star" xlink:href="#star-fill"></use></svg> <c:out value="${userRating}"/></h4>
            </div>
            <div class="card-body">
                <c:if test="${userReviews.size() == 0}">
                    <p><b><spring:message code="NoReviewsYet"/></b></p>
                </c:if>
                <ul class="list-group list-group-flush">
                <c:forEach items="${userReviews}" var="review">
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
    </main>
</div>
<components:waveDivider/>
<components:footer/>
</body>
</html>