package com.avansdevops.users;

public class ProductOwner implements User{
    private String name;
    private UserRole role;
    
    public ProductOwner(String name) {
        this.name = name;
        this.role = UserRole.PRODUCTOWNER;
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
