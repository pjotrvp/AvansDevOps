package com.avansdevops.pipeline;

public class AnalysisPipeline extends PipelineTemplate {

    public AnalysisPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        return executeStep("Initiating the " + getPipelineName() + " pipeline...", "surefire install",
                "Dependencies installed successfully!");
    }

    @Override
    public Boolean run() {
        return executeStep("Analyzing the project...", "surefire analyse", "Analysis completed successfully!");
    }
}
