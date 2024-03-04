package com.avansdevops;

public class BuildPipeline extends PipelineTemplate {

    public BuildPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        System.out.println("Initiating the " + this.getPipelineName() + " pipeline...");

        // Simulate running npm install
        System.out.println("Running command: npm install");

        System.out.println("Dependencies installed successfully!");
        return true;
    }

    @Override
    public Boolean run() {
        // Simulate building the project
        System.out.println("Building the project...");

        // Simulate running npm build
        System.out.println("Running command: npm build");

        System.out.println("Build completed successfully!");
        return true;
    }

}
