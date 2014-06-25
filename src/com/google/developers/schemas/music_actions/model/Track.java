package com.google.developers.schemas.music_actions.model;

import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.schema.EntryPoint;
import org.schema.ListenAction;
import org.schema.MusicAlbum;
import org.schema.MusicRecording;

import com.google.appengine.api.datastore.Key;
import com.google.developers.schemas.impl.DurationImpl;
import com.google.developers.schemas.impl.EntryPointImpl;
import com.google.developers.schemas.impl.ListenActionImpl;
import com.google.developers.schemas.impl.MusicAlbumImpl;
import com.google.developers.schemas.impl.MusicRecordingImpl;

@Entity
public class Track {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	private String name;
	private String uppercaseName;
	private String description;
	private String freebaseId;
	private String duration;
	
	private String albumId;
	
	//@ManyToOne(optional=false)
	//private Artist artist;
	
	//@ManyToOne(optional=false)
	@Transient
	private Album album;

	public Key getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getFreebaseId() {
		return freebaseId;
	}

	/*public Artist getArtist() {
		return artist;
	}*/

	public Album getAlbum() {
		return album;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFreebaseId(String freebaseId) {
		this.freebaseId = freebaseId;
	}

	/*public void setArtist(Artist artist) {
		this.artist = artist;
	}*/

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
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
	
	public MusicRecording asSchemaOrg(boolean showAction, boolean showChildren) {
		MusicRecording track = new MusicRecordingImpl("http://jarekandshawnmusic.com/track/" + id.getName());
    	
		if (getName() != null) {
    		track.setName(getName());
    	}
    	try {
    		track.setUrl(new URI("/track/" + id.getName()));
    		/*if (getImage() != null) {
    			album.setImage(new URI(getImage()));
    		}*/
    		if (getFreebaseId() != null) {
    			track.addSameAs(new URI(getFreebaseId()));
    		}
    		
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    	if (getDescription() != null) {
    		track.setDescription(getDescription());
    	}
    	if (getDuration() != null) {
    		track.setDuration(new DurationImpl(getDuration()));
    	}
    	if (showChildren && (getAlbum() != null)) {
    		
    		/*if (getAlbum().getArtist() != null) {
    			album.setByArtist(getAlbum().getArtist().asSchemaOrg(false));
    		}*/
    		MusicAlbum album = getAlbum().asSchemaOrg(false, true);
    		track.setInAlbum(album);
    	}
    	
    	if (showAction) {
    		ListenAction listenAction = new ListenActionImpl();
        	track.addPotentialAction(listenAction);
        	
        	EntryPoint webEntryPoint = new EntryPointImpl();
        	webEntryPoint.setUrlTemplate(track.getId());
        	listenAction.addTarget(webEntryPoint);
        	
        	EntryPoint mobileEntryPoint = new EntryPointImpl();
        	mobileEntryPoint.setUrlTemplate("android-app://com.jarekandshawnmusic.m/http/jarekandshawnmusic.com/track/" + id.getName());
        	listenAction.addTarget(mobileEntryPoint);
    	}
		
		return track;
	}
}
