package com.avansdevops.users;

public class ScrumMaster implements User {
    private String name;
    private UserRole role;

    public ScrumMaster(String name) {
        this.name = name;
        this.role = UserRole.SCRUM_MASTER;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UserRole getRole() {
        return this.role;
    }
}
