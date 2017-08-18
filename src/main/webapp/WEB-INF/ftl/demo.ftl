<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>For freemarker</title>
</head>
<body>
<p>${message}</p>
	<ul>
		<#list users as user><li>${user}</li></#list>
	</ul>
	<ul>
		<#-- 集合运算符+, 和函数式语言一样 -->
		<#list ["Monday", "Tuseday", "Wednesday"] + ["Saturday", "Sunday"] as x>
			<li>Today is ${x}</li>
		</#list>
	</ul>
	<ul>
		<#-- 对于复合类型数据，可以用点语法来取值，ftl中注解不会被渲染 -->
		<#list reportdata.report as data>
			<li>Record contains: ${data.type}, ${data.id}, ${data.name}, ${data.version}</li>
		</#list>
	</ul>
	<ul>
		<#-- assign用于创建或替换一个顶层变量 -->
		<#-- 在map的连接中，相同key的值会被右边的value替换 -->
		<#assign scores = {"语文": 86, "数学": 78} + {"数学": 87, "Java": 93}>
		<li>语文  is ${scores.语文}</li>
		<li>数学  is ${scores.数学}</li>
		<li>Java  is ${scores.Java}</li>
	</ul>
	<ul>
		<#-- freemarker内建函数，用于转换输出 -->
		<#assign test = " tom & jerry ">
		<#assign test2 = ["tom", "jerry"]>
		<li>Convert to html: ${test?html}</li>
		<li>Convert to upper case: ${test?upper_case}</li>
		<li>Convert to captial first: ${test?cap_first}</li>
		<li>Trim function: ${test?trim?html}</li>
		<li>Element number: ${test2?size}</li> <#-- size必须用在集合中，否则会出错 -->
		<li>
			<#if element??> <#-- 使用??来判断一个变量是否存在，只会返回boolean值 -->
				element is exist, value is ${elemt}
			<#else>
				element is not exist.
			</#if>
		</li>
		<#assign age = 23>
		<#if (age > 60)>
			<li>老年人</li>
		<#elseif (age > 40)>
			<li>中年人</li>
		<#elseif (age > 20)>
			<li>青年人</li>
		<#else>
			<li>少年人</li>
		</#if>
	</ul>
</body>
</html>