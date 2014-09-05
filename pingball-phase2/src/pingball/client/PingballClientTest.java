package pingball.client;


/*
 * Testing Strategy:
 *
 * We tested the pingball client's components (such as board, etc) using unit
 * testing strategies. To test the pingball client itself, which has mostly
 * graphical capabilities and display capabilities, we manually tested
 * it in both single player mode and multi player mode in the console.
 * This is because certain types of graphical capabilities cannot be unit
 * tested easily, but they should still be tested (so we did it manually).
 *
 * We tested the following configurations using the boards in our test resources
 * directory (including the custom boards that we designed).
 *
 * All the boards in single player mode, run for several minutes, and a specially
 * designed stress testing board (with tens of balls), run for several hours.
 *
 * Boards connected to themselves with two or four walls connected
 * to the same board.
 *
 * Two boards connected to each other by a single common wall.
 *
 * Two boards connected to each other by two pairs of walls (in a loop shape).
 *
 * Two boards connected to each other by a wall and also connected to themselves
 * (for a separate wall).
 *
 * Clients suddenly disconnecting from other clients (by a SIGINT) to make sure
 * that the other client receives a disconnect message from the server as soon
 * as it is recognized that a client has terminated.
 *
 * Three boards being connected to each other with two common walls between each
 * pair of boards (to form a loop).
 *
 * Many pairs of boards connected in random configurations (with self-joined walls,
 * and also walls joined to other boards).
 *
 * Multiple pairs of clients connected to the server, but with disjoint sets of joined
 * walls (multiple separate sets of boards interacting with each other).
 *
 * Clients disconnecting from and then reconnecting to the server, and then manually
 * reattaching them to other boards.
 */
public class PingballClientTest {

}
