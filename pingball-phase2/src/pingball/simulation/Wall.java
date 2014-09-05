package pingball.simulation;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import physics.Vect;
import pingball.simulation.collidable.Collidable;
import pingball.simulation.collidable.Line;
import pingball.util.DrawObject;
import pingball.util.DrawObject.Layer;
import pingball.util.StringUtils;

/**
 * A bounding wall of the board. Keep the side of it and the board that it is on in order to compute its coordinate.
 * It also keep tracks on the board name that connects to it (if there is such board).
 * Rep-Invariant:
 *      connected is false if and only if connectedboardname is null
 *      location must only be one of these: (-1,-1), (-1,board.height), (board.width,-1)
 */
public class Wall extends GameObject {
    
    /**
     * The side of the board on which the wall is.
     */
    public enum Side {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM;

        /**
         * Returns the side opposite to this wall.
         *
         * @return The opposite side.
         */
        public Side opposite() {
            switch (this) {
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
                case TOP:
                    return BOTTOM;
                case BOTTOM:
                default:
                    return TOP;
            }
        }

        /**
         * Returns the string representation of the side.
         *
         * @return The string representation.
         */
        @Override public String toString() {
            switch (this) {
                case LEFT:
                    return "left";
                case RIGHT:
                    return "right";
                case TOP:
                    return "top";
                case BOTTOM:
                default:
                    return "bottom";
            }
        }

        /**
         * Generate a side from a string.
         *
         * @param side The string representing a side.
         *
         * @return The side.
         */
        public static Side fromString(String side) {
            switch (side) {
                case "left":
                    return LEFT;
                case "right":
                    return RIGHT;
                case "top":
                    return TOP;
                case "bottom":
                default:
                    return BOTTOM;
            }
        }
    }

    protected final GridLocation location;

    private final Board board;

    private final Side side;

    private boolean connected = false;

    private final BlockingQueue<String> sendQueue;

    private String connectedBoardName;
    
    private Layer layer;

    /**
     * Creates a wall.
     * 
     * @param board The board on which the wall is.
     * @param side The side of the board on which the wall is.
     * @param sendQueue The queue to which the wall sends information
     *              about a ball hitting it.
     */
    public Wall(Board board, Side side, BlockingQueue<String> sendQueue) {
        this.board = board;
        this.side = side;
        this.sendQueue = sendQueue;
        
        switch (side) {
            case TOP: collidables.add(new Line(new Vect(0,0), new Vect(board.getWidth(),0), Constants.WALL_REFLECTION_COEFF));
                      location = new GridLocation(-1,-1);
                break;
            case BOTTOM: collidables.add(new Line(new Vect(0, board.getHeight()), new Vect(board.getWidth(), board.getHeight()), Constants.WALL_REFLECTION_COEFF));
                         location = new GridLocation(-1, board.getHeight());
                break;
            case LEFT: collidables.add(new Line(new Vect(0,0), new Vect(0, board.getHeight()), Constants.WALL_REFLECTION_COEFF));
                       location = new GridLocation(-1, -1);
                break;
            case RIGHT: default: collidables.add(new Line(new Vect(board.getWidth(), 0), new Vect(board.getWidth(), board.getHeight()), Constants.WALL_REFLECTION_COEFF)); 
                        location = new GridLocation(board.getWidth(), -1);
                break;
        }
        layer = Layer.WALL;
        
        assert(checkRep());
    }
    
    /**
     * check the rep-invariant of this object
     * @return whether the rep is ok or not
     */
    private boolean checkRep() {
        if( connectedBoardName != null && !connected ) return false;
        if( connectedBoardName == null && connected ) return false;
        if( !( ( location.x() == -1 && location.y() == -1 ) ||
               ( location.x() == -1 && location.y() == board.getHeight() ) ||
               ( location.x() == board.getWidth() && location.y() == -1 ) ) ) return false;
        return true;
    }
    
    /**
     * Returns the grid location of the top left corner of the wall.
     * 
     * @return The grid location.
     */
    @Override public final GridLocation getLocation() {
        assert(checkRep());
        return location;
    }
    

    /**
     * Connect the wall to a particular board. 
     * 
     * Balls hitting the wall will pass through this wall into the board.
     * 
     * @param name Name of the board to which this wall is connected.
     */
    public void connect(String name) {
        this.connectedBoardName = name;
        this.connected = true;
        assert(checkRep());
    }

    /**
     * Disconnect the wall from any boards. 
     * 
     * Balls hitting the wall will now reflect from the wall.
     */
    public void disconnect() {
        this.connectedBoardName = null;
        this.connected = false;
        assert(checkRep());
    }

    /**
     * Evolve the wall for a specified period of time.
     * 
     * For a wall, this does nothing.
     */
    @Override public void evolve(double time) {
        // do nothing
    }

