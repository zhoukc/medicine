package com.example.medicine.model;

public class ControllersOutput {

    public ControllersOutput() {
    }

    public ControllersOutput(String name,String simpleName, String path) {
        this.simpleName=simpleName;
        this.name = name;
        this.path = path;
    }

    private String name;
    private String simpleName;
    private String path;
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
