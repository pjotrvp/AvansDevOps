package com.avansdevops.users;

public class Tester implements User{
    private String name;
    private UserRole role;

    public Tester(String name) {
        this.name = name;
        this.role = UserRole.TESTER;
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
