<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>

<html>

<link>
<link href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"/>" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous"/>
<script src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"/>" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>
<link href="<c:url value="/css/userControl.css"/>" rel="stylesheet"/>

<head>
    <title><spring:message code="Register"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png"></head>

<body class="bodyContent">
<c:url value="/register" var="postPath"/>
<components:navBar/>


<div class="w-75 m-auto">
    <div class="card mb-3 mt-5 formCard">
        <div class="card-body">
            <c:if test="${!success}">
                <h1 class="h3 my-3 text-center"><spring:message code="Register"/></h1>
                <p class="text-center"><spring:message code="AlreadyUser"/><a href="<c:url value="/login"/>"><spring:message code="Login"/></a></p>
                <form:form modelAttribute="userForm" action="${postPath}" method="post">
                    <div class="mb-3">
                        <form:label path="cuit" class="form-label" ><spring:message code="Cuit"/></form:label>
                        <form:errors cssClass="formError" path="cuit" element="p"/>
                        <form:input type="text" class="form-control" id="cuit" maxlength="13" path="cuit" placeholder="CUIT"/>
                    </div>
                    <div class="mb-3">
                        <form:label path="name" class="form-label"><spring:message code="Name"/></form:label>
                        <form:errors cssClass="formError" path="name" element="p"/>
                        <form:input type="text" class="form-control" path="name" placeholder="Juan Perez"/>
                    </div>
                    <div class="mb-3">
                        <form:label path="email" class="form-label"><spring:message code="Email"/></form:label>
                        <form:errors cssClass="formError" path="email" element="p"/>
                        <form:input type="email" class="form-control" path="email" placeholder="name@example.com"/>
                    </div>
                    <div class="mb-3">
                        <form:label path="role" class="form-label"><spring:message code="Role"/></form:label>
                        <form:errors path="role" class="formError" element="p"/>
                        <div>
                            <form:radiobutton path="role" id="clientRadio" name="role" value="PROVIDER" />
                            <form:label path="" for="clientRadio"><spring:message code="ProviderMessage"/></form:label>

                        </div>
                        <div>
                            <form:radiobutton path="role" id="truckerRadio" name="role" value="TRUCKER" />
                            <form:label path="" for="truckerRadio"><spring:message code="TruckerMessage"/></form:label>
                        </div>
                    </div>

                    <div class="mb-3">
                        <form:label path="password" class="form-label"><spring:message code="Password"/></form:label>
                        <form:errors cssClass="formError" path="password" element="p"/>
                        <form:input type="password" class="form-control" path="password"  placeholder="********"/>
                    </div>
                    <div class="mb-3">
                        <form:label path="repeatPassword" class="form-label"><spring:message code="ConfirmPassword"/></form:label>
                        <form:errors cssClass="formError" path="repeatPassword" element="p"/>
                        <form:input type="password" class="form-control" path="repeatPassword" placeholder="********"/>
                        <form:errors cssClass="formError"/>
                    </div>
                    <button class="w-100 btn btn-lg btn-color" type="submit">Register</button>
                </form:form>
            </c:if>
            <c:if test="${success}">
                <h3 class="my-3 text-center"><spring:message code="RegisterSuccess"/></h3>
                <h5 class="text-center fw-normal my-3"><spring:message code="CheckEmailVerification"/></h5>
                <h5 class="text-center my-3"><c:out value="${email}"/></h5>
            </c:if>
        </div>
    </div>
</div>
</body>
<components:waveDivider/>
<components:footer/>
</html>

<script>
    const tele = document.querySelector('#cuit');
    tele.addEventListener('keyup', function(e){
        if (e.key !== 'Backspace' && (tele.value.length === 2 || tele.value.length === 11)){
            tele.value += '-';
        }
    });
</script>