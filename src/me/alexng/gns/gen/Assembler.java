package me.alexng.gns.gen;

import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.BlockToken;
import me.alexng.gns.lexer.tokens.BracketToken;
import me.alexng.gns.lexer.tokens.IfToken;
import me.alexng.gns.lexer.tokens.KeywordToken;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Takes tokenized input and assembles "secondary" tokens; tokens that are not directly parsed from the input text
 * but instead inferred from a sequence of tokens
 */
public class Assembler {

	public static void assemble(LinkedList<Token> tokens) throws ParsingException {
		ListIterator<Token> iterator = tokens.listIterator();
		while (iterator.hasNext()) {
			Token token = iterator.next();
			if (token.equals(BracketToken.CURLEY_OPEN)) {
				iterator.previous();
				assembleBlock(iterator);
			}
		}
		iterator = tokens.listIterator();
		while (iterator.hasNext()) {
			Token token = iterator.next();
			if (token instanceof KeywordToken && ((KeywordToken) token).getKeyword() == Keyword.IF) {
				iterator.previous();
				assembleIfStatement(iterator);
			}
		}
	}

	/**
	 * @param tokens Requires the initial open curley bracket to be the the next token in the iterator.
	 */
	private static void assembleBlock(ListIterator<Token> tokens) throws ParsingException {
		BracketToken openBracket = (BracketToken) tokens.next();
		tokens.remove();

		LinkedList<Token> blockTokens = matchTokens(tokens, BracketToken.CURLEY_OPEN, BracketToken.CURLEY_CLOSED);
		// TODO: We need to run the assembler on these newly created blocks as well.
		// TODO: Fill in start and end index
		tokens.add(new BlockToken(blockTokens, openBracket.getStartIndex(), 0));
	}

	/**
	 * @param tokens Requires the initial if keyword to be the the next token in the iterator.
	 */
	private static void assembleIfStatement(ListIterator<Token> tokens) throws ParsingException {
		KeywordToken keyword = (KeywordToken) tokens.next();
		tokens.remove();

		if (!tokens.next().equals(BracketToken.ROUND_OPEN)) {
			// TODO: Better exception
			throw new ParsingException(0, "Expected open bracket");
		}
		tokens.remove();

		LinkedList<Token> condition = matchTokens(tokens, BracketToken.ROUND_OPEN, BracketToken.ROUND_CLOSED);

		Token expectedBlock = tokens.next();
		if (!(expectedBlock instanceof BlockToken)) {
			// TODO: Better exception
			throw new ParsingException(0, "Expected block");
		}
		tokens.remove();

		BlockToken block = (BlockToken) expectedBlock;
		// TODO: Fill in start and end index
		tokens.add(new IfToken(condition, block, keyword.getStartIndex(), 0));
	}

	/**
	 * Collects all tokens in between two tokens, matching opening and closing tokens to each other.
	 * @return A list of tokens in between the matched tokens. Not including the open and close tokens (middle open and close tokens are included)
	 * @throws ParsingException
	 */
	private static LinkedList<Token> matchTokens(ListIterator<Token> tokens, Token increment, Token decrement) throws ParsingException {
		int depth = 1;
		LinkedList<Token> bucket = new LinkedList<>();
		while (tokens.hasNext()) {
			Token token = tokens.next();
			tokens.remove();
			if (token.equals(increment)) {
				depth++;
			} else if (token.equals(decrement)) {
				if (depth == 1) {
					return bucket;
				} else {
					depth--;
				}
			}
			bucket.add(token);
		}
		// TODO: Better exception
		throw new ParsingException(0, "Matching not found");
	}
}
