package com.avansdevops.report;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.avansdevops.Sprint;
import com.avansdevops.SprintGoal;
import com.avansdevops.backlog.Backlog;

public class ReportTest {
    private Sprint sprint;
    private IReportStrategy reportStrategy;
    private Report report;
    private Date todayDate = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

    @Before
    public void setUp() {
        sprint = new Sprint("Sprint 1", new Date(), new Date(), SprintGoal.PARTIALPRODUCT, new Backlog("Backlog 1"));
        reportStrategy = Mockito.mock(IReportStrategy.class);
        when(reportStrategy.generateReportInformation(sprint)).thenReturn("Report Information");
        report = new Report("Test", sprint, "Company Name", "Company Logo", 1, reportStrategy);
    }

    @Test
    public void testGenerateReport() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                report.generateReport("pdf");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Wait for the CompletableFuture to finish
        future.get();
        BufferedReader reader = new BufferedReader(new FileReader("reports/Test.pdf.txt"));
        assertEquals("Report Name: Test", reader.readLine());
        assertEquals("Sprint: Sprint 1", reader.readLine());
        assertEquals("Company Name: Company Name", reader.readLine());
        assertEquals("Company Logo: Company Logo", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("Report Information", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("Date: " + dateFormat.format(todayDate), reader.readLine());
        assertEquals("Version: 1", reader.readLine());
        reader.close();
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get("reports/Test.pdf.txt"));
    }
}