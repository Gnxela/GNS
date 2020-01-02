package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.ClassToken;
import me.alexng.gns.tokens.IdentifiedToken;

import java.util.HashMap;
import java.util.Map;

public class ClassProvider extends ScopeProvider<ClassToken, ClassToken> {

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
		return classes.get(identifiedToken.getIdentifier().getName());
	}

	@Override
	public void set(ClassToken classToken) throws RuntimeException {
		setLocal(classToken);
	}

	@Override
	public void setLocal(ClassToken classToken) {
		try {
			// TODO: Not sure if I like the idea of just waiting for an exception, see later
			get(classToken.getIdentifier());
			throw new RuntimeException(classToken, "Class already defined");
		} catch (RuntimeException ignored) {
			classes.put(classToken.getIdentifier().getName(), classToken);
		}
	}
}
