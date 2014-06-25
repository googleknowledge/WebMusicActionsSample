package com.google.developers.schemas.music_actions.doa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.developers.schemas.music_actions.model.Album;

public class AlbumDao {
	
	public static Album findAlbumById(String id) {
		EntityManager entityManager = EMFService.get().createEntityManager();
		Key key = KeyFactory.createKey(Album.class.getSimpleName(), id);
		Album album = entityManager.find(Album.class, key);
		return album;
	}
	
	public static List<Album> findAlbumsByName(String name) {
		EntityManager entityManager = EMFService.get().createEntityManager();
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaQuery<Album> query = criteriaBuilder.createQuery(Album.class);
	    final EntityType<Album> type = entityManager.getMetamodel().entity(Album.class);
	    final Root<Album> root = query.from(Album.class);
	    query.where(criteriaBuilder.equal(root.get(
                type.getDeclaredSingularAttribute("uppercaseName", String.class)), name.toUpperCase()));
		
	    return entityManager.createQuery(query).getResultList();
	}
	
	public static List<Album> findAlbumsByArtist(String artistId) {
		EntityManager entityManager = EMFService.get().createEntityManager();
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaQuery<Album> query = criteriaBuilder.createQuery(Album.class);
	    final EntityType<Album> type = entityManager.getMetamodel().entity(Album.class);
	    final Root<Album> root = query.from(Album.class);
	    query.where(criteriaBuilder.equal(root.get(
                type.getDeclaredSingularAttribute("artistId", String.class)), artistId));
	    return entityManager.createQuery(query).getResultList();
	}
}
