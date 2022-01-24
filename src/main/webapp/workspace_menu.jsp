
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Workspaces</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Workspace menu</h2>
    <hr/>
    <jsp:useBean id="workspace" type="spd.trello.domain.Workspace" scope="request"/>
    <form action="workspaces" method="post">
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="id" value="${workspace.id}">
        <label>Name: </label><input type="text" value="${workspace.name}" name="name" maxlength="20" required/><br>
        <label>Description: </label><input type="text" value="${workspace.description}" name="description" maxlength="50"/><br>
        <label>Visibility: </label>
        <select name="visibility">
        <option value="PRIVATE" ${workspace.visibility.name().equals("PRIVATE") ? "selected" : ""}>Private</option>
        <option value="PUBLIC" ${workspace.visibility.name().equals("PUBLIC") ? "selected" : ""}>Public</option>
        </select><br>

        <input type="submit" name="Submit">
    </form>
</section>
</body>
</html>