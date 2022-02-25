package com.money.money;

import java.util.List;

public interface TagRepository {

    void addTag(Tag tagName);
    Tag getTag(String name);

    List<Tag> getAllTags();

}
