package com.avansdevops.Pipelines;

public class TestPipeline extends PipelineTemplate {

    public TestPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        System.out.println("Initiating the " + this.getPipelineName() + " pipeline...");

        // Simulate running mvn install
        System.out.println("Running command: mvn install");

        // Simulate some delay
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Dependencies installed successfully!");
        return true;
    }

    @Override
    public Boolean run() {
        // Simulate testing the project
        System.out.println("Testing the project...");

        // Simulate running npm test
        System.out.println("Running command: mvn test");

        // Simulate some delay
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tests completed successfully!");
        return true;
    }
}
