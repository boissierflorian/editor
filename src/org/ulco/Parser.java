package org.ulco;

import java.util.Vector;

public class Parser {
    public void parseObjects(Vector<GraphicsObject> liste, String objectsStr) {
        while (!objectsStr.isEmpty()) {
            int separatorIndex = StringUtils.searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            liste.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
    }
}
