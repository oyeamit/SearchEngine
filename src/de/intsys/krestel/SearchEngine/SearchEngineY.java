package de.intsys.krestel.SearchEngine;

import java.util.ArrayList;
import java.util.Date;

/* This is your file! implement your search engine here!
 * 
 * Describe your search engine briefly:
 *  - multi-threaded?
 *  - stemming?
 *  - stopword removal?
 *  - index algorithm?
 *  - etc.  
 * 
 */

// Replace 'Y' with your team name
public class SearchEngineY extends SearchEngine {

	
	// Replace 'Y' with your team name
	public SearchEngineY() {
		// This should stay as is! Don't add anything here!
		super();	
	}

	@Override
	void index(String dir) {
		// TODO Auto-generated method stub
	}

	@Override
	boolean loadIndex(String directory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	ArrayList<String> search(String query, int topK, int prf) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	Double computeNdcg(ArrayList<String> goldRanking, ArrayList<String> ranking, int ndcgAt) {
	
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	int crawlNewspaper(String newspaper, Date day) {
		// TODO Auto-generated method stub
		return 0;
	}
}
