<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>

    <title>[Детали]</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
            left: 50%;
            margin: auto;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }

        .h1 {
            font-family: Source Sans Pro, sans-serif;
            font-size: 150%;
        }
        .p {
            font-style: italic;
            text-decoration: underline;
        }

        .center {
            left: 50%;
            margin: auto;

        }
    </style>
</head>
<body>
<a href="../../index.jsp"><--- На главую</a>

<h1 class="h1" align="center">Список деталей</h1>

<%--Search--%>

<c:url value="/search" var="searchAction"/>

<form:form action="${searchAction}" method="POST">
    <table class="center">
        <tr>
            <td width="300">Введите наименование детали для поиска:</td>
            <td><input size="45" type="text" name="title"/></td>
            <td><input type="submit" value="Поиск"/></td>
        </tr>
    </table>
</form:form>

<%--Filters--%>

<c:url value="/parts" var="all"><c:param name="selectorList" value="${1}"/></c:url>
<c:url value="/parts" var="necessary"><c:param name="selectorList" value="${0}"/></c:url>
<c:url value="/parts" var="optional"><c:param name="selectorList" value="${-1}"/></c:url>
<table class="center">
    <tr>
        <td><form:form action="${all}"><input type="submit"       value="Все детали  "/></form:form></td>
        <td><form:form action="${necessary}"><input type="submit" value="Необходимые "/></form:form></td>
        <td><form:form action="${optional}"><input type="submit"  value="Опциональные"/></form:form></td>
    </tr>
</table>

<%--Table--%>

<c:if test="${!empty listParts}">
    <table class="tg">
        <tr>
            <th width="80">ID</th>
            <th width="120">Наименование</th>
            <th width="120">Необходимость</th>
            <th width="120">Количество</th>
            <th width="60">Изменить</th>
            <th width="60">Удалить</th>
        </tr>
        <c:forEach items="${listParts}" var="part">
            <tr>
                <td>${part.id}</td>
                <td>${part.title}</td>

                <c:choose>
                    <c:when test="${part.necessity}">
                        <td>да</td>
                    </c:when>
                    <c:otherwise>
                        <td>нет</td>
                    </c:otherwise>
                </c:choose>

                <td>${part.quantity}</td>

                <c:url value="/edit" var="editUrl">
                    <c:param name="id" value="${part.id}"/>
                    <c:param name="page" value="${page}"/>
                </c:url>

                <td><a href='<c:out value="${editUrl}"/>'>Изменить</a></td>
                <td><a href="<c:url value='/remove/${part.id}'/>">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty listParts}">
    <h1 class="h1" align="center">{Пусто}</h1>
</c:if>

<%--Assembly--%>

</br>
<c:if test="${!empty listParts}">
    <table class="tg">
        <tr>
            <td width="355"><p class="p">Компьютеров можно собрать:</p></td>
            <td width="355"><p class="p">${forAssembly}</p></td>
        </tr>
    </table>
</c:if>
</br>

<%--Paging--%>

<div id="pagination" align="center">
    <c:url value="/parts" var="prev">
        <c:param name="page" value="${page-1}"/>
    </c:url>
    <c:if test="${page > 1}">
        <a href="<c:out value="${prev}" />" class="pn prev">Пред.</a>
    </c:if>

    <c:forEach begin="1" end="${maxPage}" step="1" varStatus="i">
        <c:choose>
            <c:when test="${page == i.index}">
                <span>${i.index}</span>
            </c:when>
            <c:otherwise>
                <c:url value="/parts" var="url">
                    <c:param name="page" value="${i.index}"/>
                </c:url>
                <a href='<c:out value="${url}" />'>${i.index}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:url value="/parts" var="next">
        <c:param name="page" value="${page + 1}"/>
    </c:url>
    <c:if test="${page + 1 <= maxPage}">
        <a href='<c:out value="${next}" />' class="pn next">След.</a>
    </c:if>
</div>


<c:choose>
    <c:when test="${empty part.title}">
        <h1 class="h1" align="center">Добавить новую деталь</h1>
    </c:when>
    <c:otherwise>
        <h1 class="h1" align="center">Изменить данные о детали</h1>
    </c:otherwise>
</c:choose>


<%--Add and Update--%>

<c:url var="addEvent" value="/parts/add"/>
<form:form action="${addEvent}" modelAttribute="part">
    <table class="center">
        <c:if test="${!empty part.title}">
            <tr>
                <td>ID:</td>
                <td><form:input path="id" value="${part.id}" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>Наименование:</td>
            <td><input type="text" name="title" value="${part.title}"/></td>
        </tr>
        <tr>
            <td>Необходимость:</td>
            <td>
                <select name="necessity">
                    <c:choose>
                        <c:when test="${part.necessity}">
                            <option value="true" label="necessity">да</option>
                            <option value="false" label="necessity">нет</option>
                        </c:when>
                        <c:otherwise>
                            <option value="false" label="necessity">нет</option>
                            <option value="true" label="necessity">да</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </td>
        </tr>
        <tr>
            <td>Количество:</td>
            <td><input type="text" name="quantity" value="${part.quantity}"/></td>
        </tr>
        <tr>
            <td>
                <c:if test="${!empty part.title}"><input type="submit" value="Изменить"/></c:if>
                <c:if test="${empty part.title}"><input type="submit" value="Добавить"/></c:if>
            </td>
        </tr>
    </table>

</form:form>

<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>
</body>
</html>
