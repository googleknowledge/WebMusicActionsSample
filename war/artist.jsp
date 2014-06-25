<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/header.jsp"/>

<section>
	<img src="${artist.image}" width="100%"/>
	<div class="inner">
		<audio controls>
		  <source src="/music/dual_core_mastering_success_and_failure.mp3" type="audio/mpeg">
		Your browser does not support the audio element.
		</audio>
		<h2>${artist.name}</h2>
		<p>${artist.description}</p>
		<c:if test="${not empty artist.albums}">
		<div><strong>Albums</strong>
			<ol>
			<c:forEach var="album" items="${artist.albums}">
				<li><a href="${album.url}">${album.name}</a> <span class="runtime">(<fmt:formatDate pattern="yyyy" value="${album.datePublished}" />)</span></li>
			</c:forEach>
			</ol>
		</div>
		</c:if>
		<script type="application/ld+json">
		${jsonld}
		</script>
	</div>
</section>
 
 <jsp:include page="/footer.jsp"/>