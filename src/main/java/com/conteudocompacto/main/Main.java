package com.conteudocompacto.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.conteudocompacto.entities.RNewsLink;
import com.conteudocompacto.entities.TokenCount;
import com.conteudocompacto.entities.WordCounterHolder;
import com.conteudocompacto.persistence.JPAUtil;

public class Main {

	public static void main(String [] args){
		try {
			EntityManager em = JPAUtil.getInstance().getEm();
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
			fullTextEntityManager.createIndexer().startAndWait();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			for (TokenCount palavra : WordCounterHolder.getInstance().getWordCounter().values()){
				em.persist(palavra);
			}
			em.flush();
			tx.commit();
			search();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void search() {
		EntityManager em = JPAUtil.getInstance().getEm();
		FullTextEntityManager fullTextEntityManager = 
		    org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		em.getTransaction().begin();

		// create native Lucene query unsing the query DSL
		// alternatively you can write the Lucene query using the Lucene query parser
		// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
		    .buildQueryBuilder().forEntity( RNewsLink.class ).get();
		org.apache.lucene.search.Query query = qb
		  .keyword()
		  .onFields("title", "description")
		  .matching("Argentina")
		  .createQuery();

		// wrap Lucene query in a javax.persistence.Query
		javax.persistence.Query persistenceQuery = 
		    fullTextEntityManager.createFullTextQuery(query, RNewsLink.class);

		// execute search
		List result = persistenceQuery.getResultList();

		em.getTransaction().commit();
		em.close();
	}
}
