<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<form class="editor" action="account" method="post">
    <div>
        <input type="text" name="title" PLACEHOLDER="TITLE">
    </div>
    <div>
        <textarea name="content" placeholder="CONTENT"></textarea>
    </div>
    <div>
        <input type="submit" name="action" value="wstaw">
    </div>
</form>
<p class="info">${info}</p>