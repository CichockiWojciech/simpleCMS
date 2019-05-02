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
<!--HEADER-->
<jsp:include page="/WEB-INF/elements/header.jsp" />1

<section class="app-container">
    <!--NAV-->
    <div class="container-box">
    </div>

    <!--CONTENT-->
    <div class="content">
        <jsp:include page="/WEB-INF/elements/login-form.jsp" />
    </div>

    <!--ASIDE-->
    <div class="container-box">
        <jsp:include page="/WEB-INF/elements/main-navigation.jsp" />
    </div>
</section>

<jsp:include page="/WEB-INF/elements/footer.jsp" />
</body>
</html>