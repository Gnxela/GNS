package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.IdentifiedToken;

public abstract class ScopeProvider<T> {

	ScopeProvider<T> parent;

	public ScopeProvider(ScopeProvider<T> parent) {
		this.parent = parent;
	}

	public abstract T get(IdentifiedToken identifiedToken) throws RuntimeException;

	public abstract T getLocal(IdentifiedToken identifiedToken);

	public abstract void set(T t) throws RuntimeException;

	public abstract void setLocal(T t);
}
