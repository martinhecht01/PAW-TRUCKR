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

<c:url value="/profile/edit" var="postPath"/>
<head>
    <title>Truckr</title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png"></head>
<body class="bodyContent">
<components:navBar/>
<div class="w-75 m-auto pt-5">
    <form:form modelAttribute="editUserForm" action="${postPath}" method="post" enctype="multipart/form-data">
    <main class="form-signin m-auto">
        <div class="card">
            <div class="card-header">
                <h4 class="card-title"><b><spring:message code="Profile"/></b></h4>
            </div>
            <div class="card-body">
                <div>
                    <div class="text-center">
                        <img id="imagePreview" src="<c:url value="/user/${currentUser.userId}/profilePicture"/>" class="profileImage" alt="Profile Picture"/>
                    </div>
                    <div>
                        <form:label path="profileImage" class="form-label">Upload a profile image:</form:label>
                        <form:errors cssClass="formError" path="profileImage" element="p"/>
                        <form:input  path="profileImage" class= "form-control-file" type="file" accept="image/png, image/jpeg" onchange="previewImage()" />
                    </div>
                </div>
                <div>
                    <h5><b>Name:</b></h5>
                    <form:label path="name" class="form-label"><spring:message code="Name"/></form:label>
                    <form:errors cssClass="formError" path="name" element="p"/>
                    <form:input type="text" class="form-control" path="name" placeholder="${currentUser.getName()}"/>
                </div>
                <div>
                    <h5><b>Cuit:</b></h5>
                    <p><c:out value="${currentUser.getCuit()}"/></p>
                </div>
                <div>
                    <h5><b>Email:</b></h5>
                    <p><c:out value="${currentUser.getEmail()}"/></p>
                </div>
                <div>
                    <h5><b>Role:</b></h5>
                    <p><c:out value="${currentRole}"/></p>
                </div>
                <div>
                    <button class="w-100 btn btn-lg btn-color" type="submit">Submit</button>
                </div>
            </div>

        </div>
    </main>
    </form:form>
</div>
<script>
function previewImage() {
let output = document.getElementById('imagePreview');
output.src = URL.createObjectURL(event.target.files[0]);
output.onload = function() {
URL.revokeObjectURL(output.src)
}
}
</script>
<components:waveDivider/>
<components:footer/>
</body>
</html>