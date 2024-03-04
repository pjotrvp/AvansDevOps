package com.avansdevops.PipelineTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avansdevops.Pipelines.PipelineTemplate;
import com.avansdevops.Pipelines.AnalysisPipeline;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
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
    public void executePipelineWorks() {
        // Call the method to test
        analysisPipeline.executePipeline();

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(analysisPipeline);
        inOrder.verify(analysisPipeline).setup();
        // inOrder.verify(analysisPipeline).run();
        inOrder.verify(analysisPipeline).teardown();
    }

    @Test
    public void threadSleepInterruptedExceptionSetup() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                analysisPipeline.setup();
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
                analysisPipeline.run();
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