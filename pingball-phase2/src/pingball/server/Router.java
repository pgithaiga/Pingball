package pingball.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pingball.simulation.Wall;
import pingball.util.BidirectionalMap;
import pingball.util.Pair;
import pingball.util.StringUtils;

/** A thread-safe mutable routing to keep track of connections between walls.
 *
 * Sockets passed into this class must not be used for outputting data anywhere
 * else. Clients of this class are allowed to read from sockets that are passed
 * in.
 *
 * Representation invariant:
 *
 * The router keeps links of walls of boards to other walls. In order for the
 * system to function correctly (and have valid meaning), these links must be
 * bidirectional. The links in wallLinkMap have to be bidirectional at all
 * times. If a link such as (a, left) -> b is in the wallLinkMap, then the
 * opposite link (b, right) -> a must be present in the map.
 *
 * Thread safety argument:
 *
 * This class is meant to be shared between threads. All methods on router are
 * synchronized, which will guarantee thread safety.
 */
public class Router {
    
    private final BidirectionalMap<Socket, String> mapSocketName = new BidirectionalMap<>();
    
    // store bidirectional links in here like:
    // (a, left) -> b
    // (b, right) -> a
    private final Map<Pair<String, Wall.Side>, String> wallLinkMap = new HashMap<>();
    
    // Pair<Socket, portalName> -> <Name otherBoard, portalName>
    private final Map<Pair<Socket, String>, Pair<String, String>> portalMap = new HashMap<>();
    
    //map each client socket to its list of portals
    private final Map<Socket, Set<String>> portalList = new HashMap<>();
    
    
    /**
     * Make a Router.
     */
    public Router() {
        //Do nothing
    }
    
    /**
     * Removes a client from the routing system.
     *
     * @param caller The user to remove.
     *
     * This method must be called when a user is removed from the system.
     */
    public synchronized void removeUser(Socket caller) {
        if( mapSocketName.containsForward(caller) ) {
            String user = mapSocketName.getForward(caller);
            
            for (Wall.Side wall: Wall.Side.values()) {
                removeWallLinkOf(user, wall);
            }
            
            mapSocketName.removeForward(caller);
        }
        if( portalList.containsKey(caller) ) {
            portalList.remove(caller);
        }
        checkRep();
    }

    public synchronized void send(Socket socket, String message) {
        try {
            System.out.println(message);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
        } catch (IOException e) {
            // ignore
        }
    }
    
    public synchronized Set<String> getUsers() {
        checkRep();
        return new HashSet<>(mapSocketName.valueSet());
    }

    /**
     * Processes a message from the client.
     *
     * @param message The message to process.
     *
     * @param caller The recipient of the message.
     */
    public synchronized void processClientMessage(String message, Socket caller) {
        String[] split = message.split(" ");

        String hellomsg = "^hello [A-Za-z_][A-Za-z_0-9]*$";
        String ballmsg = "^ball [A-Za-z_][A-Za-z_0-9]* (left|right|top|bottom)( -?(?:[0-9]+\\.[0-9]*|\\.?[0-9]+)){4}$";
        String portalConnectRegmsg = "^portal-register-connected [A-Za-z_][A-Za-z_0-9]* [A-Za-z_][A-Za-z_0-9]* [A-Za-z_][A-Za-z_0-9]*$";
        String portalLocalRegmsg = "^portal-register-local [A-Za-z_][A-Za-z_0-9]*$";
        String portalSendBallmsg = "^warp [A-Za-z_][A-Za-z_0-9]* [A-Za-z_][A-Za-z_0-9]*( -?(?:[0-9]+\\.[0-9]*|\\.?[0-9]+)){2}$";
        //String warpmsg = "^warp [A-Za-z_][A-Za-z_0-9]* [A-Za-z_][A-Za-z_0-9]*( -?(?:[0-9]+\\.[0-9]*|\\.?[0-9]+)){2}$";
        
        if (message.matches(hellomsg)) {
            addUser(split[1], caller);
        } else if (message.matches(ballmsg)) {
            String clientName = mapSocketName.getForward(caller);
            Wall.Side wall = Wall.Side.fromString(split[2]);
            Socket dest = mapSocketName.getReverse(wallLinkMap.get(Pair.of(clientName, wall)));
            if (dest != null) {
                split[2] = wall.opposite().toString();
                send(dest, StringUtils.join(" ", split));
            }
        }else if(message.matches(portalLocalRegmsg)){
            addPortalToClient(caller,split[1]);
            
        }else if(message.matches(portalConnectRegmsg)){
            addPortalToClient(caller,split[1]);
            addPortalConnection(caller,split[1],split[2],split[3]);
        
        }else if(message.matches(portalSendBallmsg)){
            if(portalMap.containsKey(Pair.of(caller, split[1]))){
                String clientName = portalMap.get(Pair.of(caller, split[1])).getFirst();
                String targetName = portalMap.get(Pair.of(caller, split[1])).getSecond();
                
                Socket dest = mapSocketName.getReverse(clientName);
                if(dest != null && portalList.get(dest).contains(targetName)){
                    System.out.println("GG");
                    send(dest, "warp " + StringUtils.join(" ", new String[]{targetName,split[2],split[3],split[4]}));
                } else {
                    send(caller, "warp " + StringUtils.join(" ", new String[]{split[1],split[2],split[3],split[4]}));
                }
            }
            else{
                send(caller, "warp " + StringUtils.join(" ", new String[]{split[1],split[2],split[3],split[4]}));
            }
            
        }else {
            System.err.println("ignoring invalid message from client");
        }
        checkRep();
    }
    
    

