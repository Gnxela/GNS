package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.ObjectValue;
import me.alexng.gns.tokens.value.Value;

import java.util.List;

public class ObjectToken extends Token {

	private List<ObjectEntry> entries;

	public ObjectToken(List<ObjectEntry> entries, FileIndex fileIndex) {
		super(fileIndex);
		this.entries = entries;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return create(scope);
	}

	public ObjectValue create(Scope localScope) throws RuntimeException {
		Scope objectScope = Scope.createObjectScope(localScope.getObjectOrGlobalScope());
		for (ObjectEntry entry : entries) {
			// TODO: Check this once when ObjectEntry is initialised.
			if (entry.value instanceof FunctionToken) {
				objectScope.set(entry.identifier, entry.value);
			} else {
				objectScope.set(entry.identifier, entry.value.execute(localScope));
			}
		}
		return new ObjectValue(objectScope, getFileIndex());
	}

	@Override
	public String toString() {
		return "<ObjectToken >";
	}

	public static class ObjectEntry {

		// TODO: Make these final
		private IdentifierToken identifier;
		private Token value;

		public ObjectEntry(IdentifierToken identifier, Token value) {
			this.identifier = identifier;
			this.value = value;
		}

		public IdentifierToken getIdentifier() {
			return identifier;
		}

		public Token getValue() {
			return value;
		}
	}
}
