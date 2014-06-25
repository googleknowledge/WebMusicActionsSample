package com.google.developers.schemas.music_actions.doa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.developers.schemas.music_actions.model.Artist;

public class ArtistDao {
	
	public static Artist findArtistById(String id) {
		EntityManager entityManager = EMFService.get().createEntityManager();
		Key key = KeyFactory.createKey(Artist.class.getSimpleName(), id);
		Artist artist = entityManager.find(Artist.class, key);
		return artist;
	}
	
	public static List<Artist> findArtistsByName(String name) {
		EntityManager entityManager = EMFService.get().createEntityManager();
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaQuery<Artist> query = criteriaBuilder.createQuery(Artist.class);
	    final EntityType<Artist> type = entityManager.getMetamodel().entity(Artist.class);
	    final Root<Artist> root = query.from(Artist.class);
	    query.where(criteriaBuilder.equal(root.get(
                type.getDeclaredSingularAttribute("uppercaseName", String.class)), name.toUpperCase()));
	    return entityManager.createQuery(query).getResultList();
	}
}
