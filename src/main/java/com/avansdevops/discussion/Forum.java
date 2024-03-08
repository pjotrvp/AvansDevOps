package com.avansdevops.discussion;

import java.util.ArrayList;
import java.util.List;

public class Forum {
    private List<Discussion> discussions = new ArrayList<>();

    public void addDiscussion(Discussion discussion) {
        discussions.add(discussion);
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }
}
