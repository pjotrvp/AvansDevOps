package com.avansdevops;

public class BacklogItem {
    private String title;
    private String description;
    private int storyPoints;
    private BacklogItem[] subActivities;
    private User storyOwner;

    public BacklogItem(String title, String description, int storyPoints) {
        this.title = title;
        this.description = description;
        this.storyPoints = storyPoints;
    }
}
