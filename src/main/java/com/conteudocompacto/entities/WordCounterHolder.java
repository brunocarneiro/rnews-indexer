package com.conteudocompacto.entities;

import java.util.HashMap;

public class WordCounterHolder {
	
	private static WordCounterHolder instance;
	
	HashMap<String, TokenCount> wordCounter;
	
	private WordCounterHolder(){
		wordCounter = new HashMap<String, TokenCount>();
	}
	
	public static WordCounterHolder getInstance(){
		if(instance==null)
			instance = new WordCounterHolder();
		return instance;
	}
	
	public HashMap<String, TokenCount> getWordCounter() {
		return wordCounter;
	}
	
}
