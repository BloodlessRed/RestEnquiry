package com.example.demo;

public class Driver {

    private String name;

    private char type;

    public Driver(String name, char type){

        setName(name);

        setType(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

}
