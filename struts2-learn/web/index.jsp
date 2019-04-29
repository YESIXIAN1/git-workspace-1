<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2019/4/28
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  好好学习，天天向上

  <h1>struts2入门案例</h1>
  <a href="${pageContext.request.contextPath}/strutsDemo1.action">访问Strus2的action</a>

  <h1>客户管理</h1>
  <a href="${pageContext.request.contextPath}/addCustomerAction.action">添加客户</a>
  <a href="${pageContext.request.contextPath}/updateCustomerAction.action">修改客户</a>
  </body>
</html>
