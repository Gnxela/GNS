package me.alexng.gns.gen.constructors;

import me.alexng.gns.InvalidTokenException;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ObjectConstructor implements Constructor {

	@Override
	public boolean isLeftToRight() {
		return true;
	}

	@Override
	public boolean accepts(Token token) {
		return token instanceof BlockToken;
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		BlockToken blockToken = Assembler.castTo(BlockToken.class, tokens.next());
		tokens.remove();
		List<Token> blockTokens = blockToken.getTokens();
		ListIterator<Token> blockTokensIterator = blockTokens.listIterator();
		if (blockTokens.get(0) instanceof EOLToken) {
			blockTokensIterator.next();
		}
		LinkedList<ObjectToken.ObjectEntry> objectEntries = new LinkedList<>();
		while (blockTokensIterator.hasNext()) {
			ObjectToken.ObjectEntry objectEntry = parseEntry(blockTokensIterator);
			objectEntries.add(objectEntry);
			Assembler.consumeAndCount(blockTokensIterator, EOLToken.class);
			if (blockTokensIterator.hasNext()) {
				Token token = blockTokensIterator.next();
				if (!(token instanceof CommaToken)) {
					throw new InvalidTokenException(token, CommaToken.class, token);
				}
			}
			Assembler.consumeAndCount(blockTokensIterator, EOLToken.class);
		}
		tokens.add(new ObjectToken(objectEntries, blockToken.getFileIndex()));
	}

	private ObjectToken.ObjectEntry parseEntry(ListIterator<Token> blockTokens) throws ParsingException {
		IdentifierToken identifierToken = Assembler.castTo(IdentifierToken.class, blockTokens.next());
		Assembler.castTo(ColonToken.class, blockTokens.next());
		Token value = blockTokens.next();
		return new ObjectToken.ObjectEntry(identifierToken, value);
	}
}
