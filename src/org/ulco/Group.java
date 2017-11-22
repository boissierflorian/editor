package org.ulco;

import java.util.Vector;

public class Group extends Container {

    public Group() {
        super();
    }

    public Group(String json) {
        super(json);
    }

    @Override
    protected void parse(String json) {
        int objectsIndex = json.indexOf("objects");
        int groupsIndex = json.indexOf("groups");
        int endIndex = json.lastIndexOf("}");

        parseObjects(json.substring(objectsIndex + 9, groupsIndex - 2));
        parseGroups(json.substring(groupsIndex + 8, endIndex - 1));
    }

    @Override
    public Group copy() {
        Group g = new Group();

        for (GraphicsObject o : m_liste)
            g.add(o.copy());

        return g;
    }

    @Override
    public void move(Point delta) {
        for (GraphicsObject g : m_liste)
            g.move(delta);
    }

    @Override
    public void parseGroups(String groupsStr) {
        m_parser.parse(m_liste, groupsStr, JSON::parseGroup);
    }

    @Override
    public String toJson() {
        StringBuilder finalBuffer = new StringBuilder("{ type: group, objects : { ");
        StringBuilder groupsBuffer = new StringBuilder(" }, groups : { ");

        JSON.fillBuilders(groupsBuffer, finalBuffer, m_liste, GraphicsObject::toJson);

        finalBuffer.append(groupsBuffer.toString() + " } }");
        return finalBuffer.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("group[[");
        StringBuilder objectsBuilder = new StringBuilder();
        StringBuilder groupsBuilder = new StringBuilder();

        for (int i = 0; i < m_liste.size(); ++i) {
            GraphicsObject element = m_liste.elementAt(i);

            if (element.isGroup()) {
                groupsBuilder.append(element.toString());
            } else {
                objectsBuilder.append(element.toString() + ", ");
            }
        }

        StringUtils.removeLastComma(objectsBuilder);
        StringUtils.addToBuilder(builder, objectsBuilder.toString(), "],[");
        StringUtils.addToBuilder(builder, groupsBuilder.toString(), "]]");

        return builder.toString();
    }

    @Override
    public boolean isGroup() { return true; }

    @Override
    public void addIfClosed(Vector<GraphicsObject> v, Point pt, double distance) {
        for (GraphicsObject go : m_liste) {
            go.addIfClosed(v, pt, distance);
        }
    }
}
