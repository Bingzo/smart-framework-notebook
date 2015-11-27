<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>客户管理 - 创建客户</title>
</head>
<body>

<h1>创建客户界面</h1>

<table>
    <tr>
        <th>客户名称</th>
        <th>联系人</th>
        <th>电话号码</th>
        <th>邮箱地址</th>
    </tr>
    <tr>
        <td>${customer.name}</td>
        <td>${customer.contact}</td>
        <td>${customer.telephone}</td>
        <td>${customer.email}</td>
    </tr>
</table>

<%-- TODO --%>

</body>
</html>