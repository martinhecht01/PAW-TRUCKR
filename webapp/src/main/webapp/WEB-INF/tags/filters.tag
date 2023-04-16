<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/browseTrips" var="getPath"/>


<form:form modelAttribute="filterForm" action="${getPath}" method="get">
    <div class="card">
        <div class="card-header">
            Filtros
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="origin">Origen:</label>
                        <input type="text" class="form-control" name="origin" id="origin" placeholder="Ingrese el origen">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="destination">Destino:</label>
                        <input type="text" class="form-control" name="destination" id="destination"
                               placeholder="Ingrese el destino">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="minPrice">Precio Min.:</label>
                        <input class="form-control" type="number" name="minPrice" id="minPrice">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="maxPrice">Precio Max.:</label>
                        <input class="form-control" type="number" name="maxPrice" id="maxPrice">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="minAvailableVolume">Volumen Min.:</label>
                <input type="number" name="minAvailableVolume" class="form-control" id="minAvailableVolume"
                       placeholder="-">
            </div>
            <div class="form-group">
                <label for="minAvailableWeight">Peso Min.:</label>
                <input type="number" class="form-control" name="minAvailableWeight" id="minAvailableWeight"
                       placeholder="-">
            </div>
            <div class="form-group row">
                <div class="col-md-6">
                    <label for="departureDate">Fecha de Salida:</label>
                    <input type="date" class="form-control" id="departureDate" name="departureDate" placeholder="DD/MM/AAAA" />
                </div>
                <div class="col-md-6">
                    <label for="arrivalDate">Fecha de Llegada</label>
                    <input type="date" class="form-control" id="arrivalDate" name="arrivalDate" placeholder="DD/MM/AAAA" />
                </div>
            </div>
            <div class="form-group">
                <label for="sortOrder">Ordenar Por:</label>
                <select class="form-control" name="sortOrder" id="sortOrder">
                    <option value="" disabled selected>Seleccionar</option>
                    <option value="departureDate ASC">Fecha de Salida (asc)</option>
                    <option value="departureDate DESC">Fecha de Salida (desc)</option>
                    <option value="arrivalDate ASC">Fecha de Llegada (asc)</option>
                    <option value="arrivalDate DESC">Fecha de Llegada (desc)</option>
                    <option value="price ASC">Precio (asc)</option>
                    <option value="price DESC">Precio (desc)</option>
                </select>
            </div>
            <button type="submit" class="btn btn-color mt-3">Aplicar Filtros</button>
        </div>
    </div>
</form:form>