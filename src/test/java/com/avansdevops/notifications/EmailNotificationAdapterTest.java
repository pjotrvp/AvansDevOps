package com.avansdevops.notifications;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class EmailNotificationAdapterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private EmailNotificationAdapter adapter;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        adapter = new EmailNotificationAdapter();
    }

    @Test
    public void testSendNotification() {
        String message = "Test message";
        adapter.sendNotification(message);
        assertEquals("Email notification: " + message + System.lineSeparator(), outContent.toString());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}