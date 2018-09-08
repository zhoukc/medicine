package com.example.medicine.features;

public enum PublicFailureReasons {

    PARAMETER_ERROR(4000, "参数错误");

    private int index;
    private String message;

    PublicFailureReasons(int index, String message) {
        this.index = index;
        this.message = message;
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
