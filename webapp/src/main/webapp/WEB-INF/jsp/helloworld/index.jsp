<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<link>
    <link href="/css/main.css" rel="stylesheet"/>
</head>
<body>
<h2>Hello <c:out value="${user.email}" escapeXml="true"/>!</h2>
</body>
</html>