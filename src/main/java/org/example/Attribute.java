package org.example;

public class Attribute {
    private String name;
    private String type;
    private String constrains;

    public Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Attribute(String name, String type, String constrains) {
        this.name = name;
        this.type = type;
        this.constrains = constrains;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConstrains() {
        return constrains;
    }

    public void setConstrains(String constrains) {
        this.constrains = constrains;
    }
}
