package com.example.medicine.model;

public class ParameterOutput {

    public ParameterOutput(String name, String typeName, String type, String genericType, String genericName, boolean enumeration) {
        this.name = name;
        this.typeName = typeName;
        this.type = type;
        this.genericType = genericType;
        this.genericName = genericName;
        this.enumeration = enumeration;
    }

    private String name;
    private String typeName;
    private String type;
    private String genericType;
    private String genericName;
    private boolean enumeration;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenericType() {
        return genericType;
    }

    public void setGenericType(String genericType) {
        this.genericType = genericType;
    }

    public boolean isEnumeration() {
        return enumeration;
    }

    public void setEnumeration(boolean enumeration) {
        this.enumeration = enumeration;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
