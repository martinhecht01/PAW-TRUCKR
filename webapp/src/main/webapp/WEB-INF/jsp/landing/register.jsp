<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="Register"/></title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/JmB4xhT/Truckr-Logo.png">
</head>
<body>
<h2>Register</h2>
<%--Creo variable postPath--%>
<c:url value="/createUser" var="postPath"/>

<%--Formulario--%>
<form:form modelAttribute="registerForm" action="${postPath}" method="post">
    <div>
        <form:label path="username">Email: </form:label>
        <form:input type="text" path="email"/>
        <form:errors path="email" cssClass="formError" element="p"/>
    </div>
    <div>
        <form:label path="username">Name: </form:label>
        <form:input type="text" path="username"/>
        <form:errors path="username" cssClass="formError" element="p"/>
    </div>
    <div>
        <form:label path="id">Cuit: </form:label>
        <form:input type="text" path="id"/>
        <form:errors path="id" cssClass="formError" element="p"/>
    </div>
    <div>
        <form:label path="password">Password: </form:label>
        <form:input type="password" path="password" />
        <form:errors path="password" cssClass="formError" element="p"/>
    </div>
    <div>
        <form:label path="repeatPassword">Repeat password: </form:label>
        <form:input type="password" path="repeatPassword"/>
        <form:errors path="repeatPassword" cssClass="formError" element="p"/>
    </div>
    <div>
        <input type="submit" value="Register!"/>
    </div>
</form:form>

</body>
</html>