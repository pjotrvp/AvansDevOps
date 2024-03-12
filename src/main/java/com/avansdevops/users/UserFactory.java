package com.avansdevops.users;

public class UserFactory {
    public User createUser(UserRole role, String name) throws IllegalArgumentException {
        switch (role) {
            case PRODUCT_OWNER:
                return new ProductOwner(name);
            case SCRUM_MASTER:
                return new ScrumMaster(name);
            case DEVELOPER:
                return new Developer(name);
            default:
                return null;
        }
    }
}
