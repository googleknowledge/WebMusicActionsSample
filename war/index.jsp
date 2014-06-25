<jsp:include page="/header.jsp"/>

<section>
	<div class="inner">
		<br/>
		<h2>Welcome to Jarek & Shawn Music</h2>
		<br/>
		This is our sample music streaming app that showcases Schema.org Actions.
		<br/><br/>
		<div><strong>Artists</strong>
			<ol>
				<li><a href="/artist/DualCore">Dual Core</a></li>
			</ol>
		</div>
	</div>
</section>

<script type="application/ld+json">
{
  "@context": "http://schema.org",
  "@id": "http://jarekandshawnmusic.com",
  "@type": "WebPage",
  "name": "Jarek & Shawn Music",
  "description": "Jarek & Shawn Music is a sample music streaming app that showcases Schema.org Actions.",
  "potentialAction": {
    "@type": "SearchAction",
    "target": {
      "@type": "EntryPoint",
      "urlTemplate": "/search?q={query}",
      "contentType": "application/ld+json"
    },
    "query-input": "required maxlength=100 name=q"
  }
}
</script>

<jsp:include page="/footer.jsp"/>