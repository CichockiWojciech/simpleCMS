<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<form action="register.do" method="POST" class="register">
    <div>
        <input type="text" name="firstName" placeholder="IMIE">
        <input type="text" name="lastName" placeholder="NAZWISKO">
    </div>
    <div>
        <input type="text" name="login" placeholder="LOGIN">
        <input type="email" name="email" placeholder="EMAIL">
    </div>
    <div>
        <input type="password" name="password" placeholder="HASŁO">
        <input type="password" name="repeatPassword" placeholder="POWTÓRZ HASŁO">
    </div>
    <input type="submit" value="REJESTRUJ">
</form>
<p class="info">${info}</p>