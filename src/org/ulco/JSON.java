package org.ulco;

public class JSON {
    static public GraphicsObject parse(String json) {
        String str = json.replaceAll("\\s+", "");
        String type = str.substring(str.indexOf("type") + 5, str.indexOf(","));
        return GraphicsObjectFactory.createObjectFromJSON(type, str);
    }

    static public Group parseGroup(String json) {
        return new Group(json);
    }

    static public Layer parseLayer(String json) {
        return new Layer(json);
    }

    static public Document parseDocument(String json) {
        return new Document(json);
    }
}
