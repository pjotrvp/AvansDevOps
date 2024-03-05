package com.avansdevops;

import java.util.HashMap;
import java.util.Map;

public class Scm {
    private String scmName;
    private String sourceCode;
    private Map<String, String> commit = new HashMap<>();

    public Scm(String scmName, String sourceCode) {
        this.scmName = scmName;
        this.sourceCode = sourceCode;
    }

    public String pull() {
        System.out.println("Pulling from " + scmName + " repository...");
        return sourceCode;
    }

    public void commit(String title, String code) throws IllegalArgumentException {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Please provide a commit title");
        } else if (code.isEmpty()) {
            throw new IllegalArgumentException("Nothing to commit");
        } else {
            System.out.println("Committing to " + scmName + " repository with title: " + title);
            this.commit.put(title, code);
        }
    }

    public void push() throws NullPointerException {
        if (commit.isEmpty()) {
            throw new NullPointerException("Nothing to push");
        } else {
            System.out.println("Pushing to " + scmName + " repository...");
            this.sourceCode = commit.get(commit.keySet().toArray()[0]);
            this.commit.clear();
        }
    }

    public String getCommitTitle() {
        if (commit.isEmpty()) {
            return null;
        }
        return commit.keySet().toArray()[0].toString();
    }

    public String getCommitCode() {
        if (commit.isEmpty()) {
            return null;
        }
        return commit.get(commit.keySet().toArray()[0]);
    }
}
