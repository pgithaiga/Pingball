package pingball.grammars;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import pingball.simulation.Board;
import pingball.simulation.Constants;
import pingball.simulation.gadget.Gadget;
import pingball.util.KeyTextConvertor;

/**
 * Child class of BoardGrammarBaseListener.java, which is auto-created by ANTLR. In this class, the methods
 * define what happens as the listener walks through the tree. As it exits a node or branch of the tree, the
 * board, gadgets, or balls are created. The gadgets and balls are also added to the board.
 * @author Natasha (phase 1)
 * @author Erik Nguyen (phase 2)
 */
public class BoardGrammarCreatorListener extends BoardGrammarBaseListener {

    //keeps track of names of gadgets and balls in the board to prevent repeated names
    private List<String> gadgetNames = new ArrayList<String>();

    //parameter to create the board to send balls to the server as they leave a board
    private LinkedBlockingQueue<String> ballQueue;
    private LinkedBlockingQueue<String> receiveQueue;
    
    private String boardName = null;
    private boolean isNamed = false;
    private double gravity = Constants.DEFAULT_GRAVITY;
    private double mu = Constants.DEFAULT_FRICTION_MU1;
    private double mu2 = Constants.DEFAULT_FRICTION_MU2;
    private Board gameBoard;

    /**
     * Returns the name of the board to be used in tests to make sure the name is correct through
     * BoardGrammarCentral class. There, only the listener that is created can call this method.
     * @return String representation of the name of the board
     */
    public String listenerBoardName() {
        return this.boardName;
    }

    /**
     * Creates the board through the listener methods after parsing through the file
     * @return Board with all the gadgets and balls specified in the file
     */
    public Board listenerBoard() {
        return this.gameBoard;
    }

    /**
     * Returns the value of mu to be tested for accuracy through BoardGrammarCentral class. There,
     * only the listener that is created can call this method.
     * @return mu represented as a double
     */
    public double listenerFric1() {
        return this.mu;
    }

    /**
     * Returns the value of mu2 to be tested for accuracy through BoardGrammarCentral class. There,
     * only the listener that is created can call this method.
     * @return mu2 represented as a double
     */
    public double listenerFric2() {
        return this.mu2;
    }

    /**
     * Returns the value of gravity to be tested for accuracy through BoardGrammarCentral class. There,
     * only the listener that is created can call this method.
     * @return gravity represented as a double
     */
    public double listenerGravity() {
        return this.gravity;
    }
    
    /**
     * Returns the value of gravity to be tested for accuracy through BoardGrammarCentral class. There,
     * only the listener that is created can call this method.
     * @return true if the board created by this object has a name
     */
    public boolean listenerBoardIsNamed(){
        return this.isNamed;
    }


    /**
     * Sets the linked blocking queue used to create the Board, based on what is passed in as a parameter
     * in BoardGrammarCentral.java. There, only the listener that is created can call this method.
     * @param queue
     */
    public void setListenerQueue(LinkedBlockingQueue<String> queue) {
        this.ballQueue = queue;
    }
    
    public void setReceiveQueue(LinkedBlockingQueue<String> queue){
        this.receiveQueue = queue;
    }


    /*The following methods come in the order that they are occur while walking through the tree*/
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Parses through the section defining the board's name, and possibly gravity, mu, and/or mu2. If
     * the gravity, mu, and/or mu2 aren't specified, they are defaulted to 25.0, 0.025, and 0.025
     * respectively. A 20x20 board is created.
     */
    @Override
    public void exitBoardLine(BoardGrammarParser.BoardLineContext ctx) {
        Board game = new Board(this.ballQueue, receiveQueue, boardName, boardName == null, this.gravity, this.mu, this.mu2);

        this.gameBoard = game;
    }

    @Override
    public void exitBoardName(BoardGrammarParser.BoardNameContext ctx){
        this.boardName = ctx.NAME().getText();
        this.isNamed = true;
    }
    
    /**
     * Sets the board's gravity to the value that is parsed in the file, if there is a value set in the file.
     * Otherwise, it is the default value of 25.0
     */
    @Override
    public void exitBoardGravity(BoardGrammarParser.BoardGravityContext ctx) {
        double boardGravity = Double.valueOf(ctx.FLOAT().getText());
        this.gravity = boardGravity;
    }

    /**
     * Sets the board's mu value to what is parsed in the file, if it is specified in the file. Otherwise
     * it's the default value of 0.025.
     */
    @Override
    public void exitBoardFric1(BoardGrammarParser.BoardFric1Context ctx) {
        double boardfric1 = Double.valueOf(ctx.FLOAT().getText());
        this.mu = boardfric1;
    }

