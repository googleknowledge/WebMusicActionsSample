package com.google.developers.schemas.music_actions.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.schema.EntryPoint;
import org.schema.ListenAction;
import org.schema.MusicAlbum;
import org.schema.MusicGroup;
import org.schema.MusicRecording;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.developers.schemas.impl.DurationImpl;
import com.google.developers.schemas.impl.EntryPointImpl;
import com.google.developers.schemas.impl.ListenActionImpl;
import com.google.developers.schemas.impl.MusicAlbumImpl;
import com.google.developers.schemas.impl.MusicRecordingImpl;

@Entity
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	private String name;
	private String uppercaseName;
	private Text description;
	private String image;
	private String freebaseId;
	private Date releaseDate;
	
	private String artistId;
	
	//@ManyToOne(optional=false)
	@Transient
	private Artist artist;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Transient
    private List<Track> tracks = new ArrayList<Track>();

	public Key getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Text getDescription() {
		return description;
	}

	public String getImage() {
		return image;
	}

	public String getFreebaseId() {
		return freebaseId;
	}

	public Artist getArtist() {
		return artist;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(Text description) {
		this.description = description;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setFreebaseId(String freebaseId) {
		this.freebaseId = freebaseId;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	@PrePersist
    @PreUpdate
    public void prePersist() {
	    if (name != null) {
	    	uppercaseName = name.toUpperCase();
	    } else {
	    	uppercaseName = null;
	    }
    }
	
	public MusicAlbum asSchemaOrg(boolean showAction, boolean showChildren) {
		MusicAlbum album = new MusicAlbumImpl("http://jarekandshawnmusic.com/album/" + id.getName());
    	
		if (getName() != null) {
    		album.setName(getName());
    	}
    	try {
    		album.setUrl(new URI("/album/" + id.getName()));
    		if (getImage() != null) {
    			album.setImage(new URI(getImage()));
    		}
    		if (getFreebaseId() != null) {
    			album.addSameAs(new URI(getFreebaseId()));
    		}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    	
    	if (getDescription() != null) {
    		album.setDescription(getDescription().getValue());
    	}
    	if (getReleaseDate() != null) {
    		album.setDatePublished(getReleaseDate());
    	}
    	
    	if (showChildren) {
    		if (getArtist() != null) {
        		album.setByArtist(getArtist().asSchemaOrg(false, false));
        	}
	    	for (Track track : getTracks()) {
	    		album.addTrack(track.asSchemaOrg(false, false));
	    	}
    	}
    	
    	if (showAction) {
    		ListenAction listenAction = new ListenActionImpl();
        	album.addPotentialAction(listenAction);
        	
        	EntryPoint webEntryPoint = new EntryPointImpl();
        	webEntryPoint.setUrlTemplate(album.getId());
        	listenAction.addTarget(webEntryPoint);
        	
        	EntryPoint mobileEntryPoint = new EntryPointImpl();
        	mobileEntryPoint.setUrlTemplate("android-app://com.jarekandshawnmusic.m/http/jarekandshawnmusic.com/album/" + id.getName());
        	listenAction.addTarget(mobileEntryPoint);
    	}
		
		return album;
	}
}
