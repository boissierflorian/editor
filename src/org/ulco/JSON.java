package org.ulco;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public static String createJsonFromList(List<? extends JsonSerializable> liste) {
        StringBuilder sb = new StringBuilder();
        Iterator<? extends JsonSerializable> iter = liste.iterator();

        while (iter.hasNext()) {
            sb.append(iter.next().toJson());

            if (iter.hasNext()) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public static void fillBuilders(StringBuilder groups, StringBuilder objects, List<GraphicsObject> list,
                                    Function<GraphicsObject, String> mapper) {
        Iterator<GraphicsObject> iter = list.iterator();
        boolean comma = false;

        while (iter.hasNext()) {
            GraphicsObject g = iter.next();
            comma = iter.hasNext() && !g.isGroup();

            if (g.isGroup()) {
                groups.append(mapper.apply(g));
            } else {
                objects.append(mapper.apply(g));
            }

            if (comma) {
                objects.append(", ");
            }
        }
    }
}
