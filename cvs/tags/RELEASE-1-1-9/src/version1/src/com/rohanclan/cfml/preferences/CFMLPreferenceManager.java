/*
 * Created on Apr 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import com.rohanclan.cfml.CFMLPlugin;
//import com.rohanclan.cfml.editors.ICFColorConstants;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.jface.resource.StringConverter;

/**
 * @author Stephen Milligan
 *
 */
public class CFMLPreferenceManager implements ICFMLPreferenceConstants {

	private IPreferenceStore store;
	
	private static final int DEFAULT_TAB_WIDTH = 4;
	private static final int DEFAULT_INSIGHT_DELAY = 500;
	private static final boolean DEFAULT_INSERT_SPACES_FOR_TABS = false;
	private static final boolean DEFAULT_ENABLE_HS_COMPATIBILITY = false;
	private static final boolean DEFAULT_ENABLE_DW_COMPATIBILITY = false;
	/** this is public because the browser uses it on errors */
	public static final String DEFAULT_PROJECT_URL = "http://livedocs.macromedia.com";
	
	public CFMLPreferenceManager() {
		store = CFMLPlugin.getDefault().getPreferenceStore();
	}
	
	public void initializeDefaultValues() {
		
        store.setDefault(P_INSIGHT_DELAY, DEFAULT_INSIGHT_DELAY); 
        store.setDefault(P_TAB_WIDTH, DEFAULT_TAB_WIDTH);
        store.setDefault(P_INSERT_SPACES_FOR_TABS, DEFAULT_INSERT_SPACES_FOR_TABS);
        store.setDefault(P_ENABLE_HS_COMPATIBILITY, DEFAULT_ENABLE_HS_COMPATIBILITY);
        store.setDefault(P_ENABLE_DW_COMPATIBILITY, DEFAULT_ENABLE_DW_COMPATIBILITY);
        store.setDefault(P_SNIPPETS_PATH, CFMLPlugin.getDefault().getStateLocation().toString());
        //store.setDefault(P_CFTAG_COLOR,ICFColorConstants.CFTAG.toString());
	}
	
	public RGB getColor(String key)
	{
		String rgbString = store.getString(key);

		if (rgbString.length() <= 0)
		{
			rgbString = store.getDefaultString(key);
			if(rgbString.length() <= 0) 
			{
				rgbString = "0,0,0";
			}
		}
		return StringConverter.asRGB(rgbString);
	}
	
	
	public int tabWidth() {
		return Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_TAB_WIDTH).trim());
	}
	
	public int defaultTabWidth() {
		return DEFAULT_TAB_WIDTH;
	}
	
	
	
	public boolean insertSpacesForTabs() {
		return store.getString(ICFMLPreferenceConstants.P_INSERT_SPACES_FOR_TABS).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultSpacesForTabs() {
		return DEFAULT_INSERT_SPACES_FOR_TABS;
	}
	
	
	
	public int insightDelay() {
		return Integer.parseInt(store.getString(ICFMLPreferenceConstants.P_INSIGHT_DELAY).trim());
	}
	
	public int defaultInsightDelay() {
		return DEFAULT_INSIGHT_DELAY;
	}
	
	
	
	public boolean homesiteCompatibility() {
		return store.getString(ICFMLPreferenceConstants.P_ENABLE_HS_COMPATIBILITY).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultHomesiteCompatibility() {
		return DEFAULT_ENABLE_HS_COMPATIBILITY;
	}
	
	
	
	public boolean dreamweaverCompatibility() {
		return store.getString(ICFMLPreferenceConstants.P_ENABLE_DW_COMPATIBILITY).trim().equalsIgnoreCase("true");
	}
	
	public boolean defaultDreamweaverCompatibility() {
		return DEFAULT_ENABLE_DW_COMPATIBILITY;
	}
	
	
	
	public String snippetsPath() {
		return store.getString(ICFMLPreferenceConstants.P_SNIPPETS_PATH).trim();
	}
	
	public String defaultSnippetsPath() {
		return CFMLPlugin.getDefault().getStateLocation().toString();
	}
	
	public String projectURL() {
		return DEFAULT_PROJECT_URL;
	}
	
}