package com.avansdevops.discussion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.avansdevops.users.User;
import com.avansdevops.users.UserFactory;
import com.avansdevops.users.UserRole;

public class ForumTest {
    private Forum forum;
    private UserFactory factory = new UserFactory();
    private User developer;

    @Before
    public void setUp() {
        developer = factory.createUser(UserRole.DEVELOPER, "Developer");
        forum = new Forum("Forum Name");
    }

    @Test
    public void testForumName() {
        forum.setForumName("New Forum Name");
        assertEquals("New Forum Name", forum.getForumName());
    }

    @Test
    public void testAddDiscussion() {
        Discussion discussion = new Discussion("Discussion Title", "Discussion Content", developer);
        forum.addDiscussion(discussion);
        assertTrue(forum.getDiscussions().contains(discussion));
    }

    @Test
    public void testGetDiscussions() {
        Discussion discussion1 = new Discussion("Discussion Title 1", "Discussion Content 1", developer);
        Discussion discussion2 = new Discussion("Discussion Title 2", "Discussion Content 2", developer);
        forum.addDiscussion(discussion1);
        forum.addDiscussion(discussion2);
        List<Discussion> discussions = forum.getDiscussions();
        assertEquals(2, discussions.size());
        assertTrue(discussions.contains(discussion1));
        assertTrue(discussions.contains(discussion2));
    }
}
