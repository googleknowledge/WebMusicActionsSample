<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/header.jsp"/>

<section>
	<img src="${track.image}" width="100%"/>
	<div class="inner">
		<audio controls>
		  <source src="/music/dual_core_mastering_success_and_failure.mp3" type="audio/mpeg">
		Your browser does not support the audio element.
		</audio>
		<h2>${track.name} <span class="runtime">(${track.duration.time})</span></h2>
		<div class="artist"><a href="${track.inAlbum.byArtist.url}">${track.inAlbum.byArtist.name}</a></div>
		<div><strong>Album:</strong> <a href="${track.inAlbum.url}">${track.inAlbum.name}</a></div>
		<div><strong>Released:</strong> <fmt:formatDate pattern="MMMM dd, yyyy" value="${track.inAlbum.datePublished}" /></div>
		<p>${track.description}</p>
		<script type="application/ld+json">
		${jsonld}
		</script>
	</div>
</section>

 <jsp:include page="/footer.jsp"/>