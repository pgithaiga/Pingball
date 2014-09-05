package pingball.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class NetworkTestUtils {

    public static final int MAX_TRIES = 10;

    public static final long DELAY_RATIO = 10; // msec

    public static final int IO_TIMEOUT = 3000; // msec

    public static final int SLEEP_DELAY = 500; // msec

    public static final String HOST = "localhost";

    public static Socket connect(int port) {
        Socket socket = null;
        int attempts = 0;
        do {
            try {
                socket = new Socket(HOST, port);
            } catch (IOException ioe) {
                if (++attempts > MAX_TRIES) {
                    throw new RuntimeException("exceeded max connection attempts", ioe);
                }
                try {
                    Thread.sleep(attempts * DELAY_RATIO);
                } catch (InterruptedException ie) {
                    // ignore
                }
            }
        } while (socket == null);
        try {
            socket.setSoTimeout(IO_TIMEOUT);
        } catch (SocketException se) {
            throw new RuntimeException(se);
        }
        return socket;
    }

    public static void sendln(Socket destination, String message) {
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(destination.getOutputStream()), true);
            pw.println(message);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static BufferedReader reader(Socket source) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(source.getInputStream()));
            return br;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(SLEEP_DELAY);
        } catch (InterruptedException ie) {
            // ignore
        }
    }

}
