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
<div class="w-75 m-auto">
    <main class="form-signin m-auto">
        <div class="card py-2">
            <div class="card-body">
                <form:form modelAttribute="userForm" action="${postPath}" method="post">
                    <h1 class="h3 mb-3 text-center">Register</h1>
                    <div class="form-floating">
                        <form:errors cssClass="form-error" path="name"/>
                        <form:input type="text" class="form-control" path="name" placeholder="Juan Perez"/>
                        <form:label path="name">Name</form:label>
                    </div>
                    <div class="form-floating">
                        <form:errors cssClass="form-error" path="email"/>
                        <form:input type="email" class="form-control" path="email" placeholder="name@example.com"/>
                        <form:label path="email">Email address</form:label>

                    </div>
                    <div class="form-floating">
                        <form:errors cssClass="form-error" path="cuit"/>
                        <form:input type="text" class="form-control" path="cuit" placeholder="CUIT"/>
                        <form:label path="cuit">CUIT</form:label>
                    </div>

                    <div class="form-floating">
                        <div>
                            <label for="clientRadio">I'm a provider! I will fill the truck with my cargo</label>
                            <form:radiobutton path="role" id="clientRadio" name="role" value="PROVIDER" />
                        </div>
                        <div>
                            <label for="truckerRadio">I'm a trucker! I will take your cargo where you need</label>
                            <form:radiobutton path="role" id="truckerRadio" name="role" value="TRUCKER" />
                        </div>
                    </div>

                    <div class="form-floating">
                        <form:errors cssClass="form-error" path="password"/>
                        <form:input type="password" class="form-control" path="password" placeholder="Password"/>
                        <form:label path="password">Password</form:label>
                    </div>
                    <div class="form-floating">
                        <form:errors cssClass="form-error" path="repeatPassword"/>
                        <form:input type="password" class="form-control" path="repeatPassword" placeholder="Confirm Password"/>
                        <form:label path="repeatPassword">Confirm Password</form:label>
                    </div>
                    <button class="w-100 btn btn-lg btn-color" type="submit">Register</button>
                </form:form>
            </div>
        </div>
    </main>
</div>
</body>


</html>