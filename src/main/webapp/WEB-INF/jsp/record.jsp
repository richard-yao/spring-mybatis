<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show query record</title>
</head>
<body>
id is ${record.id}, name is ${record.name}
<!-- 使用c:url标签将路径转化为绝对路径 ,不然会因为spring路径映射导致静态资源获取不到-->
<img alt="图片" src="<c:url value="/images/BingWallpaper-2017-03-09.jpg"></c:url>">
get message: ${message} <!-- ${message}的内容如果含有标签元素，是会被浏览器解析的 -->
</body>
</html>