    /**
     * Links two boards together horizontally.
     *
     * This breaks any preexisting connections if necessary.
     *
     * @param left The left board to link.
     *
     * @param right The right board to link.
     *
     * @return Whether the boards were successfully linked.
     */
    public synchronized boolean addConnectionHorizontal(String left, String right) {
        if (mapSocketName.getReverse(left) == null || mapSocketName.getReverse(right) == null) {
            checkRep();
            return false;
        }
        removeWallLinkOf(left, Wall.Side.RIGHT);
        removeWallLinkOf(right, Wall.Side.LEFT);
        
        wallLinkMap.put(Pair.of(left, Wall.Side.RIGHT), right);
        sendMessageMergeWall(mapSocketName.getReverse(left), Wall.Side.RIGHT, right);
        
        wallLinkMap.put(Pair.of(right, Wall.Side.LEFT), left);
        sendMessageMergeWall(mapSocketName.getReverse(right), Wall.Side.LEFT, left);
        checkRep();
        return true;
    }

    /**
     * Links two boards together vertically.
     *
     * This breaks any pre-existing connections if necessary.
     *
     * @param top The top board to link.
     *
     * @param bottom The bottom board to link.
     *
     * @return Whether the boards were successfully linked.
     */
    public synchronized boolean addConnectionVertical(String top, String bottom) {
        if (mapSocketName.getReverse(top) == null || mapSocketName.getReverse(bottom) == null) {
            checkRep();
            return false;
        }
        removeWallLinkOf(top, Wall.Side.BOTTOM);
        removeWallLinkOf(bottom, Wall.Side.TOP);
        
        wallLinkMap.put(Pair.of(top, Wall.Side.BOTTOM), bottom);
        sendMessageMergeWall(mapSocketName.getReverse(top), Wall.Side.BOTTOM, bottom);
        
        wallLinkMap.put(Pair.of(bottom, Wall.Side.TOP), top);
        sendMessageMergeWall(mapSocketName.getReverse(bottom), Wall.Side.TOP, top);
        checkRep();
        return true;
    }
    
    /**
     * Adds a portal to the set of the caller's portals
     * 
     * @param caller The socket caller
     * 
     * @param portalName The portal to be added to the caller's list of portals
     */
    private synchronized void addPortalToClient(Socket caller, String portalName){
        if(portalList.containsKey(caller)){
            if(!portalList.get(caller).contains(portalName)){
                portalList.get(caller).add(portalName);
            }
        }else{
            portalList.put(caller, new HashSet<String>());
            portalList.get(caller).add(portalName);
        }
    }
    
    /**
     * Adds a connection between sourcePortal and targetPortal
     * 
     * @param caller The socket of the caller
     * 
     * @param sourcePortal The source portal
     * 
     * @param otherBoardName The board containing the target portal
     * 
     * @param targetPortal The target portal
     */
    private synchronized void addPortalConnection(Socket caller, String sourcePortal, String otherBoardName, String targetPortal){
        portalMap.put(Pair.of(caller, sourcePortal), Pair.of(otherBoardName, targetPortal));
    }
    
    /**
     * Remove the merging wall of that user on the corresponding side
     * 
     * @param user the name of user
     * 
     * @param side the side of the wall
     */
    private synchronized void removeWallLinkOf(String user, Wall.Side side) {
        if( wallLinkMap.containsKey(Pair.of(user, side)) ) {
            String anotherUser = wallLinkMap.get(Pair.of(user, side));
            
            wallLinkMap.remove(Pair.of(user, side));
            sendMessageReturnToWall(mapSocketName.getReverse(user), side);
            
            removeWallLinkOf(anotherUser, side.opposite());
        }
    }
    
    /**
     * Adds a client to the routing system.
     *
     * @param name The name of the board.
     *
     * @param socket The socket corresponding to the client.
     */
    private synchronized void addUser(String name, Socket socket) {
        if( !mapSocketName.containsReverse(name) ) {
            mapSocketName.putForward(socket, name);
        }
        checkRep();
    }
    
    /**
     * Send message to the user to make one side of his wall to wall (not a user name anymore)
     * 
     * @param socket The socket of client
     * 
     * @param side The side of the wall
     */
    private synchronized void sendMessageReturnToWall(Socket socket, Wall.Side side) {
        send(socket, String.format("disconnect %s", side.toString()));
    }
    
    /**
     * Send message to the user to make one side of his wall merge with the corresponding name
     * 
     * @param socket The socket of client
     * 
     * @param side The side of the wall
     * 
     * @param name The name of another user 
     */
    private synchronized void sendMessageMergeWall(Socket socket, Wall.Side side, String name) {
        send(socket, String.format("connect %s %s", side.toString(), name));
    }
    
    //TODO: add check for portals
    private synchronized void checkRep() {
        // only run if assertions are enabled
        boolean assertionsEnabled = false;
        assert assertionsEnabled = true; // assignment
        if (assertionsEnabled) {
            for (Pair<String, Wall.Side> p: wallLinkMap.keySet()) {
                String otherBoard = wallLinkMap.get(p);
                Wall.Side oppositeSide = p.getSecond().opposite();
                assert wallLinkMap.containsKey(Pair.of(otherBoard, oppositeSide));
            }
        }
    }
    
}
