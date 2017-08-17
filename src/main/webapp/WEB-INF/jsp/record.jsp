<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show query record</title>
</head>
<body>
id is ${record.id}, name is ${record.name}
<br/>
<!-- 使用c:url标签将路径转化为绝对路径 ,不然会因为spring路径映射导致静态资源获取不到-->
<img alt="图片" style="width:720px" src="<c:url value="/images/BingWallpaper-2017-03-09.jpg"></c:url>">
<br/>
get message: ${message} <!-- ${message}的内容如果含有标签元素，是会被浏览器解析的 -->
<br/>
<div>
	<form:form modelAttribute="record" action="index"> <!-- 这里action指定post请求的地址 -->
		<p>
			<label for="id">Id: </label>
			<form:input path="id" readonly="readonly"/>
		</p>
		<p>
			<label for="type">Type: </label>
			<form:input path="type" readonly="readonly"/>
		</p>
		<p>
			<label for="name">Name: </label>
			<form:input path="name"/>
		</p>
		<p>
			<label for="version">Version: </label>
			<form:input path="version"  htmlEscape="true"/> <!-- 为false表示不对特殊字符进行html转义 -->
		</p>
	</form:form>
</div>
</body>
</html>