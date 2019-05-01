<%@ page import="pl.model.domain.Avatar" %>
<%@ page import="pl.model.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<div class="flex-wrapper">
    <div class="avatar">
        <form action="account" method="POST" enctype="multipart/form-data">
            <div class="image-upload">
                <label for="file-input">
                    <%
                        Avatar avatar = ((User) session.getAttribute("user")).getAvatar();
                    %>
                    <%=
                        avatar != null ?
                        "<img src=\"data:image/png;base64,"+ avatar +"\"/>" :
                        "<img src=\"img/avatar.png\"/>"
                    %>
                </label>
                <input id="file-input" type="file" name="avatar" accept="image/png"/>

            </div>
            <input type="submit" name="action" value="DODAJ">
        </form>
        <p class="info">${info}</p>
    </div>

    <div id="personal-data">
        <p class="personal-key">IMIE:</p>
        <p class="personal-value">${firstName}</p>

        <p class="personal-key">NAZWISKO:</p>
        <p class="personal-value">${lastName}</p>

        <p class="personal-key">EMAIL:</p>
        <p class="personal-value">${email}</p>

        <form action="account" method="GET">
            <input type="submit" name="action" value="EDYTUJ" />
        </form>
    </div>
</div>