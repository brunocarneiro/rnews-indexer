package com.conteudocompacto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.Session;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;

import com.conteudocompacto.entities.RNewsLink;

import static org.junit.Assert.assertEquals;

/**
 * Example testcase for Hibernate Search
 */
public class RNewsTest {

	private EntityManagerFactory emf;

	private EntityManager em;

	private static Logger log = LoggerFactory.getLogger( RNewsTest.class );

	@Before
	public void setUp() {
		initHibernate();
	}


	@Test
	public void testIndexAndSearch() throws Exception {
		RNewsLink link = em.find(RNewsLink.class, new Long(1));
		assertEquals((long)link.getId(), 1L);
	}




	private void initHibernate() {
		Ejb3Configuration config = new Ejb3Configuration();
		config.configure( "hibernate-search-example", new HashMap() );
		emf = config.buildEntityManagerFactory();
		em = emf.createEntityManager();
	}

	private void index() {
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession( (Session) em.getDelegate() );
		try {
			ftSession.createIndexer().startAndWait();
		}
		catch ( InterruptedException e ) {
			log.error( "Was interrupted during indexing", e );
		}
	}
}
