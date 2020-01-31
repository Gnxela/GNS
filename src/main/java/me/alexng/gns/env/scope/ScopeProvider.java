package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;

/**
 * @param <T> The reference type. Key.
 * @param <V> The value type. Value.
 */
public abstract class ScopeProvider<T, V> {

	ScopeProvider<T, V> parent;

	public ScopeProvider(ScopeProvider<T, V> parent) {
		this.parent = parent;
	}

	public abstract V get(T t) throws RuntimeException;

	public abstract V getLocal(T t) throws RuntimeException;

	public abstract void set(T t, V v) throws RuntimeException;

	public abstract void setLocal(T t, V v) throws RuntimeException;
}
