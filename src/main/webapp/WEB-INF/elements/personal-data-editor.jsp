<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<form action="account" method="POST" class="register">
    <div>
        <input type="text" name="firstName" placeholder="IMIE">
        <input type="text" name="lastName" placeholder="NAZWISKO">
    </div>
    <div>
        <input type="text" name="login" placeholder="LOGIN">
        <input type="email" name="email" placeholder="EMAIL">
    </div>
    <input type="submit" name="action" value="edytuj">
</form>
<p class="info">${info}</p>
