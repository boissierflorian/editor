package org.ulco;

public abstract class Container extends GraphicsObject {
    protected GraphicsObjects m_liste;
    protected Parser m_parser;

    public Container() {
        this(null);
    }

    public Container(String json) {
        m_liste = new GraphicsObjects();
        m_parser = new Parser();

        if (json != null && !json.isEmpty())
            parse(json.replaceAll("\\s+", ""));
    }

    protected abstract void parse(String json);
    public abstract void parseGroups(String groups);

    protected void parseObjects(String objects) {
        m_parser.parse(m_liste, objects, JSON::parse);
    }


    public void add(Object o) {
        if (o instanceof GraphicsObject) {
            m_liste.add((GraphicsObject) o);
        }
    }

    @Override
    public boolean isClosed(Point pt, double distance) {
        for (GraphicsObject go : m_liste) {
            if (!go.isClosed(pt, distance))
                return false;
        }

        return true;
    }

    public void add(GraphicsObject g) {
        m_liste.add(g);
    }

    public GraphicsObjects getListe() {
        return m_liste;
    }

    public GraphicsObject get(int index) {
        return m_liste.elementAt(index);
    }

    @Override
    public int size() {
        int size = 0;

        for (GraphicsObject go : m_liste)
            size += go.size();

        return size;
    }
}
