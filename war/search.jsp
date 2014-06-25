<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/header.jsp"/>

<section>
	<div class="inner">
		<br/>
		<p>Found <strong>${fn:length(results)}</strong> results for <strong>"${query}"</strong>.</p>
		
		<jsp:useBean id="typeDisambiguators" class="java.util.HashMap" scope="request"/>
		<c:set target="${typeDisambiguators}" property="com.google.developers.schemas.impl.MusicGroupImpl" value="Artist"/>
		<c:set target="${typeDisambiguators}" property="com.google.developers.schemas.impl.MusicAlbumImpl" value="Album"/>
		<c:set target="${typeDisambiguators}" property="com.google.developers.schemas.impl.MusicRecordingImpl" value="Track"/> 
		
		<c:if test="${not empty results}">
			<ol class="search_results">
			<c:forEach var="result" items="${results}">
				<li><a href="${result.url}">${result.name}</a> (${typeDisambiguators[result.class.name]})</li>
			</c:forEach>
			</ol>
		</c:if>
		<script type="application/ld+json">
		${jsonld}
		</script>
	</div>
</section>
 
<jsp:include page="/footer.jsp"/>