<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="html" uri="http://www.springframework.org/tags/form" %>

<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>

<head>
    <title><spring:message code="CreateTrip"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<body class="bodyContent">
<c:url value="/trips/create" var="postPath"/>

<components:navBar/>
<%--Formulario--%>
<form:form modelAttribute="tripForm" action="${postPath}" method="post" enctype="multipart/form-data">
    <div class="card w-75 mb-3 mt-5 formCard">
        <div class="card-header">
            <h4 class="card-title"><b><spring:message code="CreateTrip"/></b></h4>
        </div>
        <div class="card-body">
            <a href="<c:url value="/requests/browse"/>">
                <img id="imagePreview" src="https://us.123rf.com/450wm/yehorlisnyi/yehorlisnyi2104/yehorlisnyi210400016/167492439-no-photo-or-blank-image-icon-loading-images-or-missing-image-mark-image-not-available-or-image.jpg?ver=6" class="image-wrapper-create rounded-start p-3"  alt="TruckImg">
            </a>
            <div>
                <form:label path="tripImage" class="form-label"><spring:message code="uploadTrip"/></form:label>
                <form:errors cssClass="formError" path="tripImage" element="p"/>
            </div>
            <div  class="input-group mb-3">
                <form:input  path="tripImage" id="inputGroupFile01" class= "form-control" type="file" accept="image/png, image/jpeg" onchange="previewImage()"/>
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
                    <form:select class="form-select" path="origin" html:required="true">
                        <form:option value="" disabled="true" selected="true"><spring:message code="Select"/></form:option>
                        <form:options items="${cities}"/>
                    </form:select>
                </div>
                <div class="mb-3 inlineFormInput">
                    <form:label path="destination" for="destination" class="form-label"><spring:message code="Destination"/></form:label>
                    <form:errors path="destination" cssClass="formError" element="p"/>
                    <form:select class="form-select" path="destination" html:required="true">
                        <form:option value="" disabled="true" selected="true"><spring:message code="Select"/></form:option>
                        <form:options items="${cities}"/>
                    </form:select>
                </div>
            </div>
            <form:errors cssClass="formError"/>
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
                        <form:input type="number" onkeydown="return ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode === 8)" min="0" step="1" class="form-control" path="availableVolume" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">m3</span>
                        </div>
                    </div>
                </div>

                <div class="mb-3 mx-3 inlineFormTripleInput">
                    <form:label path="availableWeight" for="origin" class="form-label"><spring:message code="AvailableWeight"/></form:label>
                    <form:errors path="availableWeight" cssClass="formError" element="p"/>
                    <div class="input-group">
                        <form:input type="number" onkeydown="return ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode === 8)" min="0" step="1" class="form-control" path="availableWeight" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">kg</span>
                        </div>
                    </div>
                </div>


                <div class="mb-3 ml-3 inlineFormTripleInput">
                    <form:label path="price" class="form-label"><spring:message code="Price"/></form:label>
                    <form:errors path="price" min="0" step="1" cssClass="formError" element="p"/>
                    <div class="input-group">
                        <form:input type="number" onkeydown="return ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode === 8)" class="form-control" path="price" placeholder="0"/>
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
<script>
    function previewImage() {
        let output = document.getElementById('imagePreview');
        output.src = URL.createObjectURL(event.target.files[0]);
        output.onload = function() {
            URL.revokeObjectURL(output.src)
        }
    }
</script>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>

</body>
</html>