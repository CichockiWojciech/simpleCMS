<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Iterator" %>
<div id="menu">
    <form action="account" method="get" class="content-nav">
        <input type="submit" name="action" value="DODAJ STRONE" />
        <input type="submit" name="action" value="USTAW KOLOR" />
    </form>

    <ul id="page-list">
        <%
            Iterator<String> it = (Iterator<String>)request.getAttribute("pageList");
            while (it.hasNext()){
                String title = it.next();
                String input = "<input class=\"link\" type=\"submit\" value=\"" + title + "\" name=\"submit\" " +
                        "style=\"background-color: " + request.getAttribute("linkColor") + "!important;\"/>";
                out.println("<form action=\"content\" method=\"get\">");
                out.println("<li>" + input + "</li>");
                out.println("</form>");
            }
        %>
    </ul>
</div>