package com.avansdevops.discussion;

class ConcreteDiscussionFactory implements DiscussionFactory {
    @Override
    public Forum createForum() {
        return new Forum();
    }

    @Override
    public Discussion createDiscussion(Forum forum, String title, String content) throws IllegalArgumentException {
        if (forum == null) {
            throw new IllegalArgumentException("The discussion needs a forum");
        }
        Discussion discussion = new Discussion(title, content);
        forum.addDiscussion(discussion);
        return discussion;
    }
}