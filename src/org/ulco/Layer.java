package org.ulco;


public class Layer extends Container {

    public Layer() {
        super();
    }

    public Layer(String json) {
        super(json);
    }

    @Override
    protected void parse(String json) {
        int objectsIndex = json.indexOf("objects");
        int endIndex = json.lastIndexOf("}");

        parseObjects(json.substring(objectsIndex + 9, endIndex - 1));
    }

    @Override
    public void parseObjects(String objectsStr) {
        m_parser.parseObjects(m_liste, objectsStr);
    }

    @Override
    public void parseGroups(String groups) {

    }

    public String toJson() {
        String str = "{ type: layer, objects : { ";

        for (int i = 0; i < m_liste.size(); ++i) {
            GraphicsObject element = m_liste.elementAt(i);

            str += element.toJson();
            if (i < m_liste.size() - 1) {
                str += ", ";
            }
        }
        return str + " } }";
    }

    public String toString() {
        return new String("");
    }

    @Override
    public GraphicsObject copy() {
        Layer layer = new Layer();

        for (GraphicsObject g : m_liste)
            layer.add(g.copy());

        return layer;
    }

    public int getObjectNumber() {
        return super.size();
    }

    public void move(Point delta) {

    }
}