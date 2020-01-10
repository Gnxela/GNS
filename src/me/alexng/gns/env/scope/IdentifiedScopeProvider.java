package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.IdentifiedToken;

public abstract class IdentifiedScopeProvider<T extends IdentifiedToken> extends ScopeProvider<IdentifiedToken, T> {

	IdentifiedScopeProvider<T> parent;

	public IdentifiedScopeProvider(IdentifiedScopeProvider<T> parent) {
		super(parent);
		this.parent = parent;
	}

	public void set(T t) throws RuntimeException {
		set(t.getIdentifier(), t);
	}

	public void setLocal(T t) throws RuntimeException {
		setLocal(t.getIdentifier(), t);
	}
}
