package com.avansdevops.report;

import java.util.List;
import java.util.stream.Collectors;

import com.avansdevops.Sprint;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.backlog.DoneState;

public class SprintResultReport implements IReportStrategy {

    @Override
    public String generateReportInformation(Sprint sprint) {
        StringBuilder report = new StringBuilder();
        report.append("Sprint Result - Partial Product\n");
        report.append("--------------\n");

        List<BacklogItem> items = sprint.getBacklogItems().stream()
                .filter(item -> item.getState() instanceof DoneState)
                .collect(Collectors.toList());

        // Append done backlog items to the report
        report.append("\nDone Backlog Items:\n");
        for (BacklogItem item : items) {
            report.append("- " + item.getTitle() + "\n");
        }

        return report.toString();
    }

}
