package com.example.medicine.model;

public class ActionOutput {

    public ActionOutput(String actionName, String inputName, String inputSimpleName, String outputName, String outputSimpleName, String inputExample, String outputExample, String path, String produces, String method) {
        this.actionName = actionName;
        this.inputName = inputName;
        this.inputSimpleName = inputSimpleName;
        this.outputName = outputName;
        this.outputSimpleName = outputSimpleName;
        this.inputExample = inputExample;
        this.outputExample = outputExample;
        this.path = path;
        this.produces = produces;
        this.method = method;
    }

    private String actionName;
    private String inputName;
    private String inputSimpleName;
    private String outputName;
    private String outputSimpleName;
    private String inputExample;
    private String outputExample;
    private String path;
    private String produces;
    private String method;
    private String description;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getInputSimpleName() {
        return inputSimpleName;
    }

    public void setInputSimpleName(String inputSimpleName) {
        this.inputSimpleName = inputSimpleName;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public String getOutputSimpleName() {
        return outputSimpleName;
    }

    public void setOutputSimpleName(String outputSimpleName) {
        this.outputSimpleName = outputSimpleName;
    }

    public String getInputExample() {
        return inputExample;
    }

    public void setInputExample(String inputExample) {
        this.inputExample = inputExample;
    }

    public String getOutputExample() {
        return outputExample;
    }

    public void setOutputExample(String outputExample) {
        this.outputExample = outputExample;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProduces() {
        return produces;
    }

    public void setProduces(String produces) {
        this.produces = produces;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
