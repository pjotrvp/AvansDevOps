package com.avansdevops.pipeline;

public abstract class PipelineTemplate {
    private String pipelineName;

    protected PipelineTemplate(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public void executePipeline() {
        System.out.println("Executing " + pipelineName + " pipeline");
        setup();
        run();
        teardown();
    }

    public String getPipelineName() {
        return pipelineName;
    }

    protected void log(String message) {
        System.out.println(message);
    }

    protected void runCommand(String command) {
        log("Running command: " + command);
        // Simulate running the command
    }

    public abstract Boolean setup();

    public abstract Boolean run();

    public Boolean teardown() {
        // Simulate cleaning up the test pipeline
        System.out.println("Cleaning up the " + pipelineName + " pipeline...");

        System.out.println("The " + pipelineName + " pipeline has executed successfully!");
        return true;
    }
}
