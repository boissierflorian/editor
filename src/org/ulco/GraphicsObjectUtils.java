package org.ulco;


import java.util.Vector;

public class GraphicsObjectUtils {
    public static GraphicsObjects select(Document document, Point pt, double distance) {
        GraphicsObjects list = new GraphicsObjects();

        for (Layer layer : document.getLayers()) {
            list.addAll(GraphicsObjectUtils.select(layer, pt, distance));
        }

        return list;
    }

    private static GraphicsObjects select(Layer layer, Point pt, double distance) {
        GraphicsObjects list = new GraphicsObjects();

        for (GraphicsObject object : layer.getListe()) {
            if (object.isClosed(pt, distance)) {
                list.add(object);
            }
        }
        return list;
    }
}
