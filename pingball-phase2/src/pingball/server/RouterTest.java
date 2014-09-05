package pingball.server;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * Testing Strategy:
 *
 * The router cannot really be tested directly, because it makes
 * use of socket mappings and such. For this reason, we have tested
 * the router in combination with the server in PingballServerTest.
 * This is reasonable because the router class is explicitly designed
 * for the pingball server, and they are very closely related, so we
 * can test them together.
 */
public class RouterTest {

}
