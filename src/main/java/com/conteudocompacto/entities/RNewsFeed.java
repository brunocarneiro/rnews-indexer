package com.conteudocompacto.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name="rnewsadmin_feeds")
@Indexed
public class RNewsFeed {
	
	@Id
	private Long id;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String name; //| varchar(255)     | YES  |     | NULL    |                | 
	private String main_link;//  | varchar(255)     | YES  |     | NULL    |                | 
	private String rss_link;//   | varchar(255)     | YES  |     | NULL    |                | 
	private String image_link;//   | varchar(255)     | YES  |     | NULL    |                |
	
	@ManyToOne
    @JoinColumn(name = "cat_id", nullable = false)
	private RNewsCategory cat;  //      | tinyint(4)       | NO   |     | 0       |                |
	
	private Date last_update;//   | datetime         | YES  |     | NULL    |                | 
	private Date last_add;//      | datetime         | YES  |     | NULL    |                | 
	private Integer num_headlines;// | tinyint(4)       | NO   |     | 10      |                | 
	private Integer max_links; //    | int(4)           | YES  |     | 0       |                | 
	private Integer stat_total;  //  | int(10) unsigned | YES  |     | 1       |                | 
	private Integer stat_expand;  // | int(10) unsigned | YES  |     | 0       |                | 
	private Integer stat_click;   // | int(10) unsigned
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMain_link() {
		return main_link;
	}
	public void setMain_link(String main_link) {
		this.main_link = main_link;
	}
	public String getRss_link() {
		return rss_link;
	}
	public void setRss_link(String rss_link) {
		this.rss_link = rss_link;
	}
	public String getImage_link() {
		return image_link;
	}
	public void setImage_link(String image_link) {
		this.image_link = image_link;
	}
	public RNewsCategory getCat() {
		return cat;
	}
	public void setCat(RNewsCategory cat) {
		this.cat = cat;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	public Date getLast_add() {
		return last_add;
	}
	public void setLast_add(Date last_add) {
		this.last_add = last_add;
	}
	public Integer getNum_headlines() {
		return num_headlines;
	}
	public void setNum_headlines(Integer num_headlines) {
		this.num_headlines = num_headlines;
	}
	public Integer getMax_links() {
		return max_links;
	}
	public void setMax_links(Integer max_links) {
		this.max_links = max_links;
	}
	public Integer getStat_total() {
		return stat_total;
	}
	public void setStat_total(Integer stat_total) {
		this.stat_total = stat_total;
	}
	public Integer getStat_expand() {
		return stat_expand;
	}
	public void setStat_expand(Integer stat_expand) {
		this.stat_expand = stat_expand;
	}
	public Integer getStat_click() {
		return stat_click;
	}
	public void setStat_click(Integer stat_click) {
		this.stat_click = stat_click;
	}

	
}
