package me.alexng.gns.util;

import me.alexng.gns.tokens.Token;

import java.util.Collection;
import java.util.Map;

public interface Crawler<T> {

	/**
	 * Crawls through a collection of key value pairs.
	 */
	void crawl(Collection<Map.Entry<String, Token>> entries);

	/**
	 * Returns the final value. This is only guaranteed to give proper results on the first call.
	 */
	T destroy();
}
