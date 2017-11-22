package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject {

    private Vector<GraphicsObject> m_objectList;
    private Parser parser;

    public Group() {
        m_objectList = new Vector<>();
        parser = new Parser();
    }

    public Group(String json) {
        m_objectList = new Vector<>();
        parser = new Parser();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
        parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
    }

    public void add(Object object) {
        if (object instanceof GraphicsObject) {
            m_objectList.add((GraphicsObject) object);
        }
    }

    public Group copy() {
        Group g = new Group();

        for (Object o : m_objectList)
            g.add(((GraphicsObject)o).copy());

        return g;
    }

    @Override
    boolean isClosed(Point pt, double distance) {
        for (GraphicsObject go : m_objectList) {
            if (!go.isClosed(pt, distance))
                return false;
        }

        return true;
    }

    public void move(Point delta) {
        for (Object o : m_objectList)
            ((GraphicsObject) o).move(delta);
    }

    private void parseGroups(String groupsStr) {
        while (!groupsStr.isEmpty()) {
            int separatorIndex = StringUtils.searchSeparator(groupsStr);
            String groupStr;

            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }

            m_objectList.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    private void parseObjects(String objectsStr) {
        parser.parseObjects(m_objectList, objectsStr);
    }

    public int size() {
        int size = 0;

        for (GraphicsObject go : m_objectList)
            size += go.size();

        return size;
    }

    private void removeLastComma(StringBuilder sb) {
        int index = sb.lastIndexOf(",");
        sb.replace(index, sb.length(), "");
    }

    public String toJson() {
        StringBuilder finalBuffer = new StringBuilder("{ type: group, objects : { ");
        StringBuilder groupsBuffer = new StringBuilder(" }, groups : { ");

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);

            if (element.isGroup()) {
                groupsBuffer.append(element.toJson());
            } else {
                finalBuffer.append(element.toJson() + ", ");
            }
        }

        removeLastComma(finalBuffer);

        finalBuffer.append(groupsBuffer.toString() + " } }");
        return finalBuffer.toString();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("group[[");
        StringBuilder objectsBuilder = new StringBuilder();
        StringBuilder groupsBuilder = new StringBuilder();

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);

            if (element.isGroup()) {
                groupsBuilder.append(element.toString());
            } else {
                objectsBuilder.append(element.toString() + ", ");
            }
        }

        removeLastComma(objectsBuilder);

        StringUtils.addToBuilder(builder, objectsBuilder.toString(), "],[");
        StringUtils.addToBuilder(builder, groupsBuilder.toString(), "]]");

        return builder.toString();
    }

    @Override
    public boolean isGroup() { return true; }

    @Override
    public void addIfClosed(Vector<GraphicsObject> v, Point pt, double distance) {
        for (GraphicsObject go : m_objectList) {
            go.addIfClosed(v, pt, distance);
        }
    }
}
