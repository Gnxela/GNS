package me.alexng.gns.lexer.generators;

import me.alexng.gns.GNSException;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.BracketToken;
import me.alexng.gns.tokens.Token;

public class BracketGenerator implements TokenGenerator {

    private static final Character[] BRACKETS = new Character[]{'(', ')', '{', '}', '[', ']'};

    @Override
    public int accepts(String input, int index) {
        char firstCharacter = input.charAt(index);
        for (Character bracket : BRACKETS) {
            if (firstCharacter == bracket) {
                return index + 1;
            }
        }
        return index;
    }

    @Override
    public Token generate(String input, int startIndex, int endIndex) throws GNSException {
        switch (input.charAt(startIndex)) {
            case '(':
                return new BracketToken(BracketToken.Bracket.ROUND, BracketToken.Type.OPEN, startIndex, endIndex);
            case ')':
                return new BracketToken(BracketToken.Bracket.ROUND, BracketToken.Type.CLOSED, startIndex, endIndex);
            case '[':
                return new BracketToken(BracketToken.Bracket.SQUARE, BracketToken.Type.OPEN, startIndex, endIndex);
            case ']':
                return new BracketToken(BracketToken.Bracket.SQUARE, BracketToken.Type.CLOSED, startIndex, endIndex);
            case '{':
                return new BracketToken(BracketToken.Bracket.CURLY, BracketToken.Type.OPEN, startIndex, endIndex);
            case '}':
                return new BracketToken(BracketToken.Bracket.CURLY, BracketToken.Type.CLOSED, startIndex, endIndex);
            default:
                throw new GNSException(startIndex, endIndex, "Bracket generation failed");
        }
    }
}