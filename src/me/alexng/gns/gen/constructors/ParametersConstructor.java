package me.alexng.gns.gen.constructors;

import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.*;

import java.util.*;

public class ParametersConstructor implements Constructor {

    @Override
    public boolean accepts(Token token) {
        return token instanceof KeywordToken && ((KeywordToken) token).getKeyword() == Keyword.FUNC;
    }

    @Override
    public void construct(ListIterator<Token> tokens) throws ParsingException {
        Token functionKeyword = tokens.next(); // func token
        tokens.next(); // identifier (hopefully, checked in function constructor)

        Token expectedOpenBracket = tokens.next();
        if (!BracketToken.ROUND_OPEN.matches(expectedOpenBracket)) {
            throw new ParsingException(functionKeyword, "Missing parameters");
        }
        tokens.remove();

        LinkedList<Token> parameterTokens = Assembler.matchTokens(tokens, BracketToken.ROUND_OPEN, BracketToken.ROUND_CLOSED);
        checkFormat(parameterTokens);
        IdentifierToken[] parameters = grabIdentifiers(parameterTokens);
        checkCollision(parameters);

        // TODO: Get end index
        tokens.add(new ParametersToken(parameters, expectedOpenBracket.getStartIndex(), 0));
    }

    private void checkCollision(IdentifierToken[] parameters) throws ParsingException {
        Set<String> nameSet = new HashSet<>();
        for (IdentifierToken identifierToken : parameters) {
            if (!nameSet.add(identifierToken.getName())) {
                throw new ParsingException(identifierToken, "Identifier repeated");
            }
        }
    }

    private IdentifierToken[] grabIdentifiers(LinkedList<Token> parameterTokens) {
        List<IdentifierToken> identifiers = new LinkedList<>();
        boolean isExpectingComma = false;
        for (Token token : parameterTokens) {
            if (!isExpectingComma) {
                identifiers.add((IdentifierToken) token);
            }
            isExpectingComma = !isExpectingComma;
        }
        return identifiers.toArray(new IdentifierToken[0]);
    }

    private void checkFormat(LinkedList<Token> parameterTokens) throws ParsingException {
        boolean isExpectingComma = false;
        for (Token token : parameterTokens) {
            if (isExpectingComma) {
                if (!(token instanceof CommaToken)) {
                    throw new ParsingException(token, "Expected comma");
                }
            } else {
                if (!(token instanceof IdentifierToken)) {
                    throw new ParsingException(token, "Expected identifier");
                }
            }
            isExpectingComma = !isExpectingComma;
        }
    }
}
