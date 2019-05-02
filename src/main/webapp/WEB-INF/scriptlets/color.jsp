<%@ page import="pl.model.domain.User" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    Optional<User> user = Optional.ofNullable((User) session.getAttribute("user"));
    final String headerColor, asideColor, contentColor, linkColor, footerColor;

    if(user.isPresent()){
        headerColor =  user.get().getHeaderColor();
        asideColor = user.get().getAsideColor();
        contentColor = user.get().getContentColor();
        linkColor = user.get().getLinkColor();
        footerColor = user.get().getFooterColor();
    }else{
        headerColor =  getServletConfig().getInitParameter("defaultColor");
        asideColor = getServletConfig().getInitParameter("defaultColor");
        contentColor = getServletConfig().getInitParameter("defaultColor");
        linkColor = getServletConfig().getInitParameter("defaultColor");
        footerColor = getServletConfig().getInitParameter("defaultColor");
    }
    request.setAttribute("headerColor", headerColor);
    request.setAttribute("asideColor", asideColor);
    request.setAttribute("contentColor", contentColor);
    request.setAttribute("linkColor", linkColor);
    request.setAttribute("footerColor", footerColor);
%>
