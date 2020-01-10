package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.ClassToken;
import me.alexng.gns.tokens.IdentifiedToken;

import java.util.HashMap;
import java.util.Map;

public class ClassProvider extends IdentifiedScopeProvider<ClassToken> {

	private Map<String, ClassToken> classes;

	public ClassProvider(ClassProvider parent) {
		super(parent);
		this.classes = new HashMap<>();
	}

	@Override
	public ClassToken get(IdentifiedToken identifiedToken) throws RuntimeException {
		ClassToken classToken = getLocal(identifiedToken);
		if (classToken != null) {
			return classToken;
		}
		if (parent != null) {
			return parent.get(identifiedToken);
		}
		throw new RuntimeException(identifiedToken, "Undefined class: " + identifiedToken.getIdentifier().getName());
	}

	@Override
	public ClassToken getLocal(IdentifiedToken identifiedToken) {
		return getLocal(identifiedToken.getIdentifier().getName());
	}

	public ClassToken getLocal(String name) {
		return classes.get(name);
	}

	@Override
	public void set(IdentifiedToken identifiedToken, ClassToken classToken) throws RuntimeException {
		setLocal(identifiedToken, classToken);
	}

	@Override
	public void setLocal(IdentifiedToken identifiedToken, ClassToken classToken) {
		try {
			get(identifiedToken);
			throw new RuntimeException(classToken, "Class already defined");
		} catch (RuntimeException ignored) {
			classes.put(identifiedToken.getIdentifier().getName(), classToken);
		}
	}
}
