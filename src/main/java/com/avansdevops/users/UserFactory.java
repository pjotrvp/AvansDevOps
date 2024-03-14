package com.avansdevops.users;

public class UserFactory {
    public User createUser(UserRole role, String name) {
        switch (role) {
            case PRODUCT_OWNER:
                return new ProductOwner(name);
            case SCRUM_MASTER:
                return new ScrumMaster(name);
            case DEVELOPER:
                return new Developer(name);
            case TESTER:
                return new Tester(name);
            default:
                return null;
        }
    }
}
