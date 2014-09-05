package pingball.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pingball.grammars.BoardGrammarCentral;
import pingball.simulation.Board;
import pingball.simulation.Wall;
import pingball.ui.ClientGUI;
import pingball.util.DrawObject;

/**
 * When the main static method is called. This class pass all arguments to the main method of ui.ClientGUI,
 * which will instantiate UI component. Once the ui wants to create a pingball game instance (after the user
 * press 'play button', the ui will create an instance of this class. 
 * 
 * Client for a Pingball simulator.
 *
 * This client supports operation in single player mode and multiplayer
 * mode, where it can connect to a server.
 *
 * Thread safety argument:
 *
 * PingballClient will spawn two background threads
 * for sending and receiving messages from the server. Messages can include
 * adding/removing of balls, adding/removing transparent walls, etc. If the player
 * is not playing multiplayer, ther will be instead a thread for recyclying messages
 *
 * Each of the background threads will have a blocking queue shared with the
 * main render thread, for sending messages to and from the server.
 *
 * The main render method will be called by the GUI, and at every frame render, it will poll to see if there
 * are any messages available from the background thread and perform the
 * corresponding actions. When there are messages to send to the server,
 * the messages will be put in the queue for the thread responsible for sending
 * messages to the server, and this background thread will perform the actual
 * sending operation. Note that all methods in this class will only be called by the event dispatch thread
 * and these methods never block. Thus, the system is threadsafe.
 */
public class PingballClient {

    private static final int DEFAULT_PORT = 10987;
    
    private Board board = null;
    
    private boolean multiPlayer = false;
    
    private final LinkedBlockingQueue<String> sendQueue = new LinkedBlockingQueue<>();
    
    private final LinkedBlockingQueue<String> receiveQueue = new LinkedBlockingQueue<>();
    
    private Socket socket;
    
    private Thread sender = null, receiver = null, cycler = null;
    
    public static void main(String[] args) throws IOException {
        ClientGUI.main(args);
    }
    
    /**
     * Start a PingballClient using the given arguments.
     *
     * Usage: PingballClient [--port PORT] [--host HOST] FILE
     *
     * The optional [--port PORT] argument is the port to connect to on the server.
     *
     * The optional [--host HOST] argument is the host to connect to.
     *
     * The FILE argument is the board file to read from.
     */
    public PingballClient(String[] args) throws IOException {
        File file = null;
        String host = null;
        Integer port = DEFAULT_PORT;

        Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
        try {
            while (!arguments.isEmpty()) {
                String flag = arguments.remove();
                try {
                    if (flag.equals("--port")) {
                        port = Integer.parseInt(arguments.remove());
                        if (port < 0 || port > 65535) {
                            throw new IllegalArgumentException("port " + port + " out of range");
                        }
                    } else if (flag.equals("--host")) {
                        host = arguments.remove();
                    } else if (file == null) {
                        file = new File(flag);
                        if (!file.isFile()) {
                            throw new IllegalArgumentException("file not found: \"" + file + "\"");
                        }
                    } else {
                        throw new IllegalArgumentException("unknown option: \"" + flag + "\"");
                    }
                } catch (NoSuchElementException nsee) {
                    throw new IllegalArgumentException("missing argument for " + flag);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("unable to parse number for " + flag);
                }
            }
            if (file == null) {
                throw new IllegalArgumentException("missing positional argument FILE");
            }
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            System.err.println("usage: PingballClient [--host HOST] [--port PORT] FILE");
            return;
        }
        
        BoardGrammarCentral bgc = new BoardGrammarCentral();
        board = bgc.parse(file, sendQueue, receiveQueue);

        if (host != null) {
            multiPlayer = true;
            socket = new Socket(host, port);
            receiver = new Thread(new Receiver(socket, receiveQueue));
            receiver.start();
            sender = new Thread(new Sender(socket, sendQueue));
            sender.start();
        } else {
            cycler = new Thread(new Cycler(receiveQueue, sendQueue));
            cycler.start();
        }
    }
    
