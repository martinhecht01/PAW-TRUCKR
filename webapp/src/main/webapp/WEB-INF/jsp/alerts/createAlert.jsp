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
    <title><spring:message code="CreateAlert"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<body class="bodyContent">
<c:url value="/alerts/create" var="postPath"/>

<components:navBar/>
<%--Formulario--%>
<form:form modelAttribute="alertForm" action="${postPath}" method="post">
    <div class="card w-75 mb-3 mt-5 formCard">
        <div class="card-header">
            <h4 class="card-title"><b><spring:message code="CreateAlert"/></b></h4>
        </div>
        <div class="card-body">
            <form:errors cssClass="formError"/>
            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label path="cargoType" class="form-label"><spring:message code="CreateTripCargoType"/></form:label>
                    <form:errors path="cargoType" cssClass="formError" element="p"/>
                    <form:select class="form-select" path="cargoType">
                        <form:option value="" disabled="true" selected="true"><spring:message code="Select"/></form:option>
                        <c:forEach var="cargoType" items="${cargoTypes}">
                            <spring:message code="${cargoType}" var="cargoTypeMsg"/>
                            <form:option value="${cargoType}">${cargoTypeMsg}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="mb-3 inlineFormInput">
                    <form:label path="origin" class="form-label"><spring:message code="Origin"/></form:label>
                    <form:errors path="origin" cssClass="formError" element="p"/>
                    <form:select class="form-select" path="origin" html:required="true">
                        <form:option value="" disabled="true" selected="true"><spring:message code="Select"/></form:option>
                        <form:options items="${cities}"/>
                    </form:select>
                </div>
            </div>
            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label path="fromDate" class="form-label"><spring:message code="FromDate"/></form:label>
                    <form:errors path="fromDate" cssClass="formError" element="p"/>
                    <form:input type="datetime-local" class="form-control" path="fromDate" placeholder="DD/MM/AAAA"/>
                </div>
                <div class="mb-3 inlineFormInput">
                    <form:label path="toDate" class="form-label"><spring:message code="ToDate"/></form:label>
                    <form:errors path="toDate" cssClass="formError" element="p"/>
                    <form:input type="datetime-local" class="form-control" path="toDate" placeholder="DD/MM/AAAA"/>
                </div>
            </div>
            <div class="inlineFormInputContainer">
                <div class="mb-3 inlineFormInput">
                    <form:label path="maxVolume" class="form-label"><spring:message code="FiltersMaxVolume"/></form:label>
                    <form:errors path="maxVolume" cssClass="formError" element="p"/>
                    <div class="input-group">
                        <form:input type="number" onkeydown="return ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode === 8)" min="0" step="1" class="form-control" path="maxVolume" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">m3</span>
                        </div>
                    </div>
                </div>
                <div class="mb-3 inlineFormInput">
                    <form:label class="form-label" path="maxWeight"><spring:message code="FiltersMaxWeight"/></form:label>
                    <form:errors path="maxWeight" cssClass="formError" element="p"/>
                    <div class="input-group">
                        <form:input type="number" onkeydown="return ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode === 8)" min="0" step="1" class="form-control" path="maxWeight" placeholder="0"/>
                        <div class="input-group-append">
                            <span class="input-group-text inputSpan">kg</span>
                        </div>
                    </div>
                </div>
            </div>

<%--            <div class="inlineFormInputContainer">--%>
<%--                <div class="mb-3 mr-3 inlineFormInput">--%>
<%--                    <form:label path="maxVolume" class="form-label"><spring:message code="AvailableVolume"/></form:label>--%>
<%--                    <form:errors path="maxVolume" cssClass="formError" element="p"/>--%>
<%--                    <div class="input-group">--%>
<%--                        <form:input type="number" onkeydown="return ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode === 8)" min="0" step="1" class="form-control" path="maxVolume" placeholder="0"/>--%>
<%--                        <div class="input-group-append">--%>
<%--                            <span class="input-group-text inputSpan">m3</span>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--                <div class="mb-3 mx-3 inlineFormInput">--%>
<%--                    <form:label path="maxWeight" class="form-label"><spring:message code="AvailableWeight"/></form:label>--%>
<%--                    <form:errors path="maxWeight" cssClass="formError" element="p"/>--%>
<%--                    <div class="input-group">--%>
<%--                        <form:input type="number" onkeydown="return ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode === 8)" min="0" step="1" class="form-control" path="maxWeight" placeholder="0"/>--%>
<%--                        <div class="input-group-append">--%>
<%--                            <span class="input-group-text inputSpan">kg</span>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>

            <input type="submit" value="<spring:message code="CreateAlert"/>" class="btn btn-color mt-3 formButton"/>
        </div>
    </div>
</form:form>
<div style="margin-top: auto">
    <components:waveDivider/>
    <components:footer/>
</div>

</body>
</html>