<%--
  Created by IntelliJ IDEA.
  User: ax

  Date: 2019/2/20
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    你好

    <%--<img src="${pageContext.request.contextPath}/img/showImg?imgPath=http://119.23.12.250:8090/images/test.bmp" style="height: 200px;">--%>
    <img src="http://119.23.12.250:8090/images/test.bmp" style="height: 200px;">

    <%--<form action="${pageContext.request.contextPath}/img/imgUpload" method="post" enctype="multipart/form-data">--%>
    <form action="${pageContext.request.contextPath}/image/imgUpload" method="post" enctype="multipart/form-data">
        图片:<input type="file" name="imgFile"/><br/>
         <input type="submit" value="提交"/>
    </form>

</body>
</html>
