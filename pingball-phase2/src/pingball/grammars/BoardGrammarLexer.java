// Generated from BoardGrammar.g4 by ANTLR 4.0

package pingball.grammars;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BoardGrammarLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__27=1, T__26=2, T__25=3, T__24=4, T__23=5, T__22=6, T__21=7, T__20=8, 
		T__19=9, T__18=10, T__17=11, T__16=12, T__15=13, T__14=14, T__13=15, T__12=16, 
		T__11=17, T__10=18, T__9=19, T__8=20, T__7=21, T__6=22, T__5=23, T__4=24, 
		T__3=25, T__2=26, T__1=27, T__0=28, WHITESPACE=29, COMMENT=30, EQUALS=31, 
		FLOAT=32, INTEGER=33, NAME=34, KEY=35;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'yVelocity'", "'name'", "'friction1'", "'gravity'", "'ball'", "'otherBoard'", 
		"'y'", "'fire'", "'triangleBumper'", "'action'", "'key'", "'squareBumper'", 
		"'friction2'", "'circleBumper'", "'keyup'", "'otherPortal'", "'board'", 
		"'xVelocity'", "'portal'", "'orientation'", "'height'", "'x'", "'absorber'", 
		"'trigger'", "'leftFlipper'", "'width'", "'keydown'", "'rightFlipper'", 
		"WHITESPACE", "COMMENT", "'='", "FLOAT", "INTEGER", "NAME", "KEY"
	};
	public static final String[] ruleNames = {
		"T__27", "T__26", "T__25", "T__24", "T__23", "T__22", "T__21", "T__20", 
		"T__19", "T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", 
		"T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", 
		"T__2", "T__1", "T__0", "WHITESPACE", "COMMENT", "EQUALS", "FLOAT", "INTEGER", 
		"NAME", "KEY"
	};



	    /**
	     * Specs:
	     *
	   	 * This class contains the grammar for the Board. It gives the grammar rules to read through a .pb file
	     * and generate a pingball board with the location and/or orientation of balls and gadgets given, 
	     * along with friction, gravity, and actions that trigger other actions. If mu, mu2, and/or gravity are not
	     * given, they default to .025, .025, and 25.0 respectively.
	     *
	     * The lexer rules generate tokens FLOAT and NAME that are used to retrieve the name, location,
	     * velocity, and/or orientation of gadgets and balls. FLOATs can be decimal values or INTEGER
	     * tokens, both of whose values are correctly determined by the generated listener.
	     * Other lexer rules include WHITESPACE and COMMENT, which are skipped. BOARDNAME, which starts
	     * the parsing of the board. EQUALS, which represents the '=' symbol to ensure correct parsing
	     * through whitespaces.
	     *
	     * The parsing rules generate the tree that the listener walks through, with tokens at the nodes
	     * of the generated tree. The tree starts at root and ends at EOF (end of file). root consists
	     * of filelines, which represent each line in the parsed file. In order, this consists of a single
	     * boardLine, followed by any number of ballLine, sqBumperLine, cirBumperLine, triBumperLine, 
	     * rtFlipLine, lftFlipLine, absorberLine, and fireLine, in that specific order. Each line defines
	     * the type of object - board, ball, or specific gadget.
	     */

	    /**
	     * Call this method to have the lexer or parser throw a RuntimeException if
	     * it encounters an error.
	     */
	    public void reportErrorsAsExceptions() {
	        addErrorListener(new ExceptionThrowingErrorListener());
	    }
	    
	    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
	        @Override
	        public void syntaxError(Recognizer<?, ?> recognizer,
	                Object offendingSymbol, int line, int charPositionInLine,
	                String msg, RecognitionException e) {
	            throw new RuntimeException(msg);
	        }
	    }


	public BoardGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BoardGrammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 28: WHITESPACE_action((RuleContext)_localctx, actionIndex); break;

		case 29: COMMENT_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WHITESPACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4%\u01f3\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36"+
		"\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\36\6\36\u0137\n\36\r\36\16\36\u0138\3\36\3\36\3"+
		"\37\3\37\7\37\u013f\n\37\f\37\16\37\u0142\13\37\3\37\3\37\3 \3 \3!\3!"+
		"\7!\u014a\n!\f!\16!\u014d\13!\3!\3!\6!\u0151\n!\r!\16!\u0152\3!\3!\7!"+
		"\u0157\n!\f!\16!\u015a\13!\3!\3!\6!\u015e\n!\r!\16!\u015f\3!\3!\6!\u0164"+
		"\n!\r!\16!\u0165\5!\u0168\n!\3\"\6\"\u016b\n\"\r\"\16\"\u016c\3#\3#\7"+
		"#\u0171\n#\f#\16#\u0174\13#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u01f2\n$\2%"+
		"\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27"+
		"\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27"+
		"\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67\35\19\36\1;\37\2= \3?!\1A\""+
		"\1C#\1E$\1G%\1\3\2\r\5\13\f\17\17\"\"\4\f\f\17\17\3\62;\3\62;\3\62;\3"+
		"\62;\3\62;\3\62;\5C\\aac|\6\62;C\\aac|\4\62;c|\u0213\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63"+
		"\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2"+
		"?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\3I\3\2\2\2\5S\3"+
		"\2\2\2\7X\3\2\2\2\tb\3\2\2\2\13j\3\2\2\2\ro\3\2\2\2\17z\3\2\2\2\21|\3"+
		"\2\2\2\23\u0081\3\2\2\2\25\u0090\3\2\2\2\27\u0097\3\2\2\2\31\u009b\3\2"+
		"\2\2\33\u00a8\3\2\2\2\35\u00b2\3\2\2\2\37\u00bf\3\2\2\2!\u00c5\3\2\2\2"+
		"#\u00d1\3\2\2\2%\u00d7\3\2\2\2\'\u00e1\3\2\2\2)\u00e8\3\2\2\2+\u00f4\3"+
		"\2\2\2-\u00fb\3\2\2\2/\u00fd\3\2\2\2\61\u0106\3\2\2\2\63\u010e\3\2\2\2"+
		"\65\u011a\3\2\2\2\67\u0120\3\2\2\29\u0128\3\2\2\2;\u0136\3\2\2\2=\u013c"+
		"\3\2\2\2?\u0145\3\2\2\2A\u0167\3\2\2\2C\u016a\3\2\2\2E\u016e\3\2\2\2G"+
		"\u01f1\3\2\2\2IJ\7{\2\2JK\7X\2\2KL\7g\2\2LM\7n\2\2MN\7q\2\2NO\7e\2\2O"+
		"P\7k\2\2PQ\7v\2\2QR\7{\2\2R\4\3\2\2\2ST\7p\2\2TU\7c\2\2UV\7o\2\2VW\7g"+
		"\2\2W\6\3\2\2\2XY\7h\2\2YZ\7t\2\2Z[\7k\2\2[\\\7e\2\2\\]\7v\2\2]^\7k\2"+
		"\2^_\7q\2\2_`\7p\2\2`a\7\63\2\2a\b\3\2\2\2bc\7i\2\2cd\7t\2\2de\7c\2\2"+
		"ef\7x\2\2fg\7k\2\2gh\7v\2\2hi\7{\2\2i\n\3\2\2\2jk\7d\2\2kl\7c\2\2lm\7"+
		"n\2\2mn\7n\2\2n\f\3\2\2\2op\7q\2\2pq\7v\2\2qr\7j\2\2rs\7g\2\2st\7t\2\2"+
		"tu\7D\2\2uv\7q\2\2vw\7c\2\2wx\7t\2\2xy\7f\2\2y\16\3\2\2\2z{\7{\2\2{\20"+
		"\3\2\2\2|}\7h\2\2}~\7k\2\2~\177\7t\2\2\177\u0080\7g\2\2\u0080\22\3\2\2"+
		"\2\u0081\u0082\7v\2\2\u0082\u0083\7t\2\2\u0083\u0084\7k\2\2\u0084\u0085"+
		"\7c\2\2\u0085\u0086\7p\2\2\u0086\u0087\7i\2\2\u0087\u0088\7n\2\2\u0088"+
		"\u0089\7g\2\2\u0089\u008a\7D\2\2\u008a\u008b\7w\2\2\u008b\u008c\7o\2\2"+
		"\u008c\u008d\7r\2\2\u008d\u008e\7g\2\2\u008e\u008f\7t\2\2\u008f\24\3\2"+
		"\2\2\u0090\u0091\7c\2\2\u0091\u0092\7e\2\2\u0092\u0093\7v\2\2\u0093\u0094"+
		"\7k\2\2\u0094\u0095\7q\2\2\u0095\u0096\7p\2\2\u0096\26\3\2\2\2\u0097\u0098"+
		"\7m\2\2\u0098\u0099\7g\2\2\u0099\u009a\7{\2\2\u009a\30\3\2\2\2\u009b\u009c"+
		"\7u\2\2\u009c\u009d\7s\2\2\u009d\u009e\7w\2\2\u009e\u009f\7c\2\2\u009f"+
		"\u00a0\7t\2\2\u00a0\u00a1\7g\2\2\u00a1\u00a2\7D\2\2\u00a2\u00a3\7w\2\2"+
		"\u00a3\u00a4\7o\2\2\u00a4\u00a5\7r\2\2\u00a5\u00a6\7g\2\2\u00a6\u00a7"+
		"\7t\2\2\u00a7\32\3\2\2\2\u00a8\u00a9\7h\2\2\u00a9\u00aa\7t\2\2\u00aa\u00ab"+
		"\7k\2\2\u00ab\u00ac\7e\2\2\u00ac\u00ad\7v\2\2\u00ad\u00ae\7k\2\2\u00ae"+
		"\u00af\7q\2\2\u00af\u00b0\7p\2\2\u00b0\u00b1\7\64\2\2\u00b1\34\3\2\2\2"+
		"\u00b2\u00b3\7e\2\2\u00b3\u00b4\7k\2\2\u00b4\u00b5\7t\2\2\u00b5\u00b6"+
		"\7e\2\2\u00b6\u00b7\7n\2\2\u00b7\u00b8\7g\2\2\u00b8\u00b9\7D\2\2\u00b9"+
		"\u00ba\7w\2\2\u00ba\u00bb\7o\2\2\u00bb\u00bc\7r\2\2\u00bc\u00bd\7g\2\2"+
		"\u00bd\u00be\7t\2\2\u00be\36\3\2\2\2\u00bf\u00c0\7m\2\2\u00c0\u00c1\7"+
		"g\2\2\u00c1\u00c2\7{\2\2\u00c2\u00c3\7w\2\2\u00c3\u00c4\7r\2\2\u00c4 "+
		"\3\2\2\2\u00c5\u00c6\7q\2\2\u00c6\u00c7\7v\2\2\u00c7\u00c8\7j\2\2\u00c8"+
		"\u00c9\7g\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb\7R\2\2\u00cb\u00cc\7q\2\2"+
		"\u00cc\u00cd\7t\2\2\u00cd\u00ce\7v\2\2\u00ce\u00cf\7c\2\2\u00cf\u00d0"+
		"\7n\2\2\u00d0\"\3\2\2\2\u00d1\u00d2\7d\2\2\u00d2\u00d3\7q\2\2\u00d3\u00d4"+
		"\7c\2\2\u00d4\u00d5\7t\2\2\u00d5\u00d6\7f\2\2\u00d6$\3\2\2\2\u00d7\u00d8"+
		"\7z\2\2\u00d8\u00d9\7X\2\2\u00d9\u00da\7g\2\2\u00da\u00db\7n\2\2\u00db"+
		"\u00dc\7q\2\2\u00dc\u00dd\7e\2\2\u00dd\u00de\7k\2\2\u00de\u00df\7v\2\2"+
		"\u00df\u00e0\7{\2\2\u00e0&\3\2\2\2\u00e1\u00e2\7r\2\2\u00e2\u00e3\7q\2"+
		"\2\u00e3\u00e4\7t\2\2\u00e4\u00e5\7v\2\2\u00e5\u00e6\7c\2\2\u00e6\u00e7"+
		"\7n\2\2\u00e7(\3\2\2\2\u00e8\u00e9\7q\2\2\u00e9\u00ea\7t\2\2\u00ea\u00eb"+
		"\7k\2\2\u00eb\u00ec\7g\2\2\u00ec\u00ed\7p\2\2\u00ed\u00ee\7v\2\2\u00ee"+
		"\u00ef\7c\2\2\u00ef\u00f0\7v\2\2\u00f0\u00f1\7k\2\2\u00f1\u00f2\7q\2\2"+
		"\u00f2\u00f3\7p\2\2\u00f3*\3\2\2\2\u00f4\u00f5\7j\2\2\u00f5\u00f6\7g\2"+
		"\2\u00f6\u00f7\7k\2\2\u00f7\u00f8\7i\2\2\u00f8\u00f9\7j\2\2\u00f9\u00fa"+
		"\7v\2\2\u00fa,\3\2\2\2\u00fb\u00fc\7z\2\2\u00fc.\3\2\2\2\u00fd\u00fe\7"+
		"c\2\2\u00fe\u00ff\7d\2\2\u00ff\u0100\7u\2\2\u0100\u0101\7q\2\2\u0101\u0102"+
		"\7t\2\2\u0102\u0103\7d\2\2\u0103\u0104\7g\2\2\u0104\u0105\7t\2\2\u0105"+
		"\60\3\2\2\2\u0106\u0107\7v\2\2\u0107\u0108\7t\2\2\u0108\u0109\7k\2\2\u0109"+
		"\u010a\7i\2\2\u010a\u010b\7i\2\2\u010b\u010c\7g\2\2\u010c\u010d\7t\2\2"+
		"\u010d\62\3\2\2\2\u010e\u010f\7n\2\2\u010f\u0110\7g\2\2\u0110\u0111\7"+
		"h\2\2\u0111\u0112\7v\2\2\u0112\u0113\7H\2\2\u0113\u0114\7n\2\2\u0114\u0115"+
		"\7k\2\2\u0115\u0116\7r\2\2\u0116\u0117\7r\2\2\u0117\u0118\7g\2\2\u0118"+
		"\u0119\7t\2\2\u0119\64\3\2\2\2\u011a\u011b\7y\2\2\u011b\u011c\7k\2\2\u011c"+
		"\u011d\7f\2\2\u011d\u011e\7v\2\2\u011e\u011f\7j\2\2\u011f\66\3\2\2\2\u0120"+
		"\u0121\7m\2\2\u0121\u0122\7g\2\2\u0122\u0123\7{\2\2\u0123\u0124\7f\2\2"+
		"\u0124\u0125\7q\2\2\u0125\u0126\7y\2\2\u0126\u0127\7p\2\2\u01278\3\2\2"+
		"\2\u0128\u0129\7t\2\2\u0129\u012a\7k\2\2\u012a\u012b\7i\2\2\u012b\u012c"+
		"\7j\2\2\u012c\u012d\7v\2\2\u012d\u012e\7H\2\2\u012e\u012f\7n\2\2\u012f"+
		"\u0130\7k\2\2\u0130\u0131\7r\2\2\u0131\u0132\7r\2\2\u0132\u0133\7g\2\2"+
		"\u0133\u0134\7t\2\2\u0134:\3\2\2\2\u0135\u0137\t\2\2\2\u0136\u0135\3\2"+
		"\2\2\u0137\u0138\3\2\2\2\u0138\u0136\3\2\2\2\u0138\u0139\3\2\2\2\u0139"+
		"\u013a\3\2\2\2\u013a\u013b\b\36\2\2\u013b<\3\2\2\2\u013c\u0140\7%\2\2"+
		"\u013d\u013f\n\3\2\2\u013e\u013d\3\2\2\2\u013f\u0142\3\2\2\2\u0140\u013e"+
		"\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0143\3\2\2\2\u0142\u0140\3\2\2\2\u0143"+
		"\u0144\b\37\3\2\u0144>\3\2\2\2\u0145\u0146\7?\2\2\u0146@\3\2\2\2\u0147"+
		"\u0168\5C\"\2\u0148\u014a\t\4\2\2\u0149\u0148\3\2\2\2\u014a\u014d\3\2"+
		"\2\2\u014b\u0149\3\2\2\2\u014b\u014c\3\2\2\2\u014c\u014e\3\2\2\2\u014d"+
		"\u014b\3\2\2\2\u014e\u0150\7\60\2\2\u014f\u0151\t\5\2\2\u0150\u014f\3"+
		"\2\2\2\u0151\u0152\3\2\2\2\u0152\u0150\3\2\2\2\u0152\u0153\3\2\2\2\u0153"+
		"\u0168\3\2\2\2\u0154\u0158\7/\2\2\u0155\u0157\t\6\2\2\u0156\u0155\3\2"+
		"\2\2\u0157\u015a\3\2\2\2\u0158\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159"+
		"\u015b\3\2\2\2\u015a\u0158\3\2\2\2\u015b\u015d\7\60\2\2\u015c\u015e\t"+
		"\7\2\2\u015d\u015c\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u015d\3\2\2\2\u015f"+
		"\u0160\3\2\2\2\u0160\u0168\3\2\2\2\u0161\u0163\7/\2\2\u0162\u0164\t\b"+
		"\2\2\u0163\u0162\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0163\3\2\2\2\u0165"+
		"\u0166\3\2\2\2\u0166\u0168\3\2\2\2\u0167\u0147\3\2\2\2\u0167\u014b\3\2"+
		"\2\2\u0167\u0154\3\2\2\2\u0167\u0161\3\2\2\2\u0168B\3\2\2\2\u0169\u016b"+
		"\t\t\2\2\u016a\u0169\3\2\2\2\u016b\u016c\3\2\2\2\u016c\u016a\3\2\2\2\u016c"+
		"\u016d\3\2\2\2\u016dD\3\2\2\2\u016e\u0172\t\n\2\2\u016f\u0171\t\13\2\2"+
		"\u0170\u016f\3\2\2\2\u0171\u0174\3\2\2\2\u0172\u0170\3\2\2\2\u0172\u0173"+
		"\3\2\2\2\u0173F\3\2\2\2\u0174\u0172\3\2\2\2\u0175\u01f2\t\f\2\2\u0176"+
		"\u0177\7u\2\2\u0177\u0178\7j\2\2\u0178\u0179\7k\2\2\u0179\u017a\7h\2\2"+
		"\u017a\u01f2\7v\2\2\u017b\u017c\7e\2\2\u017c\u017d\7v\2\2\u017d\u017e"+
		"\7t\2\2\u017e\u01f2\7n\2\2\u017f\u0180\7c\2\2\u0180\u0181\7n\2\2\u0181"+
		"\u01f2\7v\2\2\u0182\u0183\7o\2\2\u0183\u0184\7g\2\2\u0184\u0185\7v\2\2"+
		"\u0185\u01f2\7c\2\2\u0186\u0187\7u\2\2\u0187\u0188\7r\2\2\u0188\u0189"+
		"\7c\2\2\u0189\u018a\7e\2\2\u018a\u01f2\7g\2\2\u018b\u018c\7n\2\2\u018c"+
		"\u018d\7g\2\2\u018d\u018e\7h\2\2\u018e\u01f2\7v\2\2\u018f\u0190\7t\2\2"+
		"\u0190\u0191\7k\2\2\u0191\u0192\7i\2\2\u0192\u0193\7j\2\2\u0193\u01f2"+
		"\7v\2\2\u0194\u0195\7w\2\2\u0195\u01f2\7r\2\2\u0196\u0197\7f\2\2\u0197"+
		"\u0198\7q\2\2\u0198\u0199\7y\2\2\u0199\u01f2\7p\2\2\u019a\u019b\7o\2\2"+
		"\u019b\u019c\7k\2\2\u019c\u019d\7p\2\2\u019d\u019e\7w\2\2\u019e\u01f2"+
		"\7u\2\2\u019f\u01a0\7g\2\2\u01a0\u01a1\7s\2\2\u01a1\u01a2\7w\2\2\u01a2"+
		"\u01a3\7c\2\2\u01a3\u01a4\7n\2\2\u01a4\u01f2\7u\2\2\u01a5\u01a6\7d\2\2"+
		"\u01a6\u01a7\7c\2\2\u01a7\u01a8\7e\2\2\u01a8\u01a9\7m\2\2\u01a9\u01aa"+
		"\7u\2\2\u01aa\u01ab\7r\2\2\u01ab\u01ac\7c\2\2\u01ac\u01ad\7e\2\2\u01ad"+
		"\u01f2\7g\2\2\u01ae\u01af\7q\2\2\u01af\u01b0\7r\2\2\u01b0\u01b1\7g\2\2"+
		"\u01b1\u01b2\7p\2\2\u01b2\u01b3\7d\2\2\u01b3\u01b4\7t\2\2\u01b4\u01b5"+
		"\7c\2\2\u01b5\u01b6\7e\2\2\u01b6\u01b7\7m\2\2\u01b7\u01b8\7g\2\2\u01b8"+
		"\u01f2\7v\2\2\u01b9\u01ba\7e\2\2\u01ba\u01bb\7n\2\2\u01bb\u01bc\7q\2\2"+
		"\u01bc\u01bd\7u\2\2\u01bd\u01be\7g\2\2\u01be\u01bf\7d\2\2\u01bf\u01c0"+
		"\7t\2\2\u01c0\u01c1\7c\2\2\u01c1\u01c2\7e\2\2\u01c2\u01c3\7m\2\2\u01c3"+
		"\u01c4\7g\2\2\u01c4\u01f2\7v\2\2\u01c5\u01c6\7d\2\2\u01c6\u01c7\7c\2\2"+
		"\u01c7\u01c8\7e\2\2\u01c8\u01c9\7m\2\2\u01c9\u01ca\7u\2\2\u01ca\u01cb"+
		"\7n\2\2\u01cb\u01cc\7c\2\2\u01cc\u01cd\7u\2\2\u01cd\u01f2\7j\2\2\u01ce"+
		"\u01cf\7u\2\2\u01cf\u01d0\7g\2\2\u01d0\u01d1\7o\2\2\u01d1\u01d2\7k\2\2"+
		"\u01d2\u01d3\7e\2\2\u01d3\u01d4\7q\2\2\u01d4\u01d5\7n\2\2\u01d5\u01d6"+
		"\7q\2\2\u01d6\u01f2\7p\2\2\u01d7\u01d8\7s\2\2\u01d8\u01d9\7w\2\2\u01d9"+
		"\u01da\7q\2\2\u01da\u01db\7v\2\2\u01db\u01f2\7g\2\2\u01dc\u01dd\7g\2\2"+
		"\u01dd\u01de\7p\2\2\u01de\u01df\7v\2\2\u01df\u01e0\7g\2\2\u01e0\u01f2"+
		"\7t\2\2\u01e1\u01e2\7e\2\2\u01e2\u01e3\7q\2\2\u01e3\u01e4\7o\2\2\u01e4"+
		"\u01e5\7o\2\2\u01e5\u01f2\7c\2\2\u01e6\u01e7\7r\2\2\u01e7\u01e8\7g\2\2"+
		"\u01e8\u01e9\7t\2\2\u01e9\u01ea\7k\2\2\u01ea\u01eb\7q\2\2\u01eb\u01f2"+
		"\7f\2\2\u01ec\u01ed\7u\2\2\u01ed\u01ee\7n\2\2\u01ee\u01ef\7c\2\2\u01ef"+
		"\u01f0\7u\2\2\u01f0\u01f2\7j\2\2\u01f1\u0175\3\2\2\2\u01f1\u0176\3\2\2"+
		"\2\u01f1\u017b\3\2\2\2\u01f1\u017f\3\2\2\2\u01f1\u0182\3\2\2\2\u01f1\u0186"+
		"\3\2\2\2\u01f1\u018b\3\2\2\2\u01f1\u018f\3\2\2\2\u01f1\u0194\3\2\2\2\u01f1"+
		"\u0196\3\2\2\2\u01f1\u019a\3\2\2\2\u01f1\u019f\3\2\2\2\u01f1\u01a5\3\2"+
		"\2\2\u01f1\u01ae\3\2\2\2\u01f1\u01b9\3\2\2\2\u01f1\u01c5\3\2\2\2\u01f1"+
		"\u01ce\3\2\2\2\u01f1\u01d7\3\2\2\2\u01f1\u01dc\3\2\2\2\u01f1\u01e1\3\2"+
		"\2\2\u01f1\u01e6\3\2\2\2\u01f1\u01ec\3\2\2\2\u01f2H\3\2\2\2\16\2\u0138"+
		"\u0140\u014b\u0152\u0158\u015f\u0165\u0167\u016c\u0172\u01f1";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}