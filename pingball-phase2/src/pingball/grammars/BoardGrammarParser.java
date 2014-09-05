// Generated from BoardGrammar.g4 by ANTLR 4.0

package pingball.grammars;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BoardGrammarParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__27=1, T__26=2, T__25=3, T__24=4, T__23=5, T__22=6, T__21=7, T__20=8, 
		T__19=9, T__18=10, T__17=11, T__16=12, T__15=13, T__14=14, T__13=15, T__12=16, 
		T__11=17, T__10=18, T__9=19, T__8=20, T__7=21, T__6=22, T__5=23, T__4=24, 
		T__3=25, T__2=26, T__1=27, T__0=28, WHITESPACE=29, COMMENT=30, EQUALS=31, 
		FLOAT=32, INTEGER=33, NAME=34, KEY=35;
	public static final String[] tokenNames = {
		"<INVALID>", "'yVelocity'", "'name'", "'friction1'", "'gravity'", "'ball'", 
		"'otherBoard'", "'y'", "'fire'", "'triangleBumper'", "'action'", "'key'", 
		"'squareBumper'", "'friction2'", "'circleBumper'", "'keyup'", "'otherPortal'", 
		"'board'", "'xVelocity'", "'portal'", "'orientation'", "'height'", "'x'", 
		"'absorber'", "'trigger'", "'leftFlipper'", "'width'", "'keydown'", "'rightFlipper'", 
		"WHITESPACE", "COMMENT", "'='", "FLOAT", "INTEGER", "NAME", "KEY"
	};
	public static final int
		RULE_root = 0, RULE_fileLines = 1, RULE_boardLine = 2, RULE_boardName = 3, 
		RULE_boardGravity = 4, RULE_boardFric1 = 5, RULE_boardFric2 = 6, RULE_ballLine = 7, 
		RULE_sqBumperLine = 8, RULE_cirBumperLine = 9, RULE_triBumperLine = 10, 
		RULE_rtFlipLine = 11, RULE_lftFlipLine = 12, RULE_absorberLine = 13, RULE_portalNoBoardLine = 14, 
		RULE_portalWithBoardLine = 15, RULE_fireLine = 16, RULE_keyDownLine = 17, 
		RULE_keyUpLine = 18, RULE_keyDown2Line = 19, RULE_keyUp2Line = 20;
	public static final String[] ruleNames = {
		"root", "fileLines", "boardLine", "boardName", "boardGravity", "boardFric1", 
		"boardFric2", "ballLine", "sqBumperLine", "cirBumperLine", "triBumperLine", 
		"rtFlipLine", "lftFlipLine", "absorberLine", "portalNoBoardLine", "portalWithBoardLine", 
		"fireLine", "keyDownLine", "keyUpLine", "keyDown2Line", "keyUp2Line"
	};

	@Override
	public String getGrammarFileName() { return "BoardGrammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }



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

	public BoardGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RootContext extends ParserRuleContext {
		public FileLinesContext fileLines() {
			return getRuleContext(FileLinesContext.class,0);
		}
		public TerminalNode EOF() { return getToken(BoardGrammarParser.EOF, 0); }
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitRoot(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42); fileLines();
			setState(43); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FileLinesContext extends ParserRuleContext {
		public KeyDownLineContext keyDownLine(int i) {
			return getRuleContext(KeyDownLineContext.class,i);
		}
		public CirBumperLineContext cirBumperLine(int i) {
			return getRuleContext(CirBumperLineContext.class,i);
		}
		public List<TriBumperLineContext> triBumperLine() {
			return getRuleContexts(TriBumperLineContext.class);
		}
		public AbsorberLineContext absorberLine(int i) {
			return getRuleContext(AbsorberLineContext.class,i);
		}
		public List<PortalWithBoardLineContext> portalWithBoardLine() {
			return getRuleContexts(PortalWithBoardLineContext.class);
		}
		public List<SqBumperLineContext> sqBumperLine() {
			return getRuleContexts(SqBumperLineContext.class);
		}
		public List<FireLineContext> fireLine() {
			return getRuleContexts(FireLineContext.class);
		}
		public PortalNoBoardLineContext portalNoBoardLine(int i) {
			return getRuleContext(PortalNoBoardLineContext.class,i);
		}
		public List<KeyUpLineContext> keyUpLine() {
			return getRuleContexts(KeyUpLineContext.class);
		}
		public List<KeyUp2LineContext> keyUp2Line() {
			return getRuleContexts(KeyUp2LineContext.class);
		}
		public SqBumperLineContext sqBumperLine(int i) {
			return getRuleContext(SqBumperLineContext.class,i);
		}
		public List<RtFlipLineContext> rtFlipLine() {
			return getRuleContexts(RtFlipLineContext.class);
		}
		public KeyDown2LineContext keyDown2Line(int i) {
			return getRuleContext(KeyDown2LineContext.class,i);
		}
		public List<KeyDown2LineContext> keyDown2Line() {
			return getRuleContexts(KeyDown2LineContext.class);
		}
		public List<CirBumperLineContext> cirBumperLine() {
			return getRuleContexts(CirBumperLineContext.class);
		}
		public List<BallLineContext> ballLine() {
			return getRuleContexts(BallLineContext.class);
		}
		public RtFlipLineContext rtFlipLine(int i) {
			return getRuleContext(RtFlipLineContext.class,i);
		}
		public BoardLineContext boardLine() {
			return getRuleContext(BoardLineContext.class,0);
		}
		public List<KeyDownLineContext> keyDownLine() {
			return getRuleContexts(KeyDownLineContext.class);
		}
		public KeyUpLineContext keyUpLine(int i) {
			return getRuleContext(KeyUpLineContext.class,i);
		}
		public LftFlipLineContext lftFlipLine(int i) {
			return getRuleContext(LftFlipLineContext.class,i);
		}
		public PortalWithBoardLineContext portalWithBoardLine(int i) {
			return getRuleContext(PortalWithBoardLineContext.class,i);
		}
		public List<LftFlipLineContext> lftFlipLine() {
			return getRuleContexts(LftFlipLineContext.class);
		}
		public List<AbsorberLineContext> absorberLine() {
			return getRuleContexts(AbsorberLineContext.class);
		}
		public KeyUp2LineContext keyUp2Line(int i) {
			return getRuleContext(KeyUp2LineContext.class,i);
		}
		public TriBumperLineContext triBumperLine(int i) {
			return getRuleContext(TriBumperLineContext.class,i);
		}
		public FireLineContext fireLine(int i) {
			return getRuleContext(FireLineContext.class,i);
		}
		public List<PortalNoBoardLineContext> portalNoBoardLine() {
			return getRuleContexts(PortalNoBoardLineContext.class);
		}
		public BallLineContext ballLine(int i) {
			return getRuleContext(BallLineContext.class,i);
		}
		public FileLinesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileLines; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterFileLines(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitFileLines(this);
		}
	}

	public final FileLinesContext fileLines() throws RecognitionException {
		FileLinesContext _localctx = new FileLinesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_fileLines);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45); boardLine();
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 8) | (1L << 9) | (1L << 12) | (1L << 14) | (1L << 15) | (1L << 19) | (1L << 23) | (1L << 25) | (1L << 27) | (1L << 28))) != 0)) {
				{
				setState(60);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(46); ballLine();
					}
					break;

				case 2:
					{
					setState(47); sqBumperLine();
					}
					break;

				case 3:
					{
					setState(48); cirBumperLine();
					}
					break;

				case 4:
					{
					setState(49); triBumperLine();
					}
					break;

				case 5:
					{
					setState(50); rtFlipLine();
					}
					break;

				case 6:
					{
					setState(51); lftFlipLine();
					}
					break;

				case 7:
					{
					setState(52); absorberLine();
					}
					break;

				case 8:
					{
					setState(53); fireLine();
					}
					break;

				case 9:
					{
					setState(54); portalNoBoardLine();
					}
					break;

				case 10:
					{
					setState(55); portalWithBoardLine();
					}
					break;

				case 11:
					{
					setState(56); keyUpLine();
					}
					break;

				case 12:
					{
					setState(57); keyDownLine();
					}
					break;

				case 13:
					{
					setState(58); keyUp2Line();
					}
					break;

				case 14:
					{
					setState(59); keyDown2Line();
					}
					break;
				}
				}
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoardLineContext extends ParserRuleContext {
		public List<BoardFric2Context> boardFric2() {
			return getRuleContexts(BoardFric2Context.class);
		}
		public List<BoardFric1Context> boardFric1() {
			return getRuleContexts(BoardFric1Context.class);
		}
		public BoardGravityContext boardGravity(int i) {
			return getRuleContext(BoardGravityContext.class,i);
		}
		public BoardFric1Context boardFric1(int i) {
			return getRuleContext(BoardFric1Context.class,i);
		}
		public BoardFric2Context boardFric2(int i) {
			return getRuleContext(BoardFric2Context.class,i);
		}
		public BoardNameContext boardName(int i) {
			return getRuleContext(BoardNameContext.class,i);
		}
		public List<BoardNameContext> boardName() {
			return getRuleContexts(BoardNameContext.class);
		}
		public List<BoardGravityContext> boardGravity() {
			return getRuleContexts(BoardGravityContext.class);
		}
		public BoardLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boardLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterBoardLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitBoardLine(this);
		}
	}

	public final BoardLineContext boardLine() throws RecognitionException {
		BoardLineContext _localctx = new BoardLineContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_boardLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); match(17);
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << 3) | (1L << 4) | (1L << 13))) != 0)) {
				{
				setState(70);
				switch (_input.LA(1)) {
				case 2:
					{
					setState(66); boardName();
					}
					break;
				case 4:
					{
					setState(67); boardGravity();
					}
					break;
				case 3:
					{
					setState(68); boardFric1();
					}
					break;
				case 13:
					{
					setState(69); boardFric2();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoardNameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public TerminalNode EQUALS() { return getToken(BoardGrammarParser.EQUALS, 0); }
		public BoardNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boardName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterBoardName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitBoardName(this);
		}
	}

	public final BoardNameContext boardName() throws RecognitionException {
		BoardNameContext _localctx = new BoardNameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_boardName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); match(2);
			setState(76); match(EQUALS);
			setState(77); match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoardGravityContext extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(BoardGrammarParser.FLOAT, 0); }
		public TerminalNode EQUALS() { return getToken(BoardGrammarParser.EQUALS, 0); }
		public BoardGravityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boardGravity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterBoardGravity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitBoardGravity(this);
		}
	}

	public final BoardGravityContext boardGravity() throws RecognitionException {
		BoardGravityContext _localctx = new BoardGravityContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_boardGravity);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); match(4);
			setState(80); match(EQUALS);
			setState(81); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoardFric1Context extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(BoardGrammarParser.FLOAT, 0); }
		public TerminalNode EQUALS() { return getToken(BoardGrammarParser.EQUALS, 0); }
		public BoardFric1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boardFric1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterBoardFric1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitBoardFric1(this);
		}
	}

	public final BoardFric1Context boardFric1() throws RecognitionException {
		BoardFric1Context _localctx = new BoardFric1Context(_ctx, getState());
		enterRule(_localctx, 10, RULE_boardFric1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83); match(3);
			setState(84); match(EQUALS);
			setState(85); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoardFric2Context extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(BoardGrammarParser.FLOAT, 0); }
		public TerminalNode EQUALS() { return getToken(BoardGrammarParser.EQUALS, 0); }
		public BoardFric2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boardFric2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterBoardFric2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitBoardFric2(this);
		}
	}

	public final BoardFric2Context boardFric2() throws RecognitionException {
		BoardFric2Context _localctx = new BoardFric2Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_boardFric2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); match(13);
			setState(88); match(EQUALS);
			setState(89); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BallLineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public BallLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ballLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterBallLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitBallLine(this);
		}
	}

	public final BallLineContext ballLine() throws RecognitionException {
		BallLineContext _localctx = new BallLineContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ballLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91); match(5);
			setState(92); match(2);
			setState(93); match(EQUALS);
			setState(94); match(NAME);
			setState(95); match(22);
			setState(96); match(EQUALS);
			setState(97); match(FLOAT);
			setState(98); match(7);
			setState(99); match(EQUALS);
			setState(100); match(FLOAT);
			setState(101); match(18);
			setState(102); match(EQUALS);
			setState(103); match(FLOAT);
			setState(104); match(1);
			setState(105); match(EQUALS);
			setState(106); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SqBumperLineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public SqBumperLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sqBumperLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterSqBumperLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitSqBumperLine(this);
		}
	}

	public final SqBumperLineContext sqBumperLine() throws RecognitionException {
		SqBumperLineContext _localctx = new SqBumperLineContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_sqBumperLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108); match(12);
			setState(109); match(2);
			setState(110); match(EQUALS);
			setState(111); match(NAME);
			setState(112); match(22);
			setState(113); match(EQUALS);
			setState(114); match(FLOAT);
			setState(115); match(7);
			setState(116); match(EQUALS);
			setState(117); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CirBumperLineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public CirBumperLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cirBumperLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterCirBumperLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitCirBumperLine(this);
		}
	}

	public final CirBumperLineContext cirBumperLine() throws RecognitionException {
		CirBumperLineContext _localctx = new CirBumperLineContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_cirBumperLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119); match(14);
			setState(120); match(2);
			setState(121); match(EQUALS);
			setState(122); match(NAME);
			setState(123); match(22);
			setState(124); match(EQUALS);
			setState(125); match(FLOAT);
			setState(126); match(7);
			setState(127); match(EQUALS);
			setState(128); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TriBumperLineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public TriBumperLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triBumperLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterTriBumperLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitTriBumperLine(this);
		}
	}

	public final TriBumperLineContext triBumperLine() throws RecognitionException {
		TriBumperLineContext _localctx = new TriBumperLineContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_triBumperLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130); match(9);
			setState(131); match(2);
			setState(132); match(EQUALS);
			setState(133); match(NAME);
			setState(134); match(22);
			setState(135); match(EQUALS);
			setState(136); match(FLOAT);
			setState(137); match(7);
			setState(138); match(EQUALS);
			setState(139); match(FLOAT);
			setState(140); match(20);
			setState(141); match(EQUALS);
			setState(142); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RtFlipLineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public RtFlipLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rtFlipLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterRtFlipLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitRtFlipLine(this);
		}
	}

	public final RtFlipLineContext rtFlipLine() throws RecognitionException {
		RtFlipLineContext _localctx = new RtFlipLineContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_rtFlipLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144); match(28);
			setState(145); match(2);
			setState(146); match(EQUALS);
			setState(147); match(NAME);
			setState(148); match(22);
			setState(149); match(EQUALS);
			setState(150); match(FLOAT);
			setState(151); match(7);
			setState(152); match(EQUALS);
			setState(153); match(FLOAT);
			setState(154); match(20);
			setState(155); match(EQUALS);
			setState(156); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LftFlipLineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public LftFlipLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lftFlipLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterLftFlipLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitLftFlipLine(this);
		}
	}

	public final LftFlipLineContext lftFlipLine() throws RecognitionException {
		LftFlipLineContext _localctx = new LftFlipLineContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_lftFlipLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158); match(25);
			setState(159); match(2);
			setState(160); match(EQUALS);
			setState(161); match(NAME);
			setState(162); match(22);
			setState(163); match(EQUALS);
			setState(164); match(FLOAT);
			setState(165); match(7);
			setState(166); match(EQUALS);
			setState(167); match(FLOAT);
			setState(168); match(20);
			setState(169); match(EQUALS);
			setState(170); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AbsorberLineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public AbsorberLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_absorberLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterAbsorberLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitAbsorberLine(this);
		}
	}

	public final AbsorberLineContext absorberLine() throws RecognitionException {
		AbsorberLineContext _localctx = new AbsorberLineContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_absorberLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172); match(23);
			setState(173); match(2);
			setState(174); match(EQUALS);
			setState(175); match(NAME);
			setState(176); match(22);
			setState(177); match(EQUALS);
			setState(178); match(FLOAT);
			setState(179); match(7);
			setState(180); match(EQUALS);
			setState(181); match(FLOAT);
			setState(182); match(26);
			setState(183); match(EQUALS);
			setState(184); match(FLOAT);
			setState(185); match(21);
			setState(186); match(EQUALS);
			setState(187); match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PortalNoBoardLineContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(BoardGrammarParser.NAME); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode NAME(int i) {
			return getToken(BoardGrammarParser.NAME, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public PortalNoBoardLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_portalNoBoardLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterPortalNoBoardLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitPortalNoBoardLine(this);
		}
	}

	public final PortalNoBoardLineContext portalNoBoardLine() throws RecognitionException {
		PortalNoBoardLineContext _localctx = new PortalNoBoardLineContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_portalNoBoardLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189); match(19);
			setState(190); match(2);
			setState(191); match(EQUALS);
			setState(192); match(NAME);
			setState(193); match(22);
			setState(194); match(EQUALS);
			setState(195); match(FLOAT);
			setState(196); match(7);
			setState(197); match(EQUALS);
			setState(198); match(FLOAT);
			setState(199); match(16);
			setState(200); match(EQUALS);
			setState(201); match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PortalWithBoardLineContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(BoardGrammarParser.NAME); }
		public List<TerminalNode> FLOAT() { return getTokens(BoardGrammarParser.FLOAT); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode NAME(int i) {
			return getToken(BoardGrammarParser.NAME, i);
		}
		public TerminalNode FLOAT(int i) {
			return getToken(BoardGrammarParser.FLOAT, i);
		}
		public PortalWithBoardLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_portalWithBoardLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterPortalWithBoardLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitPortalWithBoardLine(this);
		}
	}

	public final PortalWithBoardLineContext portalWithBoardLine() throws RecognitionException {
		PortalWithBoardLineContext _localctx = new PortalWithBoardLineContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_portalWithBoardLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203); match(19);
			setState(204); match(2);
			setState(205); match(EQUALS);
			setState(206); match(NAME);
			setState(207); match(22);
			setState(208); match(EQUALS);
			setState(209); match(FLOAT);
			setState(210); match(7);
			setState(211); match(EQUALS);
			setState(212); match(FLOAT);
			setState(213); match(6);
			setState(214); match(EQUALS);
			setState(215); match(NAME);
			setState(216); match(16);
			setState(217); match(EQUALS);
			setState(218); match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FireLineContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(BoardGrammarParser.NAME); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode NAME(int i) {
			return getToken(BoardGrammarParser.NAME, i);
		}
		public FireLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fireLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterFireLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitFireLine(this);
		}
	}

	public final FireLineContext fireLine() throws RecognitionException {
		FireLineContext _localctx = new FireLineContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_fireLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220); match(8);
			setState(221); match(24);
			setState(222); match(EQUALS);
			setState(223); match(NAME);
			setState(224); match(10);
			setState(225); match(EQUALS);
			setState(226); match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyDownLineContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(BoardGrammarParser.NAME); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode NAME(int i) {
			return getToken(BoardGrammarParser.NAME, i);
		}
		public KeyDownLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyDownLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterKeyDownLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitKeyDownLine(this);
		}
	}

	public final KeyDownLineContext keyDownLine() throws RecognitionException {
		KeyDownLineContext _localctx = new KeyDownLineContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_keyDownLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228); match(27);
			setState(229); match(11);
			setState(230); match(EQUALS);
			setState(231); match(NAME);
			setState(232); match(10);
			setState(233); match(EQUALS);
			setState(234); match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyUpLineContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(BoardGrammarParser.NAME); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public TerminalNode NAME(int i) {
			return getToken(BoardGrammarParser.NAME, i);
		}
		public KeyUpLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyUpLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterKeyUpLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitKeyUpLine(this);
		}
	}

	public final KeyUpLineContext keyUpLine() throws RecognitionException {
		KeyUpLineContext _localctx = new KeyUpLineContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_keyUpLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236); match(15);
			setState(237); match(11);
			setState(238); match(EQUALS);
			setState(239); match(NAME);
			setState(240); match(10);
			setState(241); match(EQUALS);
			setState(242); match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyDown2LineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public TerminalNode FLOAT() { return getToken(BoardGrammarParser.FLOAT, 0); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public KeyDown2LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyDown2Line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterKeyDown2Line(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitKeyDown2Line(this);
		}
	}

	public final KeyDown2LineContext keyDown2Line() throws RecognitionException {
		KeyDown2LineContext _localctx = new KeyDown2LineContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_keyDown2Line);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244); match(27);
			setState(245); match(11);
			setState(246); match(EQUALS);
			setState(247); match(FLOAT);
			setState(248); match(10);
			setState(249); match(EQUALS);
			setState(250); match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyUp2LineContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(BoardGrammarParser.NAME, 0); }
		public TerminalNode FLOAT() { return getToken(BoardGrammarParser.FLOAT, 0); }
		public List<TerminalNode> EQUALS() { return getTokens(BoardGrammarParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(BoardGrammarParser.EQUALS, i);
		}
		public KeyUp2LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyUp2Line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).enterKeyUp2Line(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardGrammarListener ) ((BoardGrammarListener)listener).exitKeyUp2Line(this);
		}
	}

	public final KeyUp2LineContext keyUp2Line() throws RecognitionException {
		KeyUp2LineContext _localctx = new KeyUp2LineContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_keyUp2Line);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252); match(15);
			setState(253); match(11);
			setState(254); match(EQUALS);
			setState(255); match(FLOAT);
			setState(256); match(10);
			setState(257); match(EQUALS);
			setState(258); match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3%\u0107\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3?\n"+
		"\3\f\3\16\3B\13\3\3\4\3\4\3\4\3\4\3\4\7\4I\n\4\f\4\16\4L\13\4\3\5\3\5"+
		"\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\2\27\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*\2\2\u0103"+
		"\2,\3\2\2\2\4/\3\2\2\2\6C\3\2\2\2\bM\3\2\2\2\nQ\3\2\2\2\fU\3\2\2\2\16"+
		"Y\3\2\2\2\20]\3\2\2\2\22n\3\2\2\2\24y\3\2\2\2\26\u0084\3\2\2\2\30\u0092"+
		"\3\2\2\2\32\u00a0\3\2\2\2\34\u00ae\3\2\2\2\36\u00bf\3\2\2\2 \u00cd\3\2"+
		"\2\2\"\u00de\3\2\2\2$\u00e6\3\2\2\2&\u00ee\3\2\2\2(\u00f6\3\2\2\2*\u00fe"+
		"\3\2\2\2,-\5\4\3\2-.\7\1\2\2.\3\3\2\2\2/@\5\6\4\2\60?\5\20\t\2\61?\5\22"+
		"\n\2\62?\5\24\13\2\63?\5\26\f\2\64?\5\30\r\2\65?\5\32\16\2\66?\5\34\17"+
		"\2\67?\5\"\22\28?\5\36\20\29?\5 \21\2:?\5&\24\2;?\5$\23\2<?\5*\26\2=?"+
		"\5(\25\2>\60\3\2\2\2>\61\3\2\2\2>\62\3\2\2\2>\63\3\2\2\2>\64\3\2\2\2>"+
		"\65\3\2\2\2>\66\3\2\2\2>\67\3\2\2\2>8\3\2\2\2>9\3\2\2\2>:\3\2\2\2>;\3"+
		"\2\2\2><\3\2\2\2>=\3\2\2\2?B\3\2\2\2@>\3\2\2\2@A\3\2\2\2A\5\3\2\2\2B@"+
		"\3\2\2\2CJ\7\23\2\2DI\5\b\5\2EI\5\n\6\2FI\5\f\7\2GI\5\16\b\2HD\3\2\2\2"+
		"HE\3\2\2\2HF\3\2\2\2HG\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK\3\2\2\2K\7\3\2\2"+
		"\2LJ\3\2\2\2MN\7\4\2\2NO\7!\2\2OP\7$\2\2P\t\3\2\2\2QR\7\6\2\2RS\7!\2\2"+
		"ST\7\"\2\2T\13\3\2\2\2UV\7\5\2\2VW\7!\2\2WX\7\"\2\2X\r\3\2\2\2YZ\7\17"+
		"\2\2Z[\7!\2\2[\\\7\"\2\2\\\17\3\2\2\2]^\7\7\2\2^_\7\4\2\2_`\7!\2\2`a\7"+
		"$\2\2ab\7\30\2\2bc\7!\2\2cd\7\"\2\2de\7\t\2\2ef\7!\2\2fg\7\"\2\2gh\7\24"+
		"\2\2hi\7!\2\2ij\7\"\2\2jk\7\3\2\2kl\7!\2\2lm\7\"\2\2m\21\3\2\2\2no\7\16"+
		"\2\2op\7\4\2\2pq\7!\2\2qr\7$\2\2rs\7\30\2\2st\7!\2\2tu\7\"\2\2uv\7\t\2"+
		"\2vw\7!\2\2wx\7\"\2\2x\23\3\2\2\2yz\7\20\2\2z{\7\4\2\2{|\7!\2\2|}\7$\2"+
		"\2}~\7\30\2\2~\177\7!\2\2\177\u0080\7\"\2\2\u0080\u0081\7\t\2\2\u0081"+
		"\u0082\7!\2\2\u0082\u0083\7\"\2\2\u0083\25\3\2\2\2\u0084\u0085\7\13\2"+
		"\2\u0085\u0086\7\4\2\2\u0086\u0087\7!\2\2\u0087\u0088\7$\2\2\u0088\u0089"+
		"\7\30\2\2\u0089\u008a\7!\2\2\u008a\u008b\7\"\2\2\u008b\u008c\7\t\2\2\u008c"+
		"\u008d\7!\2\2\u008d\u008e\7\"\2\2\u008e\u008f\7\26\2\2\u008f\u0090\7!"+
		"\2\2\u0090\u0091\7\"\2\2\u0091\27\3\2\2\2\u0092\u0093\7\36\2\2\u0093\u0094"+
		"\7\4\2\2\u0094\u0095\7!\2\2\u0095\u0096\7$\2\2\u0096\u0097\7\30\2\2\u0097"+
		"\u0098\7!\2\2\u0098\u0099\7\"\2\2\u0099\u009a\7\t\2\2\u009a\u009b\7!\2"+
		"\2\u009b\u009c\7\"\2\2\u009c\u009d\7\26\2\2\u009d\u009e\7!\2\2\u009e\u009f"+
		"\7\"\2\2\u009f\31\3\2\2\2\u00a0\u00a1\7\33\2\2\u00a1\u00a2\7\4\2\2\u00a2"+
		"\u00a3\7!\2\2\u00a3\u00a4\7$\2\2\u00a4\u00a5\7\30\2\2\u00a5\u00a6\7!\2"+
		"\2\u00a6\u00a7\7\"\2\2\u00a7\u00a8\7\t\2\2\u00a8\u00a9\7!\2\2\u00a9\u00aa"+
		"\7\"\2\2\u00aa\u00ab\7\26\2\2\u00ab\u00ac\7!\2\2\u00ac\u00ad\7\"\2\2\u00ad"+
		"\33\3\2\2\2\u00ae\u00af\7\31\2\2\u00af\u00b0\7\4\2\2\u00b0\u00b1\7!\2"+
		"\2\u00b1\u00b2\7$\2\2\u00b2\u00b3\7\30\2\2\u00b3\u00b4\7!\2\2\u00b4\u00b5"+
		"\7\"\2\2\u00b5\u00b6\7\t\2\2\u00b6\u00b7\7!\2\2\u00b7\u00b8\7\"\2\2\u00b8"+
		"\u00b9\7\34\2\2\u00b9\u00ba\7!\2\2\u00ba\u00bb\7\"\2\2\u00bb\u00bc\7\27"+
		"\2\2\u00bc\u00bd\7!\2\2\u00bd\u00be\7\"\2\2\u00be\35\3\2\2\2\u00bf\u00c0"+
		"\7\25\2\2\u00c0\u00c1\7\4\2\2\u00c1\u00c2\7!\2\2\u00c2\u00c3\7$\2\2\u00c3"+
		"\u00c4\7\30\2\2\u00c4\u00c5\7!\2\2\u00c5\u00c6\7\"\2\2\u00c6\u00c7\7\t"+
		"\2\2\u00c7\u00c8\7!\2\2\u00c8\u00c9\7\"\2\2\u00c9\u00ca\7\22\2\2\u00ca"+
		"\u00cb\7!\2\2\u00cb\u00cc\7$\2\2\u00cc\37\3\2\2\2\u00cd\u00ce\7\25\2\2"+
		"\u00ce\u00cf\7\4\2\2\u00cf\u00d0\7!\2\2\u00d0\u00d1\7$\2\2\u00d1\u00d2"+
		"\7\30\2\2\u00d2\u00d3\7!\2\2\u00d3\u00d4\7\"\2\2\u00d4\u00d5\7\t\2\2\u00d5"+
		"\u00d6\7!\2\2\u00d6\u00d7\7\"\2\2\u00d7\u00d8\7\b\2\2\u00d8\u00d9\7!\2"+
		"\2\u00d9\u00da\7$\2\2\u00da\u00db\7\22\2\2\u00db\u00dc\7!\2\2\u00dc\u00dd"+
		"\7$\2\2\u00dd!\3\2\2\2\u00de\u00df\7\n\2\2\u00df\u00e0\7\32\2\2\u00e0"+
		"\u00e1\7!\2\2\u00e1\u00e2\7$\2\2\u00e2\u00e3\7\f\2\2\u00e3\u00e4\7!\2"+
		"\2\u00e4\u00e5\7$\2\2\u00e5#\3\2\2\2\u00e6\u00e7\7\35\2\2\u00e7\u00e8"+
		"\7\r\2\2\u00e8\u00e9\7!\2\2\u00e9\u00ea\7$\2\2\u00ea\u00eb\7\f\2\2\u00eb"+
		"\u00ec\7!\2\2\u00ec\u00ed\7$\2\2\u00ed%\3\2\2\2\u00ee\u00ef\7\21\2\2\u00ef"+
		"\u00f0\7\r\2\2\u00f0\u00f1\7!\2\2\u00f1\u00f2\7$\2\2\u00f2\u00f3\7\f\2"+
		"\2\u00f3\u00f4\7!\2\2\u00f4\u00f5\7$\2\2\u00f5\'\3\2\2\2\u00f6\u00f7\7"+
		"\35\2\2\u00f7\u00f8\7\r\2\2\u00f8\u00f9\7!\2\2\u00f9\u00fa\7\"\2\2\u00fa"+
		"\u00fb\7\f\2\2\u00fb\u00fc\7!\2\2\u00fc\u00fd\7$\2\2\u00fd)\3\2\2\2\u00fe"+
		"\u00ff\7\21\2\2\u00ff\u0100\7\r\2\2\u0100\u0101\7!\2\2\u0101\u0102\7\""+
		"\2\2\u0102\u0103\7\f\2\2\u0103\u0104\7!\2\2\u0104\u0105\7$\2\2\u0105+"+
		"\3\2\2\2\6>@HJ";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}