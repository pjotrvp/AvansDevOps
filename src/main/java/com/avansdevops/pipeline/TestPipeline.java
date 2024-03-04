package com.avansdevops.pipeline;

public class TestPipeline extends PipelineTemplate {

    public TestPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        return executeStep("Initiating the " + getPipelineName() + " pipeline...", "mvn install",
                "Dependencies installed successfully!");
    }

    @Override
    public Boolean run() {
        return executeStep("Testing the project...", "mvn test", "Tests completed successfully!");
    }
}
