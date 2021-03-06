package test;

import junit.framework.TestCase;
import org.junit.Test;
import org.ulco.*;

public class DocumentTest extends TestCase {

    @Test
    public void testSelect() throws Exception {
        Document document = new Document();
        Layer layer = document.createLayer();
        Circle c = new Circle(new Point(2, 8), 10);

        layer.add(c);

        assertTrue(GraphicsObjectUtils.select(document, new Point(1,1), 8).size() == 1);
        assertTrue(GraphicsObjectUtils.select(document, new Point(1,1), 8).firstElement().getID() == c.getID());
    }

    @Test
    public void testSelect2() throws Exception {
        Document document = new Document();
        Layer layer = document.createLayer();
        Circle c = new Circle(new Point(2, 8), 10);
        Square s = new Square(new Point(-2, -3), 3);

        layer.add(c);
        layer.add(s);

        assertTrue(GraphicsObjectUtils.select(document, new Point(1,1), 8).size() == 2);
    }

    @Test
    public void testSelect3() throws Exception {
        Document document = new Document();
        Layer layer = document.createLayer();
        Square square = new Square(new Point(0, 0), 5);
        Rectangle rectangle = new Rectangle(new Point(50, 50), 5, 10);
        Rectangle rectangle2 = new Rectangle(new Point(5, 5), 5, 5);
        Group group = new Group();
        Circle c = new Circle(new Point(3, 3), 3);

        group.add(square);
        group.add(rectangle);
        group.add(rectangle2);

        layer.add(group);
        layer.add(c);

        assertEquals(3, GraphicsObjectUtils.select(document, new Point(1, 1), 9.5).size());
    }

    @Test
    public void testJSON() throws Exception {
        Document document = new Document();
        Layer l1 = document.createLayer();
        Square s = new Square(new Point(0, 0), 5);
        Circle c1 = new Circle(new Point(5, 5), 4);
        Layer l2 = document.createLayer();
        Rectangle r = new Rectangle(new Point(-5, 1), 4, 2);
        Circle c2 = new Circle(new Point(-4, 8), 1);

        l1.add(s);
        l1.add(c1);
        l2.add(r);
        l2.add(c2);
        assertEquals(document.toJson(), "{ type: document, layers: { { type: layer, objects : { { type: square, center: " +
                "{ type: point, x: 0.0, y: 0.0 }, length: 5.0 }, { type: circle, center: { type: point, x: 5.0, y: 5.0 }" +
                ", radius: 4.0 } } }, { type: layer, objects : { { type: rectangle, center: { type: point, x: -5.0, y: 1.0 }" +
                ", height: 4.0, width: 2.0 }, { type: circle, center: { type: point, x: -4.0, y: 8.0 }, radius: 1.0 } } } } }");
    }

    @Test
    public void testJSON2() throws Exception {
        Document document = new Document();
        Layer l1 = document.createLayer();
        Square s = new Square(new Point(0, 0), 5);
        Circle c1 = new Circle(new Point(5, 5), 4);
        Layer l2 = document.createLayer();
        Rectangle r = new Rectangle(new Point(-5, 1), 4, 2);
        Circle c2 = new Circle(new Point(-4, 8), 1);

        l1.add(s);
        l1.add(c1);
        l2.add(r);
        l2.add(c2);
        assertEquals(document.toJson(), "{ type: document, layers: { { type: layer, objects : { { type: square, center: " +
                "{ type: point, x: 0.0, y: 0.0 }, length: 5.0 }, { type: circle, center: { type: point, x: 5.0, y: 5.0 }" +
                ", radius: 4.0 } } }, { type: layer, objects : { { type: rectangle, center: { type: point, x: -5.0, y: 1.0 }" +
                ", height: 4.0, width: 2.0 }, { type: circle, center: { type: point, x: -4.0, y: 8.0 }, radius: 1.0 } } } } }");
    }

    @Test
    public void testConstructorGrid() throws Exception {
        Document document = DocumentFactory.createSquareGrid(new Point(0, 0), 3, 3, 5);
        assertEquals(document.getObjectNumber(), 9);
        assertEquals(document.getLayerNumber(), 1);
    }

    @Test
    public void testConstructorCircle() throws Exception {
        assertEquals(4, DocumentFactory.createConcentricCircles(new Point(0, 0), 4,
                3., 4.).getObjectNumber());
    }
}