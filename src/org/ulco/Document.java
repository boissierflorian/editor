package org.ulco;

import java.util.Vector;
import java.util.Collections;

public class Document {
    private Vector<Layer> m_layers;
    private Parser m_parser;

    public Document() {
        m_layers = new Vector<Layer>();
        m_parser = new Parser();
    }

    public Document(String json) {
        m_layers = new Vector<Layer>();
        m_parser = new Parser();

        String str = json.replaceAll("\\s+", "");
        int layersIndex = str.indexOf("layers");
        int endIndex = str.lastIndexOf("}");

        parseLayers(str.substring(layersIndex + 8, endIndex));
    }

    public Layer createLayer() {
        Layer layer = new Layer();

        m_layers.add(layer);
        return layer;
    }

    public int getLayerNumber() {
        return m_layers.size();
    }

    public int getObjectNumber() {
        int size = 0;

        for (int i = 0; i < m_layers.size(); ++i) {
            size += m_layers.elementAt(i).getObjectNumber();
        }
        return size;
    }

    private void parseLayers(String layersStr) {
        m_parser.parse(m_layers, layersStr, JSON::parseLayer);
    }

    public String toJson() {
        return "{ type: document, layers: { " + JSON.createJsonFromList(m_layers) + " } }";
    }

    public Vector<Layer> getLayers() {
        return m_layers;
    }
}
