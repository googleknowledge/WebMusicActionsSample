package com.google.developers.schemas.music_actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.schema.SearchAction;

import com.google.developers.schemas.impl.SearchActionImpl;
import com.google.developers.schemas.jsonld.JsonLdSerializer;
import com.google.developers.schemas.music_actions.doa.AlbumDao;
import com.google.developers.schemas.music_actions.doa.ArtistDao;
import com.google.developers.schemas.music_actions.doa.TrackDao;
import com.google.developers.schemas.music_actions.model.Album;
import com.google.developers.schemas.music_actions.model.Artist;
import com.google.developers.schemas.music_actions.model.Track;

public class SearchServlet extends HttpServlet {
	
	private JsonLdSerializer serializer = new JsonLdSerializer();
	
	private SearchAction doSearch(String query)  {
		SearchAction searchAction = new SearchActionImpl();
		
		for (Artist artist : ArtistDao.findArtistsByName(query)) {
			searchAction.addResult(artist.asSchemaOrg(true, false));
		}
		for (Album album : AlbumDao.findAlbumsByName(query)) {
			searchAction.addResult(album.asSchemaOrg(true, false));
		}
		for (Track track : TrackDao.findTracksByName(query)) {
			searchAction.addResult(track.asSchemaOrg(true, false));
		}
    	return searchAction;
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
        	String query = request.getParameter("q");
        	SearchAction searchAction = doSearch(query);
        	
        	// Generate JSON-LD markup for artist and actions
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
    		serializer.serialize(searchAction, os);
    		String jsonld = new String(os.toByteArray(),"UTF-8");
        	
        	if ((request.getServletPath() != null &&
        		 request.getServletPath().endsWith(".json")) ||
        		(request.getHeader("Accept") != null &&
        		 request.getHeader("Accept").contains("application/ld+json"))) {
        		response.setContentType("application/ld+json");
	    		PrintWriter writer = response.getWriter();
	    		writer.write(jsonld);
	    		writer.close();
        	} else {
	    		request.setAttribute("jsonld", jsonld);
	    		// Render page template
	    		request.setAttribute("query", query);
	    		request.setAttribute("results", searchAction.getResults());
	    		request.setAttribute("title", "Jarek & Shawn Music - Seach Results");
	    		request.getRequestDispatcher("/search.jsp").forward(request, response);
        	}
        }
        catch (Throwable e1) {
            e1.printStackTrace();
        }

    }
	
}
