<p>zalogowany</p>
<p>Nick: <%=request.getAttribute("firstName")%></p>

<form action="account" method="get">
    <input type="submit" name="action" value="PROFIL" />
</form>
<form action="account" method="get">
    <input type="submit" name="action" value="WYLOGUJ" />
</form>