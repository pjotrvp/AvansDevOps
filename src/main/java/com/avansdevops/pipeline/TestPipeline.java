package com.avansdevops.pipeline;

public class TestPipeline extends PipelineTemplate {

    public TestPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        log("Initiating the " + getPipelineName() + " pipeline...");
        runCommand("mvn install");
        log("Dependencies installed successfully!");
        return true;
    }

    @Override
    public Boolean run() {
        log("Testing the project...");
        runCommand("mvn test");
        log("Tests completed successfully!");
        return true;
    }
}
