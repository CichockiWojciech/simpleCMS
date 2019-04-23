<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/style.css">
    <title>Document</title>
</head>
<body>
<header class="app-header">
    <h1>nagłówek</h1>
</header>

<section class="app-container">
    <!--NAV-->
    <div class="container-box">
        <jsp:include page="/WEB-INF/elements/nav.jsp" />
    </div>

    <!--CONTENT-->
    <div class="content">
        <jsp:include page="/WEB-INF/elements/personal-data.jsp" />
    </div>

    <!--ASIDE-->
    <div class="container-box account">
        <jsp:include page="/WEB-INF/elements/aside.jsp" />
    </div>
</section>

<footer class="app-footer">
    <span>&copy; Copyright 2019 </span>
</footer>
</body>
</html>