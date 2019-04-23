<div class="flex-wrapper">
    <div id="avatar">
        <img src="avatar.png" alt="avatar" width="120px" height="150px">
    </div>

    <div id="personal-data">
        <p class="personal-key">IMIE:</p>
        <p class="personal-value"><%=request.getAttribute("firstName")%></p>

        <p class="personal-key">NAZWISKO:</p>
        <p class="personal-value"><%=request.getAttribute("lastName")%></p>

        <p class="personal-key">EMAIL:</p>
        <p class="personal-value"><%=request.getAttribute("email")%></p>

        <form action="" method="post">
            <input type="submit" value="EDYTUJ" />
        </form>
    </div>
</div>