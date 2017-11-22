package org.ulco;

import java.util.Vector;

public class Layer {

    private Vector<GraphicsObject> m_list;
    private int m_ID;
    private Parser parser;

    public Layer() {
        m_list = new Vector<GraphicsObject>();
        parser = new Parser();
        m_ID = ID.getInstance().generateId();
    }

    public Layer(String json) {
        m_list = new Vector<GraphicsObject>();
        parser = new Parser();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int endIndex = str.lastIndexOf("}");

        parseObjects(str.substring(objectsIndex + 9, endIndex - 1));
    }

    public void add(GraphicsObject o) {
        m_list.add(o);
    }

    public GraphicsObject get(int index) {
        return m_list.elementAt(index);
    }

    public int getObjectNumber() {
        return m_list.size();
    }

    public int getID() {
        return m_ID;
    }

    private void parseObjects(String objectsStr) {
        parser.parseObjects(m_list, objectsStr);
    }

    public String toJson() {
        String str = "{ type: layer, objects : { ";

        for (int i = 0; i < m_list.size(); ++i) {
            GraphicsObject element = m_list.elementAt(i);

            str += element.toJson();
            if (i < m_list.size() - 1) {
                str += ", ";
            }
        }
        return str + " } }";
    }

    public Vector<GraphicsObject> getListe() {
        return m_list;
    }
}
