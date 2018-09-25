package com.example.medicine.model;

public class EnumOutput {

    public EnumOutput(String name, int index, String message) {
        this.name = name;
        this.index = index;
        this.message = message;
    }

    private String name;
    private int index;
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
