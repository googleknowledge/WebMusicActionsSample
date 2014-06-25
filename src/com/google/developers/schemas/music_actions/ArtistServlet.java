package com.google.developers.schemas.music_actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.schema.MusicGroup;

import com.google.developers.schemas.jsonld.JsonLdSerializer;
import com.google.developers.schemas.music_actions.doa.AlbumDao;
import com.google.developers.schemas.music_actions.doa.ArtistDao;
import com.google.developers.schemas.music_actions.model.Album;
import com.google.developers.schemas.music_actions.model.Artist;

public class ArtistServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private JsonLdSerializer serializer = new JsonLdSerializer();
    
    public ArtistServlet() {}
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
        	// Fetch artist object
        	String artistId = request.getPathInfo().substring(1);
        	if (artistId.endsWith(".html")) {
        		artistId = artistId.substring(0, artistId.length()-5);
        	}
        	Artist artist = ArtistDao.findArtistById(artistId);
        	if (artist != null) {
	        	List<Album> albums = AlbumDao.findAlbumsByArtist(artistId);
	        	Collections.sort(albums, new Comparator(){
					@Override
					public int compare(Object o1, Object o2) {
						Album a1 = (Album)o1;
						Album a2 = (Album)o2;
						if (a1.getReleaseDate() == null) {
							return -1;
						} else if (a2.getReleaseDate() == null) {
							return 1;
						}
						return -a1.getReleaseDate().compareTo(a2.getReleaseDate());
					}});
	        	artist.setAlbums(albums);
	        	
	        	MusicGroup artistSchema = artist.asSchemaOrg(true, true);
	        	request.setAttribute("artist", artistSchema);
	        	
	        	// Generate JSON-LD markup for artist and actions
	        	ByteArrayOutputStream os = new ByteArrayOutputStream();
	    		serializer.serialize(artistSchema, os);
	    		request.setAttribute("jsonld", new String(os.toByteArray(),"UTF-8"));
	      
	    		// Render page template
	    		request.setAttribute("title", "Jarek & Shawn Music - " + artist.getName());
	    		request.setAttribute("applink", "android-app://com.jarekandshawnmusic.m/http/jarekandshawnmusic.com/artist/" + artistId);
	            request.getRequestDispatcher("/artist.jsp").forward(request, response);
        	} else {
        		response.setStatus(404);
        		request.getRequestDispatcher("/404.jsp").forward(request, response);
        	}
        }
        catch (Throwable e1) {
            e1.printStackTrace();
        }

    }
}