    /**
     * Sets the board's mu2 value to what is parsed in the file, if it is specified in the file. Otherwise
     * it's the default value of 0.025.
     */
    @Override
    public void exitBoardFric2(BoardGrammarParser.BoardFric2Context ctx) {
        double boardfric2 = Double.valueOf(ctx.FLOAT().getText());
        this.mu2 = boardfric2;
    }


    /**
     * Parses through the section defining balls and adds one at a time at its given start location with
     * its specified start velocities, only if a gadget/ball with its name hasn't already been added to
     * the board.
     */
    @Override
    public void exitBallLine(BoardGrammarParser.BallLineContext ctx) {
        String ballName = ctx.NAME().getText();
        double ballX = Double.valueOf(ctx.FLOAT(0).getText());
        double ballY = Double.valueOf(ctx.FLOAT(1).getText());
        double ballXvel = Double.valueOf(ctx.FLOAT(2).getText());
        double ballYvel = Double.valueOf(ctx.FLOAT(3).getText());

        if(!this.gadgetNames.contains(ballName)) {
            gameBoard.addBall(ballName, ballX, ballY, ballXvel, ballYvel);
            this.gadgetNames.add(ballName);
        }
    }

    /**
     * Parses through the section containing square bumpers and creates and adds them to the board based on
     * the parsed locations, given there are no gadgets with the same name that have already been added.
     */
    @Override
    public void exitSqBumperLine(BoardGrammarParser.SqBumperLineContext ctx) {
        String sqBmpName = ctx.NAME().getText();
        int sqBmpX = Double.valueOf(ctx.FLOAT(0).getText()).intValue();
        int sqBmpY = Double.valueOf(ctx.FLOAT(1).getText()).intValue();

        if(!this.gadgetNames.contains(sqBmpName)) {
            gameBoard.addSquareBumper(sqBmpName, sqBmpX, sqBmpY);
            this.gadgetNames.add(sqBmpName);
        }

    }


    /**
     * Parses through section defining circle bumpers and adds one at a time at its given location, only
     * if a gadget with its name hasn't already been added to the board.
     */
    @Override
    public void exitCirBumperLine(BoardGrammarParser.CirBumperLineContext ctx) {
        String cirBmpName = ctx.NAME().getText();
        int cirBmpX = Double.valueOf(ctx.FLOAT(0).getText()).intValue();
        int cirBmpY = Double.valueOf(ctx.FLOAT(1).getText()).intValue();

        if(!this.gadgetNames.contains(cirBmpName)) {
            gameBoard.addCircleBumper(cirBmpName, cirBmpX, cirBmpY);
            this.gadgetNames.add(cirBmpName);
        }
    }

    /**
     * Parses through triangular bumpers and adds them to the board, one at a time, given that the
     * name of the bumper hasn't appeared on the board yet.
     */
    @Override
    public void exitTriBumperLine(BoardGrammarParser.TriBumperLineContext ctx) {

        int angle = Double.valueOf(ctx.FLOAT(2).getText()).intValue();
        String triBmpAngle = "" + Integer.toString(angle);

        String triBmpName = ctx.NAME().getText();
        int triBmpX = Double.valueOf(ctx.FLOAT(0).getText()).intValue();
        int triBmpY = Double.valueOf(ctx.FLOAT(1).getText()).intValue();
        if(angle == 0 | angle == 90 | angle == 180 | angle == 270) {

        }
        if(!this.gadgetNames.contains(triBmpName)) {
            gameBoard.addTriangularBumper(triBmpName, triBmpX, triBmpY, Gadget.Orientation.of(triBmpAngle));
            gadgetNames.add(triBmpName);
        }

    }

    /**
     * Parses through the section containing right flippers and adds one at a time based on its specified
     * location and orientation, only if a gadget/ball with its name hasn't already been added to the board.
     */
    @Override
    public void exitRtFlipLine(BoardGrammarParser.RtFlipLineContext ctx) {
        int angle = Double.valueOf(ctx.FLOAT(2).getText()).intValue();
        String rtFlipAngle = "";

        String rtFlipName = ctx.NAME().getText();
        int rtFlipX = Double.valueOf(ctx.FLOAT(0).getText()).intValue();
        int rtFlipY = Double.valueOf(ctx.FLOAT(1).getText()).intValue();
        if(angle == 0 | angle == 90 | angle == 180 | angle == 270) {
            rtFlipAngle += String.valueOf((Double.valueOf(ctx.FLOAT(2).getText()).intValue()));
        }

        if(!this.gadgetNames.contains(rtFlipName)) {
            gameBoard.addRightFlipper(rtFlipName, rtFlipX, rtFlipY, Gadget.Orientation.of(rtFlipAngle));
            this.gadgetNames.add(rtFlipName);
        }
    }

