<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>For freemarker</title>
</head>
<body>
<p>${message}</p>
	<ul>
		<#list users as user><li>${user}</li></#list>
	</ul>
</body>
</html>