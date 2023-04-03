<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<body>
<h2>REgister</h2>
<c:url var="registerUrl" value="/register" />
<form action="${registerUrl}" method="post">
    <div>
        <label>email
            <input> type="text" name="email</label>
        </label>
    </div>
</form>
</body>
</html>