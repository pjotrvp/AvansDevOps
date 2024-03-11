package com.avansdevops.discussion;

import java.util.ArrayList;
import java.util.List;

public class Forum {
    private String forumName;
    private List<Discussion> discussions = new ArrayList<>();

    public Forum(String forumName) {
        this.forumName = forumName;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public void addDiscussion(Discussion discussion) {
        discussions.add(discussion);
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }
}
