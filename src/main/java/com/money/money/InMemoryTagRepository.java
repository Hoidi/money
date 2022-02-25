package com.money.money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTagRepository implements TagRepository{

    private final Map<String, Tag> tags = new HashMap<>();

    @Override
    public void addTag(Tag tag) {
        tags.put(tag.name, tag);
    }

    @Override
    public Tag getTag(String name) {
        return tags.get(name);
    }

    @Override
    public List<Tag> getAllTags() {
        return tags.values().stream().toList();
    }
}
