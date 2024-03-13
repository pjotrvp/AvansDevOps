package com.avansdevops.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class DeployPipelineTest {
    private PipelineTemplate deployPipeline = Mockito.spy(new DeployPipeline("Analysis"));

    @Test
    public void getPipelineNameReturnsCorrectName() {
        assertEquals("Analysis", deployPipeline.getPipelineName());
    }

    @Test
    public void pipelineSetupIsSuccessful() {
        assertTrue(deployPipeline.setup());
    }

    @Test
    public void pipelineRunIsSuccessful() {
        assertTrue(deployPipeline.run());
    }

    @Test
    public void pipelineTeardownIsSuccessful() {
        assertTrue(deployPipeline.teardown());
    }

    @Test
    public void executePipelineSuccess() {
        Mockito.doReturn(true).when(deployPipeline).setup();
        Mockito.doReturn(true).when(deployPipeline).run();
        Mockito.doReturn(true).when(deployPipeline).teardown();

        boolean result = deployPipeline.executePipeline();
        assertTrue(result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(deployPipeline);
        inOrder.verify(deployPipeline).setup();
        inOrder.verify(deployPipeline).run();
        inOrder.verify(deployPipeline).teardown();
    }

    @Test
    public void executePipelineSetupFails() {
        Mockito.doReturn(false).when(deployPipeline).setup();
        Mockito.doReturn(true).when(deployPipeline).run();
        Mockito.doReturn(true).when(deployPipeline).teardown();

        boolean result = deployPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(deployPipeline);
        inOrder.verify(deployPipeline).setup();
        inOrder.verify(deployPipeline, Mockito.never()).run();
        inOrder.verify(deployPipeline, Mockito.never()).teardown();
    }

    @Test
    public void executePipelineRunFails() {
        Mockito.doReturn(true).when(deployPipeline).setup();
        Mockito.doReturn(false).when(deployPipeline).run();
        Mockito.doReturn(true).when(deployPipeline).teardown();

        boolean result = deployPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(deployPipeline);
        inOrder.verify(deployPipeline).setup();
        inOrder.verify(deployPipeline).run();
        inOrder.verify(deployPipeline, Mockito.never()).teardown();
    }

    @Test
    public void executePipelineTeardownFails() {
        Mockito.doReturn(true).when(deployPipeline).setup();
        Mockito.doReturn(true).when(deployPipeline).run();
        Mockito.doReturn(false).when(deployPipeline).teardown();

        boolean result = deployPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(deployPipeline);
        inOrder.verify(deployPipeline).setup();
        inOrder.verify(deployPipeline).run();
        inOrder.verify(deployPipeline).teardown();
    }
}