package com.avansdevops.backlog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BacklogTest {
    private Backlog backlog;
    private BacklogItem item1;
    private BacklogItem item2;

    @Before
    public void setUp() {
        backlog = new Backlog("Sprint #1 backlog");
        item1 = new BacklogItem("b title", "description", 1);
        item2 = new BacklogItem("a title", "description", 1);
    }

    @Test
    public void testGetTitle() {
        assertEquals("Sprint #1 backlog", backlog.getTitle());
    }

    @Test
    public void testSetBacklogItems() {
        backlog.setBacklogItems(Arrays.asList(item1, item2));
        List<BacklogItem> items = backlog.getBacklogItems();
        assertEquals(2, items.size());
        assertEquals(item2, items.get(0)); // item2 should be first then item1, because of sorting
        assertEquals(item1, items.get(1));
    }

    @Test
    public void testAddBacklogItem() {
        backlog.addBacklogItem(item1);
        assertTrue(backlog.getBacklogItems().contains(item1));
    }

    @Test
    public void testDuplicateBacklogItem() {
        backlog.addBacklogItem(item1);
        backlog.addBacklogItem(item1);
        assertEquals(1, backlog.getBacklogItems().size());
    }

    @Test
    public void testGetBacklogItems() {
        backlog.addBacklogItem(item1);
        backlog.addBacklogItem(item2);
        List<BacklogItem> items = backlog.getBacklogItems();
        assertEquals(2, items.size());
        assertEquals(item2, items.get(0)); // item2 should be first then item1, because of sorting
        assertEquals(item1, items.get(1));
    }

    @Test
    public void testGetBacklogItemsEmpty() {
        List<BacklogItem> items = backlog.getBacklogItems();
        assertEquals(0, items.size());
    }
}
