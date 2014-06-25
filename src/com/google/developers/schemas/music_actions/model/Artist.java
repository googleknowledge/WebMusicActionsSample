package com.google.developers.schemas.music_actions.model;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.schema.EntryPoint;
import org.schema.ListenAction;
import org.schema.MusicGroup;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.developers.schemas.impl.EntryPointImpl;
import com.google.developers.schemas.impl.ListenActionImpl;
import com.google.developers.schemas.impl.MusicGroupImpl;

@Entity
public class Artist implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	private String name;
	private String uppercaseName;
	private Text description;
	private String image;
	private String freebaseId;
	
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)*/
	@Transient
    private List<Album> albums = new ArrayList<Album>();
    
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

	public List<Album> getAlbums() {
		return albums;
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

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
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
	
	public MusicGroup asSchemaOrg(boolean showAction, boolean showChildren) {
		MusicGroup musicGroup = new MusicGroupImpl("http://jarekandshawnmusic.com/artist/" + id.getName());
		if (getName() != null) {
    		musicGroup.setName(getName());
    	}
    	try {
    		musicGroup.setUrl(new URI("/artist/" + id.getName()));
    		if (getImage() != null) {
    			musicGroup.setImage(new URI(getImage()));
    		}
    		if (getFreebaseId() != null) {
    			musicGroup.addSameAs(new URI(getFreebaseId()));
    		}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    	if (getDescription() != null) {
    		musicGroup.setDescription(getDescription().getValue());
    	}
    	if (showChildren) {
	    	for (Album album : getAlbums()) {
	    		musicGroup.addAlbum(album.asSchemaOrg(false, false));
	    	}
    	}
    	
    	if (showAction) {
    		ListenAction listenAction = new ListenActionImpl();
    		musicGroup.addPotentialAction(listenAction);
        	
        	EntryPoint webEntryPoint = new EntryPointImpl();
        	webEntryPoint.setUrlTemplate(musicGroup.getId());
        	listenAction.addTarget(webEntryPoint);
        	
        	EntryPoint mobileEntryPoint = new EntryPointImpl();
        	mobileEntryPoint.setUrlTemplate("android-app://com.jarekandshawnmusic.m/http/jarekandshawnmusic.com/artist/" + id.getName());
        	listenAction.addTarget(mobileEntryPoint);
    	}
    	
		return musicGroup;
	}
}
