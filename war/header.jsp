<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>${title}</title>
	<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="/css/main.css" />
	<c:if test="${not empty applink}"><link rel="alternate" href="${applink}" /></c:if>
</head>
<body>
	<header>
		<div class="inner">
			<a href="/"><h1>Jarek & Shawn Music</h1></a>
			<form action="/search" method="GET">
			<input type="text" id="search" name="q" placeholder="Search" value="${query}" />
			</form>
		</div>
	</header>