package com.github.robindevilliers.cascade;


import com.github.robindevilliers.cascade.annotations.*;
import com.github.robindevilliers.cascade.conditions.ConditionalLogic;
import com.github.robindevilliers.cascade.modules.Scanner;
import com.github.robindevilliers.cascade.modules.executor.StandardTestExecutor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import com.github.robindevilliers.cascade.modules.completeness.StandardCompletenessStrategy;
import com.github.robindevilliers.cascade.modules.construction.StandardConstructionStrategy;
import com.github.robindevilliers.cascade.modules.filtering.StandardFilterStrategy;
import com.github.robindevilliers.cascade.modules.generator.StepBackwardsFromTerminatorsJourneyGenerator;
import com.github.robindevilliers.cascade.modules.reporter.DisableReporter;
import com.github.robindevilliers.cascade.modules.reporter.RenderingSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TestTwoScenariosOverTwoSteps {

    static int count;
    static List<Integer> doThisSetupCalled = new ArrayList<>();
    static List<Integer> doThisExecuteCalled = new ArrayList<>();
    static List<Integer> doThisCheckCalled = new ArrayList<>();
    static List<Integer> doThisClearCalled = new ArrayList<>();

    static List<Integer> doThatSetupCalled = new ArrayList<>();
    static List<Integer> doThatExecuteCalled = new ArrayList<>();
    static List<Integer> doThatCheckCalled = new ArrayList<>();
    static List<Integer> doThatClearCalled = new ArrayList<>();


    @Before
    public void setup() {
        count = 0;

        doThisSetupCalled.clear();
        doThisExecuteCalled.clear();
        doThisCheckCalled.clear();
        doThisClearCalled.clear();

        doThatSetupCalled.clear();
        doThatExecuteCalled.clear();
        doThatCheckCalled.clear();
        doThatClearCalled.clear();
    }

    @Test
    public void givenOneStep_CascadeShouldGenerateOneDescriptionAndExecuteOneTest() {

        //given
        Scanner classpathScannerMock = mock(Scanner.class);
        when(classpathScannerMock.getTypesAnnotatedWith(Step.class)).thenReturn(new HashSet<Class<?>>() {{
            add(DoOne.class);
            add(DoTwo.class);
        }});

        when(classpathScannerMock.getSubTypesOf(DoOne.class)).thenReturn(new HashSet<Class>() {{
            add(DoOne.DoThis.class);

        }});

        when(classpathScannerMock.getSubTypesOf(DoTwo.class)).thenReturn(new HashSet<Class>() {{
            add(DoTwo.DoThat.class);
        }});

        RunNotifier runNotifierMock = mock(RunNotifier.class);

        //when
        Cascade cascade = new Cascade(classpathScannerMock,
                new ScenarioFinder(),
                new StepBackwardsFromTerminatorsJourneyGenerator(new ConditionalLogic()),
                new StandardConstructionStrategy(),
                new StandardTestExecutor(),
                new StandardFilterStrategy(new ConditionalLogic()),
                new StandardCompletenessStrategy(),
                new DisableReporter(),
                new RenderingSystem());

        cascade.init(TestBasicMain.class);

        org.junit.runner.Description description = cascade.getDescription();
        assertEquals("Cascade Tests", description.getDisplayName());

        List<Description> children = description.getChildren();
        assertEquals(1, children.size());

        org.junit.runner.Description child0 = children.get(0);
        assertEquals("Test[1] Do ThisDo That(uk.co.malbec.cascade.TestTwoScenariosOverTwoSteps$TestBasicMain)", child0.getDisplayName());

        cascade.run(runNotifierMock);

        //then
        verify(classpathScannerMock).initialise("uk.co.mytest.steps");
        verify(classpathScannerMock).getTypesAnnotatedWith(Step.class);
        verify(classpathScannerMock).getSubTypesOf(DoOne.class);
        verify(classpathScannerMock).getSubTypesOf(DoTwo.class);

        verify(runNotifierMock).fireTestStarted(child0);
        verify(runNotifierMock).fireTestFinished(child0);

        assertEquals("[1]", doThisSetupCalled.toString());
        assertEquals("[3]", doThisExecuteCalled.toString());
        assertEquals("[4]", doThisCheckCalled.toString());
        assertEquals("[7]", doThisClearCalled.toString());

        assertEquals("[2]", doThatSetupCalled.toString());
        assertEquals("[5]", doThatExecuteCalled.toString());
        assertEquals("[6]", doThatCheckCalled.toString());
        assertEquals("[8]", doThatClearCalled.toString());
    }


    @Step
    public interface DoOne {

        @Narrative("Do This")
        class DoThis implements DoOne {

            @Given
            public void setup() {
                count++;
                doThisSetupCalled.add(count);
            }

            @When
            public void execute() {
                count++;
                doThisExecuteCalled.add(count);
            }

            @Then
            public void check() {
                count++;
                doThisCheckCalled.add(count);
            }

            @Clear
            public void cleanup() {
                count++;
                doThisClearCalled.add(count);
            }
        }
    }

    @Step(DoOne.class)
    public interface DoTwo {


        @Narrative("Do That")
        class DoThat implements DoTwo {

            @Given
            public void setup() {
                count++;
                doThatSetupCalled.add(count);
            }

            @When
            public void execute() {
                count++;
                doThatExecuteCalled.add(count);
            }

            @Then
            public void check() {
                count++;
                doThatCheckCalled.add(count);
            }

            @Clear
            public void cleanup() {
                count++;
                doThatClearCalled.add(count);
            }
        }
    }


    @Scan("uk.co.mytest.steps")
    public static class TestBasicMain {

    }
}
