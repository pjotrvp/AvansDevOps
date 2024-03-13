package com.avansdevops.backlog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Backlog {
    private String title;
    private List<BacklogItem> backlogItems = new ArrayList<>();

    public Backlog(String title) {
        this.title = title;
    }

    public void setBacklogItems(List<BacklogItem> backlogItems) {
        this.backlogItems = backlogItems;
    }

    public List<BacklogItem> getBacklogItems() {
        return backlogItems.stream()
                .sorted(Comparator.comparing(BacklogItem::getTitle))
                .collect(Collectors.toList());
    }

    public void addBacklogItem(BacklogItem item) {
        if (!backlogItems.contains(item)) {
            backlogItems.add(item);
        }
    }

    public String getTitle() {
        return title;
    }
}
