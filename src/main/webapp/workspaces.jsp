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
    <h2>Workspaces</h2>
    <hr/>
    <a href="workspaces?action=create">Add Workspace</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Create date</th>
            <th>Update date</th>
            <th>Description</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${workspaces}" var="workspace">
            <jsp:useBean id="workspace" type="spd.trello.domain.Workspace"/>
            <tr data-mealExcess="${workspace}">
                <td>${workspace.name}</td>
                <td>${workspace.createdDate.withNano(0).toString().replace("T"," ")}</td>
                <td>${workspace.updatedDate.withNano(0).toString().replace("T"," ")}</td>
                <td style="overflow:hidden">${workspace.description}</td>
                <td><a href="boards?workspace_id=${workspace.id}">Open</a></td>
                <td><a href="workspaces?action=update&id=${workspace.id}">Edit</a></td>
                <td><a href="workspaces?action=delete&id=${workspace.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>