package com.google.developers.schemas.music_actions.doa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.developers.schemas.music_actions.model.Album;
import com.google.developers.schemas.music_actions.model.Artist;
import com.google.developers.schemas.music_actions.model.Track;

public class TrackDao {
	
	public static Track findTrackById(String id) {
		EntityManager entityManager = EMFService.get().createEntityManager();
		Key key = KeyFactory.createKey(Track.class.getSimpleName(), id);
		Track track = entityManager.find(Track.class, key);
		return track;
	}
	
	public static List<Track> findTracksByName(String name) {
		EntityManager entityManager = EMFService.get().createEntityManager();
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaQuery<Track> query = criteriaBuilder.createQuery(Track.class);
	    final EntityType<Track> type = entityManager.getMetamodel().entity(Track.class);
	    final Root<Track> root = query.from(Track.class);
	    query.where(criteriaBuilder.equal(root.get(
                type.getDeclaredSingularAttribute("uppercaseName", String.class)), name.toUpperCase()));
	    return entityManager.createQuery(query).getResultList();
	}
	
	public static List<Track> findTracksByAlbum(String albumId) {
		EntityManager entityManager = EMFService.get().createEntityManager();
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaQuery<Track> query = criteriaBuilder.createQuery(Track.class);
	    final EntityType<Track> type = entityManager.getMetamodel().entity(Track.class);
	    final Root<Track> root = query.from(Track.class);
	    query.where(criteriaBuilder.equal(root.get(
                type.getDeclaredSingularAttribute("albumId", String.class)), albumId));
	    return entityManager.createQuery(query).getResultList();
	}
}
