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
		// TODO: We need to find either the lowest global or object scope and set it as a parent
		Scope objectScope = Scope.createObjectScope(scope.getGlobalScope());
		for (ObjectEntry entry : entries) {
			if (entry.value instanceof FunctionToken) {
				objectScope.set(entry.identifier, entry.value);
			} else {
				objectScope.set(entry.identifier, entry.value.execute(objectScope));
			}
		}
		return new ObjectValue(objectScope, getFileIndex());
	}

	@Override
	public String toString() {
		return "<ObjectToken >";
	}

	public static class ObjectEntry {

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
