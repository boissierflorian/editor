package org.ulco;

public class Square extends Rectangle {

    public Square(Point center, double length) {
        super(center, length, length);
    }

    public Square(String json) {
        String str = json.replaceAll("\\s+","");
        int centerIndex = str.indexOf("center");
        int lengthIndex = str.indexOf("length");
        int endIndex = str.lastIndexOf("}");

        m_origin = new Point(str.substring(centerIndex + 7, lengthIndex - 1));
        m_width = m_height = Double.parseDouble(str.substring(lengthIndex + 7, endIndex));
    }

    @Override
    public GraphicsObject copy() {
        return new Square(m_origin.copy(), m_width);
    }


    @Override
    public String toJson() {
        return "{ type: square, center: " + m_origin.toJson() + ", length: " + this.m_width + " }";
    }

    @Override
    public String toString() {
        return "square[" + m_origin.toString() + "," + m_width + "]";
    }
}