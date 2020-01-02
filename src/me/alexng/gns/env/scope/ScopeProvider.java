package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.IdentifiedToken;

public abstract class ScopeProvider<T, V> {

	ScopeProvider<T, V> parent;

	public ScopeProvider(ScopeProvider<T, V> parent) {
		this.parent = parent;
	}

	public abstract V get(IdentifiedToken identifiedToken) throws RuntimeException;

	public abstract V getLocal(IdentifiedToken identifiedToken);

	public abstract void set(T t) throws RuntimeException;

	public abstract void setLocal(T t);
}
