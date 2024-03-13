package com.avansdevops.pipeline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class TestPipelineTest {
    private PipelineTemplate testPipeline = Mockito.spy(new TestPipeline("Test"));

    @Test
    public void getPipelineNameReturnsCorrectName() {
        assertEquals("Test", testPipeline.getPipelineName());
    }

    @Test
    public void pipelineSetupIsSuccessful() {
        assertTrue(testPipeline.setup());
    }

    @Test
    public void pipelineRunIsSuccessful() {
        assertTrue(testPipeline.run());
    }

    @Test
    public void pipelineTeardownIsSuccessful() {
        assertTrue(testPipeline.teardown());
    }

    @Test
    public void executePipelineSuccess() {
        Mockito.doReturn(true).when(testPipeline).setup();
        Mockito.doReturn(true).when(testPipeline).run();
        Mockito.doReturn(true).when(testPipeline).teardown();

        boolean result = testPipeline.executePipeline();
        assertTrue(result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(testPipeline);
        inOrder.verify(testPipeline).setup();
        inOrder.verify(testPipeline).run();
        inOrder.verify(testPipeline).teardown();
    }

    @Test
    public void executePipelineSetupFails() {
        Mockito.doReturn(false).when(testPipeline).setup();
        Mockito.doReturn(true).when(testPipeline).run();
        Mockito.doReturn(true).when(testPipeline).teardown();

        boolean result = testPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(testPipeline);
        inOrder.verify(testPipeline).setup();
        inOrder.verify(testPipeline, Mockito.never()).run();
        inOrder.verify(testPipeline, Mockito.never()).teardown();
    }

    @Test
    public void executePipelineRunFails() {
        Mockito.doReturn(true).when(testPipeline).setup();
        Mockito.doReturn(false).when(testPipeline).run();
        Mockito.doReturn(true).when(testPipeline).teardown();

        boolean result = testPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(testPipeline);
        inOrder.verify(testPipeline).setup();
        inOrder.verify(testPipeline).run();
        inOrder.verify(testPipeline, Mockito.never()).teardown();
    }

    @Test
    public void executePipelineTeardownFails() {
        Mockito.doReturn(true).when(testPipeline).setup();
        Mockito.doReturn(true).when(testPipeline).run();
        Mockito.doReturn(false).when(testPipeline).teardown();

        boolean result = testPipeline.executePipeline();
        assertTrue(!result);

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(testPipeline);
        inOrder.verify(testPipeline).setup();
        inOrder.verify(testPipeline).run();
        inOrder.verify(testPipeline).teardown();
    }
}