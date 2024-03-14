package com.avansdevops;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.avansdevops.backlog.Backlog;
import com.avansdevops.backlog.BacklogItem;
import com.avansdevops.backlog.DoneState;
import com.avansdevops.notifications.Observer;
import com.avansdevops.notifications.Subject;
import com.avansdevops.report.Report;
import com.avansdevops.report.SprintResultReport;
import com.avansdevops.users.ProductOwner;
import com.avansdevops.users.ScrumMaster;
import com.avansdevops.users.User;
import com.avansdevops.users.UserRole;

public class Sprint implements Subject {
    private String name;
    private Date startDate;
    private Date endDate;
    private SprintGoal goal;
    private Backlog backlog;
    private Project project;
    private boolean hasStarted = false;
    private boolean hasFinished = false;
    private List<User> participants = new ArrayList<>();
    private Report report = null;
    private List<Observer> observers = new ArrayList<>();

    private static final String SPRINT_ERROR = "Sprint has already started or finished";
    private static final Logger LOGGER = Logger.getLogger(Sprint.class.getName());

    public Sprint(String name, Date startDate, Date endDate, SprintGoal goal, Backlog backlog, Project project) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goal = goal;
        this.backlog = backlog;
        this.project = project;
    }

    public void startSprint() throws IllegalStateException {
        if (this.hasStarted || this.hasFinished) {
            throw new IllegalStateException(SPRINT_ERROR);
        }

        this.hasStarted = true;
        this.startDate = new Date();
    }

    public void generateSprintReport(String name, String companyName, String companyLogo, int version)
            throws IllegalStateException {
        if (this.goal == SprintGoal.PARTIAL_PRODUCT) {
            report = new Report(name, this, companyName, companyLogo, version, new SprintResultReport());
            report.generateReport("pdf");
        } else {
            throw new IllegalStateException("Sprint goal is not partial product");
        }
    }

    public void finishSprint(boolean isSuccessful) throws IllegalStateException {
        if (!this.hasStarted || this.hasFinished) {
            throw new IllegalStateException("Sprint has not started or is already finished");
        }

        if (isSuccessful) {
            if (this.goal == SprintGoal.PARTIAL_PRODUCT && this.report == null) {
                throw new IllegalStateException("Sprint report is not generated");
            }
            commitAndPushDoneItems();
            executeDevelopmentPipelines();
        } else {
            this.hasFinished = true;
            this.hasStarted = false;
            String message = "Sprint " + this.name + " has failed";
            notifyProductOwner(message);
            notifyScrumMaster(message);
        }
    }

    void commitAndPushDoneItems() {
        for (BacklogItem item : getBacklogItems()) {
            if (item.getState() instanceof DoneState) {
                try {
                    this.project.getScm().commit(item.getTitle() + " is done", item.getCode());
                    this.project.getScm().push();
                } catch (Exception e) {
                    LOGGER.severe("An error occurred while commiting/pushing: " + e.getMessage());
                }
            }
        }
    }

    void executeDevelopmentPipelines() {
        if (this.project.executePipelines()) {
            this.hasFinished = true;
            this.hasStarted = false;
            String message = "Sprint " + this.name + " has finished successfully";
            notifyProductOwner(message);
            notifyScrumMaster(message);
        } else {
            notifyScrumMaster("Pipeline execution failed");
            throw new IllegalStateException("Pipeline execution failed");
        }
    }

    public boolean hasStarted() {
        return this.hasStarted;
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }

    public Report getReport() {
        return this.report;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalStateException {
        if (this.hasStarted || this.hasFinished) {
            throw new IllegalStateException(SPRINT_ERROR);
        }
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) throws IllegalStateException {
        if (this.hasStarted || this.hasFinished) {
            throw new IllegalStateException(SPRINT_ERROR);
        }
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) throws IllegalStateException {
        if (this.hasStarted || this.hasFinished) {
            throw new IllegalStateException(SPRINT_ERROR);
        }
        this.endDate = endDate;
    }

    public SprintGoal getGoal() {
        return this.goal;
    }

    public List<BacklogItem> getBacklogItems() {
        return this.backlog.getBacklogItems();
    }

    public Backlog getBacklog() {
        return this.backlog;
    }

    public void addBacklogItem(BacklogItem item) throws IllegalArgumentException, IllegalStateException {
        if (!this.project.getBacklogItems().contains(item)) {
            throw new IllegalArgumentException("Backlog item is not in the project's backlog");
        }

        if (getBacklogItems().contains(item)) {
            throw new IllegalArgumentException("Backlog item is already in the sprint");
        }

        if (this.hasStarted || this.hasFinished) {
            throw new IllegalStateException(SPRINT_ERROR);
        }

        if (item.getState() instanceof DoneState) {
            throw new IllegalArgumentException("Backlog item is already done");
        }

        this.backlog.addBacklogItem(item);
        item.moveToTodo();
    }

    public List<User> getParticipants() {
        return this.participants;
    }

    public void addParticipant(User user) throws IllegalStateException, IllegalArgumentException {
        if (this.hasFinished) {
            throw new IllegalStateException(SPRINT_ERROR);
        }

        if (!this.project.getMembers().contains(user)) {
            throw new IllegalArgumentException("User is not a project member");
        }

        if (this.participants.contains(user)) {
            throw new IllegalArgumentException("User is already a participant in the sprint");
        }

        if (user instanceof ProductOwner) {
            throw new IllegalArgumentException("Sprints don't require a Product Owner");
        }

        if (user instanceof ScrumMaster && this.participants.stream().anyMatch(ScrumMaster.class::isInstance)) {
            throw new IllegalArgumentException("Sprint already has a Scrum Master");
        }

        this.participants.add(user);
    }

    public void removeParticipant(User user) {
        if (!this.participants.contains(user)) {
            throw new IllegalArgumentException("User is not a participant in the sprint");
        }

        this.participants.remove(user);
    }

    public void setDefaultObserversForBacklogItems() {
        for (BacklogItem item : getBacklogItems()) {
            List<Observer> observers = getParticipants().stream()
                    .filter(participant -> participant.getRole().equals(UserRole.TESTER) ||
                            participant.getRole().equals(UserRole.SCRUM_MASTER) ||
                            (participant.getRole().equals(UserRole.DEVELOPER)
                                    && participant.equals(item.getAssignee())))
                    .map(participant -> (Observer) participant)
                    .collect(Collectors.toList());
            item.setObservers(observers);
        }
    }

    public void setDefaultObserversForSprint() {
        // Get the scrum master and the product owner from the related project
        User scrumMaster = this.project.getMembers().stream()
                .filter(member -> member.getRole().equals(UserRole.SCRUM_MASTER))
                .findFirst()
                .orElse(null);

        User productOwner = this.project.getMembers().stream()
                .filter(member -> member.getRole().equals(UserRole.PRODUCT_OWNER))
                .findFirst()
                .orElse(null);

        // Add the scrum master and the product owner to the observer list
        if (scrumMaster != null) {
            this.observers.add((Observer) scrumMaster);
        }
        if (productOwner != null) {
            this.observers.add((Observer) productOwner);
        }
    }

    public void notifyScrumMaster(String message) {
        notifyObservers(UserRole.SCRUM_MASTER, message);
    }

    public void notifyProductOwner(String message) {
        notifyObservers(UserRole.PRODUCT_OWNER, message);
    }

    @Override
    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
        throw new IllegalArgumentException("Observer not found");
    }

    @Override
    public void notifyObservers(UserRole role, String message) {
        for (Observer observer : observers) {
            if (((User) observer).getRole().equals(role)) {
                observer.update(message);
            }
        }
    }
}
