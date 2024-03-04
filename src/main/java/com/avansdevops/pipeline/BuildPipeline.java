package com.avansdevops.pipeline;

public class BuildPipeline extends PipelineTemplate {

    public BuildPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        return executeStep("Initiating the " + getPipelineName() + " pipeline...", "npm install",
                "Dependencies installed successfully!");
    }

    @Override
    public Boolean run() {
        return executeStep("Building the project...", "npm build", "Build completed successfully!");
    }
}