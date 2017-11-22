package org.ulco;

import java.util.List;
import java.util.function.Function;

public class Parser {
    public <E> void parse(List<E> liste, String objectsStr, Function<String, E> mapper) {
        while (!objectsStr.isEmpty()) {
            int separatorIndex = StringUtils.searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }

            liste.add(mapper.apply(objectStr));

            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
    }
}
