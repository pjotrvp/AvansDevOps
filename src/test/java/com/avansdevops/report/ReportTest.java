package com.avansdevops.report;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void testGenerateReport() throws IOException {
        report.generateReport("pdf");
        try (BufferedReader reader = new BufferedReader(new FileReader("reports/Test.pdf.txt"))) {
            assertEquals("Report Name: Test", reader.readLine());
            assertEquals("Sprint: Sprint 1", reader.readLine());
            assertEquals("Company Name: Company Name", reader.readLine());
            assertEquals("Company Logo: Company Logo", reader.readLine());
            assertEquals("", reader.readLine());
            assertEquals("Report Information", reader.readLine());
            assertEquals("", reader.readLine());
            assertEquals("Date: " + dateFormat.format(todayDate), reader.readLine());
            assertEquals("Version: 1", reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerateReport_Exception() throws IOException {
        Report reportException = new Report("Test", sprint, "Company Name", "Company Logo", 1, reportStrategy) {
            @Override
            protected BufferedWriter getBufferedWriter(String fileName) throws IOException {
                BufferedWriter writer = mock(BufferedWriter.class);
                doThrow(new IOException()).when(writer).write(anyString());
                return writer;
            }
        };

        reportException.generateReport("pdf");
        assertEquals(false, Files.exists(Paths.get("reports/Test.pdf.txt")));
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get("reports/Test.pdf.txt"));
    }
}