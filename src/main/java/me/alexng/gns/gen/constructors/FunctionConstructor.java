package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.*;

import java.util.ListIterator;

public class FunctionConstructor implements Constructor {

	@Override
	public boolean isLeftToRight() {
		return true;
	}

	@Override
	public boolean accepts(Token token) {
		return Keyword.FUNC.matches(token);
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keyword = Assembler.castTo(KeywordToken.class, tokens.next());
		tokens.remove();

		Token uncheckedIdentifier = tokens.next();
		IdentifierToken identifier = Assembler.castTo(IdentifierToken.class, uncheckedIdentifier);
		tokens.remove();

		ParametersToken parameters = ParametersConstructor.construct(tokens);

		BlockToken block = Assembler.castTo(BlockToken.class, tokens.next());
		tokens.remove();
		tokens.add(new FunctionToken(identifier, parameters, block, FileIndex.openClose(keyword, block)));
	}
}
