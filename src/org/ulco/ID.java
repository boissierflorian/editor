package org.ulco;

public class ID {
    private static ID instance;
    private int id;


    private ID() {
        this.id = 0;
    }

    public int generateId() {
        return ++this.id;
    }

    public int getOldId() {
        return this.id;
    }

    public static ID getInstance() {
        if (instance == null) {
            instance = new ID();
        }

        return instance;
    }

}