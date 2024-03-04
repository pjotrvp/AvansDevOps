package com.avansdevops.Pipelines;

public class AnalysisPipeline extends PipelineTemplate {
    public AnalysisPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        System.out.println("Initiating the " + this.getPipelineName() + " pipeline...");

        // Simulate running npm install
        System.out.println("Running command: surefire install");

        System.out.println("Dependencies installed successfully!");
        return true;
    }

    @Override
    public Boolean run() {
        // Simulate analyzing the project
        System.out.println("Analyzing the project...");

        // Simulate running npm analyze
        System.out.println("Running command: surefire analyse");

        System.out.println("Analysis completed successfully!");
        return true;
    }
}
