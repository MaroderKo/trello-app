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
    <h2>Board menu</h2>
    <hr/>
    <br><br>
    <jsp:useBean id="board" scope="request" type="spd.trello.domain.Board"/>
    <form action="boards" method="post">
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="id" value="${board.id}">
        <label>Name: </label><input type="text" value="${board.name}" name="name" maxlength="20" required/>
        <label>Description: </label><input type="text"value="${board.description}" name="description" maxlength="50"/>
        <label>Archived: </label><input type="checkbox" ${board.archived?"checked":""} name="archived"/>
        <label>Visibility: </label>
        <select name="visibility">
            <option value="PRIVATE" ${board.visibility.name().equals("PRIVATE") ? "selected" : ""}>Private</option>
            <option value="PUBLIC" ${board.visibility.name().equals("PUBLIC") ? "selected" : ""}>Public</option>
            <option value="WORKSPACE" ${board.visibility.name().equals("WORKSPACE") ? "selected" : ""}>Workspace</option>
        </select>
        <input type="submit" name="Submit">
    </form>
</section>
</body>
</html>