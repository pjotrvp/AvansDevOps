package com.avansdevops.users;

public class ProductOwner implements User {
    private String name;
    private UserRole role;

    public ProductOwner(String name) {
        this.name = name;
        this.role = UserRole.PRODUCT_OWNER;
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
