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

    <img src="${pageContext.request.contextPath}/img/showImg?imgPath=D:\file\photo\testIm.png" style="height: 200px;">

    <form action="${pageContext.request.contextPath}/img/imgUpload" method="post" enctype="multipart/form-data">
        图片:<input type="file" name="imgFile"/><br/>
         <input type="submit" value="提交"/>
    </form>

</body>
</html>
