package com.avansdevops.report;

import com.avansdevops.Sprint;
import com.avansdevops.users.User;

public class TeamReport implements IReportStrategy {
    @Override
    public String generateReportInformation(Sprint sprint) {
        StringBuilder report = new StringBuilder();
        report.append("Team Report\n");
        report.append("-----------\n");
        report.append("Participants:\n");
        for (User user : sprint.getParticipants()) {
            report.append("- " + user.getName() + " (" + user.getRole() + ")\n");
        }
        return report.toString();
    }
}