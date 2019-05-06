<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<script src="tinymce/tinymce.min.js"></script>
<script>tinymce.init({selector:'textarea'});</script>
<form class="editor" action="account" method="post">
    <div>
        <h3>${title}</h3>
    </div>
    <div>
        <textarea name="text" placeholder="CONTENT">${text}</textarea>
    </div>
    <div>
        <input type="submit" name="action" value="EDYTUJ TRESC">
    </div>
</form>
<p class="info">${info}</p>