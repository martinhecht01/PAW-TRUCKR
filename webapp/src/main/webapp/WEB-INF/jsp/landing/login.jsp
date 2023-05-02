<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<link>
<link href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"/>" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous"/>
<script src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"/>" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>
<link href="<c:url value="/css/userControl.css"/>" rel="stylesheet"/>


<head>
  <title>Truckr</title>
  <link rel="icon" type="image/x-icon" href="https://i.ibb.co/JmB4xhT/Truckr-Logo.png">
</head>
<body class="bodyContent">
<c:url var="loginUrl" value="/login"/>
<components:navBar/>
<div class="w-75 m-auto">
    <main class="form-signin m-auto">
        <div class="card py-2">
            <div class="card-body">
                <form:form action="${loginUrl}" method="post">
                    <%--    <img class="mb-4" src="../assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">--%>
                    <h1 class="h3 my-3 text-center">Log in</h1>
                    <p class="text-center">New to truckr? <a href="<c:url value="/register"/>">Sign up now</a></p>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="cuit" name="cuit" placeholder="00-00000000-0">
                        <label for="cuit">Cuit</label>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" name="password" id="password" placeholder="Password">
                        <label for="password">Password</label>
                    </div>
                    <p><a href="<c:url value="/resetPasswordRequest"/>">Forgot Password?</a></p>


                    <div class="checkbox mb-3">
                        <label>
                            <input type="checkbox" name="rememberme" value="remember-me"> Remember me
                        </label>
                    </div>
                    <input class="w-100 btn btn-lg btn-color" type="submit" value="Log in"/>
                </form:form>
            </div>
        </div>
    </main>
</div>
</body>
<components:waveDivider/>
<components:footer/>
</html>