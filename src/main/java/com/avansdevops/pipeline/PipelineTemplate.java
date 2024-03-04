package com.avansdevops.pipeline;

public abstract class PipelineTemplate {
    private String pipelineName;

    public PipelineTemplate(String pipelineName) {
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

    public abstract Boolean setup();

    public abstract Boolean run();

    public Boolean teardown() {
        // Simulate cleaning up the test pipeline
        System.out.println("Cleaning up the " + pipelineName + " pipeline...");

        System.out.println("The " + pipelineName + " pipeline has executed successfully!");
        return true;
    }
}
