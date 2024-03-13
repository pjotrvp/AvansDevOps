package com.avansdevops.report;

import java.util.LinkedHashMap;
import java.util.Map;

import com.avansdevops.Sprint;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.users.User;

public class StoryPointsReport implements IReportStrategy {
    @Override
    public String generateReportInformation(Sprint sprint) {
        StringBuilder report = new StringBuilder();
        report.append("Story Points Report\n");
        report.append("-----------\n");

        Map<User, Integer> userStoryPoints = new LinkedHashMap<>();
        for (User user : sprint.getParticipants()) {
            for (BacklogItem item : sprint.getBacklogItems()) {
                if (user.equals(item.getAssignee())) {
                    userStoryPoints.put(user, userStoryPoints.getOrDefault(user, 0) + item.getStoryPoints());
                }
            }
        }

        for (Map.Entry<User, Integer> entry : userStoryPoints.entrySet()) {
            report.append("User: " + entry.getKey().getName() + ", Story Points: " + entry.getValue() + "\n");
        }

        return report.toString();
    }
}