package com.google.developers.schemas.music_actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.schema.MusicRecording;

import com.google.developers.schemas.jsonld.JsonLdSerializer;
import com.google.developers.schemas.music_actions.doa.AlbumDao;
import com.google.developers.schemas.music_actions.doa.ArtistDao;
import com.google.developers.schemas.music_actions.doa.TrackDao;
import com.google.developers.schemas.music_actions.model.Album;
import com.google.developers.schemas.music_actions.model.Artist;
import com.google.developers.schemas.music_actions.model.Track;

public class TrackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private JsonLdSerializer serializer = new JsonLdSerializer();

    public TrackServlet() {}
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
        	// Fetch track object
        	String trackId = request.getPathInfo().substring(1);
        	if (trackId.endsWith(".html")) {
        		trackId = trackId.substring(0, trackId.length()-5);
        	}
        	Track track = TrackDao.findTrackById(trackId);
        	
        	Album album = AlbumDao.findAlbumById(track.getAlbumId());
        	track.setAlbum(album);
        	
        	Artist artist = ArtistDao.findArtistById(album.getArtistId());
        	album.setArtist(artist);
        	
        	MusicRecording trackSchema = track.asSchemaOrg(true, true);
        	if (album.getImage() != null) {
        		trackSchema.setImage(new URI(album.getImage()));
        	}
        	request.setAttribute("track", trackSchema);
        	
        	// Generate JSON-LD markup for track and actions
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
    		serializer.serialize(trackSchema, os);
    		request.setAttribute("jsonld", new String(os.toByteArray(),"UTF-8"));
        	
    		// Render page template
        	request.setAttribute("title", "Jarek & Shawn Music - " + track.getName() + " - " + track.getAlbum().getName());
        	request.setAttribute("applink", "android-app://com.jarekandshawnmusic.m/http/jarekandshawnmusic.com" + trackId);
        	request.getRequestDispatcher("/track.jsp").forward(request, response);
        }
        catch (Throwable e1) {
            e1.printStackTrace();
        }

    }
}