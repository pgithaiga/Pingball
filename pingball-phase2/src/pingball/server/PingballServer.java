package pingball.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import pingball.util.StringUtils;

/*
 * Thread safety argument:
 * The server will have a background thread for
 * accepting connection requests for clients, and create a set of two pop-up
 * threads to handle each client. One thread will be responsible for reading
 * messages from the client, and the other will be responsible for sending
 * messages to the client. Each of these threads will be reading from / writing
 * to a blocking queue. The server will also have a thread for reading input
 * from the command line.
 *
 * All threads interacting with clients will have access to a routing object,
 * which will store references to each clients' blocking queues. When routings
 * are to be updated, the routing object will be updated appropriately. This
 * routing object will have methods on it to route messages to the appropriate
 * destinations.
 *
 * In order to ensure correct operation, the routing object will be
 * thread-safe.
 */
public class PingballServer {

    private final ServerSocket serverSocket;
    private final Router router;
    private static final int DEFAULT_PORT = 10987;

    /**
     * Make a pingball server
     * 
     * @param port the port through which the clients and the server will be communicating
     */
    public PingballServer(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        router = new Router();
    }

    public void runServer() throws IOException {
        Thread hoster = new Thread(new Hoster(serverSocket, router));
        hoster.start();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            for (String line = in.readLine(); line != null; line = in.readLine()){
                handleCommand(line);
            }
        }
    }
    
    /**
     * Start a PingballServer using the given arguments.
     * 
     * Usage: PingballServer [--port PORT]
     * 
     * The optional [--port PORT] argument is the port that the client will connect to
     */
    public static void main(String[] args) throws IOException {
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
                    } else {
                        throw new IllegalArgumentException("unknown option: \"" + flag + "\"");
                    }
                } catch (NoSuchElementException nsee) {
                    throw new IllegalArgumentException("missing argument for " + flag);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("unable to parse number for " + flag);
                }
            }
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            System.err.println("usage: PingballServer [--port PORT]");
            return;
        }
        PingballServer server = new PingballServer(port);
        System.out.println("Running on port " + port);
        server.runServer(); 
    }
    
    /**
     * A hoster that accept an client connection and create a socket to receive messages
     */
    private static class Hoster implements Runnable {

        private final ServerSocket serverSocket;
        private final Router router;
        
        /**
         * Make a Hoster
         * 
         * @param serverSocket the server socket of this server
         * 
         * @param router the router of the server
         */
        public Hoster(ServerSocket serverSocket, Router router) {
            this.serverSocket = serverSocket;
            this.router = router;
        }
        
        /**
         * Lock until a client connects. Then create a socket for him/her and start a new HandleClient thread.
         */
        public void run() {
            while(true) {
                try {
                    Socket socket = serverSocket.accept();
                    Thread newClient = new Thread(new HandleClient(socket, router));
                    newClient.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * A handle client class that will be responsible for receive the messages from its client
     */
    private static class HandleClient implements Runnable {

        private final Socket socket;
        private final Router router;
        
        /**
         * Make a HandleClient
         * 
         * @param socket the socket of this client
         * 
         * @param router the router of the server
         */
        public HandleClient(Socket socket, Router router) {
            this.socket = socket;
            this.router = router;
        }
        
        /**
         * Lock until a new message comes. Then pass the message to the router to process the message
         */
        public void run() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    router.processClientMessage(line, socket);
                }
            } catch (IOException e) {
                // socket closed, this is okay
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // ignore
                }
                router.removeUser(socket);
            }
        }

    }
    
    protected synchronized void handleCommand(String line) {
        String joincmd = "^\\s*[hv]\\s+[A-Za-z_][A-Za-z_0-9]*\\s+[A-Za-z_][A-Za-z_0-9]*\\s*$";
        String lscmd = "^\\s*ls\\s*$";
        if (line.matches(joincmd)) {
            String[] split = line.trim().split("\\s+");
            final boolean success;
            if (split[0].equals("h")) {
                success = router.addConnectionHorizontal(split[1], split[2]);
            } else {
                success = router.addConnectionVertical(split[1], split[2]);
            }
            if (!success) {
                System.out.println("error: unable to join boards");
            }
        } else if (line.matches(lscmd)) {
            List<String> clients = new ArrayList<>(router.getUsers());
            if (clients.isEmpty()) {
                System.out.println("no boards connected");
            }
            else {
                System.out.println("connected boards:");
                System.out.println(StringUtils.join("\n", clients));
            }
        } else {
            System.out.println("error: invalid command");
        }
    }

    protected synchronized int getPort() {
        return serverSocket.getLocalPort();
    }

}
