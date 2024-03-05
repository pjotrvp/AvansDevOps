package com.avansdevops.pipeline;

public class DeployPipeline extends PipelineTemplate {

    public DeployPipeline(String pipelineName) {
        super(pipelineName);
    }

    @Override
    public Boolean setup() {
        return executeStep("Initiating the " + getPipelineName() + " pipeline...", "cloudflare setup",
                "Cloudflare setup completed successfully!");
    }

    @Override
    public Boolean run() {
        return executeStep("Deploying the project...", "cloudflare deploy", "Deployment completed successfully!");
    }
}
