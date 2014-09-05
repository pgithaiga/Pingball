package pingball.simulation;

import static java.lang.Double.parseDouble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import physics.Vect;
import pingball.simulation.collidable.Collidable;
import pingball.simulation.gadget.Absorber;
import pingball.simulation.gadget.CircleBumper;
import pingball.simulation.gadget.Flipper;
import pingball.simulation.gadget.Gadget;
import pingball.simulation.gadget.Portal;
import pingball.simulation.gadget.SquareBumper;
import pingball.simulation.gadget.TriangleBumper;
import pingball.util.DrawObject;
import pingball.util.Pair;

/**
 * A pingball board.
 *
 * This keeps track of all objects on a board, including balls, walls, and
 * gadgets.
 * 
 * Rep Invariant:
 *  None. All the game-objects on the board have a rep-invariant for themselves which checks 
 *  if they are inside the board, working correctly etc. Calling the board's checkRep is equivalent
 *  to calling all these checkReps(), which are neways being called after any method. So no need to
 *  have a separate checkRep() in board.
 *  
 */
public class Board {

    private final boolean named;

    private final String name;

    private final Set<Ball> balls = new HashSet<>();
    
    private final Set<Gadget> gadgets = new HashSet<>();
    
    private final Set<Portal> portals = new HashSet<>();
    
    private final Set<GameObject> gameObjects = new HashSet<>();
        
    private final Map<String, Gadget> gadgetNames = new HashMap<>();
    
    private final Map<String, Portal> portalNames = new HashMap<>();
    
    private final Map<String, Set<String>> keyUpBindings = new HashMap<>();
    
    private final Map<String, Set<String>> keyDownBindings = new HashMap<>();
        
    private final LinkedBlockingQueue<String> sendQueue;
    
    private final LinkedBlockingQueue<String> receiveQueue;

    private final Wall topWall, bottomWall, leftWall, rightWall;

    private double gravity = Constants.DEFAULT_GRAVITY;

    private double mu1 = Constants.DEFAULT_FRICTION_MU1, mu2 = Constants.DEFAULT_FRICTION_MU2;
    
    /**
     * Private constructor that creates a board
     * @param sendQueue the queue that takes teleported balls
     * @param receiveQueue the queue that receive all balls from the server and also from the local portal
     * @param name the name of the board if this board is named,
     * requires: non-empty string that matches the regex [A-Za-z_][A-Za-z_0-9]*
     * @param named determines if this board is named
     * @param gravity the desired gravitational acceleration in the board
     * @param mu the desired friction constant in units of 1/sec
     * @param mu2 the desired friction constant in units of 1/L
     */
    public Board(LinkedBlockingQueue<String> sendQueue, LinkedBlockingQueue<String> receiveQueue, String name, boolean named, double gravity, double mu1, double mu2){
        this.sendQueue = sendQueue;
        this.receiveQueue = receiveQueue;
        this.name = name;
        this.named = named;
        this.gravity = gravity;
        this.mu1 = mu1;
        this.mu2 = mu2;
        
        topWall = new Wall(this, Wall.Side.TOP, sendQueue);
        bottomWall = new Wall(this, Wall.Side.BOTTOM, sendQueue);
        leftWall = new Wall(this, Wall.Side.LEFT, sendQueue);
        rightWall = new Wall(this, Wall.Side.RIGHT, sendQueue);
        
        addGameObject(topWall);
        addGameObject(bottomWall);
        addGameObject(leftWall);
        addGameObject(rightWall);

        hello();
    }
    
