package com.avansdevops.discussion;

public interface DiscussionFactory {
    Forum createForum();

    Discussion createDiscussion(Forum forum, String title, String content);
}
