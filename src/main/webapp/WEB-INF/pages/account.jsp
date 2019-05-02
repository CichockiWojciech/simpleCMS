<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/style.css">
    <title>Document</title>
</head>
<jsp:include page="/WEB-INF/scriptlets/color.jsp" />
<body>
<jsp:include page="/WEB-INF/elements/header.jsp" />

<section class="app-container">
    <!--NAV-->
    <div class="container-box">
        <jsp:include page="/WEB-INF/elements/content-navigation.jsp" />
    </div>

    <!--CONTENT-->
    <div class="content" style="border-color: ${contentColor} !important;">
        <jsp:include page="/WEB-INF/elements/personal-data.jsp" />
    </div>

    <!--ASIDE-->
    <div class="container-box">
        <jsp:include page="/WEB-INF/elements/account-navigation.jsp" />
    </div>
</section>

<jsp:include page="/WEB-INF/elements/footer.jsp" />
</body>
</html>