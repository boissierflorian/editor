package org.ulco;

public class Rectangle extends GraphicsObject {
    public Rectangle() {
        this(new Point(0,0), 1, 1);
    }

    public Rectangle(Point center, double height, double width) {
        this.m_origin = center;
        this.m_height = height;
        this.m_width = width;
    }

    public Rectangle(String json) {
        String str = json.replaceAll("\\s+","");
        int centerIndex = str.indexOf("center");
        int heightIndex = str.indexOf("height");
        int widthIndex = str.indexOf("width");
        int endIndex = str.lastIndexOf("}");

        m_origin = new Point(str.substring(centerIndex + 7, heightIndex - 1));
        m_height = Double.parseDouble(str.substring(heightIndex + 7, widthIndex - 1));
        m_width = Double.parseDouble(str.substring(widthIndex + 6, endIndex));
    }

    public GraphicsObject copy() {
        return new Rectangle(m_origin.copy(), m_height, m_width);
    }

    public Point getOrigin() { return m_origin; }

    public boolean isClosed(Point pt, double distance) {
        Point center = new Point(m_origin.getX() + m_width / 2, m_origin.getY() + m_height / 2);
        return MathUtils.distanceIsLowerThan(center, pt, distance);
    }

    void move(Point delta) { m_origin.move(delta); }

    public String toJson() {
        return "{ type: rectangle, center: " + m_origin.toJson() + ", height: " + this.m_height + ", width: " + this.m_width + " }";
    }

    public String toString() {
        return "rectangle[" + m_origin.toString() + "," + m_height + "," + m_width + "]";
    }

    protected Point m_origin;
    protected double m_height;
    protected double m_width;
}