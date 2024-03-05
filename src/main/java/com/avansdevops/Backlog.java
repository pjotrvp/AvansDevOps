package com.avansdevops;

import java.util.ArrayList;
import java.util.List;

import com.avansdevops.backlog.TodoState;

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
