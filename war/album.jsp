<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/header.jsp"/>

<section>
	<img src="${album.image}" width="100%"/>
	<div class="inner">
		<audio controls>
		  <source src="/music/dual_core_mastering_success_and_failure.mp3" type="audio/mpeg">
		Your browser does not support the audio element.
		</audio>
		<h2>${album.name}</h2>
		<div class="artist">
			<a href="${album.byArtist.url}">${album.byArtist.name}</a>
		</div>
		<div><strong>Released:</strong> <fmt:formatDate pattern="MMMM dd, yyyy" value="${album.datePublished}" /></div>
		<p>${album.description}</p>
		
		<c:if test="${not empty album.tracks}">
		<div><strong>Tracks</strong>
			<ol>
			<c:forEach var="track" items="${album.tracks}">
				<li><a href="${track.url}">${track.name}</a> <span class="runtime">(${track.duration.time})</span></li>
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