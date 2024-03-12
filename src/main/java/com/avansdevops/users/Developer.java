package com.avansdevops.users;

public class Developer implements User{
    private String name;
    private UserRole role;

    public Developer(String name) {
        this.name = name;
        this.role = UserRole.DEVELOPER;
    }
    @Override
    public void performTask() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performTask'");
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
