package com.google.developers.schemas.music_actions;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.TransactionOptions;
import com.google.developers.schemas.music_actions.doa.EMFService;
import com.google.developers.schemas.music_actions.model.Album;
import com.google.developers.schemas.music_actions.model.Artist;
import com.google.developers.schemas.music_actions.model.Track;

public class TestDataServlet extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		super.init();
		EntityManager entityManager = EMFService.get().createEntityManager();
		
		entityManager.getTransaction().begin();
		Artist artist = new Artist();
		artist.setId(KeyFactory.createKey(Artist.class.getSimpleName(), "DualCore"));
		artist.setName("Dual Core");
		artist.setImage("/img/dual_core.jpg");
		artist.setDescription(new Text("Brought together by the power of the internet (and perhaps a touch of musical providence), California based computer programmer / rapper int eighty and English web designer / music producer c64 have been rocking the more studious side of the hip hop underground since 2007."));
		entityManager.persist(artist);
		entityManager.getTransaction().commit();
		
		entityManager.getTransaction().begin();
		Album album1 = new Album();
		album1.setId(KeyFactory.createKey(Album.class.getSimpleName(), "AllTheThings"));
    	album1.setName("All The Things");
    	album1.setImage("/img/all_the_things.jpg");
    	album1.setDescription(new Text("All The Things is the latest and quite possibly the greatest Dual Core album, released in August 2012. Alongside int eighty's refined vocals and c64's varied production, All The Things features guest appearances from hip hop heavyweights Remington Forbes, Blueprint, Tribe One, More or Les, Ghettosocks, Timbuktu and Dale Chase."));
    	album1.setReleaseDate(new Date(113, 7, 28));
    	album1.setArtistId("DualCore");
    	entityManager.persist(album1);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
		Album album2 = new Album();
		album2.setId(KeyFactory.createKey(Album.class.getSimpleName(), "NextLevel"));
    	album2.setName("Next Level");
    	album2.setImage("/img/next_level.jpg");
    	album2.setDescription(new Text("2009 brought the release of the awaited fourth Dual Core album, Next Level. The album features a bolder sound which highlights both int eighty's clever lyrics and c64's heightened sense of sound. Next Level has received praise worldwide for its sharp sound and continued innovation. Along with appearances from nerdcore hip hop superstars MC Frontalot, MC Lars, Beefy, ytcracker, Schaffer the Darklord, and Random, Next Level also celebrates the return of Remington Forbes to the microphone. Next Level is a complete listen from start to finish."));
    	album2.setReleaseDate(new Date(109, 6, 31));
    	album2.setArtistId("DualCore");
    	entityManager.persist(album2);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
		Album album3 = new Album();
		album3.setId(KeyFactory.createKey(Album.class.getSimpleName(), "LostReality"));
    	album3.setName("Lost Reality");
    	album3.setImage("/img/lost_reality.jpg");
    	album3.setDescription(new Text("Lost Reality is the anticipated third release from Dual Core. The album features a refined sound which highlights c64's musical talents. Rapper int eighty tones his writing and vocals to match the feel of the tracks. Along with appearances including nerdcore behemoths Wheelie Cyberman (formerly of Optimus Rhyme), Beefy and ytcracker; Lost Reality presents the listener with a full album of solid listening enjoyment."));
    	album3.setReleaseDate(new Date(108, 4, 5));
    	album3.setArtistId("DualCore");
    	entityManager.persist(album3);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
		Album album4 = new Album();
		album4.setId(KeyFactory.createKey(Album.class.getSimpleName(), "SuperPowers"));
    	album4.setName("Super Powers");
    	album4.setImage("/img/super_powers.jpg");
    	album4.setDescription(new Text("In August of 2007, Dual Core caught audiences by surprise with the release of a second album, entitled Super Powers. This time, int eighty and c64 teamed up to produce songs based on the themes of superheroes and supervillains. The compilation features nerdcore super artists Beefy, MC Wreckshin, ytcracker, and ZeaLouS1."));
    	album4.setReleaseDate(new Date(107, 7, 3));
    	album4.setArtistId("DualCore");
    	entityManager.persist(album4);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
		Album album5 = new Album();
		album5.setId(KeyFactory.createKey(Album.class.getSimpleName(), "ZeroOne"));
    	album5.setName("Zero One");
    	album5.setImage("/img/zero_one.jpg");
    	album5.setDescription(new Text("Zero One is the album that started it all. Seventeen tracks, over seventy minutes of music. Featuring the single Hostage Down, the Zero One album became an instant hit. Dual Core received media coverage in the US and UK and launched on to the scene. As with all Dual Core albums, Zero One is available in MP3, AAC, Ogg Vorbis, ALAC and FLAC formats."));
    	album5.setReleaseDate(new Date(107, 3, 27));
    	album5.setArtistId("DualCore");
    	entityManager.persist(album5);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track1 = new Track();
    	track1.setId(KeyFactory.createKey(Track.class.getSimpleName(), "1"));
    	//track1.setAlbum(album);
    	//track1.setArtist(artist);
    	track1.setName("Drifter");
    	track1.setDuration("4:31");
    	track1.setAlbumId("AllTheThings");
    	entityManager.persist(track1);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track2 = new Track();
    	track2.setId(KeyFactory.createKey(Track.class.getSimpleName(), "2"));
    	//track2.setAlbum(album);
    	//track2.setArtist(artist);
    	track2.setName("Back To Me");
    	track2.setDuration("3:30");
    	track2.setAlbumId("AllTheThings");
    	entityManager.persist(track2);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track3 = new Track();
    	track3.setId(KeyFactory.createKey(Track.class.getSimpleName(), "3"));
    	//track3.setAlbum(album);
    	//track3.setArtist(artist);
    	track3.setName("Fear & Chaos");
    	track3.setDuration("4:57");
    	track3.setAlbumId("AllTheThings");
    	entityManager.persist(track3);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track4 = new Track();
    	track4.setId(KeyFactory.createKey(Track.class.getSimpleName(), "4"));
    	//track4.setAlbum(album);
    	//track4.setArtist(artist);
    	track4.setName("Hear Them Talking");
    	track4.setDuration("4:55");
    	track4.setAlbumId("AllTheThings");
    	entityManager.persist(track4);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track5 = new Track();
    	track5.setId(KeyFactory.createKey(Track.class.getSimpleName(), "5"));
    	//track5.setAlbum(album);
    	//track5.setArtist(artist);
    	track5.setName("Staring at the Last Star");
    	track5.setDuration("5:08");
    	track5.setAlbumId("AllTheThings");
    	entityManager.persist(track5);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track6 = new Track();
    	track6.setId(KeyFactory.createKey(Track.class.getSimpleName(), "6"));
    	//track6.setAlbum(album);
    	//track6.setArtist(artist);
    	track6.setName("Running (ft. Remington Forbes, Blueprint)");
    	track6.setDuration("5:53");
    	track6.setAlbumId("AllTheThings");
    	entityManager.persist(track6);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track7 = new Track();
    	track7.setId(KeyFactory.createKey(Track.class.getSimpleName(), "7"));
    	//track7.setAlbum(album);
    	//track7.setArtist(artist);
    	track7.setName("Dangerous Ways");
    	track7.setDuration("5:34");
    	track7.setAlbumId("AllTheThings");
    	entityManager.persist(track7);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track8 = new Track();
    	track8.setId(KeyFactory.createKey(Track.class.getSimpleName(), "8"));
    	//track8.setAlbum(album);
    	//track8.setArtist(artist);
    	track8.setName("The One (ft. Tribe One)");
    	track8.setDuration("4:24");
    	track8.setAlbumId("AllTheThings");
    	entityManager.persist(track8);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track9 = new Track();
    	track9.setId(KeyFactory.createKey(Track.class.getSimpleName(), "9"));
    	//track9.setAlbum(album);
    	//track9.setArtist(artist);
    	track9.setName("Farewell");
    	track9.setDuration("4:13");
    	track9.setAlbumId("AllTheThings");
    	entityManager.persist(track9);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track10 = new Track();
    	track10.setId(KeyFactory.createKey(Track.class.getSimpleName(), "10"));
    	//track10.setAlbum(album);
    	//track10.setArtist(artist);
    	track10.setName("Go Figure (ft. More Or Les, Ghettosocks, Timbuktu)");
    	track10.setDuration("6:13");
    	track10.setAlbumId("AllTheThings");
    	entityManager.persist(track10);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track11 = new Track();
    	track11.setId(KeyFactory.createKey(Track.class.getSimpleName(), "11"));
    	//track11.setAlbum(album);
    	//track11.setArtist(artist);
    	track11.setName("No Time (ft. Dale Chase)");
    	track11.setDuration("4:19");
    	track11.setAlbumId("AllTheThings");
    	entityManager.persist(track11);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track12 = new Track();
    	track12.setId(KeyFactory.createKey(Track.class.getSimpleName(), "12"));
    	//track12.setAlbum(album);
    	//track12.setArtist(artist);
    	track12.setName("All The Things");
    	track12.setDuration("4:44");
    	track12.setAlbumId("AllTheThings");
    	entityManager.persist(track12);
    	entityManager.getTransaction().commit();
    	
    	entityManager.getTransaction().begin();
    	Track track13 = new Track();
    	track13.setId(KeyFactory.createKey(Track.class.getSimpleName(), "13"));
    	//track13.setAlbum(album);
    	//track13.setArtist(artist);
    	track13.setName("Last Writes");
    	track13.setDuration("6:04");
    	track13.setAlbumId("AllTheThings");
    	entityManager.persist(track13);
    	entityManager.getTransaction().commit();
	}
	
}
