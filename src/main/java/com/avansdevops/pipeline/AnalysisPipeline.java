package com.avansdevops.pipeline;

public class AnalysisPipeline extends PipelineTemplate {

    public AnalysisPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        log("Initiating the " + getPipelineName() + " pipeline...");
        runCommand("surefire install");
        log("Dependencies installed successfully!");
        return true;
    }

    @Override
    public Boolean run() {
        log("Analyzing the project...");
        runCommand("surefire analyse");
        log("Analysis completed successfully!");
        return true;
    }
}
