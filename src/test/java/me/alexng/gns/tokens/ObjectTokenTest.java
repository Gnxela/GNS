package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.operators.AccessToken;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.ObjectValue;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectTokenTest {

	@Test
	public void testCreate() throws RuntimeException {
		List<ObjectToken.ObjectEntry> entries = new LinkedList<>();
		IdentifierToken identifier1 = new IdentifierToken("i", FileIndex.INTERNAL_INDEX);
		IdentifierToken identifier1value = new IdentifierToken("j", FileIndex.INTERNAL_INDEX);
		entries.add(new ObjectToken.ObjectEntry(identifier1, identifier1value));
		ObjectToken objectToken = new ObjectToken(entries, FileIndex.INTERNAL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		globalScope.set(identifier1value, new NumberValue(7, FileIndex.INTERNAL_INDEX));
		ObjectValue objectValue = objectToken.create(globalScope);
		assertEquals(objectValue.getObjectScope().getValue(identifier1).getJavaValue(), 7);
	}

	@Test
	public void testCreate_valueAndIdentifierSame() throws RuntimeException {
		List<ObjectToken.ObjectEntry> entries = new LinkedList<>();
		IdentifierToken identifier1 = new IdentifierToken("i", FileIndex.INTERNAL_INDEX);
		IdentifierToken identifier1value = new IdentifierToken("i", FileIndex.INTERNAL_INDEX);
		entries.add(new ObjectToken.ObjectEntry(identifier1, identifier1value));
		ObjectToken objectToken = new ObjectToken(entries, FileIndex.INTERNAL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		globalScope.set(identifier1value, new NumberValue(7, FileIndex.INTERNAL_INDEX));
		ObjectValue objectValue = objectToken.create(globalScope);
		assertEquals(objectValue.getObjectScope().getValue(identifier1).getJavaValue(), 7);
	}

	@Test
	public void testCreate_paramFromOtherObjectScope() throws RuntimeException, ParsingException {
		List<ObjectToken.ObjectEntry> entries = new LinkedList<>();
		IdentifierToken identifier1 = new IdentifierToken("i", FileIndex.INTERNAL_INDEX);
		entries.add(new ObjectToken.ObjectEntry(identifier1, new NumberValue(7, FileIndex.INTERNAL_INDEX)));
		ObjectToken objectToken = new ObjectToken(entries, FileIndex.INTERNAL_INDEX);
		Scope globalScope = Scope.createGlobalScope(null);
		ObjectValue objectValue = objectToken.create(globalScope);

		IdentifierToken objectIdentifier = new IdentifierToken("obj", FileIndex.INTERNAL_INDEX);
		globalScope.set(objectIdentifier, objectValue);
		entries.clear();
		AccessToken accessToken = new AccessToken(FileIndex.INTERNAL_INDEX);
		accessToken.bind(objectIdentifier, identifier1);
		entries.add(new ObjectToken.ObjectEntry(identifier1, accessToken));

		ObjectValue otherObjectValue = objectToken.create(globalScope);
		assertEquals(otherObjectValue.getObjectScope().getValue(identifier1).getJavaValue(), 7);
	}
}
