<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<form action="login.do" method="POST" class="register">
    <input type="text" name="login" placeholder="LOGIN">
    <input type="password" name="password" placeholder="HASŁO">
    <input type="submit" value="ZALOGUJ">
</form>
<p class="info">${info}</p>