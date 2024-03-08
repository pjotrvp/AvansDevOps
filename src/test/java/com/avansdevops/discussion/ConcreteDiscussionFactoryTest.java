package com.avansdevops.discussion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConcreteDiscussionFactoryTest {
    private ConcreteDiscussionFactory factory;

    @Before
    public void setUp() {
        factory = new ConcreteDiscussionFactory();
    }

    @Test
    public void testCreateForum() {
        Forum forum = factory.createForum();
        assertNotNull(forum);
    }

    @Test
    public void testCreateDiscussion() {
        String title = "Discussion Title";
        String content = "Discussion Content";
        Forum forum = factory.createForum();
        Discussion discussion = factory.createDiscussion(forum, title, content);
        assertNotNull(discussion);
        assertEquals(title, discussion.getTitle());
        assertEquals(content, discussion.getContent());
        assertTrue(forum.getDiscussions().contains(discussion));
    }

    @Test
    public void testCreateDiscussionNullForum() {
        try {
            factory.createDiscussion(null, "Discussion Title", "Discussion Content");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("The discussion needs a forum", e.getMessage());
        }
    }
}
