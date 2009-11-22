<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.jdo.Query" %>
<%@ page import="minesweeper.server.Stat" %>
<%@ page import="minesweeper.server.PMF" %>
<%@ page import="minesweeper.client.Level" %>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="Minesweeper.css">
    <title>Yet another minesweeper</title>
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
		<p><a href="/">Play</a></p>
		
		<ul>
			<li><a href="?level=<%= Level.EASY %>">Easy</a></li>
			<li><a href="?level=<%= Level.MEDIUM %>">Medium</a></li>
			<li><a href="?level=<%= Level.HARD %>">Hard</a></li>
		</ul>
		
	    <table class="stats">
	    	<tr>
	    		<th>place</th>
	    		<th>user</th>
	    		<th>time</th>
	    	</tr>
<%
	String level = request.getParameter("level");
	if(level == null) {
		level = "1";
	}

	PersistenceManager pm = PMF.get().getPersistenceManager();
	Query query = pm.newQuery(Stat.class, "level == levelParam");
	query.setOrdering("time ASC");
	query.setRange(0, 100);
	query.declareParameters("int levelParam");
	List<Stat> stats = (List<Stat>) query.execute(Integer.parseInt(level));

	if (!stats.isEmpty()) {
		NumberFormat fmt = new DecimalFormat("00");
		int i = 0;
  		for (Stat stat : stats) {
  			++i;
  			int secs = stat.getTime() % 60;
  			int mins = stat.getTime() / 60;
%>
			<tr>
				<td><%= i %></td>
		    	<td><%= stat.getAuthor().getNickname() %></td>
		    	<td><%= fmt.format(mins) + ":" + fmt.format(secs) %></td>
	    	</tr>
<%
		}
	}
%>
   		</table>
	</div>
  </body>
</html>
