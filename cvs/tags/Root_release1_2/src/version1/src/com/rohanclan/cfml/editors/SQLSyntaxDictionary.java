/*
 * Created on Jan 21, 2005
 * by Christopher Bradford
 *
 * Do we need to include the MIT License in each .java file?
 * 
 */
package com.rohanclan.cfml.editors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.rohanclan.cfml.editors.CFSyntaxDictionary;

/**
 * @author Christopher Bradford
 * The dictionary for SQL keywords to be used in CFQuery tags.
 * Extends CFSyntaxDicionary for now because CF operators should be color coded.
 * The keywords are loaded from an external file by calling loadKeywords
 *
 */
public class SQLSyntaxDictionary extends CFSyntaxDictionary {

	protected static Set sqlkeywords;

	/**
     * 
     */
    public SQLSyntaxDictionary() {
        super();
		sqlkeywords = new HashSet();

		operators = new HashSet();
		buildOperatorSyntax();
    }

	/**
	 * gets SQL specific keywords (SELECT, FROM etc.);
	 * @return all the keywords
	 */
	public Set getSQLKeywords()
	{
		return sqlkeywords;
	}

	/**
	 * load SQL keywords from the specified file; relative to dictionaryBaseURL 
	 * @param keywordFilename The file to read from
	 */
	public void loadKeywords(String keywordFilename) {
	    try {
			if(filename == null) throw new IOException("Keyword file name cannot be null!");
			
			URL url = new URL(dictionaryBaseURL + "/" + keywordFilename);
			InputStream iStream = url.openStream();
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(iStream));
			String line = fileReader.readLine();
			List keywords = new ArrayList();
			while (line != null) {
			    keywords.add(line.toLowerCase().trim());
			    line = fileReader.readLine();
			}
			buildSQLKeywordSyntax(keywords);
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/** 
	 * build all the SQL keywords 
	 * @param keywords The List of keywords to add to the Set
	 */
	protected void buildSQLKeywordSyntax(List keywords)
	{
	    Iterator it = keywords.iterator();
	    while (it.hasNext()) {
	    	String word = (String)it.next();
	        sqlkeywords.add(word);
	    }
	}

}