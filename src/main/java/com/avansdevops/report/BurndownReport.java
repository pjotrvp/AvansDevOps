package com.avansdevops.report;

import java.util.List;

import com.avansdevops.Sprint;
import com.avansdevops.backlog.BacklogItem;

public class BurndownReport implements IReportStrategy {
    @Override
    public String generateReportInformation(Sprint sprint) {
        StringBuilder report = new StringBuilder();
        report.append("Burndown Chart\n");
        report.append("--------------\n");

        List<BacklogItem> items = sprint.getBacklog();
        int totalStoryPoints = items.stream().mapToInt(BacklogItem::getStoryPoints).sum();

        report.append("Total Story Points: " + totalStoryPoints + "\n");
        report.append("Day\tRemaining Story Points\n");

        // Start from day 1 with total story points
        report.append(1 + "\t" + totalStoryPoints + "\n");

        // Assuming each day one backlog item is done
        for (int i = 0; i < items.size(); i++) {
            totalStoryPoints -= items.get(i).getStoryPoints();
            report.append((i + 2) + "\t" + totalStoryPoints + "\n");
        }

        return report.toString();
    }
}