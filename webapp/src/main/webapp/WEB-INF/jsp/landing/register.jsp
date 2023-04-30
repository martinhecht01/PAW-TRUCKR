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
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/JmB4xhT/Truckr-Logo.png">
</head>

<body class="bodyContent">
<c:url value="/register" var="postPath"/>
<components:navBar/>

<form:form modelAttribute="userForm" action="${postPath}" method="post">

<div class="card w-75 mb-3 mt-5 formCard">
    <div class="card-header">
        <h4 class="card-title"><b>Register</b></h4>
    </div>
    <div class="card-body">
                    <div>
                        <form:label path="cuit" class="form-label">CUIT</form:label>
                        <form:errors cssClass="formError" path="cuit" element="p"/>
                        <form:input type="text" class="form-control" path="cuit" placeholder="CUIT"/>
                    </div>
                    <div>
                        <form:label path="name" class="form-label">Name</form:label>
                        <form:errors cssClass="formError" path="name" element="p"/>
                        <form:input type="text" class="form-control" path="name" placeholder="Juan Perez"/>
                    </div>
                    <div>
                        <form:label path="email" class="form-label">Email address</form:label>
                        <form:errors cssClass="formError" path="email" element="p"/>
                        <form:input type="email" class="form-control" path="email" placeholder="name@example.com"/>
                    </div>

                    <div>
                        <form:label path="role" class="form-label">Role</form:label>
                        <form:errors path="role" class="formError" element="p"/>
                        <div>
                            <form:label path="" for="clientRadio">I'm a provider! I will fill the truck with my cargo</form:label>
                            <form:radiobutton path="role" id="clientRadio" name="role" value="PROVIDER" />
                        </div>
                        <div>
                            <form:label path="" for="truckerRadio">I'm a trucker! I will take your cargo where you need</form:label>
                            <form:radiobutton path="role" id="truckerRadio" name="role" value="TRUCKER" />
                        </div>
                    </div>

                    <div>
                        <form:label path="password" class="form-label">Password</form:label>
                        <form:errors cssClass="formError" path="password" element="p"/>
                        <form:input type="password" class="form-control" path="password" placeholder="Password"/>
                    </div>
                    <div>
                        <form:label path="repeatPassword" class="form-label">Confirm Password</form:label>
                        <form:errors cssClass="formError" path="repeatPassword" element="p"/>
                        <form:input type="password" class="form-control" path="repeatPassword" placeholder="Confirm Password"/>
                        <form:errors cssClass="formError"/>
                    </div>
                    <button class="w-100 btn btn-lg btn-color" type="submit">Register</button>
                </form:form>
    </div>
</div>

</body>


</html>