    /**
     * Parses through the section containing left flippers and creates and adds the left flippers to the board
     * based on the given location and orientation, given that a gadget/ball with the same name hasn't
     * already been added to the board.
     */
    @Override
    public void exitLftFlipLine(BoardGrammarParser.LftFlipLineContext ctx) {
        int angle = Double.valueOf(ctx.FLOAT(2).getText()).intValue();
        String lftFlipAngle = "";

        String lftFlipName = ctx.NAME().getText();
        int lftFlipX = Double.valueOf(ctx.FLOAT(0).getText()).intValue();
        int lftFlipY = Double.valueOf(ctx.FLOAT(1).getText()).intValue();
        if(angle == 0 | angle == 90 | angle == 180 | angle == 270) {
            lftFlipAngle += String.valueOf(Double.valueOf(ctx.FLOAT(2).getText()).intValue());
        }

        if(!this.gadgetNames.contains(lftFlipName)) {
            gameBoard.addLeftFlipper(lftFlipName, lftFlipX, lftFlipY, Gadget.Orientation.of(lftFlipAngle));
            this.gadgetNames.add(lftFlipName);
        }

    }

    /**
     * Parses through section defining absorbers and creates and adds absorbers to the board, given their
     * location, width, and height, only if the name of the absorber doesn't already exist in the board.
     */
    @Override
    public void exitAbsorberLine(BoardGrammarParser.AbsorberLineContext ctx) {
        String absName = ctx.NAME().getText();
        int absX = Double.valueOf(ctx.FLOAT(0).getText()).intValue();
        int absY = Double.valueOf(ctx.FLOAT(1).getText()).intValue();
        int absWidth = Double.valueOf(ctx.FLOAT(2).getText()).intValue();
        int absHeight = Double.valueOf(ctx.FLOAT(3).getText()).intValue();

        if(!this.gadgetNames.contains(absName)) {
            gameBoard.addAbsorber(absName, absX, absY, absWidth, absHeight);
            this.gadgetNames.add(absName);
        }
    }

    /**
     * Parses through the section of the file containing fire/actions and creates a connection between
     * two gadgets, given that both gadgets exist in the board.
     */
    @Override
    public void exitFireLine(BoardGrammarParser.FireLineContext ctx) {
        String trigger = ctx.NAME(0).getText();
        String action = ctx.NAME(1).getText();

        if(this.gadgetNames.contains(trigger) && this.gadgetNames.contains(action)) {
            gameBoard.connect(trigger, action);
        }
    }
    
    @Override public void exitPortalNoBoardLine(BoardGrammarParser.PortalNoBoardLineContext ctx) { 
        String name = ctx.NAME(0).getText();
        int x = Integer.valueOf(ctx.FLOAT(0).getText());
        int y = Integer.valueOf(ctx.FLOAT(1).getText());
        String otherPortal = ctx.NAME(1).getText();
        
        
        gameBoard.addPortal(name, x, y, null, otherPortal);
    }
    
    @Override public void exitPortalWithBoardLine(BoardGrammarParser.PortalWithBoardLineContext ctx) { 
        String name = ctx.NAME(0).getText();
        int x = Integer.valueOf(ctx.FLOAT(0).getText());
        int y = Integer.valueOf(ctx.FLOAT(1).getText());
        String otherBoard = ctx.NAME(1).getText();
        String otherPortal = ctx.NAME(2).getText();
        
        gameBoard.addPortal(name, x, y, otherBoard, otherPortal);
    }
    
    @Override public void exitKeyDownLine(BoardGrammarParser.KeyDownLineContext ctx) { 
        String key = ctx.NAME(0).getText();
        String name = ctx.NAME(1).getText();
        
        gameBoard.addKeyDownBinding(KeyTextConvertor.antlrToJavaText(key), name);
    }
    
    @Override public void exitKeyUpLine(BoardGrammarParser.KeyUpLineContext ctx) { 
        String key = ctx.NAME(0).getText();
        String name = ctx.NAME(1).getText();
        
        gameBoard.addKeyUpBinding(KeyTextConvertor.antlrToJavaText(key), name);
    }
    

    @Override public void exitKeyUp2Line(BoardGrammarParser.KeyUp2LineContext ctx) { 
        String key = ctx.FLOAT().getText();
        String name = ctx.NAME().getText();
        gameBoard.addKeyUpBinding(KeyTextConvertor.antlrToJavaText(key), name);
    }
    

    @Override public void exitKeyDown2Line(BoardGrammarParser.KeyDown2LineContext ctx) { 
        String key = ctx.FLOAT().getText();
        String name = ctx.NAME().getText();
        gameBoard.addKeyDownBinding(KeyTextConvertor.antlrToJavaText(key), name);
    }
}

