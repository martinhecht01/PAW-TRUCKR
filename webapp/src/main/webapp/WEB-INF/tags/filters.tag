<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/browseTrips" var="getPath"/>


<form:form modelAttribute="filterForm" action="${getPath}" method="get">
    <div class="card">
        <div class="card-header">
            Filters
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="origin">Origin:</label>
                        <input type="text" class="form-control" name="origin" id="origin" placeholder="Enter origin">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="destination">Destination:</label>
                        <input type="text" class="form-control" name="destination" id="destination"
                               placeholder="Enter destination">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="minPrice">Min Price:</label>
                        <input class="form-control" type="number" name="minPrice" id="minPrice">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="maxPrice">Max Price:</label>
                        <input class="form-control" type="number" name="maxPrice" id="maxPrice">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="minAvailableVolume">Min Volume:</label>
                <input type="number" name="minAvailableVolume" class="form-control" id="minAvailableVolume"
                       placeholder="Enter min volume">
            </div>
            <div class="form-group">
                <label for="minAvailableWeight">Min Weight:</label>
                <input type="number" class="form-control" name="minAvailableWeight" id="minAvailableWeight"
                       placeholder="Enter min weight">
            </div>
            <div class="form-group row">
                <div class="col-md-6">
                    <label for="departureDate">Departure date:</label>
                    <input type="date" class="form-control" id="departureDate" name="departureDate" placeholder="DD/MM/AAAA" />
                </div>
                <div class="col-md-6">
                    <label for="arrivalDate">Arrival date:</label>
                    <input type="date" class="form-control" id="arrivalDate" name="arrivalDate" placeholder="DD/MM/AAAA" />
                </div>
            </div>
            <div class="form-group">
                <label for="sortOrder">Sort by:</label>
                <select class="form-control" name="sortOrder" id="sortOrder">
                    <option value="" disabled selected>Select</option>
                    <option value="departureDate ASC">Departure Date (asc)</option>
                    <option value="departureDate DESC">Departure Date (desc)</option>
                    <option value="arrivalDate ASC">Arrival Date (asc)</option>
                    <option value="arrivalDate DESC">Arrival Date (desc)</option>
                    <option value="price ASC">Price (asc)</option>
                    <option value="price DESC">Price (desc)</option>
                </select>
            </div>
            <button type="submit" class="btn btn-color btn-primary mt-3">Apply Filters</button>
        </div>
    </div>
</form:form>