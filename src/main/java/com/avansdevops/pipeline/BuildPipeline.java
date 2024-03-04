package com.avansdevops.pipeline;

public class BuildPipeline extends PipelineTemplate {
    public BuildPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        log("Initiating the " + getPipelineName() + " pipeline...");
        runCommand("npm install");
        log("Dependencies installed successfully!");
        return true;
    }

    @Override
    public Boolean run() {
        log("Building the project...");
        runCommand("npm build");
        log("Build completed successfully!");
        return true;
    }
}
