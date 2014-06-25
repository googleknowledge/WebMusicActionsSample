package com.google.developers.schemas.music_actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.schema.EntryPoint;
import org.schema.ListenAction;
import org.schema.MusicAlbum;
import org.schema.MusicGroup;
import org.schema.MusicRecording;

import com.google.developers.schemas.impl.DurationImpl;
import com.google.developers.schemas.impl.EntryPointImpl;
import com.google.developers.schemas.impl.ListenActionImpl;
import com.google.developers.schemas.impl.MusicAlbumImpl;
import com.google.developers.schemas.impl.MusicGroupImpl;
import com.google.developers.schemas.impl.MusicRecordingImpl;
import com.google.developers.schemas.jsonld.JsonLdSerializer;
import com.google.developers.schemas.music_actions.doa.AlbumDao;
import com.google.developers.schemas.music_actions.doa.ArtistDao;
import com.google.developers.schemas.music_actions.doa.TrackDao;
import com.google.developers.schemas.music_actions.model.Album;
import com.google.developers.schemas.music_actions.model.Artist;
import com.google.developers.schemas.music_actions.model.Track;

public class AlbumServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private JsonLdSerializer serializer = new JsonLdSerializer();

    public AlbumServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
        	// Fetch artist object
        	String albumId = request.getPathInfo().substring(1);
        	if (albumId.endsWith(".html")) {
        		albumId = albumId.substring(0, albumId.length()-5);
        	}
        	Album album = AlbumDao.findAlbumById(albumId);
        	if (album != null) {
	        	Artist artist = ArtistDao.findArtistById(album.getArtistId());
	        	album.setArtist(artist);
	        	
	        	List<Track> tracks = TrackDao.findTracksByAlbum(albumId);
	        	Collections.sort(tracks, new Comparator(){
					@Override
					public int compare(Object o1, Object o2) {
						Long t1 = Long.parseLong(((Track)o1).getId().getName());
						Long t2 = Long.parseLong(((Track)o2).getId().getName());
						return t1.compareTo(t2);
					}});
	        	album.setTracks(tracks);
	        	
	        	MusicAlbum albumSchema = album.asSchemaOrg(true, true);
	        	request.setAttribute("album", albumSchema);
	        	
	        	// Generate JSON-LD markup for album and actions
	        	ByteArrayOutputStream os = new ByteArrayOutputStream();
	    		serializer.serialize(albumSchema, os);
	    		request.setAttribute("jsonld", new String(os.toByteArray(),"UTF-8"));
	      
	    		// Render page template
	    		request.setAttribute("title", "Jarek & Shawn Music - " + album.getName()); // + " - " + album.getArtist().getName());
	    		request.setAttribute("applink", "android-app://com.jarekandshawnmusic.m/http/jarekandshawnmusic.com/album/" + albumId);
	    		request.getRequestDispatcher("/album.jsp").forward(request, response);
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