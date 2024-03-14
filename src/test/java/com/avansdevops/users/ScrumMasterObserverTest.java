package com.avansdevops.users;

import com.avansdevops.notifications.NotificationAdapter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

public class ScrumMasterObserverTest {
    private ScrumMaster scrumMaster;
    private NotificationAdapter mockAdapter;

    @Before
    public void setUp() {
        scrumMaster = new ScrumMaster("Piet");
        mockAdapter = Mockito.mock(NotificationAdapter.class);
        scrumMaster.setAdapters(Arrays.asList(mockAdapter));
    }

    @Test
    public void testUpdate() {
        String message = "Test message";
        scrumMaster.update(message);
        verify(mockAdapter).sendNotification("Scrum master Piet received a notification: " + message);
    }
}