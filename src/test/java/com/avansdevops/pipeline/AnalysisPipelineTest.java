package com.avansdevops.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class AnalysisPipelineTest {
    private PipelineTemplate analysisPipeline = Mockito.spy(new AnalysisPipeline("Analysis"));

    @Test
    public void getPipelineNameReturnsCorrectName() {
        assertEquals("Analysis", analysisPipeline.getPipelineName());
    }

    @Test
    public void pipelineSetupIsSuccessful() {
        assertTrue(analysisPipeline.setup());
    }

    @Test
    public void pipelineRunIsSuccessful() {
        assertTrue(analysisPipeline.run());
    }

    @Test
    public void pipelineTeardownIsSuccessful() {
        assertTrue(analysisPipeline.teardown());
    }

    @Test
    public void executePipelineSuccess() {
        Mockito.doReturn(true).when(analysisPipeline).setup();
        Mockito.doReturn(true).when(analysisPipeline).run();
        Mockito.doReturn(true).when(analysisPipeline).teardown();

        boolean result = analysisPipeline.executePipeline();
        assertTrue(result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(analysisPipeline);
        inOrder.verify(analysisPipeline).setup();
        inOrder.verify(analysisPipeline).run();
        inOrder.verify(analysisPipeline).teardown();
    }

    @Test
    public void executePipelineSetupFails() {
        Mockito.doReturn(false).when(analysisPipeline).setup();
        Mockito.doReturn(true).when(analysisPipeline).run();
        Mockito.doReturn(true).when(analysisPipeline).teardown();

        boolean result = analysisPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(analysisPipeline);
        inOrder.verify(analysisPipeline).setup();
        inOrder.verify(analysisPipeline, Mockito.never()).run();
        inOrder.verify(analysisPipeline, Mockito.never()).teardown();
    }

    @Test
    public void executePipelineRunFails() {
        Mockito.doReturn(true).when(analysisPipeline).setup();
        Mockito.doReturn(false).when(analysisPipeline).run();
        Mockito.doReturn(true).when(analysisPipeline).teardown();

        boolean result = analysisPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(analysisPipeline);
        inOrder.verify(analysisPipeline).setup();
        inOrder.verify(analysisPipeline).run();
        inOrder.verify(analysisPipeline, Mockito.never()).teardown();
    }

    @Test
    public void executePipelineTeardownFails() {
        Mockito.doReturn(true).when(analysisPipeline).setup();
        Mockito.doReturn(true).when(analysisPipeline).run();
        Mockito.doReturn(false).when(analysisPipeline).teardown();

        boolean result = analysisPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(analysisPipeline);
        inOrder.verify(analysisPipeline).setup();
        inOrder.verify(analysisPipeline).run();
        inOrder.verify(analysisPipeline).teardown();
    }
}