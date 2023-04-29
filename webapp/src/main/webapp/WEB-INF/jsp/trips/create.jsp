<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>

<head>
    <title><spring:message code="CreateTrip"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/JmB4xhT/Truckr-Logo.png">
</head>
<body class="bodyContent">
<c:url value="/trips/create" var="postPath"/>

<components:navBar/>
<%--Formulario--%>
<form:form modelAttribute="tripForm" action="${postPath}" method="post">
    <div class="card w-75 mb-3 mt-5 formCard">
        <div class="card-header">
            <h4 class="card-title"><b><spring:message code="CreateTrip"/></b></h4>
        </div>
        <div class="card-body">
            <form:errors cssClass="formError"/>
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
                        <form:option value="" disabled="true" selected="true"><spring:message code="Select"/></form:option>
                        <form:option value="Refrigerated"><spring:message code="CreateTripCargoTypeRefrigerated"/></form:option>
                        <form:option value="Hazardous"><spring:message code="CreateTripCargoTypeHazardous"/></form:option>
                        <form:option value="Normal"><spring:message code="CreateTripCargoTypeNormal"/></form:option>
                    </form:select>
                </div>
            </div>

            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label path="origin" for="origin" class="form-label"><spring:message code="Origin"/></form:label>
                    <form:errors path="origin" cssClass="formError" element="p"/>
                    <form:select class="form-select" path="origin">
                        <form:option value="" disabled="true" selected="true"><spring:message code="Select"/></form:option>
                        <form:options items="${cities}"/>
                    </form:select>
                </div>
                <div class="mb-3 inlineFormInput">
                    <form:label path="destination" for="destination" class="form-label"><spring:message code="Destination"/></form:label>
                    <form:errors path="destination" cssClass="formError" element="p"/>
                    <form:select class="form-select" path="destination">
                        <form:option value="" disabled="true" selected="true"><spring:message code="Select"/></form:option>
                        <form:options items="${cities}"/>
                    </form:select>
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
    const tele = document.querySelector('#telle');

    tele.addEventListener('keyup', function(e){
        if (e.key !== 'Backspace' && (tele.value.length === 2 || tele.value.length === 11)){
            tele.value += '-';
        }
    });

</script>