    /**
     * Collide a ball with a particular collidable in the wall.
     * 
     * If the wall is connected to another board, the ball passes
     * through the wall into the other board and is removed from this board. 
     * If the wall is not connected, the ball is reflected from the wall.
     * 
     * @param ball The ball that collides
     * @param collidable The collidable with which the ball collides
     */
    @Override public void collide(Ball ball, Collidable collidable) {
        if (connected) {
            board.removeBall(ball);
            try {
                Vect center = ball.getCircle().getCircle().getCenter();
                Vect velocity = ball.getVelocity();
                sendQueue.put(String.format("ball %s %s %f %f %f %f",
                    ball.getName(), side.toString(), center.x(), center.y(),
                    velocity.x(), velocity.y()));
                ball.setCenter(new Vect(-1,-1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            super.collide(ball, collidable);
        }
        assert(checkRep());
    }
    
    /**
     * Returns a list of strings for the string representation of the square bumper.
     * 
     * Grid representation of a unit length of the wall is "."
     * For a horizontal wall, we have this repeated width number of times in one string.
     * For a vertical wall, we have height number of such "." in the list.
     * 
     * @return the grid representation
     */
    @Override public List<String> gridRepresentation() {
        assert(checkRep());
        switch (side) {
            case TOP: 
            case BOTTOM: 
                return gridRepresentationHorizontal();
            case LEFT:
            case RIGHT:
            default:
                return gridRepresentationVertical();
        }
    }
    
    private List<String> gridRepresentationHorizontal() {
        int wallWidth = 1 + board.getWidth() + 1;
        if( connected ) {
            String shownName = connectedBoardName.length() > wallWidth ? connectedBoardName.substring(0, wallWidth) : connectedBoardName;
            int leftDots = (wallWidth - shownName.length()) / 2;
            int rightDots = (wallWidth - shownName.length() + 1) / 2;
            assert(checkRep());
            return Arrays.asList(StringUtils.join("", StringUtils.repeat(".", leftDots), shownName, StringUtils.repeat(".", rightDots)));
        } else {
            assert(checkRep());
            return Arrays.asList(StringUtils.repeat(".", wallWidth));
        }
    }
    
    private List<String> gridRepresentationVertical() {
        int wallHeight = 1 + board.getHeight() + 1;
        List<String> gridRepresentation = new ArrayList<String>();
        if( connected ) {
            assert connectedBoardName != null;
            String shownName = connectedBoardName.length() > wallHeight ? connectedBoardName.substring(0, wallHeight) : connectedBoardName;
            int topDots = (wallHeight - shownName.length()) / 2;
            int bottomDots = (wallHeight - shownName.length() + 1) / 2;
            for(int row = 0; row < topDots; row ++ ) gridRepresentation.add(".");
            for(int row = 0; row < shownName.length(); row ++ ) gridRepresentation.add(String.valueOf(shownName.charAt(row)));
            for(int row = 0; row < bottomDots; row ++ ) gridRepresentation.add(".");  
        } else {
            for(int row = 0; row < wallHeight; row ++ ) {
                gridRepresentation.add(".");
            }
        }
        assert(checkRep());
        return gridRepresentation;
    }
    
    /**
     * Returns a set of DrawObject representation of this instance 
     * 
     * @param the ratio of the representation in pixel/L
     * 
     * @return the set of DrawObject representation
     */
    @Override public Set<DrawObject> uiRepresentation(double ratio) {
        Set<DrawObject> result = new HashSet<>();
        Shape uiRepresentation;
        switch (side) {
            case TOP: uiRepresentation = new Rectangle2D.Double(0, 0, board.getWidth()*ratio, Constants.WALL_THICKNESS*ratio); break;
            case BOTTOM: uiRepresentation = new Rectangle2D.Double(0, (board.getHeight()-Constants.WALL_THICKNESS)*ratio, board.getWidth()*ratio, Constants.WALL_THICKNESS*ratio); break;
            case LEFT: uiRepresentation = new Rectangle2D.Double(0, 0, Constants.WALL_THICKNESS*ratio, board.getHeight()*ratio); break;
            case RIGHT: 
            default: uiRepresentation = new Rectangle2D.Double((board.getWidth()-Constants.WALL_THICKNESS)*ratio, 0, Constants.WALL_THICKNESS*ratio, board.getHeight()*ratio); break;
        }
        if( !connected ) {
            result.add(new DrawObject(layer, uiRepresentation, GameObjectType.WALL, 1.0));
        }
        assert(checkRep());
        return result;
    }
    
    /**
     * Returns a string name of the board that this wall connects to. Returns an empty string if it is not connecting. Note
     * that it returns in html string format
     * 
     * @return string name of the board that this wall connects to.
     */
    public String wallConnection() {
        assert(checkRep());
        if( connected ) {
            switch(side) {
                case TOP:
                case BOTTOM:
                    return connectedBoardName;
                case LEFT:
                case RIGHT:
                default:
                    return "<html>" + StringUtils.join("<br>", connectedBoardName.split("")) + "</html>";
            }
        } else {
            return "";
        }
    }
}
