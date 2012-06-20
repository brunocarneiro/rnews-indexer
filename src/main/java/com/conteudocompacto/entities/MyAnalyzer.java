package com.conteudocompacto.entities;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.KeywordMarkerFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;

public class MyAnalyzer extends StopwordAnalyzerBase {
	

	  private Set<?> stemExclusionSet;
	  
	  /** File containing default Portuguese stopwords. */
	  public final static String DEFAULT_STOPWORD_FILE = "portuguese_stop.txt";
	  
	  public MyAnalyzer(Version version) {
		  super(version);
		  stemExclusionSet=new CharArraySet(matchVersion, 10, true);
		  this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(
		        matchVersion, stemExclusionSet));
	  }
	  
	  /**
	   * Returns an unmodifiable instance of the default stop words set.
	   * @return default stop words set.
	   */
	  public static Set<?> getDefaultStopSet(){
	    return DefaultSetHolder.DEFAULT_STOP_SET;
	  }
	  
	  /**
	   * Atomically loads the DEFAULT_STOP_SET in a lazy fashion once the outer class 
	   * accesses the static final set the first time.;
	   */
	  private static class DefaultSetHolder {
	    static final Set<?> DEFAULT_STOP_SET;

	    static {
	      try {
	        DEFAULT_STOP_SET = WordlistLoader.getSnowballWordSet(IOUtils.getDecodingReader(SnowballFilter.class, 
	            DEFAULT_STOPWORD_FILE, IOUtils.CHARSET_UTF_8), Version.LUCENE_CURRENT);
	      } catch (IOException ex) {
	        // default set should always be present as it is part of the
	        // distribution (JAR)
	        throw new RuntimeException("Unable to load default stopword set");
	      }
	    }
	  }


	  /**
	   * Creates a
	   * {@link org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents}
	   * which tokenizes all the text in the provided {@link Reader}.
	   * 
	   * @return A
	   *         {@link org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents}
	   *         built from an {@link StandardTokenizer} filtered with
	   *         {@link StandardFilter}, {@link LowerCaseFilter}, {@link StopFilter}
	   *         , {@link KeywordMarkerFilter} if a stem exclusion set is
	   *         provided and {@link SnowballFilter}.
	   */
	  @Override
	  protected TokenStreamComponents createComponents(String fieldName,
	      Reader reader) {
	    final Tokenizer source = new StandardTokenizer(matchVersion, reader);
	    TokenStream result = new StandardFilter(matchVersion, source);
	    result = new LowerCaseFilter(matchVersion, result);
	    result = new StopFilter(matchVersion, result, stopwords);
	    result = new WordCounterFilter(result, getDefaultStopSet());
	    if(!stemExclusionSet.isEmpty())
	      result = new KeywordMarkerFilter(result, stemExclusionSet);
	    result = new SnowballFilter(result, new org.tartarus.snowball.ext.PortugueseStemmer());
	    return new TokenStreamComponents(source, result);
	  }
	
}

class WordCounterFilter extends FilteringTokenFilter {
	
	HashMap<String, Integer> wordCounter = new HashMap<String, Integer>(); 
	Set<?> stopWords;

	public WordCounterFilter(TokenStream input, Set<?> set) {
		super(true, input);
		stopWords=set;
		
	}

	@Override
	protected boolean accept() throws IOException {
		CharTermAttribute source = input.getAttribute(CharTermAttribute.class);
		Integer count = wordCounter.get(source.toString());
		String word = makeUniqueWord(source.toString());
		if(stopWords.contains(source.toString())){
			return false;
		}
		if (count==null)
			wordCounter.put(word,1);
		else
			wordCounter.put(word,++count);
		
		return true;
	}
	
	@Override
	public void close() throws IOException {
	
		for (String palavra : wordCounter.keySet()){
			TokenCount tc = new TokenCount(palavra, wordCounter.get(palavra));
			WordCounterHolder.getInstance().getWordCounter().put(palavra,tc);
		}
		super.close();
	}
	
	protected String makeUniqueWord(String word){
		
		return StringUtils.deleteWhitespace(word).toUpperCase();
		
	}
}
