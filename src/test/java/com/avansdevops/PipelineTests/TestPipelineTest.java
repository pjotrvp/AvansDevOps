package com.avansdevops.PipelineTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avansdevops.Pipelines.PipelineTemplate;
import com.avansdevops.Pipelines.TestPipeline;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
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
    public void executePipelineWorks() {
        // Call the method to test
        testPipeline.executePipeline();

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(testPipeline);
        inOrder.verify(testPipeline).setup();
        inOrder.verify(testPipeline).run();
        inOrder.verify(testPipeline).teardown();
    }

    @Test
    public void threadSleepInterruptedExceptionSetup() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                testPipeline.setup();
            }
        });

        thread.start();

        // Interrupt the thread
        thread.interrupt();

        try {
            // Wait for the thread to finish
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threadSleepInterruptedExceptionRun() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                testPipeline.run();
            }
        });

        thread.start();

        // Interrupt the thread
        thread.interrupt();

        try {
            // Wait for the thread to finish
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}