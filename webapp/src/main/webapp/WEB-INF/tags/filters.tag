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
                <select class="form-control" name="origin" id="origin">
                    <option>New York</option>
                    <option>Los Angeles</option>
                    <option>Chicago</option>
                </select>
            </div>
            <div class="form-group">
                <label for="destination">Destination:</label>
                <select class="form-control" name="destination" id="destination">
                    <option>London</option>
                    <option>Paris</option>
                    <option>Tokyo</option>
                </select>
            </div>
            <div class="form-group">
                <label for="minAvailableVolume">Min Volume:</label>
                <input type="number" name="minAvailableVolume" class="form-control" id="minAvailableVolume" placeholder="Enter min volume">
            </div>
            <div class="form-group">
                <label for="minAvailableWeight">Min Weight:</label>
                <input type="number" class="form-control" name="minAvailableWeight" id="minAvailableWeight" placeholder="Enter min weight">
            </div>
            <button type="submit" class="btn btn-primary">Apply Filters</button>
        </div>
    </div>
</form:form>