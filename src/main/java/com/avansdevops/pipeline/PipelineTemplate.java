package com.avansdevops.pipeline;

public abstract class PipelineTemplate {
    private String pipelineName;

    protected PipelineTemplate(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public boolean executePipeline() {
        System.out.println("Executing " + pipelineName + " pipeline");
        return setup() && run() && teardown();
    }

    public String getPipelineName() {
        return pipelineName;
    }

    protected Boolean executeStep(String startMessage, String command, String successMessage) {
        System.out.println(startMessage);
        System.out.println("Running command: " + command);
        System.out.println(successMessage);
        return true;
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
