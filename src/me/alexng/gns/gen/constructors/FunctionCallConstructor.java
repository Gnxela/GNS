package me.alexng.gns.gen.constructors;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.BracketToken;
import me.alexng.gns.lexer.tokens.CommaToken;
import me.alexng.gns.lexer.tokens.FunctionCallToken;
import me.alexng.gns.lexer.tokens.IdentifierToken;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class FunctionCallConstructor implements Constructor {

    @Override
    public boolean accepts(Token token) {
        return token instanceof IdentifierToken;
    }

    @Override
    public void construct(ListIterator<Token> tokens) throws ParsingException {
        IdentifierToken functionIdentifier = (IdentifierToken) tokens.next();

        Token expectedOpenBracket = tokens.next();
        if (!BracketToken.ROUND_OPEN.matches(expectedOpenBracket)) {
            tokens.previous();
            return;
        }
        // TODO: Anything but this
        tokens.previous();
        tokens.previous();
        tokens.remove(); // Remove the identifier token

        LinkedList<Token> parameterTokens = Assembler.matchTokens(tokens, BracketToken.ROUND_OPEN, BracketToken.ROUND_CLOSED);
        BracketToken openBracket = (BracketToken) parameterTokens.removeFirst();
        BracketToken closeBracket = (BracketToken) parameterTokens.removeLast();
        checkFormat(parameterTokens);
        Token[] parameters = grabValues(parameterTokens);

        tokens.add(new FunctionCallToken(functionIdentifier, parameters, openBracket.getStartIndex(), closeBracket.getEndIndex()));
    }

    private Token[] grabValues(LinkedList<Token> parameterTokens) {
        List<Token> identifiers = new LinkedList<>();
        boolean isExpectingComma = false;
        for (Token token : parameterTokens) {
            if (!isExpectingComma) {
                identifiers.add(token);
            }
            isExpectingComma = !isExpectingComma;
        }
        return identifiers.toArray(new Token[0]);
    }

    private void checkFormat(LinkedList<Token> parameterTokens) throws ParsingException {
        boolean isExpectingComma = false;
        for (Token token : parameterTokens) {
            if (isExpectingComma) {
                if (!(token instanceof CommaToken)) {
                    throw new ParsingException(token, "Expected comma");
                }
            }
            isExpectingComma = !isExpectingComma;
        }
    }
}
