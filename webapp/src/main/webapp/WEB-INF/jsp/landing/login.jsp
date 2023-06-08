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
   <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png">
</head>
<body class="bodyContent">
<c:url var="loginUrl" value="/login"/>
<components:navBar/>
<div class="w-100 h-50 d-flex justify-content-center align-items-center m-auto pt-2">
    <main class="w-25 mt-5 m-auto">
        <div class="card py-2">
            <div class="card-body">
                <form:form action="${loginUrl}" method="post">
                    <h1 class="h3 my-3 text-center"><spring:message code="Login"/></h1>
                    <p class="text-center"><spring:message code="NewToTruckr"/> <a href="<c:url value="/register"/>"><spring:message code="SignUpNow"/></a></p>
                    <div class="mb-3">
                        <c:if test="${error}">
                            <c:if test="${errorCode=='UserNotVerified'}">
                                <p class="formError mb-3"><spring:message code="${errorCode}"/> <a href="<c:url value="/verifyAccount"/>"><spring:message code="VerifyAccount"/></a></p>
                            </c:if>
                            <c:if test="${errorCode!='UserNotVerified'}">
                                <p class="formError mb-3"><spring:message code="${errorCode}"/></p>
                            </c:if>
                        </c:if>
                        <label class="form-label" for="cuit">Cuit</label>
                        <input type="text" class="form-control" id="cuit" maxlength="13" name="cuit" placeholder="00-00000000-0"/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label" for="password"><spring:message code="Password"/></label>
                        <input class="form-control" type="password"  name="password" id="password" placeholder="<spring:message code="Password"/>">
                    </div>
                    <p><a href="<c:url value="/resetPasswordRequest"/>"><spring:message code="ForgotPassword"/></a></p>


                    <div class="checkbox mb-3">
                        <label><input type="checkbox" name="rememberme" value="remember-me"> <spring:message code="RememberMe"/></label>
                    </div>
                    <input class="w-100 btn btn-lg btn-color" type="submit" value="<spring:message code="Login"/>"/>
                </form:form>
            </div>
        </div>
    </main>
</div>
</body>
<div class="mt-auto">
    <components:waveDivider/>
    <components:footer/>
</div>
</html>

<script>
    const tele = document.querySelector('#cuit');
    tele.addEventListener('keyup', function(e){
        if (e.key !== 'Backspace' && (tele.value.length === 2 || tele.value.length === 11)){
            tele.value += '-';
        }
    });
</script>