package com.conteudocompacto.persistence;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPAUtil {
	
	private static JPAUtil instance;
	
	private EntityManagerFactory emf;

	private EntityManager em;

	private static Logger log = LoggerFactory.getLogger( JPAUtil.class );


	private JPAUtil(){
		initHibernate();
	}
	
	public static JPAUtil getInstance(){
		if(instance==null)
			instance=new JPAUtil();
			
		return instance;
	}
	
	private void initHibernate() {
		Ejb3Configuration config = new Ejb3Configuration();
		config.configure( "hibernate-search-example", new HashMap() );
		emf = config.buildEntityManagerFactory();
		em = emf.createEntityManager();
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	
}
