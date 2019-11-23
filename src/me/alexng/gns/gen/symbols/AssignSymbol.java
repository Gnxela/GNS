package me.alexng.gns.gen.symbols;

import me.alexng.gns.gen.GenericSymbolSeeker;
import me.alexng.gns.gen.LeftConsumer;
import me.alexng.gns.gen.RightConsumer;
import me.alexng.gns.gen.SymbolConstructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.AssignToken;
import me.alexng.gns.lexer.tokens.IdentifierToken;
import me.alexng.gns.lexer.tokens.NumberToken;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class AssignSymbol {

	private static Class<? extends Token>[] acceptedIdentifiers = (Class<? extends Token>[]) new Class[]{IdentifierToken.class};
	private static Class<? extends Token>[] acceptedValues = (Class<? extends Token>[]) new Class[]{NumberToken.class, IdentifierToken.class};

	public static class Constructor extends GenericSymbolSeeker implements SymbolConstructor {

		private static final LeftConsumer LEFT_CONSUMER = new LeftConsumer(acceptedIdentifiers);
		private static final RightConsumer RIGHT_CONSUMER = new RightConsumer(acceptedValues);

		public Constructor() {
			super(AssignToken.class);
		}

		@Override
		public void construct(ListIterator<Token> tokens) {
			List<Token> left = LEFT_CONSUMER.consume(tokens);
			List<Token> right = RIGHT_CONSUMER.consume(tokens);
			System.out.println(left.toString() + ":" + right.toString());
		}
	}
}
