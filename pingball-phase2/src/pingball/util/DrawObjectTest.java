package pingball.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.junit.Test;

import pingball.simulation.GameObjectType;
import pingball.ui.colorscheme.UIColorSchemes;
import pingball.util.DrawObject.Layer;

/*
 * Testing Strategy:
 *
 * Partition based on the layer, the objecttype, and the brightlevel. Especially try birghtlevel
 * in the case where the number is out of bound
 */
public class DrawObjectTest {
    
    @Test public void testBallLayerBallTypeBrightZero() {
        DrawObject d = new DrawObject(Layer.BALL, new Polygon(), GameObjectType.BALL, 0.0);
        assertEquals(d.getLayer(), Layer.BALL);
        assertEquals(d.getFillColor(), Color.WHITE);
    }
    
    @Test public void testAbsorberLayerFGTypeBrightNegative() {
        DrawObject d = new DrawObject(Layer.GADGET_FG, new Polygon(), GameObjectType.ABSORBER, -10.2);
        assertEquals(d.getLayer(), Layer.GADGET_FG);
        assertEquals(d.getFillColor(), Color.WHITE);
    }
    
    @Test public void testWallLayerBGTypeBrightOne() {
        DrawObject d = new DrawObject(Layer.GADGET_BG, new Polygon(), GameObjectType.WALL, 1);
        assertEquals(d.getLayer(), Layer.GADGET_BG);
        assertEquals(d.getFillColor(), UIColorSchemes.getFillColor(GameObjectType.WALL));
    }
    
    @Test public void testFlipperLayerWallTypeBrightHighPositive() {
        DrawObject d = new DrawObject(Layer.FLIPPER, new Polygon(), GameObjectType.FLIPPER, 112312312.3454);
        assertEquals(d.getLayer(), Layer.FLIPPER);
        assertEquals(d.getFillColor(), UIColorSchemes.getFillColor(GameObjectType.FLIPPER));
    }

}
