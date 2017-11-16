package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject {

    private int m_ID;
    private Vector<GraphicsObject> m_objectList;

    public Group() {
        m_objectList = new Vector<>();
        m_ID = ID.getInstance().generateId();
    }

    public Group(String json) {
        m_objectList = new Vector<>();
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

        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);
            g.add(element.copy());
        }

        return g;
    }

    public int getID() {
        return m_ID;
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
        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);
            element.move(delta);
        }
    }

    private int searchSeparator(String str) {
        int index = 0;
        int level = 0;
        boolean found = false;

        while (!found && index < str.length()) {
            if (str.charAt(index) == '{') {
                ++level;
                ++index;
            } else if (str.charAt(index) == '}') {
                --level;
                ++index;
            } else if (str.charAt(index) == ',' && level == 0) {
                found = true;
            } else {
                ++index;
            }
        }
        if (found) {
            return index;
        } else {
            return -1;
        }
    }

    private void parseGroups(String groupsStr) {
        while (!groupsStr.isEmpty()) {
            int separatorIndex = searchSeparator(groupsStr);
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
        while (!objectsStr.isEmpty()) {
            int separatorIndex = searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            m_objectList.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
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
        StringBuilder groupBuilder = new StringBuilder();

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);

            if (element.isGroup()) {
                groupBuilder.append(element.toString());
            } else {
                objectsBuilder.append(element.toString() + ", ");
            }
        }

        removeLastComma(objectsBuilder);

        builder.append(objectsBuilder.toString());
        builder.append("],[");
        builder.append(groupBuilder.toString());
        builder.append("]]");

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
