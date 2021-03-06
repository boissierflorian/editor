package org.ulco;

import java.util.Vector;

abstract public class GraphicsObject implements JsonSerializable {
    private int m_ID;

    public GraphicsObject() {
        m_ID = ID.getInstance().generateId();
    }

    abstract public GraphicsObject copy();

    public int getID() {
        return m_ID;
    }

    abstract boolean isClosed(Point pt, double distance);

    abstract void move(Point delta);

    abstract public String toJson();

    abstract public String toString();

    public void addIfClosed(Vector<GraphicsObject> v, Point pt, double distance) {
        if (!isGroup() && isClosed(pt, distance)) {
            v.add(this);
        }
    }

    public boolean isGroup() { return false; }

    public int size() { return 1; }
}
