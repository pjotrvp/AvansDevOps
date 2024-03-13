package com.avansdevops.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
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

import com.avansdevops.Project;
import com.avansdevops.Sprint;
import com.avansdevops.SprintGoal;

public class ReportTest {
    private Sprint sprint;
    private IReportStrategy reportStrategy;
    private Report report;
    private Date todayDate = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    private Project project;

    @Before
    public void setUp() {
        project = new Project("Project 1");
        sprint = project.addSprint(new Date(), new Date(), SprintGoal.PARTIAL_PRODUCT);
        reportStrategy = Mockito.mock(IReportStrategy.class);
        when(reportStrategy.generateReportInformation(sprint)).thenReturn("Report Information");
        report = new Report("Test", sprint, "Company Name", "Company Logo", 1, reportStrategy);
    }

    @Test
    public void testGenerateReport() {
        report.generateReport("pdf");
        try (BufferedReader reader = new BufferedReader(new FileReader("reports" + File.separator + "Test.pdf.txt"))) {
            assertTrue(Files.exists(Paths.get("reports/Test.pdf.txt")));
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
            fail("IOException was thrown: " + e.getMessage());
        }
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get("reports/Test.pdf.txt"));
    }
}