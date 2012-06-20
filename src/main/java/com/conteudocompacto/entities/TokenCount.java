package com.conteudocompacto.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="SE_TOKEN_COUNT", sequenceName="SE_TOKEN_COUNT")
public class TokenCount {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SE_TOKEN_COUNT")
	private Long id;
	private String palavra;
	private Integer count;
	private Date data;
	
	public TokenCount() {}
	
	public TokenCount(String palavra, Integer count) {
		this.palavra = palavra;
		this.count = count;
		this.data = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPalavra() {
		return palavra;
	}
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
