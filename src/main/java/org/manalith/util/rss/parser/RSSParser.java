/*
 * Created on 2005. 4. 21
 */
package org.manalith.util.rss.parser;

import org.manalith.util.rss.RSSObject;

/**
 * @author setzer
 */
public interface RSSParser {
	public RSSObject parse(String url);
	public RSSObject parse(String url, String charset);
}
