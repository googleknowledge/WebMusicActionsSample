package com.google.developers.schemas.music_actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

public class TrackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private JsonLdSerializer serializer = new JsonLdSerializer();

    public TrackServlet() {}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
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
        	
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
    		serializer.serialize(trackSchema, os);
    		request.setAttribute("jsonld", new String(os.toByteArray(),"UTF-8"));
        	
        	request.setAttribute("title", "Jarek & Shawn Music - " + track.getName() + " - " + track.getAlbum().getName());
        	request.setAttribute("applink", "android-app://com.jarekandshawnmusic.m/http/jarekandshawnmusic.com" + trackId);
        	request.getRequestDispatcher("/track.jsp").forward(request, response);
        }
        catch (Throwable e1) {
            e1.printStackTrace();
        }

    }
}