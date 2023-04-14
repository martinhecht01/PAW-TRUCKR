<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/browseTrips" var="getPath"/>


<form:form modelAttribute="filterForm"  action="${getPath}" method="get">
    <div class="card">
        <div class="card-header">
            Filters
        </div>
        <div class="card-body">
            <div class="form-group">
                <label for="origin">Origin:</label>
                <input class="form-control" name="origin" id="origin">
            </div>
            <div class="form-group">
                <label for="destination">Destination:</label>
                <input class="form-control" name="destination" id="destination">
            </div>
            <div class="form-group">
                <label for="minPrice">Min Price:</label>
                <input class="form-control" type="number" name="minPrice" id="minPrice">
            </div>
            <div class="form-group">
                <label for="maxPrice">Max Price:</label>
                <input class="form-control" type="number" name="maxPrice" id="maxPrice">
            </div>
            <div class="form-group">
                <label for="minAvailableVolume">Min Volume:</label>
                <input type="number" name="minAvailableVolume" class="form-control" id="minAvailableVolume" placeholder="Enter min volume">
            </div>
            <div class="form-group">
                <label for="minAvailableWeight">Min Weight:</label>
                <input type="number" class="form-control" name="minAvailableWeight" id="minAvailableWeight" placeholder="Enter min weight">
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
            <button type="submit" class="btn btn-primary">Apply Filters</button>
        </div>
    </div>
</form:form>