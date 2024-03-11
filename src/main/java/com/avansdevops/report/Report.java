package com.avansdevops.report;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.avansdevops.Sprint;

public class Report {
    private String name;
    private Sprint sprint;
    private String companyName;
    private String companyLogo;
    private int version;
    private Date date;
    private IReportStrategy reportStrategy;

    protected BufferedWriter getBufferedWriter(String fileName) throws IOException {
        return new BufferedWriter(new FileWriter(fileName));
    }

    public Report(String name, Sprint sprint, String companyName, String companyLogo, int version,
            IReportStrategy reportStrategy) {
        this.name = name;
        this.sprint = sprint;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.version = version;
        this.date = new Date();
        this.reportStrategy = reportStrategy;
    }

    public void generateReport(String fileType) throws IOException {
        String dirName = "reports";
        String fileName = dirName + "/" + name + "." + fileType + ".txt";
        try (BufferedWriter writer = getBufferedWriter(fileName)) {
            // Write header
            writer.write("Report Name: " + name + "\n");
            writer.write("Sprint: " + sprint.getName() + "\n");
            writer.write("Company Name: " + companyName + "\n");
            writer.write("Company Logo: " + companyLogo + "\n\n");

            // Write report information
            String reportInformation = reportStrategy.generateReportInformation(sprint);
            writer.write(reportInformation + "\n\n");

            // Write footer
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            writer.write("Date: " + dateFormat.format(date) + "\n");
            writer.write("Version: " + version + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
