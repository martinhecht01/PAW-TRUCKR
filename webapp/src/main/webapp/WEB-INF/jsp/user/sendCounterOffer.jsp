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
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<body class="bodyContent">

<svg  xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="check" viewBox="0 0 16 16">
        <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"></path>
    </symbol>
    <symbol id="star-fill" viewBox="0 0 16 16">
        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
    </symbol>
    <symbol id="star" viewBox="0 0 16 16">
        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"></path>
    </symbol>
</svg>

<c:url value="/offers/sendCounterOffer" var="postPath"/>
<components:navBar/>
<div class="formCard justify-content-center align-items-center pt-5 mb-n5">
    <div class="inlineFormInputContainer">
        <div class="card inlineFormInputContainer" style="width: 40rem;">
            <div class="card-header">
                <h4 class="card-title"><b><spring:message code="CounterOffer"/>:</b></h4>
            </div>
            <div class="card-body">
                <form:form modelAttribute="acceptForm" action="${postPath}?offerId=${proposal.proposalId}" method="post">
                    <div class="mb-3">
                        <form:label for="description" class="form-label" path="description"><spring:message code="Description"/></form:label>
                        <spring:message var="writeDescription" code="WriteDescription"/>
                        <form:textarea type="text" id="description" class="form-control" path="description" placeholder="${writeDescription}"/>
                    </div>
                    <div class="mb-3 flex-column">
                        <form:label for="description" class="form-label" path="price"><spring:message code="OfferedPrice"/></form:label>
                        <form:errors cssClass="formError" path="price"/>
                        <form:input type="number" id="description" class="form-control" path="price"  placeholder="0"/>
                    </div>
                    <div>
                        <spring:message code="SendOffer" var="send"/>
                        <input type="submit" class="btn btn-color" value="${send}"/>
                    </div>
                </form:form>
            </div>
        </div>
            <div class="justify-content-top align-items-top px-5" >
                <div class="card" style="width: 30rem;">
                    <div class="card-header">
                        <h4><spring:message code="OriginalOffer"/></h4>
                    </div>
                    <div class="card-body">
                        <a href="<c:url value="/profile?id=${proposal.user.userId}"/>">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="card-title"><c:out value="${proposal.user.name.toUpperCase()}"/></h5>
                                <img class="profileImageNavbar" src="<c:url value="/user/${proposal.user.userId}/profilePicture"/>"/>
                            </div>
                        </a>
                        <div class="mb-3">
                            <label for="description" class="form-label"><spring:message code="Description"/></label>
                            <textarea id="description" disabled class="form-control bg-light">${proposal.description}</textarea>
                        </div>
                        <div class="d-flex w-100 flex-row align-items-center justify-content-between">
                            <div class="w-25 mb-3 flex-column">
                                <label for="description" class="form-label"><spring:message code="OfferedPrice"/></label>
                                <h4>$${proposal.price}</h4>
                            </div>
                        </div>
                    </div>
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

<script>
    $(function() {
        $('.rating input[type="radio"]').on('change', function() {
            var rating = $(this).val();
            $('input[name="rating_value"]').val(rating);
        });
    });
</script>


