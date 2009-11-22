<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="Minesweeper.css">
    <title>Yet another minesweeper</title>
    <script type="text/javascript" language="javascript" src="minesweeper/minesweeper.nocache.js"></script>
  </head>
  <body>
  	<div class="content">
    	<h1>Yet another minesweeper</h1>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>Hello, <%= user.getNickname() %>! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p>Hello, %username%!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to collect your game statistics.</p>
<%
    }
%>
		<p><a href="/Stats.jsp">Stats</a></p>
	    <div id="root"></div>
    </div>
    <script type="text/javascript">
<% 
	if (user != null) { 
%>
		var is_logged_in = true;
    	var user_email = '<%= user.getEmail() %>';
<%
	} else {
%>
		var is_logged_in = false;
		var login_url = '<%= userService.createLoginURL(request.getRequestURI()) %>';
<%
    }
%>
    </script>
  </body>
</html>
