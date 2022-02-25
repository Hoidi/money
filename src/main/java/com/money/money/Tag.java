package com.money.money;

public class Tag {

    final String name;
    final String description;

    public Tag(String tagName, String description) {
        this.name = tagName;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
