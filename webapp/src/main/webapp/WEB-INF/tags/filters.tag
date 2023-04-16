<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/browseTrips" var="getPath"/>


<form:form modelAttribute="filterForm" action="${getPath}" method="get">
    <div class="card">
        <div class="card-header">
            <h4 class="card-title"><b>Filtros</b></h4>
        </div>
        <div class="card-body">
            <div class="row mb-3">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="origin">Origen:</label>
                        <input type="text" class="form-control" name="origin" id="origin" <c:if test="${origin != null && origin != ''}">value="${origin}"</c:if> placeholder="Ingrese el origen:"/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="destination">Destino:</label>
                        <input type="text" class="form-control" name="destination" id="destination"
                               <c:if test="${destination != null && destination != ''}">value="${destination}"</c:if> placeholder="Ingrese el destino:"/>
                    </div>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="minPrice">Precio Min.:</label>
                        <input class="form-control" type="number" name="minPrice" id="minPrice" <c:if test="${minPrice != null && minPrice != ''}">value="${minPrice}"</c:if> placeholder="-"/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="maxPrice">Precio Max.:</label>
                        <input class="form-control" type="number" name="maxPrice" id="maxPrice" <c:if test="${maxPrice != null && maxPrice != ''}">value="${maxPrice}"</c:if> placeholder="-">
                    </div>
                </div>
            </div>
            <div class="form-group mb-3">
                <label for="minAvailableVolume">Volumen Min.:</label>
                <input type="number" name="minAvailableVolume" class="form-control" id="minAvailableVolume"
                       <c:if test="${minAvailableVolume != null || minAvailableVolume != ''}">value="${minAvailableVolume}"</c:if> placeholder="-">
            </div>
            <div class="form-group mb-3">
                <label for="minAvailableWeight">Peso Min.:</label>
                <input type="number" class="form-control" name="minAvailableWeight" id="minAvailableWeight"
                       <c:if test="${minAvailableWeight != null || minAvailableWeight != ''}">value="${minAvailableWeight}"</c:if> placeholder="-">
            </div>
            <div class="form-group row mb-3">
                <div class="col-md-6">
                    <label for="departureDate">Fecha de Salida:</label>
                    <input type="date" class="form-control" id="departureDate" name="departureDate" <c:if test="${departureDate != null || departureDate != ''}">value="${departureDate}"</c:if>/>
                </div>
                <div class="col-md-6">
                    <label for="arrivalDate">Fecha de Llegada</label>
                    <input type="date" class="form-control" id="arrivalDate" name="arrivalDate" <c:if test="${arrivalDate != null || arrivalDate != ''}">value="${arrivalDate}"</c:if> />
                </div>
            </div>
            <div class="form-group mb-3">
                <label for="sortOrder">Ordenar Por:</label>
                <select class="form-control" name="sortOrder" id="sortOrder">
                    <option value="" disabled <c:if test="${sortOrder == null || sortOrder == ''}">selected</c:if>>Seleccionar</option>
                    <option value="departureDate ASC" <c:if test="${sortOrder == 'departureDate ASC'}">selected</c:if>>Fecha de Salida (asc)</option>
                    <option value="departureDate DESC" <c:if test="${sortOrder == 'departureDate DESC'}">selected</c:if>>Fecha de Salida (desc)</option>
                    <option value="arrivalDate ASC" <c:if test="${sortOrder == 'arrivalDate ASC'}">selected</c:if>>Fecha de Llegada (asc)</option>
                    <option value="arrivalDate DESC" <c:if test="${sortOrder == 'arrivalDate DESC'}">selected</c:if>>Fecha de Llegada (desc)</option>
                    <option value="price ASC" <c:if test="${sortOrder == 'price ASC'}">selected</c:if>>Precio (asc)</option>
                    <option value="price DESC" <c:if test="${sortOrder == 'price DESC'}">selected</c:if>>Precio (desc)</option>
                </select>
            </div>
            <button type="submit" class="btn btn-color">Aplicar Filtros</button>
        </div>
    </div>
</form:form>