    /**
     * Send a hello message to the server.
     */
    public void hello() {
        try {
            sendQueue.put(String.format("hello %s", name));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Process a message to alter the board.
     *
     * Performs the action corresponding to a received message.
     *
     * @param message The message.
     */
    public void processMessage(String message) {
        String[] split = message.split(" ");

        String connectmsg = "connect (left|right|top|bottom) [A-Za-z_][A-Za-z_0-9]*";
        String disconnectmsg = "disconnect (left|right|top|bottom)";
        String ballmsg = "^ball [A-Za-z_][A-Za-z_0-9]* (left|right|top|bottom)( -?(?:[0-9]+\\.[0-9]*|\\.?[0-9]+)){4}$";
        String potalBallmsg = "^warp [A-Za-z_][A-Za-z_0-9]* [A-Za-z_][A-Za-z_0-9]*( -?(?:[0-9]+\\.[0-9]*|\\.?[0-9]+)){2}$";
        String keyUpmsg = "^keyup@[A-Za-z_0-9 ]*$";
        String keyDownmsg = "^keydown@[A-Za-z0-9 ]*$";
        
        if (message.matches(connectmsg)) {
            connectWall(Wall.Side.fromString(split[1]), split[2]);
        } else if (message.matches(disconnectmsg)) {
            disconnectWall(Wall.Side.fromString(split[1]));
        } else if (message.matches(ballmsg)) {
            String ballName = split[1];
            Wall.Side side = Wall.Side.fromString(split[2]);
            double x = parseDouble(split[3]), y = parseDouble(split[4]),
                vx = parseDouble(split[5]), vy = parseDouble(split[6]);
            // fix location
            switch (side) {
                case LEFT:
                case RIGHT:
                    x = getWidth() - x;
                    break;
                case TOP:
                case BOTTOM:
                    y = getHeight() - y;
                    break;
            }
            addBall(new Ball(new Vect(x, y), new Vect(vx, vy), ballName, gravity, mu1, mu2));
        } else if (message.matches(potalBallmsg)) {
            Portal target = portalNames.get(split[1]);
            String ballName = split[2];
            double vx = parseDouble(split[3]), vy = parseDouble(split[4]);
            addBall(new Ball(target.getMiddlePosition(), new Vect(vx, vy), ballName, gravity, mu1, mu2));
        } else if (message.matches(keyUpmsg)) {
            String keyText = message.split("@")[1];
            if( keyUpBindings.containsKey(keyText) ) {
                for(String gadgetName: keyUpBindings.get(keyText)) {
                    if( gadgetNames.containsKey(gadgetName) ) {
                        gadgetNames.get(gadgetName).action();
                    }
                }
            }
        } else if (message.matches(keyDownmsg)) {
            String keyText = message.split("@")[1];
            if( keyDownBindings.containsKey(keyText) ) {
                for(String gadgetName: keyDownBindings.get(keyText)) {
                    if( gadgetNames.containsKey(gadgetName) ) {
                        gadgetNames.get(gadgetName).action();
                    }
                }
            }
        } else {
            System.err.println("ignoring invalid message from server");
        }
    }
    
    /**
     * Simulate the board for a specified time.
     *
     * Evolve the contents of the board for a specified amount of time,
     * simulating gravity and collisions.
     *
     * @param time The amount of time (in seconds) to simulate for.
     */
    public void evolve(double time) {
        while (time > 0) {
            double simTime = Math.min(time, Constants.SIMULATION_TIMESTEP);
            boolean collision = false;
            Ball collideBall = null;
            GameObject collideGameObject = null;
            Collidable collideCollidable = null;
            
            for(Ball ball: balls) {
                for(GameObject gameObject: gameObjects) {
                    if( ball != gameObject ) {
                        Pair<Double, Collidable> collide = gameObject.timeUntilCollision(ball);
                        if(collide.getFirst() < simTime) {
                            collision = true;
                            collideBall = ball;
                            collideGameObject = gameObject;
                            collideCollidable = collide.getSecond();
                            simTime = collide.getFirst(); 
                        }
                    }
                }
            }
            
            simulate(simTime);
            if (collision) {
                collideGameObject.collide(collideBall, collideCollidable);
            } 
            time -= simTime;
        }
    }

    /**
     * Returns the name of this board.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the width of this board.
     *
     * @return The width.
     */
    public int getWidth() {
        return Constants.BOARD_WIDTH;
    }
    
    /**
     * Returns the height of this board.
     *
     * @return The height.
     */
    public int getHeight() {
        return Constants.BOARD_HEIGHT;
    }
    
    /**
     * Returns if the board has a name
     * @return true if the board has a name
     */
    public boolean isNamed(){
        return named;
    }

    /**
     * Returns a grid representation of this board.
     *
     * Returns a grid representation as a 22x22 grid of characters
     * corresponding to different game objects on the board.
     *
     * @return The grid representation.
     */
    public List<String> gridRepresentation() {
        char[][] representation = new char[1+getHeight()+1][1+getHeight()+1];
        for(int row = 0; row < representation.length; row ++ ) {
            for(int col = 0; col < representation[row].length; col ++ ) {
                representation[row][col] = ' ';
            }
        }
        for(GameObject gameObject: gameObjects) {
            GridLocation locationObject = gameObject.getLocation();
            List<String> representationObject = gameObject.gridRepresentation();
            
            int rowIndex = locationObject.y() + 1, colIndex = locationObject.x() + 1;
            for(int row = 0; row < representationObject.size(); row ++ ) {
                String line = representationObject.get(row);
                for(int col = 0; col < line.length(); col ++ ) {
                    char currentSymbol = representation[rowIndex+row][colIndex+col];
                    if( line.charAt(col) != ' ' && (currentSymbol == ' ' || currentSymbol == '*') ) {
                        representation[rowIndex+row][colIndex+col] = line.charAt(col);
                    }
                }
            }
        }
        List<String> gridRepresentation = new ArrayList<>();
        for(int line = 0; line < representation.length; line ++ ) {
            gridRepresentation.add(new String(representation[line]));
        }
        return gridRepresentation;
    }
    
    /**
     * Returns a set of DrawObject representation of this instance 
     * 
     * @param the ratio of the representation in pixel/L
     * 
     * @return the set of DrawObject representation
     */
    public Set<DrawObject> uiRepresentation(double ratio) {
        Set<DrawObject> result = new HashSet<>();
        for(GameObject gameObject: gameObjects) {
            result.addAll(gameObject.uiRepresentation(ratio));
        }
        return result;
    }
    
    /**
     * Adds a ball to the board.
     *
     * @param ball The ball to add.
     *
     * @return Whether this board did not already contain the ball.
     */
    public boolean addBall(Ball ball) {
        return balls.add(ball) && addGameObject(ball);
    }

    /**
     * Removes a ball from the board.
     *
     * @param ball The ball to remove.
     *
     * @return Whether this board contained the ball.
     */
    public boolean removeBall(Ball ball) {
        return balls.remove(ball) && removeGameObject(ball);
    }

    /**
     * Connect a wall to the server.
     *
     * @param side The side of the board to connect.
     *
     * @param name The name of the board that this wall is being connected to.
     */
    private void connectWall(Wall.Side side, String name) {
        switch (side) {
            case TOP: topWall.connect(name); break;
            case BOTTOM: bottomWall.connect(name); break;
            case LEFT: leftWall.connect(name); break;
            case RIGHT: rightWall.connect(name); break;
        }
    }

    /**
     * Disconnect a wall from the server.
     *
     * @param side The side of the board to disconnect.
     */
    private void disconnectWall(Wall.Side side) {
        switch (side) {
            case TOP: topWall.disconnect(); break;
            case BOTTOM: bottomWall.disconnect(); break;
            case LEFT: leftWall.disconnect(); break;
            case RIGHT: rightWall.disconnect(); break;
        }
    }

    /**
     * Creates and adds a square shaped bumper to the board with the given
     * parameters and with a reflection coefficient of 1.0.
     * @param name the name of the bumper, must be unique among all named objects
     *       in the board
     * @param x the x coordinate of the upper left corner of this bumper,
     *      0 <= x < 20 in units of L
     * @param y the y coordinate of the upper left corner of this bumper,
     *      0 <= y < 20 in units of L
     */
    public void addSquareBumper(String name, int x, int y) {
        GridLocation loc = GridLocation.of(x, y);
        SquareBumper b = new SquareBumper(this, name, loc);
        if (gadgetNames.containsKey(name)) {
            throw new RuntimeException("duplicate square bumper name: " + name);
        }
        addGadget(b);
    }

    /**
     * Creates and adds a new triangle shaped bumper to the board based with the
     * given parameters and with coefficient of reflection equal to 1.0.
     * @param name the name of the bumper, must be unique among all named objects
     *      in the board
     * @param x the x coordinate of the upper left corner of this bumper,
     *      0 <= x < 20 in units of L
     * @param y the y coordinate of the upper left corner of this bumper,
     *      0 <= y < 20 in units of L
     * @param orientation the orientation clockwise in degrees from the
     *      horizontal that this bumper is initialized in.
     */
    public void addTriangularBumper(String name, int x, int y, Gadget.Orientation orientation) {
        GridLocation loc = GridLocation.of(x, y);
        TriangleBumper b = new TriangleBumper(this, name, loc, orientation);
        if (gadgetNames.containsKey(name)) {
            throw new RuntimeException("duplicate triangle bumper name");
        }
        addGadget(b);
    }

    /**
     * Creates and adds a new circle shaped bumper to the board based with the
     * given parameters and with coefficient of reflection equal to 1.0.
     * @param name the name of the bumper, must be unique among all named objects
     *      in the board
     * @param x the x coordinate of the upper left corner of this bumper,
     *      0 <= x < 20 in units of L
     * @param y the y coordinate of the upper left corner of this bumper,
     *      0 <= y < 20 in units of L
     */
    public void addCircleBumper(String name, int x, int y) {
        GridLocation loc = GridLocation.of(x, y);
        CircleBumper b = new CircleBumper(this, name, loc);
        if (gadgetNames.containsKey(name)) {
            throw new RuntimeException("duplicate circle bumper name");
        }
        addGadget(b);
    }

    /**
     * Creates and adds a new absorber to the board based with the given
     * parameters and with coefficient of reflection equal to 1.0.
     * @param name the name of the absorber, must be unique among all named
     *      objects in the board
     * @param x the x coordinate of the upper left corner of this absorber,
     *      0 <= x + width <= 20 in units of L
     * @param y the y coordinate of the upper left corner of this absorber,
     *      0 <= y + width <= 20 in units of L
     * @param width the desired width of this absorber, 1 <= width in units of L
     * @param height the desired height of this absorber, 1 <= height in units
     *      of L
     */
    public void addAbsorber(String name, int x, int y, int width, int height) {
        GridLocation loc = GridLocation.of(x, y);
        Absorber a = new Absorber(this, name, loc, width, height);
        if (gadgetNames.containsKey(name)) {
            throw new RuntimeException("duplicate absorber name");
        }
        addGadget(a);
    }

    /**
     * Creates and adds a new left flipper to the board based with the given
     * parameters and with coefficient of reflection equal to 1.0. The '2'
     * in the requirements below takes into account the size 2L x 2L bounding
     * box for flippers
     * @param name the name of the flipper, must be unique among all named
     *      objects in the board
     * @param x the x coordinate of the upper left corner of this flipper,
     *      0 <= x + 2 <= 20 in units of L
     * @param y the y coordinate of the upper left corner of this flipper,
     *      0 <= y + 2 <= 20 in units of L
     * @param orientation the orientation clockwise in degrees from the
     *      horizontal that this flipper is initialized in.
     */
    public void addLeftFlipper(String name, int x, int y, Gadget.Orientation orientation) {
        GridLocation loc = GridLocation.of(x, y);
        Flipper f = new Flipper(this, name, loc, Flipper.FlipperType.LEFT, orientation);
        if (gadgetNames.containsKey(name)) {
            throw new RuntimeException("duplicate left flipper name");
        }
        addGadget(f);
    }

    /**
     * Creates and adds a new right flipper to the board based with the given
     * parameters and with coefficient of reflection equal to 1.0. The '2'
     * in the requirements below takes into account the size 2L x 2L bounding
     * box for flippers
     * @param name the name of the flipper, must be unique among all named
     *      objects in the board
     * @param x the x coordinate of the upper left corner of this flipper,
     *      0 <= x + 2 <= 20 in units of L
     * @param y the y coordinate of the upper left corner of this flipper,
     *      0 <= y + 2 <= 20 in units of L
     * @param orientation the orientation clockwise in degrees from the
     *      horizontal that this flipper is initialized in.
     */
    public void addRightFlipper(String name, int x, int y, Gadget.Orientation orientation) {
        GridLocation loc = GridLocation.of(x, y);
        Flipper f = new Flipper(this, name, loc, Flipper.FlipperType.RIGHT, orientation);
        if (gadgetNames.containsKey(name)) {
            throw new RuntimeException("duplicate right flipper name");
        }
        addGadget(f);
    }
    
    /**
     * Creates and adds a new portal to the board
     * @param name the name of the flipper, must be unique among all named
     *      objects in the board
     * @param x the x coordinate of the upper left corner of this flipper,
     *      0 <= x + 1 <= 20 in units of L
     * @param y the y coordinate of the upper left corner of this flipper,
     *      0 <= y + 1 <= 20 in units of L
     * @param otherBoard the name of the board this portal connecting to. Can be null if it is a local portal
     * @param otherPortal the name of the portal this portal connecting to
     */
    public void addPortal(String name, int x, int y, String otherBoard, String otherPortal) {
        GridLocation loc = GridLocation.of(x, y);
        Portal p = new Portal(this, name, otherBoard, otherPortal, loc, sendQueue, receiveQueue);
        if (gadgetNames.containsKey(name)) {
            throw new RuntimeException("duplicate portal name");
        }
        portals.add(p);
        portalNames.put(name, p);
        
        addGadget(p);
    }
    
    /**
     * Records a key binding used to activate a gadget when the key is released.
     * @param keyText the text describing the key, must be the same as a java
     *  key description
     * @param gadgetName the name of the gadget, must be a name of a gadget
     *  in this board
     */
    public void addKeyUpBinding(String keyText, String gadgetName){
        if( !keyUpBindings.containsKey(keyText) ) {
            keyUpBindings.put(keyText, new HashSet<String>());
        }
        keyUpBindings.get(keyText).add(gadgetName);
    }
    
    /**
     * Records a key binding used to activate a gadget when the key is pressed.
     * @param keyText the text describing the key, must be the same as a java
     *  key description
     * @param gadgetName the name of the gadget, must be a name of a gadget
     *  in this board
     */
    public void addKeyDownBinding(String keyText, String gadgetName){
        if( !keyDownBindings.containsKey(keyText) ) {
            keyDownBindings.put(keyText, new HashSet<String>());
        }
        keyDownBindings.get(keyText).add(gadgetName);
    }

    /**
     * Connect two gadgets together such that the events produced by the gadget
     * of {@code source} are consumed by the gadget of {@code target}
     * @param source the name of the gadget that will produce the events
     * @param target the name of the gadget that will consume the events
     */
    public void connect(String source, String target) {
        if (gadgetNames.get(source) == null || gadgetNames.get(target) == null) {
            throw new RuntimeException("link nonexistant gadget");
        }
        gadgetNames.get(source).linkGadget(gadgetNames.get(target));
    }

    /**
     * Adds a ball to this board with the specified parameters and with a
     * default diameter of 0.5 L. The .25 below refers to the radius of the ball
     * @param name the name of the ball, must be unique across all named objects
     *      in the board
     * @param x the x coordinate of the center of this ball,
     *      0 <= x + .25 <= 20 in units of L
     * @param y the y coordinate of the center of this ball,
     *      0 <= y + .25 <= 20 in units of L
     * @param vX the horizontal velocity of the ball in units of L/sec
     * @param vY the vertical velocity of the ball in units of L/sec
     */
    public void addBall(String name, double x, double y, double vX, double vY) {
        Vect center = new Vect(x, y);
        Vect velocity = new Vect(vX, vY);
        Ball b = new Ball(center, velocity, name, gravity, mu1, mu2);
        addBall(b);
    }
    
    /**
     * Adds a game object to this board.
     *
     * @param gameObject The object to add.
     *
     * @return Whether this board did not contain the game object.
     */
    public boolean addGameObject(GameObject gameObject) {
        return gameObjects.add(gameObject);
    }

    /**
     * Removes a game object from this board.
     *
     * @param gameObject The object to remove.
     *
     * @return Whether this board contained the game object.
     */
    public boolean removeGameObject(GameObject gameObject) {
        return gameObjects.remove(gameObject);
    }
    
    /**
     * Adds a game object to this board.
     *
     * @param gameObject The object to add.
     *
     * @return Whether this board did not contain the game object.
     */
    public boolean addGadget(Gadget gadget){
        if(gadgets.contains(gadget)){
            return false;
        }
        gadgetNames.put(gadget.getName(), gadget);
        addGameObject(gadget);
        gadgets.add(gadget);
        return true;
    }
    
    /**
     * Removes a gadget object from this board.
     *
     * @param gadget The object to remove.
     *
     * @return Whether this board contained the game object.
     */
    public boolean removeGadget(Gadget gadget){
        if(gadgets.contains(gadget)){
            gadgetNames.remove(gadget.getName());
            removeGameObject(gadget);
            gadgets.remove(gadget);
            return true;
        }
        return false;
    }
    
    /**
     * Simulate motions of objects for a specified time.
     *
     * This does not take collisions into account.
     *
     * @param time The time to simulate for.
     */
    private void simulate(double time) {
        for(GameObject gameObject: gameObjects) {
            gameObject.evolve(time);
        }
    }
    
    /**
     * Returns the fact that this board contains the given portal name
     * 
     * @param name the portal name we want to test
     * 
     * @return the fact that this board contains the given portal name
     */
    public boolean containsPortal(String name) {
        return portalNames.containsKey(name);
    }
    
    /**
     * Returns a html string representing the name of the board connecting to the corresponding size. If no board connects, return
     * an empty string
     * 
     * @param side The side that we consider 
     * 
     * @return a html string representing the name of the board connecting to the corresponding size
     */
    public String getWallConnection(Wall.Side side) {
        switch (side) {
            case TOP: return topWall.wallConnection(); 
            case BOTTOM: return bottomWall.wallConnection(); 
            case LEFT: return leftWall.wallConnection(); 
            case RIGHT: return rightWall.wallConnection(); 
            default: return "";
        }
    }
}
