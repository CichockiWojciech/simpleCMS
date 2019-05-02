<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<div class="account" style="border-color: ${asideColor} !important;">
    <p>zalogowany</p>
    <p>Nick: ${firstName}</p>

    <form class="aside-option" action="account" method="get">
        <input type="submit" name="action" value="PROFIL" />
    </form>
    <form class="aside-option" action="account" method="get">
        <input type="submit" name="action" value="WYLOGUJ" />
    </form>
</div>
