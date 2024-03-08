package com.avansdevops.discussion;

class ConcreteDiscussionFactory implements DiscussionFactory {
    @Override
    public Forum createForum() {
        return new Forum();
    }

    @Override
    public Discussion createDiscussion(Forum forum, String title, String content) {
        Discussion discussion = new Discussion(title, content);
        forum.addDiscussion(discussion);
        return discussion;
    }
}