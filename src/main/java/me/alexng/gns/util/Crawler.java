package me.alexng.gns.util;

import me.alexng.gns.tokens.Token;

public interface Crawler<T> {

	/**
	 * Crawls to a new key value and stores it.
	 *
	 * @param key
	 * @param value
	 */
	void crawl(String key, Token value);

	/**
	 * Returns the final value. This is only guaranteed to give proper results on the first call.
	 */
	T destroy();
}
