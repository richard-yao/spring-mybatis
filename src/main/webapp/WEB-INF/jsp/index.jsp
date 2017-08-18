<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
<h3>${message}</h3>
<h4>上传文件</h4>
<form action="fileSave" method="POST" enctype="multipart/form-data">
	<p>
		<label for="files">文件: </label>
		<input type="file" name="files" id="files" multiple="multiple">
	</p>
	<p>
		<button>提交</button>
	</p>
	<p>${images}</p>
</form>
</body>
</html>