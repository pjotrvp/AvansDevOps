package com.avansdevops;

import java.util.ArrayList;
import java.util.List;

public class Backlog {
    private List<BacklogItem> items;
    public Backlog() {
        items = new ArrayList<>();
    }

    public void addItem(BacklogItem item) {
        items.add(item);
        item.setState(new TodoState());
    }

}
