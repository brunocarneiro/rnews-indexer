package com.conteudocompacto.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name="rnewsadmin_links")
@Indexed
public class RNewsLink {

	@Id
	private Long id; 
	
	@ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
	private RNewsFeed feed;
	
	private String link;//        | varchar(255) | YES  |     | NULL    |                | 
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String title;//       | varchar(255) | YES  |     | NULL    |                |
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String description;// | text         | YES  |     | NULL    |                |  
	
	@Field(index = Index.YES, analyze=Analyze.NO, store = Store.YES)
	@DateBridge(resolution = Resolution.DAY)
	private Date pubdate ;//    | datetime     | YES  |     | NULL    |                | 
	private String guid;//        | varchar(32)  | YES  |     | NULL    |      

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RNewsFeed getFeed() {
		return feed;
	}
	public void setFeed(RNewsFeed feed) {
		this.feed = feed;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPubdate() {
		return pubdate;
	}
	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}


}
