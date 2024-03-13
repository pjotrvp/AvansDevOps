package com.avansdevops.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class BuildPipelineTest {
    private PipelineTemplate buildPipeline = Mockito.spy(new BuildPipeline("Build"));

    @Test
    public void getPipelineNameReturnsCorrectName() {
        assertEquals("Build", buildPipeline.getPipelineName());
    }

    @Test
    public void pipelineSetupIsSuccessful() {
        assertTrue(buildPipeline.setup());
    }

    @Test
    public void pipelineRunIsSuccessful() {
        assertTrue(buildPipeline.run());
    }

    @Test
    public void pipelineTeardownIsSuccessful() {
        assertTrue(buildPipeline.teardown());
    }

    @Test
    public void executePipelineSuccess() {
        Mockito.doReturn(true).when(buildPipeline).setup();
        Mockito.doReturn(true).when(buildPipeline).run();
        Mockito.doReturn(true).when(buildPipeline).teardown();

        boolean result = buildPipeline.executePipeline();
        assertTrue(result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(buildPipeline);
        inOrder.verify(buildPipeline).setup();
        inOrder.verify(buildPipeline).run();
        inOrder.verify(buildPipeline).teardown();
    }

    @Test
    public void executePipelineSetupFails() {
        Mockito.doReturn(false).when(buildPipeline).setup();
        Mockito.doReturn(true).when(buildPipeline).run();
        Mockito.doReturn(true).when(buildPipeline).teardown();

        boolean result = buildPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(buildPipeline);
        inOrder.verify(buildPipeline).setup();
        inOrder.verify(buildPipeline, Mockito.never()).run();
        inOrder.verify(buildPipeline, Mockito.never()).teardown();
    }

    @Test
    public void executePipelineRunFails() {
        Mockito.doReturn(true).when(buildPipeline).setup();
        Mockito.doReturn(false).when(buildPipeline).run();
        Mockito.doReturn(true).when(buildPipeline).teardown();

        boolean result = buildPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(buildPipeline);
        inOrder.verify(buildPipeline).setup();
        inOrder.verify(buildPipeline).run();
        inOrder.verify(buildPipeline, Mockito.never()).teardown();
    }

    @Test
    public void executePipelineTeardownFails() {
        Mockito.doReturn(true).when(buildPipeline).setup();
        Mockito.doReturn(true).when(buildPipeline).run();
        Mockito.doReturn(false).when(buildPipeline).teardown();

        boolean result = buildPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(buildPipeline);
        inOrder.verify(buildPipeline).setup();
        inOrder.verify(buildPipeline).run();
        inOrder.verify(buildPipeline).teardown();
    }
}