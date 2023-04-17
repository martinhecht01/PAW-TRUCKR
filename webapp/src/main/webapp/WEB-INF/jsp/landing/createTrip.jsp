<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>

<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


</head>
<body class="bodyContent">
<c:url value="/create" var="postPath"/>

<components:navBar/>
<%--Formulario--%>
<form:form modelAttribute="tripForm" action="${postPath}" method="post">
    <div class="card w-75 mb-3 mt-5 formCard">
        <div class="card-header">
            <h4 class="card-title"><b><spring:message code="CreateTrip"/></b></h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <form:label for="name" class="form-label" path="name"><spring:message code="Name"/></form:label>
                <form:errors path="name" cssClass="formError" element="p"/>
                <form:input type="text" class="form-control" placeholder="Juan Romero" path="name"/>
            </div>

            <div class="mb-3">
                <form:label class="form-label" path="id"><spring:message code="Cuit"/></form:label>
                <form:errors path="id" cssClass="formError" element="p"/>
                <form:input type="text" class="form-control" path="id" placeholder="00-00000000-0"/>
            </div>

            <div class="mb-3">
                <form:label for="email" class="form-label" path="email"><spring:message code="Email"/></form:label>
                <form:errors path="email" cssClass="formError" element="p"/>
                <form:input type="text" class="form-control" path="email" placeholder="ejemplo@gmail.com"/>
            </div>

            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label path="licensePlate" class="form-label"><spring:message code="CreateTripLicensePlate"/></form:label>
                    <form:errors path="licensePlate" cssClass="formError" element="p"/>
                    <form:input type="text" class="form-control" path="licensePlate" placeholder="AAA123"/>
                </div>

                <div class="mb-3 inlineFormInput">
                    <form:label path="cargoType" class="form-label"><spring:message code="CreateTripCargoType"/></form:label>
                    <form:errors path="cargoType" cssClass="formError" element="p"/>
                    <form:select class="form-select" path="cargoType">
<%--                        --%>
                        <option selected>...</option>
                        <option value="<spring:message code="CreateTripCargoTypeRefrigerated"/>"><spring:message code="CreateTripCargoTypeRefrigerated"/></option>
                        <option value="<spring:message code="CreateTripCargoTypeHazardous"/>"><spring:message code="CreateTripCargoTypeHazardous"/></option>
                        <option value="<spring:message code="CreateTripCargoTypeNormal"/>"><spring:message code="CreateTripCargoTypeNormal"/></option>
                    </form:select>
                </div>
            </div>

            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label path="origin" for="origin" class="form-label"><spring:message code="Origin"/></form:label>
                    <form:errors path="origin" cssClass="formError" element="p"/>
                    <form:input type="text" class="form-control" path="origin" placeholder="Buenos Aires (CABA)"/>
                </div>


                <div class="mb-3 inlineFormInput">
                    <form:label path="destination" for="destination" class="form-label"><spring:message code="Destination"/></form:label>
                    <form:errors path="destination" cssClass="formError" element="p"/>
                    <form:input type="text" class="form-control" path="destination" placeholder="Cordoba (CBA)"/>
                </div>
            </div>

            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label for="departureDate" path="departureDate" class="form-label"><spring:message code="DepartureDate"/></form:label>
                    <form:errors path="departureDate" cssClass="formError" element="p"/>
                    <form:input type="datetime-local" class="form-control" path="departureDate" placeholder="DD/MM/AAAA"/>
                </div>

                <div class="mb-3 inlineFormInput">
                    <form:label for="arrivalDate" path="arrivalDate" class="form-label"><spring:message code="ArrivalDate"/></form:label>
                    <form:errors path="arrivalDate" cssClass="formError" element="p"/>
                    <form:input type="datetime-local" class="form-control" path="arrivalDate" placeholder="DD/MM/AAAA"/>
                </div>
            </div>

            <div class="inlineFormInputContainer">
                <div class="mb-3 mr-3 inlineFormTripleInput">
                    <form:label path="availableVolume"  class="form-label"><spring:message code="AvailableVolume"/></form:label>
                    <form:errors path="availableVolume" cssClass="formError" element="p"/>
                    <div class="input-group">
                        <form:input type="text" class="form-control" path="availableVolume" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">m3</span>
                        </div>
                    </div>
                </div>

                <div class="mb-3 mx-3 inlineFormTripleInput">
                    <form:label path="availableWeight" for="origin" class="form-label"><spring:message code="AvailableWeight"/></form:label>
                    <form:errors path="availableWeight" cssClass="formError" element="p"/>
                    <div class="input-group">
                        <form:input type="text" class="form-control" path="availableWeight" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">kg</span>
                        </div>
                    </div>
                </div>


                <div class="mb-3 ml-3 inlineFormTripleInput">
                    <form:label path="price" class="form-label"><spring:message code="Price"/></form:label>
                    <form:errors path="price" cssClass="formError" element="p"/>
                    <div class="input-group">
                        <form:input type="text" class="form-control" path="price" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">ARS</span>
                        </div>
                    </div>
                </div>
            </div>

            <input type="submit" value="<spring:message code="CreateTrip"/>" class="btn btn-color mt-3 formButton"/>
        </div>
    </div>
</form:form>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>

</body>
</html>

<script>
</script>