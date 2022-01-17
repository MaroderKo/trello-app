<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Boards</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Boards</h2>
    <hr/>
    <a href="boards?action=create">Add Board</a>
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
        <c:forEach items="${boards}" var="board">
            <jsp:useBean id="board" type="spd.trello.domain.Board"/>
            <tr data-mealExcess="${board}">
                <td>${board.name}</td>
                <td>${board.createdDate.withNano(0).toString().replace("T"," ") }</td>
                <td>${board.updatedDate.withNano(0).toString().replace("T"," ")}</td>
                <td>${board.description}</td>
                <td><a href="boards?action=open&id=${board.id}">Open</a></td>
                <td><a href="boards?action=update&id=${board.id}">Edit</a></td>
                <td><a href="boards?action=delete&id=${board.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>