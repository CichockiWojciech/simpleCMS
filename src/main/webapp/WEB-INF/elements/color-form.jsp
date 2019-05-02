<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<form action="account" method="POST" class="color-form">
    <input type="text" name="headerColor" placeholder="KOLOR NAGŁÓWKA">
    <input type="text" name="asideColor" placeholder="KOLOR PANELU BOCZNEGO">
    <input type="text" name="contentColor" placeholder="KOLOR ZAWARTOŚCI">
    <input type="text" name="linkColor" placeholder="KOLOR NAWIGACJI">
    <input type="text" name="footerColor" placeholder="KOLOR STOPKI">
    <input type="submit" name="action" value="ZMIEN KOLOR">
</form>
<p class="info">${info}</p>