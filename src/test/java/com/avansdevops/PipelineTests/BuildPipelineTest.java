package com.avansdevops.PipelineTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avansdevops.Pipelines.PipelineTemplate;
import com.avansdevops.Pipelines.BuildPipeline;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
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
    public void executePipelineWorks() {
        // Call the method to test
        buildPipeline.executePipeline();

        // Verify that the methods were called in the correct order
        InOrder inOrder = Mockito.inOrder(buildPipeline);
        inOrder.verify(buildPipeline).setup();
        // inOrder.verify(buildPipeline).run();
        inOrder.verify(buildPipeline).teardown();
    }

    @Test
    public void threadSleepInterruptedExceptionSetup() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                buildPipeline.setup();
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
                buildPipeline.run();
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