    /**
     * Ends the game. Shuts down all threads relating to this client and close the socket if applicable
     */
    public void endGame() {
        if( multiPlayer ) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            receiver.interrupt();
            sender.interrupt();
        } else {
            cycler.interrupt();
        }
    }

    /**
     * Evolve the board for a frame.
     */
    public void evolveFrame(double time) {
        List<String> messages = new ArrayList<String>();
        receiveQueue.drainTo(messages);
        for (String message: messages) {
            board.processMessage(message);
        }
        board.evolve(time);
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
        return board.getWallConnection(side);
    }
    
    /**
     * Returns a set of DrawObject representation of this instance 
     * 
     * @param the ratio of the representation in pixel/L
     * 
     * @return the set of DrawObject representation
     */
    public Set<DrawObject> uiRrepresentation(double ratio) {
        return board.uiRepresentation(ratio);
    }
    
    /**
     * Performs the action of keyUp on the param keyText to the board
     * 
     * @param keyText the text describing the key, must be the same as a java
     *  key description
     */
    public void keyUp(String keyText) {
        try {
            receiveQueue.put(String.format("keyup@%s", keyText));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Performs the action of keyDown on the param keyText to the board
     * 
     * @param keyText the text describing the key, must be the same as a java
     *  key description
     */
    public void keyDown(String keyText) {
        try {
            receiveQueue.put(String.format("keydown@%s", keyText));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * A receiver to asynchronously receive data from a socket.
     */
    private static class Receiver implements Runnable {

        private final Socket socket;
        private final BlockingQueue<String> receiveQueue;

        /**
         * Make a Receiver.
         *
         * @param socket The socket to receive from.
         *
         * @param receiveQueue The queue to put received data into.
         */
        public Receiver(Socket socket, BlockingQueue<String> receiveQueue) throws IOException {
            this.socket = socket;
            this.receiveQueue = receiveQueue;
        }

        /**
         * Receive data from the socket and put it into the queue.
         */
        public void run() {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                for (String line = input.readLine(); line != null && !Thread.currentThread().isInterrupted(); line = input.readLine()) {
                    receiveQueue.put(line);
                }
            } catch (InterruptedException | IOException e) {
                // Interrupt expected. Do nothing
            }
        }

    }

    /**
     * A sender to asynchronously send data to a socket.
     */
    private static class Sender implements Runnable {

        private final Socket socket;
        private final BlockingQueue<String> sendQueue;

        /**
         * Make a Sender.
         *
         * @param socket The socket to receive from.
         *
         * @param sendQueue The queue to put received data into.
         */
        public Sender(Socket socket, BlockingQueue<String> sendQueue) throws IOException {
            this.socket = socket;
            this.sendQueue = sendQueue;
        }

        /**
         * Send data from the socket from the queue.
         */
        public void run() {
            try (PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                while (!Thread.currentThread().isInterrupted()) {
                    String line = sendQueue.take();
                    output.println(line);
                }
            } catch (InterruptedException | IOException e) {
                // Interrupt expected. Do nothing
            }
        }

    }
    
    /**
     * A cycle to asynchronously cycle data between sendQueue and receiveQueue in case of playing locally.
     */
    private static class Cycler implements Runnable {

        private final BlockingQueue<String> receiveQueue;
        private final BlockingQueue<String> sendQueue;
        
        /**
         * Make a Cycler
         * 
         * @param receiveQueue the receiveQueue of this client
         * @param sendQueue the sendQueue of this client
         */
        public Cycler(BlockingQueue<String> receiveQueue, BlockingQueue<String> sendQueue) throws IOException {
            this.receiveQueue = receiveQueue;
            this.sendQueue = sendQueue;
        }
        
        /**
         * Drain data from sendQueue and put it back to receiveQueue in case it is a warp command
         */
        public void run() {
            while( !Thread.currentThread().isInterrupted() ) {
                try {
                    String message = sendQueue.take();
                    if( message.split(" ")[0].equals("warp") ) {
                        receiveQueue.put(message);
                    }
                } catch (InterruptedException e) {
                    // Interrupt expected. Do nothing
                }
            }
        }

    }
}
